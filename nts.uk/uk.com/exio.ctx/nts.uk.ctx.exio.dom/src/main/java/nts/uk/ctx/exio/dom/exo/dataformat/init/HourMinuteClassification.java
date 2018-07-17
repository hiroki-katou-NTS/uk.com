package nts.uk.ctx.exio.dom.exo.dataformat.init;

/**
 * 
 * @author TamNX 時分区分
 *
 */
public enum HourMinuteClassification {
	
	//時分
	HOUR_AND_MINUTE(0, "Enum_HourMinuteClassification_HOUR_AND_MINUTE"), 
	//分
	MINUTE(1, "Enum_HourMinuteClassification_MINUTE");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private HourMinuteClassification(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
