package nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.time;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 10進表記時間の1分未満の端数処理
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum TimeBase10Rounding {
	/**
	 * 切り捨て
	 */
	ROUND_DOWN(1, "Enum_TimeBase10Rounding_ROUND_DOWN"), 
	/**
	 * 切り上げ
	 */
	ROUND_UP(2, "Enum_TimeBase10Rounding_ROUND_UP"), 
	/**
	 * 四捨五入
	 */
	ROUND(3, "Enum_TimeBase10Rounding_ROUND");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;
	
	public static TimeBase10Rounding valueOf(int value) {
		return EnumAdaptor.valueOf(value, TimeBase10Rounding.class);
	}
	
	/**
	 * 丸める
	 * @param target
	 * @return
	 */
	public int round(BigDecimal target) {
		switch(this) {
		case ROUND_DOWN:
			// 切り捨て
			return target.setScale(0, BigDecimal.ROUND_DOWN).intValue();
		case ROUND_UP:
			// 切り上げ
			return target.setScale(0, BigDecimal.ROUND_UP).intValue();
		case ROUND:
			// 四捨五入
			return target.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
		default:
			throw new RuntimeException("存在しない端数処理が指定されました。");
		}
	}
}
