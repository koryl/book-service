package koryl.library.books.domain

import koryl.library.books.infrastructure.utils.Logging
import koryl.library.books.infrastructure.utils.logger
import org.springframework.data.domain.Pageable
import java.util.*

class BookService(private val bookRepository: BookRepository) : Logging {

    fun getBookByGuid(guid: String): Book {
        return bookRepository.findBookByGuid(guid)
    }

    fun findBooks(title: String?, author: String?, isbn: String?, pageable: Pageable): BookPage {
        logger().info("Finding books by criteria...")
        return bookRepository.findAllBooksByCriteria(title, author, isbn, pageable)
    }

    fun searchBooks(searchText: String, pageable: Pageable): BookPage {
        logger().info("Search books by text...")
        return bookRepository.searchBooksByText(searchText, pageable)
    }

    fun saveBook(book: Book): String {
        book.guid = UUID.randomUUID().toString()
        val savedBook = bookRepository.saveBook(book)
        return savedBook.guid.orEmpty()
    }

    fun removeBook(guid: String) {
        bookRepository.removeBookByGuid(guid)
    }

    fun borrowBook(guid: String, userId: String) {
        val book = bookRepository.findBookByGuid(guid)
        val borrow = Borrow(book, userId)
        borrow.borrowBook()
        bookRepository.updateBook(borrow.book)
    }
}
