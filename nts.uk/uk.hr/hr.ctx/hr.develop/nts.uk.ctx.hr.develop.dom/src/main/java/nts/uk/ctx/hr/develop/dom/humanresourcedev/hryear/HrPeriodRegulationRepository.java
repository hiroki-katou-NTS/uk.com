package nts.uk.ctx.hr.develop.dom.humanresourcedev.hryear;


import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface HrPeriodRegulationRepository {
	
	Optional<HrPeriodRegulation> getByKeyAndDate(String companyId,String historyId);
	
}
