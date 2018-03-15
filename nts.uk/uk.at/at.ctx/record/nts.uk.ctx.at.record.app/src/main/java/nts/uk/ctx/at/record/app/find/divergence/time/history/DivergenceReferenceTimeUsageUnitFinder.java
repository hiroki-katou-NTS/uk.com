package nts.uk.ctx.at.record.app.find.divergence.time.history;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceReferenceTimeUsageUnit;
import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceReferenceTimeUsageUnitRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DivergenceReferenceTimeUsageUnitFinder {

	@Inject
	private DivergenceReferenceTimeUsageUnitRepository divergenReferenceTimeUsageUnitRepo;
	
	public DivergenceReferenceTimeUsageUnitDto findByCompanyId(){
		String companyId = AppContexts.user().companyId();
		DivergenceReferenceTimeUsageUnit domain = divergenReferenceTimeUsageUnitRepo.findByCompanyId(companyId);
		DivergenceReferenceTimeUsageUnitDto dto = new DivergenceReferenceTimeUsageUnitDto(domain.getCId(), domain.getWorkTypeUseSet());
		return dto;
	}
}
