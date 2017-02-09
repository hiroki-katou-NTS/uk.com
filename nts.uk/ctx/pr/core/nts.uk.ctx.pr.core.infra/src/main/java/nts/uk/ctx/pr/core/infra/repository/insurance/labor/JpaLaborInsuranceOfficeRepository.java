/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.labor;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOffice;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository;

/**
 * The Class JpaLaborInsuranceOfficeRepository.
 */
@Stateless
public class JpaLaborInsuranceOfficeRepository extends JpaRepository implements LaborInsuranceOfficeRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository#add
	 * (nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOffice)
	 */
	@Override
	public void add(LaborInsuranceOffice office) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository#
	 * update(nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOffice)
	 */
	@Override
	public void update(LaborInsuranceOffice office) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository#
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
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository#
	 * findAll(int)
	 */
	@Override
	public List<LaborInsuranceOffice> findAll(int companyCode) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository#findById(nts.uk.ctx.pr.core.dom.insurance.OfficeCode)
	 */
	@Override
	public LaborInsuranceOffice findById(OfficeCode code) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository#isDuplicateCode(nts.uk.ctx.pr.core.dom.insurance.OfficeCode)
	 */
	@Override
	public boolean isDuplicateCode(OfficeCode code) {
		// TODO Auto-generated method stub
		return false;
	}

}
