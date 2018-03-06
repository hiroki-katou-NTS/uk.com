package nts.uk.ctx.exio.dom.exi.dataformat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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
    private AcceptCdConvert cdConvertCd;
    
    /**
    * 固定値の値
    */
    private ValueOfFixed valueOfFixedValue;
    
    /**
    * 少数桁数
    */
    private DecimalDigitNumber decimalDigitNum;
    
    /**
    * 有効桁数開始桁
    */
    private StartDigit startDigit;
    
    /**
    * 有効桁数終了桁
    */
    private EndDigit endDigit;
    
    /**
    * 小数点区分
    */
    private DecimalPointClassification decimalPointCls;
    
    /**
    * 小数端数
    */
    private DecimalFraction decimalFraction;
    
    public static NumDataFormatSet createFromJavaType(Long version, String cid, String conditionSetCd, int acceptItemNum, NotUseAtr fixedValue, DecimalDivision decimalDivision, NotUseAtr effectiveDigitLength, AcceptCdConvert cdConvertCd, ValueOfFixed valueOfFixedValue, DecimalDigitNumber decimalDigitNum, StartDigit startDigit, EndDigit endDigit, DecimalPointClassification decimalPointCls, DecimalFraction decimalFraction)
    {
        NumDataFormatSet  numDataFormatSet =  new NumDataFormatSet(cid, conditionSetCd, acceptItemNum, fixedValue, decimalDivision, effectiveDigitLength, cdConvertCd, valueOfFixedValue, decimalDigitNum, startDigit, endDigit, decimalPointCls,  decimalFraction);
        numDataFormatSet.setVersion(version);
        return numDataFormatSet;
    }
    
}
