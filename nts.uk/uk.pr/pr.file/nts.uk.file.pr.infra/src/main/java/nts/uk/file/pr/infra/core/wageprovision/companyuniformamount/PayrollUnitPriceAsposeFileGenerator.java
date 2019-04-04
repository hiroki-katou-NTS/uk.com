package nts.uk.file.pr.infra.core.wageprovision.companyuniformamount;

import com.aspose.cells.*;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.file.app.core.wageprovision.companyuniformamount.PayrollUnitPriceFileGenerator;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.math.BigDecimal;
import java.util.List;


@Stateless
public class PayrollUnitPriceAsposeFileGenerator extends AsposeCellsReportGenerator implements PayrollUnitPriceFileGenerator{


    private static final String TEMPLATE_FILE = "report/会社一律金額の登録(デザイン改).xlsx";

    private static final String REPORT_FILE_NAME = "会社一律金額の登録(デザイン改).xlsx";

    private static final int MAX_LINE = 10;

    private static final int COLUMN_INDEX_CODE = 1;
    private static final int COLUMN_INDEX_NAME = 2;
    private static final int COLUMN_INDEX_DATE = 3;
    private static final int COLUMN_INDEX_MONEY = 4;
    private static final int COLUMN_INDEX_ALL = 5;
    private static final int COLUMN_INDEX_MONTH_SALARY = 6;
    private static final int COLUMN_INDEX_MONTH_SALARY_PER_DAY = 7;
    private static final int COLUMN_INDEX_ADAY_PAYEE = 8;
    private static final int COLUMN_INDEX_HOURLY_DAY = 9;



    @Override
    public void generate(FileGeneratorContext fileContext, List<Object[]> exportData) {
        try(AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)){
            Workbook wb = reportContext.getWorkbook();
            WorksheetCollection wsc = wb.getWorksheets();
            Worksheet ws = wsc.get(0);
            this.writeFileExcel(ws,exportData);
            reportContext.processDesigner();
            reportContext.saveAsExcel(this.createNewFile(fileContext,this.getReportName(REPORT_FILE_NAME)));
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void writeFileExcel(Worksheet ws, List<Object[]> exportData){
        int rowIndex = 4;
        int count = 0;
        HorizontalPageBreakCollection pageBreaks = ws.getHorizontalPageBreaks();
        for(Object[] entity : exportData){
            ws.getCells().get(rowIndex,COLUMN_INDEX_CODE).putValue(entity[0]);
            ws.getCells().get(rowIndex,COLUMN_INDEX_NAME).putValue(entity[1]);
            ws.getCells().get(rowIndex,COLUMN_INDEX_DATE).putValue(this.convertYearMonth(entity[2].toString()));
            ws.getCells().get(rowIndex,COLUMN_INDEX_MONEY).putValue(entity[3]);
            ws.getCells().get(rowIndex,COLUMN_INDEX_ALL).putValue(((BigDecimal) entity[9]).intValue() == 1 ? this.getTextResource(((BigDecimal)entity[4]).intValue())  : "-");
            ws.getCells().get(rowIndex,COLUMN_INDEX_MONTH_SALARY).putValue(((BigDecimal) entity[9]).intValue() == 0 ? this.getTextResource(((BigDecimal)entity[5]).intValue()) : "-");
            ws.getCells().get(rowIndex,COLUMN_INDEX_MONTH_SALARY_PER_DAY).putValue(((BigDecimal) entity[9]).intValue() == 0 ? this.getTextResource(((BigDecimal)entity[6]).intValue()) : "-");
            ws.getCells().get(rowIndex,COLUMN_INDEX_ADAY_PAYEE).putValue(((BigDecimal) entity[9]).intValue() == 0 ? this.getTextResource(((BigDecimal)entity[7]).intValue()) : "-");
            ws.getCells().get(rowIndex,COLUMN_INDEX_HOURLY_DAY).putValue(((BigDecimal) entity[9]).intValue() == 0 ? this.getTextResource(((BigDecimal)entity[8]).intValue()) : "-");

            if(count == MAX_LINE){
                pageBreaks.add(rowIndex);
                count = 0;
            }
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
