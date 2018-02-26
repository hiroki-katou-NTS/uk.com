package nts.uk.ctx.exio.app.find.exi.condset;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.exi.condset.AcScreenCondSet;

/**
* 受入選別条件設定
*/
@AllArgsConstructor
@Value
public class AcScreenCondSetDto
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
    public static AcScreenCondSetDto fromDomain(AcScreenCondSet domain)
    {
        return new AcScreenCondSetDto(domain.getCid(), domain.getConditionSetCd(), domain.getAcceptItemNum(), domain.getSelCompareCond(), domain.getTimeCondVal2(), domain.getTimeCondVal1(), domain.getTimeMoCondVal2(), domain.getTimeMoCondVal1(), domain.getDateCondVal2(), domain.getDateCondVal1(), domain.getCharCondVal2(), domain.getCharCondVal1(), domain.getNumCondVal2(), domain.getNumCondVal1(), domain.getVersion());
    }
    
}
