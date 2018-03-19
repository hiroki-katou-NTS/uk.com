/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.company;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSettingSetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;

/**
 * The Class JpaCompanyWtSettingSetMemento.
 */
@Getter
public class JpaComDeforLaborSettingSetMemento implements ComDeforLaborSettingSetMemento {

	@Override
	public void setYear(Year year) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStatutorySetting(List<MonthlyUnit> statutorySetting) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCompanyId(CompanyId companyId) {
		// TODO Auto-generated method stub
		
	}

}
