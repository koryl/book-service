package koryl.library.books.domain

import org.springframework.data.domain.Pageable

interface BookRepository {

    fun findBookByGuid(guid: String): Book

    fun findAllBooksByCriteria(title: String?, author: String?, isbn: String?, pageable: Pageable): BookPage

    fun searchBooksByText(searchText: String, pageable: Pageable): BookPage

    fun saveBook(book: Book): Book

    fun removeBookByGuid(guid: String)

}
