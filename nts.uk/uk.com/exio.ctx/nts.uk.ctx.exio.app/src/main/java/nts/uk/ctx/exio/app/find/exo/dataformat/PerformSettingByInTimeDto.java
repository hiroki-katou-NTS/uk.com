package nts.uk.ctx.exio.app.find.exo.dataformat;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.exio.dom.exo.dataformat.init.InTimeDataFmSet;

@AllArgsConstructor
@Value
public class PerformSettingByInTimeDto {
	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * NULL値置換
	 */
	private int nullValueSubs;

	/**
	 * NULL値置換の値
	 */
	private String valueOfNullValueSubs;

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
	 * 時分/分選択
	 */
	private int timeSeletion;

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
	 * 前日出力方法
	 */
	private int previousDayOutputMethod;
	/**
	 * 翌日出力方法
	 */
	private int nextDayOutputMethod;

	/**
	 * データ形式小数桁
	 */
	private Integer minuteFractionDigit;

	/**
	 * 進数選択
	 */
	private int decimalSelection;

	/**
	 * 分/小数処理端数区分
	 */
	private int minuteFractionDigitProcessCls;

	public static PerformSettingByInTimeDto fromDomain(InTimeDataFmSet domain) {
		/*
		 * String valueOfFixedValue = domain.getValueOfFixedValue().isPresent()
		 * ? domain.getValueOfFixedValue().get().v() : ""; String
		 * valueOfNullValueSubs = domain.getValueOfNullValueSubs().isPresent() ?
		 * domain.getValueOfNullValueSubs().get().v() : ""; Integer
		 * minuteFractionDigit = domain.getMinuteFractionDigit().isPresent() ?
		 * domain.getMinuteFractionDigit().get().v() : null; Integer
		 * fixedLongIntegerDigit = domain.getFixedLongIntegerDigit().isPresent()
		 * ? domain.getFixedLongIntegerDigit().get().v() : null;
		 */
		String valueOfFixedValue = domain.getValueOfFixedValue().map(PrimitiveValueBase::v).orElse(null);

		String valueOfNullValueSubs = domain.getValueOfNullValueReplace().map(PrimitiveValueBase::v).orElse("");

		Integer minuteFractionDigit = domain.getMinuteFractionDigit().map(PrimitiveValueBase::v).orElse(null);

		Integer fixedLongIntegerDigit = domain.getFixedLongIntegerDigit().map(PrimitiveValueBase::v).orElse(null);

		return new PerformSettingByInTimeDto(domain.getCid(), domain.getNullValueReplace().value, valueOfNullValueSubs,
				domain.getOutputMinusAsZero().value, domain.getFixedValue().value, valueOfFixedValue,
				domain.getTimeSeletion().value, domain.getFixedLengthOutput().value, fixedLongIntegerDigit,
				domain.getFixedLengthEditingMethod().value, domain.getDelimiterSetting().value,
				domain.getPrevDayOutputMethod().value, domain.getNextDayOutputMethod().value, minuteFractionDigit,
				domain.getDecimalSelection().value, domain.getMinuteFractionDigitProcessCls().value);
	}
}
