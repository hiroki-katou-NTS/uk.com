package nts.uk.file.pr.infra.core.socinsurnoticreset;

import com.aspose.cells.Workbook;
import com.aspose.cells.WorksheetCollection;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.CompanyInfor;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.InsLossDataExport;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.LossNotificationInformation;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.NotificationOfLossInsFileGenerator;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.*;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.ReasonsForLossHealthyIns;
import nts.uk.shr.com.time.japanese.JapaneseDate;
import nts.uk.shr.com.time.japanese.JapaneseEraName;
import nts.uk.shr.com.time.japanese.JapaneseErasAdapter;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Stateless
public class NotificationOfLossInsPDFAposeFileGenerator extends AsposeCellsReportGenerator implements NotificationOfLossInsFileGenerator {

    private static final String TEMPLATE_FILE_70 = "report/70歳以上被用者該当届_帳票テンプレート.xlsx";
    private static final String TEMPLATE_FILE_OTHER = "report/70歳以上被用者不該当届_帳票テンプレート.xlsx";
    private static final String FILE_NAME = "被保険者資格喪失届.pdf";

    @Inject
    private JapaneseErasAdapter adapter;

    private static final String SHOWA = "昭和";

    private static final String HEISEI = "平成";

    private static final String PEACE = "令和";

    private static final int EMP_IN_PAGE = 4;

    @Override
    public void generate(FileGeneratorContext generatorContext, LossNotificationInformation data) {
        CompanyInfor company = data.getCompany();
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE_OTHER)) {
            AsposeCellsReportContext reportContext70 = this.createContext(TEMPLATE_FILE_70);
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            reportContext.processDesigner();
            Workbook workbook70 = reportContext70.getWorkbook();
            WorksheetCollection worksheets70 = workbook70.getWorksheets();
            worksheets.add("over").copy(worksheets70.get(0));
            fillDataUnderSevenTy(worksheets, data.getHealthInsLoss(), data.getBaseDate(), company, data.getSocialInsurNotiCreateSet());
            fillDataOverSevenTy(worksheets, data.getWelfPenInsLoss(), data.getBaseDate(), company, data.getSocialInsurNotiCreateSet());
            worksheets.removeAt(1);
            worksheets.removeAt(0);
            reportContext.saveAsPdf(this.createNewFile(generatorContext, FILE_NAME));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void fillDataOverSevenTy(WorksheetCollection worksheets, List<InsLossDataExport> data, GeneralDate baseDate,CompanyInfor company, SocialInsurNotiCreateSet ins) {
        try {
            String sheetName = "underSeventy";
            for (int i = 0; i < data.size(); i++) {
                worksheets.get(worksheets.addCopy(1)).setName(sheetName + i);
                InsLossDataExport  dataRow = data.get(i);
                fillCompanyPension(worksheets, dataRow, baseDate, company,sheetName + i,ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL, ins.getOfficeInformation());
                fillEmployeeOverSeventy(worksheets, dataRow, sheetName + i, ins);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void fillDataUnderSevenTy(WorksheetCollection worksheets, List<InsLossDataExport> data, GeneralDate baseDate, CompanyInfor company, SocialInsurNotiCreateSet ins) {
        try {
            String sheetName = "overSeventy";
            String companyCd = "";
            int page = 0;
            int stt = 0;
            for (int i = 0; i < data.size(); i++, stt++) {
                InsLossDataExport  dataRow = data.get(i);
                if(stt % EMP_IN_PAGE == 0 || !companyCd.equals(data.get(i).getOfficeCd())) {
                    if(!companyCd.equals(data.get(i).getOfficeCd()) && stt % EMP_IN_PAGE != 0){
                        unSelectAll(worksheets,sheetName + page, stt + 1);
                    }
                    page++;
                    worksheets.get(worksheets.addCopy(0)).setName(sheetName + page);
                    worksheets.get(sheetName + page).autoFitRows(true);
                    companyCd = data.get(i).getOfficeCd();
                    fillCompanyHealthy(worksheets, dataRow, baseDate, company, sheetName + page, ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL, ins.getOfficeInformation());
                    stt = 0;
                }
                fillEmployeeUnderSeventy(worksheets, dataRow, sheetName + page, stt, ins);
            }
            if(stt != 3) {
                unSelectAll(worksheets,sheetName + page, stt +1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void fillCompanyPension( WorksheetCollection worksheets, InsLossDataExport data, GeneralDate baseDate, CompanyInfor company,String sheetName,boolean isHeal, BusinessDivision typeOff){
        JapaneseDate dateJp = toJapaneseDate(baseDate);
        worksheets.getRangeByName(sheetName + "!D1_1_1").setValue(dateJp.year() + 1);
        worksheets.getRangeByName(sheetName + "!D1_1_2").setValue(dateJp.month());
        worksheets.getRangeByName(sheetName + "!D1_1_3").setValue(dateJp.day());
        worksheets.getRangeByName(sheetName + "!D1_2_1").setValue(data.getOfficeNumber1().length() > 0 ? data.getOfficeNumber1().substring(0,1) : data.getOfficeNumber1());
        worksheets.getRangeByName(sheetName + "!D1_2_1").setValue(data.getOfficeNumber1().length() > 1 ? data.getOfficeNumber1().substring(1,2) : "") ;
        worksheets.getRangeByName(sheetName + "!D1_3_1").setValue(data.getOfficeNumber2().length() > 0 ? data.getOfficeNumber2().substring(0,1) : "");
        worksheets.getRangeByName(sheetName + "!D1_3_2").setValue(data.getOfficeNumber2().length() > 1 ? data.getOfficeNumber2().substring(1,2) : "");
        worksheets.getRangeByName(sheetName + "!D1_3_3").setValue(data.getOfficeNumber2().length() > 2 ? data.getOfficeNumber2().substring(2,3) : "");
        worksheets.getRangeByName(sheetName + "!D1_3_4").setValue(data.getOfficeNumber2().length() > 3 ? data.getOfficeNumber2().substring(3,4) : "");
        worksheets.getRangeByName(sheetName + "!D1_4_1").setValue(data.getOfficeNumber().length() > 0 ? data.getOfficeNumber().substring(0,1) : "");
        worksheets.getRangeByName(sheetName + "!D1_4_2").setValue(data.getOfficeNumber().length() > 1 ? data.getOfficeNumber().substring(1,2) : "");
        worksheets.getRangeByName(sheetName + "!D1_4_3").setValue(data.getOfficeNumber().length() > 2 ? data.getOfficeNumber().substring(2,3) : "");
        worksheets.getRangeByName(sheetName + "!D1_4_4").setValue(data.getOfficeNumber().length() > 3 ? data.getOfficeNumber().substring(3,4) : "");
        worksheets.getRangeByName(sheetName + "!D1_4_5").setValue(data.getOfficeNumber().length() > 4 ? data.getOfficeNumber().substring(4,5) : "");
        worksheets.getRangeByName(sheetName + "!D1_5_1").setValue(typeOff == BusinessDivision.OUTPUT_COMPANY_NAME ? company.getPostCd().length() > 2 ? company.getPostCd().substring(0,3) : company.getPostCd() :
                typeOff == BusinessDivision.OUTPUT_SIC_INSURES ? data.getPortCd().length() > 2 ? data.getPortCd().substring(0,3) : data.getPortCd() : "");
        worksheets.getRangeByName(sheetName + "!D1_5_2").setValue(typeOff == BusinessDivision.OUTPUT_COMPANY_NAME ? company.getPostCd().length() > 7 ? company.getPostCd().substring(4,8) : company.getPostCd() :
                typeOff == BusinessDivision.OUTPUT_SIC_INSURES ? data.getPortCd().length() > 7 ? data.getPortCd().substring(4,8) : "" : "");
        worksheets.getRangeByName(sheetName + "!D1_6").setValue(typeOff == BusinessDivision.OUTPUT_COMPANY_NAME ? company.getAdd_1() + company.getAdd_2() :
                typeOff == BusinessDivision.OUTPUT_SIC_INSURES ? data.getAdd1() + data.getAdd2() : "");
        worksheets.getRangeByName(sheetName + "!D1_7").setValue(typeOff == BusinessDivision.OUTPUT_COMPANY_NAME ? company.getCompanyName() :
                typeOff == BusinessDivision.OUTPUT_SIC_INSURES ? data.getCompanyName() : "");
        worksheets.getRangeByName(sheetName + "!D1_8").setValue(typeOff == BusinessDivision.OUTPUT_COMPANY_NAME ? company.getRepname() :
                typeOff == BusinessDivision.OUTPUT_SIC_INSURES ? data.getRepName() : "");
        worksheets.getRangeByName(sheetName + "!D1_9_1").setValue(typeOff == BusinessDivision.OUTPUT_COMPANY_NAME ? formatPhoneNumber(company.getPhoneNum(),1) :
                typeOff == BusinessDivision.OUTPUT_SIC_INSURES ? formatPhoneNumber(data.getPhoneNumber(),1) : "");
        worksheets.getRangeByName(sheetName + "!D1_9_2").setValue(typeOff == BusinessDivision.OUTPUT_COMPANY_NAME ? formatPhoneNumber(company.getPhoneNum(),2) :
                typeOff == BusinessDivision.OUTPUT_SIC_INSURES ? formatPhoneNumber(data.getPhoneNumber(),2) : "");
        worksheets.getRangeByName(sheetName + "!D1_9_3").setValue(typeOff == BusinessDivision.OUTPUT_COMPANY_NAME ? formatPhoneNumber(company.getPhoneNum(),3) :
                typeOff == BusinessDivision.OUTPUT_SIC_INSURES ? formatPhoneNumber(data.getPhoneNumber(),3) : "");
    }

    private String formatPortCd(String portCd, int stt){
        String result = portCd.replace("-", "");
        if (result.length() >= 3 && stt == 1) {
            return result.substring(0, 3);
        }
        if (stt == 2 && result.length() > 3) {
            return result.substring(3, result.length());
        }
        return result;
    }

    private String formatPhoneNumber(String phone, int stt) {
        String result = phone.replace("-", "");
        if (stt == 1 && result.length() >= 3) {
            return result.substring(0, 3);
        }
        if (stt == 3 && result.length() > 6) {
            return result.substring(6, result.length());
        }
        if (stt == 2 && result.length() >= 6) {
            return result.substring(3, 6);
        }
        return "";
    }

    private void fillCompanyHealthy(WorksheetCollection worksheets, InsLossDataExport data, GeneralDate baseDate, CompanyInfor company, String sheetName, boolean isHeal, BusinessDivision typeOff){
        JapaneseDate dateJp = toJapaneseDate(baseDate);
        worksheets.getRangeByName(sheetName + "!A1_1_1").setValue(dateJp.year() + 1);
        worksheets.getRangeByName(sheetName + "!A1_1_2").setValue(dateJp.month());
        worksheets.getRangeByName(sheetName + "!A1_1_3").setValue(dateJp.day());
        worksheets.getRangeByName(sheetName + "!A1_2").setValue(isHeal ? data.getOfficeNumber1() : data.getWelfOfficeNumber1());
        worksheets.getRangeByName(sheetName + "!A1_3").setValue(isHeal ? data.getOfficeNumber2() : data.getWelfOfficeNumber2());
        worksheets.getRangeByName(sheetName + "!A1_4").setValue(isHeal ? data.getOfficeNumber() : data.getWelfOfficeNumber());
        worksheets.getRangeByName(sheetName + "!A1_5_1").setValue(typeOff == BusinessDivision.OUTPUT_COMPANY_NAME ? formatPortCd(company.getPostCd(),1) :
                typeOff == BusinessDivision.OUTPUT_SIC_INSURES ? formatPortCd(data.getPortCd(),1) : "");
        worksheets.getRangeByName(sheetName + "!A1_5_2").setValue(typeOff == BusinessDivision.OUTPUT_COMPANY_NAME ? formatPortCd(company.getPostCd(),2) :
                typeOff == BusinessDivision.OUTPUT_SIC_INSURES ? formatPortCd(data.getPortCd(),2) : "");
        worksheets.getRangeByName(sheetName + "!A1_6").setValue(typeOff == BusinessDivision.OUTPUT_COMPANY_NAME ? company.getAdd_1() + company.getAdd_2() :
                typeOff == BusinessDivision.OUTPUT_SIC_INSURES ? data.getAdd1() + data.getAdd2() : "");
        worksheets.getRangeByName(sheetName + "!A1_7").setValue(typeOff == BusinessDivision.OUTPUT_COMPANY_NAME ? company.getCompanyName() :
                typeOff == BusinessDivision.OUTPUT_SIC_INSURES ? data.getCompanyName() : "");
        worksheets.getRangeByName(sheetName + "!A1_8").setValue(typeOff == BusinessDivision.OUTPUT_COMPANY_NAME ? company.getRepname() :
                typeOff == BusinessDivision.OUTPUT_SIC_INSURES ? data.getRepName() : "");
        worksheets.getRangeByName(sheetName + "!A1_9_1").setValue(typeOff == BusinessDivision.OUTPUT_COMPANY_NAME ? formatPhoneNumber(company.getPhoneNum(),1):
                typeOff == BusinessDivision.OUTPUT_SIC_INSURES ? formatPhoneNumber(data.getPhoneNumber(),1) : "");
        worksheets.getRangeByName(sheetName + "!A1_9_2").setValue(typeOff == BusinessDivision.OUTPUT_COMPANY_NAME ? formatPhoneNumber(company.getPhoneNum(),2):
                typeOff == BusinessDivision.OUTPUT_SIC_INSURES ? formatPhoneNumber(data.getPhoneNumber(),2) : "");
        worksheets.getRangeByName(sheetName + "!A1_9_3").setValue(typeOff == BusinessDivision.OUTPUT_COMPANY_NAME ? formatPhoneNumber(company.getPhoneNum(),3):
                typeOff == BusinessDivision.OUTPUT_SIC_INSURES ? formatPhoneNumber(data.getPhoneNumber(),3) : "");
    }

    private void selectEra(WorksheetCollection worksheets, String era, String sheetName, int stt){
        if(era.equals(SHOWA)) {
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_7" : "A2_7_" + (stt + 1)));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_8" : "A2_8_" + (stt + 1)));
        }
        if(era.equals(HEISEI)) {
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_6" : "A2_6_" + (stt + 1)));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_8" : "A2_8_" + (stt + 1)));
        }
        if(era.equals(PEACE)) {
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_6" : "A2_6_" + stt + 1));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_7" : "A2_7_" + stt + 1));
        }
        if(!era.equals(PEACE) && !era.equals(HEISEI) && !era.equals(SHOWA)) {
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_6" : "A2_6_" + stt + 1));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_7" : "A2_7_" + stt + 1));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_8" : "A2_8_" + (stt + 1)));
        }
    }

    private void unSelectAll(WorksheetCollection worksheets, String sheetName, int stt){
        for(int i = stt ; i<= EMP_IN_PAGE;  i++) {
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_6_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_7_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_8_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_12_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_14_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_16_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_17_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_18_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_19_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_20_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_24_" + i));
        }
    }

    private void fillEmployeeUnderSeventy(WorksheetCollection worksheets, InsLossDataExport data, String sheetName, int stt, SocialInsurNotiCreateSet ins){
        JapaneseDate birthDay = toJapaneseDate( GeneralDate.fromString(data.getBirthDay().substring(0,10), "yyyy-MM-dd"));
        JapaneseDate endDate = toJapaneseDate( GeneralDate.fromString(data.getEndDate().substring(0,10), "yyyy-MM-dd"));
        this.selectEra(worksheets, birthDay.era(), sheetName, stt);
        this.selectCause(worksheets, data.getCause(), sheetName, stt);
        this.selectUnder(worksheets, data.getIsMoreEmp(),"A2_18", sheetName, stt);
        this.selectUnder(worksheets, data.getContinReemAfterRetirement(),"A2_19", sheetName, stt);
        this.selectUnder(worksheets, data.getOther(),"A2_20", sheetName, stt);
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_1", stt)).setValue(
                ins.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_HEAL_INSUR_NUM ? data.getHealInsNumber() :
                ins.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_THE_WELF_PENNUMBER ? data.getWelfPenNumber() :
                ins.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_HEAL_INSUR_UNION ? data.getHealInsUnionNumber() :
                ins.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_THE_FUN_MEMBER ? data.getMemberNumber() : "");
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_2", stt)).setValue(
                ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? data.getPersonName().split("　")[0] : data.getPersonNameKana().split("　")[0]);
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_3", stt)).setValue(
                ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? (data.getPersonName().split("　").length > 1 ? data.getPersonName().split("　")[1] : "" ): (data.getPersonNameKana().split("　").length > 1 ? data.getPersonNameKana().split("　")[1] : ""));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_4", stt)).setValue(
                ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? data.getOldName().split("　")[0] : data.getOldNameKana().split("　")[0]);
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_5", stt)).setValue(
                ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? (data.getOldName().split("　").length > 1 ? data.getOldName().split("　")[1] : "") : (data.getOldNameKana().split("　").length > 1 ? data.getOldNameKana().split("　")[1] : ""));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_9_1", stt)).setValue(convertJpDate(birthDay).charAt(0));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_9_2", stt)).setValue(convertJpDate(birthDay).charAt(1));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_9_3", stt)).setValue(convertJpDate(birthDay).charAt(2));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_9_4", stt)).setValue(convertJpDate(birthDay).charAt(3));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_9_5", stt)).setValue(convertJpDate(birthDay).charAt(4));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_9_6", stt)).setValue(convertJpDate(birthDay).charAt(5));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_13_1", stt)).setValue(Objects.toString(endDate.year() + 1, ""));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_13_2", stt)).setValue(data.getEndDate() != null ? data.getEndDate().substring(5,7) : "");
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_13_3", stt)).setValue(data.getEndDate() != null ? data.getEndDate().substring(8,10) : "");
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_15_1", stt)).setValue(Objects.toString(endDate.year() + 1, ""));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_15_2", stt)).setValue(data.getEndDate() != null ? data.getEndDate().substring(5,7) : "");
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_15_3", stt)).setValue(data.getEndDate() != null ? data.getEndDate().substring(8,10) : "");
        if(ins.getPrintPersonNumber() != PersonalNumClass.DO_NOT_OUTPUT && ins.getPrintPersonNumber() != PersonalNumClass.OUTPUT_PER_NUMBER) {
            worksheets.getRangeByName(this.getRangeName(sheetName, "A2_10_9", stt)).setValue(data.getBasicPenNumber() != null ? data.getBasicPenNumber().length() > 0 ? data.getBasicPenNumber().charAt(0) : "" : "");
            worksheets.getRangeByName(this.getRangeName(sheetName, "A2_10_10", stt)).setValue(data.getBasicPenNumber() != null ? data.getBasicPenNumber().length() > 1 ? data.getBasicPenNumber().charAt(1) : "" : "");
            worksheets.getRangeByName(this.getRangeName(sheetName, "A2_10_1", stt)).setValue(data.getBasicPenNumber() != null ? data.getBasicPenNumber().length() > 2 ? data.getBasicPenNumber().charAt(2) : "" : "");
            worksheets.getRangeByName(this.getRangeName(sheetName, "A2_10_2", stt)).setValue(data.getBasicPenNumber() != null ? data.getBasicPenNumber().length() > 3 ? data.getBasicPenNumber().charAt(3) : "" : "");
            worksheets.getRangeByName(this.getRangeName(sheetName, "A2_10_3", stt)).setValue(data.getBasicPenNumber() != null ? data.getBasicPenNumber().length() > 4 ? data.getBasicPenNumber().charAt(4) : "" : "");
            worksheets.getRangeByName(this.getRangeName(sheetName, "A2_10_4", stt)).setValue(data.getBasicPenNumber() != null ? data.getBasicPenNumber().length() > 5 ? data.getBasicPenNumber().charAt(5) : "" : "");
            worksheets.getRangeByName(this.getRangeName(sheetName, "A2_10_5", stt)).setValue(data.getBasicPenNumber() != null ? data.getBasicPenNumber().length() > 6 ? data.getBasicPenNumber().charAt(6) : "" : "");
            worksheets.getRangeByName(this.getRangeName(sheetName, "A2_10_6", stt)).setValue(data.getBasicPenNumber() != null ? data.getBasicPenNumber().length() > 7 ? data.getBasicPenNumber().charAt(7) : "" : "");
            worksheets.getRangeByName(this.getRangeName(sheetName, "A2_10_7", stt)).setValue(data.getBasicPenNumber() != null ? data.getBasicPenNumber().length() > 8 ? data.getBasicPenNumber().charAt(8) : "" : "");
            worksheets.getRangeByName(this.getRangeName(sheetName, "A2_10_8", stt)).setValue(data.getBasicPenNumber() != null ? data.getBasicPenNumber().length() > 9 ? data.getBasicPenNumber().charAt(9) : "" : "");
        }
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_11_1", stt)).setValue(convertJpDate(endDate).charAt(0));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_11_2", stt)).setValue(convertJpDate(endDate).charAt(1));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_11_3", stt)).setValue(convertJpDate(endDate).charAt(2));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_11_4", stt)).setValue(convertJpDate(endDate).charAt(3));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_11_5", stt)).setValue(convertJpDate(endDate).charAt(4));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_11_6", stt)).setValue(convertJpDate(endDate).charAt(5));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_21", stt)).setValue(Objects.toString(data.getOtherReason(), ""));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_22", stt)).setValue(Objects.toString(data.getCaInsurance(), ""));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_23", stt)).setValue(Objects.toString(data.getNumRecoved(), ""));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_25_1", stt)).setValue(convertJpDate(endDate).charAt(0));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_25_2", stt)).setValue(convertJpDate(endDate).charAt(1));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_25_3", stt)).setValue(convertJpDate(endDate).charAt(2));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_25_4", stt)).setValue(convertJpDate(endDate).charAt(3));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_25_5", stt)).setValue(convertJpDate(endDate).charAt(4));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_25_6", stt)).setValue(convertJpDate(endDate).charAt(5));
    }

    private String getRangeName(String sheetName, String pos, int stt){
        return stt == 0 ? sheetName + "!" + pos : sheetName + "!" + pos + "_" + ++stt;
    }

    private void fillEmployeeOverSeventy(WorksheetCollection worksheets, InsLossDataExport data, String sheetName, SocialInsurNotiCreateSet ins){
        JapaneseDate birthDay = toJapaneseDate( GeneralDate.fromString(data.getBirthDay().substring(0,10), "yyyy-MM-dd"));
        JapaneseDate endDate = toJapaneseDate( GeneralDate.fromString(data.getEndDate().substring(0,10), "yyyy-MM-dd"));
        this.selectOver(worksheets, data.getIsMoreEmp(), "D2_6", sheetName);
        this.selectOver(worksheets, data.getOther(),"D2_8", sheetName);
        worksheets.getRangeByName(sheetName + "!D2_1").setValue(
                ins.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_HEAL_INSUR_NUM ? data.getHealInsNumber() :
                ins.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_THE_WELF_PENNUMBER ? data.getWelfPenNumber() :
                ins.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_HEAL_INSUR_UNION ? data.getHealInsUnionNumber() :
                ins.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_THE_FUN_MEMBER ? data.getMemberNumber() : "");
        worksheets.getRangeByName(sheetName + "!D2_2").setValue(
                ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? data.getPersonName() : data.getPersonNameKana());
        worksheets.getRangeByName(sheetName + "!D2_3").setValue(Objects.toString(data.getPersonNameKana(), ""));
        worksheets.getRangeByName(sheetName + "!D2_4_1").setValue(convertJpDate(birthDay).charAt(0));
        worksheets.getRangeByName(sheetName + "!D2_4_2").setValue(convertJpDate(birthDay).charAt(1));
        worksheets.getRangeByName(sheetName + "!D2_4_3").setValue(convertJpDate(birthDay).charAt(2));
        worksheets.getRangeByName(sheetName + "!D2_4_4").setValue(convertJpDate(birthDay).charAt(3));
        worksheets.getRangeByName(sheetName + "!D2_4_5").setValue(convertJpDate(birthDay).charAt(4));
        worksheets.getRangeByName(sheetName + "!D2_4_6").setValue(convertJpDate(birthDay).charAt(5));
        if(ins.getPrintPersonNumber() != PersonalNumClass.DO_NOT_OUTPUT && ins.getPrintPersonNumber() != PersonalNumClass.OUTPUT_PER_NUMBER) {
            worksheets.getRangeByName(sheetName + "!D2_5_10").setValue(data.getBasicPenNumber() != null ?data.getBasicPenNumber().length() > 0 ? data.getBasicPenNumber().charAt(0) : "" : "");
            worksheets.getRangeByName(sheetName + "!D2_5_9").setValue(data.getBasicPenNumber() != null ? data.getBasicPenNumber().length() > 1 ? data.getBasicPenNumber().charAt(1) : "" : "");
            worksheets.getRangeByName(sheetName + "!D2_5_1").setValue(data.getBasicPenNumber() != null ? data.getBasicPenNumber().length() > 2 ? data.getBasicPenNumber().charAt(2) : "" : "");
            worksheets.getRangeByName(sheetName + "!D2_5_2").setValue(data.getBasicPenNumber() != null ? data.getBasicPenNumber().length() > 3 ? data.getBasicPenNumber().charAt(3) : "" : "");
            worksheets.getRangeByName(sheetName + "!D2_5_3").setValue(data.getBasicPenNumber() != null ? data.getBasicPenNumber().length() > 4 ? data.getBasicPenNumber().charAt(4) : "" : "");
            worksheets.getRangeByName(sheetName + "!D2_5_4").setValue(data.getBasicPenNumber() != null ? data.getBasicPenNumber().length() > 5 ? data.getBasicPenNumber().charAt(5) : "" : "");
            worksheets.getRangeByName(sheetName + "!D2_5_5").setValue(data.getBasicPenNumber() != null ? data.getBasicPenNumber().length() > 6 ? data.getBasicPenNumber().charAt(6) : "" : "");
            worksheets.getRangeByName(sheetName + "!D2_5_6").setValue(data.getBasicPenNumber() != null ? data.getBasicPenNumber().length() > 7 ? data.getBasicPenNumber().charAt(7) : "" : "");
            worksheets.getRangeByName(sheetName + "!D2_5_7").setValue(data.getBasicPenNumber() != null ? data.getBasicPenNumber().length() > 8 ? data.getBasicPenNumber().charAt(8) : "" : "");
            worksheets.getRangeByName(sheetName + "!D2_5_8").setValue(data.getBasicPenNumber() != null ? data.getBasicPenNumber().length() > 9 ? data.getBasicPenNumber().charAt(9) : "" : "");
        }
        worksheets.getRangeByName(sheetName + "!D2_9").setValue(data.getOtherReason());
        worksheets.getRangeByName(sheetName + "!D2_10_1").setValue(convertJpDate(endDate).charAt(0));
        worksheets.getRangeByName(sheetName + "!D2_10_2").setValue(convertJpDate(endDate).charAt(1));
        worksheets.getRangeByName(sheetName + "!D2_10_3").setValue(convertJpDate(endDate).charAt(2));
        worksheets.getRangeByName(sheetName + "!D2_10_4").setValue(convertJpDate(endDate).charAt(3));
        worksheets.getRangeByName(sheetName + "!D2_10_5").setValue(convertJpDate(endDate).charAt(4));
        worksheets.getRangeByName(sheetName + "!D2_10_6").setValue(convertJpDate(endDate).charAt(5));
        worksheets.getRangeByName(sheetName + "!D2_11_1").setValue(convertJpDate(endDate).charAt(0));
        worksheets.getRangeByName(sheetName + "!D2_11_2").setValue(convertJpDate(endDate).charAt(1));
        worksheets.getRangeByName(sheetName + "!D2_11_3").setValue(convertJpDate(endDate).charAt(2));
        worksheets.getRangeByName(sheetName + "!D2_11_4").setValue(convertJpDate(endDate).charAt(3));
        worksheets.getRangeByName(sheetName + "!D2_11_5").setValue(convertJpDate(endDate).charAt(4));
        worksheets.getRangeByName(sheetName + "!D2_11_6").setValue(convertJpDate(endDate).charAt(5));
        worksheets.getRangeByName(sheetName + "!D2_12").setValue(data.getRemunMonthlyAmount());
        worksheets.getRangeByName(sheetName + "!D2_13").setValue(data.getRemunMonthlyAmountKind());
        String total = Objects.toString(data.getRemunMonthlyAmount() + data.getRemunMonthlyAmountKind());
        worksheets.getRangeByName(sheetName + "!D2_14_1").setValue(total.length() > 0 ? total.charAt(0) : "");
        worksheets.getRangeByName(sheetName + "!D2_14_2").setValue(total.length() > 1 ? total.charAt(1) : "");
        worksheets.getRangeByName(sheetName + "!D2_14_3").setValue(total.length() > 2 ? total.charAt(2) : "");
        worksheets.getRangeByName(sheetName + "!D2_14_4").setValue(total.length() > 3 ? total.charAt(3) : "");
        worksheets.getRangeByName(sheetName + "!D2_14_5").setValue(total.length() > 4 ? total.charAt(4) : "");
        worksheets.getRangeByName(sheetName + "!D2_14_6").setValue(total.length() > 5 ? total.charAt(5) : "");
        worksheets.getRangeByName(sheetName + "!D2_14_7").setValue(total.length() > 6 ? total.charAt(6) : "");
        worksheets.getRangeByName(sheetName + "!D2_14_8").setValue(total.length() > 7 ? total.charAt(7) : "");
    }

    private void selectOver(WorksheetCollection worksheets, int value,  String shapeName, String sheetName){
        if(value == 0) {
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(shapeName));
        }
    }

    private void selectUnder(WorksheetCollection worksheets, int value, String shapeName, String sheetName, int stt){
        if(value == 0) {
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? shapeName : shapeName +  "_" + ++stt));
        }
    }

    private void selectCause(WorksheetCollection worksheets, int cause,String sheetName, int stt){
        if(cause == ReasonsForLossHealthyIns.RETIREMENT.value){
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_14" : "A2_14_" + (stt + 1)));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_16" : "A2_16_" + (stt + 1)));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_17" : "A2_17_" + (stt + 1)));
        }

        if(cause == ReasonsForLossHealthyIns.DEATH.value){
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_12" : "A2_12_" + (stt + 1)));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_16" : "A2_16_" + (stt + 1)));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_17" : "A2_17_" + (stt + 1)));
        }

        if(cause == ReasonsForLossHealthyIns.ONLY_HEALTHY_INSURANCE.value){
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_14" : "A2_14_" + (stt + 1)));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_12" : "A2_12_" + (stt + 1)));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_17" : "A2_17_" + (stt + 1)));
        }

        if(cause == ReasonsForLossHealthyIns.DISABILITY_AUTHORIZATION.value){
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_14" : "A2_14_" + (stt + 1)));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_16" : "A2_16_" + (stt + 1)));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_12" : "A2_12_" + (stt + 1)));
        }
        if((cause != ReasonsForLossHealthyIns.RETIREMENT.value) && (cause != ReasonsForLossHealthyIns.DEATH.value) && (cause != ReasonsForLossHealthyIns.DISABILITY_AUTHORIZATION.value)) {
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_14" : "A2_14_" + (stt + 1)));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_16" : "A2_16_" + (stt + 1)));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_17" : "A2_17_" + (stt + 1)));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_12" : "A2_12_" + (stt + 1)));
        }
    }

    private JapaneseDate toJapaneseDate (GeneralDate date) {
        Optional<JapaneseEraName> era = this.adapter.getAllEras().eraOf(date);
        return new JapaneseDate(date, era.get());
    }

    private String convertJpDate(JapaneseDate date){
        StringBuilder result = new StringBuilder();
        int y = date.year() + 1;
        int m = date.month();
        int d = date.day();
        result.append(y > 9 ? y: "0" + y);
        result.append(m > 9 ? m : "0" + m);
        result.append(d > 9 ? d: "0" + d);
        return result.toString();
    }


}
