package nts.uk.ctx.at.schedule.dom.displaysetting;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

public interface DisplaySettingByDateForOrgRepository {

	/**
	 * get
	 * @param targetOrg
	 * @return
	 */
	Optional<DisplaySettingByDateForOrganization> get (String companyId, TargetOrgIdenInfor targetOrg);
	
	/**
	 * insert(組織別スケジュール修正日付別の表示設定)
	 * @param dispSetOrg
	 */
	void insert (String companyId, DisplaySettingByDateForOrganization dispSetOrg);
	
	/**
	 * update(組織別スケジュール修正日付別の表示設定)
	 * @param dispSetOrg
	 */
	void update (String companyId, DisplaySettingByDateForOrganization dispSetOrg);
}
