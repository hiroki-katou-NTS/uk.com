package nts.uk.ctx.pr.shared.dom.familyinfo.empfamilysocialins;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.history.DateHistoryItem;

import java.util.List;

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

    public EmpFamilySocialInsCtg(String historyId, int dependent ) {
        this.historyId = historyId;
        this.dependent = EnumAdaptor.valueOf(dependent,Distinction.class);
    }
    
}
