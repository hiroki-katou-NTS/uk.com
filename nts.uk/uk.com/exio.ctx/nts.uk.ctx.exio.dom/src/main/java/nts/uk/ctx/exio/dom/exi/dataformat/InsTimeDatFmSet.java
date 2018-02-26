package nts.uk.ctx.exio.dom.exi.dataformat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
* 時刻型データ形式設定
*/
@AllArgsConstructor
@Getter
@Setter
public class InsTimeDatFmSet extends AggregateRoot
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
    
    public static InsTimeDatFmSet createFromJavaType(Long version, String cid, String conditionSetCd, int acceptItemNum, int delimiterSet, int fixedValue, int hourMinSelect, int effectiveDigitLength, int roundProc, int decimalSelect, String valueOfFixedValue, int startDigit, int endDigit, int roundProcCls)
    {
        InsTimeDatFmSet  insTimeDatFmSet =  new InsTimeDatFmSet(cid, conditionSetCd, acceptItemNum, delimiterSet, fixedValue, hourMinSelect, effectiveDigitLength, roundProc, decimalSelect, valueOfFixedValue, startDigit, endDigit,  roundProcCls);
        insTimeDatFmSet.setVersion(version);
        return insTimeDatFmSet;
    }
    
}
