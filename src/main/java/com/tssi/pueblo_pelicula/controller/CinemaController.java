package com.tssi.pueblo_pelicula.controller;

import com.tssi.pueblo_pelicula.dto.*;
import com.tssi.pueblo_pelicula.service.CinemaService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/cinemas")
public class CinemaController {

  private final CinemaService cinemaService;

  @GetMapping("/names")
  @ResponseBody
  @Transactional(readOnly = true)
  public ResponseEntity<List<CinemaNameAndIdDTO>> getAllCinemasNameAndIds() {
    return ResponseEntity.ok(cinemaService.getCinemasNamesAndIds());
  }

  @GetMapping("/{id}")
  @ResponseBody
  @Transactional(readOnly = true)
  public ResponseEntity<CinemaDTO> getById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(cinemaService.getCinemaById(id));
  }

  @GetMapping("/screening")
  @ResponseBody
  @Transactional(readOnly = true)
  public ResponseEntity<List<ScreeningDTO>> getScreeningsForDate(@RequestParam("cinema_id") long cinemaId,
        @RequestParam("theater_id") long theaterId, @RequestParam("date") String date) {
    LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    return ResponseEntity.ok(cinemaService.getScreeningsForDate(cinemaId, theaterId, localDate));
  }

  @PostMapping("/screening/schedule")
  @ResponseBody
  @Transactional
  public ResponseEntity<Void> scheduleScreening(@Valid @RequestBody ScreeningScheduleDTO screeningScheduleDTO) {
    cinemaService.scheduleScreening(screeningScheduleDTO);

    return ResponseEntity.noContent().build();
  }

  @PostMapping("/screening/replanning")
  @ResponseBody
  @Transactional
  public ResponseEntity<Void> replanning(@Valid @RequestBody ScreeningReplanningDTO screeningReplanningDTO) {
    cinemaService.replanning(screeningReplanningDTO);

    return ResponseEntity.noContent().build();
  }
}
