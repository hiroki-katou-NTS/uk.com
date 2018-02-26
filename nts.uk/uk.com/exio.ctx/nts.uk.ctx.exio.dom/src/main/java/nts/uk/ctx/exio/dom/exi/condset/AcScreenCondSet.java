package nts.uk.ctx.exio.dom.exi.condset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
* 受入選別条件設定
*/
@AllArgsConstructor
@Getter
@Setter
public class AcScreenCondSet extends AggregateRoot
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
    * 受入項目の番号
    */
    private int acceptItemNum;
    
    /**
    * 比較条件選択
    */
    private int selCompareCond;
    
    /**
    * 時間‗条件値2
    */
    private int timeCondVal2;
    
    /**
    * 時間‗条件値1
    */
    private int timeCondVal1;
    
    /**
    * 時刻‗条件値2
    */
    private int timeMoCondVal2;
    
    /**
    * 時刻‗条件値1
    */
    private int timeMoCondVal1;
    
    /**
    * 日付‗条件値2
    */
    private GeneralDate dateCondVal2;
    
    /**
    * 日付‗条件値1
    */
    private GeneralDate dateCondVal1;
    
    /**
    * 文字‗条件値2
    */
    private String charCondVal2;
    
    /**
    * 文字‗条件値1
    */
    private String charCondVal1;
    
    /**
    * 数値‗条件値2
    */
    private String numCondVal2;
    
    /**
    * 数値‗条件値1
    */
    private String numCondVal1;
    
    public static AcScreenCondSet createFromJavaType(Long version, String cid, String conditionSetCd, int acceptItemNum, int selCompareCond, int timeCondVal2, int timeCondVal1, int timeMoCondVal2, int timeMoCondVal1, GeneralDate dateCondVal2, GeneralDate dateCondVal1, String charCondVal2, String charCondVal1, String numCondVal2, String numCondVal1)
    {
        AcScreenCondSet  acScreenCondSet =  new AcScreenCondSet(cid, conditionSetCd, acceptItemNum, selCompareCond, timeCondVal2, timeCondVal1, timeMoCondVal2, timeMoCondVal1, dateCondVal2, dateCondVal1, charCondVal2, charCondVal1, numCondVal2,  numCondVal1);
        acScreenCondSet.setVersion(version);
        return acScreenCondSet;
    }
    
}
