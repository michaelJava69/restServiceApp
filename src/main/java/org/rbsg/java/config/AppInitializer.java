package org.rbsg.java.config;

 

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
 
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
 
public class AppInitializer implements WebApplicationInitializer {
   
	
	/**
	 * We are going to use the standard dispatcher servlet that comes with Spring
	 * This is not a pure JSON application. It combines JSON and other rendorers 
	 * 
	 * In restServiceApp2 I will use the SpringMVCFramework and create my own Dispatcher Servlet using 
	 * <mvc:annotation-driven/> and a not so empty web.xml
	 *
	 * This class will be called by Tomcat on startup and will then register the ViewConfig class
	 * which will register View Resolvers 
	 * 
	 * The Server will then pick up on the anontations and initialize the Country/Pizza & Primes Controllers
	 * 
	 * 
	 * RequestMappingHandlerMapping  - Mapped "{[/country/{id}],methods=[GET],params=[],headers=[],
	 * consumes=[],produces=[application/json],custom=[]}" onto public org.rbsg.java.bean.Country org.rbsg.java.controller.CountryController.getCountryById(int)
	 * 
     * RequestMappingHandlerMapping  - Mapped "{[/countries],methods=[GET],params=[],headers=[],consumes=[],produces=[application/json],custom=[]}" onto public 
     * java.util.List<org.rbsg.java.bean.Country> org.rbsg.java.controller.CountryController.getCountries()
     * 
     * RequestMappingHandlerMapping  - Mapped "{[/pizzavalley/{pizzaName}],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}" 
     * onto public java.lang.String org.rbsg.java.controller.PizzaController.getPizza(java.lang.String,org.springframework.ui.ModelMap)
     * 
     * RequestMappingHandlerMapping  - Mapped "{[/primes/{upperLimit}],methods=[GET],params=[],headers=[],consumes=[],produces=[],custom=[]}" 
     * onto public java.lang.String org.rbsg.java.controller.PrimesController.getPrimeNumbers(java.lang.Integer,org.springframework.ui.ModelMap)
	 * 
	 * The server then proceeds to initialize the controllers
	 */
    public void onStartup(ServletContext container) throws ServletException {
         
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(ViewConfig.class);
        ctx.setServletContext(container);
 
       ServletRegistration.Dynamic servlet = container.addServlet(
                "dispatcher", new DispatcherServlet(ctx));
 
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");
    }
 
}

