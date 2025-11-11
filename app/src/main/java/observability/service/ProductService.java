package observability.service;


import observability.exceptions.ResourceConflictException;
import observability.exceptions.ResourceNotFoundException;
import observability.model.Product;
import observability.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(String id) {
        return productRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Produit non trouvé avec l'id : "+id)
        );
    }

    public Product insert(Product product) {
        Optional<Product> maybeProduct = productRepository.findById(product.getId());
        if (maybeProduct.isPresent()) {
            throw new ResourceConflictException("Un produit avec cet identifiant existe déjà");
        }
        return productRepository.insert(product);
    }

    public void delete(String id) {
        //Pour lever une la même exception si l'objet n'existe pas
        findById(id);
        productRepository.deleteById(id);
    }

    public Product update(Product product) {
        //Pour lever une la même exception si l'objet n'existe pas
        findById(product.getId());
        return productRepository.save(product);
    }
}
