package nts.uk.ctx.at.record.app.find.divergencetime;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.divergencetime.DivergenceTimeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DivergenceItemFinder {
	@Inject
	private DivergenceTimeRepository divTimeRepo;
	//user contexts
	String companyId = AppContexts.user().companyId();
	
	public List<DivergenceItemDto> getAllDivReasonByCode(){
		List<DivergenceItemDto> lst = this.divTimeRepo.getallDivItem(companyId)
				.stream()
				.map(c->DivergenceItemDto.fromDomain(c))
				.collect(Collectors.toList());
		return lst;
	}
}