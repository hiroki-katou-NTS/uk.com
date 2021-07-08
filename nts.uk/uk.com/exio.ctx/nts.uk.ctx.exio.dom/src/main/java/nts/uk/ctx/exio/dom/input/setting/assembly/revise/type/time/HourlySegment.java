package nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.time;

import java.math.BigDecimal;

/**
 * 時分区分
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
	
	/**
	 * 分へ変換
	 * @param target
	 * @param rounding
	 * @return
	 */
	public BigDecimal toMinute(BigDecimal target) {
		
		if (this == MINUTE) {
			return target;
		}
		
		// 分へ変換
		return target.multiply(new BigDecimal("60.00"));
	}
}
