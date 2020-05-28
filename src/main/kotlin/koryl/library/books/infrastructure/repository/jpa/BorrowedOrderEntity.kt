package koryl.library.books.infrastructure.repository.jpa

import koryl.library.books.domain.Book
import koryl.library.books.domain.BorrowedOrder
import koryl.library.books.domain.BorrowedOrderStatus
import org.hibernate.search.annotations.Indexed
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Indexed
@Table(name = BOOKS)
data class BorrowedOrderEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,
        val guid: String,
        val book: Book,
        val userId: String,
        val borrowingDate: LocalDateTime,
        val estimatedReturnDate: LocalDateTime,
        val actualReturnDate: LocalDateTime?,
        val status: BorrowedOrderStatus

) : Serializable


internal fun BorrowedOrder.toJpaEntity() =
        BorrowedOrderEntity(
                id,
                guid,
                book,
                userId,
                borrowingDate,
                estimatedReturnDate,
                actualReturnDate,
                status
        )

internal fun BorrowedOrderEntity.toDomain() =
        BorrowedOrder(
                id,
                guid,
                book,
                userId,
                borrowingDate,
                estimatedReturnDate,
                actualReturnDate,
                status
        )
