package nts.uk.ctx.at.request.infra.template.export;

import java.io.InputStream;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.ShapeCollection;
import com.aspose.cells.TextBox;
import com.aspose.cells.TextBoxCollection;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.template.export.TemplateExportGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AsposeTemplateExport extends AsposeCellsReportGenerator implements TemplateExportGenerator {
    

    
    @Inject
    private StoredFileStreamService fileStreamService;

    @Override
    public void generate(FileGeneratorContext generatorContext, String fileID, String fileName, String approverName, GeneralDate date, String status, boolean isExcel) {
        AsposeCellsReportContext designer;
        
        // get file uploaded
        InputStream inputStream = this.fileStreamService.takeOutFromFileId(fileID);
        
        designer = new AsposeCellsReportContext(inputStream);
        
        Workbook workbook = designer.getWorkbook();
        WorksheetCollection worksheets = workbook.getWorksheets();
        Worksheet worksheet = worksheets.get(0);
        
        // processing file
        TextBoxCollection textBoxCollection = worksheet.getTextBoxes();
        ShapeCollection sc = worksheet.getShapes();
        
        sc.get("APPORVAL1").setPrintable(true);
        TextBox textBoxName1 = textBoxCollection.get("NAME1");
        textBoxName1.setText(this.get6BytesString(approverName, 6).trim());
        TextBox textBoxDate1 = textBoxCollection.get("DATE1");
        textBoxDate1.setText(date.toString("yyyy/MM/dd"));
        TextBox textBoxStatus1 = textBoxCollection.get("STATUS1");
        textBoxStatus1.setText(status);
        
        this.setColorTextBox(textBoxName1);
        this.setColorTextBox(textBoxDate1);
        this.setColorTextBox(textBoxStatus1);
        
        // output file
        designer.getDesigner().setWorkbook(workbook);
        designer.processDesigner();

        if (isExcel) {
            designer.saveAsExcel(this.createNewFile(generatorContext, fileName.replace(".xlsx", "") + ".xlsx"));
        } else {
            designer.saveAsPdf(this.createNewFile(generatorContext, fileName.replace(".xlsx", "") + ".pdf"));
        }
    }
    
    private String get6BytesString(String text, int n) {
        String strimHalfSpaceText = text.split(" ")[0];
        String strimFullSpaceText = strimHalfSpaceText.split("　")[0];
        int lengthCut = this.findIdxFullHafl(strimFullSpaceText, n);
        return text.substring(0, lengthCut);
    }
    
    private int findIdxFullHafl(String text, int max) {
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            int c = text.charAt(i);
            int charLength = 2;
            // 0x20 ～ 0x80: 半角記号と半角英数字
            // 0xff61 ～ 0xff9f: 半角カタカナ
            if ((0x20 <= c && c <= 0x7e) || (0xff61 <= c && c <= 0xff9f)) {
                charLength = 1;
            }
            
            if (charLength + count <= max) {
                count += charLength;
            } else {
                return i;    
            }
        }
        return text.length();
    }
    
    private void setColorTextBox(TextBox textBox) {
        Font textFont = textBox.getFont();
        textFont.setColor(Color.fromArgb(255, 0, 0));
        textBox.setFont(textFont);
    }
}
