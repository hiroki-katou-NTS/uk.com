package nts.uk.file.at.infra.annualworkledger;

import com.aspose.cells.*;
import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.AnnualWorkLedgerContent;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.AnnualWorkLedgerExportDataSource;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.DailyData;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.MonthlyData;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;
import nts.uk.file.at.app.export.annualworkledger.DisplayAnnualWorkLedgerReportGenerator;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Stateless
public class AsposeDisplayAnnualWorkLedgerReportGenerator extends AsposeCellsReportGenerator
        implements DisplayAnnualWorkLedgerReportGenerator {
    private static final String TEMPLATE_FILE_ADD = "report/KWR004.xlsx";
    private static final String REPORT_FILE_NAME = "年間勤務台帳";
    private static final String PDF_EXT = ".pdf";
    private static final String EXCEL_EXT = ".xlsx";
    private static final int EXPORT_PDF = 1;
    private static final int EXPORT_EXCEL = 2;
    private static final int NUMBER_ROW_OF_PAGE = 47;
    private static final int MAX_DAY_IN_MONTH = 31;
    private static final int TOTAL_MONTH_IN_YEAR = 12;

    @Override
    public void generate(FileGeneratorContext generatorContext, AnnualWorkLedgerExportDataSource dataSource) {
        try {
            AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE_ADD);
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            Worksheet worksheet = worksheets.get(0);
            if (!dataSource.getLstAnnualWorkLedgerContent().isEmpty()) {
                settingPage(worksheet, dataSource);
                printHeaderTemplate(worksheet, dataSource);
                printContents(worksheet, dataSource);
                removeTemplate(worksheet);
            }

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

    private void settingPage(Worksheet worksheet, AnnualWorkLedgerExportDataSource dataSource) {
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setPaperSize(PaperSizeType.PAPER_A_4);
        pageSetup.setOrientation(PageOrientationType.LANDSCAPE);
        String companyName = dataSource.getCompanyName();
        pageSetup.setHeader(0, "&7&\"ＭＳ フォントサイズ\"" + companyName);
        pageSetup.setHeader(1, "&12&\"ＭＳ フォントサイズ\""
                + dataSource.getOutputSetting().getName());
        //pageSetup.setPrintArea(PRINT_AREA);
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter
                .ofPattern("yyyy/MM/dd  H:mm", Locale.JAPAN);
        worksheet.getPageSetup().setHeader(2,
                "&7&\"MS フォントサイズ\"" + LocalDateTime.now().format(fullDateTimeFormatter) + "\n" +
                        TextResource.localize("page") + " &P");
        if (dataSource.getMode() == EXPORT_PDF) {
            pageSetup.setFitToPagesTall(0);
            pageSetup.setFitToPagesWide(0);
        }
    }

    private void printHeaderTemplate(Worksheet worksheet, AnnualWorkLedgerExportDataSource dataSource) {
        Cells cells = worksheet.getCells();
        val yearMonths = dataSource.getYearMonthPeriod().yearMonthsBetween();
        for (int i = 0; i < yearMonths.size(); i++) {
            val yearMonth = yearMonths.get(i);
            if (i == 0 || yearMonth.month() == 1) {
                cells.get(4, 2 + i * 2).setValue(TextResource.localize("KWR004_209",
                        String.valueOf(yearMonth.year()), String.valueOf(yearMonth.month())));
                cells.get(38, 2 + i * 2).setValue(TextResource.localize("KWR004_209",
                        String.valueOf(yearMonth.year()), String.valueOf(yearMonth.month())));
            } else {
                cells.get(4, 2 + i * 2).setValue(TextResource.localize("KWR004_210",
                        String.valueOf(yearMonth.month())));
                cells.get(38, 2 + i * 2).setValue(TextResource.localize("KWR004_210",
                        String.valueOf(yearMonth.month())));
            }

            val firstDailyData = dataSource.getLstAnnualWorkLedgerContent().stream().findFirst().get().getDailyData();
            cells.get(5, 2 + i * 2).setValue(firstDailyData.getLeftColumnName());
            cells.get(5, 3 + i * 2).setValue(firstDailyData.getRightColumnName());
        }

        val closureDay = dataSource.getClosureDate().getLastDayOfMonth() ?
                0 : dataSource.getClosureDate().getClosureDay().v();
        // 日次項目では固定３１行であり
        for (int i = 0; i < MAX_DAY_IN_MONTH; i++) {
            val day = (closureDay + i) % MAX_DAY_IN_MONTH + 1;
            cells.get(6 + i, 1).setValue(TextResource.localize("KWR004_211", String.valueOf(day)));
            if (i > 28) {
                for (int j = 0; j < yearMonths.size(); j++) {
                    val yearMonth = yearMonths.get(j);
                    try {
                        LocalDate.of(yearMonth.year(), yearMonth.month(), day);
                    } catch (DateTimeException ex) {
                        cells.merge(6 + i, 2 + j * 2, 1, 2);
                        this.setLineDiagonal(cells.get(6 + i, 2 + j * 2));
                    }
                }
            }
        }
    }

    private void printContents(Worksheet worksheet, AnnualWorkLedgerExportDataSource dataSource) throws Exception {
        Cells cells = worksheet.getCells();
        int firstRow = NUMBER_ROW_OF_PAGE;
        val closureDay = dataSource.getClosureDate().getLastDayOfMonth() ?
                0 : dataSource.getClosureDate().getClosureDay().v();
        val startYearMonth = dataSource.getYearMonthPeriod().start();
        List<AnnualWorkLedgerContent> lstAnnualWorkLedgerContent = dataSource.getLstAnnualWorkLedgerContent();
        for (int i = 0; i < lstAnnualWorkLedgerContent.size(); i++) {
            AnnualWorkLedgerContent empInfor = lstAnnualWorkLedgerContent.get(i);
            firstRow = (i + 1) * NUMBER_ROW_OF_PAGE;
            cells.copyRows(cells, 0, firstRow, NUMBER_ROW_OF_PAGE);

            this.printDailyData(worksheet, firstRow, dataSource.isZeroDisplay(), empInfor.getDailyData(), startYearMonth, closureDay);
            this.printMonthlyData(worksheet, firstRow, dataSource.isZeroDisplay(), empInfor.getLstMonthlyData(), startYearMonth);
        }
    }

    private void printDailyData(Worksheet worksheet, int firstRow, boolean isZeroDisplay, DailyData dailyData, YearMonth startYearMonth, int closureDay) {
        Cells cells = worksheet.getCells();
        for (val data : dailyData.getLstLeftValue()) {
            val date = data.getDate();
            val rowIndex = firstRow + (date.day() - closureDay) % MAX_DAY_IN_MONTH;
            val columnIndex = firstRow + 2 + this.totalMonths(startYearMonth, date.yearMonth());
            cells.get(rowIndex, columnIndex).setValue(this.formatValue(data.getActualValue(), data.getCharacterValue(), dailyData.getLeftAttribute(), isZeroDisplay));
        }

        for (val data : dailyData.getLstRightValue()) {
            val date = data.getDate();
            val rowIndex = firstRow + (date.day() - closureDay) % MAX_DAY_IN_MONTH;
            val columnIndex = firstRow + 3 + this.totalMonths(startYearMonth, date.yearMonth());
            cells.get(rowIndex, columnIndex).setValue(this.formatValue(data.getActualValue(), data.getCharacterValue(), dailyData.getRightAttribute(), isZeroDisplay));
        }
    }

    private void printMonthlyData(Worksheet worksheet, int firstRow, boolean isZeroDisplay, List<MonthlyData> lstMonthlyData, YearMonth startYearMonth) {
        Cells cells = worksheet.getCells();
        for (int i = 0; i < lstMonthlyData.size(); i++) {
            val dataRow = lstMonthlyData.get(i);
            val rowIndex = firstRow + 39 + i;
            cells.get(rowIndex, 0).setValue(dataRow.getOutputItemName());
            for (val data : dataRow.getLstMonthlyValue()) {
                val columnIndex = firstRow + 2 + this.totalMonths(startYearMonth, data.getDate());
                cells.get(rowIndex, columnIndex).setValue(this.formatValue(data.getActualValue(), data.getCharacterValue(), dataRow.getAttribute(), isZeroDisplay));
            }
        }
    }

    private void removeTemplate(Worksheet worksheet) {
        Cells cells = worksheet.getCells();
        cells.deleteRows(0, NUMBER_ROW_OF_PAGE);
    }

    private String formatValue(double valueDouble, String valueString, CommonAttributesOfForms attributes,
                               Boolean isZeroDisplay) {
        String rs = "";
        if (!isZeroDisplay) {
            if (valueDouble == 0 || valueString.equals(""))
                return rs;
        }
        switch (attributes) {

            case TIME:
                // HH:mm　(マイナスあり)
                rs = convertToTime((int) valueDouble);
                break;
            case TIME_OF_DAY:
                // HH:mm　(マイナスあり)
                rs = convertToTime((int) valueDouble);
                break;
            case NUMBER_OF_TIMES:
                // 小数点以下は、集計する勤怠項目の小数部桁数に従う(※1)　(マイナスあり)
                rs = valueString;
                break;
            case AMOUNT_OF_MONEY:
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

    private void setLineDiagonal(Cell cell) {
        Style style = cell.getStyle();
        style.getBorders().setDiagonalStyle(BorderType.DIAGONAL_DOWN);
        cell.setStyle(style);
    }

    private int totalMonths(YearMonth start, YearMonth end) {
        return (end.year() - start.year()) * TOTAL_MONTH_IN_YEAR + (end.month() - start.month());
    }
}
