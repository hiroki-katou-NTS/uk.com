package nts.uk.file.pr.infra.core.socinsurnoticreset;

import com.aspose.cells.Workbook;
import com.aspose.cells.WorksheetCollection;
import nts.arc.error.BusinessException;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.EmpAddChangeInfoExport;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.EmpAddChangeInfoFileGenerator;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.EmpAddChangeInforData;
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
public class EmpAddChangeInfoPDFAposeFileGenerator extends AsposeCellsReportGenerator implements EmpAddChangeInfoFileGenerator {
    private static final String TEMPLATE_FILE_1 = "report/被保険者住所変更届.xlsx";
    private static final String TEMPLATE_FILE_2 = "report/国民年金第３号被保険者住所変更届.xlsx";
    private static final String FILE_NAME = "被保険者住所変更届";
    private static final String SHOWA = "昭和";
    private static final String HEISEI = "平成";
    private static final String PEACE = "令和";
    private static final String TYPE_DATE = "yyMMdd";

    @Inject
    private JapaneseErasAdapter adapter;

    @Override
    public void generate(FileGeneratorContext fileContext, EmpAddChangeInforData data) {
       try {
           AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE_1);
           Workbook workbook = reportContext.getWorkbook();
           WorksheetCollection worksheets = workbook.getWorksheets();

           AsposeCellsReportContext reportContext2 = this.createContext(TEMPLATE_FILE_2);
           Workbook workbook2 = reportContext2.getWorkbook();
           WorksheetCollection worksheets2 = workbook2.getWorksheets();

           worksheets.add("INH").copy(worksheets2.get(0));
           reportContext2.processDesigner();
           String sheetName1 = "INS";
           String sheetName2 = "INH";

           if (data.getEmpAddChangeInfoExportList().isEmpty()) {
               throw new BusinessException("Msg_37");
           }
           for (int i  = 0; i < data.getEmpAddChangeInfoExportList().size() ; i ++) {
               worksheets.get(worksheets.addCopy(0)).setName(sheetName1 + i);
               EmpAddChangeInfoExport empAddChangeInfoExport = this.checkHide(data.getEmpAddChangeInfoExportList().get(i));
               if(empAddChangeInfoExport.getPersonAddChangeDate() != null) {
                   //被保険者住所変更届
                   this.pushBusCode(worksheets, empAddChangeInfoExport,sheetName1 + i);
                   this.pushDataCommon(worksheets, empAddChangeInfoExport, data.getBaseDate(), sheetName1 + i);
               }

               if(empAddChangeInfoExport.getSpouseAddChangeDate() != null && empAddChangeInfoExport.isEmpPenInsurance()){
                   //国民年金第３号被保険者住所変更届
                   worksheets.get(worksheets.addCopy(1)).setName(sheetName2 + i);
                   this.pushDataCommon(worksheets, empAddChangeInfoExport, data.getBaseDate(), sheetName2 + i);
               }
           }

           worksheets.removeAt(1);
           worksheets.removeAt(0);
           reportContext.saveAsPdf(this.createNewFile(fileContext,FILE_NAME + ".pdf"));
       }catch (Exception e){
           throw new RuntimeException(e);
       }
    }

    private EmpAddChangeInfoExport checkHide(EmpAddChangeInfoExport export) {
        //hide A3_1 被保険者住所変更届
        if((export.getPersonAddChangeDate() != null && export.getSpouseAddChangeDate() == null)
                || (export.getPersonAddChangeDate() != null && export.isHealthInsurance() && !export.isEmpPenInsurance())) {
            export.setInsuredLivingTogether(false);
            export.setFmBsPenNum(null);
            export.setBirthDateF(null);
            export.setNameKanaF(null);
            export.setFullNameF(null);
            export.setPostalCodeF(null);
            export.setAdd1KanaF(null);
            export.setAdd2KanaF(null);
            export.setAdd1F(null);
            export.setAdd2F(null);
            export.setStartDateF(null);
            export.setAdd1BeforeChangeF(null);
            export.setAdd2BeforeChangeF(null);
            export.setSpouseLivingAbroadAtr(0);
            export.setSpouseOtherAtr(0);
            export.setSpouseResidenceOtherResidentAtr(0);
            export.setSpouseShortResidentAtr(0);
            export.setSpouseOtherReason(null);
        }

        //hide A1 国民年金第３号被保険者住所変更届
        if(export.getSpouseAddChangeDate() != null && export.isEmpPenInsurance() && export.getPersonAddChangeDate() == null) {
            export.setPostCodePs(null);
            export.setAdd1KanaPs(null);
            export.setAdd2KanaPs(null);
            export.setAdd1Ps(null);
            export.setAdd2Ps(null);
            export.setAdd1BeforeChangePs(null);
            export.setAdd2BeforeChangePs(null);
            export.setStartDatePs(null);
            export.setShortResidentAtr(0);
            export.setLivingAbroadAtr(0);
            export.setResidenceOtherResidentAtr(0);
            export.setOtherAtr(0);
            export.setOtherReason(null);
        }

        return export;
    }

    private void fillByCell(WorksheetCollection worksheet, String sheetName, String ps, String data, int kt ){
        worksheet.getRangeByName(sheetName + "!"+ ps).setValue(data != null && data.length() > kt ? data.charAt(kt) : "");
    }

    private String fillAddress (String add1, String add2) {
        StringBuffer add = new StringBuffer("");
        if (add1 != null){
            add.append(add1);
        }
        if (add2 != null){
            add.append(add2);
        }
        return add.toString();
    }

    private static String cutSpace(String name, int ps) {
        if (name == null || name.length() == 0) return "";
        String[] listF = name.split("　", 2);
        if(listF.length == 1) {
            if(ps==1) return listF[0].length() > 4 ? listF[0].substring(0,4) : listF[0];
            if(ps==2) return "";
        }
        if(listF.length > 1) {
            if(ps==1) return listF[0].length() > 4 ? listF[0].substring(0,4) : listF[0];
            if(ps==2) return listF[1].length() > 4 ? listF[1].substring(0,4) : listF[1];
        }
        return "";
    }

    private JapaneseDate toJapaneseDate (GeneralDate date) {
        if(date == null) return null;
        Optional<JapaneseEraName> era = this.adapter.getAllEras().eraOf(date);
        return new JapaneseDate(date, era.get());
    }

    private void fillEraItem(WorksheetCollection worksheets, String sheetName, GeneralDate date, String op1, String op2, String op3){
       if(date!= null) {
           JapaneseDate dateJP = this.toJapaneseDate(date);
           if (SHOWA.equals(dateJP.era())){
               worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(op2));
               worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(op3));
           } else if (HEISEI.equals(dateJP.era())){
               worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(op1));
               worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(op3));
           } else if (PEACE.equals(dateJP.era())){
               worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(op1));
               worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(op2));
           } else {
               worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(op2));
               worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(op1));
               worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(op3));
           }
       } else {
           worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(op2));
           worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(op1));
           worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(op3));
       }
    }

    private static String formatTooLongText(String text, int maxByteAllowed) throws UnsupportedEncodingException {
        if (text.getBytes("Shift_JIS").length < maxByteAllowed) return text;
        int textLength = text.length();
        int subLength = 0;
        for (int i = 0; i < textLength; i++) {
            subLength++;
            if (text.substring(0, subLength).getBytes("Shift_JIS").length > maxByteAllowed) break;
        }
        return text.substring(0, subLength);
    }

    private void pushBusCode(WorksheetCollection worksheet,
                             EmpAddChangeInfoExport empAddChangeInfoExport,
                             String i){
        try {
            worksheet.getRangeByName(i + "!B2_3").setValue(Objects.toString(empAddChangeInfoExport.getHealInsurNumber(), ""));

            this.fillByCell(worksheet , i,"B2_2_1", empAddChangeInfoExport.getBusinessEstCode2(),0 );
            this.fillByCell(worksheet , i,"B2_2_2", empAddChangeInfoExport.getBusinessEstCode2(),1 );
            this.fillByCell(worksheet , i,"B2_2_3", empAddChangeInfoExport.getBusinessEstCode2(),2 );
            this.fillByCell(worksheet , i,"B2_2_4", empAddChangeInfoExport.getBusinessEstCode2(),3 );

            this.fillByCell(worksheet , i,"B2_1_1", empAddChangeInfoExport.getBusinessEstCode1(),0 );
            this.fillByCell(worksheet , i,"B2_1_2", empAddChangeInfoExport.getBusinessEstCode1(),1 );

            if(empAddChangeInfoExport.isHealthInsurance() && !empAddChangeInfoExport.isEmpPenInsurance()) {
                //worksheet.getRangeByName(i + "!B1_1").setValue("健康保険");
                worksheet.get(i).getShapes().remove(worksheet.get(i).getShapes().get("C1_2"));
            }
            if(!empAddChangeInfoExport.isHealthInsurance() && empAddChangeInfoExport.isEmpPenInsurance()) {
                //worksheet.getRangeByName(i + "!B1_2").setValue("厚生年金保険");
                worksheet.get(i).getShapes().remove(worksheet.get(i).getShapes().get("C1_1"));
            }



        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void pushDataCommon(WorksheetCollection worksheet,
                          EmpAddChangeInfoExport empAddChangeInfoExport,
                          GeneralDate baseDate,
                          String i){
        try {

            //A1_1
            this.fillByCell(worksheet , i,"A1_1_1", empAddChangeInfoExport.getBasicPenNumber(),0 );
            this.fillByCell(worksheet , i,"A1_1_2", empAddChangeInfoExport.getBasicPenNumber(),1 );
            this.fillByCell(worksheet , i,"A1_1_3", empAddChangeInfoExport.getBasicPenNumber(),2 );
            this.fillByCell(worksheet , i,"A1_1_4", empAddChangeInfoExport.getBasicPenNumber(),3 );
            this.fillByCell(worksheet , i,"A1_1_5", empAddChangeInfoExport.getBasicPenNumber(),4 );
            this.fillByCell(worksheet , i,"A1_1_6", empAddChangeInfoExport.getBasicPenNumber(),5 );
            this.fillByCell(worksheet , i,"A1_1_7", empAddChangeInfoExport.getBasicPenNumber(),6 );
            this.fillByCell(worksheet , i,"A1_1_8", empAddChangeInfoExport.getBasicPenNumber(),7 );
            this.fillByCell(worksheet , i,"A1_1_9", empAddChangeInfoExport.getBasicPenNumber(),8 );
            this.fillByCell(worksheet , i,"A1_1_10", empAddChangeInfoExport.getBasicPenNumber(),9 );
            this.fillByCell(worksheet , i,"A1_1_11", empAddChangeInfoExport.getBasicPenNumber(),10 );
            this.fillByCell(worksheet , i,"A1_1_12", empAddChangeInfoExport.getBasicPenNumber(),11 );

            //A2_2
            this.fillByCell(worksheet , i,"A2_2_1", empAddChangeInfoExport.getFmBsPenNum(),0 );
            this.fillByCell(worksheet , i,"A2_2_2", empAddChangeInfoExport.getFmBsPenNum(),1 );
            this.fillByCell(worksheet , i,"A2_2_3", empAddChangeInfoExport.getFmBsPenNum(),2 );
            this.fillByCell(worksheet , i,"A2_2_4", empAddChangeInfoExport.getFmBsPenNum(),3 );
            this.fillByCell(worksheet , i,"A2_2_5", empAddChangeInfoExport.getFmBsPenNum(),4 );
            this.fillByCell(worksheet , i,"A2_2_6", empAddChangeInfoExport.getFmBsPenNum(),5 );
            this.fillByCell(worksheet , i,"A2_2_7", empAddChangeInfoExport.getFmBsPenNum(),6 );
            this.fillByCell(worksheet , i,"A2_2_8", empAddChangeInfoExport.getFmBsPenNum(),7 );
            this.fillByCell(worksheet , i,"A2_2_9", empAddChangeInfoExport.getFmBsPenNum(),8 );
            this.fillByCell(worksheet , i,"A2_2_10", empAddChangeInfoExport.getFmBsPenNum(),9 );
            this.fillByCell(worksheet , i,"A2_2_11", empAddChangeInfoExport.getFmBsPenNum(),10 );
            this.fillByCell(worksheet , i,"A2_2_12", empAddChangeInfoExport.getFmBsPenNum(),11 );

            worksheet.getRangeByName(i + "!A1_2").setValue(Objects.toString(empAddChangeInfoExport.getNameKanaPs() != null ?  cutSpace(empAddChangeInfoExport.getNameKanaPs(), 1): ""));
            worksheet.getRangeByName(i + "!A1_3").setValue(Objects.toString(empAddChangeInfoExport.getNameKanaPs() != null ?  cutSpace(empAddChangeInfoExport.getNameKanaPs(), 2): ""));
            worksheet.getRangeByName(i + "!A1_4").setValue(Objects.toString(empAddChangeInfoExport.getFullNamePs() != null ?  cutSpace(empAddChangeInfoExport.getFullNamePs(), 1): ""));
            worksheet.getRangeByName(i + "!A1_5").setValue(Objects.toString(empAddChangeInfoExport.getFullNamePs() != null ?  cutSpace(empAddChangeInfoExport.getFullNamePs(), 2): ""));

            worksheet.getRangeByName(i + "!A2_6").setValue(Objects.toString(empAddChangeInfoExport.getNameKanaF() != null ?  cutSpace(empAddChangeInfoExport.getNameKanaF(), 1): ""));
            worksheet.getRangeByName(i + "!A2_7").setValue(Objects.toString(empAddChangeInfoExport.getNameKanaF() != null ?  cutSpace(empAddChangeInfoExport.getNameKanaF(), 2): ""));
            worksheet.getRangeByName(i + "!A2_8").setValue(Objects.toString(empAddChangeInfoExport.getFullNameF() != null ?  cutSpace(empAddChangeInfoExport.getFullNameF(), 1): ""));
            worksheet.getRangeByName(i + "!A2_9").setValue(Objects.toString(empAddChangeInfoExport.getFullNameF() != null ?  cutSpace(empAddChangeInfoExport.getFullNameF(), 2): ""));

            this.fillByCell(worksheet , i,"A1_6_1", empAddChangeInfoExport.getPostCodePs(),0 );
            this.fillByCell(worksheet , i,"A1_6_2", empAddChangeInfoExport.getPostCodePs(),1 );
            this.fillByCell(worksheet , i,"A1_6_3", empAddChangeInfoExport.getPostCodePs(),2 );
            this.fillByCell(worksheet , i,"A1_6_4", empAddChangeInfoExport.getPostCodePs(),3 );
            this.fillByCell(worksheet , i,"A1_6_5", empAddChangeInfoExport.getPostCodePs(),4 );
            this.fillByCell(worksheet , i,"A1_6_6", empAddChangeInfoExport.getPostCodePs(),5 );
            this.fillByCell(worksheet , i,"A1_6_7", empAddChangeInfoExport.getPostCodePs(),6 );

            worksheet.getRangeByName(i + "!A1_7").setValue(this.fillAddress(empAddChangeInfoExport.getAdd1KanaPs(), empAddChangeInfoExport.getAdd2KanaPs()));
            worksheet.getRangeByName(i + "!A1_8").setValue(this.fillAddress(empAddChangeInfoExport.getAdd1Ps(), empAddChangeInfoExport.getAdd2Ps()));
            worksheet.getRangeByName(i + "!A1_9").setValue(this.fillAddress(empAddChangeInfoExport.getAdd1BeforeChangePs(), empAddChangeInfoExport.getAdd2BeforeChangePs()));

            JapaneseDate sDayPs = this.toJapaneseDate(empAddChangeInfoExport.getStartDatePs());
            this.fillByCell(worksheet , i,"A1_10_1", empAddChangeInfoExport.getStartDatePs() != null ? NotificationOfLossInsPDFAposeFileGenerator.convertJpDate(sDayPs) : "",0 );
            this.fillByCell(worksheet , i,"A1_10_2", empAddChangeInfoExport.getStartDatePs() != null ? NotificationOfLossInsPDFAposeFileGenerator.convertJpDate(sDayPs) : "",1 );
            this.fillByCell(worksheet , i,"A1_10_3", empAddChangeInfoExport.getStartDatePs() != null ? NotificationOfLossInsPDFAposeFileGenerator.convertJpDate(sDayPs) : "",2 );
            this.fillByCell(worksheet , i,"A1_10_4", empAddChangeInfoExport.getStartDatePs() != null ? NotificationOfLossInsPDFAposeFileGenerator.convertJpDate(sDayPs) : "",3 );
            this.fillByCell(worksheet , i,"A1_10_5", empAddChangeInfoExport.getStartDatePs() != null ? NotificationOfLossInsPDFAposeFileGenerator.convertJpDate(sDayPs) : "",4 );
            this.fillByCell(worksheet , i,"A1_10_6", empAddChangeInfoExport.getStartDatePs() != null ? NotificationOfLossInsPDFAposeFileGenerator.convertJpDate(sDayPs) : "",5 );

            //A1_11 ~ A1_15
            RomajiNameNotiCreSetPDFAposeFileGenerator.selectShapes(worksheet, empAddChangeInfoExport.getShortResidentAtr() == null ? 0 :  empAddChangeInfoExport.getShortResidentAtr(), i, "A4_111" );
            RomajiNameNotiCreSetPDFAposeFileGenerator.selectShapes(worksheet, empAddChangeInfoExport.getResidenceOtherResidentAtr() == null ?  0 : empAddChangeInfoExport.getResidenceOtherResidentAtr(), i, "A4_112" );
            RomajiNameNotiCreSetPDFAposeFileGenerator.selectShapes(worksheet, empAddChangeInfoExport.getLivingAbroadAtr() == null ? 0 : empAddChangeInfoExport.getLivingAbroadAtr() , i, "A4_113" );
            RomajiNameNotiCreSetPDFAposeFileGenerator.selectShapes(worksheet, empAddChangeInfoExport.getOtherAtr() == null ? 0 : empAddChangeInfoExport.getOtherAtr(), i, "A4_114" );

            worksheet.get(i).getTextBoxes().get("A1_15").setText(Objects.toString(empAddChangeInfoExport.getOtherAtr()!= null &&
                    empAddChangeInfoExport.getOtherAtr() == 1 && empAddChangeInfoExport.getOtherReason() != null ? "(" + empAddChangeInfoExport.getOtherReason() + ")": "（        ）"));

            this.fillEraItem(worksheet, i, empAddChangeInfoExport.getBirthDatePs(), "A1_51", "A1_52","A1_53");
            JapaneseDate bDayPs = this.toJapaneseDate(empAddChangeInfoExport.getBirthDatePs());
            JapaneseDate bDayF  = this.toJapaneseDate(empAddChangeInfoExport.getBirthDateF());

            this.fillByCell(worksheet , i,"A1_54_1", empAddChangeInfoExport.getBirthDatePs() != null ? NotificationOfLossInsPDFAposeFileGenerator.convertJpDate(bDayPs) : "",0);
            this.fillByCell(worksheet , i,"A1_54_2", empAddChangeInfoExport.getBirthDatePs() != null ? NotificationOfLossInsPDFAposeFileGenerator.convertJpDate(bDayPs) : "",1 );
            this.fillByCell(worksheet , i,"A1_54_3", empAddChangeInfoExport.getBirthDatePs() != null ? NotificationOfLossInsPDFAposeFileGenerator.convertJpDate(bDayPs) : "",2 );
            this.fillByCell(worksheet , i,"A1_54_4", empAddChangeInfoExport.getBirthDatePs() != null ? NotificationOfLossInsPDFAposeFileGenerator.convertJpDate(bDayPs) : "",3 );
            this.fillByCell(worksheet , i,"A1_54_5", empAddChangeInfoExport.getBirthDatePs() != null ? NotificationOfLossInsPDFAposeFileGenerator.convertJpDate(bDayPs) : "",4 );
            this.fillByCell(worksheet , i,"A1_54_6", empAddChangeInfoExport.getBirthDatePs() != null ? NotificationOfLossInsPDFAposeFileGenerator.convertJpDate(bDayPs) : "",5 );

            this.fillEraItem(worksheet, i, empAddChangeInfoExport.getBirthDateF(), "A2_3", "A2_4","A2_41");
            this.fillByCell(worksheet , i,"A2_5_1", empAddChangeInfoExport.getBirthDateF()!= null ? NotificationOfLossInsPDFAposeFileGenerator.convertJpDate(bDayF) : "",0 );
            this.fillByCell(worksheet , i,"A2_5_2", empAddChangeInfoExport.getBirthDateF()!= null ? NotificationOfLossInsPDFAposeFileGenerator.convertJpDate(bDayF) : "",1 );
            this.fillByCell(worksheet , i,"A2_5_3", empAddChangeInfoExport.getBirthDateF()!= null ? NotificationOfLossInsPDFAposeFileGenerator.convertJpDate(bDayF) : "",2 );
            this.fillByCell(worksheet , i,"A2_5_4", empAddChangeInfoExport.getBirthDateF()!= null ? NotificationOfLossInsPDFAposeFileGenerator.convertJpDate(bDayF) : "",3 );
            this.fillByCell(worksheet , i,"A2_5_5", empAddChangeInfoExport.getBirthDateF()!= null ? NotificationOfLossInsPDFAposeFileGenerator.convertJpDate(bDayF) : "",4 );
            this.fillByCell(worksheet , i,"A2_5_6", empAddChangeInfoExport.getBirthDateF()!= null ? NotificationOfLossInsPDFAposeFileGenerator.convertJpDate(bDayF) : "",5 );

            this.fillByCell(worksheet , i,"A2_10_1", empAddChangeInfoExport.getPostalCodeF(),0 );
            this.fillByCell(worksheet , i,"A2_10_2", empAddChangeInfoExport.getPostalCodeF(),1 );
            this.fillByCell(worksheet , i,"A2_10_3", empAddChangeInfoExport.getPostalCodeF(),2 );
            this.fillByCell(worksheet , i,"A2_10_4", empAddChangeInfoExport.getPostalCodeF(),3 );
            this.fillByCell(worksheet , i,"A2_10_5", empAddChangeInfoExport.getPostalCodeF(),4 );
            this.fillByCell(worksheet , i,"A2_10_6", empAddChangeInfoExport.getPostalCodeF(),5 );
            this.fillByCell(worksheet , i,"A2_10_7", empAddChangeInfoExport.getPostalCodeF(),6 );

            worksheet.getRangeByName(i + "!A2_11").setValue(this.fillAddress(empAddChangeInfoExport.getAdd1KanaF(), empAddChangeInfoExport.getAdd2KanaF()));
            worksheet.getRangeByName(i + "!A2_12").setValue(this.fillAddress(empAddChangeInfoExport.getAdd1F(), empAddChangeInfoExport.getAdd2F()));

            JapaneseDate sDayF = this.toJapaneseDate(empAddChangeInfoExport.getStartDateF());
            this.fillByCell(worksheet , i,"A2_13_1", empAddChangeInfoExport.getStartDateF()!= null ? NotificationOfLossInsPDFAposeFileGenerator.convertJpDate(sDayF): "",0 );
            this.fillByCell(worksheet , i,"A2_13_2", empAddChangeInfoExport.getStartDateF()!= null ? NotificationOfLossInsPDFAposeFileGenerator.convertJpDate(sDayF): "",1 );
            this.fillByCell(worksheet , i,"A2_13_3", empAddChangeInfoExport.getStartDateF()!= null ? NotificationOfLossInsPDFAposeFileGenerator.convertJpDate(sDayF): "",2 );
            this.fillByCell(worksheet , i,"A2_13_4", empAddChangeInfoExport.getStartDateF()!= null ? NotificationOfLossInsPDFAposeFileGenerator.convertJpDate(sDayF): "",3 );
            this.fillByCell(worksheet , i,"A2_13_5", empAddChangeInfoExport.getStartDateF()!= null ? NotificationOfLossInsPDFAposeFileGenerator.convertJpDate(sDayF): "",4 );
            this.fillByCell(worksheet , i,"A2_13_6", empAddChangeInfoExport.getStartDateF()!= null ? NotificationOfLossInsPDFAposeFileGenerator.convertJpDate(sDayF): "",5 );

            worksheet.getRangeByName(i + "!A2_14").setValue(this.fillAddress(empAddChangeInfoExport.getAdd1BeforeChangeF(), empAddChangeInfoExport.getAdd2BeforeChangeF()));

            //A2_15 ~ A2_19
            RomajiNameNotiCreSetPDFAposeFileGenerator.selectShapes(worksheet, empAddChangeInfoExport.getSpouseShortResidentAtr() == null ? 0 : empAddChangeInfoExport.getSpouseShortResidentAtr(), i, "A4_221" );
            RomajiNameNotiCreSetPDFAposeFileGenerator.selectShapes(worksheet, empAddChangeInfoExport.getSpouseResidenceOtherResidentAtr() == null ? 0 : empAddChangeInfoExport.getSpouseResidenceOtherResidentAtr() , i, "A4_222" );
            RomajiNameNotiCreSetPDFAposeFileGenerator.selectShapes(worksheet, empAddChangeInfoExport.getSpouseLivingAbroadAtr()  == null ? 0 : empAddChangeInfoExport.getSpouseLivingAbroadAtr(), i, "A4_223" );
            RomajiNameNotiCreSetPDFAposeFileGenerator.selectShapes(worksheet, empAddChangeInfoExport.getSpouseOtherAtr()  == null ? 0 : empAddChangeInfoExport.getSpouseOtherAtr(), i, "A4_224" );

            worksheet.get(i).getTextBoxes().get("A2_19").setText(Objects.toString(empAddChangeInfoExport.getSpouseOtherAtr() != null &&
                    empAddChangeInfoExport.getSpouseOtherAtr() == 1 && empAddChangeInfoExport.getSpouseOtherReason() != null ? "("+ empAddChangeInfoExport.getSpouseOtherReason() + ")": "（        ）"));

            RomajiNameNotiCreSetPDFAposeFileGenerator.selectShapes(worksheet, empAddChangeInfoExport.isInsuredLivingTogether() ? 1 : 0, i, "A4_1111" );

            JapaneseDate japaneseDate = toJapaneseDate(baseDate);
            if(japaneseDate != null ){
                int y = japaneseDate.year() + 1;
                int m = japaneseDate.month();
                int d = japaneseDate.day();
                worksheet.getRangeByName(i + "!A3_1" ).setValue(japaneseDate.era() + String.valueOf(y) + "年" + String.valueOf(m) + "月" + String.valueOf(d) + "日提出");
            }
            worksheet.getRangeByName(i + "!A3_2").setValue(this.formatTooLongText(this.fillAddress(empAddChangeInfoExport.getAddress1(), empAddChangeInfoExport.getAddress2()), 27));
            worksheet.getRangeByName(i + "!A3_3").setValue(Objects.toString(empAddChangeInfoExport.getBussinessName(), ""));
            worksheet.getRangeByName(i + "!A3_4").setValue(Objects.toString(empAddChangeInfoExport.getReferenceName(), ""));
            worksheet.getRangeByName(i + "!A3_5").setValue(Objects.toString(empAddChangeInfoExport.getPhoneNumber()!= null && empAddChangeInfoExport.getPhoneNumber().length() > 0?  RomajiNameNotiCreSetPDFAposeFileGenerator.formatPhone( empAddChangeInfoExport.getPhoneNumber(), 1) + "(" + RomajiNameNotiCreSetPDFAposeFileGenerator.formatPhone( empAddChangeInfoExport.getPhoneNumber(), 2) +")" + RomajiNameNotiCreSetPDFAposeFileGenerator.formatPhone( empAddChangeInfoExport.getPhoneNumber(), 3): "", ""));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
