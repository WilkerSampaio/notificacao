package com.wilker.notificacao.controller;

import com.wilker.notificacao.infrastructure.annotations.ApiNotificacaoResponses;
import com.wilker.notificacao.infrastructure.dto.TarefasDTORequest;
import com.wilker.notificacao.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
@Tag(name = "Notificação", description = "Envio de notificação para usuário")
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    @Operation(summary = "Envia E-mail", description = "Notifica Usuário sobre Tarefa Pendente por E-mail")
    @ApiNotificacaoResponses
    public ResponseEntity<String> enviarEmail(@RequestBody TarefasDTORequest tarefaDTORequest){
        emailService.enviarEmail(tarefaDTORequest);
        String mensagem = "E-mail enviado com sucesso para " + tarefaDTORequest.getEmailUsuario();
        return ResponseEntity.ok(mensagem);
    }

}
