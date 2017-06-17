package org.rbsg.java.viewresolver;

 

import java.util.Locale;

import org.apache.log4j.Logger;
import org.rbsg.java.controller.PrimesController;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
 
public class ExcelViewResolver implements ViewResolver{
 
	final static Logger logger = Logger.getLogger(ExcelViewResolver.class); 
	
    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
    	logger.info("We come in here to rendering the Exel format (xls) "   );
    	
    	ExcelView view = new ExcelView();
        return view;
      }
     
}
