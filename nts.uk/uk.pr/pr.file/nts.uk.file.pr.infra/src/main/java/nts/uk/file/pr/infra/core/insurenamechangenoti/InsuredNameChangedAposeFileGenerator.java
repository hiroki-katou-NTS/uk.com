package nts.uk.file.pr.infra.core.insurenamechangenoti;

import com.aspose.cells.*;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInfor;
import nts.uk.ctx.pr.file.app.core.insurenamechangenoti.InsuredNameChangedExportFileGenerator;
import nts.uk.ctx.pr.file.app.core.insurenamechangenoti.InsuredNameChangedNotiExportData;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.*;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.PitInsiderDivision;
import nts.uk.file.pr.infra.core.socinsurnoticreset.EmpAddChangeInfoPDFAposeFileGenerator;
import nts.uk.shr.com.time.japanese.JapaneseDate;
import nts.uk.shr.com.time.japanese.JapaneseEraName;
import nts.uk.shr.com.time.japanese.JapaneseErasAdapter;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Stateless
public class InsuredNameChangedAposeFileGenerator extends AsposeCellsReportGenerator implements InsuredNameChangedExportFileGenerator {

    private static final String TEMPLATE_FILE = "report/被保険者氏名変更届_帳票テンプレート.xlsx";

    private static final String REPORT_FILE_NAME = "被保険者氏名変更届.pdf";

    @Inject
    private JapaneseErasAdapter adapter;


    private static final String Meiji = "明治";

    private static final String Taisho = "大正";

    private static final String SHOWA = "昭和";

    private static final String HEISEI = "平成";

    private static final String PEACE = "令和";


    @Override
    public void generate(FileGeneratorContext fileContext, List<InsuredNameChangedNotiExportData> data, SocialInsurNotiCreateSet socialInsurNotiCreateSet, CompanyInfor company) {
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
            Workbook wb = reportContext.getWorkbook();
            WorksheetCollection wsc = wb.getWorksheets();


            for (int i = 1; i < data.size(); i++) {
                wsc.addCopy(0);
                this.writePDF(wsc, data.get(i), socialInsurNotiCreateSet, i, company);
            }

            this.writePDF(wsc, data.get(0), socialInsurNotiCreateSet, 0, company);

            reportContext.processDesigner();
            reportContext.saveAsPdf(this.createNewFile(fileContext, REPORT_FILE_NAME));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void writePDF(WorksheetCollection wsc, InsuredNameChangedNotiExportData data, SocialInsurNotiCreateSet socialInsurNotiCreateSet, int index, CompanyInfor company) throws UnsupportedEncodingException {
        
        Worksheet ws = wsc.get(index);
        String oldName = data.getPerson().getPersonNameGroup().getOldName().getFullName();
        String name = data.getPerson().getPersonNameGroup().getPersonName().getFullName();
        String nameKana = data.getPerson().getPersonNameGroup().getPersonName().getFullNameKana();
        String todoName = data.getPerson().getPersonNameGroup().getTodokedeFullName().getFullName();
        String todoNameKana = data.getPerson().getPersonNameGroup().getTodokedeFullName().getFullNameKana();
        String address = "";
        String phoneNumber = "";
        String healthInsuranceOfficeNumber1;
        String healthInsuranceOfficeNumber2[];
        String welfarePensionOfficeNumber1;
        String welfarePensionOfficeNumber2[];

        if (socialInsurNotiCreateSet.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL) {
            if (data.getSocialInsuranceOffice() != null && data.getSocialInsuranceOffice().getInsuranceMasterInformation().getOfficeOrganizeNumber().getHealthInsuranceOfficeNumber1().isPresent()) {
                healthInsuranceOfficeNumber1 = data.getSocialInsuranceOffice().getInsuranceMasterInformation().getOfficeOrganizeNumber().getHealthInsuranceOfficeNumber1().get().v();
                ws.getCells().get("E11").putValue(healthInsuranceOfficeNumber1.length() > 2 ? healthInsuranceOfficeNumber1.substring(0,3) : healthInsuranceOfficeNumber1);
            }
            if (data.getSocialInsuranceOffice() != null && data.getSocialInsuranceOffice().getInsuranceMasterInformation().getOfficeOrganizeNumber().getHealthInsuranceOfficeNumber2().isPresent()) {
                healthInsuranceOfficeNumber2 = data.getSocialInsuranceOffice().getInsuranceMasterInformation().getOfficeOrganizeNumber().getHealthInsuranceOfficeNumber2().get().v().split("");
                ws.getCells().get("G12").putValue(healthInsuranceOfficeNumber2.length > 0 ? healthInsuranceOfficeNumber2[0] : "");
                ws.getCells().get("H12").putValue(healthInsuranceOfficeNumber2.length > 1 ? healthInsuranceOfficeNumber2[1] : "");
                ws.getCells().get("I12").putValue(healthInsuranceOfficeNumber2.length > 2 ? healthInsuranceOfficeNumber2[2] : "");
                ws.getCells().get("J12").putValue(healthInsuranceOfficeNumber2.length > 3 ? healthInsuranceOfficeNumber2[3] : "");
            }

        } else {
            if (data.getSocialInsuranceOffice() != null && data.getSocialInsuranceOffice().getInsuranceMasterInformation().getOfficeOrganizeNumber().getWelfarePensionOfficeNumber1().isPresent()) {
                welfarePensionOfficeNumber1 = data.getSocialInsuranceOffice().getInsuranceMasterInformation().getOfficeOrganizeNumber().getWelfarePensionOfficeNumber1().get().v();
                ws.getCells().get("E11").putValue(welfarePensionOfficeNumber1.length() > 2 ? welfarePensionOfficeNumber1.substring(0,3) : welfarePensionOfficeNumber1);
            }
            if (data.getSocialInsuranceOffice() != null && data.getSocialInsuranceOffice().getInsuranceMasterInformation().getOfficeOrganizeNumber().getWelfarePensionOfficeNumber2().isPresent()) {
                welfarePensionOfficeNumber2 = data.getSocialInsuranceOffice().getInsuranceMasterInformation().getOfficeOrganizeNumber().getWelfarePensionOfficeNumber2().get().v().split("");
                ws.getCells().get("G12").putValue(welfarePensionOfficeNumber2.length > 0 ? welfarePensionOfficeNumber2[0] : "");
                ws.getCells().get("H12").putValue(welfarePensionOfficeNumber2.length > 1 ? welfarePensionOfficeNumber2[1] : "");
                ws.getCells().get("I12").putValue(welfarePensionOfficeNumber2.length > 2 ? welfarePensionOfficeNumber2[2] : "");
                ws.getCells().get("J12").putValue(welfarePensionOfficeNumber2.length > 3 ? welfarePensionOfficeNumber2[3] : "");
            }

        }

        //fill to A1_2
        if (socialInsurNotiCreateSet.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_THE_WELF_PENNUMBER) {
            ws.getCells().get("K11").putValue(data.getWelfPenNumInformation() != null ? data.getWelfPenNumInformation().getWelPenNumber().get() : null);
        } else if (socialInsurNotiCreateSet.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_THE_FUN_MEMBER) {
            ws.getCells().get("K11").putValue(data.getFundMembership() != null ? data.getFundMembership().getMembersNumber().v() : null);
        } else if (socialInsurNotiCreateSet.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_HEAL_INSUR_NUM) {
            ws.getCells().get("K11").putValue(data.getHealInsurNumberInfor() != null ? data.getHealInsurNumberInfor().getHealInsNumber().get().v() : null);
        } else if (socialInsurNotiCreateSet.getInsuredNumber() == InsurPersonNumDivision.OUTPUT_HEAL_INSUR_UNION) {
            ws.getCells().get("K11").putValue(data.getHealthCarePortInfor() != null ? data.getHealthCarePortInfor().getHealInsurUnionNumber().v() : null);
        }
        //fill to A1_4 -> A1_7

        JapaneseDate dateJp = toJapaneseDate(data.getPerson().getBirthDate());
        String day[] = String.valueOf(dateJp.day()).split("");
        String month[] = String.valueOf(dateJp.month()).split("");
        String year[] = String.valueOf(dateJp.year() + 1).split("");

        if (day.length == 2) {
            ws.getCells().get("AF12").putValue(day[0]);
            ws.getCells().get("AG12").putValue(day[1]);
        } else {
            ws.getCells().get("AF12").putValue("0");
            ws.getCells().get("AG12").putValue(day[0]);
        }

        if (month.length == 2) {
            ws.getCells().get("AD12").putValue(month[0]);
            ws.getCells().get("AE12").putValue(month[1]);
        } else {
            ws.getCells().get("AD12").putValue("0");
            ws.getCells().get("AE12").putValue(month[0]);
        }

        if (year.length == 2) {
            ws.getCells().get("AB12").putValue(year[0]);
            ws.getCells().get("AC12").putValue(year[1]);
        } else {
            ws.getCells().get("AB12").putValue("0");
            ws.getCells().get("AC12").putValue(year[0]);
        }

        this.selectEra(ws, dateJp.era());

        JapaneseDate submitDate = toJapaneseDate(data.getSubmitDate());
        ws.getCells().get("W20").putValue(submitDate.year() + 1);
        ws.getCells().get("Y20").putValue(submitDate.month());
        ws.getCells().get("AA20").putValue(submitDate.day());
        if(socialInsurNotiCreateSet.getSubmittedName() == SubNameClass.PERSONAL_NAME) {
            ws.getCells().get("J17").putValue(EmpAddChangeInfoPDFAposeFileGenerator.cutSpace(name, 1, 11));
            ws.getCells().get("M17").putValue(EmpAddChangeInfoPDFAposeFileGenerator.cutSpace(name, 2, 11));
            ws.getCells().get("J19").putValue(EmpAddChangeInfoPDFAposeFileGenerator.cutSpace(nameKana, 1, 16));
            ws.getCells().get("M19").putValue(EmpAddChangeInfoPDFAposeFileGenerator.cutSpace(nameKana, 2, 16));
        }
        if(socialInsurNotiCreateSet.getSubmittedName() == SubNameClass.REPORTED_NAME) {
            ws.getCells().get("J17").putValue(EmpAddChangeInfoPDFAposeFileGenerator.cutSpace(todoName, 1, 11));
            ws.getCells().get("M17").putValue(EmpAddChangeInfoPDFAposeFileGenerator.cutSpace(todoName, 2, 11));
            ws.getCells().get("J19").putValue(EmpAddChangeInfoPDFAposeFileGenerator.cutSpace(todoNameKana, 1, 16));
            ws.getCells().get("M19").putValue(EmpAddChangeInfoPDFAposeFileGenerator.cutSpace(todoNameKana, 2, 16));
        }

        ws.getCells().get("U17").putValue(EmpAddChangeInfoPDFAposeFileGenerator.cutSpace(oldName, 1, 8));
        ws.getCells().get("Z17").putValue(EmpAddChangeInfoPDFAposeFileGenerator.cutSpace(oldName, 2, 8));

        //厚生年金種別情報

        if (data.getWelfarePenTypeInfor() != null) {
            if (data.getPerson().getGender() == 1 && data.getWelfarePenTypeInfor().getUndergroundDivision().value == PitInsiderDivision.NOTTARGETED.value && data.getFundMembership() == null) {
                //1
                ws.getShapes().remove(ws.getShapes().get("C1_10"));
                ws.getShapes().remove(ws.getShapes().get("C1_11"));
                ws.getShapes().remove(ws.getShapes().get("C1_12"));
                ws.getShapes().remove(ws.getShapes().get("C1_13"));
                ws.getShapes().remove(ws.getShapes().get("C1_14"));
            } else if (data.getPerson().getGender() == 2 && data.getWelfarePenTypeInfor().getUndergroundDivision().value == PitInsiderDivision.NOTTARGETED.value && data.getFundMembership() == null) {
                //2

                ws.getShapes().remove(ws.getShapes().get("C1_9"));
                ws.getShapes().remove(ws.getShapes().get("C1_11"));
                ws.getShapes().remove(ws.getShapes().get("C1_12"));
                ws.getShapes().remove(ws.getShapes().get("C1_13"));
                ws.getShapes().remove(ws.getShapes().get("C1_14"));
            } else if ((data.getPerson().getGender() == 1 || data.getPerson().getGender() == 2) && data.getWelfarePenTypeInfor().getUndergroundDivision().value == PitInsiderDivision.TARGET.value && data.getFundMembership() == null) {
                //3
                ws.getShapes().remove(ws.getShapes().get("C1_9"));
                ws.getShapes().remove(ws.getShapes().get("C1_10"));
                ws.getShapes().remove(ws.getShapes().get("C1_12"));
                ws.getShapes().remove(ws.getShapes().get("C1_13"));
                ws.getShapes().remove(ws.getShapes().get("C1_14"));
            } else if (data.getPerson().getGender() == 1 && data.getWelfarePenTypeInfor().getUndergroundDivision().value == PitInsiderDivision.NOTTARGETED.value && data.getFundMembership() != null) {
                //5

                ws.getShapes().remove(ws.getShapes().get("C1_9"));
                ws.getShapes().remove(ws.getShapes().get("C1_10"));
                ws.getShapes().remove(ws.getShapes().get("C1_11"));
                ws.getShapes().remove(ws.getShapes().get("C1_13"));
                ws.getShapes().remove(ws.getShapes().get("C1_14"));
            } else if (data.getPerson().getGender() == 2 && data.getWelfarePenTypeInfor().getUndergroundDivision().value == PitInsiderDivision.NOTTARGETED.value && data.getFundMembership() != null) {
                //6
                ws.getShapes().remove(ws.getShapes().get("C1_9"));
                ws.getShapes().remove(ws.getShapes().get("C1_10"));
                ws.getShapes().remove(ws.getShapes().get("C1_11"));
                ws.getShapes().remove(ws.getShapes().get("C1_12"));
                ws.getShapes().remove(ws.getShapes().get("C1_14"));
            } else if ((data.getPerson().getGender() == 1 || data.getPerson().getGender() == 2) && data.getWelfarePenTypeInfor().getUndergroundDivision().value == PitInsiderDivision.TARGET.value && data.getFundMembership() != null) {
                //7

                ws.getShapes().remove(ws.getShapes().get("C1_9"));
                ws.getShapes().remove(ws.getShapes().get("C1_10"));
                ws.getShapes().remove(ws.getShapes().get("C1_11"));
                ws.getShapes().remove(ws.getShapes().get("C1_12"));
                ws.getShapes().remove(ws.getShapes().get("C1_13"));
            } else {
                ws.getShapes().remove(ws.getShapes().get("C1_9"));
                ws.getShapes().remove(ws.getShapes().get("C1_10"));
                ws.getShapes().remove(ws.getShapes().get("C1_11"));
                ws.getShapes().remove(ws.getShapes().get("C1_12"));
                ws.getShapes().remove(ws.getShapes().get("C1_13"));
                ws.getShapes().remove(ws.getShapes().get("C1_14"));
            }
        } else {
            ws.getShapes().remove(ws.getShapes().get("C1_9"));
            ws.getShapes().remove(ws.getShapes().get("C1_10"));
            ws.getShapes().remove(ws.getShapes().get("C1_11"));
            ws.getShapes().remove(ws.getShapes().get("C1_12"));
            ws.getShapes().remove(ws.getShapes().get("C1_13"));
            ws.getShapes().remove(ws.getShapes().get("C1_14"));
        }


        //会社名・住所を出力
        //fill to A2_1
        //Set style for item K23
        Style style = ws.getCells().get("K23").getStyle();
        style.setTextWrapped(true);
        ws.getCells().get("K23").setStyle(style);

        if (socialInsurNotiCreateSet.getOfficeInformation().value == BusinessDivision.OUTPUT_COMPANY_NAME.value) {
            ws.getCells().get("J22").putValue(formatPostalCode(company.getPostCd()));
            ws.getCells().get("K23").putValue(Objects.toString(formatTooLongText(company.getAdd_1() ,60) + "\r\n" + (company.getAdd_2() == null ? "" : company.getAdd_2())));
            ws.getCells().get("K24").putValue(company.getCompanyName());
            ws.getCells().get("K25").putValue(company.getRepname());
            ws.getCells().get("J27").putValue(this.formatPhoneNumber(company.getPhoneNum()));

        } else if (socialInsurNotiCreateSet.getOfficeInformation().value == BusinessDivision.OUTPUT_SIC_INSURES.value && data.getSocialInsuranceOffice() != null) {
                if (data.getSocialInsuranceOffice().getBasicInformation().getAddress().isPresent() && data.getSocialInsuranceOffice().getBasicInformation().getAddress().get().getPhoneNumber().isPresent() && !data.getSocialInsuranceOffice().getBasicInformation().getAddress().get().getPhoneNumber().get().v().isEmpty()) {
                    phoneNumber = this.formatPhoneNumber(data.getSocialInsuranceOffice().getBasicInformation().getAddress().get().getPhoneNumber().get().v());
                }
                String address1 = data.getSocialInsuranceOffice().getBasicInformation().getAddress().isPresent() && data.getSocialInsuranceOffice().getBasicInformation().getAddress().get().getAddress1().isPresent() ? data.getSocialInsuranceOffice().getBasicInformation().getAddress().get().getAddress1().get().v() : "";
                String address2 = data.getSocialInsuranceOffice().getBasicInformation().getAddress().isPresent() && data.getSocialInsuranceOffice().getBasicInformation().getAddress().get().getAddress2().isPresent() ? data.getSocialInsuranceOffice().getBasicInformation().getAddress().get().getAddress2().get().v() : "";
                address = formatTooLongText(address1, 60) + "\n" + address2;

                ws.getCells().get("J22").putValue(data.getSocialInsuranceOffice().getBasicInformation().getAddress().isPresent() && data.getSocialInsuranceOffice().getBasicInformation().getAddress().get().getPostalCode().isPresent() ? this.formatPostalCode(data.getSocialInsuranceOffice().getBasicInformation().getAddress().get().getPostalCode().get().v()): null);
                ws.getCells().get("K23").putValue(address);
                ws.getCells().get("K24").putValue(data.getSocialInsuranceOffice().getName().v());
                ws.getCells().get("K25").putValue(data.getSocialInsuranceOffice().getBasicInformation().getRepresentativeName().isPresent() ? data.getSocialInsuranceOffice().getBasicInformation().getRepresentativeName().get().v() : null);
                ws.getCells().get("J27").putValue(phoneNumber);
        }
    }

    private String formatTooLongText(String text, int maxByteAllowed) throws UnsupportedEncodingException {
        if (text == null || text.isEmpty()) {
            return "";
        }
        int textLength = text.length();
        int byteCount = 0;
        int index = 0;
        while (index < textLength - 1) {
            byteCount += String.valueOf(text.charAt(index)).getBytes("Shift_JIS").length;
            // String.getBytes("Shift_JIS") return wrong value with full size dash
            if (text.charAt(index) == '－') {
                byteCount++;
            }
            if (byteCount > maxByteAllowed) {
                index--;
                break;
            }
            index++;
        }
        return text.substring(0, index + 1);
    }

    private JapaneseDate toJapaneseDate(GeneralDate date) {
        Optional<JapaneseEraName> era = this.adapter.getAllEras().eraOf(date);
        return new JapaneseDate(date, era.get());
    }

    private void selectEra(Worksheet ws, String era) {
        if (era.equals(Meiji)) {
            ws.getShapes().remove(ws.getShapes().get("A1_5"));
            ws.getShapes().remove(ws.getShapes().get("A1_6"));
            ws.getShapes().remove(ws.getShapes().get("A1_7"));
            ws.getShapes().remove(ws.getShapes().get("A1_8"));
        } else if (era.equals(Taisho)) {
            ws.getShapes().remove(ws.getShapes().get("A1_4"));
            ws.getShapes().remove(ws.getShapes().get("A1_6"));
            ws.getShapes().remove(ws.getShapes().get("A1_7"));
            ws.getShapes().remove(ws.getShapes().get("A1_8"));
        } else if (era.equals(SHOWA)) {
            ws.getShapes().remove(ws.getShapes().get("A1_4"));
            ws.getShapes().remove(ws.getShapes().get("A1_5"));
            ws.getShapes().remove(ws.getShapes().get("A1_7"));
            ws.getShapes().remove(ws.getShapes().get("A1_8"));
        } else if (era.equals(HEISEI)) {
            ws.getShapes().remove(ws.getShapes().get("A1_4"));
            ws.getShapes().remove(ws.getShapes().get("A1_5"));
            ws.getShapes().remove(ws.getShapes().get("A1_6"));
            ws.getShapes().remove(ws.getShapes().get("A1_8"));
        } else {
            ws.getShapes().remove(ws.getShapes().get("A1_4"));
            ws.getShapes().remove(ws.getShapes().get("A1_5"));
            ws.getShapes().remove(ws.getShapes().get("A1_6"));
            ws.getShapes().remove(ws.getShapes().get("A1_7"));
        }

    }

    private String formatPhoneNumber(String number){
        String numberPhone = "";
        String[] numberSplit = number.split("-");
        String[] temp = new String[3];

        if(numberSplit.length == 2){

            if(numberSplit[1].length() <= 3){
                temp[0] = numberSplit[1].substring(0,numberSplit[1].length());
                numberPhone = numberSplit[0] + "（   　" + temp[0] + "   　局）";
            }else{
                temp[0] = numberSplit[1].substring(0,3);
                temp[1] = numberSplit[1].substring(3,numberSplit[1].length());
                numberPhone = numberSplit[0] + "（   　" + temp[0] + "   　局）" + temp[1];
            }

        }else if(numberSplit.length >= 3){
            numberPhone = numberSplit[0] + "（   　" + numberSplit[1] + "   　局）" + numberSplit[2];
        }else if(numberSplit.length == 1){
            if(number.length() <= 3){
                temp[0] = number.substring(0,number.length());
                numberPhone = temp[0];
            }else if(number.length() > 3 && number.length() <=6){
                temp[0] = number.substring(0,3);
                temp[1] = number.substring(3,number.length());
                numberPhone = temp[0] + "（   　" + temp[1] + "   　局）";
            }else if(number.length() > 6){
                temp[0] = number.substring(0,3);
                temp[1] = number.substring(3,6);
                temp[2] = number.substring(6,number.length());
                numberPhone = temp[0] + "（   　" + temp[1] + "   　局）" + temp[2];
            }

        }

        return numberPhone;
    }

    private String formatPostalCode(String number){
        String postalCode = "";
        String[] numberSplit = number.split("-");
        String[] temp = new String[2];
        if("".equals(number)) {
            return number;
        }
        if(numberSplit.length > 1){
            postalCode = "〒" + numberSplit[0] + "－" + numberSplit[1];
        }else{
            temp[0] = number.length() > 2 ? number.substring(0,3) : number;
            temp[1] = number.length() > 3 ? number.substring(3,number.length()) : "";
            postalCode = "〒" + temp[0] + "－" + temp[1];
        }
        return postalCode;
    }


}
