package nts.uk.ctx.pr.shared.dom.familyinfo.empfamilysocialins;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 社員家族社会保険区分
*/
@Getter
@AllArgsConstructor
public class EmpFamilySocialInsCtg extends AggregateRoot {
    
    /**
    * 履歴ID
    */
    private String historyId;
	
	/**
    * 被扶養者区分
    */
	private Distinction dependent;
    
}
