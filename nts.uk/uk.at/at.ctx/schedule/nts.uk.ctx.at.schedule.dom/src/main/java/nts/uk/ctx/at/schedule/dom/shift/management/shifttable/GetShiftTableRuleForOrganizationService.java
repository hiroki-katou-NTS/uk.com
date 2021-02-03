package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 組織のシフト表のルールを取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.シフト表.組織のシフト表のルールを取得する
 * @author dan_pv
 *
 */
public class GetShiftTableRuleForOrganizationService {
	
	/**
	 * 指定した組織のシフト表のルールを取得する。 できなければ会社のシフト表のルールを取得する。 会社のシフト表のルールがない場合、emptyを返す。 
	 * @param require
	 * @param targetOrg 対象組織識別情報
	 * @return
	 */
	public static Optional<ShiftTableRule> get(Require require, TargetOrgIdenInfor targetOrg) {
		
		// get shift table rule of organization
		Optional<ShiftTableRuleForOrganization> orgShiftTable = require.getOrganizationShiftTable(targetOrg);
		if (orgShiftTable.isPresent()) {
			return Optional.of(orgShiftTable.get().getShiftTableRule());
		}
		
		// get shift table rule of company
		Optional<ShiftTableRuleForCompany> comShiftTable = require.getCompanyShiftTable();
		if (comShiftTable.isPresent()) {
			return Optional.of(comShiftTable.get().getShiftTableRule());
		}
		
		return Optional.empty();
	}
	
	public static interface Require {
		
		/**
		 * 組織のシフト表のルールを取得する	
		 * @param targetOrg 対象組織識別情報
		 * @return
		 */
		Optional<ShiftTableRuleForOrganization> getOrganizationShiftTable(TargetOrgIdenInfor targetOrg);
		
		/**
		 * 会社のシフト表のルールを取得する	
		 * @return
		 */
		Optional<ShiftTableRuleForCompany> getCompanyShiftTable();
		
	}

}
