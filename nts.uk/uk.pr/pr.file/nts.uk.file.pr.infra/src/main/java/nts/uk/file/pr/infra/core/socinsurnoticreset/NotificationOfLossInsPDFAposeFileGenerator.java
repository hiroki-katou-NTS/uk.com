package nts.uk.file.pr.infra.core.socinsurnoticreset;

import com.aspose.cells.Workbook;
import com.aspose.cells.WorksheetCollection;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.CompanyInfor;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.InsLossDataExport;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.LossNotificationInformation;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.NotificationOfLossInsFileGenerator;
import nts.uk.shr.com.time.japanese.JapaneseDate;
import nts.uk.shr.com.time.japanese.JapaneseEraName;
import nts.uk.shr.com.time.japanese.JapaneseErasAdapter;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Stateless
public class NotificationOfLossInsPDFAposeFileGenerator extends AsposeCellsReportGenerator implements NotificationOfLossInsFileGenerator {

    private static final String TEMPLATE_FILE_70 = "report/70歳以上被用者該当届_帳票テンプレート.xlsx";
    private static final String TEMPLATE_FILE_OTHER = "report/70歳以上被用者不該当届_帳票テンプレート.xlsx";
    private static final String FILE_NAME = "70歳以上被用者不該当届_帳票テンプレート";

    @Inject
    private JapaneseErasAdapter adapter;

    @Override
    public void generate(FileGeneratorContext generatorContext, LossNotificationInformation data) {
        CompanyInfor company = data.getCompany();
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE_OTHER)) {
            AsposeCellsReportContext reportContext70 = this.createContext(TEMPLATE_FILE_70);
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            worksheets.add(1);
            reportContext.processDesigner();
            Workbook workbook70 = reportContext70.getWorkbook();
            WorksheetCollection worksheets70 = workbook70.getWorksheets();
            worksheets.get(1).copy(worksheets70.get(0));
            fillDataUnderSevenTy(worksheets, data.getHealthInsLoss(), data.getBaseDate(), company, data.getSocialInsuranceOffice());
            fillDataOverSevenTy(worksheets, data.getHealthInsLoss(), data.getBaseDate(), company, data.getSocialInsuranceOffice());
            //worksheets.removeAt(0);

            reportContext.saveAsPdf(this.createNewFile(generatorContext,
                    FILE_NAME + "_" + GeneralDateTime.now().toString("yyyyMMddHHmmss") + ".pdf"));
            /*reportContext.saveAsExcel(this.createNewFile(generatorContext,
                    FILE_NAME + "_" + GeneralDateTime.now().toString("yyyyMMddHHmmss") + ".xlsx"));*/
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void fillDataOverSevenTy(WorksheetCollection worksheets, List<InsLossDataExport> data, GeneralDate baseDate, CompanyInfor company, List<SocialInsuranceOffice> socialInsuranceOffice) {
        try {
            String sheetName = "underSeventy";
            // Main Data
            for (int page = 0; page < data.size(); page += 4) {
                worksheets.get(worksheets.addCopy(1)).setName(sheetName + page/4);
            }
            for (int i = 0; i < data.size(); i++) {
                InsLossDataExport  dataRow = data.get(i);
                fillCompanyPension(worksheets, dataRow, baseDate, sheetName + i/4);
                fillEmployeeOverSeventy(worksheets, dataRow, sheetName + i/4);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void fillDataUnderSevenTy(WorksheetCollection worksheets, List<InsLossDataExport> data, GeneralDate baseDate, CompanyInfor company, List<SocialInsuranceOffice> socialInsuranceOffice) {
        try {
            String sheetName = "overSeventy";
            // Main Data
            for (int page = 0; page < data.size(); page += 4) {
                worksheets.get(worksheets.addCopy(0)).setName(sheetName + page/4);
            }
            for (int i = 0; i < data.size(); i++) {
                InsLossDataExport  dataRow = data.get(i);
                fillCompanyHealthy(worksheets, dataRow, baseDate, company, sheetName + i/4);
                fillEmployeeUnderSeventy(worksheets, dataRow, sheetName + i/4, i);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void fillCompanyPension( WorksheetCollection worksheets, InsLossDataExport data, GeneralDate baseDate, String sheetName){
        worksheets.getRangeByName(sheetName + "!D1_1").setValue(Objects.toString(baseDate, ""));
        worksheets.getRangeByName(sheetName + "!D1_2").setValue(Objects.toString(data.getOfficeNumber1() , ""));
        worksheets.getRangeByName(sheetName + "!D1_3").setValue(Objects.toString(data.getOfficeNumber2(), ""));
        worksheets.getRangeByName(sheetName + "!D1_4").setValue(Objects.toString(data.getOfficeNumber(), ""));
        worksheets.getRangeByName(sheetName + "!D1_5").setValue(Objects.toString(data.getPortCd(), ""));
        worksheets.getRangeByName(sheetName + "!D1_6").setValue(Objects.toString(data.getAdd1() + data.getAdd2(), ""));
        worksheets.getRangeByName(sheetName + "!D1_7").setValue(Objects.toString(data.getCompanyName(), ""));
        worksheets.getRangeByName(sheetName + "!D1_8").setValue(Objects.toString(data.getRepName(), ""));
        worksheets.getRangeByName(sheetName + "!D1_9").setValue(Objects.toString(data.getPhoneNumber(), ""));
    }

    private void fillCompanyHealthy(WorksheetCollection worksheets, InsLossDataExport data, GeneralDate baseDate, CompanyInfor company, String sheetName){
        toJapaneseDate(baseDate).toString();
        worksheets.getRangeByName(sheetName + "!A1_1").setValue(Objects.toString(baseDate, ""));
        worksheets.getRangeByName(sheetName + "!A1_1_2").setValue(Objects.toString(baseDate, ""));
        worksheets.getRangeByName(sheetName + "!A1_1_3").setValue(Objects.toString(baseDate, ""));
        worksheets.getRangeByName(sheetName + "!A1_2").setValue(Objects.toString(data.getOfficeNumber1() , ""));
        worksheets.getRangeByName(sheetName + "!A1_3").setValue(Objects.toString(data.getOfficeNumber2(), ""));
        worksheets.getRangeByName(sheetName + "!A1_4").setValue(Objects.toString(data.getOfficeNumber(), ""));
        worksheets.getRangeByName(sheetName + "!A1_5").setValue(Objects.toString(company.postCd, ""));
        worksheets.getRangeByName(sheetName + "!A1_6").setValue(Objects.toString(company.add_1, ""));
        worksheets.getRangeByName(sheetName + "!A1_7").setValue(Objects.toString(company.add_2, ""));
        worksheets.getRangeByName(sheetName + "!A1_8").setValue(Objects.toString(company.companyName, ""));
        worksheets.getRangeByName(sheetName + "!A1_9").setValue(Objects.toString(company.repname, ""));
    }

    private void fillEmployeeUnderSeventy(WorksheetCollection worksheets, InsLossDataExport data, String sheetName, int stt){
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_1" : sheetName + "!A2_1" + "_" + ++stt).setValue(Objects.toString(data.getHealInsNumber(), ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_2" : sheetName + "!A2_2" + "_" + ++stt).setValue(Objects.toString(data.getPersonName(), ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_3" : sheetName + "!A2_3" + "_" + ++stt).setValue(Objects.toString(data.getPersonNameKana(), ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_4" : sheetName + "!A2_4" + "_" + ++stt).setValue(Objects.toString(data.getOldName(), ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_5" : sheetName + "!A2_5" + "_" + ++stt).setValue(Objects.toString(data.getOldName(), ""));
        //worksheets.get(sheetName).getShapes().get("A2_8").setText(data.getBirthDay());
        //worksheets.getRangeByName(sheetName + "!A2_9").setValue(Objects.toString(data.getBirthDay(), ""));
        worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_9" : "A2_9" +  "_" + ++stt).setText(Objects.toString(data.getBasicPenNumber(), ""));
        worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_10" : "A2_10" +  "_" + ++stt).setText(Objects.toString(data.getEndDate(), ""));
        //worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_11" : "A2_11" +  "_" + ++stt).setText(Objects.toString(data.getEndDate(), ""));
        //worksheets.getRangeByName(sheetName + "!A2_10").setValue(Objects.toString(data.getBasicPenNumber(), ""));
        //worksheets.getRangeByName(sheetName + "!A2_11").setValue(Objects.toString(data.getEndDate(), ""));
        //cells.get(C2_10_ROW, C2_10_COLUMN)
        //worksheets.getRangeByName(sheetName + "A2_12").setValue(Objects.toString(data.getBasicPenNumber(), ""));
        /*worksheets.get(sheetName).getShapes().get("A2_12").setText(Objects.toString(data.getEndDate(), ""));
        worksheets.get(sheetName).getShapes().get("A2_14").setText(Objects.toString(data.getEndDate(), ""));*/
        //worksheets.getRangeByName(stt == 0 ? sheetName + "A2_18" : sheetName + "!A2_18" + "_" + ++stt).setValue(Objects.toString(data.getIsMoreEmp(), ""));
        //worksheets.getRangeByName(stt == 0 ? sheetName + "A2_19" : sheetName + "!A2_19" + "_" + ++stt).setValue(Objects.toString(data.getContinReemAfterRetirement(), ""));
       // worksheets.getRangeByName(stt == 0 ? sheetName + "A2_20" : sheetName + "!A2_20" + "_" + ++stt).setValue(Objects.toString(data.getOther(), ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_21" : sheetName + "!A2_21" + "_" + ++stt).setValue(Objects.toString(data.getOtherReason(), ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_22" : sheetName + "!A2_22" + "_" + ++stt).setValue(Objects.toString(data.getCaInsurance(), ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_23" : sheetName + "!A2_23" + "_" + ++stt).setValue(Objects.toString(data.getNumRecoved(), ""));
        //worksheets.getRangeByName(stt == 0 ? sheetName + "A2_24" : sheetName + "!A2_24" + "_" + ++stt).setValue(Objects.toString(data.getCause(), ""));
        worksheets.get(sheetName).getShapes().get("A2_25").setText(Objects.toString(data.getEndDate(), ""));
    }

    private void fillEmployeeOverSeventy(WorksheetCollection worksheets, InsLossDataExport data, String sheetName){
        worksheets.getRangeByName(sheetName + "!D2_1").setValue(Objects.toString(data.getHealInsNumber(), ""));
        worksheets.getRangeByName(sheetName + "!D2_2").setValue(Objects.toString(data.getPersonName(), ""));
        worksheets.getRangeByName(sheetName + "!D2_3").setValue(Objects.toString(data.getPersonNameKana(), ""));
        worksheets.getRangeByName(sheetName + "!D2_4").setValue(Objects.toString(data.getOldName(), ""));
        worksheets.getRangeByName(sheetName + "!D2_5").setValue(Objects.toString(data.getOldName(), ""));
        worksheets.getRangeByName(sheetName + "!D2_9").setValue(Objects.toString(data.getBirthDay(), ""));
        worksheets.getRangeByName(sheetName + "!D2_10").setValue(Objects.toString(data.getBasicPenNumber(), ""));
        worksheets.getRangeByName(sheetName + "!D2_11").setValue(Objects.toString(data.getEndDate(), ""));
        worksheets.getRangeByName(sheetName + "!D2_12").setValue(Objects.toString(data.getIsMoreEmp(), ""));
        worksheets.getRangeByName(sheetName + "!D2_13").setValue(Objects.toString(data.getContinReemAfterRetirement(), ""));
        worksheets.getRangeByName(sheetName + "!D2_14").setValue(Objects.toString(data.getContinReemAfterRetirement(), ""));
    }

    private SocialInsuranceOffice findCompany(List<SocialInsuranceOffice> socialInsuranceOffice, String officeCode){
        Optional<SocialInsuranceOffice> insOffice = socialInsuranceOffice.stream().filter(item -> item.getCode().equals(officeCode)).findFirst();
        return insOffice.orElse(null);
    }

    public JapaneseDate toJapaneseDate (GeneralDate date) {
        Optional<JapaneseEraName> era = this.adapter.getAllEras().eraOf(date);
        return new JapaneseDate(date, era.get());
    }
}
