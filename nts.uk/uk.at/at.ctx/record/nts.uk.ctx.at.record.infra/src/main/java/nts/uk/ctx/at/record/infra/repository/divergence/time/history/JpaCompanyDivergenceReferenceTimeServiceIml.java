package nts.uk.ctx.at.record.infra.repository.divergence.time.history;

import java.util.Date;
import java.util.Optional;

import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeService;
import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceReferenceTimeUsageUnit;

public class JpaCompanyDivergenceReferenceTimeServiceIml implements CompanyDivergenceReferenceTimeService {

	@Inject
	JpaDivergenceReferenceTimeUsageUnitRepository divReferenceTimeUsageUnitRepo;

	@Override
	public void CheckDivergenceTime(Integer userId, String companyId, Date processDate, Integer divergenceTimeNo,
			String DivergenceTimeOccurred) {

		Optional<DivergenceReferenceTimeUsageUnit> optionalDivReferenceTimeUsageUnit = divReferenceTimeUsageUnitRepo
				.findByCompanyId(companyId);

		if(optionalDivReferenceTimeUsageUnit.isPresent())
		{
			DivergenceReferenceTimeUsageUnit divReferenceTimeUsageUnit = optionalDivReferenceTimeUsageUnit.get();
			
			if(divReferenceTimeUsageUnit.isWorkTypeUseSet()){
				
			}
			else{
				
			}
		}
	}

}
