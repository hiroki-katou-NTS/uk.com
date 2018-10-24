package nts.uk.ctx.exio.app.command.exo.dataformat;

import java.math.BigDecimal;

import lombok.Value;

@Value
public class AddPerformSettingByTimeCommand {
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
	private Double fixedCalculationValue;

	/**
	 * NULL値置換の値
	 */
	private String valueOfNullValueSubs;

	/**
	 * 分/小数処理端数区分
	 */
	private int minuteFractionDigitProcessCls;
}
