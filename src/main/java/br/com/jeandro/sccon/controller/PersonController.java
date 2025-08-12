package br.com.jeandro.sccon.controller;

import br.com.jeandro.sccon.dto.*;
import br.com.jeandro.sccon.model.Person;
import br.com.jeandro.sccon.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/persons")
@Tag(name = "Person", description = "Gerenciamento de pessoas")
public class PersonController {

    private static final BigDecimal DEFAULT_SALARIO_MINIMO = new BigDecimal("1320.00");

    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lista todas as pessoas",
        description = "Retorna uma lista de pessoas, ordenada em ordem alfabética por nome.")
    @ApiResponse(responseCode = "200", description = "Lista de pessoas retornada com sucesso")
    public List<PersonResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma pessoa pelo ID",
        description = "Retorna uma pessoa específica com base no ID fornecido.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pessoa encontrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    public PersonResponse get(@PathVariable("id") Long id) {
        return service.get(id);
    }

    @PostMapping
    @Operation(summary = "Cria uma nova pessoa",
        description = "Cria uma nova pessoa. Um ID será atribuído automaticamente caso não seja fornecido.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pessoa criada com sucesso",
            content = @Content(schema = @Schema(implementation = Person.class))),
        @ApiResponse(responseCode = "409", description = "Já existe uma pessoa com o ID fornecido")
    })
    public ResponseEntity<PersonResponse> create(@Valid @RequestBody PersonCreateRequest req) {
        PersonResponse created = service.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma pessoa",
        description = "Deleta uma pessoa da base de dados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pessoa Removida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma pessoa",
        description = "Atualiza todos os dados de uma pessoa existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    public PersonResponse put(@PathVariable("id") Long id, @Valid @RequestBody PersonUpdateRequest req) {
        return service.put(id, req);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualiza parcialmente uma pessoa",
        description = "Atualiza apenas os campos fornecidos de uma pessoa existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    public PersonResponse patch(@PathVariable("id") Long id, @RequestBody PersonPatchRequest req) {
        return service.patch(id, req);
    }

    // /persons/{id}/idade?output=dias|meses|anos
    @GetMapping("/{id}/idade")
    @Operation(summary = "Calcula a idade de uma pessoa",
        description = "Calcula a idade da pessoa em dias, meses ou anos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Idade calculada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Formato de saída inválido"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    public Map<String, Object> idade(@PathVariable("id") Long id, @RequestParam String output) {
        String normalized = normalizeOutput(output);
        if (!("dias".equals(normalized) || "meses".equals(normalized) || "anos".equals(normalized))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Parâmetro 'output' inválido. Use: dias | meses | anos.");
        }

        Object valor = service.idade(id, normalized);
        if (valor == null) {
            // Evita NPE no Map.of; escolha o status que fizer mais sentido no seu domínio
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                "Não foi possível calcular a idade.");
        }

        return Map.of(
            "id", id,
            "output", normalized,
            "valor", valor
        );
    }

    // /persons/{id}/salario?valorMinimo=1320
    @GetMapping("/{id}/salario")
    @Operation(summary = "Calcula o salário de uma pessoa",
        description = "Calcula o salário da pessoa com base no tempo de empresa.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Salário calculado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Formato de saída inválido"),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    public Map<String, Object> salario(@PathVariable("id") Long id,
                                       @RequestParam(name = "valorMinimo", required = false) BigDecimal valorMinimo) {
        BigDecimal minimo = (valorMinimo == null) ? DEFAULT_SALARIO_MINIMO : valorMinimo;

        Object multiplo = service.salarioMultiplo(id, minimo);
        if (multiplo == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                "Não foi possível obter o múltiplo do salário.");
        }

        return Map.of(
            "id", id,
            "salarioAtualMultiplo", multiplo,
            "salarioMinimo", minimo
        );
    }

    private String normalizeOutput(String output) {
        return output == null ? "" : output.trim().toLowerCase(Locale.ROOT);
    }
}
