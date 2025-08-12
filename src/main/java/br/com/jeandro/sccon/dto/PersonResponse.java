package br.com.jeandro.sccon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;

public class PersonResponse {
    private Long id;
    private String nome;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAdmissao;
    private BigDecimal salarioAtual;

    public PersonResponse(Long id, String nome, LocalDate dataNascimento, LocalDate dataAdmissao, BigDecimal salarioAtual) {
        this.id = id; this.nome = nome; this.dataNascimento = dataNascimento; this.dataAdmissao = dataAdmissao; this.salarioAtual = salarioAtual;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public LocalDate getDataAdmissao() { return dataAdmissao; }
    public BigDecimal getSalarioAtual() { return salarioAtual; }
}
