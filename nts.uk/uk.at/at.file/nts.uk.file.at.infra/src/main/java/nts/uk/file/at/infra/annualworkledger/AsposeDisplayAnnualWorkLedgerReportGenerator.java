package nts.uk.file.at.infra.annualworkledger;

import com.aspose.cells.*;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.AnnualWorkLedgerExportDataSource;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.OutPutWorkStatusContent;
import nts.uk.file.at.app.export.annualworkledger.DisplayAnnualWorkLedgerReportGenerator;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Stateless
public class AsposeDisplayAnnualWorkLedgerReportGenerator extends AsposeCellsReportGenerator
        implements DisplayAnnualWorkLedgerReportGenerator {
    private static final String TEMPLATE_FILE_ADD = "report/KWR004.xlsx";
    private static final String REPORT_FILE_NAME = "年間勤務台帳";
    private static final String PDF_EXT = ".pdf";
    private static final String EXCEL_EXT = ".xlsx";
    private static final String PRINT_AREA = "A1:AJ"; // TODO
    private static final int EXPORT_PDF = 1;
    private static final int EXPORT_EXCEL = 2;

    @Override
    public void generate(FileGeneratorContext generatorContext, AnnualWorkLedgerExportDataSource dataSource) {
        try {
            AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE_ADD);
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            Worksheet worksheet = worksheets.get(0);
            //printContents(worksheet, dataSource);
            worksheets.setActiveSheetIndex(0);
            reportContext.processDesigner();
            String fileName = REPORT_FILE_NAME;
            if (dataSource.getMode() == EXPORT_EXCEL) {
                // save as excel file
                reportContext.saveAsExcel(this.createNewFile(generatorContext, fileName + EXCEL_EXT));
            } else if (dataSource.getMode() == EXPORT_PDF) {
                // save as PDF file
                reportContext.saveAsPdf(this.createNewFile(generatorContext, fileName + PDF_EXT));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void settingPage(Worksheet worksheet, OutPutWorkStatusContent dataSource, int countPrint) {
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setPaperSize(PaperSizeType.PAPER_A_4);
        pageSetup.setOrientation(PageOrientationType.LANDSCAPE);
        String companyName = dataSource.getCompanyName();
        pageSetup.setHeader(0, "&7&\"ＭＳ フォントサイズ\"" + companyName);
        pageSetup.setHeader(1, "&12&\"ＭＳ フォントサイズ\""
                + dataSource.getTitle());
        pageSetup.setPrintArea(PRINT_AREA + countPrint);
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter
                .ofPattern("yyyy/MM/dd  H:mm", Locale.JAPAN);
        worksheet.getPageSetup().setHeader(2,
                "&7&\"MS フォントサイズ\"" + LocalDateTime.now().format(fullDateTimeFormatter) + "\n" +
                        TextResource.localize("page") + " &P");
        if (dataSource.getMode() == EXPORT_EXCEL) {
            pageSetup.setZoom(60);
        } else if (dataSource.getMode() == EXPORT_PDF) {
            pageSetup.setFitToPagesTall(0);
            pageSetup.setFitToPagesWide(0);
        }
    }
}
