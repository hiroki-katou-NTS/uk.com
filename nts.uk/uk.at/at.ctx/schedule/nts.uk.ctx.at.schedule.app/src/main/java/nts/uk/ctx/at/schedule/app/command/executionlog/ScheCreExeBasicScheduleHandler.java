/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.executionlog;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.BasicScheduleSaveCommand;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.ScShortWorkTimeAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.ShortWorkTimeDto;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.ConfirmedAtr;
import nts.uk.ctx.at.shared.dom.worktime_old.WorkTime;
import nts.uk.ctx.at.shared.dom.worktime_old.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime_old.WorkTimeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

/**
 * The Class ScheCreExeBasicScheduleHandler.
 */
@Stateless
public class ScheCreExeBasicScheduleHandler {

	/** The sche cre exe work time handler. */
	@Inject
	private ScheCreExeWorkTimeHandler scheCreExeWorkTimeHandler;
	
	/** The work type repository. */
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	/** The work time repository. */
	@Inject
	private WorkTimeRepository workTimeRepository;
	
	/** The sc short work time adapter. */
	@Inject
	private ScShortWorkTimeAdapter scShortWorkTimeAdapter;
	
	/** The basic schedule repository. */
	@Inject
	private BasicScheduleRepository basicScheduleRepository;
	
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
	 * Gets the schedule break time.
	 *
	 * @param command the command
	 * @param worktypeCode the worktype code
	 * @param worktimeCode the worktime code
	 * @return the schedule break time
	 */
	// 休憩予定時間帯を取得する
	// TO DO
	private void getScheduleBreakTime(ScheduleCreatorExecutionCommand command, String worktypeCode,
			String worktimeCode) {
		
		if (this.scheCreExeWorkTimeHandler.checkNullOrDefaulCode(worktypeCode)) {
			return;
		}

		Optional<WorkType> optionalWorktype = this.workTypeRepository
				.findByPK(command.getCompanyId(), worktypeCode);
		if (optionalWorktype.isPresent()) {
			WorkType workType = optionalWorktype.get();

			if (this.scheCreExeWorkTimeHandler.checkHolidayWork(workType.getDailyWork())) {

				Optional<WorkTime> optionalWorktime = this.workTimeRepository
						.findByCode(command.getCompanyId(), worktimeCode);

				if (optionalWorktime.isPresent()) {
					WorkTime workTime = optionalWorktime.get();
					if (WorkTimeDailyAtr.Enum_Regular_Work.value == workTime.getWorkTimeDivision()
							.getWorkTimeDailyAtr().value) {
						switch (workTime.getWorkTimeDivision().getWorkTimeMethodSet()) {
							case Enum_Fixed_Work :

								break;

							default :
								break;
						}
					}
				}
			}
		}
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
		Optional<BasicSchedule> optionalBasicSchedule = this.basicScheduleRepository
				.find(command.getEmployeeId(), command.getYmd());
		if (optionalBasicSchedule.isPresent()) {
			this.basicScheduleRepository.update(command.toDomain());
		} else {
			this.basicScheduleRepository.insert(command.toDomain());
		}
	}
	
	/**
	 * Update all data to command save.
	 *
	 * @param command the command
	 * @param employeeId the employee id
	 * @param worktypeCode the worktype code
	 * @param workTimeCode the work time code
	 */
	public void updateAllDataToCommandSave(ScheduleCreatorExecutionCommand command,
			String employeeId, String worktypeCode, String workTimeCode) {
		this.getScheduleBreakTime(command, worktypeCode, workTimeCode);
		this.getShortWorkTime(employeeId, command.getToDate());
		BasicScheduleSaveCommand commandSave = new BasicScheduleSaveCommand();
		commandSave.setWorktypeCode(worktypeCode);
		commandSave.setEmployeeId(employeeId);
		commandSave.setWorktimeCode(workTimeCode);
		commandSave.setYmd(GeneralDate.today());
		commandSave.setConfirmedAtr(
				this.getConfirmedAtr(command.getIsConfirm(), ConfirmedAtr.CONFIRMED).value);
		if (command.getIsDeleteBeforInsert()) {
			this.basicScheduleRepository.delete(employeeId, command.getToDate());
		}
		this.saveBasicSchedule(commandSave);;
	}
	
}
