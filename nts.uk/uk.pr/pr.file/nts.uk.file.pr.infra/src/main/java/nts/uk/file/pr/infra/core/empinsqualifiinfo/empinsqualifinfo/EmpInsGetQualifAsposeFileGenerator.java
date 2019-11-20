package nts.uk.file.pr.infra.core.empinsqualifiinfo.empinsqualifinfo;

import com.aspose.pdf.*;
import com.aspose.pdf.drawing.Circle;
import com.aspose.pdf.drawing.Graph;
import com.aspose.pdf.drawing.Line;
import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.gul.text.KatakanaConverter;
import nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo.EmpInsGetQualifReport;
import nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo.EmpInsGetQualifRptFileGenerator;
import nts.uk.shr.com.time.japanese.JapaneseDate;
import nts.uk.shr.infra.file.report.aspose.pdf.AsposePdfReportContext;
import nts.uk.shr.infra.file.report.aspose.pdf.AsposePdfReportGenerator;

import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class EmpInsGetQualifAsposeFileGenerator extends AsposePdfReportGenerator implements EmpInsGetQualifRptFileGenerator {
    private static final String TEMPLATE_FILE = "report/雇用保険被保険者資格取得届.pdf";

    @Override
    public void generate(FileGeneratorContext fileContext, List<EmpInsGetQualifReport> reportData) {
        try (AsposePdfReportContext report = this.createContext(TEMPLATE_FILE)) {
            Document doc = report.getDocument();
            Page[] curPage = {doc.getPages().get_Item(1), doc.getPages().get_Item(2)};
            for (int i = 0; i < reportData.size(); i++) {
                if (i != 0) {
                    doc.getPages().add(curPage);
                }
            }
            stylePage(doc);
            int indexPage = 1;

            for (EmpInsGetQualifReport data : reportData) {
                Paragraphs paragraphs = doc.getPages().get_Item(indexPage).getParagraphs();
                // A1_1
                detachText(48, 728, data.getPersonalNumber().toString().substring(0, 4), 4, paragraphs);
                detachText(132, 728, data.getPersonalNumber().toString().substring(5, 10), 6, paragraphs);
                detachText(252, 728, data.getPersonalNumber().toString().substring(10), 1, paragraphs);
                // A1_2
                paragraphs.add(setValue(318, 734, data.getAcquisitionAtr().toString(), 16));
                // A1_3
                paragraphs.add(setValue(48, 698, data.getInsuredName(), 16));
                // A1_4
                detachText(180, 698, data.getInsuredFullName(), 20, paragraphs);
                // A1_5
                paragraphs.add(setValue(48, 662, data.getNameAfterChange(), 16));
                // A1_6
                detachText(180, 662, data.getFullNameAfterChange(), 20, paragraphs);
                // A1_7
                paragraphs.add(setValue(48, 626, data.getGender().toString(), 16));
                // A1_8
                paragraphs.add(setValue(114, 626, data.getEraDateOfBirth(), 16));
                // A1_9
                detachText(144, 626, data.getDateOfBirthJp(), 6, paragraphs);
                // A1_10
                detachText(306, 626, data.getOfficeNumber1(), 4, paragraphs);
                detachText(390, 626, data.getOfficeNumber2(), 6, paragraphs);
                detachText(510, 626, data.getOfficeNumber3(), 1, paragraphs);
                // A1_11
                paragraphs.add(setValue(90, 578, data.getCauseOfInsured().toString(), 16));
                // A1_12
                paragraphs.add(setValue(156, 578, data.getWagePaymentMode().toString(), 16));
                // A1_13
                detachText(114, 578, data.getPaymentWage().toString(), 4, paragraphs);
                // A1_14
                detachText(114, 578, data.getQualificationDateJp().substring(0, 1), 1, paragraphs);
                detachText(114, 578, data.getPaymentWage().toString().substring(1), 6, paragraphs);
                // A1_15
                paragraphs.add(setValue(138, 530, data.getEmploymentStatus().toString(), 16));
                // A1_16
                detachText(246, 530, data.getOccupation() < 10 ? "0" + (data.getOccupation() + 1) : String.valueOf(data.getOccupation() + 1), 2, paragraphs);
                // A1_17
                paragraphs.add(setValue(318, 530, data.getJobPath().toString(), 16));
                String workingHours = (data.getScheduleWorkingTimePerWeek() / 60) < 10 ? "0" + (data.getScheduleWorkingTimePerWeek() / 60) : String.valueOf(data.getScheduleWorkingTimePerWeek() / 60);
                String workingMinutes = String.valueOf(data.getScheduleWorkingTimePerWeek() % 60);
                // A1_18
                detachText(405, 530, workingHours + workingMinutes, 4, paragraphs);
                // A1_19
                paragraphs.add(setValue(123, 476, data.getEstContractPeriod(), 16));
                // A1_20
                detachText(114, 476, data.getContractStartDateJp().substring(0, 1), 1, paragraphs);
                detachText(114, 476, data.getContractStartDateJp().substring(1, 7), 6, paragraphs);
                // A1_21
                detachText(114, 476, data.getContractEndDateJp().substring(0, 1), 1, paragraphs);
                detachText(114, 476, data.getContractEndDateJp().substring(1, 7), 6, paragraphs);
                // A1_22
                paragraphs.add(setValue(252, 446, data.getContractRenewalProvision().toString(), 16));
                // A1_23
                paragraphs.add(setValue(90, 416, data.getOfficeName(), 16));
                // A1_24 pending
                // detachText(174, 757, "", 12, paragraphs);

                // A2_1
                detachText(114, 757, data.getInsuredEnglishName(), 29, paragraphs);
                // A2_2
                detachText(114, 757, data.getInsuredEnglishName2(), 12, paragraphs);
                // A2_3
                paragraphs.add(setValue(114, 757, data.getNationalityRegion(), 16));
                // A2_4
                paragraphs.add(setValue(114, 757, data.getResidenceStatus(), 16));
                // A2_5
                detachText(114, 757, data.getStayPeriod(), 8, paragraphs);
                // A2_6
                paragraphs.add(setValue(114, 757, data.getNonQualifPermission().toString(), 16));
                // A2_7
                paragraphs.add(setValue(114, 757, data.getContractWorkAtr().toString(), 16));

                // A3_1
                paragraphs.add(setValue(114, 757, formatPostalCode(data.getOfficePostalCode()), 9));
                // A3_2
                paragraphs.add(setValue(114, 757, data.getOfficeLocation(), 9));
                // A3_4
                paragraphs.add(setValue(114, 757, data.getBusinessOwnerName(), 9));
                // A3_4
                paragraphs.add(setValue(114, 757, formatPhoneNumber(data.getOfficePhoneNumber()), 9));
                String[] fillingDate = data.getSubmissionDateJp().split("/");
                // A3_5
                paragraphs.add(setValue(114, 757, fillingDate[0], 9));
                paragraphs.add(setValue(114, 757, fillingDate[0], 9));
                paragraphs.add(setValue(114, 757, fillingDate[0], 9));

                //index page
                indexPage = indexPage + 2;
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

    private void detachText(int xRoot, int yRoot, String value, int numCells, Paragraphs paragraphs) {
        value = KatakanaConverter.fullKatakanaToHalf(value);
        if (value.length() > numCells) {
            value = value.substring(0, numCells);
        }
        List<Character> lstValue = value.chars().mapToObj(i -> (char) i).collect(Collectors.toList());
        for (int i = 0; i < lstValue.size(); i++) {
            int pixel = xRoot + (17 * i);
            paragraphs.add(setValue(pixel, yRoot, lstValue.get(i).toString(), 16));
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

    private void detachDate(int xRoot, int yRoot, JapaneseDate value, Paragraphs paragraphs) {
        paragraphs.add(setValue(xRoot, yRoot, value.year() + 1 + "", 9));
        paragraphs.add(setValue(xRoot + 30, yRoot, value.month() + "", 9));
        paragraphs.add(setValue(xRoot + 60, yRoot, value.day() + "", 9));
    }

    private String formatPhoneNumber(String number) {
        String numberPhone = "";
        String[] numberSplit = number.split("-");
        String[] temp = new String[3];

        if (numberSplit.length == 2) {

            if (numberSplit[1].length() <= 3) {
                temp[0] = numberSplit[1];
                numberPhone = numberSplit[0] + "（   　" + temp[0] + "   　）";
            } else {
                temp[0] = numberSplit[1].substring(0, 3);
                temp[1] = numberSplit[1].substring(3);
                numberPhone = numberSplit[0] + "（   　" + temp[0] + "   　）" + temp[1];
            }
        } else if (numberSplit.length >= 3) {
            numberPhone = numberSplit[0] + "（   　" + numberSplit[1] + "   　）" + numberSplit[2];
        } else if (numberSplit.length == 1) {
            if (number.length() <= 3) {
                temp[0] = number;
                numberPhone = temp[0];
            } else if (number.length() <= 6) {
                temp[0] = number.substring(0, 3);
                temp[1] = number.substring(3);
                numberPhone = temp[0] + "（   　" + temp[1] + "   　）";
            } else {
                temp[0] = number.substring(0, 3);
                temp[1] = number.substring(3, 6);
                temp[2] = number.substring(6);
                numberPhone = temp[0] + "（   　" + temp[1] + "   　）" + temp[2];
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
}
