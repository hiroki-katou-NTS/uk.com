package nts.uk.file.pr.infra.core.socinsurnoticreset;

import com.aspose.cells.AutoFitterOptions;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.WorksheetCollection;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.ExportDataCsv;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.GuaByTheInsurExportDto;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.GuaByTheInsurExportExcelGenerator;
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
public class GuaByTheInsurPdfAposeFileGenerator extends AsposeCellsReportGenerator implements GuaByTheInsurExportExcelGenerator {

    private static final String TEMPLATE_FILE = "report/被保険者資格取得届_テンプレート.xlsx";

    private static final String REPORT_FILE_NAME = "被保険者資格取得届.pdf";

    @Inject
    private JapaneseErasAdapter adapter;

    private static final int EMP_IN_PAGE = 4;

    @Override
    public void generate(FileGeneratorContext fileContext, ExportDataCsv exportData) {
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
            Workbook wb = reportContext.getWorkbook();
            WorksheetCollection wsc = wb.getWorksheets();
            this.writeFileExcel(wsc, exportData.getListContent(),exportData.getBaseDate());
            reportContext.processDesigner();
            wsc.removeAt(0);
            reportContext.saveAsPdf(this.createNewFile(fileContext, REPORT_FILE_NAME));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void writeFileExcel(WorksheetCollection ws, List<GuaByTheInsurExportDto> exportData,GeneralDate baseDate ) {
        int stt = 0;
        int page = 0;
        String sheetName = "INS";
        String companyCd = "";
        AutoFitterOptions autoOpts = new AutoFitterOptions();
        autoOpts.setOnlyAuto(true);
        try {
            for (int i = 0; i < exportData.size(); i++,stt++) {
                GuaByTheInsurExportDto element = exportData.get(i);
                if (stt % EMP_IN_PAGE == 0 || !companyCd.equals(element.getOfficeCd())) {
                    if (!companyCd.equals(element.getOfficeCd()) && stt % EMP_IN_PAGE != 0) {
                        unSelectAll(ws, sheetName + page, stt);
                    }
                    page++;

                    ws.get(ws.addCopy(0)).setName(sheetName + page);

                    companyCd = element.getOfficeCd();
                    fillDataOffice(ws, element, sheetName + page,baseDate);
                    stt = 0;
                }
                fillEmployee(ws, element, sheetName + page, stt );
            }
            unSelectAll(ws,sheetName + page, stt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillDataOffice(WorksheetCollection ws, GuaByTheInsurExportDto element, String sheetName, GeneralDate baseDate) throws UnsupportedEncodingException {
        JapaneseDate dateJp = toJapaneseDate(baseDate);
        ws.getRangeByName(sheetName + "!A1_10_1").setValue(dateJp.year() + 1);
        ws.getRangeByName(sheetName + "!A1_10_2").setValue(dateJp.month());
        ws.getRangeByName(sheetName + "!A1_10_3" ).setValue(dateJp.day());
        ws.getRangeByName(sheetName + "!A1_1").setValue(element.getBusinessstablishmentbCode1());
        ws.getRangeByName(sheetName + "!A1_2").setValue(element.getBusinessstablishmentbCode2());
        ws.getRangeByName(sheetName + "!A1_3").setValue(element.getOfficeNumber());
        ws.getRangeByName(sheetName + "!A1_4_1").setValue(formatPortCd(element.getOfficePostalCode(),1));
        ws.getRangeByName(sheetName + "!A1_4_2").setValue(formatPortCd(element.getOfficePostalCode(),2));
        Style style = ws.getRangeByName(sheetName + "!A1_5").get(0,0).getStyle();
        style.setTextWrapped(true);
        ws.getRangeByName(sheetName + "!A1_5").setStyle(style);
        ws.getRangeByName(sheetName + "!A1_5").setValue(formatTooLongText(element.getOfficeAddress1(), 60) + "\n" + (element.getOfficeAddress2() != null ? element.getOfficeAddress2() : ""));
        ws.getRangeByName(sheetName +"!A1_6").setValue(element.getBusinessName());
        ws.getRangeByName(sheetName + "!A1_7").setValue(element.getBusinessName1());
        //ws.getRangeByName(this.getRangeName(sheetName,"A1_8", stt)).setValue(element.getBusinessName1());
        ws.getRangeByName(sheetName + "!A1_9_1").setValue(formatPhoneNumber(element.getPhoneNumber(),1));
        ws.getRangeByName(sheetName + "!A1_9_2").setValue(formatPhoneNumber(element.getPhoneNumber(),2));
        ws.getRangeByName(sheetName + "!A1_9_3").setValue(formatPhoneNumber(element.getPhoneNumber(),3));
    }

    private void fillEmployee(WorksheetCollection worksheets, GuaByTheInsurExportDto data, String sheetName, int stt){
        JapaneseDate birthDay = toJapaneseDate( GeneralDate.fromString(data.getBrithDay().substring(0,10), "yyyy-MM-dd"));
        JapaneseDate startDate = data.getQualificationDate().length() >= 10 ? toJapaneseDate( GeneralDate.fromString(data.getQualificationDate().substring(0,10), "yyyy-MM-dd")) : null;
        this.selectItem(worksheets, data, sheetName, stt);
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_2", stt)).setValue(data.getNameOfInsuredPersonMr().split("　")[0] );
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_3", stt)).setValue(data.getNameOfInsuredPersonMr().split("　").length > 1 ? data.getNameOfInsuredPerson().split("　")[1] : "");
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_4", stt)).setValue(data.getNameOfInsuredPersonMrK().split("　")[0]);
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_5", stt)).setValue(data.getNameOfInsuredPerson1().split("　").length > 1 ? data.getNameOfInsuredPerson1().split("　")[1] : "");
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_9_1" , stt)).setValue(convertJpDate(birthDay).charAt(0));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_9_2" , stt)).setValue(convertJpDate(birthDay).charAt(1));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_9_3" , stt)).setValue(convertJpDate(birthDay).charAt(2));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_9_4"  , stt)).setValue(convertJpDate(birthDay).charAt(3));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_9_5" , stt)).setValue(convertJpDate(birthDay).charAt(4));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_9_6" , stt)).setValue(convertJpDate(birthDay).charAt(5));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_21_1" , stt)).setValue(convertJpDate(startDate).charAt(0));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_21_2" , stt)).setValue(convertJpDate(startDate).charAt(1));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_21_3" , stt)).setValue(convertJpDate(startDate).charAt(2));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_21_4"  , stt)).setValue(convertJpDate(startDate).charAt(3));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_21_5" , stt)).setValue(convertJpDate(startDate).charAt(4));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_21_6" , stt)).setValue(convertJpDate(startDate).charAt(5));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_24", stt)).setValue(Objects.toString(data.getMonRemunerationAmountInCurrency(), ""));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_25", stt)).setValue(Objects.toString(data.getMonRemunerationAmountOfActualItem(),""));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_26", stt)).setValue(total(data.getMonRemunerationAmountInCurrency(), data.getMonRemunerationAmountOfActualItem()));
        worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_39" : "A2_39_" + stt).setText(data.getReasonOtherContent());
        worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_35" : "A2_35_" + stt).setText(data.getRemarksOtherContent());
    }

    private String total(Integer a, Integer b){
        if(a == null && b == null) {
            return "";
        }
        if(a == null) {
            return b.toString();
        }
        if(b == null) {
            return a.toString();
        }
        return Integer.toString(a + b);
    }

    private String formatPhoneNumber(String phone, int stt) {
        String[] sub = phone.split("-");
        if (stt == 1 && sub.length > 1) {
            return sub[0];
        }
        if (stt == 2 && sub.length > 2) {
            return sub[1];
        }
        if (stt == 2 && sub.length == 2) {
            return sub[1].length() > 3 ? sub[1].substring(0,3) : sub[1];
        }
        if (stt == 3 && sub.length == 2) {
            return sub[1].length() > 6 ? sub[1].substring(3,6) : sub[1].length() > 3 ? sub[1].substring(3, sub[1].length()) : "";
        }
        if (stt == 3 && sub.length >= 3) {
            return sub[2];
        }
        if (sub.length == 1 && stt == 1 && phone.length() < 3) {
            return phone;
        }
        if (sub.length == 1 && stt == 1) {
            return phone.substring(0, 3);
        }
        if (sub.length == 1 && stt == 3 && phone.length() > 6) {
            return phone.substring(6, phone.length());
        }
        if (sub.length == 1 && stt == 2 && phone.length() >= 6) {
            return phone.substring(3, 6);
        }
        return "";
    }
    private String formatPortCd(String portCd, int stt){
        String result = portCd.replace("-", "");
        if (result.length() >= 3 && stt == 1) {
            return result.substring(0, 3);
        }
        if (stt == 2 && result.length() > 3) {
            return result.substring(3, result.length());
        }
        return result;
    }

    private void unSelectAll(WorksheetCollection worksheets, String sheetName, int stt){
        for(int i = stt ; i< EMP_IN_PAGE;  i++) {
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_6_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_7_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_8_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_10_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_11_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_12_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_13_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_14_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_15_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_16_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_17_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_18_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_20_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_22_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_23_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_30_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_31_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_32_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_33_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_34_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_36_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_37_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_38_" + i));
        }
    }

    private void selectItem(WorksheetCollection worksheets, GuaByTheInsurExportDto element, String sheetName, int stt){
        if(element.getRemarks70OldAndOverEmployees() == 0){
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_30" : "A2_30_" + stt));
        }
        if(element.getRemarksTwoOrMoreOfficeWorkers() == 0){
            worksheets.get(sheetName).getShapes().remove( worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_31" : "A2_31_" + stt));
        }
        if(element.getRemarksShortTimeWorkers() == 0){
            worksheets.get(sheetName).getShapes().remove( worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_32" : "A2_32_" + stt));
        }
        if(element.getRemarksContReemAfterRetirement() == 0){
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_33" : "A2_33_" + stt));
        }
        if(element.getRemarksOther() == 0){
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_34" : "A2_34_" + stt));
        }
        //
        switch (element.getReasonResidentAbroad()){
            case 1 : {
                worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_37" : "A2_37_" + stt));
                worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_38" : "A2_38_" + stt));

                break;
            }
            case 2 : {
                worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_36" : "A2_36_" + stt));
                worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_38" : "A2_38_" + stt));
                break;
            }
            case 3 : {
                worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_36" : "A2_36_" + stt));
                worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_37" : "A2_37_" + stt));
                break;
            }
            case 0 : {
                worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_36" : "A2_36_" + stt));
                worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_37" : "A2_37_" + stt));
                worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_38" : "A2_38_" + stt));
                break;
            }

        }


        //
        if(element.getTypeMale() == 1 ){
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_10" : "A2_10_" + stt));
        }
        if(element.getTypeFeMale() == 1){
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_11" : "A2_11_" + stt));
        }
        if(element.getTypeMiner() == 1 ){
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_12" : "A2_12_" + stt));
        }
        if(element.getTypeMaleFund() == 1 ){
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_13" : "A2_13_" + stt));
        }
        if(element.getTypeFeMaleFund() == 1 ){
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_14" : "A2_14_" + stt));
        }
        if(element.getTypeMineWorkerFund() == 1){
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_15" : "A2_15_" + stt));
        }

        //
        switch (element.getAcquiCtgHealthInsurWelfare()){
            case 1 :{
                worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_17" : "A2_17_" + stt));
                worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_18" : "A2_18_" + stt));
                break;
            }
            case 2 :{
                worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_17" : "A2_17_" + stt));
                worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_18" : "A2_18_" + stt));

                break;
            }
            case 3 :{
                worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_16" : "A2_16_" + stt));
                worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_18" : "A2_18_" + stt));
                break;
            }
            case 4 :{
                worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_17" : "A2_17_" + stt));
                worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_16" : "A2_16_" + stt));
                break;
            }
            default:
                worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_17" : "A2_17_" + stt));
                worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_16" : "A2_16_" + stt));
                worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_18" : "A2_18_" + stt));
        }
        if(element.getDependentNo() == null) {
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_23" : "A2_23_" + stt));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_22" : "A2_22_" + stt));
        }
        if(element.getDependentNo() != null && element.getDependentNo() == 0){
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_23" : "A2_23_" + stt));
        }
        if(element.getDependentNo() != null && element.getDependentNo() == 1){
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_22" : "A2_22_" + stt));
        }
        // convert to japan date
        JapaneseDate startDateOfQualifiRyowa = element.getDateOfQualifiRyowa().length() >= 10 ? toJapaneseDate( GeneralDate.fromString(element.getDateOfQualifiRyowa().substring(0,10), "yyyy-MM-dd")) : null;
        if(!startDateOfQualifiRyowa.era().equals("令和")){
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_20" : "A2_20_" + stt));
        }
        // remove shape brithday
        JapaneseDate birthDay = toJapaneseDate( GeneralDate.fromString(element.getBrithDay().substring(0,10), "yyyy-MM-dd"));
        switch (birthDay.era()){
            case "昭和" : {
                worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_7" : "A2_7_" + stt));
                worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_8" : "A2_8_" + stt));
                break;
            }
            case "平成" : {
                worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_6" : "A2_6_" + stt));
                worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_8" : "A2_8_" + stt));
                break;
            }
            case "令和" : {
                worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_6" : "A2_6_" + stt));
                worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_7" : "A2_7_" + stt));
                break;
            }
            default:{
                worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_6" : "A2_6_" + stt));
                worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_7" : "A2_7_" + stt));
                worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_8" : "A2_8_" + stt));
                break;
            }
        }


    }

    private JapaneseDate toJapaneseDate (GeneralDate date) {
        Optional<JapaneseEraName> era = this.adapter.getAllEras().eraOf(date);
        return new JapaneseDate(date, era.get());
    }

    private String convertJpDate(JapaneseDate date){
        if(date == null) {
            return "      ";
        }
        int y = date.year() + 1;
        int d = date.day();
        int m = date.month();
        StringBuilder result = new StringBuilder();
        result.append(y > 9 ? y: "0" + y);
        result.append(m > 9 ? m : "0" + m);
        result.append(d > 9 ? d: "0" + d);
        return result.toString();
    }

    private String getRangeName(String sheetName, String pos, int stt){
        return stt == 0 ? sheetName + "!" + pos : sheetName + "!" + pos + "_" + stt;
    }
    private String getShapeName(String sheetName, String pos, int stt){
        return stt == 0 ? sheetName + "!" + pos : sheetName + "!" + pos + "_" + stt;
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
