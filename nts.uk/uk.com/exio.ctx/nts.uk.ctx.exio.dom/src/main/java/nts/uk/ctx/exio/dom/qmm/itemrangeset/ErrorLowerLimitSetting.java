package nts.uk.ctx.exio.dom.qmm.itemrangeset;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;

/**
 * エラー下限値設定
 */
@Getter
public class ErrorLowerLimitSetting extends DomainObject {

	/**
	 * エラー下限値設定区分
	 */
	private RangeSettingEnum lowerLimitSettingAtr;

	/**
	 * エラー上限値金額
	 */
	private Optional<MonetaryValue> rangeValueAmount;

	/**
	 * エラー上限値時間
	 */
	private Optional<TimeValue> rangeValueTime;

	/**
	 * エラー上限値回数
	 */
	private Optional<TimesValue> rangeValueNum;

	public ErrorLowerLimitSetting(int errorLowerLimitSetAtr, BigDecimal errorLoRangeValAmount,
			Integer errorLoRangeValTime, BigDecimal errorLoRangeValNum) {
		this.lowerLimitSettingAtr = EnumAdaptor.valueOf(errorLowerLimitSetAtr, RangeSettingEnum.class);
		this.rangeValueAmount = errorLoRangeValAmount == null ? Optional.empty()
				: Optional.of(new MonetaryValue(errorLoRangeValAmount));
		this.rangeValueTime = errorLoRangeValTime == null ? Optional.empty()
				: Optional.of(new TimeValue(errorLoRangeValTime));
		this.rangeValueNum = errorLoRangeValNum == null ? Optional.empty()
				: Optional.of(new TimesValue(errorLoRangeValNum));
	}

}
