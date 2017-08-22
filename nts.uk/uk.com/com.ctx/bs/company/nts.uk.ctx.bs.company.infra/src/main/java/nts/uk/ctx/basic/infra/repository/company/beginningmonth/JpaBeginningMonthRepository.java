/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.beginningmonth;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.basic.dom.company.beginningmonth.BeginningMonth;
import nts.uk.ctx.basic.dom.company.beginningmonth.BeginningMonthRepository;
import nts.uk.ctx.basic.infra.entity.company.beginningmonth.CbmstBeginningMonth;

/**
 * The Class JpaBeginningMonthRepository.
 */
@Stateless
public class JpaBeginningMonthRepository extends JpaRepository implements BeginningMonthRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.CompanyRepository#getComanyId(java.lang.
	 * String)
	 */
	@Override
	public Optional<BeginningMonth> find(String companyId) {
		return this.queryProxy().find(companyId, CbmstBeginningMonth.class).map(bm -> this.toDomain(bm));
	}

	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the beginning month
	 */
	private BeginningMonth toDomain(CbmstBeginningMonth entity) {
		return new BeginningMonth(new JpaBeginningMonthGetMemento(entity));
	}

}
