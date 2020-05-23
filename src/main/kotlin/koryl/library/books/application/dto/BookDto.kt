package koryl.library.books.application.dto

data class BookDto(
        var guid: String,
        var title: String,
        var author: String,
        var description: String,
        var isbn: String,
        var language: String,
        var pages: Int,
        var publicationDate: String,
        var publisher: String,
        var status: String
)
