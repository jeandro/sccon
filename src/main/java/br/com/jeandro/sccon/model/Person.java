package br.com.jeandro.sccon.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Person {
    private Long id;

    @NotBlank
    private String nome;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAdmissao;

    @NotNull
    private BigDecimal salarioAtual;

    public Person(long l, String joséDaSilva, LocalDate localDate, LocalDate date) {}

    public Person(Long id, String nome, LocalDate dataNascimento, LocalDate dataAdmissao, BigDecimal salarioAtual) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.dataAdmissao = dataAdmissao;
        this.salarioAtual = salarioAtual;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
    public LocalDate getDataAdmissao() { return dataAdmissao; }
    public void setDataAdmissao(LocalDate dataAdmissao) { this.dataAdmissao = dataAdmissao; }
    public BigDecimal getSalarioAtual() { return salarioAtual; }
    public void setSalarioAtual(BigDecimal salarioAtual) { this.salarioAtual = salarioAtual; }
}
