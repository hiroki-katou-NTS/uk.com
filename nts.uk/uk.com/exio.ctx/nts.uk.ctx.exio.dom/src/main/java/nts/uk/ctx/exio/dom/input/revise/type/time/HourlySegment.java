package nts.uk.ctx.exio.dom.input.revise.type.time;

import java.math.BigDecimal;

import lombok.val;

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
	 * 時分から分へ変換
	 * @param target
	 * @param rounding
	 * @return
	 */
	public Long hourToMinute(BigDecimal target, TimeRounding rounding) {
		// 分へ変換
		val min = target.multiply(new BigDecimal("60.00"));
		// 端数を指定された方法で処理する
		return min.setScale(0, BigDecimal.ROUND_UP).longValue();
	}
}
