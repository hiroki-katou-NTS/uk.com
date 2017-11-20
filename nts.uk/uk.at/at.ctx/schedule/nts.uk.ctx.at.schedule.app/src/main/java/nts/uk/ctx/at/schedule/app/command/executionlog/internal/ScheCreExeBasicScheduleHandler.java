/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.executionlog.internal;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.app.command.executionlog.ScheduleCreatorExecutionCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.BasicScheduleSaveCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.ChildCareScheduleSaveCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.WorkScheduleTimeZoneSaveCommand;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.ScShortWorkTimeAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.ShortChildCareFrameDto;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.ShortWorkTimeDto;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.ConfirmedAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.BounceAtr;
import nts.uk.ctx.at.shared.dom.worktimeset_old.Timezone;
import nts.uk.ctx.at.shared.dom.worktimeset_old.WorkTimeSet;

/**
 * The Class ScheCreExeBasicScheduleHandler.
 */
@Stateless
public class ScheCreExeBasicScheduleHandler {
	
	/** The sc short work time adapter. */
	@Inject
	private ScShortWorkTimeAdapter scShortWorkTimeAdapter;
	
	/** The basic schedule repository. */
	@Inject
	private BasicScheduleRepository basicScheduleRepository;
	
	/**
	 * Update all data to command save.
	 *
	 * @param command the command
	 * @param employeeId the employee id
	 * @param worktypeCode the worktype code
	 * @param workTimeCode the work time code
	 */
	public void updateAllDataToCommandSave(ScheduleCreatorExecutionCommand command, String employeeId,
			String worktypeCode, String workTimeCode, WorkTimeSet workTimeSet) {

		// get short work time
		Optional<ShortWorkTimeDto> optionalShortTime = this.getShortWorkTime(employeeId, command.getToDate());

		// add command save
		BasicScheduleSaveCommand commandSave = new BasicScheduleSaveCommand();
		commandSave.setWorktypeCode(worktypeCode);
		commandSave.setEmployeeId(employeeId);
		commandSave.setWorktimeCode(workTimeCode);
		commandSave.setYmd(GeneralDate.today());
		
		if(optionalShortTime.isPresent()){
			commandSave
					.setChildCareSchedules(
							optionalShortTime.get().getLstTimeSlot().stream()
									.map(shortime -> this.convertShortTimeChildCareToChildCare(shortime,
											optionalShortTime.get().getChildCareAtr().value))
									.collect(Collectors.toList()));
		}

		if (workTimeSet != null) {
			commandSave.setWorkScheduleTimeZones(workTimeSet.getPrescribedTimezoneSetting().getTimezone().stream()
					.map(timezone -> this.convertTimeZoneToScheduleTimeZone(timezone)).collect(Collectors.toList()));
		}
		// update is confirm
		commandSave.setConfirmedAtr(this.getConfirmedAtr(command.getIsConfirm(), ConfirmedAtr.CONFIRMED).value);

		// check parameter is delete before insert
		if (command.getIsDeleteBeforInsert()) {
			this.basicScheduleRepository.delete(employeeId, command.getToDate());
		}

		// save command
		this.saveBasicSchedule(commandSave);;
	}
	
	/**
	 * Gets the short work time.
	 *
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the short work time
	 */
	// アルゴリズム (WorkTime)
	private Optional<ShortWorkTimeDto> getShortWorkTime(String employeeId, GeneralDate baseDate) {
		return this.scShortWorkTimeAdapter.findShortWorkTime(employeeId, baseDate);
	}
	
	
	/**
	 * Gets the confirmed atr.
	 *
	 * @param isConfirmContent the is confirm content
	 * @param confirmedAtr the confirmed atr
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
	 * @param command the command
	 */
	// 勤務予定情報を登録する
	private void saveBasicSchedule(BasicScheduleSaveCommand command) {

		// find basic schedule by id
		Optional<BasicSchedule> optionalBasicSchedule = this.basicScheduleRepository.find(command.getEmployeeId(),
				command.getYmd());

		// check exist data
		if (optionalBasicSchedule.isPresent()) {

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
	 * @param shortChildCareFrameDto the short child care frame dto
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
	 * Convert time zone to schedule time zone.
	 *
	 * @param timezone the timezone
	 * @return the work schedule time zone save command
	 */
	// 勤務予定時間帯
	private WorkScheduleTimeZoneSaveCommand convertTimeZoneToScheduleTimeZone(Timezone timezone){
		WorkScheduleTimeZoneSaveCommand command = new WorkScheduleTimeZoneSaveCommand();
		
		// 予定勤務回数 = 取得した勤務予定時間帯. 勤務NO
		command.setScheduleCnt(timezone.getWorkNo());
		
		// 予定開始時刻 = 取得した勤務予定時間帯. 開始
		command.setScheduleStartClock(timezone.getStart().valueAsMinutes());
		
		// 予定終了時刻 = 取得した勤務予定時間帯. 終了
		command.setScheduleEndClock(timezone.getEnd().valueAsMinutes());
		
		// TODO NOT WorktypeSet
		command.setBounceAtr(BounceAtr.NO_DIRECT_BOUNCE.value);
		return command;
	}
}
