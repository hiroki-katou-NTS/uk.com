package nts.uk.ctx.exio.app.command.exi.dataformat;

import lombok.Value;
import nts.uk.ctx.exio.dom.exi.dataformat.InsTimeDatFmSet;
import nts.uk.ctx.exio.dom.exi.dataformat.ItemType;

@Value
public class InsTimeDatFmSetCommand {

	/**
	 * 区切り文字設定
	 */
	private int delimiterSet;

	/**
	 * 固定値
	 */
	private int fixedValue;

	/**
	 * 時分/分選択
	 */
	private int hourMinSelect;

	/**
	 * 有効桁長
	 */
	private int effectiveDigitLength;

	/**
	 * 端数処理
	 */
	private int roundProc;

	/**
	 * 進数選択
	 */
	private int decimalSelect;

	/**
	 * 固定値の値
	 */
	private Integer valueOfFixedValue;

	/**
	 * 有効桁数開始桁
	 */
	private Integer startDigit;

	/**
	 * 有効桁数終了桁
	 */
	private Integer endDigit;

	/**
	 * 端数処理区分
	 */
	private Integer roundProcCls;

	public InsTimeDatFmSet toDomain() {
		return new InsTimeDatFmSet(ItemType.INS_TIME.value, this.delimiterSet, this.fixedValue, this.hourMinSelect,
				this.effectiveDigitLength, this.roundProc, this.decimalSelect, this.valueOfFixedValue, this.startDigit,
				this.endDigit, this.roundProcCls);
	}

}
