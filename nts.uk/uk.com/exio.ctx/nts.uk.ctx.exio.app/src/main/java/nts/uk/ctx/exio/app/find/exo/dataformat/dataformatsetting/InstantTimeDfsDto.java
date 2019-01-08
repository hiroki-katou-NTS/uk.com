package nts.uk.ctx.exio.app.find.exo.dataformat.dataformatsetting;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.InstantTimeDataFmSetting;

/**
 * 時刻型データ形式設定
 */
@AllArgsConstructor
@Value
public class InstantTimeDfsDto {

	/**
	 * 条件設定コード
	 */
	private String condSetCd;

	/**
	 * 出力項目コード
	 */
	private String outItemCd;

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * NULL値置換
	 */
	private int nullValueSubs;

	/**
	 * マイナス値を0で出力
	 */
	private int outputMinusAsZero;

	/**
	 * 分/小数処理端数区分
	 */
	private int minuteFractionDigitProcessCls;

	/**
	 * 前日出力方法
	 */
	private int previousDayOutputMethod;

	/**
	 * 区切り文字設定
	 */
	private int delimiterSetting;

	/**
	 * 固定値
	 */
	private int fixedValue;

	/**
	 * 固定長出力
	 */
	private int fixedLengthOutput;

	/**
	 * 固定長編集方法
	 */
	private int fixedLengthEditingMethod;

	/**
	 * 時分/分選択
	 */
	private int timeSeletion;

	/**
	 * 翌日出力方法
	 */
	private int nextDayOutputMethod;

	/**
	 * 進数選択
	 */
	private int decimalSelection;

	/**
	 * NULL値置換の値
	 */
	private String valueOfNullValueSubs;

	/**
	 * データ形式小数桁
	 */
	private int minuteFractionDigit;

	/**
	 * 固定値の値
	 */
	private String valueOfFixedValue;

	/**
	 * 固定長整数桁
	 */
	private int fixedLongIntegerDigit;

	public static InstantTimeDfsDto fromDomain(InstantTimeDataFmSetting domain) {
		return new InstantTimeDfsDto(domain.getConditionSettingCode().v(), domain.getOutputItemCode().v(),
				domain.getCid(), domain.getNullValueReplace().value, domain.getOutputMinusAsZero().value,
				domain.getMinuteFractionDigitProcessCls().value, domain.getPrevDayOutputMethod().value,
				domain.getDelimiterSetting().value, domain.getFixedValue().value, domain.getFixedLengthOutput().value,
				domain.getFixedLengthEditingMethod().value, domain.getTimeSeletion().value,
				domain.getNextDayOutputMethod().value, domain.getDecimalSelection().value,
				domain.getValueOfNullValueReplace().map(i -> i.v()).orElse(null),
				domain.getMinuteFractionDigit().map(i -> i.v()).orElse(null),
				domain.getValueOfFixedValue().map(i -> i.v()).orElse(null),
				domain.getFixedLongIntegerDigit().map(i -> i.v()).orElse(null));
	}

}
