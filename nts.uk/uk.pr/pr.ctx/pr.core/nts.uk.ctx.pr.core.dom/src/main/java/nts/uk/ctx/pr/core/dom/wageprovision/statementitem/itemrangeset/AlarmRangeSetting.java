package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.itemrangeset;

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

	public AlarmRangeSetting(int alarmUpperLimitSetAtr, Long alarmUpRangeValAmount, Integer alarmUpRangeValTime,
			BigDecimal alarmUpRangeValNum, int alarmLowerLimitSetAtr, Long alarmLoRangeValAmount,
			Integer alarmLoRangeValTime, BigDecimal alarmLoRangeValNum) {
		this.alarmUpperLimitSetting = new AlarmUpperLimitSetting(alarmUpperLimitSetAtr, alarmUpRangeValAmount,
				alarmUpRangeValTime, alarmUpRangeValNum);
		this.alarmLowerLimitSetting = new AlarmLowerLimitSetting(alarmLowerLimitSetAtr, alarmLoRangeValAmount,
				alarmLoRangeValTime, alarmLoRangeValNum);
	}

}
