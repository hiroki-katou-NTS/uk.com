/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal.employee;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegularWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaRegulaMonthActCalSetSetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.employee.KrcstShaRegMCalSet;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.employee.KrcstShaRegMCalSetPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;

/**
 * The Class JpaShaRegulaMonthActCalSetSetMemento.
 */
public class JpaShaRegulaMonthActCalSetSetMemento implements ShaRegulaMonthActCalSetSetMemento {

	/** The type value. */
	private KrcstShaRegMCalSet typeValue;

	/**
	 * Instantiates a new jpa sha regula month act cal set set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaShaRegulaMonthActCalSetSetMemento(KrcstShaRegMCalSet typeValue) {
		super();
		if(typeValue.getKrcstShaRegMCalSetPK() == null){				
			typeValue.setKrcstShaRegMCalSetPK(new KrcstShaRegMCalSetPK());
		}
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaRegulaMonthActCalSetSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.
	 * common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId cid) {
		this.typeValue.getKrcstShaRegMCalSetPK().setCid(cid.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaRegulaMonthActCalSetSetMemento#setAggrSetting(nts.uk.ctx.at.record.dom
	 * .workrecord.monthcal.RegularWorkTimeAggrSet)
	 */
	@Override
	public void setAggrSetting(RegularWorkTimeAggrSet legalAggrSetOfRegNew) {
		this.typeValue.setIncludeLegalAggr(
				BooleanGetAtr.getAtrByBoolean(legalAggrSetOfRegNew.getAggregateTimeSet().getLegalOverTimeWork()));
		this.typeValue.setIncludeHolidayAggr(
				BooleanGetAtr.getAtrByBoolean(legalAggrSetOfRegNew.getAggregateTimeSet().getLegalHoliday()));
		this.typeValue.setIncludeExtraAggr(
				BooleanGetAtr.getAtrByBoolean(legalAggrSetOfRegNew.getAggregateTimeSet().getSurchargeWeekMonth()));
		
		this.typeValue.setIncludeLegalOt(
				BooleanGetAtr.getAtrByBoolean(legalAggrSetOfRegNew.getExcessOutsideTimeSet().getLegalOverTimeWork()));
		this.typeValue.setIncludeHolidayOt(
				BooleanGetAtr.getAtrByBoolean(legalAggrSetOfRegNew.getExcessOutsideTimeSet().getLegalHoliday()));
		this.typeValue.setIncludeExtraOt(
				BooleanGetAtr.getAtrByBoolean(legalAggrSetOfRegNew.getExcessOutsideTimeSet().getSurchargeWeekMonth()));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaRegulaMonthActCalSetSetMemento#setEmployeeId(nts.uk.ctx.at.shared.dom.
	 * common.EmployeeId)
	 */
	@Override
	public void setEmployeeId(EmployeeId sid) {
		this.typeValue.getKrcstShaRegMCalSetPK().setSid(sid.v());
	}

}
