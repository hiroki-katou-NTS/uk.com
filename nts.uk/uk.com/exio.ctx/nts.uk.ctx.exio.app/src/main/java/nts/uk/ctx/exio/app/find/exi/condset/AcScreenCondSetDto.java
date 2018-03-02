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
    
    
    private Long version;
    public static AcScreenCondSetDto fromDomain(AcScreenCondSet domain)
    {
        return new AcScreenCondSetDto(domain.getCid(), domain.getConditionSetCd(), domain.getAcceptItemNum(), domain.getSelectComparisonCondition(), domain.getTimeConditionValue2(), domain.getTimeConditionValue1(), domain.getTimeMomentConditionValue2(), domain.getTimeMomentConditionValue1(), domain.getDateConditionValue2(), domain.getDateConditionValue1(), domain.getCharacterConditionValue2(), domain.getCharacterConditionValue1(), domain.getNumberConditionValue2(), domain.getNumberConditionValue1(), domain.getVersion());
    }
    
}
