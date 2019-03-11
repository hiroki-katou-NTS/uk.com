package nts.uk.file.at.app.export.yearholidaymanagement;

//出力する対象期間
public enum PeriodToOutput {
	// 現在
	CURRENT(0, "Enum_PeriodToOutput_CURRENT"),
	// 過去
	PAST(1, "Enum_PeriodToOutput_PAST");

	public int value;

	public String name;

	PeriodToOutput(int value, String name) {
		this.value = value;
		this.name = name;
	}
}
