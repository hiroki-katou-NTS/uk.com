/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.company.infra.repository.company;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.ctx.bs.company.infra.entity.company.CmnmtCompany;

/**
 * The Class JpaCompanyRepository.
 */
@Stateless
public class JpaCompanyRepository extends JpaRepository implements CompanyRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.basic.dom.company.CompanyRepository#getComanyId(java.lang.String)
	 */
	@Override
	public Optional<Company> getComanyById(String companyId) {
		return this.queryProxy().find(companyId, CmnmtCompany.class)
				.map(company -> this.toDomain(company));
	}

	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the company
	 */
	private Company toDomain(CmnmtCompany entity){
		return new Company(new JpaCompanyGetMemento(entity));
	}
	
}
