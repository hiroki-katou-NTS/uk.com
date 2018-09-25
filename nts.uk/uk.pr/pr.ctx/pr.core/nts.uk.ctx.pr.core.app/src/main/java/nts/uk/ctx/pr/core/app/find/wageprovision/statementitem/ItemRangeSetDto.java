package nts.uk.ctx.pr.core.app.find.wageprovision.statementitem;

import java.math.BigDecimal;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.itemrangeset.ItemRangeSet;

@Value
public class ItemRangeSetDto {

	/**
	 * 範囲値の属性
	 */
	private int rangeValueAtr;

	/**
	 * エラー上限値設定区分
	 */
	private int errorUpperLimitSettingAtr;

	/**
	 * エラー上限値金額
	 */
	private Long errorUpperRangeValueAmount;

	/**
	 * エラー上限値時間
	 */
	private Integer errorUpperRangeValueTime;

	/**
	 * エラー上限値回数
	 */
	private BigDecimal errorUpperRangeValueNum;

	/**
	 * エラー下限値設定区分
	 */
	private int errorLowerLimitSettingAtr;

	/**
	 * エラー上限値金額
	 */
	private Long errorLowerRangeValueAmount;

	/**
	 * エラー上限値時間
	 */
	private Integer errorLowerRangeValueTime;

	/**
	 * エラー上限値回数
	 */
	private BigDecimal errorLowerRangeValueNum;

	/**
	 * アラーム上限値設定区分
	 */
	private int alarmUpperLimitSettingAtr;

	/**
	 * アラーム上限値金額
	 */
	private Long alarmUpperRangeValueAmount;

	/**
	 * アラーム上限値時間
	 */
	private Integer alarmUpperRangeValueTime;

	/**
	 * アラーム上限値回数
	 */
	private BigDecimal alarmUpperRangeValueNum;

	/**
	 * アラーム下限値設定区分
	 */
	private int alarmLowerLimitSettingAtr;

	/**
	 * アラーム上限値金額
	 */
	private Long alarmLowerRangeValueAmount;

	/**
	 * アラーム上限値時間
	 */
	private Integer alarmLowerRangeValueTime;

	/**
	 * アラーム上限値回数
	 */
	private BigDecimal alarmLowerRangeValueNum;

	public static ItemRangeSetDto fromDomain(ItemRangeSet domain) {
		return new ItemRangeSetDto(domain.getRangeValueAtr().value,
				domain.getErrorRangeSetting().getErrorUpperLimitSetting().getErrorUpperLimitSettingAtr().value,
				domain.getErrorRangeSetting().getErrorUpperLimitSetting().getErrorUpperRangeValueAmount()
						.map(i -> i.v()).orElse(null),
				domain.getErrorRangeSetting().getErrorUpperLimitSetting().getErrorUpperRangeValueTime().map(i -> i.v())
						.orElse(null),
				domain.getErrorRangeSetting().getErrorUpperLimitSetting().getErrorUpperRangeValueNum().map(i -> i.v())
						.orElse(null),
				domain.getErrorRangeSetting().getErrorLowerLimitSetting().getErrorLowerLimitSettingAtr().value,
				domain.getErrorRangeSetting().getErrorLowerLimitSetting().getErrorLowerRangeValueAmount()
						.map(i -> i.v()).orElse(null),
				domain.getErrorRangeSetting().getErrorLowerLimitSetting().getErrorLowerRangeValueTime().map(i -> i.v())
						.orElse(null),
				domain.getErrorRangeSetting().getErrorLowerLimitSetting().getErrorLowerRangeValueNum().map(i -> i.v())
						.orElse(null),
				domain.getAlarmRangeSetting().getAlarmUpperLimitSetting().getAlarmUpperLimitSettingAtr().value,
				domain.getAlarmRangeSetting().getAlarmUpperLimitSetting().getAlarmUpperRangeValueAmount()
						.map(i -> i.v()).orElse(null),
				domain.getAlarmRangeSetting().getAlarmUpperLimitSetting().getAlarmUpperRangeValueTime().map(i -> i.v())
						.orElse(null),
				domain.getAlarmRangeSetting().getAlarmUpperLimitSetting().getAlarmUpperRangeValueNum().map(i -> i.v())
						.orElse(null),
				domain.getAlarmRangeSetting().getAlarmLowerLimitSetting().getAlarmLowerLimitSettingAtr().value,
				domain.getAlarmRangeSetting().getAlarmLowerLimitSetting().getAlarmLowerRangeValueAmount()
						.map(i -> i.v()).orElse(null),
				domain.getAlarmRangeSetting().getAlarmLowerLimitSetting().getAlarmLowerRangeValueTime().map(i -> i.v())
						.orElse(null),
				domain.getAlarmRangeSetting().getAlarmLowerLimitSetting().getAlarmLowerRangeValueNum().map(i -> i.v())
						.orElse(null));
	}

}
