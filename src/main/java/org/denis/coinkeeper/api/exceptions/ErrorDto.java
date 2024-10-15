package org.denis.coinkeeper.api.exceptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
@AllArgsConstructor
@Data
public class ErrorDto {
    private List<String> errors;
}