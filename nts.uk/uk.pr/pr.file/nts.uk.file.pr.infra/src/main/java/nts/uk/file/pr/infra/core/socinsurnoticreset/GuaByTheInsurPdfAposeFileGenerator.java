package nts.uk.file.pr.infra.core.socinsurnoticreset;

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
import java.util.List;
import java.util.Optional;

@Stateless
public class GuaByTheInsurPdfAposeFileGenerator extends AsposeCellsReportGenerator implements GuaByTheInsurExportExcelGenerator {

    private static final String TEMPLATE_FILE = "report/被保険者資格取得届_テンプレート.xlsx";

    private static final String REPORT_FILE_NAME = "被保険者資格取得届.pdf";

    @Inject
    private JapaneseErasAdapter adapter;

    private static final int EMP_IN_PAGE = 2;

    @Override
    public void generate(FileGeneratorContext fileContext, ExportDataCsv exportData) {
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
            Workbook wb = reportContext.getWorkbook();
            WorksheetCollection wsc = wb.getWorksheets();
            this.writeFileExcel(wsc, exportData.getListContent());
            reportContext.processDesigner();
            wsc.removeAt(0);
            reportContext.saveAsPdf(this.createNewFile(fileContext, REPORT_FILE_NAME));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void writeFileExcel(WorksheetCollection ws, List<GuaByTheInsurExportDto> exportData) {
        int stt = 0;
        int page = 0;
        String sheetName = "INS";
        String companyCd = "";
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
                    fillDataOffice(ws, element, sheetName + page, stt );
                    stt = 0;
                }
                fillEmployee(ws, element, sheetName + page, stt );
            }
            unSelectAll(ws,sheetName + page, stt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillDataOffice(WorksheetCollection ws, GuaByTheInsurExportDto element, String sheetName, int stt) {
        JapaneseDate dateJp = toJapaneseDate(element.getFilingDate());
        ws.getRangeByName(this.getRangeName(sheetName,"A1_10_1", stt)).setValue(dateJp.year() + 1);
        ws.getRangeByName(this.getRangeName(sheetName,"A1_10_2", stt)).setValue(dateJp.month());
        ws.getRangeByName(this.getRangeName(sheetName,"A1_10_3", stt)).setValue(dateJp.day());
        ws.getRangeByName(this.getRangeName(sheetName,"A1_1", stt)).setValue(element.getBusinessstablishmentbCode1());
        ws.getRangeByName(this.getRangeName(sheetName,"A1_2", stt)).setValue(element.getBusinessstablishmentbCode2());
        ws.getRangeByName(this.getRangeName(sheetName,"A1_3", stt)).setValue(element.getOfficeNumber());
        ws.getRangeByName(this.getRangeName(sheetName,"A1_4", stt)).setValue(element.getOfficePostalCode());
        ws.getRangeByName(this.getRangeName(sheetName,"A1_5", stt)).setValue(element.getOfficeAddress1());
        ws.getRangeByName(this.getRangeName(sheetName,"A1_6", stt)).setValue(element.getOfficeAddress2());
        ws.getRangeByName(this.getRangeName(sheetName,"A1_7", stt)).setValue(element.getBusinessName());
        //ws.getRangeByName(this.getRangeName(sheetName,"A1_8", stt)).setValue(element.getBusinessName1());
        ws.getRangeByName(this.getRangeName(sheetName,"A1_9", stt)).setValue(element.getPhoneNumber());
    }

    private void fillEmployee(WorksheetCollection worksheets, GuaByTheInsurExportDto data, String sheetName, int stt){
        JapaneseDate birthDay = toJapaneseDate( GeneralDate.fromString(data.getBrithDay().substring(0,10), "yyyy-MM-dd"));
        JapaneseDate startDate = toJapaneseDate( GeneralDate.fromString(data.getQualificationDate().substring(0,10), "yyyy-MM-dd"));
        //JapaneseDate endDate = toJapaneseDate( GeneralDate.fromString(data.getSt().substring(0,10), "yyyy-MM-dd"));
        /*this.selectEra(worksheets, birthDay.era(), sheetName, stt);
        this.selectCause(worksheets, data.getCause(), sheetName, stt);
        this.selectUnder(worksheets, data.getIsMoreEmp(),"A2_18", sheetName, stt);
        this.selectUnder(worksheets, data.getContinReemAfterRetirement(),"A2_19", sheetName, stt);
        this.selectUnder(worksheets, data.getOther(),"A2_20", sheetName, stt);*/
        this.selectItem(worksheets, data, sheetName, stt);
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_2", stt)).setValue(data.getNameOfInsuredPersonMr());
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_3", stt)).setValue(data.getNameOfInsuredPerson());
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_4", stt)).setValue(data.getNameOfInsuredPersonMrK());
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_5", stt)).setValue(data.getNameOfInsuredPerson1());
        worksheets.getRangeByName(this.getRangeName(sheetName,"A2_9_1" , stt)).setValue(convertJpDate(birthDay).charAt(0));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_9_2" , stt)).setValue(convertJpDate(birthDay).charAt(1));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_9_3" , stt)).setValue(convertJpDate(birthDay).charAt(2));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_9_4"  , stt)).setValue(convertJpDate(birthDay).charAt(3));
        worksheets.getRangeByName(this.getRangeName(sheetName,  "A2_9_5" , stt)).setValue(convertJpDate(birthDay).charAt(4));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_9_6" , stt)).setValue(convertJpDate(birthDay).charAt(5));
        worksheets.getRangeByName(this.getRangeName(sheetName,"A2_21_1" , stt)).setValue(convertJpDate(startDate).charAt(0));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_21_2" , stt)).setValue(convertJpDate(startDate).charAt(1));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_21_3" , stt)).setValue(convertJpDate(startDate).charAt(2));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_21_4"  , stt)).setValue(convertJpDate(startDate).charAt(3));
        worksheets.getRangeByName(this.getRangeName(sheetName,  "A2_21_5" , stt)).setValue(convertJpDate(startDate).charAt(4));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_21_6" , stt)).setValue(convertJpDate(startDate).charAt(5));
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_19_1", stt)).setValue(data.getPersonalNumber().length() > 0 ? data.getPersonalNumber().substring(0,1) : data.getPersonalNumber());
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_19_2", stt)).setValue(data.getPersonalNumber().length() > 1 ? data.getPersonalNumber().substring(1,2) : "");
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_19_3", stt)).setValue(data.getPersonalNumber().length() > 2 ? data.getPersonalNumber().substring(2,3) : "");
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_19_4", stt)).setValue(data.getPersonalNumber().length() > 3 ? data.getPersonalNumber().substring(3,4) : "");
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_19_5", stt)).setValue(data.getPersonalNumber().length() > 4 ? data.getPersonalNumber().substring(4,5) : "");
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_19_6", stt)).setValue(data.getPersonalNumber().length() > 5 ? data.getPersonalNumber().substring(5,6) : "");
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_19_7", stt)).setValue(data.getPersonalNumber().length() > 6 ? data.getPersonalNumber().substring(6,7) : "");
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_19_8", stt)).setValue(data.getPersonalNumber().length() > 7 ? data.getPersonalNumber().substring(7,8) : "");
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_24", stt)).setValue(data.getMonRemunerationAmountInCurrency());
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_25", stt)).setValue(data.getMonRemunerationAmountOfActualItem());
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_26", stt)).setValue(data.getCompenMonthlyAamountTotal());
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_27_1", stt)).setValue(data.getPostalCode().length() > 3 ? data.getPostalCode().substring(0,3) : data.getPostalCode());
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_27_2", stt)).setValue(data.getPostalCode().length() >= 7 ? data.getPostalCode().substring(3,7) : data.getPostalCode());
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_28", stt)).setValue(data.getStreetAddress());
        worksheets.getRangeByName(this.getRangeName(sheetName, "A2_29", stt)).setValue(data.getAddressKana());
        worksheets.getRangeByName("A2_38").setValue(data.getReasonOtherContent());
    }

    private void unSelectAll(WorksheetCollection worksheets, String sheetName, int stt){
        for(int i = stt ; i< EMP_IN_PAGE;  i++) {
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_6_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_7_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_8_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_10_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_11_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_12_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_16_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_17_" + i));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get("A2_18_" + i));
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
        if(element.getRemarks70OldAndOverEmployees() == 1){
            worksheets.get(sheetName).getShapes().remove( worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_30" : "A2_30_" + stt));
        }
        if(element.getRemarksTwoOrMoreOfficeWorkers() == 1){
            worksheets.get(sheetName).getShapes().remove( worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_31" : "A2_31_" + stt));
        }
        if(element.getRemarksShortTimeWorkers() == 1){
            worksheets.get(sheetName).getShapes().remove( worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_32" : "A2_32_" + stt));
        }
        if(element.getRemarksContReemAfterRetirement() == 1){
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_33" : "A2_33_" + stt));
        }
        if(element.getRemarksOther() == 1){
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_34" : "A2_34_" + stt));
        }
        if(element.getReasonResidentAbroad() == 1){
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_36" : "A2_36_" + stt));
        }
        if(element.getReasonShortTermResidence() == 1){
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_37" : "A2_37_" + stt));
        }
        if(element.getReasonOther() == 1){
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_38" : "A2_38_" + stt));
        }
        if(element.getTypeMale() == 0){
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_10" : "A2_10_" + stt));
        } else{
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_11" : "A2_11_" + stt));
        }
        if(element.getDependentNo() == 0){
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_22" : "A2_22_" + stt));
        } else{
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_23" : "A2_23_" + stt));
        }
    }

    private JapaneseDate toJapaneseDate (GeneralDate date) {
        Optional<JapaneseEraName> era = this.adapter.getAllEras().eraOf(date);
        return new JapaneseDate(date, era.get());
    }

    private String convertJpDate(JapaneseDate date){
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
        return stt == 0 ? sheetName + "!" + pos : sheetName + "!" + pos + "_" + ++stt;
    }


}
