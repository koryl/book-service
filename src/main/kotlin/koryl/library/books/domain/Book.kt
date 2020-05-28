package koryl.library.books.domain

import koryl.library.books.domain.BorrowedOrderStatus.SUBMITTED
import koryl.library.books.infrastructure.exception.GeneralException
import koryl.library.books.infrastructure.utils.Logging
import koryl.library.books.infrastructure.utils.logger
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

data class Book(
        var id: Long?,
        var title: String,
        var author: String,
        var description: String,
        var isbn: String,
        var language: String,
        var pages: Int,
        var publisher: String,
        var publicationDate: LocalDate,
        var status: BookStatus?,
        var version: Int?
) : Serializable, Logging

internal fun Book.borrowBook(userId: String): BorrowedOrder {
    if (this.status == BookStatus.BORROWED) {
        logger().warn("The prerequisite condition for this book {} is not met", this.id)
        throw GeneralException("Cannot borrowed already borrowed book!")
    }
    logger().info("Borrowing book with id: {}", this.id)
    this.status = BookStatus.BORROWED
    val guid = UUID.randomUUID().toString()
    val borrowingDate = LocalDateTime.now()
    val estimatedReturnDate = LocalDateTime.now().plusDays(14)
    logger().info("Prepared borrowing book data")
    return prepareOrder(guid, this, userId, borrowingDate, estimatedReturnDate, SUBMITTED)
}

internal fun Book.prepareToReturn() {
    if (this.status == BookStatus.AVAILABLE) {
        logger().warn("The prerequisite condition for this book {} is not met", this.id)
        throw GeneralException("Cannot return already available book!")
    }
    logger().info("Returning book with id: {}", this.id)
    this.status = BookStatus.AVAILABLE
    logger().info("Prepared borrowing book data")
}
