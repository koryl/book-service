package koryl.library.books.infrastructure.config

import koryl.library.books.domain.BookRepository
import koryl.library.books.domain.BookService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ContextConfig {

    @Bean
    fun bookService(bookRepository: BookRepository) = BookService(bookRepository)
}
