package nts.uk.ctx.exio.dom.exi.dataformat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.exio.dom.exi.codeconvert.AcceptCdConvert;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
* 文字型データ形式設定
*/
@AllArgsConstructor
@Getter
@Setter
public class ChrDataFormatSet extends AggregateRoot
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
    * コード編集
    */
    private NotUseAtr cdEditing;
    
    /**
    * 固定値
    */
    private NotUseAtr fixedValue;
    
    /**
    * 有効桁長
    */
    private NotUseAtr effectiveDigitLength;
    
    /**
    * コード変換コード
    */
    private AcceptCdConvert cdConvertCd;
    
    /**
    * コード編集方法
    */
    private FixedLengthEditingMethod cdEditMethod;
    
    /**
    * コード編集桁
    */
    private CodeEditDigit cdEditDigit;
    
    /**
    * 固定値の値
    */
    private ValueOfFixed fixedVal;
    
    /**
    * 有効桁数開始桁
    */
    private StartDigit startDigit;
    
    /**
    * 有効桁数終了桁
    */
    private EndDigit endDigit;
    
    public static ChrDataFormatSet createFromJavaType(Long version, String cid, String conditionSetCd, int acceptItemNum, NotUseAtr cdEditing, NotUseAtr fixedValue, NotUseAtr effectiveDigitLength, AcceptCdConvert cdConvertCd, FixedLengthEditingMethod cdEditMethod, CodeEditDigit cdEditDigit, ValueOfFixed fixedVal, StartDigit startDigit, EndDigit endDigit)
    {
        ChrDataFormatSet  chrDataFormatSet =  new ChrDataFormatSet(cid, conditionSetCd, acceptItemNum, cdEditing, fixedValue, effectiveDigitLength, cdConvertCd, cdEditMethod, cdEditDigit, fixedVal, startDigit,  endDigit);
        chrDataFormatSet.setVersion(version);
        return chrDataFormatSet;
    }
    
}
