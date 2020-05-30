package koryl.library.books.infrastructure.repository.jpa

import koryl.library.books.domain.Book
import koryl.library.books.domain.BookPage
import koryl.library.books.domain.BookStatus
import org.hibernate.search.annotations.Field
import org.hibernate.search.annotations.Indexed
import org.springframework.data.domain.Page
import java.io.Serializable
import java.time.LocalDate
import javax.persistence.*

@Entity
@Indexed
@Table(name = BOOKS)
data class BookEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        val id: Long?,

        @Field
        @Column(name = TITLE, nullable = false)
        val title: String,

        @Field
        @Column(name = AUTHOR, nullable = false)
        val author: String,

        @Field
        @Column(name = DESCRIPTION, nullable = false, length = 1000)
        val description: String,

        @Field
        @Column(name = ISBN, nullable = false)
        val isbn: String,

        @Column(name = LANGUAGE, nullable = false)
        val language: String,

        @Column(name = PAGES, nullable = false)
        val pages: Int,

        @Column(name = PUBLISHER, nullable = false)
        val publisher: String,

        @Column(name = PUBLICATION_DATE, nullable = false)
        val publicationDate: LocalDate,

        @Column(name = STATUS, nullable = false)
        val status: BookStatus?,

        @Version
        val version: Int?
) : Serializable

internal fun BookEntity.toDomain() =
        Book(
                id,
                title,
                author,
                description,
                isbn,
                language,
                pages,
                publisher,
                publicationDate,
                status,
                version
        )

internal fun Book.toJpaEntity() =
        BookEntity(
                id,
                title,
                author,
                description,
                isbn,
                language,
                pages,
                publisher,
                publicationDate,
                status,
                version
        )

internal fun toBookPage(page: Page<BookEntity>): BookPage {
        return BookPage(
                page.content.map { it.toDomain() },
                page.totalPages,
                page.totalElements
        )
}
