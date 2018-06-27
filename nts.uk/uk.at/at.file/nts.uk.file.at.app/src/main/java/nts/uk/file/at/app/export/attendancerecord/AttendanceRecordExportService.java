package nts.uk.file.at.app.export.attendancerecord;

import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BundledBusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExport;
import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExportRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.NameUseAtr;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecord;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordRepositoty;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordRepository;
import nts.uk.ctx.at.function.dom.attendancetype.AttendanceType;
import nts.uk.ctx.at.function.dom.attendancetype.AttendanceTypeRepository;
import nts.uk.ctx.at.function.dom.attendancetype.ScreenUseAtr;
import nts.uk.ctx.at.record.app.service.attendanceitem.value.AttendanceItemValueService;
import nts.uk.ctx.at.record.app.service.attendanceitem.value.AttendanceItemValueService.AttendanceItemValueResult;
import nts.uk.ctx.at.record.app.service.attendanceitem.value.AttendanceItemValueService.MonthlyAttendanceItemValueResult;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WkpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.shared.app.service.workrule.closure.ClosureEmploymentService;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfo;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfoRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceHierarchy;
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
import nts.uk.shr.com.i18n.TextResource;

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

	@Inject
	private WorkplaceConfigInfoRepository wplConfigInfoRepo;

	@Inject
	private WorkTypeRepository workTypeRepo;

	@Inject
	private WorkTimeSettingRepository workTimeRepo;

	@Inject
	private AttendanceTypeRepository attendanceRepo;

	@Inject
	private WorkplaceAdapter workplaceAdapter;

	@Override
	protected void handle(ExportServiceContext<AttendanceRecordRequest> context) {

		// get Dto
		AttendanceRecordRequest request = context.getQuery();
		String companyId = AppContexts.user().companyId();
		Map<String, List<AttendanceRecordReportEmployeeData>> reportData = new LinkedHashMap<>();
		List<AttendanceRecordReportEmployeeData> attendanceRecRepEmpDataList = new ArrayList<AttendanceRecordReportEmployeeData>();
		BundledBusinessException exceptions = BundledBusinessException.newInstance();

		Optional<AttendanceRecordExportSetting> optionalAttendanceRecExpSet = attendanceRecExpSetRepo
				.getAttendanceRecExpSet(companyId, request.getLayout());

		List<Employee> unknownEmployeeList = new ArrayList<>();
		List<WorkType> workTypeList = workTypeRepo.findByCompanyId(companyId);

		List<WorkTimeSetting> workTimeList = workTimeRepo.findByCompanyId(companyId);

		List<String> wplIds = new ArrayList<>();
		for (Employee e : request.getEmployeeList()) {
			WkpHistImport hist = workplaceAdapter.findWkpBySid(e.getEmployeeId(),
					GeneralDate.ymd(request.getEndDate().year(), request.getEndDate().month(),
							request.getEndDate().yearMonth().lastDateInMonth()));
			if (hist == null) {
				unknownEmployeeList.add(e);
			} else {
				e.setWorkplaceId(hist.getWorkplaceId());
				e.setWorkplaceCode(hist.getWorkplaceCode());
				wplIds.add(hist.getWorkplaceId());
			}
		}

		List<WorkplaceConfigInfo> wplConfigInfoList = wplConfigInfoRepo
				.findByWkpIdsAtTime(AppContexts.user().companyId(), GeneralDate.localDate(LocalDate.now()), wplIds);

		List<WorkplaceHierarchy> hierarchyList = new ArrayList<>();

		wplIds.forEach(item -> {

			for (WorkplaceConfigInfo info : wplConfigInfoList) {
				for (WorkplaceHierarchy hiearachy : info.getLstWkpHierarchy()) {
					if (item.equals(hiearachy.getWorkplaceId()))
						hierarchyList.add(hiearachy);
				}
			}
		});

		hierarchyList.sort(Comparator.comparing(WorkplaceHierarchy::getHierarchyCode));

		wplIds = hierarchyList.stream().map(item -> item.getWorkplaceId()).distinct().collect(Collectors.toList());
		List<Employee> employeeListAfterSort = new ArrayList<>();
		wplIds.forEach(id -> {
			for (Employee employee : request.getEmployeeList()) {
				if (id.equals(employee.getWorkplaceId()))
					employeeListAfterSort.add(employee);
			}

		});

		if(!unknownEmployeeList.isEmpty()){
			employeeListAfterSort.addAll(unknownEmployeeList);
		}
		employeeListAfterSort.forEach(employee -> {

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

				if (closureDate.getClosureDay().v() != 0 || closureDate.getLastDayOfMonth()) {

					List<Integer> attendanceItemList = new ArrayList<>();

					// get upper-daily-singleItem list
					List<Integer> singleIdUpper = this.singleAttendanceRepo
							.getIdSingleAttendanceRecordByPosition(companyId, request.getLayout(), UPPER_POSITION);
					attendanceItemList.addAll(singleIdUpper);
					// get upper-daily-calculateItem list

					List<CalculateAttendanceRecord> calculateUpperDaily = this.calculateAttendanceRepo
							.getIdCalculateAttendanceRecordDailyByPosition(companyId, request.getLayout(),
									UPPER_POSITION);

					// get lower-daily-singleItem list
					List<Integer> singleIdLower = this.singleAttendanceRepo
							.getIdSingleAttendanceRecordByPosition(companyId, request.getLayout(), LOWER_POSITION);

					attendanceItemList.addAll(singleIdLower);
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

					List<ScreenUseAtr> screenUseAtrList = new ArrayList<ScreenUseAtr>();
					screenUseAtrList.add(ScreenUseAtr.valueOf(13));
					screenUseAtrList.add(ScreenUseAtr.valueOf(14));

					YearMonth startYearMonth = closureDate.getLastDayOfMonth() ? request.getStartDate().yearMonth()
							: request.getStartDate().yearMonth().previousMonth();
					YearMonth endYearMonth = closureDate.getLastDayOfMonth() ? request.getEndDate().yearMonth()
							: request.getEndDate().yearMonth().previousMonth();
					YearMonth yearMonth = startYearMonth;
					List<Integer> singleId = new ArrayList<>();
					List<Integer> monthlyId = new ArrayList<>();

					singleId.addAll(singleIdUpper);
					singleId.addAll(singleIdLower);
					calculateUpperDaily.forEach(item -> {
						if (item.getAddedItem() != null)
							singleId.addAll(item.getAddedItem());
						if (item.getSubtractedItem() != null)
							singleId.addAll(item.getSubtractedItem());
					});
					calculateLowerDaily.forEach(item -> {
						if (item.getAddedItem() != null)
							singleId.addAll(item.getAddedItem());
						if (item.getSubtractedItem() != null)
							singleId.addAll(item.getSubtractedItem());
					});

					calculateUpperMonthly.forEach(item -> {
						if (item.getAddedItem() != null)
							monthlyId.addAll(item.getAddedItem());
						if (item.getSubtractedItem() != null)
							monthlyId.addAll(item.getSubtractedItem());
					});
					calculateLowerMonthly.forEach(item -> {
						if (item.getAddedItem() != null)
							monthlyId.addAll(item.getAddedItem());
						if (item.getSubtractedItem() != null)
							monthlyId.addAll(item.getSubtractedItem());
					});
					while (yearMonth.lessThanOrEqualTo(endYearMonth)) {

						GeneralDate startDateByClosure;
						GeneralDate endDateByClosure;

						if (closureDate.getLastDayOfMonth()) {
							startDateByClosure = GeneralDate.ymd(yearMonth.year(), yearMonth.month(), 1);
							endDateByClosure = GeneralDate.ymd(yearMonth.year(), yearMonth.month(),
									yearMonth.lastDateInMonth());
						} else {
							startDateByClosure = GeneralDate.ymd(yearMonth.year(), yearMonth.month(),
									closureDate.getClosureDay().v() + 1);
							endDateByClosure = GeneralDate.ymd(yearMonth.addMonths(1).year(),
									yearMonth.addMonths(1).month(), closureDate.getClosureDay().v());
						}

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

							AttendanceItemValueResult itemValueResult = AttendanceItemValueResult.builder()
									.employeeId(null).workingDate(null).attendanceItems(new ArrayList<ItemValue>())
									.build();

							if (!singleIdUpper.isEmpty() || !singleIdLower.isEmpty()) {
								itemValueResult = attendanceService.getValueOf(employee.getEmployeeId(),
										startDateByClosure, singleId);
							}
							if (!singleIdUpper.isEmpty()) {
								valueSingleUpper = AttendanceItemValueResult.builder()
										.employeeId(employee.getEmployeeId()).workingDate(startDateByClosure)
										.attendanceItems(new ArrayList<ItemValue>()).build();
								for (Integer id : singleIdUpper) {
									ItemValue value = new ItemValue();
									for (ItemValue item : itemValueResult.getAttendanceItems()) {
										if (item.getItemId() == id) {
											value = item;
											break;
										}

									}
									valueSingleUpper.getAttendanceItems().add(value);
								}

							} else {
								valueSingleUpper = AttendanceItemValueResult.builder()
										.employeeId(employee.getEmployeeId()).workingDate(startDateByClosure)
										.attendanceItems(new ArrayList<ItemValue>()).build();
								for (int i = 1; i <= 6; i++) {
									valueSingleUpper.getAttendanceItems().add(new ItemValue());

								}
							}

							if (valueSingleUpper != null) {

								valueSingleUpper.getAttendanceItems().forEach(item -> {
									if (item != null)
										upperDailyRespond
												.add(new AttendanceRecordResponse(employee.getEmployeeId(),
														employee.getEmployeeName(), closureDateTemp, "",
														this.convertString(item, workTypeList, workTimeList,
																screenUseAtrList, optionalAttendanceRecExpSet.get()
																		.getNameUseAtr())));

								});
							}
							// return result upper-daily-calculateItems
							for (CalculateAttendanceRecord item : calculateUpperDaily) {
								// get add item
								AttendanceItemValueService.AttendanceItemValueResult addValueCalUpper = AttendanceItemValueResult
										.builder().employeeId(employee.getEmployeeId()).workingDate(startDateByClosure)
										.attendanceItems(new ArrayList<ItemValue>()).build();
								if (item.getAddedItem() != null && !item.getAddedItem().isEmpty())
									for (Integer id : item.getAddedItem()) {
										ItemValue value = new ItemValue();
										for (ItemValue e : itemValueResult.getAttendanceItems()) {
											if (e.getItemId() == id) {
												value = e;
												break;
											}

										}
										addValueCalUpper.getAttendanceItems().add(value);
									}

								// get sub item
								AttendanceItemValueService.AttendanceItemValueResult subValueCalUpper = AttendanceItemValueResult
										.builder().employeeId(employee.getEmployeeId()).workingDate(startDateByClosure)
										.attendanceItems(new ArrayList<ItemValue>()).build();
								if (item.getSubtractedItem() != null && !item.getSubtractedItem().isEmpty())
									for (Integer id : item.getSubtractedItem()) {
										ItemValue value = new ItemValue();
										for (ItemValue e : itemValueResult.getAttendanceItems()) {
											if (e.getItemId() == id) {
												value = e;
												break;
											}

										}
										subValueCalUpper.getAttendanceItems().add(value);
									}

								// get result upper calculate
								String result = "";
								if (!addValueCalUpper.getAttendanceItems().isEmpty()
										|| !subValueCalUpper.getAttendanceItems().isEmpty()) {
									result = this.getSumCalculateAttendanceItem(addValueCalUpper.getAttendanceItems(),
											subValueCalUpper.getAttendanceItems());
								}
								upperDailyRespond.add(new AttendanceRecordResponse(employee.getEmployeeId(),
										employee.getEmployeeName(), closureDateTemp, "", result));

							}

							// return result lower-daily-singleItems
							AttendanceItemValueService.AttendanceItemValueResult valueSingleLower = AttendanceItemValueResult
									.builder().employeeId(employee.getEmployeeId()).workingDate(startDateByClosure)
									.attendanceItems(new ArrayList<ItemValue>()).build();
							if (!singleIdLower.isEmpty()) {
								//
								valueSingleLower = AttendanceItemValueResult.builder()
										.employeeId(employee.getEmployeeId()).workingDate(startDateByClosure)
										.attendanceItems(new ArrayList<ItemValue>()).build();
								for (Integer id : singleIdLower) {
									ItemValue value = new ItemValue();
									for (ItemValue item : itemValueResult.getAttendanceItems()) {
										if (item.getItemId() == id) {
											value = item;
											break;
										}

									}
									valueSingleLower.getAttendanceItems().add(value);
								}

							} else {
								valueSingleLower = AttendanceItemValueResult.builder()
										.employeeId(employee.getEmployeeId()).workingDate(startDateByClosure)
										.attendanceItems(new ArrayList<ItemValue>()).build();
								for (int i = 1; i <= 6; i++) {
									valueSingleLower.getAttendanceItems().add(new ItemValue());

								}
							}

							if (valueSingleLower != null)
								valueSingleLower.getAttendanceItems().forEach(item -> {
									if (item != null)
										lowerDailyRespond
												.add(new AttendanceRecordResponse(employee.getEmployeeId(),
														employee.getEmployeeName(), closureDateTemp, "",
														this.convertString(item, workTypeList, workTimeList,
																screenUseAtrList, optionalAttendanceRecExpSet.get()
																		.getNameUseAtr())));

								});

							for (CalculateAttendanceRecord item : calculateLowerDaily) {
								// get add item
								AttendanceItemValueService.AttendanceItemValueResult addValueCalUpper = AttendanceItemValueResult
										.builder().attendanceItems(new ArrayList<>()).build();
								if (item.getAddedItem() != null && !item.getAddedItem().isEmpty())
									for (Integer id : item.getAddedItem()) {
										ItemValue value = new ItemValue();
										for (ItemValue e : itemValueResult.getAttendanceItems()) {
											if (e.getItemId() == id) {
												value = e;
												break;
											}

										}
										addValueCalUpper.getAttendanceItems().add(value);
									}

								// get sub item
								AttendanceItemValueService.AttendanceItemValueResult subValueCalUpper = AttendanceItemValueResult
										.builder().attendanceItems(new ArrayList<>()).build();
								if (item.getSubtractedItem() != null && !item.getSubtractedItem().isEmpty())
									for (Integer id : item.getSubtractedItem()) {
										ItemValue value = new ItemValue();
										for (ItemValue e : itemValueResult.getAttendanceItems()) {
											if (e.getItemId() == id) {
												value = e;
												break;
											}

										}
										subValueCalUpper.getAttendanceItems().add(value);
									}

								// get result lower calculate
								String result = "";
								if (!addValueCalUpper.getAttendanceItems().isEmpty()
										|| !subValueCalUpper.getAttendanceItems().isEmpty()) {
									result = this.getSumCalculateAttendanceItem(addValueCalUpper.getAttendanceItems(),
											subValueCalUpper.getAttendanceItems());
								}
								lowerDailyRespond.add(new AttendanceRecordResponse(employee.getEmployeeId(),
										employee.getEmployeeName(), closureDateTemp, "", result));
							}

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
							dailyData.setSecondCol(flag <= 15 ? false : true);
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
						AttendanceItemValueService.MonthlyAttendanceItemValueResult itemValueResult = MonthlyAttendanceItemValueResult
								.builder().attendanceItems(new ArrayList<>()).build();

						if (!calculateUpperMonthly.isEmpty() || !calculateLowerMonthly.isEmpty()) {
							itemValueResult = attendanceService.getMonthlyValueOf(employee.getEmployeeId(),
									closureDate.getLastDayOfMonth() ? yearMonth : yearMonth.addMonths(1),
									closure.getClosureId().value, closureDate.getClosureDay().v(),
									closureDate.getLastDayOfMonth(), monthlyId);
						}

						for (CalculateAttendanceRecord item : calculateUpperMonthly) {
							AttendanceItemValueService.MonthlyAttendanceItemValueResult monthlyUpperAddResult = MonthlyAttendanceItemValueResult
									.builder().attendanceItems(new ArrayList<>()).build();
							AttendanceItemValueService.MonthlyAttendanceItemValueResult monthlyUpperSubResult = MonthlyAttendanceItemValueResult
									.builder().attendanceItems(new ArrayList<>()).build();
							if (item.getAddedItem() != null && !item.getAddedItem().isEmpty()) {
								for (Integer id : item.getAddedItem()) {
									ItemValue value = new ItemValue();
									for (ItemValue e : itemValueResult.getAttendanceItems()) {
										if (id == e.getItemId()) {
											value = e;
											break;
										}

									}
									monthlyUpperAddResult.getAttendanceItems().add(value);
								}
							}

							if (item.getSubtractedItem() != null && !item.getSubtractedItem().isEmpty()) {
								for (Integer id : item.getSubtractedItem()) {
									ItemValue value = new ItemValue();
									for (ItemValue e : itemValueResult.getAttendanceItems()) {
										if (id == e.getItemId()) {
											value = e;
											break;
										}

									}
									monthlyUpperSubResult.getAttendanceItems().add(value);
								}
							}

							String result = "";
							if (!monthlyUpperAddResult.getAttendanceItems().isEmpty()
									|| !monthlyUpperSubResult.getAttendanceItems().isEmpty()) {
								result = this.getSumCalculateAttendanceItem(monthlyUpperAddResult.getAttendanceItems(),
										monthlyUpperSubResult.getAttendanceItems());
							}
							upperResult.add(result);

						}
						// return result lower-monthly-Items
						for (CalculateAttendanceRecord item : calculateLowerMonthly) {
							AttendanceItemValueService.MonthlyAttendanceItemValueResult monthlyLowerAddResult = MonthlyAttendanceItemValueResult
									.builder().attendanceItems(new ArrayList<>()).build();

							AttendanceItemValueService.MonthlyAttendanceItemValueResult monthlyLowerSubResult = MonthlyAttendanceItemValueResult
									.builder().attendanceItems(new ArrayList<>()).build();

							if (item.getAddedItem() != null && !item.getAddedItem().isEmpty()) {
								for (Integer id : item.getAddedItem()) {
									ItemValue value = new ItemValue();
									for (ItemValue e : itemValueResult.getAttendanceItems()) {
										if (id == e.getItemId()) {
											value = e;
											break;
										}

									}
									monthlyLowerAddResult.getAttendanceItems().add(value);
								}
							}

							if (item.getSubtractedItem() != null && !item.getSubtractedItem().isEmpty()) {
								for (Integer id : item.getSubtractedItem()) {
									ItemValue value = new ItemValue();
									for (ItemValue e : itemValueResult.getAttendanceItems()) {
										if (id == e.getItemId()) {
											value = e;
											break;
										}

									}
									monthlyLowerSubResult.getAttendanceItems().add(value);
								}
							}

							String result = new String("");
							if (!monthlyLowerAddResult.getAttendanceItems().isEmpty()
									|| !monthlyLowerSubResult.getAttendanceItems().isEmpty()) {
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
						YearMonth yearMonthExport = closureDate.getLastDayOfMonth() ? yearMonth
								: yearMonth.addMonths(1);
						attendanceRecRepEmpData.setReportYearMonth(yearMonthExport.toString());

						/**
						 * Need information
						 * 
						 * The invidual. The workplace. The employment The
						 * title. The work type The year month
						 **/

						List<String> employeeIds = new ArrayList<>();
						GeneralDate referenceDate = GeneralDate.ymd(request.getEndDate().year(),
								request.getEndDate().month(), request.getEndDate().yearMonth().lastDateInMonth());
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
						attendanceRecRepEmpData.setTitle(
								result.getPosition() == null ? "" : result.getPosition().getPositionName().toString());
						attendanceRecRepEmpData.setWorkplace(result.getWorkplace() == null ? ""
								: result.getWorkplace().getWorkplaceName().toString());
						attendanceRecRepEmpData.setWorkType(result.getEmploymentCls() == null ? ""
								: TextResource.localize(EnumAdaptor.valueOf(result.getEmploymentCls(),
										WorkTimeMethodSet.class).nameId));
						attendanceRecRepEmpData.setYearMonth(yearMonthExport.year() + "/" + yearMonthExport.month());
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

		for (Employee employee : employeeListAfterSort) {
			List<AttendanceRecordReportEmployeeData> attendanceRecRepEmpDataByMonthList = new ArrayList<>();
			for (AttendanceRecordReportEmployeeData item : attendanceRecRepEmpDataList) {

				if (this.getCodeFromInvidual(item.getInvidual()).equals(employee.getEmployeeCode().trim())) {
					attendanceRecRepEmpDataByMonthList.add(item);
				}

			}
			reportData.put(employee.getEmployeeCode().trim(), attendanceRecRepEmpDataByMonthList);

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

			String upperheader = "";
			String lowerheader = "";
			if (item.getUpperPosition() != null && item.getUpperPosition().isPresent())
				upperheader = item.getUpperPosition().get().getNameDisplay();
			if (item.getLowerPosition() != null && item.getLowerPosition().isPresent())
				lowerheader = item.getLowerPosition().get().getNameDisplay();
			AttendanceRecordReportColumnData temp = (new AttendanceRecordReportColumnData(upperheader, lowerheader));
			dailyHeader.add(temp);

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

		recordReportData.setCompanyName(optionalCompany.get().getCompanyName().toString());
		recordReportData.setDailyHeader(dailyHeader);
		recordReportData.setExportDateTime(exportDate);
		recordReportData.setMonthlyHeader(monthlyHeader);
		recordReportData.setReportData(reportData);
		recordReportData.setReportName(optionalAttendanceRecExpSet.get().getName().v());
		recordReportData.setSealColName(
				optionalAttendanceRecExpSet.get().getSealUseAtr() ? sealStamp : new ArrayList<String>());

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
		return invidual.substring(index + 1).trim();
	}

	String getCodeFromInvidual(String invidual) {
		int index = invidual.indexOf(" ");
		return invidual.substring(0, index + 1).trim();
	}

	String getSumCalculateAttendanceItem(List<ItemValue> addValueCalUpper, List<ItemValue> subValueCalUpper) {

		Double sum = new Double(0);
		if (!addValueCalUpper.isEmpty()
				&& (addValueCalUpper.get(0).getValueType().isInteger()
						|| addValueCalUpper.get(0).getValueType().isDouble())
				|| !subValueCalUpper.isEmpty() && (subValueCalUpper.get(0).getValueType().isInteger()
						|| subValueCalUpper.get(0).getValueType().isDouble())) {

			// calculate add
			if (!addValueCalUpper.isEmpty()) {
				for (ItemValue i : addValueCalUpper) {
					if (i.getValue() != null)
						sum = Double.parseDouble(sum.toString()) + Double.parseDouble(i.value().toString());
				}
			}
			// calculate sub
			if (!subValueCalUpper.isEmpty()) {
				for (ItemValue i : subValueCalUpper) {
					if (i.getValue() != null)
						sum = Double.parseDouble(sum.toString()) - Double.parseDouble(i.value().toString());
				}
			}
			if (sum.equals(0.0))
				return "";

			Integer sumInt;
			List<ItemValue> list = new ArrayList<>();
			if (!addValueCalUpper.isEmpty()) {
				list.addAll(addValueCalUpper);
			} else {
				list.addAll(subValueCalUpper);
			}
			switch (list.get(0).getValueType().value) {

			case 1:
			case 2:

				sumInt = sum.intValue();
				return this.convertMinutesToHours(sumInt.toString());
			case 7:
			case 8:
				sumInt = sum.intValue();
				return sumInt.toString() + " 回";
			case 13:
				sumInt = sum.intValue();
				DecimalFormat format = new DecimalFormat("###,###,###");
				return format.format(sum.intValue());
			default:
				break;

			}

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
				lowerValue7th = this.add(lowerValue7th, item.getColumnDatas().get(6).getLower());
			}
			if (item.getColumnDatas().get(7) != null) {
				upperValue8th = this.add(upperValue8th, item.getColumnDatas().get(7).getUper());
				lowerValue8th = this.add(lowerValue8th, item.getColumnDatas().get(7).getLower());
			}
			if (item.getColumnDatas().get(8) != null) {
				upperValue9th = this.add(upperValue9th, item.getColumnDatas().get(8).getUper());
				lowerValue9th = this.add(lowerValue9th, item.getColumnDatas().get(8).getLower());
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
		int indexA = a.indexOf(":");
		int indexB = b.indexOf(":");

		int subtrA = a.indexOf("-");
		int subtrB = b.indexOf("-");

		if (indexA >= 0 && indexB >= 0) {
			Integer hourA;
			Integer hourB;
			Integer minuteA;
			Integer minuteB;
			hourA = Integer.parseInt(a.substring(0, indexA));
			if (subtrA == 0) {
				minuteA = Integer.parseInt(a.substring(indexA + 1)) * (-1);
			} else {
				minuteA = Integer.parseInt(a.substring(indexA + 1));
			}
			hourB = Integer.parseInt(b.substring(0, indexB));
			if (subtrB == 0) {
				minuteB = Integer.parseInt(b.substring(indexB + 1)) * (-1);
			} else {

				minuteB = Integer.parseInt(b.substring(indexB + 1));
			}

			Integer totalMinute = (hourA * 60 + minuteA) + (hourB * 60 + minuteB);

			return this.convertMinutesToHours(totalMinute.toString());
		} else {
			indexA = a.indexOf("回");
			indexB = b.indexOf("回");

			if (indexA >= 0 && indexB >= 0) {
				Integer countA = Integer.parseInt(a.substring(0, indexA - 1));
				Integer countB = Integer.parseInt(b.substring(0, indexB - 1));

				Integer totalCount = countA + countB;

				return totalCount + " 回";
			} else {
				String stringAmountA = a.replaceAll(",", "");
				String stringAmountB = b.replaceAll(",", "");

				Integer amountA = Integer.parseInt(stringAmountA);
				Integer amountB = Integer.parseInt(stringAmountB);

				Integer totalAmount = amountA + amountB;
				DecimalFormat format = new DecimalFormat("###,###,###");
				return format.format(totalAmount);

			}

		}

	}

	private String convertString(ItemValue item, List<WorkType> workTypeList, List<WorkTimeSetting> workTimeSettingList,
			List<ScreenUseAtr> screenUseAtrList, NameUseAtr nameUseAtr) {
		final String value = item.getValue();
		if (item.getValueType() == null || item.getValue() == null)
			return "";
		switch (item.getValueType().value) {

		case 1:
		case 2:

			if (Integer.parseInt(item.getValue()) == 0 || item.getValue().equals(""))
				return "";
			return this.convertMinutesToHours(value.toString());
		case 7:
		case 8:
			if (Integer.parseInt(item.getValue()) == 0 || item.getValue().equals(""))
				return "";
			return value.toString() + " 回";
		case 13:
			if (Integer.parseInt(item.getValue()) == 0 || item.getValue().equals(""))
				return "";
			DecimalFormat format = new DecimalFormat("###,###,###");
			return format.format(Integer.parseInt(value));
		default:
			List<AttendanceType> attendanceTypeList = new ArrayList<>();
			screenUseAtrList.forEach(screenUseAtr -> {
				attendanceTypeList.addAll(
						attendanceRepo.getItemByAtrandType(AppContexts.user().companyId(), screenUseAtr.value, 1));
			});

			if (!attendanceTypeList.isEmpty()) {
				AttendanceType attendance = attendanceTypeList.stream()
						.filter(e -> e.getAttendanceItemId() == item.getItemId()).collect(Collectors.toList()).get(0);
				if (attendance.getScreenUseAtr().equals(ScreenUseAtr.ATTENDANCE_TYPE_OF_DERVICETYPE)) {
					List<WorkType> worktype = workTypeList.stream()
							.filter(ite -> ite.getWorkTypeCode().v().equals(value)).collect(Collectors.toList());
					if (!worktype.isEmpty())

						return nameUseAtr.equals(NameUseAtr.FORMAL_NAME) ? worktype.get(0).getName().v()
								: worktype.get(0).getAbbreviationName().v();
					return value;
				} else {

					List<WorkTimeSetting> workTime = workTimeSettingList.stream()
							.filter(e -> e.getWorktimeCode().v().equals(value)).collect(Collectors.toList());
					if (!workTime.isEmpty())
						return nameUseAtr.equals(NameUseAtr.FORMAL_NAME)
								? workTime.get(0).getWorkTimeDisplayName().getWorkTimeName().v()
								: workTime.get(0).getWorkTimeDisplayName().getWorkTimeAbName().v();
					return value;
				}
			}
			return value;

		}
	}

	private AttendanceRecordExport findIndexInList(int i, List<AttendanceRecordExport> list) {
		for (AttendanceRecordExport item : list) {
			if (item.getColumnIndex() == i)
				return item;
		}

		return null;
	}

	private Boolean findInListEmployee(Employee e, List<Employee> list) {
		for (Employee item : list) {
			if (item.getEmployeeId().equals(e.employeeCode))
				return true;
		}
		return false;
	}

	private String convertMinutesToHours(String minutes) {
		if (minutes.equals("0") || minutes.equals("")) {
			return "0:00";
		}
		String FORMAT = "%02d:%02d";
		Integer minuteInt = Integer.parseInt(minutes);
		if (minuteInt < 0) {
			minuteInt *= -1;
			Integer hourInt = minuteInt / 60;
			minuteInt = minuteInt % 60;
			return "-" + String.format(FORMAT, hourInt, minuteInt);
		} else {
			Integer hourInt = minuteInt / 60;
			minuteInt = minuteInt % 60;

			return String.format(FORMAT, hourInt, minuteInt);
		}
	}
}
