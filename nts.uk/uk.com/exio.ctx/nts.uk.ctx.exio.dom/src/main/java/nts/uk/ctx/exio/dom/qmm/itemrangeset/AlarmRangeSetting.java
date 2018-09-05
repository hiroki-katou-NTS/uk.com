package nts.uk.ctx.exio.dom.qmm.itemrangeset;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * アラーム範囲設定
 */
@Getter
public class AlarmRangeSetting extends DomainObject {

	/**
	 * アラーム上限設定
	 */
	private AlarmUpperLimitSetting alarmUpperLimitSetting;

	/**
	 * アラーム下限設定
	 */
	private AlarmLowerLimitSetting alarmLowerLimitSetting;

	public AlarmRangeSetting(int alarmUpperLimitSetAtr, BigDecimal alarmUpRangeValAmount, Integer alarmUpRangeValTime,
			BigDecimal alarmUpRangeValNum, int alarmLowerLimitSetAtr, BigDecimal alarmLoRangeValAmount,
			Integer alarmLoRangeValTime, BigDecimal alarmLoRangeValNum) {
		this.alarmUpperLimitSetting = new AlarmUpperLimitSetting(alarmUpperLimitSetAtr, alarmUpRangeValAmount,
				alarmUpRangeValTime, alarmUpRangeValNum);
		this.alarmLowerLimitSetting = new AlarmLowerLimitSetting(alarmLowerLimitSetAtr, alarmLoRangeValAmount,
				alarmLoRangeValTime, alarmLoRangeValNum);
	}

}
