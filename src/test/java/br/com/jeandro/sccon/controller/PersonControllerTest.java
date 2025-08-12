package br.com.jeandro.sccon.controller;

import br.com.jeandro.sccon.dto.PersonCreateRequest;
import br.com.jeandro.sccon.dto.PersonPatchRequest;
import br.com.jeandro.sccon.dto.PersonResponse;
import br.com.jeandro.sccon.dto.PersonUpdateRequest;
import br.com.jeandro.sccon.service.PersonService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonControllerDirectTest {

    private PersonResponse sample() {
        return new PersonResponse(
            1L, "Maria Silva",
            LocalDate.of(1990, 1, 15),
            LocalDate.of(2020, 3, 10),
            new BigDecimal("3450.75")
        );
    }

    @Test
    @DisplayName("POST create -> retorna 201 e body do service")
    void create_happyPath() {
        PersonService service = mock(PersonService.class);
        PersonController controller = new PersonController(service);

        PersonCreateRequest req = mock(PersonCreateRequest.class);
        PersonResponse resp = sample();
        when(service.create(req)).thenReturn(resp);

        var entity = controller.create(req);
        assertEquals(201, entity.getStatusCodeValue());
        assertSame(resp, entity.getBody());
        verify(service).create(req);
    }

    @Test
    @DisplayName("PUT put -> retorna body do service")
    void put_happyPath() {
        PersonService service = mock(PersonService.class);
        PersonController controller = new PersonController(service);

        PersonUpdateRequest req = mock(PersonUpdateRequest.class);
        PersonResponse resp = sample();
        when(service.put(3L, req)).thenReturn(resp);

        var result = controller.put(3L, req);
        assertSame(resp, result);
        verify(service).put(3L, req);
    }

    @Test
    @DisplayName("PATCH patch -> retorna body do service")
    void patch_happyPath() {
        PersonService service = mock(PersonService.class);
        PersonController controller = new PersonController(service);

        PersonPatchRequest req = mock(PersonPatchRequest.class);
        PersonResponse resp = sample();
        when(service.patch(4L, req)).thenReturn(resp);

        var result = controller.patch(4L, req);
        assertSame(resp, result);
        verify(service).patch(4L, req);
    }

    @Test
    @DisplayName("GET idade -> normaliza output e monta Map")
    void idade_map() {
        PersonService service = mock(PersonService.class);
        PersonController controller = new PersonController(service);

        // Garanta que casa com os argumentos efetivos:
        when(service.idade(eq(1L), eq("anos"))).thenReturn(30L);
        // Alternativa à prova de erro de casamento:
        // when(service.idade(anyLong(), anyString())).thenReturn(30L);

        Map<String, Object> out = controller.idade(1L, "AnOs");

        assertEquals(1L, out.get("id"));
        assertEquals("anos", out.get("output")); // normalizado
        assertEquals(30L, out.get("valor"));

        verify(service).idade(1L, "anos");
    }

    @Test
    @DisplayName("GET salario -> usa default 1320.00 quando null")
    void salario_default() {
        PersonService service = mock(PersonService.class);
        PersonController controller = new PersonController(service);
        when(service.salarioMultiplo(eq(2L), any())).thenReturn(new BigDecimal("1.50"));

        Map<String, Object> out = controller.salario(2L, null);
        assertEquals(2L, out.get("id"));
        assertEquals(new BigDecimal("1.50"), out.get("salarioAtualMultiplo"));
        assertEquals(new BigDecimal("1320.00"), out.get("salarioMinimo"));
        verify(service).salarioMultiplo(2L, new BigDecimal("1320.00"));
    }
}
