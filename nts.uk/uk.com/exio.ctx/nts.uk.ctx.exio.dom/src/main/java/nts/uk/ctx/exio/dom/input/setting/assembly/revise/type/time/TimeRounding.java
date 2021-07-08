package nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.time;

import java.math.BigDecimal;

/**
 * 時間・時刻端数処理
 */
public enum TimeRounding {
	/**
	 * 1分未満切り捨て
	 */
	DOWN_LESS_1_MINUTE(1, "Enum_TimeRounding_DOWN_LESS_1_MINUTE"), 
	/**
	 * 1分未満切り上げ
	 */
	UP_LESS_1_MINUTE(2, "Enum_TimeRounding_UP_LESS_1_MINUTE"), 
	/**
	 * 1分未満四捨五入（小数点第1位迄）
	 */
	HALF_UP_LESS_1_MINUTE(3, "Enum_TimeRounding_OFF_TO_LESS_1_MINUTE");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private TimeRounding(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
	/**
	 * 丸める
	 * @param target
	 * @return
	 */
	public Long round(BigDecimal target) {
		switch(this) {
		case DOWN_LESS_1_MINUTE:
			// 切り捨て
			return target.setScale(0, BigDecimal.ROUND_DOWN).longValue();
		case UP_LESS_1_MINUTE:
			// 切り上げ
			return target.setScale(0, BigDecimal.ROUND_UP).longValue();
		case HALF_UP_LESS_1_MINUTE:
			// 四捨五入
			return target.setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
		default:
			throw new RuntimeException("存在しない端数処理が指定されました。");
		}
	}
}
