package com.marcosvinirocha.desafio_nexdom.dto;

import com.marcosvinirocha.desafio_nexdom.entity.enums.TipoProduto;

public record MovimentoEstoqueResponseDTO(
                Long produtoId,
                String codigo,
                String descricao,
                TipoProduto tipo,
                Long quantidadeTotalSaida,
                Integer quantidadeDisponivel

) {

}