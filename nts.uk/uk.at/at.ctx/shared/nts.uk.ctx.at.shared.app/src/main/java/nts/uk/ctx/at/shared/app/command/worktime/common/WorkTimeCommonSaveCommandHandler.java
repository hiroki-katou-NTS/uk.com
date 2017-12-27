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
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;

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

	/** The work time set policy. */
	@Inject
	private WorkTimeSettingPolicy workTimeSetPolicy;

	/**
	 * Handle.
	 *
	 * @param command the command
	 */
	public void handle(WorkTimeCommonSaveCommand command){

		// get work time setting by client send
		WorkTimeSetting workTimeSetting = command.toDomainWorkTimeSetting();

		// get pred setting by client send
		PredetemineTimeSetting predseting = command.toDomainPredetemineTimeSetting();
		
		// check is add mode
		if(command.isAddMode()){
			
			// check register 
			this.workTimeSetPolicy.canRegister(workTimeSetting);
			
			// call repository add work time setting
			this.workTimeSettingRepository.add(workTimeSetting);
			
			// call repository add predetemine time setting
			this.predetemineTimeSettingRepository.add(predseting);
		}
		else {
			// call repository update work time setting
			this.workTimeSettingRepository.update(workTimeSetting);

			// call repository update predetemine time setting
			this.predetemineTimeSettingRepository.update(predseting);
		}
	}
}
