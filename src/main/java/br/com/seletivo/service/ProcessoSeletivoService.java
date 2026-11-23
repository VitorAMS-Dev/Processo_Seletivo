package br.com.seletivo.service;

import br.com.seletivo.enums.StatusCandidato;
import br.com.seletivo.model.Candidato;
import br.com.seletivo.model.ResultadoValidacao;
import br.com.seletivo.model.Vaga;
import br.com.seletivo.validator.CandidatoValidator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProcessoSeletivoService {

    private final List<Candidato> candidatos = new ArrayList<>();
    private final CandidatoValidator validator = new CandidatoValidator();

    // ─── Cadastro ────────────────────────────────────────────────────────────────

    public void cadastrarCandidato(Candidato candidato) {
        candidatos.add(candidato);
        System.out.println("✔ Candidato cadastrado: " + candidato.getNome());
    }

    public Optional<Candidato> buscarPorId(int id) {
        return candidatos.stream().filter(c -> c.getId() == id).findFirst();
    }

    public List<Candidato> listarTodos() {
        return new ArrayList<>(candidatos);
    }

    public List<Candidato> listarPorStatus(StatusCandidato status) {
        return candidatos.stream()
                .filter(c -> c.getStatus() == status)
                .collect(Collectors.toList());
    }

    // ─── Triagem completa ─────────────────────────────────────────────────────────

    public void executarTriagemCompleta(Candidato candidato, Vaga vaga) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  TRIAGEM: " + candidato.getNome().toUpperCase());
        System.out.println("  VAGA:    " + vaga.getTitulo() + " | " + vaga.getNivel().getDescricao());
        System.out.println("=".repeat(60));

        candidato.setStatus(StatusCandidato.EM_ANALISE);

        // Etapa 1 — Dados cadastrais
        ResultadoValidacao dadosCadastrais = validator.validarDadosCadastrais(candidato);
        imprimirResultado("ETAPA 1 — Dados Cadastrais", dadosCadastrais);

        if (!dadosCadastrais.isAprovado()) {
            candidato.setStatus(StatusCandidato.REPROVADO);
            candidato.setObservacoes("Reprovado na validação de dados cadastrais.");
            System.out.println("\n  ⛔ Triagem encerrada — dados cadastrais inválidos.\n");
            return;
        }

        // Etapa 2 — Aderência à vaga
        ResultadoValidacao aderencia = validator.validarAderenciaVaga(candidato, vaga);
        imprimirResultado("ETAPA 2 — Aderência à Vaga", aderencia);

        if (!aderencia.isAprovado()) {
            candidato.setStatus(StatusCandidato.REPROVADO);
            candidato.setObservacoes("Reprovado por não atender aos requisitos da vaga.");
            System.out.println("\n  ⛔ Triagem encerrada — candidato não se adequa à vaga.\n");
            return;
        }

        // Etapa 3 — Notas de avaliação (somente se já atribuídas)
        if (candidato.getNotaTecnica() > 0 || candidato.getNotaComportamental() > 0) {
            ResultadoValidacao avaliacoes = validator.validarAvaliacoes(candidato);
            imprimirResultado("ETAPA 3 — Avaliações", avaliacoes);

            if (avaliacoes.isAprovado()) {
                candidato.setStatus(StatusCandidato.APROVADO);
                candidato.setObservacoes("Aprovado em todas as etapas. Média: " + candidato.getMediaNotas());
                System.out.println("\n  ✅ Candidato APROVADO em todas as etapas!\n");
            } else {
                candidato.setStatus(StatusCandidato.REPROVADO);
                candidato.setObservacoes("Reprovado nas avaliações técnica/comportamental.");
                System.out.println("\n  ❌ Candidato REPROVADO nas avaliações.\n");
            }
        } else {
            candidato.setStatus(StatusCandidato.AGUARDANDO_DOCUMENTOS);
            System.out.println("\n  ⏳ Candidato apto — aguardando avaliações técnica e comportamental.\n");
        }
    }

    // ─── Ranking ─────────────────────────────────────────────────────────────────

    public List<Candidato> gerarRankingAprovados() {
        return candidatos.stream()
                .filter(c -> c.getStatus() == StatusCandidato.APROVADO)
                .sorted(Comparator.comparingDouble(Candidato::getMediaNotas).reversed())
                .collect(Collectors.toList());
    }

    // ─── Relatório ───────────────────────────────────────────────────────────────

    public void imprimirRelatorioGeral() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  RELATÓRIO GERAL DO PROCESSO SELETIVO");
        System.out.println("=".repeat(60));
        System.out.printf("  Total de candidatos:  %d%n", candidatos.size());

        for (StatusCandidato status : StatusCandidato.values()) {
            long count = candidatos.stream().filter(c -> c.getStatus() == status).count();
            if (count > 0) {
                System.out.printf("  %-30s %d%n", status.getDescricao() + ":", count);
            }
        }

        List<Candidato> aprovados = gerarRankingAprovados();
        if (!aprovados.isEmpty()) {
            System.out.println("\n  RANKING — APROVADOS (por média):");
            System.out.println("  " + "-".repeat(50));
            for (int i = 0; i < aprovados.size(); i++) {
                Candidato c = aprovados.get(i);
                System.out.printf("  %2dº  %-25s  Média: %.1f%n",
                        i + 1, c.getNome(), c.getMediaNotas());
            }
        }
        System.out.println("=".repeat(60) + "\n");
    }

    // ─── Auxiliar ─────────────────────────────────────────────────────────────────

    private void imprimirResultado(String etapa, ResultadoValidacao resultado) {
        System.out.println("\n  ── " + etapa + " ──");
        resultado.getCriteriosAprovados().forEach(c -> System.out.println("     " + c));
        resultado.getCriteriosReprovados().forEach(c -> System.out.println("     " + c));
        resultado.getAlertas().forEach(a -> System.out.println("     " + a));
        System.out.println("  → " + resultado.getParecer());
        System.out.printf("  → Pontuação da etapa: %.1f/10%n", resultado.getPontuacaoFinal());
    }
}
