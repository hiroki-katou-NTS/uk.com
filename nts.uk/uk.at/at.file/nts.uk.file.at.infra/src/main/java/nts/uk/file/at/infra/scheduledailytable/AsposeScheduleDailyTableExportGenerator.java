package nts.uk.file.at.infra.scheduledailytable;

import com.aspose.cells.*;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.aggregation.dom.common.ScheRecAtr;
import nts.uk.ctx.at.aggregation.dom.common.ScheRecGettingAtr;
import nts.uk.ctx.at.aggregation.dom.scheduledailytable.PersonCounterTimesNumberCounterResult;
import nts.uk.ctx.at.aggregation.dom.scheduledailytable.WorkplaceCounterTimesNumberCounterResult;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.LicenseClassification;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.file.at.app.export.scheduledailytable.*;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.time.calendar.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AsposeScheduleDailyTableExportGenerator extends AsposeCellsReportGenerator implements ScheduleDailyTableExportGenerator {
    private final String FONT_NAME = "ＭＳ ゴシック";
    private final int FONT_SIZE = 9;
    private final String EXCEL_EXT = ".xlsx";
    private final String SPACE = "　";
    private final String COLON = "：";

    private final int COLUMN_WIDTH = 4;
    private final int START_HEADER_ROW = 6;
    private final int START_DATA_ROW = 8;
    private final int START_DATE_COL = 5;
    private final int MAX_ROWS_PER_PAGE_28 = 48;
    private final int MAX_ROWS_PER_PAGE_30 = 50;
    private final int MAX_ROWS_PER_PAGE_31 = 52;

    private int startRow = START_DATA_ROW;
    private final List<Integer> indexes = new ArrayList<>();

    @Override
    public void generate(FileGeneratorContext context, ScheduleDailyTableDataSource dataSource, DatePeriod period, boolean displayBothWhenDiffOnly) {
        AsposeCellsReportContext designer = this.createEmptyContext("SCHEDULE_DAILY_TABLE");
        Workbook workbook = designer.getWorkbook();
        for (int i = 0; i < dataSource.getDisplayInfos().size(); i++) {
            startRow = START_DATA_ROW;
            indexes.clear();
            WkpGroupRelatedDisplayInfoDto wkgGroupData = dataSource.getDisplayInfos().get(i);
            if (i > 0) workbook.getWorksheets().add();
            Worksheet worksheet = workbook.getWorksheets().get(i);
            worksheet.setName(wkgGroupData.getWkpGroupCode() + SPACE + wkgGroupData.getWkpGroupName());
            this.settingPage(worksheet, dataSource.getCompanyName(), dataSource.getScheduleDailyTableName());
            this.printHeader(worksheet, wkgGroupData, dataSource.getHeadingTitles(), period);
            this.printContent(worksheet, wkgGroupData, period, displayBothWhenDiffOnly, dataSource.getComment());
            this.handlePageBreak(worksheet, period.datesBetween().size());
			worksheet.setGridlinesVisible(false);
            worksheet.setViewType(ViewType.PAGE_LAYOUT_VIEW);
        }
        designer.processDesigner();
        designer.saveAsExcel(this.createNewFile(context, this.getReportName(dataSource.getScheduleDailyTableName() + EXCEL_EXT)));
    }

    private void settingPage(Worksheet worksheet, String companyName, String title) {
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setPaperSize(PaperSizeType.PAPER_A_4);
        pageSetup.setOrientation(PageOrientationType.LANDSCAPE);
        pageSetup.setFitToPagesTall(0);
        pageSetup.setFitToPagesWide(1);
        pageSetup.setTopMarginInch(0.98);
        pageSetup.setBottomMarginInch(0.39);
        pageSetup.setLeftMarginInch(0.2);
        pageSetup.setRightMarginInch(0.2);
        pageSetup.setHeaderMarginInch(0.39);
        pageSetup.setFooterMarginInch(0.31);
        pageSetup.setCenterHorizontally(true);
        pageSetup.setHeader(0, "&9&\"" + FONT_NAME + "\"" + companyName);
        pageSetup.setHeader(1, "&16&\"" + FONT_NAME + ",Bold\"" + title);
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd  HH:mm", Locale.JAPAN);
        pageSetup.setHeader(2, "&9&\"" + FONT_NAME + "\"" + LocalDateTime.now().format(fullDateTimeFormatter) + "\npage&P ");
        pageSetup.setPrintTitleRows("$1:$8");
    }

    private String getText(String resourceId) {
        return TextResource.localize(resourceId);
    }

    private String getDayOfWeek(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY: return "月";
            case TUESDAY: return "火";
            case WEDNESDAY: return "水";
            case THURSDAY: return "木";
            case FRIDAY: return "金";
            case SATURDAY: return "土";
            case SUNDAY: return "日";
            default: return "";
        }
    }

    private void printHeader(Worksheet worksheet, WkpGroupRelatedDisplayInfoDto dataSource, List<String> headingTitles, DatePeriod period) {
        Cells cells = worksheet.getCells();

        Style styleB1 = cells.get(3, 0).getStyle();
        styleB1.setVerticalAlignment(TextAlignmentType.CENTER);
        styleB1.getFont().setName(FONT_NAME);
        styleB1.getFont().setSize(FONT_SIZE);
        // B1_1
        cells.get(3, 0).setValue(getText("Com_WorkplaceGroup") + COLON + dataSource.getWkpGroupCode() + SPACE + dataSource.getWkpGroupName());
        cells.get(3, 0).setStyle(styleB1);
        // B1_2
        cells.get(4, 0).setValue(getText("KSU001_4129")
                + period.start().toString() + "(" + this.getDayOfWeek(period.start().dayOfWeekEnum()) + ")"
                + getText("KSU001_4130")
                + period.end().toString() + "(" + this.getDayOfWeek(period.end().dayOfWeekEnum()) + ")");
        cells.get(4, 0).setStyle(styleB1);

        // C1
        cells.setColumnWidth(0, COLUMN_WIDTH);
        cells.setColumnWidth(1, COLUMN_WIDTH);
        cells.setColumnWidth(2, COLUMN_WIDTH);
        cells.setColumnWidth(3, COLUMN_WIDTH);
        cells.setColumnWidth(4, COLUMN_WIDTH);

        Style style = cells.get(START_HEADER_ROW, 0).getStyle();
        style.setVerticalAlignment(TextAlignmentType.CENTER);
        style.setHorizontalAlignment(TextAlignmentType.CENTER);
        style.getFont().setSize(FONT_SIZE);
        style.getFont().setName(FONT_NAME);
        style.setShrinkToFit(true);
        style.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.DOTTED);
        style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.DOTTED);
        style.setPattern(BackgroundType.SOLID);
        style.setForegroundColor(Color.fromArgb(155, 194, 230));

        cells.get(START_HEADER_ROW, 0).setValue(getText("KSU011_12"));
        style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.MEDIUM);
        style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.NONE);
        cells.get(START_HEADER_ROW, 0).setStyle(style);
        cells.get(START_HEADER_ROW, 1).setStyle(style);
        cells.get(START_HEADER_ROW, 2).setStyle(style);
        cells.get(START_HEADER_ROW, 3).setStyle(style);
        cells.get(START_HEADER_ROW, 4).setStyle(style);
        cells.merge(START_HEADER_ROW, 0, 1, 5);

        cells.get(START_HEADER_ROW + 1, 0).setValue(getText("KSU011_14"));
        style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.NONE);
        style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.MEDIUM);
        cells.get(START_HEADER_ROW + 1, 0).setStyle(style);
        cells.get(START_HEADER_ROW + 1, 1).setStyle(style);
        cells.get(START_HEADER_ROW + 1, 2).setStyle(style);
        cells.get(START_HEADER_ROW + 1, 3).setStyle(style);
        cells.get(START_HEADER_ROW + 1, 4).setStyle(style);
        cells.merge(START_HEADER_ROW + 1, 0, 1, 5);

        int column = START_DATE_COL;
        List<GeneralDate> targetDates = period.datesBetween();
        for (int i = 0; i < targetDates.size(); i++) {
            GeneralDate date = targetDates.get(i);
            cells.setColumnWidth(column, COLUMN_WIDTH);
            if (i == targetDates.size() - 1) style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.DOUBLE);
            else style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.DOTTED);

            cells.get(START_HEADER_ROW, column).setValue(date.toString(i == 0 || date.day() == 1 ? "M/d" : "d"));
            style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.MEDIUM);
            style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.NONE);
            cells.get(START_HEADER_ROW, column).setStyle(style);

            cells.get(START_HEADER_ROW + 1, column).setValue(this.getDayOfWeek(date.dayOfWeekEnum()));
            style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.NONE);
            style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.MEDIUM);
            cells.get(START_HEADER_ROW + 1, column).setStyle(style);

            column++;
        }

        // B2
        Style styleB2 = styleB1;
        styleB2.setHorizontalAlignment(TextAlignmentType.CENTER);
        styleB2.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.THIN);
        styleB2.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.THIN);
        styleB2.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.THIN);
        styleB2.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.THIN);
        for (int row = 1; row < 5; row++) {
            for (int col = column + 1; col < column + 19; col ++) {
                cells.get(row, col).setStyle(styleB2);
            }
        }
        for (int i = 0; i < 6; i++) {
            String title = headingTitles.size() > i ? headingTitles.get(i) : "";
            cells.get(1, column + i * 3 + 1).setValue(title);
            cells.merge(1, column + i * 3 + 1, 1, 3);
            cells.merge(2, column + i * 3 + 1, 3, 3);
        }

        // E1
        for (int i = 0; i < 20; i++) {
            cells.setColumnWidth(column, COLUMN_WIDTH / 2);
            if (i == targetDates.size() - 1) style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.DOUBLE);
            else style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.DOTTED);

            style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.MEDIUM);
            style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.NONE);
            cells.get(START_HEADER_ROW, column).setStyle(style);

            style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.NONE);
            style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.MEDIUM);
            cells.get(START_HEADER_ROW + 1, column).setStyle(style);

            if (i % 2 == 0) {
                String value = dataSource.getPersonalTotals().size() > i / 2
                        ? dataSource.getPersonalTotals().get(i / 2).getTotalTimesABName().v()
                        : "";
                cells.get(START_HEADER_ROW, column).setValue(value);
            } else
                cells.merge(START_HEADER_ROW, column - 1, 2, 2);
            column++;
        }
    }

    private void setShiftColor(Style style, Optional<ShiftDisplayInfoDto> shiftInfo) {
        if (shiftInfo.isPresent() && shiftInfo.get().getAttendanceHolidayAttr().isPresent()) {
            if (shiftInfo.get().getAttendanceHolidayAttr().get() == AttendanceHolidayAttr.FULL_TIME)
                style.getFont().setColor(Color.getBlue());
            else if (shiftInfo.get().getAttendanceHolidayAttr().get() == AttendanceHolidayAttr.HOLIDAY)
                style.getFont().setColor(Color.getRed());
            else
                style.getFont().setColor(Color.fromArgb(255, 127, 39));
        } else {
            style.getFont().setColor(Color.getBlack());
        }
    }

    private void printContent(Worksheet worksheet, WkpGroupRelatedDisplayInfoDto dataSource, DatePeriod period, boolean displayBothWhenDiffOnly, String comment) {
        Cells cells = worksheet.getCells();
        List<GeneralDate> targetDates = period.datesBetween();
        for (int i = 0; i < dataSource.getEmployeeInfos().size(); i++) {
            EmployeeInfoDto employee = dataSource.getEmployeeInfos().get(i);
            boolean lastEmployee = i == dataSource.getEmployeeInfos().size() - 1;
            indexes.add(startRow);

            // D1
            Style style = cells.get(startRow, 0).getStyle();
            style.setVerticalAlignment(TextAlignmentType.CENTER);
            style.setHorizontalAlignment(TextAlignmentType.LEFT);
            style.getFont().setSize(FONT_SIZE);
            style.getFont().setName(FONT_NAME);
            style.setShrinkToFit(true);
            style.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.DOTTED);
            style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.DOTTED);
            style.setPattern(BackgroundType.SOLID);

            cells.get(startRow, 0).setValue(employee.getNursingClsName() == null ? "" : employee.getNursingClsName());
            style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.THIN);
            style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.NONE);
            cells.get(startRow, 0).setStyle(style);
            cells.get(startRow, 1).setStyle(style);
            cells.get(startRow, 2).setStyle(style);
            cells.get(startRow, 3).setStyle(style);
            cells.merge(startRow, 0, 1, 4);

            cells.get(startRow + 1, 0).setValue(employee.getEmployeeName() == null ? "" : SPACE + employee.getEmployeeName());
            style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.NONE);
            style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(lastEmployee ? CellBorderType.DOUBLE : CellBorderType.THIN);
            cells.get(startRow + 1, 0).setStyle(style);
            cells.get(startRow + 1, 1).setStyle(style);
            cells.get(startRow + 1, 2).setStyle(style);
            cells.get(startRow + 1, 3).setStyle(style);
            cells.merge(startRow + 1, 0, 1, 4);

            style.setHorizontalAlignment(TextAlignmentType.CENTER);

            cells.get(startRow, 4).setValue(getText("KSU011_83"));
            style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.THIN);
            style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.DOTTED);
            style.setForegroundColor(Color.getWhite());
            cells.get(startRow, 4).setStyle(style);

            cells.get(startRow + 1, 4).setValue(getText("KSU011_84"));
            style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.DOTTED);
            style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(lastEmployee ? CellBorderType.DOUBLE : CellBorderType.THIN);
            style.setForegroundColor(Color.fromArgb(221, 235, 247));
            cells.get(startRow + 1, 4).setStyle(style);

            // D2
            for (int d = 0; d < targetDates.size(); d++) {
                GeneralDate date = targetDates.get(d);
                Optional<ShiftDisplayInfoDto> scheduleShiftInfo = dataSource.getShiftDisplayInfos().stream()
                        .filter(shift -> shift.getEmployeeId().equals(employee.getEmployeeId()) && shift.getYmd().equals(date) && shift.getTargetData() == ScheRecGettingAtr.ONLY_SCHEDULE)
                        .findFirst();
                Optional<ShiftDisplayInfoDto> recordShiftInfo = dataSource.getShiftDisplayInfos().stream()
                        .filter(shift -> shift.getEmployeeId().equals(employee.getEmployeeId()) && shift.getYmd().equals(date) && shift.getTargetData() == ScheRecGettingAtr.ONLY_RECORD)
                        .findFirst();
                String scheduleShiftName = scheduleShiftInfo.isPresent() ? scheduleShiftInfo.get().getShiftName().orElse("") : null;
                String recordShiftName = recordShiftInfo.isPresent() ? recordShiftInfo.get().getShiftName().orElse("") : null;

                if (d == targetDates.size() - 1) style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.DOUBLE);
                else style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.DOTTED);

                style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.THIN);
                style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.DOTTED);
                style.setForegroundColor(Color.getWhite());
                this.setShiftColor(style, scheduleShiftInfo);
                cells.get(startRow, START_DATE_COL + d).setStyle(style);
                if (scheduleShiftName != null) {
                    cells.get(startRow, START_DATE_COL + d).setValue(scheduleShiftName.isEmpty() ? getText("KSU011_86") : scheduleShiftName);
                }

                style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.DOTTED);
                style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(lastEmployee ? CellBorderType.DOUBLE : CellBorderType.THIN);
                style.setForegroundColor(Color.fromArgb(221, 235, 247));
                this.setShiftColor(style, recordShiftInfo);
                cells.get(startRow + 1, START_DATE_COL + d).setStyle(style);
                if (displayBothWhenDiffOnly) {
                    if (scheduleShiftName != null && recordShiftName != null && !scheduleShiftName.equals(recordShiftName)) {
                        cells.get(startRow + 1, START_DATE_COL + d).setValue(recordShiftName.isEmpty() ? getText("KSU011_86") : recordShiftName);
                    }
                } else {
                    if (recordShiftName != null) {
                        cells.get(startRow + 1, START_DATE_COL + d).setValue(recordShiftName.isEmpty() ? getText("KSU011_86") : recordShiftName);
                    }
                }
            }

            int personalTotalCol = START_DATE_COL + targetDates.size();
            style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.DOTTED);
            style.setHorizontalAlignment(TextAlignmentType.RIGHT);
            for (int t = 0; t < 20; t++) {
                style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.THIN);
                style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.DOTTED);
                style.setForegroundColor(Color.getWhite());
                cells.get(startRow, personalTotalCol + t).setStyle(style);

                style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.DOTTED);
                style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(lastEmployee ? CellBorderType.DOUBLE : CellBorderType.THIN);
                style.setForegroundColor(Color.fromArgb(221, 235, 247));
                cells.get(startRow + 1, personalTotalCol + t).setStyle(style);

                if (t % 2 == 0) {
                    if (dataSource.getPersonalTotals().size() > t / 2) {
                        TotalTimes totalCount = dataSource.getPersonalTotals().get(t / 2);
                        Optional<PersonCounterTimesNumberCounterResult> totalResultSchedule = dataSource.getPersonalCounterResult().stream().filter(pt -> pt.getSid().v().equals(employee.getEmployeeId()) && pt.getTotalCountNo() == totalCount.getTotalCountNo() && pt.getScheRecAtr() == ScheRecAtr.SCHEDULE).findFirst();
                        Optional<PersonCounterTimesNumberCounterResult> totalResultRecord = dataSource.getPersonalCounterResult().stream().filter(pt -> pt.getSid().v().equals(employee.getEmployeeId()) && pt.getTotalCountNo() == totalCount.getTotalCountNo() && pt.getScheRecAtr() == ScheRecAtr.RECORD).findFirst();
                        cells.get(startRow, personalTotalCol + t).setValue(totalResultSchedule.isPresent() ? totalResultSchedule.get().getValue().toString() : "");
                        cells.get(startRow + 1, personalTotalCol + t).setValue(totalResultRecord.isPresent() ? totalResultRecord.get().getValue().toString() : "");
                    }
                } else {
                    cells.merge(startRow, personalTotalCol + t - 1, 1, 2);
                    cells.merge(startRow + 1, personalTotalCol + t - 1, 1, 2);
                }
            }
            startRow += 2;
        }

        // F1, F2, F3
        int startTotalRow = startRow;
        for (int i = 0; i < 5; i++) {
            indexes.add(startRow);
            Style style = cells.get(startRow, 0).getStyle();
            style.setVerticalAlignment(TextAlignmentType.CENTER);
            style.setHorizontalAlignment(TextAlignmentType.CENTER);
            style.getFont().setSize(FONT_SIZE);
            style.getFont().setName(FONT_NAME);
            style.setShrinkToFit(true);
            style.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.DOTTED);
            style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.DOTTED);
            style.setPattern(BackgroundType.SOLID);
            for (int j = -5; j < targetDates.size(); j++) {
                if (j == targetDates.size() - 1) style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.DOUBLE);
                else style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.DOTTED);

                if (i == 0 && j == -5) {
                    style.setVerticalAlignment(TextAlignmentType.TOP);
                    style.setHorizontalAlignment(TextAlignmentType.LEFT);
                    cells.get(startRow, START_DATE_COL + j).setValue(dataSource.getEmployeeInfos().size() + "人");
                } else if (j == -3) {
                    if (dataSource.getWorkplaceTotals().size() > i) {
                        TotalTimes totalCount = dataSource.getWorkplaceTotals().get(i);
                        cells.get(startRow, START_DATE_COL + j).setValue(totalCount.getTotalTimesABName().v());
                    }
                } else if (j == -2) {
                    style.setHorizontalAlignment(TextAlignmentType.LEFT);
                    if (dataSource.getWorkplaceTotals().size() > i) cells.get(startRow, START_DATE_COL + j).setValue(getText("Enum_LicenseClassification_NURSE")); //LicenseClassification.NURSE.nameId
                } else if (j >= 0) {
                    style.setHorizontalAlignment(TextAlignmentType.RIGHT);
                    if (dataSource.getWorkplaceTotals().size() > i) {
                        TotalTimes totalCount = dataSource.getWorkplaceTotals().get(i);
                        GeneralDate date = targetDates.get(j);
                        Optional<WorkplaceCounterTimesNumberCounterResult> totalResult = dataSource.getWorkplaceCounterResult().stream()
                                .filter(result -> result.getYmd().equals(date) && result.getTotalCountNo() == totalCount.getTotalCountNo() && result.getLicenseCls() == LicenseClassification.NURSE)
                                .findFirst();
                        cells.get(startRow, START_DATE_COL + j).setValue(totalResult.isPresent() ? totalResult.get().getValue().toString() : "");
                    }
                } else {
                    style.setVerticalAlignment(TextAlignmentType.CENTER);
                    style.setHorizontalAlignment(TextAlignmentType.CENTER);
                }
                style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.THIN);
                style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.DOTTED);
                style.setForegroundColor(Color.getWhite());
                cells.get(startRow, START_DATE_COL + j).setStyle(style);

                if (j == -2) {
                    style.setHorizontalAlignment(TextAlignmentType.LEFT);
                    if (dataSource.getWorkplaceTotals().size() > i) cells.get(startRow + 1, START_DATE_COL + j).setValue(getText("Enum_LicenseClassification_NURSE_ASSOCIATE")); //LicenseClassification.NURSE_ASSOCIATE.nameId
                } else if (j >= 0) {
                    style.setHorizontalAlignment(TextAlignmentType.RIGHT);
                    if (dataSource.getWorkplaceTotals().size() > i) {
                        TotalTimes totalCount = dataSource.getWorkplaceTotals().get(i);
                        GeneralDate date = targetDates.get(j);
                        Optional<WorkplaceCounterTimesNumberCounterResult> totalResult = dataSource.getWorkplaceCounterResult().stream()
                                .filter(result -> result.getYmd().equals(date) && result.getTotalCountNo() == totalCount.getTotalCountNo() && result.getLicenseCls() == LicenseClassification.NURSE_ASSOCIATE)
                                .findFirst();
                        cells.get(startRow + 1, START_DATE_COL + j).setValue(totalResult.isPresent() ? totalResult.get().getValue().toString() : "");
                    }
                }
                style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.DOTTED);
                style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.DOTTED);
                style.setForegroundColor(Color.fromArgb(221, 235, 247));
                cells.get(startRow + 1, START_DATE_COL + j).setStyle(style);

                if (j == -2) {
                    style.setHorizontalAlignment(TextAlignmentType.LEFT);
                    if (dataSource.getWorkplaceTotals().size() > i) cells.get(startRow + 2, START_DATE_COL + j).setValue(getText("Enum_LicenseClassification_NURSE_ASSIST")); //LicenseClassification.NURSE_ASSIST.nameId
                } else if (j >= 0) {
                    style.setHorizontalAlignment(TextAlignmentType.RIGHT);
                    if (dataSource.getWorkplaceTotals().size() > i) {
                        TotalTimes totalCount = dataSource.getWorkplaceTotals().get(i);
                        GeneralDate date = targetDates.get(j);
                        Optional<WorkplaceCounterTimesNumberCounterResult> totalResult = dataSource.getWorkplaceCounterResult().stream()
                                .filter(result -> result.getYmd().equals(date) && result.getTotalCountNo() == totalCount.getTotalCountNo() && result.getLicenseCls() == LicenseClassification.NURSE_ASSIST)
                                .findFirst();
                        cells.get(startRow + 2, START_DATE_COL + j).setValue(totalResult.isPresent() ? totalResult.get().getValue().toString() : "");
                    }
                }
                style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.DOTTED);
                style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.THIN);
                style.setForegroundColor(Color.getWhite());
                cells.get(startRow + 2, START_DATE_COL + j).setStyle(style);
            }
            cells.merge(startRow, 2, 3, 1);
            cells.merge(startRow, 3, 1, 2);
            cells.merge(startRow + 1, 3, 1, 2);
            cells.merge(startRow + 2, 3, 1, 2);
            startRow += 3;
        }
        cells.merge(startTotalRow, 0, 15, 2);

        // G1_1
        cells.get(startTotalRow, START_DATE_COL + targetDates.size()).setValue(comment);
        cells.merge(startTotalRow, START_DATE_COL + targetDates.size(), 15, 20);
        Style commentStyle = cells.get(indexes.get(indexes.size() - 1), START_DATE_COL + targetDates.size()).getStyle();
        commentStyle.getFont().setName(FONT_NAME);
        commentStyle.getFont().setSize(FONT_SIZE);
        commentStyle.setHorizontalAlignment(TextAlignmentType.LEFT);
        commentStyle.setVerticalAlignment(TextAlignmentType.TOP);
        commentStyle.setTextWrapped(true);
        cells.get(startTotalRow, START_DATE_COL + targetDates.size()).setStyle(commentStyle);

        indexes.add(startRow);
    }

    private void handlePageBreak(Worksheet worksheet, int days) {
        int start = START_DATA_ROW;
        int maxRowsPerPage;
        if (days == 31) maxRowsPerPage = MAX_ROWS_PER_PAGE_31;
        else if (days == 30) maxRowsPerPage = MAX_ROWS_PER_PAGE_30;
        else maxRowsPerPage = MAX_ROWS_PER_PAGE_28;
        for (int i = 0; i < indexes.size(); i++) {
            if (indexes.get(i) - start > maxRowsPerPage) {
                worksheet.getHorizontalPageBreaks().add(indexes.get(i - 1).intValue());
                start = indexes.get(i - 1);
            }
        }
    }
}
