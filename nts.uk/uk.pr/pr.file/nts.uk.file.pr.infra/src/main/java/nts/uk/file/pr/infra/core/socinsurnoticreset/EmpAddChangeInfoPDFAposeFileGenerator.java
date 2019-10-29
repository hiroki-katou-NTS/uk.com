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
import java.util.Objects;
import java.util.Optional;

@Stateless
public class EmpAddChangeInfoPDFAposeFileGenerator extends AsposeCellsReportGenerator implements EmpAddChangeInfoFileGenerator {
    private static final String TEMPLATE_FILE_2 = "report/国民年金第３号被保険者住所変更届.xlsx";
    private static final String TEMPLATE_FILE_1 = "report/被保険者住所変更届.xlsx";
    private static final String FILE_NAME = "被保険者住所変更届";
    private static final String SHOWA = "昭和";
    private static final String HEISEI = "平成";
    private static final String PEACE = "令和";
    private static final String TYPE_DATE = "YYMMDD";

    @Inject
    private JapaneseErasAdapter adapter;

    @Override
    public void generate(FileGeneratorContext fileContext, EmpAddChangeInforData data) {
       try {
           AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE_1);
           Workbook workbook = reportContext.getWorkbook();
           WorksheetCollection worksheets = workbook.getWorksheets();

           AsposeCellsReportContext reportContext2 = this.createContext(TEMPLATE_FILE_1);
           Workbook workbook2 = reportContext2.getWorkbook();
           WorksheetCollection worksheets2 = workbook2.getWorksheets();
           reportContext2.processDesigner();

           String sheetName = "INS";

           if (data.getEmpAddChangeInfoExportList().isEmpty()) {
               throw new BusinessException("Msg_37");
           }

           for (int i  = 0; i < data.getEmpAddChangeInfoExportList().size() ; i ++){
               worksheets.get(worksheets.addCopy(0)).setName(sheetName + i);
               EmpAddChangeInfoExport empAddChangeInfoExport = data.getEmpAddChangeInfoExportList().get(i);
               if(empAddChangeInfoExport.getPersonAddChangeDate() != null) {
                   //条件を満たす対象者のデータをもとに「被保険者住所変更届」を印刷する
                   this.pushData(worksheets, empAddChangeInfoExport,sheetName + i);
               }

               if(empAddChangeInfoExport.getSpouseAddChangeDate() != null && empAddChangeInfoExport.isEmpPenInsurance()){
                   //条件を満たす対象者のデータをもとに「国民年金第３号被保険者住所変更届」を印刷する
                   worksheets.get(worksheets2.addCopy(0)).setName(sheetName + i+1);
                   this.pushData(worksheets, empAddChangeInfoExport,sheetName + i+1);
               }
           }

           worksheets.removeAt(0);
           reportContext.saveAsExcel(this.createNewFile(fileContext,
                   FILE_NAME + ".xlsx"));

       }catch (Exception e){
           throw new RuntimeException(e);
       }
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

    private JapaneseDate toJapaneseDate (GeneralDate date) {
        Optional<JapaneseEraName> era = this.adapter.getAllEras().eraOf(date);
        return new JapaneseDate(date, era.get());
    }

    private void fillEraItem(WorksheetCollection worksheets, String sheetName, GeneralDate date, String op1, String op2, String op3){
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
        }
    }

    private void pushData(WorksheetCollection worksheet,
                          EmpAddChangeInfoExport empAddChangeInfoExport,
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

            worksheet.getRangeByName(i + "!A1_2").setValue(Objects.toString(empAddChangeInfoExport.getNameKanaPs() != null ?  empAddChangeInfoExport.getNameKanaPs(): ""));
            worksheet.getRangeByName(i + "!A1_3").setValue(Objects.toString(empAddChangeInfoExport.getNameKanaPs() != null ?  empAddChangeInfoExport.getNameKanaPs(): ""));
            worksheet.getRangeByName(i + "!A1_4").setValue(Objects.toString(empAddChangeInfoExport.getFullNamePs() != null ?  empAddChangeInfoExport.getFullNamePs(): ""));
            worksheet.getRangeByName(i + "!A1_5").setValue(Objects.toString(empAddChangeInfoExport.getFullNamePs() != null ?  empAddChangeInfoExport.getFullNamePs(): ""));

            worksheet.getRangeByName(i + "!A2_6").setValue(Objects.toString(empAddChangeInfoExport.getNameKanaF() != null ?  empAddChangeInfoExport.getNameKanaF(): ""));
            worksheet.getRangeByName(i + "!A2_7").setValue(Objects.toString(empAddChangeInfoExport.getNameKanaF() != null ?  empAddChangeInfoExport.getNameKanaF(): ""));
            worksheet.getRangeByName(i + "!A2_8").setValue(Objects.toString(empAddChangeInfoExport.getFullNameF() != null ?  empAddChangeInfoExport.getFullNameF(): ""));
            worksheet.getRangeByName(i + "!A2_9").setValue(Objects.toString(empAddChangeInfoExport.getFullNameF() != null ?  empAddChangeInfoExport.getFullNameF(): ""));

            worksheet.getRangeByName(i + "!A1_6").setValue(Objects.toString(empAddChangeInfoExport.getPostCodePs() != null ?  empAddChangeInfoExport.getPostCodePs(): ""));
            worksheet.getRangeByName(i + "!A1_7").setValue(this.fillAddress(empAddChangeInfoExport.getAdd1KanaF(),empAddChangeInfoExport.getAdd1KanaF() ));
            //worksheet.getRangeByName(i + "!A1_8").setValue(this.fillAddress(empAddChangeInfoExport.getAdd1Ps(), empAddChangeInfoExport.getAdd2Ps()));
            //worksheet.getRangeByName(i + "!A1_9").setValue(this.fillAddress(empAddChangeInfoExport.getAdd1BeforeChange(), empAddChangeInfoExport.getAdd2BeforeChange()));

            this.fillByCell(worksheet , i,"A1_10_1", empAddChangeInfoExport.getStartDatePs().toString(TYPE_DATE),0 );
            this.fillByCell(worksheet , i,"A1_10_2", empAddChangeInfoExport.getStartDatePs().toString(TYPE_DATE),1 );
            this.fillByCell(worksheet , i,"A1_10_3", empAddChangeInfoExport.getStartDatePs().toString(TYPE_DATE),2 );
            this.fillByCell(worksheet , i,"A1_10_4", empAddChangeInfoExport.getStartDatePs().toString(TYPE_DATE),3 );
            this.fillByCell(worksheet , i,"A1_10_5", empAddChangeInfoExport.getStartDatePs().toString(TYPE_DATE),4 );
            this.fillByCell(worksheet , i,"A1_10_6", empAddChangeInfoExport.getStartDatePs().toString(TYPE_DATE),5 );

            //A1_11 ~ A1_15
            worksheet.get(i).getCheckBoxes().get(111).setCheckedValue(empAddChangeInfoExport.getShortResidentAtr());
            worksheet.get(i).getCheckBoxes().get(112).setCheckedValue(empAddChangeInfoExport.getResidenceOtherResidentAtr());
            worksheet.get(i).getCheckBoxes().get(113).setCheckedValue(empAddChangeInfoExport.getLivingAbroadAtr());
            worksheet.get(i).getCheckBoxes().get(114).setCheckedValue(empAddChangeInfoExport.getOtherAtr());
            worksheet.get(i).getTextBoxes().get("A1_15").setText(Objects.toString(
                    empAddChangeInfoExport.getOtherAtr() == 1 && empAddChangeInfoExport.getOtherReason() != null ? empAddChangeInfoExport.getOtherReason().toString(): ""));

            this.fillEraItem(worksheet, i, empAddChangeInfoExport.getBirthDatePs(), "A1_51", "A1_52","A1_53");
            this.fillByCell(worksheet , i,"A1_54_1", empAddChangeInfoExport.getBirthDatePs().toString(TYPE_DATE),0 );
            this.fillByCell(worksheet , i,"A1_54_2", empAddChangeInfoExport.getBirthDatePs().toString(TYPE_DATE),1 );
            this.fillByCell(worksheet , i,"A1_54_3", empAddChangeInfoExport.getBirthDatePs().toString(TYPE_DATE),2 );
            this.fillByCell(worksheet , i,"A1_54_4", empAddChangeInfoExport.getBirthDatePs().toString(TYPE_DATE),3 );
            this.fillByCell(worksheet , i,"A1_54_5", empAddChangeInfoExport.getBirthDatePs().toString(TYPE_DATE),4 );
            this.fillByCell(worksheet , i,"A1_54_6", empAddChangeInfoExport.getBirthDatePs().toString(TYPE_DATE),5 );

            this.fillEraItem(worksheet, i, empAddChangeInfoExport.getBirthDatePs(), "A2_3", "A2_4","A2_41");
            this.fillByCell(worksheet , i,"A2_5_1", empAddChangeInfoExport.getBirthDateF().toString(TYPE_DATE),0 );
            this.fillByCell(worksheet , i,"A2_5_2", empAddChangeInfoExport.getBirthDateF().toString(TYPE_DATE),1 );
            this.fillByCell(worksheet , i,"A2_5_3", empAddChangeInfoExport.getBirthDateF().toString(TYPE_DATE),2 );
            this.fillByCell(worksheet , i,"A2_5_4", empAddChangeInfoExport.getBirthDateF().toString(TYPE_DATE),3 );
            this.fillByCell(worksheet , i,"A2_5_5", empAddChangeInfoExport.getBirthDateF().toString(TYPE_DATE),4 );
            this.fillByCell(worksheet , i,"A2_5_6", empAddChangeInfoExport.getBirthDateF().toString(TYPE_DATE),5 );

            this.fillByCell(worksheet , i,"A2_10_1", empAddChangeInfoExport.getPostalCodeF(),0 );
            this.fillByCell(worksheet , i,"A2_10_2", empAddChangeInfoExport.getPostalCodeF(),1 );
            this.fillByCell(worksheet , i,"A2_10_3", empAddChangeInfoExport.getPostalCodeF(),2 );
            this.fillByCell(worksheet , i,"A2_10_4", empAddChangeInfoExport.getPostalCodeF(),3 );
            this.fillByCell(worksheet , i,"A2_10_5", empAddChangeInfoExport.getPostalCodeF(),4 );
            this.fillByCell(worksheet , i,"A2_10_6", empAddChangeInfoExport.getPostalCodeF(),5 );
            this.fillByCell(worksheet , i,"A2_10_7", empAddChangeInfoExport.getPostalCodeF(),6 );
            this.fillByCell(worksheet , i,"A2_10_8", empAddChangeInfoExport.getPostalCodeF(),7 );
            this.fillByCell(worksheet , i,"A2_10_9", empAddChangeInfoExport.getPostalCodeF(),8 );
            this.fillByCell(worksheet , i,"A2_10_10", empAddChangeInfoExport.getPostalCodeF(),9 );
            this.fillByCell(worksheet , i,"A2_10_11", empAddChangeInfoExport.getPostalCodeF(),10 );
            this.fillByCell(worksheet , i,"A2_10_12", empAddChangeInfoExport.getPostalCodeF(),11 );

            worksheet.getRangeByName(i + "!A2_11").setValue(this.fillAddress(empAddChangeInfoExport.getAdd1KanaF(), empAddChangeInfoExport.getAdd2KanaF()));
            //worksheet.getRangeByName(i + "!A2_12").setValue(this.fillAddress(empAddChangeInfoExport.getAdd1F(), empAddChangeInfoExport.getAdd2F()));

            this.fillByCell(worksheet , i,"A2_13_1", empAddChangeInfoExport.getStartDatePs().toString(TYPE_DATE),0 );
            this.fillByCell(worksheet , i,"A2_13_2", empAddChangeInfoExport.getStartDatePs().toString(TYPE_DATE),1 );
            this.fillByCell(worksheet , i,"A2_13_3", empAddChangeInfoExport.getStartDatePs().toString(TYPE_DATE),2 );
            this.fillByCell(worksheet , i,"A2_13_4", empAddChangeInfoExport.getStartDatePs().toString(TYPE_DATE),3 );
            this.fillByCell(worksheet , i,"A2_13_5", empAddChangeInfoExport.getStartDatePs().toString(TYPE_DATE),4 );
            this.fillByCell(worksheet , i,"A2_13_6", empAddChangeInfoExport.getStartDatePs().toString(TYPE_DATE),5 );

            //worksheet.getRangeByName(i + "!A2_14").setValue(this.fillAddress(empAddChangeInfoExport.getAdd1BeforeChange(), empAddChangeInfoExport.getAdd2BeforeChange()));

            //A2_15 ~ A2_19
            worksheet.get(i).getCheckBoxes().get(221).setCheckedValue(empAddChangeInfoExport.getSpouseShortResidentAtr());
            worksheet.get(i).getCheckBoxes().get(222).setCheckedValue(empAddChangeInfoExport.getSpouseResidenceOtherResidentAtr());
            worksheet.get(i).getCheckBoxes().get(223).setCheckedValue(empAddChangeInfoExport.getSpouseLivingAbroadAtr());
            worksheet.get(i).getCheckBoxes().get(224).setCheckedValue(empAddChangeInfoExport.getSpouseOtherAtr());
            worksheet.get(i).getTextBoxes().get("A2_19").setText(Objects.toString(
                    empAddChangeInfoExport.getSpouseOtherAtr() == 1 && empAddChangeInfoExport.getSpouseOtherReason() != null ? empAddChangeInfoExport.getSpouseOtherReason().toString(): ""));

            JapaneseDate japaneseDate = toJapaneseDate(empAddChangeInfoExport.getReferenceDate());
            int y = japaneseDate.year() + 1;
            int m = japaneseDate.month();
            int d = japaneseDate.day();
            worksheet.getRangeByName(i +"!A3_7" ).setValue(japaneseDate.era() + String.valueOf(y) + "年" + String.valueOf(m) + "月" + String.valueOf(d) + "日提出");
            worksheet.getRangeByName(i + "!A3_2").setValue(this.fillAddress(empAddChangeInfoExport.getAddress1(), empAddChangeInfoExport.getAddress2()));
            worksheet.getRangeByName(i + "!A3_3").setValue(Objects.toString(empAddChangeInfoExport.getBussinessName(), ""));
            worksheet.getRangeByName(i + "!A3_4").setValue(Objects.toString(empAddChangeInfoExport.getReferenceName(), ""));
            worksheet.getRangeByName(i + "!A3_5").setValue(Objects.toString(empAddChangeInfoExport.getPhoneNumber(), ""));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
