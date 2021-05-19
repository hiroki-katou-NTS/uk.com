package nts.uk.ctx.at.function.infra.generator.holidaysremaining;

import com.aspose.cells.*;
import com.aspose.pdf.Operator;
import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.*;
import nts.uk.ctx.at.function.dom.adapter.periodofspecialleave.SpecialHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.periodofspecialleave.SpecialVacationImported;
import nts.uk.ctx.at.function.dom.adapter.reserveleave.ReservedYearHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.reserveleave.RsvLeaUsedCurrentMonImported;
import nts.uk.ctx.at.function.dom.adapter.vacation.StatusHolidayImported;
import nts.uk.ctx.at.function.dom.holidaysremaining.BreakSelection;
import nts.uk.ctx.at.function.dom.holidaysremaining.HolidaysRemainingManagement;
import nts.uk.ctx.at.function.dom.holidaysremaining.VariousVacationControl;
import nts.uk.ctx.at.function.dom.holidaysremaining.VariousVacationControlService;
import nts.uk.ctx.at.function.dom.holidaysremaining.report.HolidayRemainingDataSource;
import nts.uk.ctx.at.function.dom.holidaysremaining.report.HolidaysRemainingEmployee;
import nts.uk.ctx.at.function.dom.holidaysremaining.report.HolidaysRemainingReportGenerator;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnLeaMaxDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveEmSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class HolidaysRemainingReportGeneratorImp extends AsposeCellsReportGenerator
        implements HolidaysRemainingReportGenerator {
    @Inject
    private SpecialLeaveGrantRepository specialLeaveGrantRepository;

    @Inject
    private AnnualPaidLeaveSettingRepository annualPaidLeaveSettingRepository;

    @Inject
    private AnnLeaMaxDataRepository annLeaMaxDataRepository;

    @Inject
    private EmploymentSettingRepository employmentSettingRepository;

    @Inject
    private CompensLeaveEmSetRepository compensLeaveEmSetRepository;
    @Inject
    private CompensLeaveComSetRepository compensLeaveComSetRepository;

    private static final String TEMPLATE_FILE = "report/KDR001_V4.xlsx";
    private static final String REPORT_FILE_NAME = "休暇残数管理表.xlsx";
    private static final int NUMBER_ROW_OF_PAGE = 53;
    private static final int NUMBER_ROW_OF_HEADER = 5;
    private static final int NUMBER_COLUMN = 23;
    private static final int MIN_ROW_DETAILS = 4;
    private static final int TOTAL_MONTH_IN_YEAR = 12;
    private static final int MAX_ROW_ANNUAL_HOLIDAY = 10;
    private static final int MIN_ROW_ANNUAL_HOLIDAY = 2;

    @Override
    public void generate(FileGeneratorContext generatorContext, HolidayRemainingDataSource dataSource) {
        try {
            val designer = this.createContext(TEMPLATE_FILE);
            Workbook workbook = designer.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            // Get first sheet in template
            Worksheet worksheet = worksheets.get(0);
            printHeader(worksheet, dataSource);
            printTemplate(worksheet, dataSource);

            if (dataSource.getPageBreak() == BreakSelection.None.value) {
                printNoneBreakPage(worksheet, dataSource);
            } else if (dataSource.getPageBreak() == BreakSelection.Workplace.value) {
                printWorkplaceBreakPage(worksheet, dataSource);
            } else if (dataSource.getPageBreak() == BreakSelection.Individual.value) {
                printPersonBreakPage(worksheet, dataSource);
            }
            removeTemplate(worksheet);
            designer.getDesigner().setWorkbook(workbook);
            designer.processDesigner();

            designer.saveAsExcel(this.createNewFile(generatorContext, this.getReportName(REPORT_FILE_NAME)));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void printTemplate(Worksheet worksheet, HolidayRemainingDataSource dataSource) {
        Cells cells = worksheet.getCells();

        YearMonth startMonth = dataSource.getStartMonth().yearMonth();
        YearMonth endMonth = dataSource.getEndMonth().yearMonth();

        // B1_1, B1_2 // Update ~ -> KDR001_73
        cells.get(1, 0).setValue(TextResource.localize("KDR001_2") +
                dataSource.getStartMonth().toString("yyyy/MM")
                + TextResource.localize("KDR001_73") + dataSource.getEndMonth().toString("yyyy/MM"));
        // B1_3
        //cells.get(2, 0).setValue(TextResource.localize("KDR001_3"));// Ver: 15 : delete
        // C1_1
        cells.get(3, 2).setValue(TextResource.localize("KDR001_4"));
        // C1_2
        cells.get(3, 4).setValue(TextResource.localize("KDR001_5"));
        // C1_3
        cells.get(3, 9).setValue(TextResource.localize("KDR001_6"));
        // C1_4
        cells.get(4, 4).setValue(TextResource.localize("KDR001_7"));
        // C1_5
        cells.get(4, 5).setValue(TextResource.localize("KDR001_8"));
        // C1_6
        cells.get(4, 6).setValue(TextResource.localize("KDR001_9"));
        // C1_7
        cells.get(4, 7).setValue(TextResource.localize("KDR001_10"));
        // C1_8
        cells.get(4, 8).setValue(TextResource.localize("KDR001_11"));

        Optional<HolidaysRemainingEmployee> hdEmpOpt = dataSource.getMapEmployees().values().stream().findFirst();
        Optional<YearMonth> curMonthOpt = hdEmpOpt.isPresent() ? hdEmpOpt.get().getCurrentMonth() : Optional.empty();
        if (!dataSource.isSameCurrentMonth() || !curMonthOpt.isPresent() || curMonthOpt.get().compareTo(endMonth) > 0
                || curMonthOpt.get().compareTo(startMonth) < 0) {
            removeFirstShapes(worksheet);
        } else {
            Shape shape = worksheet.getShapes().get(0);
            shape.setLowerRightColumn(shape.getLowerRightColumn() + totalMonths(startMonth, curMonthOpt.get()));
        }
        // C1_9
        for (int i = 0; i <= totalMonths(startMonth, endMonth); i++) {
            // Update 月 -> TextResource.localize("KDR001_74")
            cells.get(4, 10 + i).setValue(String.valueOf(startMonth.addMonths(i).month()) + TextResource.localize("KDR001_74"));
        }
    }

    private void printNoneBreakPage(Worksheet worksheet, HolidayRemainingDataSource dataSource) throws Exception {
        int firstRow = NUMBER_ROW_OF_PAGE;
        Cells cells = worksheet.getCells();
        // print Header
        cells.copyRows(cells, 0, firstRow, NUMBER_ROW_OF_HEADER + 1);
        firstRow += NUMBER_ROW_OF_HEADER;
        for (String employeeIds : dataSource.getEmpIds()) {
            HolidaysRemainingEmployee employee = dataSource.getMapEmployees().get(employeeIds);
            int rowCount = countRowEachPerson(dataSource) + 1;
            if (firstRow % NUMBER_ROW_OF_PAGE != NUMBER_ROW_OF_HEADER
                    && firstRow / NUMBER_ROW_OF_PAGE != (firstRow + rowCount) / NUMBER_ROW_OF_PAGE) {
                firstRow = (firstRow / NUMBER_ROW_OF_PAGE + 1) * NUMBER_ROW_OF_PAGE;
                // print Header
                cells.copyRows(cells, 0, firstRow, NUMBER_ROW_OF_HEADER + 1);
                firstRow += NUMBER_ROW_OF_HEADER;
            }
            // D1_1, D1_2
            cells.copyRows(cells, 5, firstRow, 1);
            cells.get(firstRow, 0).setValue(TextResource.localize("KDR001_12") + ": " + employee.getWorkplaceCode()
                    + "　" + employee.getWorkplaceName());
            firstRow += 1;
            firstRow = this.printHolidayRemainingEachPerson(worksheet, firstRow, employee, dataSource);
        }
    }
    private void printWorkplaceBreakPage(Worksheet worksheet, HolidayRemainingDataSource dataSource) throws Exception {
        int firstRow = NUMBER_ROW_OF_PAGE;
        Map<String, List<HolidaysRemainingEmployee>> maps = new HashMap<>();

        for (String employeeIds : dataSource.getEmpIds()) {
            HolidaysRemainingEmployee hRemaiEmployee = dataSource.getMapEmployees().get(employeeIds);
            List<HolidaysRemainingEmployee> list = maps.get(hRemaiEmployee.getWorkplaceId());
            if (list == null) {
                list = new ArrayList<HolidaysRemainingEmployee>();
            }
            list.add(hRemaiEmployee);
            maps.put(hRemaiEmployee.getWorkplaceId(), list);
        }
        for (List<HolidaysRemainingEmployee> listEmployee : maps.values()) {
            firstRow = printEachWorkplace(worksheet, firstRow, listEmployee, dataSource);
        }
    }
    private int printEachWorkplace(Worksheet worksheet, int firstRow, List<HolidaysRemainingEmployee> employees,
                                   HolidayRemainingDataSource dataSource) throws Exception {
        if (employees.size() == 0) {
            return firstRow;
        }
        Cells cells = worksheet.getCells();
        // print Header
        cells.copyRows(cells, 0, firstRow, NUMBER_ROW_OF_HEADER + 1);
        // D1_1, D1_2
        cells.get(firstRow + NUMBER_ROW_OF_HEADER, 0).setValue(TextResource.localize("KDR001_12") + ": "
                + employees.get(0).getWorkplaceCode() + "　" + employees.get(0).getWorkplaceName());
        firstRow += NUMBER_ROW_OF_HEADER + 1;
        for (HolidaysRemainingEmployee employee : employees) {
            int rowCount = countRowEachPerson(dataSource);
            if (firstRow % NUMBER_ROW_OF_PAGE != NUMBER_ROW_OF_HEADER + 1
                    && firstRow / NUMBER_ROW_OF_PAGE != (firstRow + rowCount) / NUMBER_ROW_OF_PAGE) {
                firstRow = (firstRow / NUMBER_ROW_OF_PAGE + 1) * NUMBER_ROW_OF_PAGE;
                // print Header
                cells.copyRows(cells, 0, firstRow, NUMBER_ROW_OF_HEADER + 1);
                // D1_1, D1_2
                cells.get(firstRow + NUMBER_ROW_OF_HEADER, 0).setValue(TextResource.localize("KDR001_12") + ": "
                        + employees.get(0).getWorkplaceCode() + "　" + employees.get(0).getWorkplaceName());
                firstRow += NUMBER_ROW_OF_HEADER + 1;
            }
            firstRow = printHolidayRemainingEachPerson(worksheet, firstRow, employee, dataSource);
        }
        if (firstRow % NUMBER_ROW_OF_PAGE != 0) {
            firstRow += (NUMBER_ROW_OF_PAGE - firstRow % NUMBER_ROW_OF_PAGE);
        }
        return firstRow;
    }

    private void printPersonBreakPage(Worksheet worksheet, HolidayRemainingDataSource dataSource) throws Exception {
        int firstRow = NUMBER_ROW_OF_PAGE;

        for (String employeeIds : dataSource.getEmpIds()) {
            HolidaysRemainingEmployee employee = dataSource.getMapEmployees().get(employeeIds);
            firstRow = this.printEachPerson(worksheet, firstRow, employee, dataSource);
        }
    }
    private int printEachPerson(Worksheet worksheet, int firstRow, HolidaysRemainingEmployee employee,
                                HolidayRemainingDataSource dataSource) throws Exception {
        Cells cells = worksheet.getCells();
        // D index
        // print Header
        cells.copyRows(cells, 0, firstRow, NUMBER_ROW_OF_HEADER + 1);
        // D1_1, D1_2
        cells.get(firstRow + NUMBER_ROW_OF_HEADER, 0).setValue(TextResource.localize("KDR001_12") + ": "
                + employee.getWorkplaceCode() + "　" + employee.getWorkplaceName());
        firstRow += NUMBER_ROW_OF_HEADER + 1;

        firstRow = printHolidayRemainingEachPerson(worksheet, firstRow, employee, dataSource);

        if (firstRow % NUMBER_ROW_OF_PAGE != 0) {
            firstRow += (NUMBER_ROW_OF_PAGE - firstRow % NUMBER_ROW_OF_PAGE);
        }
        return firstRow;
    }

    private int printHolidayRemainingEachPerson(Worksheet worksheet, int firstRow, HolidaysRemainingEmployee employee,
                                                HolidayRemainingDataSource dataSource) throws Exception {
        int rowIndexD = firstRow;
        int totalRowD = 0;
        Cells cells = worksheet.getCells();
        // E
        firstRow = printAnnualHoliday(cells, firstRow, employee, dataSource);
        // F
        firstRow = printLimitForHalfHolidays(cells, firstRow, employee, dataSource);
        //G
        firstRow = printLimitUsageHolidays(cells, firstRow, employee, dataSource);
        // H
        firstRow = printYearlyReserved(cells, firstRow, employee, dataSource);
        //I
        firstRow = printSubstituteHoliday(cells, firstRow, employee, dataSource);
        // J
        firstRow = printPauseHoliday(cells, firstRow, employee, dataSource);
        // K
        firstRow = print60hOversho(cells, firstRow, employee, dataSource);
        // L
        firstRow = publicHolidays(cells, firstRow, employee, dataSource);
        // M
        firstRow = printSpecialHoliday(cells, firstRow, employee, dataSource);
        // N
        firstRow = printChildNursingVacation(cells, firstRow, employee, dataSource);
        // O
        firstRow = printNursingCareLeave(cells, firstRow, employee, dataSource);
        int totalRowDetails = countRowEachPerson(dataSource);
        if (totalRowDetails < MIN_ROW_DETAILS) {
            // Insert blank rows
            cells.copyRows(cells, 33, firstRow, MIN_ROW_DETAILS - totalRowDetails);
            firstRow += (MIN_ROW_DETAILS - totalRowDetails);
        }
        // merger cột  D2_1 + D2_2
        cells.merge(rowIndexD, 0, 1, 2, true);
        // D2_1
        totalRowD += 1;
        for (int index = 0; index < 2; index++) {
            setTopBorderStyle(cells.get(rowIndexD, index));
        }
        cells.get(rowIndexD, 0).setValue(employee.getEmployeeCode() + " " + employee.getEmployeeName());
        // D2_7 + D25 (POISITION CODE + POISITION NAME)
        cells.merge(rowIndexD + 1, 0, 1, 2, true);
        val d2_7 = employee.getPositionCode() + "  "
                + employee.getPositionName();
        totalRowD += 1;
        cells.get(rowIndexD + 1, 0).setValue(d2_7);
        // D2_6 +D2_4: EMPLOYMENT CODE +EMPLOYMENT NAME
        val d2_6 = employee.getEmploymentCode() + "  "
                + employee.getEmploymentName();
        cells.merge(rowIndexD + 2, 0, 1, 2, true);
        cells.get(rowIndexD + 2, 0)
                .setValue(d2_6);
        totalRowD += 1;
        // D2_3 No.369
        Optional<GeneralDate> grantDate = dataSource.getMapEmployees().get(employee.getEmployeeId())
                .getHolidayRemainingInfor().getGrantDate();
        boolean isDisplayHolidayYear = dataSource.getVariousVacationControl().isAnnualHolidaySetting()
                && dataSource.getHolidaysRemainingManagement()
                .getListItemsOutput().getAnnualHoliday().isYearlyHoliday();
        if (isDisplayHolidayYear) {
            // D2_3
            cells.merge(rowIndexD + 3, 0, 1, 2, true);
            if (grantDate.isPresent()) {
                cells.get(rowIndexD + 3, 0)
                        .setValue("(" + TextResource.localize("KDR001_71",
                                grantDate.get().toString("yyyy/MM/dd")) + ")");
                totalRowD += 1;
            }
            //  D2_8 No.717 TODO QA : RQ 717
            // cells.merge(rowIndexD + 4, 0, 1, 2, true);
            // cells.get(rowIndexD + 3, 0)
            //                        .setValue(TextResource.localize("("+"KDR001_72",
            //                                grantDate.get().toString("yyyy/MM/dd"))+")");
            //totalRowD+=1;

        }
        rowIndexD += totalRowD;
        if (rowIndexD > firstRow) {
            for (int index = 0; index < NUMBER_COLUMN; index++) {
                setBottomBorderStyle(cells.get(rowIndexD, index));
            }
        } else {
            for (int index = 0; index < NUMBER_COLUMN; index++) {
                setBottomBorderStyle(cells.get(firstRow - 1, index));
            }

        }
        return firstRow;
    }
    private int printAnnualHoliday(Cells cells, int firstRow, HolidaysRemainingEmployee employee,
                                   HolidayRemainingDataSource dataSource) throws Exception {
        NumberFormat df = new DecimalFormat("#0.0");
        // 年休
        // Check holiday management
        if (!dataSource.getVariousVacationControl().isAnnualHolidaySetting()) {
            return firstRow;
        }
        if (!dataSource.getHolidaysRemainingManagement().getListItemsOutput().getAnnualHoliday().isYearlyHoliday()) {
            return firstRow;
        }
        int totalAddRows = 0;
        cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 1, firstRow, MIN_ROW_ANNUAL_HOLIDAY);
        // E1_1
        cells.get(firstRow, 2).setValue(TextResource.localize("KDR001_13"));
        // E2_1
        cells.get(firstRow, 9).setValue(TextResource.localize("KDR001_14"));
        val hdRemainingInfor = dataSource.getMapEmployees().get(employee.getEmployeeId()).getHolidayRemainingInfor();
        if (hdRemainingInfor == null) {
            return firstRow + 2;
        }
        // Result RequestList281
        List<AnnLeaGrantNumberImported> listAnnLeaGrant = hdRemainingInfor.getListAnnLeaGrantNumber();
        Optional<GeneralDate> grantDate = dataSource.getMapEmployees().get(employee.getEmployeeId())
                .getHolidayRemainingInfor().getGrantDate();
        // Update :超える件数分の行を追加して付与日と付与数を表示する : bỏ giới hạn 10 dòng.
        listAnnLeaGrant = listAnnLeaGrant.stream()
                .sorted(Comparator.comparing(AnnLeaGrantNumberImported::getGrantDate))
                .collect(Collectors.toList());
        //RQ 363
        if (listAnnLeaGrant != null && grantDate.isPresent()) {
            for (int i = 0; i < listAnnLeaGrant.size(); i++) {
                if (i == 1) {
                    cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 3, firstRow + 2 * i, 2);
                } else if (i >= 2) {
                    cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 5, firstRow + i * 2, 2);
                    // E3_1
                    val text = checkShowAreaAnnualBreak1(
                            dataSource.getHolidaysRemainingManagement())
                            ? TextResource.localize("KDR001_15")
                            : "";
                    cells.get(firstRow + 2, 9).setValue(text);
                }
                // E1_2, 事象(4)của bug #102883
                cells.get(firstRow + 2 * i, 3).setValue(TextResource.localize("KDR001_57",
                        listAnnLeaGrant.get(i).getGrantDate().toString("yyyy/MM/dd")));
                // E1_3
                val vlaueE13 = listAnnLeaGrant.get(i).getGrantDays();
                Double days_Granted = checkShowAreaAnnualBreak1(
                        dataSource.getHolidaysRemainingManagement()) ?
                        (double) vlaueE13 : null;
                cells.get(firstRow + 2 * i, 4).setValue(df.format(days_Granted));
                totalAddRows += 2;
            }
        }
        // RQ 363
        val listAnnLeaveUsageStatusOfThisMonth = hdRemainingInfor.getListAnnLeaveUsageStatusOfThisMonth();
        // rs 363
        List<AggrResultOfAnnualLeaveEachMonthKdr> rs363New = hdRemainingInfor.getRs363New();
        val listItemGrant = rs363New.stream().flatMap(e -> e.getAggrResultOfAnnualLeave()
                .getAsOfGrant().stream()).collect(Collectors.toList());
        if (dataSource != null && listAnnLeaveUsageStatusOfThisMonth != null && grantDate.isPresent()) {
            // E1_4 - SUM VALUE 281
            val valueE14 = listAnnLeaGrant.stream().mapToDouble(AnnLeaGrantNumberImported::getGrantDays).sum(); // TODO QA
            Double leave_DaysRemain = checkShowAreaAnnualBreak1(
                    dataSource.getHolidaysRemainingManagement()) ?
                    (double) valueE14 : null;
            cells.get(firstRow, 5).setValue(df.format(leave_DaysRemain));
            if (leave_DaysRemain != null && leave_DaysRemain < 0) {
                setForegroundRed(cells.get(firstRow, 5));
            }
            // E1_5 - value in 363- 年休_使用数_日数
            val use_date = listAnnLeaveUsageStatusOfThisMonth.stream()
                    .mapToDouble(AnnLeaveUsageStatusOfThisMonthImported::getMonthlyUsageDays).sum();
            val use_after_grant = listItemGrant.stream().mapToDouble(e -> e.getUsedDays().v()).sum();
            val valueE15 = use_date + use_after_grant; //
            Double used_Days = checkShowAreaAnnualBreak1(
                    dataSource.getHolidaysRemainingManagement()) ?
                    (double) valueE15 : null;
            cells.get(firstRow, 6).setValue(df.format(used_Days));
            if (used_Days != null && used_Days < 0) {
                setForegroundRed(cells.get(firstRow, 6));
            }
            // E1_6 - value in 363 - 年休_残数_日数// TODO
            val valueE16 = listAnnLeaveUsageStatusOfThisMonth.stream()
                    .mapToDouble(AnnLeaveUsageStatusOfThisMonthImported::getMonthlyRemainingDays)
                    .sum(); //
            Double number_Hours = checkShowAreaAnnualBreak1(
                    dataSource.getHolidaysRemainingManagement()) ?
                    (double) valueE16 : null;
            cells.get(firstRow, 7).setValue(df.format(number_Hours));

            if (number_Hours != null && number_Hours < 0) {
                setForegroundRed(cells.get(firstRow, 7));
            }
            // E1_13; SUM VALUE 281
            Double valueE113 = null; // todo
            Double leave_Hours = checkShowAreaAnnualBreak2(
                    dataSource.getHolidaysRemainingManagement()) ?
                    valueE113 : null;
            val e113 = leave_Hours != null ? convertToTime((int) (leave_Hours * 60)) : "";
            cells.get(firstRow + 1, 5).setValue((e113));
            if (leave_Hours != null && leave_Hours < 0) {
                setForegroundRed(cells.get(firstRow + 1, 5));
            }
            // E1_14- value in 363// 年休_使用数_時間 todo;
            val use_time = listAnnLeaveUsageStatusOfThisMonth.stream()
                    .mapToDouble(e -> e.getMonthlyUsageTime().isPresent() ? e.getMonthlyUsageTime().get() : 0).sum();
            val use_after_grant_time = listItemGrant.stream().mapToDouble(e -> e.getUsedTime().v()).sum();
            val valueE114 = use_time + use_after_grant_time;
            Double uses_Hours = checkShowAreaAnnualBreak2(dataSource.getHolidaysRemainingManagement()) ?
                    (double) valueE114 : null;
            val e114 = uses_Hours != null ? convertToTime((int) (uses_Hours * 60)) : "";
            cells.get(firstRow + 1, 6).setValue(e114);
            if (uses_Hours != null && uses_Hours < 0) {
                setForegroundRed(cells.get(firstRow + 1, 6));
            }
            // E1_15- value in 363 // 年休_残数_時間 TODO;
            val valueE115 = listAnnLeaveUsageStatusOfThisMonth.stream()
                    .mapToDouble(e -> e.getMonthlyRemainingTime().isPresent() ?
                            e.getMonthlyRemainingTime().get() : 0)
                    .sum();
            Double leave_RemainHours =
                    checkShowAreaAnnualBreak2(dataSource.getHolidaysRemainingManagement()) ?
                            (double) valueE115 : null;
            val e115 = leave_RemainHours != null ? convertToTime((int) (leave_RemainHours * 60)) : "";
            cells.get(firstRow + 1, 7).setValue(e115);
            if (used_Days != null && used_Days < 0) {
                setForegroundRed(cells.get(firstRow + 1, 7));
            }
        }
        if (!employee.getCurrentMonth().isPresent()) {
            return firstRow + 1 + totalAddRows;
        }
        YearMonth currentMonth = employee.getCurrentMonth().get();
        // Result RequestList255
        List<AnnualLeaveUsageImported> listAnnLeaveUsage =
                hdRemainingInfor.getListAnnualLeaveUsage();

        int maxRange = totalMonths(dataSource.getStartMonth().yearMonth(), dataSource.getEndMonth().yearMonth());
        // BEFORE THIS MONTH: 255
        if (listAnnLeaveUsage != null && dataSource.getMapEmployees().get(employee.getEmployeeId()).getHolidayRemainingInfor().getGrantDate().isPresent()) {
            for (AnnualLeaveUsageImported item : listAnnLeaveUsage) {
                if (item.getYearMonth().compareTo(currentMonth) >= 0) {
                    continue;
                }
                int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(), item.getYearMonth());
                if (maxRange < totalMonth || totalMonth < 0) {
                    continue;
                }
                if (listAnnLeaGrant != null && listAnnLeaGrant.size() == 1) {
                    cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 3, firstRow + 2, 2);
                    val text = checkShowAreaAnnualBreak1(
                            dataSource.getHolidaysRemainingManagement())
                            ? TextResource.localize("KDR001_15")
                            : "";
                    cells.get(firstRow + 2, 9).setValue(text);
                }
                //

                val value23 = item.getUsedDays();
                Double e23 = checkShowAreaAnnualBreak1(
                        dataSource.getHolidaysRemainingManagement()) ?
                        (double) value23 : null;
                // E2_3 当月より前, set lại giá trị cho cột này nếu bằng 0 thì không hiển thị ra
                cells.get(firstRow, 10 + totalMonth)
                        .setValue(df.format(e23));
                // E2_4:月度使用時間 Update KDR ver 15
                val value24 = item.getUsedTime();
                val use_Time = checkShowAreaAnnualBreak2(dataSource.getHolidaysRemainingManagement()) ?
                        value24 : null;
                val e24 = use_Time != null ? convertToTime((int) (use_Time * 60)) : "";
                cells.get(firstRow + 1, 10 + totalMonth)
                        .setValue(e24);
                // E3_3 当月より前
                val valueE33 = item.getRemainingDays();
                val e33 = checkShowAreaAnnualBreak1(dataSource.getHolidaysRemainingManagement()) ?
                        valueE33 : null;
                cells.get(firstRow + 2, 10 + totalMonth)
                        .setValue(df.format(e33));
                // E3_4 当月より前
                val valueE34 = item.getRemainingDays();
                val e34 = checkShowAreaAnnualBreak2(dataSource.getHolidaysRemainingManagement()) ?
                        valueE34 : null;
                val vle34 = e34 != null ? convertToTime((int) (e34 * 60)) : "";
                cells.get(firstRow + 3, 10 + totalMonth)
                        .setValue(vle34);
                if (e33 != null && e33 < 0) {
                    setForegroundRed(cells.get(firstRow + 2, 10 + totalMonth));
                }
                if (e34 != null && e34 < 0) {
                    setForegroundRed(cells.get(firstRow + 3, 10 + totalMonth));
                }
            }
        }
        // Result RequestList363
        if (listAnnLeaveUsageStatusOfThisMonth != null && grantDate.isPresent()) {
            for (AnnLeaveUsageStatusOfThisMonthImported item : listAnnLeaveUsageStatusOfThisMonth) {
                if (currentMonth.compareTo(item.getYearMonth()) > 0) {
                    continue;
                }
                int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(), item.getYearMonth());
                if (maxRange < totalMonth || totalMonth < 0) {
                    continue;
                }
                if (listAnnLeaGrant != null && listAnnLeaGrant.size() == 1) {
                    cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 3, firstRow + 2, 2);
                    val text = checkShowAreaAnnualBreak1(
                            dataSource.getHolidaysRemainingManagement())
                            ? TextResource.localize("KDR001_15")
                            : "";
                    cells.get(firstRow + 2, 9).setValue(text);
                }
                if (currentMonth.compareTo(item.getYearMonth()) == 0) {
                    val listPerMonth = listAnnLeaveUsage.stream().filter(e -> e.getYearMonth().equals(currentMonth))
                            .collect(Collectors.toList());

                    val use_date255 = listPerMonth.stream().mapToDouble(AnnualLeaveUsageImported::getUsedDays).sum();
                    val use_date363 = item.getMonthlyUsageDays();

                    val value23bf = use_date255 + use_date363;

                    val per_month_after_grant = listItemGrant.stream().filter(e -> e.getYmd().yearMonth()
                            .equals(currentMonth)).collect(Collectors.toList());
                    val value23af = per_month_after_grant.stream().mapToDouble(e -> e.getUsedDays().v()).sum();
                    val vl23 = value23bf + "/" + value23af;
                    String e23 = checkShowAreaAnnualBreak1(
                            dataSource.getHolidaysRemainingManagement()) ?
                            vl23 : null;
                    // E2_3 当月以降, set lại giá trị cho cột này nếu bằng 0 thì không hiển thị ra
                    cells.get(firstRow, 10 + totalMonth)
                            .setValue(e23);
                    // E3_3 当月以降
                    val value33 = item.getMonthlyRemainingDays();
                    Double e33 = checkShowAreaAnnualBreak1(
                            dataSource.getHolidaysRemainingManagement()) ?
                            (double) value33 : null;
                    cells.get(firstRow + 1, 10 + totalMonth).setValue(e33 != null
                            && e33 != 0
                            ? e33 : null);
                    // E2_4
                    val use_time255 = listPerMonth.stream().mapToDouble(AnnualLeaveUsageImported::getUsedTime).sum();
                    val use_time363 = item.getMonthlyUsageTime().isPresent() ? item.getMonthlyUsageTime().get() : 0;
                    val value24bf = use_time255 + use_time363;

                    val value243af = per_month_after_grant.stream().mapToDouble(e -> e.getUsedTime().v()).sum();
                    val value24 = value24bf + "/" + value243af;
                    String e24 = checkShowAreaAnnualBreak1(
                            dataSource.getHolidaysRemainingManagement()) ?
                            value24 : null;
                    cells.get(firstRow + 2, 10 + totalMonth).setValue(e24);
                    val value34 = item.getMonthlyRemainingTime().isPresent() ?
                            item.getMonthlyRemainingTime().get() : 0;// chua xac dinh dc dang qa: TODO
                    Double e34 = checkShowAreaAnnualBreak1(
                            dataSource.getHolidaysRemainingManagement()) ?
                            (double) value34 : null;

                    cells.get(firstRow + 3, 10 + totalMonth).setValue(e33 != null
                            && e34 != 0
                            ? e34 : null);
                }
                if (item.getYearMonth().compareTo(currentMonth) > 0) {
                    //E2_3
                    val value23 = item.getMonthlyUsageDays();// chua xac dinh dc dang qa: TODO
                    Double e23 = checkShowAreaAnnualBreak1(
                            dataSource.getHolidaysRemainingManagement()) ?
                            (double) value23 : null;
                    // E2_3 当月以降, set lại giá trị cho cột này nếu bằng 0 thì không hiển thị ra
                    cells.get(firstRow, 10 + totalMonth)
                            .setValue(e23 != null
                                    && e23 != 0
                                    ? e23 : null);
                    val value24 = item.getMonthlyUsageTime().isPresent()
                            ? item.getMonthlyUsageTime().get() : 0;// chua xac dinh dc dang qa: TODO

                    Double e24 = checkShowAreaAnnualBreak1(
                            dataSource.getHolidaysRemainingManagement()) ?
                            (double) value24 : null;
                    val vl24 = e24 != null && e24 != 0 ? convertToTime((int) (e24 * 60)) : "";
                    cells.get(firstRow + 2, 10 + totalMonth).setValue(vl24);
                }

                // Update KDR 001 : 値＝(クリア)/背景色＝グレー;
                setBackgroundGray(cells.get(firstRow + 2, 11 + totalMonth));
                // Update KDR 001 : 値＝(クリア)/背景色＝グレー;
                setBackgroundGray(cells.get(firstRow + 3, 11 + totalMonth));
            }
        }
        firstRow += totalAddRows;
        for (int index = 0; index < NUMBER_COLUMN - 2; index++) {
            setBottomBorderStyle(cells.get(firstRow - 1, 2 + index));
        }
        return firstRow;
    }
    // NOT UPDATE IN VER 15
    private int printYearlyReserved(Cells cells, int firstRow, HolidaysRemainingEmployee employee,
                                    HolidayRemainingDataSource dataSource) throws Exception {
        NumberFormat df = new DecimalFormat("#0.0");
        if (!checkYearlyLeave(dataSource.getHolidaysRemainingManagement(), employee.getEmployeeCode(),
                dataSource.getVariousVacationControl())) {
            return firstRow;
        }
        for (int index = 0; index < NUMBER_COLUMN - 2; index++) {
            setTopBorderStyle(cells.get(firstRow, 2 + index));
        }
        cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 10, firstRow, 2);
        // H1_1
        cells.get(firstRow, 2).setValue(TextResource.localize("KDR001_76"));
        // H2_1
        cells.get(firstRow, 9).setValue(TextResource.localize("KDR001_14"));
        // H2_2
        cells.get(firstRow + 1, 9).setValue(TextResource.localize("KDR001_15"));
        val hdRemainingInfor = dataSource.getMapEmployees().get(employee.getEmployeeId()).getHolidayRemainingInfor();
        if (hdRemainingInfor == null) {
            return firstRow + 2;
        }
        // Set data for 積立年休 (H)
        // Result RequestList268
        val reserveHolidayImported = hdRemainingInfor.getReserveHoliday();
        if (reserveHolidayImported != null) {
            // H1_2
            val h12 = df.format(reserveHolidayImported.getGrantNumber());
            cells.get(firstRow, 4).setValue(h12);
            // H1_3
            cells.get(firstRow, 5).setValue(df.format(reserveHolidayImported.getStartMonthRemain()));
            if (reserveHolidayImported.getStartMonthRemain() < 0) {
                setForegroundRed(cells.get(firstRow, 5));
            }
            // H1_4
            cells.get(firstRow, 6).setValue(df.format(reserveHolidayImported.getUsedNumber()));
            // H1_5
            cells.get(firstRow, 7).setValue(df.format(reserveHolidayImported.getRemainNumber()));
            if (reserveHolidayImported.getRemainNumber() < 0) {
                setForegroundRed(cells.get(firstRow, 7));
            }
            // H1_6
            cells.get(firstRow, 8).setValue(df.format(reserveHolidayImported.getUndigestNumber()));

            if (reserveHolidayImported.getUndigestNumber() != null
                    && reserveHolidayImported.getUndigestNumber() > 0) {
                setForegroundRed(cells.get(firstRow, 8));
            }

        }
        if (!employee.getCurrentMonth().isPresent()) {
            return firstRow + 2;
        }
        YearMonth currentMonth = employee.getCurrentMonth().get();
        // Result RequestList258
        val reservedYearHolidayImportedList = hdRemainingInfor.getListReservedYearHoliday();
        // Result RequestList364
        val rsvLeaUsedCurrentMonImported = hdRemainingInfor.getListRsvLeaUsedCurrentMon();

        int maxRange = totalMonths(dataSource.getStartMonth().yearMonth(), dataSource.getEndMonth().yearMonth());

        if (reservedYearHolidayImportedList != null) {
            for (ReservedYearHolidayImported reservedYearHolidayItem : reservedYearHolidayImportedList) {
                // Before this month
                if (currentMonth.compareTo(reservedYearHolidayItem.getYearMonth()) <= 0) {
                    continue;
                }
                int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(),
                        reservedYearHolidayItem.getYearMonth());
                if (maxRange < totalMonth || totalMonth < 0) {
                    continue;
                }
                // H2_3 当月より前, set lại giá trị cho cột này nếu bằng 0 thì không hiển thị ra
                cells.get(firstRow, 10 + totalMonth)
                        .setValue(reservedYearHolidayItem.getUsedDays() != null
                                && reservedYearHolidayItem.getUsedDays() == 0 ? null : df.format(reservedYearHolidayItem.getUsedDays()));
                // H2_4 当月より前
                cells.get(firstRow + 1, 10 + totalMonth).setValue(df.format(reservedYearHolidayItem.getRemainingDays()));
                if (reservedYearHolidayItem.getRemainingDays() < 0) {
                    setForegroundRed(cells.get(firstRow + 1, 10 + totalMonth));
                }
            }
        }

        if (rsvLeaUsedCurrentMonImported != null) {
            for (RsvLeaUsedCurrentMonImported rsvLeaUsedCurrentMonItem : rsvLeaUsedCurrentMonImported) {
                // After this month
                if (currentMonth.compareTo(rsvLeaUsedCurrentMonItem.getYearMonth()) > 0) {
                    continue;
                }
                int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(),
                        rsvLeaUsedCurrentMonItem.getYearMonth());
                if (maxRange < totalMonth || totalMonth < 0) {
                    continue;
                }
                // H2_3 当月以降, set lại giá trị cho cột này nếu bằng 0 thì không hiển thị ra
                cells.get(firstRow, 10 + totalMonth).setValue(rsvLeaUsedCurrentMonItem.getUsedNumber() != null
                        && rsvLeaUsedCurrentMonItem.getUsedNumber() == 0 ? null : df.format(rsvLeaUsedCurrentMonItem.getUsedNumber()));
                if (currentMonth.compareTo(rsvLeaUsedCurrentMonItem.getYearMonth()) != 0) {
                    continue;
                }
                // H2_4 当月以降
                cells.get(firstRow + 1, 10 + totalMonth).setValue(df.format(rsvLeaUsedCurrentMonItem.getRemainNumber()));
                if (rsvLeaUsedCurrentMonItem.getRemainNumber() < 0) {
                    setForegroundRed(cells.get(firstRow + 1, 10 + totalMonth));
                }
            }
        }
        firstRow += 2;
        for (int index = 0; index < NUMBER_COLUMN - 2; index++) {
            setBottomBorderStyle(cells.get(firstRow - 1, 2 + index));
        }
        return firstRow;
    }

    private int printSubstituteHoliday(Cells cells, int firstRow, HolidaysRemainingEmployee employee,
                                       HolidayRemainingDataSource dataSource) throws Exception {
        // 代休
        if (!checkTakeABreak_01(dataSource.getHolidaysRemainingManagement(), employee.getEmploymentCode())) {
            return firstRow;
        }
        val hdRemainingInfor = dataSource.getMapEmployees().get(employee.getEmployeeId()).getHolidayRemainingInfor();
        val check = dataSource.getVariousVacationControl();
        int totalRows = 2;
        int rowIndexRepresentSubstitute = 0;
        int rowIndexIsRemainingChargeSubstitute = 0;
        cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 12, firstRow, 2);
        // I1_1
        cells.get(firstRow, 2).setValue(TextResource.localize("KDR001_77"));
        // I2_1
        cells.get(firstRow, 9).setValue(TextResource.localize("KDR001_16"));
        // I3_1
        cells.get(firstRow + 1, 9).setValue(TextResource.localize("KDR001_17"));
        if (hdRemainingInfor == null) {
            return firstRow + 2;
        }
        if (checkTakeABreak_02(dataSource.getHolidaysRemainingManagement())) {
            rowIndexRepresentSubstitute = firstRow + totalRows;
            cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 14, rowIndexRepresentSubstitute, 1);
            // I4_1
            if (checkTakeABreak_02(dataSource.getHolidaysRemainingManagement()))
                cells.get(rowIndexRepresentSubstitute, 9).setValue(TextResource.localize("KDR001_11"));
            totalRows += 1;

        }
        if (checkTakeABreak_03(dataSource.getHolidaysRemainingManagement())) {
            rowIndexIsRemainingChargeSubstitute = firstRow + totalRows;
            cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 15, rowIndexIsRemainingChargeSubstitute, 1);
            // I5_1
            cells.get(rowIndexIsRemainingChargeSubstitute, 9).setValue(TextResource.localize("KDR001_18"));
            totalRows += 1;
        }

        if (!employee.getCurrentMonth().isPresent()) {
            return firstRow + totalRows;
        }
        YearMonth currentMonth = employee.getCurrentMonth().get();
        // Set value for I
        // Result RequestList259
        val statusHolidayImported = hdRemainingInfor.getListStatusHoliday();
        // RequestList 203
        val substituteHolidayAggrResult = hdRemainingInfor.getSubstituteHolidayAggrResult();
        int maxRange = totalMonths(dataSource.getStartMonth().yearMonth(), dataSource.getEndMonth().yearMonth());
        if (statusHolidayImported != null) {
            for (StatusHolidayImported statusHolidayItem : statusHolidayImported) {
                int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(), statusHolidayItem.getYm());
                // Curren month
                if (statusHolidayItem.getYm().compareTo(currentMonth) == 0) {
                    //I2_3
                    String occurrence = "";
                    if (check.isSubstituteHolidaySetting()) {
                        occurrence = statusHolidayItem.getOccurrenceDays() == null ? "" : statusHolidayItem.getOccurrenceDays().toString();
                    } else if (check.isSubstituteHolidaySetting() && check.isHourlyLeaveSetting()) {
                        occurrence = (statusHolidayItem.getOccurrenceTimes() == null ? "" : statusHolidayItem.getOccurrenceTimes().toString());
                    }

                    cells.get(firstRow, 10 + totalMonth).setValue(occurrence);
                    // I3_3
                    String use = "";
                    if (check.isSubstituteHolidaySetting()) {
                        use = statusHolidayItem.getUseDays() == null ? "" : statusHolidayItem.getUseDays().toString();
                    }
                    if (check.isSubstituteHolidaySetting() && check.isHourlyLeaveSetting()) {
                        use = statusHolidayItem.getUseTimes() == null ? "" : statusHolidayItem.getUseDays().toString();
                    }
                    cells.get(firstRow + 1, 10 + totalMonth)
                            .setValue(use);
                    if (checkTakeABreak_02(dataSource.getHolidaysRemainingManagement())) {
                        // I4_3 代休_未消化_日数, set lại giá trị cho cột này nếu bằng 0 thì không hiển thị ra
                        String unUsed = "";
                        if (check.isSubstituteHolidaySetting()) {
                            unUsed = statusHolidayItem.getUnUsedDays() == null ? "" : statusHolidayItem.getUnUsedDays().toString();
                        } else if (check.isSubstituteHolidaySetting() && check.isHourlyLeaveSetting()) {
                            unUsed = (statusHolidayItem.getUnUsedTimes() == null ? "" : statusHolidayItem.getUnUsedTimes().toString());
                        }
                        cells.get(firstRow + 2, 10 + totalMonth)
                                .setValue(unUsed);
                    }
                }

                // BEFORE
                else if (statusHolidayItem.getYm().compareTo(currentMonth) < 0) {
                    if (maxRange >= totalMonth && totalMonth >= 0) {
                        // I2_3
                        String occurrence = "";
                        if (check.isSubstituteHolidaySetting()) {
                            occurrence = statusHolidayItem.getOccurrenceDays() == null ? "0" :
                                    statusHolidayItem.getOccurrenceDays().toString();
                        } else if (check.isSubstituteHolidaySetting() && check.isHourlyLeaveSetting()) {
                            occurrence = (statusHolidayItem.getOccurrenceTimes() == null ? "0" :
                                    statusHolidayItem.getOccurrenceTimes().toString());
                        }

                        cells.get(firstRow, 10 + totalMonth).setValue(occurrence);
                        // I3_3
                        String use = "";

                        if (check.isSubstituteHolidaySetting()) {
                            use = statusHolidayItem.getUseDays() == null ? "0" : statusHolidayItem.getUseDays().toString();
                        } else if (check.isHourlyLeaveSetting() && check.isHourlyLeaveSetting()) {
                            use = (statusHolidayItem.getUseTimes() == null ? "0" : statusHolidayItem.getUseTimes().toString());
                        }

                        cells.get(firstRow + 1, 10 + totalMonth)
                                .setValue(use);
                    }
                    if (checkTakeABreak_02(dataSource.getHolidaysRemainingManagement())) {
                        // I4_3
                        String unUsed = "";

                        if (check.isSubstituteHolidaySetting()) {
                            unUsed = statusHolidayItem.getUnUsedDays() == null ? "" : statusHolidayItem.getUnUsedDays().toString();
                        } else if (check.isSubstituteHolidaySetting() && check.isHourlyLeaveSetting()) {
                            unUsed = (statusHolidayItem.getUnUsedTimes() == null ? "" : statusHolidayItem.getUnUsedTimes().toString());
                        }
                        cells.get(firstRow + 2, 10 + totalMonth)
                                .setValue(unUsed);
                        if (statusHolidayItem.getUnUsedDays() != null && statusHolidayItem.getUnUsedDays() > 0) {
                            setForegroundRed(cells.get(rowIndexRepresentSubstitute, 10 + totalMonth));
                        }
                    }
                    if (checkTakeABreak_03(dataSource.getHolidaysRemainingManagement())) {
                        // I5_3 代休_残数_日数
                        String remain = "";

                        if (check.isSubstituteHolidaySetting()) {
                            remain = statusHolidayItem.getRemainDays() == null ? "0" : statusHolidayItem.getRemainDays().toString();
                        } else if (check.isSubstituteHolidaySetting() && check.isHourlyLeaveSetting()) {
                            remain = (statusHolidayItem.getRemainTimes() == null ? "0" : statusHolidayItem.getRemainTimes().toString());
                        }
                        cells.get(firstRow + 3, 10 + totalMonth)
                                .setValue(remain);
                    }
                } else if (statusHolidayItem.getYm().compareTo(currentMonth) > 0) {
                    if (maxRange >= totalMonth && totalMonth >= 0) {
                        // I2_3
                        String occurrence = "";
                        if (check.isSubstituteHolidaySetting()) {
                            occurrence = statusHolidayItem.getOccurrenceDays() == null ? "" : statusHolidayItem.getOccurrenceDays().toString();
                        } else if (check.isSubstituteHolidaySetting() && check.isHourlyLeaveSetting()) {
                            occurrence = (statusHolidayItem.getOccurrenceTimes() == null ? "" : statusHolidayItem.getOccurrenceTimes().toString());
                        }
                        cells.get(firstRow, 10 + totalMonth).setValue(occurrence);
                        // I3_3 代休_使用_日数時間数 実績値, set lại giá trị cho cột này nếu bằng 0 thì không hiển thị ra
                        String use = "";
                        if (check.isSubstituteHolidaySetting()) {
                            use = statusHolidayItem.getUseDays() == null ? "" : statusHolidayItem.getUseDays().toString();
                        }
                        if (check.isSubstituteHolidaySetting() && check.isHourlyLeaveSetting()) {
                            use = statusHolidayItem.getUseTimes() == null ? "" : statusHolidayItem.getUseDays().toString();
                        }
                        cells.get(firstRow + 1, 10 + totalMonth)
                                .setValue(use);
                    }
                }

            }
        }
        // 月初残数
        // 日数
        val monRemainingDate = substituteHolidayAggrResult.getCarryoverDay().v();
        // 時間
        val monRemainingTime = substituteHolidayAggrResult.getCarryoverTime().v();
        String i12 = "";
        if (check.isSubstituteHolidaySetting() && monRemainingDate != null) {
            i12 = monRemainingDate.toString();
        } else if (check.isSubstituteHolidaySetting() && check.isHourlyLeaveSetting() && monRemainingTime != null) {
            i12 = monRemainingTime.toString();
        }
        cells.get(firstRow, 5).setValue(i12);
        //I1_3 使用数
        // 日数
        val dateUse = substituteHolidayAggrResult.getDayUse().v();
        // 時間
        val timeUse = substituteHolidayAggrResult.getTimeUse().v();
        // 代休+時間代休の場合
        String i13 = "";
        if (check.isSubstituteHolidaySetting() && dateUse != null) {
            i13 = dateUse.toString();
        } else if (check.isSubstituteHolidaySetting() && check.isHourlyLeaveSetting() && timeUse != null) {
            i13 = monRemainingTime.toString();
        }
        cells.get(firstRow, 6).setValue(i13);
        // I1_4 残数
        // 日数
        val remainDay = substituteHolidayAggrResult.getRemainDay().v();
        //時間
        val remainTime = substituteHolidayAggrResult.getRemainTime().v();
        // 代休+時間代休の場合
        String i14 = "";
        if (check.isSubstituteHolidaySetting() && remainDay != null) {
            i14 = dateUse.toString();
        } else if (check.isSubstituteHolidaySetting() && check.isHourlyLeaveSetting() && remainTime != null) {
            i14 = monRemainingTime.toString();
        }
        cells.get(firstRow, 7).setValue(i14);
        // I1_5: 未消化数
        //日数
        val unusedDay = substituteHolidayAggrResult.getUnusedDay().v();
        //時間
        val unusedTime = substituteHolidayAggrResult.getUnusedTime().v();
        //代休+時間代休の場合
        String i15 = "";
        if (check.isSubstituteHolidaySetting() && unusedDay != null) {
            i15 = dateUse.toString();
        } else if (check.isSubstituteHolidaySetting() && check.isHourlyLeaveSetting() && unusedTime != null) {
            i15 = monRemainingTime.toString();
        }
        cells.get(firstRow, 8).setValue(i15);
        firstRow += totalRows;
        for (int index = 0; index < NUMBER_COLUMN - 2; index++) {
            setBottomBorderStyle(cells.get(firstRow - 1, 2 + index));
        }
        return firstRow;
    }
    private int printPauseHoliday(Cells cells, int firstRow, HolidaysRemainingEmployee employee,
                                  HolidayRemainingDataSource dataSource) throws Exception {
        // 振休
        if (!dataSource.getVariousVacationControl().isPauseItemHolidaySetting()) {
            return firstRow;
        }
        val holiday = dataSource.getHolidaysRemainingManagement().getListItemsOutput().getPause();
        boolean isPauseItem = holiday.isPauseItem();
        boolean isUndigestedPause = holiday.isUndigestedPause();
        boolean isNumberRemainingPause = holiday.isNumberRemainingPause();

        if (!isPauseItem) {
            return firstRow;
        }
        int totalRows = 2;
        int rowIndexUndigestedPause = 0;
        int rowIndexNumberRemainingPause = 0;
        cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 16, firstRow, 2);
        // J1_1
        cells.get(firstRow, 2).setValue(TextResource.localize("KDR001_78"));
        // J2_1
        cells.get(firstRow, 9).setValue(TextResource.localize("KDR001_16"));
        // J2_2
        cells.get(firstRow + 1, 9).setValue(TextResource.localize("KDR001_17"));
        val hdRemainingInfor = dataSource.getMapEmployees().get(employee.getEmployeeId()).getHolidayRemainingInfor();
        if (hdRemainingInfor == null) {
            return firstRow + 2;
        }
        if (isUndigestedPause) {
            rowIndexUndigestedPause = firstRow + totalRows;
            cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 18, rowIndexUndigestedPause, 1);
            // J2_3
            cells.get(rowIndexUndigestedPause, 9).setValue(TextResource.localize("KDR001_11"));
            totalRows += 1;
        }
        if (isNumberRemainingPause) {
            rowIndexNumberRemainingPause = firstRow + totalRows;
            cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 19, rowIndexNumberRemainingPause, 1);
            // J2_4
            cells.get(rowIndexNumberRemainingPause, 9).setValue(TextResource.localize("KDR001_18"));
            totalRows += 1;
        }

        if (!employee.getCurrentMonth().isPresent()) {

            return firstRow + totalRows;
        }
        YearMonth currentMonth = employee.getCurrentMonth().get();
        // Set value for 振休 J
        // Result RequestList270
        val currentHolidayList = hdRemainingInfor.getListCurrentHolidayRemain();
        // Result RequestList260
        val StatusOfHolidayList = hdRemainingInfor.getListStatusOfHoliday();
        //  Result RequestList204
        val compenLeaveAggrResult = hdRemainingInfor.getCompenLeaveAggrResult();
        int maxRange = totalMonths(dataSource.getStartMonth().yearMonth(), dataSource.getEndMonth().yearMonth());
        if (StatusOfHolidayList != null) {
            for (StatusOfHolidayImported statusOfHDItem : StatusOfHolidayList) {
                // Before this month
                if (currentMonth.compareTo(statusOfHDItem.getYm()) <= 0) {
                    continue;
                }
                int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(), statusOfHDItem.getYm());
                if (maxRange < totalMonth || totalMonth < 0) {
                    continue;
                }
                // J2_5 振休_発生, set lại giá trị cho cột này nếu bằng 0 thì không hiển thị ra
                cells.get(firstRow, 10 + totalMonth).setValue(statusOfHDItem.getOccurredDay() != null && statusOfHDItem.getOccurredDay() == 0 ? null : statusOfHDItem.getOccurredDay());
                // J2_6 振休_使用, set lại giá trị cho cột này nếu bằng 0 thì không hiển thị ra
                cells.get(firstRow + 1, 10 + totalMonth).setValue(statusOfHDItem.getUsedDays() != null && statusOfHDItem.getUsedDays() == 0 ? null : statusOfHDItem.getUsedDays());
                if (isUndigestedPause) {
                    // J2_7 振休_未消化, set lại giá trị cho cột này nếu bằng 0 thì không hiển thị ra
                    cells.get(rowIndexUndigestedPause, 10 + totalMonth).setValue(statusOfHDItem.getUnUsedDays() != null && statusOfHDItem.getUnUsedDays() == 0 ? null : statusOfHDItem.getUnUsedDays());
                    if (statusOfHDItem.getUnUsedDays() != null && statusOfHDItem.getUnUsedDays() > 0) {
                        setForegroundRed(cells.get(rowIndexUndigestedPause, 10 + totalMonth));
                    }
                }
                if (isNumberRemainingPause) {
                    // J2_8 振休_残数
                    cells.get(rowIndexNumberRemainingPause, 10 + totalMonth)
                            .setValue(statusOfHDItem.getRemainingDays());
                    if (statusOfHDItem.getRemainingDays() < 0) {
                        setForegroundRed(cells.get(rowIndexNumberRemainingPause, 10 + totalMonth));
                    }
                }
            }
        }

        if (currentHolidayList != null) {
            for (CurrentHolidayRemainImported holidayRemainItem : currentHolidayList) {
                if (currentMonth.compareTo(holidayRemainItem.getYm()) <= 0) {
                    int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(), holidayRemainItem.getYm());
                    if (maxRange >= totalMonth && totalMonth >= 0) {
                        // J2_5 振休_発生, set lại giá trị cho cột này nếu bằng 0 thì không hiển thị ra
                        cells.get(firstRow, 10 + totalMonth).setValue(holidayRemainItem.getMonthOccurrence() != null
                                && holidayRemainItem.getMonthOccurrence() == 0 ? null : holidayRemainItem.getMonthOccurrence());
                        // J2_6 振休_使用, set lại giá trị cho cột này nếu bằng 0 thì không hiển thị ra
                        cells.get(firstRow + 1, 10 + totalMonth).setValue(holidayRemainItem.getMonthUse() != null
                                && holidayRemainItem.getMonthUse() == 0 ? null : holidayRemainItem.getMonthUse());
                        if (currentMonth.compareTo(holidayRemainItem.getYm()) == 0) {
                            if (isUndigestedPause) {
                                // J2_7 振休_未消化, set lại giá trị cho cột này nếu bằng 0 thì không hiển thị ra
                                cells.get(rowIndexUndigestedPause, 10 + totalMonth).setValue(holidayRemainItem.getMonthExtinction() != null
                                        && holidayRemainItem.getMonthExtinction() == 0 ? null : holidayRemainItem.getMonthExtinction());
                                if (holidayRemainItem.getMonthExtinction() != null
                                        && holidayRemainItem.getMonthExtinction() > 0) {
                                    setForegroundRed(cells.get(rowIndexUndigestedPause, 10 + totalMonth));
                                }
                            }
                            if (isNumberRemainingPause) {
                                // J2_8 振休_残数
                                cells.get(rowIndexNumberRemainingPause, 10 + totalMonth)
                                        .setValue(holidayRemainItem.getMonthEndRemain());
                                if (holidayRemainItem.getMonthEndRemain() < 0) {
                                    setForegroundRed(cells.get(rowIndexNumberRemainingPause, 10 + totalMonth));
                                }
                            }
                        }
                    }
                }

            }
        }

        val currentHolidayRemainLeft = hdRemainingInfor.getCurrentHolidayRemainLeft();
        // Current month
        if (currentHolidayRemainLeft == null) {
            for (int index = 0; index < NUMBER_COLUMN - 2; index++) {
                setBottomBorderStyle(cells.get(firstRow - 1, 2 + index));
                setBottomBorderStyle(cells.get(firstRow + totalRows - 1, 2 + index));
            }
            return firstRow + totalRows;
        }
        // J1_2 振休_月初残
        cells.get(firstRow, 5).setValue(currentHolidayRemainLeft.getMonthStartRemain());
        if (currentHolidayRemainLeft.getMonthStartRemain() < 0) {
            setForegroundRed(cells.get(firstRow, 5));
        }
        // J1_3 振休_使用数
        cells.get(firstRow, 6).setValue(currentHolidayRemainLeft.getMonthUse());
        if (isPauseItem) {
            // J1_4 振休_残数
            cells.get(firstRow, 7).setValue(currentHolidayRemainLeft.getMonthEndRemain());
            if (currentHolidayRemainLeft.getMonthEndRemain() < 0) {
                setForegroundRed(cells.get(firstRow, 7));
            }
        }
        if (isUndigestedPause) {
            // J1_5 振休_未消化
            cells.get(firstRow, 8).setValue(currentHolidayRemainLeft.getMonthExtinction());
            if (currentHolidayRemainLeft.getMonthExtinction() != null
                    && currentHolidayRemainLeft.getMonthExtinction() > 0) {
                setForegroundRed(cells.get(firstRow, 8));
            }
        }
        // Set background
        for (int i = 0; i <= totalMonths(dataSource.getStartMonth().yearMonth(),
                dataSource.getEndMonth().yearMonth()); i++) {
            if (dataSource.getStartMonth().addMonths(i).yearMonth().compareTo(currentMonth) > 0) {
                if (isUndigestedPause) {
                    setBackgroundGray(cells.get(rowIndexUndigestedPause, 10 + i));
                }
                if (isNumberRemainingPause) {
                    setBackgroundGray(cells.get(rowIndexNumberRemainingPause, 10 + i));
                }
            }
            if (!dataSource.isSameCurrentMonth()
                    && dataSource.getStartMonth().addMonths(i).yearMonth().compareTo(currentMonth) == 0) {
                setCurrentMonthBackground(cells.get(firstRow, 10 + i));
                setCurrentMonthBackground(cells.get(firstRow + 1, 10 + i));
                if (isUndigestedPause) {
                    setCurrentMonthBackground(cells.get(rowIndexUndigestedPause, 10 + i));
                }
                if (isNumberRemainingPause) {
                    setCurrentMonthBackground(cells.get(rowIndexNumberRemainingPause, 10 + i));
                }
            }
        }
        for (int index = 0; index < NUMBER_COLUMN - 2; index++) {
            setBottomBorderStyle(cells.get(firstRow - 1, 2 + index));
            setBottomBorderStyle(cells.get(firstRow + totalRows - 1, 2 + index));
        }
        return firstRow + totalRows;
    }

    private int print60hOversho(Cells cells, int firstRow, HolidaysRemainingEmployee employee,
                                HolidayRemainingDataSource dataSource) throws Exception {
        // 振休
        if (!dataSource.getVariousVacationControl().isPauseItemHolidaySetting()) {
            return firstRow;
        }
        val holiday = dataSource.getHolidaysRemainingManagement().getListItemsOutput().getPause();
        boolean isPauseItem = holiday.isPauseItem();
        boolean isUndigestedPause = holiday.isUndigestedPause();
        boolean isNumberRemainingPause = holiday.isNumberRemainingPause();

        if (!isPauseItem) {
            return firstRow;
        }
        int totalRows = 2;
        int rowIndexUndigestedPause = 0;
        int rowIndexNumberRemainingPause = 0;
        cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 20, firstRow, 2);
        // K1_1
        cells.get(firstRow, 2).setValue(TextResource.localize("KDR001_78"));
        // K2_1
        cells.get(firstRow, 9).setValue(TextResource.localize("KDR001_17"));
        // K2_2
        cells.get(firstRow + 1, 9).setValue(TextResource.localize("KDR001_17"));
        val hdRemainingInfor = dataSource.getMapEmployees().get(employee.getEmployeeId()).getHolidayRemainingInfor();
        if (hdRemainingInfor == null) {
            return firstRow + 2;
        }
        if (isUndigestedPause) {
            rowIndexUndigestedPause = firstRow + totalRows;
            cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 22, rowIndexUndigestedPause, 1);
            // K2_3
            cells.get(rowIndexUndigestedPause, 9).setValue(TextResource.localize("KDR001_11"));
            totalRows += 1;
        }
        if (isNumberRemainingPause) {
            rowIndexNumberRemainingPause = firstRow + totalRows;
            cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 23, rowIndexNumberRemainingPause, 1);
            // K2_4
            cells.get(rowIndexNumberRemainingPause, 9).setValue(TextResource.localize("KDR001_18"));
            totalRows += 1;
        }
        if (!employee.getCurrentMonth().isPresent()) {
            return firstRow + totalRows;
        }
        YearMonth currentMonth = employee.getCurrentMonth().get();
        // Set background
        for (int i = 0; i <= totalMonths(dataSource.getStartMonth().yearMonth(),
                dataSource.getEndMonth().yearMonth()); i++) {
            if (dataSource.getStartMonth().addMonths(i).yearMonth().compareTo(currentMonth) > 0) {
                if (isUndigestedPause) {
                    setBackgroundGray(cells.get(rowIndexUndigestedPause, 10 + i));
                }
                if (isNumberRemainingPause) {
                    setBackgroundGray(cells.get(rowIndexNumberRemainingPause, 10 + i));
                }
            }
            if (!dataSource.isSameCurrentMonth()
                    && dataSource.getStartMonth().addMonths(i).yearMonth().compareTo(currentMonth) == 0) {
                setCurrentMonthBackground(cells.get(firstRow, 10 + i));
                setCurrentMonthBackground(cells.get(firstRow + 1, 10 + i));
                if (isUndigestedPause) {
                    setCurrentMonthBackground(cells.get(rowIndexUndigestedPause, 10 + i));
                }
                if (isNumberRemainingPause) {
                    setCurrentMonthBackground(cells.get(rowIndexNumberRemainingPause, 10 + i));
                }
            }
        }
        for (int index = 0; index < NUMBER_COLUMN - 2; index++) {
            setTopBorderStyle(cells.get(firstRow, 2 + index));
            setBottomBorderStyle(cells.get(firstRow + totalRows - 1, 2 + index));
        }
        return firstRow + totalRows;
    }
    //F
    private int printLimitForHalfHolidays(Cells cells, int firstRow, HolidaysRemainingEmployee employee,
                                          HolidayRemainingDataSource dataSource) throws Exception {
        if (!checkLimitHoliday(dataSource.getHolidaysRemainingManagement())
                || !employee.getCurrentMonth().isPresent()) {
            return firstRow;
        }
        val hdRemainingInfor = dataSource.getMapEmployees().get(employee.getEmployeeId()).getHolidayRemainingInfor();
        val data363 = hdRemainingInfor.getListAnnLeaveUsageStatusOfThisMonth();
        val rs363New = hdRemainingInfor.getRs363New();
        val listItemGrant = rs363New.stream().flatMap(e -> e.getAggrResultOfAnnualLeave()
                .getAsOfGrant().stream()).collect(Collectors.toList());

        cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 7, firstRow, 2);
        //        // Set Style
        for (int index = 0; index < NUMBER_COLUMN - 2; index++) {
            setTopBorderStyle(cells.get(firstRow, index + 2));
        }
        val dataOptional = annLeaMaxDataRepository.get(employee.getEmployeeId());
        YearMonth currentMonth = employee.getCurrentMonth().get();
        // F1_1
        cells.get(firstRow, 2).setValue(TextResource.localize("KDR001_49"));
        if (dataOptional.isPresent()) {
            val data = dataOptional.get();
            // F1_2 - OK
            Integer maxTimes = null;
            val numberOfGrantesOpt = data.getHalfdayAnnualLeaveMax();
            if (numberOfGrantesOpt.isPresent())
                maxTimes = numberOfGrantesOpt.get().getMaxTimes().v();
            String textMaxtime = "";
            if (maxTimes != null) {
                textMaxtime = maxTimes.toString() + TextResource.localize("KDR001_75");
            }
            cells.get(firstRow, 4).setValue(textMaxtime);
            // F1_3 OK
            // 半日年休_月初残回数
            //　半日年休現在状況.月初残回数←半日年休上限．使用回数
            Integer numOfMonRemaining = null;
            Integer usedTimes = null;
            Integer remainingTimes = null;
            if (numberOfGrantesOpt.isPresent()) {
                String valueF13 = "";
                //残回数
                remainingTimes = numberOfGrantesOpt.get().getRemainingTimes().v();
                valueF13 = remainingTimes.toString()
                        + TextResource.localize("KDR001_75");
                cells.get(firstRow, 5)
                        .setValue(valueF13);
            }
            //F14-F15 DATA IN 363
            String valueF14 = "";
            String valueF15 = "";
            if (data363 != null && !data363.isEmpty()) {
                val number_of_use = data363.stream()
                        .mapToDouble(AnnLeaveUsageStatusOfThisMonthImported::getMonthlyUsageDays)
                        .sum();
                val uses_after_granting = listItemGrant.stream().mapToDouble(e -> e.getUsedDays().v()).sum();
                valueF14 = number_of_use + uses_after_granting + TextResource.localize("KDR001_75");
                val number_of_date_remain = data363.stream()
                        .mapToDouble(AnnLeaveUsageStatusOfThisMonthImported::getMonthlyRemainingDays).sum();
                valueF15 = number_of_date_remain + TextResource.localize("KDR001_75");
                // F1_4:
                cells.get(firstRow, 6).setValue(valueF14);

                cells.get(firstRow, 7).setValue(valueF15);
            }
        }

        setBackgroundGray(cells.get(firstRow, 8));
        // F2_1
        cells.get(firstRow, 9).setValue(TextResource.localize("KDR001_14"));
        // F2_1
        cells.get(firstRow + 1, 9).setValue(TextResource.localize("KDR001_15"));
        // Result RequestList255
        val listAnnLeaveUsage = hdRemainingInfor.getListAnnualLeaveUsage();
        int maxRange = totalMonths(dataSource.getStartMonth().yearMonth(), dataSource.getEndMonth().yearMonth());
        if (listAnnLeaveUsage != null && dataSource.getMapEmployees().get(employee.getEmployeeId()).getHolidayRemainingInfor().getGrantDate().isPresent()) {

            for (AnnualLeaveUsageImported item : listAnnLeaveUsage) {
                if (currentMonth.compareTo(item.getYearMonth()) <= 0) {
                    continue;
                }
                int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(), item.getYearMonth());
                if (maxRange < totalMonth || totalMonth < 0) {
                    continue;
                }
                // F2_3 月度使用回数, set lại giá trị cho cột này nếu bằng 0 thì không hiển thị ra
                val use_time = item.getUsedTime() != null && item.getUsedTime()
                        != 0 ? item.getUsedDays() : null;
                if (use_time != null) {
                    cells.get(firstRow, 10 + totalMonth)
                            .setValue(use_time.toString() + TextResource.localize("KDR001_75"));
                    if (use_time < 0) {
                        setForegroundRed(cells.get(firstRow + 2, 10 + totalMonth));
                    }
                }
                val remain_time = item.getRemainingTime() != null && item.getRemainingTime() != 0 ?
                        item.getRemainingTime() : null;
                // F2_4:月度残回数 Update KDR ver 15
                if (remain_time != null) {
                    cells.get(firstRow + 1, 10 + totalMonth)
                            .setValue(remain_time.toString() + TextResource.localize("KDR001_75"));
                    if (remain_time < 0) {
                        setForegroundRed(cells.get(firstRow + 3, 10 + totalMonth));
                    }
                }

            }
        }
        Optional<GeneralDate> grantDate = dataSource.getMapEmployees().get(employee.getEmployeeId())
                .getHolidayRemainingInfor().getGrantDate();
        // Result RequestList363
        val listAnnLeaveUsageOfThisMonth = hdRemainingInfor.getListAnnLeaveUsageStatusOfThisMonth();
        if (listAnnLeaveUsageOfThisMonth != null && grantDate.isPresent()) {
            for (AnnLeaveUsageStatusOfThisMonthImported item : listAnnLeaveUsageOfThisMonth) {
                if (currentMonth.compareTo(item.getYearMonth()) > 0) {
                    continue;
                }
                int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(), item.getYearMonth());
                if (maxRange < totalMonth || totalMonth < 0) {
                    continue;
                }
                val use_day = item.getMonthlyUsageDays()
                        != null && item.getMonthlyUsageDays() != 0 ? item.getMonthlyUsageDays() : null;
                if (use_day != null) {
                    cells.get(firstRow, 10 + totalMonth)
                            .setValue(use_day.toString() + TextResource.localize("KDR001_75"));
                }
                if (item.getYearMonth().compareTo(currentMonth) > 0) {
                    // F2_3 月度使用回数, set lại giá trị cho cột này nếu bằng 0 thì không hiển thị ra
                    val monthlyUsageTime = item.getMonthlyUsageTime().isPresent()
                            ? item.getMonthlyUsageTime().get() : null;
                    //月度使用回数
                    if (monthlyUsageTime != null) {
                        cells.get(firstRow, 10 + totalMonth)
                                .setValue(monthlyUsageTime.toString() + TextResource.localize("KDR001_75"));
                    }
                    //F2_4 BLANK
                    setBackgroundGray(cells.get(firstRow + 1, 10 + totalMonth));
                }

                if (item.getYearMonth().equals(currentMonth)) {
                    val number_of_use = listAnnLeaveUsage.stream()
                            .filter(e -> e.getYearMonth().compareTo(item.getYearMonth()) == 0)
                            .mapToDouble(AnnualLeaveUsageImported::getUsedDays).sum();
                    Double monthlyUsageTime = item.getMonthlyUsageDays() + number_of_use;

                    //月度使用回数
                    //F2_3
                    cells.get(firstRow, 10 + totalMonth)
                            .setValue(monthlyUsageTime.toString() + TextResource.localize("KDR001_75"));

                    val remain = item.getMonthlyRemainingDays();
                    if (remain != null) {
                        //F_4
                        cells.get(firstRow + 1, 10 + totalMonth)
                                .setValue(remain.toString() + TextResource.localize("KDR001_75"));
                    }
                }
            }
        }
        firstRow += 2;
        for (int index = 0; index < NUMBER_COLUMN - 2; index++) {
            setBottomBorderStyle(cells.get(firstRow - 1, 2 + index));
        }
        return firstRow;
    }

    //G
    private int printLimitUsageHolidays(Cells cells, int firstRow, HolidaysRemainingEmployee employee,
                                        HolidayRemainingDataSource dataSource) throws Exception {


        if (!checkLimitHourlyHoliday(dataSource.getHolidaysRemainingManagement()))
            return firstRow;
        cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 7, firstRow, 1);
        setBackgroundGray(cells.get(firstRow, 8));
        for (int index = 0; index < NUMBER_COLUMN - 2; index++) {
            setTopBorderStyle(cells.get(firstRow, 2 + index));
        }
        val hdRemainingInfor = dataSource.getMapEmployees().get(employee.getEmployeeId()).getHolidayRemainingInfor();
        if (hdRemainingInfor == null) {
            return firstRow + 1;
        }
        val data363 = hdRemainingInfor.getListAnnLeaveUsageStatusOfThisMonth();

        // G1_1
        cells.get(firstRow, 2).setValue(TextResource.localize("KDR001_50"));
        //G2_1
        cells.get(firstRow, 9).setValue(TextResource.localize("KDR001_15"));

        if (data363 == null || data363.isEmpty()) {
            firstRow += 1;
            for (int index = 0; index < NUMBER_COLUMN - 2; index++) {
                setBottomBorderStyle(cells.get(firstRow - 1, 2 + index));
            }
            return firstRow;
        }
        val dataOptionalMax = annLeaMaxDataRepository.get(employee.getEmployeeId());
        if (dataOptionalMax.isPresent()) {
            //G1_2

            val timeAnnualLeaveMax = dataOptionalMax.get().getTimeAnnualLeaveMax();
            if (timeAnnualLeaveMax.isPresent()) {
                val valueG12 = timeAnnualLeaveMax.get().getMaxMinutes().v();
                //G1_2
                val g12 = valueG12 != null ? convertToTime((int) (valueG12 * 60)) : "";
                cells.get(firstRow, 3).setValue(g12);
                val valueG13 = timeAnnualLeaveMax.get().getRemainingMinutes().valueAsMinutes();
                val g13 = convertToTime((int) (valueG13 * 60));
                cells.get(firstRow, 4).setValue(g13);
            }

        }

        //G1_4
        val valueG14 = data363.stream()
                .mapToDouble(AnnLeaveUsageStatusOfThisMonthImported::getMonthlyUsageDays).sum();
        //G1_4 TODO QA CACH XAC DINH
        cells.get(firstRow, 5).setValue(valueG14);

        val valueG15 = data363.stream().mapToDouble(e -> e.getMonthlyRemainingTime().isPresent() ?
                e.getMonthlyRemainingTime().get().doubleValue() : 0).sum();

        //G1_5
        cells.get(firstRow, 5).setValue(valueG15);
        // Result RequestList255
        val listAnnLeaveUsage = hdRemainingInfor.getListAnnualLeaveUsage();
        if (!employee.getCurrentMonth().isPresent()) {
            return firstRow + 1;
        }
        // BEFORE
        YearMonth currentMonth = employee.getCurrentMonth().get();
        int maxRange = totalMonths(dataSource.getStartMonth().yearMonth(), dataSource.getEndMonth().yearMonth());
        if (listAnnLeaveUsage != null && dataSource.getMapEmployees().get(employee.getEmployeeId()).getHolidayRemainingInfor().getGrantDate().isPresent()) {

            for (AnnualLeaveUsageImported item : listAnnLeaveUsage) {
                if (item.getYearMonth().compareTo(currentMonth) >= 0) {
                    continue;
                }
                int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(), item.getYearMonth());
                if (maxRange < totalMonth || totalMonth < 0) {
                    continue;
                }
                val valueG22BeforeCurent = listAnnLeaveUsage.
                        stream().filter(e -> e.getYearMonth().compareTo(item.getYearMonth()) == 0)
                        .mapToDouble(e -> e.getRemainingTime() != null ? e.getRemainingTime() : 0).sum();
                // G2_2 月度残時間, set lại giá trị cho cột này nếu bằng 0 thì không hiển thị ra
                cells.get(firstRow, 10 + totalMonth).setValue(valueG22BeforeCurent == 0 ? ""
                        : valueG22BeforeCurent);
            }
        }
        // Result RequestList363 // AFTER AND CURRENT
        val listAnnLeaveUsageOfThisMonth = hdRemainingInfor.getListAnnLeaveUsageStatusOfThisMonth();
        if (listAnnLeaveUsageOfThisMonth != null && dataSource.getMapEmployees().get(employee.getEmployeeId()).getHolidayRemainingInfor().getGrantDate().isPresent()) {
            for (AnnLeaveUsageStatusOfThisMonthImported item : listAnnLeaveUsageOfThisMonth) {
                if (item.getYearMonth().compareTo(currentMonth) < 0) {
                    continue;
                }
                int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(), item.getYearMonth());
                if (maxRange < totalMonth || totalMonth < 0) {
                    continue;
                }

                if (item.getYearMonth().equals(currentMonth)) {
                    // G2_2 月度使用回数, set lại giá trị cho cột này nếu bằng 0 thì không hiển thị ra
                    val monthlyRemainingTime = item.getMonthlyRemainingTime().isPresent() ? item.getMonthlyRemainingTime().get() : null;
                    //月度使用回数
                    cells.get(firstRow, 10 + totalMonth).setValue(monthlyRemainingTime);
                } else if (item.getYearMonth().compareTo(currentMonth) > 0) {
                    // Update KDR 001 : 値＝(クリア)/背景色＝グレー;
                    setBackgroundGray(cells.get(firstRow, 10 + totalMonth));
                }

            }
        }
        for (int i = 0; i <= totalMonths(dataSource.getStartMonth().yearMonth(),
                dataSource.getEndMonth().yearMonth()); i++) {
            if (dataSource.getStartMonth().addMonths(i).yearMonth().compareTo(currentMonth) > 0) {
                setBackgroundGray(cells.get(firstRow + 1, 10 + i));
            }
            if (!dataSource.isSameCurrentMonth()
                    && dataSource.getStartMonth().addMonths(i).yearMonth().compareTo(currentMonth) == 0) {
                setCurrentMonthBackground(cells.get(firstRow, 10 + i));
                setCurrentMonthBackground(cells.get(firstRow + 1, 10 + i));
            }
        }
        firstRow += 1;
        for (int index = 0; index < NUMBER_COLUMN - 2; index++) {
            setBottomBorderStyle(cells.get(firstRow - 1, 2 + index));
        }
        return firstRow;

    }

    // M
    private int printSpecialHoliday(Cells cells, int firstRow, HolidaysRemainingEmployee employee,
                                    HolidayRemainingDataSource dataSource) throws Exception {
        // 特別休暇

        val hdRemainingInfor = dataSource.getMapEmployees().get(employee.getEmployeeId()).getHolidayRemainingInfor();
        if (hdRemainingInfor == null) {
            return firstRow;
        }

        val listSphdCode = dataSource.getHolidaysRemainingManagement().getListItemsOutput().getSpecialHoliday();
        Collections.sort(listSphdCode);

        List<SpecialHoliday> specialHolidays = dataSource.getVariousVacationControl().getListSpecialHoliday();
        int maxRange = totalMonths(dataSource.getStartMonth().yearMonth(), dataSource.getEndMonth().yearMonth());

        for (Integer specialHolidayCode : listSphdCode) {
            Optional<SpecialHoliday> specialHolidayOpt = specialHolidays.stream()
                    .filter(c -> c.getSpecialHolidayCode().v() == specialHolidayCode).findFirst();
            if (!specialHolidayOpt.isPresent()) {
                continue;
            }
            cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 32, firstRow, 4);
            // M1_1 特別休暇
            cells.get(firstRow, 2).setValue(specialHolidayOpt.get().getSpecialHolidayName().v());
            // M2_1
            cells.get(firstRow, 9).setValue(TextResource.localize("KDR001_17"));
            // M2_3
            cells.get(firstRow + 2, 9).setValue(TextResource.localize("KDR001_18"));

            if (employee.getCurrentMonth().isPresent()) {
                YearMonth currentMonth = employee.getCurrentMonth().get();

                // Result RequestList273
                val specialVacationImported = hdRemainingInfor.getMapSpecialVacation().get(specialHolidayCode);
                // Result RequestList263
                val specialHolidayList = hdRemainingInfor.getMapListSpecialHoliday().get(specialHolidayCode);

                // ドメインモデル「特別休暇付与残数データ」を取得
                List<SpecialLeaveGrantRemainingData> listSpecialLeaveGrant = specialLeaveGrantRepository
                        .getAllByExpStatus(employee.getEmployeeId(), specialHolidayCode,
                                LeaveExpirationStatus.AVAILABLE.value);

                // 全てのドメインモデル「特別休暇付与残数データ」の残時間を合計
                Double dayNumbers = listSpecialLeaveGrant.stream()
//						.mapToDouble(item -> item.getDetails().getRemainingNumber().getDayNumberOfRemain().v()).sum();
                        .mapToDouble(item -> item.getDetails().getRemainingNumber().getMinutes().isPresent() ?
                                item.getDetails().getRemainingNumber().getDays().v() : 0).sum();
                Double timeNumbers = listSpecialLeaveGrant.stream()
//						.mapToDouble(item -> item.getDetails().getRemainingNumber().getDayNumberOfRemain().v()).sum();
                        .mapToDouble(item -> item.getDetails().getRemainingNumber().getMinutes().isPresent() ?
                                item.getDetails().getRemainingNumber().getMinutes().get().v() : 0).sum();

                if (specialVacationImported != null) {
                    // M1_2 特別休暇１_付与数日数
                    cells.get(firstRow, 4).setValue(specialVacationImported.getGrantDays());
                    // M 1_6
                    cells.get(firstRow + 1, 4).setValue(specialVacationImported.getGrantHours());

                    // M1_3 特別休暇１_月初残日数
                    cells.get(firstRow, 5).setValue(dayNumbers);
                    if (dayNumbers < 0) {
                        setForegroundRed(cells.get(firstRow, 5));
                    }
                    // M1_7
                    cells.get(firstRow + 1, 5).setValue(timeNumbers);
//                    if (dayNumbers < 0) {
//                        setForegroundRed(cells.get(firstRow, 5));
//                    }
                    // M1_4 特別休暇１_使用数日数
                    cells.get(firstRow, 6).setValue(specialVacationImported.getUsedDate());

                    // M1_8
                    cells.get(firstRow, 6).setValue(specialVacationImported.getUsedHours());
                    // M1_5 特別休暇１_残数日数
                    cells.get(firstRow, 7).setValue(specialVacationImported.getRemainDate());

                    if (specialVacationImported.getRemainDate() < 0) {
                        setForegroundRed(cells.get(firstRow, 7));
                    }
                    // M1_9
                    cells.get(firstRow + 1, 7).setValue(specialVacationImported.getRemainHours());
                    if (specialVacationImported.getRemainHours() < 0) {
                        setForegroundRed(cells.get(firstRow + 1, 7));
                    }


                }

                if (specialHolidayList != null) {
                    for (SpecialHolidayImported item : specialHolidayList) {

                        // Before this month
                        if (currentMonth.compareTo(item.getYm()) > 0) {
                            int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(), item.getYm());
                            if (maxRange >= totalMonth && totalMonth >= 0) {
                                // M2_5 特別休暇１_使用日数, set lại giá trị cho cột này nếu bằng 0 thì không hiển thị ra
                                cells.get(firstRow, 10 + totalMonth).setValue(item.getUseDays() != null && item.getUseDays() == 0 ? null : item.getUseDays());
                                //M2_6
                                cells.get(firstRow + 1, 10 + totalMonth).setValue(item.getUseTimes() != null && item.getUseTimes() == 0 ? null : item.getUseTimes());
                                // M2_7 特別休暇１_残数日数
                                cells.get(firstRow + 2, 10 + totalMonth).setValue(item.getRemainDays());
                                //M2_8
                                cells.get(firstRow + 3, 10 + totalMonth).setValue(item.getRemainTimes());
                                if (item.getRemainDays() < 0) {
                                    setForegroundRed(cells.get(firstRow + 2, 10 + totalMonth));
                                }
                                if (item.getRemainTimes() < 0) {
                                    setForegroundRed(cells.get(firstRow + 3, 10 + totalMonth));
                                }
                            }
                        }
                    }
                }

                // Result RequestList273
                int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(), currentMonth);
                List<YearMonth> lstYm = new ArrayList<>();
//				int exportMonth = totalMonths(currentMonth, dataSource.getEndMonth().yearMonth());
                for (YearMonth i = currentMonth; i.lessThanOrEqualTo(dataSource.getEndMonth().yearMonth()); i = i.addMonths(1)) {
                    lstYm.add(i);
                }
                Collections.sort(lstYm);

                // update ver14
                // M2_7 特別休暇１_残数日数
                if (!lstYm.isEmpty()) {
                    SpecialVacationImported spVaCrurrentMonthImported0 = hdRemainingInfor.getMapSPVaCrurrentMonth().get(lstYm.get(0)) != null ?
                            hdRemainingInfor.getMapSPVaCrurrentMonth().get(lstYm.get(0)).get(specialHolidayCode) : null;
                    if (spVaCrurrentMonthImported0 != null) {
                        if (totalMonth <= maxRange && totalMonth >= 0) {
                            cells.get(firstRow + 1, 10 + totalMonth).setValue(spVaCrurrentMonthImported0.getRemainDate());
                        }
                    }
                }

                for (int i = 0; i < lstYm.size(); i++) {
                    YearMonth ym = lstYm.get(i);
                    SpecialVacationImported spVaCrurrentMonthImported = hdRemainingInfor.getMapSPVaCrurrentMonth().get(ym) != null ?
                            hdRemainingInfor.getMapSPVaCrurrentMonth().get(ym).get(specialHolidayCode) : null;
                    if (spVaCrurrentMonthImported != null) {
                        if (totalMonth <= maxRange && totalMonth >= 0) {
                            // M2_5 特別休暇１_使用日数
                            cells.get(firstRow, 10 + totalMonth).setValue(spVaCrurrentMonthImported.getUsedDate() != null
                                    && spVaCrurrentMonthImported.getUsedDate() == 0 ? null : spVaCrurrentMonthImported.getUsedDate());
                            // M2_6 特別休暇１_残数日数
                            cells.get(firstRow + 1, 10 + totalMonth).setValue(spVaCrurrentMonthImported.getUsedHours());
                            if (spVaCrurrentMonthImported.getRemainDate() < 0) {
                                setForegroundRed(cells.get(firstRow + 1, 10 + totalMonth));
                            }
                            if (i == 0) {
                                cells.get(firstRow + 2, 10 + totalMonth).setValue(spVaCrurrentMonthImported.getRemainDate());
                                //M2_8
                                cells.get(firstRow + 3, 10 + totalMonth).setValue(spVaCrurrentMonthImported.getRemainHours());
                            } else {
                                // M2_7 特別休暇１_使用日数
                                setBackgroundGray(cells.get(firstRow + 2, 10 + totalMonth));
                                // M2_8 特別休暇１_残数日数
                                setBackgroundGray(cells.get(firstRow + 3, 10 + totalMonth));
                            }

                        }
                    }
                    totalMonth++;
                }
                // Set background
//                for (int index = 0; index <= totalMonths(dataSource.getStartMonth().yearMonth(),
//                        dataSource.getEndMonth().yearMonth()); index++) {
//                    if (dataSource.getStartMonth().addMonths(index).yearMonth().compareTo(currentMonth) > 0) {
//                        setBackgroundGray(cells.get(firstRow + 1, 10 + index));
////						setBackgroundGray(cells.get(firstRow, 10 + index));
////						setBackgroundGray(cells.get(firstRow + 1, 10 + index));
//                    }
//
//                    if (!dataSource.isSameCurrentMonth()
//                            && dataSource.getStartMonth().addMonths(index).yearMonth().compareTo(currentMonth) == 0) {
//                        setCurrentMonthBackground(cells.get(firstRow, 10 + index));
//                        setCurrentMonthBackground(cells.get(firstRow + 1, 10 + index));
//                    }
//                }
            }

            firstRow += 4;
        }

        return firstRow;
    }

    private int printChildNursingVacation(Cells cells, int firstRow, HolidaysRemainingEmployee employee,
                                          HolidayRemainingDataSource dataSource) throws Exception {
        // 子の看護休暇
        if (!dataSource.getVariousVacationControl().isChildNursingSetting()) {
            return firstRow;
        }
        if (!dataSource.getHolidaysRemainingManagement().getListItemsOutput().getChildNursingVacation()
                .isChildNursingLeave()) {
            return firstRow;
        }

        cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 40, firstRow, 4);
        // N1_1
        cells.get(firstRow, 2).setValue(TextResource.localize("KDR001_47"));
        // N2_1
        cells.get(firstRow, 9).setValue(TextResource.localize("KDR001_9"));
        // N2_2
        cells.get(firstRow + 2, 9).setValue(TextResource.localize("KDR001_18"));
        val hdRemainingInfor = dataSource.getMapEmployees().get(employee.getEmployeeId()).getHolidayRemainingInfor();
        if (hdRemainingInfor == null) {
            return firstRow + 4;
        }
        if (!employee.getCurrentMonth().isPresent()) {
            return firstRow + 4;
        }
        YearMonth currentMonth = employee.getCurrentMonth().get();

        // Result RequestList206
        val currentSituationImported = hdRemainingInfor.getChildNursingLeave();
        if (currentSituationImported != null) {
            // N1_2 子の看護休暇_使用数
            cells.get(firstRow, 6).setValue(currentSituationImported.getNumberOfUse());
            // N1_3 子の看護休暇_残数
            cells.get(firstRow, 7).setValue(currentSituationImported.getRemainingDays());
            if (currentSituationImported.getRemainingDays().startsWith("-")) {
                setForegroundRed(cells.get(firstRow, 7));
            }

            // Position of current month
            int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(), currentMonth);
            if (currentMonth.compareTo(dataSource.getEndMonth().yearMonth()) <= 0 && totalMonth >= 0) {
                // N2_3 子の看護休暇_使用日数 当月
                cells.get(firstRow, 10 + totalMonth).setValue(currentSituationImported.getNumberOfUse());

                // N2_4 子の看護休暇_残日数
                cells.get(firstRow + 1, 10 + totalMonth).setValue(currentSituationImported.getRemainingDays());
                if (currentSituationImported.getRemainingDays().startsWith("-")) {
                    setForegroundRed(cells.get(firstRow + 1, 10));
                }
            }
        }
//        // Set background
//        for (int i = 0; i <= totalMonths(dataSource.getStartMonth().yearMonth(),
//                dataSource.getEndMonth().yearMonth()); i++) {
//            if (dataSource.getStartMonth().addMonths(i).yearMonth().compareTo(currentMonth) != 0) {
//                setBackgroundGray(cells.get(firstRow, 10 + i));
//                setBackgroundGray(cells.get(firstRow + 1, 10 + i));
//            }
//            if (!dataSource.isSameCurrentMonth()
//                    && dataSource.getStartMonth().addMonths(i).yearMonth().compareTo(currentMonth) == 0) {
//                setCurrentMonthBackground(cells.get(firstRow, 10 + i));
//                setCurrentMonthBackground(cells.get(firstRow + 1, 10 + i));
//            }
//        }

        return firstRow + 4;
    }

    private int printNursingCareLeave(Cells cells, int firstRow, HolidaysRemainingEmployee employee,
                                      HolidayRemainingDataSource dataSource) throws Exception {
        // 介護休暇
        if (!dataSource.getVariousVacationControl().isNursingCareSetting()) {
            return firstRow;
        }
        if (!dataSource.getHolidaysRemainingManagement().getListItemsOutput().getNursingcareLeave().isNursingLeave()) {
            return firstRow;
        }
        val hdRemainingInfor = dataSource.getMapEmployees().get(employee.getEmployeeId()).getHolidayRemainingInfor();


        cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 40, firstRow, 4);
        // O1_1
        cells.get(firstRow, 2).setValue(TextResource.localize("KDR001_48"));
        // O2_1
        cells.get(firstRow, 9).setValue(TextResource.localize("KDR001_9"));
        // O2_2
        cells.get(firstRow + 2, 9).setValue(TextResource.localize("KDR001_18"));
        if (hdRemainingInfor == null) {
            return firstRow + 4;
        }
        if (!employee.getCurrentMonth().isPresent()) {
            return firstRow + 4;
        }
        YearMonth currentMonth = employee.getCurrentMonth().get();

        // Result RequestList207
        val currentSituationImported = hdRemainingInfor.getNursingLeave();

        if (currentSituationImported != null) {
            // O1_2 介護休暇_使用数
            cells.get(firstRow, 6).setValue(currentSituationImported.getNumberOfUse());
            // O1_3 介護休暇_残数
            cells.get(firstRow, 7).setValue(currentSituationImported.getRemainingDays());
            if (currentSituationImported.getRemainingDays().startsWith("-")) {
                setForegroundRed(cells.get(firstRow, 7));
            }

            // Position of current month
            int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(), currentMonth);
            if (currentMonth.compareTo(dataSource.getEndMonth().yearMonth()) <= 0 && totalMonth >= 0) {
                // O2_3 介護休暇_使用日数 当月
                cells.get(firstRow, 10 + totalMonth).setValue(currentSituationImported.getNumberOfUse());

                // O2_4 介護休暇_残日数
                cells.get(firstRow + 1, 10 + totalMonth).setValue(currentSituationImported.getRemainingDays());
                if (currentSituationImported.getRemainingDays().startsWith("-")) {
                    setForegroundRed(cells.get(firstRow + 1, 10));
                }
            }
        }

//        // Set background
//        for (int i = 0; i <= totalMonths(dataSource.getStartMonth().yearMonth(),
//                dataSource.getEndMonth().yearMonth()); i++) {
//            if (dataSource.getStartMonth().addMonths(i).yearMonth().compareTo(currentMonth) != 0) {
//                setBackgroundGray(cells.get(firstRow, 10 + i));
//                setBackgroundGray(cells.get(firstRow + 1, 10 + i));
//            }
//            if (!dataSource.isSameCurrentMonth()
//                    && dataSource.getStartMonth().addMonths(i).yearMonth().compareTo(currentMonth) == 0) {
//                setCurrentMonthBackground(cells.get(firstRow, 10 + i));
//                setCurrentMonthBackground(cells.get(firstRow + 1, 10 + i));
//            }
//        }

        return firstRow + 4;
    }

    private int countRowEachPerson(HolidayRemainingDataSource dataSource) {
        int totalRowDetails = 0;
        // 年休
        if (dataSource.getHolidaysRemainingManagement().getListItemsOutput().getAnnualHoliday().isYearlyHoliday()) {
            totalRowDetails += 2;
        }
        // 積立年休
        if (dataSource.getHolidaysRemainingManagement().getListItemsOutput().getYearlyReserved().isYearlyReserved()) {
            totalRowDetails += 2;
        }
        // 代休
        if (dataSource.getHolidaysRemainingManagement().getListItemsOutput().getSubstituteHoliday()
                .isOutputItemSubstitute()) {
            totalRowDetails += 2;

            if (dataSource.getHolidaysRemainingManagement().getListItemsOutput().getSubstituteHoliday()
                    .isRemainingChargeSubstitute()) {
                totalRowDetails += 1;
            }
            if (dataSource.getHolidaysRemainingManagement().getListItemsOutput().getSubstituteHoliday()
                    .isRepresentSubstitute()) {
                totalRowDetails += 1;
            }
        }
        // 振休
        if (dataSource.getHolidaysRemainingManagement().getListItemsOutput().getPause().isPauseItem()) {
            totalRowDetails += 2;
            if (dataSource.getHolidaysRemainingManagement().getListItemsOutput().getPause().isNumberRemainingPause()) {
                totalRowDetails += 1;
            }
            if (dataSource.getHolidaysRemainingManagement().getListItemsOutput().getPause().isUndigestedPause()) {
                totalRowDetails += 1;
            }
        }
        // 特別休暇
        List<Integer> specialHoliday = dataSource.getHolidaysRemainingManagement().getListItemsOutput()
                .getSpecialHoliday();
        for (int i = 0; i < specialHoliday.size(); i++) {
            totalRowDetails += 2;
        }
        // 子の看護休暇
        if (dataSource.getHolidaysRemainingManagement().getListItemsOutput().getChildNursingVacation()
                .isChildNursingLeave()) {
            totalRowDetails += 2;
        }
        // 介護休暇
        if (dataSource.getHolidaysRemainingManagement().getListItemsOutput().getNursingcareLeave().isNursingLeave()) {
            totalRowDetails += 2;
        }

        return totalRowDetails;
    }

    // L1 - CREATE IN VER 15 - PENDING- rq 262
    private int publicHolidays(Cells cells, int firstRow, HolidaysRemainingEmployee employee,
                               HolidayRemainingDataSource dataSource) throws Exception {
        // 代休
        if (!checkTakeABreak_01(dataSource.getHolidaysRemainingManagement(), employee.getEmploymentCode())) {
            return firstRow;
        }
        val hdRemainingInfor = dataSource.getMapEmployees().get(employee.getEmployeeId()).getHolidayRemainingInfor();
        int totalRows = 4;
        int rowIndexRepresentSubstitute = 0;
        int rowIndexIsRemainingChargeSubstitute = 0;
        cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 24, firstRow, 4);
        // L1_1
        cells.get(firstRow, 2).setValue(TextResource.localize("KDR001_21"));
        // L2_1
        cells.get(firstRow, 9).setValue(TextResource.localize("KDR001_23"));
        // L2_2
        cells.get(firstRow + 1, 9).setValue(TextResource.localize("KDR001_22"));
        // L2_3
        cells.get(firstRow + 2, 9).setValue(TextResource.localize("KDR001_17"));
        // L2_4
        cells.get(firstRow + 3, 9).setValue(TextResource.localize("KDR001_80"));

//        if (checkTakeABreak_02(dataSource.getHolidaysRemainingManagement())) {
//            rowIndexRepresentSubstitute = firstRow + totalRows;
//            cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 15, rowIndexRepresentSubstitute, 1);
//            // I4_1
//            if (checkTakeABreak_02(dataSource.getHolidaysRemainingManagement()))
//                cells.get(rowIndexRepresentSubstitute, 9).setValue(TextResource.localize("KDR001_11"));
//            totalRows += 1;
//
//        }
//
//        if (checkTakeABreak_03(dataSource.getHolidaysRemainingManagement())) {
//            rowIndexIsRemainingChargeSubstitute = firstRow + totalRows;
//            cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 16, rowIndexIsRemainingChargeSubstitute, 1);
//            // I5_1
//            cells.get(rowIndexIsRemainingChargeSubstitute, 9).setValue(TextResource.localize("KDR001_18"));
//            totalRows += 1;
//        }
        if (hdRemainingInfor == null) {
            return firstRow + 4;
        }

        if (!employee.getCurrentMonth().isPresent()) {
            return firstRow + totalRows;
        }
//        YearMonth currentMonth = employee.getCurrentMonth().get();
//
//        // Set value for I
//        // Result RequestList259
//        val statusHolidayImported = hdRemainingInfor.getListStatusHoliday();
//        // RequestList 203 todo update ver15
//        val substituteHolidayAggrResult = hdRemainingInfor.getSubstituteHolidayAggrResult();
//        int maxRange = totalMonths(dataSource.getStartMonth().yearMonth(), dataSource.getEndMonth().yearMonth());
//        if (statusHolidayImported != null) {
//            for (StatusHolidayImported statusHolidayItem : statusHolidayImported) {
//                int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(), statusHolidayItem.getYm());
//                if (statusHolidayItem.getYm().compareTo(currentMonth) > 0) {
//                    // After this month
//                    // I2_3 代休_発生_日数時間数 実績値, set lại giá trị cho cột này nếu bằng 0 thì không hiển thị ra
//                    // 発生数 : 代休+時間代休の場合
//                    String occurrence;
//                    if ((statusHolidayItem.getOccurrenceDays() == 0 && statusHolidayItem.getOccurrenceTimes() == 0)
//                            || (statusHolidayItem.getOccurrenceTimes() == null && statusHolidayItem.getOccurrenceDays() == null)) {
//                        occurrence = null;
//                    } else {
//                        occurrence = statusHolidayItem.getOccurrenceDays() == null ? "0" : statusHolidayItem.getOccurrenceDays().toString() + ":" +
//                                (statusHolidayItem.getOccurrenceTimes() == null ? "0" : statusHolidayItem.getOccurrenceTimes().toString());
//                    }
//                    cells.get(firstRow, 10 + totalMonth).setValue(occurrence);
//                    // I3_3 代休_使用_日数時間数 実績値, set lại giá trị cho cột này nếu bằng 0 thì không hiển thị ra
//                    String use;
//                    use = (statusHolidayItem.getUseTimes() == null ? "0" : statusHolidayItem.getUseTimes().toString());
//                    cells.get(firstRow + 1, 10 + totalMonth)
//                            .setValue(use);
//                } else if (statusHolidayItem.getYm().compareTo(currentMonth) <= 0) {
//                    // Before this month
//                    if (maxRange >= totalMonth && totalMonth >= 0) {
//                        // I2_3 代休_発生_日数時間数 実績値, set lại giá trị cho cột này nếu bằng 0 thì không hiển thị ra
//                        // 発生数 : 代休+時間代休の場合
//                        String occurrence;
//                        if ((statusHolidayItem.getOccurrenceDays() == 0 && statusHolidayItem.getOccurrenceTimes() == 0)
//                                || (statusHolidayItem.getOccurrenceTimes() == null && statusHolidayItem.getOccurrenceDays() == null)) {
//                            occurrence = null;
//                        } else {
//                            occurrence = statusHolidayItem.getOccurrenceDays() == null ? "0" : statusHolidayItem.getOccurrenceDays().toString() + ":" +
//                                    (statusHolidayItem.getOccurrenceTimes() == null ? "0" : statusHolidayItem.getOccurrenceTimes().toString());
//                        }
//                        cells.get(firstRow, 10 + totalMonth).setValue(occurrence);
//
//                        // I3_3 代休_使用_日数時間数 実績値, set lại giá trị cho cột này nếu bằng 0 thì không hiển thị ra
//                        String use;
//                        if ((statusHolidayItem.getUseDays() == 0 && statusHolidayItem.getUseTimes() == 0) || (statusHolidayItem.getUseDays() == null
//                                && statusHolidayItem.getUseTimes() == null)) {
//                            use = null;
//                        } else {
//                            use = statusHolidayItem.getUseDays() == null ? "0" : statusHolidayItem.getUseDays().toString() + ":" +
//                                    (statusHolidayItem.getUseTimes() == null ? "0" : statusHolidayItem.getUseTimes().toString());
//                        }
//                        cells.get(firstRow + 1, 10 + totalMonth)
//                                .setValue(use);
//                        if (currentMonth.compareTo(statusHolidayItem.getYm()) >= 0) {
//
//                            if (checkTakeABreak_02(dataSource.getHolidaysRemainingManagement())) {
//                                // I4_3 代休_未消化_日数, set lại giá trị cho cột này nếu bằng 0 thì không hiển thị ra
//                                String unUsed;
//                                if ((statusHolidayItem.getUnUsedDays() == 0 && statusHolidayItem.getUnUsedTimes() == 0)
//                                        || (statusHolidayItem.getUnUsedDays() == null
//                                        && statusHolidayItem.getUnUsedTimes() == null)) {
//                                    unUsed = null;
//                                } else {
//                                    unUsed = statusHolidayItem.getUnUsedDays() == null ? "0" : statusHolidayItem.getUnUsedDays().toString() + ":" +
//                                            (statusHolidayItem.getUnUsedTimes() == null ? "0" : statusHolidayItem.getUnUsedTimes().toString());
//                                }
//                                cells.get(rowIndexRepresentSubstitute, 10 + totalMonth)
//                                        .setValue(unUsed);
//                                if (statusHolidayItem.getUnUsedDays() != null && statusHolidayItem.getUnUsedDays() > 0) {
//                                    setForegroundRed(cells.get(rowIndexRepresentSubstitute, 10 + totalMonth));
//                                }
//                            }
//                            if (checkTakeABreak_03(dataSource.getHolidaysRemainingManagement())) {
//                                // I5_3 代休_残数_日数
//                                String remain;
//                                if (((statusHolidayItem.getRemainDays() == 0 && statusHolidayItem.getRemainTimes() == 0)
//                                        || (statusHolidayItem.getRemainDays() == null
//                                        && statusHolidayItem.getRemainTimes() == null))) {
//                                    remain = null;
//                                } else {
//                                    remain = statusHolidayItem.getRemainDays() == null ? "0" : statusHolidayItem.getRemainDays().toString() + ":" +
//                                            (statusHolidayItem.getRemainTimes() == null ? "0" : statusHolidayItem.getRemainTimes().toString());
//                                }
//                                if (currentMonth.compareTo(statusHolidayItem.getYm()) == 0) {
//                                    remain = statusHolidayItem.getRemainTimes() == null ? "0" : statusHolidayItem.getRemainTimes().toString();
//                                }
//                                cells.get(rowIndexIsRemainingChargeSubstitute, 10 + totalMonth)
//                                        .setValue(remain);
//                                if (statusHolidayItem.getRemainDays() < 0) {
//                                    setForegroundRed(cells.get(rowIndexIsRemainingChargeSubstitute, 10 + totalMonth));
//
//                                }
//                            }
//                        } else if (currentMonth.compareTo(statusHolidayItem.getYm()) < 0) {
//                            setBackgroundGray(cells.get(rowIndexIsRemainingChargeSubstitute, 10 + totalMonth));
//                            setBackgroundGray(cells.get(rowIndexRepresentSubstitute, 10 + totalMonth));
//
//                        }
//                    }
//                }
//
//            }
//        }
//        // 月初残数
//        // 日数
//        val monRemainingDate = substituteHolidayAggrResult.getCarryoverDay().v();
//        // 時間
//        val monRemainingTime = substituteHolidayAggrResult.getCarryoverTime().v();
//        val monRemainingDateTime = monRemainingDate.toString() + ":" + monRemainingTime;
//        cells.get(firstRow, 5).setValue(monRemainingDateTime);
//        //I1_3 使用数
//        // 日数
//        val dateUse = substituteHolidayAggrResult.getDayUse().v();
//        // 時間
//        val timeUse = substituteHolidayAggrResult.getTimeUse().v();
//        // 代休+時間代休の場合
//        val dateTimeUse = dateUse.toString() + ":" + timeUse;
//        cells.get(firstRow, 6).setValue(dateTimeUse);
//
//
//        // I1_4 残数
//        // 日数
//        val remainDay = substituteHolidayAggrResult.getRemainDay().v();
//        //時間
//        val remainTime = substituteHolidayAggrResult.getRemainTime().v();
//        // 代休+時間代休の場合
//        val dateTimeRemain = remainDay.toString() + ":" + remainTime.toString();
//        cells.get(firstRow, 7).setValue(dateTimeRemain);
//
//        // I1_5: 未消化数
//        //日数
//        val unusedDay = substituteHolidayAggrResult.getUnusedDay().v();
//        //時間
//        val unusedTime = substituteHolidayAggrResult.getUnusedTime().v();
//        //代休+時間代休の場合
//        val dateTimeUnused = unusedDay.toString() + ":" + unusedTime.toString();
//        cells.get(firstRow, 8).setValue(dateTimeUnused);
//        // Set background
//        for (int i = 0; i <= totalMonths(dataSource.getStartMonth().yearMonth(),
//                dataSource.getEndMonth().yearMonth()); i++) {
//            if (dataSource.getStartMonth().addMonths(i).yearMonth().compareTo(currentMonth) > 0) {
//                if (checkTakeABreak_02(dataSource.getHolidaysRemainingManagement())) {
//                    setBackgroundGray(cells.get(rowIndexRepresentSubstitute, 10 + i));
//                }
//                if (checkTakeABreak_03(dataSource.getHolidaysRemainingManagement())) {
//                    setBackgroundGray(cells.get(rowIndexIsRemainingChargeSubstitute, 10 + i));
//                }
//            }
//            if (!dataSource.isSameCurrentMonth()
//                    && dataSource.getStartMonth().addMonths(i).yearMonth().compareTo(currentMonth) == 0) {
//                setCurrentMonthBackground(cells.get(firstRow, 10 + i));
//                setCurrentMonthBackground(cells.get(firstRow + 1, 10 + i));
//                if (checkTakeABreak_02(dataSource.getHolidaysRemainingManagement())) {
//                    setCurrentMonthBackground(cells.get(rowIndexRepresentSubstitute, 10 + i));
//                }
//                if (checkTakeABreak_03(dataSource.getHolidaysRemainingManagement())) {
//                    setCurrentMonthBackground(cells.get(rowIndexIsRemainingChargeSubstitute, 10 + i));
//                }
//            }
//        }
        for (int index = 0; index < NUMBER_COLUMN - 2; index++) {
            setTopBorderStyle(cells.get(firstRow, 2 + index));
            setBottomBorderStyle(cells.get(firstRow + totalRows - 1, 2 + index));
        }
        return firstRow + totalRows;
    }

    private void removeTemplate(Worksheet worksheet) {
        removeFirstShapes(worksheet);
        Cells cells = worksheet.getCells();
        cells.deleteRows(0, 53);
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

    private int totalMonths(YearMonth start, YearMonth end) {
        return (end.year() - start.year()) * TOTAL_MONTH_IN_YEAR + (end.month() - start.month());
    }

    /**
     * PRINT PAGE HEADER
     */
    private void printHeader(Worksheet worksheet, HolidayRemainingDataSource dataSource) {
        // Set print page
        PageSetup pageSetup = worksheet.getPageSetup();
        val title = dataSource.getHolidaysRemainingManagement().getName();
        pageSetup.setFirstPageNumber(1);
        pageSetup.setPrintArea("A1:N");
        //ý 1 của bug #102883  事象(1)
        pageSetup.setHeader(0, "&9&\"MS ゴシック\"" + dataSource.getCompanyName());
        pageSetup.setHeader(1, "&16&\"MS ゴシック\"" + title);
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm", Locale.JAPAN);
        pageSetup.setHeader(2, "&9&\"MS ゴシック\"" + LocalDateTime.now().format(fullDateTimeFormatter) + "\npage &P");
    }

    //E Case 1:
    private boolean checkShowAreaAnnualBreak1(HolidaysRemainingManagement holidaysRemainingManagement) {
        String cid = AppContexts.user().companyId();
        val checkLeave = annualPaidLeaveSettingRepository.findByCompanyId(cid);
        if (!holidaysRemainingManagement.getListItemsOutput().getAnnualHoliday().isYearlyHoliday()) {
            return false;
        } else {
            if ((checkLeave != null && checkLeave.getYearManageType() == ManageDistinct.NO)) {
                return false;
            } else {
                return true;
            }
        }
    }  //E Case 2:

    private boolean checkShowAreaAnnualBreak2(HolidaysRemainingManagement holidaysRemainingManagement) {
        String cid = AppContexts.user().companyId();
        val checkLeave = annualPaidLeaveSettingRepository.findByCompanyId(cid);
        if (!holidaysRemainingManagement.getListItemsOutput().getAnnualHoliday().isYearlyHoliday()) {
            return false;
        } else {
            if ((checkLeave != null && checkLeave.getYearManageType() == ManageDistinct.NO)) {
                return false;
            } else {
                if (checkLeave != null && checkLeave.getTimeSetting().getTimeManageType() == ManageDistinct.YES) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    // F
    private boolean checkLimitHoliday(HolidaysRemainingManagement holidaysRemainingManagement) {
        String cid = AppContexts.user().companyId();
        val checkLeave = annualPaidLeaveSettingRepository.findByCompanyId(cid);
        if (!holidaysRemainingManagement.getListItemsOutput().getAnnualHoliday().isInsideHalfDay()) {
            return false;
        } else if ((checkLeave == null || checkLeave.getYearManageType() == ManageDistinct.NO)) {
            return false;
        } else if (checkLeave.getManageAnnualSetting().getHalfDayManage().getManageType()
                == ManageDistinct.NO) {
            return false;
        } else {
            return true;
        }
    }

    // G
    private boolean checkLimitHourlyHoliday(HolidaysRemainingManagement holidaysRemainingManagement) {
        String cid = AppContexts.user().companyId();
        val checkLeave = annualPaidLeaveSettingRepository.findByCompanyId(cid);
        if (!holidaysRemainingManagement.getListItemsOutput().getAnnualHoliday().isInsideHalfDay()) {
            return false;
        } else {
            if ((checkLeave == null || checkLeave.getYearManageType() == ManageDistinct.NO)) {
                return false;
            } else {
                if (checkLeave.getTimeSetting().getTimeManageType() == ManageDistinct.NO) {
                    return false;
                } else {
                    if (checkLeave.getTimeSetting().getMaxYearDayLeave().manageType == ManageDistinct.NO) {
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        }
    }

    // H
    private boolean checkYearlyLeave(HolidaysRemainingManagement holidaysRemainingManagement, String employmentCode, VariousVacationControl vacationControl) {
        String cid = AppContexts.user().companyId();
        val checkLeave = annualPaidLeaveSettingRepository.findByCompanyId(cid);
        val checkByScd = employmentSettingRepository.find(cid, employmentCode);
        if (!holidaysRemainingManagement.getListItemsOutput().getYearlyReserved().isYearlyReserved()) {
            return false;
        } else {
            if ((checkLeave == null || checkLeave.getYearManageType() == ManageDistinct.NO)) {
                return false;
            } else {
                if (!checkByScd.isPresent() || checkByScd.get().getManagementCategory() == ManageDistinct.NO) {
                    if (vacationControl.isYearlyReservedSetting()) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    if (vacationControl.isYearlyReservedSetting()) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
    }

    // I - CASE 1
    private boolean checkTakeABreak_01(HolidaysRemainingManagement holidaysRemainingManagement,
                                       String employmentCode) {
        String cid = AppContexts.user().companyId();
        CompensatoryLeaveComSetting compensatoryLeaveComSetting = compensLeaveComSetRepository.find(cid);
        val checkEmLeave = compensLeaveEmSetRepository.find(cid, employmentCode);
        if (checkEmLeave == null || !holidaysRemainingManagement.getListItemsOutput().getSubstituteHoliday().isOutputItemSubstitute()) {
            return false;
        } else if (checkEmLeave.getIsManaged() == ManageDistinct.NO) {
            return compensatoryLeaveComSetting.isManaged();
        } else {
            return compensatoryLeaveComSetting.isManaged();
        }

    }

    // I - CASE 2
    private boolean checkTakeABreak_02(HolidaysRemainingManagement holidaysRemainingManagement) {
        return holidaysRemainingManagement.getListItemsOutput().getSubstituteHoliday().isRepresentSubstitute();

    }

    // I - CASE 3
    private boolean checkTakeABreak_03(HolidaysRemainingManagement holidaysRemainingManagement) {
        return holidaysRemainingManagement.getListItemsOutput().getSubstituteHoliday().isRemainingChargeSubstitute();

    }

    private String convertToTime(int minute) {
        val minuteAbs = Math.abs(minute);
        int hours = minuteAbs / 60;
        int minutes = minuteAbs % 60;
        return (minute < 0 ? "-" : "") + String.format("%d:%02d", hours, minutes);
    }

}