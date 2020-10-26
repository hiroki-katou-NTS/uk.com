package nts.uk.file.at.infra.outputitemsofworkstatustable;

import com.aspose.cells.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.DailyValue;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;
import nts.uk.file.at.app.export.outputitemsofworkstatustable.DisplayContentReportData;
import nts.uk.file.at.app.export.outputitemsofworkstatustable.DisplayWorkStatusReportGenerator;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;


@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AsposeDisplayWorkStatusReportGenerator extends AsposeCellsReportGenerator implements DisplayWorkStatusReportGenerator {
    private static final String TEMPLATE_FILE_ADD = "report/KWR003.xlsx";
    private static final String REPORT_FILE_NAME = "KWR003.xlsx";
    private static final String COMPANY_ERROR = "Company is not found!!!!";
    private static final String DATE_FORMAT = "yyyy/MM/dd";
    private static final String DAY_OF_WEEK_FORMAT_JP = "E";
    @Inject
    private CompanyAdapter company;

    @Override
    public void generate(FileGeneratorContext generatorContext, DisplayContentReportData dataSource) {
        try {
            AsposeCellsReportContext designer = this.createContext(TEMPLATE_FILE_ADD);
            Workbook workbook = designer.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            Worksheet worksheet = worksheets.get(0);
            printPage(worksheet, dataSource);

            printContent(worksheet, dataSource);
            designer.getDesigner().setWorkbook(workbook);
            designer.processDesigner();
            designer.saveAsExcel(this.createNewFile(generatorContext, this.getReportName(REPORT_FILE_NAME)));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void printPage(Worksheet worksheet, DisplayContentReportData dataSource) {
        PageSetup pageSetup = worksheet.getPageSetup();
        String companyName = company.getCurrentCompany().orElseThrow(() -> new RuntimeException(COMPANY_ERROR))
                .getCompanyName();
        pageSetup.setFirstPageNumber(1);
        pageSetup.setHeader(0, "&7&\"ＭＳ フォントサイズ\"" + companyName);
        pageSetup.setHeader(1, "&12&\"ＭＳ フォントサイズ\"" + TextResource.localize("KWR003_400"));
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd  H:mm", Locale.JAPAN);
        worksheet.getPageSetup().setHeader(2,
                "&7&\"MS フォントサイズ\"" + LocalDateTime.now().format(fullDateTimeFormatter) + "\n" +
                        TextResource.localize("page") + " &P");
    }

    private void printContent(Worksheet worksheet, DisplayContentReportData dataSource) throws Exception {
        Cells cells = worksheet.getCells();
        cells.get(0, 1).setValue(TextResource.localize("KWR003_401") + GeneralDate.today());
        GeneralDate startDate = dataSource.getPeriod().start();
        GeneralDate endDate = dataSource.getPeriod().end();
        int maxRow = cells.getMaxRow();
        int maxColumn = cells.getMaxColumn();
        for (int i = 0; i < dataSource.getData().size(); i++) {

            printDayOfWeekHeader(worksheet, dataSource);
            cells.merge(3 + i, 0,1,34);
            cells.get(3 + i, 0).getStyle().setVerticalAlignment(TextAlignmentType.LEFT);
            cells.get(3 + i, 0).setValue(TextResource.localize("KWR003_404") + dataSource.getData().get(i).getWorkPlaceCode() + "  " +
                    dataSource.getData().get(i).getWorkPlaceName());
            cells.merge(4 + i, 0,1,34);
            cells.get(4 + i, 0).getStyle().setVerticalAlignment(TextAlignmentType.LEFT);
            cells.get(4 + i, 0).setValue(TextResource.localize("KWR003_405") + dataSource.getData().get(i).getEmployeeCode() + "   " +
                    dataSource.getData().get(i).getEmployeeName());
            int startDataRow = 5;
            for (int k = 0; k < dataSource.getData().get(i).getOutputItemOneLines().size(); k++) {
                if (k % 2 == 0) {
                    cells.copyRow(cells, 5, startDataRow);
                } else {
                    cells.copyRow(cells, 6, startDataRow);
                }
                cells.clearContents(CellArea.createCellArea(startDataRow, 0, maxRow, maxColumn));
                startDataRow += 1;
                val listItem = new ArrayList<PrintOneLineDto>();
                for (int j = 0; j <= getDateRange(startDate, endDate); j++) {
                    GeneralDate loopDate = startDate.addDays(j);
                    val dailyValue = dataSource.getData().get(i).getOutputItemOneLines().get(k).getOutItemValue().stream()
                            .filter(x -> x.getDate().equals(loopDate)).findFirst().orElse(null);
                    listItem.add(new PrintOneLineDto(
                            dataSource.getData().get(i).getOutputItemOneLines().get(k).getTotalOfOneLine(),
                            dataSource.getData().get(i).getOutputItemOneLines().get(k).getOutPutItemName(),
                            dailyValue
                    ));
                }
                for (int j = 0; j < listItem.size(); j++) {
                    cells.merge(5 + k, 0, 1, 3);
                    cells.get(5 + k, 1).setValue(listItem.get(j).getOutPutItemName());
                    cells.merge(5 + k, 34, 1, 2);

                    if (listItem.get(j).getDailyValue() != null) {
                        cells.get(5 + k, 34).getStyle().setVerticalAlignment(TextAlignmentType.RIGHT);
                        cells.get(5 + k, 34).setValue(formatValue(listItem.get(j).getTotalOfOneLine(),
                                null, listItem.get(j).getDailyValue().getAttributes()));
                        cells.get(5 + k, j + 3).getStyle().setVerticalAlignment(TextAlignmentType.RIGHT);
                        cells.get(5 + k, j + 3)
                                .setValue(formatValue(listItem.get(j).getDailyValue().getActualValue(),
                                        listItem.get(j).getDailyValue().getCharacterValue(),
                                        listItem.get(j).getDailyValue().getAttributes()));
                    }

                }
            }
        }
    }

    private void printDayOfWeekHeader(Worksheet worksheet, DisplayContentReportData dataSource) {
        Cells cells = worksheet.getCells();
        GeneralDate startDate = dataSource.getPeriod().start();
        GeneralDate endDate = dataSource.getPeriod().end();
        cells.merge(1, 0, 2, 3);
        cells.get(1, 0).setValue(TextResource.localize("KWR003_402"));
        cells.get(1, 34).setValue(TextResource.localize("KWR003_403"));
        for (int i = 0; i <= getDateRange(startDate, endDate); i++) {
            cells.setColumnWidth(3 + i,3.5);
            GeneralDate loopDate = startDate.addDays(i);
            cells.get(1, i + 3).setValue(loopDate.day());
            cells.get(2, i + 3).setValue("(" + getDayOfWeekJapan(loopDate, DAY_OF_WEEK_FORMAT_JP + ")"));
        }
    }

    private int getDateRange(GeneralDate startDate, GeneralDate endDate) {
        if (endDate.year() - startDate.year() > 0) {
            int startYear = startDate.year();
            int endYear = endDate.year();
            GeneralDate beforeYearEndDate = GeneralDate.fromString(startYear + "/12/31", DATE_FORMAT);
            GeneralDate afterYearStartDate = GeneralDate.fromString(endYear + "/01/01", DATE_FORMAT);
            return endDate.dayOfYear() - afterYearStartDate.dayOfYear() + beforeYearEndDate.dayOfYear() - startDate.dayOfYear() + 1;
        } else {
            return endDate.dayOfYear() - startDate.dayOfYear();
        }
    }

    private String getDayOfWeekJapan(GeneralDate date, String formatDate) {
        SimpleDateFormat jp = new SimpleDateFormat(formatDate, Locale.JAPAN);
        return jp.format(date.date());
    }

    private String formatValue(double valueDouble, String valueString, CommonAttributesOfForms attributes) throws ParseException {
        String rs = "";

        switch (attributes) {

            case TIME:
                // HH:mm　(マイナスあり)
                rs = converToTime((int) valueDouble);
                break;
            case HOURS:
                // HH:mm　(マイナスあり)
                rs = converToTime((int) valueDouble);
                break;
            case TIMES:
                // 小数点以下は、集計する勤怠項目の小数部桁数に従う(※1)　(マイナスあり)
                rs = valueString;
                break;
            case AMOUNT:
                // ３桁毎のカンマ区切り　(マイナスあり)
                rs = valueString;
                break;
            case DAYS:
                rs = valueString;
                break;
            case WORK_TYPE:
                rs = valueString;
                break;
            case WORKING_HOURS:
                rs = valueString;
                break;
        }
        return rs;
    }

    private String converToTime(int minute) {
        int hour = minute / 60;
        int minutes = minute % 60; // 5 in this case.
        return (hour) + ":" + (minutes);
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class PrintOneLineDto {
        private double totalOfOneLine;
        private String outPutItemName;
        private DailyValue dailyValue;
    }
}
