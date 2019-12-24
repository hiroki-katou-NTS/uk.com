package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.itemrangeset;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;

/**
 * アラーム下限設定
 */
@Getter
public class AlarmLowerLimitSetting extends DomainObject {

	/**
	 * アラーム下限値設定区分
	 */
	private RangeSettingEnum alarmLowerLimitSettingAtr;

	/**
	 * アラーム上限値金額
	 */
	private Optional<MonetaryValue> alarmLowerRangeValueAmount;

	/**
	 * アラーム上限値時間
	 */
	private Optional<TimeValue> alarmLowerRangeValueTime;

	/**
	 * アラーム上限値回数
	 */
	private Optional<TimesValue> alarmLowerRangeValueNum;
	
	public AlarmLowerLimitSetting(int alarmLowerLimitSetAtr, Long alarmLoRangeValAmount,
			Integer alarmLoRangeValTime, BigDecimal alarmLoRangeValNum) {
		this.alarmLowerLimitSettingAtr =  EnumAdaptor.valueOf(alarmLowerLimitSetAtr, RangeSettingEnum.class);
		this.alarmLowerRangeValueAmount = alarmLoRangeValAmount == null ? Optional.empty() : Optional.of(new MonetaryValue(alarmLoRangeValAmount));
		this.alarmLowerRangeValueTime = alarmLoRangeValTime == null ? Optional.empty() : Optional.of(new TimeValue(alarmLoRangeValTime));
		this.alarmLowerRangeValueNum = alarmLoRangeValNum == null ? Optional.empty() : Optional.of(new TimesValue(alarmLoRangeValNum));
	}

}
