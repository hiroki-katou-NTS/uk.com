package nts.uk.file.pr.infra.core.socinsurnoticreset;

import com.aspose.cells.Shape;
import com.aspose.cells.ShapeCollection;
import com.aspose.cells.Workbook;
import com.aspose.cells.WorksheetCollection;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.*;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.BusinessDivision;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpNameReport;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.RomajiNameNotiCreSetting;
import nts.uk.ctx.pr.shared.dom.familyinfo.empfamilysocialins.EmpFamilySocialIns;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInfor;
import nts.uk.shr.com.time.japanese.JapaneseEraName;
import nts.uk.shr.com.time.japanese.JapaneseErasAdapter;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;
import nts.uk.shr.com.time.japanese.JapaneseDate;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Stateless
public class RomajiNameNotiCreSetPDFAposeFileGenerator extends AsposeCellsReportGenerator implements RomajiNameNotiCreSetFileGenerator {

    private static final String TEMPLATE_FILE = "report/ローマ字氏名届_帳票テンプレート.xlsx";
    private static final String FILE_NAME = "ローマ字氏名届";

    @Inject
    private JapaneseErasAdapter adapter;
    @Override
    public void generate(FileGeneratorContext fileContext, List<RomajiNameNotification> data) {
        try {
            AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE);
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();


            reportContext.processDesigner();

            String sheetName = "INS";
            RomajiNameNotification romajiNameNotification = null ;

            for (int i  = 0; i < data.size() ; i ++){
                worksheets.get(worksheets.addCopy(0)).setName(sheetName + i);
                romajiNameNotification = data.get(i);
                if(romajiNameNotification.getPersonInfo() != null ){
                    //push data
                    this.pushData(worksheets,
                            romajiNameNotification.getPersonTarget(),
                            romajiNameNotification.getEmpFamilySocialIns(),
                            romajiNameNotification.getEmpBasicPenNumInfor(),
                            romajiNameNotification.getPersonInfo(),
                            romajiNameNotification.getFamilyMember(),
                            romajiNameNotification.getCompanyInfor(),
                            romajiNameNotification.getSocialInsuranceOffice(),
                            romajiNameNotification.getEmpNameReport(),
                            romajiNameNotification.getRomajiNameNotiCreSetting(),
                            romajiNameNotification.getDate(),
                            sheetName + i);
                }

            }

            worksheets.removeAt(0);
            reportContext.saveAsExcel(this.createNewFile(fileContext,
                    FILE_NAME + "_" + GeneralDateTime.now().toString("yyyyMMddHHmmss") + ".xlsx"));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private String pushDataCell(String stringPara, int i){
        return stringPara.length() > 0 ?  stringPara.split("")[i].toString() : "";
    }
    private void pushData(WorksheetCollection worksheet,
                          String personTarget,
                          EmpFamilySocialIns empFamilySocialIns,
                          EmpBasicPenNumInfor empBasicPenNumInfor,
                          PersonInfo personInfo,
                          FamilyMember familyMember,
                          CompanyInfor companyInfor,
                          SocialInsuranceOffice socialInsuranceOffice,
                          EmpNameReport empNameReport,
                          RomajiNameNotiCreSetting romajiNameNotiCreSetting,
                          GeneralDate date,
                          String i){
        try {

            //For the person
            if ( personTarget.equals("0")){

                //A1_1
                if (empBasicPenNumInfor != null ) {
                    for (int h = 0; h < empBasicPenNumInfor.getBasicPenNumber().get().toString().length() ; h++) {
                        worksheet.get(i).getCells().get(12, h+1).setValue(Objects.toString(pushDataCell(empBasicPenNumInfor.getBasicPenNumber().get().toString(), h),""));
                    }
                }

                //A1-2
                if ( personInfo != null) {
                    //get year
                    worksheet.get(i).getCells().get(11, 11).setValue(Objects.toString(pushDataCell( personInfo.getBirthday().substring(0,4), 0 ),  ""));
                    worksheet.get(i).getCells().get(11, 12).setValue(Objects.toString(pushDataCell( personInfo.getBirthday().substring(0,4), 1 ),  ""));
                    worksheet.get(i).getCells().get(11, 13).setValue(Objects.toString(pushDataCell( personInfo.getBirthday().substring(0,4), 2 ),  ""));
                    worksheet.get(i).getCells().get(11, 14).setValue(Objects.toString(pushDataCell( personInfo.getBirthday().substring(0,4), 3 ),  ""));

                    //get month
                    worksheet.get(i).getCells().get(11, 15).setValue(Objects.toString(pushDataCell( personInfo.getBirthday().substring(4,6), 0 ),  ""));
                    worksheet.get(i).getCells().get(11, 16).setValue(Objects.toString(pushDataCell( personInfo.getBirthday().substring(4,6), 1 ),  ""));

                    //get day
                    worksheet.get(i).getCells().get(11, 17).setValue(Objects.toString(pushDataCell( personInfo.getBirthday().substring(6,8), 0 ),  ""));
                    worksheet.get(i).getCells().get(11, 18).setValue(Objects.toString(pushDataCell( personInfo.getBirthday().substring(6,8), 1 ),  ""));

                    //A1_3 ~ A1_6
                    this.selectShapes(worksheet, personInfo.getGender() , i, "A1_3", "A1_4" );
                }

                worksheet.getRangeByName(i +"!A2_1" ).setValue(Objects.toString(personInfo != null ? personInfo.getPersonName():  ""));
                worksheet.getRangeByName(i +"!A2_2" ).setValue(Objects.toString(personInfo != null ? personInfo.getPersonName():  ""));
                worksheet.getRangeByName(i +"!A2_3" ).setValue(Objects.toString(personInfo != null ? personInfo.getPersonNameKana(): ""));
                worksheet.getRangeByName(i +"!A2_4" ).setValue(Objects.toString(personInfo != null ? personInfo.getPersonNameKana(): ""));
                worksheet.getRangeByName(i + "!A4_5").setValue(Objects.toString(empNameReport != null && empNameReport.getPersonalSet().getOther() == 1 ? empNameReport.getPersonalSet().getOtherReason(): ""));

                if( empNameReport != null) {
                    this.selectShapes(worksheet, empNameReport.getPersonalSet().getResidentCard().value , i, "A1_5", "A1_6" );
                    this.selectShapes(worksheet, empNameReport.getPersonalSet().getShortResident() , i, "A4_1", "A1_0" );
                    this.selectShapes(worksheet, empNameReport.getPersonalSet().getAddressOverseas() , i, "A4_2", "A1_0" );
                    this.selectShapes(worksheet, empNameReport.getPersonalSet().getListed() , i, "A4_3", "A1_0" );
                    this.selectShapes(worksheet, empNameReport.getPersonalSet().getOther() , i, "A4_4", "A1_0" );
                }

            } else {

                //A1_1
                if (empFamilySocialIns != null ) {
                    for (int h1 = 0; h1 < empFamilySocialIns.getFmBsPenNum().length() ; h1++) {
                        worksheet.get(i).getCells().get(12, h1+1).setValue(Objects.toString(pushDataCell(empFamilySocialIns.getFmBsPenNum(), h1),  ""));
                    }
                }

                //A1-2
                if ( familyMember != null ) {
                    //get year
                    worksheet.get(i).getCells().get(11, 11).setValue(pushDataCell(familyMember.getBirthday().substring(0,4), 0 ));
                    worksheet.get(i).getCells().get(11, 12).setValue(pushDataCell( familyMember.getBirthday().substring(0,4), 1 ));
                    worksheet.get(i).getCells().get(11, 13).setValue(pushDataCell( familyMember.getBirthday().substring(0,4), 2 ));
                    worksheet.get(i).getCells().get(11, 14).setValue(pushDataCell( familyMember.getBirthday().substring(0,4), 3 ));

                    //get month
                    worksheet.get(i).getCells().get(11, 15).setValue(pushDataCell( familyMember.getBirthday().substring(4,6), 0 ));
                    worksheet.get(i).getCells().get(11, 16).setValue(pushDataCell( familyMember.getBirthday().substring(4,6), 1 ));

                    //get day
                    worksheet.get(i).getCells().get(11, 17).setValue(Objects.toString(pushDataCell( familyMember.getBirthday().substring(6,8), 0 ),  ""));
                    worksheet.get(i).getCells().get(11, 18).setValue(Objects.toString(pushDataCell( familyMember.getBirthday().substring(6,8), 1 ),  ""));

                    //A1_3 ~ A1_6
                    this.selectShapes(worksheet, familyMember.getGender() , i, "A1_3", "A1_4" );
                }

                worksheet.getRangeByName(i +"!A2_1" ).setValue(Objects.toString(familyMember != null ? familyMember.getNameRomajiFull():  ""));
                worksheet.getRangeByName(i +"!A2_2" ).setValue(Objects.toString(familyMember != null ? familyMember.getNameRomajiFull():  ""));
                worksheet.getRangeByName(i +"!A2_3" ).setValue(Objects.toString(familyMember != null ? familyMember.getNameRomajiFull(): ""));
                worksheet.getRangeByName(i +"!A2_4" ).setValue(Objects.toString(familyMember != null ? familyMember.getNameRomajiFull(): ""));
                worksheet.getRangeByName(i + "!A4_5").setValue(Objects.toString(empNameReport != null && empNameReport.getSpouse().getOther() == 1 ? empNameReport.getSpouse().getOtherReason(): ""));

                if( empNameReport != null) {
                    this.selectShapes(worksheet, empNameReport.getSpouse().getResidentCard().value , i, "A1_5", "A1_6" );
                    this.selectShapes(worksheet, empNameReport.getSpouse().getShortResident() , i, "A4_1", "A1_0" );
                    this.selectShapes(worksheet, empNameReport.getSpouse().getAddressOverseas() , i, "A4_2", "A1_0" );
                    this.selectShapes(worksheet, empNameReport.getSpouse().getListed() , i, "A4_3", "A1_0" );
                    this.selectShapes(worksheet, empNameReport.getSpouse().getOther() , i, "A4_4", "A1_0" );
                }
            }

            if( romajiNameNotiCreSetting.getAddressOutputClass().equals(BusinessDivision.OUTPUT_COMPANY_NAME) || romajiNameNotiCreSetting.getAddressOutputClass().equals(BusinessDivision.OUTPUT_SIC_INSURES)){
                worksheet.getRangeByName(i + "!A3_1").setValue(Objects.toString(companyInfor != null ? "〒"+formatValue(companyInfor.getPostCd().toString()) : null));
                //worksheet.getRangeByName(i + "!A3_2").setValue(Objects.toString(companyInfor != null ? companyInfor.getAdd_1():  ""));
                worksheet.getRangeByName(i + "!A3_3").setValue(Objects.toString(companyInfor != null ? companyInfor.getAdd_1()+companyInfor.getAdd_2(): ""));
                worksheet.getRangeByName(i + "!A3_4").setValue(Objects.toString(companyInfor != null ?  companyInfor.getCompanyName(): ""));
                worksheet.getRangeByName(i + "!A3_5").setValue(Objects.toString(companyInfor != null ?  companyInfor.getRepname(): ""));
                worksheet.getRangeByName(i + "!A3_6").setValue(Objects.toString(companyInfor != null ? formatValue( companyInfor.getPhoneNum().toString()) : null));

            } else if (romajiNameNotiCreSetting.getAddressOutputClass().equals(BusinessDivision.DO_NOT_OUTPUT) ||
                    romajiNameNotiCreSetting.getAddressOutputClass().equals(BusinessDivision.DO_NOT_OUTPUT_BUSINESS)) {
                worksheet.getRangeByName(i + "!A3_1").setValue(Objects.toString(socialInsuranceOffice != null ? "〒"+formatValue(socialInsuranceOffice.getBasicInformation().getAddress().get().getPostalCode().get().toString()):""));
                //worksheet.getRangeByName(i + "!A3_2").setValue(Objects.toString(socialInsuranceOffice != null ?  socialInsuranceOffice.getBasicInformation().getAddress().get().getAddress1(): ""));
                worksheet.getRangeByName(i + "!A3_3").setValue(Objects.toString(socialInsuranceOffice != null ? socialInsuranceOffice.getBasicInformation().getAddress().get().getAddress1().get().toString()+socialInsuranceOffice.getBasicInformation().getAddress().get().getAddress2().get().toString():""));
                worksheet.getRangeByName(i + "!A3_4").setValue(Objects.toString(socialInsuranceOffice != null ? socialInsuranceOffice.getName():""));
                worksheet.getRangeByName(i + "!A3_5").setValue(Objects.toString(socialInsuranceOffice != null ?  socialInsuranceOffice.getBasicInformation().getRepresentativeName().get():""));
                worksheet.getRangeByName(i + "!A3_6").setValue(Objects.toString(socialInsuranceOffice != null ? formatValue(socialInsuranceOffice.getBasicInformation().getAddress().get().getPhoneNumber().get().toString()):""));
            }

            //A3_7
            JapaneseDate japaneseDate = toJapaneseDate(date);
            worksheet.getRangeByName(i +"!A3_7" ).setValue(japaneseDate);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private JapaneseDate toJapaneseDate (GeneralDate date) {
        Optional<JapaneseEraName> era = this.adapter.getAllEras().eraOf(date);
        return new JapaneseDate(date, era.get());
    }

    private void selectShapes(WorksheetCollection worksheets, int value, String sheetName, String option1, String option2){
        worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(value == 1 ? option2 : option1 ));
    }

    private String formatValue(String pc) {
        if (pc != null) {
            String[] list = pc.split("");
            StringBuffer st = new StringBuffer("");
            for (int i = 0; i < list.length; i++) {
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
}
