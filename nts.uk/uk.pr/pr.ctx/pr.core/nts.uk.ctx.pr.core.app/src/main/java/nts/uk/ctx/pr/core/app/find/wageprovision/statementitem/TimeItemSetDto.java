package nts.uk.ctx.pr.core.app.find.wageprovision.statementitem;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.timeitemset.TimeItemSet;

import java.math.BigDecimal;

/**
 * 
 * @author thanh.tq 勤怠項目設定
 *
 */
@Value
public class TimeItemSetDto {

	/**
	 * 平均賃金区分
	 */
	private Integer averageWageAtr;

	/**
	 * 年間所定労働日数区分
	 */
	private Integer workingDaysPerYear;

	/**
	 * 時間回数区分
	 */
	private int timeCountAtr;

	/**
	 * 値設定区分
	 */
	public int errorUpperLimitSetAtr;

	/**
	 * 明細範囲値（時間）
	 */
	public Integer errorUpRangeValTime;

	/**
	 * 明細範囲値（回数）
	 */
	public BigDecimal errorUpRangeValNum;

	/**
	 * 値設定区分
	 */
	public int errorLowerLimitSetAtr;

	/**
	 * 明細範囲値（時間）
	 */
	public Integer errorLoRangeValTime;

	/**
	 * 明細範囲値（回数）
	 */
	public BigDecimal errorLoRangeValNum;

	/**
	 * 値設定区分
	 */
	public int alarmUpperLimitSetAtr;

	/**
	 * 明細範囲値（時間）
	 */
	public Integer alarmUpRangeValTime;

	/**
	 * 明細範囲値（回数）
	 */
	public BigDecimal alarmUpRangeValNum;

	/**
	 * 値設定区分
	 */
	public int alarmLowerLimitSetAtr;

	/**
	 * 明細範囲値（時間）
	 */
	public Integer alarmLoRangeValTime;

	/**
	 * 明細範囲値（回数）
	 */
	public BigDecimal alarmLoRangeValNum;

	/**
	 * 備考
	 */
	private String note;

	// 給与汎用パラメータ値.選択肢
	private int parameterValue;

	public static TimeItemSetDto fromDomain(TimeItemSet domain) {
		//pending thuật toán lấy parameterValue
		int parameterValue = 2;

		return new TimeItemSetDto(domain.getAverageWageAtr().map(i -> i.value).orElse(null),
				domain.getWorkingDaysPerYear().map(i -> i.value).orElse(null), domain.getTimeCountAtr().value,
				domain.getErrorRangeSetting().getUpperLimitSetting().getValueSettingAtr().value,
				domain.getErrorRangeSetting().getUpperLimitSetting().getTimeValue().map(i -> i.v()).orElse(null),
				domain.getErrorRangeSetting().getUpperLimitSetting().getTimesValue().map(i -> i.v()).orElse(null),
				domain.getErrorRangeSetting().getLowerLimitSetting().getValueSettingAtr().value,
				domain.getErrorRangeSetting().getLowerLimitSetting().getTimeValue().map(i -> i.v()).orElse(null),
				domain.getErrorRangeSetting().getLowerLimitSetting().getTimesValue().map(i -> i.v()).orElse(null),
				domain.getAlarmRangeSetting().getUpperLimitSetting().getValueSettingAtr().value,
				domain.getAlarmRangeSetting().getUpperLimitSetting().getTimeValue().map(i -> i.v()).orElse(null),
				domain.getAlarmRangeSetting().getUpperLimitSetting().getTimesValue().map(i -> i.v()).orElse(null),
				domain.getAlarmRangeSetting().getLowerLimitSetting().getValueSettingAtr().value,
				domain.getAlarmRangeSetting().getLowerLimitSetting().getTimeValue().map(i -> i.v()).orElse(null),
				domain.getAlarmRangeSetting().getLowerLimitSetting().getTimesValue().map(i -> i.v()).orElse(null),
				domain.getNote().map(i -> i.v()).orElse(null), parameterValue);
	}
}
