package nts.uk.ctx.at.function.dom.adapter.monthlyattendanceitem;

import lombok.Getter;

@Getter
public class MonthlyAttendanceItemFunImport {

	private String companyId;

	/* 勤怠項目ID */
	private int attendanceItemId;

	/* 勤怠項目名称 */
	private String attendanceName;

	/* 表示番号 */
	private int displayNumber;

	/* 使用区分 */
	private int userCanUpdateAtr;

	/* 勤怠項目属性 */
	private int attendanceAtr;

	/* ユーザーが値を変更できる */
	private int nameLineFeedPosition;

	public MonthlyAttendanceItemFunImport(String companyId, int attendanceItemId, String attendanceName,
			int displayNumber, int userCanUpdateAtr, int attendanceAtr, int nameLineFeedPosition) {
		super();
		this.companyId = companyId;
		this.attendanceItemId = attendanceItemId;
		this.attendanceName = attendanceName;
		this.displayNumber = displayNumber;
		this.userCanUpdateAtr = userCanUpdateAtr;
		this.attendanceAtr = attendanceAtr;
		this.nameLineFeedPosition = nameLineFeedPosition;
	}
}
