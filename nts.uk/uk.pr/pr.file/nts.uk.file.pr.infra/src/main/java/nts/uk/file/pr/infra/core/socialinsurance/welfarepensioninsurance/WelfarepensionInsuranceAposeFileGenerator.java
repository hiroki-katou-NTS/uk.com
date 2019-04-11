package nts.uk.file.pr.infra.core.socialinsurance.welfarepensioninsurance;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.file.app.core.socialinsurance.Export;
import nts.uk.ctx.pr.file.app.core.socialinsurance.welfarepensioninsurance.WelfarepensionInsuranceExportData;
import nts.uk.ctx.pr.file.app.core.socialinsurance.welfarepensioninsurance.WelfarepensionInsuranceFileGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;

@Stateless
public class WelfarepensionInsuranceAposeFileGenerator extends AsposeCellsReportGenerator implements WelfarepensionInsuranceFileGenerator {

    private static final String TEMPLATE_FILE_B = "report/QMM008社会保険事業所の登録_健康保険料率一覧.xlsx";
    private static final String TEMPLATE_FILE_C = "report/TEMPLATE_QMM008_C.xlsx";
    private static final String TEMPLATE_FILE_I = "report/TEMPLATE_QMM008_I.xlsx";
    private static final String TEMPLATE_FILE_D = "report/TEMPLATE_QMM008_D.xlsx";
    private static final String TEMPLATE_FILE_F = "report/TEMPLATE_QMM008_F.xlsx";
    private static final String REPORT_FILE_EXTENSION = ".xlsx";
    private static final String FILE_NAME_B = "QMM008-社会保険事業所の登録_健康保険料率一覧";

    @Override
    public void generate(FileGeneratorContext generatorContext, WelfarepensionInsuranceExportData exportData) {
        /*try (AsposeCellsReportContext reportContext = this.createContext(getTemplate(exportData.getExport()))) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            reportContext.setHeader(0,exportData.getCompanyName());
            reportContext.setHeader(2, "&D　&T\n&P#{PAGE}");
            Worksheet firstSheet = worksheets.get(0);
            if (exportData.getExport() == Export.HEALTHY.value) {
                printDataHealthy(firstSheet, exportData.getData());
                firstSheet.setName(TextResource.localize("QMM008_212"));
            }
            if (exportData.getExport() == Export.WELFARE_PENSION.value){
                printDataWelfarePension(firstSheet, exportData.getData());
            }
            if(exportData.getExport() == Export.SOCIAL_INSURANCE_OFFICE.value) {
                printDataSocialOffice(firstSheet, exportData.getData());
            }
            if(exportData.getExport() == Export.CONTRIBUTION_RATE.value) {
                printContributionRate(firstSheet, exportData.getData());
            }
            if(exportData.getExport() == Export.SALARY_HEALTHY.value) {
                printSalaryHealthy(firstSheet, exportData.getData());
            }
            worksheets.setActiveSheetIndex(0);
            reportContext.processDesigner();
            reportContext.saveAsExcel(this.createNewFile(generatorContext,
                    getFileName(exportData.getExport()) + GeneralDateTime.now().toString("yyyyMMddHHmmss") + REPORT_FILE_EXTENSION));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/

    }

   /* private void printDataHealthy(Worksheet worksheet, List<Object[]> data) {
        Cells cells = worksheet.getCells();
        int numRow = 47;
        int numColumn = 12;
        int rowStart = 3;
        int columnStart = 1;
        fillData(cells, data, numRow, numColumn, rowStart, columnStart);
    }

    private void printDataSocialOffice(Worksheet worksheet, List<Object[]> data){
        Cells cells = worksheet.getCells();
        int numRow = 2;
        int numColumn = 17;
        int rowStart = 4;
        int columnStart = 1;
        fillData(cells, data, numRow, numColumn, rowStart, columnStart);
    }

    private void printContributionRate(Worksheet worksheet, List<Object[]> data) {
        Cells cells = worksheet.getCells();
        int numRow = 2;
        int numColumn = 13;
        int rowStart = 6;
        int columnStart = 1;
        fillData(cells, data, numRow, numColumn, rowStart, columnStart);
    }

    private void printSalaryHealthy(Worksheet worksheet, List<Object[]> data){
        Cells cells = worksheet.getCells();
        int numRow = 2;
        int numColumn = 12;
        int rowStart = 4;
        int columnStart = 1;
        fillData(cells, data, numRow, numColumn, rowStart, columnStart);
    }

    private void printDataWelfarePension(Worksheet worksheet, List<Object[]> data) {
        Cells cells = worksheet.getCells();
        int rowStart = 5;
        int numRow = 2;
        int numColumn = 18;
        int columnStart = 1;
        fillData(cells, data, numRow, numColumn, rowStart, columnStart);
    }*/

    /*private void fillData(Cells cells, List<Object[]> data, int numRow, int numColumn, int startRow, int startColumn) {
        try {
            for (int i = 0; i < data.size(); i++) {
                Object[] dataRow = data.get(i);
                for (int j = 0; j < numColumn; j++) {
                    cells.get(i + startRow, j + startColumn).setValue(dataRow[j] != null ? dataRow[j] : "");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/
}
