package koryl.library.books.application.presentation.response

import java.io.Serializable

data class GetBookResponse(
        var guid: String,
        var title: String,
        var author: String,
        var isbn: String,
        var language: String,
        var pages: Int,
        var publicationDate: String,
        var publisher: String
) : Serializable
