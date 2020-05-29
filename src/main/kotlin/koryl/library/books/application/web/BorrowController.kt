package koryl.library.books.application.web

import koryl.library.books.application.facade.BookFacade
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/book/order")
class BorrowController(private val bookFacade: BookFacade) {

    @GetMapping("/borrow")
    fun borrowBook(@RequestHeader userId: String, @RequestParam bookId: Long) =
            bookFacade.borrowBook(userId, bookId)

    @GetMapping("/return")
    fun returnBook(@RequestHeader userId: String, @RequestParam orderGuid: String) =
            bookFacade.returnBook(userId, orderGuid)
}
