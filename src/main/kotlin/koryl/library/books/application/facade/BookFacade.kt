package koryl.library.books.application.facade

import koryl.library.books.application.presentation.request.FindBookRequest
import koryl.library.books.application.presentation.request.SaveBookRequest
import koryl.library.books.application.presentation.response.CreateBorrowedOrderResponse
import koryl.library.books.application.presentation.response.FindBookResponse
import koryl.library.books.application.presentation.response.GetBookResponse
import koryl.library.books.application.presentation.response.SaveBookResponse
import koryl.library.books.domain.BookService
import koryl.library.books.infrastructure.utils.fromSaveRequestToBook
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookFacade(private val bookService: BookService) {

    @Transactional(readOnly = true)
    fun findBookById(id: Long): GetBookResponse {
        val book = bookService.getBookById(id)
        return GetBookResponse(book)
    }

    @Transactional(readOnly = true)
    fun findBooks(request: FindBookRequest, pageable: Pageable): FindBookResponse {
        val bookPage = bookService.findBooks(request.title, request.author, request.isbn, pageable)
        return FindBookResponse(bookPage)
    }

    @Transactional(readOnly = true)
    fun searchBooks(searchText: String, pageable: Pageable): FindBookResponse {
        val bookPage = bookService.searchBooks(searchText, pageable)
        return FindBookResponse(bookPage)
    }

    @Transactional
    fun saveBook(request: SaveBookRequest): SaveBookResponse {
        val book = fromSaveRequestToBook(request)
        val id = bookService.saveBook(book)
        return SaveBookResponse(id.toString())
    }

    @Transactional
    fun removeBook(id: Long) {
        bookService.removeBook(id)
    }

    @Transactional
    fun borrowBook(userId: String, bookId: Long): CreateBorrowedOrderResponse {
        val order = bookService.borrowBook(userId, bookId)
        return CreateBorrowedOrderResponse(order.guid, order.borrowingDate, order.estimatedReturnDate)
    }

    @Transactional
    fun returnBook(userId: String, orderGuid: String) {
        bookService.returnBook(userId, orderGuid)
    }

}
