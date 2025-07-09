package com.exemplo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private Boolean concluida;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
