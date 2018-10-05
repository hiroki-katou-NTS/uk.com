package nts.uk.screen.at.app.monthlyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.attendance.AttendanceName;

@Data
@AllArgsConstructor
public class MonthlyAttendanceItemDto {
	/** The company id. */
	// 会社ID
	private String companyId;

	/** The attendance item id. */
	// 勤怠項目ID
	private int attendanceItemId;

	/** The attendance name. */
	// 勤怠項目名称
	private String attendanceName;

	/** The display number. */
	// 表示番号
	private int displayNumber;

	/** The user can update atr. */
	// 使用区分
	private int userCanUpdateAtr;

	/** The monthly attendance atr. */
	// 勤怠項目属性
	private int monthlyAttendanceAtr;

	/** The name line feed position. */
	// ユーザーが値を変更できる
	private int nameLineFeedPosition;
	
	private Integer primitive;

}
