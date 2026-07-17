package app.controllers;

import app.entities.ProductEntity;
import app.entities.StationeryEntity;
import app.repositories.StationeryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Niche Department — Stationery", description = "CRUD operations for pens and notebooks")
@RestController
@RequestMapping("/api/rest/stationery")
@CrossOrigin(origins = "*")
public class NicheRestController {

    private final StationeryRepository repository;

    public NicheRestController(StationeryRepository repository) {
        this.repository = repository;
    }

    @Operation(summary = "Retrieve all stationery items (pens and notebooks)")
    @ApiResponse(responseCode = "200", description = "List of stationery returned")
    @GetMapping
    public List<ProductEntity> all() {
        return repository.findAllStationery();
    }

    @Operation(summary = "Retrieve a single stationery item by ID")
    @ApiResponse(responseCode = "200", description = "Item found")
    @ApiResponse(responseCode = "404", description = "Item not found")
    @GetMapping("/{id}")
    public ProductEntity one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("stationery item", id));
    }

    @Operation(summary = "Create a new stationery item (pen or notebook)")
    @ApiResponse(responseCode = "200", description = "Item created successfully")
    @PostMapping
    public ProductEntity newItem(@RequestBody ProductEntity item) {
        return repository.save(item);
    }

    @Operation(summary = "Update or replace a stationery item by ID")
    @ApiResponse(responseCode = "200", description = "Item updated")
    @PutMapping("/{id}")
    public ProductEntity replaceItem(@RequestBody ProductEntity updated, @PathVariable Long id) {
        return repository.findById(id)
                .map(item -> {
                    if (item instanceof StationeryEntity s && updated instanceof StationeryEntity u) {
                        s.setBrand(u.getBrand());
                        s.setIsEcoFriendly(u.getIsEcoFriendly());
                    }
                    return repository.save(item);
                })
                .orElseGet(() -> {
                    updated.setId(id);
                    return repository.save(updated);
                });
    }

    @Operation(summary = "Delete a stationery item by ID")
    @ApiResponse(responseCode = "200", description = "Item deleted")
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        repository.deleteById(id);
    }
}