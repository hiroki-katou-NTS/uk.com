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
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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
    private static final int BUSINESS_NAME = 31;
    private static final int CAUSE_OF_LOSS_INS = 46;
    private static final int COMPANY_ADDRESS = 75;


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
            for (int i = 0; i < data.size(); i++) {
                Page pdfPage = doc.getPages().get_Item(indexPage);
                TextBuilder textBuilder = new TextBuilder(pdfPage);
                Paragraphs paragraphs = pdfPage.getParagraphs();
                EmpInsLossInfoExportData element = data.get(i);

                //A1_2
                textBuilder.appendText(setValue(114, 757, "1", 16));
                //A1_3
                String emInsNumInfo =  element.getEmpInsNumInfo() != null ? element.getEmpInsNumInfo().getEmpInsNumber().v() : "" ;
                if( element.getEmpInsNumInfo() != null && !element.getEmpInsNumInfo().getEmpInsNumber().v().equals("")  ){
                    detachText(45,711,emInsNumInfo.length() > 4 ? emInsNumInfo.substring(0,4): emInsNumInfo,4,textBuilder);
                    detachText(130,711,emInsNumInfo.length() > 4 ? emInsNumInfo.substring(4,emInsNumInfo.length()): "",6,textBuilder);
                    detachText(250,711,emInsNumInfo.length() > 10 ? emInsNumInfo.substring(10,emInsNumInfo.length()): "",1,textBuilder);
                }

                switch (element.getEmpInsReportSetting().getOfficeClsAtr()){
                    case OUTPUT_COMPANY: {
                        if (element.getCompanyInfor() != null) {
                            //A1_4
                            String companyCode = element.getCompanyInfor().getCompanyCode();
                            detachText(276, 711,companyCode.length() > 4 ? companyCode.substring(0,4) : companyCode, 4, textBuilder);
                            detachText(362, 711, companyCode.length() > 4 ? companyCode.substring(4,companyCode.length()) : "", 6, textBuilder);
                            detachText(481, 711, companyCode.length() > 11 ? companyCode.substring(10,companyCode.length()) : "", 1, textBuilder);
                            //A2_7
                            textBuilder.appendText(setValue(112, 290, formatTooLongText(element.getCompanyInfor().getCompanyName(), BUSINESS_NAME), 9));
                            //A3_1
                            String postCd = element.getCompanyInfor().getPostCd();
                            textBuilder.appendText(setValue(150, 190,formatPostalCode(postCd), 9));
                            //A3_2
                            String address = element.getCompanyInfor().getAdd_1() + " " + element.getCompanyInfor().getAdd_2();

                            textBuilder.appendText(setValue(210, 190, formatTooLongText(address, COMPANY_ADDRESS), 9));
                            //A3_3
                            textBuilder.appendText(setValue(150, 160, element.getCompanyInfor().getRepname(), 9));
                            //A3_4
                            textBuilder.appendText(setValue(150, 131, formatPhoneNumber(element.getCompanyInfor().getPhoneNum()), 9));
                        }
                        break;
                    }
                    case OUPUT_LABOR_OFFICE: {
                        if (element.getLaborInsuranceOffice() != null) {
                            //A1_4
                            detachText(276, 711, element.getLaborInsuranceOffice().getEmploymentInsuranceInfomation().getOfficeNumber1().isPresent() ? element.getLaborInsuranceOffice().getEmploymentInsuranceInfomation().getOfficeNumber1().get().v() : "", 4, textBuilder);
                            detachText(362, 711, element.getLaborInsuranceOffice().getEmploymentInsuranceInfomation().getOfficeNumber2().isPresent() ? element.getLaborInsuranceOffice().getEmploymentInsuranceInfomation().getOfficeNumber2().get().v() : "", 6, textBuilder);
                            detachText(481, 711, element.getLaborInsuranceOffice().getEmploymentInsuranceInfomation().getOfficeNumber3().isPresent() ? element.getLaborInsuranceOffice().getEmploymentInsuranceInfomation().getOfficeNumber3().get().v() : "", 1, textBuilder);

                            //A2_7
                            textBuilder.appendText(setValue(112, 290, formatTooLongText(element.getLaborInsuranceOffice().getLaborOfficeName().v(), BUSINESS_NAME), 9));

                            if (element.getLaborInsuranceOffice().getBasicInformation() != null) {
                                //A3_1
                                String postCd =  element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getPostalCode().isPresent() ? element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getPostalCode().get().v() : "";
                                textBuilder.appendText(setValue(150, 190,formatPostalCode(postCd), 9));
                                //A3_2
                                String addressLabor;
                                if (element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress1().isPresent() && element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress2().isPresent()) {
                                    addressLabor = element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress1().get().toString() + " " + element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress2().get().toString();
                                } else {
                                    if (element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress1().isPresent()) {
                                        addressLabor = element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress1().get().toString()  + " " + (element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress2().isPresent() ? element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress2().get().toString() : "");
                                    } else {
                                        addressLabor = element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress2().isPresent() ? element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress2().get().toString() : "";
                                    }
                                }
                                textBuilder.appendText(setValue(210, 190, formatTooLongText(addressLabor, COMPANY_ADDRESS), 9));
                                //A3_3
                                textBuilder.appendText(setValue(150, 160, element.getLaborInsuranceOffice().getBasicInformation().getRepresentativeName().isPresent() ? element.getLaborInsuranceOffice().getBasicInformation().getRepresentativeName().get().v() : "", 9));
                                //A3_4
                                String phoneNumber = element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getPhoneNumber().isPresent() ? element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getPhoneNumber().get().v() : "";
                                textBuilder.appendText(setValue(150, 131, formatPhoneNumber(phoneNumber), 9));
                            }
                        }
                        break;
                    }
                    default: break;
                }
                ///A1_5
                JapaneseDate empInsHistStartDate = toJapaneseDate(GeneralDate.fromString(element.getEmpInsHist().getHistoryItem().get(0).start().toString().substring(0, 10), "yyyy/MM/dd"), japaneseEras);
                textBuilder.appendText(setValue(45, 677, findEra(empInsHistStartDate.era()), 16));
                //A1_6
                {
                    String value = (empInsHistStartDate.year() + 1 < 10 ? "0" + (empInsHistStartDate.year() + 1) : empInsHistStartDate.year() + 1) + "" + (empInsHistStartDate.month() < 10 ? "0" + empInsHistStartDate.month() : empInsHistStartDate.month()) + "" + (empInsHistStartDate.day() < 10 ? "0" + empInsHistStartDate.day() : empInsHistStartDate.day()) + "";
                    detachText(79, 677, value, 6, textBuilder);
                }
                //A1_7
                JapaneseDate empInsHistEndDate = toJapaneseDate(GeneralDate.fromString(element.getEmpInsHist().getHistoryItem().get(0).end().toString().substring(0,10), "yyyy/MM/dd"), japaneseEras);
                textBuilder.appendText(setValue(233,677, findEra(empInsHistEndDate.era()), 16));
                {
                    String value = (empInsHistEndDate.year() + 1 < 10 ? "0" + (empInsHistEndDate.year() + 1) : empInsHistEndDate.year() + 1) + "" + (empInsHistEndDate.month() < 10 ? "0" + empInsHistEndDate.month() : empInsHistEndDate.month()) + "" + (empInsHistEndDate.day() < 10 ? "0" + empInsHistEndDate.day() : empInsHistEndDate.day()) + "";
                    detachText(267, 677, value, 6, textBuilder);
                }

                if (element.getEmpInsLossInfo() != null) {
                    //A1_8 Cause of loss
                    String causeOfLoss = element.getEmpInsLossInfo().getCauseOfLossAtr().isPresent() ? String.valueOf(element.getEmpInsLossInfo().getCauseOfLossAtr().get().value + 1) : "";
                    textBuilder.appendText(setValue(387, 677, causeOfLoss, 16));
                    //A1_9 reqIssuAtr
                    if (element.getEmpInsLossInfo().getRequestForIssuance().isPresent()) {
                        Integer reqIssuAtr = element.getEmpInsLossInfo().getRequestForIssuance().get().value;
                        textBuilder.appendText(setValue(45, 629, reqIssuAtr.intValue() == RequestForInsurance.NO.value ? String.valueOf(reqIssuAtr + 2) : String.valueOf(reqIssuAtr) , 16));
                    }
                    //A1_10 workingTime
                    Integer workingTime = element.getEmpInsLossInfo().getScheduleWorkingHourPerWeek().isPresent() ? element.getEmpInsLossInfo().getScheduleWorkingHourPerWeek().get().v() : null;
                    detachText(135, 629, formatWorkingTime(workingTime), 4, textBuilder);
                    //A1_11 scheForRep
                    if (element.getEmpInsLossInfo().getScheduleForReplenishment().isPresent()) {
                        Integer scheForRep = element.getEmpInsLossInfo().getScheduleForReplenishment().get().value;
                        textBuilder.appendText(setValue(248, 629, scheForRep.intValue() == RequestForInsurance.NO.value ? " " : String.valueOf(scheForRep), 16));
                    }
                    //A2_8
                    if(element.getRetirementReasonClsInfo() != null) {
                        String causeOfLossIns = element.getRetirementReasonClsInfo();
                        textBuilder.appendText(setValue(112, 255, formatTooLongText(causeOfLossIns, CAUSE_OF_LOSS_INS), 9));
                    }
                }
                //A1_12
                String fullName = element.getFullName() != null ? element.getFullName() : "";
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
                textBuilder.appendText(setValue(109, 397, element.getWorkCategory().toString(), 16));
                //A1_16
                String nationaly = element.getNationality().toString();
                textBuilder.appendText(setValue(235, 402, formatTooLongText(nationaly, NATIONALITY_MAX_BYTE), 9));
                //A1_17
                String residence = element.getResidenceStatus().toString();
                textBuilder.appendText(setValue(368, 402, formatTooLongText(residence, RESIDENT_STATUS_MAX_BYTE), 9));
                //A2_1
                String insuredName = element.getEmpInsReportSetting().getSubmitNameAtr().value == 0 ? element.getName() : element.getReportFullName();
                textBuilder.appendText(setValue(112, 362, insuredName != null ?  insuredName.length() > 23 ? insuredName.substring(0,22) : insuredName : "", 9));
                //A2_2
                String insuredPersonName = element.getEmpInsReportSetting().getSubmitNameAtr().value == 0 ? element.getNameKana() : element.getReportFullNameKana();
                textBuilder.appendText(setValue(112, 375, insuredPersonName != null ? insuredPersonName.length() > 23 ? insuredPersonName.substring(0,22) :insuredPersonName : "", 9));
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
                JapaneseDate birthDay = toJapaneseDate(GeneralDate.fromString(element.getBrithDay().substring(0, 10), "yyyy/MM/dd"), japaneseEras);
                Ellipse rect2 = null;
                switch (birthDay.era()) {
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
                    JapaneseDate birthDayJapanCla = toJapaneseDate(GeneralDate.fromString(element.getBrithDay().substring(0, 10), "yyyy/MM/dd"), japaneseEras);
                    textBuilder.appendText(setValue(418, 357, birthDayJapanCla.year() + 1 + "", 9));
                    textBuilder.appendText(setValue(455, 357,  birthDayJapanCla.month() + "", 9));
                    textBuilder.appendText(setValue(491, 357, birthDayJapanCla.day() + "", 9));
                }
                //A2_6
                if (element.getCurrentPersonResidence() != null){
                    String currentAddress = element.getCurrentPersonResidence().getAddress1() + " " + element.getCurrentPersonResidence().getAddress2();
                    textBuilder.appendText(setValue(112, 328, formatTooLongText(currentAddress, INSURED_PERSON_ADDRESS), 9));
                }

                //A3_5
                JapaneseDate fillingDate = toJapaneseDate(element.getFillingDate(),japaneseEras);
                detachDate(486, 206, fillingDate, textBuilder);
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

    private void detachText(int xRoot, int yRoot, String value, int numCells,TextBuilder textBuilder) {
        value = KatakanaConverter.hiraganaToKatakana(value);
        value = KatakanaConverter.fullKatakanaToHalf(value);
        if (value.length() > numCells) {
            value = value.substring(0, numCells);
        }
        String[] lstValue = value.split("");
        for (int i = 0; i < lstValue.length; i++) {
            int pixel = xRoot + (17 * i);
            textBuilder.appendText(setValue(pixel, yRoot, lstValue[i], 16));
        }
    }
    private String formatPhoneNumber(String number){
        String numberPhone = "";
        String[] numberSplit = number.split("-");
        String[] temp = new String[3];

        if(numberSplit.length == 2){

            if(numberSplit[1].length() <= 3){
                temp[0] = numberSplit[1].substring(0,numberSplit[1].length());
                numberPhone = numberSplit[0] + "  -" + temp[0] + " -";
            }else{
                temp[0] = numberSplit[1].substring(0,3);
                temp[1] = numberSplit[1].substring(3,numberSplit[1].length());
                numberPhone = numberSplit[0] + "  -" + temp[0] + " -" + temp[1];
            }

        }else if(numberSplit.length >= 3){
            numberPhone = numberSplit[0] + "  -" + numberSplit[1] + " -" + numberSplit[2];
        }else if(numberSplit.length == 1){
            if(number.length() <= 3){
                temp[0] = number.substring(0,number.length());
                numberPhone = temp[0];
            }else if( number.length() <=6){
                temp[0] = number.substring(0,3);
                temp[1] = number.substring(3,number.length());
                numberPhone = temp[0] + "  -" + temp[1] + " -";
            }else{
                temp[0] = number.substring(0,3);
                temp[1] = number.substring(3,6);
                temp[2] = number.substring(6,number.length());
                numberPhone = temp[0] + "  -" + temp[1] + " -" + temp[2];
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
        textBuilder.appendText(setValue(xRoot, yRoot,  value.year() + 1 + "", 9));
        textBuilder.appendText(setValue(xRoot + 30, yRoot, value.month() + "", 9));
        textBuilder.appendText(setValue(xRoot + 60, yRoot,  value.day() + "", 9));
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
