package nts.uk.file.pr.infra.core.socinsurnoticreset;

import com.aspose.cells.*;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.CompanyInfor;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.InsLossDataExport;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.LossNotificationInformation;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.NotificationOfLossInsFileGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Stateless
public class NotificationOfLossInsPDFAposeFileGenerator extends AsposeCellsReportGenerator implements NotificationOfLossInsFileGenerator {

    private static final String TEMPLATE_FILE_70 = "report/70歳以上被用者該当届_帳票テンプレート.xlsx";
    private static final String TEMPLATE_FILE_OTHER = "report/被保険者資格喪失届_帳票テンプレート.xlsx";
    private static final String FILE_NAME = "被保険者資格喪失届_帳票テンプレート";


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
            fillData(worksheets, data.getHealthInsLoss(), data.getBaseDate(), company, data.getSocialInsuranceOffice());
            //worksheets.removeAt(0);
            reportContext.saveAsPdf(this.createNewFile(generatorContext,
                    FILE_NAME + "_" + GeneralDateTime.now().toString("yyyyMMddHHmmss") + ".pdf"));
            /*reportContext.saveAsExcel(this.createNewFile(generatorContext,
                    FILE_NAME + "_" + GeneralDateTime.now().toString("yyyyMMddHHmmss") + ".xlsx"));*/
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void fillData(WorksheetCollection worksheets, List<InsLossDataExport> data, GeneralDate baseDate, CompanyInfor company, List<SocialInsuranceOffice> socialInsuranceOffice) {
        try {
            String sheetName = "INS";
            // Main Data
            for (int page = 0; page < data.size(); page += 4) {
                worksheets.get(worksheets.addCopy(0)).setName(sheetName + page/4);
            }
            for (int i = 0; i < data.size(); i++) {
                InsLossDataExport  dataRow = data.get(i);
                fillCompanyHealthy(worksheets, dataRow, baseDate, company,socialInsuranceOffice, sheetName + i/4 + "!");
                fillDataEmployee(worksheets, dataRow, sheetName + i/4 + "!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void fillCompanyHealthy(WorksheetCollection worksheets, InsLossDataExport data, GeneralDate baseDate, CompanyInfor company, List<SocialInsuranceOffice> socialInsuranceOffice, String sheetName){
        SocialInsuranceOffice insOffice = this.findCompany(socialInsuranceOffice, data.getOfficeCd());

        worksheets.getRangeByName(sheetName + "A1_1").setValue(Objects.toString(baseDate, ""));
        worksheets.getRangeByName(sheetName + "A1_2").setValue(Objects.toString(insOffice.getInsuranceMasterInformation().getOfficeOrganizeNumber().getHealthInsuranceOfficeNumber1().get().v() , ""));
        worksheets.getRangeByName(sheetName + "A1_3").setValue(Objects.toString(insOffice.getInsuranceMasterInformation().getOfficeOrganizeNumber().getHealthInsuranceOfficeNumber2().isPresent() ? insOffice.getInsuranceMasterInformation().getOfficeOrganizeNumber().getHealthInsuranceOfficeNumber2().get().v() : "", ""));
        worksheets.getRangeByName(sheetName + "A1_4").setValue(Objects.toString(insOffice.getInsuranceMasterInformation().getHealthInsuranceOfficeNumber().isPresent() ? insOffice.getInsuranceMasterInformation().getHealthInsuranceOfficeNumber().get() : "", ""));
        worksheets.getRangeByName(sheetName + "A1_5").setValue(Objects.toString(company.postCd, ""));
        worksheets.getRangeByName(sheetName + "A1_6").setValue(Objects.toString(company.add_1, ""));
        worksheets.getRangeByName(sheetName + "A1_7").setValue(Objects.toString(company.add_2, ""));
        worksheets.getRangeByName(sheetName + "A1_8").setValue(Objects.toString(company.companyName, ""));
        worksheets.getRangeByName(sheetName + "A1_9").setValue(Objects.toString(company.repname, ""));
    }

    private void fillDataEmployee(WorksheetCollection worksheets, InsLossDataExport data, String sheetName){
        worksheets.getRangeByName(sheetName + "A2_1").setValue(Objects.toString(data.getHealInsNumber(), ""));
        worksheets.getRangeByName(sheetName + "A2_2").setValue(Objects.toString(data.getPersonName(), ""));
        worksheets.getRangeByName(sheetName + "A2_3").setValue(Objects.toString(data.getPersonNameKana(), ""));
        worksheets.getRangeByName(sheetName + "A2_4").setValue(Objects.toString(data.getOldName(), ""));
        /*worksheets.getRangeByName(sheetName + "A2_5").setValue(Objects.toString(data.getOldName(), ""));
        worksheets.getRangeByName(sheetName + "A2_9").setValue(Objects.toString("", ""));
        worksheets.getRangeByName(sheetName + "A2_10").setValue(Objects.toString("", ""));
        worksheets.getRangeByName(sheetName + "A2_11").setValue(Objects.toString(data.getBirthDay(), ""));
        //cells.get(C2_10_ROW, C2_10_COLUMN)
        worksheets.getRangeByName(sheetName + "A2_12").setValue(Objects.toString(data.getBasicPenNumber(), ""));
        worksheets.getRangeByName(sheetName + "A2_18").setValue(Objects.toString("", ""));
        worksheets.getRangeByName(sheetName + "A2_19").setValue(Objects.toString("", ""));
        worksheets.getRangeByName(sheetName + "A2_20").setValue(Objects.toString("", ""));
        worksheets.getRangeByName(sheetName + "A2_21").setValue(Objects.toString(data.getCaInsurance(), ""));
        worksheets.getRangeByName(sheetName + "A2_22").setValue(Objects.toString(data.getNumRecoved(), ""));
        worksheets.getRangeByName(sheetName + "A2_25").setValue(Objects.toString("", ""));*/
    }

    private SocialInsuranceOffice findCompany(List<SocialInsuranceOffice> socialInsuranceOffice, String officeCode){
        Optional<SocialInsuranceOffice> insOffice = socialInsuranceOffice.stream().filter(item -> item.getCode().equals(officeCode)).findFirst();
        return insOffice.orElse(null);
    }
}
