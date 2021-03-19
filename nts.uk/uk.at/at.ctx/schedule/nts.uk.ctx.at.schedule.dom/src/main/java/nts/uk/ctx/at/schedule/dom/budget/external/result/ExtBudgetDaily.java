package nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 日次の外部予算実績 Root
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.勤務予定.予算管理.外部予算実績
 * @author HieuLt
 */

@AllArgsConstructor
public class ExtBudgetDaily implements DomainAggregate{
	/**対象組織識別情報 **/
	@Getter 
	private final TargetOrgIdenInfor targetOrg;
	
	/** 外部予算実績項目コード **/
	@Getter
	private final ExtBudgetActItemCode itemCode;
	/** 年月日 **/
	@Getter
	private final GeneralDate ymd;
	
	/**	値- 外部予算実績値  **/
	@Getter
    private ExtBudgetActualValues actualValue;
	
}
