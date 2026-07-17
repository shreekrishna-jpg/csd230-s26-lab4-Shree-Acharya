package app.controllers;

import app.entities.PenEntity;
import app.entities.ProductEntity;
import app.entities.StationeryEntity;
import app.repositories.StationeryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Niche Department — Stationery",
        description = "CRUD operations for pens and notebooks"
)
@RestController
@RequestMapping("/api/rest/stationery")
@CrossOrigin(origins = "*")
public class NicheRestController {

    private final StationeryRepository repository;

    public NicheRestController(StationeryRepository repository) {
        this.repository = repository;
    }

    @Operation(summary = "Retrieve all stationery items")
    @ApiResponse(
            responseCode = "200",
            description = "List of stationery returned"
    )
    @GetMapping
    public List<ProductEntity> all() {
        return repository.findAllStationery();
    }

    @Operation(summary = "Retrieve a stationery item by ID")
    @ApiResponse(responseCode = "200", description = "Item found")
    @ApiResponse(responseCode = "404", description = "Item not found")
    @GetMapping("/{id}")
    public ProductEntity one(@PathVariable Long id) {

        ProductEntity item = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "stationery item", id
                        ));

        if (!(item instanceof StationeryEntity)) {
            throw new ResourceNotFoundException(
                    "stationery item", id
            );
        }

        return item;
    }

    @Operation(summary = "Create a new pen")
    @ApiResponse(
            responseCode = "200",
            description = "Pen created successfully"
    )
    @PostMapping
    public ProductEntity newItem(@RequestBody PenEntity pen) {
        return repository.save(pen);
    }

    @Operation(summary = "Update a stationery item by ID")
    @ApiResponse(responseCode = "200", description = "Item updated")
    @ApiResponse(responseCode = "404", description = "Item not found")
    @PutMapping("/{id}")
    public ProductEntity replaceItem(
            @RequestBody PenEntity updated,
            @PathVariable Long id) {

        ProductEntity item = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "stationery item", id
                        ));

        if (!(item instanceof PenEntity pen)) {
            throw new ResourceNotFoundException(
                    "pen", id
            );
        }

        pen.setBrand(updated.getBrand());
        pen.setIsEcoFriendly(updated.getIsEcoFriendly());
        pen.setInkColor(updated.getInkColor());
        pen.setTipSize(updated.getTipSize());

        return repository.save(pen);
    }

    @Operation(summary = "Delete a stationery item by ID")
    @ApiResponse(responseCode = "200", description = "Item deleted")
    @ApiResponse(responseCode = "404", description = "Item not found")
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {

        ProductEntity item = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "stationery item", id
                        ));

        if (!(item instanceof StationeryEntity)) {
            throw new ResourceNotFoundException(
                    "stationery item", id
            );
        }

        repository.delete(item);
    }
}