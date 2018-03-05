package nts.uk.ctx.exio.app.find.exi.condset;

import java.math.BigDecimal;
import java.util.Date;

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
    private Integer acceptItemNum;
    
    /**
    * 比較条件選択
    */
    private int selectComparisonCondition;
    
    /**
    * 時間‗条件値2
    */
    private Integer timeConditionValue2;
    
    /**
    * 時間‗条件値1
    */
    private Integer timeConditionValue1;
    
    /**
    * 時刻‗条件値2
    */
    private Integer timeMomentConditionValue2;
    
    /**
    * 時刻‗条件値1
    */
    private Integer timeMomentConditionValue1;
    
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
    private BigDecimal numberConditionValue2;
    
    /**
    * 数値‗条件値1
    */
    private BigDecimal numberConditionValue1;
    
    public static AcScreenCondSetDto fromDomain(AcScreenCondSet domain)
    {
        return new AcScreenCondSetDto(domain.getCid(), domain.getConditionSetCd().v(), 
        		domain.getAcceptItemNum().v(), domain.getSelectComparisonCondition().get().value, 
        		domain.getTimeConditionValue2().get().v(), domain.getTimeConditionValue1().get().v(), 
        		domain.getTimeMomentConditionValue2().get().v(), domain.getTimeMomentConditionValue1().get().v(), 
        		domain.getDateConditionValue2(), domain.getDateConditionValue1(), 
        		domain.getCharacterConditionValue2().get().v(), domain.getCharacterConditionValue1().get().v(), 
        		domain.getNumberConditionValue2().get().v() , domain.getNumberConditionValue1().get().v());
    }
    
}
