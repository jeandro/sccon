package br.com.jeandro.sccon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class PersonUpdateRequest {
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

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
    public LocalDate getDataAdmissao() { return dataAdmissao; }
    public void setDataAdmissao(LocalDate dataAdmissao) { this.dataAdmissao = dataAdmissao; }
    public BigDecimal getSalarioAtual() { return salarioAtual; }
    public void setSalarioAtual(BigDecimal salarioAtual) { this.salarioAtual = salarioAtual; }
}
