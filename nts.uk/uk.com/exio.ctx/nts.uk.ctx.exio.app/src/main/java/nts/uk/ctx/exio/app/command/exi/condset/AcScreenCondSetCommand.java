package nts.uk.ctx.exio.app.command.exi.condset;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

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
    
    private Long version;

}
