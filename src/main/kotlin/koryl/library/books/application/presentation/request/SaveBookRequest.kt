package koryl.library.books.application.presentation.request

import java.io.Serializable

data class SaveBookRequest(
        var title: String,
        var author: String,
        var description: String,
        var isbn: String,
        var language: String,
        var pages: Int,
        var publicationDate: String,
        var publisher: String
) : Serializable
