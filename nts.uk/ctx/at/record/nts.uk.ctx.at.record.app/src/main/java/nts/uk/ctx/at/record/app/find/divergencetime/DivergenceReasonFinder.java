package nts.uk.ctx.at.record.app.find.divergencetime;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.divergencetime.DivergenceTimeRepository;

@Stateless
public class DivergenceReasonFinder {

	@Inject
	private DivergenceTimeRepository divTimeRepo;
	
	public List<DivergenceReasonDto> getAllDivReasonByCode(String companyId, String divTimeId){
		List<DivergenceReasonDto> lst = this.divTimeRepo.getDivReasonByCode(companyId, divTimeId).stream()
				.map(c->DivergenceReasonDto.fromDomain(c))
				.collect(Collectors.toList());
		return lst;
	}
}
