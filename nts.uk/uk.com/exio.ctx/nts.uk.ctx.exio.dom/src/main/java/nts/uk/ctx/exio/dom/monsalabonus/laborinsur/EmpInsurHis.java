package nts.uk.ctx.exio.dom.monsalabonus.laborinsur;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 雇用保険履歴
*/
@AllArgsConstructor
@Getter
public class EmpInsurHis extends AggregateRoot
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 履歴ID
    */
    private String hisId;
    
    /**
    * 年月期間
    */
    private int startDate;
    
    /**
    * 年月期間
    */
    private int endDate;
    
    
}
