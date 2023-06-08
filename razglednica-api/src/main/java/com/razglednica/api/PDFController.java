package com.razglednica.api;

import com.razglednica.pdf.service.PDFGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class PDFController {

  private final PDFGenerator pdfGenerator;

  @GetMapping("/getpdf")
  public ResponseEntity<byte[]> getPdf() throws IOException {
    byte[] contents = pdfGenerator.generatePdfFromPDf();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);
    // Here you have to set the actual filename of your pdf
    String filename = "output.pdf";
    headers.setContentDispositionFormData(filename, filename);
    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
    return ResponseEntity
        .ok()
        .headers(headers)
        .contentType(MediaType.APPLICATION_PDF)
        .contentLength(contents.length)
        .body(contents);
  }
}
