package nts.uk.ctx.exio.app.find.exi.dataformat;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.exi.dataformat.InsTimeDatFmSet;

/**
* 時刻型データ形式設定
*/
@AllArgsConstructor
@Value
public class InsTimeDatFmSetDto
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
    * 区切り文字設定
    */
    private int delimiterSet;
    
    /**
    * 固定値
    */
    private int fixedValue;
    
    /**
    * 時分/分選択
    */
    private int hourMinSelect;
    
    /**
    * 有効桁長
    */
    private int effectiveDigitLength;
    
    /**
    * 端数処理
    */
    private int roundProc;
    
    /**
    * 進数選択
    */
    private int decimalSelect;
    
    /**
    * 固定値の値
    */
    private String valueOfFixedValue;
    
    /**
    * 有効桁数開始桁
    */
    private int startDigit;
    
    /**
    * 有効桁数終了桁
    */
    private int endDigit;
    
    /**
    * 端数処理区分
    */
    private int roundProcCls;
    
    
    private Long version;
    public static InsTimeDatFmSetDto fromDomain(InsTimeDatFmSet domain)
    {
        return new InsTimeDatFmSetDto(domain.getCid(), domain.getConditionSetCd(), domain.getAcceptItemNum(), domain.getDelimiterSet(), domain.getFixedValue(), domain.getHourMinSelect(), domain.getEffectiveDigitLength(), domain.getRoundProc(), domain.getDecimalSelect(), domain.getValueOfFixedValue(), domain.getStartDigit(), domain.getEndDigit(), domain.getRoundProcCls(), domain.getVersion());
    }
    
}
