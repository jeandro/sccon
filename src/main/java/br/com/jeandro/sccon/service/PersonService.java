package br.com.jeandro.sccon.service;

import br.com.jeandro.sccon.dto.*;
import br.com.jeandro.sccon.exception.BadRequestException;
import br.com.jeandro.sccon.exception.NotFoundException;
import br.com.jeandro.sccon.model.Person;
import br.com.jeandro.sccon.repository.InMemoryPersonRepository;
import br.com.jeandro.sccon.util.DateUtils;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private final InMemoryPersonRepository repo = new InMemoryPersonRepository();

    @PostConstruct
    public void seed() {
        repo.seed(List.of(
            new Person(null, "Maria Silva", LocalDate.of(1999, 4, 5), LocalDate.of(2020, 6, 4), new BigDecimal("3320.00")),
            new Person(null, "João Souza", LocalDate.of(1988, 12, 10), LocalDate.of(2015, 3, 1), new BigDecimal("1800.00")),
            new Person(null, "Ana Lima", LocalDate.of(2001, 8, 23), LocalDate.of(2023, 7, 1), new BigDecimal("2500.00"))
        ));
    }

    public List<PersonResponse> list() {
        return repo.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public PersonResponse get(Long id) {
        Person p = repo.findById(id).orElseThrow(() -> new NotFoundException("Pessoa não encontrada"));
        return toResponse(p);
    }

    public PersonResponse create(PersonCreateRequest r) {
        Person p = new Person(null, r.getNome(), r.getDataNascimento(), r.getDataAdmissao(), r.getSalarioAtual());
        return toResponse(repo.save(p));
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw new NotFoundException("Pessoa não encontrada");
        repo.deleteById(id);
    }

    public PersonResponse put(Long id, PersonUpdateRequest r) {
        Person p = repo.findById(id).orElseThrow(() -> new NotFoundException("Pessoa não encontrada"));
        p.setNome(r.getNome());
        p.setDataNascimento(r.getDataNascimento());
        p.setDataAdmissao(r.getDataAdmissao());
        p.setSalarioAtual(r.getSalarioAtual());
        return toResponse(repo.save(p));
    }

    public PersonResponse patch(Long id, PersonPatchRequest r) {
        Person p = repo.findById(id).orElseThrow(() -> new NotFoundException("Pessoa não encontrada"));
        if (r.getNome() != null) p.setNome(r.getNome());
        if (r.getDataNascimento() != null) p.setDataNascimento(r.getDataNascimento());
        if (r.getDataAdmissao() != null) p.setDataAdmissao(r.getDataAdmissao());
        if (r.getSalarioAtual() != null) p.setSalarioAtual(r.getSalarioAtual());
        return toResponse(repo.save(p));
    }

    public Number idade(Long id, String output) {
        Person p = repo.findById(id).orElseThrow(() -> new NotFoundException("Pessoa não encontrada"));
        LocalDate hoje = LocalDate.now();
        return switch (output == null ? "" : output.toLowerCase()) {
            case "dias" -> DateUtils.idadeEmDias(p.getDataNascimento(), hoje);
            case "meses" -> DateUtils.idadeEmMeses(p.getDataNascimento(), hoje);
            case "anos" -> round(DateUtils.idadeEmAnos(p.getDataNascimento(), hoje), 2);
            default -> throw new BadRequestException("Parâmetro 'output' deve ser dias, meses ou anos");
        };
    }

    /** Retorna o múltiplo de salários mínimos (ex.: 2.51) */
    public java.math.BigDecimal salarioMultiplo(Long id, BigDecimal salarioMinimo) {
        if (salarioMinimo == null || salarioMinimo.compareTo(BigDecimal.ZERO) <= 0)
            throw new BadRequestException("'valorMinimo' deve ser positivo");
        Person p = repo.findById(id).orElseThrow(() -> new NotFoundException("Pessoa não encontrada"));
        return p.getSalarioAtual().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
    }

    private java.math.BigDecimal round(double v, int scale) {
        return new java.math.BigDecimal(v).setScale(scale, RoundingMode.HALF_UP);
    }

    private PersonResponse toResponse(Person p) {
        return new PersonResponse(p.getId(), p.getNome(), p.getDataNascimento(), p.getDataAdmissao(), p.getSalarioAtual());
    }
}
