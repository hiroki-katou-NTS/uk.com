package nts.uk.file.pr.infra.core.bank;

import com.aspose.cells.*;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.file.app.core.bank.BankExportFileGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;


@Stateless
public class BankAposeFileGenerator extends AsposeCellsReportGenerator implements BankExportFileGenerator {

    private static final String TEMPLATE_FILE = "report/QMM002.xlsx";

    private static final String REPORT_FILE_NAME = "QMM002銀行の登録.pdf";


    private static final int MAX_LINE = 37;
    private static final int FIRST_ROW_FILL = 3;

    private static final int CD_BANK = 3;
    private static final int NAME_BANK = 4;
    private static final int KANA_NAME_BANK = 5;
    private static final int CD_BRANCH = 6;
    private static final int NAME_BRANCH = 7;
    private static final int KANA_BRANCH = 8;



    @Override
    public void generate(FileGeneratorContext fileContext, List<Object[]> exportData, String companyName) {
        try(AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)){
            Workbook wb = reportContext.getWorkbook();
            WorksheetCollection wsc = wb.getWorksheets();

            //pagination
            for(int i = 1; i < Math.ceil((float)exportData.size()/(float)MAX_LINE) ; i ++){
                wsc.addCopy(0);
            }

            this.writeFileExcel(wsc,exportData,companyName);
            reportContext.processDesigner();
            reportContext.saveAsPdf(this.createNewFile(fileContext,this.getReportName(REPORT_FILE_NAME)));
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void writeFileExcel(WorksheetCollection wsc, List<Object[]> exportData, String companyName){
        int rowIndex = FIRST_ROW_FILL;
        int count = 0;
        int sheetIndex = 0;
        Worksheet ws = wsc.get(sheetIndex);
        this.settingHeader(ws,companyName);

        for(Object[] entity : exportData){
            if(count == MAX_LINE) {

                sheetIndex ++;
                ws = wsc.get(sheetIndex);
                this.settingHeader(ws,companyName);
                rowIndex = FIRST_ROW_FILL;
                count = 0;


            }
            ws.getCells().get(rowIndex,CD_BANK).putValue(entity[0]);
            ws.getCells().get(rowIndex,NAME_BANK).putValue(entity[1]);
            ws.getCells().get(rowIndex,KANA_NAME_BANK).putValue(entity[2]);
            ws.getCells().get(rowIndex,CD_BRANCH).putValue(entity[3]);
            ws.getCells().get(rowIndex,NAME_BRANCH).putValue(entity[4]);
            ws.getCells().get(rowIndex,KANA_BRANCH).putValue(entity[5]);
            rowIndex ++;
            count ++;
        }
    }

    private void settingHeader(Worksheet ws, String companyName){

        // Set print page
        PageSetup pageSetup = ws.getPageSetup();
        pageSetup.setHeader(0, "&\"ＭＳ ゴシック\"&10 " + companyName);

        // Set header date
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d  HH:mm:ss", Locale.JAPAN);
        pageSetup.setHeader(2, "&\"ＭＳ ゴシック\"&10 " + LocalDateTime.now().format(fullDateTimeFormatter) + "\npage&P ");

    }
}
