package nts.uk.file.at.app.export.dailyschedule.totalsum;

import lombok.Data;

/**
 * 合算値
 * @author HoangNDH
 *
 */
@Data
public class TotalValue {
	// 勤怠項目ID
	private int attendanceId;
	// 値
	private int value;
}
