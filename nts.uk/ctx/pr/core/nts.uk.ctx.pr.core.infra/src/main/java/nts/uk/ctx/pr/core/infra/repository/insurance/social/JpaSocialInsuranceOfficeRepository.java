/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.social;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOffice;
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeRepository;

/**
 * The Class JpaSocialInsuranceOfficeRepository.
 */
@Stateless
public class JpaSocialInsuranceOfficeRepository extends JpaRepository implements SocialInsuranceOfficeRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeRepository#
	 * add(nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOffice)
	 */
	@Override
	public void add(SocialInsuranceOffice office) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeRepository#
	 * update(nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOffice)
	 */
	@Override
	public void update(SocialInsuranceOffice office) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeRepository#
	 * remove(java.lang.String, java.lang.Long)
	 */
	@Override
	public void remove(String id, Long version) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeRepository#
	 * findAll(int)
	 */
	@Override
	public List<SocialInsuranceOffice> findAll(int companyCode) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeRepository#
	 * findById(java.lang.String)
	 */
	@Override
	public SocialInsuranceOffice findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeRepository#findByOfficeCode(java.lang.String)
	 */
	@Override
	public SocialInsuranceOffice findByOfficeCode(String officeCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDuplicateCode(OfficeCode code) {
		// TODO Auto-generated method stub
		return false;
	}

}
