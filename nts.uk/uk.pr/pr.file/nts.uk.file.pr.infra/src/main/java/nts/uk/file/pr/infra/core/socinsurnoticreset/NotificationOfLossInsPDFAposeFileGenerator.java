package nts.uk.file.pr.infra.core.socinsurnoticreset;

import com.aspose.cells.Cells;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;
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

    private static final String TEMPLATE_FILE = "report/被保険者資格喪失届_帳票テンプレート.xlsx";
    private static final String FILE_NAME = "被保険者資格喪失届_帳票テンプレート";
    private static final int C1_1_ROW = 4;
    private static final int C1_1_COLUMN = 2;
    private static final int C1_2_ROW = 6;
    private static final int C1_2_COLUMN = 7;
    private static final int C1_3_ROW = 6;
    private static final int C1_3_COLUMN = 19;
    private static final int C1_4_ROW = 6;
    private static final int C1_4_COLUMN = 35;
    private static final int C1_5_ROW = 11;
    private static final int C1_5_COLUMN = 9;
    private static final int C1_6_ROW = 12;
    private static final int C1_6_COLUMN = 7;
    private static final int C1_7_ROW = 16;
    private static final int C1_7_COLUMN = 7;
    private static final int C1_8_ROW = 20;
    private static final int C1_8_COLUMN = 7;
    private static final int C1_9_ROW = 24;
    private static final int C1_9_COLUMN = 12;
    private static final int C2_1_ROW = 27;
    private static final int C2_1_COLUMN = 13;
    private static final int C2_2_ROW = 27;
    private static final int C2_2_COLUMN = 27;
    private static final int C2_3_ROW = 27;
    private static final int C2_3_COLUMN = 47;
    private static final int C2_4_ROW = 29;
    private static final int C2_4_COLUMN = 27;
    private static final int C2_5_ROW = 29;
    private static final int C2_5_COLUMN = 47;
    private static final int C2_8_ROW = 84;
    private static final int C2_8_COLUMN = 23;
    private static final int C2_9_ROW = 16;
    private static final int C2_9_COLUMN = 12;
    private static final int C2_12_ROW = 25;
    private static final int C2_12_COLUMN = 19;
    private static final int C2_14_ROW = 26;
    private static final int C2_14_COLUMN = 77;
    private static final int C2_20_ROW = 6;
    private static final int C2_20_COLUMN = 7;
    private static final int C2_21_ROW = 6;
    private static final int C2_21_COLUMN = 19;
    private static final int C2_22_ROW = 6;
    private static final int C2_22_COLUMN = 35;
    private static final int C2_24_ROW = 12;
    private static final int C2_24_COLUMN = 7;

    @Override
    public void generate(FileGeneratorContext generatorContext, LossNotificationInformation data) {
        List<SocialInsuranceOffice> socialInsuranceOffice = data.getSocialInsuranceOffice();
        CompanyInfor company = data.getCompany();
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            reportContext.processDesigner();
            fillData(worksheets, data.getHealthInsLoss(), data.getBaseDate(), company, data.getSocialInsuranceOffice());
            worksheets.removeAt(0);
            reportContext.saveAsPdf(this.createNewFile(generatorContext,
                    FILE_NAME + "_" + GeneralDateTime.now().toString("yyyyMMddHHmmss") + ".pdf"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void fillData(WorksheetCollection worksheets, List<InsLossDataExport> data, GeneralDate baseDate, CompanyInfor company, List<SocialInsuranceOffice> socialInsuranceOffice) {
        try {
            String sheetName = "INS";
            Worksheet worksheet = worksheets.get(0);
            Cells cells = worksheet.getCells();
            // Main Data
            for (int page = 0; page < data.size(); page += 4) {
                worksheets.get(worksheets.addCopy(0)).setName(sheetName + page/4);
                worksheet = worksheets.get(sheetName + page/4);
            }
            for (int i = 0; i < data.size(); i++) {
                if (i % 4 == 0) {
                    Worksheet sheet = worksheets.get(sheetName + i/4);
                    cells = sheet.getCells();
                }
                InsLossDataExport  dataRow = data.get(i);
                fillCompanyHealthy(cells, dataRow, baseDate, company,socialInsuranceOffice);
                fillDataEmployee(cells, dataRow);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void fillCompanyHealthy(Cells cells, InsLossDataExport data, GeneralDate baseDate, CompanyInfor company, List<SocialInsuranceOffice> socialInsuranceOffice){
        SocialInsuranceOffice insOffice = this.findCompany(socialInsuranceOffice, data.getOfficeCd());
        cells.get(C1_1_ROW , C1_1_COLUMN).setValue(Objects.toString(baseDate, ""));
        cells.get(C1_2_ROW , C1_2_COLUMN).setValue(Objects.toString(insOffice.getInsuranceMasterInformation().getOfficeOrganizeNumber().getHealthInsuranceOfficeNumber1().get().v() , ""));
        cells.get(C1_3_ROW , C1_3_COLUMN).setValue(Objects.toString(insOffice.getInsuranceMasterInformation().getOfficeOrganizeNumber().getHealthInsuranceOfficeNumber2().get().v(), ""));
        cells.get(C1_4_ROW , C1_4_COLUMN).setValue(Objects.toString(insOffice.getInsuranceMasterInformation().getHealthInsuranceOfficeNumber().get().v(), ""));
        cells.get(C1_5_ROW , C1_5_COLUMN).setValue(Objects.toString(company.postCd, ""));
        cells.get(C1_6_ROW , C1_6_COLUMN).setValue(Objects.toString(company.add_1, ""));
        cells.get(C1_7_ROW , C1_7_COLUMN).setValue(Objects.toString(company.add_2, ""));
        cells.get(C1_8_ROW , C1_8_COLUMN).setValue(Objects.toString(company.companyName, ""));
        cells.get(C1_9_ROW , C1_9_COLUMN).setValue(Objects.toString(company.repname, ""));
    }

    private void fillDataEmployee(Cells cells, InsLossDataExport data){
        cells.get(C2_1_ROW , C2_1_COLUMN).setValue(Objects.toString("", ""));
        cells.get(C2_2_ROW , C2_2_COLUMN).setValue(Objects.toString("", ""));
        cells.get(C2_3_ROW , C2_3_COLUMN).setValue(Objects.toString("", ""));
        cells.get(C2_4_ROW , C2_4_COLUMN).setValue(Objects.toString("", ""));
        cells.get(C2_5_ROW , C2_5_COLUMN).setValue(Objects.toString("", ""));
        cells.get(C2_2_ROW , C2_2_COLUMN).setValue(Objects.toString("", ""));
        cells.get(C2_8_ROW , C2_8_COLUMN).setValue(Objects.toString("", ""));
        cells.get(C2_9_ROW , C2_9_COLUMN).setValue(Objects.toString("", ""));
        cells.get(C2_12_ROW , C2_12_COLUMN).setValue(Objects.toString("", ""));
        cells.get(C2_14_ROW , C2_14_COLUMN).setValue(Objects.toString("", ""));
        cells.get(C2_20_ROW , C2_20_COLUMN).setValue(Objects.toString("", ""));
        cells.get(C2_21_ROW , C2_21_COLUMN).setValue(Objects.toString("", ""));
        cells.get(C2_22_ROW , C2_22_COLUMN).setValue(Objects.toString("", ""));
        cells.get(C2_24_ROW , C2_24_COLUMN).setValue(Objects.toString("", ""));
    }

    private SocialInsuranceOffice findCompany(List<SocialInsuranceOffice> socialInsuranceOffice, String officeCode){
        Optional<SocialInsuranceOffice> insOffice = socialInsuranceOffice.stream().filter(item -> item.getCode().equals(officeCode)).findFirst();
        return insOffice.orElse(null);
    }
}
