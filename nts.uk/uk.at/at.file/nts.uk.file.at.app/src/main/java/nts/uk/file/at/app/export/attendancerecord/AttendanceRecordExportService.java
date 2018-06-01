package nts.uk.file.at.app.export.attendancerecord;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExport;
import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExportRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecord;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordRepositoty;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordRepository;
import nts.uk.ctx.at.record.app.service.attendanceitem.value.AttendanceItemValueService;
import nts.uk.ctx.at.shared.app.service.workrule.closure.ClosureEmploymentService;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportColumnData;
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportDailyData;
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportData;
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportEmployeeData;
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportWeeklyData;
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportWeeklySumaryData;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AttendanceRecordExportService extends ExportService<AttendanceRecordRequest> {

	final static long UPPER_POSITION = 1;
	final static long LOWER_POSITION = 2;

	@Inject
	private ClosureEmploymentService closureEmploymentService;

	@Inject
	private SingleAttendanceRecordRepository singleAttendanceRepo;

	@Inject
	private CalculateAttendanceRecordRepositoty calculateAttendanceRepo;

	@Inject
	private AttendanceItemValueService attendanceService;

	@Inject
	private AttendanceRecordExportSettingRepository attendanceRecExpSetRepo;

	@Inject
	private AttendanceRecordExportRepository attendanceRecExpRepo;

	@Inject
	private AttendanceRecordReportGenerator reportGenerator;
	
	@Inject 
	private CompanyRepository companyRepo;

	@Override
	protected void handle(ExportServiceContext<AttendanceRecordRequest> context) {

		// get Dto
		AttendanceRecordRequest request = context.getQuery();
		String companyId = AppContexts.user().companyId();
		Map<String, List<AttendanceRecordReportEmployeeData>> reportData = new HashMap<>();
		List<AttendanceRecordReportEmployeeData> attendanceRecRepEmpDataList = new ArrayList<>();
		request.getEmployeeList().forEach(employee -> {

			// get Closure
			Optional<Closure> optionalClosure = closureEmploymentService.findClosureByEmployee(employee.getEmployeeId(),
					request.getEndDate());
			ClosureDate closureDate = new ClosureDate(0, false);
			if (optionalClosure.isPresent()) {
				Closure closure = optionalClosure.get();

				// get closure history
				List<ClosureHistory> closureHistory = closure.getClosureHistories();

				// get init yearMonthMax
				YearMonth yearMonthMax = closureHistory.stream()
						.min(Comparator.comparing(ClosureHistory::getStartYearMonth))
						.orElseThrow(NoSuchElementException::new).getStartYearMonth();

				// find yearMonthMax
				for (ClosureHistory history : closureHistory) {
					YearMonth histYearMonth = history.getStartYearMonth();
					if (histYearMonth.greaterThan(yearMonthMax)
							&& histYearMonth.greaterThanOrEqualTo(request.getStartDate().yearMonth())
							&& histYearMonth.lessThanOrEqualTo(request.getEndDate().yearMonth())) {
						yearMonthMax = histYearMonth;
						closureDate = history.getClosureDate();

					}
				}

				// check if yearMonthMax is init value

				if (yearMonthMax.greaterThanOrEqualTo(request.getStartDate().yearMonth())
						&& yearMonthMax.lessThanOrEqualTo(request.getEndDate().yearMonth())) {

					// get upper-daily-singleItem list
					List<Integer> singleIdUpper = this.singleAttendanceRepo
							.getIdSingleAttendanceRecordByPosition(companyId, request.getLayout(), UPPER_POSITION);

					// get upper-daily-calculateItem list

					List<CalculateAttendanceRecord> calculateUpperDaily = this.calculateAttendanceRepo
							.getIdCalculateAttendanceRecordDailyByPosition(companyId, request.getLayout(),
									UPPER_POSITION);

					// get lower-daily-singleItem list
					List<Integer> singleIdLower = this.singleAttendanceRepo
							.getIdSingleAttendanceRecordByPosition(companyId, request.getLayout(), LOWER_POSITION);

					// get lower-daily-CalculateItem list

					List<CalculateAttendanceRecord> calculateLowerDaily = this.calculateAttendanceRepo
							.getIdCalculateAttendanceRecordDailyByPosition(companyId, request.getLayout(),
									LOWER_POSITION);

					// get upper-monthly-Item list
					List<CalculateAttendanceRecord> calculateUpperMonthly = this.calculateAttendanceRepo
							.getIdCalculateAttendanceRecordMonthlyByPosition(companyId, request.getLayout(),
									UPPER_POSITION);

					// get lower-monthly-Item list
					List<CalculateAttendanceRecord> calculateLowerMonthly = this.calculateAttendanceRepo
							.getIdCalculateAttendanceRecordMonthlyByPosition(companyId, request.getLayout(),
									LOWER_POSITION);

					YearMonth startYearMonth = request.getStartDate().yearMonth();
					YearMonth endYearMonth = request.getEndDate().yearMonth();
					YearMonth yearMonth = startYearMonth;

					while (yearMonth.lessThanOrEqualTo(endYearMonth)) {

						GeneralDate startDateByClosure = GeneralDate.ymd(yearMonth.year(), yearMonth.month(),
								closureDate.getClosureDay().v());
						GeneralDate endDateByClosure = GeneralDate.ymd(yearMonth.year(), yearMonth.month(),
								closureDate.getClosureDay().v());

						// amount day in month
						int flag = 0;

						// List DailyData
						List<AttendanceRecordReportDailyData> dailyDataList = new ArrayList<>();

						// Weekly Data
						List<AttendanceRecordReportWeeklyData> weeklyDataList = new ArrayList<>();

						// Report by Month
						while (startDateByClosure.beforeOrEquals(endDateByClosure)) {
							flag++;
							List<AttendanceRecordResponse> upperDailyRespond = new ArrayList<>();
							List<AttendanceRecordResponse> lowerDailyRespond = new ArrayList<>();
							// return result upper-daily-singleItems
							AttendanceItemValueService.AttendanceItemValueResult valueSingleUpper = attendanceService
									.getValueOf(employee.getEmployeeId(), startDateByClosure, singleIdUpper);
							valueSingleUpper.getAttendanceItems().forEach(item -> {
								upperDailyRespond.add(new AttendanceRecordResponse(employee.getEmployeeId(),
										employee.getEmployeeName(), startDateByClosure, "", item.getValue()));

							});

							// return result upper-daily-calculateItems
							calculateUpperDaily.forEach(item -> {
								// get add item
								AttendanceItemValueService.AttendanceItemValueResult addValueCalUpper = attendanceService
										.getValueOf(employee.getEmployeeId(), startDateByClosure, item.getAddedItem());

								// get sub item
								AttendanceItemValueService.AttendanceItemValueResult subValueCalUpper = attendanceService
										.getValueOf(employee.getEmployeeId(), startDateByClosure,
												item.getSubtractedItem());

								// get result upper calculate
								String result = this.getSumCalculateAttendanceItem(
										addValueCalUpper.getAttendanceItems(), subValueCalUpper.getAttendanceItems());

								upperDailyRespond.add(new AttendanceRecordResponse(employee.getEmployeeId(),
										employee.getEmployeeName(), startDateByClosure, "", result));

							});

							// return result lower-daily-singleItems
							AttendanceItemValueService.AttendanceItemValueResult valueSingleLower = attendanceService
									.getValueOf(employee.getEmployeeId(), startDateByClosure, singleIdLower);

							valueSingleLower.getAttendanceItems().forEach(item -> {
								lowerDailyRespond.add(new AttendanceRecordResponse(employee.getEmployeeId(),
										employee.getEmployeeName(), startDateByClosure, "", item.getValue()));

							});

							calculateLowerDaily.forEach(item -> {
								// get add item
								AttendanceItemValueService.AttendanceItemValueResult addValueCalUpper = attendanceService
										.getValueOf(employee.getEmployeeId(), startDateByClosure, item.getAddedItem());

								// get sub item
								AttendanceItemValueService.AttendanceItemValueResult subValueCalUpper = attendanceService
										.getValueOf(employee.getEmployeeId(), startDateByClosure,
												item.getSubtractedItem());

								// get result lower calculate
								String result = this.getSumCalculateAttendanceItem(
										addValueCalUpper.getAttendanceItems(), subValueCalUpper.getAttendanceItems());
								lowerDailyRespond.add(new AttendanceRecordResponse(employee.getEmployeeId(),
										employee.getEmployeeName(), startDateByClosure, "", result));
							});

							AttendanceRecordReportDailyData dailyData = new AttendanceRecordReportDailyData();
							dailyData.setDate(startDateByClosure.toString());
							dailyData.setDayOfWeek(startDateByClosure.localDate().getDayOfWeek().toString());
							AttendanceRecordReportColumnData[] columnDatasArray = new AttendanceRecordReportColumnData[9];
							int index = 0;
							for (AttendanceRecordResponse item : upperDailyRespond) {
								columnDatasArray[index] = new AttendanceRecordReportColumnData(item.getValue(), "");
								index++;
							}
							index = 0;
							for (AttendanceRecordResponse item : lowerDailyRespond) {
								columnDatasArray[index].setLower(item.getValue());
								index++;

							}
							List<AttendanceRecordReportColumnData> columnDatas = new ArrayList<>();
							for (int i = 0; i < columnDatasArray.length; i++) {
								columnDatas.add(columnDatasArray[i]);
							}

							dailyData.setColumnDatas(columnDatas);
							dailyData.setSecondCol(flag <= 15 ? false : true);
							dailyDataList.add(dailyData);
							// Check end of week
							if (startDateByClosure.localDate().getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
								AttendanceRecordReportWeeklyData weeklyData = new AttendanceRecordReportWeeklyData();
								weeklyData.setDailyDatas(dailyDataList);
								AttendanceRecordReportWeeklySumaryData summaryWeeklyData = new AttendanceRecordReportWeeklySumaryData();
								summaryWeeklyData = this.getSumWeeklyValue(dailyDataList);

								weeklyData.setWeeklySumaryData(summaryWeeklyData);

								weeklyDataList.add(weeklyData);
								// empty daily data list
								dailyDataList = new ArrayList<>();

							}
							startDateByClosure.addDays(1);
						}
						if (dailyDataList.size() > 0) {
							AttendanceRecordReportWeeklyData weeklyData = new AttendanceRecordReportWeeklyData();
							weeklyData.setDailyDatas(dailyDataList);
							AttendanceRecordReportWeeklySumaryData summaryWeeklyData = new AttendanceRecordReportWeeklySumaryData();
							summaryWeeklyData = this.getSumWeeklyValue(dailyDataList);

							weeklyData.setWeeklySumaryData(summaryWeeklyData);

							weeklyDataList.add(weeklyData);
							// empty daily data list
							dailyDataList = new ArrayList<>();
						}
						// return result upper-monthly-Items
						List<String> upperResult = new ArrayList<>();
						List<String> lowerResult = new ArrayList<>();
						for (CalculateAttendanceRecord item : calculateUpperMonthly) {
							AttendanceItemValueService.MonthlyAttendanceItemValueResult monthlyUpperAddResult = attendanceService
									.getMonthlyValueOf(employee.getEmployeeId(), yearMonth,
											closure.getClosureId().value, closureDate.getClosureDay().v(),
											closureDate.getLastDayOfMonth(), item.getAddedItem());

							AttendanceItemValueService.MonthlyAttendanceItemValueResult monthlyUpperSubResult = attendanceService
									.getMonthlyValueOf(employee.getEmployeeId(), yearMonth,
											closure.getClosureId().value, closureDate.getClosureDay().v(),
											closureDate.getLastDayOfMonth(), item.getSubtractedItem());

							String result = this.getSumCalculateAttendanceItem(
									monthlyUpperAddResult.getAttendanceItems(),
									monthlyUpperSubResult.getAttendanceItems());
							upperResult.add(result);

						}
						// return result lower-monthly-Items
						for (CalculateAttendanceRecord item : calculateLowerMonthly) {
							AttendanceItemValueService.MonthlyAttendanceItemValueResult monthlyLowerAddResult = attendanceService
									.getMonthlyValueOf(employee.getEmployeeId(), yearMonth,
											closure.getClosureId().value, closureDate.getClosureDay().v(),
											closureDate.getLastDayOfMonth(), item.getAddedItem());

							AttendanceItemValueService.MonthlyAttendanceItemValueResult monthlyLowerSubResult = attendanceService
									.getMonthlyValueOf(employee.getEmployeeId(), yearMonth,
											closure.getClosureId().value, closureDate.getClosureDay().v(),
											closureDate.getLastDayOfMonth(), item.getSubtractedItem());

							String result = this.getSumCalculateAttendanceItem(
									monthlyLowerAddResult.getAttendanceItems(),
									monthlyLowerSubResult.getAttendanceItems());
							lowerResult.add(result);

						}

						// Convert to AttendanceRecordReportColumnData
						List<AttendanceRecordReportColumnData> employeeMonthlyData = new ArrayList<>();

						AttendanceRecordReportColumnData[] columnDataMonthlyArray = new AttendanceRecordReportColumnData[9];
						int index = 0;
						for (String item : upperResult) {
							columnDataMonthlyArray[index] = new AttendanceRecordReportColumnData(item, "");
							index++;
						}
						index = 0;
						for (String item : lowerResult) {
							columnDataMonthlyArray[index].setLower(item);
							index++;

						}

						for (int i = 0; i < columnDataMonthlyArray.length; i++) {
							employeeMonthlyData.add(columnDataMonthlyArray[i]);
						}
						// Get Employee information

						// Get AttendanceRecordReportEmployeeData
						AttendanceRecordReportEmployeeData attendanceRecRepEmpData = new AttendanceRecordReportEmployeeData();

						attendanceRecRepEmpData.setEmployeeMonthlyData(employeeMonthlyData);
						attendanceRecRepEmpData.setWeeklyDatas(weeklyDataList);

						/**
						 * Need information
						 * 
						 * The invidual. The workplace. The employment The
						 * title. The work type The year month
						 **/
						// Fake Data
						attendanceRecRepEmpData.setEmployment("Nothing");
						attendanceRecRepEmpData.setInvidual("001 SomeOne");
						attendanceRecRepEmpData.setTitle("Blank");
						attendanceRecRepEmpData.setWorkplace("NoWhere");
						attendanceRecRepEmpData.setWorkType("FullTime");
						attendanceRecRepEmpData.setYearMonth(yearMonth.toString());
						attendanceRecRepEmpDataList.add(attendanceRecRepEmpData);

						yearMonth.addMonths(1);

					}

				}
				else{
					BundledBusinessException exceptions = BundledBusinessException.newInstance();
					exceptions.addMessage("Msg_1269");
					exceptions.throwExceptions();
				}

			}

		});
		// Map Data by month
		YearMonth startYearMonth = request.getStartDate().yearMonth();
		YearMonth endYearMonth = request.getEndDate().yearMonth();
		YearMonth yearMonth = startYearMonth;

		while (yearMonth.lessThanOrEqualTo(endYearMonth)) {
			List<AttendanceRecordReportEmployeeData> attendanceRecRepEmpDataByMonthList = new ArrayList<>();
			attendanceRecRepEmpDataList.forEach(item -> {
				if (item.getYearMonth().equals(yearMonth)) {
					attendanceRecRepEmpDataByMonthList.add(item);
				}

			});
			reportData.put(yearMonth.toString(), attendanceRecRepEmpDataByMonthList);

			yearMonth.addMonths(1);
		}

		//get seal stamp
		List<String> sealStamp = attendanceRecExpSetRepo.getSealStamp(companyId, request.getLayout());

		List<AttendanceRecordExport> dailyRecord = attendanceRecExpRepo.getAllAttendanceRecordExportDaily(companyId,
				request.getLayout());
		List<AttendanceRecordExport> monthlyRecord = attendanceRecExpRepo.getAllAttendanceRecordExportMonthly(companyId,
				request.getLayout());

		//get header
		List<AttendanceRecordReportColumnData> dailyHeader = new ArrayList<>();
		List<AttendanceRecordReportColumnData> monthlyHeader = new ArrayList<>();

		dailyRecord.forEach(item -> {
			String upperheader = item.getUpperPosition().get().getNameDisplay();
			String lowerheader = item.getLowerPosition().get().getNameDisplay();
			dailyHeader.add(new AttendanceRecordReportColumnData(upperheader, lowerheader));
		});

		monthlyRecord.forEach(item -> {
			String upperheader = item.getUpperPosition().get().getNameDisplay();
			String lowerheader = item.getLowerPosition().get().getNameDisplay();
			monthlyHeader.add(new AttendanceRecordReportColumnData(upperheader, lowerheader));
		});

		String exportDate = LocalDate.now().toString();

		AttendanceRecordReportData recordReportData = new AttendanceRecordReportData();
		Optional<Company> optionalCompany = companyRepo.find(companyId);
		Optional<AttendanceRecordExportSetting> optionalAttendanceRecExpSet = attendanceRecExpSetRepo.getAttendanceRecExpSet(companyId, request.getLayout());
		
		recordReportData.setCompanyName(optionalCompany.get().getCompanyName().toString());
		recordReportData.setDailyHeader(dailyHeader);
		recordReportData.setExportDateTime(exportDate);
		recordReportData.setMonthlyHeader(monthlyHeader);
		recordReportData.setReportData(reportData);
		recordReportData.setReportName(optionalAttendanceRecExpSet.get().getName().v());
		recordReportData.setSealColName(sealStamp);

		AttendanceRecordReportDatasource recordReportDataSource = new AttendanceRecordReportDatasource(
				recordReportData);

		reportGenerator.generate(context.getGeneratorContext(), recordReportDataSource);

	}

	Integer getNumberFromString(String string) {

		String result = string.replaceAll("\\D+", "");
		if (result != "")
			return Integer.parseInt(result);
		return 0;
	}

	String getPeriodFromString(List<ItemValue> list) {
		for (ItemValue i : list) {
			if (i.value() != null) {
				String tmp = this.getNumberFromString(i.value().toString()).toString();
				int index = i.value().toString().indexOf(tmp);
				return i.value().toString().substring(index);
			}
		}
		return null;

	}

	String getSumCalculateAttendanceItem(List<ItemValue> addValueCalUpper, List<ItemValue> subValueCalUpper) {

		Object sum;
		switch (addValueCalUpper.get(0).getValueType().value) {
		case (0):
			sum = new Integer(0);
			// calculate add
			for (ItemValue i : addValueCalUpper) {
				sum = Integer.parseInt(sum.toString()) + (int) i.value();
			}
			// calculate sub
			for (ItemValue i : subValueCalUpper) {
				sum = Integer.parseInt(sum.toString()) - (int) i.value();
			}
			break;
		case (1):
			sum = new String("");
			Integer tmp = 0;
			String period;
			for (ItemValue i : addValueCalUpper) {
				if (i.value() != null) {
					tmp = tmp + (int) this.getNumberFromString(i.value().toString());
				}

			}
			// calculate sub
			for (ItemValue i : subValueCalUpper) {
				tmp = Integer.parseInt(sum.toString()) - (int) this.getNumberFromString(i.value().toString());
			}

			period = this.getPeriodFromString(subValueCalUpper);

			sum = tmp + period;
			break;
		case (2):
			sum = new Double(0);
			// calculate add
			for (ItemValue i : addValueCalUpper) {
				sum = Double.parseDouble(sum.toString()) + (Double) i.value();
			}
			// calculate sub
			for (ItemValue i : subValueCalUpper) {
				sum = Double.parseDouble(sum.toString()) - (Double) i.value();
			}
			break;
		case (3):
			sum = new BigDecimal(0);
			BigDecimal sum1 = new BigDecimal(0);
			// calculate add
			for (ItemValue i : addValueCalUpper) {
				sum1 = sum1.add(new BigDecimal(i.value().toString()));
			}
			// calculate sub
			for (ItemValue i : subValueCalUpper) {
				sum1 = sum1.subtract(new BigDecimal(i.value().toString()));
			}
			sum = sum1;
			break;
		default:
			sum = new String("");
		}
		return sum.toString();
	}

	public AttendanceRecordReportWeeklySumaryData getSumWeeklyValue(List<AttendanceRecordReportDailyData> list) {
		AttendanceRecordReportWeeklySumaryData result = new AttendanceRecordReportWeeklySumaryData();

		String upperValue7th = "";
		String lowerValue7th = "";
		String upperValue8th = "";
		String lowerValue8th = "";
		String upperValue9th = "";
		String lowerValue9th = "";

		for (AttendanceRecordReportDailyData item : list) {
			upperValue7th = this.add(upperValue7th, item.getColumnDatas().get(7).getUper());
			lowerValue7th = this.add(upperValue7th, item.getColumnDatas().get(7).getLower());
			upperValue8th = this.add(upperValue7th, item.getColumnDatas().get(8).getUper());
			lowerValue8th = this.add(upperValue7th, item.getColumnDatas().get(8).getLower());
			upperValue9th = this.add(upperValue7th, item.getColumnDatas().get(9).getUper());
			lowerValue9th = this.add(upperValue7th, item.getColumnDatas().get(9).getLower());

		}
		List<AttendanceRecordReportColumnData> columnDatas = new ArrayList<>();
		AttendanceRecordReportColumnData columnData7 = new AttendanceRecordReportColumnData(upperValue7th,
				lowerValue7th);
		columnDatas.add(columnData7);
		AttendanceRecordReportColumnData columnData8 = new AttendanceRecordReportColumnData(upperValue8th,
				lowerValue8th);
		columnDatas.add(columnData8);
		AttendanceRecordReportColumnData columnData9 = new AttendanceRecordReportColumnData(upperValue9th,
				lowerValue9th);
		columnDatas.add(columnData9);

		result.setDateRange(list.get(0).getDate() + "-" + list.get(list.size() - 1).getDate());
		result.setColumnDatas(columnDatas);
		result.setSecondCol(list.get(list.size()).isSecondCol());

		return result;

	}

	public String add(String a, String b) {
		if (a == "")
			return b;
		if (b == "")
			return a;

		// case a!= "" and b!= ""
		try {
			// In case Double or Integer
			Double sum = Double.parseDouble(a) + Double.parseDouble(b);
			return sum.toString();
		} catch (NumberFormatException nfe) {

			try {
				// In case BigDecimal
				BigDecimal sum = new BigDecimal(a).add(new BigDecimal(b));
				return sum.toString();
			} catch (Exception ex) {

				// In case String
				Integer sum = this.getNumberFromString(a) + this.getNumberFromString(b);
				return sum.toString();
			}
		}
	}

}
