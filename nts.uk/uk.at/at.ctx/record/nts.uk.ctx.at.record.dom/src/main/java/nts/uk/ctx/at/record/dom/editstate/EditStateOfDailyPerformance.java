package nts.uk.ctx.at.record.dom.editstate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;

/**
 * 
 * @author nampt 日別実績の編集状態 - root
 *
 */
@Getter
@NoArgsConstructor
public class EditStateOfDailyPerformance extends AggregateRoot {

	/** 社員ID: 社員ID */
	private String employeeId;
	
	/** 処理年月日: 年月日 */
	private GeneralDate ymd;
	/** 編集状態 */
	private EditStateOfDailyAttd editState;

	public EditStateOfDailyPerformance(String employeeId, int attendanceItemId, GeneralDate ymd,
			EditStateSetting editStateSetting) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
		this.editState = new EditStateOfDailyAttd(attendanceItemId, editStateSetting);
	}

	public EditStateOfDailyPerformance(String employeeId, GeneralDate ymd, EditStateOfDailyAttd editState) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
		this.editState = editState;
	}


	public boolean isHandCorrect() {
		return this.editState.getEditStateSetting() == EditStateSetting.HAND_CORRECTION_MYSELF
				|| this.editState.getEditStateSetting() == EditStateSetting.HAND_CORRECTION_OTHER;
	}
}
