package nts.uk.file.at.infra.schedule.personalschedulebyworkplace;

import com.aspose.cells.*;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.calendar.DayOfWeek;
import nts.uk.ctx.at.aggregation.dom.scheduletable.OneRowOutputItem;
import nts.uk.ctx.at.aggregation.dom.scheduletable.PersonalInfoScheduleTable;
import nts.uk.ctx.at.aggregation.dom.scheduletable.ScheduleTableAttendanceItem;
import nts.uk.ctx.at.aggregation.dom.scheduletable.ScheduleTablePersonalInfoItem;
import nts.uk.ctx.at.schedule.dom.shift.management.DateInformation;
import nts.uk.file.at.app.export.schedule.personalschedulebyworkplace.EmployeeOneDayAttendanceInfo;
import nts.uk.file.at.app.export.schedule.personalschedulebyworkplace.PersonalScheduleByWkpDataSource;
import nts.uk.file.at.app.export.schedule.personalschedulebyworkplace.PersonalScheduleByWorkplaceExportGenerator;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class AsposePersonalScheduleByWorkplaceExportGenerator extends AsposeCellsReportGenerator implements PersonalScheduleByWorkplaceExportGenerator {
    private final String FONT_NAME = "ＭＳ ゴシック";
    private final String PDF_EXT = ".pdf";
    private final String EXCEL_EXT = ".xlsx";
    private final String SPACE = "　";
    private final String COLON = "　：　";

    private final int BG_COLOR_SPECIFIC_DAY = Integer.parseInt("ffc0cb", 16);
    private final int BG_COLOR_SUNDAY = Integer.parseInt("fabf8f", 16);
    private final int BG_COLOR_SATURDAY = Integer.parseInt("8bd8ff", 16);
    private final int BG_COLOR_WEEKDAY = Integer.parseInt("D9D9D9", 16);
    private final int TEXT_COLOR_SUNDAY = Integer.parseInt("ff0000", 16);
    private final int TEXT_COLOR_SATURDAY = Integer.parseInt("0000ff", 16);
    private final int TEXT_COLOR_WEEKDAY = Integer.parseInt("404040", 16);

    @Override
    public void generate(FileGeneratorContext context, PersonalScheduleByWkpDataSource dataSource, boolean excel) {
        try {
            AsposeCellsReportContext reportContext = this.createEmptyContext("PersonalScheduleByWorkplace");
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            Worksheet worksheet = worksheets.get(0);
            worksheet.setName(dataSource.getOutputSetting().getName().v());
            this.settingPage(worksheet, dataSource);
            this.printHeader(worksheet, dataSource);
            this.printContent(worksheet, dataSource);
            reportContext.processDesigner();
            if (excel) {
                // save as excel file
                reportContext.saveAsExcel(this.createNewFile(context, this.getReportName(dataSource.getOutputSetting().getName().v() + EXCEL_EXT)));
            } else {
                // save as PDF file
                reportContext.saveAsPdf(this.createNewFile(context, this.getReportName(dataSource.getOutputSetting().getName().v() + PDF_EXT)));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void settingPage(Worksheet worksheet, PersonalScheduleByWkpDataSource dataSource) {
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
        pageSetup.setHeader(0, "&9&\"" + FONT_NAME + "\"" + dataSource.getCompanyName());
        pageSetup.setHeader(1, "&16&\"" + FONT_NAME + ",Bold\"" + dataSource.getOutputSetting().getName().v());
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd  HH:mm", Locale.JAPAN);
        pageSetup.setHeader(2, "&9&\"" + FONT_NAME + "\"" + LocalDateTime.now().format(fullDateTimeFormatter) + "\npage&P ");
    }

    private String getText(String resourceId) {
        return TextResource.localize(resourceId);
    }

    private void printHeader(Worksheet worksheet, PersonalScheduleByWkpDataSource dataSource) {
        Cells cells = worksheet.getCells();
        // B part
        cells.get("B1").setValue(dataSource.getComment());
        cells.get("A3").setValue(
                getText("KSU001_4129")
                        + dataSource.getPeriod().start().toString() + "(" + this.getDayOfWeek(dataSource.getPeriod().start().dayOfWeekEnum()) + ")"
                        + getText("KSU001_4130")
                        + dataSource.getPeriod().end().toString() + "(" + this.getDayOfWeek(dataSource.getPeriod().end().dayOfWeekEnum()) + ")"
        );
        Style styleA3 = cells.get("A3").getStyle();
        styleA3.getFont().setSize(9);
        styleA3.getFont().setName(FONT_NAME);
        styleA3.getFont().setUnderline(1);
        cells.get("A3").setStyle(styleA3);
        cells.get("A4").setValue((dataSource.getOrgUnit() == 0 ? getText("KSU001_4132") : getText("KSU001_4133")) + dataSource.getOrganizationDisplayInfo().getCode() + SPACE + dataSource.getOrganizationDisplayInfo().getDisplayName());
        cells.get("A4").setStyle(styleA3);

        // C1 part
        cells.get("A6").setValue(getText("KSU001_4131"));
        Style styleA6 = cells.get("A6").getStyle();
        styleA6.setHorizontalAlignment(TextAlignmentType.CENTER);
        styleA6.setVerticalAlignment(TextAlignmentType.TOP);
        styleA6.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.THIN);
        styleA6.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(CellBorderType.THIN);
        styleA6.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.THIN);
        styleA6.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.THIN);
        styleA6.setPattern(BackgroundType.SOLID);
        styleA6.setForegroundColor(Color.fromArgb(BG_COLOR_WEEKDAY));
        styleA6.getFont().setColor(Color.fromArgb(TEXT_COLOR_WEEKDAY));
        styleA6.getFont().setName(FONT_NAME);
        styleA6.getFont().setSize(9);
        cells.get("A6").setStyle(styleA6);
        cells.get("A7").setStyle(styleA6);
        cells.get("A8").setStyle(styleA6);
        cells.get("A9").setStyle(styleA6);
        cells.merge(5, 0, 4, 1, true);
        cells.setColumnWidth(0, 18);

        // C2 part
        long additionCount = dataSource.getOutputSetting().getOutputItem().getDetails().stream().filter(i -> i.getAdditionalInfo().isPresent()).count();
        if (additionCount == 0) {
            cells.hideColumn(1);
        } else if (additionCount == 1) {
            cells.get("B6").setValue(dataSource.getOutputSetting().getOutputItem().getDetails().stream().filter(i -> i.getAdditionalInfo().isPresent()).findFirst().get().getAdditionalInfo().get().nameId);
            cells.setColumnWidth(1, 12);
        } else {
            cells.setColumnWidth(1, 12);
        }
        cells.get("B6").setStyle(styleA6);
        cells.get("B7").setStyle(styleA6);
        cells.get("B8").setStyle(styleA6);
        cells.get("B9").setStyle(styleA6);
        cells.merge(5, 1, 4, 1, true);

        // C3 part
        int startCol = 2;
        for (int i = 0; i < dataSource.getDateInfos().size(); i++) {
            DateInformation dateInfo = dataSource.getDateInfos().get(i);
            cells.get(5, startCol + i).setValue(dateInfo.getYmd().toString(i == 0 || dateInfo.getYmd().day() == 1 ? "M/d" : "d") + "\n" + this.getDayOfWeek(dateInfo.getDayOfWeek()));
            if (dateInfo.getOptCompanyEventName().isPresent()) {
                cells.get(7, startCol + i).setValue(dateInfo.getOptCompanyEventName().get().v());
            }
            if (dateInfo.getOptWorkplaceEventName().isPresent()) {
                cells.get(8, startCol + i).setValue(dateInfo.getOptWorkplaceEventName().get().v());
            }
            this.setHeaderStyle(cells.get(5, startCol + i), dateInfo, true);
            this.setHeaderStyle(cells.get(6, startCol + i), dateInfo, true);
            this.setHeaderStyle(cells.get(7, startCol + i), dateInfo, false);
            this.setHeaderStyle(cells.get(8, startCol + i), dateInfo, false);
            cells.merge(5, startCol + i, 2, 1, true);
        }
        // TODO: C4 part

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
        } else if (dateInfo.isHoliday() || dateInfo.getDayOfWeek() == DayOfWeek.SUNDAY) {
            style.setForegroundColor(Color.fromArgb(BG_COLOR_SUNDAY));
            style.getFont().setColor(Color.fromArgb(TEXT_COLOR_SUNDAY));
        } else if (dateInfo.getDayOfWeek() == DayOfWeek.SATURDAY) {
            style.setForegroundColor(Color.fromArgb(BG_COLOR_SATURDAY));
            style.getFont().setColor(Color.fromArgb(TEXT_COLOR_SATURDAY));
        } else {
            style.setForegroundColor(Color.fromArgb(BG_COLOR_WEEKDAY));
            style.getFont().setColor(Color.fromArgb(TEXT_COLOR_WEEKDAY));
        }
        style.getFont().setName(FONT_NAME);
        style.getFont().setSize(9);
        cell.setStyle(style);
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

    private void printContent(Worksheet worksheet, PersonalScheduleByWkpDataSource dataSource) {
        Cells cells = worksheet.getCells();
        // E part
        dataSource.getPersonalInfoScheduleTableList().sort(Comparator.comparing(i -> i.getEmployeeInfo().getEmployeeCode()));
        int rows = dataSource.getOutputSetting().getOutputItem().getDetails().size();
        int startRow = 9;
        long additionCount = dataSource.getOutputSetting().getOutputItem().getDetails().stream().filter(i -> i.getAdditionalInfo().isPresent()).count();
        // loop employee
        for (PersonalInfoScheduleTable emp : dataSource.getPersonalInfoScheduleTableList()) {
            List<OneRowOutputItem> personalItems = dataSource.getOutputSetting().getOutputItem().getDetails().stream().filter(i -> i.getPersonalInfo().isPresent()).collect(Collectors.toList());
            List<OneRowOutputItem> additionalItems = dataSource.getOutputSetting().getOutputItem().getDetails().stream().filter(i -> i.getAdditionalInfo().isPresent()).collect(Collectors.toList());
            List<OneRowOutputItem> attendanceItems = dataSource.getOutputSetting().getOutputItem().getDetails().stream().filter(i -> i.getAttendanceItem().isPresent()).collect(Collectors.toList());
            List<EmployeeOneDayAttendanceInfo> attendanceInfos = dataSource.getListEmpOneDayAttendanceInfo().stream().filter(i -> i.getEmployeeId().equals(emp.getEmployeeId())).collect(Collectors.toList());
            for (int i = 0; i < rows; i++) {
                // E1 part
                if (i < personalItems.size()) {
                    OneRowOutputItem personalItem = personalItems.get(i);
                    setPesonalInfoValue(cells, personalItem.getPersonalInfo(), emp, startRow + i, 0, false);
                }
                setPersonalStyle(cells, startRow + i, 0, i == 0);

                // E2 part
                if (i < additionalItems.size()) {
                    OneRowOutputItem additionalItem = additionalItems.get(i);
                    setPesonalInfoValue(cells, additionalItem.getAdditionalInfo(), emp, startRow + i, 1, additionCount == 1);
                }
                setPersonalStyle(cells, startRow + i, 1, i == 0);

                // E3 part
                int startCol = 2;
                for (int j = 0; j < dataSource.getDateInfos().size(); j++) {
                    DateInformation dateInfo = dataSource.getDateInfos().get(j);
                    Optional<EmployeeOneDayAttendanceInfo> attendanceInfo = attendanceInfos.stream().filter(info -> info.getDate().equals(dateInfo.getYmd())).findFirst();
                    this.setAttendanceValue(cells, attendanceInfo, attendanceItems.size() > i ? attendanceItems.get(i).getAttendanceItem().get() : null, startRow + i, startCol + j, i == 0);
                }
            }
            startRow += rows;
        }
    }

    private void setPesonalInfoValue(Cells cells, Optional<ScheduleTablePersonalInfoItem> item, PersonalInfoScheduleTable emp, int row, int column, boolean isValueOnly) {
        if (item.isPresent()) {
            String value = isValueOnly ? "" : item.get().nameId + COLON;
            switch (item.get()) {
                case EMPLOYEE_NAME:
                    value = emp.getEmployeeInfo().getEmployeeCode() + SPACE + emp.getEmployeeInfo().getBusinessName();
                    break;
                case EMPLOYMENT:
                    value += emp.getEmployeeInfo().getEmployment() == null ? "" : emp.getEmployeeInfo().getEmployment().getEmploymentName();
                    break;
                case JOBTITLE:
                    value += emp.getEmployeeInfo().getPosition() == null ? "" : emp.getEmployeeInfo().getPosition().getPositionName();
                    break;
                case CLASSIFICATION:
                    value += emp.getEmployeeInfo().getClassification() == null ? "" : emp.getEmployeeInfo().getClassification().getClassificationName();
                    break;
                case TEAM:
                    String teamName = emp.getEmployeeTeamInfo().isPresent() && emp.getEmployeeTeamInfo().get().getOptScheduleTeamName().isPresent() ? emp.getEmployeeTeamInfo().get().getOptScheduleTeamName().get().v() : "";
                    value += teamName;
                    break;
                case RANK:
                    String rankSymbol = emp.getEmployeeRankInfo().isPresent() && emp.getEmployeeRankInfo().get().getRankSymbol().isPresent() ? emp.getEmployeeRankInfo().get().getRankSymbol().get().v() : "";
                    value += rankSymbol;
                    break;
                case NURSE_CLASSIFICATION:
                    String licenseName = emp.getEmployeeLicenseClassification().isPresent() && emp.getEmployeeLicenseClassification().get().getOptLicenseClassification().isPresent() ? emp.getEmployeeLicenseClassification().get().getOptLicenseClassification().get().name : "";
                    value += licenseName;
                    break;
                case QUALIFICATION:
                default:
                    break;
            }
            cells.get(row, column).setValue(value);
        }
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

    private void setAttendanceValue(Cells cells, Optional<EmployeeOneDayAttendanceInfo> attendanceInfo, ScheduleTableAttendanceItem attendanceItem, int row, int column, boolean isFirstRow) {
        String value = "";
        if (attendanceItem != null && attendanceInfo.isPresent()) {
            switch (attendanceItem) {
                case SHIFT:
                    value = attendanceInfo.get().getShiftMasterName().orElse(attendanceInfo.get().getAttendanceData().get(attendanceItem).getValue() + getText("KSU001_4136"));
                    break;
                case WORK_TYPE:
                    value = attendanceInfo.get().getWorkTypeName().orElse(attendanceInfo.get().getAttendanceData().get(attendanceItem).getValue() + getText("KSU001_4135"));
                    break;
                case WORK_TIME:
                    value = attendanceInfo.get().getWorkTimeName().orElse(attendanceInfo.get().getAttendanceData().get(attendanceItem).getValue() + getText("KSU001_4135"));
                    break;
                default:
                    value = attendanceInfo.get().getAttendanceData().get(attendanceItem).getValue();
                    break;
            }
        }
        cells.get(row, column).setValue(value);
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
        style.setHorizontalAlignment(TextAlignmentType.CENTER);
        if (isFirstRow) style.setForegroundColor(Color.fromArgb(221, 235, 247));
        cells.get(row, column).setStyle(style);
    }
}
