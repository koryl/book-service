package koryl.library.books.domain

import koryl.library.books.infrastructure.exception.GeneralException
import koryl.library.books.infrastructure.utils.Logging
import koryl.library.books.infrastructure.utils.logger
import java.time.LocalDateTime

open class Borrow : Logging {
    var book: Book
    var userId: String
    lateinit var borrowingDate: LocalDateTime
    lateinit var returnDate: LocalDateTime


    constructor(book: Book, userId: String) {
        this.book = book
        this.userId = userId
    }

    constructor(book: Book, userId: String, borrowingDate: LocalDateTime, returnDate: LocalDateTime) {
        this.book = book
        this.userId = userId
        this.borrowingDate = borrowingDate
        this.returnDate = returnDate
    }

    fun borrowBook() {
        if (book.status == BookStatus.BORROWED) {
            logger().warn("The prerequisite condition for this book {} is not met", book.guid)
            throw GeneralException("Cannot borrowed already borrowed book")
        }
        logger().info("Borrowing book with guid: {}", book.guid)
        book.status = BookStatus.BORROWED
        borrowingDate = LocalDateTime.now()
        returnDate = LocalDateTime.now().plusDays(14)
        logger().info("Prepared borrowing book data")
    }
}
