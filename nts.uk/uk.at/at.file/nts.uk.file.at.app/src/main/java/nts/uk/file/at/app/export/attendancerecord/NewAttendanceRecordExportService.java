package nts.uk.file.at.app.export.attendancerecord;

import java.text.DecimalFormat;
import java.time.DayOfWeek;
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
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportColumnData;
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportDailyData;
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportData;
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportEmployeeData;
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportWeeklyData;
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportWeeklySumaryData;
import nts.uk.file.at.app.export.schedule.FileService;
import nts.uk.query.model.employee.EmployeeInformation;
import nts.uk.query.model.employee.EmployeeInformationQuery;
import nts.uk.query.model.employee.EmployeeInformationRepository;
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
	
	@Inject
	private EmployeeInformationRepository empInfoRepo;
	
	

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

			//	取得した月別実績の着目社員分をループ - Loop the acquired monthly results for the employees of interest
			ClosureDate closureDate = new ClosureDate(1, false);
			
			// Get start time - end time
			DatePeriod datePeriod = employeePeriod.get(emp.employeeId);
			YearMonth yearMonth = datePeriod.start().yearMonth();
			YearMonth endYearMonth = datePeriod.end().yearMonth();
			
			
			if (attendanceTypeList.isEmpty()) {
				attendanceTypeList.addAll(attendanceRepo.getItemByAtrandType(AppContexts.user().companyId(),
						screenUseAtrList, 1));
			}
//			for (MonthlyAttendanceItemValueResult monthlyValue : monthlyValues) {
			while(yearMonth.lessThanOrEqualTo(endYearMonth)) {
				// start date
//				GeneralDate startDate = GeneralDate.ymd(monthlyValue.getYearMonth().year(), monthlyValue.getYearMonth().month(), 1);
//				GeneralDate endDate = monthlyValue.getYearMonth().lastGeneralDate();
				GeneralDate startDate;
				GeneralDate endDate;
				
				
				// Get start - end date to export
				if (closureDate.getLastDayOfMonth()) {
					startDate = GeneralDate.ymd(yearMonth.year(), yearMonth.month(), 1);
					endDate = GeneralDate.ymd(yearMonth.year(), yearMonth.month(),
							yearMonth.lastDateInMonth());
				} else {
					startDate = GeneralDate.ymd(yearMonth.year(), yearMonth.month(),
							closureDate.getClosureDay().v() + 1);
					endDate = GeneralDate.ymd(yearMonth.addMonths(1).year(),
							yearMonth.addMonths(1).month(), closureDate.getClosureDay().v());
				}
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
					
				// List DailyData
				List<AttendanceRecordReportDailyData> dailyDataList = new ArrayList<>();
				
				// Weekly Data
				List<AttendanceRecordReportWeeklyData> weeklyDataList = new ArrayList<>();
				Integer realData = 0;
				
				
				//	重複期間．開始年月日～重複期間．終了年月日のループ
				while (startDate.beforeOrEquals(endDate)) {
						
						List<AttendanceRecordResponse> upperDailyRespond = new ArrayList<>();
						List<AttendanceRecordResponse> lowerDailyRespond = new ArrayList<>();
						
						attendanceRecord.setDate(startDate);
						GeneralDate closureDateTemp = startDate;
	
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
										.filter(x -> x.getWorkingDate().equals(closureDateTemp)).findFirst();
								if (itemValueOtp.isPresent()) {
									itemValueResult = itemValueOtp.get();
								}
							}
						}
						
						// Fill in upper single item
						if (!singleIdUpper.isEmpty()) {
							valueSingleUpper = AttendanceItemValueResult.builder().employeeId(emp.getEmployeeId())
									.workingDate(startDate).attendanceItems(new ArrayList<ItemValue>()).build();
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
									.workingDate(closureDateTemp).attendanceItems(new ArrayList<ItemValue>()).build();
							for (int i = 1; i <= 6; i++) {
								valueSingleUpper.getAttendanceItems().add(new ItemValue());
	
							}
						}
						
						// convert data to show
						if (valueSingleUpper != null) {
	
							valueSingleUpper.getAttendanceItems().forEach(item -> {
								if (item != null)
									upperDailyRespond.add(new AttendanceRecordResponse(emp.getEmployeeId(),
											emp.getEmployeeName(), closureDateTemp, "",
											this.convertString(item, workTypeList, workTimeList, attendanceTypeList,
													optionalAttendanceRecExpSet.get().getNameUseAtr())));
	
							});
						}
						
						//	日別項目（算出項目・上段） - Daily items (calculation items, upper row)
						//	勤怠項目の実績値を取得し集計、編集する - Acquire, aggregate, and edit the actual value of attendance items
						// return result upper-daily-calculateItems
						for (CalculateAttendanceRecord item : calculateUpperDaily) {
	
							AttendanceItemValueService.AttendanceItemValueResult addValueCalUpper = AttendanceItemValueResult
									.builder().employeeId(emp.getEmployeeId()).workingDate(closureDateTemp)
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
									.builder().employeeId(emp.getEmployeeId()).workingDate(closureDateTemp)
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
									emp.getEmployeeName(), closureDateTemp, "", result));
	
						}
	
						//	日別項目（単一項目・下段） - Daily items (single item / lower)
						//	勤怠項目の実績値を取得し編集する - Acquire and edit the actual value of attendance items
						// return result lower-daily-singleItems
						AttendanceItemValueService.AttendanceItemValueResult valueSingleLower = AttendanceItemValueResult
								.builder().employeeId(emp.getEmployeeId()).workingDate(closureDateTemp)
								.attendanceItems(new ArrayList<ItemValue>()).build();
						// Fill in lower single item
						if (!singleIdLower.isEmpty()) {
	
							valueSingleLower = AttendanceItemValueResult.builder()
									.employeeId(emp.getEmployeeId()).workingDate(closureDateTemp)
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
									.employeeId(emp.getEmployeeId()).workingDate(closureDateTemp)
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
											emp.getEmployeeName(), closureDateTemp, "",
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
									emp.getEmployeeName(), closureDateTemp, "", result));
						}
						
						AttendanceRecordReportDailyData dailyData = new AttendanceRecordReportDailyData();
						// Set data daily
						dailyData.setDate(String.valueOf(startDate.day()));
						dailyData.setDayOfWeek(DayOfWeekJP
								.getValue(startDate.localDate().getDayOfWeek().toString()).japanese);
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
						// end calculate lower row item 
						
						
						//	日別項目（算出項目・上段下段） - Daily items (calculation items, upper and lower)
						//	週単位の集計をする - Aggregate on a weekly basis
						//  đoạn này bạn thấy xử lý việc tính toán dai ly giống vs yêu cầu xử lý nên paste sang đây tạm
						
						
						dailyData.setColumnDatas(columnDatas);
						dailyData.setSecondCol(flag <= 15 ? false : true);
						dailyDataList.add(dailyData);
						// Check end of week
						if (startDate.localDate().getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
							AttendanceRecordReportWeeklyData weeklyData = new AttendanceRecordReportWeeklyData();
							// Set weekly data
							weeklyData.setDailyDatas(dailyDataList);
							// Set total result in week
							AttendanceRecordReportWeeklySumaryData summaryWeeklyData = new AttendanceRecordReportWeeklySumaryData();
							summaryWeeklyData = this.getSumWeeklyValue(dailyDataList);
	
							weeklyData.setWeeklySumaryData(summaryWeeklyData);
	
							weeklyDataList.add(weeklyData);
							// empty daily data list
							dailyDataList = new ArrayList<>();
	
						}
						
						// Next day
						// chác chắn đoạn next day này của bạn làm là đúng r nhé Liên
						startDate = startDate.addDays(1);
						
					}
				
					
				
				
				//	日別項目（算出項目・上段下段） -  Daily items (calculation items, upper and lower)
				//	週単位集計値の編集をする - Edit weekly aggregated values 
				// đoạn này là tổng hợp theo tuần . tính toán để đẩy ra dữ liệu  theo tuần ( weekly data ) 
				// lấy giá trị từ cái DailyOutputAttendanceRecord đã mapping ở trên
				//	Edit the zero display classification etc. according to the algorithm 「(UK2)実績を取得する」
				// TODO
				// Day of the last week in month
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
		
				
				//	月別項目（算出項目・上段） - Monthly items (calculation items, upper row)
				//	勤怠項目の実績値を取得し集計、編集する - Acquire, aggregate, and edit the actual value of attendance items
				List<String> upperResult = new ArrayList<>();
				List<String> lowerResult = new ArrayList<>();
				AttendanceItemValueService.MonthlyAttendanceItemValueResult itemValueResult = MonthlyAttendanceItemValueResult
						.builder().attendanceItems(new ArrayList<>()).build();

				if (!calculateUpperMonthly.isEmpty() || !calculateLowerMonthly.isEmpty()) {

					// Get montnly result
					if (monthlyValuesAll.containsKey(emp.getEmployeeId())) {
						List<MonthlyAttendanceItemValueResult> monthlyValuesByEmp = monthlyValuesAll
								.get(emp.getEmployeeId());
						for (MonthlyAttendanceItemValueResult item : monthlyValuesByEmp) {
							if (item.getYearMonth()
									.equals(closureDate.getLastDayOfMonth() ? yearMonth : yearMonth.addMonths(1))
									&& item.getClouseDate() == closureDate.getClosureDay().v()) {
								itemValueResult = item;
								break;
							}
						}
					}
				}

				for (CalculateAttendanceRecord item : calculateUpperMonthly) {
					AttendanceItemValueService.MonthlyAttendanceItemValueResult monthlyUpperAddResult = MonthlyAttendanceItemValueResult
							.builder().attendanceItems(new ArrayList<>()).build();
					AttendanceItemValueService.MonthlyAttendanceItemValueResult monthlyUpperSubResult = MonthlyAttendanceItemValueResult
							.builder().attendanceItems(new ArrayList<>()).build();

					// Fill in upper calculate monthly ADD item
					if (item.getAddedItem() != null && !item.getAddedItem().isEmpty()) {
						for (Integer id : item.getAddedItem()) {
							for (ItemValue e : itemValueResult.getAttendanceItems()) {
								if (id == e.getItemId()) {
									if (e.getValue() != null && !ZERO.equals(e.getValue()) && !e.getValue().isEmpty()) {
										realData++;
									}
									monthlyUpperAddResult.getAttendanceItems().add(e);
									break;
								}

							}

						}
					}

					// Fill in upper calculate monthly SUBTRACT item
					if (item.getSubtractedItem() != null && !item.getSubtractedItem().isEmpty()) {
						for (Integer id : item.getSubtractedItem()) {
							for (ItemValue e : itemValueResult.getAttendanceItems()) {
								if (id == e.getItemId()) {
									if (e.getValue() != null && !ZERO.equals(e.getValue()) && !e.getValue().isEmpty()) {
										realData++;
									}
									monthlyUpperSubResult.getAttendanceItems().add(e);
									break;
								}

							}

						}
					}

					// Get upper monthly result
					String result = "";
					if (!monthlyUpperAddResult.getAttendanceItems().isEmpty()
							|| !monthlyUpperSubResult.getAttendanceItems().isEmpty()) {
						result = this.getSumCalculateAttendanceItem(monthlyUpperAddResult.getAttendanceItems(),
								monthlyUpperSubResult.getAttendanceItems());
					}
					upperResult.add(result);

				}

				
				//	月別項目（算出項目・下段） -  Monthly items (calculation items, lower row)
				//	勤怠項目の実績値を取得し集計、編集する - Acquire, aggregate, and edit the actual value of attendance items
				for (CalculateAttendanceRecord item : calculateLowerMonthly) {
					AttendanceItemValueService.MonthlyAttendanceItemValueResult monthlyLowerAddResult = MonthlyAttendanceItemValueResult
							.builder().attendanceItems(new ArrayList<>()).build();

					AttendanceItemValueService.MonthlyAttendanceItemValueResult monthlyLowerSubResult = MonthlyAttendanceItemValueResult
							.builder().attendanceItems(new ArrayList<>()).build();
					// Fill in lower calculate monthly ADD item
					if (item.getAddedItem() != null && !item.getAddedItem().isEmpty()) {
						for (Integer id : item.getAddedItem()) {
							for (ItemValue e : itemValueResult.getAttendanceItems()) {
								if (id == e.getItemId()) {
									if (e.getValue() != null && !ZERO.equals(e.getValue()) && !e.getValue().isEmpty()) {
										realData++;
									}
									monthlyLowerAddResult.getAttendanceItems().add(e);
									break;
								}

							}

						}
					}

					// Fill in lower calculate monthly SUBTRACT item
					if (item.getSubtractedItem() != null && !item.getSubtractedItem().isEmpty()) {
						for (Integer id : item.getSubtractedItem()) {
							for (ItemValue e : itemValueResult.getAttendanceItems()) {
								if (id == e.getItemId()) {
									if (e.getValue() != null && !ZERO.equals(e.getValue()) && !e.getValue().isEmpty()) {
										realData++;
									}
									monthlyLowerSubResult.getAttendanceItems().add(e);
									break;
								}

							}

						}
					}

					// Get lower monthly result
					String result = new String("");
					if (!monthlyLowerAddResult.getAttendanceItems().isEmpty()
							|| !monthlyLowerSubResult.getAttendanceItems().isEmpty()) {
						result = this.getSumCalculateAttendanceItem(monthlyLowerAddResult.getAttendanceItems(),
								monthlyLowerSubResult.getAttendanceItems());
					}
					lowerResult.add(result);

				}

				//	月次確認済表示区分をチェックする - Check the monthly confirmed display category
				
				//	表示  - if display 
				
					//	月の承認済状況を編集する - Edit the approved status of the month
				//	TODO
				
				
				//	アルゴリズム「社員情報を返す」を実行する  - Execute the algorithm "Return employee information"
				// Param  出力対象社員ID（List) - Output target employee ID (List),   基準日 - Reference date
				
				EmployeeInformationQuery query = EmployeeInformationQuery.builder()
						.employeeIds(empIDs)
						.referenceDate(baseDate.get())
						.toGetClassification(true)
						.toGetDepartment(true)
						.toGetEmployment(true)
						.toGetEmploymentCls(true)
						.toGetPosition(false)
						.toGetWorkplace(true)
						.build();

					// note: アルゴリズム「<<Public>> 社員の情報を取得する」を実行する
				List<EmployeeInformation> lstEmplInfor = empInfoRepo.find(query);
				
				//	ヘッダー部(社員、職場、雇用、職位、勤務区分および年月)を編集する - Edit the header section (employee, workplace, employment, position, work category and year / month)
					// 	年月　←　月別実績データの年月(YM) - Year / month ← Year / month of monthly actual data (YM)
				// TODO
				
				//	ヘッダー部(締め日)を編集する - Edit the header part (closing date)
				// TODO
				
				// START Convert to AttendanceRecordReportColumnData
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
					if (item != null) {
						if (columnDataMonthlyArray[index] == null) {
							columnDataMonthlyArray[index] = new AttendanceRecordReportColumnData("", "");
						}
						columnDataMonthlyArray[index].setLower(item);
					}
					index++;

				}

				for (int i = 0; i < columnDataMonthlyArray.length; i++) {
					if (columnDataMonthlyArray[i] == null)
						columnDataMonthlyArray[i] = new AttendanceRecordReportColumnData("", "");
					employeeMonthlyData.add(columnDataMonthlyArray[i]);
				}
				
				// END Convert to AttendanceRecordReportColumnData
		
				// Next monthly
				yearMonth = yearMonth.addMonths(1);
			}
			//	着目社員の全期間分の帳票データを生成する - Generate form data for the entire period of the employee of interest
			// TODO
			
			
			
		}
		
		//		エラーリストに社員が入っているか判別する - Determine if an employee is in the error list
		// TODO
		
		//	取得できた実績データがある社員の件数をチェック - Check the number of employees who have acquired performance data
		
		AttendanceRecordReportData recordReportData = new AttendanceRecordReportData();
		//	印鑑欄を生成する - Generate a seal column
		// get seal stamp
		List<String> sealStamp = attendanceRecExpSetRepo.getSealStamp(companyId, layoutId);
		recordReportData.setSealColName(
				optionalAttendanceRecExpSet.get().getSealUseAtr() ? sealStamp : new ArrayList<String>());
		
		//	日別項目ヘッダー部の名称を生成する - Generate the name of the daily item header part
		
//		recordReportData.setDailyHeader(dailyHeader);
		
		//	月別項目ヘッダー部の名称を生成する - Generate the name of the monthly item header part
		
//		recordReportData.setMonthlyHeader(monthlyHeader);		
		//	会社名を生成する - Generate company name
		Optional<Company> optionalCompany = companyRepo.find(companyId);

		recordReportData.setCompanyName(optionalCompany.get().getCompanyName().toString());
		
		//	帳票タイトルを生成する - Generate form title
		
		//	印刷年月日時分を生成する	- Generate print date, time, and minute
		recordReportData.setReportData(reportData);
		// 	出力先を判別する - Determine the output destination
		
		

//		recordReportData.setExportDateTime(exportDate);

		//	生成した出勤簿のPDFデータをダウンロードする OR 生成した出勤簿のEXCELデータをダウンロードする
		recordReportData.setReportName(optionalAttendanceRecExpSet.get().getName().v());
		
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
	
//	private List<DailyOutputAttendanceRecord> getAttendanceValue(String companyId, GeneralDate date, long outputItem, long division) {
//		
//	}
	
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
	
//	private Integer getNumberOfColumnByFontSize(ExportFontSize fontSize) {
//		switch (fontSize) {
//			case CHAR_SIZE_LARGE: 
//				return 
//		case value:
//			
//			break;
//
//		default:
//			break;
//		}
//	}
//	
	/**
	 * Gets the sum weekly value.
	 *
	 * @param list the list
	 * @return the sum weekly value
	 */
	public AttendanceRecordReportWeeklySumaryData getSumWeeklyValue(List<AttendanceRecordReportDailyData> list) {
		AttendanceRecordReportWeeklySumaryData result = new AttendanceRecordReportWeeklySumaryData();

		String upperValue7th = "";
		String lowerValue7th = "";
		String upperValue8th = "";
		String lowerValue8th = "";
		String upperValue9th = "";
		String lowerValue9th = "";
		String upperValue10th = "";
		String lowerValue10th = "";
		String upperValue11th = "";
		String lowerValue11th = "";
		String upperValue12th = "";
		String lowerValue12th = "";
		String upperValue13th = "";
		String lowerValue13th = "";

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
			if (item.getColumnDatas().get(9) != null) {
				upperValue9th = this.add(upperValue10th, item.getColumnDatas().get(9).getUper());
				lowerValue9th = this.add(lowerValue10th, item.getColumnDatas().get(9).getLower());
			}
			if (item.getColumnDatas().get(10) != null) {
				upperValue9th = this.add(upperValue11th, item.getColumnDatas().get(10).getUper());
				lowerValue9th = this.add(lowerValue11th, item.getColumnDatas().get(10).getLower());
			}
			if (item.getColumnDatas().get(11) != null) {
				upperValue9th = this.add(upperValue12th, item.getColumnDatas().get(11).getUper());
				lowerValue9th = this.add(lowerValue12th, item.getColumnDatas().get(11).getLower());
			}
			if (item.getColumnDatas().get(12) != null) {
				upperValue9th = this.add(upperValue13th, item.getColumnDatas().get(12).getUper());
				lowerValue9th = this.add(lowerValue13th, item.getColumnDatas().get(12).getLower());
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
		AttendanceRecordReportColumnData columnData10 = new AttendanceRecordReportColumnData(upperValue10th,
				lowerValue10th);
		columnDatas.add(columnData10);
		AttendanceRecordReportColumnData columnData11 = new AttendanceRecordReportColumnData(upperValue11th,
				lowerValue11th);
		columnDatas.add(columnData11);
		AttendanceRecordReportColumnData columnData12 = new AttendanceRecordReportColumnData(upperValue12th,
				lowerValue12th);
		columnDatas.add(columnData12);
		AttendanceRecordReportColumnData columnData13 = new AttendanceRecordReportColumnData(upperValue13th,
				lowerValue13th);
		columnDatas.add(columnData13);

		result.setDateRange(list.get(0).getDate() + "-" + list.get(list.size() - 1).getDate());
		result.setColumnDatas(columnDatas);
		result.setSecondCol(list.get(list.size() - 1).isSecondCol());

		return result;

	}

	/**
	 * Adds the.
	 *
	 * @param a the a
	 * @param b the b
	 * @return the string
	 */
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
}
