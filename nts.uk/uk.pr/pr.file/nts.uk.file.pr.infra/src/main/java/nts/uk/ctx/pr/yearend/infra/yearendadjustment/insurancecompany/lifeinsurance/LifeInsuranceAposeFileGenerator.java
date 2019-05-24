package nts.uk.ctx.pr.yearend.infra.yearendadjustment.insurancecompany.lifeinsurance;

import com.aspose.cells.*;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.InsurancePremiumFractionClassification;
import nts.uk.ctx.pr.yearend.app.yearendadjustment.insurancecompany.lifeinsurance.LifeInsuranceExportData;
import nts.uk.ctx.pr.yearend.app.yearendadjustment.insurancecompany.lifeinsurance.LifeInsuranceFileGenerator;
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
public class LifeInsuranceAposeFileGenerator extends AsposeCellsReportGenerator implements LifeInsuranceFileGenerator {

    private static final String TEMPLATE_FILE = "report/QMM031_INS_LIFE.xlsx";
    private static final String REPORT_FILE_EXTENSION = ".pdf";
    private static final String FILE_NAME = "QMM031保険会社の登録_生命保険_";
    private static final int RECORD_IN_PAGE = 39;

    @Override
    public void generate(FileGeneratorContext generatorContext, LifeInsuranceExportData exportData) {
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            int page = exportData.getLifeInsurance().size() == RECORD_IN_PAGE ? 1 : exportData.getLifeInsurance().size() / RECORD_IN_PAGE + 1;
            createTable(worksheets, page, exportData.getCompanyName());
            printData(worksheets, exportData.getLifeInsurance());
            worksheets.removeAt(0);
            worksheets.setActiveSheetIndex(0);
            reportContext.processDesigner();
            reportContext.saveAsPdf(this.createNewFile(generatorContext,
                    FILE_NAME + GeneralDateTime.now().toString("yyyyMMddHHmmss") + REPORT_FILE_EXTENSION));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void createTable(WorksheetCollection worksheets,int pageMonth, String companyName) throws Exception {
        String sheetName = "life_insurance";
        Worksheet worksheet = worksheets.get(0);
        settingPage(worksheet, companyName);
        for(int i = 0 ; i< pageMonth; i++) {
            worksheets.get(worksheets.addCopy(0)).setName(sheetName + i);
        }
    }

    private void settingPage(Worksheet worksheet, String companyName){
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setPaperSize(PaperSizeType.PAPER_A_4);
        pageSetup.setOrientation(PageOrientationType.LANDSCAPE);
        pageSetup.setHeader(0, "&\"ＭＳ ゴシック\"&10 " + companyName);
        pageSetup.setHeader(1, "&\"ＭＳ ゴシック\"&16 "+ "保険会社の登録　生命保険");
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d  H:mm:ss", Locale.JAPAN);
        String currentFormattedDate = LocalDateTime.now().format(fullDateTimeFormatter);
        pageSetup.setHeader(2, "&\"ＭＳ ゴシック\"&10 " + currentFormattedDate+"\npage&P");
        pageSetup.setFitToPagesTall(1);
        pageSetup.setFitToPagesWide(1);
    }

    private void printData(WorksheetCollection worksheets, List<Object[]> data) {
        String sheetName = "life_insurance";
        int numColumn = 5;
        int columnStart = 2;
        fillData(worksheets, data, numColumn, columnStart, sheetName);
    }

    private void fillData(WorksheetCollection worksheets, List<Object[]> data, int numColumn, int startColumn, String sheetName) {
        try {
            int rowStart = 3;
            Worksheet sheet = worksheets.get(0);
            Cells cells = sheet.getCells();
            for (int i = 0; i < data.size(); i++) {
                if(i % RECORD_IN_PAGE == 0) {
                    sheet = worksheets.get(sheetName + i/RECORD_IN_PAGE);
                    cells = sheet.getCells();
                    rowStart = 3;
                }
                Object[] dataRow = data.get(i);
                for (int j = 0; j < numColumn; j++) {
                    if(j == 2) {
                        cells.get(rowStart, j + startColumn).setValue(dataRow[j] != null ? getTextAtrOfInsuranceType(((BigDecimal) dataRow[j]).intValue()) : "");
                    } else {
                        cells.get(rowStart, j + startColumn).setValue(dataRow[j] != null ? dataRow[j] : "");
                    }
                }
                rowStart++;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getTextAtrOfInsuranceType(int value){
        if(value == 0) {
            return TextResource.localize("Enum_GENERAL_LIFE_INSURANCE");
        }
        if(value == 1){
            return TextResource.localize("Enum_NURSING_MEDICAL_INSURANCE");
        }
        return TextResource.localize("Enum_INDIVIDUAL_ANNUITY_INSURANCE");
    }
}
