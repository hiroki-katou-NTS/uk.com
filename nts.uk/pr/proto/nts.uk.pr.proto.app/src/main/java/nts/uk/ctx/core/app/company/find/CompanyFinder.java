package nts.uk.ctx.core.app.company.find;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.core.dom.company.CompanyRepository;

/**
 * CompanyFinder
 */
@RequestScoped
public class CompanyFinder {

	/** CompanyRepository */
	@Inject
	private CompanyRepository repository;

	/**
	 * Find a company by code.
	 * 
	 * @param companyCode code
	 * @return company
	 */
	public Optional<CompanyDto> find(String companyCode) {
		return this.repository.find(companyCode).map(d -> CompanyDto.fromDomain(d));
	}
	
	/**
	 * Find all companies.
	 * 
	 * @return companies
	 */
	public List<CompanyDto> findAll() {
		return this.repository.findAll().stream()
				.map(d -> CompanyDto.fromDomain(d))
				.collect(Collectors.toList());
	}
}
