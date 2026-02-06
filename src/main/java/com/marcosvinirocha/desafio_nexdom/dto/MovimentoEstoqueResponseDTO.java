package com.marcosvinirocha.desafio_nexdom.dto;

import com.marcosvinirocha.desafio_nexdom.entity.enums.TipoMovimentacao;

public record MovimentoEstoqueResponseDTO(
                TipoMovimentacao tipoMovimentacao,
                Long quantidadeTotalSaida,
                Long quantidadeDisponivel

) {

}