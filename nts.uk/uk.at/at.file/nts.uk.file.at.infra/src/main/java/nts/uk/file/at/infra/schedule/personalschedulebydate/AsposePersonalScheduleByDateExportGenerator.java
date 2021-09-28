package nts.uk.file.at.infra.schedule.personalschedulebydate;

import com.aspose.cells.*;
import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.calendar.DayOfWeek;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.TimeSpanForCalcDto;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.primitives.SpecificName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeForm;
import nts.uk.file.at.app.export.schedule.personalschedulebydate.PersonalScheduleByDateDataSource;
import nts.uk.file.at.app.export.schedule.personalschedulebydate.PersonalScheduleByDateExportGenerator;
import nts.uk.file.at.app.export.schedule.personalschedulebydate.dto.EmployeeWorkScheduleResultDto;
import nts.uk.screen.at.app.ksu003.start.dto.ChangeableWorkTimeDto;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import javax.ejb.Stateless;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Stateless
public class AsposePersonalScheduleByDateExportGenerator extends AsposeCellsReportGenerator implements PersonalScheduleByDateExportGenerator {
    private static final String TEMPLATE_FILE = "report/KSU003_v2.xlsx";
    private static final String EXCEL_EXT = ".xlsx";
    private static final int MAX_ROW_IN_PAGE = 60;
    private static final int MAX_ROW_HEADER_IN_PAGE = 8;
    private final String SPACE = "　";
    private final String EMPTY = "";
    private static final String PRINT_AREA = "A1:BE";
    private static int MINUTES_IN_AN_HOUR = 60;
    private static int ROUNDING_INCREMENTS = 5;
    private static final int MAX_ROW_B5 = 4;

    @Override
    public void generate(FileGeneratorContext context, PersonalScheduleByDateDataSource dataSource) {
        try {
            long startTime = System.nanoTime();
            AsposeCellsReportContext reportContext = createContext(TEMPLATE_FILE);
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            String companyName = dataSource.getCompanyInfo().getCompanyName();

            Worksheet wsSource = worksheets.get(2);
            Worksheet wsDestination = worksheets.get(1);
            wsDestination.setName(companyName);

            pageSetting(wsDestination, dataSource);
            printHeader(wsDestination, dataSource);
            printContent(wsDestination, wsSource, dataSource);

            worksheets.removeAt(2);
            worksheets.setActiveSheetIndex(1);
            reportContext.processDesigner();

            // Save as excel file
            reportContext.saveAsExcel(createNewFile(context, getReportName(companyName + EXCEL_EXT)));
            System.out.println("Thoi gian export excel: " + (System.nanoTime() - startTime) / 1000000000 + " seconds");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ページヘッダ① : Area A
     */
    private void pageSetting(Worksheet worksheet, PersonalScheduleByDateDataSource dataSource) {
        PageSetup pageSetup = worksheet.getPageSetup();
        // A1_1
        pageSetup.setHeader(0, "&9&\"ＭＳ ゴシック\"" + dataSource.getCompanyInfo().getCompanyName());
        // A1_2
        pageSetup.setHeader(1, "&16&\"ＭＳ ゴシック\"" + getText("KSU003_138"));
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm", Locale.JAPAN);
        // A1_3, A1_4
        pageSetup.setHeader(2, "&9&\"ＭＳ ゴシック\"" + LocalDateTime.now().format(fullDateTimeFormatter) + "\npage &P");
    }

    /**
     * ページヘッダ② : Area B & Header C
     */
    private void printHeader(Worksheet worksheet, PersonalScheduleByDateDataSource dataSource) {
        Cells cells = worksheet.getCells();
        val dateInfo = dataSource.getDateInformation();
        val orgInfo = dataSource.getDisplayInfoOrganization();
        val isDoubleWorkDisplay = dataSource.getQuery().isDoubleWorkDisplay();

        // B1_1, B1_2
        cells.get(3, 0).setValue(getText("KSU003_139") + dateInfo.getYmd() + '(' + getDayOfWeek(dateInfo.getDayOfWeek()) + ')');
        // B2_1, B2_2, B2_3
        val workplaceLabel = getText(dataSource.getQuery().getOrgUnit() == TargetOrganizationUnit.WORKPLACE.value ? getText("KSU003_140") : getText("KSU003_141"));
        cells.get(4, 0).setValue(workplaceLabel + orgInfo.getCode() + SPACE + orgInfo.getDisplayName());
        // B3_1, B3_2
        val companyEvent = dateInfo.getOptCompanyEventName().isPresent() ? dateInfo.getOptCompanyEventName().get().v() : EMPTY;
        cells.get(3, 5).setValue(getText("KSU003_142") + companyEvent);
        // B4_1, B4_2
        val workplaceEvent = dateInfo.getOptWorkplaceEventName().isPresent() ? dateInfo.getOptWorkplaceEventName().get().v() : EMPTY;
        cells.get(4, 5).setValue(getText("KSU003_143") + workplaceEvent);

        // B5_1
        printB5(cells, dateInfo.getListSpecDayNameCompany(), dateInfo.getListSpecDayNameWorkplace());

        // Header C:
        cells.get(6, 0).setValue(getText("KSU003_159"));      // C1_1
        if (!isDoubleWorkDisplay) {      //２回勤務表示 = 非表示
            cells.get(6, 1).setValue(getText("KSU003_165"));  // C2_2_1
            cells.get(6, 2).setValue(getText("KSU003_166"));  // C2_2_2
            cells.get(6, 3).setValue(getText("KSU003_167"));  // C2_2_3
            cells.get(6, 4).setValue(getText("KSU003_168"));  // C2_2_4
            cells.get(6, 5).setValue(getText("KSU003_169"));  // C2_2_5
            cells.get(6, 6).setValue(getText("KSU003_170"));  // C2_2_6
        } else {                         //２回勤務表示 = 表示
            cells.get(6, 1).setValue(getText("KSU003_171"));  // C2_3_1
            cells.get(6, 2).setValue(getText("KSU003_172"));  // C2_3_2
            cells.get(6, 3).setValue(getText("KSU003_173"));  // C2_3_3
            cells.get(6, 4).setValue(getText("KSU003_174"));  // C2_3_4
            cells.get(7, 3).setValue(getText("KSU003_175"));  // C2_3_5
            cells.get(7, 4).setValue(getText("KSU003_176"));  // C2_3_6
            cells.get(6, 5).setValue(getText("KSU003_177"));  // C2_3_7
            cells.get(6, 6).setValue(getText("KSU003_178"));  // C2_3_8
        }

        // Set style by each column
        setStyleEmpWorkInfo(cells, 6, isDoubleWorkDisplay);

        // C3_1 or C3_3: Graph ruler header
        int graphStartTimeInitValue = dataSource.getQuery().getGraphStartTime();
        int graphStartTime = dataSource.getQuery().getGraphStartTime();
        if (isEven(graphStartTime)) {          // GraphStartTime is even
            for (int column = 7; column <= 55; column += 4) {
                if (graphStartTimeInitValue == 0 && graphStartTime == 24) {
                    graphStartTime = 0;
                }
                cells.get(6, column).setValue(graphStartTime);
                graphStartTime += 2;
            }
        } else {                                // GraphStartTime is odd
            cells.get(6, 7).setValue(graphStartTime);
            graphStartTime += 1;                // +1 => value is even
            for (int column = 9; column <= 53; column += 4) {
                cells.get(6, column).setValue(graphStartTime);
                graphStartTime += 2;
            }
        }
    }

    private void printContent(Worksheet wsDestination, Worksheet wsSource, PersonalScheduleByDateDataSource dataSource) throws Exception {
        Cells cells = wsDestination.getCells();
        Cells cellsTemplate = wsSource.getCells();
        ShapeCollection shapes = wsDestination.getShapes();
        HorizontalPageBreakCollection hPageBreaks = wsDestination.getHorizontalPageBreaks();
        cells.deleteRows(9, 60);

        // Set CopyOptions.ReferToDestinationSheet to true
        CopyOptions options = new CopyOptions();
        options.setReferToDestinationSheet(true);
        // Set PasteOptions
        PasteOptions pasteOptions = new PasteOptions();
        pasteOptions.setPasteType(PasteType.ALL);
        pasteOptions.setOnlyVisibleCells(true);

        int rowCount = 9;
        int pageIndex = 0;
        val employeeInfoList = dataSource.getEmployeeInfoList();
        val employeeWorkScheduleList = dataSource.getEmployeeWorkScheduleList();
        val isDoubleWorkDisplay = dataSource.getQuery().isDoubleWorkDisplay();
        val displayActual = dataSource.getQuery().isDisplayActual();
        val graphVacationDisplay = dataSource.getQuery().isGraphVacationDisplay();
        int graphStartTime = dataSource.getQuery().getGraphStartTime();

        for (int i = 1; i <= employeeWorkScheduleList.size(); i++) {
            EmployeeWorkScheduleResultDto item = employeeWorkScheduleList.get(i - 1);
            if (i == employeeWorkScheduleList.size())
                cells.copyRows(cellsTemplate, isDoubleWorkDisplay ? 55 : 49, rowCount, 2);
            else
                cells.copyRows(cellsTemplate, isDoubleWorkDisplay ? (i == 1 ? 13 : 11) : (isEndOfPage(rowCount, pageIndex) ? 49 : 9), rowCount, 2);
            cells.clearContents(CellArea.createCellArea(rowCount, 0, cells.getMaxRow(), cells.getMaxColumn()));

            val empInfoOpt = employeeInfoList.stream().filter(x -> x.getEmployeeId().equals(item.getEmployeeId())).findFirst();

            // C1_2 + C1_3
            cells.get(rowCount, 0).setValue(empInfoOpt.map(emp -> emp.getEmployeeCode() + SPACE + emp.getBusinessName()).orElse(EMPTY));
            if (!isDoubleWorkDisplay) {
                cells.get(rowCount, 1).setValue(getWorkName(item.getWorkTypeCode(), item.getWorkTypeName()));  // C2_2_7: WorkType
                cells.get(rowCount, 2).setValue(getWorkName(item.getWorkTimeCode(), item.getWorkTimeName()));  // C2_2_8: WorkTime
                cells.get(rowCount, 3).setValue(minuteToTime(item.getStartTime1()));          // C2_2_9
                cells.get(rowCount, 4).setValue(minuteToTime(item.getEndTime1()));            // C2_2_10
                cells.get(rowCount, 5).setValue(minuteToTime(item.getTotalWorkingHours()));   // C2_2_11
                cells.get(rowCount, 6).setValue(minuteToTime(item.getTotalBreakTime()));      // C2_2_12
            } else {
                cells.get(rowCount, 1).setValue(getWorkName(item.getWorkTypeCode(), item.getWorkTypeName()));  // C2_3_9:  WorkType
                cells.get(rowCount, 2).setValue(getWorkName(item.getWorkTimeCode(), item.getWorkTimeName()));  // C2_3_10: WorkTime
                cells.get(rowCount, 3).setValue(minuteToTime(item.getStartTime1()));              // C2_3_11
                cells.get(rowCount, 4).setValue(minuteToTime(item.getEndTime1()));                // C2_3_12
                cells.get(rowCount + 1, 3).setValue(minuteToTime(item.getStartTime2()));     // C2_3_13
                cells.get(rowCount + 1, 4).setValue(minuteToTime(item.getEndTime2()));       // C2_3_14
                cells.get(rowCount, 5).setValue(minuteToTime(item.getTotalWorkingHours()));      // C2_3_15
                cells.get(rowCount, 6).setValue(minuteToTime(item.getTotalBreakTime()));         // C2_3_16
            }

            if (graphVacationDisplay) {
                // C3_2_1
                if (item.getWorkType().equals(WorkTimeForm.FIXED.value)) {
                    val shape1a = calculateConvertToShape(graphStartTime, item.getStartTime1(), item.getEndTime1());
                    if (shape1a.getColumn() != null) {
                        drawRectangle(shapes, rowCount, shape1a.getColumn(), shape1a.getWidth(), shape1a.getLeft(), getBarColor(BarType.FIXED_WORKING_HOURS), false, null);
                    }

                    if (isDoubleWorkDisplay && item.getStartTime2() != null && item.getEndTime2() != null) {
                        val shape1b = calculateConvertToShape(graphStartTime, item.getStartTime2(), item.getEndTime2());
                        if (shape1b.getColumn() != null) {
                            drawRectangle(shapes, rowCount, shape1b.getColumn(), shape1b.getWidth(), shape1b.getLeft(), getBarColor(BarType.FIXED_WORKING_HOURS), false, null);
                        }
                    }
                }

                // C3_2_4
                if (item.getWorkType().equals(WorkTimeForm.FLOW.value)) {
                    val shape4a = calculateConvertToShape(graphStartTime, item.getStartTime1(), item.getEndTime1());
                    if (shape4a.getColumn() != null) {
                        drawRectangle(shapes, rowCount, shape4a.getColumn(), shape4a.getWidth(), shape4a.getLeft(), getBarColor(BarType.FLOWING_WORKING_HOURS), false, null);
                    }

                    if (isDoubleWorkDisplay && item.getStartTime2() != null && item.getEndTime2() != null) {
                        val shape4b = calculateConvertToShape(graphStartTime, item.getStartTime2(), item.getEndTime2());
                        if (shape4a.getColumn() != null) {
                            drawRectangle(shapes, rowCount, shape4b.getColumn(), shape4b.getWidth(), shape4b.getLeft(), getBarColor(BarType.FLOWING_WORKING_HOURS), false, null);
                        }
                    }
                }

                // C3_2_5
                if (item.getWorkType().equals(WorkTimeForm.FLEX.value)) {
                    val shape5 = calculateConvertToShape(graphStartTime, item.getStartTime1(), item.getEndTime1());
                    if (shape5.getColumn() != null) {
                        drawRectangle(shapes, rowCount, shape5.getColumn(), shape5.getWidth(), shape5.getLeft(), getBarColor(BarType.FLEX_WORKING_HOURS), false, null);
                    }

                    // C3_2_6
                    if (item.getCoreStartTime() != null && item.getCoreEndTime() != null) {
                        val shape6 = calculateConvertToShape(graphStartTime, item.getCoreStartTime(), item.getCoreEndTime());
                        if (shape6.getColumn() != null) {
                            drawRectangle(shapes, rowCount, shape6.getColumn(), shape6.getWidth(), shape6.getLeft(), getBarColor(BarType.CORE_TIME), false, null);
                        }
                    }
                }

                // C3_2_3
                if (!item.getOverTimeList().isEmpty()) {
                    for (ChangeableWorkTimeDto overTime : item.getOverTimeList()) {
                        TimeCheckedDto timeChecked = new TimeCheckedDto(overTime.getStartTime(), overTime.getEndTime());
                        if (!isDoubleWorkDisplay) {
                            timeChecked = checkTime(graphStartTime, overTime.getStartTime(), overTime.getEndTime(), new TimeRangeLimitDto(item.getStartTime1(), item.getEndTime1()));
                        }
                        val shape3 = calculateConvertToShape(graphStartTime, timeChecked.getStartTime(), timeChecked.getEndTime());
                        if (shape3.getColumn() != null) {
                            drawRectangle(shapes, rowCount, shape3.getColumn(), shape3.getWidth(), shape3.getLeft(), getBarColor(BarType.OVERTIME_HOURS), false, null);
                        }
                    }
                }

                // C3_2_2
                if (!item.getBreakTimeList().isEmpty()) {
                    val breakTimeList = item.getBreakTimeList();
                    for (BreakTimeSheet breakTime : breakTimeList) {
                        TimeCheckedDto timeChecked = new TimeCheckedDto(breakTime.getStartTime().v(), breakTime.getEndTime().v());
                        if (!isDoubleWorkDisplay) {
                            timeChecked = checkTime(graphStartTime, breakTime.getStartTime().v(), breakTime.getEndTime().v(),
                                    new TimeRangeLimitDto(item.getStartTime1(), item.getEndTime1()));
                        }
                        val shape2 = calculateConvertToShape(graphStartTime, timeChecked.getStartTime(), timeChecked.getEndTime());
                        if (shape2.getColumn() != null) {
                            drawRectangle(shapes, rowCount, shape2.getColumn(), shape2.getWidth(), shape2.getLeft(), getBarColor(BarType.BREAK_TIME), false, null);
                        }
                    }
                }

                // C3_2_7
                if (!item.getListTimeVacationAndType().isEmpty()) {
                    for (val timeVacation : item.getListTimeVacationAndType()) {
                        for (val time : timeVacation.getTimeVacation().getTimeZone()) {
                            val shape7 = calculateConvertToShape(graphStartTime, time.getStart(), time.getEnd());
                            if (shape7.getColumn() != null) {
                                drawRectangle(shapes, rowCount, shape7.getColumn(), shape7.getWidth(), shape7.getLeft(), getBarColor(BarType.TIME_VACATION), false, null);
                            }
                        }
                    }
                }

                // C3_2_8
                if (!item.getChildCareShortTimeList().isEmpty()) {
                    for (val time : item.getChildCareShortTimeList()) {
                        val shape8 = calculateConvertToShape(graphStartTime, time.getStartTime(), time.getEndTime());
                        if (shape8.getColumn() != null) {
                            drawRectangle(shapes, rowCount, shape8.getColumn(), shape8.getWidth(), shape8.getLeft(), getBarColor(BarType.CHILDCARE_SHORT_TIME), false, null);
                        }
                    }
                }
            }

            if (displayActual) {
                // C3_2_14
                if (item.getActualStartTime1() != null && item.getActualEndTime1() != null) {
                    val shape14a = calculateConvertToShape(graphStartTime, item.getActualStartTime1(), item.getActualEndTime1());
                    if (shape14a.getColumn() != null) {
                        drawRectangle(shapes, rowCount, shape14a.getColumn(), shape14a.getWidth(), shape14a.getLeft(), getBarColor(BarType.WORKING_HOURS_ACTUAL), true, null);
                    }
                }

                if (isDoubleWorkDisplay && item.getActualStartTime2() != null && item.getActualEndTime2() != null) {
                    val shape14b = calculateConvertToShape(graphStartTime, item.getActualStartTime2(), item.getActualEndTime2());
                    if (shape14b.getColumn() != null) {
                        drawRectangle(shapes, rowCount, shape14b.getColumn(), shape14b.getWidth(), shape14b.getLeft(), getBarColor(BarType.WORKING_HOURS_ACTUAL), true, null);
                    }
                }

                // C3_2_15
                if (!item.getActualBreakTimeList().isEmpty()) {
                    for (val time : item.getActualBreakTimeList()) {     // max 10
                        val shape15 = calculateConvertToShape(graphStartTime, time.getStartTime().v(), time.getEndTime().v());
                        if (shape15.getColumn() != null) {
                            drawRectangle(shapes, rowCount, shape15.getColumn(), shape15.getWidth(), shape15.getLeft(), getBarColor(BarType.BREAK_TIME_ACTUAL), true, null);
                        }
                    }
                }

                // C3_2_16
                if (!item.getOverTimeList().isEmpty() && item.getActualStartTime1() != null && item.getActualEndTime1() != null) {
                    for (val time : item.getOverTimeList()) {
                        TimeCheckedDto timeChecked = new TimeCheckedDto(time.getStartTime(), time.getEndTime());
                        if (!isDoubleWorkDisplay) {
                            timeChecked = checkTime(graphStartTime, time.getStartTime(), time.getEndTime(), new TimeRangeLimitDto(item.getActualStartTime1(), item.getActualEndTime1()));
                        }
                        val shape16 = calculateConvertToShape(graphStartTime, timeChecked.getStartTime(), timeChecked.getEndTime());
                        if (shape16.getColumn() != null) {
                            drawRectangle(shapes, rowCount, shape16.getColumn(), shape16.getWidth(), shape16.getLeft(), getBarColor(BarType.OVERTIME_HOURS_ACTUAL), true, null);
                        }
                    }
                }
            }
            rowCount += 2;

            // Paging
            if (isNextPage(rowCount, pageIndex)) {
                cells.copyRows(cellsTemplate, 57, rowCount, 1, options);  // close ruler
                rowCount += 1;     // close ruler
                hPageBreaks.add(rowCount);
                pageIndex += 1;
            }

            // Ruler close
            if (i == employeeWorkScheduleList.size()) {
                cells.copyRows(cellsTemplate, 57, rowCount, 1, options);
                rowCount += 1;
            }
        }
        PageSetup pageSetup = wsDestination.getPageSetup();
        pageSetup.setPrintArea(PRINT_AREA + rowCount);
    }

    /**
     * Calculate data to draw shape
     *
     * @param graphStartTime graphStartTime
     * @param start          start
     * @param end            end
     * @return DrawRectangleProperties
     */
    private DrawRectangleProperties calculateConvertToShape(int graphStartTime, Integer start, Integer end) {
        if (start == null && end == null) return new DrawRectangleProperties(null, null, null);

        // Check time limit
        val timeChecked = checkRangeLimit(graphStartTime, start, end);

        // Convert to hour and minute
        val timeConverted = convertToHourMinute(timeChecked.getStartTime(), timeChecked.getEndTime());

        // Get map with key is Hour and value is column
        val hourColumnMap = getMapHourColumn(graphStartTime);

        val startTime = timeConverted.getStart();
        val endTime = timeConverted.getEnd();

        int columnStart = hourColumnMap.get(startTime.getHour()) == null ? 0 : hourColumnMap.get(startTime.getHour()) + 1;
        int minuteStart = roundUp(startTime.getMinute(), ROUNDING_INCREMENTS);
        if (minuteStart > 30 && minuteStart <= 60) {
            columnStart += 1;
            minuteStart -= 30;
        }
        int marginLeft = 0;
        if (startTime.getMinute() != 0) {
            marginLeft = Math.round(calcRatioCell() * minuteStart);
        }

        int columnEnd = hourColumnMap.get(endTime.getHour()) == null ? 0 : hourColumnMap.get(endTime.getHour()) + 1;
        int minuteEnd = roundDown(endTime.getMinute(), ROUNDING_INCREMENTS);
        if (minuteEnd > 30 && minuteEnd <= 60) {
            columnEnd += 1;
            minuteEnd -= 30;
        }

        int shapeWidth = minuteEnd == 0
                ? (columnEnd * 32) - (columnStart * 32) - marginLeft
                : ((columnEnd * 32) + Math.round(calcRatioCell() * minuteEnd)) - (columnStart * 32) - marginLeft;

        return new DrawRectangleProperties(columnStart, marginLeft, shapeWidth);
    }

    private TimeCheckedDto checkTime(int graphStartTime, Integer start, Integer end, TimeRangeLimitDto timeRange) {
        // Check data time in range of ruler time?
        val timeChecked = checkRangeLimit(graphStartTime, start, end);

        // Check overlap
        val overlapChecked = checkOverlapRange(timeRange, timeChecked.getStartTime(), timeChecked.getEndTime());

        return new TimeCheckedDto(overlapChecked.getStartTime(), overlapChecked.getEndTime());
    }

    private DoubleWorkTimeCheckedDto checkTimeDoubleWorkDisplay(int graphStartTime, Integer start1, Integer end1, Integer start2,
                                                                Integer end2, TimeRangeLimitDto timeRange1, TimeRangeLimitDto timeRange2) {
        // Check data time in range of ruler time?
//        val timeChecked = checkLimitTime(graphStartTime, start1, end1);

        // Check overlap
        val overlapChecked1 = checkOverlapRange(timeRange1, start1, end1);
        val overlapChecked2 = checkOverlapRange(timeRange2, start2, end2);

        return new DoubleWorkTimeCheckedDto(
                new TimeCheckedDto(overlapChecked1.getStartTime(), overlapChecked1.getEndTime()),
                new TimeCheckedDto(overlapChecked2.getStartTime(), overlapChecked2.getEndTime()));
    }

    /**
     * Check limit time with ruler of time in excel
     *
     * @param graphStartTime graphStartTime
     * @param start          startTime
     * @param end            endTime
     * @return TimeCheckedDto
     */
    private TimeCheckedDto checkRangeLimit(int graphStartTime, Integer start, Integer end) {
        val timeLimit = getTimeLimit(graphStartTime, OutputType.TOTAL_MINUTE);
        if (!isInRange(start, timeLimit.getMinLimit(), timeLimit.getMaxLimit()) || !isInRange(end, timeLimit.getMinLimit(), timeLimit.getMaxLimit())) {
            if (start < timeLimit.getMinLimit()) {
                start = timeLimit.getMinLimit();
            }

            if (end > timeLimit.getMaxLimit()) {
                end = timeLimit.getMaxLimit();
            }
        }

        return new TimeCheckedDto(start, end);
    }

    /**
     * Get min time limit and max time limit
     *
     * @param graphStartTime graphStartTime
     * @return TimeRangeLimitDto
     */
    private TimeRangeLimitDto getTimeLimit(int graphStartTime, OutputType type) {
        Integer minLimit = null, maxLimit = null;
        val mapHourAndColumn = getMapHourColumn(graphStartTime);
        val firstEntry = Ksu003Utils.findFirstEntry(mapHourAndColumn);

        switch (type) {
            case TOTAL_MINUTE:
                minLimit = firstEntry.getKey() * MINUTES_IN_AN_HOUR;
                maxLimit = Ksu003Utils.findLastEntryKey(mapHourAndColumn) * MINUTES_IN_AN_HOUR;
                break;
            case HOUR:
                minLimit = firstEntry.getKey();
                maxLimit = Ksu003Utils.findLastEntryKey(mapHourAndColumn);
                break;
            case COLUMN:
                minLimit = firstEntry.getValue();
                maxLimit = Ksu003Utils.findLastEntryValue(mapHourAndColumn);
                break;
        }

        return new TimeRangeLimitDto(minLimit, maxLimit);
    }

    /**
     * Check overlap range
     *
     * @param start start
     * @param end   end
     * @return TimeRangeLimitDto
     */
    private TimeCheckedDto checkOverlapRange(TimeRangeLimitDto rangeTime, Integer start, Integer end) {
        Integer startTime = start, endTime = end;
        if (!isInRange(start, rangeTime.getMinLimit(), rangeTime.getMaxLimit())) {
            if (start < rangeTime.getMinLimit())
                startTime = rangeTime.getMinLimit();
        }

        if (!isInRange(end, rangeTime.getMinLimit(), rangeTime.getMaxLimit())) {
            if (end > rangeTime.getMaxLimit())
                endTime = rangeTime.getMaxLimit();
        }

        return new TimeCheckedDto(startTime, endTime);
    }

    private List<TimeCheckedDto> checkContinuityOfTime(List<ScheduleTimeInput> sources) {
        return null;
    }

    /**
     * Check overlap range for actual time
     *
     * @param start start
     * @param end   end
     * @return TimeRangeLimitDto
     */
    private TimeRangeLimitDto checkOverlapRangeActualTime(TimeRangeLimitDto rangeTime, Integer start, Integer end) {
        Integer startTime = start, endTime = end;
        if (start < rangeTime.getMinLimit())
            startTime = rangeTime.getMinLimit();
        if (end > rangeTime.getMaxLimit())
            endTime = rangeTime.getMaxLimit();

        return new TimeRangeLimitDto(startTime, endTime);
    }

    private TimeRangeLimitDto checkRangeBreakTime(List<TimeSpanForCalcDto> timeSpanForCalcList, TimeRangeLimitDto fixedTime) {
        TimeRangeLimitDto timeRange = new TimeRangeLimitDto(0, 9999);
        for (val item : timeSpanForCalcList) {
            if (item.getStart() > fixedTime.getMaxLimit()) {
                timeRange.setMaxLimit(item.getStart());
            }
            if (item.getEnd() < fixedTime.getMinLimit()) {
                timeRange.setMinLimit(item.getEnd());
            }
        }

        return timeRange;
    }

    /**
     * Convert to hour + minute
     *
     * @param start start
     * @param end   end
     * @return HourMinuteInfo
     */
    private HourMinuteInfo convertToHourMinute(int start, int end) {
        val startTime = minuteToTime(start);
        String[] hourMinutesStart = startTime.trim().split(":");

        val endTime = minuteToTime(end);
        String[] hourMinutesEnd = endTime.trim().split(":");

        return new HourMinuteInfo(
                new HourMinuteValue(Integer.parseInt(hourMinutesStart[0]), Integer.parseInt(hourMinutesStart[1])),
                new HourMinuteValue(Integer.parseInt(hourMinutesEnd[0]), Integer.parseInt(hourMinutesEnd[1]))
        );
    }

    /**
     * Map Hour and column
     *
     * @param graphStartTime graphStartTime
     * @return Map
     */
    private Map<Integer, Integer> getMapHourColumn(int graphStartTime) {
        Map<Integer, Integer> mapHourAndColumn = new LinkedHashMap<>(); // Map<Hour,Column>
        if (isEven(graphStartTime)) {
            for (int column = 7; column <= 55; column += 2) {
                mapHourAndColumn.put(graphStartTime, column);
                graphStartTime += 1;
            }
        } else {
            mapHourAndColumn.put(graphStartTime, 7);
            graphStartTime += 1;                // +1 => value is even
            for (int column = 9; column <= 55; column += 2) {
                mapHourAndColumn.put(graphStartTime, column);
                graphStartTime += 1;
            }
        }

        return mapHourAndColumn;
    }

    private String getWorkName(String code, String name) {
        return StringUtils.isNotEmpty(name) ? name : StringUtils.isNotEmpty(code) ? code + getText("KSU003_189") : Strings.EMPTY;
    }

    private boolean isNextPage(int rowCount, int pageIndex) {
        return (rowCount - (MAX_ROW_IN_PAGE * pageIndex)) - MAX_ROW_HEADER_IN_PAGE > MAX_ROW_IN_PAGE;
    }

    /**
     * Check number is even or odd
     *
     * @param number number
     * @return true/false
     */
    private boolean isEven(int number) {
        return number % 2 == 0;
    }

    private boolean isEndOfPage(int rowCount, int pageIndex) {
        return (rowCount - (MAX_ROW_IN_PAGE * pageIndex)) - MAX_ROW_HEADER_IN_PAGE > MAX_ROW_IN_PAGE - 2;
    }

    private String minuteToTime(Integer totalMinute) {
        if (totalMinute == null) return "0:00";
        int hour = totalMinute / MINUTES_IN_AN_HOUR;
        int minute = totalMinute % MINUTES_IN_AN_HOUR;

        return (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute);
    }

    private int calcWidthCells(int column) {
        return column * 25;
    }

    private float calcRatioCell() {
        return (float) 25 / 30;
    }

    private int roundUp(int num, int multipleOf) {
        int temp = num % multipleOf;
        if (temp < 0)
            temp = multipleOf + temp;
        if (temp == 0) // Trường hợp num = 5, 10, 15, 20,... thì không làm tròn
            return num;
        return num + multipleOf - temp;
    }

    private int roundDown(int num, int multipleOf) {
        double result = num / multipleOf;
        result = Math.floor(result);
        result *= multipleOf;
        return (int) result;
    }

    private String getText(String resourceId) {
        return TextResource.localize(resourceId);
    }

    private void drawRectangle(ShapeCollection shapes, int row, int column, int width, int left, Color color, boolean displayActual, Integer zOrderIndex) throws Exception {
        Shape shape = shapes.addShape(MsoDrawingType.RECTANGLE, row, displayActual ? 12 : 4, column, left, displayActual ? 8 : 25, width);
        shape.setPrintable(true);
        shape.getFill().setFillType(FillType.SOLID);
        shape.getFill().getSolidFill().setColor(color);
        shape.getLine().setWeight(0);
        shape.getLine().setFillType(FillType.SOLID);
        shape.getLine().getSolidFill().setColor(Color.getBlack());
        if (zOrderIndex != null) {
            shape.setZOrderPosition(zOrderIndex);
        }
    }

    private Color getBarColor(BarType barType) {
        switch (barType) {
            case FIXED_WORKING_HOURS:    // C3_2_1
                return Color.fromArgb(204, 204, 255);
            case BREAK_TIME:             // C3_2_2
                return Color.fromArgb(255, 153, 153);
            case OVERTIME_HOURS:          // C3_2_3
                return Color.fromArgb(255, 255, 0);
            case FLOWING_WORKING_HOURS:   // C3_2_4
                return Color.fromArgb(255, 192, 0);
            case FLEX_WORKING_HOURS:      // C3_2_5
                return Color.fromArgb(204, 204, 255);
            case CORE_TIME:               // C3_2_6
                return Color.fromArgb(0, 255, 204);
            case TIME_VACATION:           // C3_2_7
                return Color.fromArgb(196, 189, 151);
            case CHILDCARE_SHORT_TIME:    // C3_2_8
                return Color.fromArgb(111, 165, 39);
            case WORKING_HOURS_ACTUAL:    // C3_2_14
                return Color.fromArgb(24, 23, 23);
            case BREAK_TIME_ACTUAL:       // C3_2_15
                return Color.fromArgb(255, 153, 255);
            case OVERTIME_HOURS_ACTUAL:   // C3_2_16
                return Color.fromArgb(0, 102, 255);
            default:
                return Color.getEmpty();
        }
    }

    @SuppressWarnings("Duplicates")
    private String getDayOfWeek(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY:
                return "月";
            case TUESDAY:
                return "火";
            case WEDNESDAY:
                return "水";
            case THURSDAY:
                return "木";
            case FRIDAY:
                return "金";
            case SATURDAY:
                return "土";
            case SUNDAY:
                return "日";
            default:
                return "";
        }
    }

    private boolean isInRange(int i, int minValueInclusive, int maxValueInclusive) {
        return (i >= minValueInclusive && i <= maxValueInclusive);
    }

    private void printB5(Cells cells, List<SpecificName> specDayCompanies, List<SpecificName> specDayWorkplaces) {
        val mergedSpecDayList = Stream.of(specDayCompanies, specDayWorkplaces).flatMap(Collection::stream).collect(Collectors.toList());
        int rowStart = getRowStartB5(mergedSpecDayList.size());
        int row = rowStart;
        int column = 14;
        for (SpecificName specDayName : mergedSpecDayList) {
            // B5_1, B5_2
            if (row > MAX_ROW_B5) {
                row = rowStart;
                column = 21;
            }
            cells.get(row, column).setValue(getText(column == 14 ? "KSU003_186" : "KSU003_187") + specDayName.v());
            setBottomBorder(cells, row, column, column == 14 ? 19 : 26);
            row += 1;
        }
    }

    private int getRowStartB5(int maxSize) {
        switch (maxSize) {
            case 1:
            case 2:
                return 4;
            case 3:
            case 4:
                return 3;
            case 5:
            case 6:
                return 2;
            case 7:
            case 8:
                return 1;
            case 9:
            case 10:
                return 0;
            default:
                return 4;
        }
    }

    private void setStyleEmpWorkInfo(Cells cells, int row, boolean isDoubleWorkDisplay) {
        for (int column = 0; column < 7; column++) {
            if (isDoubleWorkDisplay && (column == 3 || column == 4)) continue;
            cells.merge(row, column, 2, 1, true, true);
            setStyleEmployeeWorkInfo(cells.get(row, column), isDoubleWorkDisplay);
        }
    }

    private void setStyleEmployeeWorkInfo(Cell cell, boolean isDoubleWorkDisplay) {
        Style style = cell.getStyle();
        style.setHorizontalAlignment(TextAlignmentType.CENTER);
        style.setVerticalAlignment(TextAlignmentType.CENTER);
        if (!isDoubleWorkDisplay) {
            style.setTextWrapped(true);
        }
        style.getFont().setSize(9);
        cell.setStyle(style);
    }

    private void setBottomBorder(Cells cells, int row, int columnStart, int columnEnd) {
        for (int col = columnStart; col <= columnEnd; col++) {
            Cell cell = cells.get(row, col);
            Style style = cell.getStyle();
            style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
            cell.setStyle(style);
        }
    }

    private void removeTopBorder(Cell cell) {
        Style style = cell.getStyle();
        style.setBorder(BorderType.TOP_BORDER, CellBorderType.NONE, Color.getEmpty());
        style.getBorders().getByBorderType(BorderType.TOP_BORDER).setLineStyle(CellBorderType.NONE);
        cell.setStyle(style);
    }
}

class Ksu003Utils {
    public static <K, V> Map.Entry<K, V> findFirstEntry(Map<K, V> map) {
        return map.entrySet().iterator().next();
    }

    public static <K, V> Map.Entry<K, V> findLastEntry(Map<K, V> linkedMap) {
        List<Map.Entry<K, V>> entryList = new ArrayList<>(linkedMap.entrySet());
        return entryList.get(entryList.size() - 1);
    }

    public static Integer findLastEntryKey(Map<Integer, Integer> linkedMap) {
        List<Map.Entry<Integer, Integer>> entryList = new ArrayList<>(linkedMap.entrySet());
        return entryList.get(entryList.size() - 1).getKey();
    }

    public static Integer findLastEntryValue(Map<Integer, Integer> linkedMap) {
        List<Map.Entry<Integer, Integer>> entryList = new ArrayList<>(linkedMap.entrySet());
        return entryList.get(entryList.size() - 1).getValue();
    }
}
