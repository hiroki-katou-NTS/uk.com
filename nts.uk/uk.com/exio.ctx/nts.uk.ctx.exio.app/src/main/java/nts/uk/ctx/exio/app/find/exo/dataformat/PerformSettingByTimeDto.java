package nts.uk.ctx.exio.app.find.exo.dataformat;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.exio.dom.exo.dataformat.init.TimeDataFmSet;

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
	private Integer fixedLongIntegerDigit;

	/**
	 * 固定長編集方法
	 */
	private int fixedLengthEditingMethod;

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
	private Integer minuteFractionDigit;

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
	private BigDecimal fixedCalculationValue;

	/**
	 * NULL値置換の値
	 */
	private String valueOfNullValueSubs;

	/**
	 * 分/小数処理端数区分
	 */
	private int minuteFractionDigitProcessCls;

	public static PerformSettingByTimeDto fromDomain(TimeDataFmSet domain) {
		/*
		 * String valueOfFixedValue = domain.getValueOfFixedValue().isPresent()
		 * ? domain.getValueOfFixedValue().get().v() : "";
		 */
		String valueOfFixedValue = domain.getValueOfFixedValue().map(PrimitiveValueBase::v).orElse(null);

		String valueOfNullValueSubs = domain.getValueOfNullValueReplace().map(PrimitiveValueBase::v).orElse("");

		Integer minuteFractionDigit = domain.getMinuteFractionDigit().map(PrimitiveValueBase::v).orElse(null);

		Integer fixedLongIntegerDigit = domain.getFixedLongIntegerDigit().map(PrimitiveValueBase::v).orElse(null);

		BigDecimal fixedCalculationValue = domain.getFixedCalculationValue().map(PrimitiveValueBase::v).orElse(null);

		/*
		 * String valueOfNullValueSubs =
		 * domain.getValueOfNullValueSubs().isPresent() ?
		 * domain.getValueOfNullValueSubs().get().v() : ""; Integer
		 * minuteFractionDigit = domain.getMinuteFractionDigit().isPresent() ?
		 * domain.getMinuteFractionDigit().get().v() : null; Integer
		 * fixedLongIntegerDigit = domain.getFixedLongIntegerDigit().isPresent()
		 * ? domain.getFixedLongIntegerDigit().get().v() : null; BigDecimal
		 * fixedCalculationValue = domain.getFixedCalculationValue().isPresent()
		 * ? domain.getFixedCalculationValue().get().v() : null;
		 */

		return new PerformSettingByTimeDto(domain.getCid(), domain.getNullValueReplace().value,
				domain.getOutputMinusAsZero().value, domain.getFixedValue().value, valueOfFixedValue,
				domain.getFixedLengthOutput().value, fixedLongIntegerDigit, domain.getFixedLengthEditingMethod().value,
				domain.getDelimiterSetting().value, domain.getSelectHourMinute().value, minuteFractionDigit,
				domain.getDecimalSelection().value, domain.getFixedValueOperationSymbol().value,
				domain.getFixedValueOperation().value, fixedCalculationValue, valueOfNullValueSubs,
				domain.getMinuteFractionDigitProcessCls().value);
	}
}
