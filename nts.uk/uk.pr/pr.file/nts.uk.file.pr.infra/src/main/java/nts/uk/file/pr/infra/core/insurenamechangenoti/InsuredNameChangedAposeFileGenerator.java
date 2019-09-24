package nts.uk.file.pr.infra.core.insurenamechangenoti;

import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.file.app.core.insurenamechangenoti.InsuredNameChangedExportFileGenerator;
import nts.uk.ctx.pr.file.app.core.insurenamechangenoti.InsuredNameChangedNotiExportData;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.*;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.PitInsiderDivision;
import nts.uk.shr.com.time.japanese.JapaneseDate;
import nts.uk.shr.com.time.japanese.JapaneseEraName;
import nts.uk.shr.com.time.japanese.JapaneseErasAdapter;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;


@Stateless
public class InsuredNameChangedAposeFileGenerator extends AsposeCellsReportGenerator implements InsuredNameChangedExportFileGenerator {

    private static final String TEMPLATE_FILE = "report/QSI002.xlsx";

    private static final String REPORT_FILE_NAME = "被保険者氏名変更届.pdf";

    @Inject
    private JapaneseErasAdapter adapter;


    private static final String Meiji = "明治";

    private static final String Taisho = "大正";

    private static final String SHOWA = "昭和";

    private static final String HEISEI = "平成";

    private static final String PEACE = "令和";




    @Override
    public void generate(FileGeneratorContext fileContext, List<InsuredNameChangedNotiExportData> data, SocialInsurNotiCreateSet socialInsurNotiCreateSet) {
        int index = 0;
        try(AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)){
            Workbook wb = reportContext.getWorkbook();
            WorksheetCollection wsc = wb.getWorksheets();

            for(int i = 1; i < data.size(); i ++){
                wsc.addCopy(0);
            }

            for (InsuredNameChangedNotiExportData item : data) {
                this.writePDF(wsc,item,socialInsurNotiCreateSet,index);
                index ++;
            }


            reportContext.processDesigner();
            reportContext.saveAsPdf(this.createNewFile(fileContext,this.getReportName(REPORT_FILE_NAME)));
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void writePDF(WorksheetCollection wsc, InsuredNameChangedNotiExportData data, SocialInsurNotiCreateSet socialInsurNotiCreateSet, int index){

        String empBasicPenNumInfor[];

        Worksheet ws = wsc.get(index);

        if(socialInsurNotiCreateSet.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL){
            ws.getCells().get("K11").putValue(data.getSocialInsuranceOffice() != null ? data.getSocialInsuranceOffice().getInsuranceMasterInformation().getOfficeOrganizeNumber().getHealthInsuranceOfficeNumber1() : null);
        }else{
            ws.getCells().get("K11").putValue(data.getSocialInsuranceOffice() != null ? data.getSocialInsuranceOffice().getInsuranceMasterInformation().getOfficeOrganizeNumber().getWelfarePensionOfficeNumber1() : null);
        }

        //fill to A1_2
        if(socialInsurNotiCreateSet.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_THE_WELF_PENNUMBER){
            ws.getCells().get("K11").putValue(data.getWelfarePenTypeInfor() != null ? data.getWelfarePenTypeInfor().getUndergroundDivision().value : null);
        }else if(socialInsurNotiCreateSet.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_THE_FUN_MEMBER){
            ws.getCells().get("K11").putValue(data.getFundMembership() != null ? data.getFundMembership().getMembersNumber().v() : null);
        }else if(socialInsurNotiCreateSet.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_HEAL_INSUR_NUM){
            ws.getCells().get("K11").putValue(data.getHealInsurNumberInfor() != null ?  data.getHealInsurNumberInfor().getHealInsNumber().get().v() : null );
        }else if(socialInsurNotiCreateSet.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_HEAL_INSUR_UNION){
            ws.getCells().get("K11").putValue(data.getHealthCarePortInfor() != null ? data.getHealthCarePortInfor().getHealInsurUnionNumber().v() : null);
        }

        // fill to A1_3
        if(socialInsurNotiCreateSet.getPrintPersonNumber() == PersonalNumClass.OUTPUT_BASIC_PEN_NOPER || socialInsurNotiCreateSet.getPrintPersonNumber() == PersonalNumClass.OUTPUT_BASIC_PER_NUMBER){
            if(data.getEmpBasicPenNumInfor() != null && data.getEmpBasicPenNumInfor().getBasicPenNumber().isPresent()){
                empBasicPenNumInfor = data.getEmpBasicPenNumInfor().getBasicPenNumber().get().v().split("");
                ws.getCells().get("N12").putValue(empBasicPenNumInfor[0]);
                ws.getCells().get("O12").putValue(empBasicPenNumInfor[1]);
                ws.getCells().get("P12").putValue(empBasicPenNumInfor[2]);
                ws.getCells().get("Q12").putValue(empBasicPenNumInfor[3]);
                ws.getCells().get("R12").putValue(empBasicPenNumInfor[4]);
                ws.getCells().get("S12").putValue(empBasicPenNumInfor[5]);
                ws.getCells().get("T12").putValue(empBasicPenNumInfor[6]);
                ws.getCells().get("U12").putValue(empBasicPenNumInfor[7]);
                ws.getCells().get("V12").putValue(empBasicPenNumInfor[8]);
                ws.getCells().get("W12").putValue(empBasicPenNumInfor[10]);
            }

        }

        //fill to A1_4 -> A1_7

        JapaneseDate dateJp = toJapaneseDate(data.getEmployeeInfo().getBirthDay());
        String day[] = String.valueOf(dateJp.day()).split("");
        String month[] = String.valueOf(dateJp.month()).split("");
        String year[] = String.valueOf(dateJp.year()).split("");

        if(day.length == 2){
            ws.getCells().get("AB12").putValue(day[0]);
            ws.getCells().get("AC12").putValue(day[1]);
        }else{
            ws.getCells().get("AC12").putValue(day[0]);
        }

        if(month.length == 2){
            ws.getCells().get("AD12").putValue(month[0]);
            ws.getCells().get("AE12").putValue(month[1]);
        }else{
            ws.getCells().get("AE12").putValue(month[0]);
        }

        if(year.length == 2){
            ws.getCells().get("AF12").putValue(year[0]);
            ws.getCells().get("AG12").putValue(year[1]);
        }else{
            ws.getCells().get("AG12").putValue(year[0]);
        }

        this.selectEra(ws,dateJp.era());

        JapaneseDate submitDate = toJapaneseDate(data.getSubmitDate());
        ws.getCells().get("W20").putValue(submitDate.day());
        ws.getCells().get("Y20").putValue(submitDate.month());
        ws.getCells().get("AA20").putValue(submitDate.year());

        if(data.getEmployeeInfo() != null){
            ws.getCells().get("J17").putValue(data.getEmployeeInfo().getPName());
            ws.getCells().get("M17").putValue(data.getEmployeeInfo().getPName());
            ws.getCells().get("J19").putValue(data.getEmployeeInfo().getPName());
            ws.getCells().get("M19").putValue(data.getEmployeeInfo().getPName());

            ws.getCells().get("U17").putValue(data.getEmployeeInfo().getPName());
            ws.getCells().get("Z17").putValue(data.getEmployeeInfo().getPName());
        }

        //厚生年金種別情報

        if(data.getWelfarePenTypeInfor() != null && data.getFundMembership() != null && data.getEmployeeInfo() != null){
            if(data.getEmployeeInfo().getGender() == 1 && data.getWelfarePenTypeInfor().getUndergroundDivision().value == PitInsiderDivision.NOTTARGETED.value && data.getFundMembership().getHistoryId() == null){
                //1
                ws.getShapes().remove(ws.getShapes().get("C1_10"));
                ws.getShapes().remove(ws.getShapes().get("C1_11"));
                ws.getShapes().remove(ws.getShapes().get("C1_12"));
                ws.getShapes().remove(ws.getShapes().get("C1_13"));
                ws.getShapes().remove(ws.getShapes().get("C1_14"));
            }else if(data.getEmployeeInfo().getGender() == 2 && data.getWelfarePenTypeInfor().getUndergroundDivision().value == PitInsiderDivision.NOTTARGETED.value && data.getFundMembership().getHistoryId() == null){
                //2

                ws.getShapes().remove(ws.getShapes().get("C1_9"));
                ws.getShapes().remove(ws.getShapes().get("C1_11"));
                ws.getShapes().remove(ws.getShapes().get("C1_12"));
                ws.getShapes().remove(ws.getShapes().get("C1_13"));
                ws.getShapes().remove(ws.getShapes().get("C1_14"));
            }else if((data.getEmployeeInfo().getGender() == 1 || data.getEmployeeInfo().getGender() == 2) && data.getWelfarePenTypeInfor().getUndergroundDivision().value == PitInsiderDivision.TARGET.value && data.getFundMembership().getHistoryId() == null){
                //3
                ws.getShapes().remove(ws.getShapes().get("C1_9"));
                ws.getShapes().remove(ws.getShapes().get("C1_10"));
                ws.getShapes().remove(ws.getShapes().get("C1_12"));
                ws.getShapes().remove(ws.getShapes().get("C1_13"));
                ws.getShapes().remove(ws.getShapes().get("C1_14"));
            }else if(data.getEmployeeInfo().getGender() == 1 && data.getWelfarePenTypeInfor().getUndergroundDivision().value == PitInsiderDivision.NOTTARGETED.value && data.getFundMembership().getHistoryId() != null ){
                //5

                ws.getShapes().remove(ws.getShapes().get("C1_9"));
                ws.getShapes().remove(ws.getShapes().get("C1_10"));
                ws.getShapes().remove(ws.getShapes().get("C1_11"));
                ws.getShapes().remove(ws.getShapes().get("C1_13"));
                ws.getShapes().remove(ws.getShapes().get("C1_14"));
            }else if(data.getEmployeeInfo().getGender() == 2 && data.getWelfarePenTypeInfor().getUndergroundDivision().value == PitInsiderDivision.NOTTARGETED.value && data.getFundMembership().getHistoryId() != null ){
                //6
                ws.getShapes().remove(ws.getShapes().get("C1_9"));
                ws.getShapes().remove(ws.getShapes().get("C1_10"));
                ws.getShapes().remove(ws.getShapes().get("C1_11"));
                ws.getShapes().remove(ws.getShapes().get("C1_12"));
                ws.getShapes().remove(ws.getShapes().get("C1_14"));
            }else if((data.getEmployeeInfo().getGender() == 1 || data.getEmployeeInfo().getGender() == 2) && data.getWelfarePenTypeInfor().getUndergroundDivision().value == PitInsiderDivision.TARGET.value && data.getFundMembership().getHistoryId() != null){
                //7

                ws.getShapes().remove(ws.getShapes().get("C1_9"));
                ws.getShapes().remove(ws.getShapes().get("C1_10"));
                ws.getShapes().remove(ws.getShapes().get("C1_11"));
                ws.getShapes().remove(ws.getShapes().get("C1_12"));
                ws.getShapes().remove(ws.getShapes().get("C1_13"));
            }
        }else{
            ws.getShapes().remove(ws.getShapes().get("C1_9"));
            ws.getShapes().remove(ws.getShapes().get("C1_10"));
            ws.getShapes().remove(ws.getShapes().get("C1_11"));
            ws.getShapes().remove(ws.getShapes().get("C1_12"));
            ws.getShapes().remove(ws.getShapes().get("C1_13"));
            ws.getShapes().remove(ws.getShapes().get("C1_14"));
        }

        //会社名・住所を出力
        //fill to A2_1
        if(socialInsurNotiCreateSet.getOfficeInformation().value == BusinessDivision.OUTPUT_COMPANY_NAME.value){

            if(data.getCompanyInfor() != null){
                ws.getCells().get("J22").putValue("〒 " + data.getCompanyInfor().getPostCd().substring(0,3) + " － " + data.getCompanyInfor().getPostCd().substring(4));
                ws.getCells().get("M22").putValue(data.getCompanyInfor().getAdd_1());
                ws.getCells().get("M23").putValue(data.getCompanyInfor().getAdd_2());
                ws.getCells().get("J24").putValue(data.getCompanyInfor().getCompanyName());
                ws.getCells().get("J25").putValue(data.getCompanyInfor().getRepname());
                ws.getCells().get("J27").putValue(data.getCompanyInfor().getPhoneNum());
            }

        }else{
            if(data.getSocialInsuranceOffice() != null){
                ws.getCells().get("J22").putValue(data.getSocialInsuranceOffice().getBasicInformation().getAddress().isPresent() ? ("〒 " + data.getSocialInsuranceOffice().getBasicInformation().getAddress().get().getPostalCode().get().v().substring(0,3) + " － " + data.getSocialInsuranceOffice().getBasicInformation().getAddress().get().getPostalCode().get().v().substring(4)) : null);
                ws.getCells().get("M22").putValue(data.getSocialInsuranceOffice().getBasicInformation().getAddress().isPresent() ? data.getSocialInsuranceOffice().getBasicInformation().getAddress().get().getAddress1().get().v() : null);
                ws.getCells().get("M23").putValue(data.getSocialInsuranceOffice().getBasicInformation().getAddress().isPresent() ? data.getSocialInsuranceOffice().getBasicInformation().getAddress().get().getAddress2().get().v() : null);
                ws.getCells().get("J24").putValue(data.getSocialInsuranceOffice().getName().v());
                ws.getCells().get("J25").putValue(data.getSocialInsuranceOffice().getBasicInformation().getRepresentativeName().isPresent() ? data.getSocialInsuranceOffice().getBasicInformation().getRepresentativeName().get().v() : null);
                ws.getCells().get("J27").putValue(data.getSocialInsuranceOffice().getBasicInformation().getAddress().isPresent() ? data.getSocialInsuranceOffice().getBasicInformation().getAddress().get().getPhoneNumber().get().v() : null);
            }

        }



    }

    private JapaneseDate toJapaneseDate (GeneralDate date) {
        Optional<JapaneseEraName> era = this.adapter.getAllEras().eraOf(date);
        return new JapaneseDate(date, era.get());
    }

    private void selectEra(Worksheet ws, String era){
        if(era.equals(Meiji)){
            ws.getShapes().remove(ws.getShapes().get("A1_5"));
            ws.getShapes().remove(ws.getShapes().get("A1_6"));
            ws.getShapes().remove(ws.getShapes().get("A1_7"));
            ws.getShapes().remove(ws.getShapes().get("A1_8"));
        }else  if(era.equals(Taisho)){
            ws.getShapes().remove(ws.getShapes().get("A1_4"));
            ws.getShapes().remove(ws.getShapes().get("A1_6"));
            ws.getShapes().remove(ws.getShapes().get("A1_7"));
            ws.getShapes().remove(ws.getShapes().get("A1_8"));
        }else if(era.equals(SHOWA)){
            ws.getShapes().remove(ws.getShapes().get("A1_4"));
            ws.getShapes().remove(ws.getShapes().get("A1_5"));
            ws.getShapes().remove(ws.getShapes().get("A1_7"));
            ws.getShapes().remove(ws.getShapes().get("A1_8"));
        }else if(era.equals(HEISEI)){
            ws.getShapes().remove(ws.getShapes().get("A1_4"));
            ws.getShapes().remove(ws.getShapes().get("A1_5"));
            ws.getShapes().remove(ws.getShapes().get("A1_6"));
            ws.getShapes().remove(ws.getShapes().get("A1_8"));
        }else{
            ws.getShapes().remove(ws.getShapes().get("A1_4"));
            ws.getShapes().remove(ws.getShapes().get("A1_5"));
            ws.getShapes().remove(ws.getShapes().get("A1_6"));
            ws.getShapes().remove(ws.getShapes().get("A1_7"));
        }

    }
}
