/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.app.command.dailyworkschedule;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class OutputItemDailyWorkScheduleDeleteHandler.
 * @author HoangDD
 */
@Stateless
public class OutputItemDailyWorkScheduleDeleteHandler {
	
	/** The repository. */
	@Inject 
	OutputItemDailyWorkScheduleRepository repository;
	
	/**
	 * Delete.
	 *
	 * @param code the code
	 */
	public void delete(String code) {
		String companyId = AppContexts.user().companyId();
		
		repository.deleteByCidAndCode(companyId, code);
	}
}
