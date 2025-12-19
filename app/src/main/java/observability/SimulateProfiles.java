package observability;


import observability.context.UserContext;
import observability.model.Product;
import observability.model.User;
import observability.repository.UserRepository;
import observability.service.LogProcessingService;
import observability.service.ProductService;
import observability.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
public class SimulateProfiles implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SimulateProfiles.class);
    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private LogProcessingService logProcessingService;

    @Override
    public void run(String... args) throws Exception {
        System.err.println("Début de la simulation");

        User u1 = userService.login("t1@mail.com", "admin");
        User u2 = userService.login("t2@mail.com", "admin");
        User u3 = userService.login("t3@mail.com", "admin");
        User u4 = userService.login("t4@mail.com", "admin");
        User u5 = userService.login("t5@mail.com", "admin");

        //Beaucoup de lectures
        simulateUser(u1.getId(), ()->{
            productService.findAll();
            productService.findById("69359db0ce444f71963d7354");
            productService.findById("69359dbdce444f71963d7355");
            productService.findAll();
            productService.findAll();
        });

        //Beaucoup d'écritures
        simulateUser(u2.getId(), ()->{
            productService.findAll();
            productService.insert(new Product("Coussin", 6.99f, "01-12-2025"));
            productService.insert(new Product("Couettes", 25.99f, "01-12-2025"));
            productService.insert(new Product("Plaid", 9.99f, "01-12-2025"));
            productService.insert(new Product("Matelas", 100.99f, "01-12-2025"));
        });


        //Les plus chères
        simulateUser(u3.getId(), ()->{
            productService.findMostExpensiveProduct();
            productService.findMostExpensiveProduct();
            productService.findMostExpensiveProduct();
            productService.findMostExpensiveProduct();
        });

        //Beaucoup de lectures avec quelques écritures
        simulateUser(u4.getId(), ()->{
            productService.findAll();
            productService.insert(new Product("Emmental", 2.99f, "01-12-2025"));
            productService.insert(new Product("St-Felicien", 5.99f, "01-12-2025"));
            productService.findById("69359db0ce444f71963d7354");
            productService.findById("69359dbdce444f71963d7355");
            productService.findAll();
            productService.findAll();
        });

        System.err.println("Fin de la simulation\nDébut du parsing");
        logProcessingService.parse();
    }

    private void simulateUser(String userId, Runnable actions) {
        try {
            System.out.println("--- Simulation actions de " + userId + " ---");
            UserContext.setCurrentUser(userId); // 1. On connecte l'user

            // On exécute ses actions (ex: 20 scénarios comme demandé Q4)
            actions.run();

        } catch (Exception e) {
            System.err.println("Erreur simulation " + userId + ": " + e.getMessage());
        } finally {
            UserContext.clear(); // 2. On nettoie (Très important !)
        }
    }}
