package koryl.library.books.application.support

import koryl.library.books.infrastructure.exception.ExceptionModel
import koryl.library.books.infrastructure.utils.Logging
import koryl.library.books.infrastructure.utils.logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalHttpExceptionHandler : Logging {

    @ExceptionHandler(Exception::class)
    fun handleExceptions(ex: Exception): ResponseEntity<ExceptionModel> {
        logger().warn("Handling error...", ex)
        val model = ExceptionModel("ERROR", ex.message.orEmpty())
        return ResponseEntity(model, HttpStatus.INTERNAL_SERVER_ERROR)
    }

}
