/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableHead;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadRepository;

/**
 * The Class JpaCertifyGroupRepository.
 */
@Stateless
public class JpaWageTableHeadRepository extends JpaRepository implements WageTableHeadRepository {

	@Override
	public void add(WageTableHead wageTableHead) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(WageTableHead wageTableHead) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(CompanyCode companyCode, String groupCode, Long version) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<WageTableHead> findAll(CompanyCode companyCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<WageTableHead> findById(CompanyCode companyCode, String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDuplicateCode(CompanyCode companyCode, WageTableCode code) {
		// TODO Auto-generated method stub
		return false;
	}

}
