package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 組織別のシフト表のルール
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.シフト表.組織のシフト表のルール
 * @author hiroko_miura
 *
 */
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class ShiftTableRuleForOrganization implements DomainAggregate {

	/** 対象組織 */
	private final TargetOrgIdenInfor targetOrg;
	
	/** シフト表のルール */
	private ShiftTableRule shiftTableRule;
}
