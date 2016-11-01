package nts.uk.shr.infra.data.repository;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.GeneralRepository;
import nts.uk.ctx.core.dom.company.Company;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.core.dom.company.CompanyRepository;
import nts.uk.shr.infra.data.entity.company.SmpmtCompany;

@RequestScoped
public class JpaCompanyRepository extends GeneralRepository implements CompanyRepository {

	@Override
	public Optional<Company> find(String companyCode) {
		return this.queryProxy().find(companyCode, SmpmtCompany.class)
				.map(c -> toDomain(c));
		
// alternative:
//		return this.queryProxy().query("SELECT c FROM SmpmtCompany c WHERE c.code = :code", SmpmtCompany.class)
//				.setParameter("code", companyCode)
//				.getSingle(c -> toDomain(c));
	}

	@Override
	public List<Company> findAll() {
		return this.queryProxy().query("SELECT c FROM SmpmtCompany c", SmpmtCompany.class)
				.getList(c -> toDomain(c));
	}

	@Override
	public void add(Company company) {
		this.commandProxy().insert(toEntity(company));
	}

	@Override
	public void update(Company company) {
		this.commandProxy().update(toEntity(company));
	}

	@Override
	public void remove(CompanyCode companyCode) {
		this.commandProxy().remove(SmpmtCompany.class, companyCode.v());
	}
	
	private static SmpmtCompany toEntity(Company domain) {
		val entity = new SmpmtCompany();
		entity.setVersion(domain.getVersion());
		entity.setCode(domain.getCode().v());
		entity.setName(domain.getName().v());
		
		return entity;
	}

	private static Company toDomain(SmpmtCompany entity) {
		val domain = Company.createFromJavaType(entity.getCode(), entity.getName());
		domain.setVersion(entity.getVersion());
		
		return domain;
	}

}
