/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.wageledger;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSetting;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingRepository;

/**
 * The Class JpaWLOutputSettingRepository.
 */
@Stateless
public class JpaWLOutputSettingRepository implements WLOutputSettingRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingRepository
	 * #create(nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSetting)
	 */
	@Override
	public void create(WLOutputSetting outputSetting) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingRepository
	 * #update(nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSetting)
	 */
	@Override
	public void update(WLOutputSetting outputSetting) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingRepository
	 * #remove(nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSetting)
	 */
	@Override
	public void remove(String code) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingRepository
	 * #find(java.lang.String, java.lang.String)
	 */
	@Override
	public WLOutputSetting findByCode(String code, String companyCode) {
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingRepository
	 * #isExist(java.lang.String)
	 */
	@Override
	public boolean isExist(String code) {
		// TODO Auto-generated method stub
		return false;
	}
}
