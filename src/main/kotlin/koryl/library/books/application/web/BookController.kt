package koryl.library.books.application.web

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
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
    @Parameters(value = [
        Parameter(name = "page", description = "result page number"),
        Parameter(name = "size", description = "result page size"),
        Parameter(name = "sort", description = "sort specification")
    ])
    fun findBooks(request: FindBookRequest, @Parameter(hidden = true) page: Pageable) =
            bookFacade.findBooks(request, page)

    @GetMapping("/search")
    @Parameters(value = [
        Parameter(name = "page", description = "result page number"),
        Parameter(name = "size", description = "result page size"),
        Parameter(name = "sort", description = "sort specification")
    ])
    fun findBooks(searchText: String, @Parameter(hidden = true) page: Pageable) =
            bookFacade.searchBooks(searchText, page)

    @PostMapping
    fun saveBook(@RequestBody request: SaveBookRequest) = bookFacade.saveBook(request)

    @DeleteMapping("/{id}")
    fun removeBookById(@PathVariable id: Long) = bookFacade.removeBook(id)

}
