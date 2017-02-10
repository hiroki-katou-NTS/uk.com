package nts.uk.ctx.pr.core.app.insurance.social.pensionrate.find;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionAvgearnRepository;

@Stateless
public class PensionAvgearnFinder {
	@Inject
	private PensionAvgearnRepository repository;

	public List<PensionAvgearnDto> find(String id) {
		return repository.find(id).stream().map(domain -> PensionAvgearnDto.fromDomain(domain))
				.collect(Collectors.toList());
	}
}
