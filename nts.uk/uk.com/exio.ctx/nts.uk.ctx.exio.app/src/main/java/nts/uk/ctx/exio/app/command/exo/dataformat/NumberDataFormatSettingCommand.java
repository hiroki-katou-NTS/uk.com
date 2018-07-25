package nts.uk.ctx.exio.app.command.exo.dataformat;

import java.math.BigDecimal;

import lombok.Value;

@Value
public class NumberDataFormatSettingCommand {

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
}
