package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.reflect;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.workschedule.WorkScheduleWorkInforAdapter;
import nts.uk.ctx.at.record.dom.adapter.workschedule.WorkScheduleWorkInforImport;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.CheckAttendanceHolidayOutPut;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.OutputGetTimeStamReflect;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.ErrMessageResource;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
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
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingService;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 出退勤で応援データ補正する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.日別実績処理.作成処理.一日の日別実績の作成処理（New）.打刻反映.打刻応援反映する.出退勤で応援データ補正する.出退勤で応援データ補正する
 * @author phongtq
 *
 */
@Stateless
public class CorrectSupportDataWork {
	
	@Inject
	private TimeReflectFromWorkinfo fromWorkinfo;
	
	@Inject
	private SupportWorkReflection workReflection;
	
	@Inject
	private WorkInformationRepository workInformationRepository;
	
	@Inject
	private BasicScheduleService basicScheduleService;

	@Inject
	private WorkingConditionItemService workingConditionItemService;
	
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;

	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private WorkTimeSettingService workTimeSettingService;
	
	@Inject
	private WorkScheduleWorkInforAdapter workScheduleWorkInforAdapter;
	
	public SupportDataWork correctSupportDataWork(IntegrationOfDaily integrationOfDaily) {
		String companyId = AppContexts.user().companyId();
		
		// 勤務情報から打刻反映時間帯を取得する
		// output : 終了状態, 打刻反映範囲
		OutputTimeReflectForWorkinfo workinfo = fromWorkinfo.get(companyId, integrationOfDaily.getEmployeeId(), integrationOfDaily.getYmd(), integrationOfDaily.getWorkInformation());
		
		// 終了状態が正常以外の場合
		if(workinfo.getEndStatus() != EndStatus.NORMAL) {
			switch(workinfo.getEndStatus()) {
			// 就業時間帯なしの場合：「Msg_591」
			case NO_WORK_TIME: 
				return new SupportDataWork(null, "Msg_591");
			// 勤務種類なしの場合：「Msg_590」
			case NO_WORK_TYPE: 
				return new SupportDataWork(null, "Msg_590");
			// 休日出勤設定なしの場合：「Msg_1678」
			case NO_HOLIDAY_SETTING: 
				return new SupportDataWork(null, "Msg_1678");
			// 労働条件なしの場合：「Msg_430」
			case NO_WORK_CONDITION: 
				return new SupportDataWork(null, "Msg_430");
			default:
				break;
			}
		} else {
			// set StampReflectRangeOutput
			OutputGetTimeStamReflect outputGetTimeStamReflect = new OutputGetTimeStamReflect();
			WorkInfoOfDailyPerformance workInfoOfDailyPerformanceNew = new WorkInfoOfDailyPerformance(integrationOfDaily.getEmployeeId(),
					integrationOfDaily.getYmd(), integrationOfDaily.getWorkInformation()); 
			
			outputGetTimeStamReflect = this.getTimeStamReflect(companyId, integrationOfDaily.getEmployeeId(),
					integrationOfDaily.getYmd(), workInfoOfDailyPerformanceNew);
			if(outputGetTimeStamReflect.getError().isEmpty()) {
				workinfo.setStampReflectRangeOutput(outputGetTimeStamReflect.getStampReflectRangeOutput());
			}
			
			//終了状態は正常の場合
			// 1回目の出退勤を取得する
			// 日別勤怠（Work）。出退勤。時間帯（勤務枠No１）
			Optional<TimeLeavingWork> leavingWork = integrationOfDaily.getAttendanceLeave().get().getTimeLeavingWorks().stream().filter(x -> x.getWorkNo().v() == 1).findFirst();
			// 取得できる
			if(leavingWork.isPresent()) {
				ReflectionAtr reflectionAtr = this.supportCorrectWork(leavingWork, workinfo.getStampReflectRangeOutput(), integrationOfDaily);
				// 反映状態を確認する - 反映済みの場合
				if(reflectionAtr == ReflectionAtr.REFLECTED) {
					return new SupportDataWork(integrationOfDaily, null);
				}
			}
			
			// 取得できない
			// 反映状態を確認する - 未反映の場合
			// 2回目の出退勤を取得する
			Optional<TimeLeavingWork> leavingWork2 = integrationOfDaily.getAttendanceLeave().get().getTimeLeavingWorks().stream().filter(x -> x.getWorkNo().v() == 2).findFirst();
			if(leavingWork2.isPresent()) {
				this.supportCorrectWork(leavingWork, workinfo.getStampReflectRangeOutput(), integrationOfDaily);
			}
			
		}
		return new SupportDataWork(integrationOfDaily, null);
	}
	
	// 出退勤で応援補正する
	public ReflectionAtr supportCorrectWork(Optional<TimeLeavingWork> leavingWork,
			StampReflectRangeOutput stampReflectRangeOutput, IntegrationOfDaily integrationOfDaily) {
		// 出退勤の出勤を確認する
		// Nullじゃない場合
		CacheCarrier carrier = new CacheCarrier();
		if (leavingWork.get().getAttendanceStamp().isPresent()) {
			// 応援作業反映
			SupportParam param = new SupportParam(true, integrationOfDaily, stampReflectRangeOutput,
					StartAtr.START_OF_SUPPORT,
					leavingWork.get().getAttendanceStamp().get().getStamp().get().getTimeDay(), Optional.empty(),
					Optional.empty());
			workReflection.supportWorkReflect(param, carrier);
			// 「反映状態＝反映済み」を返す
			return ReflectionAtr.REFLECTED;
			// Nullの場合
		} else {
			// 出退勤の退勤を確認する
			// Nullじゃない場合
			if (leavingWork.get().getLeaveStamp().isPresent()) {
				// 応援作業反映
				SupportParam param = new SupportParam(true, integrationOfDaily, stampReflectRangeOutput,
						StartAtr.END_OF_SUPPORT, leavingWork.get().getLeaveStamp().get().getStamp().get().getTimeDay(),
						Optional.empty(), Optional.empty()); // TODO
				workReflection.supportWorkReflect(param, carrier);
				// 「反映状態＝反映済み」を返す
				return ReflectionAtr.REFLECTED;
				// Nullの場合
			} else {
				return ReflectionAtr.NOTREFLECTED;
			}
		}
	}

	// 打刻反映時間帯を取得する
	public OutputGetTimeStamReflect getTimeStamReflect(String companyID, String employeeID, GeneralDate processingDate,
			WorkInfoOfDailyPerformance workInfoOfDailyPerformance) {
		List<ErrorMessageInfo> errMesInfos = new ArrayList<>();
		StampReflectRangeOutput stampReflectRangeOutput = new StampReflectRangeOutput();

		WorkTypeCode workTypeCode = workInfoOfDailyPerformance.getWorkInformation().getRecordInfo().getWorkTypeCode();

		WorkTimeCode workTimeCode = workInfoOfDailyPerformance.getWorkInformation().getRecordInfo().getWorkTimeCode();

		// 打刻反映時間帯を取得する - start
		// 打刻反映時の出勤休日扱いチェック
		CheckAttendanceHolidayOutPut attendanceHolidayOutPut = this.checkAttendanceHoliday(employeeID, companyID,
				workTypeCode.v(), processingDate);
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
		return new OutputGetTimeStamReflect(stampReflectRangeOutput, errMesInfos);

	}

	/*
	 * 1日分の打刻反映範囲を取得
	 */
	private StampReflectRangeOutput attendanSytemStampRange(WorkTimeCode workTimeCode, String companyID,
			WorkInfoOfDailyPerformance workInfoOfDailyPerformance) {

		StampReflectRangeOutput stampReflectRangeOutput = new StampReflectRangeOutput();
		if (workTimeCode == null) {
			return stampReflectRangeOutput;
		}
		// ドメインモデル「就業時間帯の設定」を取得
		Optional<WorkTimeSetting> workTimeSetting = workTimeSettingRepository.findByCode(companyID, workTimeCode.v());

		if (workTimeSetting.isPresent()) {

			List<ScheduleTimeSheet> scheduleTimeSheets = workInfoOfDailyPerformance.getWorkInformation()
					.getScheduleTimeSheets();

			// 打刻反映時間帯を取得する
			List<StampReflectTimezone> stampReflectTimezones = this.workTimeSettingService.getStampReflectTimezone(
					companyID, workTimeCode.v(),
					(!scheduleTimeSheets.isEmpty() && scheduleTimeSheets.get(0) != null)
							? scheduleTimeSheets.get(0).getAttendance().valueAsMinutes() : null,
					(!scheduleTimeSheets.isEmpty() && scheduleTimeSheets.get(0) != null)
							? scheduleTimeSheets.get(0).getLeaveWork().valueAsMinutes() : null,
					(scheduleTimeSheets.size() > 1 && scheduleTimeSheets.get(1) != null)
							? scheduleTimeSheets.get(1).getAttendance().valueAsMinutes() : null,
					(scheduleTimeSheets.size() > 1 && scheduleTimeSheets.get(1) != null)
							? scheduleTimeSheets.get(1).getLeaveWork().valueAsMinutes() : null);

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
			// List<StampReflectTimezoneOutput> lstStampReflectTimezone = new
			// ArrayList<>();
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
		Optional<SingleDaySchedule> singleDaySchedule = workingConditionItemService.getHolidayWorkSchedule(companyID,
				employeeId, processingDate, workTypeCode.v());
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
			this.stampReflectCorrection(stampReflectOnHolidayOutPut, 1);
		}

		if (stampPreviousDay != null) {
			// 前日との関係から打刻範囲範囲を補正
			this.stampReflectCorrection(stampReflectOnHolidayOutPut, 2);
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
	private CheckAttendanceHolidayOutPut checkAttendanceHoliday(String employeeID, String companyID,
			String workTypeCode, GeneralDate processingDate) {

		CheckAttendanceHolidayOutPut outPut = new CheckAttendanceHolidayOutPut();

		List<ErrMessageInfo> errMesInfos = new ArrayList<>();

		// ドメインモデル「勤務種類」を取得する
		Optional<WorkType> workTypeOpt = this.workTypeRepository.findByDeprecated(companyID, workTypeCode);

		if (!workTypeOpt.isPresent()) {
			ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeID, "", new ErrMessageResource("015"),
					EnumAdaptor.valueOf(0, ExecutionContent.class), processingDate,
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
		Optional<WorkScheduleWorkInforImport> scheduleWorkInfor = workScheduleWorkInforAdapter.get(employeeId,
				processingDate);
		if (!scheduleWorkInfor.isPresent()) {
			return null;
		}
		// 1日半日出勤・1日休日系の判定
		// 打刻反映時の出勤休日扱いチェック
		WorkStyle workStyle = basicScheduleService.checkWorkDay(scheduleWorkInfor.get().getWorkTyle());
		StampReflectRangeOutput stampReflectRangeOutput = null;

		if (workStyle != WorkStyle.ONE_DAY_REST) {
			Optional<WorkInfoOfDailyPerformance> workInfoOfDailyPerformanceNew = this.workInformationRepository
					.find(employeeId, processingDate);
			// get workTimeCode of processingDate
			String worktimeCode = this.stampRangeCheckWorkRecord(processingDate, employeeId);
			if (workInfoOfDailyPerformanceNew.isPresent() && worktimeCode != null) {
				// 1日分の打刻反映範囲を取得
				stampReflectRangeOutput = this.attendanSytemStampRange(new WorkTimeCode(worktimeCode), companyID,
						workInfoOfDailyPerformanceNew.get());

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

		if (workInfoOfDailyPerformance.isPresent()) {
			if (!(workInfoOfDailyPerformance.get().getWorkInformation().getRecordInfo().getWorkTimeCode() == null
					&& !workInfoOfDailyPerformance.get().getWorkInformation().getRecordInfo().getWorkTimeCodeNotNull()
							.isPresent())) {
				return workInfoOfDailyPerformance.get().getWorkInformation().getRecordInfo().getWorkTimeCode().v();
			}
		}
		// Imported(就業.勤務実績)「勤務予定基本情報」を取得する
		Optional<WorkScheduleWorkInforImport> scheduleWorkInfor = workScheduleWorkInforAdapter.get(employeeId,
				processingDate);
		if (scheduleWorkInfor.isPresent()) {
			return scheduleWorkInfor.get().getWorkTyle();
		}
		return null;
	}

	/*
	 * Stamp Reflect Correction
	 */
	private void stampReflectCorrection(StampReflectOnHolidayOutPut stampReflectOnHolidayOutPut, int dayAttr) {

		/**
		 * dayAttr 1 : two day before , 2 : previous day 3 : next day
		 */
		StampReflectRangeOutput stampReflectRangeOutput = dayAttr == 1
				? stampReflectOnHolidayOutPut.getStampReflectTwoDayBefore()
				: stampReflectOnHolidayOutPut.getStampReflectPreviousDay();
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
}
