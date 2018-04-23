package nts.uk.ctx.at.function.dom.annualworkschedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 項目の算出式
*/
@AllArgsConstructor
@Getter
public class CalcFormulaItem extends AggregateRoot
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * コード
    */
    private int setOutCd;
    
    /**
    * コード
    */
    private int itemOutCd;
    
    /**
    * 勤怠項目ID
    */
    private int attendanceItemId;
    
    /**
    * 加, 減
    */
    private int operation;
    
    
}
