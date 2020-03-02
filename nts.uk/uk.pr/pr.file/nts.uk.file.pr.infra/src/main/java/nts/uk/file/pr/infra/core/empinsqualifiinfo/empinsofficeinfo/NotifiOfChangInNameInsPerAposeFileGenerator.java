package nts.uk.file.pr.infra.core.empinsqualifiinfo.empinsofficeinfo;


import com.aspose.pdf.*;
import com.aspose.pdf.drawing.Circle;
import com.aspose.pdf.drawing.Ellipse;
import com.aspose.pdf.drawing.Graph;
import com.aspose.pdf.drawing.Line;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.gul.text.KatakanaConverter;
import nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsofficeinfo.NotifiOfChangInNameInsPerExFileGenerator;
import nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsofficeinfo.NotifiOfChangInNameInsPerExportData;
import nts.uk.shr.com.time.japanese.JapaneseDate;
import nts.uk.shr.com.time.japanese.JapaneseEraName;
import nts.uk.shr.com.time.japanese.JapaneseErasAdapter;
import nts.uk.shr.infra.file.report.aspose.pdf.AsposePdfReportContext;
import nts.uk.shr.infra.file.report.aspose.pdf.AsposePdfReportGenerator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpSubNameClass.PERSONAL_NAME;

@Stateless
public class NotifiOfChangInNameInsPerAposeFileGenerator extends AsposePdfReportGenerator implements NotifiOfChangInNameInsPerExFileGenerator {
    private static final String TEMPLATE_FILE = "report/氏名変更届.pdf";
    private static final String SHOWA = "昭和";

    private static final String HEISEI = "平成";

    private static final String PEACE = "令和";

    private static final String TAISO = "明治";

    private static final String MEI = "大正";

    @Inject
    private JapaneseErasAdapter adapter;

    @Override
    public void generate(FileGeneratorContext fileContext, List<NotifiOfChangInNameInsPerExportData> data) {
        try (AsposePdfReportContext report = this.createContext(TEMPLATE_FILE)) {
            Document doc = report.getDocument();
            Page[] curPage = {doc.getPages().get_Item(1)};
            for (int i = 0; i < data.size(); i++) {
                if (i != 0) {
                    doc.getPages().add(curPage);
                }
            }
            stylePage(doc);
            int indexPage = 1;
            for (int i = 0; i < data.size(); i++) {

                Page pdfPage = doc.getPages().get_Item(indexPage);
                TextBuilder textBuilder = new TextBuilder(pdfPage);
                Paragraphs paragraphs = pdfPage.getParagraphs();
                NotifiOfChangInNameInsPerExportData element = data.get(i);

                // A1_2
                textBuilder.appendText(setValue(114, 757, "0", 16));
                //A1_3
                String emInsNumInfo = element.getEmpInsNumInfo() != null ? element.getEmpInsNumInfo().getEmpInsNumber().v() : "";
                if (element.getEmpInsNumInfo() != null && !element.getEmpInsNumInfo().getEmpInsNumber().v().equals("")) {
                    detachText(45, 711, emInsNumInfo.length() > 4 ? emInsNumInfo.substring(0, 4) : emInsNumInfo, 4, textBuilder);
                    detachText(130, 711, emInsNumInfo.length() > 4 ? emInsNumInfo.substring(4, emInsNumInfo.length()) : "", 6, textBuilder);
                    detachText(250, 711, emInsNumInfo.length() > 10 ? emInsNumInfo.substring(10, emInsNumInfo.length()) : "", 1, textBuilder);
                }
                //A1_4
                {
                    switch (element.getEmpInsReportSetting().getOfficeClsAtr()) {
                        case OUTPUT_COMPANY: {
                            if (element.getCompanyInfor() != null) {
                                String companyCode = element.getCompanyInfor().getCompanyCode();
                                detachText(276, 711, companyCode.length() > 4 ? companyCode.substring(0, 4) : companyCode, 4, textBuilder);
                                detachText(362, 711, companyCode.length() > 4 ? companyCode.substring(4, companyCode.length()) : "", 6, textBuilder);
                                detachText(481, 711, companyCode.length() > 11 ? companyCode.substring(10, companyCode.length()) : "", 1, textBuilder);
                                //A2_6
                                textBuilder.appendText(setValue(112, 290, element.getCompanyInfor().getCompanyName(), 9));
                                //A3_1
                                String postCd = element.getCompanyInfor().getPostCd();
                                textBuilder.appendText(setValue(150, 190, formatPostalCode(postCd), 9));
                                //A3_2
                                textBuilder.appendText(setValue(210, 190, formatTooLongText(element.getCompanyInfor().getAdd_1() + element.getCompanyInfor().getAdd_2(),62), 9));
                                //A3_3
                                textBuilder.appendText(setValue(150, 160, element.getCompanyInfor().getRepname(), 9));
                                //A3_4
                                textBuilder.appendText(setValue(150, 131, formatPhoneNumber(element.getCompanyInfor().getPhoneNum()), 9));
                            }
                            break;
                        }
                        case OUPUT_LABOR_OFFICE: {
                            if (element.getLaborInsuranceOffice() != null) {
                                detachText(276, 711, element.getLaborInsuranceOffice().getEmploymentInsuranceInfomation().getOfficeNumber1().isPresent() ? element.getLaborInsuranceOffice().getEmploymentInsuranceInfomation().getOfficeNumber1().get().v() : "", 4, textBuilder);
                                detachText(362, 711, element.getLaborInsuranceOffice().getEmploymentInsuranceInfomation().getOfficeNumber2().isPresent() ? element.getLaborInsuranceOffice().getEmploymentInsuranceInfomation().getOfficeNumber2().get().v() : "", 6, textBuilder);
                                detachText(481, 711, element.getLaborInsuranceOffice().getEmploymentInsuranceInfomation().getOfficeNumber3().isPresent() ? element.getLaborInsuranceOffice().getEmploymentInsuranceInfomation().getOfficeNumber3().get().v() : "", 1, textBuilder);

                                //A2_6
                                textBuilder.appendText(setValue(112, 290, element.getLaborInsuranceOffice().getLaborOfficeName().v(), 9));
                                //A3_1
                                if (element.getLaborInsuranceOffice().getBasicInformation() != null) {
                                    String postCd = element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getPostalCode().isPresent() ? element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getPostalCode().get().v() : "";
                                    textBuilder.appendText(setValue(150, 190, formatPostalCode(postCd), 9));
                                    //A3_2
                                    String addressLabor;
                                    if (element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress1().isPresent() && element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress2().isPresent()) {
                                        addressLabor = element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress1().get().toString() + element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress2().get().toString();
                                    } else {
                                        if (element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress1().isPresent()) {
                                            addressLabor = element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress1().get().toString() + (element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress2().isPresent() ? element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress2().get().toString() : "");
                                        } else {
                                            addressLabor = element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress2().isPresent() ? element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress2().get().toString() : "";
                                        }
                                    }

                                    textBuilder.appendText(setValue(210, 190, formatTooLongText(addressLabor,62), 9));
                                    //A3_3
                                    textBuilder.appendText(setValue(150, 160, element.getLaborInsuranceOffice().getBasicInformation().getRepresentativeName().isPresent() ? element.getLaborInsuranceOffice().getBasicInformation().getRepresentativeName().get().v() : "", 9));
                                    //A3_4
                                    String phoneNumber = element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getPhoneNumber().isPresent() ? element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getPhoneNumber().get().v() : "";
                                    textBuilder.appendText(setValue(150, 131, formatPhoneNumber(phoneNumber), 9));
                                }
                            }
                            break;
                        }
                        case DO_NOT_OUTPUT: {
                            //A2_6
                            //A3_1
                            break;
                        }
                    }
                }
                //A1_5
                JapaneseDate empInsHistStartDate = toJapaneseDate(GeneralDate.fromString(element.getEmpInsHist().getHistoryItem().get(0).start().toString().substring(0, 10), "yyyy/MM/dd"));
                textBuilder.appendText(setValue(45, 677, findEra(empInsHistStartDate.era()), 16));
                //A1_6
                {
                    String value = (empInsHistStartDate.year() + 1 < 10 ? "0" + (empInsHistStartDate.year() + 1) : empInsHistStartDate.year() + 1) + "" + (empInsHistStartDate.month() < 10 ? "0" + empInsHistStartDate.month() : empInsHistStartDate.month()) + "" + (empInsHistStartDate.day() < 10 ? "0" + empInsHistStartDate.day() : empInsHistStartDate.day()) + "";
                    detachText(79, 677, value, 6, textBuilder);
                }
                //A1_7
                if (element.getEmpInsReportSetting().getSubmitNameAtr() == PERSONAL_NAME) {

                    textBuilder.appendText(setValue(45, 586, element.getName() != null ? formatTooLongText(element.getName(),13) :  "", 16));
                    //A1_8
                    detachText(182, 586, element.getNameKana() != null ? element.getNameKana() : "", 20, textBuilder);

                } else {
                    textBuilder.appendText(setValue(45, 586,element.getReportFullName() != null ?  formatTooLongText(element.getReportFullName(),13) : "" , 16));
                    //A1_8
                    detachText(182, 586, element.getReportFullNameKana() != null ? element.getReportFullNameKana() : "", 20, textBuilder);
                }
                //A1_9
                GeneralDate dummyStart = GeneralDate.fromString("2015/01/01", "yyyy/MM/dd");
                GeneralDate dummyEnd = GeneralDate.fromString("2019/01/01", "yyyy/MM/dd");
                GeneralDate baseDate = GeneralDate.fromString(element.getFillingDate().substring(0, 10), "yyyy/MM/dd");

                if (baseDate.beforeOrEquals(dummyEnd) && baseDate.afterOrEquals(dummyStart)) {
                    String fullName = element.getFullName() != null ? element.getFullName() : "";
                    detachText(45, 466, fullName, 28, textBuilder);
                    //A1_10
                    //Check
                    if (fullName.length() >= 28) {
                        detachText(45, 434, element.getFullName().substring(28, element.getFullName().length()), 12, textBuilder);
                    }
                }
                //A2_1
                textBuilder.appendText(setValue(112, 362, element.getOldName() != null ? element.getOldName().length() > 21 ? element.getOldName().substring(0, 20) : element.getOldName() : "", 9));
                //A2_2
                textBuilder.appendText(setValue(112, 375, element.getOldNameKana() != null ? element.getOldNameKana().length() > 21 ? element.getOldNameKana().substring(0, 20) : element.getOldNameKana() : "", 9));
                //A2_3
                Graph graph = new Graph(100, 518);
                // tạo line gạch chữ
                Line line = new Line(new float[]{295, 495, 357, 495});
                Line line2 = new Line(new float[]{125, -101, 180, -101});
                line.getGraphInfo().setLineWidth(5f);
                line2.getGraphInfo().setLineWidth(5f);
                graph.getShapes().add(line);
                graph.getShapes().add(line2);
                paragraphs.add(graph);

                //

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
                JapaneseDate birthDay = toJapaneseDate(GeneralDate.fromString(element.getBrithDay().substring(0, 10), "yyyy/MM/dd"));
//                textBuilder.appendText(setValue(400,353,);
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
                    case PEACE: {
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
                    JapaneseDate birthDayJapanCla = toJapaneseDate(GeneralDate.fromString(element.getBrithDay().substring(0, 10), "yyyy/MM/dd"));
                    if (!birthDayJapanCla.era().equals(TAISO)) {
                        textBuilder.appendText(setValue(418, 357, birthDayJapanCla.year() + 1 + "", 9));
                        textBuilder.appendText(setValue(455, 357, birthDayJapanCla.month() + "", 9));
                        textBuilder.appendText(setValue(491, 357, birthDayJapanCla.day() + "", 9));
                    }

                }
                //A2_7
                if (!element.getChangeDate().equals("")) {
                    JapaneseDate changeDate = toJapaneseDate(GeneralDate.fromString(element.getChangeDate().substring(0, 10), "yyyy/MM/dd"));
                    //lấy ví dụ  element.getChangeDate()
                    detachDate(445, 284, changeDate, textBuilder);
                }
                //A3_5
                JapaneseDate fillingDate = toJapaneseDate(GeneralDate.fromString(element.getFillingDate().substring(0, 10), "yyyy/MM/dd"));

                detachDate(486, 206, fillingDate, textBuilder);

                //index page
                indexPage++;
            }
            report.saveAsPdf(this.createNewFile(fileContext, "雇用保険被保険者氏名変更届.pdf"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String findEra(String era) {
        if (TAISO.equals(era)) {
            return "1";
        }
        if (MEI.equals(era)) {
            return "2";
        }

        if (SHOWA.equals(era)) {
            return "3";
        }
        if (HEISEI.equals(era)) {
            return "4";
        }
        if (PEACE.equals(era)) {
            return "5";
        }
        return "";
    }

    private JapaneseDate toJapaneseDate(GeneralDate date) {
        Optional<JapaneseEraName> era = this.adapter.getAllEras().eraOf(date);
        return new JapaneseDate(date, era.get());
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
        String[] lstValue = value.split("");
        for (int i = 0; i < lstValue.length; i++) {
            int pixel = xRoot + (17 * i);
            textBuilder.appendText(setValue(pixel, yRoot, lstValue[i], 16));
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
        textBuilder.appendText(setValue(xRoot + 30, yRoot, value.month() + "", 9));
        textBuilder.appendText(setValue(xRoot + 60, yRoot, value.day() + "", 9));
    }

    private String formatPhoneNumber(String number) {
        String numberPhone = "";
        String[] numberSplit = number.split("-");
        String[] temp = new String[3];

        if (numberSplit.length == 2) {

            if (numberSplit[1].length() <= 3) {
                temp[0] = numberSplit[1].substring(0, numberSplit[1].length());
                numberPhone = numberSplit[0] + "（  " + temp[0] + "  ）";
            } else {
                temp[0] = numberSplit[1].substring(0, 3);
                temp[1] = numberSplit[1].substring(3, numberSplit[1].length());
                numberPhone = numberSplit[0] + "（  " + temp[0] + "  ）" + temp[1];
            }

        } else if (numberSplit.length >= 3) {
            numberPhone = numberSplit[0] + "（ 　" + numberSplit[1] + "  ）" + numberSplit[2];
        } else if (numberSplit.length == 1) {
            if (number.length() <= 3) {
                temp[0] = number.substring(0, number.length());
                numberPhone = temp[0];
            } else if (number.length() <= 6) {
                temp[0] = number.substring(0, 3);
                temp[1] = number.substring(3, number.length());
                numberPhone = temp[0] + "（  " + temp[1] + "  ）";
            } else {
                temp[0] = number.substring(0, 3);
                temp[1] = number.substring(3, 6);
                temp[2] = number.substring(6, number.length());
                numberPhone = temp[0] + "（  " + temp[1] + "  ）" + temp[2];
            }

        }

        return numberPhone;
    }

    private String formatPostalCode(String number) {
        String postalCode = "";
        String[] numberSplit = number.split("-");
        String[] temp = new String[2];
        if ("".equals(number)) {
            return number;
        }
        if (numberSplit.length > 1) {
            postalCode = numberSplit[0] + "-" + numberSplit[1];
        } else {
            temp[0] = number.length() > 2 ? number.substring(0, 3) : number;
            temp[1] = number.length() > 3 ? number.substring(3, number.length()) : "";
            postalCode = temp[0] + "-" + temp[1];
        }
        return postalCode;
    }

    private String formatTooLongText(String text, int maxByteAllowed) throws UnsupportedEncodingException {
        if (text.getBytes("Shift_JIS").length < maxByteAllowed)
            return text;
        return text.substring(0, text.length() * maxByteAllowed / text.getBytes("Shift_JIS").length);
    }
}
