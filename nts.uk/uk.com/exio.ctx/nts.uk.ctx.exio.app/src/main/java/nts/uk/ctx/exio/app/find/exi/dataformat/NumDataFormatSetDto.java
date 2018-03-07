package nts.uk.ctx.exio.app.find.exi.dataformat;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.exi.dataformat.NumDataFormatSet;

/**
 * 数値型データ形式設定
 */
@AllArgsConstructor
@Value
public class NumDataFormatSetDto {

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
	private int decimalDigitNum;

	/**
	 * 有効桁数開始桁
	 */
	private int startDigit;

	/**
	 * 有効桁数終了桁
	 */
	private int endDigit;

	/**
	 * 小数点区分
	 */
	private int decimalPointCls;

	/**
	 * 小数端数
	 */
	private int decimalFraction;

	public static NumDataFormatSetDto fromDomain(NumDataFormatSet domain) {
		return new NumDataFormatSetDto(domain.getFixedValue().value, domain.getDecimalDivision().value,
				domain.getEffectiveDigitLength().value, domain.getCdConvertCd().get().v(),
				domain.getValueOfFixedValue().get().v(), domain.getDecimalDigitNum().get().v(),
				domain.getStartDigit().get().v(), domain.getEndDigit().get().v(),
				domain.getDecimalPointCls().get().value, domain.getDecimalFraction().get().value);
	}

}
