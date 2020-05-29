package koryl.library.books.infrastructure.config

import koryl.library.books.domain.BookRepository
import koryl.library.books.domain.BookService
import koryl.library.books.domain.DomainBookService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("koryl.library.books.infrastructure.repository")
class ContextConfig {

    @Bean
    fun bookService(bookRepository: BookRepository): BookService = DomainBookService(bookRepository)
}
