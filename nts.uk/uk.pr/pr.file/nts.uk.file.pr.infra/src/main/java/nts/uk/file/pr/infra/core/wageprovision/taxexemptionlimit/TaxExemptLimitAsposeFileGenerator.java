package nts.uk.file.pr.infra.core.wageprovision.taxexemptionlimit;

import com.aspose.cells.*;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.file.app.core.wageprovision.taxexemptionlimit.TaxExemptLimitFileGenerator;
import nts.uk.ctx.pr.file.app.core.wageprovision.taxexemptionlimit.TaxExemptLimitSetExportData;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Stateless
public class TaxExemptLimitAsposeFileGenerator extends AsposeCellsReportGenerator
        implements TaxExemptLimitFileGenerator {

    private static final String TEMPLATE_FILE = "report/QMM023.xlsx";

    private static final String REPORT_FILE_NAME = "QMM023非課税限度額の登録.pdf";

    private static final int COLUMN_START_1= 1;
    private static final int COLUMN_START_2 = 5;

    @Inject
    private CompanyAdapter company;

    @Override
    public void generate(FileGeneratorContext fileContext, List<TaxExemptLimitSetExportData> exportData) {
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
            int rowStart = 2;
            Workbook wb = reportContext.getWorkbook();
            WorksheetCollection wsc = wb.getWorksheets();
            Worksheet ws = wsc.get(0);
            ws.setName(TextResource.localize("QMM023_14"));
            //set headler
            // Company name
            String companyName = this.company.getCurrentCompany().map(CompanyInfor::getCompanyName).orElse("");
            PageSetup pageSetup = ws.getPageSetup();
            pageSetup.setHeader(0, "&\"ＭＳ ゴシック\"&10 " + companyName);

            // Output item name
            pageSetup.setHeader(1, "&\"ＭＳ ゴシック\"&16 " + "非課税限度額の登録");

            // Set header date
            DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d  H:mm:ss", Locale.JAPAN);
            String currentFormattedDate = LocalDateTime.now().format(fullDateTimeFormatter);
            pageSetup.setHeader(2, "&\"ＭＳ ゴシック\"&10 " + currentFormattedDate+"\npage&P");
            Cells cells = ws.getCells();
            //break page

            int page =  (exportData.size() / 72);
            int pageExcess = (exportData.size() % 72);
            int countElement = 0;
            for (int i = 1; i < Math.ceil((float)exportData.size()/(float)72); i++) {
                wsc.get(wsc.addCopy(0)).setName("sheetName" + i);
            }
            for (int i = 0; i <= page; i++) {
                if (countElement % 72 == 0 && i != 0 && pageExcess != 0) {
                    Worksheet sheet = wsc.get("sheetName" + i);
                    cells = sheet.getCells();
                    rowStart = 2;
                }
                for(int x = 0 ; x < 72 ; x++){
                    if(countElement == exportData.size()){
                        break;
                    }
                    if(x < 36){
                        TaxExemptLimitSetExportData e = exportData.get(countElement);
                        cells.get(rowStart,COLUMN_START_1).setValue(e.getTaxFreeAmountCode());
                        cells.get(rowStart,COLUMN_START_1+1).setValue(e.getTaxExemptionName());
                        cells.get(rowStart,COLUMN_START_1+2).setValue(formatPrice(e.getTaxExemption().toString()));
                    }
                    else {
                        TaxExemptLimitSetExportData e = exportData.get(countElement);
                        cells.get(rowStart,COLUMN_START_2).setValue(e.getTaxFreeAmountCode());
                        cells.get(rowStart,COLUMN_START_2+1).setValue(e.getTaxExemptionName());
                        cells.get(rowStart,COLUMN_START_2+2).setValue(formatPrice(e.getTaxExemption().toString()));
                    }
                    if(x == 35){
                        rowStart = 1;
                    }
                    rowStart++;
                    countElement++;
                }
            }
            reportContext.processDesigner();
            reportContext.saveAsPdf(this.createNewFile(fileContext, this.getReportName(REPORT_FILE_NAME)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private String formatPrice(String price) {
        double amountParse = Double.parseDouble(price);
        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance(Locale.JAPAN);
        DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance(Locale.JAPAN);
        dfs.setCurrencySymbol("¥");
        decimalFormat.setDecimalFormatSymbols(dfs);
        return decimalFormat.format(amountParse);
    }
}
