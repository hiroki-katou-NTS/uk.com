package nts.uk.ctx.at.function.dom.alarm.extractionrange.month;
/**
 * 開始月の指定方法
 * @author phongtq
 *
 */
public enum SpecifyStartMonth {
	
	/** 締め開始月を指定する */
	DESIGNATE_CLOSE_START_MONTH(1, "締め開始月を指定する"), // Chỉ định tháng bắt đầu chốt

	/** 固定の月度を指定する */
	SPECIFY_FIXED_MOON_DEGREE(2, "固定の月度を指定する"); // Chỉ định tháng cố định
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private SpecifyStartMonth(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
