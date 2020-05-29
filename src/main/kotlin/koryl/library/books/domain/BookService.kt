package koryl.library.books.domain

import org.springframework.data.domain.Pageable

interface BookService {
    fun getBookById(bookId: Long): Book

    fun findBooks(title: String?, author: String?, isbn: String?, pageable: Pageable): BookPage

    fun searchBooks(searchText: String, pageable: Pageable): BookPage

    fun saveBook(book: Book): Long

    fun removeBook(bookId: Long)

    fun borrowBook(userId: String, id: Long): BorrowedOrder

    fun returnBook(userId: String, orderGuid: String)
}
