package koryl.library.books.application.presentation.response

import koryl.library.books.application.dto.BookDto

data class FindBookResponse(
        var content: List<BookDto>,
        var totalPages: Int,
        var totalElements: Long
)

