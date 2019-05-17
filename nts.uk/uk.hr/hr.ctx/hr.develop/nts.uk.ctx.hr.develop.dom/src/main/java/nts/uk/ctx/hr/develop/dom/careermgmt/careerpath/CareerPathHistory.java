package nts.uk.ctx.hr.develop.dom.careermgmt.careerpath;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**キャリアパスの履歴*/
@AllArgsConstructor
@Getter
public class CareerPathHistory extends AggregateRoot {

	private String companyId;
	
	private List<DateHistoryItem> careerPathHistory;
	
}
