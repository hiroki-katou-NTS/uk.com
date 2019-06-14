package nts.uk.file.pr.infra.core.socialinsurance.salaryhealth_healthinsur;

import com.aspose.cells.*;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.dto.CusHealthInsuDto;
import nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth_healthinsur.SalaryHealthInsurExportData;
import nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth_healthinsur.SalaryHealthInsurFileGenerator;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Stateless
public class SalaryHealthInsurAposeFileGenerator extends AsposeCellsReportGenerator implements SalaryHealthInsurFileGenerator {

    private static final String TEMPLATE_FILE_B = "report/QMM008_SMR_EHI.xlsx";
    private static final String REPORT_FILE_NAME = "QMM008社会保険事業所の登録_標準報酬月額表（健康保険）.pdf";

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
            pageSetup.setHeader(0, "&\"ＭＳ ゴシック\"&10 " + companyName);

            // Output item name
            pageSetup.setHeader(1, "&\"ＭＳ ゴシック\"&16 " + "標準報酬月額表（健康保険）");
            // Set header date
            DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d  H:mm:ss", Locale.JAPAN);
            String currentFormattedDate = LocalDateTime.now().format(fullDateTimeFormatter);
            pageSetup.setHeader(2, "&\"ＭＳ ゴシック\"&10 " + currentFormattedDate+"\npage&P");
            //
            Cells cells = ws.getCells();
            //break page

            int page =  exportData.getCusDataDtos().size() / 55;
            if(exportData.getCusDataDtos().size() % 55 != 0){
                page++;
            }
            cells.get(0, 2).setValue(exportData.getOfficeCode());
            cells.get(0, 4).setValue(exportData.getSocialInsuranceName());
            for (int i = 0; i < exportData.getCusDataDtos().size(); i++) {
                if(i >= 1 && i <= page-1 && page != 1 ){
                    wsc.get(wsc.addCopy(0)).setName("sheetName" + i);
                }
                if(i%55 == 0 && i != 0){
                    Worksheet sheet = wsc.get("sheetName" + i / 55);
                    cells = sheet.getCells();
                    rowStart = 4;
                }
                CusHealthInsuDto e = exportData.getCusDataDtos().get(i);
                cells.get(rowStart, COLUMN_START).setValue(e.getHealthInsuranceGrade());
                cells.get(rowStart, COLUMN_START + 1).setValue(e.getStandardMonthlyFee());
                cells.get(rowStart, COLUMN_START + 2).setValue(e.getRewardMonthlyLowerLimit());
                cells.get(rowStart, COLUMN_START + 3).setValue(e.getRewardMonthlyUpperLimit());
                //Insur
                cells.get(rowStart, COLUMN_START + 4).setValue(e.getInHealthInsurancePremium() == null ? "" : new BigDecimal(e.getInHealthInsurancePremium()));
                cells.get(rowStart, COLUMN_START + 5).setValue(e.getInNursingCare()== null ? "": new BigDecimal(e.getInNursingCare()));
                cells.get(rowStart, COLUMN_START + 6).setValue(e.getInSpecInsurancePremium()== null ? "" : new BigDecimal(e.getInSpecInsurancePremium()));
                cells.get(rowStart, COLUMN_START + 7).setValue(e.getInBasicInsurancePremium()== null ? "" : new BigDecimal(e.getInBasicInsurancePremium()));
                //Employee
                cells.get(rowStart, COLUMN_START + 8).setValue(e.getEmHealthInsurancePremium()== null ? "" : new BigDecimal(e.getEmHealthInsurancePremium()));
                cells.get(rowStart, COLUMN_START + 9).setValue(e.getEmNursingCare()== null ? "" : new BigDecimal(e.getEmNursingCare()));
                cells.get(rowStart, COLUMN_START + 10).setValue(e.getEmSpecInsurancePremium()== null? "" : new BigDecimal(e.getEmSpecInsurancePremium()));
                cells.get(rowStart, COLUMN_START + 11).setValue(e.getEmBasicInsurancePremium()== null ? "" : new BigDecimal(e.getEmBasicInsurancePremium()));

                rowStart++;
            }
            reportContext.processDesigner();
            reportContext.saveAsPdf(this.createNewFile(fileContext, this.getReportName(REPORT_FILE_NAME)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
