package br.com.seletivo.model;

import br.com.seletivo.enums.NivelVaga;
import java.util.ArrayList;
import java.util.List;

public class Vaga {

    private String titulo;
    private String departamento;
    private NivelVaga nivel;
    private double salarioMinimo;
    private double salarioMaximo;
    private int vagasDisponiveis;
    private boolean exigeFormacaoSuperior;
    private List<String> habilidadesRequeridas;
    private String descricao;

    public Vaga(String titulo, String departamento, NivelVaga nivel,
                double salarioMinimo, double salarioMaximo,
                int vagasDisponiveis, boolean exigeFormacaoSuperior) {
        this.titulo = titulo;
        this.departamento = departamento;
        this.nivel = nivel;
        this.salarioMinimo = salarioMinimo;
        this.salarioMaximo = salarioMaximo;
        this.vagasDisponiveis = vagasDisponiveis;
        this.exigeFormacaoSuperior = exigeFormacaoSuperior;
        this.habilidadesRequeridas = new ArrayList<>();
        this.descricao = "";
    }

    public void adicionarHabilidadeRequerida(String habilidade) {
        this.habilidadesRequeridas.add(habilidade);
    }

    // Getters e Setters
    public String getTitulo() { return titulo; }
    public String getDepartamento() { return departamento; }
    public NivelVaga getNivel() { return nivel; }
    public double getSalarioMinimo() { return salarioMinimo; }
    public double getSalarioMaximo() { return salarioMaximo; }
    public int getVagasDisponiveis() { return vagasDisponiveis; }
    public void setVagasDisponiveis(int vagasDisponiveis) { this.vagasDisponiveis = vagasDisponiveis; }
    public boolean isExigeFormacaoSuperior() { return exigeFormacaoSuperior; }
    public List<String> getHabilidadesRequeridas() { return habilidadesRequeridas; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    @Override
    public String toString() {
        return String.format("%s (%s) | Nível: %s | Salário: R$ %.2f - R$ %.2f | Vagas: %d",
                titulo, departamento, nivel.getDescricao(), salarioMinimo, salarioMaximo, vagasDisponiveis);
    }
}
