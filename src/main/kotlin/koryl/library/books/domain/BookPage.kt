package koryl.library.books.domain

import java.io.Serializable

data class BookPage(
        var content : List<Book>,
        var totalPages: Int,
        var totalElements: Long
) : Serializable
