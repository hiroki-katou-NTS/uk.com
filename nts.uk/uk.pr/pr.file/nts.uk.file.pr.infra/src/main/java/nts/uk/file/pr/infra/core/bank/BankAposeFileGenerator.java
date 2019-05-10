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

    private static final int CD_BANK = 1;
    private static final int NAME_BANK = 2;
    private static final int KANA_NAME_BANK = 3;
    private static final int CD_BRANCH = 4;
    private static final int NAME_BRANCH = 5;
    private static final int KANA_BRANCH = 6;



    @Override
    public void generate(FileGeneratorContext fileContext, List<Object[]> exportData, String companyName) {
        try(AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)){
            Workbook wb = reportContext.getWorkbook();
            WorksheetCollection wsc = wb.getWorksheets();
            Worksheet ws = wsc.get(0);
            this.writeFileExcel(ws,exportData,companyName);
            reportContext.processDesigner();
            reportContext.saveAsPdf(this.createNewFile(fileContext,this.getReportName(REPORT_FILE_NAME)));
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void writeFileExcel(Worksheet ws, List<Object[]> exportData, String companyName){
        int rowIndex = 3;
        int count = 0;
        int numberRow = 38;
        int sourceRowIndex = 0;
        int destinationRowIndex = 38;

        // Set print page
        PageSetup pageSetup = ws.getPageSetup();
        pageSetup.setHeader(0, "&10&\"MS ゴシック\"" + companyName);

        // Set header date
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d  HH:mm:ss", Locale.JAPAN);
        pageSetup.setHeader(2, "&10&\"MS ゴシック\" " + LocalDateTime.now().format(fullDateTimeFormatter) + "\npage &P ");
        HorizontalPageBreakCollection pageBreaks = ws.getHorizontalPageBreaks();

        for(int i = 1; i < Math.ceil((float)exportData.size()/(float)MAX_LINE) ; i ++){
            try {
                ws.getCells().copyRows(ws.getCells(),0,39,39);
            }catch (Exception e){

            }
            //numberRow  = numberRow + 37;
            //sourceRowIndex = sourceRowIndex + 37;
            //destinationRowIndex = destinationRowIndex + 37;
        }

        for(Object[] entity : exportData){
            if(count == MAX_LINE) {
                pageBreaks.add(rowIndex - 2);
                count = 0;
                ws.getCells().get(rowIndex,CD_BANK).putValue(entity[0]);
                ws.getCells().get(rowIndex,NAME_BANK).putValue(entity[1]);
                ws.getCells().get(rowIndex,KANA_NAME_BANK).putValue(entity[2]);
                rowIndex = rowIndex + 3;
            }else{
                ws.getCells().get(rowIndex,CD_BANK).putValue(entity[8].toString().equals("1") ? entity[0] : null);
                ws.getCells().get(rowIndex,NAME_BANK).putValue(entity[8].toString().equals("1")  ? entity[1] : null);
                ws.getCells().get(rowIndex,KANA_NAME_BANK).putValue(entity[8].toString().equals("1") ? entity[2] : null);
            }

            ws.getCells().get(rowIndex,CD_BRANCH).putValue(entity[3]);
            ws.getCells().get(rowIndex,NAME_BRANCH).putValue(entity[4]);
            ws.getCells().get(rowIndex,KANA_BRANCH).putValue(entity[5]);
            rowIndex ++;
            count ++;
        }
    }
}
