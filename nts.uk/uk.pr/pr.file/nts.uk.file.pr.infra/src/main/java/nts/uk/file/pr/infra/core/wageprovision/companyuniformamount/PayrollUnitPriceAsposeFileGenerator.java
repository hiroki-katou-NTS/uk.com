package nts.uk.file.pr.infra.core.wageprovision.companyuniformamount;

import com.aspose.cells.*;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.file.app.core.wageprovision.companyuniformamount.PayrollUnitPriceFileGenerator;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;


@Stateless
public class PayrollUnitPriceAsposeFileGenerator extends AsposeCellsReportGenerator implements PayrollUnitPriceFileGenerator{


    private static final String TEMPLATE_FILE = "report/QMM007.xlsx";

    private static final String REPORT_FILE_NAME = "QMM007会社一律金額の登録.pdf";


    private static final int MAX_LINE = 36;

    private static final int COLUMN_INDEX_CODE = 2;
    private static final int COLUMN_INDEX_NAME = 3;
    private static final int COLUMN_INDEX_DATE = 4;
    private static final int COLUMN_INDEX_MONEY = 5;
    private static final int COLUMN_INDEX_ALL = 6;
    private static final int COLUMN_INDEX_MONTH_SALARY = 7;
    private static final int COLUMN_INDEX_MONTH_SALARY_PER_DAY = 8;
    private static final int COLUMN_INDEX_ADAY_PAYEE = 9;
    private static final int COLUMN_INDEX_HOURLY_DAY = 10;

    private static final int NUMBER_ROW_HEADER = 3;



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
        int numberRow = 39;
        int sourceRowIndex = 0;
        int destinationRowIndex = 39;

        // Set print page
        PageSetup pageSetup = ws.getPageSetup();
        pageSetup.setHeader(0, "&\"ＭＳ ゴシック\"&10 " + companyName);

        // Set header date
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d  HH:mm:ss", Locale.JAPAN);
        pageSetup.setHeader(2, "&\"ＭＳ ゴシック\"&10 " + LocalDateTime.now().format(fullDateTimeFormatter) + "\npage&P ");
        HorizontalPageBreakCollection pageBreaks = ws.getHorizontalPageBreaks();

        for(int i = 1; i < Math.ceil((float)exportData.size()/(float)MAX_LINE) ; i ++){
            try {
                ws.getCells().copyRows(ws.getCells(),sourceRowIndex,destinationRowIndex,numberRow);
            }catch (Exception e){

            }
            numberRow  = numberRow + 38;
            sourceRowIndex = sourceRowIndex + 38;
            destinationRowIndex = destinationRowIndex + 38;
        }

        for(Object[] entity : exportData){
            if(count == MAX_LINE){
                pageBreaks.add(rowIndex);
                count = 0;
                rowIndex = rowIndex + NUMBER_ROW_HEADER;
                ws.getCells().get(rowIndex,COLUMN_INDEX_CODE).putValue(entity[0]);
                ws.getCells().get(rowIndex,COLUMN_INDEX_NAME).putValue(entity[1]);
            }else{
                ws.getCells().get(rowIndex,COLUMN_INDEX_CODE).putValue(entity[10].toString().equals("1") ? entity[0] : null);
                ws.getCells().get(rowIndex,COLUMN_INDEX_NAME).putValue(entity[10].toString().equals("1")  ? entity[1] : null);
            }

            ws.getCells().get(rowIndex,COLUMN_INDEX_DATE).putValue(this.convertYearMonth(entity[2].toString()));
            ws.getCells().get(rowIndex,COLUMN_INDEX_MONEY).putValue(entity[3]);
            ws.getCells().get(rowIndex,COLUMN_INDEX_ALL).putValue(((BigDecimal) entity[9]).intValue() == 1 ? this.getTextResource(((BigDecimal)entity[4]).intValue())  : "-");
            ws.getCells().get(rowIndex,COLUMN_INDEX_MONTH_SALARY).putValue(((BigDecimal) entity[9]).intValue() == 0 ? this.getTextResource(((BigDecimal)entity[5]).intValue()) : "-");
            ws.getCells().get(rowIndex,COLUMN_INDEX_MONTH_SALARY_PER_DAY).putValue(((BigDecimal) entity[9]).intValue() == 0 ? this.getTextResource(((BigDecimal)entity[6]).intValue()) : "-");
            ws.getCells().get(rowIndex,COLUMN_INDEX_ADAY_PAYEE).putValue(((BigDecimal) entity[9]).intValue() == 0 ? this.getTextResource(((BigDecimal)entity[7]).intValue()) : "-");
            ws.getCells().get(rowIndex,COLUMN_INDEX_HOURLY_DAY).putValue(((BigDecimal) entity[9]).intValue() == 0 ? this.getTextResource(((BigDecimal)entity[8]).intValue()) : "-");
            rowIndex++;
            count ++;
        }

    }

    private String getTextResource(int value){
        if(value == 1)
            return TextResource.localize("QMM007_20");
        return TextResource.localize("QMM007_21");
    }

    private String convertYearMonth(String value){
        String year = value.substring(0,4);
        String month = value.substring(4);
        return year + "/" + month;
    }
}
