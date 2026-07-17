package app;

import app.entities.*;
import app.repositories.*;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner demo(
            BookRepository bookRepository,
            UserRepository userRepository,
            TicketEntityRepository ticketRepository,
            MagazineEntityRepository magazineRepository,
            StationeryRepository stationeryRepository) {

        return (args) -> {
            Faker faker = new Faker();

            // 1. Seed Books
            if (bookRepository.count() == 0) {
                for (int i = 0; i < 5; i++) {
                    bookRepository.save(new BookEntity(
                            faker.book().author(),
                            faker.book().title(),
                            faker.number().randomDouble(2, 10, 50),
                            faker.number().numberBetween(5, 20)
                    ));
                }
                System.out.println(">> Seeded 5 Books");
            }

            // 2. Seed Tickets
            if (ticketRepository.count() == 0) {
                ticketRepository.save(new TicketEntity("Concert Ticket", 99.99));
                ticketRepository.save(new TicketEntity("Movie Ticket", 12.50));
                ticketRepository.save(new TicketEntity("Sports Event Ticket", 45.00));
                System.out.println(">> Seeded 3 Tickets");
            }

            // 3. Seed Magazines and DiscMags
            if (magazineRepository.count() == 0) {
                magazineRepository.save(new MagazineEntity(100, LocalDateTime.now(), "Tech Monthly", 9.99, 50));
                magazineRepository.save(new MagazineEntity(200, LocalDateTime.now().minusMonths(1), "Science Weekly", 7.49, 30));
                magazineRepository.save(new DiscMagEntity(true, 75, LocalDateTime.now(), "Game World + Disc", 14.99, 25));
                magazineRepository.save(new DiscMagEntity(false, 60, LocalDateTime.now().minusMonths(2), "Photo Mag (No Disc)", 11.99, 40));
                System.out.println(">> Seeded 2 Magazines + 2 DiscMags");
            }

            // 4. Seed Stationery (Niche)
            if (stationeryRepository.count() == 0) {
                stationeryRepository.save(new PenEntity("Pilot", true, "Black", 0.5));
                stationeryRepository.save(new PenEntity("Staedtler", false, "Blue", 0.7));
                stationeryRepository.save(new NotebookEntity("Moleskine", true, 200, "Ruled"));
                stationeryRepository.save(new NotebookEntity("Leuchtturm", true, 249, "Dotted"));
                System.out.println(">> Seeded 2 Pens + 2 Notebooks");
            }

            // 5. Seed Default Users
            if (userRepository.count() == 0) {
                userRepository.save(new UserEntity("admin", passwordEncoder().encode("admin"), "ADMIN"));
                userRepository.save(new UserEntity("user", passwordEncoder().encode("user"), "USER"));
                System.out.println(">> Seeded Users: admin/admin, user/user");
            }
        };
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
            }
        };
    }
}