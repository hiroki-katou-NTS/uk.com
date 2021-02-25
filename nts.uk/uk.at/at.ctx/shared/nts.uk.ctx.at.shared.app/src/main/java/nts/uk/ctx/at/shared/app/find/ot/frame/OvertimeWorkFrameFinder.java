/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.ot.frame;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.enums.UseAtr;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class OvertimeWorkFrameFinder.
 */
@Stateless
public class OvertimeWorkFrameFinder {
	
	/** The repository. */
	@Inject 
	private OvertimeWorkFrameRepository repository;
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<OvertimeWorkFrameFindDto> findAll(){
		// get company id
		String companyId = AppContexts.user().companyId();

		// get all
		List<OvertimeWorkFrame> managementCategories = this.repository
			.getAllOvertimeWorkFrame(companyId);
		
		// to domain
		return managementCategories.stream().map(category -> {
			OvertimeWorkFrameFindDto dto = new OvertimeWorkFrameFindDto();
			category.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}

	/**
	 * Find all used.
	 *
	 * @return the list
	 */
	public List<OvertimeWorkFrameFindDto> findAllUsed() {
		List<OvertimeWorkFrame> otWorkFrames = this.repository
				.getOvertimeWorkFrameByFrameByCom(AppContexts.user().companyId(), UseAtr.USE.value);

		// to domain
		return otWorkFrames.stream().map(category -> {
			OvertimeWorkFrameFindDto dto = new OvertimeWorkFrameFindDto();
			category.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}
}
