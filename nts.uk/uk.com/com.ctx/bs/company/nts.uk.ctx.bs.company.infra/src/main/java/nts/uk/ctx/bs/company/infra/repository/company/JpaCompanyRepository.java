/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.company.infra.repository.company;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.ctx.bs.company.infra.entity.company.BcmdtCompany;

/**
 * The Class JpaCompanyRepository.
 */
@Stateless
public class JpaCompanyRepository extends JpaRepository implements CompanyRepository {

	private static final String GETALLCOMPANY;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM BcmdtCompany e");
		GETALLCOMPANY = builderString.toString();
	}

	private static Company toDomainNotFullAtr(BcmdtCompany entity) {
		val domain = Company.createFromJavaType(entity.getCcd(), entity.getCompanyName(), entity.getCid(),
				entity.getAbolitionAtr());
		return domain;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.CompanyRepository#getComanyId(java.lang.String)
	 */
	@Override
	public Optional<Company> getComanyById(String companyId) {
		return this.queryProxy().find(companyId, BcmdtCompany.class).map(company -> this.toDomain(company));
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the company
	 */
	private Company toDomain(BcmdtCompany entity) {
		return new Company(new JpaCompanyGetMemento(entity));
	}

	@Override
	public List<Company> getAllCompany() {
		// TODO Auto-generated method stub
		return this.queryProxy().query(GETALLCOMPANY, BcmdtCompany.class).getList(c -> toDomainNotFullAtr(c));
	}

}
