package nts.uk.file.at.infra.outputitemsofworkstatustable;

import com.aspose.cells.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.DailyValue;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.OutPutWorkStatusContent;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;;
import nts.uk.file.at.app.export.outputworkstatustable.DisplayWorkStatusReportGenerator;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;


@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AsposeDisplayWorkStatusReportGenerator extends AsposeCellsReportGenerator implements DisplayWorkStatusReportGenerator {
    private static final String TEMPLATE_FILE_ADD = "report/KWR003.xlsx";
    private static final String REPORT_FILE_NAME = "帳票設計書-KWR003_勤務状況表";
    private static final String DATE_FORMAT = "yyyy/MM/dd";
    private static final String DAY_OF_WEEK_FORMAT_JP = "E";
    private static final String PDF_EXT = ".pdf";
    private static final String EXCEL_EXT = ".xlsx";
    private static final String PRINT_AREA = "A1:AJ";
    private static final int EXPORT_EXCEL = 2;
    private static final int EXPORT_PDF = 1;
    private static final int MAX_EMP_IN_PAGE = 15;


    @Override
    public void generate(FileGeneratorContext generatorContext, OutPutWorkStatusContent dataSource) {
        try {
            AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE_ADD);
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            Worksheet worksheet = worksheets.get(0);
            printContents(worksheet, dataSource);
            worksheets.setActiveSheetIndex(0);
            reportContext.processDesigner();
            String fileName = REPORT_FILE_NAME;
            if (dataSource.getMode() == EXPORT_EXCEL) {
                // save as excel file
                reportContext.saveAsExcel(this.createNewFile(generatorContext, fileName + EXCEL_EXT));
            } else if (dataSource.getMode() == EXPORT_PDF) {
                // save as PDF file
                reportContext.saveAsPdf(this.createNewFile(generatorContext, fileName + PDF_EXT));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void settingPage(Worksheet worksheet, OutPutWorkStatusContent dataSource, int countPrint) {
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setPaperSize(PaperSizeType.PAPER_A_4);
        pageSetup.setOrientation(PageOrientationType.LANDSCAPE);
        String companyName = dataSource.getCompanyName();
        pageSetup.setHeader(0, "&7&\"ＭＳ フォントサイズ\"" + companyName);
        pageSetup.setHeader(1, "&12&\"ＭＳ フォントサイズ\""
                + dataSource.getTitle());
        pageSetup.setPrintArea(PRINT_AREA + countPrint);
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter
                .ofPattern("yyyy/MM/dd  H:mm", Locale.JAPAN);
        worksheet.getPageSetup().setHeader(2,
                "&7&\"MS フォントサイズ\"" + LocalDateTime.now().format(fullDateTimeFormatter) + "\n" +
                        TextResource.localize("page") + " &P");
        if (dataSource.getMode() == EXPORT_EXCEL) {
            pageSetup.setZoom(60);
        } else if (dataSource.getMode() == EXPORT_PDF) {
            pageSetup.setFitToPagesTall(0);
            pageSetup.setFitToPagesWide(0);
        }
    }

    private void printContents(Worksheet worksheet, OutPutWorkStatusContent content) throws Exception {
        int countRow = 0;
        int countPage = 1;
        Cells cells = worksheet.getCells();
        GeneralDate startDate = content.getPeriod().start();
        GeneralDate endDate = content.getPeriod().end();
        int maxColumnData = getDateRange(startDate, endDate) + 4;
        boolean isPageFirst = true;
        val isZeroDisplay = content.isZeroDisplay();
        int maxColumn = cells.getMaxColumn();
        int maxRow = cells.getMaxRow();
        // clear content
        boolean isPageBreak = content.isPageBreak();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM");
        String date = DATE_FORMAT.format(startDate.date());
        cells.clearContents(0, 0, maxRow, maxColumn);
        cells.merge(0, 0, 1, maxColumnData);
        cells.get(0, 0).setValue(TextResource.localize("KWR003_401") +date);
        printDayOfWeekHeader(worksheet, content.getPeriod(), countRow);

        countRow += 3;
        int countItem = 0;
        for (int q = 0; q < content.getExcelDtoList().size(); q++) {

            val dataSource = content.getExcelDtoList().get(q);
            cells.copyRow(cells, 3, countRow);
            cells.clearContents(CellArea.createCellArea(countRow, 0, countRow, maxColumn));
            cells.merge(countRow, 0, 1, maxColumnData);
            cells.get(countRow, 0).getStyle().setVerticalAlignment(TextAlignmentType.LEFT);
            cells.get(countRow, 0).setValue(TextResource.localize("KWR003_404")
                    + dataSource.getWorkPlaceCode() + "  " +
                    dataSource.getWorkPlaceName());
            countRow++;

            for (int i = 0; i < dataSource.getData().size(); i++) {

                cells.copyRow(cells, 4, countRow);
                cells.clearContents(CellArea.createCellArea(countRow, 0,
                        countRow, maxColumn));
                cells.merge(countRow, 0, 1, maxColumnData);
                cells.get(countRow, 0).getStyle().setVerticalAlignment(TextAlignmentType.LEFT);
                cells.get(countRow, 0).setValue(TextResource.localize("KWR003_405")
                        + dataSource.getData().get(i).getEmployeeCode() + "   " +
                        dataSource.getData().get(i).getEmployeeName());
                countRow++;
                for (int k = 0; k < dataSource.getData().get(i).getOutputItemOneLines().size(); k++) {
                    if (countItem > MAX_EMP_IN_PAGE) {
                        countRow +=1;
                        String cellBreak = "A" + (countRow);
                        worksheet.addPageBreaks(cellBreak);
                        cells.copyRow(cells, 0, countRow -1);
                        cells.copyRow(cells, 1, countRow);
                        cells.copyRow(cells, 2, countRow + 1);
                        cells.clearContents(CellArea.createCellArea(countRow, 0, countRow + 1, maxColumn));
                        printDayOfWeekHeader(worksheet, content.getPeriod(), countRow - 1);
                        countRow += 2;
                        cells.copyRow(cells, 3, countRow);
                        cells.get(countRow, 0).getStyle().setVerticalAlignment(TextAlignmentType.LEFT);
                        cells.get(countRow, 0).setValue(TextResource.localize("KWR003_404")
                                + dataSource.getWorkPlaceCode() + "  " +
                                dataSource.getWorkPlaceName());
                        countPage++;
                        countRow++;
                        cells.copyRow(cells, 4, countRow);
                        cells.get(countRow, 0).getStyle().setVerticalAlignment(TextAlignmentType.LEFT);
                        cells.get(countRow, 0).setValue(TextResource.localize("KWR003_405")
                                + dataSource.getData().get(i).getEmployeeCode() + "  " +
                                dataSource.getData().get(i).getEmployeeName());
                        countRow++;
                        countItem = 0;
                        isPageFirst = false;
                    }
                    if (k % 2 == 0) {
                        cells.copyRow(cells, 5, countRow);
                    } else {
                        cells.copyRow(cells, 6, countRow);
                    }
                    cells.clearContents(CellArea.createCellArea(countRow, 0, countRow, maxColumn));

                    val listItem = new ArrayList<PrintOneLineDto>();
                    for (int j = 0; j <= getDateRange(startDate, endDate); j++) {
                        GeneralDate loopDate = startDate.addDays(j);
                        val dailyValue = dataSource.getData().get(i).getOutputItemOneLines().get(k)
                                .getOutItemValue().stream()
                                .filter(x -> x.getDate().equals(loopDate)).findFirst().orElse(null);
                        listItem.add(new PrintOneLineDto(
                                dataSource.getData().get(i).getOutputItemOneLines().get(k).getTotalOfOneLine(),
                                dataSource.getData().get(i).getOutputItemOneLines().get(k).getOutPutItemName(),
                                dailyValue
                        ));
                    }
                    for (int j = 0; j < listItem.size(); j++) {
                        cells.get(countRow, 1).setValue(listItem.get(j).getOutPutItemName());
                        if(isPageFirst)
                        cells.merge(countRow, 0, 1, 3);

                        if (listItem.get(j).getDailyValue() != null) {
                            cells.get(countRow, maxColumnData).getStyle()
                                    .setVerticalAlignment(TextAlignmentType.RIGHT);
                            cells.get(countRow, maxColumnData)
                                    .setValue(formatValue(listItem.get(j).getTotalOfOneLine(),
                                            null, listItem.get(j).getDailyValue().getAttributes(), isZeroDisplay));
                            cells.merge(countRow, maxColumnData, 1, 2);
                            cells.get(countRow, j + 3).getStyle()
                                    .setVerticalAlignment(TextAlignmentType.RIGHT);
                            cells.get(countRow, j + 3)
                                    .setValue(formatValue(listItem.get(j).getDailyValue().getActualValue(),
                                            listItem.get(j).getDailyValue().getCharacterValue(),
                                            listItem.get(j).getDailyValue().getAttributes(), isZeroDisplay));
                        }
                    }

                    countRow++;
                    countItem++;
                }
            }
            settingPage(worksheet, content, countRow);
            if(isPageBreak){
                cells.copyRow(cells, 0, countRow );
                cells.clearContents(CellArea.createCellArea(countRow, 0,
                        countRow, maxColumn));
                countRow++;
            }
        }

    }

    private void printDayOfWeekHeader(Worksheet worksheet, DatePeriod datePeriod, int countRow) {
        Cells cells = worksheet.getCells();
        GeneralDate startDate = datePeriod.start();
        GeneralDate endDate = datePeriod.end();
        cells.merge(countRow + 1, 0, 2, 3);
        val maxColumnData = getDateRange(startDate, endDate);
        cells.get(countRow + 1, 0).setValue(TextResource.localize("KWR003_402"));

        cells.get(countRow + 1, maxColumnData+4).setValue(TextResource.localize("KWR003_403"));
        cells.merge(countRow + 1, maxColumnData+4, 2, 2);
        for (int i = 0; i <= maxColumnData; i++) {
            cells.setColumnWidth(3 + i, 2.8);
            GeneralDate loopDate = startDate.addDays(i);
            cells.get(countRow + 1, i + 3).setValue(loopDate.day());
            cells.get(countRow + 2, i + 3).setValue("("
                    + getDayOfWeekJapan(loopDate, DAY_OF_WEEK_FORMAT_JP + ")"));
        }
    }

    private int getDateRange(GeneralDate startDate, GeneralDate endDate) {
        if (endDate.year() - startDate.year() > 0) {
            int startYear = startDate.year();
            int endYear = endDate.year();
            GeneralDate beforeYearEndDate = GeneralDate.fromString(startYear + "/12/31", DATE_FORMAT);
            GeneralDate afterYearStartDate = GeneralDate.fromString(endYear + "/01/01", DATE_FORMAT);
            return endDate.dayOfYear() - afterYearStartDate.dayOfYear()
                    + beforeYearEndDate.dayOfYear() - startDate.dayOfYear() + 1;
        } else {
            return endDate.dayOfYear() - startDate.dayOfYear();
        }
    }

    private String getDayOfWeekJapan(GeneralDate date, String formatDate) {
        SimpleDateFormat jp = new SimpleDateFormat(formatDate, Locale.JAPAN);
        return jp.format(date.date());
    }

    private String formatValue(double valueDouble, String valueString, CommonAttributesOfForms attributes, Boolean isZeroDisplay) {
        String rs = "";
        if (isZeroDisplay) {
            if (valueDouble == 0 || valueString.equals(""))
                return rs;
        }
        switch (attributes) {

            case TIME:
                // HH:mm　(マイナスあり)
                rs = convertToTime((int) valueDouble);
                break;
            case HOURS:
                // HH:mm　(マイナスあり)
                rs = convertToTime((int) valueDouble);
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

    private String convertToTime(int minute) {
        int hour = minute / 60;
        int minutes = minute % 60;
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
