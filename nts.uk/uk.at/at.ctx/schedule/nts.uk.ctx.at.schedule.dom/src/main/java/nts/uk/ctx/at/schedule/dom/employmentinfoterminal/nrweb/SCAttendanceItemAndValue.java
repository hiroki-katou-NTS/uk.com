package nts.uk.ctx.at.schedule.dom.employmentinfoterminal.nrweb;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;

/**
 * @author thanh_nx
 *
 *         勤怠項目と値
 */
@Getter
public class SCAttendanceItemAndValue {

	// NO
	private int no;

	// 勤怠項目ID
	private int attendanceItemId;

	// 勤怠項目名称
	private String name;

	// 値
	private String value;

	// 日別勤怠の編集状態
	private EditStateSetting state;

	public SCAttendanceItemAndValue(int no, int attendanceItemId, String name, String value, EditStateSetting state) {
		this.no = no;
		this.attendanceItemId = attendanceItemId;
		this.name = name;
		this.value = value;
		this.state = state;
	}

}
