package nts.uk.ctx.at.record.dom.divergence.time.history;

import java.util.Date;
import java.util.Optional;

import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

public class CompanyDivergenceReferenceTimeServiceIml implements CompanyDivergenceReferenceTimeService {

	@Inject
	DivergenceReferenceTimeUsageUnitRepository divReferenceTimeUsageUnitRepo;

	@Override
	public DetermineReferenceTime CheckDivergenceTime(String userId, String companyId, GeneralDate processDate,
			int divergenceTimeNo, AttendanceTime DivergenceTimeOccurred) {

		Optional<DivergenceReferenceTimeUsageUnit> optionalDivReferenceTimeUsageUnit = divReferenceTimeUsageUnitRepo
				.findByCompanyId(companyId);
		
		if (!optionalDivReferenceTimeUsageUnit.isPresent()
				|| !optionalDivReferenceTimeUsageUnit.get().isWorkTypeUseSet()) {
			//Incase false or domain is not exist
		}
		return new DetermineReferenceTime();
	}

}
