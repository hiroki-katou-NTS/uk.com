package nts.uk.file.at.app.export.yearholidaymanagement;

//改頁区分
public enum BreakPageType {
	// なし
	NONE(0, "Enum_BreakPageType_NONE"),
	// 職場
	WORKPLACE(1, "Enum_BreakPageType_WORKPLACE");

	public int value;

	public String name;

	BreakPageType(int value, String name) {
		this.value = value;
		this.name = name;
	}
}
