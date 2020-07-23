package nts.uk.screen.at.app.ksu001.getworkscheduleshift;

import java.util.Optional;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.workschedule.domainservice.ShiftEditState;

/**
 * @author laitv
 * 	勤務予定（シフト）dto
 */
@Data
public class WorkScheduleShiftDto {
	
	// 社員ID
	private String employeeId;
	// 年月日
	private GeneralDate date;
	// データがあるか
	private boolean haveData;
	// 勤務予定が必要か
	private boolean needToWork;
	// 実績か
	private boolean achievements;
	// 確定済みか
	private boolean confirmed;
	// 応援か
	private Integer supportCategory;
	// シフトコード
	private Optional<String> shiftCode;
	// シフトの編集状態
	private Optional<ShiftEditState> editState;
	// シフト名称
	private Optional<String> shiftName;

	// 出勤休日区分
	private Optional<Integer> workHolidayClassification;

}
