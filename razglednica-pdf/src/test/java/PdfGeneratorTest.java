import com.razglednica.pdf.service.PDFGenerator;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class PdfGeneratorTest {

  @Test
  public void shouldTest() throws IOException, InvalidFormatException {
    PDFGenerator pdfGenerator = new PDFGenerator();


    pdfGenerator.convertToPdf();

  }
}
