package nts.uk.ctx.exio.dom.input.setting.assembly.revise.dataformat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.exio.dom.dataformat.value.AcceptedDigit;
import nts.uk.ctx.exio.dom.dataformat.value.DecimalDigitNumber;
import nts.uk.ctx.exio.dom.dataformat.value.DecimalDivision;
import nts.uk.ctx.exio.dom.dataformat.value.DecimalPointClassification;
import nts.uk.ctx.exio.dom.dataformat.value.Rounding;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.codeconvert.CodeConvertCode;
import nts.uk.ctx.exio.dom.input.validation.condition.user.type.numeric.real.ImportingConditionReal;
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
	private Optional<ImportingConditionReal> valueOfFixedValue;

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
	private Optional<Rounding> decimalFraction;

	public NumDataFormatSet(int itemType, int fixedValue, int decimalDivision, int effectiveDigitLength,
			String cdConvertCd, BigDecimal valueOfFixedValue, Integer decimalDigitNum, Integer startDigit, Integer endDigit,
			Integer decimalPointCls, Integer decimalFraction) {
		super(itemType);
		this.fixedValue = EnumAdaptor.valueOf(fixedValue, NotUseAtr.class);
		this.decimalDivision = EnumAdaptor.valueOf(decimalDivision, DecimalDivision.class);
		this.effectiveDigitLength = EnumAdaptor.valueOf(effectiveDigitLength, NotUseAtr.class);
		this.cdConvertCd = Optional.ofNullable(cdConvertCd == null ? null : new CodeConvertCode(cdConvertCd));
		this.valueOfFixedValue = Optional.ofNullable(valueOfFixedValue == null ? null : new ImportingConditionReal(valueOfFixedValue));
		this.decimalDigitNum = Optional.ofNullable(decimalDigitNum == null ? null : new DecimalDigitNumber(decimalDigitNum));
		this.startDigit = Optional.ofNullable(startDigit == null ? null : new AcceptedDigit(startDigit));
		this.endDigit = Optional.ofNullable(endDigit == null ? null : new AcceptedDigit(endDigit));
		this.decimalPointCls = Optional.ofNullable(decimalPointCls == null ? null : EnumAdaptor.valueOf(decimalPointCls, DecimalPointClassification.class));
		this.decimalFraction = Optional.ofNullable(decimalFraction == null ? null : EnumAdaptor.valueOf(decimalFraction, Rounding.class));
	}
	
	public Double editNumber(String itemValue) {
		Double result = null;
		//固定値使用する/しないを判別
		if(this.fixedValue == NotUseAtr.USE) {
			return this.valueOfFixedValue.get().v().doubleValue();
		}
		//有効桁長あり/なしを判別
		if(this.effectiveDigitLength == NotUseAtr.USE) {
			itemValue = itemValue.substring(this.startDigit.get().v(), this.endDigit.get().v());
		}
		try {
			result = Double.valueOf(itemValue);
		} catch (Exception e) {
			return null;
		}
		//小数編集あり/なしを判別
		if(this.decimalDivision == DecimalDivision.DECIMAL) {
			//「編集値」に「小数桁」の桁数を小数の有効桁とする
			BigDecimal bDecimal = BigDecimal.valueOf(result);
			bDecimal = bDecimal.setScale(this.decimalDigitNum.get().v(), RoundingMode.HALF_UP);
			result = bDecimal.doubleValue();
			//小数点あり/なしを判別する
			if(this.decimalPointCls.get() == DecimalPointClassification.NO_OUTPUT_DECIMAL_POINT) {
				//「編集値」の小数を小数点を割愛する
				result = result * 10 * this.decimalDigitNum.get().v();
			} 
		} else {
			//処理を判別する
			switch (this.decimalFraction.get()) {
			case TRUNCATION:
				result = Math.floor(result);
				break;
			case ROUND_UP:
				result = Math.ceil(result);
				break;
			case DOWN_4_UP_5:
				result = Double.valueOf(Math.round(result));
				break;
			default:
				break;
			}
		}
		
		return result;
	}
}
