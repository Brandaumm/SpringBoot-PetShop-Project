package org.example.springbootpetshopproject.controller;


import lombok.AllArgsConstructor;
import org.example.springbootpetshopproject.service.EnderecoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/enderecos/")
public class EnderecoController {
    EnderecoService service;

}
