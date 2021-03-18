package nts.uk.screen.com.app.find.ccg005.attendance.information.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttendanceDetailDto {

	// 勤務の色
	private Integer workColor;

	// 勤務名
	private String workName;

	// 終了の色
	private Integer checkOutColor;

	// 終了時刻
	private String checkOutTime;

	// 開始の色
	private Integer checkInColor;

	// 開始時刻
	private String checkInTime;

	// 勤務区分
	private Integer workDivision;
}
