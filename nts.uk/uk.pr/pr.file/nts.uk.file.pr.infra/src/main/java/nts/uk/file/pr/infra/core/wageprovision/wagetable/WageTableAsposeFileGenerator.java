package nts.uk.file.pr.infra.core.wageprovision.wagetable;

import com.aspose.cells.*;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationPaymentMethod;
import nts.uk.ctx.pr.file.app.core.wageprovision.unitpricename.SalaryPerUnitSetExportData;
import nts.uk.ctx.pr.file.app.core.wageprovision.wagetable.ItemDataNameExport;
import nts.uk.ctx.pr.file.app.core.wageprovision.wagetable.WageTableExportData;
import nts.uk.ctx.pr.file.app.core.wageprovision.wagetable.WageTableFileGenerator;
import nts.uk.ctx.pr.file.app.core.wageprovision.wagetable.WageTablelData;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Stateless
public class WageTableAsposeFileGenerator extends AsposeCellsReportGenerator
        implements WageTableFileGenerator {

    private static final String TEMPLATE_FILE = "report/QMM016.xlsx";

    private static final String REPORT_FILE_NAME = "QMM016単価名の登録.pdf";

    private static final int COLUMN_START = 1;

    private static final int MAX_ROWS = 71;
    @Inject
    private CompanyAdapter company;

    @Override
    public void generate(FileGeneratorContext fileContext, List<WageTablelData> exportData,List<ItemDataNameExport> dataName) {
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {

            int rowStart = 3;
            Workbook wb = reportContext.getWorkbook();
            WorksheetCollection wsc = wb.getWorksheets();
            Worksheet ws = wsc.get(0);
            ws.setName(TextResource.localize("QMM013_40"));
            //set headler
            // Company name
            String companyName = this.company.getCurrentCompany().map(CompanyInfor::getCompanyName).orElse("");
            PageSetup pageSetup = ws.getPageSetup();
            pageSetup.setHeader(0, "&10&\"MS ゴシック\"" + companyName);

            // Output item name
            pageSetup.setHeader(1, "&16&\"MS ゴシック\"" + "単価名の登録");

            // Set header date
            DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d  H:mm:ss", Locale.JAPAN);
            String currentFormattedDate = LocalDateTime.now().format(fullDateTimeFormatter);
            pageSetup.setHeader(2, "&\"ＭＳ ゴシック\"&10 " + currentFormattedDate+"\npage&P");

            //break page
            Cells cells = ws.getCells();
            // fill data
            for (int i = 1; i <  Math.ceil((float)exportData.size()/(float)MAX_ROWS); i++) {
                wsc.get(wsc.addCopy(0)).setName("sheetName" + i);
            }
            for (int i = 0; i < exportData.size(); i++) {
                if (i % MAX_ROWS == 0 && i != 0) {
                    Worksheet sheet = wsc.get("sheetName" + i / MAX_ROWS);
                    cells = sheet.getCells();
                    rowStart = 3;
                }

                WageTablelData e = exportData.get(i);
                cells.get(rowStart, COLUMN_START).setValue(e.getWageTableCode());
                cells.get(rowStart, COLUMN_START + 1).setValue(e.getWageTableName());
                cells.get(rowStart, COLUMN_START + 2).setValue(e.getWageHisStartYm());
                cells.get(rowStart, COLUMN_START + 3).setValue(e.getWageHisEndYm());


                cells.get(rowStart, COLUMN_START + 4).setValue(getFixedValue3(e,dataName));/*R2_5*/
                cells.get(rowStart, COLUMN_START + 5).setValue(getFixedValue2(e,dataName));/*R2_6*/
                cells.get(rowStart, COLUMN_START + 6).setValue(getFixedValue1(e,dataName));/*R2_7*/

                cells.get(rowStart, COLUMN_START + 7).setValue(getR2_8(e));
                cells.get(rowStart, COLUMN_START + 8).setValue(getR2_9(e));
                cells.get(rowStart, COLUMN_START + 9).setValue(getR2_10(e));

                cells.get(rowStart, COLUMN_START + 10).setValue(e.getPayAmount() != null ? e.getPayAmount() : "");
                cells.get(rowStart, COLUMN_START + 11).setValue(enumQualificationPaymentMethod(Integer.parseInt(e.getPayMethod())));
                rowStart++;
            }
            reportContext.processDesigner();
            reportContext.saveAsPdf(this.createNewFile(fileContext, this.getReportName(REPORT_FILE_NAME)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /* get list item name*/
    private String getListItemName(List<ItemDataNameExport> data,String codeOp){
        Optional<ItemDataNameExport> dataName = data.stream().filter(e -> e.equals(codeOp)).findFirst();
        return  dataName.isPresent()? dataName.get().getName() : "";
    }



    /*ElementSetting*/
    private String enumElementSetting(int index){
        switch (index){
            case 0 : {
                return TextResource.localize("Enum_Element_Setting_One_Dimension");
            }
            case 1 : {
                return TextResource.localize("Enum_Element_Setting_Two_Dimension");
            }
            case 2 : {
                return TextResource.localize("Enum_Element_Setting_Three_Dimension");
            }
            case 3 : {
                return TextResource.localize("Enum_Element_Setting_Qualification");
            }
            case 4 : {
                return TextResource.localize("Enum_Element_Setting_Fine_Work");
            }
            default: return "";
        }
    }
    /*ElementType*/
    private String enumElementType(String code){
        switch (code){
            case "M001" : {
                return TextResource.localize("Enum_Element_Type_M001");
            }
            case "M002" : {
                return TextResource.localize("Enum_Element_Type_M002");
            }
            case "M003" : {
                return TextResource.localize("Enum_Element_Type_M003");
            }
            case "M004" : {
                return TextResource.localize("Enum_Element_Type_M004");
            }
            case "M005" : {
                return TextResource.localize("Enum_Element_Type_M005");
            }
            case "M006" : {
                return TextResource.localize("Enum_Element_Type_M006");
            }
            case "M007" : {
                return TextResource.localize("Enum_Element_Type_M007");
            }
            case "N001" : {
                return TextResource.localize("Enum_Element_Type_N001");
            }
            case "N002" : {
                return TextResource.localize("Enum_Element_Type_N002");
            }
            case "N003" : {
                return TextResource.localize("Enum_Element_Type_N003");
            }
            default: return "";
        }
    }
    /*MasterNumericAtr*/
    private String enumMasterNumericAtr(int index){
        switch (index){
            case 0 : {
                return "マスタ項目";
            }
            case 1 : {
                return "数値項目";
            }


            default: return "";
        }
    }
    /*QualificationPaymentMethod*/
    private String enumQualificationPaymentMethod(int index){
        switch (index){
            case 0 : {
                return "Enum_Qualify_Pay_Method_Add_Multiple";
            }
            case 1 : {
                return "Enum_Qualify_Pay_Method_Only_One_Highest";
            }

            default: return "";
        }
    }



    private String getFixedValue3(WageTablelData e,List<ItemDataNameExport> dataName){
       if( e.getElementSet().equals("3")){
           return "資格グループ";
       }
       if(!e.getFixElement3().isEmpty()){
           return enumElementType(e.getFixElement1());
       }
       if(!e.getOptAddElement3().isEmpty()){
            return getListItemName(dataName, e.getOptAddElement3());
       }
       return "";

    }
    private String getFixedValue2(WageTablelData e,List<ItemDataNameExport> dataName){
        if( e.getElementSet().equals("3")){
            return "資格名称";
        }
        if(!e.getFixElement2().isEmpty()){
            return enumElementType(e.getFixElement1());
        }
        if(!e.getOptAddElement2().isEmpty()){
            return getListItemName(dataName, getOptAddElementName());
        }

        return "";
    }
    private String getFixedValue1(WageTablelData e,List<ItemDataNameExport> dataName){

        if(!e.getFixElement1().isEmpty()){
            return enumElementType(e.getFixElement1());
        }
        if(!e.getOptAddElement1().isEmpty()){
            return getListItemName(dataName, e.getOptAddElement1());
        }

        return "";
    }
    private String getOptAddElementName(){
        return "";
    }
    private String getLayThangThu3(){
        return "";
    }
    // get Condittion R2_8
    private String getR2_8(WageTablelData e){
        if(!e.getFixElement1().isEmpty()){
            return enumElementType(e.getFixElement1());
        }
        if(!e.getLowerLimit1().isEmpty()){
            return e.getLowerLimit1()+e.getUpperLimit1();
        }
        return "";
    }
    private String getR2_9(WageTablelData e){
        if(!e.getFixElement2().isEmpty()){
            return enumElementType(e.getFixElement2());
        }
        if(!e.getLowerLimit2().isEmpty()){
            return e.getLowerLimit2()+e.getUpperLimit2();
        }
        return "";
    }
    private String getR2_10(WageTablelData e){
        if(!e.getFixElement3().isEmpty()){
            return enumElementType(e.getFixElement3());
        }
        if(!e.getLowerLimit3().isEmpty()){
            return e.getLowerLimit3()+e.getUpperLimit3();
        }
        return "";
    }

    // R_11



}
