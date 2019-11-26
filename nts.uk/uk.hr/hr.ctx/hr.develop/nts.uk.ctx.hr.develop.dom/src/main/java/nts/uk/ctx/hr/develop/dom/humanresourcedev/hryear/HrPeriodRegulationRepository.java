package nts.uk.ctx.hr.develop.dom.humanresourcedev.hryear;


import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface HrPeriodRegulationRepository {
	
	Optional<HrPeriodRegulation> getByKey(String historyId);
	
	List<HrPeriodRegulation> getByCIDandBaseDate(String companyId , GeneralDate baseDate);

}
