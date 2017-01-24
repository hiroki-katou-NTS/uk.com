/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.labor.businesstype;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateRepository;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.InsuranceBusinessType;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.InsuranceBusinessTypeRepository;

/**
 * The Class JpaAccidentInsuranceRateRepository.
 */
@Stateless
public class JpaInsuranceBusinessTypeRepository extends JpaRepository implements InsuranceBusinessTypeRepository {

	@Override
	public void add(InsuranceBusinessType type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(InsuranceBusinessType type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String id, Long version) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<InsuranceBusinessType> findAll(int companyCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InsuranceBusinessType findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
