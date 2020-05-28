package koryl.library.books.application.presentation.request

import java.io.Serializable
import java.time.LocalDate

data class SaveBookRequest(
        var title: String,
        var author: String,
        var description: String,
        var isbn: String,
        var language: String,
        var pages: Int,
        var publicationDate: LocalDate,
        var publisher: String
) : Serializable
