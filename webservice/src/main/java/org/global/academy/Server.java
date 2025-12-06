package org.global.academy;

import static spark.Spark.*;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collections; // Added for shuffling
import java.util.List;
import java.util.Random;

public class Server {
    
    private static List<Flashcard> flashcards;

    public static void main(String[] args) {
        // 1. Initialize Thai Data
        flashcards = createThaiFlashcards();
        
        // 2. Configure Spark
        port(8080);
        staticFiles.location("/public");
        
        // 3. CORS & Encoding Headers
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
            response.type("application/json; charset=utf-8");
        });

        Gson gson = new Gson();
        Random random = new Random();

        // --- ROUTES ---

        // NEW: Returns ALL cards so we can make a grid
        get("/all-flashcards", (req, res) -> {
            res.type("application/json; charset=utf-8");
            // Return a copy so we don't mess up the original order on server
            List<Flashcard> shuffled = new ArrayList<>(flashcards);
            Collections.shuffle(shuffled); 
            return gson.toJson(shuffled);
        });

        // OLD: Still here if you need single random card
        get("/flashcard", (req, res) -> {
            int randomIndex = random.nextInt(flashcards.size());
            Flashcard randomCard = flashcards.get(randomIndex);
            res.type("application/json; charset=utf-8");
            return gson.toJson(randomCard);
        });

        post("/login", (req, res) -> {
            LoginRequest lr = gson.fromJson(req.body(), LoginRequest.class);
            if ("Alice".equalsIgnoreCase(lr.username) && "Secret".equals(lr.password)) {
                res.type("application/json");
                return gson.toJson(new LoginResponse("token-alice-123", "Alice"));
            } else {
                res.status(401);
                res.type("application/json");
                return gson.toJson(new ErrorResponse("Authentication Failed"));
            }
        });
        
        System.out.println("Koro-sensei is ready on http://localhost:8080");
    }

    // --- CLASSES ---
    static class LoginRequest { String username; String password; }
    static class LoginResponse { String token; String username; LoginResponse(String t, String u) { token = t; username = u; }}
    static class ErrorResponse { String error; ErrorResponse(String e) { error = e; }}

    // --- DATA ---
    private static List<Flashcard> createThaiFlashcards() {
        List<Flashcard> cards = new ArrayList<>();
        // MID CLASS
        cards.add(new ThaiConsonantFlashcard("ก", "Gor Gai (Chicken)", "First letter. Mid Class. Sound: G.", "Mid"));
        cards.add(new ThaiConsonantFlashcard("จ", "Jor Jaan (Plate)", "Mid Class. Sound: J.", "Mid"));
        cards.add(new ThaiConsonantFlashcard("ด", "Dor Dek (Child)", "Mid Class. Sound: D.", "Mid"));
        cards.add(new ThaiConsonantFlashcard("ต", "Tor Tao (Turtle)", "Mid Class. Sound: T.", "Mid"));
        cards.add(new ThaiConsonantFlashcard("ฎ", "Dor Cha-da (Headdress)", "Mid Class. Sound: D.", "Mid"));
        cards.add(new ThaiConsonantFlashcard("ฏ", "Tor Pa-tak (Goad)", "Mid Class. Sound: T.", "Mid"));
        cards.add(new ThaiConsonantFlashcard("บ", "Bor Bai-mai (Leaf)", "Mid Class. Sound: B.", "Mid"));
        cards.add(new ThaiConsonantFlashcard("ป", "Por Pla (Fish)", "Mid Class. Sound: P.", "Mid"));
        cards.add(new ThaiConsonantFlashcard("อ", "Or Ang (Basin)", "Mid Class. Sound: O (Silent initial).", "Mid"));

        // HIGH CLASS
        cards.add(new ThaiConsonantFlashcard("ข", "Khor Khai (Egg)", "High Class. Sound: K (rising).", "High"));
        cards.add(new ThaiConsonantFlashcard("ฃ", "Khor Khuad (Bottle)", "High Class. Obsolete.", "High"));
        cards.add(new ThaiConsonantFlashcard("ฉ", "Chor Ching (Cymbals)", "High Class. Sound: Ch.", "High"));
        cards.add(new ThaiConsonantFlashcard("ฐ", "Thor Than (Pedestal)", "High Class. Sound: Th.", "High"));
        cards.add(new ThaiConsonantFlashcard("ถ", "Thor Thung (Sack)", "High Class. Sound: Th.", "High"));
        cards.add(new ThaiConsonantFlashcard("ผ", "Phor Phueng (Bee)", "High Class. Sound: Ph.", "High"));
        cards.add(new ThaiConsonantFlashcard("ฝ", "For Fa (Lid)", "High Class. Sound: F.", "High"));
        cards.add(new ThaiConsonantFlashcard("ศ", "Sor Sa-la (Pavilion)", "High Class. Sound: S.", "High"));
        cards.add(new ThaiConsonantFlashcard("ษ", "Sor Rue-si (Hermit)", "High Class. Sound: S.", "High"));
        cards.add(new ThaiConsonantFlashcard("ส", "Sor Suea (Tiger)", "High Class. Sound: S.", "High"));
        cards.add(new ThaiConsonantFlashcard("ห", "Hor Hip (Chest)", "High Class. Sound: H.", "High"));

        // LOW CLASS
        cards.add(new ThaiConsonantFlashcard("ค", "Khor Khwai (Buffalo)", "Low Class. Sound: K.", "Low"));
        cards.add(new ThaiConsonantFlashcard("ฅ", "Khor Khon (Person)", "Low Class. Obsolete.", "Low"));
        cards.add(new ThaiConsonantFlashcard("ฆ", "Khor Ra-khang (Bell)", "Low Class. Sound: K.", "Low"));
        cards.add(new ThaiConsonantFlashcard("ง", "Ngor Ngu (Snake)", "Low Class. Sound: Ng.", "Low"));
        cards.add(new ThaiConsonantFlashcard("ช", "Chor Chang (Elephant)", "Low Class. Sound: Ch.", "Low"));
        cards.add(new ThaiConsonantFlashcard("ซ", "Sor So (Chain)", "Low Class. Sound: S.", "Low"));
        cards.add(new ThaiConsonantFlashcard("ฌ", "Chor Cher (Tree)", "Low Class. Sound: Ch.", "Low"));
        cards.add(new ThaiConsonantFlashcard("ญ", "Yor Ying (Woman)", "Low Class. Sound: Y.", "Low"));
        cards.add(new ThaiConsonantFlashcard("ฑ", "Thor Mon-tho (Dancer)", "Low Class. Sound: Th.", "Low"));
        cards.add(new ThaiConsonantFlashcard("ฒ", "Thor Phu-thao (Elder)", "Low Class. Sound: Th.", "Low"));
        cards.add(new ThaiConsonantFlashcard("ณ", "Nor Nen (Novice Monk)", "Low Class. Sound: N.", "Low"));
        cards.add(new ThaiConsonantFlashcard("ท", "Thor Tha-han (Soldier)", "Low Class. Sound: Th.", "Low"));
        cards.add(new ThaiConsonantFlashcard("ธ", "Thor Thong (Flag)", "Low Class. Sound: Th.", "Low"));
        cards.add(new ThaiConsonantFlashcard("น", "Nor Nu (Mouse)", "Low Class. Sound: N.", "Low"));
        cards.add(new ThaiConsonantFlashcard("พ", "Phor Phan (Tray)", "Low Class. Sound: P.", "Low"));
        cards.add(new ThaiConsonantFlashcard("ฟ", "For Fan (Teeth)", "Low Class. Sound: F.", "Low"));
        cards.add(new ThaiConsonantFlashcard("ภ", "Phor Sam-phao (Sailboat)", "Low Class. Sound: P.", "Low"));
        cards.add(new ThaiConsonantFlashcard("ม", "Mor Ma (Horse)", "Low Class. Sound: M.", "Low"));
        cards.add(new ThaiConsonantFlashcard("ย", "Yor Yak (Giant)", "Low Class. Sound: Y.", "Low"));
        cards.add(new ThaiConsonantFlashcard("ร", "Ror Rua (Boat)", "Low Class. Sound: R.", "Low"));
        cards.add(new ThaiConsonantFlashcard("ล", "Lor Ling (Monkey)", "Low Class. Sound: L.", "Low"));
        cards.add(new ThaiConsonantFlashcard("ว", "Wor Waen (Ring)", "Low Class. Sound: W.", "Low"));
        cards.add(new ThaiConsonantFlashcard("ฬ", "Lor Chu-la (Kite)", "Low Class. Sound: L.", "Low"));
        cards.add(new ThaiConsonantFlashcard("ฮ", "Hor Nok-huk (Owl)", "Low Class. Sound: H.", "Low"));
        return cards;
    }
}