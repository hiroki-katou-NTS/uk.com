package nts.uk.file.pr.infra.core.socialinsurance.salaryhealth_healthinsur;

import com.aspose.cells.*;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.dto.CusHealthInsuDto;
import nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth.SalaryHealthExportData;
import nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth.SalaryHealthFileGenerator;
import nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth_healthinsur.SalaryHealthInsurExportData;
import nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth_healthinsur.SalaryHealthInsurFileGenerator;
import nts.uk.ctx.pr.file.app.core.wageprovision.unitpricename.SalaryPerUnitSetExportData;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Stateless
public class SalaryHealthInsurAposeFileGenerator extends AsposeCellsReportGenerator implements SalaryHealthInsurFileGenerator {

    private static final String TEMPLATE_FILE_B = "report/TEMPLATE_QMM008_E.xlsx";
    private static final String REPORT_FILE_NAME = "QMM008社会保険事業所の登録_標準報酬月額表（健康保険）.xlsx";

    @Inject
    private CompanyAdapter company;

    private static final int COLUMN_START = 1;

    @Override
    public void generate(FileGeneratorContext fileContext, SalaryHealthInsurExportData exportData) {
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE_B)) {
            int rowStart = 4;
            Workbook wb = reportContext.getWorkbook();
            WorksheetCollection wsc = wb.getWorksheets();
            Worksheet ws = wsc.get(0);
            ws.setName(TextResource.localize("QMM008_212"));
            //set headler
            // Company name
            String companyName = this.company.getCurrentCompany().map(CompanyInfor::getCompanyName).orElse("");
            PageSetup pageSetup = ws.getPageSetup();
            pageSetup.setHeader(0, "&8&\"MS ゴシック\"" + companyName);

            // Output item name
            pageSetup.setHeader(1, "&16&\"MS ゴシック\"" + "標準報酬月額表（健康保険）");

            // Set header date
            DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss", Locale.JAPAN);
            pageSetup.setHeader(2, "&8&\"MS ゴシック\" " + LocalDateTime.now().format(fullDateTimeFormatter) + "\npage &P ");
            Cells cells = ws.getCells();
            //break page
            HorizontalPageBreakCollection pageBreaks = ws.getHorizontalPageBreaks();


            int page =  exportData.getCusDataDtos().size() / 55;
            boolean isFirst = (page == 1 ) && page%55 == 0 ? true : false;

            cells.get(0, 2).setValue(exportData.getOfficeCode());
            cells.get(0, 3).setValue(exportData.getSocialInsuranceName());
            cells.get(0, 5).setValue(exportData.getDisplayStart());
            cells.get(0, 6).setValue(exportData.getDisplayEnd());

            for (int i = 0; i < exportData.getCusDataDtos().size(); i++) {
                if(i < page && !isFirst ){
                    ws.getCells().copyRows(cells,0, 62*(i+1), 62 );
                    pageBreaks.add(62*(i+1));
                }
                if(i%55 == 0 && i != 0){
                    rowStart = rowStart+7;
                }
                CusHealthInsuDto e = exportData.getCusDataDtos().get(i);
                cells.get(rowStart, COLUMN_START).setValue(e.getHealthInsuranceGrade());
                cells.get(rowStart, COLUMN_START + 1).setValue(e.getStandardMonthlyFee());
                cells.get(rowStart, COLUMN_START + 2).setValue(e.getRewardMonthlyLowerLimit());
                cells.get(rowStart, COLUMN_START + 3).setValue(e.getRewardMonthlyUpperLimit());
                //Insur
                cells.get(rowStart, COLUMN_START + 4).setValue(e.getInHealthInsurancePremium());
                cells.get(rowStart, COLUMN_START + 5).setValue(e.getInNursingCare());
                cells.get(rowStart, COLUMN_START + 6).setValue(e.getInSpecInsurancePremium());
                cells.get(rowStart, COLUMN_START + 7).setValue(e.getInBasicInsurancePremium());
                //Employee
                cells.get(rowStart, COLUMN_START + 8).setValue(e.getEmHealthInsurancePremium());
                cells.get(rowStart, COLUMN_START + 9).setValue(e.getEmNursingCare());
                cells.get(rowStart, COLUMN_START + 10).setValue(e.getEmSpecInsurancePremium());
                cells.get(rowStart, COLUMN_START + 11).setValue(e.getEmBasicInsurancePremium());

                rowStart++;
            }


            reportContext.processDesigner();
            reportContext.saveAsExcel(this.createNewFile(fileContext, this.getReportName(REPORT_FILE_NAME)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    private void printSalaryHealthy(Worksheet worksheet, List<Object[]> data){
        Cells cells = worksheet.getCells();
        int numRow = 2;
        int numColumn = 12;
        int rowStart = 4;
        int columnStart = 1;
        fillData(cells, data, numRow, numColumn, rowStart, columnStart);
    }

    private void fillData(Cells cells, List<Object[]> data, int numRow, int numColumn, int startRow, int startColumn) {
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
    }
}
