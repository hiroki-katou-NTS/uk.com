package nts.uk.file.at.infra.annualworkledger;

import com.aspose.cells.*;
import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.AnnualWorkLedgerContent;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.AnnualWorkLedgerExportDataSource;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.file.at.app.export.annualworkledger.DisplayAnnualWorkLedgerReportGenerator;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import org.apache.commons.lang3.StringUtils;

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
    private static final String TEMPLATE_FILE_ADD = "report/KWR004_v3.xlsx";
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
            worksheet.setName(dataSource.getOutputSetting().getName().v());
            if (!dataSource.getLstAnnualWorkLedgerContent().isEmpty()) {
                settingPage(worksheet, dataSource);
                printContents(worksheet, dataSource);
                // removeTemplate(worksheet);
            }

            worksheets.setActiveSheetIndex(0);
            reportContext.processDesigner();
            String fileName = dataSource.getOutputSetting().getName().v() + "_" + GeneralDateTime.now().toString("yyyyMMddHHmmss");
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
        String companyName = dataSource.getCompanyName();
        pageSetup.setCenterHorizontally(true);
        pageSetup.setHeader(0, "&7&\"ＭＳ ゴシック\"" + companyName);
        pageSetup.setHeader(1, "&12&\"ＭＳ ゴシック,Bold\""
                + dataSource.getOutputSetting().getName());
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter
                .ofPattern("yyyy/MM/dd  H:mm", Locale.JAPAN);
        pageSetup.setHeader(2,
                "&7&\"ＭＳ ゴシック\"" + LocalDateTime.now().format(fullDateTimeFormatter) + "\n" +
                        TextResource.localize("page") + " &P");
        pageSetup.setPrintArea("A1:Z" + dataSource.getLstAnnualWorkLedgerContent().size()*NUMBER_ROW_OF_PAGE);
    }

    private void printContents(Worksheet worksheet, AnnualWorkLedgerExportDataSource dataSource) throws Exception {
        HorizontalPageBreakCollection pageBreaks = worksheet.getHorizontalPageBreaks();
        Cells cells = worksheet.getCells();
        List<AnnualWorkLedgerContent> lstAnnualWorkLedgerContent = dataSource.getLstAnnualWorkLedgerContent();
        for (int i = 0; i < lstAnnualWorkLedgerContent.size(); i++) {
            AnnualWorkLedgerContent empInfo = lstAnnualWorkLedgerContent.get(i);
            int firstRow = (i) * NUMBER_ROW_OF_PAGE;
            if (i >= 1) {
                pageBreaks.add(firstRow);
                cells.copyRows(cells, 0, firstRow, NUMBER_ROW_OF_PAGE);
                cells.clearContents(firstRow, 0, cells.getMaxRow(), cells.getMaxColumn());
            }
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
        val wplCode  = StringUtils.deleteWhitespace(empInfo.getWorkplaceCode());
        val emCode  = StringUtils.deleteWhitespace(empInfo.getEmployeeCode());
        cells.get(firstRow, 0).setValue(TextResource.localize("KWR004_201") + wplCode + "　" + empInfo.getWorkplaceName());
        // B2_1 B2_2 B2_3
        cells.get(firstRow + 1, 0).setValue(TextResource.localize("KWR004_202") + emCode + "　" + empInfo.getEmployeeName());
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
        cells.get(firstRow + 2, 0).setValue(TextResource.localize("KWR004_206"));
        val lastDateInMonth = dataSource.getDatePeriod().start().lastDateInMonth();

        val closureDay = dataSource.getDatePeriod().start().day() == lastDateInMonth ? 0 : dataSource.getDatePeriod().start().day() - 1;
        val yearMonths = dataSource.getYearMonthPeriod().yearMonthsBetween();
        for (int mi = 0; mi < yearMonths.size(); mi++) {
            val yearMonth = yearMonths.get(mi);
            String yearMonthString = (mi == 0 || yearMonth.month() == 1) ?
                    TextResource.localize("KWR004_209", String.valueOf(yearMonth.year()), String.valueOf(yearMonth.month())) :
                    TextResource.localize("KWR004_210", String.valueOf(yearMonth.month()));
            // C2_1
            cells.get(firstRow + 3, 2 + mi * 2).setValue(yearMonthString);
            // C2_2
            cells.get(firstRow + 4, 2 + mi * 2).setValue(dailyData.getLeftColumnName());
            // C2_3
            cells.get(firstRow + 4, 3 + mi * 2).setValue(dailyData.getRightColumnName());

            // 日次項目では固定３１行であり
            for (int di = 0; di < MAX_DAY_IN_MONTH; di++) {
                val day = (closureDay + di) % MAX_DAY_IN_MONTH + 1;
                // D1_1
                cells.get(firstRow + 5 + di, 0).setValue(TextResource.localize("KWR004_211", String.valueOf(day)));

                try {
                    val date = GeneralDate.ymd(yearMonth.year(), yearMonth.month(), day);
                    val leftData = dailyData.getLstLeftValue().stream().filter(x -> x.getDate().compareTo(date) == 0).findFirst();
                    if (leftData.isPresent() && dailyData.getLeftAttribute() != null) {
                        // D2_1
                        cells.get(firstRow + 5 + di, 2 + mi * 2)
                                .setValue(this.formatValue(leftData.get().getActualValue(), leftData.get().getCharacterValue(), dailyData.getLeftAttribute(), dataSource.isZeroDisplay()));
                        Cell cell = cells.get(firstRow + 5 + di, 2 + mi * 2);
                        Style style =   cell.getStyle();
                        style.setHorizontalAlignment(checkText(dailyData.getLeftAttribute())?ColumnTextAlign.LEFT.value:ColumnTextAlign.RIGHT.value);
                        if (dailyData.getLeftAttribute() == CommonAttributesOfForms.WORK_TYPE ||
                                dailyData.getLeftAttribute() == CommonAttributesOfForms.WORKING_HOURS) {
                            style.setHorizontalAlignment(ColumnTextAlign.CENTER.value);
                        }
                        cell.setStyle(style);
                    }

                    val rightData = dailyData.getLstRightValue().stream().filter(x -> x.getDate().compareTo(date) == 0).findFirst();
                    if (rightData.isPresent() && dailyData.getRightAttribute() != null) {
                        cells.get(firstRow + 5 + di, 3 + mi * 2)
                                .setValue(this.formatValue(rightData.get().getActualValue(), rightData.get().getCharacterValue(), dailyData.getRightAttribute(), dataSource.isZeroDisplay()));
                        Cell cell =  cells.get(firstRow + 5 + di, 3 + mi * 2);
                        Style style =  cell.getStyle();
                        style.setHorizontalAlignment(checkText(dailyData.getRightAttribute())?ColumnTextAlign.LEFT.value:ColumnTextAlign.RIGHT.value);
                        if (dailyData.getRightAttribute() == CommonAttributesOfForms.WORK_TYPE ||
                                dailyData.getRightAttribute() == CommonAttributesOfForms.WORKING_HOURS) {
                            style.setHorizontalAlignment(ColumnTextAlign.CENTER.value);
                        }
                        cell.setStyle(style);
                    }
                } catch (DateTimeException ex) {
                    cells.merge(firstRow + 5 + di, 2 + mi * 2, 1, 2);
                    this.setLineDiagonal(cells.get(firstRow + 5 + di, 2 + mi * 2));
                }
            }

            // Monthly Data
            val lstMonthlyData = empInfo.getLstMonthlyData();
            // E1_1
            cells.get(firstRow + 36, 0).setValue(TextResource.localize("KWR004_207"));
            // E2_1
            cells.get(firstRow + 37, 2 + mi * 2).setValue(yearMonthString);

            for (int i = 0; i < lstMonthlyData.size(); i++) {
                val dataRow = lstMonthlyData.get(i);
                val rowIndex = firstRow + 38 + i;
                // F1_1
                cells.get(rowIndex, 0).setValue(dataRow.getOutputItemName());
                val monthlyData = dataRow.getLstMonthlyValue().stream().filter(x -> x.getDate().compareTo(yearMonth) == 0).findFirst();
                if (monthlyData.isPresent() && dataRow.getAttribute() != null) {
                    // F2_1
                    cells.get(rowIndex, 2 + mi * 2)
                            .setValue(this.formatValue(monthlyData.get().getActualValue(), monthlyData.get().getCharacterValue(), dataRow.getAttribute(), dataSource.isZeroDisplay()));
                    Cell cell =  cells.get(rowIndex, 2 + mi * 2);
                    Style style =  cell.getStyle();
                    style.setHorizontalAlignment(checkText(dataRow.getAttribute())?ColumnTextAlign.LEFT.value:ColumnTextAlign.RIGHT.value);
                    if (dataRow.getAttribute() == CommonAttributesOfForms.WORK_TYPE ||
                            dataRow.getAttribute() == CommonAttributesOfForms.WORKING_HOURS) {
                        style.setHorizontalAlignment(ColumnTextAlign.CENTER.value);
                    }
                    cell.setStyle(style);
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
    private String formatValue(Double valueDouble, String valueString, CommonAttributesOfForms attributes, Boolean isZeroDisplay) {
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
                    rs = convertToTime(valueDouble.intValue());
                }
                break;
            case TIME:
                if (valueDouble != null) {
                    val minute = valueDouble.intValue();
                    if (minute != 0 || isZeroDisplay) {
                        rs = convertToTime(minute);
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
            case NUMBER_OF_TIMES:
                if (valueDouble != null) {
                    if (valueDouble != 0 || isZeroDisplay) {
                        DecimalFormat formatter1 = new DecimalFormat("#.#");
                        rs = formatter1.format(valueDouble) + TextResource.localize("KWR_2");
                    }
                }

                break;
        }
        return rs;
    }
    public boolean checkText(CommonAttributesOfForms attributes){
        return attributes == CommonAttributesOfForms.WORK_TYPE
              ||attributes == CommonAttributesOfForms.WORKING_HOURS
              ||attributes == CommonAttributesOfForms.OTHER_CHARACTER_NUMBER
              ||attributes == CommonAttributesOfForms.OTHER_CHARACTERS
              ||attributes == CommonAttributesOfForms.OTHER_NUMERICAL_VALUE;

    }

    /**
     * Convert minute to HH:mm
     */
    private String convertToTime(int minute) {
        int minuteAbs = Math.abs(minute);
        if (minute < 0) {
            minuteAbs = Math.abs(minute +1440);
        }
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
