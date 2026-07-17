package app.controllers;

import app.entities.MagazineEntity;
import app.repositories.MagazineEntityRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Magazine Management", description = "CRUD operations for magazines and disc magazines")
@RestController
@RequestMapping("/api/rest/magazines")
@CrossOrigin(origins = "*")
public class MagazineRestController {

    private final MagazineEntityRepository repository;

    public MagazineRestController(MagazineEntityRepository repository) {
        this.repository = repository;
    }

    @Operation(summary = "Retrieve all magazines including disc magazines")
    @ApiResponse(responseCode = "200", description = "List of magazines returned")
    @GetMapping
    public List<MagazineEntity> all() {
        return repository.findAll();
    }

    @Operation(summary = "Retrieve a single magazine by ID")
    @ApiResponse(responseCode = "200", description = "Magazine found")
    @ApiResponse(responseCode = "404", description = "Magazine not found")
    @GetMapping("/{id}")
    public MagazineEntity one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("magazine", id));
    }

    @Operation(summary = "Create a new magazine or disc magazine")
    @ApiResponse(responseCode = "200", description = "Magazine created successfully")
    @PostMapping
    public MagazineEntity newMagazine(@RequestBody MagazineEntity magazine) {
        return repository.save(magazine);
    }

    @Operation(summary = "Update or replace a magazine by ID")
    @ApiResponse(responseCode = "200", description = "Magazine updated")
    @PutMapping("/{id}")
    public MagazineEntity replaceMagazine(@RequestBody MagazineEntity updated, @PathVariable Long id) {
        return repository.findById(id)
                .map(mag -> {
                    mag.setTitle(updated.getTitle());
                    mag.setPrice(updated.getPrice());
                    mag.setCopies(updated.getCopies());
                    mag.setOrderQty(updated.getOrderQty());
                    mag.setCurrentIssue(updated.getCurrentIssue());
                    return repository.save(mag);
                })
                .orElseGet(() -> {
                    updated.setId(id);
                    return repository.save(updated);
                });
    }

    @Operation(summary = "Delete a magazine by ID")
    @ApiResponse(responseCode = "200", description = "Magazine deleted")
    @DeleteMapping("/{id}")
    public void deleteMagazine(@PathVariable Long id) {
        repository.deleteById(id);
    }
}