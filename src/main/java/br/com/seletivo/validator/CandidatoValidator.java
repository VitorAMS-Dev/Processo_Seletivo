package br.com.seletivo.validator;

import br.com.seletivo.model.Candidato;
import br.com.seletivo.model.ResultadoValidacao;
import br.com.seletivo.model.Vaga;
import br.com.seletivo.util.ValidadorCPF;
import br.com.seletivo.util.ValidadorEmail;

public class CandidatoValidator {

    private static final int IDADE_MINIMA = 18;
    private static final double NOTA_MINIMA_APROVACAO = 6.0;

    /**
     * Valida os dados cadastrais do candidato (CPF, e-mail, idade mínima).
     */
    public ResultadoValidacao validarDadosCadastrais(Candidato candidato) {
        ResultadoValidacao resultado = new ResultadoValidacao();
        int pontos = 0;
        int totalCriterios = 3;

        // 1. CPF
        if (ValidadorCPF.validar(candidato.getCpf())) {
            resultado.adicionarAprovado("CPF válido: " + ValidadorCPF.formatar(candidato.getCpf()));
            pontos++;
        } else {
            resultado.adicionarReprovado("CPF inválido ou mal formatado: " + candidato.getCpf());
        }

        // 2. E-mail
        if (ValidadorEmail.validar(candidato.getEmail())) {
            resultado.adicionarAprovado("E-mail válido: " + candidato.getEmail());
            pontos++;
        } else {
            resultado.adicionarReprovado("E-mail inválido: " + candidato.getEmail());
        }

        // 3. Idade mínima
        int idade = candidato.getIdade();
        if (idade >= IDADE_MINIMA) {
            resultado.adicionarAprovado("Idade dentro do permitido: " + idade + " anos");
            pontos++;
        } else {
            resultado.adicionarReprovado("Candidato menor de " + IDADE_MINIMA + " anos: " + idade + " anos");
        }

        double pontuacao = ((double) pontos / totalCriterios) * 10;
        resultado.setPontuacaoFinal(pontuacao);
        resultado.setAprovado(pontos == totalCriterios);

        if (resultado.isAprovado()) {
            resultado.setParecer("Dados cadastrais VÁLIDOS. Candidato apto para prosseguir.");
        } else {
            resultado.setParecer("Dados cadastrais INVÁLIDOS. Candidato reprovado na triagem inicial.");
        }

        return resultado;
    }

    /**
     * Valida a aderência do candidato aos requisitos da vaga.
     */
    public ResultadoValidacao validarAderenciaVaga(Candidato candidato, Vaga vaga) {
        ResultadoValidacao resultado = new ResultadoValidacao();
        int pontos = 0;
        int totalCriterios = 0;

        // 1. Experiência mínima
        totalCriterios++;
        int expMinima = vaga.getNivel().getAnosExperienciaMinimos();
        if (candidato.getAnosExperiencia() >= expMinima) {
            resultado.adicionarAprovado(String.format(
                    "Experiência suficiente: %d anos (mínimo: %d anos para %s)",
                    candidato.getAnosExperiencia(), expMinima, vaga.getNivel().getDescricao()));
            pontos++;
        } else {
            resultado.adicionarReprovado(String.format(
                    "Experiência insuficiente: %d anos (mínimo: %d anos para %s)",
                    candidato.getAnosExperiencia(), expMinima, vaga.getNivel().getDescricao()));
        }

        // 2. Pretensão salarial
        totalCriterios++;
        double pretensao = candidato.getPretensaoSalarial();
        if (pretensao >= vaga.getSalarioMinimo() && pretensao <= vaga.getSalarioMaximo()) {
            resultado.adicionarAprovado(String.format(
                    "Pretensão salarial dentro da faixa: R$ %.2f (faixa: R$ %.2f – R$ %.2f)",
                    pretensao, vaga.getSalarioMinimo(), vaga.getSalarioMaximo()));
            pontos++;
        } else if (pretensao < vaga.getSalarioMinimo()) {
            resultado.adicionarAlerta(String.format(
                    "Pretensão abaixo da faixa (R$ %.2f). Verificar expectativas do candidato.", pretensao));
            pontos++; // Alerta, mas não reprova
        } else {
            resultado.adicionarReprovado(String.format(
                    "Pretensão acima do teto da vaga: R$ %.2f (máximo: R$ %.2f)",
                    pretensao, vaga.getSalarioMaximo()));
        }

        // 3. Formação superior (se exigida)
        if (vaga.isExigeFormacaoSuperior()) {
            totalCriterios++;
            if (candidato.isPossuiFormacaoSuperior()) {
                resultado.adicionarAprovado("Formação superior confirmada: " + candidato.getNivelFormacao());
                pontos++;
            } else {
                resultado.adicionarReprovado("Vaga exige formação superior — candidato não possui.");
            }
        }

        // 4. Habilidades requeridas
        if (!vaga.getHabilidadesRequeridas().isEmpty()) {
            totalCriterios++;
            long habilidadesAtendidas = vaga.getHabilidadesRequeridas().stream()
                    .filter(req -> candidato.getHabilidades().stream()
                            .anyMatch(h -> h.equalsIgnoreCase(req)))
                    .count();
            double percentualHabilidades = (double) habilidadesAtendidas / vaga.getHabilidadesRequeridas().size() * 100;

            if (percentualHabilidades >= 70) {
                resultado.adicionarAprovado(String.format(
                        "Habilidades: %.0f%% das requeridas atendidas (%d/%d)",
                        percentualHabilidades, habilidadesAtendidas, vaga.getHabilidadesRequeridas().size()));
                pontos++;
            } else if (percentualHabilidades >= 50) {
                resultado.adicionarAlerta(String.format(
                        "Habilidades parcialmente atendidas: %.0f%% (%d/%d). Treinamento pode ser necessário.",
                        percentualHabilidades, habilidadesAtendidas, vaga.getHabilidadesRequeridas().size()));
                pontos++; // Alerta, mas não reprova diretamente
            } else {
                resultado.adicionarReprovado(String.format(
                        "Habilidades insuficientes: apenas %.0f%% das requeridas atendidas (%d/%d)",
                        percentualHabilidades, habilidadesAtendidas, vaga.getHabilidadesRequeridas().size()));
            }
        }

        double pontuacao = totalCriterios > 0 ? ((double) pontos / totalCriterios) * 10 : 0;
        resultado.setPontuacaoFinal(pontuacao);
        resultado.setAprovado(resultado.getCriteriosReprovados().isEmpty());

        if (resultado.isAprovado()) {
            resultado.setParecer("Candidato APTO para a vaga de " + vaga.getTitulo() + ".");
        } else {
            resultado.setParecer("Candidato INAPTO para a vaga de " + vaga.getTitulo()
                    + ". Verifique os critérios reprovados.");
        }

        return resultado;
    }

    /**
     * Valida as notas das etapas de avaliação (técnica e comportamental).
     */
    public ResultadoValidacao validarAvaliacoes(Candidato candidato) {
        ResultadoValidacao resultado = new ResultadoValidacao();
        int pontos = 0;
        int totalCriterios = 2;

        // Nota técnica
        if (candidato.getNotaTecnica() >= NOTA_MINIMA_APROVACAO) {
            resultado.adicionarAprovado(String.format(
                    "Avaliação técnica aprovada: %.1f (mínimo: %.1f)",
                    candidato.getNotaTecnica(), NOTA_MINIMA_APROVACAO));
            pontos++;
        } else {
            resultado.adicionarReprovado(String.format(
                    "Avaliação técnica abaixo do mínimo: %.1f (mínimo: %.1f)",
                    candidato.getNotaTecnica(), NOTA_MINIMA_APROVACAO));
        }

        // Nota comportamental
        if (candidato.getNotaComportamental() >= NOTA_MINIMA_APROVACAO) {
            resultado.adicionarAprovado(String.format(
                    "Avaliação comportamental aprovada: %.1f (mínimo: %.1f)",
                    candidato.getNotaComportamental(), NOTA_MINIMA_APROVACAO));
            pontos++;
        } else {
            resultado.adicionarReprovado(String.format(
                    "Avaliação comportamental abaixo do mínimo: %.1f (mínimo: %.1f)",
                    candidato.getNotaComportamental(), NOTA_MINIMA_APROVACAO));
        }

        double media = candidato.getMediaNotas();

        if (media >= 9.0) {
            resultado.adicionarAlerta("Candidato DESTAQUE: média superior a 9.0 — considere para aceleração de carreira.");
        }

        resultado.setPontuacaoFinal(media);
        resultado.setAprovado(pontos == totalCriterios);

        if (resultado.isAprovado()) {
            resultado.setParecer(String.format(
                    "Avaliações APROVADAS. Média final: %.1f — candidato apto para a fase seguinte.", media));
        } else {
            resultado.setParecer(String.format(
                    "Avaliações REPROVADAS. Média final: %.1f — candidato não atingiu a nota mínima.", media));
        }

        return resultado;
    }
}
