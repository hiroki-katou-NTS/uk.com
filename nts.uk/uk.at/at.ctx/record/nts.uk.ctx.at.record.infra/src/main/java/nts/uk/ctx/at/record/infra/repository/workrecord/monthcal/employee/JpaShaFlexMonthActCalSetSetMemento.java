/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.employee;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaFlexMonthActCalSetSetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.employee.KrcstShaFlexMCalSet;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.employee.KrcstShaFlexMCalSetPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

/**
 * The Class JpaShaFlexMonthActCalSetSetMemento.
 */
public class JpaShaFlexMonthActCalSetSetMemento implements ShaFlexMonthActCalSetSetMemento {

	/** The type value. */
	private KrcstShaFlexMCalSet typeValue;

	/**
	 * Instantiates a new jpa sha flex month act cal set set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaShaFlexMonthActCalSetSetMemento(KrcstShaFlexMCalSet typeValue) {
		super();
		if(typeValue.getKrcstShaFlexMCalSetPK() == null){				
			typeValue.setKrcstShaFlexMCalSetPK(new KrcstShaFlexMCalSetPK());
		}
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaFlexMonthActCalSetSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.
	 * common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId cid) {
		this.typeValue.getKrcstShaFlexMCalSetPK().setCid(cid.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaFlexMonthActCalSetSetMemento#setAggrSetting(nts.uk.ctx.at.record.dom.
	 * workrecord.monthcal.FlexMonthWorkTimeAggrSet)
	 */
	@Override
	public void setAggrSetting(FlexMonthWorkTimeAggrSet aggrSettingMonthlyOfFlxNew) {
		this.typeValue.setAggrMethod(aggrSettingMonthlyOfFlxNew.getAggrMethod().value);
		this.typeValue.setInsufficSet(aggrSettingMonthlyOfFlxNew.getInsufficSet().getCarryforwardSet().value);
		this.typeValue.setLegalAggrSet(aggrSettingMonthlyOfFlxNew.getLegalAggrSet().getAggregateSet().value);
		this.typeValue.setIncludeOt(aggrSettingMonthlyOfFlxNew.getIncludeOverTime() ? 1 : 0);
		this.typeValue.setIncludeHdwk(aggrSettingMonthlyOfFlxNew.getIncludeIllegalHdwk() ? 1 : 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaFlexMonthActCalSetSetMemento#setEmployeeId(nts.uk.ctx.at.shared.dom.
	 * common.EmployeeId)
	 */
	@Override
	public void setEmployeeId(EmployeeId sid) {
		this.typeValue.getKrcstShaFlexMCalSetPK().setSid(sid.v());
	}

}
