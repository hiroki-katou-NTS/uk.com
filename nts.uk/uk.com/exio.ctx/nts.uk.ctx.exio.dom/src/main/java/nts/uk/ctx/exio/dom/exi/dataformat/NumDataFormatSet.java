package nts.uk.ctx.exio.dom.exi.dataformat;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.exio.dom.exi.codeconvert.AcceptCdConvert;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
* 数値型データ形式設定
*/
@AllArgsConstructor
@Getter
@Setter
public class NumDataFormatSet extends AggregateRoot
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 条件設定コード
    */
    private String conditionSetCd;
    
    /**
    * 受入項目番号
    */
    private int acceptItemNum;
    
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
    private Integer cdConvertCd;
    
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
    

	public NumDataFormatSet(String cid, String conditionSetCd, int acceptItemNum, int fixedValue,
			int decimalDivision, int effectiveDigitLength, Integer cdConvertCd,
			String valueOfFixedValue, Integer decimalDigitNum, Integer startDigit,
			Integer endDigit, Integer decimalPointCls, Integer decimalFraction) {
		super();
		this.cid = cid;
		this.conditionSetCd = conditionSetCd;
		this.acceptItemNum = acceptItemNum;
		this.fixedValue = EnumAdaptor.valueOf(fixedValue, NotUseAtr.class);
		this.decimalDivision = EnumAdaptor.valueOf(decimalDivision, DecimalDivision.class);
		this.effectiveDigitLength = EnumAdaptor.valueOf(effectiveDigitLength, NotUseAtr.class);
		/*if (null == cdConvertCd) {
			this.cdConvertCd = Optional.empty();
		} else {
			this.cdConvertCd = Optional.of(cdConvertCd);
		}*/
		if (null == valueOfFixedValue) {
			this.valueOfFixedValue = Optional.empty();
		} else {
			this.valueOfFixedValue = Optional.of(new ValueOfFixed(valueOfFixedValue));
		}
		if (null == decimalDigitNum) {
			this.decimalDigitNum = Optional.empty();
		} else {
			this.decimalDigitNum = Optional.of(new DecimalDigitNumber(decimalDigitNum));
		}
		if (null == startDigit) {
			this.startDigit = Optional.empty();
		} else {
			this.startDigit = Optional.of(new AcceptedDigit(startDigit));
		}
		if (null == endDigit) {
			this.endDigit = Optional.empty();
		} else {
			this.endDigit = Optional.of(new AcceptedDigit(endDigit));
		}
		if (null == decimalPointCls) {
			this.decimalPointCls = Optional.empty();
		} else {
			this.decimalPointCls = Optional.of(EnumAdaptor.valueOf(decimalPointCls, DecimalPointClassification.class));
		}
		if (null == decimalFraction) {
			this.decimalFraction = Optional.empty();
		} else {
			this.decimalFraction = Optional.of(EnumAdaptor.valueOf(decimalFraction, DecimalFraction.class));
		}
	}  
}
