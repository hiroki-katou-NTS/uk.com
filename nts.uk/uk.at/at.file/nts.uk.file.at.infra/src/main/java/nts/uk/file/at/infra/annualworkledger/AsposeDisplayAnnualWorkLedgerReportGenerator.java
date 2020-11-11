package nts.uk.file.at.infra.annualworkledger;

import com.aspose.cells.*;
import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.AnnualWorkLedgerContent;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.AnnualWorkLedgerExportDataSource;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;
import nts.uk.file.at.app.export.annualworkledger.DisplayAnnualWorkLedgerReportGenerator;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.text.DecimalFormat;
import java.time.DateTimeException;
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

    @Override
    public void generate(FileGeneratorContext generatorContext, AnnualWorkLedgerExportDataSource dataSource) {
        try {
            AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE_ADD);
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            Worksheet worksheet = worksheets.get(0);
            if (!dataSource.getLstAnnualWorkLedgerContent().isEmpty()) {
                settingPage(worksheet, dataSource);
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

    private void printContents(Worksheet worksheet, AnnualWorkLedgerExportDataSource dataSource) throws Exception {
        Cells cells = worksheet.getCells();
        List<AnnualWorkLedgerContent> lstAnnualWorkLedgerContent = dataSource.getLstAnnualWorkLedgerContent();
        for (int i = 0; i < lstAnnualWorkLedgerContent.size(); i++) {
            AnnualWorkLedgerContent empInfo = lstAnnualWorkLedgerContent.get(i);
            int firstRow = (i + 1) * NUMBER_ROW_OF_PAGE;
            cells.copyRows(cells, 0, firstRow, NUMBER_ROW_OF_PAGE);

            this.printEmployeeInfor(worksheet, firstRow, dataSource, empInfo);
            this.printData(worksheet, firstRow, dataSource, empInfo);
        }
    }

    /**
     * Print employee information
     */
    private void printEmployeeInfor(Worksheet worksheet, int firstRow, AnnualWorkLedgerExportDataSource dataSource, AnnualWorkLedgerContent empInfo) {
        Cells cells = worksheet.getCells();
        // B1_1 B1_2 B1_3
        cells.get(firstRow, 0).setValue(TextResource.localize("KWR004_201") + "　" + empInfo.getWorkplaceCode() + "　" + empInfo.getWorkplaceName());
        // B2_1 B2_2 B2_3
        cells.get(firstRow + 1, 0).setValue(TextResource.localize("KWR004_202") + "　" + empInfo.getEmployeeCode() + "　" + empInfo.getEmployeeName());
        // B3_1 B3_2 B3_3
        cells.get(firstRow + 2, 0).setValue(TextResource.localize("KWR004_203") + "　" + empInfo.getEmploymentCode() + "　" + empInfo.getEmploymentName());
        // B4_1 B4_2
        cells.get(firstRow + 2, 5).setValue(TextResource.localize("KWR004_204") + "　" + empInfo.getClosureDate());
        // B5_1 B5_2
        cells.get(firstRow, 10).setValue(TextResource.localize("KWR004_205") +
                TextResource.localize("KWR004_208", this.toYearMonthString(dataSource.getYearMonthPeriod().start()),
                        this.toYearMonthString(dataSource.getYearMonthPeriod().end())));
    }

    /**
     * Print data
     */
    private void printData(Worksheet worksheet, int firstRow, AnnualWorkLedgerExportDataSource dataSource, AnnualWorkLedgerContent empInfo) {
        Cells cells = worksheet.getCells();

        // Daily data
        val dailyData = empInfo.getDailyData();
        // C1_1
        cells.get(firstRow + 3, 0).setValue(TextResource.localize("KWR004_206"));

        val closureDay = dataSource.getClosureDate().getLastDayOfMonth() ? 0 : dataSource.getClosureDate().getClosureDay().v();
        val yearMonths = dataSource.getYearMonthPeriod().yearMonthsBetween();
        for (int mi = 0; mi < yearMonths.size(); mi++) {
            val yearMonth = yearMonths.get(mi);
            String yearMonthString = (mi == 0 || yearMonth.month() == 1) ?
                    TextResource.localize("KWR004_209", String.valueOf(yearMonth.year()), String.valueOf(yearMonth.month())) :
                    TextResource.localize("KWR004_210", String.valueOf(yearMonth.month()));
            // C2_1
            cells.get(firstRow + 4, 2 + mi * 2).setValue(yearMonthString);
            // C2_2
            cells.get(firstRow + 5, 2 + mi * 2).setValue(dailyData.getLeftColumnName());
            // C2_3
            cells.get(firstRow + 5, 3 + mi * 2).setValue(dailyData.getRightColumnName());

            // 日次項目では固定３１行であり
            for (int di = 0; di < MAX_DAY_IN_MONTH; di++) {
                val day = (closureDay + di) % MAX_DAY_IN_MONTH + 1;
                // D1_1
                cells.get(firstRow + 6 + di, 0).setValue(TextResource.localize("KWR004_211", String.valueOf(day)));

                try {
                    val date = GeneralDate.ymd(yearMonth.year(), yearMonth.month(), day);
                    val leftData = dailyData.getLstLeftValue().stream().filter(x -> x.getDate().compareTo(date) == 0).findFirst();
                    if (leftData.isPresent()) {
                        // D2_1
                        cells.get(firstRow + 6 + di, 2 + mi * 2)
                                .setValue(this.formatValue(leftData.get().getActualValue(), leftData.get().getCharacterValue(), dailyData.getLeftAttribute(), dataSource.isZeroDisplay()));
                    }

                    val rightData = dailyData.getLstLeftValue().stream().filter(x -> x.getDate().compareTo(date) == 0).findFirst();
                    if (rightData.isPresent()) {
                        cells.get(firstRow + 6 + di, 3 + mi * 2)
                                .setValue(this.formatValue(rightData.get().getActualValue(), rightData.get().getCharacterValue(), dailyData.getRightAttribute(), dataSource.isZeroDisplay()));
                    }
                } catch (DateTimeException ex) {
                    cells.merge(firstRow + 6 + di, 2 + mi * 2, 1, 2);
                    this.setLineDiagonal(cells.get(firstRow + 6 + di, 2 + mi * 2));
                }
            }

            // Monthly Data
            val lstMonthlyData = empInfo.getLstMonthlyData();
            // E1_1
            cells.get(firstRow + 37, 0).setValue(TextResource.localize("KWR004_207"));
            // E2_1
            cells.get(firstRow + 38, 2 + mi * 2).setValue(yearMonthString);

            for (int i = 0; i < lstMonthlyData.size(); i++) {
                val dataRow = lstMonthlyData.get(i);
                val rowIndex = firstRow + 39 + i;
                // F1_1
                cells.get(rowIndex, 0).setValue(dataRow.getOutputItemName());
                val monthlyData = dataRow.getLstMonthlyValue().stream().filter(x -> x.getDate().compareTo(yearMonth) == 0).findFirst();
                if (monthlyData.isPresent()) {
                    // F2_1
                    cells.get(rowIndex, 2 + mi * 2)
                            .setValue(this.formatValue(monthlyData.get().getActualValue(), monthlyData.get().getCharacterValue(), dataRow.getAttribute(), dataSource.isZeroDisplay()));
                }
            }

        }
    }

    /**
     * Remove page template
     */
    private void removeTemplate(Worksheet worksheet) {
        Cells cells = worksheet.getCells();
        cells.deleteRows(0, NUMBER_ROW_OF_PAGE);
    }

    /**
     * Format value
     */
    private String formatValue(double valueDouble, String valueString, CommonAttributesOfForms attributes, Boolean isZeroDisplay) {
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
                    rs = formatter1.format(valueDouble) + "回"; // TODO Text Resource
                }
                break;
            case DAYS:
                if (valueDouble != 0 || isZeroDisplay) {
                    DecimalFormat formatter2 = new DecimalFormat("#.#");
                    rs = formatter2.format(valueDouble) + "日";// TODO Text Resource
                }
                break;
            case AMOUNT_OF_MONEY:
                if (valueDouble != 0 || isZeroDisplay) {
                    DecimalFormat formatter3 = new DecimalFormat("#,###");
                    rs = formatter3.format((int) valueDouble) + "円"; // TODO Text Resource
                }
                break;
        }
        return rs;
    }

    /**
     * Convert minute to HH:mm
     */
    private String convertToTime(int minute) {
        val minuteAbs = Math.abs(minute);
        int hours = minuteAbs / 60;
        int minutes = minuteAbs % 60;
        return (minute < 0 ? "-" : "") + String.format("%d:%02d", hours, minutes);
    }

    /**
     * 存在しない日のセールに斜線を付ける
     */
    private void setLineDiagonal(Cell cell) {
        Style style = cell.getStyle();
        style.setBorder(BorderType.DIAGONAL_UP, CellBorderType.THIN, Color.getBlack());
        cell.setStyle(style);
    }

    /**
     * Display year month yyyy/MM
     */
    private String toYearMonthString(YearMonth yearMonth) {
        return String.format("%04d/%02d", yearMonth.year(), yearMonth.month());
    }
}
