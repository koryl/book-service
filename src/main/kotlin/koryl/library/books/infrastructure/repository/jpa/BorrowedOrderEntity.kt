package koryl.library.books.infrastructure.repository.jpa

import koryl.library.books.domain.BorrowedOrder
import koryl.library.books.domain.BorrowedOrderStatus
import org.hibernate.search.annotations.Indexed
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Indexed
@Table(name = ORDERS)
data class BorrowedOrderEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        val id: Long?,

        @Column(name = ORDER_GUID, nullable = false, unique = true)
        val guid: String,

        @OneToOne
        @JoinColumn(name = BOOK_ID, nullable = false)
        val book: BookEntity,

        @Column(name = USER_ID, nullable = false)
        val userId: String,

        @Column(name = BORROWING_DATE, nullable = false)
        val borrowingDate: LocalDateTime,

        @Column(name = ESTIMATED_RETURN_DATE, nullable = false)
        val estimatedReturnDate: LocalDateTime,

        @Column(name = ACTUAL_RETURN_DATE, nullable = true)
        val actualReturnDate: LocalDateTime?,

        @Column(name = ORDER_STATUS, nullable = false)
        val status: BorrowedOrderStatus

) : Serializable


internal fun BorrowedOrder.toJpaEntity() =
        BorrowedOrderEntity(
                id,
                guid,
                book.toJpaEntity(),
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
                book.toDomain(),
                userId,
                borrowingDate,
                estimatedReturnDate,
                actualReturnDate,
                status
        )
