/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable.element;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.element.WageTableElement;
import nts.uk.ctx.pr.core.dom.wagetable.element.WageTableElementRepository;

/**
 * The Class JpaCertifyGroupRepository.
 */
@Stateless
public class JpaWageTableElementRepository extends JpaRepository implements WageTableElementRepository {

	@Override
	public void add(WageTableElement wageTableElement) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(WageTableElement wageTableElement) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(CompanyCode companyCode, String groupCode, Long version) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<WageTableElement> findAll(CompanyCode companyCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<WageTableElement> findById(CompanyCode companyCode, String code) {
		// TODO Auto-generated method stub
		return null;
	}

}
