package com.marcosvinirocha.desafio_nexdom.dto;

import java.math.BigDecimal;

import com.marcosvinirocha.desafio_nexdom.entity.enums.TipoProduto;

public record produtoDTO(
        Long id,
        String codigo,
        String descricao,
        TipoProduto tipo,
        BigDecimal valorFornecedor,
        Integer quantidadeEstoque) {

}
