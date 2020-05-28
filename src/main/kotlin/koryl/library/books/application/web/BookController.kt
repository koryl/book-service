package koryl.library.books.application.web

import koryl.library.books.application.facade.BookFacade
import koryl.library.books.application.presentation.request.FindBookRequest
import koryl.library.books.application.presentation.request.SaveBookRequest
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/book")
class BookController(private val bookFacade: BookFacade) {

    @GetMapping("/{id}")
    fun getBookById(@PathVariable id: Long) = bookFacade.findBookById(id)

    @GetMapping("/find")
    fun findBooks(request: FindBookRequest, page: Pageable) = bookFacade.findBooks(request, page)

    @GetMapping("/search")
    fun findBooks(searchText: String, page: Pageable) = bookFacade.searchBooks(searchText, page)

    @PostMapping
    fun saveBook(@RequestBody request: SaveBookRequest) = bookFacade.saveBook(request)

    @DeleteMapping("/{id}")
    fun removeBookById(@PathVariable id: Long) = bookFacade.removeBook(id)

}
