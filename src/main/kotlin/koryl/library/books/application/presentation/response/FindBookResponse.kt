package koryl.library.books.application.presentation.response

import koryl.library.books.application.dto.BookDto
import koryl.library.books.domain.BookPage
import java.io.Serializable

data class FindBookResponse(
        var content: List<BookDto>,
        var totalPages: Int,
        var totalElements: Long
) : Serializable {

    constructor(bookPage: BookPage) {
        content = bookPage.content.map { BookDto(it) }
        totalPages = bookPage.totalPages
        totalElements = bookPage.totalElements
    }
}

