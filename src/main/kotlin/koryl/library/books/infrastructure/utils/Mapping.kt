package koryl.library.books.infrastructure.utils

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

interface Mapping

val objectMapper: ObjectMapper = Mapper.provideObjectMapper()

inline fun <reified T : Mapping, R> T.mapping(obj: Any, type: Class<R>): R = objectMapper.convertValue(obj, type)

@Component
class Mapper {
    companion object {
        private lateinit var objectMapper: ObjectMapper

        fun provideObjectMapper() = this.objectMapper
    }

    @Autowired
    protected fun setObjectMapper(objectMapper: ObjectMapper) {
        Mapper.objectMapper = objectMapper
    }
}
