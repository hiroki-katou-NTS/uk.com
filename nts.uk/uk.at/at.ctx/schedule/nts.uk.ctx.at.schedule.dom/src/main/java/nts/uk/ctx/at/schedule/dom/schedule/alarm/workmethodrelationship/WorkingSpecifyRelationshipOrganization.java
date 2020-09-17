package nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
/**
 * 組織の勤務方法の関係性
 * @author lan_lt
 *
 */
@Getter
@AllArgsConstructor
public class WorkingSpecifyRelationshipOrganization implements DomainAggregate{
	//対象組織
	private final TargetOrgIdenInfor targetOrg;
	
	//	勤務方法の関係性
	private WorkMethodRelationship workMethodRelationship;
	
}
