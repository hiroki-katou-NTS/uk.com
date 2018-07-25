/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.executionlog.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.app.command.executionlog.ScheduleCreatorExecutionCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.BasicScheduleSaveCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.ChildCareScheduleSaveCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.WorkScheduleBreakSaveCommand;
import nts.uk.ctx.at.schedule.dom.adapter.ScTimeAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.ScTimeImport;
import nts.uk.ctx.at.schedule.dom.adapter.ScTimeParam;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.ShortChildCareFrameDto;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.ShortWorkTimeDto;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.EmployeeGeneralInfoImported;
import nts.uk.ctx.at.schedule.dom.schedule.algorithm.BusinessDayCal;
import nts.uk.ctx.at.schedule.dom.schedule.algorithm.CreScheWithBusinessDayCalService;
import nts.uk.ctx.at.schedule.dom.schedule.algorithm.WorkRestTimeZoneDto;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.ConfirmedAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletime.WorkScheduleTime;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.BounceAtr;
import nts.uk.ctx.at.schedule.dom.schedule.commonalgorithm.ScheduleMasterInformationDto;
import nts.uk.ctx.at.schedule.dom.schedule.commonalgorithm.ScheduleMasterInformationService;
import nts.uk.ctx.at.schedule.dom.schedule.schedulemaster.ScheMasterInfo;
import nts.uk.ctx.at.shared.app.command.worktime.predset.dto.PrescribedTimezoneSettingDto;
import nts.uk.ctx.at.shared.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmpDto;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSetCheck;

/**
 * The Class ScheCreExeBasicScheduleHandler.
 */
@Stateless
public class ScheCreExeBasicScheduleHandler {

	/** The basic schedule repository. */
	@Inject
	private BasicScheduleRepository basicScheduleRepository;

	/** The sche cre exe error log handler. */
	@Inject
	private ScheCreExeErrorLogHandler scheCreExeErrorLogHandler;

	/** The sche cre exe work time handler. */
	@Inject
	private ScheCreExeWorkTimeHandler scheCreExeWorkTimeHandler;

	/** The work type repository. */
	@Inject
	private WorkTypeRepository workTypeRepository;

	@Inject
	private CreScheWithBusinessDayCalService scheWithBusinessDayCalService;

	@Inject
	private ScTimeAdapter scTimeAdapter;

	@Inject
	private ScheduleMasterInformationService scheduleMasterInformationService;

	/** The Constant DEFAULT_VALUE. */
	private static final int DEFAULT_VALUE = 0;

	/**
	 * Update all data to command save.
	 *
	 * @param command
	 * @param employeeId
	 * @param worktypeDto
	 * @param workTimeCode
	 * @param empGeneralInfo
	 * @param listWorkType
	 * @param listWorkTimeSetting
	 * @param listBusTypeOfEmpHis
	 * @param allData
	 * @param listFixedWorkSetting
	 * @param listFlowWorkSetting
	 * @param listDiffTimeWorkSetting
	 */
	public void updateAllDataToCommandSave(ScheduleCreatorExecutionCommand command, GeneralDate dateInPeriod,
			String employeeId, WorktypeDto worktypeDto, String workTimeCode, EmployeeGeneralInfoImported empGeneralInfo,
			List<WorkType> listWorkType, List<WorkTimeSetting> listWorkTimeSetting,
			List<BusinessTypeOfEmpDto> listBusTypeOfEmpHis, List<BasicSchedule> allData,
			Map<String, WorkRestTimeZoneDto> mapFixedWorkSetting, Map<String, WorkRestTimeZoneDto> mapFlowWorkSetting,
			Map<String, WorkRestTimeZoneDto> mapDiffTimeWorkSetting, List<ShortWorkTimeDto> listShortWorkTimeDto) {

		// 「社員の短時間勤務一覧」からパラメータ.社員ID、対象日をもとに該当する短時間勤務を取得する
		// EA修正履歴：No2135
		// EA修正履歴：No2136
		Optional<ShortWorkTimeDto> optionalShortTime = listShortWorkTimeDto.stream()
				.filter(x -> (x.getEmployeeId().equals(employeeId) && x.getPeriod().contains(dateInPeriod)))
				.findFirst();

		// add command save
		BasicScheduleSaveCommand commandSave = new BasicScheduleSaveCommand();
		commandSave.setWorktypeCode(worktypeDto.getWorktypeCode());
		commandSave.setEmployeeId(employeeId);
		commandSave.setWorktimeCode(workTimeCode);
		commandSave.setYmd(dateInPeriod);
		
		// add childCare
		if (optionalShortTime.isPresent()) {
			commandSave
					.setChildCareSchedules(
							optionalShortTime.get().getLstTimeSlot().stream()
									.map(shortime -> this.convertShortTimeChildCareToChildCare(shortime,
											optionalShortTime.get().getChildCareAtr().value))
									.collect(Collectors.toList()));
		}

		// 勤務予定マスタ情報を取得する
		if (!this.saveScheduleMaster(commandSave, command.getExecutionId(), empGeneralInfo, listBusTypeOfEmpHis))
			return;

		// check not exist error
		if (!this.scheCreExeErrorLogHandler.checkExistError(command.toBaseCommand(dateInPeriod), employeeId)) {

			WorkTimeSetGetterCommand commandGetter = new WorkTimeSetGetterCommand();
			commandGetter.setWorktypeCode(worktypeDto.getWorktypeCode());
			commandGetter.setCompanyId(command.getCompanyId());
			commandGetter.setWorkingCode(workTimeCode);
			
			Optional<PrescribedTimezoneSetting> optionalWorkTimeSet = this.scheCreExeWorkTimeHandler
					.getScheduleWorkHour(commandGetter);
			if (optionalWorkTimeSet.isPresent()) {
				// update scheTimeZone
				PrescribedTimezoneSetting workTimeSet = optionalWorkTimeSet.get();
				commandSave.updateWorkScheduleTimeZones(workTimeSet);
			}
		}

		if (worktypeDto.getWorktypeSet() != null && commandSave.getWorkScheduleTimeZones() != null
				&& !commandSave.getWorkScheduleTimeZones().isEmpty()) {
			Optional<BounceAtr> bounceAtrOpt = Optional.of(this.getBounceAtr(worktypeDto.getWorktypeSet()));

			if (bounceAtrOpt.isPresent()) {
				// set scheTimeZone
				commandSave.setWorkScheduleTimeZones(commandSave.getWorkScheduleTimeZones().stream().map(timeZone -> {
					timeZone.setBounceAtr(bounceAtrOpt.get().value);
					return timeZone;
				}).collect(Collectors.toList()));
			}
		}
		
		// 休憩予定時間帯を取得する
		this.saveBreakTime(command.getCompanyId(), commandSave, listWorkType, listWorkTimeSetting, mapFixedWorkSetting,
				mapFlowWorkSetting, mapDiffTimeWorkSetting);

		// update is confirm
		commandSave.setConfirmedAtr(this.getConfirmedAtr(command.getConfirm(), ConfirmedAtr.UNSETTLED).value);

        // 勤務予定時間
		if (CollectionUtil.isEmpty(commandSave.getWorkScheduleTimeZones())) {
			commandSave.setWorkScheduleTime(Optional.empty());
		} else {
			List<Integer> startClock = new ArrayList<>();
			List<Integer> endClock = new ArrayList<>();
			List<Integer> breakStartTime = new ArrayList<>();
			List<Integer> breakEndTime = new ArrayList<>();
			List<Integer> childCareStartTime = new ArrayList<>();
			List<Integer> childCareEndTime = new ArrayList<>();

			commandSave.getWorkScheduleTimeZones().forEach(x -> {
				startClock.add(x.getScheduleStartClock().v());
				endClock.add(x.getScheduleEndClock().v());
			});

			commandSave.getWorkScheduleBreaks().forEach(x -> {
				breakStartTime.add(x.getScheduledStartClock().v());
				breakEndTime.add(x.getScheduledEndClock().v());
			});

			commandSave.getChildCareSchedules().forEach(x -> {
				childCareStartTime.add(x.getChildCareScheduleStart().v());
				childCareEndTime.add(x.getChildCareScheduleEnd().v());
			});

			ScTimeParam param = new ScTimeParam(employeeId, dateInPeriod,
					new WorkTypeCode(worktypeDto.getWorktypeCode()), new WorkTimeCode(workTimeCode), startClock,
					endClock, breakStartTime, breakEndTime, childCareStartTime, childCareEndTime);
			this.saveScheduleTime(param, commandSave);
		}
        
		// check parameter is delete before insert
		if (command.getIsDeleteBeforInsert()) {
			this.basicScheduleRepository.delete(employeeId, dateInPeriod);
		}
		// add to list basicSchedule to insert/update all
		allData.add(commandSave.toDomain());
	}

	/**
	 * Gets the confirmed atr.
	 *
	 * @param isConfirmContent
	 *            the is confirm content
	 * @param confirmedAtr
	 *            the confirmed atr
	 * @return the confirmed atr
	 */
	// 予定確定区分を取得
	private ConfirmedAtr getConfirmedAtr(boolean isConfirmContent, ConfirmedAtr confirmedAtr) {

		// check is confirm content
		if (isConfirmContent) {
			return ConfirmedAtr.CONFIRMED;
		} else {
			return confirmedAtr;
		}
	}

	/**
	 * Save basic schedule.
	 *
	 * @param command
	 *            the command
	 */
	// 勤務予定情報を登録する
	private void saveBasicSchedule(BasicScheduleSaveCommand command) {

		// find basic schedule by id
		boolean optionalBasicSchedule = this.basicScheduleRepository.isExists(command.getEmployeeId(),
				command.getYmd());

		// check exist data
		if (optionalBasicSchedule) {

			// update domain
			this.basicScheduleRepository.update(command.toDomain());
		} else {

			// insert domain
			this.basicScheduleRepository.insert(command.toDomain());
		}
	}

	/**
	 * Convert short tim child care to child care.
	 *
	 * @param shortChildCareFrameDto
	 *            the short child care frame dto
	 * @return the child care schedule save command
	 */
	// 勤務予定育児介護時間帯
	private ChildCareScheduleSaveCommand convertShortTimeChildCareToChildCare(
			ShortChildCareFrameDto shortChildCareFrameDto, int childCareAtr) {
		ChildCareScheduleSaveCommand command = new ChildCareScheduleSaveCommand();

		// 予定育児介護回数 = 取得した短時間勤務. 時間帯. 回数
		command.setChildCareNumber(shortChildCareFrameDto.getTimeSlot());

		// 予定育児介護開始時刻 = 取得した短時間勤務. 時間帯. 開始時刻
		command.setChildCareScheduleStart(shortChildCareFrameDto.getStartTime().valueAsMinutes());

		// 予定育児介護終了時刻 = 取得した短時間勤務. 時間帯. 終了時刻
		command.setChildCareScheduleEnd(shortChildCareFrameDto.getEndTime().valueAsMinutes());

		// 育児介護区分 = 取得した短時間勤務. 育児介護区分
		command.setChildCareAtr(childCareAtr);
		return command;
	}

	/**
	 * 再設定する情報を取得する
	 * 
	 * Reset all data to command save.
	 *
	 * @param BasicScheduleResetCommand,
	 *            GeneralDate
	 */
	public void resetAllDataToCommandSave(BasicScheduleResetCommand command, GeneralDate toDate,
			List<BasicSchedule> allData, EmployeeGeneralInfoImported empGeneralInfo, List<BusinessTypeOfEmpDto> listBusTypeOfEmpHis) {
		// add command save
		BasicScheduleSaveCommand commandSave = new BasicScheduleSaveCommand();
		commandSave.setWorktypeCode(command.getWorkTypeCode());
		commandSave.setEmployeeId(command.getEmployeeId());
		commandSave.setWorktimeCode(command.getWorkingCode());
		commandSave.setYmd(toDate);
		// 勤務開始・終了時刻を再設定する
		commandSave = this.resetCreatedData(command, commandSave);
		// update is confirm
		commandSave.setConfirmedAtr(this.getConfirmedAtr(command.getConfirm(), ConfirmedAtr.UNSETTLED).value);
		
		// マスタ情報を再設定する
		// 勤務予定マスタ情報を取得する
		if (!this.saveScheduleMaster(commandSave, command.getExecutionId(), empGeneralInfo, listBusTypeOfEmpHis))
			return;
		// save command
		allData.add(commandSave.toDomain());
	}

	/**
	 * Reset created data.
	 *
	 * @param resetAtr
	 *            the reset atr
	 */
	// 作成済みのデータを再設定する
	private BasicScheduleSaveCommand resetCreatedData(BasicScheduleResetCommand command,
			BasicScheduleSaveCommand commandSave) {
		Optional<BounceAtr> optionalBounceAtr = this.resetDirectLineBounce(command);
		commandSave = this.resetWorkTime(command, commandSave);
		// check exist and not empty list
		if (optionalBounceAtr.isPresent() && !CollectionUtil.isEmpty(commandSave.getWorkScheduleTimeZones())) {
			commandSave.setWorkScheduleTimeZones(commandSave.getWorkScheduleTimeZones().stream().map(timeZone -> {
				timeZone.setBounceAtr(optionalBounceAtr.get().value);
				return timeZone;
			}).collect(Collectors.toList()));
		}
		return commandSave;
	}

	/**
	 * Reset direct line bounce.
	 */
	// 直行直帰再設定
	private Optional<BounceAtr> resetDirectLineBounce(BasicScheduleResetCommand command) {
		// comment code below because not do in this phrase
		// check is 直行直帰再設定 TRUE
		// if (command.getResetAtr().getResetDirectLineBounce()) {

		WorkType worktype = this.workTypeRepository.findByPK(command.getCompanyId(), command.getWorkTypeCode()).get();

		if (worktype.getWorkTypeSet() != null) {
			return Optional.of(this.getBounceAtr(worktype.getWorkTypeSet()));
		}

		// }
		return Optional.empty();
	}

	/**
	 * Gets the bounce atr.
	 *
	 * @param workTypeSet
	 *            the work type set
	 * @return the bounce atr
	 */
	private BounceAtr getBounceAtr(WorkTypeSet workTypeSet) {

		// 出勤時刻を直行とする：False AND 退勤時刻を直行とする：False⇒ 直行直帰なし
		if (workTypeSet.getAttendanceTime() == WorkTypeSetCheck.NO_CHECK
				&& workTypeSet.getTimeLeaveWork() == WorkTypeSetCheck.NO_CHECK) {
			return BounceAtr.DIRECT_BOUNCE;
		}

		// 出勤時刻を直行とする：True AND 退勤時刻を直行とする：False⇒ 直行のみ
		if (workTypeSet.getAttendanceTime() == WorkTypeSetCheck.CHECK
				&& workTypeSet.getTimeLeaveWork() == WorkTypeSetCheck.NO_CHECK) {
			return BounceAtr.BOUNCE_ONLY;
		}

		// 出勤時刻を直行とする：False AND 退勤時刻を直行とする：True⇒ 直帰のみ
		if (workTypeSet.getAttendanceTime() == WorkTypeSetCheck.NO_CHECK
				&& workTypeSet.getTimeLeaveWork() == WorkTypeSetCheck.CHECK) {
			return BounceAtr.NO_DIRECT_BOUNCE;
		}

		// 出勤時刻を直行とする：True AND 退勤時刻を直行とする：True⇒ 直行直帰
		if (workTypeSet.getAttendanceTime() == WorkTypeSetCheck.CHECK
				&& workTypeSet.getTimeLeaveWork() == WorkTypeSetCheck.CHECK) {
			return BounceAtr.DIRECTLY_ONLY;
		}
		return BounceAtr.DIRECTLY_ONLY;
	}

	/**
	 * Reset work time.
	 *
	 * @param command
	 *            the command
	 * @return the basic schedule save command
	 */
	// 就業時間帯再設定
	private BasicScheduleSaveCommand resetWorkTime(BasicScheduleResetCommand command,
			BasicScheduleSaveCommand commandSave) {

		// check 就業時間帯再設定 is TRUE
		if (command.getResetAtr().getResetWorkingHours()) {
			WorkTimeSetGetterCommand commandGetter = new WorkTimeSetGetterCommand();
			commandGetter.setWorktypeCode(command.getWorkTypeCode());
			commandGetter.setCompanyId(command.getCompanyId());
			commandGetter.setWorkingCode(command.getWorkingCode());
			Optional<PrescribedTimezoneSetting> optionalWorkTimeSet = this.scheCreExeWorkTimeHandler
					.getScheduleWorkHour(commandGetter);
			if (optionalWorkTimeSet.isPresent()) {
				PrescribedTimezoneSetting workTimeSet = optionalWorkTimeSet.get();
				commandSave.updateWorkScheduleTimeZones(workTimeSet);
			}
		}
		return commandSave;
	}

	/**
	 * 勤務予定休憩
	 * 
	 * @param employeeId
	 * @param toDate
	 */
	private BasicScheduleSaveCommand saveBreakTime(String companyId, BasicScheduleSaveCommand commandSave,
			List<WorkType> listWorkType, List<WorkTimeSetting> listWorkTimeSetting,
			Map<String, WorkRestTimeZoneDto> mapFixedWorkSetting, Map<String, WorkRestTimeZoneDto> mapFlowWorkSetting,
			Map<String, WorkRestTimeZoneDto> mapDiffTimeWorkSetting) {
		if(CollectionUtil.isEmpty(commandSave.getWorkScheduleTimeZones())){
			commandSave.setWorkScheduleBreaks(Collections.emptyList());
			return commandSave;
		}
		List<DeductionTime> listScheTimeZones = commandSave.getWorkScheduleTimeZones().stream()
				.map(x -> new DeductionTime(x.getScheduleStartClock(), x.getScheduleEndClock()))
				.collect(Collectors.toList());
		BusinessDayCal businessDayCal = this.scheWithBusinessDayCalService.getScheduleBreakTime(companyId,
				commandSave.getWorktypeCode(), commandSave.getWorktimeCode(), listWorkType, listWorkTimeSetting,
				mapFixedWorkSetting, mapFlowWorkSetting, mapDiffTimeWorkSetting, listScheTimeZones);
		if (businessDayCal == null) {
			return commandSave;
		}
		List<WorkScheduleBreakSaveCommand> workScheduleBreaks = new ArrayList<>();
		List<DeductionTime> timeZones = businessDayCal.getTimezones();

		if (timeZones == null) {
			timeZones = new ArrayList<>();
		}

		for (int i = 0; i < timeZones.size(); i++) {
			WorkScheduleBreakSaveCommand wBreakSaveCommand = new WorkScheduleBreakSaveCommand();
			wBreakSaveCommand.setScheduleBreakCnt(i + 1);
			wBreakSaveCommand.setScheduledStartClock(timeZones.get(i).getStart().valueAsMinutes());
			wBreakSaveCommand.setScheduledEndClock(timeZones.get(i).getEnd().valueAsMinutes());
			workScheduleBreaks.add(wBreakSaveCommand);
		}
		commandSave.setWorkScheduleBreaks(workScheduleBreaks);
		return commandSave;
	}

	/**
	 * 勤務予定マスタ情報を取得する
	 * 
	 * @param employeeId
	 * @param toDate
	 */
	private boolean saveScheduleMaster(BasicScheduleSaveCommand commandSave, String executionId,
			EmployeeGeneralInfoImported empGeneralInfo, List<BusinessTypeOfEmpDto> listBusTypeOfEmpHis) {
		// 勤務予定マスタ情報を取得する
		Optional<ScheduleMasterInformationDto> scheduleMasterInforOpt = this.scheduleMasterInformationService
				.getScheduleMasterInformationDto(commandSave.getEmployeeId(), commandSave.getYmd(), executionId,
						empGeneralInfo, listBusTypeOfEmpHis);
		if (!scheduleMasterInforOpt.isPresent())
			return false;
		ScheduleMasterInformationDto scheduleMasterInfor = scheduleMasterInforOpt.get();
		ScheMasterInfo workScheduleMaster = new ScheMasterInfo(commandSave.getEmployeeId(), commandSave.getYmd(),
				scheduleMasterInfor.getEmployeeCode(), scheduleMasterInfor.getClassificationCode(),
				scheduleMasterInfor.getBusinessTypeCode(), scheduleMasterInfor.getJobId(),
				scheduleMasterInfor.getWorkplaceId());
		commandSave.setWorkScheduleMaster(workScheduleMaster);
		return true;
	}

	/**
	 * 勤務予定時間
	 */
	private BasicScheduleSaveCommand saveScheduleTime(ScTimeParam param, BasicScheduleSaveCommand commandSave) {
		ScTimeImport scTimeImport = scTimeAdapter.calculation(param);
		WorkScheduleTime workScheduleTime = new WorkScheduleTime(Collections.emptyList(),
				scTimeImport.getBreakTime(), scTimeImport.getActualWorkTime(), scTimeImport.getWeekDayTime(),
				scTimeImport.getPreTime(), scTimeImport.getTotalWorkTime(), scTimeImport.getChildCareTime());
		commandSave.setWorkScheduleTime(Optional.ofNullable(workScheduleTime));
		return commandSave;
	}

	/**
	 * Create a basic schedule command to save
	 * 
	 * @param basicSchedule
	 *            the basic schedule
	 * @param optPrescribedSetting
	 *            the Optional prescribed setting
	 * @param command
	 *            the work time set getter command
	 * @param employeeId
	 *            the employee Id
	 * @param baseDate
	 *            the base date (input from screen A)
	 */
	public void registerBasicScheduleSaveCommand(Optional<BasicSchedule> optBasicSchedule,
			Optional<PrescribedTimezoneSetting> optPrescribedSetting, WorkTimeSetGetterCommand command,
			String employeeId, GeneralDate baseDate) {
		BasicSchedule basicSchedule;

		// Create basic schedule
		if (!optBasicSchedule.isPresent()) {
			basicSchedule = new BasicSchedule(employeeId, baseDate, command.getWorktypeCode(), command.getWorkingCode(),
					ConfirmedAtr.CONFIRMED);
		} else {
			basicSchedule = optBasicSchedule.get();
		}
		BasicScheduleSaveCommand basicScheduleSaveCommand = new BasicScheduleSaveCommand();
		basicScheduleSaveCommand.setEmployeeId(basicSchedule.getEmployeeId());
		basicScheduleSaveCommand.setWorktimeCode(command.getWorkingCode());
		basicScheduleSaveCommand.setWorktypeCode(command.getWorktypeCode());
		basicScheduleSaveCommand.setYmd(basicSchedule.getDate());
		basicScheduleSaveCommand.setWorkScheduleTime(basicSchedule.getWorkScheduleTime());

		PrescribedTimezoneSetting prescribedTimezoneSetting;

		// 該当日の該当社員の個人勤務予定が既に存在するかチェック
		if (optPrescribedSetting.isPresent()) {
			// 存在しない場合

			// ドメインモデル「勤務予定基本情報」を追加する
			prescribedTimezoneSetting = optPrescribedSetting.get();
		} else {
			// 存在する場合

			// ドメインモデル「勤務予定基本情報」を更新する
			PrescribedTimezoneSettingDto prescribedTimezoneSettingDto = new PrescribedTimezoneSettingDto();
			prescribedTimezoneSettingDto.setMorningEndTime(DEFAULT_VALUE);
			prescribedTimezoneSettingDto.setAfternoonStartTime(DEFAULT_VALUE);
			prescribedTimezoneSettingDto.setLstTimezone(new ArrayList<>());
			prescribedTimezoneSetting = new PrescribedTimezoneSetting(prescribedTimezoneSettingDto);
		}

		basicScheduleSaveCommand.updateWorkScheduleTimeZones(prescribedTimezoneSetting);

		saveBasicSchedule(basicScheduleSaveCommand);
	}
}
