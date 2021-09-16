package nts.uk.file.at.infra.schedule.personalschedulebyindividual;

import com.aspose.cells.BackgroundType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.PageOrientationType;
import com.aspose.cells.PageSetup;
import com.aspose.cells.PaperSizeType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;
import lombok.experimental.var;
import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.at.schedule.dom.shift.management.DateInformation;
import nts.uk.file.at.app.export.schedule.personalScheduleByIndividual.PersonalScheduleByIndividualExportGenerator;
import nts.uk.file.at.app.export.schedule.personalScheduleByIndividual.PersonalScheduleIndividualDataSource;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class AsposePersonalScheduleByIndividualExportGenerator extends AsposeCellsReportGenerator implements PersonalScheduleByIndividualExportGenerator {
    private final String FONT_NAME = "ＭＳ ゴシック";
    private final String EXCEL_EXT = ".xlsx";
    private static final String TEMPLATE_FILE = "report/KSU002B.xlsx";
    private static final int NUMBER_ROW_OF_PAGE = 37;
    private final String SPACE = "　";
    private final String COLON = "　：　";

    private final int BG_COLOR_SPECIFIC_DAY = Integer.parseInt("ffc0cb", 16);
    private final int TEXT_COLOR_SUNDAY = Integer.parseInt("ff0000", 16);

    @Override
    public void generate(FileGeneratorContext context, PersonalScheduleIndividualDataSource dataSource) {
        try {
            val designer = this.createContext(TEMPLATE_FILE);
            Workbook workbook = designer.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            // Get first sheet in template
            Worksheet worksheet = worksheets.get(0);

            this.pageSetting(worksheet, dataSource);
            this.printHeader(worksheet, dataSource);
            this.printContent(worksheet, dataSource);

            designer.getDesigner().setWorkbook(workbook);
            designer.processDesigner();

            // Save as excel file
            designer.saveAsExcel(this.createNewFile(context, this.getReportName("NAME_TODO" + EXCEL_EXT)));  //TODO
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void pageSetting(Worksheet worksheet, PersonalScheduleIndividualDataSource dataSource) {
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setPaperSize(PaperSizeType.PAPER_A_4);
        pageSetup.setOrientation(PageOrientationType.LANDSCAPE);
        String a11 = dataSource.getCompanyName();
        val a12 = TextResource.localize("KSU002_56");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        val a13 = formatter.format(new Date());
//        pageSetup.setFitToPagesTall(0);
//        pageSetup.setFitToPagesWide(1);
//        pageSetup.setTopMarginInch(0.98);
//        pageSetup.setBottomMarginInch(0.39);
//        pageSetup.setLeftMarginInch(0.39);
//        pageSetup.setRightMarginInch(0.39);
//        pageSetup.setHeaderMarginInch(0.39);
//        pageSetup.setFooterMarginInch(0.31);
//        pageSetup.setCenterHorizontally(true);
        pageSetup.setHeader(0, "&9&\"MS ゴシック\"" + "Company_Name");
        pageSetup.setHeader(1, "&16&\"MS ゴシック\"" + "title");
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm", Locale.JAPAN);
        pageSetup.setHeader(2, "&9&\"MS ゴシック\"" + LocalDateTime.now().format(fullDateTimeFormatter) + "\npage &P");
    }

    private void printHeader(Worksheet worksheet, PersonalScheduleIndividualDataSource dataSource) {
        var b11 = TextResource.localize("KSU002_57");

    }

    private void printContent(Worksheet worksheet, PersonalScheduleIndividualDataSource dataSource) {

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

    private void removeTemplate(Worksheet worksheet) {
        removeFirstShapes(worksheet);
        Cells cells = worksheet.getCells();
        cells.deleteRows(0, NUMBER_ROW_OF_PAGE);
    }

    private void removeFirstShapes(Worksheet worksheet) {
        if (worksheet.getShapes().getCount() > 0) {
            worksheet.getShapes().removeAt(0);
        }
    }

    private void setTopBorderStyle(Cell cell) {
        Style style = cell.getStyle();
        style.setBorder(BorderType.TOP_BORDER, CellBorderType.MEDIUM, Color.getBlack());
        cell.setStyle(style);
    }

    private void setBottomBorderStyle(Cell cell) {
        Style style = cell.getStyle();
        style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.MEDIUM, Color.getBlack());
        cell.setStyle(style);
    }

    private void setBackgroundGray(Cell cell) {
        Style style = cell.getStyle();
        style.setForegroundColor(Color.getGray());
        cell.setStyle(style);
    }

    private void setCurrentMonthBackground(Cell cell) {
        Style style = cell.getStyle();
        style.setForegroundColor(Color.fromArgb(197, 217, 241));
        cell.setStyle(style);
    }

    private void setForegroundRed(Cell cell) {
        Style style = cell.getStyle();
        style.getFont().setColor(Color.getRed());
        cell.setStyle(style);
    }
}
