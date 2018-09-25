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
import java.util.stream.IntStream;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.schedule.app.command.executionlog.CreateScheduleMasterCache;
import nts.uk.ctx.at.schedule.app.command.executionlog.ScheduleCreatorExecutionCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.BasicScheduleSaveCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.ChildCareScheduleSaveCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.WorkScheduleBreakSaveCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.WorkScheduleTimeZoneSaveCommand;
import nts.uk.ctx.at.schedule.dom.adapter.ScTimeAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.ScTimeImport;
import nts.uk.ctx.at.schedule.dom.adapter.ScTimeParam;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.SCEmployeeAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.EmployeeDto;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.ShortChildCareFrameDto;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.ShortWorkTimeDto;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.EmployeeGeneralInfoImported;
import nts.uk.ctx.at.schedule.dom.schedule.algorithm.BusinessDayCal;
import nts.uk.ctx.at.schedule.dom.schedule.algorithm.CreScheWithBusinessDayCalService;
import nts.uk.ctx.at.schedule.dom.schedule.algorithm.WorkRestTimeZoneDto;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.ConfirmedAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service.DateRegistedEmpSche;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workschedulebreak.WorkScheduleBreak;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletime.PersonFeeTime;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletime.WorkScheduleTime;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.BounceAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.WorkScheduleTimeZone;
import nts.uk.ctx.at.schedule.dom.schedule.commonalgorithm.ScheduleMasterInformationDto;
import nts.uk.ctx.at.schedule.dom.schedule.commonalgorithm.ScheduleMasterInformationService;
import nts.uk.ctx.at.schedule.dom.schedule.schedulemaster.ScheMasterInfo;
import nts.uk.ctx.at.schedule.dom.schedule.workschedulestate.ScheduleEditState;
import nts.uk.ctx.at.schedule.dom.schedule.workschedulestate.WorkScheduleState;
import nts.uk.ctx.at.schedule.dom.scheduleitemmanagement.ScheduleItem;
import nts.uk.ctx.at.schedule.dom.scheduleitemmanagement.ScheduleItemManagementRepository;
import nts.uk.ctx.at.shared.app.command.worktime.predset.dto.PrescribedTimezoneSettingDto;
import nts.uk.ctx.at.shared.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmpDto;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.UseSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSetCheck;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.context.ScreenIdentifier;
import nts.uk.shr.com.security.audittrail.basic.LogBasicInformation;
import nts.uk.shr.com.security.audittrail.correction.content.CorrectionAttr;
import nts.uk.shr.com.security.audittrail.correction.content.DataCorrectionLog;
import nts.uk.shr.com.security.audittrail.correction.content.DataValueAttribute;
import nts.uk.shr.com.security.audittrail.correction.content.ItemInfo;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataKey;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataKey.CalendarKeyType;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataType;
import nts.uk.shr.com.security.audittrail.correction.content.UserInfo;
import nts.uk.shr.com.security.audittrail.correction.processor.DataCorrectionLogWriter;
import nts.uk.shr.com.security.audittrail.correction.processor.LogBasicInformationWriter;
import nts.uk.shr.com.security.audittrail.start.StartPageLog;
import nts.uk.shr.com.security.audittrail.start.StartPageLogRepository;

/**
 * The Class ScheCreExeBasicScheduleHandler.
 */
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
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
	
	@Inject
	private ScheduleItemManagementRepository scheduleItemManagementRepository;
	
	@Inject
	private DataCorrectionLogWriter dataCorrectionLogWriter;
	
	@Inject
	private StartPageLogRepository startPageLogRepository;
	
	@Inject
	private LogBasicInformationWriter logBasicInformationWriter;
	
	@Inject
	private SCEmployeeAdapter scEmployeeAdapter;

	/** The Constant DEFAULT_VALUE. */
	private static final int DEFAULT_VALUE = 0;
	
	private static final int WORK_TYPE_CODE = 1;
	private static final int WORK_TIME_CODE = 2;
	private static final int[] START_TIME = {3, 5};
	private static final int[] END_TIME = {4, 6};
	private static final int[] BREAK_START_TIME = {7, 9, 11, 13, 15, 17, 19, 21, 23, 25};
	private static final int[] BREAK_END_TIME = {8, 10, 12, 14, 16, 18, 20, 22, 24, 26};
	private static final int[] CHILD_START_TIME = {28, 30};
	private static final int[] CHILD_END_TIME = {30, 32};

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
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void updateAllDataToCommandSave(
			ScheduleCreatorExecutionCommand command,
			GeneralDate dateInPeriod,
			String employeeId,
			WorktypeDto worktypeDto,
			String workTimeCode,
			CreateScheduleMasterCache masterCache,
			List<BasicSchedule> listBasicSchedule,
			DateRegistedEmpSche dateRegistedEmpSche) {

		// 「社員の短時間勤務一覧」からパラメータ.社員ID、対象日をもとに該当する短時間勤務を取得する
		// EA修正履歴：No2135
		// EA修正履歴：No2136
		Optional<ShortWorkTimeDto> optionalShortTime = masterCache.getListShortWorkTimeDto().stream()
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
		if (!this.saveScheduleMaster(
				commandSave,
				command.getExecutionId(),
				masterCache.getEmpGeneralInfo(),
				masterCache.getListBusTypeOfEmpHis()))
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
		commandSave.setWorkScheduleBreaks(
				this.getBreakTime(
						command.getCompanyId(),
						commandSave,
						masterCache));

		// update is confirm
		commandSave.setConfirmedAtr(this.getConfirmedAtr(command.getConfirm(), ConfirmedAtr.UNSETTLED).value);

        // 勤務予定時間
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

		ScTimeParam param = new ScTimeParam(employeeId, dateInPeriod, new WorkTypeCode(worktypeDto.getWorktypeCode()),
				new WorkTimeCode(workTimeCode), startClock, endClock, breakStartTime, breakEndTime, childCareStartTime,
				childCareEndTime);
		this.saveScheduleTime(command.getCompanySetting(), param, commandSave);
        
		// check parameter is delete before insert
		if (command.getIsDeleteBeforInsert()) {
			this.basicScheduleRepository.delete(employeeId, dateInPeriod, commandSave.toDomain());
		}
		
		// save command
		this.saveBasicSchedule(commandSave, listBasicSchedule, command.getIsDeleteBeforInsert(), dateRegistedEmpSche);
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
			this.basicScheduleRepository.update(command.toDomain());
		} else {
			this.basicScheduleRepository.insert(command.toDomain());
		}
	}
	
	/**
	 * Save basic schedule, also add work schedule state
	 *
	 * @param command
	 *            the command
	 */
	// 勤務予定情報を登録する
	private void saveBasicSchedule(BasicScheduleSaveCommand command, List<WorkScheduleState> lstWorkScheState) {

		// find basic schedule by id
        boolean optionalBasicSchedule = this.basicScheduleRepository.isExists(command.getEmployeeId(),
                command.getYmd());

        BasicSchedule basicSchedule = command.toDomain();
        basicSchedule.setWorkScheduleStates(lstWorkScheState);
        
        // check exist data
        if (optionalBasicSchedule) {
			this.basicScheduleRepository.update(basicSchedule);
		} else {
			this.basicScheduleRepository.insert(basicSchedule);
		}
	}
	
	// 勤務予定情報を登録する-for KSC001
	private void saveBasicSchedule(BasicScheduleSaveCommand command, List<BasicSchedule> listBasicSchedule,
			boolean isDeleteBeforeInsert, DateRegistedEmpSche dateRegistedEmpSche) {
		// 登録対象日を保持しておく（暫定データ作成用）
		dateRegistedEmpSche.getListDate().add(command.getYmd());
		
		// if delete before, it always insert
		if(isDeleteBeforeInsert){
			this.basicScheduleRepository.insert(command.toDomain());
			return;
		}
		
		// find basic schedule by id
		// fix for response
		Optional<BasicSchedule> optionalBasicSchedule = listBasicSchedule.stream()
				.filter(x -> (x.getEmployeeId().equals(command.getEmployeeId())
						&& x.getDate().compareTo(command.getYmd()) == 0))
				.findFirst();
		
		if (optionalBasicSchedule.isPresent()) {
			this.basicScheduleRepository.update(command.toDomain());
		} else {
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
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void resetAllDataToCommandSave(BasicScheduleResetCommand command, GeneralDate toDate,
			EmployeeGeneralInfoImported empGeneralInfo, List<BusinessTypeOfEmpDto> listBusTypeOfEmpHis,
			List<BasicSchedule> listBasicSchedule, DateRegistedEmpSche dateRegistedEmpSche) {
		String employeeId = command.getEmployeeId();
		String workTypeCode = command.getWorkTypeCode();
		String workTimeCode = command.getWorkingCode();
		// add command save
		BasicScheduleSaveCommand commandSave = new BasicScheduleSaveCommand();
		commandSave.setWorktypeCode(workTypeCode);
		commandSave.setEmployeeId(employeeId);
		commandSave.setWorktimeCode(workTimeCode);
		commandSave.setYmd(toDate);
		// 勤務開始・終了時刻を再設定する
		commandSave = this.resetCreatedData(command, commandSave);
		// update is confirm
		commandSave.setConfirmedAtr(this.getConfirmedAtr(command.getConfirm(), ConfirmedAtr.UNSETTLED).value);

		// マスタ情報を再設定する
		// 勤務予定マスタ情報を取得する
		if (!this.saveScheduleMaster(commandSave, command.getExecutionId(), empGeneralInfo, listBusTypeOfEmpHis))
			return;
		
		 // 勤務予定時間
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

		ScTimeParam param = new ScTimeParam(employeeId, toDate, new WorkTypeCode(workTypeCode),
				new WorkTimeCode(workTimeCode), startClock, endClock, breakStartTime, breakEndTime, childCareStartTime,
				childCareEndTime);
		this.saveScheduleTime(command.getCompanySetting(), param, commandSave);
		
		boolean isDeleteBeforeInsert = false;
		// save command
		this.saveBasicSchedule(commandSave, listBasicSchedule, isDeleteBeforeInsert, dateRegistedEmpSche);
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
			return BounceAtr.NO_DIRECT_BOUNCE;
		}

		// 出勤時刻を直行とする：True AND 退勤時刻を直行とする：False⇒ 直行のみ
		if (workTypeSet.getAttendanceTime() == WorkTypeSetCheck.CHECK
				&& workTypeSet.getTimeLeaveWork() == WorkTypeSetCheck.NO_CHECK) {
			return BounceAtr.DIRECTLY_ONLY;
		}

		// 出勤時刻を直行とする：False AND 退勤時刻を直行とする：True⇒ 直帰のみ
		if (workTypeSet.getAttendanceTime() == WorkTypeSetCheck.NO_CHECK
				&& workTypeSet.getTimeLeaveWork() == WorkTypeSetCheck.CHECK) {
			return BounceAtr.BOUNCE_ONLY;
		}

		// 出勤時刻を直行とする：True AND 退勤時刻を直行とする：True⇒ 直行直帰
		if (workTypeSet.getAttendanceTime() == WorkTypeSetCheck.CHECK
				&& workTypeSet.getTimeLeaveWork() == WorkTypeSetCheck.CHECK) {
			return BounceAtr.DIRECT_BOUNCE;
		}
		return BounceAtr.DIRECT_BOUNCE;
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
	private List<WorkScheduleBreakSaveCommand> getBreakTime(
			String companyId,
			BasicScheduleSaveCommand commandSave,
			CreateScheduleMasterCache masterCache) {
		if(CollectionUtil.isEmpty(commandSave.getWorkScheduleTimeZones())){
			return Collections.emptyList();
		}
		List<DeductionTime> listScheTimeZones = commandSave.getWorkScheduleTimeZones().stream()
				.map(x -> new DeductionTime(x.getScheduleStartClock(), x.getScheduleEndClock()))
				.collect(Collectors.toList());
		BusinessDayCal businessDayCal = this.scheWithBusinessDayCalService.getScheduleBreakTime(
				companyId,
				commandSave.getWorktypeCode(),
				commandSave.getWorktimeCode(),
				masterCache.getListWorkType(),
				masterCache.getListWorkTimeSetting(),
				masterCache.getMapFixedWorkSetting(),
				masterCache.getMapFlowWorkSetting(),
				masterCache.getMapDiffTimeWorkSetting(),
				listScheTimeZones);
		if (businessDayCal == null) {
			return Collections.emptyList();
		}
		List<WorkScheduleBreakSaveCommand> workScheduleBreaks = new ArrayList<>();
		List<DeductionTime> timeZones = businessDayCal.getTimezones();

		for (int i = 0; i < timeZones.size(); i++) {
			WorkScheduleBreakSaveCommand wBreakSaveCommand = new WorkScheduleBreakSaveCommand();
			wBreakSaveCommand.setScheduleBreakCnt(i + 1);
			wBreakSaveCommand.setScheduledStartClock(timeZones.get(i).getStart().valueAsMinutes());
			wBreakSaveCommand.setScheduledEndClock(timeZones.get(i).getEnd().valueAsMinutes());
			workScheduleBreaks.add(wBreakSaveCommand);
		}
		return workScheduleBreaks;
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
	private BasicScheduleSaveCommand saveScheduleTime(Object companySetting, ScTimeParam param, BasicScheduleSaveCommand commandSave) {
		ScTimeImport scTimeImport = CalculationCache.getResult(
				param.forCache(),
				() -> scTimeAdapter.calculation(companySetting, param));
		
		List<PersonFeeTime> personFeeTime = new ArrayList<>();
		for(int i = 1; i <= scTimeImport.getPersonalExpenceTime().size(); i++){
			personFeeTime.add(PersonFeeTime.createFromJavaType(i, scTimeImport.getPersonalExpenceTime().get(i)));
		}
		WorkScheduleTime workScheduleTime = new WorkScheduleTime(personFeeTime,
				scTimeImport.getBreakTime(), scTimeImport.getActualWorkTime(), scTimeImport.getWeekDayTime(),
				scTimeImport.getPreTime(), scTimeImport.getTotalWorkTime(), scTimeImport.getChildTime(), scTimeImport.getCareTime(), scTimeImport.getFlexTime());
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
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void registerBasicScheduleSaveCommand(String companyId, Optional<BasicSchedule> optBasicSchedule,
			Optional<PrescribedTimezoneSetting> optPrescribedSetting, WorkTimeSetGetterCommand command,
			String employeeId, GeneralDate baseDate, WorkType workType) {
		BasicSchedule basicSchedule;
		String sid = AppContexts.user().employeeId();

		// 予定時間を計算する
		ScTimeParam.ScTimeParamBuilder bld = ScTimeParam.builder();
		bld.employeeId(employeeId);
		bld.targetDate(baseDate);
		bld.workTypeCode(new WorkTypeCode(command.getWorktypeCode()));
		bld.workTimeCode(new WorkTimeCode(command.getWorkingCode()));
		
		// Create basic schedule
		if (!optBasicSchedule.isPresent()) {
			basicSchedule = new BasicSchedule(employeeId, baseDate, command.getWorktypeCode(), command.getWorkingCode(),
					ConfirmedAtr.CONFIRMED);
			bld.childCareStartTime(new ArrayList<>()).childCareEndTime(new ArrayList<>()).breakStartTime(new ArrayList<>()).breakEndTime(new ArrayList<>());
		} else {
			basicSchedule = optBasicSchedule.get();
			
			bld.childCareStartTime(basicSchedule.getChildCareSchedules().stream().map(sche -> sche.getChildCareScheduleStart().v()).collect(Collectors.toList()));
			bld.childCareEndTime(basicSchedule.getChildCareSchedules().stream().map(sche -> sche.getChildCareScheduleEnd().v()).collect(Collectors.toList()));
			bld.breakStartTime(basicSchedule.getWorkScheduleBreaks().stream().map(sche -> sche.getScheduledStartClock().v()).collect(Collectors.toList()));
			bld.breakEndTime(basicSchedule.getWorkScheduleBreaks().stream().map(sche -> sche.getScheduledEndClock().v()).collect(Collectors.toList()));
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
			prescribedTimezoneSetting = optPrescribedSetting.get();
			
			bld.startClock(prescribedTimezoneSetting.getLstTimezone().stream().filter(timeZone -> timeZone.getUseAtr() == UseSetting.USE).map(timeZone -> timeZone.getStart().v()).collect(Collectors.toList()));
			bld.endClock(prescribedTimezoneSetting.getLstTimezone().stream().filter(timeZone -> timeZone.getUseAtr() == UseSetting.USE).map(timeZone -> timeZone.getEnd().v()).collect(Collectors.toList()));
		} else {
			// 存在する場合
			PrescribedTimezoneSettingDto prescribedTimezoneSettingDto = new PrescribedTimezoneSettingDto();
			prescribedTimezoneSettingDto.setMorningEndTime(DEFAULT_VALUE);
			prescribedTimezoneSettingDto.setAfternoonStartTime(DEFAULT_VALUE);
			prescribedTimezoneSettingDto.setLstTimezone(new ArrayList<>());
			prescribedTimezoneSetting = new PrescribedTimezoneSetting(prescribedTimezoneSettingDto);
			
			bld.startClock(new ArrayList<>()).endClock(new ArrayList<>());
		}
		
		ScTimeParam param = bld.build();
		
		// Imported（勤務予定）「勤務予定の計算時間」を取得する
		basicScheduleSaveCommand.updateWorkScheduleTimeZonesKeepBounceAtr(prescribedTimezoneSetting, workType);
		basicScheduleSaveCommand = saveScheduleTime(null, param, basicScheduleSaveCommand);
		
		// Get all schedule item by company id (for optimization)
		List<ScheduleItem> lstScheduleItem = scheduleItemManagementRepository.findAllScheduleItem(companyId);
		
		List<WorkScheduleState> lstWorkScheduleState = lstScheduleItem.stream().map(x -> {
			return WorkScheduleState.createFromJavaType(
					basicSchedule.getEmployeeId().equals(sid) ? ScheduleEditState.HAND_CORRECTION_PRINCIPAL.value : ScheduleEditState.HAND_CORRECTION_ORDER.value, 
					Integer.parseInt(x.getScheduleItemId()), 
					basicSchedule.getDate(), employeeId);
		}).collect(Collectors.toList());
		

		saveBasicSchedule(basicScheduleSaveCommand, lstWorkScheduleState);
		
//		this.basicScheduleRepository.removeScheState(employeeId, baseDate, lstWorkScheduleState);
//		this.basicScheduleRepository.insertAllScheduleState(lstWorkScheduleState);
		
		
		// 修正ログ情報を作成する
		addEditDetailsLog(companyId, basicSchedule, basicScheduleSaveCommand, lstScheduleItem, employeeId, baseDate, optBasicSchedule.isPresent());
	}
	
	/**
	 * 修正ログ情報を作成する
	 * @param basicScheduleSaveCommand
	 */
	private void addEditDetailsLog(String companyId, BasicSchedule backupBasicSchedule, BasicScheduleSaveCommand basicScheduleSaveCommand, List<ScheduleItem> lstScheduleItem, String employeeId, GeneralDate targetDate, boolean isUpdate) {
		String sid = AppContexts.user().employeeId();
		
		//勤務種類コード
		Optional<ScheduleItem> optScheduleItemWorkType = lstScheduleItem.stream().filter(x -> StringUtils.equals(x.getScheduleItemId(), String.valueOf(WORK_TYPE_CODE))).findFirst();
		
		// 就業時間帯コード
		Optional<ScheduleItem> optScheduleItemWorkTime = lstScheduleItem.stream().filter(x -> StringUtils.equals(x.getScheduleItemId(), String.valueOf(WORK_TIME_CODE))).findFirst();
		
		// 開始時刻1～2
		List<ScheduleItem> lstScheduleItemStartTime = lstScheduleItem.stream().filter(x -> IntStream.of(START_TIME).anyMatch(y -> y == Integer.parseInt(x.getScheduleItemId()))).collect(Collectors.toList());
			
		// 終了時刻1～2
		List<ScheduleItem> lstScheduleItemEndTime = lstScheduleItem.stream().filter(x -> IntStream.of(END_TIME).anyMatch(y -> y == Integer.parseInt(x.getScheduleItemId()))).collect(Collectors.toList());
		
		// 休憩開始時刻1~10
		List<ScheduleItem> optScheduleItemBreakStartTime = lstScheduleItem.stream().filter(x -> IntStream.of(BREAK_START_TIME).anyMatch(y -> y == Integer.parseInt(x.getScheduleItemId()))).collect(Collectors.toList());
			
		// 休憩終了時刻1~10
		List<ScheduleItem> optScheduleItemBreakEndTime = lstScheduleItem.stream().filter(x -> IntStream.of(BREAK_END_TIME).anyMatch(y -> y == Integer.parseInt(x.getScheduleItemId()))).collect(Collectors.toList());
		
		// 育児介護開始時刻 1~2
		List<ScheduleItem> optScheduleItemChildStartTime = lstScheduleItem.stream().filter(x -> IntStream.of(CHILD_START_TIME).anyMatch(y -> y == Integer.parseInt(x.getScheduleItemId()))).collect(Collectors.toList());
		
		// 育児介護終了時刻 1~2
		List<ScheduleItem> optScheduleItemChildEndTime = lstScheduleItem.stream().filter(x -> IntStream.of(CHILD_END_TIME).anyMatch(y -> y == Integer.parseInt(x.getScheduleItemId()))).collect(Collectors.toList());
		
		List<StartPageLog> lstStartPageLog = startPageLogRepository.findBySid(sid);
		StartPageLog lastLog = lstStartPageLog.get(lstStartPageLog.size() - 1);
		
		// 「データ修正記録のパラメータ」を生成する
		List<DataCorrectionLog> lstDataCorrectionLog = new ArrayList<>();
		
		LogBasicInformation logBasicInformation = lastLog.getBasicInfo();
		String operationId = IdentifierUtil.randomUniqueId();
		
		// Recreate new log basic information using new operation id
		LogBasicInformation logBasicInformationNew = new LogBasicInformation(operationId, logBasicInformation.getCompanyId(), logBasicInformation.getUserInfo(), logBasicInformation.getLoginInformation(), GeneralDateTime.now(), logBasicInformation.getAuthorityInformation(), new ScreenIdentifier("KSU007", "B", ""), logBasicInformation.getNote());
		
		lstDataCorrectionLog.add(createWorkTypeCorrectionLog(employeeId, targetDate, operationId, backupBasicSchedule, basicScheduleSaveCommand, optScheduleItemWorkType));
		lstDataCorrectionLog.add(createWorkTimeCorrectionLog(employeeId, targetDate, operationId, backupBasicSchedule, basicScheduleSaveCommand, optScheduleItemWorkTime));
		lstDataCorrectionLog.addAll(createTimeCorrectionLog(employeeId, targetDate, operationId, backupBasicSchedule, basicScheduleSaveCommand, lstScheduleItemStartTime, 0));
		lstDataCorrectionLog.addAll(createTimeCorrectionLog(employeeId, targetDate, operationId, backupBasicSchedule, basicScheduleSaveCommand, lstScheduleItemEndTime, 1));
		lstDataCorrectionLog.addAll(createTimeCorrectionLog(employeeId, targetDate, operationId, backupBasicSchedule, basicScheduleSaveCommand, optScheduleItemBreakStartTime, 2));
		lstDataCorrectionLog.addAll(createTimeCorrectionLog(employeeId, targetDate, operationId, backupBasicSchedule, basicScheduleSaveCommand, optScheduleItemBreakEndTime, 3));
		lstDataCorrectionLog.addAll(createTimeCorrectionLog(employeeId, targetDate, operationId, backupBasicSchedule, basicScheduleSaveCommand, optScheduleItemChildStartTime, 4));
		lstDataCorrectionLog.addAll(createTimeCorrectionLog(employeeId, targetDate, operationId, backupBasicSchedule, basicScheduleSaveCommand, optScheduleItemChildEndTime, 5));
		
		dataCorrectionLogWriter.save(lstDataCorrectionLog);
		
		logBasicInformationWriter.save(logBasicInformationNew);
	}
	
	private DataCorrectionLog createWorkTypeCorrectionLog(String employeeId, GeneralDate targetDate, String operationId, BasicSchedule backupBasicSchedule, BasicScheduleSaveCommand basicScheduleSaveCommand, Optional<ScheduleItem> optScheduleItemWorkType) {
		ScheduleItem workTypeItem = optScheduleItemWorkType.get(); // Supposely won't null
		LoginUserContext userContext = AppContexts.user();
		EmployeeDto employeeDto = scEmployeeAdapter.findByEmployeeId(employeeId);
		DataCorrectionLog log = new DataCorrectionLog(operationId,
				new UserInfo(userContext.userId(), employeeId, employeeDto.getEmployeeName()),
				TargetDataType.SCHEDULE, 
				new TargetDataKey(CalendarKeyType.DATE, targetDate, workTypeItem.scheduleItemName), 
				CorrectionAttr.EDIT, 
				ItemInfo.create(workTypeItem.scheduleItemId, workTypeItem.scheduleItemName, DataValueAttribute.STRING, backupBasicSchedule.getWorkTypeCode(), basicScheduleSaveCommand.getWorktypeCode()),
				workTypeItem.getDispOrder());
		
		return log;
	}
	
	private DataCorrectionLog createWorkTimeCorrectionLog(String employeeId, GeneralDate targetDate, String operationId, BasicSchedule backupBasicSchedule, BasicScheduleSaveCommand basicScheduleSaveCommand, Optional<ScheduleItem> optScheduleItemWorkTime) {
		ScheduleItem workTypeItem = optScheduleItemWorkTime.get(); // Supposely won't null
		LoginUserContext userContext = AppContexts.user();
		EmployeeDto employeeDto = scEmployeeAdapter.findByEmployeeId(employeeId);
		DataCorrectionLog log = new DataCorrectionLog(operationId,
				new UserInfo(userContext.userId(), employeeId, employeeDto.getEmployeeName()),
				TargetDataType.SCHEDULE, 
				new TargetDataKey(CalendarKeyType.DATE, targetDate, workTypeItem.scheduleItemName), 
				CorrectionAttr.EDIT, 
				ItemInfo.create(workTypeItem.scheduleItemId, workTypeItem.scheduleItemName, DataValueAttribute.STRING, backupBasicSchedule.getWorkTimeCode(), basicScheduleSaveCommand.getWorktimeCode()),
				workTypeItem.getDispOrder());
		
		return log;
	}
	
	private List<DataCorrectionLog> createTimeCorrectionLog(String employeeId, GeneralDate targetDate, String operationId, BasicSchedule backupBasicSchedule, BasicScheduleSaveCommand basicScheduleSaveCommand, List<ScheduleItem> lstScheduleItemStartTime, int timeType) {
		LoginUserContext userContext = AppContexts.user();
		EmployeeDto employeeDto = scEmployeeAdapter.findByEmployeeId(employeeId);
		
		List<DataCorrectionLog> lstLog = new ArrayList<>();
		
		// Get backup basic schedule data
		List<WorkScheduleTimeZone> lstOldScheTimeZone = backupBasicSchedule.getWorkScheduleTimeZones();
		List<WorkScheduleBreak> lstOldBreakTime = backupBasicSchedule.getWorkScheduleBreaks();
		List<ChildCareSchedule> lstOldChildTime = backupBasicSchedule.getChildCareSchedules();
		
		int lstOldScheTimeZoneCount = lstOldScheTimeZone.size();
		int lstOldBreakTimeCount = lstOldBreakTime.size();
		int lstOldChildTimeCount = lstOldChildTime.size();
		
		// Get update basic schedule data
		List<WorkScheduleTimeZoneSaveCommand> lstSaveScheTimeZone = basicScheduleSaveCommand.getWorkScheduleTimeZones();
		List<WorkScheduleBreakSaveCommand> lstSaveBreakTime = basicScheduleSaveCommand.getWorkScheduleBreaks();
		List<ChildCareScheduleSaveCommand> lstSaveChildTime = basicScheduleSaveCommand.getChildCareSchedules();
		int lstSaveScheTimeZoneCount = lstSaveScheTimeZone.size();
		int lstSaveBreakTimeCount = lstSaveBreakTime.size();
		int lstSaveChildTimeCount = lstSaveChildTime.size();
		
		try {
			for (int i = 0; i < lstScheduleItemStartTime.size(); i++) {
				ScheduleItem item = lstScheduleItemStartTime.get(i);
				ItemInfo itemInfo = null;
				
				switch(timeType) {
				case 0:
					if (i > lstOldScheTimeZoneCount && i > lstSaveScheTimeZoneCount) break;
					else if (i >= lstOldScheTimeZoneCount)
						itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, null, lstSaveScheTimeZone.get(i).getScheduleStartClock().v());
					else if (i >= lstSaveScheTimeZoneCount)
						itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, lstOldScheTimeZone.get(i).getScheduleStartClock().v(), null);
					else
						itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, lstOldScheTimeZone.get(i).getScheduleStartClock().v(), lstSaveScheTimeZone.get(i).getScheduleStartClock().v());
					break;
				case 1:
					if (i > lstOldScheTimeZoneCount && i > lstSaveScheTimeZoneCount) break;
					else if (i >= lstOldScheTimeZoneCount)
						itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, null, lstSaveScheTimeZone.get(i).getScheduleEndClock().v());
					else if (i >= lstSaveScheTimeZoneCount)
						itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, lstOldScheTimeZone.get(i).getScheduleEndClock().v(), null);
					else
						itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, lstOldScheTimeZone.get(i).getScheduleEndClock().v(), lstSaveScheTimeZone.get(i).getScheduleEndClock().v());
					break;
				case 2:
					if (i > lstOldBreakTimeCount && i > lstSaveBreakTimeCount) break;
					else if (i >= lstOldBreakTimeCount)
						itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, null, lstSaveBreakTime.get(i).getScheduledStartClock().v());
					else if (i >= lstSaveBreakTimeCount)
						itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, lstOldBreakTime.get(i).getScheduledStartClock().v(), null);
					else
						itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, lstOldBreakTime.get(i).getScheduledStartClock().v(), lstSaveBreakTime.get(i).getScheduledStartClock().v());
					break;
				case 3:
					if (i > lstOldBreakTimeCount && i > lstSaveBreakTimeCount) break;
					else if (i >= lstOldBreakTimeCount)
						itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, null, lstSaveBreakTime.get(i).getScheduledEndClock().v());
					else if (i >= lstSaveBreakTimeCount)
						itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, lstOldBreakTime.get(i).getScheduledEndClock().v(), null);
					else
						itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, lstOldBreakTime.get(i).getScheduledEndClock().v(), lstSaveBreakTime.get(i).getScheduledEndClock().v());
					break;
				case 4:
					if (i > lstOldChildTimeCount && i > lstSaveChildTimeCount) break;
					else if (i >= lstOldChildTimeCount)
						itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, null, lstSaveChildTime.get(i).getChildCareScheduleStart().v());
					else if (i >= lstSaveChildTimeCount)
						itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, lstOldChildTime.get(i).getChildCareScheduleStart().v(), null);
					else
						itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, lstOldChildTime.get(i).getChildCareScheduleStart().v(), lstSaveChildTime.get(i).getChildCareScheduleStart().v());
					break;
				case 5:
					if (i > lstOldChildTimeCount && i > lstSaveChildTimeCount) break;
					else if (i >= lstOldChildTimeCount)
						itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, null, lstSaveChildTime.get(i).getChildCareScheduleEnd().v());
					else if (i >= lstSaveChildTimeCount)
						itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, lstOldChildTime.get(i).getChildCareScheduleEnd().v(), null);
					else
						itemInfo = ItemInfo.create(item.scheduleItemId, item.scheduleItemName, DataValueAttribute.CLOCK, lstOldChildTime.get(i).getChildCareScheduleEnd().v(), lstSaveChildTime.get(i).getChildCareScheduleEnd().v());
					break;
				}
				
				if (itemInfo == null) break;
				
				DataCorrectionLog log = new DataCorrectionLog(operationId,
						new UserInfo(userContext.userId(), employeeId, employeeDto.getEmployeeName()),
						TargetDataType.SCHEDULE, 
						new TargetDataKey(CalendarKeyType.DATE, targetDate, item.scheduleItemName), 	
						CorrectionAttr.EDIT, 
						itemInfo,
						item.getDispOrder());
				lstLog.add(log);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return lstLog;
	}
}
