package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.basicschedule.BasicScheduleAdapter;
import nts.uk.ctx.at.record.dom.adapter.workschedule.WorkScheduleWorkInforAdapter;
import nts.uk.ctx.at.record.dom.adapter.workschedule.WorkScheduleWorkInforImport;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.record.dom.affiliationinformation.repository.AffiliationInforOfDailyPerforRepository;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.record.dom.calculationattribute.repo.CalAttrOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGateOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.CheckAttendanceHolidayOutPut;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.NewReflectStampOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ReflectStampOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.EmbossingExecutionFlag;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.createdailyresults.CreateDailyResults;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.errorcheck.CalculationErrorCheckService;
import nts.uk.ctx.at.record.dom.dailyresultcreationprocess.creationprocess.creationclass.dailywork.TemporarilyReflectStampDailyAttd;
import nts.uk.ctx.at.record.dom.shorttimework.ShortTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm.BreakTimeStampIncorrectOrderChecking;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm.BreakTimeStampLeakageChecking;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm.DoubleStampAlgorithm;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm.ExitStampCheck;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm.ExitStampIncorrectOrderCheck;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm.GoingOutStampLeakageChecking;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm.GoingOutStampOrderChecking;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm.LackOfStampingAlgorithm;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm.MissingOfTemporaryStampChecking;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm.PCLogOnOffIncorrectOrderCheck;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm.PClogOnOffLackOfStamp;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm.StampIncorrectOrderAlgorithm;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm.TemporaryDoubleStampChecking;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm.TemporaryStampOrderChecking;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.ErrMessageResource;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.output.ReflectShortWorkingOutPut;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.repository.RecreateFlag;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.repository.ReflectShortWorkingTimeDomainService;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.ScheduleTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.EndStatus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.OutputTimeReflectForWorkinfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.StampReflectOnHolidayOutPut;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.StampReflectRangeOutput;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.StampReflectTimezoneOutput;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.TimeReflectFromWorkinfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.TimeZoneOutput;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemService;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfo;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionContent;
import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingService;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ReflectStampDomainServiceImpl implements ReflectStampDomainService {

	@Inject
	private WorkInformationRepository workInformationRepository;
	
	@Inject
	private BasicScheduleService basicScheduleService;

	@Inject
	private BasicScheduleAdapter basicScheduleAdapter;

	@Inject
	private LackOfStampingAlgorithm lackOfStamping;

	@Inject
	private StampIncorrectOrderAlgorithm stampIncorrectOrderAlgorithm;

	@Inject
	private DoubleStampAlgorithm doubleStampAlgorithm;

	@Inject
	private MissingOfTemporaryStampChecking missingOfTemporaryStampChecking;

	@Inject
	private TemporaryStampOrderChecking temporaryStampOrderChecking;

	@Inject
	private TemporaryDoubleStampChecking temporaryDoubleStampChecking;

	@Inject
	private GoingOutStampLeakageChecking goingOutStampLeakageChecking;

	@Inject
	private GoingOutStampOrderChecking goingOutStampOrderChecking;

	@Inject
	private BreakTimeStampLeakageChecking breakTimeStampLeakageChecking;

	@Inject
	private BreakTimeStampIncorrectOrderChecking breakTimeStampIncorrectOrderChecking;

	@Inject
	private ReflectEmbossingDomainService reflectEmbossingDomainService;

	@Inject
	private StampDomainService stampDomainService;

	@Inject
	private WorkingConditionItemService workingConditionItemService;

	@Inject
	private ExitStampCheck exitStampCheck;

	@Inject
	private ExitStampIncorrectOrderCheck exitStampIncorrectOrderCheck;

	@Inject
	private PClogOnOffLackOfStamp pClogOnOffLackOfStamp;

	@Inject
	private PCLogOnOffIncorrectOrderCheck pCLogOnOffIncorrectOrderCheck;

	@Inject
	private ReflectBreakTimeOfDailyDomainService reflectBreakTimeOfDailyDomainService;

	@Inject
	private ReflectShortWorkingTimeDomainService reflectShortWorkingTimeDomainService;

	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;

	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private AffiliationInforOfDailyPerforRepository affiliationInforOfDailyPerforRepository;
	
	@Inject
	private CalAttrOfDailyPerformanceRepository calAttrOfDailyPerformanceRepository;
	
	@Inject
	private TimeReflectFromWorkinfo timeReflectFromWorkinfo;
	
	@Inject
	private DailyRecordConverter dailyRecordConverter;
	
	@Inject
	private CreateDailyResults createDailyResults;
	
	@Inject
	private CalculationErrorCheckService calculationErrorCheckService;
	
	@Inject
	private TemporarilyReflectStampDailyAttd temporarilyReflectStampDailyAttd;
	
	@Inject
	private WorkTimeSettingService workTimeSettingService;
	
	@Inject
	private WorkScheduleWorkInforAdapter workScheduleWorkInforAdapter;
	
	@Override
	public NewReflectStampOutput reflectStampInfo(String companyID, String employeeID, GeneralDate processingDate,
			WorkInfoOfDailyPerformance workInfoOfDailyPerformance,
			TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance, String empCalAndSumExecLogID,
			Optional<CalAttrOfDailyPerformance> calcOfDaily,
			Optional<AffiliationInforOfDailyPerfor> affInfoOfDaily,
        	RecreateFlag recreateFlag) {

		NewReflectStampOutput newReflectStampOutput = new NewReflectStampOutput();

		ReflectStampOutput reflectStamp = new ReflectStampOutput();
		List<ErrMessageInfo> errMesInfos = new ArrayList<>();

		WorkTypeCode workTypeCode = workInfoOfDailyPerformance.getWorkInformation().getRecordInfo().getWorkTypeCode();

		WorkTimeCode workTimeCode = workInfoOfDailyPerformance.getWorkInformation().getRecordInfo().getWorkTimeCode();

		// 1日半日出勤・1日休日系の判
		WorkStyle workStyle = basicScheduleService.checkWorkDay(workTypeCode.v());

		if (workStyle == WorkStyle.ONE_DAY_REST) {
			newReflectStampOutput.setErrMesInfos(errMesInfos);
			newReflectStampOutput.setReflectStampOutput(reflectStamp);
			return newReflectStampOutput;
		}
		//ドメインモデル「勤務種類」を取得
		boolean checkWorkType = this.workTypeRepository.findWorkTypeRecord(companyID, workTypeCode.v());
		if(checkWorkType == false){
			//エラー処理
			ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeID, empCalAndSumExecLogID,
					new ErrMessageResource("023"), EnumAdaptor.valueOf(0, ExecutionContent.class), processingDate,
					new ErrMessageContent(TextResource.localize("Msg_590")));
			errMesInfos.add(employmentErrMes);
			newReflectStampOutput.setErrMesInfos(errMesInfos);
			return newReflectStampOutput;
		}

		// ドメインモデル「就業時間帯の設定」を取得する
		Optional<WorkTimeSetting> workTimeOpt = this.workTimeSettingRepository.findByCodeAndAbolishCondition(companyID,
				workTimeCode.v(), AbolishAtr.NOT_ABOLISH);

		if (!workTimeOpt.isPresent()) {
			ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeID, empCalAndSumExecLogID,
					new ErrMessageResource("016"), EnumAdaptor.valueOf(0, ExecutionContent.class), processingDate,
					new ErrMessageContent(TextResource.localize("Msg_591")));
			errMesInfos.add(employmentErrMes);
			newReflectStampOutput.setErrMesInfos(errMesInfos);
			return newReflectStampOutput;
		}

		// result data
		StampReflectRangeOutput stampReflectRangeOutput = new StampReflectRangeOutput();

		// 打刻反映時間帯を取得する - start
		// 打刻反映時の出勤休日扱いチェック
		CheckAttendanceHolidayOutPut attendanceHolidayOutPut = this.checkAttendanceHoliday(employeeID,
				companyID, workTypeCode.v(), processingDate);
		if (attendanceHolidayOutPut.getErrMesInfos() != null && !attendanceHolidayOutPut.getErrMesInfos().isEmpty()) {
			errMesInfos.addAll(attendanceHolidayOutPut.getErrMesInfos());
			newReflectStampOutput.setErrMesInfos(errMesInfos);
			return newReflectStampOutput;
		}
		boolean isAtWork = attendanceHolidayOutPut.isAtWork();

		// 終了状態：休日扱い - る
		if (isAtWork == false) {
			stampReflectRangeOutput = this.holidayStampRange(companyID, workInfoOfDailyPerformance, processingDate,
					employeeID);
		}
		// 終了状態：出勤扱い - 出勤系の打刻範囲を取得する
		else {
			stampReflectRangeOutput = this.attendanSytemStampRange(workTimeCode, companyID, workInfoOfDailyPerformance);
		}

		// 打刻取得範囲の作成
		createStampReflectRangeInaDay(stampReflectRangeOutput);

		// 外出の打刻反映範囲を取得する
		stampReflectRangeOutput.setGoOut(stampReflectRangeOutput.getStampRange());
		// 臨時の打刻反映範囲を作成する
		stampReflectRangeOutput.setTemporary(stampReflectRangeOutput.getStampRange());
		// - end

		// 打刻を反映する - Dung code
        List<Stamp> lstStamp =this.stampDomainService.handleData(stampReflectRangeOutput,
                empCalAndSumExecLogID, processingDate, employeeID, companyID,recreateFlag);
		if (lstStamp == null) {
			reflectStamp = null;
		}

		TimeLeavingOfDailyPerformance performance = new TimeLeavingOfDailyPerformance(employeeID, processingDate, timeLeavingOfDailyPerformance.getAttendance());
		// lstStamp is null -> has error
		if (lstStamp != null) {

			if (!lstStamp.isEmpty()) {
				reflectStamp = this.reflectEmbossingDomainService.reflectStamp(workInfoOfDailyPerformance,
						timeLeavingOfDailyPerformance, lstStamp, stampReflectRangeOutput, processingDate,
						employeeID, companyID);
			} else {
				reflectStamp.setTimeLeavingOfDailyPerformance(performance);
			}

			// 就業時間帯の休憩時間帯を日別実績に写す
			// 就業時間帯の休憩時間帯を日別実績に反映する
			BreakTimeOfDailyPerformance breakTimeOfDailyPerformance = this.reflectBreakTimeOfDailyDomainService
					.reflectBreakTimeZone(companyID, employeeID, processingDate, empCalAndSumExecLogID,
							timeLeavingOfDailyPerformance, workInfoOfDailyPerformance);
			reflectStamp.setBreakTimeOfDailyPerformance(breakTimeOfDailyPerformance);

			// 短時間勤務時間帯を反映する
			ReflectShortWorkingOutPut outPut = this.reflectShortWorkingTimeDomainService.reflect(empCalAndSumExecLogID,
					companyID, processingDate, employeeID, workInfoOfDailyPerformance.getWorkInformation(), timeLeavingOfDailyPerformance.getAttendance());
			if (outPut.getErrMesInfos() != null && !outPut.getErrMesInfos().isEmpty()) {
				errMesInfos.addAll(outPut.getErrMesInfos());
				newReflectStampOutput.setErrMesInfos(errMesInfos);
				return newReflectStampOutput;
			}
			ShortTimeOfDailyPerformance dailyPerformances = new ShortTimeOfDailyPerformance(employeeID, processingDate, outPut.getShortTimeOfDailyPerformance());
			reflectStamp.setShortTimeOfDailyPerformance(dailyPerformances);
			WorkInfoOfDailyPerformance dailyPerformance = new WorkInfoOfDailyPerformance(employeeID, processingDate, workInfoOfDailyPerformance.getWorkInformation());
			// エラーチェック
			this.errorCheck(companyID, employeeID, processingDate, dailyPerformance,
					reflectStamp.getTimeLeavingOfDailyPerformance(), reflectStamp.getOutingTimeOfDailyPerformance(),
					reflectStamp.getTemporaryTimeOfDailyPerformance(), breakTimeOfDailyPerformance,
					reflectStamp.getAttendanceLeavingGateOfDaily(), reflectStamp.getPcLogOnInfoOfDaily());

			// calculateDailyRecordServiceCenter.errorCheck(integrationList);
		}
		newReflectStampOutput.setErrMesInfos(errMesInfos);
		newReflectStampOutput.setReflectStampOutput(reflectStamp);

		return newReflectStampOutput;
	}
	//打刻反映時間帯を取得する
	public OutputGetTimeStamReflect getTimeStamReflect(String companyID,String employeeID, GeneralDate processingDate,
			WorkInfoOfDailyPerformance workInfoOfDailyPerformance) {
		List<ErrorMessageInfo> errMesInfos = new ArrayList<>();
		StampReflectRangeOutput stampReflectRangeOutput = new StampReflectRangeOutput();
		
		WorkTypeCode workTypeCode = workInfoOfDailyPerformance.getWorkInformation().getRecordInfo().getWorkTypeCode();
		
		WorkTimeCode workTimeCode = workInfoOfDailyPerformance.getWorkInformation().getRecordInfo().getWorkTimeCode();

		// 打刻反映時間帯を取得する - start
		// 打刻反映時の出勤休日扱いチェック
		CheckAttendanceHolidayOutPut attendanceHolidayOutPut = this.checkAttendanceHoliday(employeeID, companyID, workTypeCode.v(), processingDate);
		if (attendanceHolidayOutPut.getErrMesInfos() != null && !attendanceHolidayOutPut.getErrMesInfos().isEmpty()) {
			errMesInfos.addAll(attendanceHolidayOutPut.getErrMesInfos().stream()
					.map(c -> new ErrorMessageInfo(companyID, c.getEmployeeID(), c.getDisposalDay(),
							c.getExecutionContent(), c.getResourceID(), c.getMessageError()))
					.collect(Collectors.toList()));
		}
		boolean isAtWork = attendanceHolidayOutPut.isAtWork();

		// 終了状態：休日扱い - る
		if (isAtWork == false) {
			stampReflectRangeOutput = this.holidayStampRange(companyID, workInfoOfDailyPerformance, processingDate,
					employeeID);
		}
		// 終了状態：出勤扱い - 出勤系の打刻範囲を取得する
		else {
			stampReflectRangeOutput = this.attendanSytemStampRange(workTimeCode, companyID, workInfoOfDailyPerformance);
		}

		// 打刻取得範囲の作成
		createStampReflectRangeInaDay(stampReflectRangeOutput);

		// 外出の打刻反映範囲を取得する
		stampReflectRangeOutput.setGoOut(stampReflectRangeOutput.getStampRange());
		// 臨時の打刻反映範囲を作成する
		stampReflectRangeOutput.setTemporary(stampReflectRangeOutput.getStampRange());
		// - end
		return new OutputGetTimeStamReflect(stampReflectRangeOutput,errMesInfos);
		
	}


	/*
	 * 1日分の打刻反映範囲を取得
	 */
	private StampReflectRangeOutput attendanSytemStampRange(WorkTimeCode workTimeCode, String companyID,
			WorkInfoOfDailyPerformance workInfoOfDailyPerformance) {

		StampReflectRangeOutput stampReflectRangeOutput = new StampReflectRangeOutput();
		if(workTimeCode == null) {
			return stampReflectRangeOutput;
		}
		 // ドメインモデル「就業時間帯の設定」を取得
		Optional<WorkTimeSetting> workTimeSetting = workTimeSettingRepository.findByCode(companyID, workTimeCode.v());
		
		if (workTimeSetting.isPresent()) {

			List<ScheduleTimeSheet> scheduleTimeSheets = workInfoOfDailyPerformance.getWorkInformation().getScheduleTimeSheets();

			// 打刻反映時間帯を取得する
			List<StampReflectTimezone> stampReflectTimezones = this.workTimeSettingService.getStampReflectTimezone(
					companyID, workTimeCode.v(),
					(!scheduleTimeSheets.isEmpty() && scheduleTimeSheets.get(0) != null)
							? scheduleTimeSheets.get(0).getAttendance().valueAsMinutes()
							: null,
					(!scheduleTimeSheets.isEmpty() && scheduleTimeSheets.get(0) != null)
							? scheduleTimeSheets.get(0).getLeaveWork().valueAsMinutes()
							: null,
					(scheduleTimeSheets.size() > 1 && scheduleTimeSheets.get(1) != null)
							? scheduleTimeSheets.get(1).getAttendance().valueAsMinutes()
							: null,
					(scheduleTimeSheets.size() > 1 && scheduleTimeSheets.get(1) != null)
							? scheduleTimeSheets.get(1).getLeaveWork().valueAsMinutes()
							: null);

			if (!stampReflectTimezones.isEmpty()) {
				List<StampReflectTimezoneOutput> stampReflectRangeOutputs = new ArrayList<>();
				stampReflectTimezones.stream().forEach(timezone -> {
					StampReflectTimezoneOutput stampReflectTimezoneOutput = new StampReflectTimezoneOutput(
							timezone.getWorkNo(), timezone.getClassification(), timezone.getEndTime(),
							timezone.getStartTime());
					stampReflectRangeOutputs.add(stampReflectTimezoneOutput);
				});
				stampReflectRangeOutput.setLstStampReflectTimezone(stampReflectRangeOutputs);
			}
			// fake data
			// List<StampReflectTimezoneOutput> lstStampReflectTimezone = new ArrayList<>();
			// StampReflectTimezoneOutput stampReflectTimezoneOutput1 = new
			// StampReflectTimezoneOutput(new WorkNo(1),
			// GoLeavingWorkAtr.GO_WORK, new TimeWithDayAttr(1739), new
			// TimeWithDayAttr(300));
			// StampReflectTimezoneOutput stampReflectTimezoneOutput2 = new
			// StampReflectTimezoneOutput(new WorkNo(1),
			// GoLeavingWorkAtr.LEAVING_WORK, new TimeWithDayAttr(1739), new
			// TimeWithDayAttr(300));
			// lstStampReflectTimezone.add(stampReflectTimezoneOutput1);
			// lstStampReflectTimezone.add(stampReflectTimezoneOutput2);
			// stampReflectRangeOutput.setLstStampReflectTimezone(lstStampReflectTimezone);
		}
		return stampReflectRangeOutput;
	}

	/*
	 * 休日系の打刻範囲を取得する
	 */
	private StampReflectRangeOutput holidayStampRange(String companyID,
			WorkInfoOfDailyPerformance workInfoOfDailyPerformance, GeneralDate processingDate, String employeeId) {

		StampReflectOnHolidayOutPut stampReflectOnHolidayOutPut = new StampReflectOnHolidayOutPut();

		// get workTimeCode
		WorkTimeCode workTimeCode = workInfoOfDailyPerformance.getWorkInformation().getRecordInfo().getWorkTimeCode();

		// 当日の打刻反映範囲を取得 - 当日の就業時間帯コードを取得
		// start get data of this day
		// use workTypeCode
		WorkTypeCode workTypeCode = workInfoOfDailyPerformance.getWorkInformation().getRecordInfo().getWorkTypeCode();
		// 休日出勤時の勤務情報を取得する - new wave
		Optional<SingleDaySchedule> singleDaySchedule = workingConditionItemService
				.getHolidayWorkSchedule(companyID, employeeId, processingDate, workTypeCode.v());
		if (!singleDaySchedule.isPresent()
				|| (singleDaySchedule.isPresent() && !singleDaySchedule.get().getWorkTimeCode().isPresent())) {
			workTimeCode = null;
		} else {
			workTimeCode = new WorkTimeCode(singleDaySchedule.get().getWorkTimeCode().get().v());
		}

		// 当日の打刻反映範囲を取得 - end get data of this day
		StampReflectRangeOutput stampReflectRangeOutput = this.attendanSytemStampRange(workTimeCode, companyID,
				workInfoOfDailyPerformance);
		// set data of this day
		stampReflectOnHolidayOutPut.setStampReflectThisDay(stampReflectRangeOutput);

		// 前々日の打刻反映範囲を取得 - get data of two day before
		GeneralDate twoDayBeforeDate = processingDate.addDays(-2);
		StampReflectRangeOutput stampTwoDay = this.calculationStamp(workInfoOfDailyPerformance, twoDayBeforeDate,
				employeeId, companyID, 1);
		// set data of two day before
		stampReflectOnHolidayOutPut.setStampReflectTwoDayBefore(stampTwoDay);

		// 前日の打刻反映範囲を取得 - get data of previous day
		GeneralDate previousDay = processingDate.addDays(-1);
		StampReflectRangeOutput stampPreviousDay = this.calculationStamp(workInfoOfDailyPerformance, previousDay,
				employeeId, companyID, 2);
		stampReflectOnHolidayOutPut.setStampReflectPreviousDay(stampPreviousDay);

		// 翌日の打刻反映範囲を取得 - get data of next day
		GeneralDate nextDay = processingDate.addDays(1);
		StampReflectRangeOutput stampNextDay = this.calculationStamp(workInfoOfDailyPerformance, nextDay, employeeId,
				companyID, 3);
		stampReflectOnHolidayOutPut.setStampReflectNextDay(stampNextDay);

		if (stampTwoDay != null) {
			// 前々日との関係から打刻反映範囲を補正
			this.stampReflectCorrection(stampReflectOnHolidayOutPut,1);
		}

		if (stampPreviousDay != null) {
			// 前日との関係から打刻範囲範囲を補正
			this.stampReflectCorrection(stampReflectOnHolidayOutPut,2);
		}

		if (stampNextDay != null) {
			// 翌日との関係から打刻反映範囲を補正
			this.nextDayCorrection(stampReflectOnHolidayOutPut);
		}

		return stampReflectOnHolidayOutPut.getStampReflectThisDay();
	}

	/**
	 * 打刻反映時の出勤休日扱いチェック
	 * 
	 * @param workTypeCode
	 * @return
	 */
	private CheckAttendanceHolidayOutPut checkAttendanceHoliday(String employeeID,
			String companyID, String workTypeCode, GeneralDate processingDate) {

		CheckAttendanceHolidayOutPut outPut = new CheckAttendanceHolidayOutPut();

		List<ErrMessageInfo> errMesInfos = new ArrayList<>();

		// ドメインモデル「勤務種類」を取得する
		Optional<WorkType> workTypeOpt = this.workTypeRepository.findByDeprecated(companyID, workTypeCode);

		if (!workTypeOpt.isPresent()) {
			ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeID, "",
					new ErrMessageResource("015"), EnumAdaptor.valueOf(0, ExecutionContent.class), processingDate,
					new ErrMessageContent(TextResource.localize("Msg_590")));
			errMesInfos.add(employmentErrMes);
			outPut.setErrMesInfos(errMesInfos);
			return outPut;
		}

		// 1日半日出勤・1日休日系の判定
		WorkStyle workStyle = basicScheduleService.checkWorkDay(workTypeCode);

		if (workStyle == WorkStyle.ONE_DAY_REST) {
			outPut.setAtWork(false);
		} else {
			outPut.setAtWork(true);
		}

		return outPut;
	}

	/*
	 * function common for calculation for two day before, previous day, next
	 * day
	 */
	private StampReflectRangeOutput calculationStamp(WorkInfoOfDailyPerformance workInfoOfDailyPerformance,
			GeneralDate processingDate, String employeeId, String companyID, int dayAttr) {

		/**
		 * dayAttr 1 : two day before , 2 : previous day 3 : next day
		 */
		Optional<WorkScheduleWorkInforImport> scheduleWorkInfor = workScheduleWorkInforAdapter.get(employeeId, processingDate);
		if(!scheduleWorkInfor.isPresent()) {
			return null;
		}
		// 1日半日出勤・1日休日系の判定
		// 打刻反映時の出勤休日扱いチェック
		WorkStyle workStyle = basicScheduleService
				.checkWorkDay(scheduleWorkInfor.get().getWorkTyle());
		StampReflectRangeOutput stampReflectRangeOutput = null;

		if (workStyle != WorkStyle.ONE_DAY_REST) {
			// get workTimeCode of processingDate
			String worktimeCode = this.stampRangeCheckWorkRecord(processingDate, employeeId);
			if (worktimeCode != null) {
				// 1日分の打刻反映範囲を取得
				stampReflectRangeOutput = this.attendanSytemStampRange(new WorkTimeCode(worktimeCode), companyID,
						workInfoOfDailyPerformance);

				if (stampReflectRangeOutput != null
						&& !stampReflectRangeOutput.getLstStampReflectTimezone().isEmpty()) {
					// loop list 出退勤
					stampReflectRangeOutput.getLstStampReflectTimezone().stream().forEach(timezone -> {
						if (dayAttr == 1) {
							// 開始時刻をパラメータ「ズラす時間」だけズラす
							TimeWithDayAttr startTimeTwoDay = new TimeWithDayAttr(timezone.getStartTime().v() - 2880);
							if (startTimeTwoDay.v() < (-720)) {
								startTimeTwoDay = new TimeWithDayAttr(-720);
							}
							// 終了時刻をパラメータ「ズラす時間」だけズラす
							TimeWithDayAttr endTimeTwoDay = new TimeWithDayAttr(timezone.getEndTime().v() - 2880);
							if (endTimeTwoDay.v() < (-720)) {
								endTimeTwoDay = new TimeWithDayAttr(-720);
							}
							timezone.setStartTime(startTimeTwoDay);
							timezone.setEndTime(endTimeTwoDay);

						} else if (dayAttr == 2) {
							TimeWithDayAttr startTimePreviousDay = new TimeWithDayAttr(
									timezone.getStartTime().v() - 1440);
							if (startTimePreviousDay.v() < (-720)) {
								startTimePreviousDay = new TimeWithDayAttr(-720);
							}
							TimeWithDayAttr endTimePreviousDay = new TimeWithDayAttr(timezone.getEndTime().v() - 1440);
							if (endTimePreviousDay.v() < (-720)) {
								endTimePreviousDay = new TimeWithDayAttr(-720);
							}
							timezone.setStartTime(startTimePreviousDay);
							timezone.setEndTime(endTimePreviousDay);
						} else if (dayAttr == 3) {
							TimeWithDayAttr startTimeNextDay = new TimeWithDayAttr(timezone.getStartTime().v() + 1440);
							if (startTimeNextDay.v() > 4319) {
								startTimeNextDay = new TimeWithDayAttr(4319);
							}
							TimeWithDayAttr endTimeNextDay = new TimeWithDayAttr(timezone.getEndTime().v() + 1440);
							if (endTimeNextDay.v() > 4319) {
								endTimeNextDay = new TimeWithDayAttr(4319);
							}
							timezone.setStartTime(startTimeNextDay);
							timezone.setEndTime(endTimeNextDay);
						}
					});
				}

			}

		}

		return stampReflectRangeOutput;
	}

	/*
	 * 打刻範囲チェック用勤務実績取得
	 */
	public String stampRangeCheckWorkRecord(GeneralDate processingDate, String employeeId) {
		// ドメインモデル「日別実績の勤務情報」を取得
		Optional<WorkInfoOfDailyPerformance> workInfoOfDailyPerformance = this.workInformationRepository
				.find(employeeId, processingDate);

		if (!workInfoOfDailyPerformance.isPresent()) {
			// Imported(就業.勤務実績)「勤務予定基本情報」を取得する
			Optional<WorkScheduleWorkInforImport> scheduleWorkInfor = workScheduleWorkInforAdapter.get(employeeId, processingDate);
			if (scheduleWorkInfor.isPresent()) {
				return scheduleWorkInfor.get().getWorkTyle();
			} else {
				return null;
			}
		} else {
			if (!(workInfoOfDailyPerformance.get().getWorkInformation().getRecordInfo().getWorkTimeCode() == null 
					&& !workInfoOfDailyPerformance.get().getWorkInformation().getRecordInfo().getWorkTimeCodeNotNull().isPresent())) {
				return workInfoOfDailyPerformance.get().getWorkInformation().getRecordInfo().getWorkTimeCode().v();
			} else {
//				if (!(workInfoOfDailyPerformance.get().getWorkInformation().getScheduleInfo().getWorkTimeCode() == null
//						&& !workInfoOfDailyPerformance.get().getWorkInformation().getScheduleInfo().getWorkTimeCodeNotNull().isPresent()
//						)) {
//					return workInfoOfDailyPerformance.get().getWorkInformation().getScheduleInfo().getWorkTimeCode().v();
//				} else {
					return null;
//				}
			}
		}
	}

	/*
	 * Stamp Reflect Correction
	 */
	private void stampReflectCorrection(StampReflectOnHolidayOutPut stampReflectOnHolidayOutPut,int dayAttr ) {

		/**
		 * dayAttr 1 : two day before , 2 : previous day 3 : next day
		 */
		StampReflectRangeOutput stampReflectRangeOutput = dayAttr == 1?stampReflectOnHolidayOutPut.getStampReflectTwoDayBefore():stampReflectOnHolidayOutPut.getStampReflectPreviousDay();
		if (!stampReflectRangeOutput.getLstStampReflectTimezone().isEmpty()) {
			// 前々日の打刻反映範囲から一番遅い時刻を取得
			TimeWithDayAttr lastestTimeGoWork = getLastestTimeFromListStampReflect(GoLeavingWorkAtr.GO_WORK,
					stampReflectRangeOutput);
			// 出勤の打刻反映範囲を補正
			deleteBeforeDesignatedTimeFromStampReflect(GoLeavingWorkAtr.GO_WORK, lastestTimeGoWork,
					stampReflectOnHolidayOutPut.getStampReflectThisDay());
			// 前日の打刻反映範囲から一番遅い時刻を取得
			TimeWithDayAttr lastestTimeLeavingWork = getLastestTimeFromListStampReflect(GoLeavingWorkAtr.LEAVING_WORK,
					stampReflectRangeOutput);
			// 退勤の打刻反映範囲を補正
			deleteBeforeDesignatedTimeFromStampReflect(GoLeavingWorkAtr.LEAVING_WORK, lastestTimeLeavingWork,
					stampReflectOnHolidayOutPut.getStampReflectThisDay());
		}
	};

	/*
	 * next day correction
	 */
	private void nextDayCorrection(StampReflectOnHolidayOutPut stampReflectOnHolidayOutPut) {
		StampReflectRangeOutput stampReflectRangeOutput = stampReflectOnHolidayOutPut.getStampReflectNextDay();
		if (!stampReflectRangeOutput.getLstStampReflectTimezone().isEmpty()) {
			// 翌日の打刻反映範囲から一番早い時刻を取得
			TimeWithDayAttr earliestTimeGoWork = getEarliestTimeFromStampReflect(GoLeavingWorkAtr.GO_WORK,
					stampReflectRangeOutput);

			// 出勤の打刻反映範囲を補正
			deleteAfterDesignatedTimeFromStampReflect(GoLeavingWorkAtr.GO_WORK, earliestTimeGoWork,
					stampReflectOnHolidayOutPut.getStampReflectThisDay());

			// 翌日の打刻反映範囲から一番早い時刻を取得
			TimeWithDayAttr earliestLeavingWork = getEarliestTimeFromStampReflect(GoLeavingWorkAtr.LEAVING_WORK,
					stampReflectRangeOutput);

			// 退勤の打刻反映範囲を補正
			deleteAfterDesignatedTimeFromStampReflect(GoLeavingWorkAtr.LEAVING_WORK, earliestLeavingWork,
					stampReflectOnHolidayOutPut.getStampReflectThisDay());
		}
	}

	/*
	 * 1日の打刻反映範囲を作成
	 */
	private void createStampReflectRangeInaDay(StampReflectRangeOutput stampReflectRangeOutput) {

		// 一番早い時刻を取得
		TimeWithDayAttr earliestTime = getEarliestTimeFromStampReflect(null, stampReflectRangeOutput);

		// 一番遅い時刻を取得
		TimeWithDayAttr lastestTime = getLastestTimeFromListStampReflect(null, stampReflectRangeOutput);

		TimeZoneOutput temporary = new TimeZoneOutput(earliestTime, lastestTime);

		stampReflectRangeOutput.setStampRange(temporary);
	}

	/*
	 * エラーチェック
	 */
	private void errorCheck(String companyID, String employeeID, GeneralDate processingDate,
			WorkInfoOfDailyPerformance workInfoOfDailyPerformance,
			TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance,
			OutingTimeOfDailyPerformance outingTimeOfDailyPerformance,
			TemporaryTimeOfDailyPerformance temporaryTimeOfDailyPerformance,
			BreakTimeOfDailyPerformance breakTimeOfDailyPerformance,
			AttendanceLeavingGateOfDaily attendanceLeavingGateOfDaily, PCLogOnInfoOfDaily pcLogOnInfoOfDaily) {

		// 出勤系打刻漏れをチェックする
		this.lackOfStamping.lackOfStamping(companyID, employeeID, processingDate,
				workInfoOfDailyPerformance,
				timeLeavingOfDailyPerformance);

		// 出勤系打刻順序不正をチェックする
		this.stampIncorrectOrderAlgorithm.stampIncorrectOrder(companyID, employeeID, processingDate,
				timeLeavingOfDailyPerformance);

		// 出勤系二重打刻をチェックする
		this.doubleStampAlgorithm.doubleStamp(companyID, employeeID, processingDate, timeLeavingOfDailyPerformance);

		// TODO: ドメインモデル「臨時勤務管理」を取得する
		// get data from domain : 臨時勤務管理, this domain, now haven't created
		// fake data :
		UseAtr useAtr = UseAtr.USE;
		if (useAtr == UseAtr.USE) {

			// 臨時系打刻漏れをチェックする
			missingOfTemporaryStampChecking.missingOfTemporaryStampChecking(companyID, employeeID, processingDate,
					temporaryTimeOfDailyPerformance);

			// 臨時系打刻順序不正をチェックする
			temporaryStampOrderChecking.temporaryStampOrderChecking(employeeID, companyID, processingDate,
					temporaryTimeOfDailyPerformance);

			// 臨時系二重打刻をチェックする
			temporaryDoubleStampChecking.temporaryDoubleStampChecking(companyID, employeeID, processingDate,
					temporaryTimeOfDailyPerformance);
		}
		// 外出系打刻漏れをチェックする
		goingOutStampLeakageChecking.goingOutStampLeakageChecking(companyID, employeeID, processingDate,
				outingTimeOfDailyPerformance);

		// 外出系打刻順序不正をチェックする
		goingOutStampOrderChecking.goingOutStampOrderChecking(companyID, employeeID, processingDate,
				outingTimeOfDailyPerformance, timeLeavingOfDailyPerformance, temporaryTimeOfDailyPerformance);

		// 休憩系打刻漏れをチェックする
		breakTimeStampLeakageChecking.breakTimeStampLeakageChecking(companyID, employeeID, processingDate,
				breakTimeOfDailyPerformance);

		// 休憩系打刻順序不正をチェックする
		breakTimeStampIncorrectOrderChecking.breakTimeStampIncorrectOrderChecking(companyID, employeeID, processingDate,
				breakTimeOfDailyPerformance);
		UseAtr useAtr2 = UseAtr.USE;
		if (useAtr2 == UseAtr.USE) {
			// 入退門の打刻漏れをチェックする
			exitStampCheck.exitStampCheck(companyID, employeeID, processingDate, attendanceLeavingGateOfDaily,
					workInfoOfDailyPerformance);
			// 入退門の打刻順序不正をチェックする
			exitStampIncorrectOrderCheck.exitStampIncorrectOrderCheck(companyID, employeeID, processingDate,
					attendanceLeavingGateOfDaily, timeLeavingOfDailyPerformance);
		}

		UseAtr useAtr3 = UseAtr.USE;
		if (useAtr3 == UseAtr.USE) {
			// PCログオンログオフの打刻漏れをチェックする
			this.pClogOnOffLackOfStamp.pClogOnOffLackOfStamp(companyID, employeeID, processingDate, pcLogOnInfoOfDaily,
					workInfoOfDailyPerformance);

			// PCログオンログオフの打刻順序不正をチェックする
			this.pCLogOnOffIncorrectOrderCheck.pCLogOnOffIncorrectOrderCheck(companyID, employeeID, processingDate,
					pcLogOnInfoOfDaily, timeLeavingOfDailyPerformance);
		}
	}

	/*
	 * 打刻反映範囲のListから一番早い時刻を取得
	 */
	public TimeWithDayAttr getEarliestTimeFromStampReflect(GoLeavingWorkAtr goLeavingWorkAtr,
			StampReflectRangeOutput stampReflectRangeOutput) {

		List<StampReflectTimezoneOutput> stampReflectTimezoneList = new ArrayList<>();

		if (goLeavingWorkAtr == null) {
			stampReflectTimezoneList = stampReflectRangeOutput.getLstStampReflectTimezone();
		} else {
			stampReflectTimezoneList = stampReflectRangeOutput.getLstStampReflectTimezone().stream()
					.filter(item -> item.getClassification() == goLeavingWorkAtr).collect(Collectors.toList());
		}

		TimeWithDayAttr earliestTime = null;
		for (StampReflectTimezoneOutput stampReflect : stampReflectTimezoneList) {
			if ((earliestTime == null) || stampReflect.getStartTime().lessThan(earliestTime)) {
				earliestTime = new TimeWithDayAttr(stampReflect.getStartTime().v());
			}
		}
		;

		return earliestTime;
	}

	/*
	 * 打刻反映範囲から指定時刻以降を削除
	 */
	public void deleteAfterDesignatedTimeFromStampReflect(GoLeavingWorkAtr goLeavingWorkAtr,
			TimeWithDayAttr earliestTime, StampReflectRangeOutput stampReflectRangeOutput) {
		List<StampReflectTimezoneOutput> list = stampReflectRangeOutput.getLstStampReflectTimezone().stream()
				.filter(item -> item.getClassification() == goLeavingWorkAtr).collect(Collectors.toList());

		for (StampReflectTimezoneOutput timeZone : list) {
			if (timeZone.getStartTime().lessThan(earliestTime) && timeZone.getEndTime().greaterThan(earliestTime)) {
				timeZone.setEndTime(new TimeWithDayAttr(earliestTime.v() - 1));
			} else if (timeZone.getStartTime().greaterThan(earliestTime)) {
				stampReflectRangeOutput = new StampReflectRangeOutput();
			}
		}
	}

	/*
	 * 打刻反映範囲のListから一番遅い時刻を取得
	 */
	public TimeWithDayAttr getLastestTimeFromListStampReflect(GoLeavingWorkAtr goLeavingWorkAtr,
			StampReflectRangeOutput stampReflectRangeOutput) {
		List<StampReflectTimezoneOutput> stampReflectTimezoneList = new ArrayList<>();
		if (goLeavingWorkAtr == null) {
			stampReflectTimezoneList = stampReflectRangeOutput.getLstStampReflectTimezone();
		} else {
			stampReflectTimezoneList = stampReflectRangeOutput.getLstStampReflectTimezone().stream()
					.filter(item -> item.getClassification() == goLeavingWorkAtr).collect(Collectors.toList());
		}

		TimeWithDayAttr lastestTime = null;
		for (StampReflectTimezoneOutput stampReflect : stampReflectTimezoneList) {
			if ((lastestTime == null) || stampReflect.getEndTime().greaterThan(lastestTime)) {
				lastestTime = new TimeWithDayAttr(stampReflect.getEndTime().v());
			}
		}
		;

		return lastestTime;
	}

	/*
	 * 打刻反映範囲から指定時刻以前を削除
	 */
	public void deleteBeforeDesignatedTimeFromStampReflect(GoLeavingWorkAtr goLeavingWorkAtr,
			TimeWithDayAttr lastestTime, StampReflectRangeOutput stampReflectRangeOutput) {

		List<StampReflectTimezoneOutput> list = stampReflectRangeOutput.getLstStampReflectTimezone().stream()
				.filter(item -> item.getClassification() == goLeavingWorkAtr).collect(Collectors.toList());

		for (StampReflectTimezoneOutput timeZone : list) {
			if (timeZone.getStartTime().lessThan(lastestTime) && timeZone.getEndTime().greaterThan(lastestTime)) {
				timeZone.setStartTime(new TimeWithDayAttr(lastestTime.v() + 1));
			} else if (timeZone.getEndTime().lessThan(lastestTime)) {
				stampReflectRangeOutput = new StampReflectRangeOutput();
			}
		}
	}

	@Override
	public NewReflectStampOutput acquireReflectEmbossing(String companyId, String employeeId,
			GeneralDate processingDate, Optional<WorkInfoOfDailyPerformance> workInfoOfDailyPerformanceOpt,
			TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance, String empCalAndSumExecLogID,
			Optional<CalAttrOfDailyPerformance> calcOfDailyOpt,
			Optional<AffiliationInforOfDailyPerfor> affInfoOfDailyOpt,
        	RecreateFlag recreateFlag) {
		NewReflectStampOutput newReflectStampOutput = new NewReflectStampOutput();
		List<ErrMessageInfo> errMesInfos = new ArrayList<>();
		ReflectStampOutput reflectStamp = new ReflectStampOutput();
		// パラメータを確認
		if (!workInfoOfDailyPerformanceOpt.isPresent()) {
			workInfoOfDailyPerformanceOpt = Optional
					.ofNullable(this.workInformationRepository.find(employeeId, processingDate).get());
		}
		if (!affInfoOfDailyOpt.isPresent()) {
			affInfoOfDailyOpt = Optional
					.ofNullable(this.affiliationInforOfDailyPerforRepository.findByKey(employeeId, processingDate).get());
		}
		if (!calcOfDailyOpt.isPresent()) {
			calcOfDailyOpt = Optional
					.ofNullable(this.calAttrOfDailyPerformanceRepository.find(employeeId, processingDate));
		}
		// ドメインモデル「日別実績の勤務情報」を取得
		// Optional<WorkInfoOfDailyPerformance> workInfoOfDailyPerformance =
		// this.workInformationRepository
		// .find(employeeId, processingDate);
		String workTimeCode = null;
		String workTypeCode = null;
		WorkTimeCode workTimeCodeDefault = workInfoOfDailyPerformanceOpt.get().getWorkInformation().getRecordInfo().getWorkTimeCode();
		
		if(workInfoOfDailyPerformanceOpt.isPresent() && workInfoOfDailyPerformanceOpt.get().getWorkInformation().getRecordInfo() != null){
			if(workInfoOfDailyPerformanceOpt.get().getWorkInformation().getRecordInfo().getWorkTypeCode() != null){
				workTypeCode = workInfoOfDailyPerformanceOpt.get().getWorkInformation().getRecordInfo().getWorkTypeCode().v();
			}
			if(workInfoOfDailyPerformanceOpt.get().getWorkInformation().getRecordInfo().getWorkTimeCode() != null){
				workTimeCode = workInfoOfDailyPerformanceOpt.get().getWorkInformation().getRecordInfo().getWorkTimeCode().v();
			}
		}
		
		//ドメインモデル「勤務種類」を取得
		boolean checkWorkType = this.workTypeRepository.findWorkTypeRecord(companyId, workTypeCode);
		if(checkWorkType == false){
			//エラー処理
			ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeId, empCalAndSumExecLogID,
					new ErrMessageResource("023"), EnumAdaptor.valueOf(0, ExecutionContent.class), processingDate,
					new ErrMessageContent(TextResource.localize("Msg_590")));
			errMesInfos.add(employmentErrMes);
			newReflectStampOutput.setErrMesInfos(errMesInfos);
			return newReflectStampOutput;
		}
		
		// 就業時間帯コードを取得
		if (workTimeCode == null && checkWorkType == true) {
			// 休日出勤時の勤務情報を取得する
			Optional<SingleDaySchedule> singleDaySchedule = workingConditionItemService
					.getHolidayWorkSchedule(companyId, employeeId, processingDate, workTypeCode);
			if (!singleDaySchedule.isPresent()) {
				ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeId, empCalAndSumExecLogID,
						new ErrMessageResource("016"), EnumAdaptor.valueOf(0, ExecutionContent.class), processingDate,
						new ErrMessageContent(TextResource.localize("Msg_591")));
				errMesInfos.add(employmentErrMes);
				newReflectStampOutput.setErrMesInfos(errMesInfos);
				return newReflectStampOutput;
			}

			workTimeCode = singleDaySchedule.get().getWorkTimeCode().isPresent() ? singleDaySchedule.get().getWorkTimeCode().get().v() : null;
		}
		//update workTime when work time change
		workInfoOfDailyPerformanceOpt.get().getWorkInformation().getRecordInfo().setWorkTimeCode(new WorkTimeCode(workTimeCode));
		// ドメインモデル「就業時間帯の設定」を取得する
		Optional<WorkTimeSetting> workTimeOpt = this.workTimeSettingRepository.findByCodeAndAbolishCondition(companyId,
				workTimeCode, AbolishAtr.NOT_ABOLISH);
		if (!workTimeOpt.isPresent()) {
			ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeId, empCalAndSumExecLogID,
					new ErrMessageResource("016"), EnumAdaptor.valueOf(0, ExecutionContent.class), processingDate,
					new ErrMessageContent(TextResource.localize("Msg_591")));
			errMesInfos.add(employmentErrMes);
			newReflectStampOutput.setErrMesInfos(errMesInfos);
			return newReflectStampOutput;
		}

		StampReflectRangeOutput stampReflectRangeOutput = new StampReflectRangeOutput();
		// 打刻反映時間帯を取得する - start
		// 打刻反映時の出勤休日扱いチェック
		CheckAttendanceHolidayOutPut attendanceHolidayOutPut = this.checkAttendanceHoliday(employeeId,
				companyId, workTypeCode, processingDate);
		if (attendanceHolidayOutPut.getErrMesInfos() != null && !attendanceHolidayOutPut.getErrMesInfos().isEmpty()) {
			errMesInfos.addAll(attendanceHolidayOutPut.getErrMesInfos());
			newReflectStampOutput.setErrMesInfos(errMesInfos);
			return newReflectStampOutput;
		}
		boolean isAtWork = attendanceHolidayOutPut.isAtWork();

		// 終了状態：休日扱い - 休日系の打刻範囲を取得する 
		if (isAtWork == false) {
			stampReflectRangeOutput = this.holidayStampRange(companyId, workInfoOfDailyPerformanceOpt.get(),
					processingDate, employeeId);
		}
		// 終了状態：出勤扱い - 出勤系の打刻範囲を取得する
		else {
			stampReflectRangeOutput = this.attendanSytemStampRange(new WorkTimeCode(workTimeCode), companyId,
					workInfoOfDailyPerformanceOpt.get());
		}

		// 打刻取得範囲の作成
		createStampReflectRangeInaDay(stampReflectRangeOutput);

		// 外出の打刻反映範囲を取得する
		stampReflectRangeOutput.setGoOut(stampReflectRangeOutput.getStampRange());
		// 臨時の打刻反映範囲を作成する
		stampReflectRangeOutput.setTemporary(stampReflectRangeOutput.getStampRange());
		// - end

		// 打刻を反映する - Dung code
        List<Stamp> lstStamp = this.stampDomainService.handleData(stampReflectRangeOutput,
                empCalAndSumExecLogID, processingDate, employeeId, companyId,recreateFlag);
		if (lstStamp == null) {
			reflectStamp = null;
		}
		TimeLeavingOfDailyPerformance performance = new TimeLeavingOfDailyPerformance(employeeId, processingDate, timeLeavingOfDailyPerformance.getAttendance());
		// lstStampItem is null -> has error
		if (lstStamp != null) {

			if (!lstStamp.isEmpty()) {
				reflectStamp = this.reflectEmbossingDomainService.reflectStamp(workInfoOfDailyPerformanceOpt.get(),
						timeLeavingOfDailyPerformance, lstStamp, stampReflectRangeOutput, processingDate,
						employeeId, companyId);
			} else {
				reflectStamp.setTimeLeavingOfDailyPerformance(performance);
			}
			//set worktime default  
			workInfoOfDailyPerformanceOpt.get().getWorkInformation().getRecordInfo().setWorkTimeCode(workTimeCodeDefault);
			// 就業時間帯の休憩時間帯を日別実績に写す
			// 就業時間帯の休憩時間帯を日別実績に反映する
			BreakTimeOfDailyPerformance breakTimeOfDailyPerformance = this.reflectBreakTimeOfDailyDomainService
					.reflectBreakTimeZone(companyId, employeeId, processingDate, empCalAndSumExecLogID,
							timeLeavingOfDailyPerformance, workInfoOfDailyPerformanceOpt.get());
			reflectStamp.setBreakTimeOfDailyPerformance(breakTimeOfDailyPerformance);

			// 短時間勤務時間帯を反映する
			ReflectShortWorkingOutPut outPut = this.reflectShortWorkingTimeDomainService.reflect(empCalAndSumExecLogID,
					companyId, processingDate, employeeId, workInfoOfDailyPerformanceOpt.get().getWorkInformation(),
					timeLeavingOfDailyPerformance.getAttendance());
			if (outPut.getErrMesInfos() != null && !outPut.getErrMesInfos().isEmpty()) {
				errMesInfos.addAll(outPut.getErrMesInfos());
				newReflectStampOutput.setErrMesInfos(errMesInfos);
				return newReflectStampOutput;
			}
			ShortTimeOfDailyPerformance dailyPerformances = new ShortTimeOfDailyPerformance(employeeId, processingDate, outPut.getShortTimeOfDailyPerformance());
			reflectStamp.setShortTimeOfDailyPerformance(dailyPerformances);
			Optional<WorkInfoOfDailyPerformance> optional = Optional.ofNullable(new WorkInfoOfDailyPerformance(employeeId, processingDate, workInfoOfDailyPerformanceOpt.get().getWorkInformation()));
			// エラーチェック
			this.errorCheck(companyId, employeeId, processingDate, optional.get(),
					reflectStamp.getTimeLeavingOfDailyPerformance(), reflectStamp.getOutingTimeOfDailyPerformance(),
					reflectStamp.getTemporaryTimeOfDailyPerformance(), breakTimeOfDailyPerformance,
					reflectStamp.getAttendanceLeavingGateOfDaily(), reflectStamp.getPcLogOnInfoOfDaily());

			// calculateDailyRecordServiceCenter.errorCheck(integrationList);
		}
		newReflectStampOutput.setErrMesInfos(errMesInfos);
		newReflectStampOutput.setReflectStampOutput(reflectStamp);

		return newReflectStampOutput;
	}
	

	@Override
	public OutputAcquireReflectEmbossingNew acquireReflectEmbossingNew(String companyID, String employeeID,
			GeneralDate processingDate, ExecutionTypeDaily executionType, EmbossingExecutionFlag flag,
			IntegrationOfDaily integrationOfDaily, ChangeDailyAttendance changeDailyAtt) {
		List<ErrorMessageInfo> listErrorMessageInfo = new ArrayList<>();
		
		//勤務情報から打刻反映時間帯を取得する
		OutputTimeReflectForWorkinfo outputTimeReflectForWorkinfo = timeReflectFromWorkinfo.get(companyID, employeeID, processingDate,
				integrationOfDaily.getWorkInformation());
		
		switch(outputTimeReflectForWorkinfo.getEndStatus()) {
		case NO_WORK_TIME:
			listErrorMessageInfo.add(new ErrorMessageInfo(companyID, employeeID, processingDate, ExecutionContent.DAILY_CREATION,
					new ErrMessageResource("022"), new ErrMessageContent(TextResource.localize("Msg_591"))));
			break;
		case NO_WORK_TYPE :
			listErrorMessageInfo.add(new ErrorMessageInfo(companyID, employeeID, processingDate, ExecutionContent.DAILY_CREATION,
					new ErrMessageResource("023"), new ErrMessageContent(TextResource.localize("Msg_590"))));
			break;
		case NO_HOLIDAY_SETTING:
			listErrorMessageInfo.add(new ErrorMessageInfo(companyID, employeeID, processingDate, ExecutionContent.DAILY_CREATION,
					new ErrMessageResource("023"), new ErrMessageContent(TextResource.localize("Msg_1678"))));
			break;
		case NO_WORK_CONDITION:
			listErrorMessageInfo.add(new ErrorMessageInfo(companyID, employeeID, processingDate, ExecutionContent.DAILY_CREATION,
					new ErrMessageResource("023"), new ErrMessageContent(TextResource.localize("Msg_430"))));
			break;
		default: //NORMAL
		}
		if(!listErrorMessageInfo.isEmpty()) {
			return new OutputAcquireReflectEmbossingNew(listErrorMessageInfo, new ArrayList<>(), integrationOfDaily, changeDailyAtt);
		}
		OutputGetTimeStamReflect outputGetTimeStamReflect = new OutputGetTimeStamReflect();
		WorkInfoOfDailyPerformance workInfoOfDailyPerformanceNew = new WorkInfoOfDailyPerformance(employeeID,
				processingDate, integrationOfDaily.getWorkInformation());  
		//打刻反映時間帯を取得する
		if (outputTimeReflectForWorkinfo.getEndStatus() == EndStatus.NORMAL) {
			outputGetTimeStamReflect = this.getTimeStamReflect(companyID, employeeID,
					processingDate, workInfoOfDailyPerformanceNew);
			if(outputGetTimeStamReflect.getError().isEmpty()) {
				outputTimeReflectForWorkinfo.setStampReflectRangeOutput(outputGetTimeStamReflect.getStampReflectRangeOutput());
			}
			listErrorMessageInfo.addAll(outputGetTimeStamReflect.getError());
		}
		
		if(outputGetTimeStamReflect.getStampReflectRangeOutput().getStampRange().getStart() == null ||
				outputGetTimeStamReflect.getStampReflectRangeOutput().getStampRange().getEnd() == null) {
			listErrorMessageInfo.add(new ErrorMessageInfo(companyID, employeeID, processingDate, ExecutionContent.DAILY_CREATION,
					new ErrMessageResource("022"), new ErrMessageContent(TextResource.localize("Msg_591"))));
			return new OutputAcquireReflectEmbossingNew(listErrorMessageInfo, new ArrayList<>(), integrationOfDaily, changeDailyAtt);
		}
		
		//打刻を取得する
		List<Stamp> lstStamp = this.stampDomainService.handleDataNew(outputGetTimeStamReflect.getStampReflectRangeOutput(),
                processingDate, employeeID, companyID,flag);
		if(lstStamp.isEmpty()) {
			//取得した打刻の件数　＜＝　0
			return new OutputAcquireReflectEmbossingNew(listErrorMessageInfo, new ArrayList<>(), integrationOfDaily, changeDailyAtt);
		}
		
		//日別実績のコンバーターを作成する
		//日別実績のデータをコンバーターに入れる
		DailyRecordToAttendanceItemConverter converter = dailyRecordConverter.createDailyConverter().setData(integrationOfDaily).completed();
		
		//打刻を反映する (TKT)
		for(Stamp stamp:lstStamp) {
			listErrorMessageInfo.addAll(temporarilyReflectStampDailyAttd.reflectStamp(stamp,
					outputGetTimeStamReflect.getStampReflectRangeOutput(), integrationOfDaily, changeDailyAtt));
		}
		//手修正がある勤怠項目ID一覧を取得する
		List<Integer> attendanceItemIdList = integrationOfDaily.getEditState().stream()
				.filter(c -> c.getEditStateSetting() != EditStateSetting.REFLECT_APPLICATION)
				.map(editState -> editState.getAttendanceItemId()).distinct().collect(Collectors.toList());
		List<ItemValue> listItemValue = converter.convert(attendanceItemIdList);
		// 手修正項目のデータを元に戻す
		if(!attendanceItemIdList.isEmpty()) {
			integrationOfDaily = createDailyResults.restoreData(converter, integrationOfDaily, listItemValue);
		}
		// エラーチェック
		integrationOfDaily = calculationErrorCheckService.errorCheck(companyID, employeeID, processingDate, integrationOfDaily, true);
		
		return new OutputAcquireReflectEmbossingNew(listErrorMessageInfo,lstStamp,integrationOfDaily, changeDailyAtt);
	}
	
	
}
