package nts.uk.file.at.infra.schedule.personalschedulebyworkplace;

import com.aspose.cells.*;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.primitive.PrimitiveValueBase;
import nts.arc.system.ServerSystemProperties;
import nts.arc.time.calendar.DayOfWeek;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personcounter.EstimatedSalary;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personcounter.WorkClassificationAsAggregationTarget;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.PersonalCounterCategory;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.WorkplaceCounterCategory;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.timescounting.TimesNumberCounterSelection;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.timescounting.TimesNumberCounterSelectionRepo;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.timescounting.TimesNumberCounterType;
import nts.uk.ctx.at.aggregation.dom.scheduletable.*;
import nts.uk.ctx.at.schedule.dom.shift.management.DateInformation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.AttendanceTimesForAggregation;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesRepository;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceDayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.file.at.app.export.schedule.personalschedulebyworkplace.PersonalScheduleByWkpDataSource;
import nts.uk.file.at.app.export.schedule.personalschedulebyworkplace.PersonalScheduleByWorkplaceExportGenerator;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class AsposePersonalScheduleByWorkplaceExportGenerator extends AsposeCellsReportGenerator implements PersonalScheduleByWorkplaceExportGenerator {
    private final String FONT_NAME = "ＭＳ ゴシック";
    private final String PDF_EXT = ".pdf";
    private final String EXCEL_EXT = ".xlsx";
    private final String SPACE = "　";
    private final String COLON = "：";

    private final int BG_COLOR_SPECIFIC_DAY = Integer.parseInt("ffc0cb", 16);
    private final int BG_COLOR_SUNDAY = Integer.parseInt("fabf8f", 16);
    private final int BG_COLOR_SATURDAY = Integer.parseInt("8bd8ff", 16);
    private final int BG_COLOR_WEEKDAY = Integer.parseInt("D9D9D9", 16);
    private final int TEXT_COLOR_SUNDAY = Integer.parseInt("ff0000", 16);
    private final int TEXT_COLOR_SATURDAY = Integer.parseInt("0000ff", 16);
    private final int TEXT_COLOR_WEEKDAY = Integer.parseInt("404040", 16);

    private final int COLUMN_WIDTH = 4;
    private final int START_HEADER_ROW = 4;
    private final int START_DATA_ROW = 8;
    private final int START_DATE_COL = 3;
    private final int PERSONAL_INFO_COLUMN = 0;
    private final int ADDITIONAL_PERSONAL_INFO_COLUMN = 2;

    @Inject
    private TotalTimesRepository totalTimesRepo;
    @Inject
    private TimesNumberCounterSelectionRepo timesNumberCounterSelectionRepo;

    @Inject
    private WorkTimeSettingRepository workTimeSettingRepo;
    @Inject
    private ShiftMasterRepository shiftMasterRepo;
    @Inject
    private WorkTypeRepository workTypeRepo;

    private final OneDayEmployeeAttendanceInfo.Require require = new OneDayEmployeeAttendanceInfo.Require() {
        @Override
        public Optional<ShiftMaster> getShiftMaster(String workTypeCode, Optional<String> workTimeCode) {
            return shiftMasterRepo.getByWorkTypeAndWorkTime(AppContexts.user().companyId(), workTypeCode, workTimeCode.orElse(null));
        }

        @Override
        public Optional<WorkType> getWorkType(String workTypeCode) {
            return workTypeRepo.findByPK(AppContexts.user().companyId(), workTypeCode);
        }

        @Override
        public Optional<WorkTimeSetting> getWorkTimeSetting(String workTimeCode) {
            return workTimeSettingRepo.findByCode(AppContexts.user().companyId(), workTimeCode);
        }
    };

    @Override
    public void generate(FileGeneratorContext context, PersonalScheduleByWkpDataSource dataSource, boolean excel, boolean preview) {
        try {
            AsposeCellsReportContext reportContext = this.createEmptyContext("PersonalScheduleByWorkplace");
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            Worksheet worksheet = worksheets.get(0);
            worksheet.setName(dataSource.getOutputSetting().getName().v());
            this.printHeader(worksheet, dataSource);
            this.printContent(worksheet, dataSource);
            worksheet.setViewType(ViewType.PAGE_LAYOUT_VIEW);
            reportContext.processDesigner();
            if (preview) {
                // save as html file
                HtmlSaveOptions options = new HtmlSaveOptions(SaveFormat.AUTO);
                options.setPresentationPreference(true);
                String fileName = this.getReportName(dataSource.getOutputSetting().getName().v() + ".html");
                workbook.save(this.createNewFile(context, fileName), options);
                workbook.save(ServerSystemProperties.fileStoragePath() + "\\" + fileName, options);
            } else {
                this.settingPage(worksheet, dataSource);
                if (excel) {
                    // save as excel file
                    reportContext.saveAsExcel(this.createNewFile(context, this.getReportName(dataSource.getOutputSetting().getName().v() + EXCEL_EXT)));
                } else {
                    // save as PDF file
                    reportContext.saveAsPdf(this.createNewFile(context, this.getReportName(dataSource.getOutputSetting().getName().v() + PDF_EXT)));
                }
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
        pageSetup.setPrintTitleRows("$5:$8");
    }

    private String getText(String resourceId) {
        return TextResource.localize(resourceId);
    }

    private void printHeader(Worksheet worksheet, PersonalScheduleByWkpDataSource dataSource) {
        Cells cells = worksheet.getCells();
        // B part
        cells.get(0, START_DATE_COL).setValue(dataSource.getComment());
        Style styleC1 = cells.get(0, START_DATE_COL).getStyle();
        styleC1.getFont().setSize(9);
        styleC1.getFont().setName(FONT_NAME);
        styleC1.setVerticalAlignment(TextAlignmentType.TOP);
        styleC1.setHorizontalAlignment(TextAlignmentType.JUSTIFY);
        styleC1.setTextWrapped(true);
        cells.get(0, START_DATE_COL).setStyle(styleC1);
        cells.merge(0, START_DATE_COL, 2, dataSource.getDateInfos().size());

        cells.get("A3").setValue(
                getText("KSU001_4129")
                        + dataSource.getPeriod().start().toString() + "(" + this.getDayOfWeek(dataSource.getPeriod().start().dayOfWeekEnum()) + ")"
                        + getText("KSU001_4130")
                        + dataSource.getPeriod().end().toString() + "(" + this.getDayOfWeek(dataSource.getPeriod().end().dayOfWeekEnum()) + ")"
        );
        Style styleA3 = cells.get("A3").getStyle();
        styleA3.getFont().setSize(9);
        styleA3.getFont().setName(FONT_NAME);
        cells.get("A3").setStyle(styleA3);
        cells.get("A4").setValue((dataSource.getOrgUnit() == 0 ? getText("KSU001_4132") : getText("KSU001_4133")) + dataSource.getOrganizationDisplayInfo().getCode() + SPACE + dataSource.getOrganizationDisplayInfo().getDisplayName());
        cells.get("A4").setStyle(styleA3);

        // C1 part
        cells.get(START_HEADER_ROW, PERSONAL_INFO_COLUMN).setValue(getText("KSU001_4131"));
        this.setHeaderStyle(cells.get(START_HEADER_ROW, PERSONAL_INFO_COLUMN), null, false, true, false, false, false);
        this.setHeaderStyle(cells.get(START_HEADER_ROW + 1, PERSONAL_INFO_COLUMN), null, false, false, false, false, false);
        this.setHeaderStyle(cells.get(START_HEADER_ROW + 2, PERSONAL_INFO_COLUMN), null, false, false, false, false, false);
        this.setHeaderStyle(cells.get(START_HEADER_ROW + 3, PERSONAL_INFO_COLUMN), null, false, false, true, false, false);
        this.setHeaderStyle(cells.get(START_HEADER_ROW, PERSONAL_INFO_COLUMN + 1), null, false, true, false, false, false);
        this.setHeaderStyle(cells.get(START_HEADER_ROW + 1, PERSONAL_INFO_COLUMN + 1), null, false, false, false, false, false);
        this.setHeaderStyle(cells.get(START_HEADER_ROW + 2, PERSONAL_INFO_COLUMN + 1), null, false, false, false, false, false);
        this.setHeaderStyle(cells.get(START_HEADER_ROW + 3, PERSONAL_INFO_COLUMN + 1), null, false, false, true, false, false);
        cells.merge(START_HEADER_ROW, PERSONAL_INFO_COLUMN, 4, 2, true);
        cells.setColumnWidth(PERSONAL_INFO_COLUMN, 10);
        cells.setColumnWidth(PERSONAL_INFO_COLUMN + 1, 5);

        // C2 part
        long additionCount = dataSource.getOutputSetting().getOutputItem().getDetails().stream().filter(i -> i.getAdditionalInfo().isPresent()).count();
        if (additionCount == 0) {
            cells.hideColumn(ADDITIONAL_PERSONAL_INFO_COLUMN);
        } else if (additionCount == 1) {
            cells.get(START_HEADER_ROW, ADDITIONAL_PERSONAL_INFO_COLUMN).setValue(getText(dataSource.getOutputSetting().getOutputItem().getDetails().stream().filter(i -> i.getAdditionalInfo().isPresent()).findFirst().get().getAdditionalInfo().get().nameId));
            cells.setColumnWidth(ADDITIONAL_PERSONAL_INFO_COLUMN, 9);
        } else {
            cells.setColumnWidth(ADDITIONAL_PERSONAL_INFO_COLUMN, 9);
        }
        this.setHeaderStyle(cells.get(START_HEADER_ROW, ADDITIONAL_PERSONAL_INFO_COLUMN), null, false, true, false, false, false);
        this.setHeaderStyle(cells.get(START_HEADER_ROW + 1, ADDITIONAL_PERSONAL_INFO_COLUMN), null, false, false, false, false, false);
        this.setHeaderStyle(cells.get(START_HEADER_ROW + 2, ADDITIONAL_PERSONAL_INFO_COLUMN), null, false, false, false, false, false);
        this.setHeaderStyle(cells.get(START_HEADER_ROW + 3, ADDITIONAL_PERSONAL_INFO_COLUMN), null, false, false, true, false, false);
        cells.merge(START_HEADER_ROW, ADDITIONAL_PERSONAL_INFO_COLUMN, 4, 1, true);

        // C3 part
        int startCol = START_DATE_COL;
        for (int i = 0; i < dataSource.getDateInfos().size(); i++) {
            DateInformation dateInfo = (DateInformation) dataSource.getDateInfos().get(i);
            cells.get(START_HEADER_ROW, startCol + i).setValue(dateInfo.getYmd().toString(i == 0 || dateInfo.getYmd().day() == 1 ? "M/d" : "d") + "\n" + this.getDayOfWeek(dateInfo.getDayOfWeek()));
            if (dateInfo.getOptCompanyEventName().isPresent()) {
                cells.get(START_HEADER_ROW + 2, startCol + i).setValue(dateInfo.getOptCompanyEventName().get().v());
            }
            if (dateInfo.getOptWorkplaceEventName().isPresent()) {
                cells.get(START_HEADER_ROW + 3, startCol + i).setValue(dateInfo.getOptWorkplaceEventName().get().v());
            }
            this.setHeaderStyle(cells.get(START_HEADER_ROW, startCol + i), dateInfo, true, true, false, i == dataSource.getDateInfos().size() - 1, false);
            this.setHeaderStyle(cells.get(START_HEADER_ROW + 1, startCol + i), dateInfo, true, false, false, i == dataSource.getDateInfos().size() - 1, false);
            this.setHeaderStyle(cells.get(START_HEADER_ROW + 2, startCol + i), dateInfo, false, false, false, i == dataSource.getDateInfos().size() - 1, false);
            this.setHeaderStyle(cells.get(START_HEADER_ROW + 3, startCol + i), dateInfo, false, false, true, i == dataSource.getDateInfos().size() - 1, false);
            cells.merge(START_HEADER_ROW, startCol + i, 2, 1, true);
            cells.setColumnWidth(startCol + i, COLUMN_WIDTH);
        }

        // C4 part
        startCol += dataSource.getDateInfos().size();
        for (int i = 0; i < dataSource.getOutputSetting().getPersonalCounterCategories().size(); i++) {
            PersonalCounterCategory personalCounterCategory = dataSource.getOutputSetting().getPersonalCounterCategories().get(i);
            switch (personalCounterCategory) {
                case WORKING_HOURS:
                    cells.get(START_HEADER_ROW, startCol).setValue(getText(personalCounterCategory.nameId));
                    cells.get(START_HEADER_ROW + 1, startCol).setValue(getText("KSU001_20"));
                    cells.get(START_HEADER_ROW + 1, startCol + 1).setValue(getText("KSU001_50"));
                    cells.get(START_HEADER_ROW + 1, startCol + 2).setValue(getText("KSU001_51"));
                    for (int j = 0; j < 4; j++) {
                        this.setHeaderStyle(cells.get(START_HEADER_ROW + j, startCol), null, j != 0, j == 0, j == 3, false, false);
                        this.setHeaderStyle(cells.get(START_HEADER_ROW + j, startCol + 1), null, j != 0, j == 0, j == 3, false, false);
                        this.setHeaderStyle(cells.get(START_HEADER_ROW + j, startCol + 2), null, j != 0, j == 0, j == 3, false, false);
                    }
                    cells.merge(START_HEADER_ROW, startCol, 1, 3);
                    cells.merge(START_HEADER_ROW + 1, startCol, 3, 1);
                    cells.merge(START_HEADER_ROW + 1, startCol + 1, 3, 1);
                    cells.merge(START_HEADER_ROW + 1, startCol + 2, 3, 1);
                    cells.setColumnWidth(startCol, COLUMN_WIDTH);
                    cells.setColumnWidth(startCol + 1, COLUMN_WIDTH);
                    cells.setColumnWidth(startCol + 2, COLUMN_WIDTH);
                    startCol += 3;
                    break;
                case MONTHLY_EXPECTED_SALARY:
                case CUMULATIVE_ESTIMATED_SALARY:
                    cells.get(START_HEADER_ROW, startCol).setValue(getText(personalCounterCategory.nameId));
                    cells.get(START_HEADER_ROW + 1, startCol).setValue(getText("KSU001_18"));
                    cells.get(START_HEADER_ROW + 1, startCol + 1).setValue(getText("KSU001_19"));
                    for (int j = 0; j < 4; j++) {
                        this.setHeaderStyle(cells.get(START_HEADER_ROW + j, startCol), null, j != 0, j == 0, j == 3, false, false);
                        this.setHeaderStyle(cells.get(START_HEADER_ROW + j, startCol + 1), null, j != 0, j == 0, j == 3, false, false);
                    }
                    cells.merge(START_HEADER_ROW, startCol, 1, 2);
                    cells.merge(START_HEADER_ROW + 1, startCol, 3, 1);
                    cells.merge(START_HEADER_ROW + 1, startCol + 1, 3, 1);
                    cells.setColumnWidth(startCol, COLUMN_WIDTH);
                    cells.setColumnWidth(startCol + 1, COLUMN_WIDTH);
                    startCol += 2;
                    break;
                case TIMES_COUNTING_1:
                case TIMES_COUNTING_2:
                case TIMES_COUNTING_3:
                    TimesNumberCounterType counterType;
                    if (personalCounterCategory == PersonalCounterCategory.TIMES_COUNTING_1)
                        counterType = TimesNumberCounterType.PERSON_1;
                    else if (personalCounterCategory == PersonalCounterCategory.TIMES_COUNTING_2)
                        counterType = TimesNumberCounterType.PERSON_2;
                    else counterType = TimesNumberCounterType.PERSON_3;
                    Optional<TimesNumberCounterSelection> timesNumberCounterSelection = timesNumberCounterSelectionRepo.get(AppContexts.user().companyId(), counterType);
                    if (timesNumberCounterSelection.isPresent()) {
                        cells.get(START_HEADER_ROW, startCol).setValue(getText(personalCounterCategory.nameId));
                        List<TotalTimes> totalTimes = totalTimesRepo.getTotalTimesDetailByListNo(AppContexts.user().companyId(), timesNumberCounterSelection.get().getSelectedNoList())
                                .stream().filter(t -> t.getUseAtr() == UseAtr.Use).collect(Collectors.toList());
                        for (int j = 0; j < totalTimes.size(); j++) {
                            TotalTimes totalTime = totalTimes.get(j);
                            cells.get(START_HEADER_ROW + 1, startCol + j).setValue(totalTime.getTotalTimesABName().v());
                        }
                        for (int j = 0; j < Math.max(totalTimes.size(), 1); j++) {
                            for (int k = 0; k < 4; k++) {
                                this.setHeaderStyle(cells.get(START_HEADER_ROW + k, startCol + j), null, k != 0, k == 0, k == 3, false, false);
                            }
                        }
                        for (int j = 0; j < Math.max(totalTimes.size(), 1); j++) {
                            cells.merge(START_HEADER_ROW + 1, startCol + j, 3, 1);
                            cells.setColumnWidth(startCol + j, COLUMN_WIDTH);
                        }
                        cells.merge(START_HEADER_ROW, startCol, 1, Math.max(totalTimes.size(), 1));
                        startCol += Math.max(totalTimes.size(), 1);
                    }
                    break;
                case ATTENDANCE_HOLIDAY_DAYS:
                    cells.get(START_HEADER_ROW, startCol).setValue(getText(personalCounterCategory.nameId));
                    cells.get(START_HEADER_ROW + 1, startCol).setValue(getText("KSU001_62"));
                    cells.get(START_HEADER_ROW + 1, startCol + 1).setValue(getText("KSU001_63"));
                    for (int j = 0; j < 4; j++) {
                        this.setHeaderStyle(cells.get(START_HEADER_ROW + j, startCol), null, j != 0, j == 0, j == 3, false, false);
                        this.setHeaderStyle(cells.get(START_HEADER_ROW + j, startCol + 1), null, j != 0, j == 0, j == 3, false, false);
                    }
                    cells.merge(START_HEADER_ROW, startCol, 1, 2);
                    cells.merge(START_HEADER_ROW + 1, startCol, 3, 1);
                    cells.merge(START_HEADER_ROW + 1, startCol + 1, 3, 1);
                    cells.setColumnWidth(startCol, COLUMN_WIDTH);
                    cells.setColumnWidth(startCol + 1, COLUMN_WIDTH);
                    startCol += 2;
                    break;
                default:
                    break;
            }
        }
    }

    private void setHeaderStyle(Cell cell, DateInformation dateInfo, boolean wrapText, boolean firstRow, boolean lastRow, boolean lastDateColumn, boolean wkp) {
        Style style = cell.getStyle();
        style.setHorizontalAlignment(TextAlignmentType.CENTER);
        style.setVerticalAlignment(TextAlignmentType.CENTER);
        if (wrapText) {
            style.setTextWrapped(true);
        } else {
            style.setShrinkToFit(true);
        }
        style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(firstRow ? (wkp ? CellBorderType.THIN : CellBorderType.MEDIUM) : CellBorderType.DOTTED);
        style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(lastRow ? (wkp ? CellBorderType.THIN : CellBorderType.MEDIUM) : CellBorderType.DOTTED);
        style.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.DOTTED);
        style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(lastDateColumn ? CellBorderType.DOUBLE : CellBorderType.DOTTED);
        style.setPattern(BackgroundType.SOLID);
        if (dateInfo != null) {
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
        List<ScheduleTablePersonalInfo> personalInfoScheduleTableList = dataSource.getPersonalInfoScheduleTableList();
        personalInfoScheduleTableList.sort(Comparator.comparing(i -> i.getPersonalInfoMap().get(ScheduleTablePersonalInfoItem.EMPLOYEE_NAME).getCode()));

        // fake emp count
        ScheduleTablePersonalInfo pInfo = personalInfoScheduleTableList.get(0);
        for (int g = 0; g < 15; g++) {
            personalInfoScheduleTableList.add(pInfo);
        }

        int rows = dataSource.getOutputSetting().getOutputItem().getDetails().size();
        int startRow = START_DATA_ROW;
        long additionCount = dataSource.getOutputSetting().getOutputItem().getDetails().stream().filter(i -> i.getAdditionalInfo().isPresent()).count();
        // loop employee
        for (int i1 = 0; i1 < personalInfoScheduleTableList.size(); i1++) {
            ScheduleTablePersonalInfo emp = personalInfoScheduleTableList.get(i1);
            List<OneRowOutputItem> personalItems = dataSource.getOutputSetting().getOutputItem().getDetails().stream().filter(i -> i.getPersonalInfo().isPresent()).collect(Collectors.toList());
            List<OneRowOutputItem> additionalItems = dataSource.getOutputSetting().getOutputItem().getDetails().stream().filter(i -> i.getAdditionalInfo().isPresent()).collect(Collectors.toList());
            List<OneRowOutputItem> attendanceItems = dataSource.getOutputSetting().getOutputItem().getDetails().stream().filter(i -> i.getAttendanceItem().isPresent()).collect(Collectors.toList());
            List<OneDayEmployeeAttendanceInfo> attendanceInfos = (List<OneDayEmployeeAttendanceInfo>) dataSource.getListEmpOneDayAttendanceInfo()
                    .stream().filter(i -> ((OneDayEmployeeAttendanceInfo) i).getEmployeeId().equals(emp.getEmployeeId())).collect(Collectors.toList());
            int startCol = START_DATE_COL;
            for (int i = 0; i < rows; i++) {
                // E1 part
                if (i < personalItems.size()) {
                    OneRowOutputItem personalItem = personalItems.get(i);
                    this.setPesonalInfoValue(cells, personalItem.getPersonalInfo(), emp, startRow + i, PERSONAL_INFO_COLUMN, false);
                }
                this.setPersonalStyle(cells, startRow + i, PERSONAL_INFO_COLUMN, i == 0, i == rows - 1, i1 == personalInfoScheduleTableList.size() - 1);
                this.setPersonalStyle(cells, startRow + i, PERSONAL_INFO_COLUMN + 1, i == 0, i == rows - 1, i1 == personalInfoScheduleTableList.size() - 1);
                cells.merge(startRow + i, PERSONAL_INFO_COLUMN, 1, 2);

                // E2 part
                if (i < additionalItems.size()) {
                    OneRowOutputItem additionalItem = additionalItems.get(i);
                    this.setPesonalInfoValue(cells, additionalItem.getAdditionalInfo(), emp, startRow + i, ADDITIONAL_PERSONAL_INFO_COLUMN, additionCount == 1);
                }
                this.setPersonalStyle(cells, startRow + i, ADDITIONAL_PERSONAL_INFO_COLUMN, i == 0, i == rows - 1, i1 == personalInfoScheduleTableList.size() - 1);

                // E3 part
                for (int j = 0; j < dataSource.getDateInfos().size(); j++) {
                    DateInformation dateInfo = (DateInformation) dataSource.getDateInfos().get(j);
                    Optional<OneDayEmployeeAttendanceInfo> attendanceInfo = attendanceInfos.stream().filter(info -> info.getDate().equals(dateInfo.getYmd())).findFirst();
                    this.setAttendanceValue(
                            cells,
                            attendanceInfo,
                            attendanceItems.size() > i ? attendanceItems.get(i).getAttendanceItem().get() : null,
                            startRow + i,
                            startCol + j,
                            i == 0,
                            i == rows - 1,
                            i1 == personalInfoScheduleTableList.size() - 1,
                            j == dataSource.getDateInfos().size() - 1
                    );
                }
            }
            startCol += dataSource.getDateInfos().size();
            // E4 part
            for (int ii = 0; ii < dataSource.getOutputSetting().getPersonalCounterCategories().size(); ii++) {
                PersonalCounterCategory personalCounterCategory = dataSource.getOutputSetting().getPersonalCounterCategories().get(ii);
                switch (personalCounterCategory) {
                    case WORKING_HOURS:
                        Map<String, Map<AttendanceTimesForAggregation, BigDecimal>> workingHoursMap = (Map<String, Map<AttendanceTimesForAggregation, BigDecimal>>) dataSource.getPersonalTotalResult().get(personalCounterCategory);
                        Map<AttendanceTimesForAggregation, BigDecimal> workingHours = workingHoursMap.get(emp.getEmployeeId());
                        AttendanceTimesForAggregation[] values = AttendanceTimesForAggregation.values();
                        for (int jj = 0; jj < values.length; jj++) {
                            AttendanceTimesForAggregation attendanceTimesForAggregation = values[jj];
                            if (attendanceTimesForAggregation != AttendanceTimesForAggregation.NIGHTSHIFT) {
                                String value = workingHours != null && workingHours.get(attendanceTimesForAggregation) != null ? new TimeWithDayAttr(workingHours.get(attendanceTimesForAggregation).intValue()).getRawTimeWithFormat() : "";
                                this.setPersonalTotalValue(cells.get(startRow, startCol + jj), value, i1 == personalInfoScheduleTableList.size() - 1, Optional.empty());
                                for (int kk = 1; kk < rows; kk++) {
                                    this.setPersonalTotalValue(cells.get(startRow + kk, startCol + jj), null, i1 == personalInfoScheduleTableList.size() - 1, Optional.empty());
                                }
                                cells.merge(startRow, startCol + jj, rows, 1);
                            }
                        }
                        startCol += 3;
                        break;
                    case MONTHLY_EXPECTED_SALARY:
                    case CUMULATIVE_ESTIMATED_SALARY:
                        Map<String, EstimatedSalary> estimatedSalaryMap = (Map<String, EstimatedSalary>) dataSource.getPersonalTotalResult().get(personalCounterCategory);
                        EstimatedSalary estimatedSalary = estimatedSalaryMap.get(emp.getEmployeeId());
                        this.setPersonalTotalValue(
                                cells.get(startRow, startCol),
                                estimatedSalary == null ? "" : NumberFormat.getCurrencyInstance().format(estimatedSalary.getCriterion().v()),
                                i1 == personalInfoScheduleTableList.size() - 1,
                                estimatedSalary.getBackground().map(i -> i.v().substring(1))
                        );
                        this.setPersonalTotalValue(
                                cells.get(startRow, startCol + 1),
                                estimatedSalary == null ? "" : NumberFormat.getCurrencyInstance().format(estimatedSalary.getSalary()),
                                i1 == personalInfoScheduleTableList.size() - 1,
                                estimatedSalary.getBackground().map(PrimitiveValueBase::toString)
                        );
                        for (int kk = 1; kk < rows; kk++) {
                            this.setPersonalTotalValue(cells.get(startRow + kk, startCol), null, i1 == personalInfoScheduleTableList.size() - 1, Optional.empty());
                            this.setPersonalTotalValue(cells.get(startRow + kk, startCol + 1), null, i1 == personalInfoScheduleTableList.size() - 1, Optional.empty());
                        }
                        cells.merge(startRow, startCol, rows, 1);
                        cells.merge(startRow, startCol + 1, rows, 1);
                        startCol += 2;
                        break;
                    case TIMES_COUNTING_1:
                    case TIMES_COUNTING_2:
                    case TIMES_COUNTING_3:
                        Map<String, Map<Integer, BigDecimal>> timesCountMap = (Map<String, Map<Integer, BigDecimal>>) dataSource.getPersonalTotalResult().get(personalCounterCategory);
                        Map<Integer, BigDecimal> timesCount = timesCountMap.get(emp.getEmployeeId());
                        TimesNumberCounterType counterType;
                        if (personalCounterCategory == PersonalCounterCategory.TIMES_COUNTING_1)
                            counterType = TimesNumberCounterType.PERSON_1;
                        else if (personalCounterCategory == PersonalCounterCategory.TIMES_COUNTING_2)
                            counterType = TimesNumberCounterType.PERSON_2;
                        else counterType = TimesNumberCounterType.PERSON_3;
                        Optional<TimesNumberCounterSelection> timesNumberCounterSelection = timesNumberCounterSelectionRepo.get(AppContexts.user().companyId(), counterType);
                        if (timesNumberCounterSelection.isPresent()) {
                            List<TotalTimes> totalTimes = totalTimesRepo.getTotalTimesDetailByListNo(AppContexts.user().companyId(), timesNumberCounterSelection.get().getSelectedNoList())
                                    .stream().filter(t -> t.getUseAtr() == UseAtr.Use).collect(Collectors.toList());
                            for (int jj = 0; jj < totalTimes.size(); jj++) {
                                TotalTimes totalTime = totalTimes.get(jj);
                                this.setPersonalTotalValue(
                                        cells.get(startRow, startCol + jj),
                                        timesCount == null || timesCount.get(totalTime.getTotalCountNo()) == null ? "" : timesCount.get(totalTime.getTotalCountNo()).toString(),
                                        i1 == personalInfoScheduleTableList.size() - 1,
                                        Optional.empty()
                                );
                                for (int kk = 1; kk < rows; kk++) {
                                    this.setPersonalTotalValue(cells.get(startRow + kk, startCol + jj), null, i1 == personalInfoScheduleTableList.size() - 1, Optional.empty());
                                }
                                cells.merge(startRow, startCol + jj, rows, 1);
                            }
                        }
                        break;
                    case ATTENDANCE_HOLIDAY_DAYS:
                        Map<String, Map<WorkClassificationAsAggregationTarget, BigDecimal>> holidayWorkMap = (Map<String, Map<WorkClassificationAsAggregationTarget, BigDecimal>>) dataSource.getPersonalTotalResult().get(personalCounterCategory);
                        Map<WorkClassificationAsAggregationTarget, BigDecimal> holidayWork = holidayWorkMap.get(emp.getEmployeeId());
                        this.setPersonalTotalValue(
                                cells.get(startRow, startCol),
                                holidayWork == null || holidayWork.get(WorkClassificationAsAggregationTarget.WORKING) == null ? "" : holidayWork.get(WorkClassificationAsAggregationTarget.WORKING).toString(),
                                i1 == personalInfoScheduleTableList.size() - 1,
                                Optional.empty()
                        );
                        this.setPersonalTotalValue(
                                cells.get(startRow, startCol + 1),
                                holidayWork == null || holidayWork.get(WorkClassificationAsAggregationTarget.HOLIDAY) == null ? "" : holidayWork.get(WorkClassificationAsAggregationTarget.HOLIDAY).toString(),
                                i1 == personalInfoScheduleTableList.size() - 1,
                                Optional.empty()
                        );
                        for (int kk = 1; kk < rows; kk++) {
                            this.setPersonalTotalValue(cells.get(startRow + kk, startCol), null, i1 == personalInfoScheduleTableList.size() - 1, Optional.empty());
                            this.setPersonalTotalValue(cells.get(startRow + kk, startCol + 1), null, i1 == personalInfoScheduleTableList.size() - 1, Optional.empty());
                        }
                        cells.merge(startRow, startCol, rows, 1);
                        cells.merge(startRow, startCol + 1, rows, 1);
                        break;
                    default:
                        break;
                }
            }
            startRow += rows;
        }

        // F1, F2, F3 parts
        this.printContentF(worksheet, dataSource, startRow);
    }

    private void setPesonalInfoValue(Cells cells, Optional<ScheduleTablePersonalInfoItem> item, ScheduleTablePersonalInfo emp, int row, int column, boolean isValueOnly) {
        if (item.isPresent()) {
            String value = isValueOnly ? "" : getText(item.get().nameId) + COLON;
            switch (item.get()) {
                case EMPLOYEE_NAME:
                    value = emp.getPersonalInfoMap().get(item.get()).getCode() + SPACE + emp.getPersonalInfoMap().get(item.get()).getName();
                    break;
                case EMPLOYMENT:
                case JOBTITLE:
                case CLASSIFICATION:
                case TEAM:
                case RANK:
                case NURSE_CLASSIFICATION:
                    value += emp.getPersonalInfoMap().get(item.get()) != null ? emp.getPersonalInfoMap().get(item.get()).getName() : "";
                    break;
//                case QUALIFICATION:
                default:
                    break;
            }
            cells.get(row, column).setValue(value);
        }
    }

    private void setPersonalStyle(Cells cells, int row, int column, boolean firstRow, boolean lastRow, boolean lastEmployee) {
        Style style = cells.get(row, column).getStyle();
        style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(firstRow ? CellBorderType.THIN : CellBorderType.DOTTED);
        style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(lastRow ? (lastEmployee ? CellBorderType.DOUBLE : CellBorderType.THIN) : CellBorderType.DOTTED);
        style.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.DOTTED);
        style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.DOTTED);
        style.getFont().setName(FONT_NAME);
        style.getFont().setSize(9);
        style.setShrinkToFit(true);
        style.setPattern(BackgroundType.SOLID);
        style.setVerticalAlignment(TextAlignmentType.CENTER);
//        if (firstRow) style.setForegroundColor(Color.fromArgb(221, 235, 247));
        cells.get(row, column).setStyle(style);
    }

    private void setAttendanceValue(Cells cells, Optional<OneDayEmployeeAttendanceInfo> attendanceInfo, ScheduleTableAttendanceItem attendanceItem, int row, int column, boolean isFirstRow, boolean isLastRow, boolean isLastEmployee, boolean isLastDateColumn) {
        String value = "";
        if (attendanceItem != null && attendanceInfo.isPresent()) {
            switch (attendanceItem) {
                case SHIFT:
                    Optional<ShiftMaster> shiftMaster = attendanceInfo.get().getShiftMaster(require);
                    if (shiftMaster.isPresent())
                        value = shiftMaster.get().getDisplayInfor().getName().v();
//                    else
//                        value = getText("KSU001_4136");
                    break;
                case WORK_TYPE:
                    Optional<WorkType> workType = attendanceInfo.get().getWorkType(require);
                    if (workType.isPresent())
                        value = workType.get().getAbbreviationName().v();
                    else if (attendanceInfo.get().getAttendanceItemInfoMap().get(ScheduleTableAttendanceItem.WORK_TYPE) != null)
                        value = attendanceInfo.get().getAttendanceItemInfoMap().get(ScheduleTableAttendanceItem.WORK_TYPE) + getText("KSU001_4135");
                    break;
                case WORK_TIME:
                    Optional<WorkTimeSetting> workTime = attendanceInfo.get().getWorkTime(require);
                    if (workTime.isPresent())
                        value = workTime.get().getWorkTimeDisplayName().getWorkTimeAbName().v();
                    else if (attendanceInfo.get().getAttendanceItemInfoMap().get(ScheduleTableAttendanceItem.WORK_TIME) != null)
                        value = attendanceInfo.get().getAttendanceItemInfoMap().get(ScheduleTableAttendanceItem.WORK_TIME) + getText("KSU001_4135");
                    break;
                case START_TIME:
                case END_TIME:
                case START_TIME_2:
                case END_TIME_2:
                    TimeWithDayAttr time = (TimeWithDayAttr) attendanceInfo.get().getAttendanceItemInfoMap().get(attendanceItem);
                    if (time != null) value = time.getRawTimeWithFormat();
                    break;
                case TOTAL_WORKING_HOURS:
                case WORKING_HOURS:
                case ACTUAL_WORKING_HOURS:
                case LABOR_COST_TIME_1:
                case LABOR_COST_TIME_2:
                case LABOR_COST_TIME_3:
                case LABOR_COST_TIME_4:
                case LABOR_COST_TIME_5:
                case LABOR_COST_TIME_6:
                case LABOR_COST_TIME_7:
                case LABOR_COST_TIME_8:
                case LABOR_COST_TIME_9:
                case LABOR_COST_TIME_10:
                    AttendanceTime attdTime = (AttendanceTime) attendanceInfo.get().getAttendanceItemInfoMap().get(attendanceItem);
                    if (attdTime != null) value = new TimeWithDayAttr(attdTime.v()).getRawTimeWithFormat();
                default:
                    if (attendanceInfo.get().getAttendanceItemInfoMap().get(attendanceItem) != null)
                        value = attendanceInfo.get().getAttendanceItemInfoMap().get(attendanceItem).toString();
                    break;
            }
        }
        cells.get(row, column).setValue(value);
        Style style = cells.get(row, column).getStyle();
        style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(isFirstRow ? CellBorderType.THIN : CellBorderType.DOTTED);
        style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(isLastRow ? (isLastEmployee ? CellBorderType.DOUBLE : CellBorderType.THIN) : CellBorderType.DOTTED);
        style.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.DOTTED);
        style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(isLastDateColumn ? CellBorderType.DOUBLE : CellBorderType.DOTTED);
        style.getFont().setName(FONT_NAME);
        style.getFont().setSize(9);
        if (attendanceInfo.isPresent()) {
            Optional<WorkType> workType = attendanceInfo.get().getWorkType(require);
            if (workType.isPresent()) {
                if (workType.get().chechAttendanceDay() == AttendanceDayAttr.FULL_TIME) {
                    style.getFont().setColor(Color.getBlue());
                } else if (workType.get().chechAttendanceDay() == AttendanceDayAttr.HOLIDAY) {
                    style.getFont().setColor(Color.getRed());
                } else if (workType.get().chechAttendanceDay() == AttendanceDayAttr.HALF_TIME_AM || workType.get().chechAttendanceDay() == AttendanceDayAttr.HALF_TIME_PM) {
                    style.getFont().setColor(Color.fromArgb(255, 127, 39));
                }
            }
        }
        style.setShrinkToFit(true);
        style.setPattern(BackgroundType.SOLID);
        style.setVerticalAlignment(TextAlignmentType.CENTER);
        style.setHorizontalAlignment(TextAlignmentType.CENTER);
//        if (isFirstRow) {
//            style.setForegroundColor(Color.fromArgb(221, 235, 247));
//        } else
        if (attendanceItem == ScheduleTableAttendanceItem.SHIFT && attendanceInfo.isPresent()) {
            Optional<ShiftMaster> shiftMaster = attendanceInfo.get().getShiftMaster(require);
            if (shiftMaster.isPresent())
                style.setForegroundColor(Color.fromArgb(Integer.parseInt(shiftMaster.get().getDisplayInfor().getColor().v(), 16)));
        }
        cells.get(row, column).setStyle(style);
    }

    private void setPersonalTotalValue(Cell cell, String value, boolean lastEmployee, Optional<String> colorCode) {
        cell.setValue(value);
        Style style = cell.getStyle();
        style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.THIN);
        style.getBorders().getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(lastEmployee ? CellBorderType.DOUBLE : CellBorderType.THIN);
        style.getBorders().getByBorderType(BorderType.LEFT_BORDER).setLineStyle(CellBorderType.DOTTED);
        style.getBorders().getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(CellBorderType.DOTTED);
        style.getFont().setName(FONT_NAME);
        style.getFont().setSize(9);
        style.setShrinkToFit(true);
        style.setPattern(BackgroundType.SOLID);
        style.setVerticalAlignment(TextAlignmentType.CENTER);
        style.setHorizontalAlignment(TextAlignmentType.RIGHT);
//        if (isFirstRow)
//            style.setForegroundColor(Color.fromArgb(221, 235, 247));
//        else
        if (colorCode.isPresent()) {
            style.setForegroundColor(Color.fromArgb(Integer.parseInt(colorCode.get(), 16)));
        }
        cell.setStyle(style);
    }

    private void printContentF(Worksheet worksheet, PersonalScheduleByWkpDataSource dataSource, int startRow) {
        Cells cells = worksheet.getCells();
        for (WorkplaceCounterCategory category : dataSource.getOutputSetting().getWorkplaceCounterCategories()) {
            // header
            this.printHeaderF(cells, category, dataSource.getDateInfos(), startRow);
            startRow += 2;

            // content

        }
    }

    private void printHeaderF(Cells cells, WorkplaceCounterCategory category, List<DateInformation> dateInformations, int startRow) {
        // F1 part
        cells.get(startRow, PERSONAL_INFO_COLUMN).setValue(getText(category.nameId));
        this.setHeaderStyle(cells.get(startRow, PERSONAL_INFO_COLUMN), null, false, true, false, false, true);
        this.setHeaderStyle(cells.get(startRow + 1, PERSONAL_INFO_COLUMN), null, false, false, true, false, true);
        this.setHeaderStyle(cells.get(startRow, PERSONAL_INFO_COLUMN + 1), null, false, true, false, false, true);
        this.setHeaderStyle(cells.get(startRow + 1, PERSONAL_INFO_COLUMN + 1), null, false, false, true, false, true);
        cells.merge(startRow, PERSONAL_INFO_COLUMN, 2, 2, true);

        // F2 part
        cells.get(startRow, ADDITIONAL_PERSONAL_INFO_COLUMN).setValue(getText("KSU001_4134"));
        this.setHeaderStyle(cells.get(startRow, ADDITIONAL_PERSONAL_INFO_COLUMN), null, false, true, false, false, true);
        this.setHeaderStyle(cells.get(startRow + 1, ADDITIONAL_PERSONAL_INFO_COLUMN), null, false, false, true, false, true);
        cells.merge(startRow, ADDITIONAL_PERSONAL_INFO_COLUMN, 2, 1, true);

        // F3 part
        int startCol = START_DATE_COL;
        for (int i = 0; i < dateInformations.size(); i++) {
            DateInformation dateInfo = dateInformations.get(i);
            cells.get(startRow, startCol + i).setValue(dateInfo.getYmd().toString(i == 0 || dateInfo.getYmd().day() == 1 ? "M/d" : "d") + "\n" + this.getDayOfWeek(dateInfo.getDayOfWeek()));
            this.setHeaderStyle(cells.get(startRow, startCol + i), dateInfo, true, true, false, i == dateInformations.size() - 1, true);
            this.setHeaderStyle(cells.get(startRow + 1, startCol + i), dateInfo, true, false, true, i == dateInformations.size() - 1, true);
            cells.merge(startRow, startCol + i, 2, 1, true);
        }
    }
}
