package nts.uk.ctx.at.shared.dom.application.reflectprocess.cancellation;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;

/**
 * @author thanh_nx
 *
 *         申請反映前の勤怠
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AttendanceBeforeApplicationReflect {
	
	// 勤怠項目ID
	private int attendanceId;

	// 値
	private String value;

	// 編集状態
	private Optional<EditStateOfDailyAttd> editState;
}
