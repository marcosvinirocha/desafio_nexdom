package com.marcosvinirocha.desafio_nexdom.dto;

import java.math.BigDecimal;

import com.marcosvinirocha.desafio_nexdom.entity.enums.TipoProduto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record produtoDTO(
                @NotBlank(message = "Código é obrigatório") String codigo,
                @NotNull(message = "Tipo é obrigatório") TipoProduto tipo,
                @NotNull(message = "Valor do fornecedor é obrigatório") BigDecimal valorFornecedor,
                @NotNull(message = "Quantidade em estoque é obrigatória") Integer quantidadeEstoque) {

}
