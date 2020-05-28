package koryl.library.books.domain

import koryl.library.books.infrastructure.utils.Logging
import koryl.library.books.infrastructure.utils.logger
import org.springframework.data.domain.Pageable

class BookService(private val bookRepository: BookRepository) : Logging {

    fun getBookById(bookId: Long): Book {
        return bookRepository.findBookById(bookId)
    }

    fun findBooks(title: String?, author: String?, isbn: String?, pageable: Pageable): BookPage {
        logger().info("Finding books by criteria...")
        return bookRepository.findAllBooksByCriteria(title, author, isbn, pageable)
    }

    fun searchBooks(searchText: String, pageable: Pageable): BookPage {
        logger().info("Search books by text...")
        return bookRepository.searchBooksByText(searchText, pageable)
    }

    fun saveBook(book: Book): Long {
        val savedBook = bookRepository.saveBook(book)
        return savedBook.id!!
    }

    fun removeBook(bookId: Long) {
        bookRepository.removeBookById(bookId)
    }

    fun borrowBook(userId: String, id: Long): BorrowedOrder {
        logger().info("Making book borrowing attempt...")
        val book = bookRepository.findBookById(id)
        val borrow = book.borrowBook(userId)
        bookRepository.saveBook(borrow.book)
        val savedOrder = bookRepository.saveBorrowedOrder(borrow)
        logger().info("Book was borrowed successfully!")
        return savedOrder
    }

    fun returnBook(userId: String, orderGuid: String) {
        logger().info("Attempt to return book...")
        val order = bookRepository.findOrder(userId, orderGuid)
        order.returnOrder()
        bookRepository.saveBorrowedOrder(order)
        logger().info("Book was return successfully!")
    }
}
