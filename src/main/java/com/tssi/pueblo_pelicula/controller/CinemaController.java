package com.tssi.pueblo_pelicula.controller;

import com.tssi.pueblo_pelicula.service.CinemaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cinemas")
public class CinemaController {

    private CinemaService cinemaService;

    public CinemaController (CinemaService cinemaService){
        this.cinemaService = cinemaService;
    }
    @GetMapping("/names")
    public ResponseEntity<List<String>> getNames(){
        return new ResponseEntity<>(cinemaService.getNames(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return new ResponseEntity<>(cinemaService.getById(id), HttpStatus.OK);
    }
}
