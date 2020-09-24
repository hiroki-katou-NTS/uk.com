package nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
/**
 * 会社の勤務方法の関係性
 * @author lan_lt
 *
 */
@AllArgsConstructor
@Getter
public class WorkingSpecifyRelationshipCom implements DomainAggregate{
	//会社ID
	private final String companyId;
	
	//	勤務方法の関係性
	private WorkMethodRelationship workMethodRelationship;
	
}
