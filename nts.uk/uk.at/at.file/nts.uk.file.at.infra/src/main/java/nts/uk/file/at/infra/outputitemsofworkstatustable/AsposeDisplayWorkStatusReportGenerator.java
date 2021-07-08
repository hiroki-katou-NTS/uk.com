package nts.uk.file.at.infra.outputitemsofworkstatustable;

import com.aspose.cells.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.DailyValue;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.OutPutWorkStatusContent;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;
import nts.uk.file.at.app.export.outputworkstatustable.DisplayWorkStatusReportGenerator;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;

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
    private static final String DAY_OF_WEEK_FORMAT_JP = "E";
    private static final String PDF_EXT = ".pdf";
    private static final String EXCEL_EXT = ".xlsx";
    private static final String PRINT_AREA = "A1:AJ";
    private static final int EXPORT_EXCEL = 2;
    private static final int EXPORT_PDF = 1;
    private static final int MAX_EMP_IN_PAGE = 34;
    private static final int MAX_COL_IN_PAGE = 31;

    @Override
    public void generate(FileGeneratorContext generatorContext, OutPutWorkStatusContent dataSource) {
        try {
            AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE_ADD);
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            Worksheet worksheet = worksheets.get(0);
            worksheet.setName(dataSource.getTitle());
            settingPage(worksheet, dataSource);
            printContents(worksheet, dataSource);
            worksheets.setActiveSheetIndex(0);
            reportContext.processDesigner();
            String fileName = dataSource.getTitle() + "_" + GeneralDateTime.now().toString("yyyyMMddHHmmss");
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
        pageSetup.setHeader(0, "&7&\"MSゴシック\"" + companyName);
        pageSetup.setHeader(1, "&12&\"MSゴシック,Bold\""
                + dataSource.getTitle());
        pageSetup.setCenterHorizontally(true);
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter
                .ofPattern("yyyy/MM/dd  H:mm", Locale.JAPAN);
        pageSetup.setHeader(2,
                "&7&\"MSゴシック\"" + LocalDateTime.now().format(fullDateTimeFormatter) + "\n" +
                        TextResource.localize("page") + " &P");
        if (dataSource.getMode() == EXPORT_EXCEL) {
            pageSetup.setZoom(100);
        } else if (dataSource.getMode() == EXPORT_PDF) {
            pageSetup.setFitToPagesTall(0);
            pageSetup.setFitToPagesWide(0);
        }
    }

    private void printContents(Worksheet worksheet, OutPutWorkStatusContent content) throws Exception {
        int countRow = 10;
        Cells cells = worksheet.getCells();
        if (content.getExcelDtoList().size() > 0) {
            printDayOfWeekHeader(worksheet, content, countRow);
            countRow +=3;
            printData(worksheet, content,countRow);
        }
        cells.deleteRows(0,10);
    }

    private void printData(Worksheet worksheet, OutPutWorkStatusContent content,int countRow) throws Exception {
        boolean isWplPrinted;
        Cells cells = worksheet.getCells();
        HorizontalPageBreakCollection pageBreaks = worksheet.getHorizontalPageBreaks();
        int pages = 1;
        int countItem = 3;
        val isZeroDisplay = content.isZeroDisplay();
        int maxColumnData = MAX_COL_IN_PAGE + 5;
        int maxColumn = cells.getMaxColumn();
        val listDate = content.getPeriod().datesBetween();
        for (int q = 0; q < content.getExcelDtoList().size(); q++) {
            // Get data
            val dataSource = content.getExcelDtoList().get(q);
            // Check and break page
            if (content.isPageBreak()) {
                if (q != 0) {
                    for (int index = 0; index <= 35; index++) {
                        setBottomBorderStyle(cells.get(countRow-1 , index));
                    }
                    pageBreaks.add(countRow);
                    pages++;
                    // countRow = MAX_EMP_IN_PAGE * q;
                    cells.copyRows(cells, 10, countRow, 3);
                    countRow += 3;
                    countItem = 3;
                }
            }

            cells.copyRow(cells, 3, countRow);
            cells.clearContents(CellArea.createCellArea(countRow, 0, countRow, maxColumn));
            cells.merge(countRow, 0, 1, maxColumnData, true, true);
            cells.get(countRow, 0).getStyle().setVerticalAlignment(TextAlignmentType.LEFT);
            cells.get(countRow, 0).setValue(TextResource.localize("KWR003_404") + dataSource.getWorkPlaceCode() + " " + dataSource.getWorkPlaceName());
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
                        for (int index = 0; index <= 35; index++) {
                            setBottomBorderStyle(cells.get(countRow-1 , index));
                        }
                        //countRow = check.getCount();
                        if (check.isBreak()) {
                            pages += 1;
                            cells.copyRows(cells, 10, countRow, 3);
                            countRow += 3;
                            countItem = 3;
                            cells.copyRow(cells, 3, countRow);
                            cells.clearContents(CellArea.createCellArea(countRow, 0, countRow, maxColumn));
                            cells.merge(countRow, 0, 1, maxColumnData, true, true);
                            cells.get(countRow, 0).getStyle().setVerticalAlignment(TextAlignmentType.LEFT);
                            cells.get(countRow, 0).setValue(TextResource.localize("KWR003_404") + dataSource.getWorkPlaceCode() + " " + dataSource.getWorkPlaceName());
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
                        cells.clearRange(countRow - 1, 0, countRow - 1, maxColumn);
                        countRow--;

                    }
                    for (int index = 0; index <= 35; index++) {
                        setBottomBorderStyle(cells.get(countRow-1 , index));
                    }
                    pageBreaks.add(countRow);
                    pages++;
                    //countRow += MAX_EMP_IN_PAGE - countItem;
                    pages += 1;
                    cells.copyRows(cells, 10, countRow, 3);
                    countRow += 3;
                    countItem = 3;
                    cells.copyRow(cells, 3, countRow);
                    cells.clearContents(CellArea.createCellArea(countRow, 0, countRow, maxColumn));
                    cells.merge(countRow, 0, 1, maxColumnData, true, true);
                    cells.get(countRow, 0).getStyle().setVerticalAlignment(TextAlignmentType.LEFT);
                    cells.get(countRow, 0).setValue(TextResource.localize("KWR003_404") + dataSource.getWorkPlaceCode() + " " + dataSource.getWorkPlaceName());
                    countRow++;
                    countItem++;

                }

                cells.copyRow(cells, 4, countRow);
                cells.clearContents(CellArea.createCellArea(countRow, 0,
                        countRow, maxColumn));
                cells.merge(countRow, 0, 1, maxColumnData, true, true);
                cells.get(countRow, 0).getStyle().setVerticalAlignment(TextAlignmentType.LEFT);
                cells.get(countRow, 0).setValue(TextResource.localize("KWR003_405") + code + " " + name);
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
                                for (int index = 0; index <= 35; index++) {
                                    setBottomBorderStyle(cells.get(countRow-1 , index));
                                }
                                pages += 1;
                                cells.copyRows(cells, 10, countRow, 3);
                                countRow += 3;
                                countItem = 3;
                                cells.copyRow(cells, 3, countRow);
                                cells.clearContents(CellArea.createCellArea(countRow, 0, countRow, maxColumn));
                                cells.merge(countRow, 0, 1, maxColumnData, true, true);
                                cells.get(countRow, 0).getStyle().setVerticalAlignment(TextAlignmentType.LEFT);
                                cells.get(countRow, 0).setValue(TextResource.localize("KWR003_404") + dataSource.getWorkPlaceCode() + " " + dataSource.getWorkPlaceName());
                                countRow++;
                                countItem++;
                                cells.copyRow(cells, 4, countRow);
                                cells.clearContents(CellArea.createCellArea(countRow, 0, countRow, maxColumn));
                                cells.merge(countRow, 0, 1, maxColumnData, true, true);
                                cells.get(countRow, 0).getStyle().setVerticalAlignment(TextAlignmentType.LEFT);
                                cells.get(countRow, 0).setValue(TextResource.localize("KWR003_405") + code + " " + name);
                                countRow++;
                                countItem++;
                            }
                        }
                    }
                    val itemOneLine = itemOneLines.get(k);
                    val listItem = itemOneLine.getOutItemValue().stream().map(r ->
                            new PrintOneLineDto(
                                    itemOneLine.getTotalOfOneLine(),
                                    itemOneLine.getOutPutItemName(),
                                    r,
                                    r.getDate()
                            )).collect(Collectors.toList());
                    if (listItem.size() == 0) {
                        if (k % 2 == 0) {
                            cells.copyRow(cells, 7, countRow);
                        } else {
                            cells.copyRow(cells, 8, countRow);
                        }
                        cells.clearContents(CellArea.createCellArea(countRow, 0, countRow, maxColumn));
                        cells.get(countRow, 1).setValue(itemOneLine.getOutPutItemName());
                        countRow++;
                        countItem++;
                        continue;
                    }
                    if (k % 2 == 0) {
                        cells.copyRow(cells, 7, countRow);
                    } else {
                        cells.copyRow(cells, 8, countRow);
                    }
                    cells.clearContents(CellArea.createCellArea(countRow, 0, countRow, maxColumn));

                    for (int j = 0; j < listItem.size(); j++) {
                        val item = listItem.get(j);
                        val column = listDate.indexOf(item.getDate()) + 3;
                        cells.get(countRow, 1).setValue(item.getOutPutItemName());
                        cells.get(countRow, maxColumnData - 2).setValue(formatValue(item.getTotalOfOneLine(), null, item.getDailyValue().getAttributes(), isZeroDisplay));

                        if (item.getDailyValue() == null) continue;
                        cells.get(countRow, column).setValue(formatValue(item.getDailyValue().getActualValue(), item.getDailyValue().getCharacterValue(),
                                item.getDailyValue().getAttributes(), isZeroDisplay));
                        Cell cell = cells.get(countRow, column);
                        Style style = cell.getStyle();
                        if (item.getDailyValue().getAttributes() == CommonAttributesOfForms.WORK_TYPE ||
                                item.getDailyValue().getAttributes() == CommonAttributesOfForms.WORKING_HOURS) {
                            style.setHorizontalAlignment(ColumnTextAlign.CENTER.value);
                        } else {
                            style.setHorizontalAlignment(checkText(item.getDailyValue().getAttributes()) ? ColumnTextAlign.LEFT.value : ColumnTextAlign.RIGHT.value);
                        }
                        cell.setStyle(style);
                    }
                    countRow++;
                    countItem++;
                }
            }

        }
        for (int index = 0; index <= 35; index++) {
            setBottomBorderStyle(cells.get(countRow-1 , index));
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

    private void printDayOfWeekHeader(Worksheet worksheet, OutPutWorkStatusContent content, int countRow) throws Exception {
        Cells cells = worksheet.getCells();
        cells.copyRows(cells, 0, countRow, 3);
        GeneralDate endDate = content.getPeriod().end();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM");
        String date = DATE_FORMAT.format(endDate.date());
        cells.merge(countRow, 0, 1, MAX_COL_IN_PAGE + 5);
        cells.get(countRow, 0).setValue(TextResource.localize("KWR003_401") + date);
        DatePeriod datePeriod = content.getPeriod();
        GeneralDate startDate = datePeriod.start();

        cells.merge(countRow + 1, 0, 2, 3, true, true);
        val maxColumnData = MAX_COL_IN_PAGE;
        cells.get(countRow + 1, 0).setValue(TextResource.localize("KWR003_402"));
        cells.get(countRow + 1, maxColumnData + 4).setValue(TextResource.localize("KWR003_403"));
        cells.merge(countRow + 1, maxColumnData + 3, 2, 2, true, true);
        for (int i = 0; i < maxColumnData; i++) {
            GeneralDate loopDate = startDate.addDays(i);
            if (!loopDate.beforeOrEquals(datePeriod.end())) {
                cells.get(countRow + 1, i + 3).setValue("");
                cells.get(countRow + 2, i + 3).setValue("");
            } else {
                cells.get(countRow + 1, i + 3).setValue(loopDate.day());
                cells.get(countRow + 2, i + 3).setValue("("
                        + getDayOfWeekJapan(loopDate, DAY_OF_WEEK_FORMAT_JP + ")"));
            }
        }
    }
    private String getDayOfWeekJapan(GeneralDate date, String formatDate) {
        SimpleDateFormat jp = new SimpleDateFormat(formatDate, Locale.JAPAN);
        return jp.format(date.date());
    }


    private String formatValue(Double valueDouble, String valueString, CommonAttributesOfForms attributes,
                               Boolean isZeroDisplay) {
        String rs = "";
        switch (attributes) {
            case WORK_TYPE:
                rs = valueString;
                break;
            case WORKING_HOURS:
                rs = valueString;
                break;
            case OTHER_CHARACTER_NUMBER:
                rs = valueString;
                break;
            case OTHER_CHARACTERS:
                rs = valueString;
                break;
            case OTHER_NUMERICAL_VALUE:
                rs = valueString;
                break;
            case TIME_OF_DAY:
                if (valueDouble != null) {
                    rs = convertToTime((int) valueDouble.intValue());
                }
                break;
            case TIME:
                if (valueDouble != null) {
                    val minute = (int) valueDouble.intValue();
                    if (minute != 0 || isZeroDisplay) {
                        rs = convertToTime(minute);
                    }
                }
                break;
            case NUMBER_OF_TIMES:
                if (valueDouble != null) {
                    if (valueDouble != 0 || isZeroDisplay) {
                        DecimalFormat formatter1 = new DecimalFormat("#.#");
                        rs = formatter1.format(valueDouble) + TextResource.localize("KWR_2");
                    }
                }

                break;
            case DAYS:
                if (valueDouble != null) {
                    if (valueDouble != 0 || isZeroDisplay) {
                        DecimalFormat formatter2 = new DecimalFormat("#.#");
                        rs = formatter2.format(valueDouble) + TextResource.localize("KWR_1");
                    }
                }
                break;
            case AMOUNT_OF_MONEY:
                if (valueDouble != null) {
                    if (valueDouble != 0 || isZeroDisplay) {
                        DecimalFormat formatter3 = new DecimalFormat("#,###");
                        rs = formatter3.format((int) valueDouble.intValue()) + TextResource.localize("KWR_3");
                    }
                }

                break;
        }
        return rs;
    }

    private String convertToTime(int minute) {
        int minuteAbs = Math.abs(minute);
        if (minute < 0) {
            minuteAbs = Math.abs(minute + 1440);
        }
        int hours = minuteAbs / 60;
        int minutes = minuteAbs % 60;
        return (minute < 0 ? "-" : "") + String.format("%d:%02d", hours, minutes);
    }

    public boolean checkText(CommonAttributesOfForms attributes) {
        return attributes == CommonAttributesOfForms.OTHER_CHARACTER_NUMBER
                || attributes == CommonAttributesOfForms.OTHER_CHARACTERS
                || attributes == CommonAttributesOfForms.OTHER_NUMERICAL_VALUE;

    }

    @AllArgsConstructor
    @Getter
    @Setter
    private static class PrintOneLineDto {
        private Double totalOfOneLine;
        private String outPutItemName;
        private DailyValue dailyValue;
        private GeneralDate date;
    }
    private void setBottomBorderStyle(Cell cell) {
        Style style = cell.getStyle();
        style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.MEDIUM, Color.getBlack());
        cell.setStyle(style);
    }
    @AllArgsConstructor
    @Getter
    @Setter
    private static class PrintPage {
        private int pageNumber;
        private int count;
        private boolean isBreak;
    }

}
