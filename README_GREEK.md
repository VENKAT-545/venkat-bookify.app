# 🛩️ Bookify – Εφαρμογή Διαχείρισης Ταξιδιωτικού Γραφείου (JavaFX)

<p align="center">
  <img src="screenshots/bookify.gif" alt="Ζωντανή επίδειξη Bookify" width="800"/>
</p>

To Bookify είναι ένα **μοντέρνο** desktop εργαλείο για πελάτες, προορισμούς, κρατήσεις και αναφορές.

---

## 1. Γρήγορη Εκκίνηση

### Απαιτήσεις

- **JDK 11+** (δοκιμασμένο 11 & 17)
- **Apache Maven 3.9+**

### Εκτέλεση

```bash
# κλωνοποίηση
git clone https://github.com/PanagiotisPitsikoulis/bookify.git
cd bookify

# dev mode
mvn clean javafx:run

# παραγωγή – δημιουργία jar
mvn clean package
java -jar target/travel-agency-1.0-SNAPSHOT.jar --generate-data  # για δείγμα δεδομένων
```

Demo κωδικοί: `admin / admin`

---

## 2. Χρήση

1. **Login** – εισάγετε τα στοιχεία.
2. **Κεντρικό Παράθυρο** – tabs:
   - **Dashboard** – ζωντανά δεδομένα
   - **Customers** – πελάτες (Νέος/Επεξεργασία/Διαγραφή)
   - **Destinations** – προορισμοί
   - **Bookings** – κρατήσεις
   - **Reports** – εξαγωγή CSV

Κουμπιά: **Save** (μπλε), **Reset** (γκρι), **Delete** (κόκκινο).

---

## 3. Αρχιτεκτονική MVC (σε 2 γραμμές)

- **Model** – POJO & DAO που μιλάνε με SQLite.
- **View** – FXML + CSS. **Controller** – Java κώδικας που συνδέει τα δύο.

<p align="center">
  <img src="screenshots/bookify_diagram.png" alt="Bookify Architecture Diagram" width="700"/>
</p>

---

## 4. Λειτουργίες

- Ελαφρύ Flat-Blue θέμα
- Φίλτρο λίστας σε πραγματικό χρόνο
- Στατιστικά & γραφήματα
- Cache εικόνων
- Ενσωματωμένη βάση **SQLite**

---

## 5. Βασικοί Αλγόριθμοι

| Θέμα                       | Λύση                            |
| -------------------------- | ------------------------------- |
| Περιορισμός λαθών σύνδεσης | 3 προσπάθειες → κλείδωμα 30"    |
| Uniq IDs                   | `AtomicInteger`                 |
| Αναζήτηση                  | `FilteredList` _(O(n))_         |
| KPIs                       | μονό πέρασμα _(O(n))_           |
| Διαθεσιμότητα              | έλεγχος επικαλύψεων ημερομηνιών |

---

## 6. Στιγμιότυπα

| Οθόνη        | Εικόνα                                        |
| ------------ | --------------------------------------------- |
| Login        | ![Login](screenshots/login.png)               |
| Demo GIF     | ![Demo](screenshots/bookify.gif)              |
| Dashboard    | ![Dashboard](screenshots/main.png)            |
| Customers    | ![Customers](screenshots/customers.png)       |
| Destinations | ![Destinations](screenshots/destinations.png) |
| Bookings     | ![Bookings](screenshots/bookings.png)         |
| Reports      | ![Reports](screenshots/reports.png)           |

---

## 7. Δομή Έργου (σύντομη)

```
src/main/java/com/bookify/app
  ├─ controller
  ├─ model
  ├─ dao
  ├─ database
  └─ utils
src/main/resources (FXML, εικόνες, styles.css)
pom.xml
```

---

## 8. Ιδέες για επέκταση αυτού του template

- Σύνδεση με SQL server ή MySQL
- Ρόλοι χρηστών / δικαιώματα
- Πολυγλωσσικό περιβάλλον (i18n)

---

### Credits

Δημιουργός **Panagiotis Pitsikoulis** — <https://panagiotispitsikoulis.gr>  
[LinkedIn](https://www.linkedin.com/in/panagiotis-pitsikoulis-47141733a/)

> Το Bookify δημιουργήθηκε για να δείξει πώς μπορεί να είναι μια σύγχρονη JavaFX εφαρμογή: καθαρή, μινιμαλιστική και φιλική προς τον χρήστη, εμπνευσμένη από τα καλύτερα UI της εποχής μας. Εστίασα σε καθαρή δομή κώδικα (MVC), ευκολία συντήρησης και μια ευχάριστη εμπειρία για τον προγραμματιστή, με στόχο το έργο να λειτουργεί τόσο ως πρακτικό εργαλείο όσο και ως εκπαιδευτικό παράδειγμα για φοιτητές και όσους ξεκινούν με JavaFX. Κάθε επιλογή σχεδιασμού—από το flat blue θέμα μέχρι το sidebar navigation—έγινε με γνώμονα την αισθητική, τη χρηστικότητα και τη σαφήνεια του κώδικα.
