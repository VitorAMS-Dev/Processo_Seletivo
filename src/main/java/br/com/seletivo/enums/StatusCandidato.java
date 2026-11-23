package br.com.seletivo.enums;

public enum StatusCandidato {
    PENDENTE("Pendente"),
    APROVADO("Aprovado"),
    REPROVADO("Reprovado"),
    EM_ANALISE("Em Análise"),
    AGUARDANDO_DOCUMENTOS("Aguardando Documentos");

    private final String descricao;

    StatusCandidato(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
