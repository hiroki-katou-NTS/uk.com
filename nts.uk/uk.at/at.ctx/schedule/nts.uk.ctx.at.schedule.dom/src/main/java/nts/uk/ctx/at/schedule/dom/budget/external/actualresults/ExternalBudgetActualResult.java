package nts.uk.ctx.at.schedule.dom.budget.external.actualresults;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetCd;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 日次の外部予算実績
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.予算.外部予算.外部予算実績.日次の外部予算実績
 * @author HieuLt
 */
@Getter
@AllArgsConstructor
public class ExternalBudgetActualResult implements DomainAggregate {

	/** 対象組織識別情報 **/
	private final TargetOrgIdenInfor targetOrg;

	/** 外部予算実績項目コード **/
	private final ExternalBudgetCd itemCode;

	/** 年月日 **/
	private final GeneralDate ymd;

	/** 値- 外部予算実績値 **/
	private ExternalBudgetValues actualValue;

}
