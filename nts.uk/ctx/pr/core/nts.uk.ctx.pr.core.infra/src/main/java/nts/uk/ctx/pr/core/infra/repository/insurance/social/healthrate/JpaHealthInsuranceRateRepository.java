/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.social.healthrate;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRateRepository;

/**
 * The Class JpaHealthInsuranceRateRepository.
 */
@Stateless
public class JpaHealthInsuranceRateRepository extends JpaRepository implements HealthInsuranceRateRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthrate.
	 * HealthInsuranceRateRepository#add(nts.uk.ctx.pr.core.dom.insurance.social
	 * .healthrate.HealthInsuranceRate)
	 */
	@Override
	public void add(HealthInsuranceRate rate) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthrate.
	 * HealthInsuranceRateRepository#update(nts.uk.ctx.pr.core.dom.insurance.
	 * social.healthrate.HealthInsuranceRate)
	 */
	@Override
	public void update(HealthInsuranceRate rate) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthrate.
	 * HealthInsuranceRateRepository#remove(java.lang.String, java.lang.Long)
	 */
	@Override
	public void remove(String id, Long version) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthrate.
	 * HealthInsuranceRateRepository#findAll(int)
	 */
	@Override
	public List<HealthInsuranceRate> findAll(int companyCode) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthrate.
	 * HealthInsuranceRateRepository#findById(java.lang.String)
	 */
	@Override
	public HealthInsuranceRate findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
