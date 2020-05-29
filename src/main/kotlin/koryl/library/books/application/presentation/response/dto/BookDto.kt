package koryl.library.books.application.presentation.response.dto

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
)

fun fromBookToDto(book: Book) =
        BookDto(book.id!!.toString(),
                book.title,
                book.author,
                book.description,
                book.isbn,
                book.language,
                book.pages,
                book.publicationDate.toString(),
                book.publisher,
                book.status!!.toString()
        )
