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
    private String setOutCd;
    
    /**
    * コード
    */
    private String itemOutCd;
    
    /**
    * 勤怠項目ID
    */
    private int attendanceItemId;
    
    /**
    * オペレーション
    */
    private int operation;

	public static CalcFormulaItem createFromJavaType(String cid, String setOutCd, String itemOutCd, int attendanceItemId, int operation) {
		return new CalcFormulaItem(cid, setOutCd, itemOutCd, attendanceItemId, operation);
	}
}
