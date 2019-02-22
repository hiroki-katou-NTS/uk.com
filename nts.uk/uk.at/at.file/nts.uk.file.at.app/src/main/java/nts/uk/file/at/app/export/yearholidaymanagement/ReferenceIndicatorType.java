package nts.uk.file.at.app.export.yearholidaymanagement;

//参照区分
public enum ReferenceIndicatorType {

	// 実績
	PERFORMANCE(0, "Enum_ReferenceIndicatorType_PERFORMANCE"),
	// 予定・申請含
	SCHEDULE_APLICATION(0, "Enum_ReferenceIndicatorType_SCHEDULE_APLICATION");

	public int value;

	public String name;

	ReferenceIndicatorType(int value, String name) {
		this.value = value;
		this.name = name;
	}
}
