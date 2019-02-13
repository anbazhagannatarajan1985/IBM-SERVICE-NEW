package com.ibm.services.data.manager;

 
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderAndFooterPdfPageEventHelper extends PdfPageEventHelper {

	public void onStartPage(PdfWriter pdfWriter, Document document) {
//	      System.out.println("onStartPage() method > Writing header in file");
//	      Rectangle rect = pdfWriter.getBoxSize("rectangle");
//	      
//	      // TOP LEFT
//	      ColumnText.showTextAligned(pdfWriter.getDirectContent(),
//	               Element.ALIGN_CENTER, new Phrase("TOP LEFT"), rect.getLeft(),
//	               rect.getTop(), 0);
//	 
//	      // TOP MEDIUM
//	      ColumnText.showTextAligned(pdfWriter.getDirectContent(),
//	               Element.ALIGN_CENTER, new Phrase("TOP MEDIUM"),
//	               rect.getRight() / 2, rect.getTop(), 0);
//	 
//	      // TOP RIGHT
//	      ColumnText.showTextAligned(pdfWriter.getDirectContent(),
//	               Element.ALIGN_CENTER, new Phrase("TOP RIGHT"), rect.getRight(),
//	               rect.getTop(), 0);
	  }
	 
	  public void onEndPage(PdfWriter pdfWriter, Document document) {
	      System.out.println("onEndPage() method > Writing footer in file");
	      Rectangle rect = pdfWriter.getBoxSize("rectangle");
	      // BOTTOM LEFT
	      String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	      ColumnText.showTextAligned(pdfWriter.getDirectContent(),
	               Element.ALIGN_CENTER, new Phrase("Date:" + currentDate),
	               rect.getLeft()+30, rect.getBottom(), 0);
	 
	      // BOTTOM MEDIUM
	      ColumnText.showTextAligned(pdfWriter.getDirectContent(),
	               Element.ALIGN_CENTER, new Phrase("IBM Security"),
	               rect.getRight() / 2, rect.getBottom(), 0);
	 
	      // BOTTOM RIGHT
	      ColumnText.showTextAligned(pdfWriter.getDirectContent(),
	               Element.ALIGN_CENTER, new Phrase("page " + document.getPageNumber()),
	               rect.getRight()-10, rect.getBottom(), 0);
	  }
	  
}
