package nts.uk.ctx.pr.proto.app.paymentdata.find;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.core.dom.company.CompanyRepository;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PersonalPaymentSettingRepository;

/**
 * PersonalPaymentSettingFinder
 */
@RequestScoped
public class PersonalPaymentSettingFinder {

	/** PersonalPaymentSettingRepository */
	@Inject
	private PersonalPaymentSettingRepository repository;

	/**
	 * Find a company by code.
	 * 
	 * @param companyCode
	 *            code
	 * @return company
	 */
	public Optional<PersonalPaymentSettingDto> find(String companyCode, Integer personId) {
//		return this.repository.find(companyCode, personId).map(d -> PersonalPaymentSettingDto.fromDomain(d));
		return null;
	}

	/**
	 * Find all companies.
	 * 
	 * @return companies
	 */
	public List<PersonalPaymentSettingDto> findAll() {
//		return this.repository.findAll().stream().map(d -> PersonalPaymentSettingDto.fromDomain(d))
//				.collect(Collectors.toList());
		return null;
	}
}
