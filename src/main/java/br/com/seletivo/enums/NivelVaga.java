package br.com.seletivo.enums;

public enum NivelVaga {
    JUNIOR("Júnior", 1),
    PLENO("Pleno", 3),
    SENIOR("Sênior", 5);

    private final String descricao;
    private final int anosExperienciaMinimos;

    NivelVaga(String descricao, int anosExperienciaMinimos) {
        this.descricao = descricao;
        this.anosExperienciaMinimos = anosExperienciaMinimos;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getAnosExperienciaMinimos() {
        return anosExperienciaMinimos;
    }
}
