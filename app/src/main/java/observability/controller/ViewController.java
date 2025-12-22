package observability.controller;

import observability.model.Product;
import observability.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class ViewController {

    @Autowired
    private ProductService productService;


    /**
     * Page d'accueil
     */
    @GetMapping
    public String home() {
        return "index";
    }

    /**
     * Page de connexion
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    /**
     * Page d'inscription
     */
    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    /**
     * Page de gestion des produits
     */
    @GetMapping("/products")
    public String productsPage(Model model) {
        try {
            List<Product> products = productService.findAll();
            model.addAttribute("products", products);
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors du chargement des produits");
        }
        return "products";
    }

    /**
     * Page pour ajouter un nouveau produit
     */
    @GetMapping("/products/add")
    public String addProductPage() {
        return "add-product";
    }

    /**
     * Page pour éditer un produit
     */
    @GetMapping("/products/{id}/edit")
    public String editProductPage(@PathVariable String id, Model model) {
        try {
            Product product = productService.findById(id);
            model.addAttribute("product", product);
        } catch (Exception e) {
            model.addAttribute("error", "Produit non trouvé");
            return "error";
        }
        return "edit-product";
    }

    /**
     * Page de détail d'un produit
     */
    @GetMapping("/products/{id}")
    public String productDetailPage(@PathVariable String id, Model model) {
        try {
            Product product = productService.findById(id);
            model.addAttribute("product", product);
        } catch (Exception e) {
            model.addAttribute("error", "Produit non trouvé");
            return "error";
        }
        return "product-detail";
    }

    /**
     * Page des statistiques (produit le plus cher)
     */
    @GetMapping("/stats")
    public String statsPage(Model model) {
        try {
            Product mostExpensive = productService.findMostExpensiveProduct();
            model.addAttribute("mostExpensive", mostExpensive);
        } catch (Exception e) {
            model.addAttribute("error", "Aucune statistique disponible");
        }
        return "stats";
    }
}

