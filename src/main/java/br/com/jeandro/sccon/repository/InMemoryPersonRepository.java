package br.com.jeandro.sccon.repository;

import br.com.jeandro.sccon.model.Person;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryPersonRepository {

    private final Map<Long, Person> store = new ConcurrentHashMap<>();

    private final AtomicLong seq = new AtomicLong(0);

    public InMemoryPersonRepository() {}

    public List<Person> findAll() { return new ArrayList<>(store.values()); }
    public Optional<Person> findById(Long id) { return Optional.ofNullable(store.get(id)); }
    public Person save(Person p) {
        if (p.getId() == null) p.setId(seq.incrementAndGet());
        store.put(p.getId(), p);
        return p;
    }
    public boolean existsById(Long id) { return store.containsKey(id); }
    public void deleteById(Long id) { store.remove(id); }

    public void seed(List<Person> initial) {
        initial.forEach(p -> {
            if (p.getId() == null) p.setId(seq.incrementAndGet());
            store.put(p.getId(), p);
        });
    }
}
