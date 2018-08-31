package nts.uk.ctx.exio.dom.qmm.itemrangeset;

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
	private RangeSettingEnum lowerLimitSettingAtr;

	/**
	 * アラーム上限値金額
	 */
	private Optional<MonetaryValue> rangeValueAmount;

	/**
	 * アラーム上限値時間
	 */
	private Optional<TimeValue> rangeValueTime;

	/**
	 * アラーム上限値回数
	 */
	private Optional<TimesValue> rangeValueNum;
	
	public AlarmLowerLimitSetting(int alarmLowerLimitSetAtr, BigDecimal alarmLoRangeValAmount,
			Integer alarmLoRangeValTime, BigDecimal alarmLoRangeValNum) {
		this.lowerLimitSettingAtr =  EnumAdaptor.valueOf(alarmLowerLimitSetAtr, RangeSettingEnum.class);
		this.rangeValueAmount = alarmLoRangeValAmount == null ? Optional.empty() : Optional.of(new MonetaryValue(alarmLoRangeValAmount));
		this.rangeValueTime = alarmLoRangeValTime == null ? Optional.empty() : Optional.of(new TimeValue(alarmLoRangeValTime));
		this.rangeValueNum = alarmLoRangeValNum == null ? Optional.empty() : Optional.of(new TimesValue(alarmLoRangeValNum));
	}

}
