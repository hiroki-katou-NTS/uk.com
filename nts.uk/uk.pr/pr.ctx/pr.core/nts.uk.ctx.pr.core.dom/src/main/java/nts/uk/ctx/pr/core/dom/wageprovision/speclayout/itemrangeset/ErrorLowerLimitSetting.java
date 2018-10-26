package nts.uk.ctx.pr.core.dom.wageprovision.speclayout.itemrangeset;

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
	private RangeSettingEnum errorLowerLimitSettingAtr;

	/**
	 * エラー上限値金額
	 */
	private Optional<MonetaryValue> errorLowerRangeValueAmount;

	/**
	 * エラー上限値時間
	 */
	private Optional<TimeValue> errorLowerRangeValueTime;

	/**
	 * エラー上限値回数
	 */
	private Optional<TimesValue> errorLowerRangeValueNum;

	public ErrorLowerLimitSetting(int errorLowerLimitSetAtr, Long errorLoRangeValAmount,
			Integer errorLoRangeValTime, BigDecimal errorLoRangeValNum) {
		this.errorLowerLimitSettingAtr = EnumAdaptor.valueOf(errorLowerLimitSetAtr, RangeSettingEnum.class);
		this.errorLowerRangeValueAmount = errorLoRangeValAmount == null ? Optional.empty()
				: Optional.of(new MonetaryValue(errorLoRangeValAmount));
		this.errorLowerRangeValueTime = errorLoRangeValTime == null ? Optional.empty()
				: Optional.of(new TimeValue(errorLoRangeValTime));
		this.errorLowerRangeValueNum = errorLoRangeValNum == null ? Optional.empty()
				: Optional.of(new TimesValue(errorLoRangeValNum));
	}

}
