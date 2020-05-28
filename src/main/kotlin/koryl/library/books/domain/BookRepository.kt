package koryl.library.books.domain

import org.springframework.data.domain.Pageable

interface BookRepository {

    fun findBookById(id: Long): Book

    fun findAllBooksByCriteria(title: String?, author: String?, isbn: String?, pageable: Pageable): BookPage

    fun searchBooksByText(searchText: String, pageable: Pageable): BookPage

    fun saveBook(book: Book): Book

    fun removeBookById(id: Long)

    fun saveBorrowedOrder(borrowedOrder: BorrowedOrder): BorrowedOrder

    fun findOrder(userId: String, orderGuid: String): BorrowedOrder

}
