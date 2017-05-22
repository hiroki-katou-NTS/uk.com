/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacationsetting.acquisitionrule;

import java.util.List;
import java.util.Optional;

public interface VaAcRuleRepository {
	
	void create(VacationAcquisitionRule acquisitionRule);
	
	void update(VacationAcquisitionRule acquisitionRule);
	
	void remove(String companyId);
	
	Optional<VacationAcquisitionRule> findById(String companyId);
	
	List<VacationAcquisitionRule> findAll();
	
	
}
