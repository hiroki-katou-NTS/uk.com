package nts.uk.file.at.app.export.attendancerecord;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
import nts.uk.ctx.at.record.app.service.attendanceitem.value.AttendanceItemValueService.AttendanceItemValueResult;
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
import nts.uk.query.pub.employee.EmployeeInformationExport;
import nts.uk.query.pub.employee.EmployeeInformationPub;
import nts.uk.query.pub.employee.EmployeeInformationQueryDto;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AttendanceRecordExportService extends ExportService<AttendanceRecordRequest> {

	final static long UPPER_POSITION = 1;
	final static long LOWER_POSITION = 2;
	final static int PDF_MODE = 1;

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

	@Inject
	private EmployeeInformationPub employeePub;

	// @Inject
	// private EmployeeInformationPub employeeInfo;

	@Override
	protected void handle(ExportServiceContext<AttendanceRecordRequest> context) {

		// get Dto
		AttendanceRecordRequest request = context.getQuery();
		String companyId = AppContexts.user().companyId();
		Map<String, List<AttendanceRecordReportEmployeeData>> reportData = new HashMap<>();
		List<AttendanceRecordReportEmployeeData> attendanceRecRepEmpDataList = new ArrayList<AttendanceRecordReportEmployeeData>();
		BundledBusinessException exceptions = BundledBusinessException.newInstance();

		request.getEmployeeList().forEach(employee -> {

			// get Closure
			Optional<Closure> optionalClosure = closureEmploymentService.findClosureByEmployee(employee.getEmployeeId(),
					request.getEndDate());
			ClosureDate closureDate = new ClosureDate(0, false);
			if (optionalClosure.isPresent()) {
				Closure closure = optionalClosure.get();

				// get closure history
				List<ClosureHistory> closureHistory = closure.getClosureHistories();

				// find closure history
				for (ClosureHistory history : closureHistory) {
					if (history.getStartYearMonth().lessThanOrEqualTo(request.getEndDate().yearMonth())
							&& history.getEndYearMonth().greaterThanOrEqualTo(request.getEndDate().yearMonth())) {
						closureDate = history.getClosureDate();

					}
				}

				// check if closure is found

				if (closureDate.getClosureDay().v() != 0) {

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
						GeneralDate endDateByClosure = GeneralDate.ymd(yearMonth.addMonths(1).year(),
								yearMonth.addMonths(1).month(), closureDate.getClosureDay().v());

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
							AttendanceItemValueService.AttendanceItemValueResult valueSingleUpper = null;
							GeneralDate closureDateTemp = startDateByClosure;
							if (!singleIdUpper.isEmpty()) {
								valueSingleUpper = AttendanceItemValueResult.builder()
										.employeeId(employee.getEmployeeId()).workingDate(startDateByClosure)
										.attendanceItems(new ArrayList<ItemValue>()).build();
								for (Integer item : singleIdUpper) {
									valueSingleUpper.getAttendanceItems()
											.add(attendanceService
													.getValueOf(employee.getEmployeeId(), startDateByClosure, item)
													.isPresent()
															? attendanceService.getValueOf(employee.getEmployeeId(),
																	startDateByClosure, item).get()
															: new ItemValue());

								}
							}

							if (valueSingleUpper != null) {

								valueSingleUpper.getAttendanceItems().forEach(item -> {
									upperDailyRespond.add(new AttendanceRecordResponse(employee.getEmployeeId(),
											employee.getEmployeeName(), closureDateTemp, "", item.getValue()));

								});
							}
							// return result upper-daily-calculateItems
							calculateUpperDaily.forEach(item -> {
								// get add item
								AttendanceItemValueService.AttendanceItemValueResult addValueCalUpper = null;
								if (!item.getAddedItem().isEmpty()) {
									addValueCalUpper = attendanceService.getValueOf(employee.getEmployeeId(),
											closureDateTemp, item.getAddedItem());

								}
								// get sub item
								AttendanceItemValueService.AttendanceItemValueResult subValueCalUpper = null;

								if (!item.getSubtractedItem().isEmpty()) {
									subValueCalUpper = attendanceService.getValueOf(employee.getEmployeeId(),
											closureDateTemp, item.getSubtractedItem());
								}
								// get result upper calculate
								String result = "";
								if (!item.getAddedItem().isEmpty() && !item.getSubtractedItem().isEmpty()) {
									result = this.getSumCalculateAttendanceItem(addValueCalUpper.getAttendanceItems(),
											subValueCalUpper.getAttendanceItems());
								}
								upperDailyRespond.add(new AttendanceRecordResponse(employee.getEmployeeId(),
										employee.getEmployeeName(), closureDateTemp, "", result));

							});

							// return result lower-daily-singleItems
							AttendanceItemValueService.AttendanceItemValueResult valueSingleLower = null;
							if (!singleIdLower.isEmpty()) {

								valueSingleLower = AttendanceItemValueResult.builder()
										.employeeId(employee.getEmployeeId()).workingDate(startDateByClosure)
										.attendanceItems(new ArrayList<ItemValue>()).build();
								for (Integer item : singleIdLower) {
									valueSingleLower.getAttendanceItems()
											.add(attendanceService
													.getValueOf(employee.getEmployeeId(), startDateByClosure, item)
													.isPresent()
															? attendanceService.getValueOf(employee.getEmployeeId(),
																	startDateByClosure, item).get()
															: new ItemValue());

								}
							}

							if (valueSingleLower != null && !valueSingleLower.getAttendanceItems().isEmpty())
								valueSingleLower.getAttendanceItems().forEach(item -> {
									lowerDailyRespond.add(new AttendanceRecordResponse(employee.getEmployeeId(),
											employee.getEmployeeName(), closureDateTemp, "", item.getValue()));

								});

							calculateLowerDaily.forEach(item -> {
								// get add item
								AttendanceItemValueService.AttendanceItemValueResult addValueCalUpper = null;
								if (!item.getAddedItem().isEmpty()) {
									addValueCalUpper = attendanceService.getValueOf(employee.getEmployeeId(),
											closureDateTemp, item.getAddedItem());
								}

								// get sub item
								AttendanceItemValueService.AttendanceItemValueResult subValueCalUpper = null;
								if (!item.getSubtractedItem().isEmpty()) {
									subValueCalUpper = attendanceService.getValueOf(employee.getEmployeeId(),
											closureDateTemp, item.getSubtractedItem());
								}
								// get result lower calculate
								String result = "";
								if (addValueCalUpper != null && subValueCalUpper != null) {
									result = this.getSumCalculateAttendanceItem(addValueCalUpper.getAttendanceItems(),
											subValueCalUpper.getAttendanceItems());
								}
								lowerDailyRespond.add(new AttendanceRecordResponse(employee.getEmployeeId(),
										employee.getEmployeeName(), closureDateTemp, "", result));
							});

							AttendanceRecordReportDailyData dailyData = new AttendanceRecordReportDailyData();
							dailyData.setDate(String.valueOf(startDateByClosure.day()));
							dailyData.setDayOfWeek(DayOfWeekJP
									.getValue(startDateByClosure.localDate().getDayOfWeek().toString()).japanese);
							AttendanceRecordReportColumnData[] columnDatasArray = new AttendanceRecordReportColumnData[9];
							int index = 0;
							for (AttendanceRecordResponse item : upperDailyRespond) {
								columnDatasArray[index] = new AttendanceRecordReportColumnData("", "");
								if (item.getValue() != null)
									columnDatasArray[index].setUper(item.getValue());
								index++;
							}
							index = 0;
							for (AttendanceRecordResponse item : lowerDailyRespond) {
								if (item.getValue() != null)
									columnDatasArray[index].setLower(item.getValue());
								index++;

							}
							List<AttendanceRecordReportColumnData> columnDatas = new ArrayList<>();
							for (int i = 0; i < columnDatasArray.length; i++) {
								if (columnDatasArray[i] == null)
									columnDatasArray[i] = new AttendanceRecordReportColumnData("", "");
								columnDatas.add(columnDatasArray[i]);
							}

							dailyData.setColumnDatas(columnDatas);
							dailyData.setSecondCol(flag <= 16 ? false : true);
							dailyDataList.add(dailyData);
							// Check end of week
							if (startDateByClosure.localDate().getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
								AttendanceRecordReportWeeklyData weeklyData = new AttendanceRecordReportWeeklyData();
								weeklyData.setDailyDatas(dailyDataList);
								AttendanceRecordReportWeeklySumaryData summaryWeeklyData = new AttendanceRecordReportWeeklySumaryData();
								summaryWeeklyData = this.getSumWeeklyValue(dailyDataList);

								weeklyData.setWeeklySumaryData(summaryWeeklyData);

								weeklyDataList.add(weeklyData);
								// empty daily data list
								dailyDataList = new ArrayList<>();

							}
							startDateByClosure = startDateByClosure.addDays(1);

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
							AttendanceItemValueService.MonthlyAttendanceItemValueResult monthlyUpperAddResult = null;
							AttendanceItemValueService.MonthlyAttendanceItemValueResult monthlyUpperSubResult = null;
							if (item.getAddedItem() != null && !item.getAddedItem().isEmpty()) {
								monthlyUpperAddResult = attendanceService.getMonthlyValueOf(employee.getEmployeeId(),
										yearMonth, closure.getClosureId().value, closureDate.getClosureDay().v(),
										closureDate.getLastDayOfMonth(), item.getAddedItem());
							}
							if (item.getSubtractedItem() != null && !item.getSubtractedItem().isEmpty()) {
								monthlyUpperSubResult = attendanceService.getMonthlyValueOf(employee.getEmployeeId(),
										yearMonth, closure.getClosureId().value, closureDate.getClosureDay().v(),
										closureDate.getLastDayOfMonth(), item.getSubtractedItem());
							}
							String result = "";
							if (monthlyUpperAddResult != null && monthlyUpperSubResult != null) {
								result = this.getSumCalculateAttendanceItem(monthlyUpperAddResult.getAttendanceItems(),
										monthlyUpperSubResult.getAttendanceItems());
							}
							upperResult.add(result);

						}
						// return result lower-monthly-Items
						for (CalculateAttendanceRecord item : calculateLowerMonthly) {
							AttendanceItemValueService.MonthlyAttendanceItemValueResult monthlyLowerAddResult = null;
							AttendanceItemValueService.MonthlyAttendanceItemValueResult monthlyLowerSubResult = null;
							if (item.getAddedItem() != null && !item.getAddedItem().isEmpty()) {
								monthlyLowerAddResult = attendanceService.getMonthlyValueOf(employee.getEmployeeId(),
										yearMonth, closure.getClosureId().value, closureDate.getClosureDay().v(),
										closureDate.getLastDayOfMonth(), item.getAddedItem());
							}

							if (item.getSubtractedItem() != null && !item.getSubtractedItem().isEmpty()) {
								monthlyLowerSubResult = attendanceService.getMonthlyValueOf(employee.getEmployeeId(),
										yearMonth, closure.getClosureId().value, closureDate.getClosureDay().v(),
										closureDate.getLastDayOfMonth(), item.getSubtractedItem());

							}
							String result = new String("");
							if (monthlyLowerAddResult != null && monthlyLowerSubResult != null) {
								result = this.getSumCalculateAttendanceItem(monthlyLowerAddResult.getAttendanceItems(),
										monthlyLowerSubResult.getAttendanceItems());
							}
							lowerResult.add(result);

						}

						// Convert to AttendanceRecordReportColumnData
						List<AttendanceRecordReportColumnData> employeeMonthlyData = new ArrayList<>();

						AttendanceRecordReportColumnData[] columnDataMonthlyArray = new AttendanceRecordReportColumnData[12];
						int index = 0;
						for (String item : upperResult) {
							columnDataMonthlyArray[index] = new AttendanceRecordReportColumnData("", "");
							if (item != null)
								columnDataMonthlyArray[index].setUper(item);
							index++;
						}
						index = 0;
						for (String item : lowerResult) {
							if (item != null)
								columnDataMonthlyArray[index].setLower(item);
							index++;

						}

						for (int i = 0; i < columnDataMonthlyArray.length; i++) {
							if (columnDataMonthlyArray[i] == null)
								columnDataMonthlyArray[i] = new AttendanceRecordReportColumnData("", "");
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
						List<String> employeeIds = new ArrayList<>();
						GeneralDate referenceDate = GeneralDate.ymd(closure.getClosureMonth().getProcessingYm().year(),
								closure.getClosureMonth().getProcessingYm().month(), closureDate.getClosureDay().v());
						employeeIds.add(employee.getEmployeeId());
						// build param
						EmployeeInformationQueryDto param = EmployeeInformationQueryDto.builder()
								.employeeIds(employeeIds).referenceDate(referenceDate).toGetWorkplace(true)
								.toGetDepartment(false).toGetPosition(true).toGetEmployment(true)
								.toGetClassification(false).toGetEmploymentCls(true).build();

						List<EmployeeInformationExport> employeeInfoList = employeePub.find(param);
						EmployeeInformationExport result = employeeInfoList.get(0);

						attendanceRecRepEmpData.setEmployment(result.getEmployment().getEmploymentName().toString());
						attendanceRecRepEmpData
								.setInvidual(employee.getEmployeeCode() + " " + employee.getEmployeeName());
						attendanceRecRepEmpData.setTitle(result.getPosition().getPositionName().toString());
						attendanceRecRepEmpData.setWorkplace(result.getWorkplace().getWorkplaceName().toString());
						attendanceRecRepEmpData.setWorkType(result.getEmployment().getEmploymentName().toString());
						attendanceRecRepEmpData.setYearMonth(yearMonth.toString());
						attendanceRecRepEmpDataList.add(attendanceRecRepEmpData);

						yearMonth = yearMonth.addMonths(1);

					}

				} else {
					exceptions.addMessage("Msg_1269");
					exceptions.throwExceptions();
				}

			} else {

				exceptions.addMessage("Msg_1269");
				exceptions.throwExceptions();
			}

		});
		if (request.getMode() == PDF_MODE) {

			for (Employee employee : request.getEmployeeList()) {
				List<AttendanceRecordReportEmployeeData> attendanceRecRepEmpDataByMonthList = new ArrayList<>();
				for (AttendanceRecordReportEmployeeData item : attendanceRecRepEmpDataList) {

					if (this.getNameFromInvidual(item.getInvidual()).equals(employee.getEmployeeName())) {
						attendanceRecRepEmpDataByMonthList.add(item);
					}

				}
				reportData.put(employee.getEmployeeCode(), attendanceRecRepEmpDataByMonthList);

			}

		} else {
			// Map Data by month
			YearMonth startYearMonth = request.getStartDate().yearMonth();
			YearMonth endYearMonth = request.getEndDate().yearMonth();
			YearMonth yearMonth = startYearMonth;

			while (yearMonth.lessThanOrEqualTo(endYearMonth)) {
				List<AttendanceRecordReportEmployeeData> attendanceRecRepEmpDataByMonthList = new ArrayList<>();
				for (AttendanceRecordReportEmployeeData item : attendanceRecRepEmpDataList) {

					if (item.getYearMonth().equals(yearMonth.toString()) && item != null) {
						attendanceRecRepEmpDataByMonthList.add(item);
					}

				}
				reportData.put(yearMonth.toString(), attendanceRecRepEmpDataByMonthList);

				yearMonth = yearMonth.addMonths(1);
			}
			reportData = reportData.entrySet().stream().sorted((x, y) -> x.getKey().compareTo(y.getKey()))
					.collect(LinkedHashMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), Map::putAll);

		}
		// get seal stamp
		List<String> sealStamp = attendanceRecExpSetRepo.getSealStamp(companyId, request.getLayout());

		List<AttendanceRecordExport> dailyRecord = attendanceRecExpRepo.getAllAttendanceRecordExportDaily(companyId,
				request.getLayout());
		List<AttendanceRecordExport> dailyRecordTotal = new ArrayList<>();

		for (int i = 1; i <= 9; i++) {
			if (this.findIndexInList(i, dailyRecord) == null) {
				AttendanceRecordExport item = new AttendanceRecordExport();
				item.setLowerPosition(null);
				item.setUpperPosition(null);
				dailyRecordTotal.add(item);
			} else {
				dailyRecordTotal.add(this.findIndexInList(i, dailyRecord));
			}
		}

		List<AttendanceRecordExport> monthlyRecord = attendanceRecExpRepo.getAllAttendanceRecordExportMonthly(companyId,
				request.getLayout());
		List<AttendanceRecordExport> monthlyRecordTotal = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			if (this.findIndexInList(i, monthlyRecord) == null) {
				AttendanceRecordExport item = new AttendanceRecordExport();
				item.setLowerPosition(null);
				item.setUpperPosition(null);
				monthlyRecordTotal.add(item);
			} else {
				monthlyRecordTotal.add(this.findIndexInList(i, monthlyRecord));
			}
		}

		// get header
		List<AttendanceRecordReportColumnData> dailyHeader = new ArrayList<AttendanceRecordReportColumnData>();
		List<AttendanceRecordReportColumnData> monthlyHeader = new ArrayList<AttendanceRecordReportColumnData>();

		for (AttendanceRecordExport item : dailyRecordTotal) {
			try {
				String upperheader = "";
				String lowerheader = "";
				if (item.getUpperPosition() != null && item.getUpperPosition().isPresent())
					upperheader = item.getUpperPosition().get().getNameDisplay();
				if (item.getLowerPosition() != null && item.getLowerPosition().isPresent())
					lowerheader = item.getLowerPosition().get().getNameDisplay();
				AttendanceRecordReportColumnData temp = (new AttendanceRecordReportColumnData(upperheader,
						lowerheader));
				dailyHeader.add(temp);
			} catch (Exception ex) {
				exceptions.addMessage(ex.getMessage());
				exceptions.throwExceptions();
			}
		}

		for (AttendanceRecordExport item : monthlyRecordTotal) {
			String upperheader = "";
			String lowerheader = "";
			if (item.getUpperPosition() != null && item.getUpperPosition().isPresent())
				upperheader = item.getUpperPosition().get().getNameDisplay();
			if (item.getLowerPosition() != null && item.getLowerPosition().isPresent())
				lowerheader = item.getLowerPosition().get().getNameDisplay();
			monthlyHeader.add(new AttendanceRecordReportColumnData(upperheader, lowerheader));
		}

		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

		LocalDateTime presentDate = LocalDateTime.now();
		String exportDate = presentDate.format(format).toString();

		AttendanceRecordReportData recordReportData = new AttendanceRecordReportData();
		Optional<Company> optionalCompany = companyRepo.find(companyId);
		Optional<AttendanceRecordExportSetting> optionalAttendanceRecExpSet = attendanceRecExpSetRepo
				.getAttendanceRecExpSet(companyId, request.getLayout());

		recordReportData.setCompanyName(optionalCompany.get().getCompanyName().toString());
		recordReportData.setDailyHeader(dailyHeader);
		recordReportData.setExportDateTime(exportDate);
		recordReportData.setMonthlyHeader(monthlyHeader);
		recordReportData.setReportData(reportData);
		recordReportData.setReportName(optionalAttendanceRecExpSet.get().getName().v());
		recordReportData.setSealColName(sealStamp);

		AttendanceRecordReportDatasource recordReportDataSource = new AttendanceRecordReportDatasource(recordReportData,
				request.getMode());

		reportGenerator.generate(context.getGeneratorContext(), recordReportDataSource);

	}

	Integer getNumberFromString(String string) {

		String result = string.replaceAll("\\D+", "");
		if (!result.equals(""))
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

	String getNameFromInvidual(String invidual) {
		int index = invidual.indexOf(" ");
		return invidual.substring(index + 1);
	}

	String getSumCalculateAttendanceItem(List<ItemValue> addValueCalUpper, List<ItemValue> subValueCalUpper) {

		Object sum;
		switch (addValueCalUpper.get(0).getValueType().value) {
		case (0):
			sum = new Integer(0);
			// calculate add
			if (!addValueCalUpper.isEmpty()) {
				for (ItemValue i : addValueCalUpper) {
					if (i.getValue() != null)
						sum = Integer.parseInt(sum.toString()) + (int) i.value();
				}
			}
			// calculate sub
			if (!subValueCalUpper.isEmpty()) {
				for (ItemValue i : subValueCalUpper) {
					if (i.getValue() != null)
						sum = Integer.parseInt(sum.toString()) - (int) i.value();
				}
			}
			break;
		case (1):
			sum = new String("");
			Integer tmp = 0;
			String period;
			if (!addValueCalUpper.isEmpty()) {
				for (ItemValue i : addValueCalUpper) {

					if (i.value() != null) {
						tmp = tmp + (int) this.getNumberFromString(i.value().toString());
					}

				}
			}
			// calculate sub
			if (!subValueCalUpper.isEmpty()) {
				for (ItemValue i : subValueCalUpper) {
					if (i.value() != null) {
						tmp = Integer.parseInt(sum.toString()) - (int) this.getNumberFromString(i.value().toString());
					}
				}
			}
			period = this.getPeriodFromString(subValueCalUpper);

			sum = tmp + period;
			break;
		case (2):
			sum = new Double(0);
			// calculate add
			for (ItemValue i : addValueCalUpper) {
				if (i.getValue() != null)
					sum = Double.parseDouble(sum.toString()) + (Double) i.value();
			}
			// calculate sub
			for (ItemValue i : subValueCalUpper) {
				if (i.getValue() != null)
					sum = Double.parseDouble(sum.toString()) - (Double) i.value();
			}
			break;
		case (3):
			sum = new BigDecimal(0);
			BigDecimal sum1 = new BigDecimal(0);
			// calculate add
			for (ItemValue i : addValueCalUpper) {
				if (i.getValue() != null)
					sum1 = sum1.add(new BigDecimal(i.value().toString()));
			}
			// calculate sub
			for (ItemValue i : subValueCalUpper) {
				if (i.getValue() != null)
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
			if (item.getColumnDatas().get(6) != null) {
				upperValue7th = this.add(upperValue7th, item.getColumnDatas().get(6).getUper());
				lowerValue7th = this.add(upperValue7th, item.getColumnDatas().get(6).getLower());
			}
			if (item.getColumnDatas().get(7) != null) {
				upperValue8th = this.add(upperValue8th, item.getColumnDatas().get(7).getUper());
				lowerValue8th = this.add(upperValue8th, item.getColumnDatas().get(7).getLower());
			}
			if (item.getColumnDatas().get(8) != null) {
				upperValue9th = this.add(upperValue9th, item.getColumnDatas().get(8).getUper());
				lowerValue9th = this.add(upperValue9th, item.getColumnDatas().get(8).getLower());
			}

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
		result.setSecondCol(list.get(list.size() - 1).isSecondCol());

		return result;

	}

	public String add(String a, String b) {
		if (a.equals(""))
			return b;
		if (b.equals(""))
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

	private AttendanceRecordExport findIndexInList(int i, List<AttendanceRecordExport> list) {
		for (AttendanceRecordExport item : list) {
			if (item.getColumnIndex() == i)
				return item;
		}

		return null;
	}
	
	private String convertMinutesToHours(String minutes){
		if(minutes.equals("0")||minutes.equals("")){
			return "0:00";
		}
		String FORMAT = "%02d:%02d";
		Integer minuteInt = Integer.parseInt(minutes);		
		Integer hourInt = minuteInt/60;
		minuteInt = minuteInt%60;
		
		return String.format(FORMAT, hourInt,minuteInt);
	}
}
