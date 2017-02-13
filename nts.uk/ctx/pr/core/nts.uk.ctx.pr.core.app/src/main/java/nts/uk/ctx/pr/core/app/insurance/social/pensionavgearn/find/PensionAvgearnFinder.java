package nts.uk.ctx.pr.core.app.insurance.social.pensionavgearn.find;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnRepository;

@Stateless
public class PensionAvgearnFinder {

	@Inject
	private PensionAvgearnRepository repository;

	public List<PensionAvgearnDto> find(String id) {
		return repository.find(id).stream().map(domain -> {
			PensionAvgearnDto dto = PensionAvgearnDto.builder().build();
			domain.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}
}
