package koryl.library.books.domain

import koryl.library.books.domain.BorrowedOrderStatus.COMPLETED
import java.time.LocalDateTime

data class BorrowedOrder(
        val id: Long?,
        val guid: String,
        val book: Book,
        val userId: String,
        var borrowingDate: LocalDateTime,
        var estimatedReturnDate: LocalDateTime,
        var actualReturnDate: LocalDateTime?,
        var status: BorrowedOrderStatus
)

internal fun prepareOrder(guid: String,
                          book: Book,
                          userId: String,
                          borrowingDate: LocalDateTime,
                          estimatedReturnDate: LocalDateTime,
                          status: BorrowedOrderStatus) =
        BorrowedOrder(
                null,
                guid,
                book,
                userId,
                borrowingDate,
                estimatedReturnDate,
                null,
                status
        )

internal fun BorrowedOrder.returnOrder() {
        book.prepareToReturn()
        actualReturnDate = LocalDateTime.now()
        status = COMPLETED
}

