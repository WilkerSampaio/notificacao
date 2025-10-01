package com.wilker.notificacao.service;

import com.wilker.notificacao.infrastructure.dto.out.TarefasDTORequest;
import com.wilker.notificacao.infrastructure.exception.EmailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class EmailService {

    // Injeção do componente responsável por enviar e-mails
    private final JavaMailSender javaMailSender;

    // Injeção do TemplateEngine (Thymeleaf) para processar templates HTML
    private final TemplateEngine templateEngine;

    // Pega do application.properties o e-mail do remetente
    @Value("${envio.email.remetente}")
    public String remetente;

    // Pega do application.properties o nome amigável do remetente
    @Value("${envio.email.nomeRemetente}")
    private String nomeRemetente;

    // Metodo que envia o e-mail de notificação baseado nos dados da tarefa
    public void enviarEmail(TarefasDTORequest tarefasDTORequest){
        try{
            // Cria um objeto de mensagem MIME (suporta HTML, anexos, etc.)
            MimeMessage mensagem = javaMailSender.createMimeMessage();

            // Helper para facilitar a construção do e-mail
            // true → habilita multipart (pode ter HTML + anexos)
            // UTF-8 → garante acentuação correta
            MimeMessageHelper mimeMessageHelper =
                    new MimeMessageHelper(mensagem, true, StandardCharsets.UTF_8.name());

            // Define remetente (e-mail + nome amigável)
            mimeMessageHelper.setFrom(new InternetAddress(remetente, nomeRemetente));

            // Define destinatário (pega do DTO)
            mimeMessageHelper.setTo(InternetAddress.parse(tarefasDTORequest.getEmailUsuario()));

            // Define o assunto do e-mail
            mimeMessageHelper.setSubject("Notificação de Tarefa");

            // Cria um contexto do Thymeleaf para passar variáveis para o template

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                    .withLocale(new Locale("pt", "BR"));

            Context context = new Context();
            context.setVariable("nomeTarefa", tarefasDTORequest.getNomeTarefa());
            context.setVariable("dataEvento", tarefasDTORequest.getDataEvento().format(formatter));
            context.setVariable("descricao", tarefasDTORequest.getDescricao());

            // Processa o template "notificacao.html" substituindo as variáveis acima
            String template = templateEngine.process("notificacao", context);

            // Define o corpo do e-mail como HTML (segundo parâmetro "true")
            mimeMessageHelper.setText(template, true);

            // Envia a mensagem pelo servidor SMTP configurado
            javaMailSender.send(mensagem);

        } catch (MessagingException | UnsupportedEncodingException e) {
            // Caso ocorra algum erro ao montar ou enviar, lança exceção customizada
            throw new EmailException("Erro ao enviar o email" + e.getCause());
        }
    }
}


