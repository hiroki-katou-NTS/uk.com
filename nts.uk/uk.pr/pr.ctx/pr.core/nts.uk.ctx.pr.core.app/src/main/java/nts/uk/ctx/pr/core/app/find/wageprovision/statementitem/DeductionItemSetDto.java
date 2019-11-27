package nts.uk.ctx.pr.core.app.find.wageprovision.statementitem;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.deductionitemset.DeductionItemSet;

import java.math.BigDecimal;

/**
 * 控除項目設定
 */
@Value
public class DeductionItemSetDto {

	/**
	 * 控除項目区分
	 */
	private int deductionItemAtr;

	/**
	 * 内訳項目利用区分
	 */
	private int breakdownItemUseAtr;

	/**
	 * 値設定区分
	 */
	private int errorUpperLimitSetAtr;

	/**
	 * 範囲値
	 */
	private BigDecimal errorUpRangeVal;

	/**
	 * 値設定区分
	 */
	private int errorLowerLimitSetAtr;

	/**
	 * 範囲値
	 */
	private BigDecimal errorLoRangeVal;

	/**
	 * 値設定区分
	 */
	private int alarmUpperLimitSetAtr;

	/**
	 * 範囲値
	 */
	private BigDecimal alarmUpRangeVal;

	/**
	 * 値設定区分
	 */
	private int alarmLowerLimitSetAtr;

	/**
	 * 範囲値
	 */
	private BigDecimal alarmLoRangeVal;

	/**
	 * 備考
	 */
	private String note;

	public static DeductionItemSetDto fromDomain(DeductionItemSet domain) {
		return new DeductionItemSetDto(domain.getDeductionItemAtr().value, domain.getBreakdownItemUseAtr().value,
				domain.getErrorRangeSetting().getUpperLimitSetting().getValueSettingAtr().value,
				domain.getErrorRangeSetting().getUpperLimitSetting().getRangeValue().map(i -> i.v()).orElse(null),
				domain.getErrorRangeSetting().getLowerLimitSetting().getValueSettingAtr().value,
				domain.getErrorRangeSetting().getLowerLimitSetting().getRangeValue().map(i -> i.v()).orElse(null),
				domain.getAlarmRangeSetting().getUpperLimitSetting().getValueSettingAtr().value,
				domain.getAlarmRangeSetting().getUpperLimitSetting().getRangeValue().map(i -> i.v()).orElse(null),
				domain.getAlarmRangeSetting().getLowerLimitSetting().getValueSettingAtr().value,
				domain.getAlarmRangeSetting().getLowerLimitSetting().getRangeValue().map(i -> i.v()).orElse(null),
				domain.getNote().map(i -> i.v()).orElse(null));
	}

}
