package ecommerce.firstproduct.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.productservice.entity.Product;
import ecommerce.productservice.repository.ProductRepository;

@RestController
@RequestMapping("/first/product")
public class ProductFirstController {
    
    @Autowired
    private ProductRepository repo;

     @PostMapping("/addOne")
     public Product addProduct(@RequestBody Product product	) {
    	 return repo.save(product);
     }
     
     @PostMapping("/addList")
     public List<Product> addProductList (@RequestBody List<Product> products) {
    	 return repo.saveAll(products);
     }
     
     @GetMapping("/getAll")
     public List<Product> getAllProduct() {
    	 return repo.findAll();
     }
    
}
