package org.denis.coinkeeper.api.exceptions;

import lombok.Builder;

import java.util.List;



@Builder
public record ErrorDto( String error, List<String> errorDetails) {

}