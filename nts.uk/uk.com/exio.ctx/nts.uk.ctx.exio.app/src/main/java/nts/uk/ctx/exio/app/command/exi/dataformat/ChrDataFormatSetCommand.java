package nts.uk.ctx.exio.app.command.exi.dataformat;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.exi.dataformat.ChrDataFormatSet;
import nts.uk.ctx.exio.dom.exi.dataformat.ItemType;

@Value
public class ChrDataFormatSetCommand {

	/**
	 * コード編集
	 */
	private int cdEditing;

	/**
	 * 固定値
	 */
	private int fixedValue;

	/**
	 * 有効桁長
	 */
	private int effectiveDigitLength;

	/**
	 * コード変換コード
	 */
	private String cdConvertCd;

	/**
	 * コード編集方法
	 */
	private Integer cdEditMethod;

	/**
	 * コード編集桁
	 */
	private Integer cdEditDigit;

	/**
	 * 固定値の値
	 */
	private String fixedVal;

	/**
	 * 有効桁数開始桁
	 */
	private Integer startDigit;

	/**
	 * 有効桁数終了桁
	 */
	private Integer endDigit;

	public ChrDataFormatSet toDomain() {
		return new ChrDataFormatSet(ItemType.CHARACTER.value, this.cdEditing, this.fixedValue,
				this.effectiveDigitLength, this.cdConvertCd, this.cdEditMethod, this.cdEditDigit, this.fixedVal,
				this.startDigit, this.endDigit);
	}

}
