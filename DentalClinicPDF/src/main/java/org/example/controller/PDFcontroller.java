package org.example.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class PDFcontroller {

    @RequestMapping("/api/prescription/pdf")
    public ResponseEntity<byte[]> getPrescription(@RequestParam String patient_name, @RequestParam String doctor_name, @RequestParam String prescription_body, HttpServletResponse response) throws IOException {
        try (PDDocument document = new PDDocument()) {
            byte[] doc_bytes = null;
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            File font = new File("C:\\Users\\Matvey\\IdeaProjects\\DentalClinic\\DentalClinicPDF\\src\\main\\resources\\TimesNewRomanRegular.ttf");
            contentStream.setFont(PDType0Font.load(document, font), 12);

            contentStream.beginText();
            contentStream.newLineAtOffset(30, 760);
            contentStream.setLeading(16.0f);
            contentStream.showText("Министерство здравоохранения");
            contentStream.newLine();
            contentStream.showText("Российской федерации");
            contentStream.setLeading(30.0f);
            contentStream.newLine();
            contentStream.showText("Наименование (штамп)");
            contentStream.setLeading(16.0f);
            contentStream.newLine();
            contentStream.showText("медецинской организации");
            contentStream.endText();

            contentStream.beginText();
            contentStream.newLineAtOffset(350, 760);
            contentStream.showText("Код формы по ОКУД 3108805");
            contentStream.setLeading(25.0f);
            contentStream.newLine();
            contentStream.showText("Медицинская документация");
            contentStream.setLeading(14.0f);
            contentStream.newLine();
            contentStream.showText("Форма № 148-1/у-88");
            contentStream.setLeading(25.0f);
            contentStream.newLine();
            contentStream.showText("Утверждено приказом");
            contentStream.setLeading(14.0f);
            contentStream.newLine();
            contentStream.showText("Министерства здравоохранения");
            contentStream.newLine();
            contentStream.showText("Российской Федерации");
            contentStream.newLine();
            contentStream.showText("от 14 января 2019 г. № 4н");
            contentStream.newLine();
            contentStream.endText();

            contentStream.moveTo(30, 645);
            contentStream.lineTo(560, 645);
            contentStream.setLineWidth(1f);
            contentStream.stroke();

            contentStream.beginText();
            contentStream.newLineAtOffset(260, 620);
            contentStream.setLeading(20.0f);
            contentStream.setFont(PDType0Font.load(document, font), 20);
            contentStream.showText("РЕЦЕПТ");
            contentStream.endText();

            contentStream.beginText();
            contentStream.newLineAtOffset(185, 604);
            contentStream.setLeading(16.0f);
            contentStream.setFont(PDType0Font.load(document, font), 14);
            contentStream.showText("(взрослый, детский - нужное подчеркнуть)");
            contentStream.endText();

            contentStream.beginText();
            contentStream.newLineAtOffset(30, 558);
            contentStream.setFont(PDType0Font.load(document, font), 14);
            contentStream.setLeading(16.0f);
            contentStream.showText("Фамилия, инициалы имени и отчества (последнее - при наличии) пациента:");
            contentStream.endText();

            contentStream.beginText();
            contentStream.newLineAtOffset(30, 535);
            contentStream.setFont(PDType0Font.load(document, font), 16);
            contentStream.showText(patient_name);
            contentStream.endText();

            contentStream.beginText();
            contentStream.newLineAtOffset(30, 515);
            contentStream.setFont(PDType0Font.load(document, font), 14);
            contentStream.setLeading(16.0f);
            contentStream.showText("Фамилия, инициалы имени и отчества (последнее - при наличии) лечащего");
            contentStream.newLine();
            contentStream.showText("врача (фельдшера, акушерки):");
            contentStream.endText();

            contentStream.beginText();
            contentStream.newLineAtOffset(30, 475);
            contentStream.setFont(PDType0Font.load(document, font), 16);
            contentStream.showText(doctor_name);
            contentStream.endText();

            contentStream.beginText();
            contentStream.newLineAtOffset(30, 455);
            contentStream.setFont(PDType0Font.load(document, font), 15);
            contentStream.setLeading(19.0f);
            contentStream.showText("руб. | коп. | Rp.");
            contentStream.newLine();
            contentStream.setFont(PDType0Font.load(document, font), 17);
            contentStream.showText(prescription_body);
            contentStream.endText();

            contentStream.moveTo(30, 150);
            contentStream.lineTo(560, 150);
            contentStream.setLineWidth(1f);
            contentStream.stroke();

            contentStream.beginText();
            contentStream.newLineAtOffset(30, 130);
            contentStream.setFont(PDType0Font.load(document, font), 14);
            contentStream.setLeading(18.0f);
            contentStream.showText("Подпись");
            contentStream.newLine();
            contentStream.showText("и печать лечащего врача");
            contentStream.newLine();
            contentStream.showText("(подпись фельдшера, акушерки)");
            contentStream.setLeading(30.0f);
            contentStream.newLine();
            contentStream.setFont(PDType0Font.load(document, font), 16);
            contentStream.showText("Рецепт действителен в течение 60 дней");
            contentStream.endText();

            contentStream.close();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            doc_bytes = baos.toByteArray();

            response.setContentType("application/pdf");
            DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
            String currentDateTime = dateFormat.format(new Date());
            String headerkey = "Content-Disposition";
            String headervalue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
            response.setHeader(headerkey, headervalue);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(doc_bytes);
        }
    }
}
