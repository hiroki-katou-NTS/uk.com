package nts.uk.ctx.at.function.infra.generator.holidaysremaining;

import com.aspose.cells.*;
import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.*;
import nts.uk.ctx.at.function.dom.adapter.reserveleave.ReservedYearHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.reserveleave.RsvLeaUsedCurrentMonImported;
import nts.uk.ctx.at.function.dom.adapter.vacation.StatusHolidayImported;
import nts.uk.ctx.at.function.dom.holidaysremaining.BreakSelection;
import nts.uk.ctx.at.function.dom.holidaysremaining.HolidaysRemainingManagement;
import nts.uk.ctx.at.function.dom.holidaysremaining.VariousVacationControl;
import nts.uk.ctx.at.function.dom.holidaysremaining.report.HolidayRemainingDataSource;
import nts.uk.ctx.at.function.dom.holidaysremaining.report.HolidaysRemainingEmployee;
import nts.uk.ctx.at.function.dom.holidaysremaining.report.HolidaysRemainingReportGenerator;
import nts.uk.ctx.at.function.dom.holidaysremaining.report.SpecialHolidayRemainDataOutputKdr;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnLeaMaxDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnLeaRemNumEachMonthRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveEmSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrameRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
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
    @Inject
    private ComSubstVacationRepository comSubstVacationRepository;
    @Inject
    private EmpSubstVacationRepository substVacationRepository;
    @Inject
    private AnnLeaRemNumEachMonthRepository annLeaRemNumEachMonthRepository;
    @Inject
    private SpecialHolidayFrameRepository specialHolidayFrameRepository;

    private static final String TEMPLATE_FILE = "report/KDR001_V5.xlsx";
    private static final String REPORT_FILE_NAME = "休暇残数管理表.xlsx";
    private static final int NUMBER_ROW_OF_PAGE = 32;
    private static final int NUMBER_ROW_OF_HEADER = 5;
    private static final int NUMBER_COLUMN = 23;
    private static final int MIN_ROW_DETAILS = 4;
    private static final int TOTAL_MONTH_IN_YEAR = 12;
    private static final int MAX_ROW_IN_PAGE = 32;
    private static final int MAX_ROW_IN_PAGE_TEMPLATE = 53;

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
            worksheets.setActiveSheetIndex(0);
            HorizontalPageBreakCollection pageBreaks = worksheet.getHorizontalPageBreaks();
            // pageBreaks.add(53);
            if (dataSource.getPageBreak() == BreakSelection.None.value) {
                printNoneBreakPage(worksheet, dataSource);
            } else if (dataSource.getPageBreak() == BreakSelection.Workplace.value) {
                printWorkplaceBreakPage(worksheet, dataSource, pageBreaks);
            } else if (dataSource.getPageBreak() == BreakSelection.Individual.value) {
                printPersonBreakPage(worksheet, dataSource, pageBreaks);
            }
            PageSetup pageSetup = worksheet.getPageSetup();
            pageSetup.setPaperSize(PaperSizeType.PAPER_A_4);
            pageSetup.setOrientation(PageOrientationType.LANDSCAPE);
            pageSetup.setFitToPagesTall(0);
            pageSetup.setFitToPagesWide(0);
            pageSetup.setFirstPageNumber(0);
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
        int firstRow = MAX_ROW_IN_PAGE_TEMPLATE;
        Cells cells = worksheet.getCells();
        cells.copyRows(cells, 0, firstRow, NUMBER_ROW_OF_HEADER + 1);
        firstRow += NUMBER_ROW_OF_HEADER;
        Map<String,Integer> mapCountAndSid = new HashMap<>();
        for (String employeeIds : dataSource.getEmpIds()) {
            int count = 0;
            HolidaysRemainingEmployee employee = dataSource.getMapEmployees().get(employeeIds);
            count += countE(dataSource, employee);
            count += countF(dataSource, employee);
            count += countG(dataSource, employee);
            count += countM(dataSource, employee);
            count += countN(dataSource, employee);
            count += countO(dataSource, employee);
            count += countH(dataSource, employee);
            count += countI(dataSource, employee);
            count += countJ(dataSource, employee);
            count += countK(dataSource, employee);
            count += countL(dataSource, employee);
            mapCountAndSid.put(employeeIds,count);
        }
        List<String> empIds = dataSource.getEmpIds();
        int countRow = 0;
        for (int i = 0; i < empIds.size(); i++) {
            String employeeIds = empIds.get(i);
            HolidaysRemainingEmployee employee = dataSource.getMapEmployees().get(employeeIds);
//            if (countRow>32) {
//                countRow = 0;
//                // print Header
//                cells.copyRows(cells, 0, firstRow, NUMBER_ROW_OF_HEADER + 1);
//                firstRow += NUMBER_ROW_OF_HEADER;
//            }
            // D1_1, D1_2
            cells.copyRows(cells, 5, firstRow, 1);
            cells.get(firstRow, 0).setValue(TextResource.localize("KDR001_12") + ": " + employee.getWorkplaceCode()
                    + "　" + employee.getWorkplaceName());
            firstRow += 1;
            firstRow = this.printHolidayRemainingEachPerson(worksheet, firstRow, employee, dataSource);
            countRow += mapCountAndSid.getOrDefault(employeeIds,0);
        }
    }

    private void printWorkplaceBreakPage(Worksheet worksheet, HolidayRemainingDataSource dataSource,
                                         HorizontalPageBreakCollection pageBreaks) throws Exception {
        int firstRow = MAX_ROW_IN_PAGE_TEMPLATE;
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
            firstRow = printEachWorkplace(worksheet, firstRow, listEmployee, dataSource, pageBreaks);
            pageBreaks.add(firstRow);
        }

    }

    private int printEachWorkplace(Worksheet worksheet, int firstRow, List<HolidaysRemainingEmployee> employees,
                                   HolidayRemainingDataSource dataSource, HorizontalPageBreakCollection pageBreaks) throws Exception {
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
        Map<String,Integer> mapCountAndSid = new HashMap<>();
        for (String employeeIds : dataSource.getEmpIds()) {
            int count = 0;
            HolidaysRemainingEmployee employee = dataSource.getMapEmployees().get(employeeIds);
            count += countE(dataSource, employee);
            count += countF(dataSource, employee);
            count += countG(dataSource, employee);
            count += countM(dataSource, employee);
            count += countN(dataSource, employee);
            count += countO(dataSource, employee);
            count += countH(dataSource, employee);
            count += countI(dataSource, employee);
            count += countJ(dataSource, employee);
            count += countK(dataSource, employee);
            count += countL(dataSource, employee);
            mapCountAndSid.put(employeeIds,count);
        }
        int countRow = 0;
        for (HolidaysRemainingEmployee employee : employees) {

//            if (countRow>32){
//                pageBreaks.add(firstRow);
//                // print Header
//                cells.copyRows(cells, 0, firstRow, NUMBER_ROW_OF_HEADER + 1);
//                // D1_1, D1_2
//                cells.get(firstRow + NUMBER_ROW_OF_HEADER, 0).setValue(TextResource.localize("KDR001_12") + ": "
//                        + employees.get(0).getWorkplaceCode() + "　" + employees.get(0).getWorkplaceName());
//                firstRow += NUMBER_ROW_OF_HEADER + 1;
//            }
            firstRow = printHolidayRemainingEachPerson(worksheet, firstRow, employee, dataSource);
            countRow += mapCountAndSid.getOrDefault(employee.getEmployeeId(),0);

        }
        if (firstRow % NUMBER_ROW_OF_PAGE != 0) {
            firstRow += (NUMBER_ROW_OF_PAGE - firstRow % NUMBER_ROW_OF_PAGE);
        }
        return firstRow;
    }

    private void printPersonBreakPage(Worksheet worksheet, HolidayRemainingDataSource dataSource,
                                      HorizontalPageBreakCollection pageBreaks) throws Exception {
        int firstRow = MAX_ROW_IN_PAGE_TEMPLATE;
        List<String> empIds = dataSource.getEmpIds();
        for (int i = 0; i < empIds.size(); i++) {
            String employeeIds = empIds.get(i);
            HolidaysRemainingEmployee employee = dataSource.getMapEmployees().get(employeeIds);
            firstRow = this.printEachPerson(worksheet, firstRow, employee, dataSource);
        }
    }

    private int printEachPerson(Worksheet worksheet, int firstRow, HolidaysRemainingEmployee employee,
                                HolidayRemainingDataSource dataSource) throws Exception {
        Cells cells = worksheet.getCells();
        HorizontalPageBreakCollection pageBreaks = worksheet.getHorizontalPageBreaks();
        // D index
        // print Header
        cells.copyRows(cells, 0, firstRow, NUMBER_ROW_OF_HEADER + 1);
        // D1_1, D1_2
        cells.get(firstRow + NUMBER_ROW_OF_HEADER, 0).setValue(TextResource.localize("KDR001_12") + ": "
                + employee.getWorkplaceCode() + "　" + employee.getWorkplaceName());
        firstRow += NUMBER_ROW_OF_HEADER + 1;
        firstRow = printHolidayRemainingEachPersonNew(worksheet, firstRow, employee, dataSource);
//
//        if (firstRow % NUMBER_ROW_OF_PAGE != 0) {
//            firstRow += (NUMBER_ROW_OF_PAGE - firstRow % NUMBER_ROW_OF_PAGE);
//        }
        return firstRow;
    }

    private int printHolidayRemainingEachPerson(Worksheet worksheet, int firstRow, HolidaysRemainingEmployee employee,
                                                HolidayRemainingDataSource dataSource) throws Exception {
        int rowIndexD = firstRow;
        int totalRowD = 0;
        int first = firstRow;
        Cells cells = worksheet.getCells();
        // E
        firstRow = printAnnualHoliday(cells, firstRow, employee, dataSource);
        // F
        firstRow = printLimitForHalfHolidays(cells, firstRow, employee, dataSource);
        //G
        firstRow = printLimitUsageHolidays(cells, firstRow, employee, dataSource);
        // H
        firstRow = printYearlyReserved(cells, firstRow, employee, dataSource);
        // I
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
        int totalRowDetails = count(dataSource);
        int totalRow = firstRow - first;
        YearMonth currentMonth = employee.getCurrentMonth().get();
        if (!dataSource.isSameCurrentMonth()) {
            for (int j = 0; j < totalRow; j++) {
                int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(), currentMonth);
                setCurrentMonthBackground(cells.get(first + j, 10 + totalMonth));
            }

        }
        if (totalRowDetails < MIN_ROW_DETAILS) {
            // Insert blank rows
            cells.copyRows(cells, 54, firstRow, totalRowDetails - totalRow);
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
                        .setValue(TextResource.localize("KDR001_71",
                                grantDate.get().toString("yyyy/MM/dd")));
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

    private int printHolidayRemainingEachPersonNew(Worksheet worksheet, int firstRow, HolidaysRemainingEmployee employee,
                                                   HolidayRemainingDataSource dataSource) throws Exception {

        HorizontalPageBreakCollection pageBreaks = worksheet.getHorizontalPageBreaks();
        int rowIndexD = firstRow;
        int first = firstRow;
        int totalRowD = 0;
        Cells cells = worksheet.getCells();
        //E
        firstRow = printAnnualHoliday(cells, firstRow, employee, dataSource);
        //F
        firstRow = printLimitForHalfHolidays(cells, firstRow, employee, dataSource);
        //G
        firstRow = printLimitUsageHolidays(cells, firstRow, employee, dataSource);
        // H
        firstRow = printYearlyReserved(cells, firstRow, employee, dataSource);
        // I
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
                        .setValue(TextResource.localize("KDR001_71",
                                grantDate.get().toString("yyyy/MM/dd")));
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
        int tottal = firstRow - first + 6;
        int totalRowDetails = firstRow - first;
        YearMonth currentMonth = employee.getCurrentMonth().get();
        if (!dataSource.isSameCurrentMonth()) {
            for (int j = 0; j < totalRowDetails; j++) {
                int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(), currentMonth);
                setCurrentMonthBackground(cells.get(first + j, 10 + totalMonth));
            }

        }
        double pages = tottal / (MAX_ROW_IN_PAGE - 6);
        for (int i = 0; i < pages; i++) {
            int indexRow = first + i * (MAX_ROW_IN_PAGE - 6);
            if (i >= 1) {
                cells.insertRows(indexRow, 6);
                for (int index = 0; index < NUMBER_COLUMN; index++) {
                    setBottomBorderStyle(cells.get(indexRow - 1, index));
                }
                pageBreaks.add(indexRow);
                cells.copyRows(cells, 0, indexRow, 6);
                cells.get(indexRow + 5, 0).setValue(TextResource.localize("KDR001_12") + ": " + employee.getWorkplaceCode()
                        + "　" + employee.getWorkplaceName());
                val rowIndexDs = indexRow + 6;
                // merger cột  D2_1 + D2_2
                cells.merge(rowIndexDs, 0, 1, 2, true);
                cells.get(rowIndexDs, 0).setValue(employee.getEmployeeCode() + " " + employee.getEmployeeName());
                // D2_7 + D25 (POISITION CODE + POISITION NAME)
                cells.merge(rowIndexDs + 1, 0, 1, 2, true);
                cells.get(rowIndexDs + 1, 0).setValue(d2_7);
                // D2_6 +D2_4: EMPLOYMENT CODE +EMPLOYMENT NAME
                cells.merge(rowIndexDs + 2, 0, 1, 2, true);
                cells.get(rowIndexDs + 2, 0)
                        .setValue(d2_6);
                // D2_3 No.369
                if (isDisplayHolidayYear) {
                    // D2_3
                    cells.merge(rowIndexDs + 3, 0, 1, 2, true);
                    if (grantDate.isPresent()) {
                        cells.get(rowIndexDs + 3, 0)
                                .setValue(TextResource.localize("KDR001_71",
                                        grantDate.get().toString("yyyy/MM/dd")));
                    }
                    //  D2_8 No.717 TODO QA : RQ 717
                    // cells.merge(rowIndexD + 4, 0, 1, 2, true);
                    // cells.get(rowIndexD + 3, 0)
                    //                        .setValue(TextResource.localize("("+"KDR001_72",
                    //                                grantDate.get().toString("yyyy/MM/dd"))+")");
                    //totalRowD+=1;
                }
                for (int index = 0; index < NUMBER_COLUMN; index++) {
                    setBottomBorderStyle(cells.get(indexRow + 5, index));
                    setTopBorderStyle(cells.get(indexRow + 5, index));
                }
                firstRow += 6;
                first += 6;
            }

        }
        pageBreaks.add(firstRow);
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
        int totalAddRows = 4;
        cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 1, firstRow, 4);
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
        // E3_1
        val text = checkShowAreaAnnualBreak1(
                dataSource.getHolidaysRemainingManagement())
                ? TextResource.localize("KDR001_15")
                : "";
        cells.get(firstRow + 2, 9).setValue(text);
        if (listAnnLeaGrant != null && grantDate.isPresent()) {
            for (int i = 0; i < listAnnLeaGrant.size(); i++) {
                if (i >= 2) {
                    cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 5, firstRow + i * 2, 2);
                    totalAddRows+=2;
                }
                // E1_2, 事象(4)của bug #102883
                cells.get(firstRow + 2 * i, 3).setValue(TextResource.localize("KDR001_57",
                        listAnnLeaGrant.get(i).getGrantDate().toString("yyyy/MM/dd")));
                // E1_3
                val vlaueE13 = listAnnLeaGrant.get(i).getGrantDays();
                Double days_Granted = checkShowAreaAnnualBreak1(
                        dataSource.getHolidaysRemainingManagement()) && vlaueE13 != 0 ?
                        (Double) vlaueE13 : null;
                cells.get(firstRow + 2 * i, 4).setValue(df.format(days_Granted.doubleValue()));
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
            val valueE14 = listAnnLeaGrant.stream().mapToDouble(AnnLeaGrantNumberImported::getRemainDay).sum();
            Double leave_DaysRemain = checkShowAreaAnnualBreak1(
                    dataSource.getHolidaysRemainingManagement()) ?
                    valueE14 : null;
            cells.get(firstRow, 5).setValue(df.format(leave_DaysRemain.doubleValue()));
            if (leave_DaysRemain != null && leave_DaysRemain < 0) {
                setForegroundRed(cells.get(firstRow, 5));
            }
            // E1_5 - value in 363- 年休_使用数_日数
            // 年休_使用数_日数 =  月度使用日数+ 付与後月度使用日数;
            // 月度使用日数 = 年月毎年休の集計結果．年休の集計結果．年休情報(期間終了日時点)．残数．年休(マイナスあり)．使用数．付与前;

            val use_date = rs363New.stream()
                    .map(e -> {
                        val i = e.getAggrResultOfAnnualLeave().getAsOfPeriodEnd()
                                .getRemainingNumber().getAnnualLeaveWithMinus().getUsedNumberInfo()
                                .getUsedNumberBeforeGrant().getUsedDays();
                        if (i.isPresent()) {
                            return i.get().v();
                        } else {
                            return (double) 0;
                        }
                    }).mapToDouble(e -> e).sum();
            // 付与後月度使用日数 = 年月毎年休の集計結果．年休の集計結果．年休情報(期間終了日時点)．残数．年休(マイナスあり)．使用数．付与後
            val use_after_grant = rs363New.stream()
                    .map(e -> e.getAggrResultOfAnnualLeave().getAsOfPeriodEnd()
                            .getRemainingNumber().getAnnualLeaveWithMinus().getUsedNumberInfo()
                            .getUsedNumberAfterGrantOpt()).mapToDouble(e ->
                            {
                                return e.filter(annualLeaveUsedNumber -> annualLeaveUsedNumber.getUsedDays().isPresent())
                                        .map(annualLeaveUsedNumber -> annualLeaveUsedNumber.getUsedDays().get().v()).orElseGet(() -> (double) 0);
                            }
                    ).sum();

            val valueE15 = use_date + use_after_grant; //
            Double used_Days = checkShowAreaAnnualBreak1(
                    dataSource.getHolidaysRemainingManagement()) ?
                    valueE15 : null;
            cells.get(firstRow, 6).setValue(df.format(used_Days.doubleValue()));
            // E1_6 - value in 363 - 年休_残数_日数
            // 年休_残数_日数: So luong con la :月度残日数: 年月毎年休の集計結果．年休の集計結果．年休情報(期間終了日時点)
            // ．残数．年休(マイナスあり)．残数．付与前	合計残日数
            val valueE16 = rs363New.stream()
                    .map(e -> e.getAggrResultOfAnnualLeave().getAsOfPeriodEnd()
                            .getRemainingNumber().getAnnualLeaveWithMinus().getRemainingNumberInfo()
                            .getRemainingNumberBeforeGrant().getTotalRemainingDays()).mapToDouble(e -> e != null ? e.v() : 0).sum(); //

            Double number_date_remain = checkShowAreaAnnualBreak1(
                    dataSource.getHolidaysRemainingManagement()) ?
                    valueE16 : null;
            cells.get(firstRow, 7).setValue(df.format(number_date_remain.doubleValue()));

            if (number_date_remain != null && number_date_remain < 0) {
                setForegroundRed(cells.get(firstRow, 7));
            }
            // E1_13; SUM VALUE 281
            Double valueE113 = listAnnLeaGrant.stream().mapToDouble(AnnLeaGrantNumberImported::getRemainTime).sum();
            Double leave_Hours = checkShowAreaAnnualBreak2(
                    dataSource.getHolidaysRemainingManagement()) ?
                    valueE113 : null;
            val e113 = leave_Hours != null ? convertToTime((int) (leave_Hours.doubleValue())) : "";
            cells.get(firstRow + 1, 5).setValue((e113));
            if (leave_Hours != null && leave_Hours < 0) {
                setForegroundRed(cells.get(firstRow + 1, 5));
            }
            // E1_14
            // 年休_使用数_時間 = 月度使用時間+付与後月度使用時間
            // 月度使用時間 = 年月毎年休の集計結果．年休の集計結果．年休情報(期間終了日時点)．残数．年休(マイナスあり)．使用数．付与前
            val use_time =
                    rs363New.stream()
                            .map((AggrResultOfAnnualLeaveEachMonthKdr e) -> {
                                val i = e.getAggrResultOfAnnualLeave().getAsOfPeriodEnd()
                                        .getRemainingNumber().getAnnualLeaveWithMinus().getUsedNumberInfo()
                                        .getUsedNumberBeforeGrant().getUsedTime();
                                if (i.isPresent()) {
                                    return i.get().v();
                                } else {
                                    return 0;
                                }
                            }).mapToDouble(e -> e).sum();
            // 付与後月度使用時間 =年月毎年休の集計結果．年休の集計結果．年休情報(期間終了日時点)．残数．年休(マイナスあり)．使用数．付与後
            val use_after_grant_time = rs363New.stream()
                    .map(e -> {
                                val i = e.getAggrResultOfAnnualLeave().getAsOfPeriodEnd()
                                        .getRemainingNumber().getAnnualLeaveWithMinus().getUsedNumberInfo()
                                        .getUsedNumberAfterGrantOpt();
                                if (i.isPresent() && i.get().getUsedTime().isPresent()) {
                                    return i.get().getUsedTime().get().v();
                                } else return 0;
                            }
                    ).mapToDouble(e -> e).sum();

            val valueE114 = use_time + use_after_grant_time;
            Double uses_Hours = checkShowAreaAnnualBreak2(dataSource.getHolidaysRemainingManagement()) ?
                    valueE114 : null;
            val e114 = uses_Hours != null ? convertToTime(uses_Hours.intValue()) : "";
            cells.get(firstRow + 1, 6).setValue(e114);
            // E1_15
            // 年休_残数_時間 :月度残時間
            // 月度残時間 = 年月毎年休の集計結果．年休の集計結果．年休情報(期間終了日時点)．残数．年休(マイナスあり)．残数．付与前
            val valueE115 = rs363New.stream()
                    .map(e -> e.getAggrResultOfAnnualLeave().getAsOfPeriodEnd()
                            .getRemainingNumber().getAnnualLeaveWithMinus().getRemainingNumberInfo()
                            .getRemainingNumberBeforeGrant().getTotalRemainingDays()).mapToDouble(e -> e != null ? e.v() : 0).sum();
            Double leave_RemainHours =
                    checkShowAreaAnnualBreak2(dataSource.getHolidaysRemainingManagement()) ?
                            valueE115 : null;
            val e115 = leave_RemainHours != null ? convertToTime(leave_RemainHours.intValue()) : "";
            cells.get(firstRow + 1, 7).setValue(e115);
            if (leave_RemainHours != null && leave_RemainHours < 0) {
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
                    val text2 = checkShowAreaAnnualBreak1(
                            dataSource.getHolidaysRemainingManagement())
                            ? TextResource.localize("KDR001_15")
                            : "";
                    cells.get(firstRow + 2, 9).setValue(text2);
                }
                //

                val value23 = item.getUsedDays() == 0 ? null : item.getUsedDays();
                Double e23 = checkShowAreaAnnualBreak1(
                        dataSource.getHolidaysRemainingManagement()) ?
                        value23 : null;
                // E2_3 当月より前, set lại giá trị cho cột này nếu bằng 0 thì không hiển thị ra
                val vl23 = e23 != null && e23 != 0 ? df.format(e23.doubleValue()) : "";
                cells.get(firstRow, 10 + totalMonth)
                        .setValue(vl23);
                // E2_4:月度使用時間 Update KDR ver 15
                val value24 = item.getUsedTime();
                val use_Time = checkShowAreaAnnualBreak2(dataSource.getHolidaysRemainingManagement()) ?
                        value24 : null;
                val e24 = use_Time != null && use_Time != 0 ? convertToTime((int) (use_Time)) : "";
                cells.get(firstRow + 1, 10 + totalMonth)
                        .setValue(e24);
                // E3_3 当月より前
                val valueE33 = item.getRemainingDays();
                val e33 = checkShowAreaAnnualBreak1(dataSource.getHolidaysRemainingManagement()) ?
                        valueE33 : null;
                cells.get(firstRow + 2, 10 + totalMonth)
                        .setValue(df.format(e33));
                // E3_4 当月より前
                val valueE34 = item.getRemainingTime();
                val e34 = checkShowAreaAnnualBreak2(dataSource.getHolidaysRemainingManagement()) ?
                        valueE34 : null;
                val vle34 = e34 != null ? convertToTime((int) (e34.doubleValue())) : "";
                cells.get(firstRow + 3, 10 + totalMonth)
                        .setValue(vle34);
                if (valueE33 != null && valueE33 < 0) {
                    setForegroundRed(cells.get(firstRow + 2, 10 + totalMonth));
                }
                if (valueE34 != null && valueE34 < 0) {
                    setForegroundRed(cells.get(firstRow + 3, 10 + totalMonth));
                }
            }
        }
        // Result RequestList363
        if (listAnnLeaveUsageStatusOfThisMonth != null && grantDate.isPresent()) {
            for (AggrResultOfAnnualLeaveEachMonthKdr item : rs363New) {
                if (currentMonth.compareTo(item.getYearMonth()) > 0) {
                    continue;
                }
                int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(), item.getYearMonth());
                if (maxRange < totalMonth || totalMonth < 0) {
                    continue;
                }
                if (listAnnLeaGrant != null && listAnnLeaGrant.size() == 1) {
                    cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 3, firstRow + 2, 2);
                    val text2 = checkShowAreaAnnualBreak1(
                            dataSource.getHolidaysRemainingManagement())
                            ? TextResource.localize("KDR001_15")
                            : "";
                    cells.get(firstRow + 2, 9).setValue(text2);
                }
                if (currentMonth.compareTo(item.getYearMonth()) == 0) {
                    val listPerMonth = listAnnLeaveUsage.stream().filter(e -> e.getYearMonth().equals(currentMonth))
                            .collect(Collectors.toList());
                    // 年休使用日数 実績値:
                    // BEFORE : 月度使用日数＋(255)年休利用状況.月度使用日数(※)
                    val use_date363opt = item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd()
                            .getRemainingNumber().getAnnualLeaveWithMinus().getUsedNumberInfo()
                            .getUsedNumberBeforeGrant().getUsedDays();

                    if (checkShowAreaAnnualBreak1(
                            dataSource.getHolidaysRemainingManagement())) {
                        Double value23bf = null;
                        if (use_date363opt.isPresent()) {
                            val use_date363 = use_date363opt.get().v();
                            val use_date255 = listPerMonth.stream().mapToDouble(AnnualLeaveUsageImported::getUsedDays).sum();

                            value23bf = use_date255 + use_date363;

                        }
                        val value23afOpt = item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd()
                                .getRemainingNumber().getAnnualLeaveWithMinus().getUsedNumberInfo()
                                .getUsedNumberAfterGrantOpt();
                        String value23af = null;
                        if (value23afOpt.isPresent() && value23afOpt.get().getUsedDays().get() != null) {
                            value23af = df.format(value23afOpt.get().getUsedDays().get().v());
                        }
                        if (value23af != null && value23bf != null ) {
                            val vl23 = df.format(value23bf) + "/" + value23af;
                            // E2_3 当月以降, set lại giá trị cho cột này nếu bằng 0 thì không hiển thị ra
                            cells.get(firstRow, 10 + totalMonth)
                                    .setValue(vl23);
                        } else {
                            val vl23bf = value23bf != null &&  value23bf != 0 ? df.format(value23bf) : "";
                            val vl23af = value23af != null &&  value23bf != 0 ? df.format(value23af) : "";
                            cells.get(firstRow, 10 + totalMonth)
                                    .setValue(vl23bf + vl23af);
                        }

                    }
                    // E3_3 当月以降 : 月度残日数
                    val value33 = item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd()
                            .getRemainingNumber().getAnnualLeaveWithMinus().getRemainingNumberInfo()
                            .getRemainingNumberBeforeGrant().getTotalRemainingDays() != null ?
                            item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd()
                                    .getRemainingNumber().getAnnualLeaveWithMinus().getRemainingNumberInfo()
                                    .getRemainingNumberBeforeGrant().getTotalRemainingDays()
                                    .v() : null;
                    Double e33 = checkShowAreaAnnualBreak1(
                            dataSource.getHolidaysRemainingManagement()) ?
                            value33 : null;
                    cells.get(firstRow + 2, 10 + totalMonth).setValue(e33 != null
                            && e33 != 0
                            ? df.format(e33) : null);
                    if (e33 != null && e33 < 0) {
                        setForegroundRed(cells.get(firstRow + 3, 10 + totalMonth));
                    }
                    // E2_4
                    if (checkShowAreaAnnualBreak1(
                            dataSource.getHolidaysRemainingManagement())) {
                        val use_time255 = listPerMonth.stream().mapToDouble(AnnualLeaveUsageImported::getUsedTime).sum();
                        // BEFORE: 付与前 = 月度使用時間＋(255)年休利用状況.月度使用時間(※)
                        val use_time363Opt = item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd()
                                .getRemainingNumber().getAnnualLeaveWithMinus().getUsedNumberInfo()
                                .getUsedNumberBeforeGrant().getUsedTime();
                        String e24bf = null;
                        String e24af = null;
                        val value243afOpt = item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd()
                                .getRemainingNumber().getAnnualLeaveWithMinus().getUsedNumberInfo()
                                .getUsedNumberAfterGrantOpt();
                        if (use_time363Opt.isPresent()) {
                            val use_time363 = use_time363Opt.get().v();
                            val value24bf = use_time255 + use_time363;
                            e24bf =value24bf!=0? convertToTime((int) value24bf): null;
                            // AF : 付与後月度使用時間 :付与前
                        }

                        if (value243afOpt.isPresent() && value243afOpt.get().getUsedTime().get() != null
                                && value243afOpt.get().getUsedTime().get().v() != 0) {
                            e24af = convertToTime((int) value243afOpt.get().getUsedTime().get().v());
                        }
                        if (e24af != null && e24bf != null) {
                            cells.get(firstRow + 1, 10 + totalMonth).setValue(e24bf + "/" + e24af);
                        } else {
                            e24bf = e24bf != null  ? e24bf : "";
                            e24af = e24af != null ? e24af : "";
                            cells.get(firstRow + 1, 10 + totalMonth).setValue(e24bf + e24af);
                        }
                    }
                    //E3-4:
                    // 年休残時間 実績値 : 月度残時間
                    val value34 = item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd()
                            .getRemainingNumber().getAnnualLeaveWithMinus().getRemainingNumberInfo()
                            .getRemainingNumberBeforeGrant().getTotalRemainingDays() != null ?
                            item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd()
                                    .getRemainingNumber().getAnnualLeaveWithMinus().getRemainingNumberInfo()
                                    .getRemainingNumberBeforeGrant().getTotalRemainingDays().v() : null;
                    Double e34 = checkShowAreaAnnualBreak1(
                            dataSource.getHolidaysRemainingManagement()) ?
                            value34 : null;

                    cells.get(firstRow + 3, 10 + totalMonth).setValue(e33 != null
                            && e34 != 0
                            ? convertToTime((int) e34.doubleValue()) : null);
                    if (!dataSource.isSameCurrentMonth()) {
                        setCurrentMonthBackground(cells.get(firstRow + 3, 10 + totalMonth));
                    }
                    if (e34 != null && e34 < 0) {
                        setForegroundRed(cells.get(firstRow + 3, 10 + totalMonth));
                    }
                }
                if (item.getYearMonth().compareTo(currentMonth) > 0) {
                    //E2_3 年休使用日数 実績値: 月度使用日数;
                    val value23 = item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd()
                            .getRemainingNumber().getAnnualLeaveWithMinus().getUsedNumberInfo()
                            .getUsedNumberBeforeGrant().getUsedDays().isPresent() ?
                            (item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd()
                                    .getRemainingNumber().getAnnualLeaveWithMinus()
                                    .getUsedNumberInfo() != null ?
                                    item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd()
                                            .getRemainingNumber().getAnnualLeaveWithMinus()
                                            .getUsedNumberInfo().getUsedNumberBeforeGrant().getUsedDays()
                                            .get().v() : null)

                            : null;

                    Double e23 = checkShowAreaAnnualBreak1(
                            dataSource.getHolidaysRemainingManagement()) ?
                            (Double) value23 : null;
                    cells.get(firstRow, 10 + totalMonth)
                            .setValue(e23 != null
                                    && e23 != 0
                                    ? df.format(e23) : null);
                    if (e23 != null && e23 < 0) {
                        setForegroundRed(cells.get(firstRow, 10 + totalMonth));
                    }
                    //E2_4: 月度使用時間
                    val value24 = item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd()
                            .getRemainingNumber().getAnnualLeaveWithMinus().getUsedNumberInfo()
                            .getUsedNumberBeforeGrant().getUsedTime().isPresent() ?
                            item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd()
                                    .getRemainingNumber().getAnnualLeaveWithMinus().getUsedNumberInfo()
                                    .getUsedNumberBeforeGrant().getUsedTime().get().v() : null;

                    Integer e24 = checkShowAreaAnnualBreak1(
                            dataSource.getHolidaysRemainingManagement()) ?
                            (Integer) value24 : null;
                    val vl24 = e24 != null && e24 != 0 ? convertToTime((int) (e24)) : "";

                    cells.get(firstRow + 1, 10 + totalMonth).setValue(vl24);
                    if (value24 != null && value24 < 0) {
                        setForegroundRed(cells.get(firstRow + 1, 10 + totalMonth));
                    }
                    // Update KDR 001 : 値＝(クリア)/背景色＝グレー;
                    setBackgroundGray(cells.get(firstRow + 2, 10 + totalMonth));
                    // Update KDR 001 : 値＝(クリア)/背景色＝グレー;
                    setBackgroundGray(cells.get(firstRow + 3, 10 + totalMonth));
                }
            }
        }
        firstRow += totalAddRows;
        for (int index = 0; index < NUMBER_COLUMN - 2; index++) {
            setBottomBorderStyle(cells.get(firstRow - 1, 2 + index));
        }
        return firstRow;
    }

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
            val h12 = df.format(reserveHolidayImported.getGrantNumber().doubleValue());
            cells.get(firstRow, 4).setValue(h12);
            // H1_3
            cells.get(firstRow, 5).setValue(df.format(reserveHolidayImported.getStartMonthRemain().doubleValue()));
            if (reserveHolidayImported.getStartMonthRemain() < 0) {
                setForegroundRed(cells.get(firstRow, 5));
            }
            // H1_4
            cells.get(firstRow, 6).setValue(df.format(reserveHolidayImported.getUsedNumber().doubleValue()));
            // H1_5
            cells.get(firstRow, 7).setValue(df.format(reserveHolidayImported.getRemainNumber().doubleValue()));
            if (reserveHolidayImported.getRemainNumber() < 0) {
                setForegroundRed(cells.get(firstRow, 7));
            }
            // H1_6
            cells.get(firstRow, 8).setValue(df.format(reserveHolidayImported.getUndigestNumber().doubleValue()));

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
                                && reservedYearHolidayItem.getUsedDays() == 0 ? null : df.format(reservedYearHolidayItem.getUsedDays().doubleValue()));
                // H2_4 当月より前
                cells.get(firstRow + 1, 10 + totalMonth).setValue(df.format(reservedYearHolidayItem.getRemainingDays().doubleValue()));
                if (reservedYearHolidayItem.getRemainingDays() < 0) {
                    setForegroundRed(cells.get(firstRow + 1, 10 + totalMonth));
                }
                if (currentMonth.compareTo(reservedYearHolidayItem.getYearMonth()) == 0) {
                    if (!dataSource.isSameCurrentMonth()) {
                        setCurrentMonthBackground(cells.get(firstRow, 10 + totalMonth));
                        setCurrentMonthBackground(cells.get(firstRow + 1, 10 + totalMonth));
                    }
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
                        && rsvLeaUsedCurrentMonItem.getUsedNumber() == 0 ? null : df.format(rsvLeaUsedCurrentMonItem.getUsedNumber().doubleValue()));
                // H2_4 当月以降
                setBackgroundGray(cells.get(firstRow + 1, 10 + totalMonth));
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
        NumberFormat df = new DecimalFormat("#0.0");

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
                if (maxRange < totalMonth || totalMonth < 0) {
                    continue;
                }
                // Curren month
                if (statusHolidayItem.getYm().compareTo(currentMonth) == 0) {
                    //I2_3
                    String occurrence = "";
                    if (check.isSubstituteHolidaySetting()) {
                        occurrence = statusHolidayItem.getOccurrenceDays() == null||statusHolidayItem.getOccurrenceDays() == 0 ? "" :
                               df.format( statusHolidayItem.getOccurrenceDays().doubleValue());
                    } else if (check.isSubstituteHolidaySetting() && check.isHourlyLeaveSetting()) {
                        occurrence = (statusHolidayItem.getOccurrenceTimes() == null||statusHolidayItem.getOccurrenceTimes()==0 ? "" :
                                convertToTime(statusHolidayItem.getOccurrenceTimes()));
                    }

                    cells.get(firstRow, 10 + totalMonth).setValue(occurrence);
                    // I3_3
                    String use = "";
                    if (check.isSubstituteHolidaySetting()) {
                        use = statusHolidayItem.getUseDays() == null||statusHolidayItem.getUseDays()==0 ? "" :
                                df.format(statusHolidayItem.getUseDays().doubleValue());
                    }
                    if (check.isSubstituteHolidaySetting() && check.isHourlyLeaveSetting()) {
                        use = statusHolidayItem.getUseTimes() == null||statusHolidayItem.getUseTimes()==0 ? "" :
                                convertToTime(statusHolidayItem.getUseTimes());
                    }
                    cells.get(firstRow + 1, 10 + totalMonth)
                            .setValue(use);
                    if (checkTakeABreak_02(dataSource.getHolidaysRemainingManagement())) {
                        // I4_3 代休_未消化_日数, set lại giá trị cho cột này nếu bằng 0 thì không hiển thị ra
                        String unUsed = "";
                        if (check.isSubstituteHolidaySetting()) {
                            unUsed = statusHolidayItem.getUnUsedDays() == null||statusHolidayItem.getUnUsedDays()==0 ? "" :
                                    df.format(statusHolidayItem.getUnUsedDays().doubleValue());
                        } else if (check.isSubstituteHolidaySetting() && check.isHourlyLeaveSetting()) {
                            unUsed = (statusHolidayItem.getUnUsedTimes() == null||statusHolidayItem.getUnUsedTimes()==0 ? "" :
                                   convertToTime( statusHolidayItem.getUnUsedTimes()));
                        }
                        cells.get(firstRow + 2, 10 + totalMonth)
                                .setValue(unUsed);
                    }
                    if (checkTakeABreak_03(dataSource.getHolidaysRemainingManagement())) {
                        // I5_3 代休_残数_日数
                        String remain = "";
                        if (check.isSubstituteHolidaySetting() && check.isHourlyLeaveSetting()) {
                            remain = (statusHolidayItem.getRemainTimes() == null ? "0" : convertToTime(statusHolidayItem.getRemainTimes()));
                        }
                        cells.get(firstRow + 3, 10 + totalMonth)
                                .setValue(remain);
                    }
                }

                // BEFORE
                else if (statusHolidayItem.getYm().compareTo(currentMonth) < 0) {

                        // I2_3
                        String occurrence = "";
                        if (check.isSubstituteHolidaySetting()) {
                            occurrence = statusHolidayItem.getOccurrenceDays() == null||statusHolidayItem.getOccurrenceDays()==0 ? "" :
                                    df.format(statusHolidayItem.getOccurrenceDays().doubleValue());
                        } else if (check.isSubstituteHolidaySetting() && check.isHourlyLeaveSetting()) {
                            occurrence = (statusHolidayItem.getOccurrenceTimes() == null||statusHolidayItem.getOccurrenceTimes()==0 ? "" :
                                   convertToTime( statusHolidayItem.getOccurrenceTimes()));
                        }

                        cells.get(firstRow, 10 + totalMonth).setValue(occurrence);
                        // I3_3
                        String use = "";

                        if (check.isSubstituteHolidaySetting()) {
                            use = statusHolidayItem.getUseDays() == null||statusHolidayItem.getUseDays()==0 ? "" :
                                    df.format(statusHolidayItem.getUseDays().doubleValue());
                        } else if (check.isHourlyLeaveSetting() && check.isHourlyLeaveSetting()) {
                            use = (statusHolidayItem.getUseTimes() == null||statusHolidayItem.getUseTimes()==0 ? "" :
                                    convertToTime(statusHolidayItem.getUseTimes()));
                        }

                        cells.get(firstRow + 1, 10 + totalMonth)
                                .setValue(use);


                        // I4_3
                        String unUsed = "";

                        if (check.isSubstituteHolidaySetting()) {
                            unUsed = statusHolidayItem.getUnUsedDays() == null||statusHolidayItem.getUnUsedDays()==0 ? "" :
                                   df.format( statusHolidayItem.getUnUsedDays().doubleValue());
                        } else if (check.isSubstituteHolidaySetting() && check.isHourlyLeaveSetting()) {
                            unUsed = (statusHolidayItem.getUnUsedTimes() == null||statusHolidayItem.getUnUsedTimes()==0 ? "" :
                                   convertToTime( statusHolidayItem.getUnUsedTimes()));
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
                            remain = statusHolidayItem.getRemainDays() == null ? "0" :df.format( statusHolidayItem.getRemainDays().doubleValue());
                        } else if (check.isSubstituteHolidaySetting() && check.isHourlyLeaveSetting()) {
                            remain = (statusHolidayItem.getRemainTimes() == null ? "0" : convertToTime(statusHolidayItem.getRemainTimes()));
                        }
                        cells.get(firstRow + 3, 10 + totalMonth)
                                .setValue(remain);

                } else if (statusHolidayItem.getYm().compareTo(currentMonth) > 0) {

                        // I2_3
                        String occurrence = "";
                        if (check.isSubstituteHolidaySetting()) {
                            occurrence = statusHolidayItem.getOccurrenceDays() == null||statusHolidayItem.getOccurrenceDays()==0 ? "" :
                                   df.format( statusHolidayItem.getOccurrenceDays().doubleValue());
                        } else if (check.isSubstituteHolidaySetting() && check.isHourlyLeaveSetting()) {
                            occurrence = (statusHolidayItem.getOccurrenceTimes() == null||statusHolidayItem.getOccurrenceTimes()==0 ? "" :
                                    convertToTime(statusHolidayItem.getOccurrenceTimes()));
                        }
                        cells.get(firstRow, 10 + totalMonth).setValue(occurrence);
                        // I3_3 代休_使用_日数時間数 実績値, set lại giá trị cho cột này nếu bằng 0 thì không hiển thị ra
                        String use = "";
                        if (check.isSubstituteHolidaySetting()) {
                            use = statusHolidayItem.getUseDays() == null||statusHolidayItem.getUseDays()==0 ? "" :
                                    df.format(statusHolidayItem.getUseDays().doubleValue());
                        }
                        if (check.isSubstituteHolidaySetting() && check.isHourlyLeaveSetting()) {
                            use = statusHolidayItem.getUseTimes() == null||statusHolidayItem.getUseTimes()==0 ? "" :
                                   convertToTime( statusHolidayItem.getUseTimes());
                        }
                        cells.get(firstRow + 1, 10 + totalMonth)
                                .setValue(use);
                    }
                }

        }
        // 月初残数

        val monRemainingDate = substituteHolidayAggrResult.getCarryoverDay().v();
        // 時間
        val monRemainingTime = substituteHolidayAggrResult.getCarryoverTime().v();
        String i12 = "";
        if (check.isSubstituteHolidaySetting() && monRemainingDate != null) {
            i12 = monRemainingDate == 0 ? "" : monRemainingDate.toString();
        } else if (check.isSubstituteHolidaySetting() && check.isHourlyLeaveSetting() && monRemainingTime != null) {
            i12 = monRemainingTime == 0 ? "" : monRemainingTime.toString();
        }
        cells.get(firstRow, 5).setValue(i12);
        if (check.isSubstituteHolidaySetting() && monRemainingDate != null && monRemainingDate < 0) {
            setForegroundRed(cells.get(firstRow, 5));
        }
        if (check.isSubstituteHolidaySetting() && check.isHourlyLeaveSetting()
                && monRemainingTime != null && monRemainingTime < 0) {
            setForegroundRed(cells.get(firstRow, 5));
        }
        //I1_3 使用数
        // 日数
        val dateUse = substituteHolidayAggrResult.getDayUse().v();
        // 時間
        val timeUse = substituteHolidayAggrResult.getTimeUse().v();
        // 代休+時間代休の場合
        String i13 = "";
        if (check.isSubstituteHolidaySetting() && dateUse != null) i13 = dateUse.toString();
        else if (check.isSubstituteHolidaySetting() && check.isHourlyLeaveSetting() && timeUse != null) {
            i13 = Objects.requireNonNull(monRemainingTime == 0 ? "" : monRemainingTime).toString();
        }
        cells.get(firstRow, 6).setValue(i13);
        // I1_4 残数
        // 日数
        val remainDay = substituteHolidayAggrResult.getRemainDay().v();
        //時間
        val remainTime = substituteHolidayAggrResult.getRemainTime().v();
        // 代休+時間代休の場合
        String i14 = "";
        if (check.isSubstituteHolidaySetting() && remainDay != null && remainDay != 0) {
            i14 = Objects.requireNonNull(remainDay).toString();
        } else if (check.isSubstituteHolidaySetting() && check.isHourlyLeaveSetting() && (remainTime != null)
                && (remainTime.doubleValue() != 0)) {
            i14 = Objects.requireNonNull(remainTime).toString();
        }
        cells.get(firstRow, 7).setValue(i14);
        if (check.isSubstituteHolidaySetting() && remainDay != null && remainDay != 0 && remainDay < 0) {
            setForegroundRed(cells.get(firstRow, 7));
        }
        if (check.isSubstituteHolidaySetting() && check.isHourlyLeaveSetting() && (remainTime != null)
                && remainTime < 0) {
            setForegroundRed(cells.get(firstRow, 7));
        }

        // I1_5: 未消化数
        //日数
        val unusedDay = substituteHolidayAggrResult.getUnusedDay().v();
        //時間
        val unusedTime = substituteHolidayAggrResult.getUnusedTime().v();
        //代休+時間代休の場合
        String i15 = "";
        if (check.isSubstituteHolidaySetting() && (unusedDay != null) && (unusedDay != 0)) {
            i15 = Objects.requireNonNull(unusedDay).toString();
        } else if (check.isSubstituteHolidaySetting() && check.isHourlyLeaveSetting() && (unusedTime != null) && (unusedTime != 0)) {
            i15 = Objects.requireNonNull(unusedTime).toString();
        }
        cells.get(firstRow, 8).setValue(i15);

        if (check.isSubstituteHolidaySetting() && unusedDay != null && unusedDay > 0) {
            setForegroundRed(cells.get(firstRow, 8));
        }
        if (check.isSubstituteHolidaySetting() && check.isHourlyLeaveSetting() && (unusedTime != null)
                && unusedTime > 0) {
            setForegroundRed(cells.get(firstRow, 8));
        }

        firstRow += totalRows;
        for (int index = 0; index < NUMBER_COLUMN - 2; index++) {
            setBottomBorderStyle(cells.get(firstRow - 1, 2 + index));
        }
        return firstRow;
    }

    private int printPauseHoliday(Cells cells, int firstRow, HolidaysRemainingEmployee employee,
                                  HolidayRemainingDataSource dataSource) throws Exception {
        // 振休
        NumberFormat df = new DecimalFormat("#0.0");
        if (!checkJ1(dataSource,employee)) {
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
                cells.get(firstRow, 10 + totalMonth).setValue(statusOfHDItem.getOccurredDay() == null ||
                        statusOfHDItem.getOccurredDay() == 0.0 ? null : df.format(statusOfHDItem.getOccurredDay().doubleValue()));
                // J2_6 振休_使用, set lại giá trị cho cột này nếu bằng 0 thì không hiển thị ra
                cells.get(firstRow + 1, 10 + totalMonth).setValue(statusOfHDItem.getUsedDays() == null ||
                        statusOfHDItem.getUsedDays() == 0.0 ? null : df.format(statusOfHDItem.getUsedDays().doubleValue()));
                if (isUndigestedPause) {
                    // J2_7 振休_未消化, set lại giá trị cho cột này nếu bằng 0 thì không hiển thị ra
                    cells.get(rowIndexUndigestedPause, 10 + totalMonth).setValue(statusOfHDItem.getUnUsedDays() == null
                            || statusOfHDItem.getUnUsedDays() == 0.0 ? null : df.format(statusOfHDItem.getUnUsedDays().doubleValue()));
                    if (statusOfHDItem.getUnUsedDays() != null && statusOfHDItem.getUnUsedDays() > 0) {
                        setForegroundRed(cells.get(rowIndexUndigestedPause, 10 + totalMonth));
                    }
                }
                if (isNumberRemainingPause) {
                    // J2_8 振休_残数
                    cells.get(rowIndexNumberRemainingPause, 10 + totalMonth)
                            .setValue(df.format(statusOfHDItem.getRemainingDays().doubleValue()));
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
                                && holidayRemainItem.getMonthOccurrence() == 0.0 ? null :
                                df.format(holidayRemainItem.getMonthOccurrence().doubleValue()));
                        // J2_6 振休_使用, set lại giá trị cho cột này nếu bằng 0 thì không hiển thị ra
                        cells.get(firstRow + 1, 10 + totalMonth).setValue(holidayRemainItem.getMonthUse() != null
                                && holidayRemainItem.getMonthUse() == 0.0 ? null :
                                df.format(holidayRemainItem.getMonthUse().doubleValue()));
                        if (currentMonth.compareTo(holidayRemainItem.getYm()) == 0) {
                            if (isUndigestedPause) {
                                // J2_7 振休_未消化, set lại giá trị cho cột này nếu bằng 0 thì không hiển thị ra
                                cells.get(rowIndexUndigestedPause, 10 + totalMonth).setValue(
                                        holidayRemainItem.getMonthExtinction() != null
                                                && holidayRemainItem.getMonthExtinction() == 0 ? null :
                                                df.format(holidayRemainItem.getMonthExtinction().doubleValue()));
                                if (holidayRemainItem.getMonthExtinction() != null
                                        && holidayRemainItem.getMonthExtinction() > 0) {
                                    setForegroundRed(cells.get(rowIndexUndigestedPause, 10 + totalMonth));
                                }
                            }
                            if (isNumberRemainingPause) {
                                // J2_8 振休_残数
                                cells.get(rowIndexNumberRemainingPause, 10 + totalMonth)
                                        .setValue(df.format(holidayRemainItem.getMonthEndRemain().doubleValue()));
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
        cells.get(firstRow, 5).setValue(currentHolidayRemainLeft.getMonthStartRemain() == 0 ? "" :
                df.format(currentHolidayRemainLeft.getMonthStartRemain().doubleValue()));
        if (currentHolidayRemainLeft.getMonthStartRemain() < 0) {
            setForegroundRed(cells.get(firstRow, 5));
        }
        // J1_3 振休_使用数
        cells.get(firstRow, 6).setValue(currentHolidayRemainLeft.getMonthUse() == 0 ? "" :
                df.format(currentHolidayRemainLeft.getMonthUse().doubleValue()));
        if (isPauseItem) {
            // J1_4 振休_残数
            cells.get(firstRow, 7).setValue(currentHolidayRemainLeft.getMonthEndRemain() == 0 ? "" :
                    df.format(currentHolidayRemainLeft.getMonthEndRemain().doubleValue()));
            if (currentHolidayRemainLeft.getMonthEndRemain() < 0) {
                setForegroundRed(cells.get(firstRow, 7));
            }
        }
        if (isUndigestedPause) {
            // J1_5 振休_未消化
            cells.get(firstRow, 8).setValue(currentHolidayRemainLeft.getMonthExtinction() == 0 ? "" :
                    df.format(currentHolidayRemainLeft.getMonthExtinction().doubleValue()));
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
        val holidayRemainingManagement = dataSource.getHolidaysRemainingManagement().getListItemsOutput();

        if (!holidayRemainingManagement.getOutOfTime().isOvertimeItem()) {
            return firstRow;
        }

        if (!dataSource.getVariousVacationControl().isCom60HourVacationSetting()) {
            return firstRow;
        }
        //※2
        boolean isUndigestedPause = holidayRemainingManagement.getOutOfTime().isOvertimeOverUndigested();
        boolean isOvertimeRemaining = holidayRemainingManagement.getOutOfTime().isOvertimeRemaining();
        int totalRows = 2;
        int rowIndexUndigestedPause = 0;
        int rowIndexNumberRemainingPause = 0;
        cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 20, firstRow, 2);
        // K1_1
        cells.get(firstRow, 2).setValue(TextResource.localize("KDR001_79"));
        val hdRemainingInfor = dataSource.getMapEmployees().get(employee.getEmployeeId()).getHolidayRemainingInfor();
        if (hdRemainingInfor == null) {
            return firstRow + 2;
        }
        val data677 = hdRemainingInfor.getAggrResultOfHolidayOver60h();
        // K2_1
        cells.get(firstRow, 9).setValue(TextResource.localize("KDR001_16"));
        // K2_2
        cells.get(firstRow + 1, 9).setValue(TextResource.localize("KDR001_17"));
        if (isUndigestedPause) {
            rowIndexUndigestedPause = firstRow + totalRows;
            cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 22, rowIndexUndigestedPause, 1);
            // K2_3
            cells.get(rowIndexUndigestedPause, 9).setValue(TextResource.localize("KDR001_11"));
            totalRows += 1;
        }
        if (isOvertimeRemaining) {
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
//        for (int i = 0; i <= totalMonths(dataSource.getStartMonth().yearMonth(),
//                dataSource.getEndMonth().yearMonth()); i++) {
//            if (dataSource.getStartMonth().addMonths(i).yearMonth().compareTo(currentMonth) > 0) {
//                if (isUndigestedPause) {
//                    setBackgroundGray(cells.get(rowIndexUndigestedPause, 10 + i));
//                }
//                if (isNumberRemainingPause) {
//                    setBackgroundGray(cells.get(rowIndexNumberRemainingPause, 10 + i));
//                }
//            }
//            if (!dataSource.isSameCurrentMonth()
//                    && dataSource.getStartMonth().addMonths(i).yearMonth().compareTo(currentMonth) == 0) {
//                setCurrentMonthBackground(cells.get(firstRow, 10 + i));
//                setCurrentMonthBackground(cells.get(firstRow + 1, 10 + i));
//                if (isUndigestedPause) {
//                    setCurrentMonthBackground(cells.get(rowIndexUndigestedPause, 10 + i));
//                }
//                if (isNumberRemainingPause) {
//                    setCurrentMonthBackground(cells.get(rowIndexNumberRemainingPause, 10 + i));
//                }
//            }
//        }
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
        val rs363New = hdRemainingInfor.getRs363New();
        cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 7, firstRow, 2);
        //
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
            if (maxTimes != null && maxTimes != 0) {
                textMaxtime = maxTimes.toString() + TextResource.localize("KDR001_75");
            }
            cells.get(firstRow, 4).setValue(textMaxtime);
            // F1_3
            // 半日年休_月初残回数
            //　半日年休現在状況.月初残回数←半日年休上限．使用回数
            Integer remainingTimes = null;
            if (numberOfGrantesOpt.isPresent() && numberOfGrantesOpt.get().getRemainingTimes().v() != 0) {
                String valueF13 = "";
                //残回数
                remainingTimes = numberOfGrantesOpt.get().getRemainingTimes().v();
                valueF13 = remainingTimes.toString()
                        + TextResource.localize("KDR001_75");
                cells.get(firstRow, 5)
                        .setValue(valueF13);
                if (remainingTimes < 0) {
                    setForegroundRed(cells.get(firstRow, 5));
                }
            }
            //F14: 半日年休_使用回数 = 月度使用回数+付与後月度使用回数
            String valueF14 = "";
            String valueF15 = "";
            if (!rs363New.isEmpty()) {
                // 月度使用回数 :年月毎年休の集計結果．年休の集計結果．年休情報(期間終了日時点)．残数．半日年休(マイナスあり)
                val number_of_use = rs363New.stream()
                        .map(e ->
                                {
                                    val i = e.getAggrResultOfAnnualLeave().getAsOfPeriodEnd()
                                            .getRemainingNumber().getHalfDayAnnualLeaveNoMinus();
                                    if (i.isPresent()) {
                                        return
                                                i.get().getUsedNum().getTimes().v();
                                    } else {
                                        return 0;
                                    }
                                }
                        ).mapToDouble(e -> e).sum();
                // 付与後月度使用回数: Mô tả của tài liệu là bỏ trống.
                val uses_after_granting = 0;
                valueF14 = number_of_use == 0 ? "" : (number_of_use + uses_after_granting + TextResource.localize("KDR001_75"));
                // F1_5: 半日年休_残回数 : 月度残回数: 年月毎年休の集計結果．年休の集計結果．年休情報(期間終了日時点)．残数．半日年休(マイナスあり)
                val number_of_date_remain = rs363New.stream()
                        .map(e ->
                                {
                                    val i = e.getAggrResultOfAnnualLeave().getAsOfPeriodEnd()
                                            .getRemainingNumber().getHalfDayAnnualLeaveNoMinus();
                                    if (i.isPresent()) {
                                        return
                                                i.get().getRemainingNum().getTimes().v();
                                    } else {
                                        return 0;
                                    }
                                }
                        ).mapToDouble(e -> e).sum();
                valueF15 = number_of_date_remain == 0 ? "" : (number_of_date_remain + TextResource.localize("KDR001_75"));
                // F1_4:
                if (number_of_date_remain < 0) {
                    setForegroundRed(cells.get(firstRow, 7));
                }
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
                if (item.getYearMonth().compareTo(currentMonth) > 0) {
                    continue;
                }
                int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(), item.getYearMonth());
                if (maxRange < totalMonth || totalMonth < 0) {
                    continue;
                }
                if (item.getYearMonth().compareTo(currentMonth) < 0) {
                    val number_use = item.getNumOfuses();
                    if (number_use != null) {
                        cells.get(firstRow, 10 + totalMonth)
                                .setValue(number_use + TextResource.localize("KDR001_75"));
                        if (number_use < 0) {
                            setForegroundRed(cells.get(firstRow + 1, 10 + totalMonth));
                        }
                    }
                    val number_remain = item.getNumOfRemain();//
                    if (number_remain != null) {
                        cells.get(firstRow + 1, 10 + totalMonth)
                                .setValue(number_remain + TextResource.localize("KDR001_75"));
                        if (number_remain < 0) {
                            setForegroundRed(cells.get(firstRow + 1, 10 + totalMonth));
                        }
                    }
                }
            }
        }
        // Result RequestList363
        for (AggrResultOfAnnualLeaveEachMonthKdr item : rs363New) {
            if (item.getYearMonth().compareTo(currentMonth) < 0) {
                continue;
            }
            int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(), item.getYearMonth());
            if (maxRange < totalMonth || totalMonth < 0) {
                continue;
            }
            if (item.getYearMonth().compareTo(currentMonth) == 0) {
                if (!dataSource.isSameCurrentMonth()) {
                    setCurrentMonthBackground(cells.get(firstRow, 10 + totalMonth));
                    setCurrentMonthBackground(cells.get(firstRow + 1, 10 + totalMonth));
                }
                Integer number_of_use = null;

                //F2_3: 半日年休_使用回数 実績値 : 月度使用回数＋(255)半日年休利用状況.月度使用回数(※)
                // 月度使用回数 =
                val number_of_useOpt363 = item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd()
                        .getRemainingNumber().getHalfDayAnnualLeaveNoMinus();
                if (number_of_useOpt363.isPresent()) {

                    number_of_use = number_of_useOpt363.get().getUsedNum().getTimes().v();
                    if (listAnnLeaveUsage != null && !listAnnLeaveUsage.isEmpty()) {
                        val list255 = listAnnLeaveUsage.stream()
                                .filter(e -> e.getYearMonth().compareTo(currentMonth) == 0 && e.getNumOfuses() != null)
                                .collect(Collectors.toList()); //;
                        val number_of_useOpt255 = list255.stream()
                                .mapToInt(AnnualLeaveUsageImported::getNumOfuses).sum();
                        number_of_use += number_of_useOpt255;
                    }
                    cells.get(firstRow, 10 + totalMonth)
                            .setValue(number_of_use.toString() + TextResource.localize("KDR001_75"));
                }
                //F2_4:半日年休_月度残回数 実績値:月度残回数
                val number_of_date_remainOpt = item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd()
                        .getRemainingNumber().getHalfDayAnnualLeaveNoMinus();
                if (number_of_date_remainOpt.isPresent() && number_of_date_remainOpt.get().getRemainingNum() != null &&
                        number_of_date_remainOpt.get().getRemainingNum().getTimes().v() != 0) {
                    val number_of_date_remain = number_of_date_remainOpt.get().getRemainingNum().getTimes();
                    cells.get(firstRow + 1, 10 + totalMonth)
                            .setValue(number_of_date_remain.toString() + TextResource.localize("KDR001_75"));
                    if (number_of_date_remain.v() < 0) {
                        setForegroundRed(cells.get(firstRow + 1, 10 + totalMonth));
                    }
                }
            }
            if (item.getYearMonth().compareTo(currentMonth) > 0) {
                String number_of_use = "";
                // F2_3 月度使用回数,
                val number_of_useOpt363 = item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd()
                        .getRemainingNumber().getHalfDayAnnualLeaveNoMinus();
                if (number_of_useOpt363.isPresent()
                        && (item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd().getRemainingNumber().getHalfDayAnnualLeaveNoMinus().isPresent())
                        && (item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd().getRemainingNumber().getHalfDayAnnualLeaveNoMinus().get().getUsedNum() != null)
                        && (number_of_useOpt363.get().getUsedNum().getTimes().v() != 0)) {
                    number_of_use = number_of_useOpt363.get().getUsedNum().getTimes().v().toString();
                    cells.get(firstRow, 10 + totalMonth)
                            .setValue(number_of_use + TextResource.localize("KDR001_75"));

                }
                //F2_4 BLANK
                setBackgroundGray(cells.get(firstRow + 1, 10 + totalMonth));
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
        val data363 = hdRemainingInfor.getRs363New();

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
                val g12 = valueG12 != null ? convertToTime((int) (valueG12)) : "";
                cells.get(firstRow, 4).setValue(g12);
                val valueG13 = timeAnnualLeaveMax.get().getRemainingMinutes().valueAsMinutes();
                val g13 = convertToTime((int) (valueG13));
                cells.get(firstRow, 5).setValue(g13);
                if (valueG13 < 0) {
                    setForegroundRed(cells.get(firstRow, 4));
                }
            }
            //G1_4
            val valueG14 = "";
            //G1_4
            cells.get(firstRow, 6).setValue(valueG14);

        }


        val valueG15 = data363.stream()
                .map(e -> e.getAggrResultOfAnnualLeave().getAsOfPeriodEnd()
                        .getRemainingNumber().getAnnualLeaveWithMinus().getRemainingNumberInfo()
                        .getRemainingNumberBeforeGrant().getTotalRemainingDays()).mapToDouble(e -> e != null ? e.v() : 0).sum();
        //G1_5 : 月度残時間
        val g15 = convertToTime((int) (valueG15));
        cells.get(firstRow, 7).setValue(valueG15 == 0 ? "" : g15);
        if (valueG15 < 0) {
            setForegroundRed(cells.get(firstRow, 6));
        }
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
                // G2_2 月度残時間,

                val g22 = convertToTime((int) (valueG22BeforeCurent));
                cells.get(firstRow, 10 + totalMonth).setValue(g22);
                if (valueG22BeforeCurent < 0) {
                    setForegroundRed(cells.get(firstRow, 10 + totalMonth));
                }
            }
        }
        // Result RequestList363 // AFTER AND CURRENT
        if (dataSource.getMapEmployees().get(employee.getEmployeeId()).getHolidayRemainingInfor().getGrantDate().isPresent()) {
            for (AggrResultOfAnnualLeaveEachMonthKdr item : data363) {
                if (item.getYearMonth().compareTo(currentMonth) < 0) {
                    continue;
                }
                int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(), item.getYearMonth());
                if (maxRange < totalMonth || totalMonth < 0) {
                    continue;
                }

                if (item.getYearMonth().equals(currentMonth)) {
                    // G2_2: 月度残時間:
                    val monthlyRemainingTime = item.getAggrResultOfAnnualLeave().getAsOfPeriodEnd()
                            .getRemainingNumber().getAnnualLeaveWithMinus().getRemainingNumberInfo()
                            .getRemainingNumberBeforeGrant().getTotalRemainingDays().v();
                    //月度使用回数
                    val g22 = convertToTime((int) (monthlyRemainingTime.doubleValue()));
                    cells.get(firstRow, 10 + totalMonth).setValue(g22);

                    if (monthlyRemainingTime < 0) {
                        setForegroundRed(cells.get(firstRow, 10 + totalMonth));
                    }

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
        NumberFormat df = new DecimalFormat("#0.0");
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
            val listFrameNo = specialHolidayOpt.get().getTargetItem().getFrameNo();

            val itemFrame = specialHolidayFrameRepository.findHolidayFrameByListFrame(AppContexts.user().companyId(),listFrameNo)
                    .stream().filter(e->e.getTimeMngAtr().value == NotUseAtr.USE.value);
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
                // RS263 NEW
                val rs263 = hdRemainingInfor.getGetSpeHdOfConfMonVer2()
                        .stream().filter(e -> e.getSpecialHolidayCd() == specialHolidayCode).collect(Collectors.toList());
                // ドメインモデル「特別休暇付与残数データ」を取得
                List<SpecialLeaveGrantRemainingData> listSpecialLeaveGrant = specialLeaveGrantRepository
                        .getAllByExpStatus(employee.getEmployeeId(), specialHolidayCode,
                                LeaveExpirationStatus.AVAILABLE.value);

                // 全てのドメインモデル「特別休暇付与残数データ」の残時間を合計
                Double dayNumbers = listSpecialLeaveGrant.stream()
//						.mapToDouble(item -> item.getDetails().getRemainingNumber().getDayNumberOfRemain().v()).sum();
                        .mapToDouble(item -> item.getDetails().getRemainingNumber().getDays() != null ?
                                item.getDetails().getRemainingNumber().getDays().v() : 0).sum();
                Double timeNumbers = listSpecialLeaveGrant.stream()
//						.mapToDouble(item -> item.getDetails().getRemainingNumber().getDayNumberOfRemain().v()).sum();
                        .mapToDouble(item -> item.getDetails().getRemainingNumber().getMinutes().isPresent() ?
                                item.getDetails().getRemainingNumber().getMinutes().get().v() : 0).sum();

                // 全てのドメインモデル「特別休暇付与残数データ」の残時間を合計
                Double dayUse = listSpecialLeaveGrant.stream()
//						.mapToDouble(item -> item.getDetails().getRemainingNumber().getDayNumberOfRemain().v()).sum();
                        .mapToDouble(item -> item.getDetails().getUsedNumber().getDays() != null ?
                                item.getDetails().getUsedNumber().getDays().v() : 0).sum();
                Double timeUse = listSpecialLeaveGrant.stream()
//						.mapToDouble(item -> item.getDetails().getRemainingNumber().getDayNumberOfRemain().v()).sum();
                        .mapToDouble(item -> item.getDetails().getUsedNumber().getMinutes().isPresent() ?
                                item.getDetails().getUsedNumber().getMinutes().get().v() : 0).sum();


                    // M1_2 特別休暇１_付与数日数

                    val vlm12 = listSpecialLeaveGrant.stream()
//						.mapToDouble(item -> item.getDetails().getRemainingNumber().getDayNumberOfRemain().v()).sum();
                            .mapToDouble(item -> item.getDetails().getGrantNumber().getDays() != null ?
                                    item.getDetails().getGrantNumber().getDays().v() : 0).sum();

                    val vlm16 = listSpecialLeaveGrant.stream()
//						.mapToDouble(item -> item.getDetails().getRemainingNumber().getDayNumberOfRemain().v()).sum();
                            .mapToDouble(item -> item.getDetails().getGrantNumber().getMinutes().isPresent() ?
                                    item.getDetails().getGrantNumber().getMinutes().get().v() : 0).sum();

                    cells.get(firstRow, 4)
                            .setValue(vlm12 == 0 ? "" : df.format(vlm12));
                    if(specialHolidayOpt.get().)
                    // M 1_6
                    cells.get(firstRow + 1, 4).setValue(vlm16== 0 ? "" :
                            convertToTime((int) vlm16));

                    // M1_3 特別休暇１_月初残日数
                    cells.get(firstRow, 5).setValue(dayNumbers == 0 ? "" : df.format(dayNumbers));
                    if (dayNumbers < 0) {
                        setForegroundRed(cells.get(firstRow, 5));
                    }
                    // M1_7
                    cells.get(firstRow + 1, 5).setValue(timeNumbers == 0 ? "" : convertToTime((int) timeNumbers.doubleValue()));
                    // M1_4 特別休暇１_使用数日数
                    cells.get(firstRow, 6).setValue(dayUse == 0 ? ""
                            : df.format(dayUse));

                    // M1_8
                    cells.get(firstRow +1, 6).setValue(timeUse == 0 ? "" :
                            convertToTime((int) timeUse.doubleValue()));
                    // M1_5 特別休暇１_残数日数
                    cells.get(firstRow, 7).setValue(specialVacationImported.getRemainDate() == 0 ? "" :
                            df.format(specialVacationImported.getRemainDate()));

                    if (specialVacationImported.getRemainDate() < 0) {
                        setForegroundRed(cells.get(firstRow, 7));
                    }
                    // M1_9
                    cells.get(firstRow + 1, 7).setValue(specialVacationImported.getRemainHours() == 0 ? "" :
                            convertToTime((int) specialVacationImported.getRemainHours().doubleValue()));
                    if (specialVacationImported.getRemainHours() < 0) {
                        setForegroundRed(cells.get(firstRow + 1, 7));
                    }


                if (rs263 != null) {
                    for (SpecialHolidayRemainDataOutputKdr item : rs263) {
                        // Before this month
                        int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(), item.getYm());
                        if (currentMonth.compareTo(item.getYm()) > 0) {

                            if (maxRange >= totalMonth && totalMonth >= 0) {
                                val bfNumofDate = item.getBeforeUseDays();
                                // M2_5 特別休暇１_使用日数, set lại giá trị cho cột này nếu bằng 0 thì không hiển thị ra
                                cells.get(firstRow, 10 + totalMonth).setValue(bfNumofDate == 0 ? null : df.format(bfNumofDate));
                                //M2_6
                                cells.get(firstRow + 1, 10 + totalMonth).setValue(item.getBeforeUseTimes() == 0 ? null :
                                        convertToTime((int) item.getBeforeUseTimes()));
                                // M2_7 特別休暇１_残数日数
                                cells.get(firstRow + 2, 10 + totalMonth).setValue(item.getBeforeRemainDays() == 0 ? null :
                                        df.format(item.getBeforeRemainDays()));
                                //M2_8
                                cells.get(firstRow + 3, 10 + totalMonth).setValue(item.getBeforeRemainTimes() == 0 ? null :
                                        convertToTime((int) item.getBeforeRemainTimes()));
                                if (item.getBeforeUseDays() < 0) {
                                    setForegroundRed(cells.get(firstRow, 10 + totalMonth));
                                }
                                if (item.getBeforeUseTimes() < 0) {
                                    setForegroundRed(cells.get(firstRow + 1, 10 + totalMonth));
                                }
                                if (item.getBeforeRemainDays() < 0) {
                                    setForegroundRed(cells.get(firstRow + 2, 10 + totalMonth));
                                }
                                if (item.getBeforeRemainTimes() < 0) {
                                    setForegroundRed(cells.get(firstRow + 3, 10 + totalMonth));
                                }
                            }
                        }
                        if (currentMonth.compareTo(item.getYm()) == 0) {
                            val bfDate = item.getBeforeUseDays();
                            val afDate = item.getAfterUseDays();
                            String vlm25 = "";
                            if(bfDate!= 0 && afDate !=0 ){
                                vlm25 = String.valueOf(df.format(bfDate)) + "/" + String.valueOf(df.format(afDate));
                            } else {
                                vlm25 =( bfDate==0?"":String.valueOf(df.format(bfDate))) + (afDate==0?"":String.valueOf(df.format(afDate)));
                            }
                            cells.get(firstRow, 10 + totalMonth).setValue(vlm25);

                            //M2_6
                            if (item.getBeforeUseDays() < 0) {
                                setForegroundRed(cells.get(firstRow, 10 + totalMonth));
                            }
                            val bftime = item.getBeforeUseTimes();
                            val afdtime = item.getBeforeUseTimes();

                            String vlm26 = "";
                            if(bftime!= 0 && afdtime !=0 ){
                                vlm26 = String.valueOf(convertToTime(bftime)) + "/" + String.valueOf(convertToTime(afdtime));
                            } else {
                                vlm26 =( bftime==0?"":String.valueOf(convertToTime(bftime))) + (afdtime==0?"":String.valueOf(convertToTime(afdtime)));
                            }
                            cells.get(firstRow + 1, 10 + totalMonth).setValue(vlm26);
                            // M2_7 特別休暇１_残数日数
                            if (item.getBeforeUseTimes() < 0) {
                                setForegroundRed(cells.get(firstRow + 1, 10 + totalMonth));
                            }
                            val bfdateRemain = item.getBeforeRemainDays();
                            val afdateReman = item.getAfterRemainDays();
                            val m27 = String.valueOf(df.format(bfdateRemain) + "/" + String.valueOf(df.format(afdateReman)));

                            cells.get(firstRow + 2, 10 + totalMonth).setValue(m27);
                            if (item.getBeforeRemainDays() < 0) {
                                setForegroundRed(cells.get(firstRow + 2, 10 + totalMonth));
                            }
                            //M2_8
                            val bfRemainTime = item.getBeforeRemainTimes();
                            val afRemanTime = item.getAfterRemainTimes();
                            val m28 = String.valueOf(convertToTime(bfRemainTime) + "/" + String.valueOf(convertToTime(afRemanTime)));
                            cells.get(firstRow + 3, 10 + totalMonth).setValue(m28);
                            if (item.getBeforeRemainTimes() < 0) {
                                setForegroundRed(cells.get(firstRow + 2, 10 + totalMonth));
                            }
                        }
                        if (currentMonth.compareTo(item.getYm()) < 0) {
                            //M25
                            val bfDate = item.getBeforeUseDays();
                            cells.get(firstRow, 10 + totalMonth).setValue(bfDate == 0 ? "" : df.format(bfDate));
                            if (item.getBeforeUseDays() < 0) {
                                setForegroundRed(cells.get(firstRow, 10 + totalMonth));
                            }
                            val bftime = item.getBeforeUseTimes();
                            cells.get(firstRow + 1, 10 + totalMonth).setValue(bftime == 0 ? "" : convertToTime(bftime));
                            // M2_7 特別休暇１_残数日数
                            if (item.getBeforeUseDays() < 0) {
                                setForegroundRed(cells.get(firstRow, 10 + totalMonth));
                            }
                            if (item.getBeforeUseTimes() < 0) {
                                setForegroundRed(cells.get(firstRow + 1, 10 + totalMonth));
                            }
                            setBackgroundGray(cells.get(firstRow + 2, 10 + totalMonth));
                            setBackgroundGray(cells.get(firstRow + 3, 10 + totalMonth));
                        }

                    }
                }
            }

            firstRow += 4;
        }

        return firstRow;
    }

    private int printChildNursingVacation(Cells cells, int firstRow, HolidaysRemainingEmployee employee,
                                          HolidayRemainingDataSource dataSource) throws Exception {
        // 子の看護休暇
        // ※1
        if (!dataSource.getVariousVacationControl().isChildNursingSetting()) {
            return firstRow;
        }
        if (!dataSource.getHolidaysRemainingManagement().getListItemsOutput().getChildNursingVacation()
                .isChildNursingLeave()) {
            return firstRow;
        }
        //※2
        boolean iscareSetting = dataSource.getVariousVacationControl().isNursingCareSetting();

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
            cells.get(firstRow, 6).setValue(currentSituationImported.getNumberOfUse().equals("0") ? "" : currentSituationImported.getNumberOfUse());
            // N1_3 子の看護休暇_残数
            cells.get(firstRow, 7).setValue(currentSituationImported.getRemainingDays().equals("0") ? "" : currentSituationImported.getRemainingDays());
            if (currentSituationImported.getRemainingDays().startsWith("-")) {
                setForegroundRed(cells.get(firstRow, 7));
            }

            // Position of current month
            int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(), currentMonth);
            if (currentMonth.compareTo(dataSource.getEndMonth().yearMonth()) <= 0 && totalMonth >= 0) {
                // N2_3 子の看護休暇_使用日数 当月
                cells.get(firstRow, 10 + totalMonth).setValue(currentSituationImported.getNumberOfUse().equals("0") ? "" : currentSituationImported.getNumberOfUse());

                if(iscareSetting)
                // N2_4 子の看護休暇_残日数
                cells.get(firstRow + 1, 10 + totalMonth).setValue(currentSituationImported
                        .getRemainingDays().equals("0") ? currentSituationImported.getRemainingDays() : "");

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
        // 積立年休 H
        if (dataSource.getHolidaysRemainingManagement().getListItemsOutput().getYearlyReserved().isYearlyReserved()) {
            totalRowDetails += 2;
        }
        // 代休 I
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
        // 振休 - K
        if (dataSource.getHolidaysRemainingManagement().getListItemsOutput().getPause().isPauseItem()) {
            totalRowDetails += 2;
            if (dataSource.getHolidaysRemainingManagement().getListItemsOutput().getPause().isNumberRemainingPause()) {
                totalRowDetails += 1;
            }
            if (dataSource.getHolidaysRemainingManagement().getListItemsOutput().getPause().isUndigestedPause()) {
                totalRowDetails += 1;
            }
        }
        // 特別休暇 - M
        List<Integer> specialHoliday = dataSource.getHolidaysRemainingManagement().getListItemsOutput()
                .getSpecialHoliday();
        for (int i = 0; i < specialHoliday.size(); i++) {
            totalRowDetails += 2;
        }
        // 子の看護休暇 - N
        if (dataSource.getHolidaysRemainingManagement().getListItemsOutput().getChildNursingVacation()
                .isChildNursingLeave()) {
            totalRowDetails += 2;
        }
        // 介護休暇 -  O
        if (dataSource.getHolidaysRemainingManagement().getListItemsOutput().getNursingcareLeave().isNursingLeave()) {
            totalRowDetails += 2;
        }

        return totalRowDetails;
    }

    // L1 - CREATE IN VER 15 - PENDING- rq 262
    private int publicHolidays(Cells cells, int firstRow, HolidaysRemainingEmployee employee,
                               HolidayRemainingDataSource dataSource) throws Exception {
        // 代休
        val hdRemainingInfor = dataSource.getMapEmployees().get(employee.getEmployeeId()).getHolidayRemainingInfor();
        val listItemsOutput = dataSource.getHolidaysRemainingManagement().getListItemsOutput();
        //※1: 出力項目設定[公休]：休暇残数管理表の出力項目設定．出力する項目一覧．公休．公休の項目を出力する
        //     公休管理設定：公休設定．管理区分
        if (!checkPublicHolidays(dataSource)) {
            return firstRow;
        }
        // ※2 : 出力項目設定[公休繰越数]：休暇残数管理表の出力項目設定．出力する項目一覧．公休．公休繰越数を出力する
        // outputHolidayForward
        // ※3 : 出力項目設定[公休月度残]：休暇残数管理表の出力項目設定．出力する項目一覧．公休．公休月度残を出力する
        // monthlyPublic

        int totalRows = 4;
        int rowIndexRepresentSubstitute = 0;
        int rowIndexIsRemainingChargeSubstitute = 0;
        cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 24, firstRow, 4);
        // L1_1
        cells.get(firstRow, 2).setValue(TextResource.localize("KDR001_21"));
        if (listItemsOutput.getHolidays().isMonthlyPublic()) {
            // L2_4
            cells.get(firstRow + 3, 9).setValue(TextResource.localize("KDR001_80"));
        }
        if(listItemsOutput.getHolidays().isOutputHolidayForward()){
            // L2_1
            cells.get(firstRow, 9).setValue(TextResource.localize("KDR001_23"));
        }
        // L2_2
        cells.get(firstRow + 1, 9).setValue(TextResource.localize("KDR001_22"));
        // L2_3
        cells.get(firstRow + 2, 9).setValue(TextResource.localize("KDR001_17"));


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
        style.setForegroundColor(Color.fromArgb(147, 193, 242));
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
        //pageSetup.setPrintArea("A1:N");
        //ý 1 của bug #102883  事象(1)
        pageSetup.setHeader(0, "&7&\"MS ゴシック\"" + dataSource.getCompanyName());
        pageSetup.setHeader(1, "&10&\"MS ゴシック\"" + title);
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm", Locale.JAPAN);
        pageSetup.setHeader(2, "&7&\"MS ゴシック\"" + LocalDateTime.now().format(fullDateTimeFormatter) + "\npage &P");
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
    }

    //E Case 2:
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
        if (!holidaysRemainingManagement.getListItemsOutput().getAnnualHoliday().isInsideHours()) {
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

    /* I - CASE 1 */
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

    // L 1
    private boolean checkPublicHolidays(HolidayRemainingDataSource dataSource) {
        val check = dataSource.getHolidaysRemainingManagement().getListItemsOutput().
                getHolidays();
        val various = dataSource.getVariousVacationControl();
        return check.isOutputItemsHolidays() && various.isPublicHolidaySetting();

    }

    private boolean checkJ1(HolidayRemainingDataSource dataSource,HolidaysRemainingEmployee employee){
        val companyId = AppContexts.user().companyId();
        val checkPause = dataSource.getHolidaysRemainingManagement().getListItemsOutput().
                getPause().isPauseItem();
        val substVacation = comSubstVacationRepository.findById(companyId);
        val empSubstVacation = substVacationRepository.findAll(companyId);
        if(!checkPause){
            return false;
        }else {
            return substVacation.map(ComSubstVacation::isManaged).orElse(false);
        }

    }

    private String convertToTime(int minute) {
        val minuteAbs = Math.abs(minute);
        int hours = minuteAbs / 60;
        int minutes = minuteAbs % 60;
        return (minute < 0 ? "-" : "") + String.format("%d:%02d", hours, minutes);
    }

    private int count(HolidayRemainingDataSource dataSource) {
        int firstRow = 0;
        for (String employeeIds : dataSource.getEmpIds()) {
            HolidaysRemainingEmployee employee = dataSource.getMapEmployees().get(employeeIds);
            firstRow += countE(dataSource, employee);
            firstRow += countF(dataSource, employee);
            firstRow += countG(dataSource, employee);
            firstRow += countM(dataSource, employee);
            firstRow += countN(dataSource, employee);
            firstRow += countO(dataSource, employee);
            firstRow += countH(dataSource, employee);
            firstRow += countI(dataSource, employee);
            firstRow += countJ(dataSource, employee);
            firstRow += countK(dataSource, employee);
            firstRow += countL(dataSource, employee);
        }
        return firstRow;
    }

    private int countE(HolidayRemainingDataSource dataSource, HolidaysRemainingEmployee employee) {
        int firstRow = 0;
        if (!dataSource.getVariousVacationControl().isAnnualHolidaySetting()) {
            return firstRow;
        }
        if (!dataSource.getHolidaysRemainingManagement().getListItemsOutput().getAnnualHoliday().isYearlyHoliday()) {
            return firstRow;
        }
        val hdRemainingInfor = dataSource.getMapEmployees().get(employee.getEmployeeId()).getHolidayRemainingInfor();
        if (hdRemainingInfor == null) {
            return firstRow + 2;
        }
        int total = 4;
        List<AnnLeaGrantNumberImported> listAnnLeaGrant = hdRemainingInfor.getListAnnLeaGrantNumber();
        Optional<GeneralDate> grantDate = dataSource.getMapEmployees().get(employee.getEmployeeId())
                .getHolidayRemainingInfor().getGrantDate();
        if (listAnnLeaGrant != null && grantDate.isPresent()) {
            for (int i = 0; i < listAnnLeaGrant.size(); i++) {
               if(i>=2){
                   total += 2;
               }
            }
        }
        return firstRow +total;
    }

    private int countF(HolidayRemainingDataSource dataSource, HolidaysRemainingEmployee employee) {
        int firstRow = 0;
        if (!checkLimitHoliday(dataSource.getHolidaysRemainingManagement())
                || !employee.getCurrentMonth().isPresent()) {
            return firstRow;
        }
        firstRow += 2;
        return firstRow;
    }

    private int countG(HolidayRemainingDataSource dataSource, HolidaysRemainingEmployee employee) {
        int firstRow = 0;
        if (!checkLimitHourlyHoliday(dataSource.getHolidaysRemainingManagement()))
            return firstRow;
        val hdRemainingInfor = dataSource.getMapEmployees().get(employee.getEmployeeId()).getHolidayRemainingInfor();
        if (hdRemainingInfor == null) {
            return firstRow + 1;
        }
        val data363 = hdRemainingInfor.getRs363New();

        if (data363 == null || data363.isEmpty()) {
            return firstRow;
        }
        if (!employee.getCurrentMonth().isPresent()) {
            return firstRow + 1;
        }
        firstRow += 1;
        return firstRow;

    }

    private int countM(HolidayRemainingDataSource dataSource, HolidaysRemainingEmployee employee) {
        // 特別休暇
        int firstRow = 0;
        val hdRemainingInfor = dataSource.getMapEmployees().get(employee.getEmployeeId()).getHolidayRemainingInfor();
        if (hdRemainingInfor == null) {
            return firstRow;
        }
        val listSphdCode = dataSource.getHolidaysRemainingManagement().getListItemsOutput().getSpecialHoliday();
        Collections.sort(listSphdCode);
        List<SpecialHoliday> specialHolidays = dataSource.getVariousVacationControl().getListSpecialHoliday();
        for (Integer specialHolidayCode : listSphdCode) {
            Optional<SpecialHoliday> specialHolidayOpt = specialHolidays.stream()
                    .filter(c -> c.getSpecialHolidayCode().v() == specialHolidayCode).findFirst();
            if (!specialHolidayOpt.isPresent()) {
                continue;
            }
            firstRow += 4;
        }
        return firstRow;
    }

    private int countN(HolidayRemainingDataSource dataSource, HolidaysRemainingEmployee employee) {
        int firstRow = 0;
        // 子の看護休暇
        if (!dataSource.getVariousVacationControl().isChildNursingSetting()) {
            return firstRow;
        }
        if (!dataSource.getHolidaysRemainingManagement().getListItemsOutput().getChildNursingVacation()
                .isChildNursingLeave()) {
            return firstRow;
        }
        val hdRemainingInfor = dataSource.getMapEmployees().get(employee.getEmployeeId()).getHolidayRemainingInfor();
        if (hdRemainingInfor == null) {
            return firstRow + 4;
        }
        if (!employee.getCurrentMonth().isPresent()) {
            return firstRow + 4;
        }
        return firstRow + 4;
    }

    private int countO(HolidayRemainingDataSource dataSource, HolidaysRemainingEmployee employee) {
        // 介護休暇
        int firstRow = 0;
        if (!dataSource.getVariousVacationControl().isNursingCareSetting()) {
            return firstRow;
        }
        if (!dataSource.getHolidaysRemainingManagement().getListItemsOutput().getNursingcareLeave().isNursingLeave()) {
            return firstRow;
        }
        val hdRemainingInfor = dataSource.getMapEmployees().get(employee.getEmployeeId()).getHolidayRemainingInfor();

        if (hdRemainingInfor == null) {
            return firstRow + 4;
        }
        if (!employee.getCurrentMonth().isPresent()) {
            return firstRow + 4;
        }
        return firstRow + 4;
    }

    private int countH(HolidayRemainingDataSource dataSource, HolidaysRemainingEmployee employee) {
        int firstRow = 0;
        if (!checkYearlyLeave(dataSource.getHolidaysRemainingManagement(), employee.getEmployeeCode(),
                dataSource.getVariousVacationControl())) {
            return firstRow;
        }
        val hdRemainingInfor = dataSource.getMapEmployees().get(employee.getEmployeeId()).getHolidayRemainingInfor();
        if (hdRemainingInfor == null) {
            return firstRow + 2;
        }
        if (!employee.getCurrentMonth().isPresent()) {
            return firstRow + 2;
        }
        firstRow += 2;
        return firstRow;
    }

    private int countI(HolidayRemainingDataSource dataSource, HolidaysRemainingEmployee employee) {
        int firstRow = 0;
        if (!checkTakeABreak_01(dataSource.getHolidaysRemainingManagement(), employee.getEmploymentCode())) {
            return firstRow;
        }
        val hdRemainingInfor = dataSource.getMapEmployees().get(employee.getEmployeeId()).getHolidayRemainingInfor();
        int totalRows = 2;
        if (hdRemainingInfor == null) {
            return firstRow + 2;
        }
        if (checkTakeABreak_02(dataSource.getHolidaysRemainingManagement())) {
            totalRows += 1;
        }
        if (checkTakeABreak_03(dataSource.getHolidaysRemainingManagement())) {
            totalRows += 1;
        }

        if (!employee.getCurrentMonth().isPresent()) {
            return firstRow + totalRows;
        }
        firstRow += totalRows;
        return firstRow;
    }

    private int countJ(HolidayRemainingDataSource dataSource, HolidaysRemainingEmployee employee) {
        int firstRow = 0;
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
        val hdRemainingInfor = dataSource.getMapEmployees()
                .get(employee.getEmployeeId()).getHolidayRemainingInfor();
        if (hdRemainingInfor == null) {
            return firstRow + 2;
        }
        if (isUndigestedPause) {

            totalRows += 1;
        }
        if (isNumberRemainingPause) {

            totalRows += 1;
        }
        if (!employee.getCurrentMonth().isPresent()) {
            return firstRow + totalRows;
        }
        val currentHolidayRemainLeft = hdRemainingInfor.getCurrentHolidayRemainLeft();
        // Current month
        if (currentHolidayRemainLeft == null) {
            return firstRow + totalRows;
        }
        return firstRow + totalRows;
    }

    private int countK(HolidayRemainingDataSource dataSource, HolidaysRemainingEmployee employee) {
        int firstRow = 0;
        val holidayRemainingManagement = dataSource.getHolidaysRemainingManagement().getListItemsOutput();
        if (!holidayRemainingManagement.getOutOfTime().isOvertimeItem()) {
            return firstRow;
        }
        if (!dataSource.getVariousVacationControl().isCom60HourVacationSetting()) {
            return firstRow;
        }
        boolean isUndigestedPause = holidayRemainingManagement.getOutOfTime().isOvertimeOverUndigested();
        boolean isOvertimeRemaining = holidayRemainingManagement.getOutOfTime().isOvertimeRemaining();
        int totalRows = 2;
        val hdRemainingInfor = dataSource.getMapEmployees().get(employee.getEmployeeId()).getHolidayRemainingInfor();
        if (hdRemainingInfor == null) {
            return firstRow + 2;
        }
        ;
        if (isUndigestedPause) {
            totalRows += 1;
        }
        if (isOvertimeRemaining) {
            totalRows += 1;
        }
        if (!employee.getCurrentMonth().isPresent()) {
            return firstRow + totalRows;
        }
        return firstRow + totalRows;
    }

    private int countL(HolidayRemainingDataSource dataSource, HolidaysRemainingEmployee employee) {
        int firstRow = 0;
        val hdRemainingInfor = dataSource.getMapEmployees().get(employee.getEmployeeId()).getHolidayRemainingInfor();
        if (!checkPublicHolidays(dataSource)) {
            return firstRow;
        }
        int totalRows = 4;
        if (hdRemainingInfor == null) {
            return firstRow + 4;
        }
        if (!employee.getCurrentMonth().isPresent()) {
            return firstRow + totalRows;
        }
        return firstRow + totalRows;
    }
}