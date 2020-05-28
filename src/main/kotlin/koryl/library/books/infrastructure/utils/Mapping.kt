package koryl.library.books.infrastructure.utils

import com.fasterxml.jackson.databind.ObjectMapper
import koryl.library.books.application.presentation.request.SaveBookRequest
import koryl.library.books.domain.Book
import koryl.library.books.domain.BookStatus
import koryl.library.books.infrastructure.exception.GeneralException
import org.slf4j.LoggerFactory.getLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

interface Mapping

inline fun <reified T : Mapping, R : Any> T.mapping(obj: Any, type: KClass<R>): R = Mapper.convertValue(obj, type)

@Component
class Mapper {
    companion object {
        private val logger = getLogger(Mapper::class.java)
        private lateinit var objectMapper: ObjectMapper

        fun <R : Any> convertValue(obj: Any, type: KClass<R>): R {
            try {
                return objectMapper.convertValue(obj, type.java)

            } catch (ex: Exception) {
                logger.error("Cannot convert value!", ex)
                throw GeneralException("Cannot convert value!")
            }
        }
    }

    @Autowired
    protected fun setObjectMapper(objectMapper: ObjectMapper) {
        Mapper.objectMapper = objectMapper
    }
}

fun fromSaveRequestToBook(request: SaveBookRequest): Book {
    return Book(null,
            request.title,
            request.author,
            request.description,
            request.isbn,
            request.language,
            request.pages,
            request.publisher,
            request.publicationDate,
            BookStatus.AVAILABLE,
            null
    )
}
