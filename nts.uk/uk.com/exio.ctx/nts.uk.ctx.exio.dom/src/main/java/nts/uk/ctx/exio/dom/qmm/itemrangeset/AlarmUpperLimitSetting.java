package nts.uk.ctx.exio.dom.qmm.itemrangeset;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;

/**
 * アラーム上限設定
 */
@Getter
public class AlarmUpperLimitSetting extends DomainObject {

	/**
	 * アラーム上限値設定区分
	 */
	private RangeSettingEnum upperLimitSettingAtr;

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

	public AlarmUpperLimitSetting(int alarmUpperLimitSetAtr, BigDecimal alarmUpRangeValAmount,
			Integer alarmUpRangeValTime, BigDecimal alarmUpRangeValNum) {
		this.upperLimitSettingAtr =  EnumAdaptor.valueOf(alarmUpperLimitSetAtr, RangeSettingEnum.class);
		this.rangeValueAmount = alarmUpRangeValAmount == null ? Optional.empty() : Optional.of(new MonetaryValue(alarmUpRangeValAmount));
		this.rangeValueTime = alarmUpRangeValTime == null ? Optional.empty() : Optional.of(new TimeValue(alarmUpRangeValTime));
		this.rangeValueNum = alarmUpRangeValNum == null ? Optional.empty() : Optional.of(new TimesValue(alarmUpRangeValNum));
	}
	
}
