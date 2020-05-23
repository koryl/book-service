package koryl.library.books.application.web

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/book")
class BorrowController {

    @GetMapping("/borrow")
    fun borrowBook(@RequestHeader userId: String, @RequestParam bookGuid: String) {
    }

    @GetMapping("/return")
    fun returnBook(@RequestHeader userId: String, @RequestParam bookGuid: String) {
    }
}
