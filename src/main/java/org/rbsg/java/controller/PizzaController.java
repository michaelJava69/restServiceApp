package org.rbsg.java.controller;

import org.rbsg.java.model.Pizza;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class PizzaController {

	
	 @RequestMapping(value="/pizzavalley/{pizzaName}", method = RequestMethod.GET)
	    public String getPizza(@PathVariable String pizzaName, ModelMap model) {
	  
	        Pizza pizza = new Pizza(pizzaName);
	        model.addAttribute("pizza", pizza);
	  
	        return "pizza";
	  
	    }
	 
}
