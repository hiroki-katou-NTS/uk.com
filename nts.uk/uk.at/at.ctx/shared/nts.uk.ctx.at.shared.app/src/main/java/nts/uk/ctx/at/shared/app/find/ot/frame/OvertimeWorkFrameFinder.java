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
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

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
		
		// get login info
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id
		String companyId = loginUserContext.companyId();

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
}
