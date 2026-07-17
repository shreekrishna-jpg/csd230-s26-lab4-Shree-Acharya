package app.controllers;

import app.entities.TicketEntity;
import app.repositories.TicketEntityRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Ticket Operations", description = "CRUD operations for event and movie tickets")
@RestController
@RequestMapping("/api/rest/tickets")
@CrossOrigin(origins = "*")
public class TicketRestController {

    private final TicketEntityRepository repository;

    public TicketRestController(TicketEntityRepository repository) {
        this.repository = repository;
    }

    @Operation(summary = "Retrieve all tickets")
    @ApiResponse(responseCode = "200", description = "List of tickets returned")
    @GetMapping
    public List<TicketEntity> all() {
        return repository.findAll();
    }

    @Operation(summary = "Retrieve a single ticket by ID")
    @ApiResponse(responseCode = "200", description = "Ticket found")
    @ApiResponse(responseCode = "404", description = "Ticket not found")
    @GetMapping("/{id}")
    public TicketEntity one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ticket", id));
    }

    @Operation(summary = "Create a new ticket")
    @ApiResponse(responseCode = "200", description = "Ticket created successfully")
    @PostMapping
    public TicketEntity newTicket(@RequestBody TicketEntity ticket) {
        return repository.save(ticket);
    }

    @Operation(summary = "Update or replace a ticket by ID")
    @ApiResponse(responseCode = "200", description = "Ticket updated")
    @PutMapping("/{id}")
    public TicketEntity replaceTicket(@RequestBody TicketEntity updated, @PathVariable Long id) {
        return repository.findById(id)
                .map(ticket -> {
                    ticket.setDescription(updated.getDescription());
                    ticket.setPrice(updated.getPrice());
                    return repository.save(ticket);
                })
                .orElseGet(() -> {
                    updated.setId(id);
                    return repository.save(updated);
                });
    }

    @Operation(summary = "Delete a ticket by ID")
    @ApiResponse(responseCode = "200", description = "Ticket deleted")
    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable Long id) {
        repository.deleteById(id);
    }
}