package nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
/**
 * 会社の勤務方法の関係性
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.勤務方法の関係性.会社の勤務方法の関係性
 * @author lan_lt
 *
 */
@AllArgsConstructor
@Getter
public class WorkMethodRelationshipCompany implements DomainAggregate{
	
	/**	勤務方法の関係性*/
	private WorkMethodRelationship workMethodRelationship;
	
}
