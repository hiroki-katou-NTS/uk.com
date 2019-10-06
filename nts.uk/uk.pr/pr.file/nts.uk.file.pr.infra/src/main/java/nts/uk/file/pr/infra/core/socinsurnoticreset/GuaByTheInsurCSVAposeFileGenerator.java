package nts.uk.file.pr.infra.core.socinsurnoticreset;

import com.aspose.cells.Cells;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;
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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Stateless
public class GuaByTheInsurCSVAposeFileGenerator extends AsposeCellsReportGenerator implements GuaByTheInsurExportCSVGenerator {

    private static final String REPORT_ID = "CSV_GENERATOR";


    private static final String SHOWA = "昭和";

    private static final String HEISEI = "平成";

    private static final String PEACE = "令和";

    private static final int EMP_IN_PAGE = 4;

    private static final String TAISO = "明治";

    private static final String MEI = "大正";

    @Inject
    private JapaneseErasAdapter adapter;

    @Override
    public void generate(FileGeneratorContext generatorContext, ExportDataCsv exportData) {
        try (val reportContext = this.createEmptyContext(REPORT_ID)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            if (exportData.getIns().getOutputFormat().get() == OutputFormatClass.PEN_OFFICE) {
                fillPensionOffice(exportData.getPensionOfficeData(), worksheets.get(0), exportData.getInfor(), exportData.getCompany(), exportData.getIns(), exportData.getBaseDate(), convertYearMonthToInt(exportData.getStartDate()), convertYearMonthToInt(exportData.getEndDate()));
            }
            if (exportData.getIns().getOutputFormat().get() == OutputFormatClass.HEAL_INSUR_ASSO) {
                fillHealthInsAssociationOffice(exportData.getPensionOfficeData(), worksheets.get(0), exportData.getInfor(), exportData.getCompany(), exportData.getIns(), exportData.getBaseDate(), convertYearMonthToInt(exportData.getStartDate()), convertYearMonthToInt(exportData.getEndDate()));
            }
            if (exportData.getIns().getOutputFormat().get() == OutputFormatClass.THE_WELF_PEN) {
                fillEmpPensionFundOffice(exportData.getEmpPenFundSub(), worksheets.get(0), exportData.getInfor(), exportData.getCompany(), exportData.getIns(), exportData.getBaseDate(), convertYearMonthToInt(exportData.getStartDate()), convertYearMonthToInt(exportData.getEndDate()));
            }
            reportContext.getDesigner().setWorkbook(workbook);
            reportContext.processDesigner();
            reportContext.saveAsCSV((this.createNewFile(generatorContext, getFileName(exportData.getIns().getOutputFormat().get()) + ".csv")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getFileName(OutputFormatClass output) {
        if (output == OutputFormatClass.PEN_OFFICE) {
            return "SHFD0006";
        }
        if (output == OutputFormatClass.HEAL_INSUR_ASSO) {
            return "KPFD0006";
        }
        return "KNFD0006";
    }

    public int convertYearMonthToInt(GeneralDate yearMonth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        String formattedString = yearMonth.localDate().format(formatter);
        return Integer.valueOf(formattedString);

    }

    private void fillPensionOffice(List<PensionOfficeDataExport> pensionOfficeData, Worksheet worksheet,
                                   List<SocialInsurancePrefectureInformation> infor, CompanyInfor company, SocialInsurNotiCreateSet ins, GeneralDate baseDate, int startDate, int endDate) {
        Cells cells = worksheet.getCells();
        int startRow = 0;
        for (int i = 0; i < pensionOfficeData.size(); i++) {
            PensionOfficeDataExport data = pensionOfficeData.get(i);
            if (i == 0) {
                cells.get(startRow, 0).setValue(getPreferCode(data.getHealPrefectureNo(), startDate, endDate, infor));
                cells.get(startRow, 1).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? checkLength(data.getHealOfficeNumber1(), 2) : checkLength(data.getWelOfficeNumber1(), 2));
                cells.get(startRow, 2).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? checkLength(data.getHealOfficeNumber2(), 4) : checkLength(data.getWelOfficeNumber2(), 4));
                cells.get(startRow, 3).setValue(ins.getFdNumber().isPresent() ? ins.getFdNumber().get() : "001");
                cells.get(startRow, 4).setValue(baseDate.toString("yyyyMMdd"));
                cells.get(startRow, 5).setValue("22223");
                startRow++;
                cells.get(startRow, 0).setValue("[kanri]");
                startRow++;
                cells.get(startRow, 0).setValue("");
                cells.get(startRow, 1).setValue("001");
                startRow++;
                cells.get(startRow, 0).setValue(getPreferCode(data.getHealPrefectureNo(), startDate, endDate, infor));
                cells.get(startRow, 1).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? checkLength(data.getHealOfficeNumber1(), 2) :checkLength(data.getWelOfficeNumber1(), 2));
                cells.get(startRow, 2).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? checkLength(data.getHealOfficeNumber2(), 4) :checkLength(data.getWelOfficeNumber2(), 4));
                cells.get(startRow, 3).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? data.getHealOfficeNumber() : data.getWelOfficeNumber());
                String portCd = ins.getOfficeInformation().value == 0 ? company.getPostCd() : data.getPortCd();
                cells.get(startRow, 4).setValue(formatPortCd(portCd, 1));
                cells.get(startRow, 5).setValue(formatPortCd(portCd, 2));
                String add1 = ins.getOfficeInformation().value == 0 ? company.getAdd_1() + company.getAdd_2() : data.getAdd();
                cells.get(startRow, 6).setValue(checkLength(add1, 37));

                String companyName = ins.getOfficeInformation().value == 0 ? company.getCompanyName() : data.getCompanyName();
                String repName = ins.getOfficeInformation().value == 0 ? company.getRepname() : data.getRepName();
                cells.get(startRow, 7).setValue(companyName.length() > 25 ? companyName.substring(0, 25) : companyName);
                cells.get(startRow, 8).setValue(repName);
                String phoneNumber = ins.getOfficeInformation().value == 0 ? company.getPhoneNum() : data.getPhoneNumber();
                cells.get(startRow, 9).setValue(formatPhone(phoneNumber, 0));
                cells.get(startRow, 10).setValue(formatPhone(phoneNumber, 1));
                cells.get(startRow, 11).setValue(formatPhone(phoneNumber, 2));
                startRow++;
                cells.get(startRow, 0).setValue("[data]");
            }
            startRow++;
            fillPensionEmployee(data, cells, infor, ins, startRow, startDate, endDate, company);

        }
    }

    private String formatPhone(String phone, int stt) {
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

    private JapaneseDate toJapaneseDate(GeneralDate date) {
        Optional<JapaneseEraName> era = this.adapter.getAllEras().eraOf(date);
        return new JapaneseDate(date, era.get());
    }

    private String formatPortCd(String portCd, int stt) {
        String result = portCd.replace("-", "");
        if (result.length() >= 3 && stt == 1) {
            return result.substring(0, 3);
        }
        if (stt == 2 && result.length() > 3) {
            return result.substring(3, result.length());
        }
        return result;
    }

    private void fillPensionEmployee(PensionOfficeDataExport data, Cells cells,
                                     List<SocialInsurancePrefectureInformation> infor, SocialInsurNotiCreateSet ins, int startRow, int startDate, int endDate,CompanyInfor company) {
        JapaneseDate dateJp = toJapaneseDate(GeneralDate.fromString(data.getBirthDay().substring(0, 10), "yyyy-MM-dd"));
        JapaneseDate healStart = toJapaneseDate(GeneralDate.fromString(data.getStartDate1().substring(0, 10), "yyyy-MM-dd"));
        if (!data.getStartDate2().equals("")) {
            JapaneseDate welStart = toJapaneseDate(GeneralDate.fromString(data.getStartDate2().substring(0, 10), "yyyy-MM-dd"));
            cells.get(startRow, 17).setValue(conpareDate(data.getStartDate1(), data.getStartDate2()) ? healStart.era().equals(HEISEI) ? 7 : dateJp.era().equals(SHOWA) ? 5 : 9 : welStart.era().equals(HEISEI) ? 7 : dateJp.era().equals(SHOWA) ? 5 : 9);
            cells.get(startRow, 18).setValue(conpareDate(data.getStartDate1(), data.getStartDate2()) ? convertJpDate(healStart) : convertJpDate(welStart));

        }

        cells.get(startRow, 0).setValue("2200700");
        cells.get(startRow, 1).setValue(getPreferCode(data.getHealPrefectureNo(), startDate, endDate, infor));
        cells.get(startRow, 2).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? checkLength(data.getHealOfficeNumber1(), 2) :checkLength(data.getWelOfficeNumber1(), 2));
        cells.get(startRow, 3).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? checkLength(data.getHealOfficeNumber2(), 4) : checkLength(data.getWelOfficeNumber2(), 4));
        cells.get(startRow, 4).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? data.getHealOfficeNumber() : data.getWelOfficeNumber());

        cells.get(startRow, 6).setValue(ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? data.getPersonName().length() > 25 ? data.getPersonName().substring(0, 25) : data.getPersonName() :
                data.getPersonNameKana().length() > 25 ? data.getPersonNameKana().substring(0, 25) : data.getPersonNameKana());
        cells.get(startRow, 7).setValue(ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? data.getOldName().length() > 12 ? data.getOldName().substring(0, 12) : data.getOldName() :
                data.getOldNameKana().length() > 12 ? data.getOldName().substring(0, 12) : data.getOldName());
        cells.get(startRow, 8).setValue(dateJp.era().equals(HEISEI) ? 7 : dateJp.era().equals(SHOWA) ? 5 : 9);
        cells.get(startRow, 9).setValue(convertJpDate(dateJp));
        cells.get(startRow, 10).setValue(ins.getPrintPersonNumber() != PersonalNumClass.DO_NOT_OUTPUT && ins.getPrintPersonNumber() != PersonalNumClass.OUTPUT_PER_NUMBER ? data.getGender() : data.getHealInsCtg());
        cells.get(startRow, 11).setValue(data.getDistin());
        cells.get(startRow, 13).setValue(data.getLivingAbroad() == 1 ? data.getLivingAbroad() : data.getShortStay() == 1 ? data.getShortStay() : data.getResonOther());
        cells.get(startRow, 14).setValue(data.getResonAndOtherContent());

        cells.get(startRow, 21).setValue(data.getDepenAppoint());
        cells.get(startRow, 22).setValue(data.getRemunMonthlyAmount());
        cells.get(startRow, 23).setValue(data.getRemunMonthlyAmountKind());
        cells.get(startRow, 24).setValue(data.getTotalMonthyRemun());
        cells.get(startRow, 25).setValue(data.getPercentOrMore() == 1 ? 1 : "");
        cells.get(startRow, 26).setValue(data.getIsMoreEmp() == 1 ? 1 : "");
        cells.get(startRow, 27).setValue(data.getShortTimeWorkes() == 1 ? 1 : "");
        cells.get(startRow, 28).setValue(data.getContinReemAfterRetirement() == 1 ? 1 : "");
        cells.get(startRow, 29).setValue(checkLength(data.getRemarksAndOtherContent(), 37));
        // bổ sung design
        String portCd = ins.getOfficeInformation().value == 0 ? company.getPostCd(): data.getPortCd();
        String add = ins.getOfficeInformation().value == 0 ? company.getAdd_1() +" "+ company.getAdd_2(): data.getAdd();
        String addKana = ins.getOfficeInformation().value == 0 ? company.getAddKana_1() + company.getAddKana_2(): data.getAddKana();
        cells.get(startRow, 30).setValue(formatPortCd(portCd, 1));
        cells.get(startRow, 31).setValue(formatPortCd(portCd, 2));
        cells.get(startRow, 32).setValue(add);
        cells.get(startRow, 33).setValue(addKana);
        //
        cells.get(startRow, 34).setValue(data.getPercentOrMore() == 1 ? 1 : "");
    }


    private boolean conpareDate(String start1, String start2) {
        return GeneralDate.fromString(start1.substring(0, 10), "yyyy-MM-dd").after(GeneralDate.fromString(start2.substring(0, 10), "yyyy-MM-dd"));
    }

    private void fillHealthInsAssociationOffice(List<PensionOfficeDataExport> healthInsAssociation,
                                                Worksheet worksheet, List<SocialInsurancePrefectureInformation> infor, CompanyInfor company, SocialInsurNotiCreateSet ins, GeneralDate baseDate, int startDate, int endDate) {
        Cells cells = worksheet.getCells();
        int startRow = 0;
        for (int i = 0; i < healthInsAssociation.size(); i++) {
            PensionOfficeDataExport data = healthInsAssociation.get(i);
            if (i == 0) {
                cells.get(startRow, 0).setValue(data.getUnionOfficeNumber());
                cells.get(startRow, 1).setValue(Objects.toString(ins.getFdNumber().orElse(null), "001"));
                cells.get(startRow, 2).setValue(baseDate.toString("yyyyMMdd"));
                cells.get(startRow, 3).setValue(data.getHealInsInherenPr().length() > 10 ? data.getHealInsInherenPr().substring(0, 10) : data.getHealInsInherenPr());
                startRow = startRow + 1;
                cells.get(startRow, 0).setValue("[kanri]");
                startRow = startRow + 1;
                cells.get(startRow, 1).setValue("001");
                startRow = startRow + 1;
                cells.get(startRow, 0).setValue(data.getUnionOfficeNumber());
                String portCd = ins.getOfficeInformation().value == 0 ? company.getPostCd() : data.getPortCd();
                cells.get(startRow, 1).setValue(formatPortCd(portCd, 1));
                cells.get(startRow, 2).setValue(formatPortCd(portCd, 2));
                String add = ins.getOfficeInformation().value == 0 ? company.getAdd_1() +" "+ company.getAdd_2() : data.getAdd();
                cells.get(startRow, 3).setValue(checkLength(add, 37));
                String companyName = ins.getOfficeInformation().value == 0 ? company.getCompanyName() : data.getCompanyName();
                cells.get(startRow, 4).setValue(companyName.length() > 25 ? companyName.substring(0, 25) : companyName);
                String repName = ins.getOfficeInformation().value == 0 ? company.getRepname() : data.getRepName();
                cells.get(startRow, 5).setValue(repName.length() > 7 ? repName.substring(0, 7) :repName);
                String phoneNumber = ins.getOfficeInformation().value == 0 ? company.getPhoneNum() : data.getPhoneNumber();
                cells.get(startRow, 6).setValue(formatPhone(phoneNumber, 0));
                cells.get(startRow, 7).setValue(formatPhone(phoneNumber, 1));
                cells.get(startRow, 8).setValue(formatPhone(phoneNumber, 2));
                startRow++;
                cells.get(startRow, 0).setValue("[data]");
            }
            startRow++;
            fillHealthInsAssociation(data, cells, infor, ins, startRow++, startDate, endDate);

        }
    }

    private void fillHealthInsAssociation(PensionOfficeDataExport data, Cells cells,
                                          List<SocialInsurancePrefectureInformation> infor, SocialInsurNotiCreateSet ins, int startRow, int startDate, int endDate) {
        JapaneseDate dateJp = toJapaneseDate(GeneralDate.fromString(data.getBirthDay().substring(0, 10), "yyyy-MM-dd"));
        JapaneseDate startDateJp = toJapaneseDate(GeneralDate.fromString(data.getStartDate1().substring(0, 10), "yyyy-MM-dd"));
        cells.get(startRow, 0).setValue("2200700");
        cells.get(startRow, 1).setValue(getPreferCode(data.getHealPrefectureNo(), startDate, endDate, infor));
        cells.get(startRow, 2).setValue(data.getHealOfficeNumber1().length() > 2 ? data.getHealOfficeNumber1().substring(0, 2) : data.getHealOfficeNumber1());
        cells.get(startRow, 3).setValue(data.getHealOfficeNumber2().length() > 4 ? data.getHealOfficeNumber2().substring(0, 4) : data.getHealOfficeNumber2());
        cells.get(startRow, 4).setValue(data.getHealOfficeNumber());
        cells.get(startRow, 6).setValue(ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? data.getPersonName().length() > 25 ? data.getPersonName().substring(0, 25) : data.getPersonName() :
                data.getPersonNameKana().length() > 25 ? data.getPersonNameKana().substring(0, 25) : data.getPersonNameKana());
        cells.get(startRow, 7).setValue(ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? data.getOldName().length() > 12 ? data.getOldName().substring(0, 12) : data.getOldName() :
                data.getOldNameKana().length() > 12 ? data.getOldName().substring(0, 12) : data.getOldName());
        cells.get(startRow, 8).setValue(dateJp.era().equals(HEISEI) ? 7 : dateJp.era().equals(SHOWA) ? 5 : 9);
        cells.get(startRow, 9).setValue(convertJpDate(dateJp));
        // gender
        //Male(1), Female(2)
        String hisId = data.getHisId();
        int gender = data.getGender();
        int undergoundDivision = data.getUnderDivision();
        if (hisId.equals("")) {
            if (gender == 1 && undergoundDivision == 0) {
                cells.get(startRow, 10).setValue("1");

            }
            if (gender == 2 && undergoundDivision == 0) {
                cells.get(startRow, 10).setValue("2");

            }
            if (undergoundDivision == 1) {
                cells.get(startRow, 10).setValue("3");

            }
        } else {
            if (gender == 1 && undergoundDivision == 0) {
                cells.get(startRow, 10).setValue("5");

            }
            if (gender == 2 && undergoundDivision == 0) {
                cells.get(startRow, 10).setValue("6");

            }
            if (undergoundDivision == 1) {
                cells.get(startRow, 10).setValue("7");

            }
        }
        //
        cells.get(startRow, 11).setValue(data.getDistin());
        //
        if (data.getLivingAbroad()  == 1 ) {
            cells.get(startRow, 13).setValue("1");
        }
        if (data.getShortStay() == 2) {
            cells.get(startRow, 13).setValue("2");
        }
        if (data.getResonOther() == 3) {
            cells.get(startRow, 13).setValue("3");
        }
        if (data.getShortTimeWorkes() == 3) {
            cells.get(startRow, 13).setValue("3");
        }
        //
        cells.get(startRow, 14).setValue(data.getResonAndOtherContent());
        cells.get(startRow, 17).setValue(startDateJp.era().equals(HEISEI) ? 7 : dateJp.era().equals(SHOWA) ? 5 : 9);
        cells.get(startRow, 18).setValue(data.getStartDate1().substring(0, 4) + data.getStartDate1().substring(5, 7) + data.getStartDate1().substring(8, 10));
        cells.get(startRow, 19).setValue(data.getDepenAppoint());
        cells.get(startRow, 20).setValue(data.getRemunMonthlyAmount());
        cells.get(startRow, 21).setValue(data.getRemunMonthlyAmountKind());
        cells.get(startRow, 22).setValue(data.getTotalMonthyRemun());

        cells.get(startRow, 25).setValue(data.getPercentOrMore());
        cells.get(startRow, 26).setValue(data.getIsMoreEmp() == 1 ? 1 : "");
        cells.get(startRow, 27).setValue(data.getShortTimeWorkes() == 1 ? 1 : "");
        cells.get(startRow, 28).setValue(data.getContinReemAfterRetirement() == 1 ? 1 : "");
        cells.get(startRow, 29).setValue(checkLength(data.getRemarksAndOtherContent(), 37));
        cells.get(startRow, 33).setValue(data.getPercentOrMore() == 1 ? 1 : "");
        cells.get(startRow, 34).setValue(data.getHealUnionNumber());
        cells.get(startRow, 35).setValue(data.getHealInsInherenPr());
    }

    private void fillEmpPensionFundOffice(List<EmpPenFundSubData> empPenFundSub, Worksheet worksheet, List<SocialInsurancePrefectureInformation> infor,
                                          CompanyInfor company, SocialInsurNotiCreateSet socialInsurNotiCreateSet, GeneralDate baseDate, int startDate, int endDate) {
        Cells cells = worksheet.getCells();
        int startRow = 0;
        for (int i = 0; i < empPenFundSub.size(); i++) {
            EmpPenFundSubData data = empPenFundSub.get(i);
            if (i == 0) {
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
                startRow++;
//                cells.get(startRow, 0).setValue(data.getFunMember());
//                cells.get(startRow, 1).setValue(data.getWelPenOfficeNumber());
                String portCd = socialInsurNotiCreateSet.getOfficeInformation().value == 0 ? company.getPostCd() : data.getPortCd();
                cells.get(startRow, 2).setValue(formatPortCd(portCd, 1));
                cells.get(startRow, 3).setValue(formatPortCd(portCd, 2));

                String add = socialInsurNotiCreateSet.getOfficeInformation().value == 0 ? company.getAdd_1()+" "+company.getAdd_2() : data.getAdd();
                String addKana = socialInsurNotiCreateSet.getOfficeInformation().value == 0 ? company.getAddKana_1()+" "+company.getAddKana_2() : data.getAddKana();
                String companyName = socialInsurNotiCreateSet.getOfficeInformation().value == 0 ? company.getCompanyName() : data.getCompanyName();
                String repName = socialInsurNotiCreateSet.getOfficeInformation().value == 0 ? company.getRepname() : data.getRepName();
                String phoneNumber = socialInsurNotiCreateSet.getOfficeInformation().value == 0 ? company.getPhoneNum() : data.getPhoneNumber();

                cells.get(startRow, 4).setValue(checkLength(add, 25));
                cells.get(startRow, 5).setValue(companyName.length() > 25 ?companyName.substring(0, 25) : companyName);
                cells.get(startRow, 6).setValue(repName);
                cells.get(startRow, 7).setValue(formatPhone(phoneNumber, 0));
                cells.get(startRow, 8).setValue(formatPhone(phoneNumber, 1));
                cells.get(startRow, 9).setValue(formatPhone(phoneNumber, 2));
                startRow++;
                cells.get(startRow, 0).setValue("[data]");
            }
            startRow++;
            fillEmpPensionFund(data, cells, infor, socialInsurNotiCreateSet, startRow, startDate, endDate, company);
        }

    }

    private void fillEmpPensionFund(EmpPenFundSubData data, Cells cells,
                                    List<SocialInsurancePrefectureInformation> infor, SocialInsurNotiCreateSet ins, int startRow, int startDate, int endDate,CompanyInfor company) {
        JapaneseDate addAppCtgSal = !data.getBirthDay().isEmpty() ? toJapaneseDate( GeneralDate.fromString(data.getBirthDay().substring(0,10), "yyyy-MM-dd")) : null;
        JapaneseDate dateJp = toJapaneseDate(GeneralDate.fromString(data.getBirthDay().substring(0, 10), "yyyy-MM-dd"));
        JapaneseDate startDateKohoInfo = toJapaneseDate(GeneralDate.fromString(data.getStartDate().substring(0, 10), "yyyy-MM-dd"));
        cells.get(startRow, 0).setValue("2200700");
        cells.get(startRow, 1).setValue(getPreferCode(data.getPrefectureNo(), startDate, endDate, infor));
        cells.get(startRow, 2).setValue(data.getWelOfficeNumber1().length() > 2 ? data.getWelOfficeNumber1().substring(0, 2) : data.getWelOfficeNumber1());
        cells.get(startRow, 3).setValue(data.getWelOfficeNumber2().length() > 4 ? data.getWelOfficeNumber2().substring(0, 4) : data.getWelOfficeNumber2());
        cells.get(startRow, 4).setValue(data.getWelPenOfficeNumber());
        cells.get(startRow, 6).setValue(ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? data.getPersonNameKana() : data.getOldNameKana());
        cells.get(startRow, 7).setValue(ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? data.getPersonName() : data.getOldName());
        cells.get(startRow, 8).setValue(findEra(dateJp.era()));
        cells.get(startRow, 9).setValue(convertJpDate(dateJp));
        //Male(1), Female(2)
        String hisId = data.getHisId();
        int undergoundDivision = data.getUnderDivision();
        int gender = data.getGender();
        if (hisId.equals("")) {
            if (gender == 1 && undergoundDivision == 0) {
                cells.get(startRow, 10).setValue("1");

            }
            if (gender == 2 && undergoundDivision == 0) {
                cells.get(startRow, 10).setValue("2");

            }
            if (undergoundDivision == 1) {
                cells.get(startRow, 10).setValue("3");

            }
        } else {
            if (gender == 1 && undergoundDivision == 0) {
                cells.get(startRow, 10).setValue("5");

            }
            if (gender == 2 && undergoundDivision == 0) {
                cells.get(startRow, 10).setValue("6");

            }
            if (undergoundDivision == 1) {
                cells.get(startRow, 10).setValue("7");

            }
        }

        cells.get(startRow, 11).setValue(data.getQualifiDistin());
        cells.get(startRow, 12).setValue("");
        cells.get(startRow, 13).setValue("");
        cells.get(startRow, 14).setValue("");
//        cells.get(startRow, 15).setValue(ins.getTextPersonNumber().get() != TextPerNumberClass.OUTPUT_NUMBER ? checkLength(data.getBasicPenNumber(),4) : "");
//        cells.get(startRow, 16).setValue(ins.getTextPersonNumber().get() != TextPerNumberClass.OUTPUT_NUMBER ? data.getBasicPenNumber().length() > 10 ? data.getBasicPenNumber().substring(4,10) :
//                data.getBasicPenNumber().length() > 4 ? data.getBasicPenNumber().substring(4,data.getBasicPenNumber().length()) : "" : "");
        cells.get(startRow, 17).setValue(9);
        cells.get(startRow, 18).setValue(convertJpDate(startDateKohoInfo));
        cells.get(startRow, 19).setValue(data.getDepenAppoint());
        cells.get(startRow, 20).setValue(data.getRemunMonthlyAmount());
        cells.get(startRow, 21).setValue(data.getRemunMonthlyAmountKind());
        cells.get(startRow, 22).setValue(data.getTotalMonthlyRemun());
        cells.get(startRow, 23).setValue(data.getPercentOrMore());
        cells.get(startRow, 24).setValue(data.getIsMoreEmp());
        cells.get(startRow, 25).setValue(data.getShortStay());
        cells.get(startRow, 26).setValue(data.getContinReemAfterRetirement());
        cells.get(startRow, 27).setValue(data.getRemarksAndOtherContents());
        String portCd = ins.getOfficeInformation().value == 0 ? company.getPostCd() : data.getPortCd();
        String add = ins.getOfficeInformation().value == 0 ? company.getAdd_1()+" "+company.getAdd_2() : data.getAdd();
        String addKana = ins.getOfficeInformation().value == 0 ? company.getAddKana_1()+" "+company.getAddKana_2() : data.getAddKana();
//        cells.get(startRow, 28).setValue(formatPortCd(portCd, 1));
//        cells.get(startRow, 29).setValue(formatPortCd(portCd, 2));
//        cells.get(startRow, 30).setValue(addKana);
//        cells.get(startRow, 31).setValue(add);
//        cells.get(startRow, 32).setValue("");
//        cells.get(startRow, 33).setValue(data.getFunMember());
//        cells.get(startRow, 34).setValue(data.getWelPenOfficeNumber());
        cells.get(startRow, 35).setValue(data.getMemberNumber());
        cells.get(startRow, 36).setValue(data.getSubType());
//        cells.get(startRow, 37).setValue(findEra(addAppCtgSal.era()));
//        cells.get(startRow, 38).setValue(convertJpDate(addAppCtgSal));
        cells.get(startRow, 39).setValue(data.getAddAppCtgSal());
        cells.get(startRow, 40).setValue(findSubscriptionType(data.getAppFormCls()));
        cells.get(startRow, 41).setValue(data.getAddSal().isEmpty() ? "" : Double.parseDouble(data.getAddSal().trim()) > 10000000 ? "9999999" : data.getAddSal());
        cells.get(startRow, 42).setValue(data.getStandSal().isEmpty() ? "" : Double.parseDouble(data.getStandSal().trim()) > 10000000 ? "9999" : data.getStandSal().length() > 4 ? data.getStandSal().substring(0,4) : data.getStandSal());
        cells.get(startRow, 43).setValue(data.getSecAddSalary().isEmpty() ? "" : Double.parseDouble(data.getSecAddSalary().trim()) > 10000000 ? "9999999" : data.getSecAddSalary());
        cells.get(startRow, 44).setValue(data.getSecStandSal().isEmpty() ? "" : Double.parseDouble(data.getSecStandSal().trim()) > 10000000 ? "9999" : data.getSecStandSal().length() > 4 ? data.getSecStandSal().substring(0,4) : data.getSecStandSal());
        cells.get(startRow, 45).setValue(checkLengtFunSpecific(data.getFunSpecific1()));
        cells.get(startRow, 46).setValue(checkLengtFunSpecific(data.getFunSpecific2()));
        cells.get(startRow, 47).setValue(checkLengtFunSpecific(data.getFunSpecific3()));
        cells.get(startRow, 48).setValue(checkLengtFunSpecific(data.getFunSpecific4()));
        cells.get(startRow, 49).setValue(checkLengtFunSpecific(data.getFunSpecific5()));
        cells.get(startRow, 50).setValue(checkLengtFunSpecific(data.getFunSpecific6()));
        cells.get(startRow, 51).setValue(checkLengtFunSpecific(data.getFunSpecific7()));
        cells.get(startRow, 52).setValue(checkLengtFunSpecific(data.getFunSpecific8()));
        cells.get(startRow, 53).setValue(checkLengtFunSpecific(data.getFunSpecific9()));
        cells.get(startRow, 54).setValue(checkLengtFunSpecific(data.getFunSpecific10()));
    }
    private String checkLengtFunSpecific(String funSpecific){
        if(funSpecific.length() > 4){
            return funSpecific.substring(0,5);
        }
        return funSpecific;
    }
    private String findEra(String era) {
        if (era.equals(TAISO)) {
            return "1";
        }
        if (era.equals(MEI)) {
            return "3";
        }
        if (era.equals(SHOWA)) {
            return "5";
        }
        if (era.equals(HEISEI)) {
            return "7";
        }
        if (era.equals(PEACE)) {
            return "9";
        }
        return "";
    }
    private String findSubscriptionType(int applFormClass) {
        //    NEW(1, "新規"),
        //    MOVING(2, "転入"),
        //    REVIVAL(3, "復活"),
        //    REJOIN(4, "再加入");
       switch (applFormClass){
           case 1 : {
               return "1";
           }
           case 2 : {
               return "2";
           }
           case 3 : {
               return "3";
           }
           case 4 : {
               return "4";
           }

       }
       return "";
    }

    private String convertJpDate(JapaneseDate date) {
        int m = date.month();
        int d = date.day();
        int y = date.year() + 1;
        StringBuilder result = new StringBuilder();
        result.append(y > 9 ? y : "0" + y);
        result.append(m > 9 ? m : "0" + m);
        result.append(d > 9 ? d : "0" + d);
        return result.toString();
    }

    private String checkLength(String s, int digitsNumber) {
        return (s != null && s.length() > digitsNumber) ? s.substring(0, digitsNumber) : s;
    }

    private String getPreferCode(int prefectureNo, int startDate, int endDate, List<SocialInsurancePrefectureInformation> infor) {
        Optional<SocialInsurancePrefectureInformation> refecture = infor.stream()
                .filter( item -> item.getNo() == prefectureNo
                        && item.getStartYearMonth() >= startDate
                        && item.getStartYearMonth() <= endDate )
                .findFirst();
        return refecture.isPresent() ? refecture.get().getPrefectureCode().v() : "";
    }

    private int convertDateToYearMonth(String date) {
        return Integer.parseInt(date.substring(0, 4) + date.substring(5, 7));
    }

}
