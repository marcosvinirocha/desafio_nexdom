package com.marcosvinirocha.desafio_nexdom.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import com.marcosvinirocha.desafio_nexdom.entity.Produto;
import com.marcosvinirocha.desafio_nexdom.service.ProdutoService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ProdutoController.class)
public class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProdutoService produtoService;

    @Test
    void deveCriarProduto() throws Exception {
        Produto produto = new Produto();
        produto.setId(1L);

        when(produtoService.save(any())).thenReturn(produto);

        mockMvc.perform(post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                              "descricao": "Mouse",
                              "quantidadeEstoque": 10
                            }
                        """))
                .andExpect(status().isOk());
    }

    @Test
    void deveRetornarProdutoPorId() throws Exception {
        Produto produto = new Produto();
        produto.setId(1L);

        when(produtoService.findById(1L)).thenReturn(produto);

        mockMvc.perform(get("/produtos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
}
