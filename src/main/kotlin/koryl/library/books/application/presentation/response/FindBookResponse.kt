package koryl.library.books.application.presentation.response

import koryl.library.books.application.presentation.response.dto.BookDto
import java.io.Serializable

data class FindBookResponse(
        var content: List<BookDto>,
        var totalPages: Int,
        var totalElements: Long
) : Serializable
