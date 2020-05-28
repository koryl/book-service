package koryl.library.books.infrastructure.repository.jpa

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BookJpaRepository : JpaRepository<BookEntity, Long> {

    @Query("""
        select b from BookEntity b 
        where (:title is null or b.title = :title) 
        and (:author is null or b.author = :author)
        and (:isbn is null or b.isbn = :isbn)
        """)
    fun findAllByCriteria(title: String?, author: String?, isbn: String?, page: Pageable): Page<BookEntity>

}
