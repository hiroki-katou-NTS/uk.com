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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Stateless
public class SalaryHealthAposeFileGenerator extends AsposeCellsReportGenerator implements SalaryHealthFileGenerator {

    private static final String TEMPLATE_FILE = "report/QMM008_SMR_EPI.xlsx";
    private static final String FILE_NAME = "QMM008社会保険事業所の登録_標準報酬月額表（厚生年金)";
    private static final int MAX_LINE = 62;
    @Override
    public void generate(FileGeneratorContext generatorContext, SalaryHealthExportData exportData,List<CusWelfarePensionStandardDto> list, String socialInsuranceCode, String socialInsuranceName) {
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            worksheets.get(worksheets.addCopy(0)).setName("salary");
            this.writeExcel(worksheets, exportData,list,socialInsuranceCode,socialInsuranceName);
            reportContext.processDesigner();
            reportContext.saveAsPdf(this.createNewFile(generatorContext,
                    FILE_NAME + "_" + GeneralDateTime.now().toString("yyyyMMddHHmmss") + ".pdf"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void writeExcel(WorksheetCollection wsc ,SalaryHealthExportData exportData, List<CusWelfarePensionStandardDto> list, String socialInsuranceCode, String socialInsuranceName){

        Boolean flag = exportData.getResponseWelfarePension().getDisplay();

        Worksheet ws = wsc.get(0);
        Worksheet ws1 = wsc.get(1);


        int rowIndex = 5;
        int tempRowIndex;

        // Set print page 1
        PageSetup pageSetup = ws.getPageSetup();
        pageSetup.setHeader(0, "&\"ＭＳ ゴシック\"&10 " + exportData.getCompanyName());

        // Set header date
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d  HH:mm:ss", Locale.JAPAN);
        pageSetup.setHeader(2, "&\"ＭＳ ゴシック\"&10 " + LocalDateTime.now().format(fullDateTimeFormatter) + "\npage&P ");

        // Set print page 1
        PageSetup pageSetup1 = ws1.getPageSetup();
        pageSetup1.setHeader(0, "&\"ＭＳ ゴシック\"&10 " + exportData.getCompanyName());

        // Set header date
        pageSetup1.setHeader(2, "&\"ＭＳ ゴシック\"&10 " + LocalDateTime.now().format(fullDateTimeFormatter) + "\npage&P ");

        ws.getCells().get(0,3).putValue(socialInsuranceCode);
        ws.getCells().get(0,5).putValue(socialInsuranceName);

        ws1.getCells().get(0,3).putValue(socialInsuranceCode);
        ws1.getCells().get(0,5).putValue(socialInsuranceName);

        if( list.size() < MAX_LINE){
            wsc.removeAt(1);
        }

        for (CusWelfarePensionDto cusWelfarePensions : exportData.getResponseWelfarePension().getCusWelfarePensions()) {
            if(rowIndex >= 67){
                tempRowIndex = rowIndex - MAX_LINE;
                ws1.getCells().get(tempRowIndex,2).putValue(new BigDecimal(cusWelfarePensions.getWelfarePensionGrade()));
                ws1.getCells().get(tempRowIndex,3).putValue(new BigDecimal(cusWelfarePensions.getStandardMonthlyFee()));
                ws1.getCells().get(tempRowIndex,4).putValue(new BigDecimal(cusWelfarePensions.getRewardMonthlyLowerLimit()));
                ws1.getCells().get(tempRowIndex,5).putValue(new BigDecimal(cusWelfarePensions.getRewardMonthlyUpperLimit()));

                ws1.getCells().get(tempRowIndex,6).putValue(cusWelfarePensions.getInMaleInsurancePremium() == null ? null : new BigDecimal(cusWelfarePensions.getInMaleInsurancePremium()));
                ws1.getCells().get(tempRowIndex,7).putValue(cusWelfarePensions.getInFemaleInsurancePremium() == null ? null : new BigDecimal(cusWelfarePensions.getInFemaleInsurancePremium()));
                ws1.getCells().get(tempRowIndex,8).putValue(cusWelfarePensions.getEmMaleInsurancePremium() == null ? null : new BigDecimal(cusWelfarePensions.getEmMaleInsurancePremium()));
                ws1.getCells().get(tempRowIndex,9).putValue(cusWelfarePensions.getEmFemaleInsurancePremium() == null ? null : new BigDecimal(cusWelfarePensions.getEmFemaleInsurancePremium()));

                this.putValue(ws1,tempRowIndex,flag,cusWelfarePensions);

                Optional<CusWelfarePensionStandardDto> cusWelfarePensionStandardDto  = list.stream().filter(x -> x.getWelfarePensionGrade() == cusWelfarePensions.getWelfarePensionGrade()).findFirst();
                if(cusWelfarePensionStandardDto.isPresent()){
                    ws1.getCells().get(tempRowIndex,14).putValue(cusWelfarePensionStandardDto.get().getChildCareContribution() == null ? null : new BigDecimal(cusWelfarePensionStandardDto.get().getChildCareContribution()));
                }else{
                    ws1.getCells().get(tempRowIndex,14).putValue("");
                }

            }else {
                ws.getCells().get(rowIndex,2).putValue(new BigDecimal(cusWelfarePensions.getWelfarePensionGrade()));
                ws.getCells().get(rowIndex,3).putValue(new BigDecimal(cusWelfarePensions.getStandardMonthlyFee()));
                ws.getCells().get(rowIndex,4).putValue(new BigDecimal(cusWelfarePensions.getRewardMonthlyLowerLimit()));
                ws.getCells().get(rowIndex,5).putValue(new BigDecimal(cusWelfarePensions.getRewardMonthlyUpperLimit()));

                ws.getCells().get(rowIndex,6).putValue(cusWelfarePensions.getInMaleInsurancePremium() == null ? null : new BigDecimal(cusWelfarePensions.getInMaleInsurancePremium()));
                ws.getCells().get(rowIndex,7).putValue(cusWelfarePensions.getInFemaleInsurancePremium() == null ? null : new BigDecimal(cusWelfarePensions.getInFemaleInsurancePremium()));
                ws.getCells().get(rowIndex,8).putValue(cusWelfarePensions.getEmMaleInsurancePremium() == null ? null : new BigDecimal(cusWelfarePensions.getEmMaleInsurancePremium()));
                ws.getCells().get(rowIndex,9).putValue(cusWelfarePensions.getEmFemaleInsurancePremium() == null ? null : new BigDecimal(cusWelfarePensions.getEmFemaleInsurancePremium()));

                this.putValue(ws,rowIndex,flag,cusWelfarePensions);

                Optional<CusWelfarePensionStandardDto> cusWelfarePensionStandardDto  = list.stream().filter(x -> x.getWelfarePensionGrade() == cusWelfarePensions.getWelfarePensionGrade()).findFirst();
                if(cusWelfarePensionStandardDto.isPresent()){
                    ws.getCells().get(rowIndex,14).putValue(cusWelfarePensionStandardDto.get().getChildCareContribution() == null ? null : new BigDecimal(cusWelfarePensionStandardDto.get().getChildCareContribution()));
                }else{
                    ws.getCells().get(rowIndex,14).putValue("");
                }
            }
            rowIndex ++;

        }
    }

    private void putValue(Worksheet ws, int rowIndex, boolean flag, CusWelfarePensionDto cusWelfarePensions){
        if(flag == true){
            ws.getCells().get(rowIndex,10).putValue("-");
            ws.getCells().get(rowIndex,11).putValue("-");

            ws.getCells().get(rowIndex,12).putValue("-");
            ws.getCells().get(rowIndex,13).putValue("-");
        }else {
            ws.getCells().get(rowIndex,10).putValue(cusWelfarePensions.getInMaleExemptionInsurance() == null ? null : new BigDecimal(cusWelfarePensions.getInMaleExemptionInsurance()));
            ws.getCells().get(rowIndex,11).putValue(cusWelfarePensions.getInFemaleExemptionInsurance() == null ? null : new BigDecimal(cusWelfarePensions.getInFemaleExemptionInsurance()));

            ws.getCells().get(rowIndex,12).putValue(cusWelfarePensions.getEmMaleExemptionInsurance() == null ? null : new BigDecimal(cusWelfarePensions.getEmMaleExemptionInsurance()));
            ws.getCells().get(rowIndex,13).putValue(cusWelfarePensions.getEmFemaleExemptionInsurance() == null ? null : new BigDecimal(cusWelfarePensions.getEmFemaleExemptionInsurance()));
        }
    }



}
