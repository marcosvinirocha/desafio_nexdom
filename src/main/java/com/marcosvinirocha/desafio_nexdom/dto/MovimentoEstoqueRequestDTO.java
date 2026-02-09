package com.marcosvinirocha.desafio_nexdom.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.marcosvinirocha.desafio_nexdom.entity.enums.TipoMovimentacao;

import jakarta.validation.constraints.NotNull;

public record MovimentoEstoqueRequestDTO(
                @NotNull(message = "Produto é obrigatório") Long produtoId,
                @NotNull(message = "Tipo de movimentação é obrigatório") TipoMovimentacao tipoMovimentacao,
                BigDecimal valorVenda,
                LocalDateTime dataVenda,
                @NotNull(message = "Quantidade é obrigatória") Integer quantidade) {

}
