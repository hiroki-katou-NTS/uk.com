package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.itemrangeset;

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
	private RangeSettingEnum errorUpperLimitSettingAtr;

	/**
	 * エラー上限値金額
	 */
	private Optional<MonetaryValue> errorUpperRangeValueAmount;

	/**
	 * エラー上限値時間
	 */
	private Optional<TimeValue> errorUpperRangeValueTime;

	/**
	 * エラー上限値回数
	 */
	private Optional<TimesValue> errorUpperRangeValueNum;
	
	public ErrorUpperLimitSetting(int errorUpperLimitSetAtr, Long errorUpRangeValAmount,
			Integer errorUpRangeValTime, BigDecimal errorUpRangeValNum) {
		this.errorUpperLimitSettingAtr = EnumAdaptor.valueOf(errorUpperLimitSetAtr, RangeSettingEnum.class);
		this.errorUpperRangeValueAmount = errorUpRangeValAmount == null ? Optional.empty()
				: Optional.of(new MonetaryValue(errorUpRangeValAmount));
		this.errorUpperRangeValueTime = errorUpRangeValTime == null ? Optional.empty()
				: Optional.of(new TimeValue(errorUpRangeValTime));
		this.errorUpperRangeValueNum = errorUpRangeValNum == null ? Optional.empty()
				: Optional.of(new TimesValue(errorUpRangeValNum));
	}
	
}
