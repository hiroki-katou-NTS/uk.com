package nts.uk.ctx.at.aggregation.dom.schedulecounter.budget.laborcost;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 人件費予算
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.予算.人件費予算.人件費予算
 * @author kumiko_otake
 */
@AllArgsConstructor
@Getter
public class LaborCostBudget implements DomainAggregate {

	/** 対象組織 **/
	private final TargetOrgIdenInfor targetOrg;

	/** 年月日 **/
	private final GeneralDate ymd;

	/** 予算 **/
	private final LaborCostBudgetAmount amount;

}
