package restaurante.modelo;

public class Cliente {
    private final String nome;
    private final String cpf;
    private final String telefone;

    public Cliente(String nome, String cpf, String telefone) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome obrigatorio.");
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getTelefone() { return telefone; }
}
