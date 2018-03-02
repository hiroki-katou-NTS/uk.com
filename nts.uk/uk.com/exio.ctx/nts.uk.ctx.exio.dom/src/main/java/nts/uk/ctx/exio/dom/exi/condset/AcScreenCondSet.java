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
    private int selectComparisonCondition;
    
    /**
    * 時間‗条件値2
    */
    private int timeConditionValue2;
    
    /**
    * 時間‗条件値1
    */
    private int timeConditionValue1;
    
    /**
    * 時刻‗条件値2
    */
    private int timeMomentConditionValue2;
    
    /**
    * 時刻‗条件値1
    */
    private int timeMomentConditionValue1;
    
    /**
    * 日付‗条件値2
    */
    private GeneralDate dateConditionValue2;
    
    /**
    * 日付‗条件値1
    */
    private GeneralDate dateConditionValue1;
    
    /**
    * 文字‗条件値2
    */
    private String characterConditionValue2;
    
    /**
    * 文字‗条件値1
    */
    private String characterConditionValue1;
    
    /**
    * 数値‗条件値2
    */
    private String numberConditionValue2;
    
    /**
    * 数値‗条件値1
    */
    private String numberConditionValue1;
   
    public static AcScreenCondSet createFromJavaType(Long version, String cid, String conditionSetCd, int acceptItemNum, int selCompareCond, int timeCondVal2, int timeCondVal1, int timeMoCondVal2, int timeMoCondVal1, GeneralDate dateCondVal2, GeneralDate dateCondVal1, String charCondVal2, String charCondVal1, String numCondVal2, String numCondVal1)
    {
        AcScreenCondSet  acScreenCondSet =  new AcScreenCondSet(cid, conditionSetCd, acceptItemNum, selCompareCond, timeCondVal2, timeCondVal1, timeMoCondVal2, timeMoCondVal1, dateCondVal2, dateCondVal1, charCondVal2, charCondVal1, numCondVal2,  numCondVal1);
        acScreenCondSet.setVersion(version);
        return acScreenCondSet;
    }
    
}
