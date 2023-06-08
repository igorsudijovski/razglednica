package com.razglednica.pdf.service;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@Service
public class PDFGenerator {

  public void convertToPdf() {
    File inputWord = new File("src/main/resources/templates/test_small_2.docx");
    File outputFile = new File("src/main/resources/templates/test_small.pdf");
    try  {
      InputStream docxInputStream = new FileInputStream(inputWord);
      OutputStream outputStream = new FileOutputStream(outputFile);
      IConverter converter = LocalConverter.builder().build();
      converter.convert(docxInputStream).as(DocumentType.DOCX).to(outputStream).as(DocumentType.PDF).execute();
      outputStream.close();
      System.out.println("success");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void replaceImage() throws InvalidFormatException, IOException {
    XWPFDocument doc = new XWPFDocument(OPCPackage.open("src/main/resources/templates/test_small.docx"));

    for (XWPFParagraph p: doc.getParagraphs()) {
      List<XWPFRun> runs = p.getRuns();
      if (runs != null) {
        int pos = -1;
        double height = 0;
        double width = 0;
        for (XWPFRun r : runs) {
          if (r.getEmbeddedPictures() != null) {
            List<XWPFPicture> embeddedPictures = r.getEmbeddedPictures();
            if (!embeddedPictures.isEmpty()) {
              pos = 1;
              width = embeddedPictures.get(0).getWidth();
              height = embeddedPictures.get(0).getDepth();
            }
          }
        }
        if (pos != -1) {
          p.removeRun(pos);
          XWPFRun run = p.createRun();
          try (FileInputStream is = new FileInputStream("src/main/resources/images/test1.png")) {
            run.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, "src/main/resources/images/test1.png",
                Units.toEMU(width), Units.toEMU(height));
          }
          p.addRun(run);
        }
      }
    }
    doc.write(new FileOutputStream("src/main/resources/templates/test_small_2.docx"));
    doc.close();

  }

  public void changeText() throws InvalidFormatException, IOException {
    XWPFDocument doc = new XWPFDocument(OPCPackage.open("src/main/resources/templates/test_small.docx"));
    for (XWPFParagraph p: doc.getParagraphs()) {
      List<XWPFRun> runs = p.getRuns();
      if (runs != null) {
        for (XWPFRun r : runs) {
          String text = r.getText(0);
          if (text != null && text.startsWith("Jes")) {
            text = text.replace("Jes", "JAJAJAJA");
            r.setText(text, 0);
          }
        }
      }
      System.out.println(p.getText());
    }
    doc.write(new FileOutputStream("src/main/resources/templates/test_small_1.docx"));
    doc.close();
  }


}
