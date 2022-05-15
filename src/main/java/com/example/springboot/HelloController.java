package com.example.springboot;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class HelloController {


	@Autowired
	private ProductService personService;

	@RequestMapping(value = "product/{id}/add", method = RequestMethod.PUT, produces = "application/json")
	public void add(@PathVariable int id, @RequestBody Product product) {
		personService.addProduct(product);
	}


	@RequestMapping(value = "product/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Product getProduct(@PathVariable int id) {
		return personService.getProduct(id);
	}

	@RequestMapping(value = "checkout", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody void checkout(@RequestBody String message) throws IOException {
		 personService.checkout(message);
	}

}
