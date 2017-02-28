/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.wageledger;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSetting;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingCode;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingRepository;

/**
 * The Class JpaWLOutputSettingRepository.
 */
@Stateless
public class JpaWLOutputSettingRepository implements WLOutputSettingRepository {

	@Override
	public void create(WLOutputSetting outputSetting) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(WLOutputSetting outputSetting) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(WLOutputSettingCode code) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public WLOutputSetting findByCode(WLOutputSettingCode code, CompanyCode companyCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isExist(WLOutputSettingCode code) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
