package netology.cloud.config;


import lombok.AllArgsConstructor;
import netology.cloud.exceptions.AuthenticationCloudException;
import netology.cloud.exceptions.DiplomaException;
import netology.cloud.exceptions.UploadFileException;
import netology.cloud.model.Error;
import netology.cloud.repository.ErrorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@AllArgsConstructor
public class ExceptionConfig {
    private final ErrorRepository errorMsgRepository;

    @ExceptionHandler({UploadFileException.class, DiplomaException.class})
    public ResponseEntity<Error> handleUploadException(Exception exception) {
        return ResponseEntity.status(400)
                .body(errorMsgRepository.save(Error.builder().massage(exception.getMessage()).build()));
    }

    @ExceptionHandler({AuthenticationCloudException.class})
    public ResponseEntity<Error> handleConverterErrors(Exception exception) {
        return ResponseEntity.status(401)
                .body(errorMsgRepository.save(Error.builder().massage(exception.getMessage()).build()));
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Error> handleRTM(Exception exception) {
        return ResponseEntity.status(500)
                .body(errorMsgRepository.save(Error.builder().massage(exception.getMessage()).build()));
    }

}
