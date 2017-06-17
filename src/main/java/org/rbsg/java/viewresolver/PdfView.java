package org.rbsg.java.viewresolver;


import java.awt.Color;
import java.util.Map;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.springframework.web.servlet.view.document.AbstractPdfView;
 
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.rbsg.java.model.PrimesResponse;
 
public class PdfView extends AbstractPdfView {
 
    @Override
    protected void buildPdfDocument(Map<String, Object> model,
            Document document, PdfWriter writer, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
 
    	PrimesResponse primesResponse = (PrimesResponse) model.get("primesResponse");
 
        PdfPTable table = new PdfPTable(2);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.getDefaultCell().setBackgroundColor(Color.lightGray);
 
        table.addCell("Initial"); 
        table.addCell("Primes");
 
         
        table.addCell(primesResponse.getInitial().toString());
 
        StringBuffer primeNumbers = new StringBuffer("");
            for (Integer primeNumber : primesResponse.getPrimes()) {
         	primeNumbers.append(primeNumber);
         	primeNumbers.append(" ");
        }
        table.addCell(primeNumbers.toString());
        document.add(table);
 
    }
 
}
