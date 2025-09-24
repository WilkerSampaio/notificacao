package com.wilker.notificacao.controller;

import com.wilker.notificacao.infrastructure.dto.TarefasDTO;
import com.wilker.notificacao.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<Void> enviarEmail(@RequestBody TarefasDTO tarefasDTO){
        emailService.enviarEmail(tarefasDTO);
        return ResponseEntity.ok().build();
    }
}
