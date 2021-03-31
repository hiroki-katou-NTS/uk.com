package nts.uk.ctx.at.function.infra.genarate.alarmworkplace.excel;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSettingRepository;
import nts.uk.ctx.at.function.dom.alarmworkplace.export.AlarmListExtractResultWorkplaceData;
import nts.uk.ctx.at.function.dom.alarmworkplace.export.AlarmListWorkPlaceGenerator;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Stateless
public class AlarmListWorkPlaceExportGenerator extends AsposeCellsReportGenerator implements AlarmListWorkPlaceGenerator {

    private static final String TEMPLATE_FILE = "report/KAL011-アラームリスト(職場別).xlsx";
    private static final String COMPANY_ERROR = "Company is not found!!!!";

    @Inject
    private CompanyAdapter company;

    @Inject
    private AlarmPatternSettingRepository alarmPatternSettingRepo;

    @Override
    public void generateExcelScreen(FileGeneratorContext generatorContext,
                                    List<AlarmListExtractResultWorkplaceData> dataSource,
                                    String alarmPatternCode, String alarmPatternName) {
        try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
            setHeaderAndHeaderColumn(reportContext, alarmPatternCode, alarmPatternName);
            // set data source named "item"
            reportContext.setDataSource("item", dataSource);
            // process data binginds in template
            reportContext.processDesigner();
            // save as Excel file
            GeneralDateTime dateNow = GeneralDateTime.now();
            String dateTime = dateNow.toString("yyyyMMddHHmmss");
            String fileName = "AlarmList_" + dateTime + ".xlsx";
            OutputStream outputStream = this.createNewFile(generatorContext, fileName);
            reportContext.saveAsExcel(outputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setHeaderAndHeaderColumn(AsposeCellsReportContext reportContext, String alarmPatternCode, String alarmPatternName) {

        String companyName = company.getCurrentCompany().orElseThrow(() -> new RuntimeException(COMPANY_ERROR))
                .getCompanyName();

        reportContext.getWorkbook().getWorksheets().get(0).getPageSetup().setHeader(0, "&9&\"MS ゴシック\"" + companyName);
        reportContext.getWorkbook().getWorksheets().get(0).getPageSetup().setHeader(1, "&16&\"MS ゴシック\"" + TextResource.localize("KAL011_35"));
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd  H:mm", Locale.JAPAN);
        reportContext.getWorkbook().getWorksheets().get(0).getPageSetup().setHeader(2,
                "&9&\"MS ゴシック\"" + LocalDateTime.now().format(fullDateTimeFormatter) + "\n" + TextResource.localize("KAL001_102") + " &P");
        val cell = reportContext.getWorkbook().getWorksheets().get(0).getCells();
        val name = "  "+ alarmPatternName;
        cell.get(0, 0).setValue(TextResource.localize("KAL011_45", alarmPatternCode, name));
        cell.get(1, 0).setValue(TextResource.localize("KAL011_37"));
        cell.get(1, 1).setValue(TextResource.localize("KAL011_38"));
        cell.get(1, 2).setValue(TextResource.localize("KAL011_39"));
        cell.get(1, 3).setValue(TextResource.localize("KAL011_40"));
        cell.get(1, 4).setValue(TextResource.localize("KAL011_41"));
        cell.get(1, 5).setValue(TextResource.localize("KAL011_42"));
        cell.get(1, 6).setValue(TextResource.localize("KAL011_43"));
        cell.get(1, 7).setValue(TextResource.localize("KAL011_44"));

    }

}