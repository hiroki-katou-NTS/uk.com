package nts.uk.file.at.infra.schedule.personalschedulebydate;

import com.aspose.cells.*;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.at.schedule.dom.shift.management.DateInformation;
import nts.uk.file.at.app.export.schedule.personalschedulebydate.PersonalScheduleByDateDataSource;
import nts.uk.file.at.app.export.schedule.personalschedulebydate.PersonalScheduleByDateExportGenerator;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class AsposePersonalScheduleByDateExportGenerator extends AsposeCellsReportGenerator implements PersonalScheduleByDateExportGenerator {
    private final String FONT_NAME = "ＭＳ ゴシック";
    private final String EXCEL_EXT = ".xlsx";
    private final String SPACE = "　";
    private final String COLON = "　：　";

    private final int BG_COLOR_SPECIFIC_DAY = Integer.parseInt("ffc0cb", 16);
    private final int TEXT_COLOR_SUNDAY = Integer.parseInt("ff0000", 16);

    @Override
    public void generate(FileGeneratorContext context, PersonalScheduleByDateDataSource dataSource) {
        try {
            AsposeCellsReportContext reportContext = this.createEmptyContext("PersonalScheduleByDate");
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            Worksheet worksheet = worksheets.get(0);
            worksheet.setName("");
            this.settingPage(worksheet, dataSource);
            this.printHeader(worksheet, dataSource);
            this.printContent(worksheet, dataSource);
            reportContext.processDesigner();
            // Save as excel file
            reportContext.saveAsExcel(this.createNewFile(context, this.getReportName("NAME_TODO" + EXCEL_EXT)));  //TODO

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void settingPage(Worksheet worksheet, PersonalScheduleByDateDataSource dataSource) {
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setPaperSize(PaperSizeType.PAPER_A_4);
        pageSetup.setOrientation(PageOrientationType.LANDSCAPE);
        pageSetup.setFitToPagesTall(0);
        pageSetup.setFitToPagesWide(1);
        pageSetup.setTopMarginInch(0.98);
        pageSetup.setBottomMarginInch(0.39);
        pageSetup.setLeftMarginInch(0.39);
        pageSetup.setRightMarginInch(0.39);
        pageSetup.setHeaderMarginInch(0.39);
        pageSetup.setFooterMarginInch(0.31);
        pageSetup.setCenterHorizontally(true);
        pageSetup.setHeader(0, "&9&\"" + FONT_NAME + "\"" + "TODO"); //TODO
        pageSetup.setHeader(1, "&16&\"" + FONT_NAME + ",Bold\"" + "TODO");  //TODO
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd  HH:mm", Locale.JAPAN);
        pageSetup.setHeader(2, "&9&\"" + FONT_NAME + "\"" + LocalDateTime.now().format(fullDateTimeFormatter) + "\npage&P ");
    }

    private void printHeader(Worksheet worksheet, PersonalScheduleByDateDataSource dataSource) {

    }

    private void printContent(Worksheet worksheet, PersonalScheduleByDateDataSource dataSource) {

    }

    private void setHeaderStyle(Cell cell, DateInformation dateInfo, boolean isDate) {
        Style style = cell.getStyle();
        style.setHorizontalAlignment(TextAlignmentType.CENTER);
        style.setVerticalAlignment(TextAlignmentType.CENTER);
        if (isDate) {
            style.setTextWrapped(true);
        } else {
            style.setShrinkToFit(true);
        }
        style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.THIN);
        style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.THIN);
        style.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.THIN);
        style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.THIN);
        style.setPattern(BackgroundType.SOLID);
        if (dateInfo.isSpecificDay()) {
            style.setForegroundColor(Color.fromArgb(BG_COLOR_SPECIFIC_DAY));
            style.getFont().setColor(Color.fromArgb(TEXT_COLOR_SUNDAY));
        }
        style.getFont().setName(FONT_NAME);
        style.getFont().setSize(9);
        cell.setStyle(style);
    }

    private void setPersonalStyle(Cells cells, int row, int column, boolean isFirstRow) {
        Style style = cells.get(row, column).getStyle();
        style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.THIN);
        style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.THIN);
        style.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.THIN);
        style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.THIN);
        style.getFont().setName(FONT_NAME);
        style.getFont().setSize(9);
        style.setShrinkToFit(true);
        style.setPattern(BackgroundType.SOLID);
        style.setVerticalAlignment(TextAlignmentType.CENTER);
        if (isFirstRow) style.setForegroundColor(Color.fromArgb(221, 235, 247));
        cells.get(row, column).setStyle(style);
    }

    private String getText(String resourceId) {
        return TextResource.localize(resourceId);
    }
}
