package com.exemplo.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TarefaDTO {
    private Long id;
    private String descricao;
    private Boolean concluida;
    private Long usuarioId;  // Referência apenas ao ID do usuário
}

