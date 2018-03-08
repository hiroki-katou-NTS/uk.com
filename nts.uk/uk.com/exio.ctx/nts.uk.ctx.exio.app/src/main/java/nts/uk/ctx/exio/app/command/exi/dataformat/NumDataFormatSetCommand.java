package nts.uk.ctx.exio.app.command.exi.dataformat;

import lombok.Value;
import nts.uk.ctx.exio.dom.exi.dataformat.ItemType;
import nts.uk.ctx.exio.dom.exi.dataformat.NumDataFormatSet;

@Value
public class NumDataFormatSetCommand {

	/**
	 * 固定値
	 */
	private int fixedValue;

	/**
	 * 小数区分
	 */
	private int decimalDivision;

	/**
	 * 有効桁長
	 */
	private int effectiveDigitLength;

	/**
	 * コード変換コード
	 */
	private String cdConvertCd;

	/**
	 * 固定値の値
	 */
	private String valueOfFixedValue;

	/**
	 * 少数桁数
	 */
	private Integer decimalDigitNum;

	/**
	 * 有効桁数開始桁
	 */
	private Integer startDigit;

	/**
	 * 有効桁数終了桁
	 */
	private Integer endDigit;

	/**
	 * 小数点区分
	 */
	private Integer decimalPointCls;

	/**
	 * 小数端数
	 */
	private Integer decimalFraction;

	public NumDataFormatSet toDomain() {
		return new NumDataFormatSet(ItemType.NUMERIC.value, this.fixedValue, this.decimalDivision,
				this.effectiveDigitLength, this.cdConvertCd, this.valueOfFixedValue, this.decimalDigitNum,
				this.startDigit, this.endDigit, this.decimalPointCls, this.decimalFraction);
	}

}
