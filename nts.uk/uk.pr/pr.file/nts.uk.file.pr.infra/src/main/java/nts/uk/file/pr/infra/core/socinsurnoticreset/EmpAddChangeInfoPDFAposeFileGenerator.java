package nts.uk.file.pr.infra.core.socinsurnoticreset;

import com.aspose.cells.Workbook;
import com.aspose.cells.WorksheetCollection;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.CompanyInformation;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.EmpAddChangeInfoExport;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.EmpAddChangeInfoFileGenerator;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.EmpAddChangeInforData;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.PersonalNumClass;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.SocialInsurNotiCreateSet;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.SubNameClass;
import nts.uk.shr.com.time.japanese.JapaneseDate;
import nts.uk.shr.com.time.japanese.JapaneseEraName;
import nts.uk.shr.com.time.japanese.JapaneseErasAdapter;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;

public class EmpAddChangeInfoPDFAposeFileGenerator extends AsposeCellsReportGenerator implements EmpAddChangeInfoFileGenerator {
    private static final String TEMPLATE_FILE_1 = "report/国民年金第３号被保険者住所変更届.xlsx";
    private static final String TEMPLATE_FILE_2 = "report/被保険者住所変更届.xlsx";
    private static final String FILE_NAME_1 = "国民年金第３号被保険者住所変更届";
    private static final String FILE_NAME_2 = "被保険者住所変更届";

    @Inject
    private JapaneseErasAdapter adapter;

    private static final String TYPE_DATE = "YYYYMMDD";

    @Override
    public void generate(FileGeneratorContext fileContext, EmpAddChangeInforData data) {
       try {
           AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE_1);
           Workbook workbook = reportContext.getWorkbook();
           WorksheetCollection worksheets = workbook.getWorksheets();
           reportContext.processDesigner();
           String sheetName = "INS";
           for (int i  = 0; i < data.getEmpAddChangeInfoExportList().size() ; i ++){
               worksheets.get(worksheets.addCopy(0)).setName(sheetName + i);
               EmpAddChangeInfoExport empAddChangeInfoExport = data.getEmpAddChangeInfoExportList().get(i);
               //push data
               this.pushData(worksheets,
                       data.getCompanyInformation(),
                       data.getSocialInsuranceOffice(),
                       data.getSocialInsurNotiCreateSet(),
                       empAddChangeInfoExport,
                       sheetName + i);
           }

           worksheets.removeAt(0);
           reportContext.saveAsExcel(this.createNewFile(fileContext,
                   FILE_NAME_1 + ".xlsx"));

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

    private void pushData(WorksheetCollection worksheet,
                          CompanyInformation companyInformation,
                          SocialInsuranceOffice socialInsuranceOffice,
                          SocialInsurNotiCreateSet socialInsurNotiCreateSet,
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
            worksheet.getRangeByName(i + "!A1_8").setValue(this.fillAddress(empAddChangeInfoExport.getAdd1Ps(), empAddChangeInfoExport.getAdd2Ps()));
            worksheet.getRangeByName(i + "!A1_9").setValue(this.fillAddress(empAddChangeInfoExport.getAdd1BeforeChange(), empAddChangeInfoExport.getAdd2BeforeChange()));

            this.fillByCell(worksheet , i,"A1_10_1", empAddChangeInfoExport.getStartDatePs().toString(TYPE_DATE),0 );
            this.fillByCell(worksheet , i,"A1_10_2", empAddChangeInfoExport.getStartDatePs().toString(TYPE_DATE),1 );
            this.fillByCell(worksheet , i,"A1_10_3", empAddChangeInfoExport.getStartDatePs().toString(TYPE_DATE),2 );
            this.fillByCell(worksheet , i,"A1_10_4", empAddChangeInfoExport.getStartDatePs().toString(TYPE_DATE),3 );
            this.fillByCell(worksheet , i,"A1_10_5", empAddChangeInfoExport.getStartDatePs().toString(TYPE_DATE),4 );
            this.fillByCell(worksheet , i,"A1_10_6", empAddChangeInfoExport.getStartDatePs().toString(TYPE_DATE),5 );

            worksheet.getRangeByName(i + "!A1_11").setValue(Objects.toString(empAddChangeInfoExport.getShortResidentAtr() != null ?  empAddChangeInfoExport.getShortResidentAtr(): ""));
            worksheet.getRangeByName(i + "!A1_12").setValue(Objects.toString(empAddChangeInfoExport.getResidenceOtherResidentAtr() != null ?  empAddChangeInfoExport.getResidenceOtherResidentAtr(): ""));
            worksheet.getRangeByName(i + "!A1_13").setValue(Objects.toString(empAddChangeInfoExport.getLivingAbroadAtr() != null ?  empAddChangeInfoExport.getLivingAbroadAtr(): ""));
            worksheet.getRangeByName(i + "!A1_14").setValue(Objects.toString(empAddChangeInfoExport.getOtherAtr() != null ?  empAddChangeInfoExport.getOtherAtr(): ""));
            worksheet.getRangeByName(i + "!A1_15").setValue(Objects.toString(empAddChangeInfoExport.getOtherReason() != null ?  empAddChangeInfoExport.getOtherReason(): ""));






        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
