package com.example.ecommerce.util;

import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.Product;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Component;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Component
public class PdfGenerator {

    public ByteArrayInputStream generateOrderPdf(Order order) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // ✅ Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("🛒 E-Commerce Order Receipt", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // ✅ Order Info
            document.add(new Paragraph("Order ID: " + order.getId()));
            if (order.getUser() != null) {
                document.add(new Paragraph("Customer: " + order.getUser().getName()));
            }
            if (order.getOrderDate() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                document.add(new Paragraph("Date: " + order.getOrderDate().format(formatter)));
            }
            document.add(Chunk.NEWLINE);

            // ✅ Product Table
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{4, 2, 2});

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("Product Name", headFont));
            hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Price", headFont));
            hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Category", headFont));
            hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(hcell);

            double total = 0;

            for (Product product : order.getProducts()) {
                table.addCell(product.getName());
                table.addCell("₹" + product.getPrice());
                table.addCell(product.getCategory());
                total += product.getPrice();
            }

            document.add(table);

            document.add(Chunk.NEWLINE);

            // ✅ Total Price
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            document.add(new Paragraph("Total: ₹" + total, boldFont));
            document.close();

        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
}
    
}