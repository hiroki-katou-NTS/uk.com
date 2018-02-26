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
public class NumDataFormatSetDto
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
    
    
    private Long version;
    public static NumDataFormatSetDto fromDomain(NumDataFormatSet domain)
    {
        return new NumDataFormatSetDto(domain.getCid(), domain.getConditionSetCd(), domain.getAcceptItemNum(), domain.getFixedValue(), domain.getDecimalDivision(), domain.getEffectiveDigitLength(), domain.getCdConvertCd(), domain.getValueOfFixedValue(), domain.getDecimalDigitNum(), domain.getStartDigit(), domain.getEndDigit(), domain.getDecimalPointCls(), domain.getDecimalFraction(), domain.getVersion());
    }
    
}
