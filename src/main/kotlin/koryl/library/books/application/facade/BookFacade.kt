package koryl.library.books.application.facade

import koryl.library.books.application.presentation.request.FindBookRequest
import koryl.library.books.application.presentation.request.SaveBookRequest
import koryl.library.books.application.presentation.response.FindBookResponse
import koryl.library.books.application.presentation.response.GetBookResponse
import koryl.library.books.application.presentation.response.SaveBookResponse
import koryl.library.books.domain.Book
import koryl.library.books.domain.BookService
import koryl.library.books.domain.BookStatus.AVAILABLE
import koryl.library.books.infrastructure.utils.Mapping
import koryl.library.books.infrastructure.utils.mapping
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookFacade(private val bookService: BookService) : Mapping {

    @Transactional(readOnly = true)
    fun findBookByGuid(guid: String): GetBookResponse {
        val bookPage = bookService.getBookByGuid(guid)
        return mapping(bookPage, GetBookResponse::class)
    }

    @Transactional(readOnly = true)
    fun findBooks(request: FindBookRequest, pageable: Pageable): FindBookResponse {
        val bookPage = bookService.findBooks(request.title, request.author, request.isbn, pageable)
        return mapping(bookPage, FindBookResponse::class)
    }

    @Transactional(readOnly = true)
    fun searchBooks(searchText: String, pageable: Pageable): FindBookResponse {
        val bookPage = bookService.searchBooks(searchText, pageable)
        return mapping(bookPage, FindBookResponse::class)
    }

    @Transactional
    fun saveBook(request: SaveBookRequest): SaveBookResponse {
        val book = mapping(request, Book::class)
        book.status = AVAILABLE
        val id = bookService.saveBook(book)
        return SaveBookResponse(id)
    }

    @Transactional
    fun removeBook(guid: String) {
        bookService.removeBook(guid)
    }

}
