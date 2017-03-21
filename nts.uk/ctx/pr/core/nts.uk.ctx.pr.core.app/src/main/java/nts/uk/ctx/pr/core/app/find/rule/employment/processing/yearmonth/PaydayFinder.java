package nts.uk.ctx.pr.core.app.find.rule.employment.processing.yearmonth;


import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.rule.employment.processing.yearmonth.PaydayRepository;

@Stateless
public class PaydayFinder {

	@Inject
	private PaydayRepository repository;	
	
	public List<PaydayDto> select6(String companyCode, int processingNo, int  processingYm){
		return repository.find6(companyCode, processingNo, processingYm).stream()
				.map(m -> PaydayDto.fromDomain(m)).collect(Collectors.toList());
	}
}
