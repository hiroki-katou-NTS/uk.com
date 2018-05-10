package nts.uk.file.at.app.export.dailyschedule.totalsum;

/**
 * 回数項目
 * @author HoangNDH
 *
 */
public enum NumberOfItem {
	// 遅刻時間1
	Late_Come_1(592, 1),
	// 遅刻時間2
	Late_Come_2(598, 1),
	// 早退回数1
	Early_Leave_1(604, 1),
	// 早退回数2
	Early_Leave_2(610, 1);
	
	public int attendanceId;
	
	public int value;

	private NumberOfItem(int attendanceId, int value) {
		this.attendanceId = attendanceId;
		this.value = value;
	}
}
