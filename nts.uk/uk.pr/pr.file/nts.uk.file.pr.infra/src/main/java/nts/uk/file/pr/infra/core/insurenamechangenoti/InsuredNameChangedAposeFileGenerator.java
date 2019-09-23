package nts.uk.file.pr.infra.core.insurenamechangenoti;

import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.file.app.core.insurenamechangenoti.InsuredNameChangedExportFileGenerator;
import nts.uk.ctx.pr.file.app.core.insurenamechangenoti.InsuredNameChangedNotiExportData;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.*;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.util.List;


@Stateless
public class InsuredNameChangedAposeFileGenerator extends AsposeCellsReportGenerator implements InsuredNameChangedExportFileGenerator {

    private static final String TEMPLATE_FILE = "report/QSI002.xlsx";

    private static final String REPORT_FILE_NAME = "被保険者氏名変更届.pdf";

    @Override
    public void generate(FileGeneratorContext fileContext, List<InsuredNameChangedNotiExportData> data, SocialInsurNotiCreateSet socialInsurNotiCreateSet) {
        try(AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)){
            Workbook wb = reportContext.getWorkbook();
            WorksheetCollection wsc = wb.getWorksheets();


            //pagination
            for (InsuredNameChangedNotiExportData item : data) {

            }

            reportContext.processDesigner();
            reportContext.saveAsPdf(this.createNewFile(fileContext,this.getReportName(REPORT_FILE_NAME)));
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void writePDF(WorksheetCollection wsc, InsuredNameChangedNotiExportData data, SocialInsurNotiCreateSet socialInsurNotiCreateSet){
        Worksheet ws = wsc.get(0);

        if(socialInsurNotiCreateSet.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL){
            ws.getCells().get("H12").putValue(data.getSocialInsuranceOffice().getInsuranceMasterInformation().getOfficeOrganizeNumber().getHealthInsuranceOfficeNumber1());
        }else{
            ws.getCells().get("H12").putValue(data.getSocialInsuranceOffice().getInsuranceMasterInformation().getOfficeOrganizeNumber().getWelfarePensionOfficeNumber1());
        }

        //fill to A1_2
        if(socialInsurNotiCreateSet.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_THE_WELF_PENNUMBER){
            ws.getCells().get("A1_2").putValue(data.getWelfarePenTypeInfor().getUndergroundDivision().value);
        }else if(socialInsurNotiCreateSet.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_THE_FUN_MEMBER){
            ws.getCells().get("A1_2").putValue(data.getFundMembership().getMembersNumber().v());
        }else if(socialInsurNotiCreateSet.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_HEAL_INSUR_NUM){
            ws.getCells().get("A1_2").putValue(data.getHealInsurNumberInfor().getHealInsNumber().isPresent() ? data.getHealInsurNumberInfor().getHealInsNumber().get().v() : null);
        }else if(socialInsurNotiCreateSet.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_HEAL_INSUR_UNION){
            ws.getCells().get("A1_2").putValue(data.getHealthCarePortInfor().getHealInsurUnionNumber().v());
        }

        // fill to A1_3
        if(socialInsurNotiCreateSet.getPrintPersonNumber() == PersonalNumClass.OUTPUT_BASIC_PEN_NOPER || socialInsurNotiCreateSet.getPrintPersonNumber() == PersonalNumClass.OUTPUT_BASIC_PER_NUMBER){
            ws.getCells().get("A1_3").putValue(data.getEmpBasicPenNumInfor().getBasicPenNumber().isPresent() ? data.getEmpBasicPenNumInfor().getBasicPenNumber().get().v() : null);
        }

        //会社名・住所を出力
        //fill to A2_1
        if(socialInsurNotiCreateSet.getOfficeInformation().value == BusinessDivision.OUTPUT_COMPANY_NAME.value){
            ws.getCells().get("A2_1").putValue(data.getCompanyInfor().getPostCd());
            ws.getCells().get("A2_2").putValue(data.getCompanyInfor().getAdd_1());
            ws.getCells().get("A2_3").putValue(data.getCompanyInfor().getAdd_2());
            ws.getCells().get("A2_4").putValue(data.getCompanyInfor().getCompanyName());
            ws.getCells().get("A2_5").putValue(data.getCompanyInfor().getRepname());
            ws.getCells().get("A2_6").putValue(data.getCompanyInfor().getPhoneNum());
        }else{
            ws.getCells().get("A2_1").putValue(data.getSocialInsuranceOffice().getBasicInformation().getAddress().isPresent() ? data.getSocialInsuranceOffice().getBasicInformation().getAddress().get().getPostalCode().get().v() : null);
            ws.getCells().get("A2_2").putValue(data.getSocialInsuranceOffice().getBasicInformation().getAddress().isPresent() ? data.getSocialInsuranceOffice().getBasicInformation().getAddress().get().getAddress1().get().v() : null);
            ws.getCells().get("A2_3").putValue(data.getSocialInsuranceOffice().getBasicInformation().getAddress().isPresent() ? data.getSocialInsuranceOffice().getBasicInformation().getAddress().get().getAddress2().get().v() : null);
            ws.getCells().get("A2_4").putValue(data.getSocialInsuranceOffice().getName().v());
            ws.getCells().get("A2_5").putValue(data.getSocialInsuranceOffice().getBasicInformation().getRepresentativeName().isPresent() ? data.getSocialInsuranceOffice().getBasicInformation().getRepresentativeName().get().v() : null);
            ws.getCells().get("A2_6").putValue(data.getSocialInsuranceOffice().getBasicInformation().getAddress().isPresent() ? data.getSocialInsuranceOffice().getBasicInformation().getAddress().get().getPhoneNumber().get().v() : null);
        }


    }
}
