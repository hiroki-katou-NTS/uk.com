/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthcal.workplace;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.app.find.workrecord.monthcal.common.DeforWorkTimeAggrSetDto;
import nts.uk.ctx.at.record.app.find.workrecord.monthcal.common.FlexMonthWorkTimeAggrSetDto;
import nts.uk.ctx.at.record.app.find.workrecord.monthcal.common.RegularWorkTimeAggrSetDto;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegularWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpDeforLaborMonthActCalSetSetMemento;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpFlexMonthActCalSetSetMemento;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpRegulaMonthActCalSetSetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;

/**
 * The Class WkpMonthCalSetDto.
 */
@Getter
@Setter
@Builder
public class WkpMonthCalSetDto implements WkpRegulaMonthActCalSetSetMemento,
		WkpFlexMonthActCalSetSetMemento, WkpDeforLaborMonthActCalSetSetMemento {

	/** The company id. */
	private String companyId;
	
	/** The workplace id. */
	private String workplaceId;;
	
	/** The flex aggr setting. */
	private FlexMonthWorkTimeAggrSetDto flexAggrSetting;

	/** The reg aggr setting. */
	private RegularWorkTimeAggrSetDto regAggrSetting;

	/** The defor aggr setting. */
	private DeforWorkTimeAggrSetDto deforAggrSetting;

	/* 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.WkpDeforLaborMonthActCalSetSetMemento#setAggrSetting(nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSet)
	 */
	@Override
	public void setAggrSetting(DeforWorkTimeAggrSet legalAggrSetOfIrgNew) {
		deforAggrSetting = DeforWorkTimeAggrSetDto.builder().build();
		deforAggrSetting.fromDomain(legalAggrSetOfIrgNew);
	}

	/* 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.WkpFlexMonthActCalSetSetMemento#setAggrSetting(nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet)
	 */
	@Override
	public void setAggrSetting(FlexMonthWorkTimeAggrSet aggrSettingMonthlyOfFlxNew) {
		flexAggrSetting = FlexMonthWorkTimeAggrSetDto.builder().build();
		flexAggrSetting.fromDomain(aggrSettingMonthlyOfFlxNew);
	}

	/* 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.WkpRegulaMonthActCalSetSetMemento#setAggrSetting(nts.uk.ctx.at.record.dom.workrecord.monthcal.RegularWorkTimeAggrSet)
	 */
	@Override
	public void setAggrSetting(RegularWorkTimeAggrSet legalAggrSetOfRegNew) {
		regAggrSetting = RegularWorkTimeAggrSetDto.builder().build();
		regAggrSetting.fromDomain(legalAggrSetOfRegNew);
	}

	/* 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.WkpRegulaMonthActCalSetSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.companyId = companyId.v();
	}

	/* 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.WkpRegulaMonthActCalSetSetMemento#setWorkplaceId(nts.uk.ctx.at.shared.dom.common.WorkplaceId)
	 */
	@Override
	public void setWorkplaceId(WorkplaceId workplaceId) {
		this.workplaceId = workplaceId.v();
	}



}
