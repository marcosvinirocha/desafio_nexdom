package com.marcosvinirocha.desafio_nexdom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.marcosvinirocha.desafio_nexdom.dto.LucroProdutoResponseDTO;
import com.marcosvinirocha.desafio_nexdom.dto.MovimentoEstoqueResponseDTO;
import com.marcosvinirocha.desafio_nexdom.entity.MovimentoEstoque;

public interface MovimentacaoRepository extends JpaRepository<MovimentoEstoque, Long> {

    @Query("""
                SELECT new com.marcosvinirocha.desafio_nexdom.dto.MovimentoEstoqueResponseDTO(
                    p.tipoProduto,
                    COALESCE(SUM(
                        CASE
                            WHEN m.tipoMovimentacao =
                                 com.marcosvinirocha.desafio_nexdom.entity.enums.TipoMovimentacao.SAIDA
                            THEN m.quantidade
                            ELSE 0
                        END
                    ), 0),
                    SUM(p.quantidadeEstoque)
                )
                FROM Produto p
                LEFT JOIN MovimentoEstoque m
                    ON m.produto = p
                GROUP BY p.tipoProduto
            """)
    List<MovimentoEstoqueResponseDTO> findResumoPorTipo();

    @Query("""
                SELECT new com.marcosvinirocha.desafio_nexdom.dto.LucroProdutoResponseDTO(
                    COALESCE(SUM(
                        CASE
                            WHEN m.tipoMovimentacao =
                                 com.marcosvinirocha.desafio_nexdom.entity.enums.TipoMovimentacao.SAIDA
                            THEN m.quantidade
                            ELSE 0
                        END
                    ), 0),
                    COALESCE(SUM(
                        CASE
                            WHEN m.tipoMovimentacao =
                                 com.marcosvinirocha.desafio_nexdom.entity.enums.TipoMovimentacao.SAIDA
                            THEN m.quantidade * m.valorUnitario
                            ELSE 0
                        END
                    ), 0)
                )
                FROM MovimentoEstoque m
            """)
    List<LucroProdutoResponseDTO> findLucroPorProduto();

}
