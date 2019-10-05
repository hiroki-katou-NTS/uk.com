package nts.uk.file.pr.infra.core.socinsurnoticreset;

import com.aspose.cells.Cells;
import com.aspose.cells.Worksheet;
import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsurancePrefectureInformation;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.*;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.*;
import nts.uk.shr.com.time.japanese.JapaneseDate;
import nts.uk.shr.com.time.japanese.JapaneseEraName;
import nts.uk.shr.com.time.japanese.JapaneseErasAdapter;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Stateless
public class NotificationOfLossInsCSVAposeFileGenerator extends AsposeCellsReportGenerator implements NotificationOfLossInsCSVFileGenerator {

    private static final String REPORT_ID = "CSV_GENERATOR";

    @Inject
    private JapaneseErasAdapter adapter;

    private static final String SHOWA = "昭和";

    private static final String HEISEI = "平成";



    @Override
    public void generate(FileGeneratorContext generatorContext, LossNotificationInformation data) {
        CompanyInfor company = data.getCompany();
        val reportContext = this.createEmptyContext(REPORT_ID);
        val workbook = reportContext.getWorkbook();
        val sheet = workbook.getWorksheets().get(0);
        if(data.getSocialInsurNotiCreateSet().getOutputFormat().get() == OutputFormatClass.PEN_OFFICE) {
            fillPensionOffice(data.getHealthInsLoss(), sheet,data.getInfor(), company, data.getSocialInsurNotiCreateSet(), data.getBaseDate());
        }
        if(data.getSocialInsurNotiCreateSet().getOutputFormat().get() == OutputFormatClass.HEAL_INSUR_ASSO) {
            fillHealthInsAssociationOffice(data.getHealthInsLoss(), sheet,data.getInfor(), company, data.getSocialInsurNotiCreateSet(), data.getBaseDate());
        }
        if(data.getSocialInsurNotiCreateSet().getOutputFormat().get() == OutputFormatClass.THE_WELF_PEN) {
            fillEmpPensionFundOffice(data.getHealthInsAssociationData(), sheet, data.getInfor(), company, data.getSocialInsurNotiCreateSet(), data.getBaseDate());
        }
        reportContext.processDesigner();
        reportContext.saveAsCSV(this.createNewFile(generatorContext, getFileName(data.getSocialInsurNotiCreateSet().getOutputFormat().get() ) + ".csv"));
    }

    private String getFileName(OutputFormatClass output){
        if( output == OutputFormatClass.PEN_OFFICE) {
            return "SHFD0006";
        }
        if( output == OutputFormatClass.HEAL_INSUR_ASSO) {
            return "KPFD0006";
        }
        return "KNFD0006";
    }

    private void fillPensionOffice(List<InsLossDataExport> healthInsLoss, Worksheet worksheet,
                                   List<SocialInsurancePrefectureInformation> infor, CompanyInfor company, SocialInsurNotiCreateSet ins, GeneralDate baseDate){
        Cells cells = worksheet.getCells();
        int startRow = 0;
        for(int i = 0; i < healthInsLoss.size(); i++){
            InsLossDataExport data = healthInsLoss.get(i);
            if(i == 0) {
                cells.get(startRow, 0).setValue(getPreferCode(data.getPrefectureNo(), data.getEndDate(), infor));
                cells.get(startRow, 1).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? checkLength(data.getOfficeNumber1(), 2) : checkLength(data.getWelfOfficeNumber1(),2));
                cells.get(startRow, 2).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ?
                        checkLength(data.getOfficeNumber2(),4) : checkLength(data.getWelfOfficeNumber2(),4));
                cells.get(startRow, 3).setValue(Objects.toString(ins.getFdNumber().orElse(null), "001"));
                cells.get(startRow, 4).setValue(baseDate.toString("yyyyMMdd"));
                cells.get(startRow, 5).setValue("22223");
                startRow++;
                cells.get(startRow, 0).setValue("[kanri]");
                startRow++;
                cells.get(startRow, 0).setValue("");
                cells.get(startRow, 1).setValue("001");
                startRow++;
                cells.get(startRow, 0).setValue(getPreferCode(data.getPrefectureNo(), data.getEndDate(), infor));
                cells.get(startRow, 1).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? checkLength(data.getOfficeNumber1(),2) :
                        checkLength(data.getWelfOfficeNumber1(),2));
                cells.get(startRow, 2).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? checkLength(data.getOfficeNumber2(),4):
                        checkLength(data.getWelfOfficeNumber2(),4));
                cells.get(startRow, 3).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? data.getOfficeNumber() : data.getWelfOfficeNumber());
                cells.get(startRow, 4).setValue(checkLength(company.getPostCd(),3));
                cells.get(startRow, 5).setValue(company.getPostCd().length() == 8 ? company.getPostCd().substring(4, 8) : company.getPostCd().length() > 4 ? company.getPostCd().substring(4, company.getPostCd().length()) : "");
                cells.get(startRow, 6).setValue(checkLength(company.getAdd_1() + company.getAdd_2(),75));
                cells.get(startRow, 7).setValue(checkLength(company.getCompanyName(), 50));
                cells.get(startRow, 8).setValue(company.getRepname());
                cells.get(startRow, 9).setValue(formatPhoneNumber(company.getPhoneNum(),0));
                cells.get(startRow, 10).setValue(formatPhoneNumber(company.getPhoneNum(),1));
                cells.get(startRow, 11).setValue(formatPhoneNumber(company.getPhoneNum(),2));
                startRow++;
                cells.get(startRow, 0).setValue("[data]");
            }
            startRow++;
            fillPensionEmployee( data, cells, infor, ins, startRow);
        }
    }

    private JapaneseDate toJapaneseDate (GeneralDate date) {
        Optional<JapaneseEraName> era = this.adapter.getAllEras().eraOf(date);
        return new JapaneseDate(date, era.get());
    }


    private void fillPensionEmployee(InsLossDataExport data, Cells cells,
                                     List<SocialInsurancePrefectureInformation> infor, SocialInsurNotiCreateSet ins, int startRow){
        JapaneseDate dateJp = toJapaneseDate( GeneralDate.fromString(data.getBirthDay().substring(0,10), "yyyy-MM-dd"));
        cells.get(startRow, 0).setValue("2201700");
        cells.get(startRow, 1).setValue(getPreferCode(data.getPrefectureNo(), data.getEndDate(), infor));
        cells.get(startRow, 2).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ?
                checkLength(data.getOfficeNumber1(),2) : checkLength(data.getWelfOfficeNumber1(),2));
        cells.get(startRow, 3).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ?
                checkLength(data.getOfficeNumber2(),4) : checkLength(data.getWelfOfficeNumber2(),4));
        cells.get(startRow, 4).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? data.getOfficeNumber() : data.getWelfOfficeNumber());
        cells.get(startRow, 5).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? data.getHealInsNumber() : data.getWelfPenNumber());
        cells.get(startRow, 6).setValue(ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? checkLength(data.getPersonNameKana(),25) : checkLength(data.getOldNameKana(),25));
        cells.get(startRow, 7).setValue(ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? checkLength(data.getPersonName(),12) : checkLength(data.getOldName(),12));
        cells.get(startRow, 8).setValue(dateJp.era().equals(HEISEI) ? 7 : dateJp.era().equals(SHOWA) ? 5 : 9);
        cells.get(startRow, 9).setValue(convertJpDate(dateJp));
        cells.get(startRow, 10).setValue(ins.getPrintPersonNumber() != PersonalNumClass.DO_NOT_OUTPUT && ins.getPrintPersonNumber() != PersonalNumClass.OUTPUT_PER_NUMBER ? data.getBasicPenNumber() : "");
        cells.get(startRow, 11).setValue(ins.getTextPersonNumber().get() != TextPerNumberClass.OUTPUT_NUMBER ? checkLength(data.getBasicPenNumber(),4) : "");
        cells.get(startRow, 12).setValue(ins.getTextPersonNumber().get() != TextPerNumberClass.OUTPUT_NUMBER ? data.getBasicPenNumber().length() > 10 ? data.getBasicPenNumber().substring(4,10) :
                data.getBasicPenNumber().length() > 4 ? data.getBasicPenNumber().substring(4,data.getBasicPenNumber().length()) : "" : "");
        cells.get(startRow, 13).setValue(9);
        cells.get(startRow, 14).setValue(data.getEndDate().substring(0,4) + data.getEndDate().substring(5,7) + data.getEndDate().substring(8,10));
        cells.get(startRow, 15).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? data.getCause() == null ? "" : data.getCause() : data.getCause2() == null ? "": data.getCause2());
        cells.get(startRow, 16).setValue(9);
        cells.get(startRow, 17).setValue(data.getCause() == null ? ""  : data.getCause() == 4 && data.getCause() == 5 ? ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? convertDate(data.getEndDate()) : convertDate(data.getEndDate2()) : "");
        cells.get(startRow, 18).setValue(data.getIsMoreEmp());
        cells.get(startRow, 19).setValue(data.getContinReemAfterRetirement());
        cells.get(startRow, 20).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? data.getOtherReason() : data.getOtherReason2());
        cells.get(startRow, 21).setValue(Objects.toString(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? data.getCaInsurance() : data.getNumRecoved2(), ""));
        cells.get(startRow, 22).setValue(Objects.toString(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? data.getCaInsurance() : data.getNumRecoved2(), ""));
        cells.get(startRow, 23).setValue(data.getPercentOrMore().equals("1") ? 1 : "");
        cells.get(startRow, 24).setValue(data.getPercentOrMore().equals("1") ? 9 : "");
        cells.get(startRow, 25).setValue(data.getPercentOrMore() == null ? "" : data.getPercentOrMore().equals("1")  ? convertDate(data.getEndDate()) : convertDate(data.getEndDate2()));
        cells.get(startRow, 26).setValue(data.getCause2() == null ? "" : data.getCause2() == 6 ? "" : 1);

    }


    private String convertDate(String date){
        if(date.length() < 10)  {
            return "";
        }
        GeneralDate temp = GeneralDate.fromString(date.substring(0,10), "yyyy-MM-dd");
        return temp.addDays(-1).toString("yyyyMMdd");
    }

    private String convertJpDate(JapaneseDate date){
        int d = date.day();
        int y = date.year() + 1;
        int m = date.month();
        StringBuilder result = new StringBuilder();
        result.append(y > 9 ? y: "0" + y);
        result.append(m > 9 ? m : "0" + m);
        result.append(d > 9 ? d: "0" + d);
        return result.toString();
    }

    private String getPreferCode(int prefectureNo, String endDate, List<SocialInsurancePrefectureInformation> infor){
        Optional<SocialInsurancePrefectureInformation> refecture =  infor.stream().filter(item -> item.getNo() == prefectureNo
                && item.getEndYearMonth() >= convertDateToYearMonth(endDate)
                && item.getStartYearMonth() <= convertDateToYearMonth(endDate)).findFirst();
        return refecture.isPresent() ? refecture.get().getPrefectureCode().v() : "";
    }

    private int convertDateToYearMonth(String date){
        return Integer.parseInt(date.substring(0,4) + date.substring(5,7));
    }

    private void fillHealthInsAssociationOffice(List<InsLossDataExport> healthInsAssociation, Worksheet worksheet,List<SocialInsurancePrefectureInformation> infor, CompanyInfor company, SocialInsurNotiCreateSet ins, GeneralDate baseDate){
        Cells cells = worksheet.getCells();
        int startRow = 0;
        for(int i = 0; i < healthInsAssociation.size(); i++){
            InsLossDataExport data = healthInsAssociation.get(i);
            if(i == 0) {
                cells.get(startRow, 0).setValue(data.getUnionOfficeNumber());
                cells.get(startRow, 1).setValue(Objects.toString(ins.getFdNumber().orElse(null), "001"));
                cells.get(startRow, 2).setValue(baseDate.toString("yyyyMMdd"));
                cells.get(startRow, 4).setValue(checkLength(data.getHealInsInherenPr(),10));
                cells.get(startRow, 5).setValue(data.getHealInsInherenPr().length() > 20 ? data.getHealInsInherenPr().substring(10, 20) : "");
                cells.get(startRow, 6).setValue(data.getHealInsInherenPr().length() > 30 ? data.getHealInsInherenPr().substring(20, 30) : "");
                cells.get(startRow, 7).setValue(data.getHealInsInherenPr().length() > 40 ? data.getHealInsInherenPr().substring(30, 40) : "");
                startRow = startRow + 1;
                cells.get(startRow, 0).setValue("[kanri]");
                startRow = startRow + 1;
                cells.get(startRow, 1).setValue("001");
                startRow = startRow + 1;
                cells.get(startRow, 0).setValue(data.getUnionOfficeNumber());
                cells.get(startRow, 1).setValue(checkLength(company.getPostCd(), 3));
                cells.get(startRow, 2).setValue(company.getPostCd().length() == 8 ? company.getPostCd().substring(4, 8) : company.getPostCd().length() > 4 ? company.getPostCd().substring(4, company.getPostCd().length()) : "");
                cells.get(startRow, 3).setValue(checkLength(company.getAdd_1() + company.getAdd_2(),75));
                cells.get(startRow, 4).setValue(checkLength(company.getCompanyName(), 50));
                cells.get(startRow, 5).setValue(company.getRepname());
                cells.get(startRow, 6).setValue(formatPhoneNumber(company.getPhoneNum(),0));
                cells.get(startRow, 7).setValue(formatPhoneNumber(company.getPhoneNum(),1));
                cells.get(startRow, 8).setValue(formatPhoneNumber(company.getPhoneNum(),2));
                startRow++;
                cells.get(startRow, 0).setValue("[data]");
            }
            startRow++;
            fillHealthInsAssociation( data, cells, infor, ins, startRow++);

        }
    }

    private void fillHealthInsAssociation(InsLossDataExport data, Cells cells,
                                          List<SocialInsurancePrefectureInformation> infor, SocialInsurNotiCreateSet ins, int startRow){
        JapaneseDate dateJp = toJapaneseDate( GeneralDate.fromString(data.getBirthDay().substring(0,10), "yyyy-MM-dd"));
        cells.get(startRow, 0).setValue("2201700");
        cells.get(startRow, 1).setValue(getPreferCode(data.getPrefectureNo(), data.getEndDate(), infor));
        cells.get(startRow, 2).setValue(checkLength(data.getOfficeNumber1(),2));
        cells.get(startRow, 3).setValue(checkLength(data.getOfficeNumber2(),4));
        cells.get(startRow, 4).setValue(data.getOfficeNumber());
        cells.get(startRow, 5).setValue(data.getHealInsNumber());
        cells.get(startRow, 6).setValue(ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? checkLength(data.getPersonNameKana(),25) : checkLength(data.getOldNameKana(),25));
        cells.get(startRow, 7).setValue(ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? checkLength(data.getPersonName(),12) : checkLength(data.getOldName(),12));
        cells.get(startRow, 8).setValue(dateJp.era().equals(HEISEI) ? 7 : dateJp.era().equals(SHOWA) ? 5 : 9);
        cells.get(startRow, 9).setValue(convertJpDate(dateJp));
        cells.get(startRow, 10).setValue(ins.getPrintPersonNumber() != PersonalNumClass.DO_NOT_OUTPUT && ins.getPrintPersonNumber() != PersonalNumClass.OUTPUT_PER_NUMBER ? data.getBasicPenNumber() : "");
        cells.get(startRow, 11).setValue(ins.getTextPersonNumber().get() != TextPerNumberClass.OUTPUT_NUMBER ? checkLength(data.getBasicPenNumber(),4) : "");
        cells.get(startRow, 12).setValue(ins.getTextPersonNumber().get() != TextPerNumberClass.OUTPUT_NUMBER ? data.getBasicPenNumber().length() > 10 ? data.getBasicPenNumber().substring(4,10) :
                data.getBasicPenNumber().length() > 4 ? data.getBasicPenNumber().substring(4,data.getBasicPenNumber().length()) : "" : "");
        cells.get(startRow, 13).setValue(9);
        cells.get(startRow, 14).setValue(data.getEndDate().substring(0,4) + data.getEndDate().substring(5,7) + data.getEndDate().substring(8,10));
        cells.get(startRow, 15).setValue(Objects.toString(data.getCause(), ""));
        cells.get(startRow, 16).setValue(9);
        cells.get(startRow, 17).setValue(data.getCause()!= null && (data.getCause() == 4 || data.getCause() == 5) ? data.getEndDate().substring(0,4) + data.getEndDate().substring(5,7) + data.getEndDate().substring(8,10) : "");
        cells.get(startRow, 18).setValue(data.getIsMoreEmp());
        cells.get(startRow, 19).setValue(data.getContinReemAfterRetirement());
        cells.get(startRow, 20).setValue(data.getOtherReason());
        cells.get(startRow, 21).setValue(data.getCaInsurance());
        cells.get(startRow, 22).setValue(data.getNumRecoved());
        cells.get(startRow, 27).setValue(data.getUnionOfficeNumber());
        cells.get(startRow, 28).setValue(data.getHealInsUnionNumber());
        cells.get(startRow, 29).setValue(data.getHealInsInherenPr());
    }

    private void fillEmpPensionFundOffice(List<PensFundSubmissData> healthInsAssociation, Worksheet worksheet,List<SocialInsurancePrefectureInformation> infor,
                                          CompanyInfor company, SocialInsurNotiCreateSet socialInsurNotiCreateSet, GeneralDate baseDate){
        Cells cells = worksheet.getCells();
        int startRow = 0;
        for(int i = 0; i < healthInsAssociation.size(); i++){
            PensFundSubmissData data = healthInsAssociation.get(i);
            if(i == 0 ) {
                cells.get(startRow, 0).setValue(data.getOfficeNumber());
                cells.get(startRow, 1).setValue(Objects.toString(socialInsurNotiCreateSet.getFdNumber().orElse(null), "001"));
                cells.get(startRow, 2).setValue(baseDate.toString("yyyyMMdd"));
                cells.get(startRow, 13).setValue(data.getFunSpecific1());
                cells.get(startRow, 14).setValue(data.getFunSpecific2());
                cells.get(startRow, 15).setValue(data.getFunSpecific3());
                cells.get(startRow, 16).setValue(data.getFunSpecific4());
                cells.get(startRow, 17).setValue(data.getFunSpecific5());
                cells.get(startRow, 18).setValue(data.getFunSpecific6());
                cells.get(startRow, 19).setValue(data.getFunSpecific7());
                cells.get(startRow, 20).setValue(data.getFunSpecific8());
                cells.get(startRow, 21).setValue(data.getFunSpecific9());
                cells.get(startRow, 22).setValue(data.getFunSpecific10());
                startRow++;
                cells.get(startRow, 0).setValue("[kanri]");
                startRow++;
                cells.get(startRow, 1).setValue("001");
                startRow++;
                cells.get(startRow, 0).setValue(data.getFunMember());
                cells.get(startRow, 1).setValue(data.getWelPenOfficeNumber());
                cells.get(startRow, 2).setValue(checkLength(company.getPostCd(),3));
                cells.get(startRow, 3).setValue(company.getPostCd().length() == 8 ? company.getPostCd().substring(4, 8) : company.getPostCd().length() > 4 ? company.getPostCd().substring(4, company.getPostCd().length()) : "");
                cells.get(startRow, 4).setValue(checkLength(company.getAdd_1() + company.getAdd_2(), 75));
                cells.get(startRow, 5).setValue(checkLength(company.getCompanyName(),50));
                cells.get(startRow, 6).setValue(company.getRepname());
                cells.get(startRow, 7).setValue(formatPhoneNumber(company.getPhoneNum(),0));
                cells.get(startRow, 8).setValue(formatPhoneNumber(company.getPhoneNum(),1));
                cells.get(startRow, 9).setValue(formatPhoneNumber(company.getPhoneNum(),2));
                startRow++;
                cells.get(startRow, 0).setValue("[data]");
            }
            startRow++;
            fillEmpPensionFund(data, cells, infor , socialInsurNotiCreateSet, startRow);
        }

    }

    private String formatPhoneNumber(String phone, int stt) {
        String[] sub = phone.split("-");
        if (stt == 2 && sub.length >= 3) {
            return sub[2];
        }
        if (stt == 0 && sub.length > 1) {
            return sub[0];
        }
        if (stt == 1 && sub.length >= 2) {
            return sub[1];
        }
        if (sub.length == 1 && stt == 0 && phone.length() >= 3) {
            return phone.substring(0, 3);
        }
        if (sub.length == 1 && stt == 2 && phone.length() > 6) {
            return phone.substring(6, phone.length());
        }
        if (sub.length == 1 && stt == 1 && phone.length() >= 6) {
            return phone.substring(3, 6);
        }
        if (sub.length == 1 && phone.length() < 3) {
            return phone;
        }
        return "";
    }

    private void fillEmpPensionFund(PensFundSubmissData data, Cells cells,
                       List<SocialInsurancePrefectureInformation> infor, SocialInsurNotiCreateSet ins, int startRow){
        JapaneseDate dateJp = toJapaneseDate( GeneralDate.fromString(data.getBirthDay().substring(0,10), "yyyy-MM-dd"));
        cells.get(startRow, 0).setValue("2201700");
        cells.get(startRow, 1).setValue(getPreferCode(data.getPrefectureNo(), data.getEndDate(), infor));
        cells.get(startRow, 2).setValue(checkLength(data.getOfficeNumber1(),2));
        cells.get(startRow, 3).setValue(checkLength(data.getOfficeNumber2(),4));
        cells.get(startRow, 4).setValue(data.getWelPenOfficeNumber());
        cells.get(startRow, 5).setValue(data.getHealInsNumber());
        cells.get(startRow, 6).setValue(ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? checkLength(data.getPersonName(),25) : checkLength(data.getPersonNameKana(),25));
        cells.get(startRow, 7).setValue(ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? checkLength(data.getOldName(),12) : checkLength(data.getOldNameKana(),12));
        cells.get(startRow, 8).setValue(dateJp.era().equals(HEISEI) ? 7 : dateJp.era().equals(SHOWA) ? 5 : 9);
        cells.get(startRow, 9).setValue(convertJpDate(dateJp));
        cells.get(startRow, 11).setValue(ins.getTextPersonNumber().get() != TextPerNumberClass.OUTPUT_NUMBER ? data.getBasicPenNumber().substring(0,4) : "");
        cells.get(startRow, 12).setValue(ins.getTextPersonNumber().get() != TextPerNumberClass.OUTPUT_NUMBER ? data.getBasicPenNumber().length() > 10 ? data.getBasicPenNumber().substring(4,10) :
                data.getBasicPenNumber().length() > 4 ? data.getBasicPenNumber().substring(4,data.getBasicPenNumber().length()) : "" : "");
        cells.get(startRow, 13).setValue(9);
        cells.get(startRow, 14).setValue(data.getEndDate().substring(0,4) + data.getEndDate().substring(5,7) + data.getEndDate().substring(8,10));
        cells.get(startRow, 15).setValue(data.getCause());
        cells.get(startRow, 16).setValue(9);
        cells.get(startRow, 17).setValue(data.getCause() == 4 && data.getCause() == 5 ? convertDate(data.getEndDate()) : "");
        cells.get(startRow, 18).setValue(data.getIsMoreEmp());
        cells.get(startRow, 19).setValue(data.getContinReemAfterRetirement());
        cells.get(startRow, 20).setValue(data.getOtherReason());
        cells.get(startRow, 23).setValue(data.getCause() == 6 ? 1 : "");
        cells.get(startRow, 24).setValue(data.getCause() == 6 ? 9 : "");
        cells.get(startRow, 25).setValue(data.getCause() == 6 ? data.getEndDate().substring(0,4) + data.getEndDate().substring(5,7) + data.getEndDate().substring(8,10) : "");
        cells.get(startRow, 27).setValue(data.getFunMember());
        cells.get(startRow, 28).setValue(data.getWelPenOfficeNumber());
        cells.get(startRow, 29).setValue(data.getMemberNumber());
        cells.get(startRow,30).setValue(checkLength(data.getPortCd(),3));
        cells.get(startRow, 31).setValue(data.getPortCd().length() > 7 ? data.getPortCd().substring(3,7) : "");
        cells.get(startRow, 32).setValue(checkLength(data.getRetirementAddBefore(),75));
        cells.get(startRow,33).setValue(checkLength(data.getRetirementAdd(),37));
        cells.get(startRow, 34).setValue(data.getReasonForLoss());
        cells.get(startRow, 35).setValue(data.getAddAppCtgSal());
        cells.get(startRow, 36).setValue(data.getReason());
        cells.get(startRow, 37).setValue(data.getAddSal().isEmpty() ? "" : Double.parseDouble(data.getAddSal().trim()) > 10000000 ? "9999999" : data.getAddSal());
        cells.get(startRow, 38).setValue(data.getStandSal().isEmpty() ? "" : Double.parseDouble(data.getStandSal().trim()) > 10000000 ? "9999" : data.getStandSal().length() > 4 ? data.getStandSal().substring(0,data.getStandSal().length() - 3) : data.getStandSal());
        cells.get(startRow, 39).setValue(data.getSecAddSalary().isEmpty() ? "" : Double.parseDouble(data.getSecAddSalary().trim()) > 10000000 ? "9999999" : data.getSecAddSalary());
        cells.get(startRow, 40).setValue(data.getSecStandSal().isEmpty() ? "" : Double.parseDouble(data.getSecStandSal().trim()) > 10000000 ? "9999" : data.getSecStandSal().length() > 4 ? data.getSecStandSal().substring(0,data.getSecStandSal().length() - 3) : data.getSecStandSal());
        cells.get(startRow, 41).setValue(data.getFunSpecific1());
        cells.get(startRow, 42).setValue(data.getFunSpecific2());
        cells.get(startRow, 43).setValue(data.getFunSpecific3());
        cells.get(startRow, 44).setValue(data.getFunSpecific4());
        cells.get(startRow, 45).setValue(data.getFunSpecific5());
        cells.get(startRow, 46).setValue(data.getFunSpecific6());
        cells.get(startRow, 47).setValue(data.getFunSpecific7());
        cells.get(startRow, 48).setValue(data.getFunSpecific8());
        cells.get(startRow, 49).setValue(data.getFunSpecific9());
        cells.get(startRow, 50).setValue(data.getFunSpecific10());
    }

    private String checkLength(String s, int digitsNumber){
        return (s != null && s.length() >= digitsNumber) ? s.substring(0,digitsNumber) : s;
    }

}


