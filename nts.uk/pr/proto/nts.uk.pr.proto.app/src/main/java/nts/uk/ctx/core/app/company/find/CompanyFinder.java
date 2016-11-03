package nts.uk.ctx.core.app.company.find;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.core.dom.company.CompanyRepository;

@RequestScoped
public class CompanyFinder {

	@Inject
	private CompanyRepository repository;

	public CompanyDto find(String companyCode) {
		return this.repository.find(companyCode).map(d -> CompanyDto.fromDomain(d)).get();
	}
	
	public List<CompanyDto> findAll() {
		return this.repository.findAll().stream()
				.map(d -> CompanyDto.fromDomain(d))
				.collect(Collectors.toList());
	}
}
