package br.com.jeandro.sccon.service;

import br.com.jeandro.sccon.dto.PersonCreateRequest;
import br.com.jeandro.sccon.dto.PersonResponse;
import br.com.jeandro.sccon.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes de unidade da camada de serviço de Pessoas.
 * Objetivo: validar regras de negócio e operações sobre o repositório em memória.
 */
@DisplayName("PersonService - Regras de negócio e operações básicas")
class PersonServiceTest {

    @Test
    @DisplayName("Deve criar uma pessoa e, em seguida, recuperá-la por ID")
    void createAndFind() {
        // Given: um serviço com dados seed e um payload válido de criação
        PersonService service = new PersonService();
        service.seed();
        PersonCreateRequest r = new PersonCreateRequest();
        r.setNome("Teste");
        r.setDataNascimento(LocalDate.of(2000,1,1));
        r.setDataAdmissao(LocalDate.of(2020,1,1));
        r.setSalarioAtual(new BigDecimal("2640"));

        // When: criamos a pessoa e buscamos pelo ID retornado
        PersonResponse pr = service.create(r);

        // Then: o ID deve existir e o nome recuperado deve corresponder ao criado
        assertNotNull(pr.getId(), "ID deve ser preenchido após criar");
        assertEquals("Teste", service.get(pr.getId()).getNome(), "Nome recuperado deve ser igual ao enviado");
    }

    @Test
    @DisplayName("Deve lançar NotFoundException ao tentar deletar ID inexistente")
    void deleteUnknown() {
        // Given: um serviço com dados seed
        PersonService service = new PersonService();
        service.seed();

        // When/Then: deletar um ID inexistente deve lançar NotFoundException
        assertThrows(NotFoundException.class, () -> service.delete(999L),
            "Esperava NotFoundException ao deletar pessoa inexistente");
    }
}
