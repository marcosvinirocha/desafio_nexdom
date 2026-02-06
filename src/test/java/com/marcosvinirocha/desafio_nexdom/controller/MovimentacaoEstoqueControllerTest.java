package com.marcosvinirocha.desafio_nexdom.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import com.marcosvinirocha.desafio_nexdom.dto.MovimentoEstoqueRequestDTO;
import com.marcosvinirocha.desafio_nexdom.exception.EstoqueInsuficienteException;
import com.marcosvinirocha.desafio_nexdom.service.MovimentacaoEstoqueService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovimentacaoEstoqueController.class)
public class MovimentacaoEstoqueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MovimentacaoEstoqueService service;

    @Test
    void deveMovimentarEstoque() throws Exception {
        mockMvc.perform(post("/movimentacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                              "produtoId": 1,
                              "tipoMovimentacao": "ENTRADA",
                              "quantidade": 5
                            }
                        """))
                .andExpect(status().isOk());
    }

    @Test
    void deveRetornarErroQuandoEstoqueInsuficiente() throws Exception {
        doThrow(new EstoqueInsuficienteException("Sem estoque"))
                .when(service).movimentar(any(MovimentoEstoqueRequestDTO.class));

        mockMvc.perform(post("/movimentacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                              "produtoId": 1,
                              "tipoMovimentacao": "SAIDA",
                              "quantidade": 100
                            }
                        """))
                .andExpect(status().isBadRequest());
    }
}
