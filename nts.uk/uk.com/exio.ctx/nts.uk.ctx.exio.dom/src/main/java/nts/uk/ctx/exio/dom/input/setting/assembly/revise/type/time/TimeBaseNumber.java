package nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.time;

/**
 * 時間・時刻進数
 */
public enum TimeBaseNumber {
	
	/** 60進数 */
	HEXA_DECIMAL(0, "60進数"), 
	
	/** 10進数 */
	DECIMAL(1, "10進数");
	
	/** The value. */
	public final int value;
	
	/** The name id. */
	public final String nameId;
	
	private TimeBaseNumber(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
