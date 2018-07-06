package nts.uk.ctx.at.function.infra.repository.holidaysremaining.report;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.adapter.annualleave.GetNextAnnLeaGrantDateAdapter;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.AbsenceReruitmentManaAdapter;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.AnnLeaGrantNumberImported;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.AnnLeaveOfThisMonthImported;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.AnnLeaveRemainingAdapter;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.AnnLeaveUsageStatusOfThisMonthImported;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.AnnualLeaveUsageImported;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.CurrentHolidayRemainImported;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.StatusOfHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.periodofspecialleave.ComplileInPeriodOfSpecialLeaveAdapter;
import nts.uk.ctx.at.function.dom.adapter.periodofspecialleave.SpecialHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.periodofspecialleave.SpecialVacationImported;
import nts.uk.ctx.at.function.dom.adapter.reserveleave.GetReserveLeaveNumbersAdpter;
import nts.uk.ctx.at.function.dom.adapter.reserveleave.ReserveHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.reserveleave.ReservedYearHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.reserveleave.RsvLeaUsedCurrentMonImported;
import nts.uk.ctx.at.function.dom.adapter.vacation.CurrentHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.vacation.MonthlyDayoffRemainAdapter;
import nts.uk.ctx.at.function.dom.adapter.vacation.StatusHolidayImported;
import nts.uk.ctx.at.function.dom.holidaysremaining.BreakSelection;
import nts.uk.ctx.at.function.dom.holidaysremaining.report.HolidayRemainingDataSource;
import nts.uk.ctx.at.function.dom.holidaysremaining.report.HolidaysRemainingEmployee;
import nts.uk.ctx.at.function.dom.holidaysremaining.report.HolidaysRemainingReportGenerator;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.children.service.ChildCareNursingRemainOutputPara;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.children.service.ChildNursingLeaveMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.service.CareHolidayMngService;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class HolidaysRemainingReportGeneratorImp extends AsposeCellsReportGenerator
		implements HolidaysRemainingReportGenerator {

	@Inject
	private AnnLeaveRemainingAdapter annLeaveRemainingAdapter;

	@Inject
	private GetReserveLeaveNumbersAdpter getReserveLeaveNumbersFinder;

	@Inject
	private MonthlyDayoffRemainAdapter monthlyDayoffRemainAdapter;

	@Inject
	private AbsenceReruitmentManaAdapter absenceReruitmentManaAdapter;

	@Inject
	private ComplileInPeriodOfSpecialLeaveAdapter complileInPeriodOfSpecialLeaveAdapter;

	@Inject
	private ChildNursingLeaveMng childNursingLeaveMng;

	@Inject
	private CareHolidayMngService careHolidayMngService;

	@Inject
	private GetNextAnnLeaGrantDateAdapter getNextAnnLeaGrantDateAdapter;

	@Inject
	private SpecialHolidayRepository specialHolidayRepository;

	@Inject
	private SpecialLeaveGrantRepository specialLeaveGrantRepository;

	private static final String TEMPLATE_FILE = "report/休暇残数管理票_テンプレート.xlsx";
	private static final String REPORT_FILE_NAME = "休暇残数管理票.xlsx";
	private static final int NUMBER_ROW_OF_PAGE = 37;
	private static final int NUMBER_ROW_OF_HEADER = 5;
	private static final int NUMBER_COLUMN = 23;
	private static final int MIN_ROW_DETAILS = 4;
	private static final int TOTAL_MONTH_IN_YEAR = 12;

	@Override
	public void generate(FileGeneratorContext generatorContext, HolidayRemainingDataSource dataSource) {
		try {
			val designer = this.createContext(TEMPLATE_FILE);
			Workbook workbook = designer.getWorkbook();
			WorksheetCollection worksheets = workbook.getWorksheets();
			// Get first sheet in template
			Worksheet worksheet = worksheets.get(0);

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

		// B1_1, B1_2
		cells.get(1, 0).setValue(TextResource.localize("KDR001_2") + dataSource.getStartMonth().toString("yyyy/MM")
				+ "　～　" + dataSource.getEndMonth().toString("yyyy/MM"));
		// B1_3
		cells.get(2, 0).setValue(TextResource.localize("KDR001_3"));
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

		if (!dataSource.isSameCurrentMonth()) {
			removeFirstShapes(worksheet);
		}

		// C1_9
		for (int i = 0; i <= totalMonths(dataSource.getStartMonth().yearMonth(),
				dataSource.getEndMonth().yearMonth()); i++) {
			cells.get(4, 10 + i).setValue(String.valueOf(dataSource.getStartMonth().addMonths(i).month()) + "月");
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
			cells.get(firstRow, 0).setValue(TextResource.localize("KDR001_12") + ": " + employee.getWorkplaceCode()
					+ "　" + employee.getWorkplaceName());
			firstRow += 1;
			firstRow = this.printHolidayRemainingEachPerson(worksheet, firstRow, employee, dataSource);
		}
	}

	private void printWorkplaceBreakPage(Worksheet worksheet, HolidayRemainingDataSource dataSource) throws Exception {
		int firstRow = NUMBER_ROW_OF_PAGE;

		Map<String, List<HolidaysRemainingEmployee>> map = dataSource.getMapEmployees().values().stream()
				.collect(Collectors.groupingBy(HolidaysRemainingEmployee::getWorkplaceId));

		for (List<HolidaysRemainingEmployee> listEmployee : map.values()) {
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
		Cells cells = worksheet.getCells();

		firstRow = printAnnualHoliday(cells, firstRow, employee, dataSource);

		firstRow = printYearlyReserved(cells, firstRow, employee, dataSource);

		firstRow = printSubstituteHoliday(cells, firstRow, employee, dataSource);

		firstRow = printPauseHoliday(cells, firstRow, employee, dataSource);

		firstRow = printSpecialHoliday(cells, firstRow, employee, dataSource);

		firstRow = printChildNursingVacation(cells, firstRow, employee, dataSource);

		firstRow = printNursingCareLeave(cells, firstRow, employee, dataSource);

		int totalRowDetails = countRowEachPerson(dataSource);
		if (totalRowDetails < MIN_ROW_DETAILS) {
			// Insert blank rows
			cells.copyRows(cells, 25, firstRow, MIN_ROW_DETAILS - totalRowDetails);
			firstRow += (MIN_ROW_DETAILS - totalRowDetails);
		}

		// D2_1
		cells.get(rowIndexD, 0).setValue(employee.getEmployeeCode());
		// D2_2
		cells.get(rowIndexD, 1).setValue(employee.getEmployeeName());
		// D2_3 No.369
		Optional<GeneralDate> grantDate = getNextAnnLeaGrantDateAdapter.algorithm(AppContexts.user().companyId(),
				employee.getEmployeeId());
		grantDate.ifPresent(generalDate -> cells.get(rowIndexD + 1, 0).setValue(generalDate));

		// D2_4
		cells.get(rowIndexD + 2, 0).setValue(employee.getEmploymentName());
		// D2_5
		cells.get(rowIndexD + 3, 0).setValue(employee.getJobTitle());

		// Set Style
		for (int index = 0; index < NUMBER_COLUMN; index++) {
			setTopBorderStyle(cells.get(rowIndexD, index));
			setBottomBorderStyle(cells.get(firstRow - 1, index));
		}

		return firstRow;
	}

	private int printAnnualHoliday(Cells cells, int firstRow, HolidaysRemainingEmployee employee,
			HolidayRemainingDataSource dataSource) throws Exception {
		// 年休
		if (dataSource.getHolidaysRemainingManagement().getListItemsOutput().getAnnualHoliday().isYearlyHoliday()) {
			YearMonthPeriod yearMonthPeriod = new YearMonthPeriod(dataSource.getStartMonth().yearMonth(),
					dataSource.getEndMonth().yearMonth());
			DatePeriod datePeriod = new DatePeriod(dataSource.getStartMonth(), dataSource.getEndMonth());
			int totalAddRows = 2;
			cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 1, firstRow, totalAddRows);
			// E1_1
			cells.get(firstRow, 2).setValue(TextResource.localize("Com_PaidHoliday"));
			// E2_1
			cells.get(firstRow, 9).setValue(TextResource.localize("KDR001_14"));
			// E3_1
			cells.get(firstRow + 1, 9).setValue(TextResource.localize("KDR001_15"));

			// Call requestList281
			List<AnnLeaGrantNumberImported> listAnnLeaGrant = annLeaveRemainingAdapter
					.algorithm(employee.getEmployeeId());
			for (int i = 0; i < listAnnLeaGrant.size(); i++) {
				if (i >= totalAddRows) {
					totalAddRows += 1;
					cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 1, firstRow + totalAddRows, 1);
				}
				// E1_2
				cells.get(firstRow + i, 3).setValue(listAnnLeaGrant.get(i).getGrantDate());
				// E1_3
				cells.get(firstRow + i, 4).setValue(listAnnLeaGrant.get(i).getGrantDays());
			}

			// Call requestList265
			AnnLeaveOfThisMonthImported annLeave = annLeaveRemainingAdapter
					.getAnnLeaveOfThisMonth(employee.getEmployeeId());
			if (annLeave != null) {
				// E1_4
				cells.get(firstRow, 5).setValue(annLeave.getFirstMonthRemNumDays());
				if (annLeave.getFirstMonthRemNumDays() < 0) {
					setForegroundRed(cells.get(firstRow, 5));
				}
				// E1_5
				cells.get(firstRow, 6).setValue(annLeave.getUsedDays());
				// E1_6
				cells.get(firstRow, 7).setValue(annLeave.getRemainDays());
				if (annLeave.getRemainDays() < 0) {
					setForegroundRed(cells.get(firstRow, 7));
				}
			}
			if (employee.getCurrentMonth().isPresent()) {
				YearMonth currentMonth = employee.getCurrentMonth().get();
				// Call requestList255
				List<AnnualLeaveUsageImported> listAnnLeaveUsage = annLeaveRemainingAdapter
						.algorithm(employee.getEmployeeId(), yearMonthPeriod);
				for (AnnualLeaveUsageImported item : listAnnLeaveUsage) {
					if (currentMonth.compareTo(item.getYearMonth()) > 0) {
						int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(), item.getYearMonth());
						if (totalMonth >= 0) {
							// E2_3 当月より前
							cells.get(firstRow, 10 + totalMonth).setValue(item.getUsedDays());
							// E3_3 当月より前
							cells.get(firstRow + 1, 10 + totalMonth).setValue(item.getRemainingDays());
							if (item.getRemainingDays() < 0) {
								setForegroundRed(cells.get(firstRow + 1, 10 + totalMonth));
							}
						}
					}
				}

				// Call requestList363
				List<AnnLeaveUsageStatusOfThisMonthImported> listAnnLeaveUsageOfThisMonth = annLeaveRemainingAdapter
						.getAnnLeaveUsageOfThisMonth(employee.getEmployeeId(), datePeriod);
				for (AnnLeaveUsageStatusOfThisMonthImported item : listAnnLeaveUsageOfThisMonth) {
					if (currentMonth.compareTo(item.getYearMonth()) == 0) {
						int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(), item.getYearMonth());
						if (totalMonth >= 0) {
							// E2_3 当月以降
							cells.get(firstRow, 10 + totalMonth).setValue(item.getMonthlyUsageDays());
							// E3_3 当月以降
							cells.get(firstRow + 1, 10 + totalMonth).setValue(item.getMonthlyRemainingDays());
							if (item.getMonthlyRemainingDays() < 0) {
								setForegroundRed(cells.get(firstRow + 1, 10 + totalMonth));
							}
						}
					}
				}

				// Set background
				for (int i = 0; i <= totalMonths(dataSource.getStartMonth().yearMonth(),
						dataSource.getEndMonth().yearMonth()); i++) {
					if (dataSource.getStartMonth().addMonths(i).yearMonth().compareTo(currentMonth) > 0) {
						for (int j = 0; j < totalAddRows; j++) {
							setBackgroundGray(cells.get(firstRow + j, 10 + i));
						}
					}
					if (!dataSource.isSameCurrentMonth()
							&& dataSource.getStartMonth().addMonths(i).yearMonth().compareTo(currentMonth) == 0) {
						for (int j = 0; j < totalAddRows; j++) {
							setCurrentMonthBackground(cells.get(firstRow + j, 10 + i));
						}
					}
				}
			}
			firstRow += totalAddRows;
		}
		return firstRow;
	}

	private int printYearlyReserved(Cells cells, int firstRow, HolidaysRemainingEmployee employee,
			HolidayRemainingDataSource dataSource) throws Exception {
		// 積立年休
		if (dataSource.getHolidaysRemainingManagement().getListItemsOutput().getYearlyReserved().isYearlyReserved()) {
			YearMonthPeriod yearMonthPeriod = new YearMonthPeriod(dataSource.getStartMonth().yearMonth(),
					dataSource.getEndMonth().yearMonth());
			cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 3, firstRow, 2);
			// H1_1
			cells.get(firstRow, 2).setValue(TextResource.localize("Com_FundedPaidHoliday"));
			// H2_1
			cells.get(firstRow, 9).setValue(TextResource.localize("KDR001_14"));
			// H2_2
			cells.get(firstRow + 1, 9).setValue(TextResource.localize("KDR001_15"));
			// Set data for 積立年休 (H)
			// Call requestList268
			ReserveHolidayImported reserveHolidayImported = getReserveLeaveNumbersFinder
					.algorithm(employee.getEmployeeId());
			// H1_2
			cells.get(firstRow, 4).setValue(reserveHolidayImported.getGrantNumber());
			// H1_3
			cells.get(firstRow, 5).setValue(reserveHolidayImported.getStartMonthRemain());
			if (reserveHolidayImported.getStartMonthRemain() < 0) {
				setForegroundRed(cells.get(firstRow, 5));
			}
			// H1_4
			cells.get(firstRow, 6).setValue(reserveHolidayImported.getUsedNumber());
			// H1_5
			cells.get(firstRow, 7).setValue(reserveHolidayImported.getRemainNumber());
			if (reserveHolidayImported.getRemainNumber() < 0) {
				setForegroundRed(cells.get(firstRow, 7));
			}
			// H1_6
			cells.get(firstRow, 8).setValue(reserveHolidayImported.getUndigestNumber());
			setForegroundRed(cells.get(firstRow, 8));

			if (employee.getCurrentMonth().isPresent()) {
				YearMonth currentMonth = employee.getCurrentMonth().get();
				// Call requestList258
				List<ReservedYearHolidayImported> reservedYearHolidayImportedList = getReserveLeaveNumbersFinder
						.algorithm(employee.getEmployeeId(), yearMonthPeriod);
				// Call requestList364
				List<RsvLeaUsedCurrentMonImported> rsvLeaUsedCurrentMonImported = getReserveLeaveNumbersFinder
						.algorithm364(employee.getEmployeeId(), yearMonthPeriod);

				for (ReservedYearHolidayImported reservedYearHolidayItem : reservedYearHolidayImportedList) {
					// Before this month
					if (currentMonth.compareTo(reservedYearHolidayItem.getYearMonth()) > 0) {
						int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(),
								reservedYearHolidayItem.getYearMonth());
						if (totalMonth >= 0) {
							// H2_3 当月より前
							cells.get(firstRow, 10 + totalMonth).setValue(reservedYearHolidayItem.getUsedDays());
							// H2_4 当月より前
							cells.get(firstRow + 1, 10 + totalMonth)
									.setValue(reservedYearHolidayItem.getRemainingDays());
							if (reservedYearHolidayItem.getRemainingDays() < 0) {
								setForegroundRed(cells.get(firstRow + 1, 10 + totalMonth));
							}
						}
					}
				}

				for (RsvLeaUsedCurrentMonImported rsvLeaUsedCurrentMonItem : rsvLeaUsedCurrentMonImported) {
					// After this month
					if (currentMonth.compareTo(rsvLeaUsedCurrentMonItem.getYearMonth()) <= 0) {
						int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(),
								rsvLeaUsedCurrentMonItem.getYearMonth());
						if (totalMonth >= 0) {
							// H2_3 当月以降
							cells.get(firstRow, 10 + totalMonth).setValue(rsvLeaUsedCurrentMonItem.getUsedNumber());
							if (currentMonth.compareTo(rsvLeaUsedCurrentMonItem.getYearMonth()) == 0) {
								// H2_4 当月以降
								cells.get(firstRow + 1, 10 + totalMonth)
										.setValue(rsvLeaUsedCurrentMonItem.getRemainNumber());
								if (rsvLeaUsedCurrentMonItem.getRemainNumber() < 0) {
									setForegroundRed(cells.get(firstRow + 1, 10 + totalMonth));
								}
							}
						}
					}
				}

				// Set background
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
			}

			firstRow += 2;
		}
		return firstRow;
	}

	private int printSubstituteHoliday(Cells cells, int firstRow, HolidaysRemainingEmployee employee,
			HolidayRemainingDataSource dataSource) throws Exception {
		// 代休
		if (dataSource.getHolidaysRemainingManagement().getListItemsOutput().getSubstituteHoliday()
				.isOutputItemSubstitute()) {
			cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 5, firstRow, 4);
			// I1_1
			cells.get(firstRow, 2).setValue(TextResource.localize("Com_CompensationHoliday"));
			// I2_1
			cells.get(firstRow, 9).setValue(TextResource.localize("KDR001_16"));
			// I3_1
			cells.get(firstRow + 1, 9).setValue(TextResource.localize("KDR001_17"));
			// I4_1
			cells.get(firstRow + 2, 9).setValue(TextResource.localize("KDR001_11"));
			// I5_1
			cells.get(firstRow + 3, 9).setValue(TextResource.localize("KDR001_18"));

			if (employee.getCurrentMonth().isPresent()) {
				YearMonth currentMonth = employee.getCurrentMonth().get();
				// Set value for I
				// Call requestList269
				List<CurrentHolidayImported> currentHolidayImported = monthlyDayoffRemainAdapter
						.getInterimRemainAggregate(employee.getEmployeeId(), dataSource.getBaseDate(),
								dataSource.getStartMonth().yearMonth(), dataSource.getEndMonth().yearMonth());
				// Call requestList259
				List<StatusHolidayImported> statusHolidayImported = monthlyDayoffRemainAdapter
						.lstDayoffCurrentMonthOfEmployee(employee.getEmployeeId(),
								dataSource.getStartMonth().yearMonth(), dataSource.getEndMonth().yearMonth());

				for (StatusHolidayImported statusHolidayItem : statusHolidayImported) {
					// Before this month
					if (currentMonth.compareTo(statusHolidayItem.getYm()) > 0) {
						// Before this month
						int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(), statusHolidayItem.getYm());
						if (totalMonth >= 0) {
							// I2_3 代休_発生_日数
							cells.get(firstRow, 10 + totalMonth).setValue(statusHolidayItem.getOccurrenceDays());
							// I3_3 代休_使用_日数
							cells.get(firstRow + 1, 10 + totalMonth).setValue(statusHolidayItem.getUseDays());
							// I4_3 代休_未消化_日数
							cells.get(firstRow + 2, 10 + totalMonth).setValue(statusHolidayItem.getUnUsedDays());
							setForegroundRed(cells.get(firstRow + 2, 10 + totalMonth));
							// I5_3 代休_残数_日数
							cells.get(firstRow + 3, 10 + totalMonth).setValue(statusHolidayItem.getRemainDays());
							if (statusHolidayItem.getRemainDays() < 0) {
								setForegroundRed(cells.get(firstRow + 3, 10 + totalMonth));
							}
						}
					}

				}
				for (CurrentHolidayImported currentHolidayItem : currentHolidayImported) {
					// After this month
					if (currentMonth.compareTo(currentHolidayItem.getYm()) <= 0) {
						int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(),
								currentHolidayItem.getYm());
						if (totalMonth >= 0) {
							// I2_3 代休_発生_日数
							cells.get(firstRow, 10 + totalMonth).setValue(currentHolidayItem.getMonthOccurrence());
							// I3_3 代休_使用_日数
							cells.get(firstRow + 1, 10 + totalMonth).setValue(currentHolidayItem.getMonthUse());
							if (currentMonth.compareTo(currentHolidayItem.getYm()) == 0) {
								// I4_3 代休_未消化_日数
								cells.get(firstRow + 2, 10 + totalMonth)
										.setValue(currentHolidayItem.getMonthExtinction());
								setForegroundRed(cells.get(firstRow + 2, 10 + totalMonth));
								// I5_3 代休_残数_日数
								cells.get(firstRow + 3, 10 + totalMonth)
										.setValue(currentHolidayItem.getMonthEndRemain());
								if (currentHolidayItem.getMonthEndRemain() < 0) {
									setForegroundRed(cells.get(firstRow + 3, 10 + totalMonth));
								}
							}
						}
					}

					// Current month
					if (currentMonth.compareTo(currentHolidayItem.getYm()) == 0) {
						// I1_2 代休_月初残_日数
						cells.get(firstRow, 5).setValue(currentHolidayItem.getMonthStartRemain());
						if (currentHolidayItem.getMonthStartRemain() < 0) {
							setForegroundRed(cells.get(firstRow, 5));
						}
						// I1_3 代休_使用数_日数
						cells.get(firstRow, 6).setValue(currentHolidayItem.getMonthUse());
						// I1_4 代休_残数_日数
						cells.get(firstRow, 7).setValue(currentHolidayItem.getMonthEndRemain());
						if (currentHolidayItem.getMonthEndRemain() < 0) {
							setForegroundRed(cells.get(firstRow, 7));
						}
						// I1_5 代休_未消化_日数
						cells.get(firstRow, 8).setValue(currentHolidayItem.getMonthExtinction());
						setForegroundRed(cells.get(firstRow, 8));
					}
				}

				// Set background
				for (int i = 0; i <= totalMonths(dataSource.getStartMonth().yearMonth(),
						dataSource.getEndMonth().yearMonth()); i++) {
					if (dataSource.getStartMonth().addMonths(i).yearMonth().compareTo(currentMonth) > 0) {
						setBackgroundGray(cells.get(firstRow + 2, 10 + i));
						setBackgroundGray(cells.get(firstRow + 3, 10 + i));
					}
					if (!dataSource.isSameCurrentMonth()
							&& dataSource.getStartMonth().addMonths(i).yearMonth().compareTo(currentMonth) == 0) {
						setCurrentMonthBackground(cells.get(firstRow, 10 + i));
						setCurrentMonthBackground(cells.get(firstRow + 1, 10 + i));
						setCurrentMonthBackground(cells.get(firstRow + 2, 10 + i));
						setCurrentMonthBackground(cells.get(firstRow + 3, 10 + i));
					}
				}
			}

			firstRow += 4;
		}
		return firstRow;
	}

	private int printPauseHoliday(Cells cells, int firstRow, HolidaysRemainingEmployee employee,
			HolidayRemainingDataSource dataSource) throws Exception {
		// 振休
		if (dataSource.getHolidaysRemainingManagement().getListItemsOutput().getPause().isPauseItem()) {
			cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 9, firstRow, 4);
			// J1_1
			cells.get(firstRow, 2).setValue(TextResource.localize("Com_SubstituteHoliday"));
			// J2_1
			cells.get(firstRow, 9).setValue(TextResource.localize("KDR001_16"));
			// J2_2
			cells.get(firstRow + 1, 9).setValue(TextResource.localize("KDR001_17"));
			// J2_3
			cells.get(firstRow + 2, 9).setValue(TextResource.localize("KDR001_11"));
			// J2_4
			cells.get(firstRow + 3, 9).setValue(TextResource.localize("KDR001_18"));

			if (employee.getCurrentMonth().isPresent()) {
				YearMonth currentMonth = employee.getCurrentMonth().get();
				// Set value for 振休 J
				// Call requestList270
				List<CurrentHolidayRemainImported> currentHolidayList = absenceReruitmentManaAdapter
						.getAbsRecRemainAggregate(employee.getEmployeeId(), dataSource.getBaseDate(),
								dataSource.getStartMonth().yearMonth(), dataSource.getEndMonth().yearMonth());
				// Call requestList260
				List<StatusOfHolidayImported> StatusOfHolidayList = absenceReruitmentManaAdapter
						.getDataCurrentMonthOfEmployee(employee.getEmployeeId(), dataSource.getStartMonth().yearMonth(),
								dataSource.getEndMonth().yearMonth());

				for (StatusOfHolidayImported statusOfHDItem : StatusOfHolidayList) {
					// Before this month
					if (currentMonth.compareTo(statusOfHDItem.getYm()) > 0) {
						int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(), statusOfHDItem.getYm());
						if (totalMonth >= 0) {
							// J2_5 振休_発生
							cells.get(firstRow, 10 + totalMonth).setValue(statusOfHDItem.getOccurredDay());
							// J2_6 振休_使用
							cells.get(firstRow + 1, 10 + totalMonth).setValue(statusOfHDItem.getUsedDays());
							// J2_7 振休_未消化
							cells.get(firstRow + 2, 10 + totalMonth).setValue(statusOfHDItem.getUnUsedDays());
							setForegroundRed(cells.get(firstRow + 2, 10 + totalMonth));
							// J2_8 振休_残数
							cells.get(firstRow + 3, 10 + totalMonth).setValue(statusOfHDItem.getRemainingDays());
							if (statusOfHDItem.getRemainingDays() < 0) {
								setForegroundRed(cells.get(firstRow + 3, 10 + totalMonth));
							}
						}
					}
				}
				for (CurrentHolidayRemainImported holidayRemainItem : currentHolidayList) {
					if (currentMonth.compareTo(holidayRemainItem.getYm()) <= 0) {
						int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(), holidayRemainItem.getYm());
						if (totalMonth >= 0) {
							// J2_5 振休_発生
							cells.get(firstRow, 10 + totalMonth).setValue(holidayRemainItem.getMonthOccurrence());
							// J2_6 振休_使用
							cells.get(firstRow + 1, 10 + totalMonth).setValue(holidayRemainItem.getMonthUse());
							if (currentMonth.compareTo(holidayRemainItem.getYm()) == 0) {
								// J2_7 振休_未消化
								cells.get(firstRow + 2, 10 + totalMonth)
										.setValue(holidayRemainItem.getMonthExtinction());
								setForegroundRed(cells.get(firstRow + 2, 10 + totalMonth));
								// J2_8 振休_残数
								cells.get(firstRow + 3, 10 + totalMonth)
										.setValue(holidayRemainItem.getMonthEndRemain());
								if (holidayRemainItem.getMonthEndRemain() < 0) {
									setForegroundRed(cells.get(firstRow + 3, 10 + totalMonth));
								}
							}
						}
					}

					// Current month
					if (currentMonth.compareTo(holidayRemainItem.getYm()) == 0) {
						// J1_2 振休_月初残
						cells.get(firstRow, 5).setValue(holidayRemainItem.getMonthStartRemain());
						if (holidayRemainItem.getMonthStartRemain() < 0) {
							setForegroundRed(cells.get(firstRow, 5));
						}
						// J1_3 振休_使用数
						cells.get(firstRow, 6).setValue(holidayRemainItem.getMonthUse());
						// J1_4 振休_残数
						cells.get(firstRow, 7).setValue(holidayRemainItem.getMonthEndRemain());
						if (holidayRemainItem.getMonthEndRemain() < 0) {
							setForegroundRed(cells.get(firstRow, 7));
						}
						// J1_5 振休_未消化
						cells.get(firstRow, 8).setValue(holidayRemainItem.getMonthExtinction());
						setForegroundRed(cells.get(firstRow, 8));
					}
				}

				// Set background
				for (int i = 0; i <= totalMonths(dataSource.getStartMonth().yearMonth(),
						dataSource.getEndMonth().yearMonth()); i++) {
					if (dataSource.getStartMonth().addMonths(i).yearMonth().compareTo(currentMonth) > 0) {
						setBackgroundGray(cells.get(firstRow + 2, 10 + i));
						setBackgroundGray(cells.get(firstRow + 3, 10 + i));
					}
					if (!dataSource.isSameCurrentMonth()
							&& dataSource.getStartMonth().addMonths(i).yearMonth().compareTo(currentMonth) == 0) {
						setCurrentMonthBackground(cells.get(firstRow, 10 + i));
						setCurrentMonthBackground(cells.get(firstRow + 1, 10 + i));
						setCurrentMonthBackground(cells.get(firstRow + 2, 10 + i));
						setCurrentMonthBackground(cells.get(firstRow + 3, 10 + i));
					}
				}
			}

			firstRow += 4;
		}
		return firstRow;
	}

	private int printSpecialHoliday(Cells cells, int firstRow, HolidaysRemainingEmployee employee,
			HolidayRemainingDataSource dataSource) throws Exception {
		// 特別休暇
		List<Integer> specialHoliday = dataSource.getHolidaysRemainingManagement().getListItemsOutput()
				.getSpecialHoliday();

		DatePeriod datePeriod = new DatePeriod(dataSource.getStartMonth(), dataSource.getEndMonth());
		String cid = AppContexts.user().companyId();
		List<SpecialHoliday> specialHolidays = specialHolidayRepository.findByCompanyId(cid);

		for (Integer aSpecialHoliday : specialHoliday) {
			cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 13, firstRow, 2);

			int specialHolidayCode = aSpecialHoliday;
			Optional<SpecialHoliday> specialHolidayOpt = specialHolidays.stream()
					.filter(c -> c.getSpecialHolidayCode().v() == specialHolidayCode).findFirst();
			if (specialHolidayOpt.isPresent()) {
				// M1_1 特別休暇
				cells.get(firstRow, 2).setValue(specialHolidayOpt.get().getSpecialHolidayName().v());
			}
			// M2_1
			cells.get(firstRow, 9).setValue(TextResource.localize("KDR001_17"));
			// M2_3
			cells.get(firstRow + 1, 9).setValue(TextResource.localize("KDR001_18"));

			if (employee.getCurrentMonth().isPresent()) {
				YearMonth currentMonth = employee.getCurrentMonth().get();

				// Call requestList273
				SpecialVacationImported specialVacationImported = complileInPeriodOfSpecialLeaveAdapter
						.complileInPeriodOfSpecialLeave(cid, employee.getEmployeeId(), datePeriod, false,
								dataSource.getBaseDate(), specialHolidayCode, false);
				// Call requestList263
				List<SpecialHolidayImported> specialHolidayList = complileInPeriodOfSpecialLeaveAdapter
						.getSpeHoliOfConfirmedMonthly(employee.getEmployeeId(), dataSource.getStartMonth().yearMonth(),
								dataSource.getEndMonth().yearMonth());

				// ドメインモデル「特別休暇付与残数データ」を取得
				List<SpecialLeaveGrantRemainingData> listSpecialLeaveGrant = specialLeaveGrantRepository
						.getAllByExpStatus(employee.getEmployeeId(), specialHolidayCode,
								LeaveExpirationStatus.AVAILABLE.value);

				// 全てのドメインモデル「特別休暇付与残数データ」の残時間を合計
				Double dayNumbers = listSpecialLeaveGrant.stream()
						.mapToDouble(item -> item.getDetails().getRemainingNumber().getDayNumberOfRemain().v()).sum();

				// M1_2 特別休暇１_付与数日数
				cells.get(firstRow, 4).setValue(specialVacationImported.getGrantDays());
				// M1_3 特別休暇１_月初残日数
				cells.get(firstRow, 5).setValue(dayNumbers);
				if (dayNumbers < 0) {
					setForegroundRed(cells.get(firstRow, 5));
				}
				// M1_4 特別休暇１_使用数日数
				cells.get(firstRow, 6).setValue(specialVacationImported.getUsedDate());
				// M1_5 特別休暇１_残数日数
				cells.get(firstRow, 7).setValue(specialVacationImported.getRemainDate());
				if (specialVacationImported.getRemainDate() < 0) {
					setForegroundRed(cells.get(firstRow, 7));
				}

				for (SpecialHolidayImported item : specialHolidayList) {

					// Before this month
					if (currentMonth.compareTo(item.getYm()) > 0) {
						int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(), item.getYm());
						if (totalMonth >= 0) {
							// M2_5 特別休暇１_使用日数
							cells.get(firstRow, 10 + totalMonth).setValue(item.getUseDays());
							// M2_7 特別休暇１_残数日数
							cells.get(firstRow + 1, 10 + totalMonth).setValue(item.getRemainDays());
							if (item.getRemainDays() < 0) {
								setForegroundRed(cells.get(firstRow + 1, 10 + totalMonth));
							}
						}
					}
					if (currentMonth.compareTo(item.getYm()) == 0) {
						int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(), item.getYm());
						if (totalMonth >= 0) {
							// M2_5 特別休暇１_使用日数
							cells.get(firstRow, 10 + totalMonth).setValue(specialVacationImported.getUsedDate());
							// M2_7 特別休暇１_残数日数
							cells.get(firstRow + 1, 10 + totalMonth).setValue(specialVacationImported.getRemainDate());
							if (specialVacationImported.getRemainDate() < 0) {
								setForegroundRed(cells.get(firstRow + 1, 10 + totalMonth));
							}
						}
					}
				}

				// Set background
				for (int index = 0; index <= totalMonths(dataSource.getStartMonth().yearMonth(),
						dataSource.getEndMonth().yearMonth()); index++) {
					if (dataSource.getStartMonth().addMonths(index).yearMonth().compareTo(currentMonth) > 0) {
						setBackgroundGray(cells.get(firstRow, 10 + index));
						setBackgroundGray(cells.get(firstRow + 1, 10 + index));
					}

					if (!dataSource.isSameCurrentMonth()
							&& dataSource.getStartMonth().addMonths(index).yearMonth().compareTo(currentMonth) == 0) {
						setCurrentMonthBackground(cells.get(firstRow, 10 + index));
						setCurrentMonthBackground(cells.get(firstRow + 1, 10 + index));
					}
				}
			}

			firstRow += 2;
		}

		return firstRow;
	}

	private int printChildNursingVacation(Cells cells, int firstRow, HolidaysRemainingEmployee employee,
			HolidayRemainingDataSource dataSource) throws Exception {
		// 子の看護休暇
		if (dataSource.getHolidaysRemainingManagement().getListItemsOutput().getChildNursingVacation()
				.isChildNursingLeave()) {
			DatePeriod datePeriod = new DatePeriod(dataSource.getStartMonth(), dataSource.getEndMonth());
			String cid = AppContexts.user().companyId();
			cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 15, firstRow, 2);
			// N1_1
			cells.get(firstRow, 2).setValue(TextResource.localize("KDR001_47"));
			// N2_1
			cells.get(firstRow, 9).setValue(TextResource.localize("KDR001_9"));
			// N2_2
			cells.get(firstRow + 1, 9).setValue(TextResource.localize("KDR001_18"));

			if (employee.getCurrentMonth().isPresent()) {
				YearMonth currentMonth = employee.getCurrentMonth().get();

				// Call requestList341
				ChildCareNursingRemainOutputPara currentSituationImported = childNursingLeaveMng
						.calChildNursOfInPeriod(cid, employee.getEmployeeId(), datePeriod, false);
				// N1_2 子の看護休暇_使用数
				cells.get(firstRow, 6).setValue(currentSituationImported.getBeforeUseDays());
				// N1_3 子の看護休暇_残数
				cells.get(firstRow, 7).setValue(currentSituationImported.getBeforeCareLeaveDays());
				if (currentSituationImported.getBeforeCareLeaveDays() < 0) {
					setForegroundRed(cells.get(firstRow, 7));
				}
				// N2_3 子の看護休暇_使用日数
				cells.get(firstRow, 11).setValue(currentSituationImported.getBeforeUseDays());

				// Position of current month
				int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(), currentMonth);
				if (currentMonth.compareTo(dataSource.getEndMonth().yearMonth()) <= 0) {
					// 子の看護休暇_使用日数
					String grantUsedDayValue = currentSituationImported.getBeforeUseDays() + "/"
							+ currentSituationImported.getAfterUseDays().orElse(0d);
					cells.get(firstRow, 10 + totalMonth).setValue(grantUsedDayValue);

					// N2_4 子の看護休暇_残日数
					String grantReamainDayValue = currentSituationImported.getBeforeCareLeaveDays() + "/"
							+ currentSituationImported.getAfterCareLeaveDays().orElse(0d);
					cells.get(firstRow + 1, 10 + totalMonth).setValue(grantReamainDayValue);
				}
				// Set background
				for (int i = 0; i <= totalMonths(dataSource.getStartMonth().yearMonth(),
						dataSource.getEndMonth().yearMonth()); i++) {
					if (dataSource.getStartMonth().addMonths(i).yearMonth().compareTo(currentMonth) > 0) {
						setBackgroundGray(cells.get(firstRow, 10 + i));
					}
					if (dataSource.getStartMonth().addMonths(i).yearMonth().compareTo(currentMonth) != 0) {
						setBackgroundGray(cells.get(firstRow + 1, 10 + i));
					}
					if (!dataSource.isSameCurrentMonth()
							&& dataSource.getStartMonth().addMonths(i).yearMonth().compareTo(currentMonth) == 0) {
						setCurrentMonthBackground(cells.get(firstRow, 10 + i));
						setCurrentMonthBackground(cells.get(firstRow + 1, 10 + i));
					}
				}
			}

			firstRow += 2;
		}
		return firstRow;
	}

	private int printNursingCareLeave(Cells cells, int firstRow, HolidaysRemainingEmployee employee,
			HolidayRemainingDataSource dataSource) throws Exception {
		// 介護休暇
		if (dataSource.getHolidaysRemainingManagement().getListItemsOutput().getNursingcareLeave().isNursingLeave()) {
			DatePeriod datePeriod = new DatePeriod(dataSource.getStartMonth(), dataSource.getEndMonth());
			String cid = AppContexts.user().companyId();

			cells.copyRows(cells, NUMBER_ROW_OF_HEADER + 17, firstRow, 2);
			// O1_1
			cells.get(firstRow, 2).setValue(TextResource.localize("KDR001_48"));
			// O2_1
			cells.get(firstRow, 9).setValue(TextResource.localize("KDR001_9"));
			// O2_2
			cells.get(firstRow + 1, 9).setValue(TextResource.localize("KDR001_18"));

			if (employee.getCurrentMonth().isPresent()) {
				YearMonth currentMonth = employee.getCurrentMonth().get();
				// Call requestList343
				ChildCareNursingRemainOutputPara careHoliday = careHolidayMngService.calCareRemainOfInPerior(cid,
						employee.getEmployeeId(), datePeriod, false);
				// O1_2 介護休暇_使用数
				cells.get(firstRow, 6).setValue(careHoliday.getBeforeUseDays());
				// O1_3 介護休暇_残数
				cells.get(firstRow, 7).setValue(careHoliday.getBeforeCareLeaveDays());
				if (careHoliday.getBeforeCareLeaveDays() < 0) {
					setForegroundRed(cells.get(firstRow, 7));
				}
				// O2_3 介護休暇_使用日数
				cells.get(firstRow, 11).setValue(careHoliday.getBeforeUseDays());

				// Position of current month
				int totalMonth = totalMonths(dataSource.getStartMonth().yearMonth(), currentMonth);
				if (currentMonth.compareTo(dataSource.getEndMonth().yearMonth()) <= 0) {
					// 介護休暇_使用日数
					String grantUsedDayValue = careHoliday.getBeforeUseDays() + "/"
							+ careHoliday.getAfterUseDays().orElse(0d);
					cells.get(firstRow, 10 + totalMonth).setValue(grantUsedDayValue);

					// O2_4 介護休暇_残日数
					String grantReamainDayValue = careHoliday.getBeforeCareLeaveDays() + "/"
							+ careHoliday.getAfterCareLeaveDays().orElse(0d);
					cells.get(firstRow + 1, 10 + totalMonth).setValue(grantReamainDayValue);
				}

				// Set background
				for (int i = 0; i <= totalMonths(dataSource.getStartMonth().yearMonth(),
						dataSource.getEndMonth().yearMonth()); i++) {
					if (dataSource.getStartMonth().addMonths(i).yearMonth().compareTo(currentMonth) > 0) {
						setBackgroundGray(cells.get(firstRow, 10 + i));
					}
					if (dataSource.getStartMonth().addMonths(i).yearMonth().compareTo(currentMonth) != 0) {
						setBackgroundGray(cells.get(firstRow + 1, 10 + i));
					}
					if (!dataSource.isSameCurrentMonth()
							&& dataSource.getStartMonth().addMonths(i).yearMonth().compareTo(currentMonth) == 0) {
						setCurrentMonthBackground(cells.get(firstRow, 10 + i));
						setCurrentMonthBackground(cells.get(firstRow + 1, 10 + i));
					}
				}
			}

			firstRow += 2;
		}
		return firstRow;
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
			totalRowDetails += 4;
		}
		// 振休
		if (dataSource.getHolidaysRemainingManagement().getListItemsOutput().getPause().isPauseItem()) {
			totalRowDetails += 4;
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

	private int totalMonths(YearMonth start, YearMonth end) {
		return (end.year() - start.year()) * TOTAL_MONTH_IN_YEAR + (end.month() - start.month());
	}
}
