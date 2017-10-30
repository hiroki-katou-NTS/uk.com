package nts.uk.ctx.at.schedule.dom.adapter.dailyattendanceitem;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScDailyAttendanceItemDto {
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
	private int dailyAttendanceAtr;

	/* ユーザーが値を変更できる */
	private int nameLineFeedPosition;
}
