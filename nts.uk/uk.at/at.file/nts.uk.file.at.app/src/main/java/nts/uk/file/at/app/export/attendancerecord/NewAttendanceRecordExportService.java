package nts.uk.file.at.app.export.attendancerecord;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.task.data.TaskDataSetter;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExportRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportFontSize;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ItemSelectionType;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.NameUseAtr;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecord;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordRepositoty;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecord;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordRepository;
import nts.uk.ctx.at.function.dom.attendancetype.AttendanceType;
import nts.uk.ctx.at.function.dom.attendancetype.AttendanceTypeRepository;
import nts.uk.ctx.at.function.dom.attendancetype.ScreenUseAtr;
import nts.uk.ctx.at.function.dom.dailyworkschedule.scrA.SEmpHistExportAdapter;
import nts.uk.ctx.at.function.dom.dailyworkschedule.scrA.SEmpHistExportImported;
import nts.uk.ctx.at.record.app.service.attendanceitem.value.AttendanceItemValueService;
import nts.uk.ctx.at.record.app.service.attendanceitem.value.AttendanceItemValueService.AttendanceItemValueResult;
import nts.uk.ctx.at.record.app.service.attendanceitem.value.AttendanceItemValueService.MonthlyAttendanceItemValueResult;
import nts.uk.ctx.at.record.dom.workrecord.manageactualsituation.approval.monthly.MonthlyApprovalProcess;
import nts.uk.ctx.at.record.dom.workrecord.managectualsituation.ApprovalStatus;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcess;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcessRepository;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.shared.app.service.workrule.closure.ClosureEmploymentService;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceInformationRepository;
import nts.uk.ctx.bs.employee.pub.company.StatusOfEmployee;
import nts.uk.ctx.bs.employee.pub.company.SyCompanyPub;
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportData;
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportEmployeeData;
import nts.uk.file.at.app.export.schedule.FileService;
import nts.uk.query.pub.employee.EmployeeInformationPub;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class NewAttendanceRecordExportService extends ExportService<AttendanceRecordRequest> {

	final static long UPPER_POSITION = 1;

	final static long LOWER_POSITION = 2;

	final static int PDF_MODE = 1;

	final static String ZERO = "0";
	
	final static long SINGLE =  1;
	
	final static long CALCULATE = 2;

	/** The Constant MASTER_UNREGISTERED. */
	private static final String MASTER_UNREGISTERED = " マスタ未登録";

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
	private WorkplaceInformationRepository wplConfigInfoRepo;

	@Inject
	private WorkTypeRepository workTypeRepo;

	@Inject
	private WorkTimeSettingRepository workTimeRepo;

	@Inject
	private AttendanceTypeRepository attendanceRepo;

	@Inject
	private WorkplaceAdapter workplaceAdapter;

	@Inject
	private ManagedParallelWithContext parallel;

	@Inject
	private FileService service;

	@Inject
	private SEmpHistExportAdapter sEmpHistExportAdapter;

	@Inject
	private MonthlyApprovalProcess monthlyApprovalProcess;

	@Inject
	private ApprovalProcessRepository approvalRepo;

	@Inject
	private SyCompanyPub symCompany;

	@Override
	protected void handle(ExportServiceContext<AttendanceRecordRequest> context) {
		// get Dto
		AttendanceRecordRequest request = context.getQuery();
		AttendanceRecordOutputConditions condition = AttendanceRecordOutputConditions
				.createFromJavaType(request.getCondition());
		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		String layoutId = condition.getSelectionType() == ItemSelectionType.FREE_SETTING
				? condition.getFreeSettingLayoutId()
				: condition.getStandardSelectionLayoutId();
		Map<String, List<AttendanceRecordReportEmployeeData>> reportData = new LinkedHashMap<>();
		
		Set<Integer> singleId = new HashSet<>();
		Set<Integer> monthlyId = new HashSet<>();
		// Get workType info
		List<WorkType> workTypeList = workTypeRepo.findByCompanyId(companyId);

		// Get workTime info
		List<WorkTimeSetting> workTimeList = workTimeRepo.findByCompanyId(companyId);
				
		List<Integer> singleIdUpper = this.singleAttendanceRepo.getIdSingleAttendanceRecordByPosition(layoutId,
				UPPER_POSITION);
		// get lower-daily-singleItem list
		List<Integer> singleIdLower = this.singleAttendanceRepo.getIdSingleAttendanceRecordByPosition(layoutId,
				LOWER_POSITION);

		List<ScreenUseAtr> screenUseAtrList = Arrays.asList(ScreenUseAtr.ATTENDANCE_TYPE_OF_DERVICETYPE,
				ScreenUseAtr.EMPLOYEE_BOOKING_HOURS);
		
		List<AttendanceType> attendanceTypeList = new ArrayList<>();
		
		List<String> empIDs = request.getEmployeeList().stream().map(e -> e.getEmployeeId())
				.collect(Collectors.toList());
		List<Employee> employeeListSortByCD = request.getEmployeeList().stream()
				.sorted(Comparator.comparing(Employee::getEmployeeCode)).distinct().collect(Collectors.toList());
		

		//	get domain model by item selection
		Optional<AttendanceRecordExportSetting> optionalAttendanceRecExpSet = this.attendanceRecExpSetRepo.findByCode(
				condition.getSelectionType(), companyId, Optional.of(employeeId), String.valueOf(request.getLayout()));

		//	取得できなかった
		if (!optionalAttendanceRecExpSet.isPresent()) {
			//	エラーメッセージ(#Msg_1141)を表示する
			throw new BusinessException("Msg_1141");
		}

		// 	対応する「月別実績」をすべて取得する - Param ( List <employee ID> , Period (year / month).
		// Start date ≤ year / month (YM) ≤ parameter. Period (year / month). End date ,

		// get upper-monthly-Item list
		List<CalculateAttendanceRecord> calculateUpperMonthly = this.calculateAttendanceRepo
				.getIdCalculateAttendanceRecordMonthlyByPosition(layoutId, UPPER_POSITION);
		List<CalculateAttendanceRecord> calculateLowerMonthly = this.calculateAttendanceRepo
				.getIdCalculateAttendanceRecordMonthlyByPosition(layoutId, LOWER_POSITION);
		

		// get lower-daily-CalculateItem list
		List<CalculateAttendanceRecord> calculateLowerDaily = this.calculateAttendanceRepo
				.getIdCalculateAttendanceRecordDailyByPosition(layoutId, LOWER_POSITION);
		// get upper-daily-calculateItem list
		List<CalculateAttendanceRecord> calculateUpperDaily = this.calculateAttendanceRepo
				.getIdCalculateAttendanceRecordDailyByPosition(layoutId, UPPER_POSITION);

		// Get all Monthly Attendance item
		Stream.concat(calculateUpperMonthly.stream(), calculateLowerMonthly.stream()).forEach(item -> {
			if (item.getAddedItem() != null)
				monthlyId.addAll(item.getAddedItem());
			if (item.getSubtractedItem() != null)
				singleId.addAll(item.getSubtractedItem());
		});

		Stream.concat(calculateUpperDaily.stream(), calculateLowerDaily.stream()).forEach(item -> {
			if (item.getAddedItem() != null)
				singleId.addAll(item.getAddedItem());
			if (item.getSubtractedItem() != null)
				singleId.addAll(item.getSubtractedItem());
		});

		YearMonthPeriod periodMonthly = new YearMonthPeriod(request.getStartDate().yearMonth(),
				request.getEndDate().yearMonth());
		int closureId = request.getClosureId() == 0 ? 1 : request.getClosureId();
		Optional<GeneralDate> baseDate = service.getProcessingYM(companyId, closureId);
		
		Map<String, DatePeriod> employeePeriod = service.getAffiliationDatePeriod(empIDs, periodMonthly, baseDate.get());

		//	「日別実績」を取得する
		List<AttendanceItemValueResult> dailyValues;
		List<MonthlyAttendanceItemValueResult> monthlyValues;
		{
			List<AttendanceItemValueResult> syncResultsDaily = Collections.synchronizedList(new ArrayList<>());
            List<MonthlyAttendanceItemValueResult> syncResultsMonthly = Collections.synchronizedList(new ArrayList<>());
			this.parallel.forEach(employeePeriod.entrySet(), emp -> {
				if (!monthlyId.isEmpty()) {
                    syncResultsMonthly.addAll(attendanceService.getMonthlyValueOf(Arrays.asList(emp.getKey()), periodMonthly, monthlyId));
                }
				if (!singleId.isEmpty()) {				
    				syncResultsDaily.addAll(attendanceService.getValueOf(Arrays.asList(emp.getKey()), emp.getValue(), singleId));
    			}
			});
			dailyValues = new ArrayList<>(syncResultsDaily);
            monthlyValues =  new ArrayList<>(syncResultsMonthly);
		}
		
        Map<String, List<MonthlyAttendanceItemValueResult>> monthlyValuesAll = monthlyValues.stream()
                .collect(Collectors.groupingBy(MonthlyAttendanceItemValueResult::getEmployeeId));

		Map<String, List<AttendanceItemValueResult>> dailyValuesAll = dailyValues.stream()
                .collect(Collectors.groupingBy(AttendanceItemValueResult::getEmployeeId));

		// Get all Daily attendance item
		singleId.addAll(singleIdUpper);
		singleId.addAll(singleIdLower);

		// 月の承認済を取得する
		// TODO check lại giá trị trả về của giải thuật [月の承認済を取得する] là một lst hoặc obj
		MonthlyApprovalStatusAttendanceRecord monthlyApproval = new MonthlyApprovalStatusAttendanceRecord();
		Optional<ApprovalProcess> approvalProcOp = approvalRepo.getApprovalProcessById(companyId);
		monthlyValues.stream().forEach(i -> {
			ApprovalStatus approvalMonth = monthlyApprovalProcess.monthlyApprovalCheck(companyId, i.getEmployeeId(),
					i.getYearMonth().v(), i.getClosureId(), baseDate.get(), approvalProcOp, null);
			if (approvalMonth.equals(ApprovalStatus.APPROVAL)) {
				monthlyApproval.setYm(i.getYearMonth());
				monthlyApproval.setClosureId(i.getClosureId());
				monthlyApproval.setApprovedFlag(true);
				monthlyApproval.setEmployeeId(i.getEmployeeId());
				monthlyApproval.setYmd(i.getYearMonth());
			}
		});

		//	社員IDでループ - Loop by employee ID
		for (Employee emp : employeeListSortByCD) {
			DailyOutputAttendanceRecord attendanceRecord = new DailyOutputAttendanceRecord();
			
			attendanceRecord.setEmployeeId(emp.getEmployeeId());

//			//	取得した月別実績の着目社員分をループ - Loop the acquired monthly results for the employees of interest
//			ClosureDate closureDate = new ClosureDate(1, false);
//			
//			// Get start time - end time
//			DatePeriod datePeriod = employeePeriod.get(emp.employeeId);
//			YearMonth endYearMonth = datePeriod.end().yearMonth();
//			YearMonth yearMonth = datePeriod.start().yearMonth();
//			
//			if (attendanceTypeList.isEmpty()) {
//				attendanceTypeList.addAll(attendanceRepo.getItemByAtrandType(AppContexts.user().companyId(),
//						screenUseAtrList, 1));
//			}
			for (MonthlyAttendanceItemValueResult monthlyValue : monthlyValues) {
				
				// start date
				GeneralDate startDate = GeneralDate.ymd(monthlyValue.getYearMonth().year(), monthlyValue.getYearMonth().month(), 1);
				GeneralDate endDate = monthlyValue.getYearMonth().lastGeneralDate();
				// Get start and end date of month
				DatePeriod monthPeriod = new DatePeriod(startDate, endDate);

				//	月別実績の月初と月末の雇用コードをチェックする
				// TODO first last employee
				MonthlyResultCheck monthResultCheck = this.checkEmployeeCodeInMonth(employeeId, monthPeriod, "", "");
				//	雇用取得結果：true
				if (!monthResultCheck.isEmployeeResult()) {
					// TODO lst err
					//	エラーリストに「社員コード」「社員名」を書き出す
				}
				if (!monthResultCheck.isCheckResult()) {
					// 雇用コードが一致しなかったのでこの月別実績は処理しない
					// →次の月別実績データの処理に移行(continue)
					continue;

				}
				//	月別実績１ヶ月分の期間(YMD)と所属会社履歴の重複期間(YMD)を取得する
				//	社員の指定期間中の所属期間を取得する RequestList 588
				List<StatusOfEmployee> statusEmps = this.symCompany.GetListAffComHistByListSidAndPeriod(empIDs, monthPeriod);

				// (UK2)出勤簿を出力する
				if (statusEmps.isEmpty()) {
					continue;
				}
					
				//	重複期間．開始年月日～重複期間．終了年月日のループ
				while (startDate.beforeOrEquals(endDate)) {
					attendanceRecord.setDate(startDate);

					Integer realData = 0;

					GeneralDate startDateByClosure;
					GeneralDate endDateByClosure;

					// Get start - end date to export
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
					
					//	日別項目（単一項目・上段） - Daily items (single item, upper row)
					//	勤怠項目の実績値を取得し編集する - Acquire and edit the actual value of attendance items
					AttendanceItemValueResult itemValueResult = AttendanceItemValueResult.builder()
							.employeeId(null).workingDate(null).attendanceItems(new ArrayList<ItemValue>())
							.build();
					AttendanceItemValueService.AttendanceItemValueResult valueSingleUpper = null;
					if (!singleIdUpper.isEmpty() || !singleIdLower.isEmpty()) {
						if (dailyValuesAll.containsKey(emp.getEmployeeId())) {
							List<AttendanceItemValueResult> dailyValuesByEmp = dailyValuesAll.get(emp.getEmployeeId());
							Optional<AttendanceItemValueResult> itemValueOtp = dailyValuesByEmp.stream()
									.filter(x -> x.getWorkingDate().equals(startDateByClosure)).findFirst();
							if (itemValueOtp.isPresent()) {
								itemValueResult = itemValueOtp.get();
							}
						}
					}
					
					// Fill in upper single item
					if (!singleIdUpper.isEmpty()) {
						valueSingleUpper = AttendanceItemValueResult.builder().employeeId(emp.getEmployeeId())
								.workingDate(startDateByClosure).attendanceItems(new ArrayList<ItemValue>()).build();
						for (Integer id : singleIdUpper) {
							ItemValue value = new ItemValue();
							for (ItemValue item : itemValueResult.getAttendanceItems()) {
								if (item.getItemId() == id) {
									if (item.getValue() != null && !ZERO.equals(item.getValue())
											&& !item.getValue().isEmpty()) {
										realData++;
									}
									value = item;
									break;
								}

							}
							valueSingleUpper.getAttendanceItems().add(value);
						}

					} else {
						valueSingleUpper = AttendanceItemValueResult.builder().employeeId(emp.getEmployeeId())
								.workingDate(startDateByClosure).attendanceItems(new ArrayList<ItemValue>()).build();
						for (int i = 1; i <= 6; i++) {
							valueSingleUpper.getAttendanceItems().add(new ItemValue());

						}
					}
					
					// convert data to show
					if (valueSingleUpper != null) {

						valueSingleUpper.getAttendanceItems().forEach(item -> {
							if (item != null)
								upperDailyRespond.add(new AttendanceRecordResponse(emp.getEmployeeId(),
										emp.getEmployeeName(), startDateByClosure, "",
										this.convertString(item, workTypeList, workTimeList, attendanceTypeList,
												optionalAttendanceRecExpSet.get().getNameUseAtr())));

						});
					}
					
					//	日別項目（算出項目・上段） - Daily items (calculation items, upper row)
					//	勤怠項目の実績値を取得し集計、編集する - Acquire, aggregate, and edit the actual value of attendance items
					// return result upper-daily-calculateItems
					for (CalculateAttendanceRecord item : calculateUpperDaily) {

						AttendanceItemValueService.AttendanceItemValueResult addValueCalUpper = AttendanceItemValueResult
								.builder().employeeId(emp.getEmployeeId()).workingDate(startDateByClosure)
								.attendanceItems(new ArrayList<ItemValue>()).build();
						// Fill in upper calculate daily ADD item
						if (item.getAddedItem() != null && !item.getAddedItem().isEmpty())
							for (Integer id : item.getAddedItem()) {
								for (ItemValue e : itemValueResult.getAttendanceItems()) {
									if (e.getItemId() == id) {
										if (e.getValue() != null && !ZERO.equals(e.getValue())
												&& !e.getValue().isEmpty()) {
											realData++;
										}
										addValueCalUpper.getAttendanceItems().add(e);
										break;
									}
								}
							}

						AttendanceItemValueService.AttendanceItemValueResult subValueCalUpper = AttendanceItemValueResult
								.builder().employeeId(emp.getEmployeeId()).workingDate(startDateByClosure)
								.attendanceItems(new ArrayList<ItemValue>()).build();
						// Fill in upper calculate daily SUBTRACT item
						if (item.getSubtractedItem() != null && !item.getSubtractedItem().isEmpty())
							for (Integer id : item.getSubtractedItem()) {

								for (ItemValue e : itemValueResult.getAttendanceItems()) {
									if (e.getItemId() == id) {
										if (e.getValue() != null && !ZERO.equals(e.getValue())
												&& !e.getValue().isEmpty()) {
											realData++;
										}
										subValueCalUpper.getAttendanceItems().add(e);
										break;
									}

								}

							}

						// get result upper calculate
						String result = "";
						if (!addValueCalUpper.getAttendanceItems().isEmpty()
								|| !subValueCalUpper.getAttendanceItems().isEmpty()) {
							result = this.getSumCalculateAttendanceItem(addValueCalUpper.getAttendanceItems(),
									subValueCalUpper.getAttendanceItems());
						}
						upperDailyRespond.add(new AttendanceRecordResponse(emp.getEmployeeId(),
								emp.getEmployeeName(), startDateByClosure, "", result));

					}

					//	日別項目（単一項目・下段） - Daily items (single item / lower)
					//	勤怠項目の実績値を取得し編集する - Acquire and edit the actual value of attendance items
					// return result lower-daily-singleItems
					AttendanceItemValueService.AttendanceItemValueResult valueSingleLower = AttendanceItemValueResult
							.builder().employeeId(emp.getEmployeeId()).workingDate(startDateByClosure)
							.attendanceItems(new ArrayList<ItemValue>()).build();
					// Fill in lower single item
					if (!singleIdLower.isEmpty()) {

						valueSingleLower = AttendanceItemValueResult.builder()
								.employeeId(emp.getEmployeeId()).workingDate(startDateByClosure)
								.attendanceItems(new ArrayList<ItemValue>()).build();
						for (Integer id : singleIdLower) {
							ItemValue value = new ItemValue();
							for (ItemValue item : itemValueResult.getAttendanceItems()) {
								if (item.getItemId() == id) {
									if (item.getValue() != null && !ZERO.equals(item.getValue())
											&& !item.getValue().isEmpty()) {
										realData++;
									}
									value = item;
									break;
								}

							}
							valueSingleLower.getAttendanceItems().add(value);
						}

					} else {
						valueSingleLower = AttendanceItemValueResult.builder()
								.employeeId(emp.getEmployeeId()).workingDate(startDateByClosure)
								.attendanceItems(new ArrayList<ItemValue>()).build();
						for (int i = 1; i <= 6; i++) {
							valueSingleLower.getAttendanceItems().add(new ItemValue());

						}
					}
					// convert data to show
					if (valueSingleLower != null)
						valueSingleLower.getAttendanceItems().forEach(item -> {
							if (item != null)
								lowerDailyRespond.add(new AttendanceRecordResponse(emp.getEmployeeId(),
										emp.getEmployeeName(), startDateByClosure, "",
										this.convertString(item, workTypeList, workTimeList, attendanceTypeList,
												optionalAttendanceRecExpSet.get().getNameUseAtr())));

						});

					//	日別項目（算出項目・下段） - Daily items (calculation items, lower row)
					//	勤怠項目の実績値を取得し集計、編集する - Acquire, aggregate, and edit the actual value of attendance items
					for (CalculateAttendanceRecord item : calculateLowerDaily) {

						AttendanceItemValueService.AttendanceItemValueResult addValueCalUpper = AttendanceItemValueResult
								.builder().attendanceItems(new ArrayList<>()).build();
						// Fill in lower calculate daily ADD item
						if (item.getAddedItem() != null && !item.getAddedItem().isEmpty())
							for (Integer id : item.getAddedItem()) {
								for (ItemValue e : itemValueResult.getAttendanceItems()) {
									if (e.getItemId() == id) {
										if (e.getValue() != null && !ZERO.equals(e.getValue())
												&& !e.getValue().isEmpty()) {
											realData++;
										}
										addValueCalUpper.getAttendanceItems().add(e);
										break;
									}

								}
							}

						AttendanceItemValueService.AttendanceItemValueResult subValueCalUpper = AttendanceItemValueResult
								.builder().attendanceItems(new ArrayList<>()).build();
						// Fill in lower calculate daily SUBTRACT item
						if (item.getSubtractedItem() != null && !item.getSubtractedItem().isEmpty())
							for (Integer id : item.getSubtractedItem()) {
								for (ItemValue e : itemValueResult.getAttendanceItems()) {
									if (e.getItemId() == id) {
										if (e.getValue() != null && !ZERO.equals(e.getValue())
												&& !e.getValue().isEmpty()) {
											realData++;
										}
										subValueCalUpper.getAttendanceItems().add(e);
										break;
									}

								}

							}

						// get result lower calculate daily
						String result = "";
						if (!addValueCalUpper.getAttendanceItems().isEmpty()
								|| !subValueCalUpper.getAttendanceItems().isEmpty()) {
							result = this.getSumCalculateAttendanceItem(addValueCalUpper.getAttendanceItems(),
									subValueCalUpper.getAttendanceItems());
						}
						lowerDailyRespond.add(new AttendanceRecordResponse(emp.getEmployeeId(),
								emp.getEmployeeName(), startDateByClosure, "", result));
					}
					
					
					// end calculate lower row item 
					
					startDate = startDate.addDays(1);
				}
			}
		}
		
		AttendanceRecordReportData recordReportData = new AttendanceRecordReportData();
		Optional<Company> optionalCompany = companyRepo.find(companyId);

		recordReportData.setCompanyName(optionalCompany.get().getCompanyName().toString());
//		recordReportData.setDailyHeader(dailyHeader);
//		recordReportData.setExportDateTime(exportDate);
//		recordReportData.setMonthlyHeader(monthlyHeader);
		recordReportData.setReportData(reportData);
		recordReportData.setReportName(optionalAttendanceRecExpSet.get().getName().v());
//		recordReportData.setSealColName(
//				optionalAttendanceRecExpSet.get().getSealUseAtr() ? sealStamp : new ArrayList<String>());
		recordReportData.setFontSize(optionalAttendanceRecExpSet.get().getExportFontSize().value);
		
		AttendanceRecordReportDatasource recordReportDataSource = new AttendanceRecordReportDatasource(recordReportData,
				request.getMode());
		
		// Generate file
		reportGenerator.generate(context.getGeneratorContext(), recordReportDataSource);

	}

	/**
	 *  月別実績の月初と月末の雇用コードをチェックする
	 *
	 * @param employeeId 社員ID(SID)
	 * @param period 期間(開始年月日(START_YMD)、終了年月日(END_YMD))
	 * @param firstEmpCode 月初雇用コード(FIRST_EMP_CD)
	 * @param lastEmpCode 月末雇用コード(LAST_EMP_CD)
	 * @return the monthly result check
	 */
	private MonthlyResultCheck checkEmployeeCodeInMonth(String employeeId, DatePeriod period, String firstEmpCode, String lastEmpCode) {
		String companyId = AppContexts.user().companyId();
		MonthlyResultCheck monthlyResultCheck = new MonthlyResultCheck();

		//	雇用取得結果　←　true (OK), チェック結果 　　←　true (OK)
		monthlyResultCheck.setEmployeeResult(true);
		monthlyResultCheck.setCheckResult(true);

		// Imported「（就業）所属雇用履歴」を取得する (Lấy Imported「（就業）所属雇用履歴」)
		Optional<SEmpHistExportImported> empStartDate = sEmpHistExportAdapter.getSEmpHistExport(companyId, employeeId,
				period.start());
		//	存在しない場合
		if (!empStartDate.isPresent()) {
			// 雇用取得結果　←　flase (NG) - チェック結果 　　←　true (OK)
			monthlyResultCheck.setEmployeeResult(false);
			monthlyResultCheck.setCheckResult(true);
			return monthlyResultCheck;
		}

		// Imported「（就業）所属雇用履歴」を取得する (Lấy Imported「（就業）所属雇用履歴」)
		Optional<SEmpHistExportImported> empEndDate = sEmpHistExportAdapter.getSEmpHistExport(companyId, employeeId,
				period.end());
		
		//	存在しない場合
		if (!empEndDate.isPresent()) {
			// 雇用取得結果　←　flase (NG) - チェック結果 　　←　true (OK)
			monthlyResultCheck.setEmployeeResult(false);
			monthlyResultCheck.setCheckResult(true);
			return monthlyResultCheck;
		}
		// パラメータ「月初雇用コード」と取得した現在の月初雇用コードが同じ
		// パラメータ「月末雇用コード」と取得した現在の月末雇用コードが同じ
		if (!firstEmpCode.equals(empStartDate.get().getEmploymentCode())
			|| !lastEmpCode.equals(empEndDate.get().getEmploymentCode())) {
			monthlyResultCheck.setEmployeeResult(true);
			monthlyResultCheck.setCheckResult(false);
			return monthlyResultCheck;
		}
		return monthlyResultCheck;
	}
	
	private DailyOutputAttendanceRecord getValueAttendanceItem(String layoutId, String employeeId, GeneralDate date, long outputItem, long division) {
		DailyOutputAttendanceRecord attendanceRecord = new DailyOutputAttendanceRecord();
		if (outputItem == SINGLE) {
			if (division == UPPER_POSITION) {
				//	より勤怠項目IDを取得する
				List<Integer> singleUperRecord  = this.singleAttendanceRepo.getIdSingleAttendanceRecordByPosition(layoutId,
						UPPER_POSITION);
			} else {
				List<Integer> singleLowerRecord  = this.singleAttendanceRepo.getIdSingleAttendanceRecordByPosition(layoutId,
						LOWER_POSITION);
			}
		} else {

		}
		return attendanceRecord;
	}
	
	private List<DailyOutputAttendanceRecord> getAttendanceValue(String companyId, GeneralDate date, long outputItem, long division) {
		
	}
	
	private String getSumCalculateAttendanceItem(List<ItemValue> addValueCalUpper, List<ItemValue> subValueCalUpper) {

		Double sum = new Double(0);
		if (!addValueCalUpper.isEmpty()
				&& (addValueCalUpper.get(0).getValueType().isInteger()
						|| addValueCalUpper.get(0).getValueType().isDouble())
				|| !subValueCalUpper.isEmpty() && (subValueCalUpper.get(0).getValueType().isInteger()
						|| subValueCalUpper.get(0).getValueType().isDouble())) {

			// calculate add
			if (!addValueCalUpper.isEmpty()) {
				for (ItemValue i : addValueCalUpper) {
					if (i.getValue() != null && !i.getValue().isEmpty())
						sum = Double.parseDouble(sum.toString()) + Double.parseDouble(i.value().toString());
				}
			}
			// calculate sub
			if (!subValueCalUpper.isEmpty()) {
				for (ItemValue i : subValueCalUpper) {
					if (i.getValue() != null && !i.getValue().isEmpty())
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
	
	private String convertMinutesToHours(String minutes) {
		if (minutes.equals("0") || minutes.equals("")) {
			return "0:00";
		}
		String FORMAT = "%d:%02d";
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
	
	private String convertString(ItemValue item, List<WorkType> workTypeList, List<WorkTimeSetting> workTimeSettingList,
			List<AttendanceType> attendanceTypeList, NameUseAtr nameUseAtr) {
		final String value = item.getValue();
		if (item.getValueType() == null || item.getValue() == null)
			return "";
		switch (item.getValueType()) {

		case TIME:
		case CLOCK:
		case TIME_WITH_DAY:

			if (Integer.parseInt(item.getValue()) == 0 || item.getValue().isEmpty())
				return "";
			return this.convertMinutesToHours(value.toString());
		case COUNT:
		case COUNT_WITH_DECIMAL:
			if (Integer.parseInt(item.getValue()) == 0 || item.getValue().isEmpty())
				return "";
			return value.toString() + " 回";
		case AMOUNT:
			if (Integer.parseInt(item.getValue()) == 0 || item.getValue().isEmpty())
				return "";
			DecimalFormat format = new DecimalFormat("###,###,###");
			return format.format(Integer.parseInt(value));

		case CODE:

			if (!attendanceTypeList.isEmpty()) {
				AttendanceType attendance = attendanceTypeList.stream()
						.filter(e -> e.getAttendanceItemId() == item.getItemId()).collect(Collectors.toList()).get(0);
				if (attendance.getScreenUseAtr().equals(ScreenUseAtr.ATTENDANCE_TYPE_OF_DERVICETYPE)) {
					List<WorkType> worktype = workTypeList.stream()
							.filter(ite -> ite.getWorkTypeCode().v().equals(value)).collect(Collectors.toList());
					if (!worktype.isEmpty())

						return nameUseAtr.equals(NameUseAtr.FORMAL_NAME) ? worktype.get(0).getName().v()
								: worktype.get(0).getAbbreviationName().v();
					return value + MASTER_UNREGISTERED ;
				} else {

					List<WorkTimeSetting> workTime = workTimeSettingList.stream()
							.filter(e -> e.getWorktimeCode().v().equals(value)).collect(Collectors.toList());
					if (!workTime.isEmpty())
						return nameUseAtr.equals(NameUseAtr.FORMAL_NAME)
								? workTime.get(0).getWorkTimeDisplayName().getWorkTimeName().v()
								: workTime.get(0).getWorkTimeDisplayName().getWorkTimeAbName().v();
					return value + MASTER_UNREGISTERED;
				}
			}
			return value + MASTER_UNREGISTERED;

		default:
			return value;

		}
	}
	
	private Integer getNumberOfColumnByFontSize(ExportFontSize fontSize) {
		switch (fontSize) {
			case CHAR_SIZE_LARGE: 
				return 
		case value:
			
			break;

		default:
			break;
		}
	}
}
