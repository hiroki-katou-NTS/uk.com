package nts.uk.file.at.infra.holidayconfirmationtable;

import com.aspose.cells.*;
import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.app.query.arbitraryperiodsummarytable.*;
import nts.uk.ctx.at.function.dom.arbitraryperiodsummarytable.OutputSettingOfArbitrary;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;
import nts.uk.ctx.sys.gateway.dom.adapter.company.CompanyBsImport;
import nts.uk.file.at.app.export.arbitraryperiodsummarytable.ArbitraryPeriodSummaryDto;
import nts.uk.file.at.app.export.holidayconfirmationtable.OutputTraceConfirmTableDataSource;
import nts.uk.file.at.app.export.holidayconfirmationtable.OutputTraceConfirmTableReportGenerator;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Stateless
public class OutputTraceConfirmTableReportGeneratorImpl extends AsposeCellsReportGenerator
        implements OutputTraceConfirmTableReportGenerator {
    private static final String TEMPLATE_FILE_ADD = "";
    private static final String EXCEL_EXT = ".xlsx";
    private static final String PRINT_AREA = "";
    private static final String FORMAT_DATE = "yyyy/MM/dd";
    private static final int MAX_LINE_IN_PAGE = 22;
    private static final int MAX_COL_IN_PAGE = 22;
    private static final double PAGE_WIDTH = 11.69 -2;
    private static final Integer HIERARCHY_LENGTH = 3;
    @Override
    public void generate(FileGeneratorContext generatorContext, OutputTraceConfirmTableDataSource dataSource) {
        try {
            AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE_ADD);
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            String title ="";
            Worksheet worksheet = worksheets.get(1);
            worksheet.setName(title);
            settingPage(worksheet,dataSource, title);
            printContents(worksheet, dataSource);
            worksheets.removeAt(0);
            reportContext.processDesigner();
            String fileName = title + "_" + GeneralDateTime.now().toString("yyyyMMddHHmmss");
            reportContext.saveAsExcel(this.createNewFile(generatorContext, fileName + EXCEL_EXT));


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void settingPage(Worksheet worksheet,OutputTraceConfirmTableDataSource dataSource,String title) {
        CompanyBsImport companyBsImport = dataSource.getCompanyInfo();
        String companyName = companyBsImport.getCompanyName();
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setPaperSize(PaperSizeType.PAPER_A_4);
        pageSetup.setOrientation(PageOrientationType.LANDSCAPE);

        pageSetup.setHeader(0, "&9&\"ＭＳ フォントサイズ\"" + companyName);
        pageSetup.setHeader(1, "&16&\"ＭＳ フォントサイズ,Bold\"" + title);

        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter
                .ofPattern("yyyy/MM/dd  H:mm", Locale.JAPAN);
        pageSetup.setHeader(2,
                "&9&\"MS フォントサイズ\"" + LocalDateTime.now().format(fullDateTimeFormatter) + "\n" +
                        TextResource.localize("page") + " &P");
        pageSetup.setFitToPagesTall(0);
        pageSetup.setFitToPagesWide(0);
        pageSetup.setCenterHorizontally(true);
        pageSetup.setTopMarginInch(1.5);
        pageSetup.setLeftMarginInch(1.0);
        pageSetup.setRightMarginInch(1.0);
        pageSetup.setHeaderMarginInch(0.8);
        pageSetup.setBottomMarginInch(1.5);
        pageSetup.setZoom(100);
    }
    private void printContents(Worksheet worksheet, OutputTraceConfirmTableDataSource dataSource) {
        try {
            HorizontalPageBreakCollection pageBreaks = worksheet.getHorizontalPageBreaks();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
