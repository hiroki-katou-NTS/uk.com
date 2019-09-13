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
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.ReasonsForLossHealthyIns;
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

    private static final String SHOWA = "昭和";

    private static final String HEISEI = "平成";

    private static final String PEACE = "令和";

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
            //fillDataOverSevenTy(worksheets, data.getHealthInsLoss(), data.getBaseDate(), company, data.getSocialInsuranceOffice());
            worksheets.removeAt(0);
            //worksheets.removeAt(1);
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
        JapaneseDate dateJp = toJapaneseDate(baseDate);
        worksheets.getRangeByName(sheetName + "!D1_1").setValue(Objects.toString(dateJp.year(), ""));
        worksheets.getRangeByName(sheetName + "!D1_1_2").setValue(Objects.toString(dateJp.month(), ""));
        worksheets.getRangeByName(sheetName + "!D1_1_3").setValue(Objects.toString(dateJp.day(), ""));
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
        JapaneseDate dateJp = toJapaneseDate(baseDate);
       // worksheets.getRangeByName(sheetName + "!A1_1_1").setValue(Objects.toString(dateJp.year(), ""));
        worksheets.getRangeByName(sheetName + "!A1_1_2").setValue(Objects.toString(dateJp.month(), ""));
        worksheets.getRangeByName(sheetName + "!A1_1_3").setValue(Objects.toString(dateJp.day(), ""));
        worksheets.getRangeByName(sheetName + "!A1_2").setValue(Objects.toString(data.getOfficeNumber1() , ""));
        worksheets.getRangeByName(sheetName + "!A1_3").setValue(Objects.toString(data.getOfficeNumber2(), ""));
        worksheets.getRangeByName(sheetName + "!A1_4").setValue(Objects.toString(data.getOfficeNumber(), ""));
        worksheets.getRangeByName(sheetName + "!A1_5").setValue(Objects.toString(company.postCd, ""));
        worksheets.getRangeByName(sheetName + "!A1_6").setValue(Objects.toString(company.add_1, ""));
        worksheets.getRangeByName(sheetName + "!A1_7").setValue(Objects.toString(company.add_2, ""));
        worksheets.getRangeByName(sheetName + "!A1_8").setValue(Objects.toString(company.companyName, ""));
        worksheets.getRangeByName(sheetName + "!A1_9").setValue(Objects.toString(company.repname, ""));
    }

    private void selectEra(WorksheetCollection worksheets, String era, String sheetName, int stt){
        if(era.equals(SHOWA)) {
            worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_7" : "A2_7" +  "_" + ++stt).getName();
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_7" : "A2_7" +  "_" + ++stt));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_8" : "A2_8" +  "_" + ++stt));
        }

        if(era.equals(HEISEI)) {
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_6" : "A2_6" +  "_" + ++stt));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_8" : "A2_8" +  "_" + ++stt));
        }

        if(era.equals(PEACE)) {
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_6" : "A2_6" +  "_" + ++stt));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_7" : "A2_7" +  "_" + ++stt));
        }
    }

    private void fillEmployeeUnderSeventy(WorksheetCollection worksheets, InsLossDataExport data, String sheetName, int stt){
        JapaneseDate dateJp = toJapaneseDate( GeneralDate.fromString(data.getBirthDay().substring(0,10), "yyyy-MM-dd"));
        JapaneseDate endDate = toJapaneseDate( GeneralDate.fromString(data.getEndDate().substring(0,10), "yyyy-MM-dd"));
        this.selectEra(worksheets, dateJp.era(), sheetName, stt);
        this.selectCause(worksheets, data.getCause(), sheetName, stt);
        this.selectMoreEmp(worksheets, data.getIsMoreEmp(), sheetName, stt);
        this.selectContinReemAfterRetirement(worksheets, data.getContinReemAfterRetirement(), sheetName, stt);
        this.selectOther(worksheets, data.getOther(), sheetName, stt);
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_1" : sheetName + "!A2_1" + "_" + ++stt).setValue(Objects.toString(data.getHealInsNumber(), ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_2" : sheetName + "!A2_2" + "_" + ++stt).setValue(Objects.toString(data.getPersonName(), ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_3" : sheetName + "!A2_3" + "_" + ++stt).setValue(Objects.toString(data.getPersonNameKana(), ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_4" : sheetName + "!A2_4" + "_" + ++stt).setValue(Objects.toString(data.getOldName(), ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_5" : sheetName + "!A2_5" + "_" + ++stt).setValue(Objects.toString(data.getOldName(), ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_9_1" : sheetName + "!A2_9_1" + "_" + ++stt).setValue(Objects.toString(dateJp.toString().charAt(2), ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_9_2" : sheetName + "!A2_9_2" + "_" + ++stt).setValue(Objects.toString(dateJp.toString().charAt(3), ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_9_3" : sheetName + "!A2_9_3" + "_" + ++stt).setValue(Objects.toString(dateJp.toString().charAt(5), ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_9_4" : sheetName + "!A2_9_4" + "_" + ++stt).setValue(Objects.toString(dateJp.toString().charAt(6), ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_9_5" : sheetName + "!A2_9_5" + "_" + ++stt).setValue(Objects.toString(Objects.toString(dateJp.toString().charAt(8), "")));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_9_6" : sheetName + "!A2_9_6" + "_" + ++stt).setValue(Objects.toString(Objects.toString(dateJp.toString().charAt(9), "")));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_13_1" : sheetName + "!A2_13_1" + "_" + ++stt).setValue(Objects.toString(endDate.year(), ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_13_2" : sheetName + "!A2_13_2" + "_" + ++stt).setValue(Objects.toString(endDate.month(), ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_13_3" : sheetName + "!A2_13_3" + "_" + ++stt).setValue(Objects.toString(endDate.day(), ""));
        // check null
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_10_1" : sheetName + "!A2_10_1" +  "_" + ++stt).setValue(data.getPhoneNumber() != null ? data.getPhoneNumber().charAt(0) : ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_10_2" : sheetName + "!A2_10_2" +  "_" + ++stt).setValue(data.getPhoneNumber() != null ? data.getPhoneNumber().charAt(1) : ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_10_3" : sheetName + "!A2_10_3" +  "_" + ++stt).setValue(data.getPhoneNumber() != null ? data.getPhoneNumber().charAt(2) : ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_10_4" : sheetName + "!A2_10_4" +  "_" + ++stt).setValue(data.getPhoneNumber() != null ? data.getPhoneNumber().charAt(3) : ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_10_5" : sheetName + "!A2_10_5" +  "_" + ++stt).setValue(data.getPhoneNumber() != null ? data.getPhoneNumber().charAt(4) : ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_10_6" : sheetName + "!A2_10_6" +  "_" + ++stt).setValue(data.getPhoneNumber() != null ? data.getPhoneNumber().charAt(5) : ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_10_7" : sheetName + "!A2_10_7" +  "_" + ++stt).setValue(data.getPhoneNumber() != null ? data.getPhoneNumber().charAt(6) : ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_10_8" : sheetName + "!A2_10_8" +  "_" + ++stt).setValue(data.getPhoneNumber() != null ? data.getPhoneNumber().charAt(7) : ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_11_1" : sheetName + "!A2_11_1" +  "_" + ++stt).setValue(Objects.toString(data.getEndDate().charAt(1), ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_11_2" : sheetName + "!A2_11_2" +  "_" + ++stt).setValue(Objects.toString(data.getEndDate().charAt(2), ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_11_3" : sheetName + "!A2_11_3" +  "_" + ++stt).setValue(Objects.toString(data.getEndDate().charAt(3), ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_11_4" : sheetName + "!A2_11_4" +  "_" + ++stt).setValue(Objects.toString(data.getEndDate().charAt(4), ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_11_5" : sheetName + "!A2_11_5" +  "_" + ++stt).setValue(Objects.toString(data.getEndDate().charAt(5), ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_11_6" : sheetName + "!A2_11_6" +  "_" + ++stt).setValue(Objects.toString(data.getEndDate().charAt(6), ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_21" : sheetName + "!A2_21" + "_" + ++stt).setValue(Objects.toString(data.getOtherReason(), ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_22" : sheetName + "!A2_22" + "_" + ++stt).setValue(Objects.toString(data.getCaInsurance(), ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_23" : sheetName + "!A2_23" + "_" + ++stt).setValue(Objects.toString(data.getNumRecoved(), ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_25_1" : sheetName + "!A2_25_1" + "_" + ++stt).setValue(Objects.toString(endDate.toString().charAt(2), ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_25_2" : sheetName + "!A2_25_2" + "_" + ++stt).setValue(Objects.toString(endDate.toString().charAt(3), ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_25_3" : sheetName + "!A2_25_3" + "_" + ++stt).setValue(Objects.toString(endDate.toString().charAt(5), ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_25_4" : sheetName + "!A2_25_4" + "_" + ++stt).setValue(Objects.toString(endDate.toString().charAt(6), ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_25_5" : sheetName + "!A2_25_5" + "_" + ++stt).setValue(Objects.toString(endDate.toString().charAt(8), ""));
        worksheets.getRangeByName(stt == 0 ? sheetName + "!A2_25_6" : sheetName + "!A2_25_6" + "_" + ++stt).setValue(Objects.toString(endDate.toString().charAt(9), ""));
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

    private void selectMoreEmp(WorksheetCollection worksheets, int isMoreEmp, String sheetName, int stt){
        if(isMoreEmp == 0) {
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_18" : "A2_18" +  "_" + ++stt));
        }
    }

    private void selectContinReemAfterRetirement(WorksheetCollection worksheets, int continReemAfterRetirement, String sheetName, int stt){
        if(continReemAfterRetirement == 0) {
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_19" : "A2_19" +  "_" + ++stt));
        }
    }

    private void selectOther(WorksheetCollection worksheets, int other, String sheetName, int stt){
        if(other == 0) {
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_20" : "A2_20" +  "_" + ++stt));
        }
    }

    private void selectCause(WorksheetCollection worksheets, int cause,String sheetName, int stt){
        if(cause == ReasonsForLossHealthyIns.RETIREMENT.value){
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_14" : "A2_14" +  "_" + ++stt));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_16" : "A2_16" +  "_" + ++stt));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_17" : "A2_17" +  "_" + ++stt));
        }

        if(cause == ReasonsForLossHealthyIns.DEATH.value){
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_12" : "A2_12" +  "_" + ++stt));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_16" : "A2_16" +  "_" + ++stt));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_17" : "A2_17" +  "_" + ++stt));
        }

        if(cause == ReasonsForLossHealthyIns.ONLY_HEALTHY_INSURANCE.value){
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_14" : "A2_14" +  "_" + ++stt));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_12" : "A2_12" +  "_" + ++stt));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_17" : "A2_17" +  "_" + ++stt));
        }

        if(cause == ReasonsForLossHealthyIns.DISABILITY_AUTHORIZATION.value){
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_14" : "A2_14" +  "_" + ++stt));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_16" : "A2_16" +  "_" + ++stt));
            worksheets.get(sheetName).getShapes().remove(worksheets.get(sheetName).getShapes().get(stt == 0 ? "A2_12" : "A2_12" +  "_" + ++stt));
        }
    }

    private JapaneseDate toJapaneseDate (GeneralDate date) {
        Optional<JapaneseEraName> era = this.adapter.getAllEras().eraOf(date);
        return new JapaneseDate(date, era.get());
    }
}
