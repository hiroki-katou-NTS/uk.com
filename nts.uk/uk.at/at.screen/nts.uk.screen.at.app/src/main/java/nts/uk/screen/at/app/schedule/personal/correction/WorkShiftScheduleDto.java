package nts.uk.screen.at.app.schedule.personal.correction;

import java.util.Optional;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.workschedule.domainservice.ShiftEditState;

/**
 * @author anhdt
 * 	勤務予定（シフト）dto
 */
@Data
public class WorkShiftScheduleDto {
	
	// シフトコード
	private Optional<String> shiftCode;
	// シフトの編集状態
	private Optional<ShiftEditState> editState;
	// シフト名称
	private Optional<String> shiftName;
	// データがあるか
	private boolean haveData;
	// 出勤休日区分
	private Optional<Integer> workHolidayClassification;
	// 勤務予定が必要か
	private boolean needToWork;
	// 実績か
	private boolean achievements;
	// 年月日
	private GeneralDate date;
	// 応援か
	private Integer supportCategory;
	// 確定済みか
	private boolean confirmed;
	// 社員ID
	private String employeeId;
	
	
//	public WorkShiftScheduleDto(ScheManaStatuTempo scheduleManagementState, WorkSchedule workSchedule) {
//		this.employeeId = scheduleManagementState.getEmployeeID();
//		// TODO: hieu lam not nhe
//	}
}
