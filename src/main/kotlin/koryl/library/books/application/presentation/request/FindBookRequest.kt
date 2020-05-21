package koryl.library.books.application.presentation.request

data class FindBookRequest(
        var title: String?,
        var author: String?,
        var isbn: String?
)
