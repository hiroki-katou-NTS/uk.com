package nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting;

import java.math.BigDecimal;

import lombok.Value;

@Value
public class TimeDfsCommand {

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
	 * 区切り文字設定
	 */
	private int delimiterSetting;

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
	 * 時分/分選択
	 */
	private int selectHourMinute;

	/**
	 * 進数選択
	 */
	private int decimalSelection;

	/**
	 * NULL値置換の値
	 */
	private String valueOfNullValueSubs;

	/**
	 * 分/小数処理桁
	 */
	private int minuteFractionDigit;

	/**
	 * 固定値の値
	 */
	private String valueOfFixedValue;

	/**
	 * 固定値演算値
	 */
	private BigDecimal fixedCalculationValue;

	/**
	 * 固定長整数桁
	 */
	private int fixedLongIntegerDigit;

	public TimeDfsCommand(String condSetCd, String outItemCd, String cid, int nullValueSubs, int outputMinusAsZero,
			int minuteFractionDigitProcessCls, int delimiterSetting, int fixedValue, int fixedValueOperation,
			int fixedValueOperationSymbol, int fixedLengthOutput, int fixedLengthEditingMethod, int selectHourMinute,
			int decimalSelection, String valueOfNullValueSubs, int minuteFractionDigit, String valueOfFixedValue,
			BigDecimal fixedCalculationValue, int fixedLongIntegerDigit) {
		super();
		this.condSetCd = condSetCd;
		this.outItemCd = outItemCd;
		this.cid = cid;
		this.nullValueSubs = nullValueSubs;
		this.outputMinusAsZero = outputMinusAsZero;
		this.minuteFractionDigitProcessCls = minuteFractionDigitProcessCls;
		this.delimiterSetting = delimiterSetting;
		this.fixedValue = fixedValue;
		this.fixedValueOperation = fixedValueOperation;
		this.fixedValueOperationSymbol = fixedValueOperationSymbol;
		this.fixedLengthOutput = fixedLengthOutput;
		this.fixedLengthEditingMethod = fixedLengthEditingMethod;
		this.selectHourMinute = selectHourMinute;
		this.decimalSelection = decimalSelection;
		this.valueOfNullValueSubs = valueOfNullValueSubs;
		this.minuteFractionDigit = minuteFractionDigit;
		this.valueOfFixedValue = valueOfFixedValue;
		this.fixedCalculationValue = fixedCalculationValue;
		this.fixedLongIntegerDigit = fixedLongIntegerDigit;
	}

}
