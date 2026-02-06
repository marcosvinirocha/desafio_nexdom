package com.marcosvinirocha.desafio_nexdom.dto;

public record ErrorResponse(
        String code,
        String message,
        String details) {
    public static final String ESTOQUE_INSUFICIENTE = "ESTOQUE_INSUFICIENTE";
    public static final String PRODUTO_NAO_ENCONTRADO = "PRODUTO_NAO_ENCONTRADO";
    // ... outros códigos

    public ErrorResponse(String message) {
        this(ESTOQUE_INSUFICIENTE, message, null);
    }
}
