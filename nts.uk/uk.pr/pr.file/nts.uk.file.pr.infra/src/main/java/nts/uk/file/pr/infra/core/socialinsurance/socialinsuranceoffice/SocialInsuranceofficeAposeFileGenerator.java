package nts.uk.file.pr.infra.core.socialinsurance.socialinsuranceoffice;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.file.app.core.socialinsurance.Export;
import nts.uk.ctx.pr.file.app.core.socialinsurance.SocialInsuranceExportData;
import nts.uk.ctx.pr.file.app.core.socialinsurance.SocialInsuranceFileGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;

@Stateless
public class SocialInsuranceofficeAposeFileGenerator extends AsposeCellsReportGenerator implements SocialInsuranceFileGenerator {

    private static final String TEMPLATE_FILE_B = "report/QMM008社会保険事業所の登録_健康保険料率一覧.xlsx";
    private static final String TEMPLATE_FILE_C = "report/TEMPLATE_QMM008_C.xlsx";
    private static final String TEMPLATE_FILE_I = "report/TEMPLATE_QMM008_I.xlsx";
    private static final String TEMPLATE_FILE_D = "report/TEMPLATE_QMM008_D.xlsx";
    private static final String TEMPLATE_FILE_F = "report/TEMPLATE_QMM008_F.xlsx";
    private static final String REPORT_FILE_EXTENSION = ".xlsx";
    private static final String FILE_NAME_B = "QMM008-社会保険事業所の登録_健康保険料率一覧";

    @Override
    public void generate(FileGeneratorContext generatorContext, SocialInsuranceExportData exportData) {
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

    private String getFileName(int export){
        if(export == Export.HEALTHY.value) {
            return FILE_NAME_B;
        }
        return "";
    }
    private String getTemplate(int export){
        if(export == Export.HEALTHY.value) {
            return TEMPLATE_FILE_B;
        }
        if(export == Export.WELFARE_PENSION.value) {
            return TEMPLATE_FILE_C;
        }
        if(export == Export.SOCIAL_INSURANCE_OFFICE.value) {
            return TEMPLATE_FILE_I;
        }
        if(export == Export.CONTRIBUTION_RATE.value) {
            return TEMPLATE_FILE_D;
        }
        return TEMPLATE_FILE_F;
    }

    /*private void printDataHealthy(Worksheet worksheet, List<Object[]> data) {
        try {
            Cells cells = worksheet.getCells();
            int rowStart = 1;
            int numRow = 9;
            // Main Data
            for (int i = 0; i < data.size(); i++) {
                Object[] dataRow = data.get(i);
                cells.copyRows(cells, rowStart , rowStart + numRow, numRow);
                for (int j = 0; j < numRow; j++) {
                    if( j == 0) {
                        cells.get(rowStart + j, 2).setValue(dataRow[0] != null ? dataRow[0] : "");
                        cells.get(rowStart + j, 4).setValue(dataRow[1] != null ? dataRow[1] : "");
                    }
                    if(j == 1 ) {
                        cells.get(rowStart + j, 2).setValue(dataRow[2] != null ? dataRow[2] : "");
                        cells.get(rowStart + j, 4).setValue(dataRow[3] != null ? dataRow[3] : "");
                    }
                    if(j == 2) {
                        cells.get(rowStart + j, 2).setValue(dataRow[4] != null ? dataRow[4] : "");
                        cells.get(rowStart + j, 4).setValue(dataRow[5] != null ? dataRow[5] : "");
                    }
                    if(j == 3) {
                        cells.get(rowStart + j, 2).setValue(dataRow[6] != null ? dataRow[6] : "");
                        cells.get(rowStart + j, 4).setValue(dataRow[7] != null ? dataRow[7] : "");

                    }
                    if(j == 4) {
                        cells.get(rowStart + j, 2).setValue(dataRow[8] != null ? dataRow[8] : "");
                        cells.get(rowStart + j, 13).setValue(dataRow[9] != null ? dataRow[9] : "");
                    }
                    if(j == 5) {
                        cells.get(rowStart + j, 2).setValue(dataRow[10] != null ? dataRow[10] : "");
                        cells.get(rowStart + j, 5).setValue(dataRow[11] != null ? dataRow[11] : "");
                        cells.get(rowStart + j, 8).setValue(dataRow[12] != null ? dataRow[12] : "");
                        cells.get(rowStart + j, 10).setValue(dataRow[13] != null ? dataRow[13] : "");
                        cells.get(rowStart + j, 11).setValue(dataRow[14] != null ? dataRow[14] : "");
                        cells.get(rowStart + j, 13).setValue(dataRow[15] != null ? dataRow[15] : "");
                    }
                    if(j == 6) {
                        cells.get(rowStart + j, 2).setValue(dataRow[16] != null ? dataRow[16] : "");
                        cells.get(rowStart + j, 5).setValue(dataRow[17] != null ? dataRow[17] : "");
                        cells.get(rowStart + j, 8).setValue(dataRow[18] != null ? dataRow[18] : "");
                        cells.get(rowStart + j, 10).setValue(dataRow[19] != null ? dataRow[19] : "");
                        cells.get(rowStart + j, 11).setValue(dataRow[20] != null ? dataRow[20] : "");
                    }
                    if(j == 7) {
                        cells.get(rowStart + j, 1).setValue(dataRow[21] != null ? dataRow[21] : "");
                    }
                }
                rowStart += numRow;
            }
            cells.deleteRows(rowStart, numRow);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

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
