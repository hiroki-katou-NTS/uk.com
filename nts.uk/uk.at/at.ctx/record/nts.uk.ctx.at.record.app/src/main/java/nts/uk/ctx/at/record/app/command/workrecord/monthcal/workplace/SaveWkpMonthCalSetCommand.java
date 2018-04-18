/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthcal.workplace;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.common.DeforWorkTimeAggrSetDto;
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.common.FlexMonthWorkTimeAggrSetDto;
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.common.RegularWorkTimeAggrSetDto;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegularWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpDeforLaborMonthActCalSetGetMemento;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpFlexMonthActCalSetGetMemento;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpRegulaMonthActCalSetGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveWkpMonthCalSetCommand.
 */
@Getter
@Setter
public class SaveWkpMonthCalSetCommand implements WkpDeforLaborMonthActCalSetGetMemento,
		WkpFlexMonthActCalSetGetMemento, WkpRegulaMonthActCalSetGetMemento {

	/** The wkp id. */
	private String workplaceId;

	/** The flex aggr setting. */
	private FlexMonthWorkTimeAggrSetDto flexAggrSetting;

	/** The reg aggr setting. */
	private RegularWorkTimeAggrSetDto regAggrSetting;

	/** The defor aggr setting. */
	private DeforWorkTimeAggrSetDto deforAggrSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpDeforLaborMonthActCalSetGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(AppContexts.user().companyId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpDeforLaborMonthActCalSetGetMemento#getWorkplaceId()
	 */
	@Override
	public WorkplaceId getWorkplaceId() {
		return new WorkplaceId(this.workplaceId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpRegulaMonthActCalSetGetMemento#getRegularAggrSetting()
	 */
	@Override
	public RegularWorkTimeAggrSet getRegularAggrSetting() {
		return this.regAggrSetting.toDomain();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpFlexMonthActCalSetGetMemento#getFlexAggrSetting()
	 */
	@Override
	public FlexMonthWorkTimeAggrSet getFlexAggrSetting() {
		return this.flexAggrSetting.toDomain();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpDeforLaborMonthActCalSetGetMemento#getDeforAggrSetting()
	 */
	@Override
	public DeforWorkTimeAggrSet getDeforAggrSetting() {
		return this.deforAggrSetting.toDomain();
	}
	
	/**
	 * Gets the wkp id.
	 *
	 * @return the wkp id
	 */
	public String getWkpId() {
		return this.workplaceId;
	}
}
