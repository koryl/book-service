package koryl.library.books.infrastructure.repository.jpa

import koryl.library.books.domain.Book
import org.hibernate.search.annotations.Field
import org.hibernate.search.annotations.Indexed
import java.io.Serializable
import java.time.LocalDate
import javax.persistence.*


@Entity
@Indexed
@Table(name = BOOK,
        indexes = [
            Index(name = GUID_INDEX, columnList = GUID, unique = true)
        ])
data class BookEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        @Column(name = GUID, nullable = false, unique = true)
        val guid: String,

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

        @Version
        val version: Int
) : Serializable

fun BookEntity.toBook() = Book(guid, title, author, description, isbn, language, pages, publisher, publicationDate)
