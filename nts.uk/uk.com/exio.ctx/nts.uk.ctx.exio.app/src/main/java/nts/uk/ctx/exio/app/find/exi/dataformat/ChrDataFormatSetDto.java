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
public class ChrDataFormatSetDto
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
    
    
    private Long version;
    public static ChrDataFormatSetDto fromDomain(ChrDataFormatSet domain)
    {
        return new ChrDataFormatSetDto(domain.getCid(), domain.getConditionSetCd(), domain.getAcceptItemNum(), domain.getCdEditing(), domain.getFixedValue(), domain.getEffectiveDigitLength(), domain.getCdConvertCd(), domain.getCdEditMethod(), domain.getCdEditDigit(), domain.getFixedVal(), domain.getStartDigit(), domain.getEndDigit(), domain.getVersion());
    }
    
}
