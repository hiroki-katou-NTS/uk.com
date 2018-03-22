package nts.uk.ctx.exio.app.find.exi.dataformat;

import lombok.Value;
import nts.uk.ctx.exio.dom.exi.dataformat.ChrDataFormatSet;

/**
 * 文字型データ形式設定
 */
@Value
public class ChrDataFormatSetDto {

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

	public static ChrDataFormatSetDto fromDomain(ChrDataFormatSet domain) {
		return new ChrDataFormatSetDto(domain.getCdEditing().value, domain.getFixedValue().value,
				domain.getEffectiveDigitLength().value,
				domain.getCdConvertCd().isPresent() ? domain.getCdConvertCd().get().v() : null,
				domain.getCdEditMethod().isPresent() ? domain.getCdEditMethod().get().value : null,
				domain.getCdEditDigit().isPresent() ? domain.getCdEditDigit().get().v() : null,
				domain.getFixedVal().isPresent() ? domain.getFixedVal().get().v() : null,
				domain.getStartDigit().isPresent() ? domain.getStartDigit().get().v() : null,
				domain.getEndDigit().isPresent() ? domain.getEndDigit().get().v() : null);
	}

}
