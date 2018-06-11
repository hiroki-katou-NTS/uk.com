package nts.uk.file.at.app.export.dailyschedule.totalsum;

/**
 * 回数項目
 * @author HoangNDH
 *
 */
public enum NumberOfItem {
	// 遅刻時間1
	LATE_COME_1(592, 1),
	// 遅刻時間2
	LATE_COME_2(598, 1),
	// 早退回数1
	EARLY_LEAVE_1(604, 1),
	// 早退回数2
	EARLY_LEAVE_2(610, 1);
	
	public int attendanceId;
	
	public int value;

	private NumberOfItem(int attendanceId, int value) {
		this.attendanceId = attendanceId;
		this.value = value;
	}
}
