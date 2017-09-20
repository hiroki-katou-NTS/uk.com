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
import nts.uk.ctx.bs.company.infra.entity.company.BcmmtCompany;

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
		builderString.append(" FROM BcmmtCompany e");
		builderString.append(" WHERE e.abolitionAtr = 0 ");
		GETALLCOMPANY = builderString.toString();
	}

	/**
	 * @param entity
	 * @return new Company(companyCode,companyName,companyId,isAboltiton)
	 */
	private static Company toSimpleDomain(BcmmtCompany entity) {
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
		return this.queryProxy().find(companyId, BcmmtCompany.class).map(company -> this.toDomain(company));
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the company
	 */
	private Company toDomain(BcmmtCompany entity) {
		return new Company(new JpaCompanyGetMemento(entity));
	}

	@Override
	public List<Company> getAllCompany() {
		return this.queryProxy().query(GETALLCOMPANY, BcmmtCompany.class).getList(c -> toSimpleDomain(c));
	}

}
