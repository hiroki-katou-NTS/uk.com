package nts.uk.ctx.exio.dom.exi.dataformat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

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
    private int cdConvertCd;
    
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
    
    public static NumDataFormatSet createFromJavaType(Long version, String cid, String conditionSetCd, int acceptItemNum, int fixedValue, int decimalDivision, int effectiveDigitLength, int cdConvertCd, String valueOfFixedValue, int decimalDigitNum, int startDigit, int endDigit, int decimalPointCls, int decimalFraction)
    {
        NumDataFormatSet  numDataFormatSet =  new NumDataFormatSet(cid, conditionSetCd, acceptItemNum, fixedValue, decimalDivision, effectiveDigitLength, cdConvertCd, valueOfFixedValue, decimalDigitNum, startDigit, endDigit, decimalPointCls,  decimalFraction);
        numDataFormatSet.setVersion(version);
        return numDataFormatSet;
    }
    
}
