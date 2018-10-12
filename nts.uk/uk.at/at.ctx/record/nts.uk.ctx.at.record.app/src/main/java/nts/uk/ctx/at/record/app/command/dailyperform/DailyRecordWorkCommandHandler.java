package nts.uk.ctx.at.record.app.command.dailyperform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.task.AsyncTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.command.dailyperform.DailyCorrectEventServiceCenter.CorrectResult;
import nts.uk.ctx.at.record.app.command.dailyperform.affiliationInfor.AffiliationInforOfDailyPerformCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.affiliationInfor.AffiliationInforOfDailyPerformCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.affiliationInfor.BusinessTypeOfDailyPerformCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.affiliationInfor.BusinessTypeOfDailyPerformCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.attendanceleavinggate.AttendanceLeavingGateOfDailyCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.attendanceleavinggate.AttendanceLeavingGateOfDailyCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.attendanceleavinggate.PCLogInfoOfDailyCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.attendanceleavinggate.PCLogInfoOfDailyCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.attendancetime.AttendanceTimeOfDailyPerformCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.attendancetime.AttendanceTimeOfDailyPerformCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.audittrail.DPAttendanceItemRC;
import nts.uk.ctx.at.record.app.command.dailyperform.audittrail.DailyCorrectionLogCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.audittrail.DailyCorrectionLogCommandHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.breaktime.BreakTimeOfDailyPerformanceCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.breaktime.BreakTimeOfDailyPerformanceCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.calculationattribute.CalcAttrOfDailyPerformanceCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.calculationattribute.CalcAttrOfDailyPerformanceCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.checkdata.CheckPairDeviationReason;
import nts.uk.ctx.at.record.app.command.dailyperform.checkdata.DPItemValueRC;
import nts.uk.ctx.at.record.app.command.dailyperform.checkdata.RCDailyCorrectionResult;
import nts.uk.ctx.at.record.app.command.dailyperform.editstate.EditStateOfDailyPerformCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.editstate.EditStateOfDailyPerformCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.goout.OutingTimeOfDailyPerformanceCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.goout.OutingTimeOfDailyPerformanceCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.month.UpdateMonthAfterProcessDaily;
import nts.uk.ctx.at.record.app.command.dailyperform.month.UpdateMonthDailyParam;
import nts.uk.ctx.at.record.app.command.dailyperform.optionalitem.OptionalItemOfDailyPerformCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.optionalitem.OptionalItemOfDailyPerformCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.remark.RemarkOfDailyCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.remark.RemarkOfDailyCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.shorttimework.ShortTimeOfDailyCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.shorttimework.ShortTimeOfDailyCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.specificdatetttr.SpecificDateAttrOfDailyCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.specificdatetttr.SpecificDateAttrOfDailyCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.temporarytime.TemporaryTimeOfDailyPerformanceCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.temporarytime.TemporaryTimeOfDailyPerformanceCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.workinfo.WorkInformationOfDailyPerformCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.workinfo.WorkInformationOfDailyPerformCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.workrecord.AttendanceTimeByWorkOfDailyCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.workrecord.AttendanceTimeByWorkOfDailyCommandUpdateHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.workrecord.TimeLeavingOfDailyPerformanceCommandAddHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.workrecord.TimeLeavingOfDailyPerformanceCommandUpdateHandler;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordWorkFinder;
import nts.uk.ctx.at.record.dom.daily.itemvalue.DailyItemValue;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.AdTimeAndAnyItemAdUpService;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculateDailyRecordServiceCenter;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CommonCompanySettingForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ManagePerCompanySet;
import nts.uk.ctx.at.record.dom.monthly.erroralarm.EmployeeMonthlyPerErrorRepository;
import nts.uk.ctx.at.record.dom.monthly.updatedomain.UpdateAllDomainMonthService;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.IntegrationOfMonthly;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionType;
import nts.uk.ctx.at.shared.app.util.attendanceitem.CommandFacade;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil;
import nts.uk.ctx.at.shared.dom.attendance.util.RecordHandler;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@Stateless
public class DailyRecordWorkCommandHandler extends RecordHandler {

	/** 勤務情報： 日別実績の勤務情報 */
	@Inject
	@AttendanceItemLayout(layout = DAILY_WORK_INFO_CODE, jpPropertyName = DAILY_WORK_INFO_NAME, index = 1)
	private WorkInformationOfDailyPerformCommandAddHandler workInfoAddHandler;
	@Inject
	@AttendanceItemLayout(layout = DAILY_WORK_INFO_CODE, jpPropertyName = DAILY_WORK_INFO_NAME, index = 1)
	private WorkInformationOfDailyPerformCommandUpdateHandler workInfoUpdateHandler;

	/** 計算区分： 日別実績の計算区分 */
	@Inject
	@AttendanceItemLayout(layout = DAILY_CALCULATION_ATTR_CODE, jpPropertyName = DAILY_CALCULATION_ATTR_NAME, index = 2)
	private CalcAttrOfDailyPerformanceCommandAddHandler calcAttrAddHandler;
	@Inject
	@AttendanceItemLayout(layout = DAILY_CALCULATION_ATTR_CODE, jpPropertyName = DAILY_CALCULATION_ATTR_NAME, index = 2)
	private CalcAttrOfDailyPerformanceCommandUpdateHandler calcAttrUpdateHandler;

	/** 所属情報： 日別実績の所属情報 */
	@Inject
	@AttendanceItemLayout(layout = DAILY_AFFILIATION_INFO_CODE, jpPropertyName = DAILY_AFFILIATION_INFO_NAME, index = 3)
	private AffiliationInforOfDailyPerformCommandAddHandler affiliationInfoAddHandler;
	@Inject
	@AttendanceItemLayout(layout = DAILY_AFFILIATION_INFO_CODE, jpPropertyName = DAILY_AFFILIATION_INFO_NAME, index = 3)
	private AffiliationInforOfDailyPerformCommandUpdateHandler affiliationInfoUpdateHandler;

	/** エラー一覧： 社員の日別実績エラー一覧 */
	// @Inject
	// @AttendanceItemLayout(layout = "D", jpPropertyName = "", index = 4)
	// private EmployeeDailyPerErrorCommandAddHandler errorAddHandler;
	// @Inject
	// @AttendanceItemLayout(layout = "D", jpPropertyName = "", index = 4)
	// private EmployeeDailyPerErrorCommandUpdateHandler errorUpdateHandler;

	/** エラー一覧： 社員の日別実績エラー一覧 */
	@Inject
	@AttendanceItemLayout(layout = DAILY_BUSINESS_TYPE_CODE, jpPropertyName = DAILY_BUSINESS_TYPE_NAME, index = 4)
	private BusinessTypeOfDailyPerformCommandAddHandler businessTypeAddHandler;
	@Inject
	@AttendanceItemLayout(layout = DAILY_BUSINESS_TYPE_CODE, jpPropertyName = DAILY_BUSINESS_TYPE_NAME, index = 4)
	private BusinessTypeOfDailyPerformCommandUpdateHandler businessTypeUpdateHandler;

	/** 外出時間帯: 日別実績の外出時間帯 */
	@Inject
	@AttendanceItemLayout(layout = DAILY_OUTING_TIME_CODE, jpPropertyName = DAILY_OUTING_TIME_NAME, index = 5)
	private OutingTimeOfDailyPerformanceCommandAddHandler outingTimeAddHandler;
	@Inject
	@AttendanceItemLayout(layout = DAILY_OUTING_TIME_CODE, jpPropertyName = DAILY_OUTING_TIME_NAME, index = 5)
	private OutingTimeOfDailyPerformanceCommandUpdateHandler outingTimeUpdateHandler;

	/** 休憩時間帯: 日別実績の休憩時間帯 */
	@Inject
	@AttendanceItemLayout(layout = DAILY_BREAK_TIME_CODE, jpPropertyName = DAILY_BREAK_TIME_NAME, index = 6)
	private BreakTimeOfDailyPerformanceCommandAddHandler breakTimeAddHandler;
	@Inject
	@AttendanceItemLayout(layout = DAILY_BREAK_TIME_CODE, jpPropertyName = DAILY_BREAK_TIME_NAME, index = 6)
	private BreakTimeOfDailyPerformanceCommandUpdateHandler breakTimeUpdateHandler;

	/** 勤怠時間: 日別実績の勤怠時間 */
	@Inject
	@AttendanceItemLayout(layout = DAILY_ATTENDANCE_TIME_CODE, jpPropertyName = DAILY_ATTENDANCE_TIME_NAME, index = 7)
	private AttendanceTimeOfDailyPerformCommandAddHandler attendanceTimeAddHandler;
	@Inject
	@AttendanceItemLayout(layout = DAILY_ATTENDANCE_TIME_CODE, jpPropertyName = DAILY_ATTENDANCE_TIME_NAME, index = 7)
	private AttendanceTimeOfDailyPerformCommandUpdateHandler attendanceTimeUpdateHandler;

	/** 作業別勤怠時間: 日別実績の作業別勤怠時間 */
	@Inject
	@AttendanceItemLayout(layout = DAILY_ATTENDANCE_TIME_BY_WORK_CODE, jpPropertyName = DAILY_ATTENDANCE_TIME_BY_WORK_NAME, index = 8)
	private AttendanceTimeByWorkOfDailyCommandAddHandler attendanceTimeByWorkAddHandler;
	@Inject
	@AttendanceItemLayout(layout = DAILY_ATTENDANCE_TIME_BY_WORK_CODE, jpPropertyName = DAILY_ATTENDANCE_TIME_BY_WORK_NAME, index = 8)
	private AttendanceTimeByWorkOfDailyCommandUpdateHandler attendanceTimeByWorkUpdateHandler;

	/** 出退勤: 日別実績の出退勤 */
	@Inject
	@AttendanceItemLayout(layout = DAILY_ATTENDACE_LEAVE_CODE, jpPropertyName = DAILY_ATTENDACE_LEAVE_NAME, index = 9)
	private TimeLeavingOfDailyPerformanceCommandAddHandler timeLeavingAddHandler;
	@Inject
	@AttendanceItemLayout(layout = DAILY_ATTENDACE_LEAVE_CODE, jpPropertyName = DAILY_ATTENDACE_LEAVE_NAME, index = 9)
	private TimeLeavingOfDailyPerformanceCommandUpdateHandler timeLeavingUpdatedHandler;

	/** 短時間勤務時間帯: 日別実績の短時間勤務時間帯 */
	@Inject
	@AttendanceItemLayout(layout = DAILY_SHORT_TIME_CODE, jpPropertyName = DAILY_SHORT_TIME_NAME, index = 10)
	private ShortTimeOfDailyCommandAddHandler shortWorkTimeAddHandler;
	@Inject
	@AttendanceItemLayout(layout = DAILY_SHORT_TIME_CODE, jpPropertyName = DAILY_SHORT_TIME_NAME, index = 10)
	private ShortTimeOfDailyCommandUpdateHandler shortWorkTimeUpdateHandler;

	/** 特定日区分: 日別実績の特定日区分 */
	@Inject
	@AttendanceItemLayout(layout = DAILY_SPECIFIC_DATE_ATTR_CODE, jpPropertyName = DAILY_SPECIFIC_DATE_ATTR_NAME, index = 11)
	private SpecificDateAttrOfDailyCommandAddHandler specificDateAttrAddHandler;
	@Inject
	@AttendanceItemLayout(layout = DAILY_SPECIFIC_DATE_ATTR_CODE, jpPropertyName = DAILY_SPECIFIC_DATE_ATTR_NAME, index = 11)
	private SpecificDateAttrOfDailyCommandUpdateHandler specificDateAttrUpdateHandler;

	/** 入退門: 日別実績の入退門 */
	@Inject
	@AttendanceItemLayout(layout = DAILY_ATTENDANCE_LEAVE_GATE_CODE, jpPropertyName = DAILY_ATTENDANCE_LEAVE_GATE_NAME, index = 12)
	private AttendanceLeavingGateOfDailyCommandAddHandler attendanceLeavingGateAddHandler;
	@Inject
	@AttendanceItemLayout(layout = DAILY_ATTENDANCE_LEAVE_GATE_CODE, jpPropertyName = DAILY_ATTENDANCE_LEAVE_GATE_NAME, index = 12)
	private AttendanceLeavingGateOfDailyCommandUpdateHandler attendanceLeavingGateUpdateHandler;

	/** 任意項目: 日別実績の任意項目 */
	@Inject
	@AttendanceItemLayout(layout = DAILY_OPTIONAL_ITEM_CODE, jpPropertyName = DAILY_ATTENDACE_LEAVE_NAME, index = 13)
	private OptionalItemOfDailyPerformCommandAddHandler optionalItemAddHandler;
	@Inject
	@AttendanceItemLayout(layout = DAILY_OPTIONAL_ITEM_CODE, jpPropertyName = DAILY_OPTIONAL_ITEM_NAME, index = 13)
	private OptionalItemOfDailyPerformCommandUpdateHandler optionalItemUpdateHandler;

	/** 編集状態: 日別実績の編集状態 */
	@Inject
	@AttendanceItemLayout(layout = DAILY_EDIT_STATE_CODE, jpPropertyName = DAILY_EDIT_STATE_NAME, index = 14)
	private EditStateOfDailyPerformCommandAddHandler editStateAddHandler;
	@Inject
	@AttendanceItemLayout(layout = DAILY_EDIT_STATE_CODE, jpPropertyName = DAILY_EDIT_STATE_NAME, index = 14)
	private EditStateOfDailyPerformCommandUpdateHandler editStateUpdateHandler;

	/** 臨時出退勤: 日別実績の臨時出退勤 */
	@Inject
	@AttendanceItemLayout(layout = DAILY_TEMPORARY_TIME_CODE, jpPropertyName = DAILY_TEMPORARY_TIME_NAME, index = 15)
	private TemporaryTimeOfDailyPerformanceCommandAddHandler temporaryTimeAddHandler;
	@Inject
	@AttendanceItemLayout(layout = DAILY_TEMPORARY_TIME_CODE, jpPropertyName = DAILY_TEMPORARY_TIME_NAME, index = 15)
	private TemporaryTimeOfDailyPerformanceCommandUpdateHandler temporaryTimeUpdateHandler;

	@Inject
	@AttendanceItemLayout(layout = DAILY_PC_LOG_INFO_CODE, jpPropertyName = DAILY_PC_LOG_INFO_NAME, index = 16)
	private PCLogInfoOfDailyCommandAddHandler pcLogInfoAddHandler;
	@Inject
	@AttendanceItemLayout(layout = DAILY_PC_LOG_INFO_CODE, jpPropertyName = DAILY_PC_LOG_INFO_NAME, index = 16)
	private PCLogInfoOfDailyCommandUpdateHandler pcLogInfoUpdateHandler;

	@Inject
	@AttendanceItemLayout(layout = DAILY_REMARKS_CODE, jpPropertyName = DAILY_REMARKS_NAME, index = 17)
	private RemarkOfDailyCommandAddHandler remarksAddHandler;
	@Inject
	@AttendanceItemLayout(layout = DAILY_REMARKS_CODE, jpPropertyName = DAILY_REMARKS_NAME, index = 17)
	private RemarkOfDailyCommandUpdateHandler remarksUpdateHandler;

	@Inject
	private CalculateDailyRecordServiceCenter calcService;

	// @Inject
	// private ErAlCheckService determineErrorAlarmWorkRecordService;

	@Inject
	private EmployeeDailyPerErrorRepository employeeErrorRepo;

	@Inject
	private DailyRecordWorkFinder finder;

	@Inject
	private CheckPairDeviationReason checkPairDeviationReason;

	@Inject
	private DailyCorrectEventServiceCenter dailyCorrectEventServiceCenter;

	@Inject
	private DailyCorrectionLogCommandHandler handlerLog;

	@Inject
	private AdTimeAndAnyItemAdUpService registerCalcedService;

	@Inject
	private UpdateMonthAfterProcessDaily updateMonthAfterProcessDaily;
	
	@Inject
	private CommonCompanySettingForCalc commonCompanySettingForCalc;
	
	@Inject
	private UpdateAllDomainMonthService updateAllDomainMonthService;
	
	@Inject
	private EmployeeMonthlyPerErrorRepository employeeMonthlyPerErrorRepository;

	private static final List<String> DOMAIN_CHANGED_BY_CALCULATE = Arrays.asList(DAILY_WORK_INFO_CODE, DAILY_ATTENDANCE_TIME_CODE, DAILY_OPTIONAL_ITEM_CODE);
	
	private static final Map<String, String[]> DOMAIN_CHANGED_BY_EVENT = new HashMap<>();
	{
		DOMAIN_CHANGED_BY_EVENT.put(DAILY_WORK_INFO_CODE, getArray(DAILY_ATTENDACE_LEAVE_CODE, DAILY_BREAK_TIME_CODE));
		DOMAIN_CHANGED_BY_EVENT.put(DAILY_ATTENDACE_LEAVE_CODE, getArray(DAILY_BREAK_TIME_CODE));
	}

	private String[] getArray(String... arrays) {
		return arrays;
	}

	public void handleAdd(DailyRecordWorkCommand command) {
		handler(command, false);
	}

	public void handleUpdate(DailyRecordWorkCommand command) {
		handler(command, true);
	}

	public void handleAdd(List<DailyRecordWorkCommand> command) {
		handler(command, false);
	}

	public List<DPItemValueRC> handleUpdate(List<DailyRecordWorkCommand> command) {
		return handler(command, true);
	}

	// fix response
	public List<DPItemValueRC> handlerResWithNoEvent(List<DailyRecordWorkCommand> commandNew,
			List<DailyRecordWorkCommand> commandOld, List<DailyItemValue> dailyItems, UpdateMonthDailyParam month) {
		return handlerRes(commandNew, commandOld, dailyItems, true, month);
	}

	// fix response
	public RCDailyCorrectionResult handleUpdateRes(List<DailyRecordWorkCommand> commandNew,
			List<DailyRecordWorkCommand> commandOld, List<DailyItemValue> dailyItems, UpdateMonthDailyParam month,
			int mode) {
		return handlerResWithNoEvent(commandNew, commandOld, dailyItems, true, month, mode);
	}

	private <T extends DailyWorkCommonCommand> void handler(DailyRecordWorkCommand command, boolean isUpdate) {
		handler(Arrays.asList(command), isUpdate);
	}

	private <T extends DailyWorkCommonCommand> List<DPItemValueRC> handler(List<DailyRecordWorkCommand> commands,
			boolean isUpdate) {
		registerNotCalcDomain(commands, isUpdate);

		List<IntegrationOfDaily> calced = calcIfNeed(commands);

		List<DPItemValueRC> items = checkPairDeviationReason.checkInputDeviationReason(commands, new ArrayList<>());

		if (!items.isEmpty()) {
			return items;
		}
		updateDomainAfterCalc(calced, null);

		registerErrorWhenCalc(toMapParam(commands),
				calced.stream().map(d -> d.getEmployeeError()).flatMap(List::stream).collect(Collectors.toList()));
		return items;
	}

	private <T extends DailyWorkCommonCommand> List<DPItemValueRC> handlerRes(List<DailyRecordWorkCommand> commandNew,
			List<DailyRecordWorkCommand> commandOld, List<DailyItemValue> dailyItems, boolean isUpdate,
			UpdateMonthDailyParam month) {
		long time = System.currentTimeMillis();
		// remove domain error
		employeeErrorRepo.removeParam(toMapParam(commandNew));
		//merge item is edited into old domain  
		///domainOld
		//List<IntegrationOfDaily> domainDailyOld = convertToDomain(commandOld);
		///domainNew
		List<IntegrationOfDaily> domainDailyNew = new ArrayList<>(); 				
		//TODO insert before <=> domain event
		CorrectResult correctResult = dailyCorrectEventServiceCenter.correctTimeLeaveAndBreakTime(commandNew, AppContexts.user().companyId());
		List<DailyRecordWorkCommand> commandNewAfter = correctResult.getData();
		
		domainDailyNew = convertToDomain(commandNewAfter);
		
		//calculate
		ManagePerCompanySet  manageComanySet = commonCompanySettingForCalc.getCompanySetting();
		domainDailyNew = calcService.calculatePassCompanySetting(domainDailyNew, Optional.ofNullable(manageComanySet),ExecutionType.NORMAL_EXECUTION);
		// get error after caculator
		List<DPItemValueRC> items = checkPairDeviationReason.checkInputDeviationReason(commandNew, dailyItems);
		if (!items.isEmpty()) {
			return items;
		}
		// TODO update data
		registerNotCalcDomain(commandNewAfter, isUpdate);
		List<IntegrationOfDaily> lastDt = updateDomainAfterCalc(domainDailyNew, correctResult);

		registerErrorWhenCalc(domainDailyNew);

		updateMonthAfterProcessDaily.updateMonth(commandNewAfter, domainDailyNew,
				month == null ? Optional.empty() : month.getDomainMonth(), month);

		System.out.print("time insert: " + (System.currentTimeMillis() - time));
		ExecutorService executorService = Executors.newFixedThreadPool(1);
		AsyncTask task = AsyncTask.builder().withContexts().keepsTrack(false).threadName(this.getClass().getName())
				.build(() -> {
					Map<String, List<GeneralDate>> mapSidDate = commandOld.stream()
							.collect(Collectors.groupingBy(x -> x.getEmployeeId(),
									Collectors.collectingAndThen(Collectors.toList(),
											c -> c.stream().map(q -> q.getWorkDate()).collect(Collectors.toList()))));
//					List<DailyRecordDto> dtos = finder.find(mapSidDate);
					
					List<DailyRecordDto> dtos = lastDt.stream().map(c -> DailyRecordDto.from(c)).collect(Collectors.toList());
					
					List<DailyItemValue> dailyItemNews = dtos.stream()
							.map(c -> DailyItemValue.build().createItems(AttendanceItemUtil.toItemValues(c))
									.createEmpAndDate(c.getEmployeeId(), c.getDate()))
							.collect(Collectors.toList());
					handlerLog.handle(new DailyCorrectionLogCommand(dailyItems, dailyItemNews, commandNew, new HashMap<>()));
				});
		executorService.submit(task);

		return items;
	}

	private <T extends DailyWorkCommonCommand> RCDailyCorrectionResult handlerResWithNoEvent(
			List<DailyRecordWorkCommand> commandNew, List<DailyRecordWorkCommand> commandOld,
			List<DailyItemValue> dailyItems, boolean isUpdate, UpdateMonthDailyParam month, int mode) {
		List<IntegrationOfDaily> domainDailyNew = new ArrayList<>();
		List<IntegrationOfMonthly> lstMonthDomain = new ArrayList<>();
		if (month == null || !month.getDomainMonth().isPresent()) {
			// remove domain error
			employeeErrorRepo.removeParam(toMapParam(commandNew));
			
			// merge item is edited into old domain
			domainDailyNew = convertToDomain(commandNew);

			// caculator
			domainDailyNew = calcService.calculate(domainDailyNew);

		}
		if (mode == 0 && month.getNeedCallCalc() != null && month.getNeedCallCalc()) {
			lstMonthDomain = updateMonthAfterProcessDaily.updateMonth(commandNew,
					(month == null || !month.getDomainMonth().isPresent()) ? domainDailyNew : Collections.emptyList(),
					(month == null || !month.getDomainMonth().isPresent()) ? Optional.empty() : month.getDomainMonth(),
					month);
		}

		return new RCDailyCorrectionResult(domainDailyNew, lstMonthDomain, commandNew, commandOld, dailyItems, isUpdate);
	}

	public void handlerInsertAll(List<DailyRecordWorkCommand> commandNew, List<IntegrationOfDaily> domainDailyNew,
			List<DailyRecordWorkCommand> commandOld, List<DailyItemValue> dailyItems,
			List<IntegrationOfMonthly> lstMonthDomain, boolean isUpdate, UpdateMonthDailyParam month,  Map<Integer, DPAttendanceItemRC> lstAttendanceItem) {
		// get error after caculator
		// update data
		long time = System.currentTimeMillis();
		registerNotCalcDomain(commandNew, isUpdate);
		List<IntegrationOfDaily> lastDt =  updateDomainAfterCalc(domainDailyNew, null);
		
//		lstMonthDomain.forEach(x ->{
		if (month != null && month.getEmployeeId() != null) {
			// val error = x.getEmployeeMonthlyPerErrorList().get(0);
			employeeMonthlyPerErrorRepository.removeAll(month.getEmployeeId(), new YearMonth(month.getYearMonth()),
					ClosureId.valueOf(month.getClosureId()), new ClosureDate(month.getClosureDate().getClosureDay(),
							month.getClosureDate().getLastDayOfMonth()));
		}
//		});
		if(!lstMonthDomain.isEmpty() && month!= null && month.getDatePeriod() != null ) updateAllDomainMonthService.merge(lstMonthDomain, month.getDatePeriod().end());
		
		registerErrorWhenCalc(domainDailyNew);

		System.out.print("time insert: " + (System.currentTimeMillis() - time));
		ExecutorService executorService = Executors.newFixedThreadPool(1);
		AsyncTask task = AsyncTask.builder().withContexts().keepsTrack(false).threadName(this.getClass().getName())
				.build(() -> {
					Map<String, List<GeneralDate>> mapSidDate = commandOld.stream()
							.collect(Collectors.groupingBy(x -> x.getEmployeeId(),
									Collectors.collectingAndThen(Collectors.toList(),
											c -> c.stream().map(q -> q.getWorkDate()).collect(Collectors.toList()))));
					
//					List<DailyRecordDto> dtos = finder.find(mapSidDate);
					List<DailyRecordDto> dtos = lastDt.stream().map(c -> DailyRecordDto.from(c)).collect(Collectors.toList());
					
					List<DailyItemValue> dailyItemNews = AttendanceItemUtil.toItemValues(dtos).entrySet().stream().map(dto -> DailyItemValue.build().createItems(dto.getValue())
									.createEmpAndDate(dto.getKey().getEmployeeId(), dto.getKey().getDate())).collect(Collectors.toList());
//					List<DailyItemValue> dailyItemNews = dtos.stream()
//							.map(c -> DailyItemValue.build().createItems(AttendanceItemUtil.toItemValues(c))
//									.createEmpAndDate(c.getEmployeeId(), c.getDate()))
//							.collect(Collectors.toList());
					handlerLog.handle(new DailyCorrectionLogCommand(dailyItems, dailyItemNews, commandNew, lstAttendanceItem));
				});
		executorService.submit(task);
	}

	public void handlerNoCalc(List<DailyRecordWorkCommand> commandNew, List<DailyRecordWorkCommand> commandOld, List<EmployeeDailyPerError> lstError,
			List<DailyItemValue> dailyItems, boolean isUpdate, UpdateMonthDailyParam month, int mode, Map<Integer, DPAttendanceItemRC> lstAttendanceItem) {
		
		employeeErrorRepo.removeParam(toMapParam(commandNew));

		List<IntegrationOfDaily> domainDailyNew = convertToDomain(commandNew);

		registerNotCalcDomain(commandNew, isUpdate);

		List<IntegrationOfDaily> lastDt = updateDomainAfterCalc(domainDailyNew, null);

		registerErrorWhenCalc(lstError);

		if (mode == 0 && month.getNeedCallCalc()) {
			List<IntegrationOfMonthly> lstMonthDomain = updateMonthAfterProcessDaily.updateMonth(commandNew, domainDailyNew,
					month == null ? Optional.empty() : month.getDomainMonth(), month);
			
			lstMonthDomain.forEach(x -> {
				if (!x.getEmployeeMonthlyPerErrorList().isEmpty()) {
					val error = x.getEmployeeMonthlyPerErrorList().get(0);
					employeeMonthlyPerErrorRepository.removeAll(error.getEmployeeID(), error.getYearMonth(),
							error.getClosureId(), error.getClosureDate());
				}
			});
			updateAllDomainMonthService.merge(lstMonthDomain, month.getDatePeriod().end());
		}

		ExecutorService executorService = Executors.newFixedThreadPool(1);
		AsyncTask task = AsyncTask.builder().withContexts().keepsTrack(false).threadName(this.getClass().getName())
				.build(() -> {
					Map<String, List<GeneralDate>> mapSidDate = commandOld.stream()
							.collect(Collectors.groupingBy(x -> x.getEmployeeId(),
									Collectors.collectingAndThen(Collectors.toList(),
											c -> c.stream().map(q -> q.getWorkDate()).collect(Collectors.toList()))));
//					List<DailyRecordDto> dtos = finder.find(mapSidDate);
					List<DailyRecordDto> dtos = lastDt.stream().map(c -> DailyRecordDto.from(c)).collect(Collectors.toList());
					
					List<DailyItemValue> dailyItemNews = AttendanceItemUtil.toItemValues(dtos).entrySet().stream().map(et -> {
						return DailyItemValue.build().createItems(et.getValue())
						.createEmpAndDate(et.getKey().getEmployeeId(), et.getKey().getDate());
					}).collect(Collectors.toList());
					
					handlerLog.handle(new DailyCorrectionLogCommand(dailyItems, dailyItemNews, commandNew, lstAttendanceItem));
				});
		executorService.submit(task);
	}

	private <T extends DailyWorkCommonCommand> List<IntegrationOfDaily> updateDomainAfterCalc(List<IntegrationOfDaily> calced, CorrectResult correctResult) {
		if(correctResult != null){
			return registerCalcedService.addAndUpdate(calced, correctResult.getWorkType());
		}
		return registerCalcedService.addAndUpdate(calced);
//		calced.stream().forEach(c -> {
//			registerCalcedService.addAndUpdate(c.getAffiliationInfor().getEmployeeId(), c.getAffiliationInfor().getYmd(), 
//					c.getAttendanceTimeOfDailyPerformance(), c.getAnyItemValue());
//		});
//		commands.stream().forEach(c -> {
//			calced.stream().filter(d -> d.getAffiliationInfor().getEmployeeId().equals(c.getEmployeeId()) 
//					&& d.getAffiliationInfor().getYmd().equals(c.getWorkDate()))
//			.findFirst().ifPresent(d -> {
//				DOMAIN_CHANGED_BY_CALCULATE.stream().forEach(layout -> {
//					T command = (T) c.getCommand(layout);
//					Object updatedD = getDomain(layout, d);
//					if(updatedD != null){
//						updateCommandData(command, updatedD);
//						CommandFacade<T> handler = (CommandFacade<T>) getHandler(layout, isUpdate);
//						if(handler != null){
//							handler.handle(command);
//						}
//					}
//				});
//				
//			});
//		});
	}

	private void registerErrorWhenCalc(Map<String, List<GeneralDate>> param, List<EmployeeDailyPerError> errors) {
		// remove data error
		employeeErrorRepo.removeParam(param);
		// insert error;
		employeeErrorRepo.insert(errors.stream().filter(e -> e != null && e.getAttendanceItemList().get(0) != null)
				.collect(Collectors.toList()));
		// determineErrorAlarmWorkRecordService.createEmployeeDailyPerError(errors);
	}

	private void registerErrorWhenCalc(List<EmployeeDailyPerError> lstError) {
		// insert error;
		employeeErrorRepo.insert(lstError);
	}
	
	private void registerErrorWhenCalc(Collection<IntegrationOfDaily> domain) {
		registerErrorWhenCalc(domain.stream().map(d -> d.getEmployeeError()).flatMap(List::stream)
				.collect(Collectors.toList()).stream().filter(e -> e != null && e.getAttendanceItemList().get(0) != null)
				.collect(Collectors.toList()));
	}

	@SuppressWarnings({ "unchecked" })
	private <T extends DailyWorkCommonCommand> void registerNotCalcDomain(List<DailyRecordWorkCommand> commands,
			boolean isUpdate) {
		commands.stream().forEach(command -> {
			handleEditStates(isUpdate, command);

			List<String> mapped = command.itemValues().stream().map(c -> getGroup(c)).distinct()
					.collect(Collectors.toList());

			mapped.stream().filter(c -> !DOMAIN_CHANGED_BY_CALCULATE.contains(c)).forEach(c -> {
				CommandFacade<T> handler = (CommandFacade<T>) getHandler(c, isUpdate);
				if (handler != null) {
					handler.handle((T) command.getCommand(c));
				}
			});

			DOMAIN_CHANGED_BY_EVENT.entrySet().stream().filter(entry -> mapped.contains(entry.getKey()))
					.map(entry -> entry.getValue()).flatMap(x -> Arrays.stream(x)).distinct().forEach(layout -> {
						CommandFacade<T> handler = (CommandFacade<T>) getHandler(layout, isUpdate);
						if (handler != null) {
							handler.handle((T) command.getCommand(layout));
						}
					});
		});
	}

	@SuppressWarnings({ "unchecked" })
	private <T extends DailyWorkCommonCommand> void handleEditStates(boolean isUpdate, DailyRecordWorkCommand command) {
		CommandFacade<T> handler = (CommandFacade<T>) getHandler(DAILY_EDIT_STATE_CODE, isUpdate);
		if (handler != null) {
			handler.handle((T) command.getCommand(DAILY_EDIT_STATE_CODE));
		}
	}

	private List<IntegrationOfDaily> calcIfNeed(List<DailyRecordWorkCommand> commands) {
		return calcService.calculate(commands.stream().map(c -> c.toDomain()).collect(Collectors.toList()));
	}

	private CommandFacade<?> getHandler(String layout, boolean isUpdate) {
		switch (layout) {
		case DAILY_WORK_INFO_CODE:
			return isUpdate ? this.workInfoUpdateHandler : this.workInfoAddHandler;
		case DAILY_CALCULATION_ATTR_CODE:
			return isUpdate ? this.calcAttrUpdateHandler : this.calcAttrAddHandler;
		case DAILY_AFFILIATION_INFO_CODE:
			return isUpdate ? this.affiliationInfoUpdateHandler : this.affiliationInfoAddHandler;
		case DAILY_BUSINESS_TYPE_CODE:
			return isUpdate ? this.businessTypeUpdateHandler : this.businessTypeAddHandler;
		case DAILY_OUTING_TIME_CODE:
			return isUpdate ? this.outingTimeUpdateHandler : this.outingTimeAddHandler;
		case DAILY_BREAK_TIME_CODE:
			return isUpdate ? this.breakTimeUpdateHandler : this.breakTimeAddHandler;
		case DAILY_ATTENDANCE_TIME_CODE:
			return isUpdate ? this.attendanceTimeUpdateHandler : this.attendanceTimeAddHandler;
		case DAILY_ATTENDANCE_TIME_BY_WORK_CODE:
			return isUpdate ? this.attendanceTimeByWorkUpdateHandler : this.attendanceTimeByWorkAddHandler;
		case DAILY_ATTENDACE_LEAVE_CODE:
			return isUpdate ? this.timeLeavingUpdatedHandler : this.timeLeavingAddHandler;
		case DAILY_SHORT_TIME_CODE:
			return isUpdate ? this.shortWorkTimeUpdateHandler : this.shortWorkTimeAddHandler;
		case DAILY_SPECIFIC_DATE_ATTR_CODE:
			return isUpdate ? this.specificDateAttrUpdateHandler : this.specificDateAttrAddHandler;
		case DAILY_ATTENDANCE_LEAVE_GATE_CODE:
			return isUpdate ? this.attendanceLeavingGateUpdateHandler : this.attendanceLeavingGateAddHandler;
		case DAILY_OPTIONAL_ITEM_CODE:
			return isUpdate ? this.optionalItemUpdateHandler : this.optionalItemAddHandler;
		case DAILY_EDIT_STATE_CODE:
			return isUpdate ? this.editStateUpdateHandler : this.editStateAddHandler;
		case DAILY_TEMPORARY_TIME_CODE:
			return isUpdate ? this.temporaryTimeUpdateHandler : this.temporaryTimeAddHandler;
		case DAILY_PC_LOG_INFO_CODE:
			return isUpdate ? this.pcLogInfoUpdateHandler : this.pcLogInfoAddHandler;
		case DAILY_REMARKS_CODE:
			return isUpdate ? this.remarksUpdateHandler : this.remarksAddHandler;
		default:
			return null;
		}
	}

	private String getGroup(ItemValue c) {
		return String.valueOf(c.layoutCode().charAt(0));
	}

	private Map<String, List<GeneralDate>> toMapParam(List<DailyRecordWorkCommand> commands) {
		return commands.stream()
				.collect(Collectors.groupingBy(c -> c.getEmployeeId(), Collectors.collectingAndThen(Collectors.toList(),
						c -> c.stream().map(q -> q.getWorkDate()).collect(Collectors.toList()))));
	}

	private List<IntegrationOfDaily> convertToDomain(List<DailyRecordWorkCommand> commands) {
		return commands.stream().map(c -> c.toDomain()).collect(Collectors.toList());
	}

}
