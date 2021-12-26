package netology.cloud.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DiplomaException extends RuntimeException{
    public DiplomaException(String message) {
        super(message);
    }
}
