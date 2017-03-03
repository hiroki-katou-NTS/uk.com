/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable.history;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistory;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryRepository;

/**
 * The Class JpaCertifyGroupRepository.
 */
@Stateless
public class JpaWageTableHistoryRepository extends JpaRepository
		implements WageTableHistoryRepository {

	@Override
	public void remove(CompanyCode companyCode, String groupCode, Long version) {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(WageTableHistory wageTableHistory) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(WageTableHistory wageTableHistory) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<WageTableHistory> findAll(CompanyCode companyCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<WageTableHistory> findById(CompanyCode companyCode, String code) {
		// TODO Auto-generated method stub
		return null;
	}

}
