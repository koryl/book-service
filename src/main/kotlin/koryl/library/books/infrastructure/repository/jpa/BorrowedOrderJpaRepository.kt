package koryl.library.books.infrastructure.repository.jpa

import org.springframework.data.jpa.repository.JpaRepository

interface BorrowedOrderJpaRepository : JpaRepository<BorrowedOrderEntity, Long> {

    fun findByUserIdAndGuid(userId: String, guid: String): BorrowedOrderEntity
}
