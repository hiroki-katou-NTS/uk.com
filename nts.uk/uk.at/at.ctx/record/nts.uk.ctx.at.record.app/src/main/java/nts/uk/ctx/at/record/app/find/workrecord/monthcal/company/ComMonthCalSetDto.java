/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthcal.company;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.app.find.workrecord.monthcal.common.DeforWorkTimeAggrSetDto;
import nts.uk.ctx.at.record.app.find.workrecord.monthcal.common.FlexMonthWorkTimeAggrSetDto;
import nts.uk.ctx.at.record.app.find.workrecord.monthcal.common.RegularWorkTimeAggrSetDto;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegularWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComDeforLaborMonthActCalSetSetMemento;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComFlexMonthActCalSetSetMemento;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.company.ComRegulaMonthActCalSetSetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class ComMonthCalSetDto.
 */
@Getter
@Setter
@Builder
public class ComMonthCalSetDto implements ComRegulaMonthActCalSetSetMemento, ComFlexMonthActCalSetSetMemento,
		ComDeforLaborMonthActCalSetSetMemento {

	/** The flex aggr setting. */
	private FlexMonthWorkTimeAggrSetDto flexAggrSetting;

	/** The reg aggr setting. */
	private RegularWorkTimeAggrSetDto regAggrSetting;

	/** The defor aggr setting. */
	private DeforWorkTimeAggrSetDto deforAggrSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.company.
	 * ComRegulaMonthActCalSetSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.
	 * common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// Do nothing.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.company.
	 * ComRegulaMonthActCalSetSetMemento#setRegulaAggrSetting(nts.uk.ctx.at.
	 * record.dom.workrecord.monthcal.RegularWorkTimeAggrSet)
	 */
	@Override
	public void setRegulaAggrSetting(RegularWorkTimeAggrSet legalAggrSetOfRegNew) {
		regAggrSetting = RegularWorkTimeAggrSetDto.builder().build();
		regAggrSetting.fromDomain(legalAggrSetOfRegNew);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.company.
	 * ComDeforLaborMonthActCalSetSetMemento#setDeforAggrSetting(nts.uk.ctx.at.
	 * record.dom.workrecord.monthcal.DeforWorkTimeAggrSet)
	 */
	@Override
	public void setDeforAggrSetting(DeforWorkTimeAggrSet legalAggrSetOfIrgNew) {
		deforAggrSetting = DeforWorkTimeAggrSetDto.builder().build();
		deforAggrSetting.fromDomain(legalAggrSetOfIrgNew);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.company.
	 * ComFlexMonthActCalSetSetMemento#setFlexAggrSetting(nts.uk.ctx.at.record.
	 * dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet)
	 */
	@Override
	public void setFlexAggrSetting(FlexMonthWorkTimeAggrSet aggrSetting) {
		flexAggrSetting = FlexMonthWorkTimeAggrSetDto.builder().build();
		flexAggrSetting.fromDomain(aggrSetting);
	}

}
