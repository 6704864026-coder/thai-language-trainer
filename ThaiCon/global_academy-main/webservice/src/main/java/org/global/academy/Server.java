package org.global.academy;

import static spark.Spark.*;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.IOException;

public class Server {
    public static void main(String[] args) {
        port(8080);
        staticFiles.location("/public");
        
        // Simple CORS
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*"); // or specific origin
            response.header("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
            response.header("Access-Control-Allow-Headers", "Content-Type,Authorization");
        });
        options("/*", (req, res) -> {
            String accessControlRequestHeaders = req.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                res.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = req.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                res.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });

        Gson gson = new Gson();
        Random random = new Random();

        // This is the GET route for serving a random Thai flashcard
        get("/flashcard", (req, res) -> {
            
            // 1. Pick a random card from our static list
            Flashcard randomCard = flashcards.get(random.nextInt(flashcards.size()));

            // 2. Set the response type to "application/json"
            res.type("application/json");

            // 3. Convert the card object to a JSON string and return it
            return gson.toJson(randomCard);
        }); // <-- I just cleaned up the formatting here

        
        // --- THIS IS THE NEW CODE FOR TASK 4 ---
        // --- I ADDED THIS INSIDE main() ---
        get("/showrandcard", (req, res) -> {
            
            // Pick a random card from the *new* generic list
            Flashcard randomCard = genericFlashcards.get(random.nextInt(genericFlashcards.size()));

            // Set the response type to "application/json"
            res.type("application/json");

            // Convert the card object to a JSON string and return it
            return gson.toJson(randomCard);
        });
        // --- END OF NEW CODE ---


        post("/login", (req, res) -> {
            System.out.println("Received /login request with body: " + req.body());
            LoginRequest lr = gson.fromJson(req.body(), LoginRequest.class);
            // TODO: validate username/password
            if ("alice".equals(lr.username) && "secret".equals(lr.password)) {
                res.type("application/json");
                return gson.toJson(new LoginResponse("a-fake-token", lr.username));
            } else {
                res.status(401);
                res.type("application/json");
                return gson.toJson(new ErrorResponse("Invalid credentials"));
            }
        });
    } // <-- THIS IS THE END OF THE main() METHOD
    
    
    // --- THIS IS ALL YOUR CODE *OUTSIDE* main() ---
    // --- (IT IS CORRECT) ---
    
    // A static list to hold all our flashcards
    private static List<Flashcard> flashcards = createFlashcards();
    
    // A helper method to create and populate the list of 10 cards
    private static List<Flashcard> createFlashcards() {
        List<Flashcard> cards = new ArrayList<>();
        
        cards.add(new ThaiConsonantFlashcard("ก", "kɔɔ kày (chicken)"));
        cards.add(new ThaiConsonantFlashcard("ข", "khɔ̌ɔ khày (egg)"));
        cards.add(new ThaiConsonantFlashcard("ค", "khɔɔ khwaay (buffalo)"));
        cards.add(new ThaiConsonantFlashcard("ง", "ŋɔɔ ŋuu (snake)"));
        cards.add(new ThaiConsonantFlashcard("จ", "cɔɔ caan (plate)"));
        cards.add(new ThaiConsonantFlashcard("ฉ", "chɔ̌ɔ chìŋ (cymbals)"));
        cards.add(new ThaiConsonantFlashcard("ช", "chɔɔ cháaŋ (elephant)"));
        cards.add(new ThaiConsonantFlashcard("ซ", "sɔɔ sôo (chain)"));
        cards.add(new ThaiConsonantFlashcard("ด", "dɔɔ dèk (child)"));
        cards.add(new ThaiConsonantFlashcard("ต", "tɔɔ tào (turtle)"));
        
        
        return cards;
    }

    // --- For Task 3: Create 3 generic flashcards ---
    private static List<Flashcard> genericFlashcards = createGenericFlashcards();
    
    private static List<Flashcard> createGenericFlashcards() {
        List<Flashcard> cards = new ArrayList<>();
        
        // As requested, country-capital pairs
        cards.add(new Flashcard("Japan", "Tokyo"));
        cards.add(new Flashcard("France", "Paris"));
        cards.add(new Flashcard("Thailand", "Bangkok"));
        
        return cards;
    }
    // --- END OF NEW CODE ---


    // This is your old code, which is also outside main()
    static class LoginRequest {
        String username;
        String password;
    }

    static class LoginResponse {
        String token;
        String username;
        LoginResponse(String t, String u) {
            token = t;
            username = u;
        }
    }

    static class ErrorResponse {
        String error;
        ErrorResponse(String e) {
            error = e;
        }
    }
}