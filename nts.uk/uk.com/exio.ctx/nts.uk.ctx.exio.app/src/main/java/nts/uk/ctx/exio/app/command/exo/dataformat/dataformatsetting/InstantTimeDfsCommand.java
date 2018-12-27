package nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting;

import lombok.Value;

@Value
public class InstantTimeDfsCommand {

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

}
