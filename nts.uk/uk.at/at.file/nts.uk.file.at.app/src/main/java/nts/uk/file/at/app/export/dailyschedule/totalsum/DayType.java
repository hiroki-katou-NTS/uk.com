package nts.uk.file.at.app.export.dailyschedule.totalsum;

/**
 * 日数項目
 * Enum value is based on class WorkTypeClassification
 * @author HoangNDH
 *
 */
public enum DayType {
	// 出勤
	HOLIDAY(1),
	// 年休
	ANNUAL_HOLIDAY(2),
	// 積立年休
	YEARLY_RESERVED(3),
	// 特休
	SPECIAL(4),
	// 欠勤
	ABSENCE(5),
	// 休出
	OFF_WORK(11);
	
	public int value;
	
	private DayType(int value) {
		this.value = value;
	}
}
