package nts.uk.ctx.exio.dom.qmm.itemrangeset;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;

/**
 * エラー上限値設定
 */
@Getter
public class ErrorUpperLimitSetting extends DomainObject {

	/**
	 * エラー上限値設定区分
	 */
	private RangeSettingEnum upperLimitSettingAtr;

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
	
	public ErrorUpperLimitSetting(int errorUpperLimitSetAtr, BigDecimal errorUpRangeValAmount,
			Integer errorUpRangeValTime, BigDecimal errorUpRangeValNum) {
		this.upperLimitSettingAtr = EnumAdaptor.valueOf(errorUpperLimitSetAtr, RangeSettingEnum.class);
		this.rangeValueAmount = errorUpRangeValAmount == null ? Optional.empty()
				: Optional.of(new MonetaryValue(errorUpRangeValAmount));
		this.rangeValueTime = errorUpRangeValTime == null ? Optional.empty()
				: Optional.of(new TimeValue(errorUpRangeValTime));
		this.rangeValueNum = errorUpRangeValNum == null ? Optional.empty()
				: Optional.of(new TimesValue(errorUpRangeValNum));
	}
	
}
