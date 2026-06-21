import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class populate_database {
    private static final Random random = new Random();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:travel_agency.db");
            System.out.println("Connected to database");

            // Create tables
            createTables(conn);

            // Populate with tons of data
            generateCustomers(conn, 500);
            generateDestinations(conn, 100);
            generateBookings(conn, 1000);

            conn.close();
            System.out.println("Database populated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createTables(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        // Create customers table
        stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS customers (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT NOT NULL, " +
                        "email TEXT NOT NULL, " +
                        "phone TEXT NOT NULL, " +
                        "address TEXT NOT NULL)");

        // Create destinations table
        stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS destinations (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT NOT NULL, " +
                        "country TEXT NOT NULL, " +
                        "description TEXT NOT NULL, " +
                        "price_per_person REAL NOT NULL)");

        // Create bookings table
        stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS bookings (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "customer_id INTEGER NOT NULL, " +
                        "destination_id INTEGER NOT NULL, " +
                        "booking_date TEXT NOT NULL, " +
                        "travel_date TEXT NOT NULL, " +
                        "number_of_people INTEGER NOT NULL, " +
                        "total_price REAL NOT NULL, " +
                        "status TEXT NOT NULL, " +
                        "FOREIGN KEY (customer_id) REFERENCES customers(id), " +
                        "FOREIGN KEY (destination_id) REFERENCES destinations(id))");

        System.out.println("Tables created successfully");
    }

    private static final String[] FIRST_NAMES = {
            "John", "Jane", "Michael", "Emma", "David", "Olivia", "Daniel", "Sophia", "Matthew", "Ava",
            "James", "Isabella", "Joseph", "Mia", "Andrew", "Charlotte", "Thomas", "Amelia", "Benjamin", "Harper",
            "Alexander", "Evelyn", "William", "Abigail", "Christopher", "Emily", "Joshua", "Elizabeth", "Nicholas",
            "Sofia",
            "Ethan", "Avery", "Anthony", "Ella", "Ryan", "Scarlett", "Jacob", "Grace", "Samuel", "Lily",
            "George", "Lucas", "Mason", "Logan", "Elijah", "Oliver", "Aiden", "Caden", "Owen", "Henry",
            "Liam", "Noah", "Sebastian", "Theodore", "Jack", "Leo", "Hudson", "Jasper", "Asher", "Wyatt"
    };

    private static final String[] LAST_NAMES = {
            "Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor",
            "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez", "Robinson",
            "Clark", "Rodriguez", "Lewis", "Lee", "Walker", "Hall", "Allen", "Young", "Hernandez", "King",
            "Wright", "Lopez", "Hill", "Scott", "Green", "Adams", "Baker", "Gonzalez", "Nelson", "Carter",
            "Mitchell", "Perez", "Roberts", "Turner", "Phillips", "Campbell", "Parker", "Evans", "Edwards", "Collins"
    };

    private static final String[] CITIES = {
            "New York", "Los Angeles", "Chicago", "Houston", "Phoenix", "Philadelphia", "San Antonio", "San Diego",
            "Dallas", "San Jose", "Austin", "Jacksonville", "Fort Worth", "Columbus", "San Francisco", "Charlotte",
            "Indianapolis", "Seattle", "Denver", "Boston", "El Paso", "Detroit", "Nashville", "Portland", "Memphis",
            "Oklahoma City", "Las Vegas", "Louisville", "Baltimore", "Milwaukee", "Albuquerque", "Tucson", "Fresno",
            "Mesa", "Sacramento", "Atlanta", "Kansas City", "Colorado Springs", "Miami", "Raleigh", "Omaha"
    };

    private static final String[] COUNTRIES = {
            "Greece", "Italy", "Spain", "France", "Germany", "United Kingdom", "Portugal", "Netherlands",
            "Switzerland", "Austria", "Belgium", "Sweden", "Norway", "Denmark", "Finland", "Poland",
            "Czech Republic", "Hungary", "Croatia", "Turkey", "Egypt", "Morocco", "Tunisia", "South Africa",
            "Kenya", "Tanzania", "UAE", "Jordan", "Israel", "India", "Thailand", "Vietnam", "Indonesia",
            "Malaysia", "Singapore", "Philippines", "Japan", "South Korea", "China", "Australia", "New Zealand",
            "Canada", "Mexico", "Brazil", "Argentina", "Chile", "Peru", "Colombia", "Costa Rica", "Cuba"
    };

    private static final String[] DESTINATION_NAMES = {
            "Santorini", "Mykonos", "Athens", "Crete", "Rhodes", "Corfu", "Zakynthos", "Rome", "Venice",
            "Florence", "Milan", "Naples", "Barcelona", "Madrid", "Seville", "Valencia", "Paris", "Nice",
            "Lyon", "Marseille", "Berlin", "Munich", "Hamburg", "Frankfurt", "London", "Edinburgh",
            "Manchester", "Liverpool", "Lisbon", "Porto", "Amsterdam", "Rotterdam", "Vienna", "Salzburg",
            "Zurich", "Geneva", "Brussels", "Antwerp", "Stockholm", "Oslo", "Copenhagen", "Helsinki",
            "Warsaw", "Krakow", "Prague", "Budapest", "Zagreb", "Dubrovnik", "Istanbul", "Antalya",
            "Cairo", "Luxor", "Marrakech", "Casablanca", "Tunis", "Cape Town", "Johannesburg", "Nairobi",
            "Mombasa", "Dar es Salaam", "Dubai", "Abu Dhabi", "Amman", "Petra", "Jerusalem", "Tel Aviv",
            "Mumbai", "Delhi", "Goa", "Bangkok", "Phuket", "Chiang Mai", "Ho Chi Minh City", "Hanoi",
            "Bali", "Jakarta", "Kuala Lumpur", "Singapore", "Manila", "Cebu", "Tokyo", "Kyoto", "Osaka",
            "Seoul", "Busan", "Beijing", "Shanghai", "Hong Kong", "Sydney", "Melbourne", "Auckland",
            "Queenstown", "Toronto", "Vancouver", "Montreal", "Mexico City", "Cancun", "Rio de Janeiro",
            "Sao Paulo", "Buenos Aires", "Santiago", "Lima", "Bogota", "San Jose", "Havana"
    };

    private static void generateCustomers(Connection conn, int count) throws SQLException {
        System.out.println("Generating " + count + " customers...");

        String sql = "INSERT INTO customers (name, email, phone, address) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        for (int i = 0; i < count; i++) {
            String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
            String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
            String name = firstName + " " + lastName;

            String email = firstName.toLowerCase() + "." + lastName.toLowerCase() +
                    "@" + (random.nextBoolean() ? "gmail.com" : "yahoo.com");

            String phone = String.format("+1-%03d-%03d-%04d",
                    200 + random.nextInt(800),
                    100 + random.nextInt(900),
                    1000 + random.nextInt(9000));

            String address = (100 + random.nextInt(9900)) + " " +
                    (random.nextBoolean() ? "Main St" : "Oak Ave") + ", " +
                    CITIES[random.nextInt(CITIES.length)] + ", " +
                    (random.nextBoolean() ? "CA" : "NY") + " " +
                    (10000 + random.nextInt(90000));

            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.setString(4, address);
            pstmt.executeUpdate();

            if (i % 100 == 0) {
                System.out.println("  Generated " + i + " customers...");
            }
        }
        pstmt.close();
    }

    private static void generateDestinations(Connection conn, int count) throws SQLException {
        System.out.println("Generating " + count + " destinations...");

        String sql = "INSERT INTO destinations (name, country, description, price_per_person) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        Set<String> usedCombinations = new HashSet<>();

        for (int i = 0; i < count && i < DESTINATION_NAMES.length; i++) {
            String destination = DESTINATION_NAMES[i];
            String country = COUNTRIES[random.nextInt(COUNTRIES.length)];

            // Make sure we don't duplicate destination-country combinations
            String combo = destination + "-" + country;
            while (usedCombinations.contains(combo) && usedCombinations.size() < count) {
                country = COUNTRIES[random.nextInt(COUNTRIES.length)];
                combo = destination + "-" + country;
            }
            usedCombinations.add(combo);

            String[] descriptions = {
                    "A stunning destination with beautiful architecture and rich history",
                    "Famous for its beaches, nightlife, and cultural attractions",
                    "Perfect blend of modern amenities and traditional charm",
                    "Breathtaking natural beauty and world-class hospitality",
                    "An unforgettable experience with amazing food and friendly locals",
                    "Historic sites, museums, and vibrant local markets",
                    "Paradise for adventure seekers and nature lovers",
                    "Luxury resorts, pristine beaches, and crystal-clear waters",
                    "Cultural heritage site with ancient ruins and monuments",
                    "Modern city with excellent shopping and dining options"
            };

            String description = descriptions[random.nextInt(descriptions.length)];
            double price = 299 + random.nextInt(3000) + random.nextDouble();

            pstmt.setString(1, destination);
            pstmt.setString(2, country);
            pstmt.setString(3, description);
            pstmt.setDouble(4, Math.round(price * 100.0) / 100.0);
            pstmt.executeUpdate();
        }
        pstmt.close();
    }

    private static void generateBookings(Connection conn, int count) throws SQLException {
        System.out.println("Generating " + count + " bookings...");

        // Get customer and destination counts
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM customers");
        int customerCount = rs.getInt(1);

        rs = stmt.executeQuery("SELECT COUNT(*) FROM destinations");
        int destinationCount = rs.getInt(1);

        // Get all destination prices
        Map<Integer, Double> destinationPrices = new HashMap<>();
        rs = stmt.executeQuery("SELECT id, price_per_person FROM destinations");
        while (rs.next()) {
            destinationPrices.put(rs.getInt("id"), rs.getDouble("price_per_person"));
        }

        String sql = "INSERT INTO bookings (customer_id, destination_id, booking_date, travel_date, " +
                "number_of_people, total_price, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        String[] statuses = { "Confirmed", "Pending", "Completed", "Cancelled", "In Progress" };
        LocalDate today = LocalDate.now();

        for (int i = 0; i < count; i++) {
            int customerId = 1 + random.nextInt(customerCount);
            int destinationId = 1 + random.nextInt(destinationCount);

            // Booking date: between 180 days ago and today
            LocalDate bookingDate = today.minusDays(random.nextInt(180));

            // Travel date: between booking date and 365 days from booking date
            LocalDate travelDate = bookingDate.plusDays(1 + random.nextInt(365));

            int numberOfPeople = 1 + random.nextInt(6);
            double basePrice = destinationPrices.get(destinationId);
            double totalPrice = basePrice * numberOfPeople;

            String status = statuses[random.nextInt(statuses.length)];

            // Completed bookings should have travel dates in the past
            if (status.equals("Completed") && travelDate.isAfter(today)) {
                travelDate = today.minusDays(1 + random.nextInt(90));
            }

            // Cancelled bookings can be any date
            // Pending and Confirmed should have future travel dates
            if ((status.equals("Pending") || status.equals("Confirmed")) && travelDate.isBefore(today)) {
                travelDate = today.plusDays(1 + random.nextInt(180));
            }

            pstmt.setInt(1, customerId);
            pstmt.setInt(2, destinationId);
            pstmt.setString(3, bookingDate.format(formatter));
            pstmt.setString(4, travelDate.format(formatter));
            pstmt.setInt(5, numberOfPeople);
            pstmt.setDouble(6, Math.round(totalPrice * 100.0) / 100.0);
            pstmt.setString(7, status);
            pstmt.executeUpdate();

            if (i % 200 == 0) {
                System.out.println("  Generated " + i + " bookings...");
            }
        }
        pstmt.close();
    }
}