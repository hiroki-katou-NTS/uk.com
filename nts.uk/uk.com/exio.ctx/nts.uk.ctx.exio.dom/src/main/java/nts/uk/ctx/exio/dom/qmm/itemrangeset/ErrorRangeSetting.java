package nts.uk.ctx.exio.dom.qmm.itemrangeset;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * エラー範囲設定
 */
@Getter
public class ErrorRangeSetting extends DomainObject {

	/**
	 * エラー上限値設定
	 */
	private ErrorUpperLimitSetting upperLimitSetting;

	/**
	 * エラー下限値設定
	 */
	private ErrorLowerLimitSetting lowerLimitSetting;

	public ErrorRangeSetting(int errorUpperLimitSetAtr, BigDecimal errorUpRangeValAmount, Integer errorUpRangeValTime,
			BigDecimal errorUpRangeValNum, int errorLowerLimitSetAtr, BigDecimal errorLoRangeValAmount,
			Integer errorLoRangeValTime, BigDecimal errorLoRangeValNum) {
		this.upperLimitSetting = new ErrorUpperLimitSetting(errorUpperLimitSetAtr, errorUpRangeValAmount,
				errorUpRangeValTime, errorUpRangeValNum);
		this.lowerLimitSetting = new ErrorLowerLimitSetting(errorLowerLimitSetAtr, errorLoRangeValAmount,
				errorLoRangeValTime, errorLoRangeValNum);
	}

}
