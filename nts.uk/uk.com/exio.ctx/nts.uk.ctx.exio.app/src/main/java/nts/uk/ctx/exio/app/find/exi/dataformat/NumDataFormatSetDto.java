package nts.uk.ctx.exio.app.find.exi.dataformat;

import lombok.Value;
import nts.uk.ctx.exio.dom.exi.dataformat.NumDataFormatSet;

/**
 * 数値型データ形式設定
 */

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
	private Double valueOfFixedValue;

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

	public static NumDataFormatSetDto fromDomain(NumDataFormatSet domain) {
		return new NumDataFormatSetDto(domain.getFixedValue().value, domain.getDecimalDivision().value,
				domain.getEffectiveDigitLength().value,
				domain.getCdConvertCd().isPresent() ? domain.getCdConvertCd().get().v() : null,
				domain.getValueOfFixedValue().isPresent() ? domain.getValueOfFixedValue().get().v().doubleValue() : null,
				domain.getDecimalDigitNum().isPresent() ? domain.getDecimalDigitNum().get().v() : null,
				domain.getStartDigit().isPresent() ? domain.getStartDigit().get().v() : null,
				domain.getEndDigit().isPresent() ? domain.getEndDigit().get().v() : null,
				domain.getDecimalPointCls().isPresent() ? domain.getDecimalPointCls().get().value : null,
				domain.getDecimalFraction().isPresent() ? domain.getDecimalFraction().get().value : null);
	}

}
