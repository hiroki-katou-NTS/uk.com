package nts.uk.ctx.exio.dom.exi.dataformat;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.exio.dom.exi.codeconvert.CodeConvertCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 数値型データ形式設定
 */
@Getter
public class NumDataFormatSet extends DataFormatSetting {

	/**
	 * 固定値
	 */
	private NotUseAtr fixedValue;

	/**
	 * 小数区分
	 */
	private DecimalDivision decimalDivision;

	/**
	 * 有効桁長
	 */
	private NotUseAtr effectiveDigitLength;

	/**
	 * コード変換コード
	 */
	private Optional<CodeConvertCode> cdConvertCd;

	/**
	 * 固定値の値
	 */
	private Optional<ValueOfFixed> valueOfFixedValue;

	/**
	 * 少数桁数
	 */
	private Optional<DecimalDigitNumber> decimalDigitNum;

	/**
	 * 有効桁数開始桁
	 */
	private Optional<AcceptedDigit> startDigit;

	/**
	 * 有効桁数終了桁
	 */
	private Optional<AcceptedDigit> endDigit;

	/**
	 * 小数点区分
	 */
	private Optional<DecimalPointClassification> decimalPointCls;

	/**
	 * 小数端数
	 */
	private Optional<DecimalFraction> decimalFraction;

	public NumDataFormatSet(int itemType, int fixedValue, int decimalDivision, int effectiveDigitLength,
			String cdConvertCd, String valueOfFixedValue, Integer decimalDigitNum, Integer startDigit, Integer endDigit,
			Integer decimalPointCls, Integer decimalFraction) {
		super(itemType);
		this.fixedValue = EnumAdaptor.valueOf(fixedValue, NotUseAtr.class);
		this.decimalDivision = EnumAdaptor.valueOf(decimalDivision, DecimalDivision.class);
		this.effectiveDigitLength = EnumAdaptor.valueOf(effectiveDigitLength, NotUseAtr.class);

		this.cdConvertCd = Optional.ofNullable(new CodeConvertCode(cdConvertCd));
		this.valueOfFixedValue = Optional.ofNullable(new ValueOfFixed(valueOfFixedValue));
		this.decimalDigitNum = Optional.ofNullable(new DecimalDigitNumber(decimalDigitNum));
		this.startDigit = Optional.ofNullable(new AcceptedDigit(startDigit));
		this.endDigit = Optional.ofNullable(new AcceptedDigit(endDigit));
		this.decimalPointCls = Optional
				.ofNullable(EnumAdaptor.valueOf(decimalPointCls, DecimalPointClassification.class));
		this.decimalFraction = Optional.ofNullable(EnumAdaptor.valueOf(decimalFraction, DecimalFraction.class));
	}
}
