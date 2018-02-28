package nts.uk.ctx.exio.dom.exi.dataformat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

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
    private int cdConvertCd;
    
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
    
    public static ChrDataFormatSet createFromJavaType(Long version, String cid, String conditionSetCd, int acceptItemNum, int cdEditing, int fixedValue, int effectiveDigitLength, int cdConvertCd, int cdEditMethod, int cdEditDigit, String fixedVal, int startDigit, int endDigit)
    {
        ChrDataFormatSet  chrDataFormatSet =  new ChrDataFormatSet(cid, conditionSetCd, acceptItemNum, cdEditing, fixedValue, effectiveDigitLength, cdConvertCd, cdEditMethod, cdEditDigit, fixedVal, startDigit,  endDigit);
        chrDataFormatSet.setVersion(version);
        return chrDataFormatSet;
    }
    
}
