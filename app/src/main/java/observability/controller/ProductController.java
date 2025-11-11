package observability.controller;


import observability.model.Product;
import observability.service.ProductService;
import observability.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/fetch")
    public ResponseEntity<Product> getProductById(@RequestParam String id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findById(id));
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteProductById(@RequestParam String id) {
        productService.delete(id);
        return ResponseEntity.ok("Produit id:"+id+" supprimé");
    }

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product addedProduct = productService.insert(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct);
    }

    
}
