package br.com.seletivo;

import br.com.seletivo.enums.NivelVaga;
import br.com.seletivo.model.Candidato;
import br.com.seletivo.model.Vaga;
import br.com.seletivo.service.ProcessoSeletivoService;

import java.time.LocalDate;

/**
 * ╔══════════════════════════════════════════════════════════╗
 * ║   Sistema de Validação de Processo Seletivo              ║
 * ║   Demonstração com candidatos e vaga fictícios           ║
 * ╚══════════════════════════════════════════════════════════╝
 */
public class Main {

    public static void main(String[] args) {

        ProcessoSeletivoService service = new ProcessoSeletivoService();

        // ── Definição da vaga ─────────────────────────────────────────────────────
        Vaga vaga = new Vaga(
                "Desenvolvedor Backend",
                "Engenharia de Software",
                NivelVaga.PLENO,
                6000.00,
                10000.00,
                2,
                true
        );
        vaga.adicionarHabilidadeRequerida("Java");
        vaga.adicionarHabilidadeRequerida("Spring Boot");
        vaga.adicionarHabilidadeRequerida("SQL");
        vaga.adicionarHabilidadeRequerida("REST API");
        vaga.setDescricao("Vaga para desenvolvedor backend com foco em microsserviços e APIs REST.");

        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE VALIDAÇÃO DE PROCESSO SELETIVO              ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        System.out.println("\n  Vaga: " + vaga);

        // ── Candidato 1 — APROVADO ────────────────────────────────────────────────
        Candidato c1 = new Candidato(
                "Ana Souza",
                "529.982.247-25",
                "ana.souza@email.com",
                LocalDate.of(1995, 3, 14),
                4,
                8500.00,
                true,
                "Graduação"
        );
        c1.adicionarHabilidade("Java");
        c1.adicionarHabilidade("Spring Boot");
        c1.adicionarHabilidade("SQL");
        c1.adicionarHabilidade("REST API");
        c1.setNotaTecnica(8.5);
        c1.setNotaComportamental(9.0);
        service.cadastrarCandidato(c1);

        // ── Candidato 2 — REPROVADO (CPF inválido) ───────────────────────────────
        Candidato c2 = new Candidato(
                "Carlos Mendes",
                "111.111.111-11",          // CPF inválido intencional
                "carlos.mendes@email.com",
                LocalDate.of(1990, 7, 22),
                5,
                9000.00,
                true,
                "Pós-Graduação"
        );
        c2.adicionarHabilidade("Java");
        c2.adicionarHabilidade("Spring Boot");
        service.cadastrarCandidato(c2);

        // ── Candidato 3 — REPROVADO (experiência insuficiente) ────────────────────
        Candidato c3 = new Candidato(
                "Lucas Ferreira",
                "123.456.789-09",
                "lucas.ferreira@email.com",
                LocalDate.of(2001, 11, 5),
                1,                         // Apenas 1 ano — vaga exige 3 (Pleno)
                6500.00,
                true,
                "Graduação"
        );
        c3.adicionarHabilidade("Java");
        c3.adicionarHabilidade("SQL");
        service.cadastrarCandidato(c3);

        // ── Candidato 4 — APROVADO COM DESTAQUE ──────────────────────────────────
        Candidato c4 = new Candidato(
                "Mariana Lima",
                "987.654.321-00",
                "mariana.lima@email.com",
                LocalDate.of(1992, 5, 18),
                6,
                9800.00,
                true,
                "Mestrado"
        );
        c4.adicionarHabilidade("Java");
        c4.adicionarHabilidade("Spring Boot");
        c4.adicionarHabilidade("SQL");
        c4.adicionarHabilidade("REST API");
        c4.setNotaTecnica(9.5);
        c4.setNotaComportamental(9.2);
        service.cadastrarCandidato(c4);

        // ── Candidato 5 — AGUARDANDO AVALIAÇÕES ──────────────────────────────────
        Candidato c5 = new Candidato(
                "Pedro Costa",
                "456.789.012-91",
                "pedro.costa@email.com",
                LocalDate.of(1988, 9, 30),
                5,
                8000.00,
                true,
                "Graduação"
        );
        c5.adicionarHabilidade("Java");
        c5.adicionarHabilidade("Spring Boot");
        c5.adicionarHabilidade("REST API");
        // Notas não atribuídas ainda — sistema indicará aguardando avaliações
        service.cadastrarCandidato(c5);

        // ── Execução das triagens ─────────────────────────────────────────────────
        service.executarTriagemCompleta(c1, vaga);
        service.executarTriagemCompleta(c2, vaga);
        service.executarTriagemCompleta(c3, vaga);
        service.executarTriagemCompleta(c4, vaga);
        service.executarTriagemCompleta(c5, vaga);

        // ── Relatório final ───────────────────────────────────────────────────────
        service.imprimirRelatorioGeral();
    }
}
