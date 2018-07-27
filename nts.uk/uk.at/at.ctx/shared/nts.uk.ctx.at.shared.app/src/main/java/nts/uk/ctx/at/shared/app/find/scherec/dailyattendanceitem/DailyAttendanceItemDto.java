package nts.uk.ctx.at.shared.app.find.scherec.dailyattendanceitem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItem;

@Getter
@Setter
@NoArgsConstructor
public class DailyAttendanceItemDto {

	private String companyId;

	/* 勤怠項目ID */
	private int attendanceItemId;

	/* 勤怠項目名称 */
	private String attendanceName;

	/* 表示番号 */
	private int displayNumber;

	/* ユーザーが値を変更できる */
	private int userCanUpdateAtr;

	/* 勤怠項目属性 */
	private int dailyAttendanceAtr;

	/* 名称の改行位置*/
	private int nameLineFeedPosition;

	public DailyAttendanceItemDto(String companyId, int attendanceItemId, String attendanceName, int displayNumber, int userCanUpdateAtr, int dailyAttendanceAtr, int nameLineFeedPosition) {
		super();
		this.companyId = companyId;
		this.attendanceItemId = attendanceItemId;
		this.attendanceName = attendanceName;
		this.displayNumber = displayNumber;
		this.userCanUpdateAtr = userCanUpdateAtr;
		this.dailyAttendanceAtr = dailyAttendanceAtr;
		this.nameLineFeedPosition = nameLineFeedPosition;
	}
	
	public static DailyAttendanceItemDto fromDomain(DailyAttendanceItem domain) {
		return new DailyAttendanceItemDto(
				domain.getCompanyId(),
				domain.getAttendanceItemId(),
				domain.getAttendanceName().v(),
				domain.getDisplayNumber(),
				domain.getUserCanUpdateAtr().value,
				domain.getDailyAttendanceAtr().value,
				domain.getNameLineFeedPosition()
				);
	}
	
}
