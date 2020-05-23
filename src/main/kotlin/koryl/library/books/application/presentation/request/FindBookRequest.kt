package koryl.library.books.application.presentation.request

import java.io.Serializable

data class FindBookRequest(
        var title: String?,
        var author: String?,
        var isbn: String?
) : Serializable
