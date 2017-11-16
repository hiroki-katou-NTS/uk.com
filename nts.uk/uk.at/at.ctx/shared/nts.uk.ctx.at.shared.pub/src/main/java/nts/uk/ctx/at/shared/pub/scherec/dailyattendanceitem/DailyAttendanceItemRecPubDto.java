package nts.uk.ctx.at.shared.pub.scherec.dailyattendanceitem;

import lombok.Builder;
import lombok.Data;

/**
 * 
 * @author nampt
 *
 */
@Data
@Builder
public class DailyAttendanceItemRecPubDto {
	
	private String companyId;

	/* 勤�?�?目ID */
	private int attendanceItemId;

	/* 勤�?�?目名称 */
	private String attendanceName;

	/* 表示番号 */
	private int displayNumber;

	/* 使用区�? */
	private int userCanUpdateAtr;

	/* 勤�?�?目属�?� */
	private int dailyAttendanceAtr;

	/* ユーザーが�?�を変更できる */
	private int nameLineFeedPosition;

	public DailyAttendanceItemRecPubDto(String companyId, int attendanceItemId, String attendanceName, int displayNumber,
			int userCanUpdateAtr, int dailyAttendanceAtr, int nameLineFeedPosition) {
		super();
		this.companyId = companyId;
		this.attendanceItemId = attendanceItemId;
		this.attendanceName = attendanceName;
		this.displayNumber = displayNumber;
		this.userCanUpdateAtr = userCanUpdateAtr;
		this.dailyAttendanceAtr = dailyAttendanceAtr;
		this.nameLineFeedPosition = nameLineFeedPosition;
	}

}
