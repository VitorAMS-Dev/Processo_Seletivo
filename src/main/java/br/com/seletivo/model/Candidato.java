package br.com.seletivo.model;

import br.com.seletivo.enums.StatusCandidato;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Candidato {

    private static int contadorId = 1;

    private int id;
    private String nome;
    private String cpf;
    private String email;
    private LocalDate dataNascimento;
    private int anosExperiencia;
    private double pretensaoSalarial;
    private List<String> habilidades;
    private boolean possuiFormacaoSuperior;
    private String nivelFormacao; // Técnico, Graduação, Pós-Graduação, Mestrado, Doutorado
    private StatusCandidato status;
    private String observacoes;
    private double notaTecnica;    // 0 a 10
    private double notaComportamental; // 0 a 10

    public Candidato(String nome, String cpf, String email, LocalDate dataNascimento,
                     int anosExperiencia, double pretensaoSalarial,
                     boolean possuiFormacaoSuperior, String nivelFormacao) {
        this.id = contadorId++;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.anosExperiencia = anosExperiencia;
        this.pretensaoSalarial = pretensaoSalarial;
        this.possuiFormacaoSuperior = possuiFormacaoSuperior;
        this.nivelFormacao = nivelFormacao;
        this.habilidades = new ArrayList<>();
        this.status = StatusCandidato.PENDENTE;
        this.observacoes = "";
        this.notaTecnica = 0;
        this.notaComportamental = 0;
    }

    public int getIdade() {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    public double getMediaNotas() {
        return (notaTecnica + notaComportamental) / 2.0;
    }

    public void adicionarHabilidade(String habilidade) {
        this.habilidades.add(habilidade);
    }

    // Getters e Setters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public int getAnosExperiencia() { return anosExperiencia; }
    public void setAnosExperiencia(int anosExperiencia) { this.anosExperiencia = anosExperiencia; }
    public double getPretensaoSalarial() { return pretensaoSalarial; }
    public void setPretensaoSalarial(double pretensaoSalarial) { this.pretensaoSalarial = pretensaoSalarial; }
    public List<String> getHabilidades() { return habilidades; }
    public boolean isPossuiFormacaoSuperior() { return possuiFormacaoSuperior; }
    public String getNivelFormacao() { return nivelFormacao; }
    public StatusCandidato getStatus() { return status; }
    public void setStatus(StatusCandidato status) { this.status = status; }
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    public double getNotaTecnica() { return notaTecnica; }
    public void setNotaTecnica(double notaTecnica) { this.notaTecnica = notaTecnica; }
    public double getNotaComportamental() { return notaComportamental; }
    public void setNotaComportamental(double notaComportamental) { this.notaComportamental = notaComportamental; }

    @Override
    public String toString() {
        return String.format("[#%d] %s | CPF: %s | Idade: %d anos | Exp: %d anos | Status: %s",
                id, nome, cpf, getIdade(), anosExperiencia, status.getDescricao());
    }
}
