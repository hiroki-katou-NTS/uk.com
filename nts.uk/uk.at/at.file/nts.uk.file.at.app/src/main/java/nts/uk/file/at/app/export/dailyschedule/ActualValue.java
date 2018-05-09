package nts.uk.file.at.app.export.dailyschedule;

import lombok.Value;

/**
 * 実績値
 * @author HoangNDH
 *
 */
@Value
public class ActualValue {
	// 勤怠項目ID
	private int attendanceId;
	// 値
	private int value;
}
