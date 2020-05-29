package koryl.library.books.application.facade

import koryl.library.books.application.presentation.response.FindBookResponse
import koryl.library.books.application.presentation.response.GetBookResponse
import koryl.library.books.application.presentation.response.dto.BookDto
import koryl.library.books.domain.Book
import koryl.library.books.domain.BookPage

internal fun fromBookToGetBookResponse(book: Book) =
        GetBookResponse(
                id = book.id!!.toString(),
                title = book.title,
                author = book.author,
                isbn = book.isbn,
                language = book.language,
                pages = book.pages,
                publicationDate = book.publicationDate.toString(),
                publisher = book.publisher,
                status = book.status!!.toString(),
                description = book.description
        )

internal fun fromBookPageToFindBookResponse(bookPage: BookPage) =
        FindBookResponse(
                bookPage.content.map { fromBookToDto(it) },
                bookPage.totalPages,
                bookPage.totalElements
        )

internal fun fromBookToDto(book: Book) =
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
