package nts.uk.ctx.pr.core.app.insurance.social.healthrate.find;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceAvgearnRepository;

@Stateless
public class HealthInsuranceAvgearnFinder {

	@Inject
	private HealthInsuranceAvgearnRepository repository;

	public List<HealthInsuranceAvgearnDto> find(String id) {
		return repository.findById(id).stream().map(domain -> HealthInsuranceAvgearnDto.fromDomain(domain))
				.collect(Collectors.toList());
	}
}
