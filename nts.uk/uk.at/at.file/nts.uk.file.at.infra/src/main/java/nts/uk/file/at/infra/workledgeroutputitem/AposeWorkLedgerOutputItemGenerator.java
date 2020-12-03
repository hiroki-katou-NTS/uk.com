package nts.uk.file.at.infra.workledgeroutputitem;

import com.aspose.cells.*;
import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;
import nts.uk.ctx.at.function.dom.workledgeroutputitem.WorkLedgerDisplayContent;
import nts.uk.ctx.at.function.dom.workledgeroutputitem.WorkLedgerExportDataSource;
import nts.uk.file.at.app.export.workledgeroutputitem.WorkLedgerOutputItemGenerator;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;


@Stateless
public class AposeWorkLedgerOutputItemGenerator extends AsposeCellsReportGenerator implements WorkLedgerOutputItemGenerator {

    private static final String TEMPLATE_FILE_ADD = "report/KWR005.xlsx";
    private static final String REPORT_FILE_NAME = "KWR005_勤務状況表";
    private static final String DATE_FORMAT = "yyyy/MM/dd";
    private static final String DAY_OF_WEEK_FORMAT_JP = "E";
    private static final String EXCEL_EXT = ".xlsx";
    private static final String PRINT_AREA = "A1:AJ";
    private static final int MAX_EMP_IN_PAGE = 10;
    private static final int NUMBER_ROW_OF_PAGE = 48;
    private static final int MAX_DAY_IN_MONTH = 31;

    @Override
    public void generate(FileGeneratorContext generatorContext, WorkLedgerExportDataSource dataSource) {
        try {
            AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE_ADD);
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            Worksheet worksheet = worksheets.get(0);
            if (!dataSource.getListContent().isEmpty()) {
                settingPage(worksheet, dataSource);
                printContents(worksheet, dataSource);
                removeTemplate(worksheet);
            }
            worksheets.setActiveSheetIndex(0);
            reportContext.processDesigner();
            // save as excel file
            reportContext.saveAsExcel(this.createNewFile(generatorContext, REPORT_FILE_NAME + EXCEL_EXT));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void settingPage(Worksheet worksheet, WorkLedgerExportDataSource dataSource) {
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
        pageSetup.setZoom(100);
    }

    private void printContents(Worksheet worksheet, WorkLedgerExportDataSource dataSource) throws Exception {
        HorizontalPageBreakCollection pageBreaks = worksheet.getHorizontalPageBreaks();
        List<WorkLedgerDisplayContent> listContent = dataSource.getListContent();
        int count = 0;
        Cells cells = worksheet.getCells();
        for (int i = 0; i < listContent.size(); i++) {
            val content = listContent.get(i);
            if (i >= 1) {
                pageBreaks.add(count);
                cells.copyRow(cells, 0, count);
                cells.copyRow(cells, 1, count + 1);
            }
            cells.get(count, 0).setValue(TextResource.localize("KWR005_301") + "　" + content.getWorkplaceCode() + "　" + content.getWorkplaceName());
            cells.get(count + 1, 0).setValue(TextResource.localize("KWR005_302") + "　" + content.getEmployeeCode() + "　" + content.getEmployeeName());
            count += 2;
            val yearMonths = dataSource.getYearMonthPeriod().yearMonthsBetween();
            for (int mi = 0; mi < yearMonths.size(); mi++) {
                val yearMonth = yearMonths.get(mi);
                String yearMonthString = (mi == 0 || yearMonth.month() == 1) ?
                        TextResource.localize("KWR005_309", String.valueOf(yearMonth.year()), String.valueOf(yearMonth.month())) :
                        TextResource.localize("KWR005_310", String.valueOf(yearMonth.month()));
                cells.get(count + 3, 2 + mi).setValue(yearMonthString);
            }
            cells.get(count + 3, 15).setValue(TextResource.localize("KWR005_304"));
            count++;
            val data = content.getMonthlyDataList();
            for (int j = 0; j < data.size(); j++) {
                val oneLine = data.get(j);
                if (j % 2 == 0) {
                    cells.copyRow(cells, 3, 3 + count);
                } else {
                    cells.copyRow(cells, 4, 4 + count);
                }

            }
        }

    }

    /**
     * Display year month yyyy/MM
     */
    private String toYearMonthString(YearMonth yearMonth) {
        return String.format("%04d/%02d", yearMonth.year(), yearMonth.month());
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
            case DAYS:
                if (valueDouble != 0 || isZeroDisplay) {
                    DecimalFormat formatter2 = new DecimalFormat("#.#");
                    rs = formatter2.format(valueDouble) + TextResource.localize("KWR_1");
                }
                break;
            case TIME:
                val minute = (int) valueDouble;
                if (minute != 0 || isZeroDisplay) {
                    rs = convertToTime(minute);
                }
                break;
            case AMOUNT_OF_MONEY:
                if (valueDouble != 0 || isZeroDisplay) {
                    DecimalFormat formatter3 = new DecimalFormat("#,###");
                    rs = formatter3.format((int) valueDouble) + TextResource.localize("KWR_3");
                }
                break;
            case NUMBER_OF_TIMES:
                if (valueDouble != 0 || isZeroDisplay) {
                    DecimalFormat formatter1 = new DecimalFormat("#.#");
                    rs = formatter1.format(valueDouble) + TextResource.localize("KWR_2");
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
}
