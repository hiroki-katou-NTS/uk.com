package nts.uk.file.pr.infra.core.empinsqualifiinfo.empinsqualifinfo;

import com.aspose.pdf.*;
import com.aspose.pdf.Color;
import com.aspose.pdf.drawing.Circle;
import com.aspose.pdf.drawing.Ellipse;
import com.aspose.pdf.drawing.Graph;
import com.aspose.pdf.drawing.Line;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.gul.text.KatakanaConverter;
import nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo.EmpInsLossInfoExportData;
import nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo.EmpInsLossInfoFileGenerator;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.RequestForInsurance;
import nts.uk.shr.com.time.japanese.JapaneseDate;
import nts.uk.shr.com.time.japanese.JapaneseEraName;
import nts.uk.shr.com.time.japanese.JapaneseEras;
import nts.uk.shr.com.time.japanese.JapaneseErasAdapter;
import nts.uk.shr.infra.file.report.aspose.pdf.AsposePdfReportContext;
import nts.uk.shr.infra.file.report.aspose.pdf.AsposePdfReportGenerator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
@Stateless
public class EmpInsLossInfoAsposeFileGenerator extends AsposePdfReportGenerator implements EmpInsLossInfoFileGenerator{

    private static final String SHOWA = "昭和";
    private static final String HEISEI = "平成";
    private static final String REIWA = "令和";
    private static final String TAISO = "明治";
    private static final String MEI = "大正";
    private static final String TEMPLATE_FILE = "report/雇用保険被保険者資格喪失届.pdf";

    private static final int NATIONALITY_MAX_BYTE = 22;
    private static final int RESIDENT_STATUS_MAX_BYTE = 18;
    private static final int INSURED_NAME_MAX_BYTE = 22;
    private static final int INSURED_PERSON_ADDRESS = 46;
    private static final int BUSINESS_NAME = 41;
    private static final int CAUSE_OF_LOSS_INS = 46;
    private static final int COMPANY_ADDRESS = 60;


    @Inject
    private JapaneseErasAdapter jpErasAdapter;


    @Override
    public void generate(FileGeneratorContext fileContext, List<EmpInsLossInfoExportData> data) {
        try(AsposePdfReportContext report = this.createContext(TEMPLATE_FILE)) {
            Document doc = report.getDocument();
            Page[] curPage = {doc.getPages().get_Item(1)};
            for (int i = 0; i < data.size(); i++) {
                if (i != 0) {
                    doc.getPages().add(curPage);
                }
            }
            stylePage(doc);
            int indexPage = 1;
            JapaneseEras japaneseEras = jpErasAdapter.getAllEras();

            String emInsNumInfo, companyCode, postCd, address, phoneNumber, causeOfLoss, causeOfLossIns, fullName, nationaly, residence, insuredName, insuredPersonName;
            Integer reqIssuAtr, workingTime, scheForRep ;
            JapaneseDate empInsHistDate;

            for (int i = 0; i < data.size(); i++) {
                Page pdfPage = doc.getPages().get_Item(indexPage);
                TextBuilder textBuilder = new TextBuilder(pdfPage);
                Paragraphs paragraphs = pdfPage.getParagraphs();
                EmpInsLossInfoExportData element = data.get(i);

                //A1_2
                textBuilder.appendText(setValue(114, 757, "1", 16, false));
                //A1_3
                emInsNumInfo =  element.getEmpInsNumInfo() != null ? element.getEmpInsNumInfo().getEmpInsNumber().v() : "" ;
                if( element.getEmpInsNumInfo() != null && !element.getEmpInsNumInfo().getEmpInsNumber().v().equals("")  ){
                    detachText(45,711,emInsNumInfo.length() > 4 ? emInsNumInfo.substring(0,4): emInsNumInfo,4,textBuilder);
                    detachText(130,711,emInsNumInfo.length() > 4 ? emInsNumInfo.substring(4,emInsNumInfo.length()): "",6,textBuilder);
                    detachText(250,711,emInsNumInfo.length() > 10 ? emInsNumInfo.substring(10,emInsNumInfo.length()): "",1,textBuilder);
                }
                //A1_4
                if (element.getLaborInsuranceOffice() != null) {
                    detachText(276, 711, element.getLaborInsuranceOffice().getEmploymentInsuranceInfomation().getOfficeNumber1().isPresent() ? element.getLaborInsuranceOffice().getEmploymentInsuranceInfomation().getOfficeNumber1().get().v() : "", 4, textBuilder);
                    detachText(362, 711, element.getLaborInsuranceOffice().getEmploymentInsuranceInfomation().getOfficeNumber2().isPresent() ? element.getLaborInsuranceOffice().getEmploymentInsuranceInfomation().getOfficeNumber2().get().v() : "", 6, textBuilder);
                    detachText(481, 711, element.getLaborInsuranceOffice().getEmploymentInsuranceInfomation().getOfficeNumber3().isPresent() ? element.getLaborInsuranceOffice().getEmploymentInsuranceInfomation().getOfficeNumber3().get().v() : "", 1, textBuilder);
                }
                switch (element.getEmpInsReportSetting().getOfficeClsAtr()){
                    case OUTPUT_COMPANY: {
                        if (element.getCompanyInfor() != null) {
                            //A2_7
                            textBuilder.appendText(setValue(112, 290, formatTooLongText(element.getCompanyInfor().getCompanyName(), BUSINESS_NAME), 9, false));
                            //A3_1
                            postCd = element.getCompanyInfor().getPostCd();
                            textBuilder.appendText(setValue(150, 201,formatPostalCode(postCd), 9, false));
                            //A3_2
                            textBuilder.appendText(setValue(150, 191, formatTooLongText(element.getCompanyInfor().getAdd_1(), COMPANY_ADDRESS), 9, false));
                            textBuilder.appendText(setValue(150, 181, element.getCompanyInfor().getAdd_2(), 9, false));
                            //A3_3
                            textBuilder.appendText(setValue(150, 160, element.getCompanyInfor().getRepname(), 9, false));
                            //A3_4
                            textBuilder.appendText(setValue(150, 131, formatPhoneNumber(element.getCompanyInfor().getPhoneNum()), 9, false));
                        }
                        break;
                    }
                    case OUPUT_LABOR_OFFICE: {
                        if (element.getLaborInsuranceOffice() != null) {
                            //A2_7
                            textBuilder.appendText(setValue(112, 290, formatTooLongText(element.getLaborInsuranceOffice().getLaborOfficeName().v(), BUSINESS_NAME), 9, false));

                            if (element.getLaborInsuranceOffice().getBasicInformation() != null) {
                                //A3_1
                                postCd =  element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getPostalCode().isPresent() ? element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getPostalCode().get().v() : "";
                                textBuilder.appendText(setValue(150, 201,formatPostalCode(postCd), 9, false));
                                //A3_2
                                String add_1 = "";
                                String add_2 = "";
                                if ( element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress1().isPresent() && element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress2().isPresent()){
                                    add_1 = element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress1().get().toString();
                                    add_2 = element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress2().get().toString();
                                } else {
                                    if (element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress1().isPresent()) {
                                        add_1 = element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress1().get().toString();
                                    }
                                    if (element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress2().isPresent()) {
                                        add_1 = element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress2().get().toString();
                                    }
                                }
                                textBuilder.appendText(setValue(150, 191, formatTooLongText(add_1, COMPANY_ADDRESS), 9, false));
                                textBuilder.appendText(setValue(150, 181, add_2, 9, false));
                                //A3_3
                                textBuilder.appendText(setValue(150, 160, element.getLaborInsuranceOffice().getBasicInformation().getRepresentativeName().isPresent() ? element.getLaborInsuranceOffice().getBasicInformation().getRepresentativeName().get().v() : "", 9, false));
                                //A3_4
                                phoneNumber = element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getPhoneNumber().isPresent() ? element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getPhoneNumber().get().v() : "";
                                textBuilder.appendText(setValue(150, 131, formatPhoneNumber(phoneNumber), 9, false));
                            }
                        }
                        break;
                    }
                    default: break;
                }
                ///A1_5
                empInsHistDate = toJapaneseDate(GeneralDate.fromString(element.getEmpInsHist().getHistoryItem().get(0).start().toString().substring(0, 10), "yyyy/MM/dd"), japaneseEras);
                textBuilder.appendText(setValue(45, 677, findEra(empInsHistDate.era()), 16, false));
                //A1_6
                {
                    String value = (empInsHistDate.year() + 1 < 10 ? "0" + (empInsHistDate.year() + 1) : empInsHistDate.year() + 1) + "" + (empInsHistDate.month() < 10 ? "0" + empInsHistDate.month() : empInsHistDate.month()) + "" + (empInsHistDate.day() < 10 ? "0" + empInsHistDate.day() : empInsHistDate.day()) + "";
                    detachText(79, 677, value, 6, textBuilder);
                }
                //A1_7
                empInsHistDate = toJapaneseDate(GeneralDate.fromString(element.getEmpInsHist().getHistoryItem().get(0).end().toString().substring(0,10), "yyyy/MM/dd"), japaneseEras);
                textBuilder.appendText(setValue(233,677, findEra(empInsHistDate.era()), 16, false));
                {
                    String value = (empInsHistDate.year() + 1 < 10 ? "0" + (empInsHistDate.year() + 1) : empInsHistDate.year() + 1) + "" + (empInsHistDate.month() < 10 ? "0" + empInsHistDate.month() : empInsHistDate.month()) + "" + (empInsHistDate.day() < 10 ? "0" + empInsHistDate.day() : empInsHistDate.day()) + "";
                    detachText(267, 677, value, 6, textBuilder);
                }
                if (element.getEmpInsLossInfo() != null) {
                    //A1_8 Cause of loss
                    causeOfLoss = element.getEmpInsLossInfo().getCauseOfLoss().isPresent() ? String.valueOf(element.getEmpInsLossInfo().getCauseOfLoss().get().value + 1) : "";
                    textBuilder.appendText(setValue(387, 677, causeOfLoss, 16, false));
                    //A1_9 reqIssuAtr
                    if (element.getEmpInsLossInfo().getRequestForIssuance().isPresent()) {
                        reqIssuAtr = element.getEmpInsLossInfo().getRequestForIssuance().get().value;
                        textBuilder.appendText(setValue(45, 629, reqIssuAtr.intValue() == RequestForInsurance.NO.value ? String.valueOf(reqIssuAtr + 2) : String.valueOf(reqIssuAtr) , 16, false));
                    }
                    //A1_10 workingTime
                    workingTime = element.getEmpInsLossInfo().getScheduleWorkingHourPerWeek().isPresent() ? element.getEmpInsLossInfo().getScheduleWorkingHourPerWeek().get().v() : null;
                    detachText(135, 629, formatWorkingTime(workingTime), 4, textBuilder);
                    //A1_11 scheForRep
                    if (element.getEmpInsLossInfo().getScheduleForReplenishment().isPresent()) {
                        scheForRep = element.getEmpInsLossInfo().getScheduleForReplenishment().get().value;
                        textBuilder.appendText(setValue(248, 629, scheForRep.intValue() == RequestForInsurance.NO.value ? " " : String.valueOf(scheForRep), 16, false));
                    }
                    //A2_8
                    if(element.getRetirementReasonClsInfo() != null) {
                        causeOfLossIns = element.getRetirementReasonClsInfo();
                        textBuilder.appendText(setValue(112, 255, formatTooLongText(causeOfLossIns, CAUSE_OF_LOSS_INS), 9, false));
                    }
                }
                //A1_12
                fullName = element.getFullName() != null ? element.getFullName() : "";
                detachText(45, 466, fullName, 28, textBuilder);
                //A1_13
                if(fullName.length() >= 29){
                    detachText(45, 434, element.getFullName() != null ? element.getFullName().substring(28) : "", 12, textBuilder);
                }
                //A1_14
                {
                    detachText(334, 433, element.getPeriodOfStay(), 8, textBuilder);
                }
                //A1_15
                textBuilder.appendText(setValue(109, 397, element.getWorkCategory().toString(), 16, false));
                //A1_16
                nationaly = element.getNationality().toString();
                textBuilder.appendText(setValue(235, 402, formatTooLongText(nationaly, NATIONALITY_MAX_BYTE), 9, false));
                //A1_17
                residence = element.getResidenceStatus().toString();
                textBuilder.appendText(setValue(368, 402, formatTooLongText(residence, RESIDENT_STATUS_MAX_BYTE), 9, false));
                //A2_1
                insuredName = element.getEmpInsReportSetting().getSubmitNameAtr().value == 0 ? element.getName() : element.getReportFullName();
                textBuilder.appendText(setValue(112, 362, insuredName != null ?  insuredName.length() > 23 ? insuredName.substring(0,22) : insuredName : "", 9, false));
                //A2_2
                insuredPersonName = element.getEmpInsReportSetting().getSubmitNameAtr().value == 0 ? element.getNameKana() : element.getReportFullNameKana();
                textBuilder.appendText(setValue(112, 375, insuredPersonName != null ? KatakanaConverter.fullKatakanaToHalf(KatakanaConverter.hiraganaToKatakana(insuredPersonName)) : "", 9, false));
                //A2_3
                Graph graph = new Graph(100, 518);
                // tạo line gạch chữ
                Line line = new Line(new float[]{295, 482, 358, 482});
                Line line2 = new Line(new float[]{185, -101, 240, -101});
                line.getGraphInfo().setLineWidth(5f);
                line2.getGraphInfo().setLineWidth(5f);
                graph.getShapes().add(line);
                graph.getShapes().add(line2);
                paragraphs.add(graph);

                Circle rect = null;
                if (element.getGender() == 1) {
                    rect = new Circle(317, 38, 8);
                } else {
                    rect = new Circle(345, 38, 8);
                }
                rect.getGraphInfo().setLineWidth(1f);
                rect.getGraphInfo().setColor(com.aspose.pdf.Color.getBlack());
                graph.getShapes().add(rect);
                //A2_4
                empInsHistDate = toJapaneseDate(GeneralDate.fromString(element.getBrithDay().substring(0, 10), "yyyy/MM/dd"), japaneseEras);
                Ellipse rect2 = null;
                switch (empInsHistDate.era()) {
                    case MEI: {
                        rect2 = new Ellipse(363, 36, 16.5, 11);
                        break;
                    }
                    case SHOWA: {
                        rect2 = new Ellipse(384, 36, 16.5, 11);
                        break;
                    }
                    case REIWA: {
                        rect2 = new Ellipse(384, 26.5, 16.5, 11);
                        break;
                    }
                    case HEISEI: {
                        rect2 = new Ellipse(363, 26.5, 16.5, 11);
                        break;
                    }
                    default: {
                        rect2 = new Ellipse(370, 40, 0, 0);
                    }
                }
                rect2.getGraphInfo().setLineWidth(1f);
                rect2.getGraphInfo().setColor(com.aspose.pdf.Color.getBlack());
                graph.getShapes().add(rect2);
                //A2_5
                {
                    empInsHistDate = toJapaneseDate(GeneralDate.fromString(element.getBrithDay().substring(0, 10), "yyyy/MM/dd"), japaneseEras);
                    textBuilder.appendText(setValue(418, 357, empInsHistDate.year() + 1 + "", 9, false));
                    textBuilder.appendText(setValue(455, 357,  empInsHistDate.month() + "", 9, false));
                    textBuilder.appendText(setValue(491, 357, empInsHistDate.day() + "", 9, false));
                }
                //A2_6
                if (element.getCurrentPersonResidence() != null){
                    address = element.getCurrentPersonResidence().getAddress1() + element.getCurrentPersonResidence().getAddress2();
                    textBuilder.appendText(setValue(112, 328, formatTooLongText(address, INSURED_PERSON_ADDRESS), 9, false));
                }

                //A3_5
                empInsHistDate = toJapaneseDate(element.getFillingDate(),japaneseEras);
                detachDate(480, 206, empInsHistDate, textBuilder);
                //index page
                indexPage = indexPage + 1;
            }
            report.saveAsPdf(this.createNewFile(fileContext, "雇用保険被保険者資格喪失届.pdf"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private JapaneseDate toJapaneseDate(GeneralDate date, JapaneseEras japaneseEras) {
        Optional<JapaneseEraName> era = japaneseEras.eraOf(date);
        return new JapaneseDate(date, era.get());
    }

    private void stylePage(Document doc) {
        PageInfo pageInfo = doc.getPageInfo();
        MarginInfo marginInfo = pageInfo.getMargin();
        marginInfo.setLeft(0);
        marginInfo.setRight(0);
        marginInfo.setTop(0);
        marginInfo.setBottom(0);
    }

    private TextFragment setValue(int x, int y, String value, int textSize, boolean isDetach) {
        TextFragment textFragment = new TextFragment(value);
        textFragment.setPosition(new Position(x, y));
        styleText(textFragment.getTextState(), textSize, isDetach);
        return textFragment;
    }

    private void styleText(TextFragmentState textFragmentState, int textSize, boolean isDetach) {
        if (isDetach) {
            textFragmentState.setCharacterSpacing(9);
        }
        textFragmentState.setFont(FontRepository.findFont("MS-Gothic"));
        textFragmentState.setFontSize(textSize);
        textFragmentState.setForegroundColor(Color.getBlack());
    }

    private void detachText(int xRoot, int yRoot, String value, int numCells,TextBuilder textBuilder) {
        value = KatakanaConverter.hiraganaToKatakana(value);
        value = KatakanaConverter.fullKatakanaToHalf(value);
        if (value.length() > numCells) {
            value = value.substring(0, numCells);
        }

        textBuilder.appendText(setValue(xRoot, yRoot, value, 16, true));

    }
    private String formatPhoneNumber(String number) {
        if (number.matches("(\\+*\\d*\\(\\d*\\)\\d*)")) {
            return number;
        }
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

    private String findEra(String era) {
        if (TAISO.equals(era)) {
            return " ";
        }
        if (MEI.equals(era)) {
            return " ";
        }
        if (SHOWA.equals(era)) {
            return "3";
        }
        if (HEISEI.equals(era)) {
            return "4";
        }
        if (REIWA.equals(era)) {
            return "5";
        }
        return "";
    }

    private String formatPostalCode(String number){
        String postalCode = "";
        String[] numberSplit = number.split("-");
        String[] temp = new String[2];
        if("".equals(number)) {
            return number;
        }
        if(numberSplit.length > 1){
            postalCode =  numberSplit[0] + "－" + numberSplit[1];
        }else{
            temp[0] = number.length() > 2 ? number.substring(0,3) : number;
            temp[1] = number.length() > 3 ? number.substring(3,number.length()) : "";
            postalCode =  temp[0] + "－" + temp[1];
        }
        return postalCode;
    }

    private void detachDate(int xRoot, int yRoot, JapaneseDate value, TextBuilder textBuilder) {
        textBuilder.appendText(setValue(xRoot, yRoot,  value.year() + 1 + "", 9, false));
        textBuilder.appendText(setValue(xRoot + 36, yRoot, value.month() + "", 9, false));
        textBuilder.appendText(setValue(xRoot + 66, yRoot,  value.day() + "", 9, false));
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

}
