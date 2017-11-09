/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.ac.workplace.affiliate;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceDto;
import nts.uk.ctx.bs.employee.pub.workplace.SWkpHistExport;
import nts.uk.ctx.bs.employee.pub.workplace.SyWorkplacePub;

/**
 * The Class AffWorkplaceAdapterImpl.
 */
@Stateless
public class AffWorkplaceAdapterImpl implements AffWorkplaceAdapter {

	/** The wkp pub. */
	@Inject
	private SyWorkplacePub wkpPub;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter#findBySid(java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public Optional<AffWorkplaceDto> findBySid(String employeeId, GeneralDate baseDate) {

		Optional<SWkpHistExport> opSWkpHistExport = this.wkpPub.findBySid(employeeId, baseDate);
		
		AffWorkplaceDto affWorkplaceDto = new AffWorkplaceDto();
		
		if(opSWkpHistExport.isPresent()){
		affWorkplaceDto = AffWorkplaceDto.builder().workplaceId(opSWkpHistExport.get().getWorkplaceId())
				.workplaceCode(opSWkpHistExport.get().getWorkplaceCode())
				.workplaceName(opSWkpHistExport.get().getWorkplaceName()).build();
		}
		
		return Optional.ofNullable(affWorkplaceDto);
	}

}
