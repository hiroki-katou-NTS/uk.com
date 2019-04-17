package nts.uk.file.pr.infra.core.socialinsurance.healthinsurance;

import com.aspose.cells.*;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.InsurancePremiumFractionClassification;
import nts.uk.ctx.pr.file.app.core.socialinsurance.healthinsurance.HealthInsuranceExportData;
import nts.uk.ctx.pr.file.app.core.socialinsurance.healthinsurance.HealthInsuranceFileGenerator;
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
public class HealthInsuranceAposeFileGenerator extends AsposeCellsReportGenerator implements HealthInsuranceFileGenerator {

    private static final String TEMPLATE_FILE = "report/QMM008社会保険事業所の登録_健康保険料率一覧.xlsx";
    private static final String REPORT_FILE_EXTENSION = ".pdf";
    private static final String FILE_NAME = "QMM008-社会保険事業所の登録_健康保険料率一覧";
    private static final int ROW_IN_PAGE = 49;
    private static final int RECORD_IN_PAGE = 45;

    @Override
    public void generate(FileGeneratorContext generatorContext, HealthInsuranceExportData exportData) {
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            reportContext.setHeader(0, exportData.getCompanyName());
            String time = GeneralDateTime.now().toString();
            reportContext.setHeader(2,  time  + "\n page &P");
            Worksheet firstSheet = worksheets.get(0);
            Cells cells = firstSheet.getCells();
            cells.get(0,1).setValue("対象年月：　"+ convertYearMonth(exportData.getStartDate()));
            int pageHealthyData = exportData.getHealthMonth().size() / RECORD_IN_PAGE;
            int pageBonusData = exportData.getBonusHealth().size() / RECORD_IN_PAGE;
            createTable(firstSheet, pageHealthyData, pageBonusData);
            printDataHealthy(firstSheet, exportData.getHealthMonth());
            worksheets.setActiveSheetIndex(0);
            reportContext.processDesigner();
            reportContext.saveAsPdf(this.createNewFile(generatorContext,
                    FILE_NAME + GeneralDateTime.now().toString("yyyyMMddHHmmss") + REPORT_FILE_EXTENSION));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String convertYearMonth(Integer startYearMonth){
        return startYearMonth.toString().substring(0,3) + "/" + startYearMonth.toString().substring(4,6);
    }

    private void createTable(Worksheet worksheet,int pageMonth, int pageBonus){
        Cells cells = worksheet.getCells();
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setPaperSize(PaperSizeType.PAPER_A_4);
        pageSetup.setOrientation(PageOrientationType.LANDSCAPE);
        pageSetup.setHeader(0, "&\"ＭＳ ゴシック\"&9 " + "dfdf");
        pageSetup.setHeader(1,"労働保険事業所の登録");
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d  H:mm", Locale.JAPAN);
        String currentFormattedDate = LocalDateTime.now().format(fullDateTimeFormatter);
        pageSetup.setHeader(2, "&\"ＭＳ ゴシック\"&9 " + currentFormattedDate+"\npage&P");
        pageSetup.setFitToPagesTall(1);
        pageSetup.setFitToPagesWide(1);
        int indexMonth = ROW_IN_PAGE - 1;
        int indexBonus = (ROW_IN_PAGE - 1) * (pageMonth + 2);
        for(int i = 0 ; i< pageBonus; i++) {
                try {
                    cells.copyRows(cells, ROW_IN_PAGE - 1, indexBonus , ROW_IN_PAGE);
                    indexBonus = indexBonus + ROW_IN_PAGE;
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        if( pageBonus == 0 && pageMonth != 0) {
            try {
                cells.copyRows(cells, ROW_IN_PAGE , indexBonus + 2 , ROW_IN_PAGE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for(int i = 0 ; i< pageMonth; i++) {
            try {
                cells.copyRows(cells, 0, indexMonth , ROW_IN_PAGE);
                indexMonth = indexMonth + ROW_IN_PAGE - 1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void printDataHealthy(Worksheet worksheet, List<Object[]> data) {
        Cells cells = worksheet.getCells();
        int numColumn = 12;
        int rowStart = 3;
        int columnStart = 1;
        fillData(cells, data, numColumn, rowStart, columnStart);
    }

    private void printDataBonus(Worksheet worksheet, List<Object[]> data, int rowStart) {
        Cells cells = worksheet.getCells();
        int numColumn = 12;
        int columnStart = 1;
        fillData(cells, data, numColumn, rowStart, columnStart);
    }

    private void fillData(Cells cells, List<Object[]> data, int numColumn, int startRow, int startColumn) {
        try {
            for (int i = 0; i < data.size(); i++) {
                Object[] dataRow = data.get(i);
                for (int j = 0; j < numColumn; j++) {
                    if(j == 6 || j == 11) {
                        cells.get(i + startRow, j + startColumn).setValue(dataRow[j] != null
                                ? TextResource.localize(EnumAdaptor.valueOf(((BigDecimal) dataRow[j]).intValue(), InsurancePremiumFractionClassification.class).nameId) : "");
                    } else {
                        cells.get(i + startRow, j + startColumn).setValue(dataRow[j] != null ? dataRow[j] : "");
                    }
                }
                if((i + 1) % (RECORD_IN_PAGE) == 0 && i > 0) {
                    startRow = startRow + 3;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
