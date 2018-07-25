package nts.uk.ctx.exio.app.find.exo.dataformat.dataformatsetting;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.NumberDataFmSetting;

/**
 * 数値型データ形式設定
 */
@AllArgsConstructor
@Value
public class NumberDfsDto {

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
	private int nullValueReplace;

	/**
	 * マイナス値を0で出力
	 */
	private int outputMinusAsZero;

	/**
	 * 固定値
	 */
	private int fixedValue;

	/**
	 * 固定値演算
	 */
	private int fixedValueOperation;

	/**
	 * 固定値演算符号
	 */
	private int fixedValueOperationSymbol;

	/**
	 * 固定長出力
	 */
	private int fixedLengthOutput;

	/**
	 * 固定長編集方法
	 */
	private int fixedLengthEditingMethod;

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

	/**
	 * NULL値置換の値
	 */
	private String valueOfNullValueReplace;

	/**
	 * 固定値の値
	 */
	private String valueOfFixedValue;

	/**
	 * 固定値演算値
	 */
	private String fixedCalculationValue;

	/**
	 * 固定長整数桁
	 */
	private int fixedLengthIntegerDigit;

	/**
	 * 小数桁
	 */
	private int decimalDigit;

	public static NumberDfsDto fromDomain(NumberDataFmSetting domain) {
		return new NumberDfsDto(domain.getConditionSettingCode().v(), domain.getOutputItemCode().v(), domain.getCid(),
				domain.getNullValueReplace().value, domain.getOutputMinusAsZero().value, domain.getFixedValue().value,
				domain.getFixedValueOperation().value, domain.getFixedValueOperationSymbol().value,
				domain.getFixedLengthOutput().value, domain.getFixedLengthEditingMethod().value,
				domain.getDecimalPointClassification().value, domain.getDecimalFraction().value,
				domain.getFormatSelection().value, domain.getValueOfNullValueReplace().map(i -> i.v()).orElse(null),
				domain.getValueOfFixedValue().map(i -> i.v()).orElse(null),
				domain.getFixedCalculationValue().map(i -> i.v().toString()).orElse(null),
				domain.getFixedLengthIntegerDigit().map(i -> i.v()).orElse(null),
				domain.getDecimalDigit().map(i -> i.v()).orElse(null));
	}

}
