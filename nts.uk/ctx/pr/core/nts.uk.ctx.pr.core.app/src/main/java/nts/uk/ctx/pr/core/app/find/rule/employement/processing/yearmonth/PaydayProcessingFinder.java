package nts.uk.ctx.pr.core.app.find.rule.employement.processing.yearmonth;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth.PaydayProcessingRepository;

@Stateless
public class PaydayProcessingFinder {

	@Inject
	private PaydayProcessingRepository repository;

	public List<PaydayProcessingDto> getAlll(String companyCode) {
		return repository.findAll3(companyCode).stream().map(m -> PaydayProcessingDto.fromDomain(m))
				.collect(Collectors.toList());
	}

}
