package koryl.library.books.domain

import java.io.Serializable
import java.time.LocalDate

data class Book(
        var guid: String?,
        var title: String,
        var author: String,
        var description: String,
        var isbn: String,
        var language: String,
        var pages: Int,
        var publisher: String,
        var publicationDate: LocalDate
) : Serializable
