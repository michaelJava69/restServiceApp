package org.rbsg.java.controller;

import org.apache.log4j.Logger;

import org.rbsg.java.model.Pizza;
import org.rbsg.java.model.PrimesResponse;
import org.rbsg.java.service.PrimeNumberService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/***
 * @author micha
 * 
 * In the restServiceApp2 I will use Spring 4.0 and will  benot providing any view information
 * in springrest-servlet.xml as we do in Spring MVC. If we need to directly get resource from controller,
 * we need to return @ResponseBody as per Spring 3 but with Spring 4, we can use @RestController for that.
 * In spring 4.0, we can use @RestController which is combination of @Controller + @ResponseBody.
 *
 * Summary : for content negotiator/viewresolver to work this controller can not be rest or return a responseBody
 */
 
 
@Controller  
public class PrimesController {

	final static Logger logger = Logger.getLogger(PrimesController.class);

	    /**
         * Constructor for the PrimesController class
         *
  	     * @param upperLimit - Prime Number upper limit
         * @param model      - Container map that will containing retrieved data
         * 
         * "primeResponse is used explicitly here to indicate to the browser that something
         * needs to be rendered.
         * 
         * 
         */
	
	     @RequestMapping(value = "/primes/{upperLimit}", method = RequestMethod.GET)
	     // @ResponseBody
	     @Cacheable(value = "primes", key = "#upperLimit")
	     public String getPrimeNumbers(@PathVariable final Integer upperLimit, ModelMap model) {
	   	
	     PrimeNumberService primeService = new PrimeNumberService();

	   	 // Ehcache not woking so my custom cache
	   	 final PrimesResponse primesResponse; 
	   	 
	   	 
	   	 // CacheManager.getInstance().addCache("xyz"); // creates a cache called xyz.
	        
	   	 logger.info(" **** Test : Inside PrimeController  ***"); 
	   	  
	   	 /**
	   	  * This is where the caching logic resides. I check cache and if not populated i carry out the 
	   	  * PrimesNumber task. If it is I get it from the Cache.
	   	  * 
	   	  * To emphasis the wait time when not getting from cache I have deliberately delayed by 2 secs 
	   	  * the call outside of the cache
	   	  * 
	   	  */
	   	 Cache xyz = CacheManager.getInstance().getCache("primes");
	        //Check
	   	 if (xyz.get(upperLimit)==null) {
	   	       
	   		 logger.info( "Getting data outside of the cachce.********....." );
	   		
	   	     primesResponse = new PrimesResponse(upperLimit, primeService.getPrimeNumbers(upperLimit));
	   	     xyz.put(new Element(upperLimit, primesResponse));
	   	 }else{
	   		 
	   		 logger.info("Test : Inside my Cache.********");
	   		 
	   		 Element e = xyz.get(upperLimit);
	   		 primesResponse =  (PrimesResponse)   e.getObjectValue();
	   	 }
	         
	         
	         
	         
	         model.addAttribute("primesResponse", primesResponse);
	         return "primesResponse";
	     }
}
