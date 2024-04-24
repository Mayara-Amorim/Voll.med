package med.voll.api.consultas.validacoes.cancelamento;

import med.voll.api.consultas.DadosCancelamentoConsulta;

public interface ValidadorCancelamentoDeConsulta {
    void validar(DadosCancelamentoConsulta dados);
}
