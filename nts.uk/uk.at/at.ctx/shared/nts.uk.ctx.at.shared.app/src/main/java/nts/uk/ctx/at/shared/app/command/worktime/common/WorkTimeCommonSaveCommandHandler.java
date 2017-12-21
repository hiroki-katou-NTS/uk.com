/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class WorkTimeCommonSaveCommandHandler.
 */
@Stateless
public class WorkTimeCommonSaveCommandHandler{
	
	/** The work time setting repository. */
	@Inject 
	private WorkTimeSettingRepository workTimeSettingRepository; 
	
	/** The predetemine time setting repository. */
	@Inject 
	private PredetemineTimeSettingRepository predetemineTimeSettingRepository; 
	/*

	/**
	 * Handler.
	 */
	public void handler(WorkTimeCommonSaveCommand command){
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();


		// get work time setting by client send
		WorkTimeSetting workTimeSetting = command.toDomainWorkTimeSetting(companyId);

		// get pred setting by client send
		PredetemineTimeSetting predseting = command.toDomainPredetemineTimeSetting(companyId);

		// call repository save work time setting
		this.workTimeSettingRepository.save(workTimeSetting);

		// call repository save pred setting
		this.predetemineTimeSettingRepository.save(predseting);
	}
}
