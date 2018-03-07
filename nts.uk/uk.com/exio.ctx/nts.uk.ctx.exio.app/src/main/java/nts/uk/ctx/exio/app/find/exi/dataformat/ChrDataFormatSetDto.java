package nts.uk.ctx.exio.app.find.exi.dataformat;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.exi.dataformat.ChrDataFormatSet;

/**
 * 文字型データ形式設定
 */
@AllArgsConstructor
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
	private int cdEditMethod;

	/**
	 * コード編集桁
	 */
	private int cdEditDigit;

	/**
	 * 固定値の値
	 */
	private String fixedVal;

	/**
	 * 有効桁数開始桁
	 */
	private int startDigit;

	/**
	 * 有効桁数終了桁
	 */
	private int endDigit;

	public static ChrDataFormatSetDto fromDomain(ChrDataFormatSet domain) {
		return new ChrDataFormatSetDto(domain.getCdEditing().value, domain.getFixedValue().value,
				domain.getEffectiveDigitLength().value, domain.getCdConvertCd().get().v(),
				domain.getCdEditMethod().get().value, domain.getCdEditDigit().get().v(), domain.getFixedVal().get().v(),
				domain.getStartDigit().get().v(), domain.getStartDigit().get().v());
	}

}
