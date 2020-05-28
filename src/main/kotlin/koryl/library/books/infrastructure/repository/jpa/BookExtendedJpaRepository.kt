package koryl.library.books.infrastructure.repository.jpa

import koryl.library.books.domain.Book
import koryl.library.books.domain.BookPage
import koryl.library.books.domain.BookRepository
import koryl.library.books.domain.BorrowedOrder
import koryl.library.books.infrastructure.exception.GeneralException
import koryl.library.books.infrastructure.utils.Logging
import koryl.library.books.infrastructure.utils.logger
import org.apache.lucene.search.Query
import org.hibernate.search.jpa.FullTextEntityManager
import org.hibernate.search.jpa.Search
import org.hibernate.search.query.dsl.QueryBuilder
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
internal class BookExtendedJpaRepository(private val bookJpaRepository: BookJpaRepository,
                                         private val borrowedOrderJpaRepository: BorrowedOrderJpaRepository,
                                         private val entityManager: EntityManager)
    : BookRepository, Logging {


    override fun findBookById(id: Long): Book {
        logger().info("Finding book by id {} in database...", id)
        val bookOp = handle { bookJpaRepository.findById(id) }
        val bookEntity = bookOp.orElseThrow { throw GeneralException("Cannot find book for id!") }
        logger().info("Finished finding book in database")
        return bookEntity.toDomain()
    }

    override fun findAllBooksByCriteria(title: String?, author: String?, isbn: String?, pageable: Pageable): BookPage {
        logger().info("Finding books by criteria in database...")
        val resultPage = handle { bookJpaRepository.findAllByCriteria(title, author, isbn, pageable) }
        logger().info("It was found {} books", resultPage.content.size)

        return toBookPage(resultPage)
    }

    override fun searchBooksByText(searchText: String, pageable: Pageable): BookPage {
        logger().info("Searching books by text in database...")
        val resultPage = handle { searchBooks(searchText, pageable) }
        logger().info("It was found {} books", resultPage.content.size)

        return toBookPage(resultPage)
    }

    private fun searchBooks(searchText: String, pageable: Pageable): Page<BookEntity> {
        val fullTextEntityManager = Search.getFullTextEntityManager(entityManager)
        val luceneQuery = getQueryBuilder(fullTextEntityManager)
                .keyword()
                .onFields(TITLE, AUTHOR, DESCRIPTION, ISBN)
                .matching(searchText)
                .createQuery()

        val result = getJpaQuery(luceneQuery, fullTextEntityManager, pageable)
        return PageImpl(result, pageable, result.size.toLong())
    }


    private fun getQueryBuilder(fullTextEntityManager: FullTextEntityManager): QueryBuilder {
        return fullTextEntityManager.searchFactory
                .buildQueryBuilder()
                .forEntity(BookEntity::class.java)
                .get()
    }

    @Suppress("UNCHECKED_CAST")
    private fun getJpaQuery(luceneQuery: Query, fullTextEntityManager: FullTextEntityManager, pageable: Pageable): List<BookEntity> {
        return fullTextEntityManager.createFullTextQuery(luceneQuery, BookEntity::class.java)
                .setFirstResult(pageable.pageSize * pageable.pageNumber)
                .setMaxResults(pageable.pageSize)
                .resultList as List<BookEntity>
    }

    override fun removeBookById(id: Long) {
        logger().info("Removing book by id {}...", id)
        handle { bookJpaRepository.deleteById(id) }
        logger().info("Book with id {} was successfully removed", id)
    }

    override fun saveBook(book: Book): Book {
        val bookEntity = book.toJpaEntity()

        logger().info("Saving new book...")
        val savedBook = handle { bookJpaRepository.save(bookEntity) }
        logger().info("Book was saved successfully!")

        return savedBook.toDomain()
    }

    override fun saveBorrowedOrder(borrowedOrder: BorrowedOrder): BorrowedOrder {
        val borrowedOrderEntity = borrowedOrder.toJpaEntity()

        logger().info("Saving new book...")
        val savedOrder = handle { borrowedOrderJpaRepository.save(borrowedOrderEntity) }
        logger().info("Book was saved successfully!")

        return savedOrder.toDomain()
    }

    override fun findOrder(userId: String, orderGuid: String): BorrowedOrder {
        logger().info("Saving new book...")
        val order = handle { borrowedOrderJpaRepository.findByUserIdAndGuid(userId, orderGuid) }
        logger().info("Book was saved successfully!")

        return order.toDomain()
    }

    private fun <T> handle(func: () -> T): T {
        try {
            return func()

        } catch (ex: Exception) {
            logger().error("Error occurred during handling database operation!", ex)
            throw GeneralException("Database operation failure!")
        }
    }

}
