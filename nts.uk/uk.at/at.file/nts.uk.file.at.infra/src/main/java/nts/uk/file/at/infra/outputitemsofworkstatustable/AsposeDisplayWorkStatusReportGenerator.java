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
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;
import nts.uk.file.at.app.export.outputworkstatustable.DisplayWorkStatusReportGenerator;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AsposeDisplayWorkStatusReportGenerator extends AsposeCellsReportGenerator implements DisplayWorkStatusReportGenerator {
    private static final String TEMPLATE_FILE_ADD = "report/KWR003.xlsx";
    private static final String REPORT_FILE_NAME = "勤務状況表";
    private static final String DATE_FORMAT = "yyyy/MM/dd";
    private static final String DAY_OF_WEEK_FORMAT_JP = "E";
    private static final String PDF_EXT = ".pdf";
    private static final String EXCEL_EXT = ".xlsx";
    private static final String PRINT_AREA = "A1:AJ";
    private static final int EXPORT_EXCEL = 2;
    private static final int EXPORT_PDF = 1;
    private static final int MAX_EMP_IN_PAGE = 30;

    @Override
    public void generate(FileGeneratorContext generatorContext, OutPutWorkStatusContent dataSource) {
        try {
            AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE_ADD);
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            Worksheet worksheet = worksheets.get(0);
            settingPage(worksheet, dataSource);
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

    private void settingPage(Worksheet worksheet, OutPutWorkStatusContent dataSource) {
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setPaperSize(PaperSizeType.PAPER_A_4);
        pageSetup.setOrientation(PageOrientationType.LANDSCAPE);
        String companyName = dataSource.getCompanyName();
        pageSetup.setHeader(0, "&7&\"ＭＳ フォントサイズ\"" + companyName);
        pageSetup.setHeader(1, "&12&\"ＭＳ フォントサイズ\""
                + dataSource.getTitle());

        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter
                .ofPattern("yyyy/MM/dd  H:mm", Locale.JAPAN);
        pageSetup.setHeader(2,
                "&7&\"MS フォントサイズ\"" + LocalDateTime.now().format(fullDateTimeFormatter) + "\n" +
                        TextResource.localize("page") + " &P");
        if (dataSource.getMode() == EXPORT_EXCEL) {
            pageSetup.setZoom(100);
        } else if (dataSource.getMode() == EXPORT_PDF) {
            pageSetup.setFitToPagesTall(0);
            pageSetup.setFitToPagesWide(0);
        }
    }

    private void printContents(Worksheet worksheet, OutPutWorkStatusContent content) throws Exception {
        int countRow = 0;
        Cells cells = worksheet.getCells();
        GeneralDate startDate = content.getPeriod().start();
        GeneralDate endDate = content.getPeriod().end();
        int maxColumnData = getDateRange(startDate, endDate) + 4;
        int maxColumn = cells.getMaxColumn();
        int maxRow = cells.getMaxRow();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM");
        String date = DATE_FORMAT.format(endDate.date());
        cells.clearContents(0, 0, maxRow, maxColumn);
        cells.merge(0, 0, 1, maxColumnData);
        cells.get(0, 0).setValue(TextResource.localize("KWR003_401") + date);

        if (content.getExcelDtoList().size() > 0) {
            printDayOfWeekHeader(worksheet, content.getPeriod(), countRow);
            printData(worksheet, content);
        }
    }

    public void printData(Worksheet worksheet, OutPutWorkStatusContent content) throws Exception {
        boolean isWplPrinted = false;
        Cells cells = worksheet.getCells();
        HorizontalPageBreakCollection pageBreaks = worksheet.getHorizontalPageBreaks();
        int pages = 1;
        int countRow = 3;
        int countItem = 3;
        GeneralDate startDate = content.getPeriod().start();
        GeneralDate endDate = content.getPeriod().end();
        val isZeroDisplay = content.isZeroDisplay();
        int maxColumnData = getDateRange(startDate, endDate) + 4;
        int maxColumn = cells.getMaxColumn();
        val listDate = content.getPeriod().datesBetween();
        for (int q = 0; q < content.getExcelDtoList().size(); q++) {
            // Get data
            val dataSource = content.getExcelDtoList().get(q);
            // Check and break page
            if (content.isPageBreak()) {
                if (q != 0) {
                    pageBreaks.add(countRow);
                    pages++;
                    // countRow = MAX_EMP_IN_PAGE * q;
                    cells.copyRows(cells, 0, countRow, 3);
                    countRow += 3;
                    countItem = 3;
                }
            }

            cells.copyRow(cells, 3, countRow);
            cells.clearContents(CellArea.createCellArea(countRow, 0, countRow, maxColumn));
            cells.merge(countRow, 0, 1, maxColumnData, true, true);
            cells.get(countRow, 0).getStyle().setVerticalAlignment(TextAlignmentType.LEFT);
            cells.get(countRow, 0).setValue("★ " + TextResource.localize("KWR003_404") + dataSource.getWorkPlaceCode() + "  " + dataSource.getWorkPlaceName());
            isWplPrinted = true;
            countRow++;
            countItem++;
            val data = dataSource.getData();
            if (data.isEmpty()) continue;
            for (int i = 0; i < data.size(); i++) {
                val detail = data.get(i);
                if (detail == null || detail.getOutputItemOneLines() == null || detail.getOutputItemOneLines().size() == 0)
                    continue;
                // Check and break page
                if (content.isPageBreak()) {
                    val check = checkAndBreakPage(worksheet, countItem, pages);
                    if (check != null) {
                        //countRow = check.getCount();
                        if (check.isBreak()) {
                            pages += 1;
                            cells.copyRows(cells, 0, countRow, 3);
                            countRow += 3;
                            countItem = 3;
                            cells.copyRow(cells, 3, countRow);
                            cells.clearContents(CellArea.createCellArea(countRow, 0, countRow, maxColumn));
                            cells.merge(countRow, 0, 1, maxColumnData, true, true);
                            cells.get(countRow, 0).getStyle().setVerticalAlignment(TextAlignmentType.LEFT);
                            cells.get(countRow, 0).setValue("★ " + TextResource.localize("KWR003_404") + dataSource.getWorkPlaceCode() + "  " + dataSource.getWorkPlaceName());
                            countRow++;
                            countItem++;
                        }
                    }
                }


                val checkCountRow = detail.getOutputItemOneLines().size() - (MAX_EMP_IN_PAGE - countItem);
                val code = detail.getEmployeeCode();
                val name = detail.getEmployeeName();
                if (checkCountRow > 0) {

                    if (isWplPrinted) {
                        //cells.deleteRow(countRow -1);
                        cells.clearRange(countRow - 1, 0, countRow - 1, maxColumn);
                        System.out.println("Index:" + "-----------------" + countRow);
                        countRow--;

                    }
                    pageBreaks.add(countRow);
                    pages++;
                    //countRow += MAX_EMP_IN_PAGE - countItem;
                    pages += 1;
                    cells.copyRows(cells, 0, countRow, 3);
                    countRow += 3;
                    countItem = 3;
                    cells.copyRow(cells, 3, countRow);
                    cells.clearContents(CellArea.createCellArea(countRow, 0, countRow, maxColumn));
                    cells.merge(countRow, 0, 1, maxColumnData, true, true);
                    cells.get(countRow, 0).getStyle().setVerticalAlignment(TextAlignmentType.LEFT);
                    cells.get(countRow, 0).setValue("★ " + TextResource.localize("KWR003_404") + dataSource.getWorkPlaceCode() + "  " + dataSource.getWorkPlaceName());
                    countRow++;
                    countItem++;

                }

                cells.copyRow(cells, 4, countRow);
                cells.clearContents(CellArea.createCellArea(countRow, 0,
                        countRow, maxColumn));
                cells.merge(countRow, 0, 1, maxColumnData, true, true);
                cells.get(countRow, 0).getStyle().setVerticalAlignment(TextAlignmentType.LEFT);
                cells.get(countRow, 0).setValue("★ " + TextResource.localize("KWR003_405") + code + "   " + name);
                isWplPrinted = false;
                countRow++;
                countItem++;
                val itemOneLines = detail.getOutputItemOneLines();
                for (int k = 0; k < itemOneLines.size(); k++) {
                    if (content.isPageBreak()) {
                        val check = checkAndBreakPage(worksheet, countItem, pages);
                        if (check != null) {
                            countRow = check.getCount();
                            if (check.isBreak()) {
                                pages += 1;
                                cells.copyRows(cells, 0, countRow, 3);
                                countRow += 3;
                                countItem = 3;
                                cells.copyRow(cells, 3, countRow);
                                cells.clearContents(CellArea.createCellArea(countRow, 0, countRow, maxColumn));
                                cells.merge(countRow, 0, 1, maxColumnData, true, true);
                                cells.get(countRow, 0).getStyle().setVerticalAlignment(TextAlignmentType.LEFT);
                                cells.get(countRow, 0).setValue("★ " + TextResource.localize("KWR003_404") + dataSource.getWorkPlaceCode() + "  " + dataSource.getWorkPlaceName());
                                countRow++;
                                countItem++;
                                cells.copyRow(cells, 4, countRow);
                                cells.clearContents(CellArea.createCellArea(countRow, 0, countRow, maxColumn));
                                cells.merge(countRow, 0, 1, maxColumnData, true, true);
                                cells.get(countRow, 0).getStyle().setVerticalAlignment(TextAlignmentType.LEFT);
                                cells.get(countRow, 0).setValue("★ " + TextResource.localize("KWR003_405") + code + "   " + name);
                                countRow++;
                                countItem++;
                            }
                        }
                    }
                    val itemOneLine = itemOneLines.get(k);
                    val listItem = itemOneLine.getOutItemValue().parallelStream().map(r ->
                            new PrintOneLineDto(
                                    itemOneLine.getTotalOfOneLine(),
                                    itemOneLine.getOutPutItemName(),
                                    r,
                                    r.getDate()
                            )).collect(Collectors.toList());
                    if (listItem.size() == 0) {
                        if (k % 2 == 0) {
                            cells.copyRow(cells, 5, countRow);
                        } else {
                            cells.copyRow(cells, 6, countRow);
                        }
                        cells.clearContents(CellArea.createCellArea(countRow, 0, countRow, maxColumn));
                        cells.get(countRow, 1).setValue(itemOneLine.getOutPutItemName());

                        cells.merge(countRow, 0, 1, 3, true, true);

                        cells.get(countRow, maxColumnData).getStyle().setVerticalAlignment(TextAlignmentType.RIGHT);
                        cells.get(countRow, maxColumnData).setValue("");
                        cells.merge(countRow, maxColumnData, 1, 2, true, true);
                        countRow++;
                        countItem++;
                        continue;
                    }
                    if (k % 2 == 0) {
                        cells.copyRow(cells, 5, countRow);
                    } else {
                        cells.copyRow(cells, 6, countRow);
                    }
                    cells.clearContents(CellArea.createCellArea(countRow, 0, countRow, maxColumn));

                    for (int j = 0; j < listItem.size(); j++) {
                        val item = listItem.get(j);
                        val column = listDate.indexOf(item.getDate()) + 3;
                        cells.get(countRow, 1).setValue(item.getOutPutItemName());

                        cells.merge(countRow, 0, 1, 3, true, true);

                        cells.get(countRow, maxColumnData).getStyle()
                                .setVerticalAlignment(TextAlignmentType.RIGHT);
                        cells.get(countRow, maxColumnData).setValue(formatValue(item.getTotalOfOneLine(), null, item.getDailyValue().getAttributes(), isZeroDisplay));
                        cells.merge(countRow, maxColumnData, 1, 2, true, true);
                        cells.get(countRow, column).getStyle().setVerticalAlignment(TextAlignmentType.RIGHT);
                        if (item.getDailyValue() == null) continue;
                        cells.get(countRow, column).setValue(formatValue(item.getDailyValue().getActualValue(), item.getDailyValue().getCharacterValue(),
                                item.getDailyValue().getAttributes(), isZeroDisplay));
                    }
                    countRow++;
                    countItem++;
                }
            }

        }
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setPrintArea(PRINT_AREA + countRow);
    }

    private PrintPage checkAndBreakPage(Worksheet worksheet, int countRowItem, int numberPage) {
        HorizontalPageBreakCollection pageBreaks = worksheet.getHorizontalPageBreaks();
        if (countRowItem > MAX_EMP_IN_PAGE) {
            pageBreaks.add(countRowItem);
            return new PrintPage(
                    numberPage + 1,
                    numberPage * MAX_EMP_IN_PAGE,
                    true);
        }
        return null;
    }

    private void printDayOfWeekHeader(Worksheet worksheet, DatePeriod datePeriod, int countRow) {
        Cells cells = worksheet.getCells();
        GeneralDate startDate = datePeriod.start();
        GeneralDate endDate = datePeriod.end();
        cells.merge(countRow + 1, 0, 2, 3, true, true);
        val maxColumnData = getDateRange(startDate, endDate);
        cells.get(countRow + 1, 0).setValue(TextResource.localize("KWR003_402"));

        cells.get(countRow + 1, maxColumnData + 4).setValue(TextResource.localize("KWR003_403"));
        cells.merge(countRow + 1, maxColumnData + 4, 2, 2, true, true);
        for (int i = 0; i <= maxColumnData; i++) {
            cells.setColumnWidth(3 + i, 3);
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


    private String formatValue(double valueDouble, String valueString, CommonAttributesOfForms attributes,
                               Boolean isZeroDisplay) {
        String rs = "";
        switch (attributes) {
            case WORK_TYPE:
                rs = valueString;
                break;
            case WORKING_HOURS:
                rs = valueString;
                break;
            case TIME_OF_DAY:
                rs = convertToTime((int) valueDouble);
                break;
            case TIME:
                val minute = (int) valueDouble;
                if (minute != 0 || isZeroDisplay) {
                    rs = convertToTime(minute);
                }
                break;
            case NUMBER_OF_TIMES:
                if (valueDouble != 0 || isZeroDisplay) {
                    DecimalFormat formatter1 = new DecimalFormat("#.#");
                    rs = formatter1.format(valueDouble) + TextResource.localize("KWR_2");
                }
                break;
            case DAYS:
                if (valueDouble != 0 || isZeroDisplay) {
                    DecimalFormat formatter2 = new DecimalFormat("#.#");
                    rs = formatter2.format(valueDouble) +  TextResource.localize("KWR_1");
                }
                break;
            case AMOUNT_OF_MONEY:
                if (valueDouble != 0 || isZeroDisplay) {
                    DecimalFormat formatter3 = new DecimalFormat("#,###");
                    rs = formatter3.format((int) valueDouble) +  TextResource.localize("KWR_3");
                }
                break;
        }
        return rs;
    }

    private String convertToTime(int minute) {
        val minuteAbs = Math.abs(minute);
        int hours = minuteAbs / 60;
        int minutes = minuteAbs % 60;
        return (minute < 0 ? "-" : "") + String.format("%d:%02d", hours, minutes);
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class PrintOneLineDto {
        private double totalOfOneLine;
        private String outPutItemName;
        private DailyValue dailyValue;
        private GeneralDate date;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class PrintPage {
        private int pageNumber;
        private int count;
        private boolean isBreak;
    }

}
