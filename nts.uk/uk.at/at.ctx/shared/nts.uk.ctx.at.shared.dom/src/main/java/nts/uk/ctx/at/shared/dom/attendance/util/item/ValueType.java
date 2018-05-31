package nts.uk.ctx.at.shared.dom.attendance.util.item;

public enum ValueType {
	
	INTEGER(0, "INTEGER", "回数、時間、時刻"),
	STRING(1, "STRING", "コード、文字"),
	DECIMAL(2, "DECIMAL", "回数"),
	DATE(3, "DATE", "年月日"),
	BOOLEAN(4, "BOOLEAN", "年月日"),
	DOUBLE(5, "DOUBLE", "日数");

	public final int value;
	public final String name;
	public final String description;
	
	private ValueType (int value, String name, String description){
		this.value = value;
		this.name = name;
		this.description = description;
	}
	
	/**
	 * INTEGERであるか判定する
	 * @return　INTEGERである
	 */
	public boolean isInteger() {
		return INTEGER.equals(this);
	}
}
