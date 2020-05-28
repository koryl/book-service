package koryl.library.books.application.presentation.response

import java.time.LocalDateTime

data class CreateBorrowedOrderResponse(val guid: String, val borrowingDate: LocalDateTime, val returnDate: LocalDateTime)
