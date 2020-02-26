package nts.uk.ctx.hr.develop.dom.careermgmt.careerclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.history.DateHistoryItem;

/**キャリアの履歴*/
@AllArgsConstructor
@Getter
public class CareerClassHistory extends AggregateRoot{

	private String companyId;
	
	private String careerClassId;
	
	private DateHistoryItem careerClassHistory;
	
	
}
