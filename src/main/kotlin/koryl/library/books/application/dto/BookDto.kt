package koryl.library.books.application.dto

import koryl.library.books.domain.Book

data class BookDto(
        var id: String,
        var title: String,
        var author: String,
        var description: String,
        var isbn: String,
        var language: String,
        var pages: Int,
        var publicationDate: String,
        var publisher: String,
        var status: String
) {
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
