package nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.time;

import java.math.BigDecimal;

/**
 * 10進表記時間の1分未満の端数処理
 */
public enum Time10BasedRounding {
	/**
	 * 切り捨て
	 */
	ROUND_DOWN(1, "Enum_Time10BasedRounding_ROUND_DOWN"), 
	/**
	 * 切り上げ
	 */
	ROUND_UP(2, "Enum_Time10BasedRounding_ROUND_UP"), 
	/**
	 * 四捨五入
	 */
	ROUND(3, "Enum_Time10BasedRounding_ROUND");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private Time10BasedRounding(int value, String nameId) {
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
		case ROUND_DOWN:
			// 切り捨て
			return target.setScale(0, BigDecimal.ROUND_DOWN).longValue();
		case ROUND_UP:
			// 切り上げ
			return target.setScale(0, BigDecimal.ROUND_UP).longValue();
		case ROUND:
			// 四捨五入
			return target.setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
		default:
			throw new RuntimeException("存在しない端数処理が指定されました。");
		}
	}
}
