package med.voll.api.medico;

import jakarta.persistence.*;
import med.voll.api.pacientes.Paciente;

import java.time.LocalDateTime;

@Entity(name="Consulta")
@Table(name="consultas")

public class Consulta {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Paciente paciente;
    private LocalDateTime data;






    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
