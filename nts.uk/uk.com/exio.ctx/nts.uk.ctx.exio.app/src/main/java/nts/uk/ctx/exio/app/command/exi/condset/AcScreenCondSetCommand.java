package nts.uk.ctx.exio.app.command.exi.condset;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class AcScreenCondSetCommand
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
    private int numberConditionValue2;
    
    /**
    * 数値‗条件値1
    */
    private int numberConditionValue1;
}
