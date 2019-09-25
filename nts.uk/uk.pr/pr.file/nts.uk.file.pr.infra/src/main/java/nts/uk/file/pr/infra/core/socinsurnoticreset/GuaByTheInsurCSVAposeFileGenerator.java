package nts.uk.file.pr.infra.core.socinsurnoticreset;

import com.aspose.cells.*;
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
public class GuaByTheInsurCSVAposeFileGenerator extends AsposeCellsReportGenerator implements GuaByTheInsurExportCSVGenerator {

    private static final String REPORT_ID = "CSV_GENERATOR";

    private static final String SHOWA = "昭和";

    private static final String HEISEI = "平成";

    @Inject
    private JapaneseErasAdapter adapter;

    @Override
    public void generate(FileGeneratorContext generatorContext, ExportDataCsv exportData) {
        try (val reportContext = this.createEmptyContext(REPORT_ID)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            if(exportData.getIns().getOutputFormat().get() == OutputFormatClass.PEN_OFFICE) {
                fillPensionOffice(exportData.getPensionOfficeData(), worksheets.get(0), exportData.getInfor(), exportData.getCompany(), exportData.getIns(), exportData.getBaseDate());
            }
            if(exportData.getIns().getOutputFormat().get() == OutputFormatClass.HEAL_INSUR_ASSO) {
                fillHealthInsAssociationOffice(exportData.getPensionOfficeData(), worksheets.get(0), exportData.getInfor(), exportData.getCompany(), exportData.getIns(), exportData.getBaseDate());
            }
            if(exportData.getIns().getOutputFormat().get() == OutputFormatClass.THE_WELF_PEN) {
                fillEmpPensionFundOffice(exportData.getEmpPenFundSub(), worksheets.get(0), exportData.getInfor(), exportData.getCompany(), exportData.getIns(), exportData.getBaseDate());
            }
            reportContext.getDesigner().setWorkbook(workbook);
            reportContext.processDesigner();
            reportContext.saveAsCSV((this.createNewFile(generatorContext, getFileName(exportData.getIns().getOutputFormat().get()) + ".csv")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    private void fillPensionOffice(List<PensionOfficeDataExport> pensionOfficeData, Worksheet worksheet,
                                   List<SocialInsurancePrefectureInformation> infor, CompanyInfor company, SocialInsurNotiCreateSet ins, GeneralDate baseDate){
        Cells cells = worksheet.getCells();
        int startRow = 0;
        for(int i = 0; i < pensionOfficeData.size(); i++){
            PensionOfficeDataExport data = pensionOfficeData.get(i);
            if(i == 0) {
                cells.get(startRow, 0).setValue(getPreferCode(data.getHealPrefectureNo(), data.getStartDate1(), infor));
                cells.get(startRow, 1).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? checkLength(data.getHealOfficeNumber1(), 2) : checkLength(data.getWelOfficeNumber(), 2));
                cells.get(startRow, 2).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ?
                        checkLength(data.getHealOfficeNumber2(), 4) : checkLength(data.getWelOfficeNumber2(),4));
                cells.get(startRow, 3).setValue(ins.getFdNumber().isPresent() ? ins.getFdNumber().get() : "001");
                cells.get(startRow, 4).setValue(baseDate.toString("yyyyMMdd"));
                cells.get(startRow, 5).setValue("22223");
                startRow++;
                cells.get(startRow, 0).setValue("[kanri]");
                startRow++;
                cells.get(startRow, 0).setValue("");
                cells.get(startRow, 1).setValue("001");
                startRow++;
                cells.get(startRow, 0).setValue(getPreferCode(data.getHealPrefectureNo(), data.getStartDate1(), infor));
                cells.get(startRow, 1).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? checkLength(data.getHealOfficeNumber1(),2) :
                        checkLength(data.getWelOfficeNumber1(),2));
                cells.get(startRow, 2).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? data.getHealOfficeNumber2().length() > 4 ? data.getHealOfficeNumber2().substring(0, 4) :
                        data.getHealOfficeNumber2() : data.getHealOfficeNumber2().length() > 4 ? data.getWelOfficeNumber2().substring(0, 4) : data.getWelOfficeNumber2());
                cells.get(startRow, 4).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? data.getHealOfficeNumber() : data.getWelOfficeNumber());
                cells.get(startRow, 5).setValue(company.getPostCd().length() > 3 ? company.getPostCd().substring(0, 3) : company.getPostCd());
                cells.get(startRow, 6).setValue(company.getPostCd().length() == 8 ? company.getPostCd().substring(0, 4) : "");
                cells.get(startRow, 7).setValue((company.getAdd_1() + company.getAdd_2()).length() > 37 ? (company.getAdd_1() + company.getAdd_2()).substring(0, 37) : company.getAdd_1() + company.getAdd_2());
                cells.get(startRow, 8).setValue(company.getCompanyName().length() > 25 ? company.getCompanyName().substring(0, 25) : company.getCompanyName());
                cells.get(startRow, 9).setValue(company.getRepname());
                cells.get(startRow, 10).setValue(company.getPhoneNum().length() > 5 ? company.getPhoneNum().substring(0, 5) : company.getPhoneNum());
                cells.get(startRow, 11).setValue(company.getPhoneNum().length() > 11 ? company.getPhoneNum().substring(6, 10) : "");
                cells.get(startRow, 12).setValue(company.getPhoneNum().length() > 16 ? company.getPhoneNum().substring(11, 16) : "");
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

    private void fillPensionEmployee(PensionOfficeDataExport data, Cells cells,
                                     List<SocialInsurancePrefectureInformation> infor, SocialInsurNotiCreateSet ins, int startRow){
        JapaneseDate dateJp = toJapaneseDate( GeneralDate.fromString(data.getBirthDay().substring(0,10), "yyyy-MM-dd"));
        JapaneseDate healStart = toJapaneseDate( GeneralDate.fromString(data.getStartDate1().substring(0,10), "yyyy-MM-dd"));
        if(!data.getStartDate2().equals("")){
            JapaneseDate welStart = toJapaneseDate( GeneralDate.fromString(data.getStartDate2().substring(0,10), "yyyy-MM-dd"));
            cells.get(startRow, 17).setValue(conpareDate(data.getStartDate1(), data.getStartDate2()) ? healStart.era().equals(HEISEI) ? 7 : dateJp.era().equals(SHOWA) ? 5 : 9 : welStart.era().equals(HEISEI) ? 7 : dateJp.era().equals(SHOWA) ? 5 : 9);
            cells.get(startRow, 18).setValue(conpareDate(data.getStartDate1(), data.getStartDate2()) ? convertJpDate(healStart) : convertJpDate(welStart));

        }

        cells.get(startRow, 0).setValue("2200700");
        cells.get(startRow, 1).setValue(getPreferCode(data.getHealPrefectureNo(), data.getStartDate1(), infor));
        cells.get(startRow, 2).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? data.getHealOfficeNumber1().length() > 2 ? data.getHealOfficeNumber1().substring(0, 2) : data.getHealOfficeNumber1() :
                data.getWelOfficeNumber1().length() > 2 ? data.getWelOfficeNumber1().substring(0, 2) : data.getWelOfficeNumber1());
        cells.get(startRow, 3).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? data.getHealOfficeNumber2().length() > 4 ? data.getHealOfficeNumber2().substring(0, 4) : data.getHealOfficeNumber2() :
                data.getWelOfficeNumber2().length() > 4 ? data.getWelOfficeNumber2().substring(0, 4) : data.getWelOfficeNumber2());
        cells.get(startRow, 4).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? data.getHealOfficeNumber() : data.getWelOfficeNumber());

        cells.get(startRow, 6).setValue(ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? data.getPersonName().length() > 25 ? data.getPersonName().substring(0,25) : data.getPersonName() :
                data.getPersonNameKana().length() > 25 ? data.getPersonNameKana().substring(0,25) : data.getPersonNameKana());
        cells.get(startRow, 7).setValue(ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? data.getOldName().length() > 12 ? data.getOldName().substring(0,12) : data.getOldName() :
                data.getOldNameKana().length() > 12 ? data.getOldName().substring(0,12) : data.getOldName());
        cells.get(startRow, 8).setValue(dateJp.era().equals(HEISEI) ? 7 : dateJp.era().equals(SHOWA) ? 5 : 9);
        cells.get(startRow, 9).setValue(convertJpDate(dateJp));
        cells.get(startRow, 10).setValue(ins.getPrintPersonNumber() != PersonalNumClass.DO_NOT_OUTPUT && ins.getPrintPersonNumber() != PersonalNumClass.OUTPUT_PER_NUMBER ? data.getGender() : data.getHealInsCtg());
        cells.get(startRow, 11).setValue(data.getDistin());
        cells.get(startRow, 13).setValue(data.getLivingAbroad() == 1 ? data.getLivingAbroad() :  data.getShortStay() == 1 ? data.getShortStay() : data.getResonOther());
        cells.get(startRow, 14).setValue(data.getResonAndOtherContent());

        cells.get(startRow, 21).setValue(data.getDepenAppoint());
        cells.get(startRow, 22).setValue(data.getRemunMonthlyAmount());
        cells.get(startRow, 23).setValue(data.getRemunMonthlyAmountKind());
        cells.get(startRow, 24).setValue(data.getTotalMonthyRemun());
        cells.get(startRow, 25).setValue(data.getPercentOrMore());
        cells.get(startRow, 26).setValue(data.getIsMoreEmp() == 1 ? 1 : "") ;
        cells.get(startRow, 27).setValue(data.getShortTimeWorkes() == 1 ? 1 : "");
        cells.get(startRow, 28).setValue(data.getContinReemAfterRetirement() == 1 ? 1 : "");
        cells.get(startRow, 29).setValue(checkLength(data.getRemarksAndOtherContent(),37));
        cells.get(startRow, 34).setValue(data.getPercentOrMore() == 1 ? 1 : "");
    }


    private boolean conpareDate(String start1 ,String start2){
        return GeneralDate.fromString(start1.substring(0,10), "yyyy-MM-dd").after(GeneralDate.fromString(start2.substring(0,10), "yyyy-MM-dd"));
    }

    private void fillHealthInsAssociationOffice(List<PensionOfficeDataExport> healthInsAssociation, Worksheet worksheet,List<SocialInsurancePrefectureInformation> infor, CompanyInfor company, SocialInsurNotiCreateSet ins, GeneralDate baseDate){
        Cells cells = worksheet.getCells();
        int startRow = 0;
        for(int i = 0; i < healthInsAssociation.size(); i++){
            PensionOfficeDataExport data = healthInsAssociation.get(i);
            if(i == 0) {
                cells.get(startRow, 0).setValue(data.getUnionOfficeNumber());
                cells.get(startRow, 1).setValue(Objects.toString(ins.getFdNumber().orElse(null), "001"));
                cells.get(startRow, 2).setValue(baseDate.toString("yyyyMMdd"));
                cells.get(startRow, 4).setValue(data.getHealInsInherenPr().length() > 10 ? data.getHealInsInherenPr().substring(0, 10) : data.getHealInsInherenPr());
                startRow = startRow + 1;
                cells.get(startRow, 0).setValue("[kanri]");
                startRow = startRow + 1;
                cells.get(startRow, 1).setValue("001");
                startRow = startRow + 1;
                cells.get(startRow, 1).setValue(company.getPostCd().length() > 3 ? company.getPostCd().substring(0, 3) : company.getPostCd());
                cells.get(startRow, 2).setValue(company.getPostCd().length() == 8 ? company.getPostCd().substring(4, 8) : "");
                cells.get(startRow, 3).setValue((company.getAdd_1() + company.getAdd_2()).length() > 0 ? (company.getAdd_1() + company.getAdd_2()).substring(0, 37) : company.getAdd_1() + company.getAdd_2());
                cells.get(startRow, 4).setValue(company.getCompanyName().length() > 25 ? company.getCompanyName().substring(0, 25) : company.getCompanyName());
                cells.get(startRow, 5).setValue(company.getRepname().length() > 7 ? company.getRepname().substring(0,7) : company.getRepname());
                cells.get(startRow, 10).setValue(company.getPhoneNum().length() > 5 ? company.getPhoneNum().substring(0, 5) : company.getPhoneNum());
                cells.get(startRow, 11).setValue(company.getPhoneNum().length() > 10 ? company.getPhoneNum().substring(6, 10) : "");
                cells.get(startRow, 12).setValue(company.getPhoneNum().length() > 16 ? company.getPhoneNum().substring(11, 16) : "");
                startRow++;
                cells.get(startRow, 0).setValue("[data]");
            }
            startRow++;
            fillHealthInsAssociation( data, cells, infor, ins, startRow++);

        }
    }

    private void fillHealthInsAssociation(PensionOfficeDataExport data, Cells cells,
                                          List<SocialInsurancePrefectureInformation> infor, SocialInsurNotiCreateSet ins, int startRow){
        JapaneseDate dateJp = toJapaneseDate( GeneralDate.fromString(data.getBirthDay().substring(0,10), "yyyy-MM-dd"));
        JapaneseDate startDateJp = toJapaneseDate( GeneralDate.fromString(data.getStartDate1().substring(0,10), "yyyy-MM-dd"));
        cells.get(startRow, 0).setValue("2200700");
        cells.get(startRow, 1).setValue(getPreferCode(data.getHealPrefectureNo(), data.getStartDate1(), infor));
        cells.get(startRow, 2).setValue( data.getHealOfficeNumber1().length() > 2 ? data.getHealOfficeNumber1().substring(0, 2) : data.getHealOfficeNumber1());
        cells.get(startRow, 3).setValue(data.getHealOfficeNumber2().length() > 4 ? data.getHealOfficeNumber2().substring(0, 4) : data.getHealOfficeNumber2());
        cells.get(startRow, 4).setValue(data.getHealOfficeNumber());
        cells.get(startRow, 6).setValue(ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? data.getPersonName().length() > 25 ? data.getPersonName().substring(0,25) : data.getPersonName() :
        data.getPersonNameKana().length() > 25 ? data.getPersonNameKana().substring(0,25) : data.getPersonNameKana());
        cells.get(startRow, 7).setValue(ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? data.getOldName().length() > 12 ? data.getOldName().substring(0,12) : data.getOldName() :
                data.getOldNameKana().length() > 12 ? data.getOldName().substring(0,12) : data.getOldName());
        cells.get(startRow, 8).setValue(dateJp.era().equals(HEISEI) ? 7 : dateJp.era().equals(SHOWA) ? 5 : 9);
        cells.get(startRow, 9).setValue(convertJpDate(dateJp));
        cells.get(startRow, 10).setValue(ins.getPrintPersonNumber() != PersonalNumClass.DO_NOT_OUTPUT && ins.getPrintPersonNumber() != PersonalNumClass.OUTPUT_PER_NUMBER ? data.getGender() : data.getHealInsCtg());
        cells.get(startRow, 11).setValue(data.getDistin());

        cells.get(startRow, 14).setValue(data.getResonAndOtherContent());
        cells.get(startRow, 17).setValue(startDateJp.era().equals(HEISEI) ? 7 : dateJp.era().equals(SHOWA) ? 5 : 9);
        cells.get(startRow, 18).setValue(data.getStartDate1().substring(0,4) + data.getStartDate1().substring(5,7) + data.getStartDate1().substring(8,10));
        cells.get(startRow, 19).setValue(data.getDepenAppoint());
        cells.get(startRow,20).setValue(data.getRemunMonthlyAmount());
        cells.get(startRow, 23).setValue(data.getRemunMonthlyAmountKind());
        cells.get(startRow, 24).setValue(data.getTotalMonthyRemun());
        cells.get(startRow, 25).setValue(data.getPercentOrMore());
        cells.get(startRow, 26).setValue(data.getIsMoreEmp() == 1 ? 1 : "") ;
        cells.get(startRow, 27).setValue(data.getShortTimeWorkes() == 1 ? 1 : "");
        cells.get(startRow, 28).setValue(data.getContinReemAfterRetirement() == 1 ? 1 : "");
        cells.get(startRow, 29).setValue(checkLength(data.getRemarksAndOtherContent(),37));
        cells.get(startRow, 33).setValue(data.getPercentOrMore() == 1 ? 1 : "");
        cells.get(startRow,34).setValue(data.getHealUnionNumber());
        cells.get(startRow,35).setValue(data.getHealInsInherenPr());
    }

    private void fillEmpPensionFundOffice(List<EmpPenFundSubData> empPenFundSub, Worksheet worksheet,List<SocialInsurancePrefectureInformation> infor,
                                          CompanyInfor company, SocialInsurNotiCreateSet socialInsurNotiCreateSet, GeneralDate baseDate){
        Cells cells = worksheet.getCells();
        int startRow = 0;
        for(int i = 0; i < empPenFundSub.size(); i++){
            EmpPenFundSubData data = empPenFundSub.get(i);
            if(i == 0 ) {
                cells.get(startRow, 0).setValue(data.getWelPenOfficeNumber());
                cells.get(startRow, 1).setValue(Objects.toString(socialInsurNotiCreateSet.getFdNumber().orElse(null), "001"));
                cells.get(startRow, 2).setValue(baseDate.toString("yyyyMMdd"));
                cells.get(startRow, 3).setValue(data.getFunSpecific1());
                cells.get(startRow, 4).setValue(data.getFunSpecific2());
                cells.get(startRow, 5).setValue(data.getFunSpecific3());
                cells.get(startRow, 6).setValue(data.getFunSpecific4());
                cells.get(startRow, 7).setValue(data.getFunSpecific5());
                cells.get(startRow, 8).setValue(data.getFunSpecific6());
                cells.get(startRow, 9).setValue(data.getFunSpecific7());
                cells.get(startRow, 10).setValue(data.getFunSpecific8());
                cells.get(startRow, 11).setValue(data.getFunSpecific9());
                cells.get(startRow, 12).setValue(data.getFunSpecific10());
                startRow++;
                cells.get(startRow, 0).setValue("[kanri]");
                startRow++;
                cells.get(startRow, 1).setValue("001");
                cells.get(startRow, 2).setValue(data.getFunMember());
                cells.get(startRow, 3).setValue(company.getPostCd().length() > 3 ? company.getPostCd().substring(0, 3) : company.getPostCd());
                cells.get(startRow, 4).setValue(company.getPostCd().length() == 8  ? company.getPostCd().substring(4, 8) : "");
                cells.get(startRow, 5).setValue((company.getAdd_1() + company.getAdd_2()).length() > 0 ? (company.getAdd_1() + company.getAdd_2()).substring(0, 25) : company.getAdd_1() + company.getAdd_2());
                cells.get(startRow, 4).setValue(company.getCompanyName().length() > 25 ? company.getCompanyName().substring(0, 25) : company.getCompanyName());
                cells.get(startRow, 5).setValue(company.getRepname());
                cells.get(startRow, 10).setValue(company.getPhoneNum().length() > 5 ? company.getPhoneNum().substring(0, 5) : company.getPhoneNum());
                cells.get(startRow, 11).setValue(company.getPhoneNum().length() > 11 ? company.getPhoneNum().substring(6, 10) : "");
                cells.get(startRow, 12).setValue(company.getPhoneNum().length() > 16 ? company.getPhoneNum().substring(11, 16) : "");
                startRow++;
                cells.get(startRow, 0).setValue("[data]");
            }
            startRow++;
            fillEmpPensionFund(data, cells, infor , socialInsurNotiCreateSet, startRow);
        }

    }

    private void fillEmpPensionFund(EmpPenFundSubData data, Cells cells,
                                    List<SocialInsurancePrefectureInformation> infor, SocialInsurNotiCreateSet ins, int startRow){
        JapaneseDate dateJp = toJapaneseDate( GeneralDate.fromString(data.getBirthDay().substring(0,10), "yyyy-MM-dd"));
        cells.get(startRow, 0).setValue("2200700");
        cells.get(startRow, 1).setValue(getPreferCode(data.getPrefectureNo(), data.getEndDate(), infor));
        cells.get(startRow, 2).setValue(data.getWelOfficeNumber1().length() > 2 ? data.getWelOfficeNumber1().substring(0, 2) : data.getWelOfficeNumber1());
        cells.get(startRow, 3).setValue(data.getWelOfficeNumber2().length() > 4 ?data.getWelOfficeNumber2().substring(0, 4) : data.getWelOfficeNumber2());
        cells.get(startRow, 4).setValue(data.getWelPenOfficeNumber());
        cells.get(startRow, 6).setValue(ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? data.getPersonName() : data.getPersonNameKana());
        cells.get(startRow, 7).setValue(ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? data.getOldName() : data.getOldNameKana());
        cells.get(startRow, 8).setValue(dateJp.era().equals(HEISEI) ? 7 : dateJp.era().equals(SHOWA) ? 5 : 9);
        cells.get(startRow, 9).setValue(convertJpDate(dateJp));
        cells.get(startRow, 11).setValue(ins.getTextPersonNumber().get() != TextPerNumberClass.OUTPUT_NUMBER ? data.getGender() : data.getUnderDivision());
        cells.get(startRow, 12).setValue(data.getQualifiDistin());
        cells.get(startRow, 17).setValue(9);
        cells.get(startRow, 18).setValue(data.getEndDate().substring(0,4) + data.getEndDate().substring(5,7) + data.getEndDate().substring(8,10));
        cells.get(startRow, 19).setValue(data.getDepenAppoint());
        cells.get(startRow, 20).setValue(data.getRemunMonthlyAmount());
        cells.get(startRow, 17).setValue(data.getRemunMonthlyAmountKind());
        cells.get(startRow, 18).setValue(data.getTotalMonthlyRemun());
        cells.get(startRow, 19).setValue(data.getPercentOrMore());
        cells.get(startRow, 20).setValue(data.getIsMoreEmp());
        cells.get(startRow, 23).setValue(data.getShortStay());
        cells.get(startRow, 24).setValue(data.getContinReemAfterRetirement());
        cells.get(startRow, 25).setValue(data.getRemarksAndOtherContents());
        cells.get(startRow, 27).setValue(data.getFunMember());
        cells.get(startRow, 28).setValue(data.getWelPenOfficeNumber());
        cells.get(startRow, 29).setValue(data.getMemberNumber());
        cells.get(startRow,30).setValue(data.getSubType());
        cells.get(startRow, 31).setValue(data.getAddAppCtgSal());
        cells.get(startRow, 32).setValue(data.getAppFormCls());
        cells.get(startRow,33).setValue(data.getAddSal());

        cells.get(startRow, 39).setValue(data.getSecAddSalary());
        cells.get(startRow, 40).setValue(data.getSecStandSal());
        cells.get(startRow, 41).setValue(data.getFunSpecific1());
        cells.get(startRow, 42).setValue(data.getFunSpecific2());
        cells.get(startRow, 43).setValue(data.getFunSpecific3());
        cells.get(startRow, 44).setValue(data.getFunSpecific4());
        cells.get(startRow, 45).setValue(data.getFunSpecific5());
        cells.get(startRow, 46).setValue(data.getFunSpecific6());
        cells.get(startRow, 47).setValue(data.getFunSpecific7());
        cells.get(startRow, 54).setValue(data.getFunSpecific8());
        cells.get(startRow, 54).setValue(data.getFunSpecific9());
        cells.get(startRow, 55).setValue(data.getFunSpecific10());
    }

    private String convertJpDate(JapaneseDate date){
        int m = date.month();
        int d = date.day();
        int y = date.year() + 1;
        StringBuilder result = new StringBuilder();
        result.append(y > 9 ? y: "0" + y);
        result.append(m > 9 ? m : "0" + m);
        result.append(d > 9 ? d: "0" + d);
        return result.toString();
    }

    private String checkLength(String s, int digitsNumber){
        return (s != null && s.length() > digitsNumber) ? s.substring(0,digitsNumber) : s;
    }

    private String getPreferCode(int prefectureNo, String endDate, List<SocialInsurancePrefectureInformation> infor){
        Optional<SocialInsurancePrefectureInformation> refecture =  infor.stream().filter(item -> item.getNo() == prefectureNo
                && item.getEndYearMonth() > convertDateToYearMonth(endDate)
                && item.getEndYearMonth() < convertDateToYearMonth(endDate)).findFirst();
        return refecture.isPresent() ? refecture.get().getPrefectureCode().v() : "";
    }

    private int convertDateToYearMonth(String date){
        return Integer.parseInt(date.substring(0,4) + date.substring(5,6));
    }
}
