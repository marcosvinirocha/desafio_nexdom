package com.marcosvinirocha.desafio_nexdom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.marcosvinirocha.desafio_nexdom.dto.LucroProdutoResponseDTO;
import com.marcosvinirocha.desafio_nexdom.dto.MovimentoEstoqueResponseDTO;
import com.marcosvinirocha.desafio_nexdom.entity.Produto;

public interface ProdutoRespository extends JpaRepository<Produto, Long> {

    @Query("""
                SELECT new com.marcosvinirocha.desafio_nexdom.dto.MovimentoEstoqueResponseDTO(
                    p.id,
                    p.codigo,
                    p.descricao,
                    p.tipo,
                    COALESCE(
                        SUM(
                            CASE
                                WHEN m.tipoMovimentacao =
                                    com.marcosvinirocha.desafio_nexdom.entity.enums.TipoMovimentacao.SAIDA
                                THEN m.quantidade
                                ELSE 0
                            END
                        ),
                        0L
                    ),
                    p.quantidadeEstoque
                )
                FROM Produto p
                LEFT JOIN MovimentoEstoque m ON m.produto = p
                GROUP BY
                    p.id,
                    p.codigo,
                    p.descricao,
                    p.tipo,
                    p.quantidadeEstoque
            """)
    List<MovimentoEstoqueResponseDTO> findResumoPorTipo();

    @Query("""
                SELECT new com.marcosvinirocha.desafio_nexdom.dto.LucroProdutoResponseDTO(
                    p.id,
                    p.codigo,
                    p.descricao,
                    COALESCE(SUM(m.quantidade), 0),
                    COALESCE(SUM(
                        m.quantidade * (m.valorVenda - p.valorFornecedor)
                    ), 0)
                )
                FROM Produto p
                LEFT JOIN MovimentoEstoque m
                    ON m.produto = p
                    AND m.tipoMovimentacao = com.marcosvinirocha.desafio_nexdom.entity.enums.TipoMovimentacao.SAIDA
                GROUP BY
                    p.id,
                    p.codigo,
                    p.descricao
            """)
    List<LucroProdutoResponseDTO> findLucroPorProduto();
}
