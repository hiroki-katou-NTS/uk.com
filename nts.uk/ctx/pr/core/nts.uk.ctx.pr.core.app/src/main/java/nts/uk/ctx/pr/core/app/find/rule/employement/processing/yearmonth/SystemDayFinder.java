package nts.uk.ctx.pr.core.app.find.rule.employement.processing.yearmonth;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth.SystemDayRepository;

@Stateless
public class SystemDayFinder {

	@Inject
	private SystemDayRepository repository;
	
	public SystemDayDto select1(String companyCode, int processingNo){		
		return repository.findAll(companyCode, processingNo).stream()
				.map(m -> SystemDayDto.fromDomain(m)).collect(Collectors.toList()).get(0);
	}
}
