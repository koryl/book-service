package koryl.library.books.application.presentation.response

import koryl.library.books.domain.Book
import java.io.Serializable

data class GetBookResponse(
        var id: String,
        var title: String,
        var author: String,
        var isbn: String,
        var language: String,
        var pages: Int,
        var publicationDate: String,
        var publisher: String,
        var status: String
) : Serializable {

    constructor(book: Book) {
        id = book.id!!.toString()
        title = book.title
        author = book.author
        isbn = book.isbn
        language = book.language
        pages = book.pages
        publicationDate = book.publicationDate.toString()
        publisher = book.publisher
        status = book.status!!.toString()
    }
}
