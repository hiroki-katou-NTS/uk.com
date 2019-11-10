package nts.uk.file.pr.infra.core.empinsqualifiinfo.empinsofficeinfo;

import com.aspose.pdf.*;
import com.aspose.pdf.drawing.Circle;
import com.aspose.pdf.drawing.Graph;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.file.app.core.empinsreportsetting.EmpInsReportSettingExFileGenerator;
import nts.uk.ctx.pr.file.app.core.empinsreportsetting.EmpInsReportSettingExportData;
import nts.uk.shr.com.time.japanese.JapaneseDate;
import nts.uk.shr.com.time.japanese.JapaneseEraName;
import nts.uk.shr.com.time.japanese.JapaneseErasAdapter;
import nts.uk.shr.infra.file.report.aspose.pdf.AsposePdfReportContext;
import nts.uk.shr.infra.file.report.aspose.pdf.AsposePdfReportGenerator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpSubNameClass.PERSONAL_NAME;

@Stateless
public class EmpInsReportSettingAposeFileGenerator extends AsposePdfReportGenerator implements EmpInsReportSettingExFileGenerator {
    private static final String TEMPLATE_FILE = "report/氏名変更届.pdf";
    private static final String SHOWA = "昭和";

    private static final String HEISEI = "平成";

    private static final String PEACE = "令和";

    private static final String TAISO = "明治";

    private static final String MEI = "大正";

    @Inject
    private JapaneseErasAdapter adapter;

    @Override
    public void generate(FileGeneratorContext fileContext, List<EmpInsReportSettingExportData> data) {
        try (AsposePdfReportContext report = this.createContext(TEMPLATE_FILE)) {
            Document doc = report.getDocument();
            Page[] curPage = {doc.getPages().get_Item(1),doc.getPages().get_Item(2)};
            for (int i = 0 ; i < data.size() ; i++){
                if(i != 0){
                    doc.getPages().add(curPage);
                }
            }
            stylePage(doc);
            int indexPage =1;
            for (int i = 0 ; i < data.size() ; i++){

                Paragraphs paragraphs =  doc.getPages().get_Item(indexPage).getParagraphs();
                EmpInsReportSettingExportData element = data.get(i);
                // A1_2
                paragraphs.add(setValue(114,757,"0"));
                //A1_3
                paragraphs.add(setValue(130,712,element.getEmpInsNumInfo() != null ? element.getEmpInsNumInfo().getEmpInsNumber().v() : ""));
                //A1_4
                {
                    switch (element.getEmpInsReportSetting().getOfficeClsAtr()){
                        case OUTPUT_COMPANY:{
                            if(element.getCompanyInfor() != null){
//                                paragraphs.add(setValue(114,711, element.getCompanyInfor().getCompanyCode()));
                                detachText(363,711,element.getCompanyInfor().getCompanyCode(),6,paragraphs);
                                //A2_6
                                paragraphs.add(setValue(120,290, element.getCompanyInfor().getCompanyName()));
                                //A3_1
                                paragraphs.add(setValue(150,186, element.getCompanyInfor().getPostCd()));
                                //A3_2
                                paragraphs.add(setValue(220,186, element.getCompanyInfor().getAdd_1()+  element.getCompanyInfor().getAdd_2()));
                                //A3_3
                                paragraphs.add(setValue(150,160, element.getCompanyInfor().getRepname()));
                                //A3_4
                                paragraphs.add(setValue(150,130,element.getCompanyInfor().getPhoneNum()));
                            }
                            break;
                        }
                        case OUPUT_LABOR_OFFICE:{
                            if(element.getLaborInsuranceOffice() != null){
                                detachText(360,711, element.getLaborInsuranceOffice().getLaborOfficeCode().v(),6,paragraphs);
//                                paragraphs.add(setValue());
                                //A2_6
                                paragraphs.add(setValue(120,290, element.getLaborInsuranceOffice().getLaborOfficeName().v()));
                                //A3_1
                                if( element.getLaborInsuranceOffice().getBasicInformation() != null ){
                                    paragraphs.add(setValue(150,186, element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getPostalCode().isPresent() ? element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getPostalCode().get().v() : "" ));
                                    //A3_2
                                    String addressLabor ;
                                    if(element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress1().isPresent() && element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress2().isPresent()){
                                        addressLabor = element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress1().get().toString()+" "+ element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress2().get().toString();
                                    }
                                    else{
                                        if(element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress1().isPresent()){
                                            addressLabor = element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress1().get().toString() +" "+ (element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress2().isPresent() ? element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress2().get().toString() : "");
                                        }
                                        else{
                                            addressLabor = element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress2().isPresent() ? element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getAddress2().get().toString() : "";
                                        }
                                    }
                                    paragraphs.add(setValue(220,186,addressLabor));
                                    //A3_3
                                    paragraphs.add(setValue(150,160, element.getLaborInsuranceOffice().getBasicInformation().getRepresentativeName().isPresent() ? element.getLaborInsuranceOffice().getBasicInformation().getRepresentativeName().get().v() : "" ));
                                    //A3_4
                                    paragraphs.add(setValue(150,130,element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getPhoneNumber().isPresent() ? element.getLaborInsuranceOffice().getBasicInformation().getStreetAddress().getPhoneNumber().get().v() : "" ));

                                }

                            }
                            break;
                        }
                        case DO_NOT_OUTPUT:{
                            //A2_6
                            //A3_1
                            break;
                        }
                    }
                }
                //A1_5
                JapaneseDate empInsHistStartDate = toJapaneseDate( GeneralDate.fromString(element.getEmpInsHist().getHistoryItem().get(0).start().toString().substring(0,10), "yyyy/MM/dd"));
                paragraphs.add(setValue(45,677,findEra(empInsHistStartDate.era())));
                //A1_6
                {
                    String value = empInsHistStartDate.year()+1+""+empInsHistStartDate.month()+""+empInsHistStartDate.day()+"";
                    detachText(79,677,value,6,paragraphs);
                }
                //A1_7
                if(element.getEmpInsReportSetting().getSubmitNameAtr() == PERSONAL_NAME){
                    paragraphs.add(setValue(45,586,element.getName() != null ? element.getName() : ""));
                    //A1_8
                    detachText(180,586,element.getNameKana() != null ? element.getNameKana(): "" ,20,paragraphs);

                }
                else{
                    paragraphs.add(setValue(45,586,element.getReportFullName() != null ? element.getReportFullName() : ""));
                    //A1_8
                    paragraphs.add(setValue(182,586,element.getReportFullNameKana() != null ? element.getReportFullNameKana() : ""));
                }
                //A1_9
                detachText(45,466,element.getFullName() != null ? element.getFullName() : "",28,paragraphs);

                //A1_10
                detachText(45,434,element.getFullNameKana() != null ? element.getFullNameKana(): "",12,paragraphs);

                //A2_1
                paragraphs.add(setValue(112,353,element.getOldName() != null ? element.getOldName() : ""));
                //A2_2
                paragraphs.add(setValue(112,367,element.getOldNameKana() != null ? element.getOldNameKana() : "" ));
                //A2_3
                Graph graph = new Graph(50, 50);
                paragraphs.add(graph);
                Circle rect = null;
                if(element.getGender() == 1){
                     rect = new Circle(317,45,8);
                }
                else {
                    rect = new Circle(345,45,8);
                }
                rect.getGraphInfo().setLineWidth(1f);
                rect.getGraphInfo().setColor(com.aspose.pdf.Color.getBlack());
                graph.getShapes().add(rect);
                //A2_4
                JapaneseDate birthDay = toJapaneseDate( GeneralDate.fromString(element.getBrithDay().substring(0,10), "yyyy/MM/dd"));
//                paragraphs.add(setValue(400,353,);
                Circle rect2 = null;
                switch (birthDay.era()){
                    case MEI : {
                         rect2 = new Circle(370,48,7);
                        break;
                    }
                    case SHOWA : {
                         rect2 = new Circle(392,48,7);
                        break;
                    }
                    case PEACE: {
                         rect2 = new Circle(392,40,7);
                        break;
                    }
                    case HEISEI:{
                         rect2 = new Circle(370,40,7);
                        break;
                    }
                    default:{
                        rect2 = new Circle(370,40,0);
                    }
                }
                rect2.getGraphInfo().setLineWidth(1f);
                rect2.getGraphInfo().setColor(com.aspose.pdf.Color.getBlack());
                graph.getShapes().add(rect2);
                  //A2_5
                {
                    JapaneseDate birthDayJapanCla = toJapaneseDate( GeneralDate.fromString(element.getBrithDay().substring(0,10), "yyyy/MM/dd"));
                    paragraphs.add(setValue(414,353,birthDayJapanCla.year()+""));
                    paragraphs.add(setValue(450,353,birthDayJapanCla.month()+""));
                    paragraphs.add(setValue(485,353,birthDayJapanCla.day()+""));
                }
                //A2_7
                {   JapaneseDate birthDayJapanCla = toJapaneseDate( GeneralDate.fromString(element.getBrithDay().substring(0,10), "yyyy/MM/dd"));
                    //lấy ví dụ  element.getChangeDate()
                    detachDate(441,280,birthDayJapanCla,paragraphs);
                }
                //A3_5
                JapaneseDate fillingDate = toJapaneseDate( GeneralDate.fromString(element.getFillingDate().substring(0,10), "yyyy/MM/dd"));

                detachDate(483,202,fillingDate,paragraphs);
                //index page
                indexPage = indexPage + 2;
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
            return "3";
        }
        if (SHOWA.equals(era)) {
            return "5";
        }
        if (HEISEI.equals(era)) {
            return "7";
        }
        if (PEACE.equals(era)) {
            return "9";
        }
        return "";
    }
    private JapaneseDate toJapaneseDate (GeneralDate date) {
        Optional<JapaneseEraName> era = this.adapter.getAllEras().eraOf(date);
        return new JapaneseDate(date, era.get());
    }
    private TextFragment setValue(int x, int y, String value) {
        TextFragment textFragment = new TextFragment(value);
        textFragment.setPosition(new Position(x, y));
        styleText(textFragment.getTextState());
        return textFragment;
    }

    private void styleText(TextFragmentState textFragmentState) {
        textFragmentState.setFont(FontRepository.findFont("MS-Gothic"));
        textFragmentState.setFontSize(16);
        textFragmentState.setForegroundColor(Color.getBlack());
    }
    private void detachText(int xRoot,int yRoot,String value, int numCells,Paragraphs paragraphs){
        if(value.length() > numCells){
            value = value.substring(0,numCells);
        }
        List<Character> lstValue = value.chars().mapToObj(i -> (char) i).collect(Collectors.toList());
        lstValue.forEach(e -> {

        });
        for(int i = 0 ; i < lstValue.size() ; i++){
            int pixel = xRoot + (17*i);
            paragraphs.add(setValue(pixel, yRoot ,lstValue.get(i).toString()));
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
    private void detachDate(int xRoot,int yRoot,JapaneseDate value,Paragraphs paragraphs){
        paragraphs.add(setValue(xRoot,yRoot,value.year()+1+""));
        paragraphs.add(setValue(xRoot+30,yRoot,value.month()+""));
        paragraphs.add(setValue(xRoot+60,yRoot,value.day()+""));
    }
    private void createCircle(Circle rect,Graph graph){
        rect.getGraphInfo().setLineWidth(1f);
        rect.getGraphInfo().setColor(com.aspose.pdf.Color.getBlack());
        graph.getShapes().add(rect);
    }
}
