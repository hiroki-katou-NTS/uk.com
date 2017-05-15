package nts.uk.ctx.at.record.app.find.divergencetime;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder.In;

import nts.uk.ctx.at.record.dom.divergencetime.DivergenceTimeRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class DivergenceItemFinder {
	@Inject
	private DivergenceTimeRepository divTimeRepo;
	String companyId = AppContexts.user().companyId();
	public List<DivergenceItemDto> getAllName(List<Integer> listAttendanceItemId){
		List<DivergenceItemDto> lstItemSelected = this.divTimeRepo.getName(companyId, listAttendanceItemId)
										.stream()
										.map(c->DivergenceItemDto.fromDomain(c))
										.collect(Collectors.toList());
		return lstItemSelected;
	}
}
