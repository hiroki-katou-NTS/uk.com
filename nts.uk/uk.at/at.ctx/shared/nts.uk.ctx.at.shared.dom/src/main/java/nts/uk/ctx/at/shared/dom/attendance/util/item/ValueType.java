package nts.uk.ctx.at.shared.dom.attendance.util.item;

public enum ValueType {

	TIME(1, "TIME", "時間"),
	CLOCK(2, "CLOCK", "時刻"),
	CODE(3, "CODE", "コード"),
	TEXT(4, "TEXT", "文字"),
	DATE(5, "DATE", "年月日"),
	RATE(6, "DOUBLE", "率"),
	COUNT(7, "COUNT", "回数"),
	COUNT_WITH_DECIMAL(8, "COUNT", "回数"),
	FLAG(9, "FLAG", "フラグ"),
	ATTR(10, "ATTR", "区分"),
	UNKNOWN(11, "UNKNOWN", "UNKNOWN"),
	DAYS(12, "DAYS", "日数"),
	AMOUNT(13, "AMOUNT", "金額"),
	NUMBER(14, "NUMBER", "数"),
	TIME_WITH_DAY(15, "TIME_WITH_DAY", "時刻（日区分付き）");

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
		return TIME.equals(this) || CLOCK.equals(this) || ATTR.equals(this)
				|| NUMBER.equals(this) || COUNT.equals(this) || TIME_WITH_DAY.equals(this);
	}
	
	/**
	 * Doubleであるか判定する
	 * @return　Doubleである
	 */
	public boolean isDouble() {
		return RATE.equals(this) || COUNT_WITH_DECIMAL.equals(this) || DAYS.equals(this) || AMOUNT.equals(this);
	}
	
	/**
	 * Booleanであるか判定する
	 * @return　Booleanである
	 */
	public boolean isBoolean() {
		return FLAG.equals(this);
	}
	
	/**
	 * DATEであるか判定する
	 * @return　DATEである
	 */
	public boolean isDate() {
		return DATE.equals(this);
	}
	
	/**
	 * Stringであるか判定する
	 * @return　Stringである
	 */
	public boolean isString() {
		return CODE.equals(this) || TEXT.equals(this);
	}
	
	/**
	 * Unknownであるか判定する
	 * @return　Unknownである
	 */
	public boolean isUnknownType() {
		return UNKNOWN.equals(this);
	}
	
	/**
	 * INTEGERであるか判定する (countable)
	 * Added by HoangNDH for exporting purpose
	 * @return
	 */
	public boolean isIntegerCountable() {
		return COUNT.equals(this) || TIME.equals(this);
	}
	
	/**
	 * Doubleであるか判定する (countable
	 * Added by HoangNDH for exporting purpose
	 * @return　Doubleである
	 */
	public boolean isDoubleCountable() {
		return COUNT_WITH_DECIMAL.equals(this) || AMOUNT.equals(this) || DAYS.equals(this);
	}
	
	public boolean isTime() {
		return TIME.equals(this) || CLOCK.equals(this) || TIME_WITH_DAY.equals(this);
	}
}
