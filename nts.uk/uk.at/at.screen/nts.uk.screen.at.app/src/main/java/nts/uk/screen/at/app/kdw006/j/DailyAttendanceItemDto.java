package nts.uk.screen.at.app.kdw006.j;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;

/**
 * 
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DailyAttendanceItemDto {


	/* 勤怠項目ID */
	private int attendanceItemId;

	/* 勤怠項目名称 */
	private String attendanceName;

	/* 表示番号 */
	private int displayNumber;

	public DailyAttendanceItemDto(AttItemName param) {
		super();
		this.attendanceItemId = param.getAttendanceItemId();
		this.attendanceName = param.getAttendanceItemName();
		this.displayNumber = param.getAttendanceItemDisplayNumber();
	}

}
