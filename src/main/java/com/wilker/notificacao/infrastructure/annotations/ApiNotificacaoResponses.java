package com.wilker.notificacao.infrastructure.annotations;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Requisição processada com sucesso.",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = String.class)
                )
        ),
        @ApiResponse(responseCode = "400", description = "Requisição inválida."),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado."),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
})
public @interface ApiNotificacaoResponses {
}
