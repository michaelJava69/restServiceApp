package org.rbsg.java.config;
 
import java.util.ArrayList;
import java.util.List;
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
 
import org.rbsg.java.model.Pizza;
import org.rbsg.java.model.PrimesResponse;
import org.rbsg.java.viewresolver.ExcelViewResolver;
import org.rbsg.java.viewresolver.JsonViewResolver;
import org.rbsg.java.viewresolver.Jaxb2MarshallingXmlViewResolver;
import org.rbsg.java.viewresolver.PdfViewResolver;
 

/**
 * This class is called by AppInitializer on Server start up
 * In restServiceApp2 I will use the SpringMVCFramework and create my own Dispatcher Servlet using 
 * <mvc:annotation-driven/>   and a not so empty web.xml
 *
 * For now this class will be called by Tomcat on startup and will then rtegister the ViewConfig class
 * which will initialize my View Resolvers 
 * 
 * This is not a pure JSON application. It combines JSON and other rendorers 
 * 
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "org.rbsg.java")
public class ViewConfig extends WebMvcConfigurerAdapter {
 
    /*
     * Configure ContentNegotiationManager
     */
	
	/**
	 * Use it every time you override a method for two benefits. Do it so that you can take advantage of the
	 * compiler checking to make sure you actually are overriding a method when you think you are. This way,
	 * if you make a common mistake of misspelling a method name or not correctly matching the parameters, 
	 * you will be warned that you method does not actually override as you think it does. Secondly, it makes
	 * your code easier to understand because it is more obvious when methods are overwritten.
     *
     * Additionally, in Java 1.6 you can use it to mark when a method implements an interface for the same 
     * benefits. I think it would be better to have a separate annotation (like @Implements), but it's 
     * better than nothing.
     * 
     *  Below note when there is no media type the default is HTML
     *  
     *  Example : http://localhost:8080/restServiceApp/primes/10  HTML
     *            http://localhost:8080/restServiceApp/primes/10.json  JSON
     *            http://localhost:8080/restServiceApp/country/4.json  JSON
     *            http://localhost:8080/restServiceApp/countries.json  JSON
     *            http://localhost:8080/restServiceApp/primes/10.pdf  PDF
     *            http://localhost:8080/restServiceApp/primes/10.xml   XML
     *            http://localhost:8080/restServiceApp/primes/10.xls   Excel
     *            
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.ignoreAcceptHeader(true).defaultContentType(
                MediaType.TEXT_HTML);
    }
 
    
    /***
     * ContentNegotiator determines from the URL what view to rendor
     * 
     * @param manager
     * @return  ViewResolver
     */
    /*
     * Configure ContentNegotiatingViewResolver
     */
    @Bean
    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setContentNegotiationManager(manager);
 
        // Define all possible view resolvers
        List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
 
        resolvers.add(jaxb2MarshallingXmlViewResolver());
        resolvers.add(jsonViewResolver());
        resolvers.add(jspViewResolver());
        resolvers.add(pdfViewResolver());
        resolvers.add(excelViewResolver());
         
        resolver.setViewResolvers(resolvers);
        return resolver;
    }
 
    /*
     * Configure View resolver to provide XML output Uses JAXB2 marshaller to
     * marshall/unmarshall POJO's (with JAXB annotations) to XML
     */
    @Bean
    public ViewResolver jaxb2MarshallingXmlViewResolver() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(Pizza.class,PrimesResponse.class);
         
        return new Jaxb2MarshallingXmlViewResolver(marshaller);
    }
 
    /*
     * Configure View resolver to provide JSON output using JACKSON library to
     * convert object in JSON format.
     */
    @Bean
    public ViewResolver jsonViewResolver() {
        return new JsonViewResolver();
    }
 
    /*
     * Configure View resolver to provide PDF output using lowagie pdf library to
     * generate PDF output for an object content
     */
    @Bean
    public ViewResolver pdfViewResolver() {
        return new PdfViewResolver();
    }
 
    /*
     * Configure View resolver to provide XLS output using Apache POI library to
     * generate XLS output for an object content
     */
    @Bean
    public ViewResolver excelViewResolver() {
        return new ExcelViewResolver();
    }
 
    /*
     * Configure View resolver to provide HTML output This is the default format
     * in absence of any type suffix.
     * 
     * Under views you can find the JSP's that will display the HTML for the Pizza Controller and the 
     * Primes Controller
     * 
     * To renmder the values correctlly on HTML (see PrimeRespoonse.jsp)
     * <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	 *		<html>
	 *	<head>
	 *		<%@ page isELIgnored="false" %>
	 *	</head>      
     */
    @Bean
    public ViewResolver jspViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
 
}