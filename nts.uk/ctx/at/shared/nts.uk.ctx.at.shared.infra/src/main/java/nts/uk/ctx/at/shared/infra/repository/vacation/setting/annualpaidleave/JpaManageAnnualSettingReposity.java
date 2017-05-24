/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.annualpaidleave;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.ManageAnnualSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.ManageAnnualSettingReposity;

@Stateless
public class JpaManageAnnualSettingReposity extends JpaRepository
		implements ManageAnnualSettingReposity {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.
	 * ManageAnnualSettingReposity#update(nts.uk.ctx.pr.core.dom.vacation.
	 * setting.annualpaidleave.ManageAnnualSetting)
	 */
	@Override
	public void update(ManageAnnualSetting setting) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.
	 * ManageAnnualSettingReposity#findByCompanyId(java.lang.String)
	 */
	@Override
	public ManageAnnualSetting findByCompanyId(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

}
