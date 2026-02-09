package com.marcosvinirocha.desafio_nexdom.controller;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcosvinirocha.desafio_nexdom.dto.ErrorResponse;

import com.marcosvinirocha.desafio_nexdom.dto.MovimentoEstoqueRequestDTO;
import com.marcosvinirocha.desafio_nexdom.exception.EstoqueInsuficienteException;
import com.marcosvinirocha.desafio_nexdom.service.MovimentacaoEstoqueService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/movimentacoes")
public class MovimentacaoEstoqueController {

    private final MovimentacaoEstoqueService movimentacaoEstoqueService;

    public MovimentacaoEstoqueController(MovimentacaoEstoqueService movimentacaoEstoqueService) {
        this.movimentacaoEstoqueService = movimentacaoEstoqueService;
    }

    @PostMapping
    public ResponseEntity<Object> movimentar(@Valid @RequestBody MovimentoEstoqueRequestDTO dto) {
        try {
            movimentacaoEstoqueService.movimentar(dto);
            return ResponseEntity.ok().build();
        } catch (EstoqueInsuficienteException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Estoque insuficiente para realizar a operação"));
        }
    }
}
