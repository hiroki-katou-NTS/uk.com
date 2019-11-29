package nts.uk.file.pr.infra.core.empinsqualifiinfo.empinsqualifinfo;

import com.aspose.pdf.*;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.gul.text.KatakanaConverter;
import nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo.EmpInsGetQualifReport;
import nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo.EmpInsGetQualifRptFileGenerator;
import nts.uk.shr.com.time.japanese.JapaneseDate;
import nts.uk.shr.infra.file.report.aspose.pdf.AsposePdfReportContext;
import nts.uk.shr.infra.file.report.aspose.pdf.AsposePdfReportGenerator;

import javax.ejb.Stateless;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class EmpInsGetQualifAsposeFileGenerator extends AsposePdfReportGenerator implements EmpInsGetQualifRptFileGenerator {
    private static final String TEMPLATE_FILE = "report/雇用保険被保険者資格取得届.pdf";
    private static final int LOCATION_MAX_BYTE = 46;
    private static final int BUSINESS_NAME_MAX_BYTE = 46;
    private static final int OFFICE_NAME_MAX_BYTE = 40;
    private static final int NATIONALITY_MAX_BYTE = 20;
    private static final int RESIDENT_STATUS_MAX_BYTE = 16;
    private static final int INSURED_NAME_MAX_BYTE = 22;
    private static final int INSURED_FULLNAME_MAX_BYTE = 22;

    @Override
    public void generate(FileGeneratorContext fileContext, List<EmpInsGetQualifReport> reportData) {
        try (AsposePdfReportContext report = this.createContext(TEMPLATE_FILE)) {
            Document doc = report.getDocument();
            Page[] curPage = {doc.getPages().get_Item(1)};
            for (int i = 0; i < reportData.size(); i++) {
                if (i != 0) {
                    doc.getPages().add(curPage);
                }
            }
            stylePage(doc);
            int indexPage = 1;

            for (EmpInsGetQualifReport data : reportData) {
                Page pdfPage = doc.getPages().get_Item(indexPage);
                TextBuilder textBuilder = new TextBuilder(pdfPage);

                // A1_1
                String insuredNumber = data.getInsuredNumber() == null ? "" : data.getInsuredNumber();
                detachText(46, 718, insuredNumber, 4, textBuilder);
                detachText(131, 718, insuredNumber.length() > 4 ? insuredNumber.substring(4) : "", 6, textBuilder);
                detachText(251, 718, insuredNumber.length() > 10 ? insuredNumber.substring(10) : "", 1, textBuilder);
                // A1_2
                String acquisitionAtr = data.getAcquisitionAtr() == null ? "" : data.getAcquisitionAtr().toString();
                textBuilder.appendText(setValue(318, 718, acquisitionAtr, 16));
                // A1_3
                String insuredName = data.getInsuredName() == null ? "" : data.getInsuredName();
                textBuilder.appendText(setValue(45, 686, formatTooLongText(insuredName, INSURED_NAME_MAX_BYTE), 9));
                // A1_4
                String insuredFullName = data.getInsuredFullName() == null ? "" : data.getInsuredFullName();
                detachText(182, 682, insuredFullName, 20, textBuilder);
                // A1_5
                String nameAfterChange = data.getNameAfterChange() == null ? "" : data.getNameAfterChange();
                textBuilder.appendText(setValue(45, 650, formatTooLongText(nameAfterChange, INSURED_FULLNAME_MAX_BYTE), 9));
                // A1_6
                String fullNameAfterChange = data.getFullNameAfterChange() == null ? "" : data.getFullNameAfterChange();
                detachText(182, 647, fullNameAfterChange, 20, textBuilder);
                // A1_7
                String gender = data.getGender() == null ? "" : data.getGender().toString();
                textBuilder.appendText(setValue(46, 609, gender, 16));
                // A1_8
                String eraDob = data.getEraDateOfBirth() == null ? "" : data.getEraDateOfBirth();
                textBuilder.appendText(setValue(108, 609, eraDob, 16));
                // A1_9
                String dobJp = data.getDateOfBirthJp() == null ? "" : data.getDateOfBirthJp();
                detachText(142, 609, dobJp, 6, textBuilder);
                // A1_10
                detachText(301, 609, data.getOfficeNumber1() == null ? "" : data.getOfficeNumber1(), 4, textBuilder);
                detachText(386, 609, data.getOfficeNumber2() == null ? "" : data.getOfficeNumber2(), 6, textBuilder);
                detachText(505, 609, data.getOfficeNumber3() == null ? "" : data.getOfficeNumber3(), 1, textBuilder);
                // A1_11
                String insuredCause = data.getCauseOfInsured() == null ? "" : data.getCauseOfInsured().toString();
                textBuilder.appendText(setValue(88, 566, insuredCause, 16));
                // A1_12
                String wagePaymentMode = data.getWagePaymentMode() == null ? "" : data.getWagePaymentMode().toString();
                textBuilder.appendText(setValue(155, 566, wagePaymentMode, 16));
                // A1_13
                String paymentWage = data.getPaymentWage() == null ? "" : data.getPaymentWage().toString();
                detachText(189 + (4 - paymentWage.length()) * 17, 566, paymentWage, 4, textBuilder);
                // A1_14
                String qualificationDateJp = data.getQualificationDateJp() == null ? "" : data.getQualificationDateJp();
                detachText(352, 566, qualificationDateJp, 1, textBuilder);
                detachText(386, 566, qualificationDateJp.length() > 1 ? qualificationDateJp.substring(1) : "", 6, textBuilder);
                // A1_15
                String employmentStatus = data.getEmploymentStatus() == null ? "" : data.getEmploymentStatus().toString();
                textBuilder.appendText(setValue(134, 516, employmentStatus, 16));
                // A1_16
                String occupation = data.getOccupation() == null ? "" : (data.getOccupation() < 10 ? "0" + (data.getOccupation() + 1) : String.valueOf(data.getOccupation() + 1));
                detachText(242, 516, occupation, 2, textBuilder);
                // A1_17
                String jobPath = data.getJobPath() == null ? "" : data.getJobPath().toString();
                textBuilder.appendText(setValue(317, 516, jobPath, 16));
                // A1_18
                String workingTime = formatWorkingTime(data.getScheduleWorkingTimePerWeek());
                detachText(402, 516, workingTime, 4, textBuilder);
                // A1_19
                String estContractPeriod = data.getEstContractPeriod() == null ? "" : data.getEstContractPeriod();
                textBuilder.appendText(setValue(119, 464, estContractPeriod, 16));
                // A1_20
                String contractStartDateJp = data.getContractStartDateJp() == null ? "" : data.getContractStartDateJp();
                detachText(217, 464, contractStartDateJp, 1, textBuilder);
                detachText(251, 464, contractStartDateJp.length() > 1 ? contractStartDateJp.substring(1) : "", 6, textBuilder);
                // A1_21
                String contractEndDateJp = data.getContractEndDateJp() == null ? "" : data.getContractEndDateJp();
                detachText(372, 464, contractEndDateJp, 1, textBuilder);
                detachText(406, 464, contractEndDateJp.length() > 1 ? contractEndDateJp.substring(1) : "", 6, textBuilder);
                // A1_22
                String contractRenewalProvision = data.getContractRenewalProvision() == null ? "" : data.getContractRenewalProvision().toString();
                textBuilder.appendText(setValue(250, 432, contractRenewalProvision, 16));
                // A1_23
                String officeName = data.getOfficeName() == null ? "" : data.getOfficeName();
                textBuilder.appendText(setValue(90, 406, formatTooLongText(officeName, OFFICE_NAME_MAX_BYTE), 9));
                // A1_24 pending
                // detachText(174, 757, data.getPersonalNumber(), 11, textBuilder);
                // A2_1
                String insuredRomanName = data.getInsuredRomanName() == null ? "" : data.getInsuredRomanName();
                detachText(48, 356, insuredRomanName, 28, textBuilder);
                // A2_2
                String insuredRomanName2 = data.getInsuredRomanName2() == null ? "" : data.getInsuredRomanName2();
                detachText(48, 320, insuredRomanName2, 12, textBuilder);
                // A2_3
                String nationalityRegion = data.getNationalityRegion() == null ? "" : data.getNationalityRegion();
                textBuilder.appendText(setValue(271, 324, formatTooLongText(nationalityRegion, NATIONALITY_MAX_BYTE), 9));
                // A2_4
                String residenceStatus = data.getResidenceStatus() == null ? "" : data.getResidenceStatus();
                textBuilder.appendText(setValue(405, 324, formatTooLongText(residenceStatus, RESIDENT_STATUS_MAX_BYTE), 9));
                // A2_5
                String stayPeriod = data.getStayPeriod() == null ? "" : data.getStayPeriod();
                detachText(85, 291, stayPeriod, 8, textBuilder);
                // A2_6
                String nonQualificationPermission = data.getNonQualifPermission() == null ? "" : data.getNonQualifPermission().toString();
                textBuilder.appendText(setValue(305, 291, nonQualificationPermission, 16));
                // A2_7
                String contractWorkAtr = data.getContractWorkAtr() == null ? "" : data.getContractWorkAtr().toString();
                textBuilder.appendText(setValue(415, 291, contractWorkAtr, 16));
                // A3_1
                String postalCode = data.getOfficePostalCode() == null ? "" : data.getOfficePostalCode();
                textBuilder.appendText(setValue(110, 179, formatPostalCode(postalCode), 9));
                // A3_2
                String officeLocation = data.getOfficeLocation() == null ? "" : data.getOfficeLocation();
                textBuilder.appendText(setValue(176, 179, formatTooLongText(officeLocation, LOCATION_MAX_BYTE), 9));
                // A3_4
                String businessOwnerName = data.getBusinessOwnerName() == null ? "" : data.getBusinessOwnerName();
                textBuilder.appendText(setValue(110, 151, formatTooLongText(businessOwnerName, BUSINESS_NAME_MAX_BYTE), 9));
                // A3_4
                String officePhoneNumber = data.getOfficePhoneNumber() == null ? "" : data.getOfficePhoneNumber();
                textBuilder.appendText(setValue(110, 122, formatPhoneNumber(officePhoneNumber), 9));

                // A3_5
                detachDate(467, 180, data.getSubmissionDateJp(), textBuilder);
                // index page
                indexPage++;
            }
            report.saveAsPdf(this.createNewFile(fileContext, "雇用保険被保険者氏名変更届.pdf"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private TextFragment setValue(int x, int y, String value, int textSize) {
        TextFragment textFragment = new TextFragment(value);
        textFragment.setPosition(new Position(x, y));
        styleText(textFragment.getTextState(), textSize);
        return textFragment;
    }

    private void styleText(TextFragmentState textFragmentState, int textSize) {
        textFragmentState.setFont(FontRepository.findFont("MS-Gothic"));
        textFragmentState.setFontSize(textSize);
        textFragmentState.setForegroundColor(Color.getBlack());
    }

    private void detachText(int xRoot, int yRoot, String value, int numCells, TextBuilder textBuilder) {
        value = KatakanaConverter.hiraganaToKatakana(value);
        value = KatakanaConverter.fullKatakanaToHalf(value);
        if (value.length() > numCells) {
            value = value.substring(0, numCells);
        }
        List<Character> lstValue = value.chars().mapToObj(i -> (char) i).collect(Collectors.toList());
        for (int i = 0; i < lstValue.size(); i++) {
            int pixel = xRoot + (17 * i);
            textBuilder.appendText(setValue(pixel, yRoot, lstValue.get(i).toString(), 16));
        }
    }

    private void stylePage(Document doc) {
        PageInfo pageInfo = doc.getPageInfo();
        MarginInfo marginInfo = pageInfo.getMargin();
        marginInfo.setLeft(0);
        marginInfo.setRight(0);
        marginInfo.setTop(0);
        marginInfo.setBottom(0);
    }

    private void detachDate(int xRoot, int yRoot, JapaneseDate value, TextBuilder textBuilder) {
        textBuilder.appendText(setValue(xRoot, yRoot, value.year() + 1 + "", 9));
        textBuilder.appendText(setValue(xRoot + 36, yRoot, value.month() + "", 9));
        textBuilder.appendText(setValue(xRoot + 72, yRoot, value.day() + "", 9));
    }

    private String formatPhoneNumber(String number) {
        String numberPhone = "";
        String[] numberSplit = number.split("-");
        String[] temp = new String[3];

        if (numberSplit.length == 2) {
            if (numberSplit[1].length() <= 3) {
                temp[0] = numberSplit[1];
                numberPhone = numberSplit[0] + "（ " + temp[0] + " ）";
            } else {
                temp[0] = numberSplit[1].substring(0, 3);
                temp[1] = numberSplit[1].substring(3);
                numberPhone = numberSplit[0] + "（ " + temp[0] + " ）" + temp[1];
            }
        } else if (numberSplit.length >= 3) {
            numberPhone = numberSplit[0] + "（ " + numberSplit[1] + " ）" + numberSplit[2];
        } else if (numberSplit.length == 1) {
            if (number.length() <= 3) {
                temp[0] = number;
                numberPhone = temp[0];
            } else if (number.length() <= 6) {
                temp[0] = number.substring(0, 3);
                temp[1] = number.substring(3);
                numberPhone = temp[0] + "（ " + temp[1] + " ）";
            } else {
                temp[0] = number.substring(0, 3);
                temp[1] = number.substring(3, 6);
                temp[2] = number.substring(6);
                numberPhone = temp[0] + "（ " + temp[1] + " ）" + temp[2];
            }
        }
        return numberPhone;
    }

    private String formatPostalCode(String number) {
        String postalCode;
        String[] numberSplit = number.split("-");
        String[] temp = new String[2];
        if ("".equals(number)) {
            return number;
        }
        if (numberSplit.length > 1) {
            postalCode = "〒" + numberSplit[0] + "－" + numberSplit[1];
        } else {
            temp[0] = number.length() > 2 ? number.substring(0, 3) : number;
            temp[1] = number.length() > 3 ? number.substring(3) : "";
            postalCode = "〒" + temp[0] + "－" + temp[1];
        }
        return postalCode;
    }

    private String formatWorkingTime(Integer workingTime) {
        if (workingTime == null) {
            return "";
        }
        if (workingTime > 5999) {
            return "9959";
        }
        String workingHours = (workingTime / 60) < 10 ? "0" + (workingTime / 60) : String.valueOf(workingTime / 60);
        String workingMinutes = (workingTime % 60) < 10 ? "0" + (workingTime % 60) : String.valueOf(workingTime % 60);
        return workingHours + workingMinutes;
    }

    private String formatTooLongText(String text, int maxByteAllowed) throws UnsupportedEncodingException {
        if (text.getBytes("Shift_JIS").length < maxByteAllowed) return text;
        int textLength = text.length();
        int subLength = 0;
        for (int i = 0; i < textLength; i++) {
            if (text.substring(0, subLength).getBytes("Shift_JIS").length > maxByteAllowed) break;
            subLength++;
        }
        return text.substring(0, subLength);
    }
}
