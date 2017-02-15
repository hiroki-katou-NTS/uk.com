/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.pensionrate.find;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRate;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRepository;

/**
 * The Class PensionRateFinder.
 */
@Stateless
public class PensionRateFinder {

	/** The pension rate repository. */
	@Inject
	private PensionRateRepository pensionRateRepo;

	/**
	 * Find.
	 *
	 * @param id
	 *            the id
	 * @return the optional
	 */
	public Optional<PensionRateDto> find(String id) {
		Optional<PensionRate> pensionRate = this.pensionRateRepo.findById(id);
		PensionRateDto dto = PensionRateDto.builder().build();
		if (pensionRate.isPresent()) {
			pensionRate.get().saveToMemento(dto);
		}
		return Optional.ofNullable(dto);
	}

	/**
	 * Find all.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the list
	 */
	public List<PensionRateDto> findAll(String companyCode) {
		return pensionRateRepo.findAll(companyCode).stream().map(domain -> {
			PensionRateDto dto = PensionRateDto.builder().build();
			domain.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}
}
