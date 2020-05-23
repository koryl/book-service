package koryl.library.books.infrastructure.repository.jpa

import koryl.library.books.domain.Book
import koryl.library.books.domain.BookPage
import koryl.library.books.domain.BookRepository
import koryl.library.books.infrastructure.exception.GeneralException
import koryl.library.books.infrastructure.utils.Logging
import koryl.library.books.infrastructure.utils.Mapping
import koryl.library.books.infrastructure.utils.logger
import koryl.library.books.infrastructure.utils.mapping
import org.apache.lucene.search.Query
import org.hibernate.search.jpa.FullTextEntityManager
import org.hibernate.search.jpa.FullTextQuery
import org.hibernate.search.jpa.Search
import org.hibernate.search.query.dsl.QueryBuilder
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class BookExtendedJpaRepository(private val bookJpaRepository: BookJpaRepository,
                                private val entityManager: EntityManager)
    : BookRepository, Logging, Mapping {


    override fun findBookByGuid(guid: String): Book {
        logger().info("Finding book by guid {} in database...", guid)
        val bookOp = handle { bookJpaRepository.findByGuid(guid) }
        val bookEntity = bookOp.orElseThrow { throw GeneralException("Cannot find book for guid!") }
        logger().info("Finished finding book in database")
        return mapping(bookEntity, Book::class)
    }

    override fun findAllBooksByCriteria(title: String?, author: String?, isbn: String?, pageable: Pageable): BookPage {
        logger().info("Finding books by criteria in database...")
        val bookPage = handle { bookJpaRepository.findAllByCriteria(title, author, isbn, pageable) }
        logger().info("It was found {} books", bookPage.content.size)

        return mapping(bookPage, BookPage::class)
    }

    override fun searchBooksByText(searchText: String, pageable: Pageable): BookPage {
        logger().info("Searching books by text in database...")
        val bookPage = handle { searchBooks(searchText, pageable) }
        logger().info("It was found {} books", bookPage.content.size)

        return mapping(bookPage, BookPage::class)
    }

    private fun searchBooks(searchText: String, pageable: Pageable): BookPage {
        val fullTextEntityManager = Search.getFullTextEntityManager(entityManager)
        val luceneQuery = getQueryBuilder(fullTextEntityManager)
                .keyword()
                .onFields(TITLE, AUTHOR, DESCRIPTION, ISBN)
                .matching(searchText)
                .createQuery()

        val result = getJpaQuery(luceneQuery, fullTextEntityManager, pageable).resultList
        val pageResult = PageImpl(result, pageable, result.size.toLong())
        return mapping(pageResult, BookPage::class)
    }


    private fun getQueryBuilder(fullTextEntityManager: FullTextEntityManager): QueryBuilder {
        return fullTextEntityManager.searchFactory
                .buildQueryBuilder()
                .forEntity(BookEntity::class.java)
                .get()
    }

    private fun getJpaQuery(luceneQuery: Query, fullTextEntityManager: FullTextEntityManager, pageable: Pageable): FullTextQuery {
        return fullTextEntityManager.createFullTextQuery(luceneQuery, BookEntity::class.java)
                .setFirstResult(pageable.pageSize * pageable.pageNumber)
                .setMaxResults(pageable.pageSize)
    }

    override fun removeBookByGuid(guid: String) {
        logger().info("Removing book by guid {}...", guid)
        handle { bookJpaRepository.removeByGuid(guid) }
        logger().info("Book with guid {} was successfully removed", guid)
    }

    override fun saveBook(book: Book): Book {
        val bookEntity = mapping(book, BookEntity::class)

        logger().info("Saving new book...")
        val savedBook = handle { bookJpaRepository.save(bookEntity) }
        logger().info("Book was saved successfully!")

        return mapping(savedBook, Book::class)
    }

    override fun updateBook(book: Book): Book {
        val bookEntity = mapping(book, BookEntity::class)
        logger().info("Saving new book...")
        val savedBook = handle { bookJpaRepository.save(bookEntity) }
        logger().info("Book was saved successfully!")

        return mapping(savedBook, Book::class)
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
