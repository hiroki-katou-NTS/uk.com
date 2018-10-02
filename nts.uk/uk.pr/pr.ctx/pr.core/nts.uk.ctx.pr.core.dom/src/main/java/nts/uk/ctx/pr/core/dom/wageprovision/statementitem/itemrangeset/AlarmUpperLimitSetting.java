package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.itemrangeset;

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
	private RangeSettingEnum alarmUpperLimitSettingAtr;

	/**
	 * アラーム上限値金額
	 */
	private Optional<MonetaryValue> alarmUpperRangeValueAmount;

	/**
	 * アラーム上限値時間
	 */
	private Optional<TimeValue> alarmUpperRangeValueTime;

	/**
	 * アラーム上限値回数
	 */
	private Optional<TimesValue> alarmUpperRangeValueNum;

	public AlarmUpperLimitSetting(int alarmUpperLimitSetAtr, Long alarmUpRangeValAmount,
			Integer alarmUpRangeValTime, BigDecimal alarmUpRangeValNum) {
		this.alarmUpperLimitSettingAtr =  EnumAdaptor.valueOf(alarmUpperLimitSetAtr, RangeSettingEnum.class);
		this.alarmUpperRangeValueAmount = alarmUpRangeValAmount == null ? Optional.empty() : Optional.of(new MonetaryValue(alarmUpRangeValAmount));
		this.alarmUpperRangeValueTime = alarmUpRangeValTime == null ? Optional.empty() : Optional.of(new TimeValue(alarmUpRangeValTime));
		this.alarmUpperRangeValueNum = alarmUpRangeValNum == null ? Optional.empty() : Optional.of(new TimesValue(alarmUpRangeValNum));
	}
	
}
