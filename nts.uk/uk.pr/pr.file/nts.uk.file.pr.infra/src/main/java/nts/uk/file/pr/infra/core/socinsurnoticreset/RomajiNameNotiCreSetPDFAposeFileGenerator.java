package nts.uk.file.pr.infra.core.socinsurnoticreset;

import com.aspose.cells.Workbook;
import com.aspose.cells.WorksheetCollection;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.*;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.BusinessDivision;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.RomajiNameNotiCreSetting;
import nts.uk.shr.com.time.japanese.JapaneseEraName;
import nts.uk.shr.com.time.japanese.JapaneseErasAdapter;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;
import nts.uk.shr.com.time.japanese.JapaneseDate;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;

@Stateless
public class RomajiNameNotiCreSetPDFAposeFileGenerator extends AsposeCellsReportGenerator implements RomajiNameNotiCreSetFileGenerator {

    private static final String TEMPLATE_FILE = "report/ローマ字氏名届_帳票テンプレート.xlsx";
    private static final String FILE_NAME = "ローマ字氏名届";

    @Inject
    private JapaneseErasAdapter adapter;
    @Override
    public void generate(FileGeneratorContext fileContext, RomajiNameNotification data ) {
        try {
            AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE);
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            reportContext.processDesigner();
            String sheetName = "INS";
            for (int i  = 0; i < data.getRomajiNameNotiCreSetExportList().size() ; i ++){
                worksheets.get(worksheets.addCopy(0)).setName(sheetName + i);
                RomajiNameNotiCreSetExport romajiNameNotiCreSetExport = data.getRomajiNameNotiCreSetExportList().get(i);
                    //push data
                    this.pushData(worksheets,
                            data.getPersonTarget(),
                            data.getDate(),
                            data.getFamilyMember(),
                            data.getPersonInfo(),
                            data.getCompanyInfor(),
                            data.getRomajiNameNotiCreSetting(),
                            romajiNameNotiCreSetExport,
                            sheetName + i);
            }

            worksheets.removeAt(0);
            reportContext.saveAsPdf(this.createNewFile(fileContext,
                    FILE_NAME + ".pdf"));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private String pushDataCell(String stringPara, int i){
        return stringPara.length() > 0 ?  stringPara.split("")[i].toString() : "";
    }

    private void pushData(WorksheetCollection worksheet,
                          String personTarget,
                          GeneralDate date,
                          FamilyMember familyMember,
                          PersonInfo personInfo,
                          CompanyInfor companyInfor,
                          RomajiNameNotiCreSetting romajiNameNotiCreSetting,
                          RomajiNameNotiCreSetExport romajiNameNotiCreSetExport,
                          String i){
        try {
            if ( personTarget.equals("0")) {
                this.pushName(romajiNameNotiCreSetExport.getBasicPenNumber() != null ? romajiNameNotiCreSetExport.getBasicPenNumber().toString() : null, worksheet, i, 11, 1);

                if ( personInfo != null) {
                    this.pushBirthDay(personInfo.getBirthday(), worksheet, i);
                    this.selectShapesRadio(worksheet, personInfo.getGender() , i, "A1_3","A1_4");
                }

                this.pushName(personInfo.getPersonNameRomaji(), worksheet, i, 14, 4);
                this.pushName(personInfo.getPersonNameKana(), worksheet, i, 13, 4);

                worksheet.get(i).getTextBoxes().get("A4_5").setText(Objects.toString(
                        romajiNameNotiCreSetExport.getPersonalSetOther() == 1 && romajiNameNotiCreSetExport.getPersonalOtherReason() != null ? romajiNameNotiCreSetExport.getPersonalOtherReason().toString(): ""));

                this.selectShapesRadio(worksheet, romajiNameNotiCreSetExport.getPersonalResidentCard() , i, "A1_5","A1_6" );
                this.selectShapes(worksheet, romajiNameNotiCreSetExport.getPersonalShortResident() , i, "A4_1" );
                this.selectShapes(worksheet, romajiNameNotiCreSetExport.getPersonalAddressOverseas(), i, "A4_2" );
                this.selectShapes(worksheet, romajiNameNotiCreSetExport.getPersonalSetListed() , i, "A4_3" );
                this.selectShapes(worksheet, romajiNameNotiCreSetExport.getPersonalSetOther() , i, "A4_4" );

            } else {

                this.pushName(romajiNameNotiCreSetExport.getFmBsPenNum() != null ? romajiNameNotiCreSetExport.getFmBsPenNum().toString() : null, worksheet, i, 11, 1);
                if ( familyMember != null ) {
                    this.pushBirthDay(familyMember.getBirthday(), worksheet, i);
                    this.selectShapesRadio(worksheet, familyMember.getGender() , i, "A1_3","A1_4");
                }

                this.pushName(familyMember.getFullName(), worksheet, i, 14, 4);
                this.pushName(familyMember.getFullNameKana(), worksheet, i, 13, 4);

                /*worksheet.getRangeByName(i + "!A4_5").setValue(Objects.toString(
                        romajiNameNotiCreSetExport.getSpouseSetOther() == 1 && romajiNameNotiCreSetExport.getSpouseOtherReason().toString() != null ? romajiNameNotiCreSetExport.getSpouseOtherReason().toString(): ""));
                 */
                worksheet.get(i).getTextBoxes().get("A4_5").setText(Objects.toString(
                        romajiNameNotiCreSetExport.getSpouseSetOther() == 1 && romajiNameNotiCreSetExport.getSpouseOtherReason() != null ? romajiNameNotiCreSetExport.getSpouseOtherReason().toString(): ""));
                this.selectShapesRadio(worksheet, romajiNameNotiCreSetExport.getSpouseResidentCard() , i, "A1_5","A1_6" );
                this.selectShapes(worksheet, romajiNameNotiCreSetExport.getSpouseShortResident(), i, "A4_1" );
                this.selectShapes(worksheet, romajiNameNotiCreSetExport.getSpouseAddressOverseas() , i, "A4_2" );
                this.selectShapes(worksheet, romajiNameNotiCreSetExport.getSpouseSetListed() , i, "A4_3" );
                this.selectShapes(worksheet,romajiNameNotiCreSetExport.getSpouseSetOther() , i, "A4_4" );
            }

            if( romajiNameNotiCreSetting.getAddressOutputClass().equals(BusinessDivision.OUTPUT_COMPANY_NAME)){
                worksheet.getRangeByName(i + "!A3_1").setValue(Objects.toString(companyInfor != null ? "〒"+formatPostCode(companyInfor.getPostCd().toString()) : null));
                worksheet.getRangeByName(i + "!A3_3").setValue(Objects.toString(companyInfor != null ? companyInfor.getAdd_1()+companyInfor.getAdd_2(): ""));
                worksheet.getRangeByName(i + "!A3_4").setValue(Objects.toString(companyInfor != null ?  companyInfor.getCompanyName(): ""));
                worksheet.getRangeByName(i + "!A3_5").setValue(Objects.toString(companyInfor != null ?  companyInfor.getRepname(): ""));
                worksheet.getRangeByName(i + "!A3_6").setValue(Objects.toString(companyInfor != null ? formatPhone( companyInfor.getPhoneNum().toString()) : null));

            } else if (romajiNameNotiCreSetting.getAddressOutputClass().equals(BusinessDivision.OUTPUT_SIC_INSURES)) {
                worksheet.getRangeByName(i + "!A3_1").setValue(Objects.toString("〒"+formatPostCode(romajiNameNotiCreSetExport.getPostalCode())));
                worksheet.getRangeByName(i + "!A3_3").setValue(Objects.toString(romajiNameNotiCreSetExport.getAddress1() + romajiNameNotiCreSetExport.getAddress2()));
                worksheet.getRangeByName(i + "!A3_4").setValue(Objects.toString(romajiNameNotiCreSetExport.getName()));
                worksheet.getRangeByName(i + "!A3_6").setValue(Objects.toString(formatPhone(romajiNameNotiCreSetExport.getPhoneNumber())));
                worksheet.getRangeByName(i + "!A3_5").setValue(Objects.toString(romajiNameNotiCreSetExport.getRepresentativeName() != null ? romajiNameNotiCreSetExport.getRepresentativeName():""));
            }

            JapaneseDate japaneseDate = toJapaneseDate(date);
            int y = japaneseDate.year() + 1;
            int m = japaneseDate.month();
            int d = japaneseDate.day();
            worksheet.getRangeByName(i +"!A3_7" ).setValue(japaneseDate.era() + String.valueOf(y) + "年" + String.valueOf(m) + "月" + String.valueOf(d) + "日");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private JapaneseDate toJapaneseDate (GeneralDate date) {
        Optional<JapaneseEraName> era = this.adapter.getAllEras().eraOf(date);
        return new JapaneseDate(date, era.get());
    }

    private void selectShapes(WorksheetCollection worksheets, Integer value, String sheetName, String option){
        if( value!= null && value.intValue() != 1){
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(option));
        }
    }

    private void selectShapesRadio(WorksheetCollection worksheets, Integer value, String sheetName, String option1, String option2){
        if (value == null )return;
        worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(value.intValue() != 1 ? option1 : option2));
    }

    private static String formatPostCode(String pc) {
        if (pc != null) {
            String[] list = pc.split("");
            StringBuffer st = new StringBuffer("");
            for (int i = 0; i < list.length; i++) {
                if("-".equals(list[i])){
                    return pc;
                }
                st.append(list[i]);
                if((i+1)%3==0 && list.length > st.length()) {
                    st.append("-");
                }
            }
            return st.toString();
        } else {
            return  null;
        }
    }

    /**
     * cut character -
     * @param pc
     * @return after cut sring
     */
    private static String cutChar(String pc) {
        String[] list = pc.split("");
        StringBuffer st = new StringBuffer("");
        for (int i = 0; i < list.length; i++) {
            if(!"-".equals(list[i])) {
                st.append(list[i]);
            }
        }
        return st.toString();
    }

    private String formatPhone(String pc) {
        if (pc != null) {
            String[] sub = pc.split("");
            String ch  = cutChar(pc);
            String[] ch_sub  = ch.split("");
            int num = pc.length() - ch.length();
            boolean first = true;
            StringBuffer st =  new StringBuffer("");

            //case 2 "-"
            if(num == 2) {
                for (int i = 0; i < sub.length; i++) {
                    if("-".equals(sub[i]) && first) {
                        sub[i] = "(";
                        first = false;
                    } else if ("-".equals(sub[i]) && !first) {
                        sub[i] = ")";
                    }
                    st.append(sub[i]);
                }
            } else {
                for (int i = 0; i < ch_sub.length; i++) {
                    if (ch_sub.length >= 7 && i==3) {
                        ch_sub[i] = "(";
                    }

                    if (ch_sub.length >= 7 && i==7) {
                        ch_sub[i] = ")";
                    }
                    st.append(ch_sub[i]);
                }
            }
            return st.toString();
        } else {
            return  null;
        }
    }

    private void pushBirthDay(String birthDate, WorksheetCollection worksheet, String i ){
        worksheet.get(i).getCells().get(11, 11).setValue(Objects.toString(pushDataCell( birthDate.substring(0,4), 0 ),  ""));
        worksheet.get(i).getCells().get(11, 12).setValue(Objects.toString(pushDataCell( birthDate.substring(0,4), 1 ),  ""));
        worksheet.get(i).getCells().get(11, 13).setValue(Objects.toString(pushDataCell( birthDate.substring(0,4), 2 ),  ""));
        worksheet.get(i).getCells().get(11, 14).setValue(Objects.toString(pushDataCell( birthDate.substring(0,4), 3 ),  ""));
        worksheet.get(i).getCells().get(11, 15).setValue(Objects.toString(pushDataCell( birthDate.substring(5,7), 0 ),  ""));
        worksheet.get(i).getCells().get(11, 16).setValue(Objects.toString(pushDataCell( birthDate.substring(5,7), 1 ),  ""));
        worksheet.get(i).getCells().get(11, 17).setValue(Objects.toString(pushDataCell( birthDate.substring(8), 0 ),  ""));
        worksheet.get(i).getCells().get(11, 18).setValue(Objects.toString(pushDataCell( birthDate.substring(8), 1 ),  ""));
    }

    private void pushName(String name, WorksheetCollection worksheet, String i, int row, int column) {
        if (name == null) {
            return;
        }
        for (int k = 0; k < name.length(); k++) {
            worksheet.get(i).getCells().get(row, column + k).setValue(name.charAt(k));
        }
    }
}