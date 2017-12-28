/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;

/**
 * The Class JpaFlowWorkSettingRepository.
 */

@Stateless
public class JpaFlowWorkSettingRepository extends JpaRepository
		implements FlowWorkSettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository#find(
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<FlowWorkSetting> find(String companyId, String workTimeCode) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository#save(
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting)
	 */
	@Override
	public void save(FlowWorkSetting domain) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean remove(String companyId, String workTimeCode) {
		// TODO Auto-generated method stub
		return false;
	}

}
