package nts.uk.ctx.hr.develop.dom.careermgmt.careertype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.history.DateHistoryItem;

/**職務の履歴*/
@AllArgsConstructor
@Getter
public class CareerTypeHistory extends AggregateRoot{

	private String companyId;
	
	private String careerTypeId;
	
	private DateHistoryItem careerTypeHistory;
}
