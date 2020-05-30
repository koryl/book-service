package koryl.library.books.infrastructure.config

import koryl.library.books.domain.BookRepository
import koryl.library.books.domain.BookService
import koryl.library.books.domain.DomainBookService
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("koryl.library.books.infrastructure.repository")
class ContextConfig {

    @Bean
    fun bookService(bookRepository: BookRepository): BookService = DomainBookService(bookRepository)

    @Bean
    @ConditionalOnExpression("\${cors.enabled}")
    fun corsFilter(): CorsFilter? {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.applyPermitDefaultValues()
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }
}
