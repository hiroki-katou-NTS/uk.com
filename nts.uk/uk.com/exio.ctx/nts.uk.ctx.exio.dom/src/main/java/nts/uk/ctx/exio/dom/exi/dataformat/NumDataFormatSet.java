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
	private Optional<DataSettingFixedValue> valueOfFixedValue;

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
		if (cdConvertCd == null)
			this.cdConvertCd = Optional.empty();
		else
			this.cdConvertCd = Optional.of(new CodeConvertCode(cdConvertCd));
		if (valueOfFixedValue == null)
			this.valueOfFixedValue = Optional.empty();
		else
			this.valueOfFixedValue = Optional.of(new DataSettingFixedValue(valueOfFixedValue));
		if (decimalDigitNum == null)
			this.decimalDigitNum = Optional.empty();
		else
			this.decimalDigitNum = Optional.of(new DecimalDigitNumber(decimalDigitNum));
		if (startDigit == null)
			this.startDigit = Optional.empty();
		else
			this.startDigit = Optional.of(new AcceptedDigit(startDigit));
		if (endDigit == null)
			this.endDigit = Optional.empty();
		else
			this.endDigit = Optional.of(new AcceptedDigit(endDigit));
		if (decimalPointCls == null)
			this.decimalPointCls = Optional.empty();
		else
			this.decimalPointCls = Optional
					.of(EnumAdaptor.valueOf(decimalPointCls, DecimalPointClassification.class));
		if (decimalFraction == null)
			this.decimalFraction = Optional.empty();
		else
			this.decimalFraction = Optional.of(EnumAdaptor.valueOf(decimalFraction, DecimalFraction.class));
	}
}
