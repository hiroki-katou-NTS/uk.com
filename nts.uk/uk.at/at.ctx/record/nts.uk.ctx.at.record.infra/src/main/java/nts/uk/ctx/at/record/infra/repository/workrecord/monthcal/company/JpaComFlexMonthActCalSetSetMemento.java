/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.company;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComFlexMonthActCalSetSetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.company.KrcstComFlexMCalSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaWorkFixedSetMemento.
 */
public class JpaComFlexMonthActCalSetSetMemento implements ComFlexMonthActCalSetSetMemento {

	/** The type value. */
	private KrcstComFlexMCalSet typeValue;

	@Override
	public void setCompanyId(CompanyId companyId) {
		this.typeValue.setCid(companyId.v());
	}

	@Override
	public void setFlexAggrSetting(FlexMonthWorkTimeAggrSet aggrSettingMonthlyOfFlxNew) {
		this.typeValue.setAggrMethod(aggrSettingMonthlyOfFlxNew.getAggrMethod().value);
		this.typeValue.setInsufficSet(aggrSettingMonthlyOfFlxNew.getInsufficSet().getCarryforwardSet().value);
		this.typeValue.setLegalAggrSet(aggrSettingMonthlyOfFlxNew.getLegalAggrSet().getAggregateSet().value);
		this.typeValue.setIncludeOt(aggrSettingMonthlyOfFlxNew.getIncludeOverTime().value);
	}
}
