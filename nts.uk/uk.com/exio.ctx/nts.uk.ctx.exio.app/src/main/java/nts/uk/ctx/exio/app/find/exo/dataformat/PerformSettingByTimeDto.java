package nts.uk.ctx.exio.app.find.exo.dataformat;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.exio.dom.exo.dataformat.TimeDataFmSet;

@AllArgsConstructor
@Value
public class PerformSettingByTimeDto {
	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * NULL値置換
	 */
	private int nullValueSubs;

	/**
	 * マイナス値を0で出力する
	 */
	private int outputMinusAsZero;

	/**
	 * 固定値
	 */
	private int fixedValue;

	/**
	 * 固定値の値
	 */
	private String valueOfFixedValue;

	/**
	 * 固定長出力
	 */
	private int fixedLengthOutput;

	/**
	 * 固定長整数桁
	 */
	private int fixedLongIntegerDigit;

	/**
	 * 固定長編集方法
	 */
	private int fixedLengthEditingMothod;

	/**
	 * 区切り文字設定
	 */
	private int delimiterSetting;

	/**
	 * 時分/分選択
	 */
	private int selectHourMinute;

	/**
	 * 分/小数処理桁
	 */
	private int minuteFractionDigit;

	/**
	 * 進数選択
	 */
	private int decimalSelection;

	/**
	 * 固定値演算符号
	 */
	private int fixedValueOperationSymbol;

	/**
	 * 固定値演算
	 */
	private int fixedValueOperation;

	/**
	 * 固定値演算値
	 */
	private String fixedCalculationValue;

	/**
	 * NULL値置換の値
	 */
	private String valueOfNullValueSubs;

	/**
	 * 分/小数処理端数区分
	 */
	private int minuteFractionDigitProcessCla;

	public static PerformSettingByTimeDto fromDomain(TimeDataFmSet domain) {
		String valueOfFixedValue = domain.getValueOfFixedValue().isPresent() ? domain.getValueOfFixedValue().get().v()
				: "";
		String valueOfNullValueSubs = domain.getValueOfNullValueSubs().isPresent()
				? domain.getValueOfNullValueSubs().get().v() : "";
		Integer minuteFractionDigit = domain.getMinuteFractionDigit().isPresent()
				? domain.getMinuteFractionDigit().get().v() : null;
		Integer fixedLongIntegerDigit = domain.getFixedLongIntegerDigit().isPresent()
				? domain.getFixedLongIntegerDigit().get().v() : null;

		return new PerformSettingByTimeDto(domain.getCid(), domain.getNullValueSubs().value,
				domain.getOutputMinusAsZero().value, domain.getFixedValue().value, valueOfFixedValue,
				domain.getFixedLengthOutput().value, fixedLongIntegerDigit, domain.getFixedLengthEditingMothod().value,
				domain.getDelimiterSetting().value, domain.getSelectHourMinute().value, minuteFractionDigit,
				domain.getDecimalSelection().value, domain.getFixedValueOperationSymbol().value,
				domain.getFixedValueOperation().value, domain.getFixedCalculationValue(), valueOfNullValueSubs,
				domain.getMinuteFractionDigitProcessCla().value);
	}
}
