package nts.uk.file.pr.infra.core.socialinsurance.salaryhealth;

import com.aspose.cells.*;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.dto.CusWelfarePensionDto;
import nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.dto.CusWelfarePensionStandardDto;
import nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth.SalaryHealthExportData;
import nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth.SalaryHealthFileGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Stateless
public class SalaryHealthAposeFileGenerator extends AsposeCellsReportGenerator implements SalaryHealthFileGenerator {

    private static final String TEMPLATE_FILE_B = "report/QMM008社会保険事業所の登録_標準報酬月額表（厚生年金).xlsx";
    private static final String REPORT_FILE_EXTENSION = ".xlsx";
    private static final String FILE_NAME = "QMM008社会保険事業所の登録_標準報酬月額表（厚生年金)";

    private static final int WELFARE_PENSION_GRADE = 2;
    private static final int STANDARD_MONTHLY_FEE = 3;

    private static final int REWARD_MONTHLY_LOWER_LIMIT = 4;
    private static final int REWARD_MONTHLY_UPER_LIMIT = 5;

    private static final int IN_MALE_INSURANCE_PREMIUM = 5;
    private static final int EM_MALE_INSURANCE_PREMIUM = 5;
    private static final int IN_MALE_EXEMPTION_INSURANCE = 5;
    private static final int EM_MALE_EXEMPTION_INSURANCE = 5;

    private static final int IN_FEMALE_INSURANCE_PREMIUM = 6;
    private static final int EM_FEMALE_INSURANCE_PREMIUM = 5;
    private static final int IN_FEMALE_EXEMPTION_INSURANCE = 5;
    private static final int EM_FEMALE_EXEMPTION_INSURANCE = 5;



    @Override
    public void generate(FileGeneratorContext generatorContext, SalaryHealthExportData exportData,List<CusWelfarePensionStandardDto> list) {
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE_B)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            Worksheet worksheet = worksheets.get(0);
            worksheets.setActiveSheetIndex(0);
            this.writeExcel(worksheet,exportData,list);
            reportContext.processDesigner();
            reportContext.saveAsExcel(this.createNewFile(generatorContext,
                    FILE_NAME + GeneralDateTime.now().toString("yyyyMMddHHmmss") + REPORT_FILE_EXTENSION));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void writeExcel(Worksheet ws,SalaryHealthExportData exportData, List<CusWelfarePensionStandardDto> list){

        int rowIndex = 5;
        int count = 0;

        // Set print page
        PageSetup pageSetup = ws.getPageSetup();
        pageSetup.setHeader(0, "&10&\"MS ゴシック\"" + exportData.getCompanyName());

        // Set header date
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d  H:mm", Locale.JAPAN);
        pageSetup.setHeader(2, "&10&\"MS ゴシック\" " + LocalDateTime.now().format(fullDateTimeFormatter) + "\npage &P ");
        HorizontalPageBreakCollection pageBreaks = ws.getHorizontalPageBreaks();
        for(int i = 0; i < Math.round(exportData.getResponseWelfarePension().getCusWelfarePensions().size()/62); i ++){
            try {
                ws.getCells().copyRows(ws.getCells(),0,67,69 );
            }catch (Exception e){

            }
        }
        for (CusWelfarePensionDto cusWelfarePensions : exportData.getResponseWelfarePension().getCusWelfarePensions()) {

            /*ws.getCells().get(rowIndex,WELFARE_PENSION_GRADE).putValue(cusWelfarePensions.getWelfarePensionGrade());
            ws.getCells().get(rowIndex,STANDARD_MONTHLY_FEE).putValue(cusWelfarePensions.getStandardMonthlyFee());
            ws.getCells().get(rowIndex,REWARD_MONTHLY_LOWER_LIMIT).putValue(cusWelfarePensions.getRewardMonthlyLowerLimit());
            ws.getCells().get(rowIndex,REWARD_MONTHLY_UPER_LIMIT).putValue(cusWelfarePensions.getRewardMonthlyUpperLimit());

            ws.getCells().get(rowIndex,IN_MALE_INSURANCE_PREMIUM).putValue(cusWelfarePensions.getInMaleInsurancePremium());
            ws.getCells().get(rowIndex,IN_FEMALE_INSURANCE_PREMIUM).putValue(cusWelfarePensions.getInFemaleInsurancePremium());

            ws.getCells().get(rowIndex,EM_MALE_INSURANCE_PREMIUM).putValue(cusWelfarePensions.getEmMaleInsurancePremium());
            ws.getCells().get(rowIndex,EM_FEMALE_INSURANCE_PREMIUM).putValue(cusWelfarePensions.getEmFemaleInsurancePremium());

            ws.getCells().get(rowIndex,IN_MALE_EXEMPTION_INSURANCE).putValue(cusWelfarePensions.getInMaleExemptionInsurance());
            ws.getCells().get(rowIndex,IN_FEMALE_EXEMPTION_INSURANCE).putValue(cusWelfarePensions.getInFemaleInsurancePremium());

            ws.getCells().get(rowIndex,EM_MALE_EXEMPTION_INSURANCE).putValue(cusWelfarePensions.getEmMaleExemptionInsurance());
            ws.getCells().get(rowIndex,EM_FEMALE_EXEMPTION_INSURANCE).putValue(cusWelfarePensions.getEmFemaleInsurancePremium());
*/

            if(count == 62){
                pageBreaks.add(rowIndex);
                count = 0;
                rowIndex = rowIndex + 6;
            }
            ws.getCells().get(rowIndex,2).putValue(cusWelfarePensions.getWelfarePensionGrade());
            ws.getCells().get(rowIndex,3).putValue(cusWelfarePensions.getStandardMonthlyFee());
            ws.getCells().get(rowIndex,4).putValue(cusWelfarePensions.getRewardMonthlyLowerLimit());
            ws.getCells().get(rowIndex,5).putValue(cusWelfarePensions.getRewardMonthlyUpperLimit());

            ws.getCells().get(rowIndex,6).putValue(cusWelfarePensions.getInMaleInsurancePremium());
            ws.getCells().get(rowIndex,7).putValue(cusWelfarePensions.getInFemaleInsurancePremium());

            ws.getCells().get(rowIndex,8).putValue(cusWelfarePensions.getEmMaleInsurancePremium());
            ws.getCells().get(rowIndex,9).putValue(cusWelfarePensions.getEmFemaleInsurancePremium());

            ws.getCells().get(rowIndex,10).putValue(cusWelfarePensions.getInMaleExemptionInsurance() == null ? "－" : cusWelfarePensions.getInMaleExemptionInsurance());
            ws.getCells().get(rowIndex,11).putValue(cusWelfarePensions.getInFemaleExemptionInsurance() == null ? "－" : cusWelfarePensions.getInFemaleExemptionInsurance());

            ws.getCells().get(rowIndex,12).putValue(cusWelfarePensions.getEmMaleExemptionInsurance() == null ? "－" : cusWelfarePensions.getEmMaleExemptionInsurance());
            ws.getCells().get(rowIndex,13).putValue(cusWelfarePensions.getEmFemaleExemptionInsurance() == null ? "－" : cusWelfarePensions.getEmFemaleExemptionInsurance());

            Optional<CusWelfarePensionStandardDto> cusWelfarePensionStandardDto  = list.stream().filter(x -> x.getWelfarePensionGrade() == cusWelfarePensions.getWelfarePensionGrade()).findFirst();
            if(cusWelfarePensionStandardDto.isPresent()){
                ws.getCells().get(rowIndex,14).putValue(cusWelfarePensionStandardDto.get().getChildCareContribution());
            }else{
                ws.getCells().get(rowIndex,14).putValue("");
            }

            rowIndex ++;
            count++;
        }
    }



}
