package org.denis.coinkeeper.api.exceptions;

import java.util.List;

public record ErrorDto(List<String> errorDetails) {
}