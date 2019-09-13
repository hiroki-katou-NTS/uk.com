package nts.uk.file.pr.infra.core.socinsurnoticreset;

import com.aspose.cells.Cells;
import com.aspose.cells.Worksheet;
import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsurancePrefectureInformation;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.*;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.OutputFormatClass;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.SocialInsurNotiCreateSet;
import nts.uk.shr.com.time.japanese.JapaneseDate;
import nts.uk.shr.com.time.japanese.JapaneseEraName;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Stateless
public class NotificationOfLossInsCSVAposeFileGenerator extends AsposeCellsReportGenerator implements NotificationOfLossInsCSVFileGenerator {

    private static final String REPORT_ID = "CSV_GENERATOR";
    private static final String FILE_NAME = "TEMP";
    private static final int ROW_START = 0;
    private static final int CODE = 0;
    private static final int CITY_CODE = 1;
    private static final int OFFICE_SYMBOL = 2;
    private static final int SERIAL_NUMBER = 3;
    private static final int CREATE_DATE = 4;
    private static final int REP_CODE = 5;
    private static final int OFFICE_IDENT_CD = 1;
    private static final int RESERVE = 1;
    private static final int NUMBER_OFFICE = 2;



    @Override
    public void generate(FileGeneratorContext generatorContext, LossNotificationInformation data) {
        val reportContext = this.createEmptyContext(REPORT_ID);
        val workbook = reportContext.getWorkbook();
        val sheet = workbook.getWorksheets().get(0);
        if(data.getSocialInsurNotiCreateSet().getOutputFormat().get() == OutputFormatClass.PEN_OFFICE) {
            fillPensionOffice(data.getHealthInsLoss(), sheet, data.getInfor(), data.getSocialInsurNotiCreateSet(), data.getBaseDate());
        }
        if(data.getSocialInsurNotiCreateSet().getOutputFormat().get() == OutputFormatClass.HEAL_INSUR_ASSO) {
            fillHealthInsAssociation(data.getHealthInsLoss(), sheet, data.getSocialInsurNotiCreateSet(), data.getBaseDate());
        }
        if(data.getSocialInsurNotiCreateSet().getOutputFormat().get() == OutputFormatClass.THE_WELF_PEN) {
            fillEmpPensionFund(data.getHealthInsLoss(), sheet, data.getSocialInsurNotiCreateSet(), data.getBaseDate());
        }
        reportContext.processDesigner();
        reportContext.saveAsCSV(this.createNewFile(generatorContext, FILE_NAME + ".csv"));
    }

    private void fillPensionOffice(List<InsLossDataExport> healthInsLoss, Worksheet worksheet,
                                   List<SocialInsurancePrefectureInformation> infor, SocialInsurNotiCreateSet socialInsurNotiCreateSet, GeneralDate baseDate){
        Cells cells = worksheet.getCells();
        int startRow = 0;
        for(int i = 0; i < healthInsLoss.size(); i++){
            InsLossDataExport data = healthInsLoss.get(i);
            cells.get(startRow, CODE).setValue(getPreferCode(data, infor));
            cells.get(startRow, CITY_CODE).setValue(data.getOfficeNumber1());
            cells.get(startRow, OFFICE_SYMBOL).setValue(data.getOfficeNumber2());
            cells.get(startRow, SERIAL_NUMBER).setValue(socialInsurNotiCreateSet.getFdNumber());
            cells.get(startRow, CREATE_DATE).setValue(baseDate);
            cells.get(startRow, REP_CODE).setValue("22223");
            startRow = startRow + 1;
            cells.get(startRow, OFFICE_IDENT_CD).setValue("[kanri]");
            startRow = startRow + 1;
            cells.get(startRow, RESERVE).setValue("");
            cells.get(startRow, NUMBER_OFFICE).setValue("001");
            startRow = startRow + 1;
            cells.get(startRow, CODE).setValue(getPreferCode(data, infor));
            cells.get(startRow, CITY_CODE).setValue(data.getOfficeNumber1());
            cells.get(startRow, OFFICE_SYMBOL).setValue(data.getOfficeNumber2());
            cells.get(startRow, 4).setValue(data.getOfficeNumber());
        }
    }

    private String getPreferCode(InsLossDataExport data, List<SocialInsurancePrefectureInformation> infor){
        Optional<SocialInsurancePrefectureInformation> refecture =  infor.stream().filter(item -> item.getNo() == data.getPrefectureNo()
                && item.getEndYearMonth() > convertDateToYearMonth(data.getEndDate())
                && item.getEndYearMonth() < convertDateToYearMonth(data.getEndDate())).findFirst();
        return refecture.isPresent() ? refecture.get().getPrefectureCode().v() : "";
    }

    private int convertDateToYearMonth(String date){
        return Integer.parseInt(date.substring(0,4) + date.substring(4,6));
    }

    private void fillHealthInsAssociation(List<InsLossDataExport> healthInsAssociation, Worksheet worksheet, SocialInsurNotiCreateSet socialInsurNotiCreateSet, GeneralDate baseDate){
        Cells cells = worksheet.getCells();
        int startRow = 0;
        for(int i = 0; i < healthInsAssociation.size(); i++){
            InsLossDataExport data = healthInsAssociation.get(i);
            cells.get(startRow, 0).setValue(data.getUnionOfficeNumber());
            cells.get(startRow, 1).setValue(Objects.toString(socialInsurNotiCreateSet.getFdNumber(),"001"));
            cells.get(startRow, 2).setValue(baseDate.toString("yyyyMMdd"));
            cells.get(startRow, 4).setValue("HEAL_INSUR_INHEREN_PR");
            startRow = startRow + 1;
            cells.get(startRow, 0).setValue("[kanri]");
            startRow = startRow + 1;
            cells.get(startRow, RESERVE).setValue(data.getHealInsNumber());
            cells.get(startRow, NUMBER_OFFICE).setValue(data.getHealInsNumber());
        }
    }

    private void fillEmpPensionFund(List<InsLossDataExport> healthInsAssociation, Worksheet worksheet, SocialInsurNotiCreateSet socialInsurNotiCreateSet, GeneralDate baseDate){
        Cells cells = worksheet.getCells();
        int startRow = 0;
        for(int i = 0; i < healthInsAssociation.size(); i++){
            InsLossDataExport data = healthInsAssociation.get(i);
            cells.get(startRow, 0).setValue(data.getUnionOfficeNumber());
            cells.get(startRow, 1).setValue(Objects.toString(socialInsurNotiCreateSet.getFdNumber(),"001"));
            cells.get(startRow, 2).setValue(baseDate.toString("yyyyMMdd"));
            cells.get(startRow, 4).setValue("HEAL_INSUR_INHEREN_PR");
            startRow = startRow + 1;
            //cells.get(startRow, 0).setValue("[kanri]");
            startRow = startRow + 1;
            cells.get(startRow, RESERVE).setValue(data.getHealInsNumber());
            cells.get(startRow, NUMBER_OFFICE).setValue(data.getHealInsNumber());
        }
    }



}


