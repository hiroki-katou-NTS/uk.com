package nts.uk.file.pr.infra.core.socinsurnoticreset;

import com.aspose.cells.ShapeCollection;
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

    private static final String TEMPLATE_FILE = "report/被保険者資格取得届_帳票テンプレート.xlsx";

    private static final String REPORT_FILE_NAME = "被保険者資格取得届.pdf";

    @Inject
    private JapaneseErasAdapter adapter;

    @Override
    public void generate(FileGeneratorContext fileContext, ExportDataCsv exportData) {
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
            Workbook wb = reportContext.getWorkbook();
            WorksheetCollection wsc = wb.getWorksheets();

            this.writeFileExcel(wsc, exportData.getListContent(), "");
            reportContext.processDesigner();
            reportContext.saveAsPdf(this.createNewFile(fileContext, this.getReportName(REPORT_FILE_NAME)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void writeFileExcel(WorksheetCollection ws, List<GuaByTheInsurExportDto> exportData, String companyName) {


        // Main Data
        String sheetName = "INS";
        for (int page = 0; page < exportData.size(); page += 4) {
            for (int i = 0; i < exportData.size(); i += 4) {
                try {
                    GuaByTheInsurExportDto element = exportData.get(i);
                    ShapeCollection shapes =  ws.get(0).getShapes();
                    if (element != null) {

                        ws.getRangeByName("A1_10").setValue(element.getFilingDate());

                        ws.getRangeByName("A1_1").setValue(element.getBusinessstablishmentbCode1());
                        ws.getRangeByName("A1_2").setValue(element.getBusinessstablishmentbCode2());
                        ws.getRangeByName("A1_3").setValue(element.getOfficeNumber());
                        ws.getRangeByName("A1_4").setValue(element.getOfficePostalCode());
                        ws.getRangeByName("A1_5").setValue(element.getOfficeAddress1());
                        ws.getRangeByName("A1_6").setValue(element.getOfficeAddress2());
                        ws.getRangeByName("A1_7").setValue(element.getBusinessName());
                        ws.getRangeByName("A1_8").setValue(element.getBusinessName1());
                        ws.getRangeByName("A1_9").setValue(element.getPhoneNumber());

                        ws.getRangeByName("A2_1").setValue("Chưa có");
                        ws.getRangeByName("A2_2").setValue(element.getNameOfInsuredPersonMr());
                        ws.getRangeByName("A2_3").setValue(element.getNameOfInsuredPerson());
                        ws.getRangeByName("A2_4").setValue(element.getNameOfInsuredPersonMrK());
                        ws.getRangeByName("A2_5").setValue(element.getNameOfInsuredPerson1());
                        {
                            //pick data
                        }
                        ws.getRangeByName("A2_8").setValue(element.getBrithDay());
                        {
                            // fill data gender
                            if(element.getTypeMale() == 0){
                                shapes.remove( ws.get(0).getShapes().get("A2_9"));
                            }
                            else{
                                shapes.remove( ws.get(0).getShapes().get("A2_10"));
                            }
                        }
                        {
                            //fill date undergroundDivision

                        }
                        ws.getRangeByName("A2_20").setValue(element.getQualificationDate());
                        {
                            // depen apoint yes or no
                            if(element.getDependentNo() == 0){
                                shapes.remove( ws.get(0).getShapes().get("A2_21"));
                            }
                            else{
                                shapes.remove( ws.get(0).getShapes().get("A2_22"));
                            }
                        }
                        ws.getRangeByName("A2_23").setValue(element.getMonRemunerationAmountInCurrency());
                        ws.getRangeByName("A2_24").setValue(element.getMonRemunerationAmountOfActualItem());
                        ws.getRangeByName("A2_25").setValue(element.getCompenMonthlyAamountTotal());
                        ws.getRangeByName("A2_26").setValue(element.getPostalCode());
                        ws.getRangeByName("A2_27").setValue(element.getStreetAddress());
                        ws.getRangeByName("A2_28").setValue(element.getAddressKana());
                        if(element.getRemarks70OldAndOverEmployees() == 1){
                            shapes.remove( ws.get(0).getShapes().get("A2_29"));
                        }
                        if(element.getRemarksTwoOrMoreOfficeWorkers() == 1){
                            shapes.remove( ws.get(0).getShapes().get("A2_30"));
                        }
                        if(element.getRemarksShortTimeWorkers() == 1){
                            shapes.remove( ws.get(0).getShapes().get("A2_31"));
                        }
                        if(element.getRemarksContReemAfterRetirement() == 1){
                            shapes.remove( ws.get(0).getShapes().get("A2_32"));
                        }
                        if(element.getRemarksOther() == 1){
                            shapes.remove( ws.get(0).getShapes().get("A2_33"));
                        }

                        if(element.getReasonResidentAbroad() == 1){
                            shapes.remove( ws.get(0).getShapes().get("A2_35"));
                        }
                        if(element.getReasonShortTermResidence() == 1){
                            shapes.remove( ws.get(0).getShapes().get("A2_36"));
                        }
                        if(element.getReasonOther() == 1){
                            shapes.remove( ws.get(0).getShapes().get("A2_37"));
                        }
                        ws.get(0).getShapes().get("A2_38").setText(element.getReasonOtherContent());
                    } else {
                        return;
                    }
                    // Fill element 2nd
                    GuaByTheInsurExportDto element1 = exportData.get(i + 1);
                    if (element1!= null) {
                        ws.getRangeByName("A1_10").setValue(element1.getFilingDate());

                        ws.getRangeByName("A2_1_1").setValue("Chưa có");
                        ws.getRangeByName("A2_2_1").setValue(element1.getNameOfInsuredPersonMr());
                        ws.getRangeByName("A2_3_1").setValue(element1.getNameOfInsuredPerson());
                        ws.getRangeByName("A2_4_1").setValue(element1.getNameOfInsuredPersonMrK());
                        ws.getRangeByName("A2_5_1").setValue(element1.getNameOfInsuredPerson1());
                        {
                            //pick data
                        }
                        ws.getRangeByName("A2_8_1").setValue(element.getBrithDay());
                        {
                            // fill data gender
                            if(element.getTypeMale() == 0){
                                shapes.remove( ws.get(0).getShapes().get("A2_9_1"));
                            }
                            else{
                                shapes.remove( ws.get(0).getShapes().get("A2_10_1"));
                            }
                        }
                        {
                            //fill date undergroundDivision

                        }
                        ws.getRangeByName("A2_20_1").setValue(element.getQualificationDate());
                        {
                            // depen apoint yes or no
                            if(element.getDependentNo() == 0){
                                shapes.remove( ws.get(0).getShapes().get("A2_21_1"));
                            }
                            else{
                                shapes.remove( ws.get(0).getShapes().get("A2_22_1"));
                            }
                        }
                        ws.getRangeByName("A2_23_1").setValue(element1.getMonRemunerationAmountInCurrency());
                        ws.getRangeByName("A2_24_1").setValue(element1.getMonRemunerationAmountOfActualItem());
                        ws.getRangeByName("A2_25_1").setValue(element1.getCompenMonthlyAamountTotal());
                        ws.getRangeByName("A2_26_1").setValue(element1.getPostalCode());
                        ws.getRangeByName("A2_27_1").setValue(element1.getStreetAddress());
                        ws.getRangeByName("A2_28_1").setValue(element1.getAddressKana());
                    }
                    else{
                        return;
                    }
                    // Fill element 3nd
                    GuaByTheInsurExportDto element2 = exportData.get(i + 2);
                    if (element2!= null) {
                        ws.getRangeByName("A1_10").setValue(element.getFilingDate());



                        ws.getRangeByName("A2_1_2").setValue("Chưa có");
                        ws.getRangeByName("A2_2_2").setValue(element2.getNameOfInsuredPersonMr());
                        ws.getRangeByName("A2_3_2").setValue(element2.getNameOfInsuredPerson());
                        ws.getRangeByName("A2_4_2").setValue(element2.getNameOfInsuredPersonMrK());
                        ws.getRangeByName("A2_5_2").setValue(element2.getNameOfInsuredPerson1());
                        {
                            //pick data
                        }
                            ws.getRangeByName("A2_8_2").setValue(element.getBrithDay());
                            {
                                // fill data gender
                                if(element.getTypeMale() == 0){
                                    shapes.remove( ws.get(0).getShapes().get("A2_9_2"));
                                }
                                else{
                                    shapes.remove( ws.get(0).getShapes().get("A2_10_2"));
                                }
                            }
                            {
                                //fill date undergroundDivision

                            }
                            ws.getRangeByName("A2_20_2").setValue(element.getQualificationDate());
                            {
                                // depen apoint yes or no
                                if(element.getDependentNo() == 0){
                                    shapes.remove( ws.get(0).getShapes().get("A2_21_2"));
                                }
                                else{
                                    shapes.remove( ws.get(0).getShapes().get("A2_22_2"));
                                }
                            }
                        ws.getRangeByName("A2_23_2").setValue(element2.getMonRemunerationAmountInCurrency());
                        ws.getRangeByName("A2_24_2").setValue(element2.getMonRemunerationAmountOfActualItem());
                        ws.getRangeByName("A2_25_2").setValue(element2.getCompenMonthlyAamountTotal());
                        ws.getRangeByName("A2_26_2").setValue(element2.getPostalCode());
                        ws.getRangeByName("A2_27_2").setValue(element2.getStreetAddress());
                        ws.getRangeByName("A2_28_2").setValue(element2.getAddressKana());
                    }
                    else{
                        return;
                    }
                    // Fill element 4nd
                    GuaByTheInsurExportDto element3 = exportData.get(i + 3);
                    if (element3!= null) {
                        ws.getRangeByName("A1_10").setValue(element3.getFilingDate());



                        ws.getRangeByName("A2_1_3").setValue("Chưa có");
                        ws.getRangeByName("A2_2_3").setValue(element3.getNameOfInsuredPersonMr());
                        ws.getRangeByName("A2_3_3").setValue(element3.getNameOfInsuredPerson());
                        ws.getRangeByName("A2_4_3").setValue(element3.getNameOfInsuredPersonMrK());
                        ws.getRangeByName("A2_5_3").setValue(element3.getNameOfInsuredPerson1());
                        {
                            //pick data
                        }
                        ws.getRangeByName("A2_8_3").setValue(element.getBrithDay());
                        {
                            // fill data gender
                            if(element.getTypeMale() == 0){
                                shapes.remove( ws.get(0).getShapes().get("A2_9_3"));
                            }
                            else{
                                shapes.remove( ws.get(0).getShapes().get("A2_10_3"));
                            }
                        }
                        {
                            //fill date undergroundDivision

                        }
                        ws.getRangeByName("A2_20_3").setValue(element.getQualificationDate());
                        {
                            // depen apoint yes or no
                            if(element.getDependentNo() == 0){
                                shapes.remove( ws.get(0).getShapes().get("A2_21_3"));
                            }
                            else{
                                shapes.remove( ws.get(0).getShapes().get("A2_22_3"));
                            }
                        }
                        ws.getRangeByName("A2_23_3").setValue(element3.getMonRemunerationAmountInCurrency());
                        ws.getRangeByName("A2_24_3").setValue(element3.getMonRemunerationAmountOfActualItem());
                        ws.getRangeByName("A2_25_3").setValue(element3.getCompenMonthlyAamountTotal());
                        ws.getRangeByName("A2_26_3").setValue(element3.getPostalCode());
                        ws.getRangeByName("A2_27_3").setValue(element3.getStreetAddress());
                        ws.getRangeByName("A2_28_3").setValue(element3.getAddressKana());
                    }
                    else{
                        return;
                    }

                    ws.get(ws.addCopy(0)).setName(sheetName + page / 4);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }
    private JapaneseDate toJapaneseDate(GeneralDate date) {
        Optional<JapaneseEraName> era = this.adapter.getAllEras().eraOf(date);
        return new JapaneseDate(date, era.get());
    }

}
