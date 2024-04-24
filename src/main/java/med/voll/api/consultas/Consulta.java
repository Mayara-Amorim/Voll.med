package med.voll.api.consultas;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import med.voll.api.medico.Medico;
import med.voll.api.pacientes.Paciente;

import java.time.LocalDateTime;


@Entity(name="Consulta")
@Table(name="consultas")
@EqualsAndHashCode(of = "id")

public class Consulta {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;
    private LocalDateTime data;
    @Column(name = "motivo_cancelamento")
    @Enumerated(EnumType.STRING)
    private Motivo motivo;
    public Consulta() {
    }
    public Consulta(Long id, Medico medico, Paciente paciente, LocalDateTime data, Motivo motivo) {
        this.id = id;
        this.medico = medico;
        this.paciente = paciente;
        this.data = data;
        this.motivo = motivo;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Motivo getMotivo() {
        return motivo;
    }
    public void cancelar(Motivo motivo) {
        this.motivo = motivo;
    }
}
