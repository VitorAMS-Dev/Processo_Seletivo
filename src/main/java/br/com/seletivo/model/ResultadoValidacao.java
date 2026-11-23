package br.com.seletivo.model;

import java.util.ArrayList;
import java.util.List;

public class ResultadoValidacao {

    private boolean aprovado;
    private List<String> criteriosAprovados;
    private List<String> criteriosReprovados;
    private List<String> alertas;
    private double pontuacaoFinal;
    private String parecer;

    public ResultadoValidacao() {
        this.criteriosAprovados = new ArrayList<>();
        this.criteriosReprovados = new ArrayList<>();
        this.alertas = new ArrayList<>();
        this.aprovado = false;
        this.pontuacaoFinal = 0;
        this.parecer = "";
    }

    public void adicionarAprovado(String criterio) {
        criteriosAprovados.add("✔ " + criterio);
    }

    public void adicionarReprovado(String criterio) {
        criteriosReprovados.add("✘ " + criterio);
    }

    public void adicionarAlerta(String alerta) {
        alertas.add("⚠ " + alerta);
    }

    public boolean isAprovado() { return aprovado; }
    public void setAprovado(boolean aprovado) { this.aprovado = aprovado; }
    public List<String> getCriteriosAprovados() { return criteriosAprovados; }
    public List<String> getCriteriosReprovados() { return criteriosReprovados; }
    public List<String> getAlertas() { return alertas; }
    public double getPontuacaoFinal() { return pontuacaoFinal; }
    public void setPontuacaoFinal(double pontuacaoFinal) { this.pontuacaoFinal = pontuacaoFinal; }
    public String getParecer() { return parecer; }
    public void setParecer(String parecer) { this.parecer = parecer; }
}
