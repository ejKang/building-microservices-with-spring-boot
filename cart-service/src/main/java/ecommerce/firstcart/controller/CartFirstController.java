package ecommerce.firstcart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.cartservice.entity.Product;
import ecommerce.cartservice.repository.ProductRepository;

@RestController
@RequestMapping("/cart")
public class CartFirstController {

	@Autowired
	ProductRepository productRepository;
	
	@GetMapping("/getProducts")
	List<Product> getCartProduct() {
		return productRepository.findAll();
	}
	
	@GetMapping("/deleteOne/{id}")
	void deleteProduct(@PathVariable Long id) {
		productRepository.deleteById(id);
	}
	
	@GetMapping("/deleteAll")
	void deleteProducts() {
		productRepository.deleteAll();
	}
	
	@GetMapping("/info")
	String getInfo() {
		return "cart microservice";
	}
}
	
