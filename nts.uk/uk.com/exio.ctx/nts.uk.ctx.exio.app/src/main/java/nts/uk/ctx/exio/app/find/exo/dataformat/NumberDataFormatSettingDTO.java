package nts.uk.ctx.exio.app.find.exo.dataformat;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.exio.dom.exo.dataformat.init.NumberDataFmSet;

@AllArgsConstructor
@Value
public class NumberDataFormatSettingDTO {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * NULL値置換
	 */
	private int nullValueReplace;

	/**
	 * NULL値置換の値
	 */
	private String valueOfNullValueReplace;

	/**
	 * マイナス値を0で出力
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
	 * 固定値演算
	 */
	private int fixedValueOperation;

	/**
	 * 固定値演算値
	 */
	private BigDecimal fixedCalculationValue;

	/**
	 * 固定値演算符号
	 */
	private int fixedValueOperationSymbol;

	/**
	 * 固定長出力
	 */
	private int fixedLengthOutput;

	/**
	 * 固定長整数桁
	 */
	private Integer fixedLengthIntegerDigit;

	/**
	 * 固定長編集方法
	 */
	private int fixedLengthEditingMethod;

	/**
	 * 小数桁
	 */
	private Integer decimalDigit;

	/**
	 * 小数点区分
	 */
	private int decimalPointClassification;

	/**
	 * 小数端数
	 */
	private int decimalFraction;

	/**
	 * 形式選択
	 */
	private int formatSelection;

	public static NumberDataFormatSettingDTO fromDomain(NumberDataFmSet domain) {
		return new NumberDataFormatSettingDTO(domain.getCid(), domain.getNullValueReplace().value,
				domain.getValueOfNullValueReplace().isPresent() ? domain.getValueOfNullValueReplace().get().v() : null,
				domain.getOutputMinusAsZero().value, domain.getFixedValue().value,
				domain.getValueOfFixedValue().isPresent() ? domain.getValueOfFixedValue().get().v() : null,
				domain.getFixedValueOperation().value,
				domain.getFixedCalculationValue().isPresent() ? domain.getFixedCalculationValue().get().v() : null,
				domain.getFixedValueOperationSymbol().value, domain.getFixedLengthOutput().value,
				domain.getFixedLengthIntegerDigit().isPresent() ? domain.getFixedLengthIntegerDigit().get().v() : null,
				domain.getFixedLengthEditingMethod().value,
				domain.getDecimalDigit().isPresent() ? domain.getDecimalDigit().get().v() : null,
				domain.getDecimalPointClassification().value, domain.getDecimalFraction().value,
				domain.getFormatSelection().value);
	}
}
