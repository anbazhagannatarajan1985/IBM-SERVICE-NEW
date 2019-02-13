package com.ibm.services.data.manager;

import org.springframework.stereotype.Component;

import com.ibm.services.data.entity.EstimatorDetails;
import com.ibm.services.data.entity.FormDetails;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

@Component
public class PdfManager {


	public static ByteArrayInputStream GenerateSecurityReport(FormDetails formDetails) {

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			
			PdfWriter pdfWriter = PdfWriter.getInstance(document, out);
			com.itextpdf.text.Rectangle rectangle = new com.itextpdf.text.Rectangle(30, 30, 550, 800);
		    pdfWriter.setBoxSize("rectangle", rectangle);
		      
			HeaderAndFooterPdfPageEventHelper headerAndFooter =  new HeaderAndFooterPdfPageEventHelper();
			pdfWriter.setPageEvent(headerAndFooter);

			document.open();
			
			Font fontStyle_Underline =  FontFactory.getFont(FontFactory.TIMES, 30f, Font.UNDERLINE, BaseColor.BLUE);
			Paragraph title = new Paragraph("IBM Security", fontStyle_Underline);
			title.setAlignment(Element.ALIGN_CENTER);
			
			document.add(title);
			document.add( Chunk.NEWLINE );
			
//			Font fontStyle_Underline =  FontFactory.getFont(FontFactory.TIMES, 30f, Font.UNDERLINE, BaseColor.BLUE);
			Paragraph subTitle = new Paragraph("Infrastructure and Endpoint Security Sizing Worksheet");
			subTitle.setAlignment(Element.ALIGN_CENTER);
			
			document.add(subTitle);
			document.add( Chunk.NEWLINE );
			
			addCustomerDetailsTable(document, formDetails);
			document.add( Chunk.NEWLINE );
			addFteReport(document, formDetails);
			document.add( Chunk.NEWLINE );
			addTermsAndConditions(document);
			document.close();

		} catch (DocumentException | IOException ex) {

		
		}

		return new ByteArrayInputStream(out.toByteArray());
	}
	
	private static void addTermsAndConditions(Document document) throws DocumentException, IOException {
		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 5});
		
		Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		
		PdfPCell headerCell = createCell("Terms & Conditions", headFont, null, 5, Element.ALIGN_LEFT, null, null);
		table.addCell(headerCell);

		Font valueCellFont = FontFactory.getFont(FontFactory.HELVETICA, 10f);
		
		PdfPCell row1cell3 = createCell("", valueCellFont, 5, 5, Element.ALIGN_LEFT, null, null);
		String termsAndConditionsText = getTermsAndConditionsText();
		List<Element> parseToList = HTMLWorker.parseToList(new StringReader(termsAndConditionsText), null);
		for (int k = 0; k < parseToList.size(); ++k)

	      {

	            Element ele =  (Element) parseToList.get(k);

	            row1cell3.addElement(ele);                        

	      }
		table.addCell(row1cell3);

		document.add(table);
	}
	
	private static String getTermsAndConditionsText() {
		return "<p>Please Note:<p>\r\n" + 
				"<p>During Transition months shifts will not be considered. If shifts required during Transition than add those months to Agreement terms month.</p> \r\n" + 
				"<ul style=\"list-style-type:none;\">\r\n" + 
				"<li><p>Service Start Date should be minimum 4 months from Solution Date else would need GID approval to start early.</p>\r\n" + 
				"</li>\r\n" + 
				"<li><p>Use Case developer will work only in 8x5 shift.</p>\r\n" + 
				"</li>\r\n" + 
				"<li><p>No On-call during Transition.</p>\r\n" + 
				"</li>\r\n" + 
				"<li><p>Final Cost will only appear in PDF once Solution is Saved.</p>\r\n" + 
				"</li>\r\n" + 
				"</ul>\r\n" + 
				"\r\n" + 
				"<p>Limitation:</p>\r\n" + 
				"<ul style=\"list-style-type:none;\">\r\n" + 
				"<li><p>SIOC Solution will NOT function if EPS is less than 800</p>\r\n" + 
				"</li>\r\n" + 
				"<li><p>Years of Service is limited to 10 years.</p>\r\n" + 
				"</li>\r\n" + 
				"<li><p>Cost is shown in USD only. </p>\r\n" + 
				"</li>\r\n" + 
				"<li><p>GID can not service if Term of Agreement is less than 12 months (excluding Transition time)</p>\r\n" + 
				"</li>\r\n" + 
				"</ul>";
	}
	
	private static void addFteReport(Document document, FormDetails formDetails) throws DocumentException {
		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 2, 2, 2 });
		
		BaseColor keyColumnColor = new BaseColor(255, 218, 179);
		BaseColor valueColumnColor = new BaseColor(204, 230, 255);

		Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

		
		PdfPCell headerCell = createCell("FTE Report", headFont, null, 5, Element.ALIGN_LEFT, null, 3);
		table.addCell(headerCell);

		Font cellFont = FontFactory.getFont(FontFactory.TIMES_ITALIC, 12f);
		Font valueCellFont = FontFactory.getFont(FontFactory.HELVETICA, 11f);
		Font subHeadFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10f);
		
		PdfPCell row1cell1 = createCell("", cellFont, null, 5, Element.ALIGN_LEFT, null, null);
		table.addCell(row1cell1);
		PdfPCell row1cell2 = createCell("Resource FTE", subHeadFont, null, 5, Element.ALIGN_CENTER, null, null);
		table.addCell(row1cell2);
		PdfPCell row1cell3 = createCell("", valueCellFont, 5, 5, Element.ALIGN_LEFT, null, 3);
		table.addCell(row1cell3);

		PdfPCell row2Cell1 = createCell("Band:", subHeadFont, null, 5, Element.ALIGN_CENTER, null, null);
		table.addCell(row2Cell1);
		PdfPCell row2Cell2 =createCell("Steady State", subHeadFont, 5, 5, Element.ALIGN_CENTER, null, null);
		table.addCell(row2Cell2);
		PdfPCell row2Cell3 = createCell("Transition FTE", subHeadFont, null, 5, Element.ALIGN_CENTER, null, null);
		table.addCell(row2Cell3);
		
		EstimatorDetails estimatorDetails = formDetails.getEstimatorDetails();
		
		PdfPCell row3Cell1 = createCell("B5", cellFont, 5, 5, Element.ALIGN_CENTER, keyColumnColor, null);
		table.addCell(row3Cell1);
		PdfPCell row3Cell2 =createCell(estimatorDetails.getB5(), valueCellFont, 5, 5, Element.ALIGN_CENTER, valueColumnColor, null);
		table.addCell(row3Cell2);		
		PdfPCell row3Cell3 =createCell(estimatorDetails.getTransitionFte(), valueCellFont, 5, 5, Element.ALIGN_CENTER, valueColumnColor, null);
		table.addCell(row3Cell3);
		
		PdfPCell row4Cell1 = createCell("B7", cellFont, 5, 5, Element.ALIGN_CENTER, keyColumnColor, null);
		table.addCell(row4Cell1);
		PdfPCell row4Cell2 =createCell(estimatorDetails.getB7(), valueCellFont, 5, 5, Element.ALIGN_CENTER, valueColumnColor, null);
		table.addCell(row4Cell2);
		PdfPCell row4Cell3 =createCell(estimatorDetails.getTransitionFte(), valueCellFont, 5, 5, Element.ALIGN_CENTER, valueColumnColor, null);
		table.addCell(row4Cell3);
		
		PdfPCell row5Cell1 = createCell("B7", cellFont, 5, 5, Element.ALIGN_CENTER, keyColumnColor, null);
		table.addCell(row5Cell1);
		PdfPCell row5Cell2 =createCell(estimatorDetails.getB71(), valueCellFont, 5, 5, Element.ALIGN_CENTER, valueColumnColor, null);
		table.addCell(row5Cell2);
		PdfPCell row5Cell3 =createCell(estimatorDetails.getTransitionFte(), valueCellFont, 5, 5, Element.ALIGN_CENTER, valueColumnColor, null);
		table.addCell(row5Cell3);
		
		PdfPCell row6Cell1 = createCell("Base FTE", cellFont, 5, 5, Element.ALIGN_CENTER, keyColumnColor, null);
		table.addCell(row6Cell1);
		PdfPCell row6Cell2 =createCell(estimatorDetails.getBaseFte(), valueCellFont, 5, 5, Element.ALIGN_CENTER, valueColumnColor, null);
		table.addCell(row6Cell2);
		PdfPCell row6Cell3 =createCell(estimatorDetails.getTransitionFte(), valueCellFont, 5, 5, Element.ALIGN_CENTER, valueColumnColor, null);
		table.addCell(row6Cell3);
		
		PdfPCell row7Cell1 = createCell("Total FTE", cellFont, 5, 5, Element.ALIGN_CENTER, keyColumnColor, null);
		table.addCell(row7Cell1);
		PdfPCell row7Cell2 =createCell(estimatorDetails.getTotalFte(), valueCellFont, 5, 5, Element.ALIGN_CENTER, valueColumnColor, null);
		table.addCell(row7Cell2);
		PdfPCell row7Cell3 =createCell(estimatorDetails.getTransitionFte(), valueCellFont, 5, 5, Element.ALIGN_CENTER, valueColumnColor, null);
		table.addCell(row7Cell3);
		
		document.add(table);
	}
	
	private static void addCustomerDetailsTable(Document document, FormDetails formDetails) throws DocumentException {
		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 2, 3,1,1 });
		
		BaseColor keyColumnColor = new BaseColor(255, 218, 179);
		BaseColor valueColumnColor = new BaseColor(204, 230, 255);

		Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

		
		PdfPCell headerCell = createCell("Customer Details", headFont, null, 5, Element.ALIGN_LEFT, null, 4);
		table.addCell(headerCell);

		Font cellFont = FontFactory.getFont(FontFactory.TIMES_ITALIC, 12f);
		Font valueCellFont = FontFactory.getFont(FontFactory.HELVETICA, 11f);
	
		PdfPCell keyCell = createCell("Customer/Account Name:", cellFont, null, 5, Element.ALIGN_LEFT, keyColumnColor, null);
		table.addCell(keyCell);

		PdfPCell cell1 = createCell(formDetails.getCustomerName(), valueCellFont, 5, 5, Element.ALIGN_LEFT, valueColumnColor, 3);
		table.addCell(cell1);

		PdfPCell row2Cell1 = createCell("Date Prepared:", cellFont, null, 5, Element.ALIGN_LEFT, keyColumnColor, null);
		table.addCell(row2Cell1);
		PdfPCell row2Cell2 =createCell(formDetails.getDatePrepared(), valueCellFont, 5, 5, Element.ALIGN_LEFT, valueColumnColor, 3);
		table.addCell(row2Cell2);
		
		PdfPCell row3Cell1 = createCell("Estimate Valid Until:", cellFont, null, 5, Element.ALIGN_LEFT, keyColumnColor, null);
		table.addCell(row3Cell1);
		PdfPCell row3Cell2 =createCell(formDetails.getValidTo(), valueCellFont, 5, 5, Element.ALIGN_LEFT, valueColumnColor, 3);
		table.addCell(row3Cell2);
		
		PdfPCell row4Cell1 = createCell("Sales Connect #:", cellFont, null, 5, Element.ALIGN_LEFT, keyColumnColor, null);
		table.addCell(row4Cell1);
		PdfPCell row4Cell2 =createCell(formDetails.getSalesConnectNo(), valueCellFont, 5, 5, Element.ALIGN_LEFT, valueColumnColor, 3);
		table.addCell(row4Cell2);
		
		PdfPCell row5Cell1 = createCell("RFS #:", cellFont, null, 5, Element.ALIGN_LEFT, keyColumnColor, null);
		table.addCell(row5Cell1);
		PdfPCell row5Cell2 =createCell(formDetails.getRfsNo(), valueCellFont, 5, 5, Element.ALIGN_LEFT, valueColumnColor, 3);
		table.addCell(row5Cell2);
		
		PdfPCell row6Cell1 = createCell("Prepared By:", cellFont, null, 5, Element.ALIGN_LEFT, keyColumnColor, null);
		table.addCell(row6Cell1);
		PdfPCell row6Cell2 =createCell(formDetails.getRequestorName(), valueCellFont, 5, 5, Element.ALIGN_LEFT, valueColumnColor, null);
		table.addCell(row6Cell2);
		
		PdfPCell row7Cell1 = createCell("Region:", cellFont, null, 5, Element.ALIGN_LEFT, keyColumnColor, null);
		table.addCell(row7Cell1);
		PdfPCell row7Cell2 =createCell(formDetails.getRegion(), valueCellFont, 5, 5, Element.ALIGN_LEFT, valueColumnColor, null);
		table.addCell(row7Cell2);
		
		PdfPCell row9Cell1 = createCell("Risk Rating:", cellFont, null, 5, Element.ALIGN_LEFT, keyColumnColor, null);
		table.addCell(row9Cell1);
		PdfPCell row9Cell2 =createCell(formDetails.getRiskRating(), valueCellFont, 5, 5, Element.ALIGN_LEFT, valueColumnColor, null);
		table.addCell(row9Cell2);
		
		PdfPCell row8Cell1 = createCell("Custom:", cellFont, null, 5, Element.ALIGN_LEFT, keyColumnColor, null);
		table.addCell(row8Cell1);
		PdfPCell row8Cell2 =createCell(formDetails.getCustom(), valueCellFont, 5, 5, Element.ALIGN_LEFT, valueColumnColor, null);
		table.addCell(row8Cell2);
		
		document.add(table);
	}
	
	private static PdfPCell createCell(String value, Font font, Integer paddingLeft, Integer paddingBottom, Integer alignment,
			BaseColor bgColor, Integer colspan) {
		PdfPCell cell;
		if (font != null) {
			cell = new PdfPCell(new Phrase(value, font));
		} else {
			cell = new PdfPCell(new Phrase(value));
		}
		if ( alignment != null) {
			cell.setHorizontalAlignment(alignment);
		}
		
		if(paddingLeft != null) {
			cell.setPaddingLeft(paddingLeft);
		}
		if (paddingBottom != null) {
			cell.setPaddingBottom(paddingBottom);
		}
		
		if (bgColor != null) {
			cell.setBackgroundColor(bgColor);
		}
		
		if (colspan != null) {
			cell.setColspan(colspan);
		}
		
		return cell;
	}
}
