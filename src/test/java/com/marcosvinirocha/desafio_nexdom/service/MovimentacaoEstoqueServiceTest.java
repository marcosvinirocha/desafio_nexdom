package com.marcosvinirocha.desafio_nexdom.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.marcosvinirocha.desafio_nexdom.dto.MovimentoEstoqueRequestDTO;
import com.marcosvinirocha.desafio_nexdom.entity.MovimentoEstoque;
import com.marcosvinirocha.desafio_nexdom.entity.Produto;
import com.marcosvinirocha.desafio_nexdom.entity.enums.TipoMovimentacao;
import com.marcosvinirocha.desafio_nexdom.exception.EstoqueInsuficienteException;
import com.marcosvinirocha.desafio_nexdom.repository.MovimentacaoRepository;
import com.marcosvinirocha.desafio_nexdom.repository.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
public class MovimentacaoEstoqueServiceTest {

        @Mock
        private ProdutoRepository produtoRepository;

        @Mock
        private MovimentacaoRepository movimentacaoRepository;

        @InjectMocks
        private MovimentacaoEstoqueService movimentacaoEstoqueService;

        @Captor
        private ArgumentCaptor<Produto> produtoCaptor;
        @Captor
        private ArgumentCaptor<MovimentoEstoque> movimentoCaptor;

        private Produto produtoExistente;

        @BeforeEach
        void setUp() {
                produtoExistente = new Produto();
                produtoExistente.setId(1L);
                produtoExistente.setDescricao("Mouse Gamer");
                produtoExistente.setQuantidadeEstoque(10);
        }

        @Test
        @DisplayName("Deve registrar entrada de estoque e salvar movimento corretamente")
        void deveRegistrarEntradaDeEstoqueComSucesso() {
                // Arrange
                Long produtoId = 1L;
                int quantidadeEntrada = 15;
                int valorVenda = 89; // valor em centavos ou como inteiro, conforme seu DTO

                MovimentoEstoqueRequestDTO dto = new MovimentoEstoqueRequestDTO(
                                produtoId,
                                TipoMovimentacao.ENTRADA,
                                BigDecimal.valueOf(valorVenda),
                                LocalDateTime.now().minusHours(1), // data qualquer válida
                                quantidadeEntrada);

                when(produtoRepository.findById(produtoId))
                                .thenReturn(Optional.of(produtoExistente));

                // Act
                assertDoesNotThrow(() -> movimentacaoEstoqueService.movimentar(dto));

                // Assert - Produto
                verify(produtoRepository).findById(produtoId);
                verify(produtoRepository).save(produtoCaptor.capture());

                Produto produtoSalvo = produtoCaptor.getValue();
                assertThat(produtoSalvo.getQuantidadeEstoque())
                                .isEqualTo(10 + 15) // 25
                                .describedAs("Quantidade de estoque deve ser aumentada");

                // Assert - MovimentoEstoque
                verify(movimentacaoRepository).save(movimentoCaptor.capture());

                MovimentoEstoque movimentoSalvo = movimentoCaptor.getValue();

                assertThat(movimentoSalvo.getProdutoId()).isEqualTo(produtoId);
                assertThat(movimentoSalvo.getTipoMovimentacao()).isEqualTo(TipoMovimentacao.ENTRADA);
                assertThat(movimentoSalvo.getQuantidade()).isEqualTo(quantidadeEntrada);
                assertThat(movimentoSalvo.getValorVenda()).isEqualTo(BigDecimal.valueOf(valorVenda));

                // Data deve ser próxima do momento atual (não testamos exatidão de
                // milissegundos)
                assertThat(movimentoSalvo.getDataMovimentacao())
                                .isAfter(LocalDateTime.now().minusSeconds(5))
                                .isBefore(LocalDateTime.now().plusSeconds(5));

                // Verificações extras importantes
                verifyNoMoreInteractions(produtoRepository, movimentacaoRepository);
        }

        @Test
        @DisplayName("Deve registrar saída de estoque e salvar movimento corretamente")
        void deveRegistrarSaidaDeEstoqueComSucesso() {
                // Arrange
                Long produtoId = 1L;
                int quantidadeSaida = 5;
                int valorVenda = 89;

                MovimentoEstoqueRequestDTO dto = new MovimentoEstoqueRequestDTO(
                                produtoId,
                                TipoMovimentacao.SAIDA,
                                BigDecimal.valueOf(valorVenda),
                                LocalDateTime.now().minusHours(1),
                                quantidadeSaida);

                when(produtoRepository.findById(produtoId))
                                .thenReturn(Optional.of(produtoExistente));

                // Act
                assertDoesNotThrow(() -> movimentacaoEstoqueService.movimentar(dto));

                // Assert - Produto
                verify(produtoRepository).findById(produtoId);
                verify(produtoRepository).save(produtoCaptor.capture());

                Produto produtoSalvo = produtoCaptor.getValue();
                assertThat(produtoSalvo.getQuantidadeEstoque())
                                .isEqualTo(10 - 5) // 5
                                .describedAs("Quantidade de estoque deve ser diminuída");

                // Assert - MovimentoEstoque
                verify(movimentacaoRepository).save(movimentoCaptor.capture());

                MovimentoEstoque movimentoSalvo = movimentoCaptor.getValue();

                assertThat(movimentoSalvo.getProdutoId()).isEqualTo(produtoId);
                assertThat(movimentoSalvo.getTipoMovimentacao()).isEqualTo(TipoMovimentacao.SAIDA);
                assertThat(movimentoSalvo.getQuantidade()).isEqualTo(quantidadeSaida);
                assertThat(movimentoSalvo.getValorVenda()).isEqualTo(BigDecimal.valueOf(valorVenda));

                assertThat(movimentoSalvo.getDataMovimentacao())
                                .isAfter(LocalDateTime.now().minusSeconds(5))
                                .isBefore(LocalDateTime.now().plusSeconds(5));

                verifyNoMoreInteractions(produtoRepository, movimentacaoRepository);
        }

        @Test
        @DisplayName("Deve lançar exceção quando produto não existe")
        void deveLancarExcecaoQuandoProdutoNaoExiste() {
                // Arrange
                MovimentoEstoqueRequestDTO dto = new MovimentoEstoqueRequestDTO(
                                999L, // ID que não existe
                                TipoMovimentacao.SAIDA, // ou ENTRADA, tanto faz nesse caso
                                BigDecimal.valueOf(150),
                                LocalDateTime.now(),
                                10);

                when(produtoRepository.findById(999L))
                                .thenReturn(Optional.empty());

                // Act & Assert
                assertThrows(RuntimeException.class,
                                () -> movimentacaoEstoqueService.movimentar(dto));

                // Ou melhor, verificando a mensagem também:
                RuntimeException ex = assertThrows(RuntimeException.class,
                                () -> movimentacaoEstoqueService.movimentar(dto));

                assertEquals("Produto não encontrado", ex.getMessage());
        }

        @Test
        @DisplayName("Deve lançar exceção quando estoque é insuficiente para saída")
        void deveLancarExcecaoQuandoEstoqueInsuficiente() {
                // Arrange
                Long produtoId = 1L;
                int quantidadeSaida = 20; // mais do que os 10 disponíveis

                MovimentoEstoqueRequestDTO dto = new MovimentoEstoqueRequestDTO(
                                produtoId,
                                TipoMovimentacao.SAIDA,
                                BigDecimal.valueOf(150),
                                LocalDateTime.now(),
                                quantidadeSaida);

                when(produtoRepository.findById(produtoId))
                                .thenReturn(Optional.of(produtoExistente)); // estoque inicial = 10

                // Act & Assert
                EstoqueInsuficienteException ex = assertThrows(EstoqueInsuficienteException.class,
                                () -> movimentacaoEstoqueService.movimentar(dto));

                assertEquals("Estoque insuficiente", ex.getMessage());

                // Verificações: produto não pode ter sido salvo com estoque negativo
                verify(produtoRepository).findById(produtoId);
                verify(produtoRepository, never()).save(any()); // nunca deve salvar se der erro
                verifyNoMoreInteractions(produtoRepository, movimentacaoRepository);
        }

}
