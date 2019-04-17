package nts.uk.file.pr.infra.core.socialinsurance.salaryhealth;

import com.aspose.cells.*;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.dto.CusWelfarePensionDto;
import nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.dto.CusWelfarePensionStandardDto;
import nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth.SalaryHealthExportData;
import nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth.SalaryHealthFileGenerator;
import nts.uk.shr.com.i18n.TextResource;
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

    private static final String TEMPLATE_FILE = "report/QMM008社会保険事業所の登録_標準報酬月額表（厚生年金).xlsx";
    private static final String FILE_NAME = "QMM008社会保険事業所の登録_標準報酬月額表（厚生年金)";

    @Override
    public void generate(FileGeneratorContext generatorContext, SalaryHealthExportData exportData,List<CusWelfarePensionStandardDto> list, String socialInsuranceCode, String socialInsuranceName) {
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            Worksheet worksheet = worksheets.get(0);
            Worksheet worksheet1 = worksheets.get(1);

            worksheet.setName(TextResource.localize("QMM008_49"));
            this.writeExcel(worksheet,worksheet1,exportData,list,socialInsuranceCode,socialInsuranceName);
            reportContext.processDesigner();
            reportContext.saveAsPdf(this.createNewFile(generatorContext,
                    FILE_NAME + "_" + GeneralDateTime.now().toString("yyyyMMddHHmmss") + ".pdf"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void writeExcel(Worksheet ws, Worksheet ws1,SalaryHealthExportData exportData, List<CusWelfarePensionStandardDto> list, String socialInsuranceCode, String socialInsuranceName){

        int rowIndex = 5;
        int tempRowIndex;

        // Set print page
        PageSetup pageSetup = ws.getPageSetup();
        pageSetup.setHeader(0, "&10&\"MS ゴシック\"" + exportData.getCompanyName());

        // Set header date
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d  H:mm", Locale.JAPAN);
        pageSetup.setHeader(2, "&10&\"MS ゴシック\" " + LocalDateTime.now().format(fullDateTimeFormatter) + "\npage &P ");

        HorizontalPageBreakCollection pageBreaks = ws.getHorizontalPageBreaks();
        ws.getCells().get(0,3).putValue(socialInsuranceCode);
        ws.getCells().get(0,5).putValue(socialInsuranceName);

        ws1.getCells().get(0,3).putValue(socialInsuranceCode);
        ws1.getCells().get(0,5).putValue(socialInsuranceName);

        for (CusWelfarePensionDto cusWelfarePensions : exportData.getResponseWelfarePension().getCusWelfarePensions()) {
            if(rowIndex >= 67){
                tempRowIndex = rowIndex - 62;
                ws1.getCells().get(tempRowIndex,2).putValue(cusWelfarePensions.getWelfarePensionGrade());
                ws1.getCells().get(tempRowIndex,3).putValue(cusWelfarePensions.getStandardMonthlyFee());
                ws1.getCells().get(tempRowIndex,4).putValue(cusWelfarePensions.getRewardMonthlyLowerLimit());
                ws1.getCells().get(tempRowIndex,5).putValue(cusWelfarePensions.getRewardMonthlyUpperLimit());

                ws1.getCells().get(tempRowIndex,6).putValue(cusWelfarePensions.getInMaleInsurancePremium());
                ws1.getCells().get(tempRowIndex,7).putValue(cusWelfarePensions.getInFemaleInsurancePremium());

                ws1.getCells().get(tempRowIndex,8).putValue(cusWelfarePensions.getEmMaleInsurancePremium());
                ws1.getCells().get(tempRowIndex,9).putValue(cusWelfarePensions.getEmFemaleInsurancePremium());

                ws1.getCells().get(tempRowIndex,10).putValue(cusWelfarePensions.getInMaleExemptionInsurance() == null ? "－" : cusWelfarePensions.getInMaleExemptionInsurance());
                ws1.getCells().get(tempRowIndex,11).putValue(cusWelfarePensions.getInFemaleExemptionInsurance() == null ? "－" : cusWelfarePensions.getInFemaleExemptionInsurance());

                ws1.getCells().get(tempRowIndex,12).putValue(cusWelfarePensions.getEmMaleExemptionInsurance() == null ? "－" : cusWelfarePensions.getEmMaleExemptionInsurance());
                ws1.getCells().get(tempRowIndex,13).putValue(cusWelfarePensions.getEmFemaleExemptionInsurance() == null ? "－" : cusWelfarePensions.getEmFemaleExemptionInsurance());

                Optional<CusWelfarePensionStandardDto> cusWelfarePensionStandardDto  = list.stream().filter(x -> x.getWelfarePensionGrade() == cusWelfarePensions.getWelfarePensionGrade()).findFirst();
                if(cusWelfarePensionStandardDto.isPresent()){
                    ws1.getCells().get(tempRowIndex,14).putValue(cusWelfarePensionStandardDto.get().getChildCareContribution());
                }else{
                    ws1.getCells().get(tempRowIndex,14).putValue("");
                }

            }else {
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
            }
            rowIndex ++;
        }
    }



}
