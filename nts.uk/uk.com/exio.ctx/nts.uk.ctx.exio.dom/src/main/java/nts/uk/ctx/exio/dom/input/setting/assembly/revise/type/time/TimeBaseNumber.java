package nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.time;

/**
 * 時間・時刻進数
 */
public enum TimeBaseNumber {
	
	/** 60進数 */
	SEXAGESIMAL(0, "Enum_TimeBaseNumber_SEXAGESIMAL"), 
	
	/** 10進数 */
	DECIMAL(1, "Enum_TimeBaseNumber_DECIMAL");
	
	/** The value. */
	public final int value;
	
	/** The name id. */
	public final String nameId;
	
	private TimeBaseNumber(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
