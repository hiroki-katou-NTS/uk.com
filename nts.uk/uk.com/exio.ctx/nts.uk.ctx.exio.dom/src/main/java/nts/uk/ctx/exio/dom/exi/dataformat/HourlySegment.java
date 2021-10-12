package nts.uk.ctx.exio.dom.exi.dataformat;

/**
 * 
 * @author DatLH 時分区分
 *
 */
public enum HourlySegment {
	/**
	 * 時分
	 */
	HOUR_MINUTE(0, "時分"), 
	/**
	 * 分 
	 */
	MINUTE(1, "分");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private HourlySegment(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
