package com.marcosvinirocha.desafio_nexdom.dto;

import java.math.BigDecimal;

public record LucroProdutoResponseDTO(
                Long produtoId,
                String codigo,
                String descricao,
                Long quantidadeTotalSaida,
                BigDecimal lucroTotal) {

}
