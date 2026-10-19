
package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kangaroos")
public class KangarooController {

    private Map<Integer, Kangaroo> kangaroos;

    @PostConstruct
    public void init() {
        kangaroos = new HashMap<>();
    }

    // Tüm kangaroo nesnelerini getir
    @GetMapping
    public List<Kangaroo> getAll() {
        return new ArrayList<>(kangaroos.values());
    }

    // ID'ye göre kangaroo getir
    @GetMapping("/{id}")
    public Kangaroo getById(@PathVariable int id) {
        Kangaroo kangaroo = kangaroos.get(id);

        if (kangaroo == null) {
            throw new ZooException(
                    "Kangaroo bulunamadı. ID: " + id,
                    HttpStatus.NOT_FOUND
            );
        }

        return kangaroo;
    }

    // Yeni kangaroo ekle
    @PostMapping
    public Kangaroo create(@RequestBody Kangaroo kangaroo) {
        if (kangaroo.getId() <= 0
                || kangaroo.getName() == null
                || kangaroo.getName().isBlank()
                || kangaroo.getWeight() <= 0
                || kangaroo.getHeight() <= 0
                || kangaroo.getGender() == null
                || kangaroo.getGender().isBlank()) {

            throw new ZooException(
                    "Geçersiz kanguru bilgileri.",
                    HttpStatus.BAD_REQUEST
            );
        }

        kangaroos.put(kangaroo.getId(), kangaroo);
        return kangaroo;
    }

    // Kangaroo bilgilerini güncelle
    @PutMapping("/{id}")
    public Kangaroo update(
            @PathVariable int id,
            @RequestBody Kangaroo kangaroo) {

        if (!kangaroos.containsKey(id)) {
            throw new ZooException(
                    "Kangaroo bulunamadı. ID: " + id,
                    HttpStatus.NOT_FOUND
            );
        }

        kangaroos.put(id, kangaroo);
        return kangaroo;
    }


    // Kangaroo sil
    @DeleteMapping("/{id}")
    public Kangaroo delete(@PathVariable int id) {
        Kangaroo kangaroo = kangaroos.remove(id);

        if (kangaroo == null) {
            throw new ZooException(
                    "Kangaroo bulunamadı. ID: " + id,
                    HttpStatus.NOT_FOUND
            );
        }

        return kangaroo;
    }
}

