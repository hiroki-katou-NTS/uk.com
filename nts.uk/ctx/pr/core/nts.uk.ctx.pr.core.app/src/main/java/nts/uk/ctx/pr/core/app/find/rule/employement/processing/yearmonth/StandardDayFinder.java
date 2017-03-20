package nts.uk.ctx.pr.core.app.find.rule.employement.processing.yearmonth;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth.StandardDayRepository;

@Stateless
public class StandardDayFinder {

	@Inject
	private StandardDayRepository repository;

	public StandardDayDto select1(String companyCode,int processingNo) {
		return repository.select1(companyCode, processingNo).stream().map(m -> StandardDayDto.fromDomain(m))
				.collect(Collectors.toList()).get(0);
	}
}
