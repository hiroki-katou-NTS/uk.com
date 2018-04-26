/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayModeRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WorkTimeCommonDeleteCommandHandler.
 */
@Stateless
@Transactional
public class WorkTimeCommonDeleteCommandHandler extends CommandHandler<WorkTimeCommonDeleteCommand> {

	/** The work time setting repository. */
	@Inject 
	private WorkTimeSettingRepository workTimeSettingRepository; 
	
	/** The work time display mode repository. */
	@Inject
	private WorkTimeDisplayModeRepository workTimeDisplayModeRepository;
	
	/** The predetemine time setting repository. */
	@Inject 
	private PredetemineTimeSettingRepository predetemineTimeSettingRepository; 

	/** The fixed work setting repository. */
	@Inject 
	private FixedWorkSettingRepository fixedWorkSettingRepository;

	/** The flow work setting repository. */
	@Inject 
	private FlowWorkSettingRepository flowWorkSettingRepository;

	/** The diff time work setting repository. */
	@Inject 
	private DiffTimeWorkSettingRepository diffTimeWorkSettingRepository;
	
	/** The flex work setting repository. */
	@Inject 
	private FlexWorkSettingRepository flexWorkSettingRepository;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<WorkTimeCommonDeleteCommand> context) {
		
		String companyId = AppContexts.user().companyId();
		
		WorkTimeCommonDeleteCommand command = context.getCommand();

		String workTimeCode = command.getWorkTimeCode();

		//remove pred
		this.predetemineTimeSettingRepository.remove(companyId,workTimeCode);
		
		//remove worktimeset
		this.workTimeSettingRepository.remove(companyId,workTimeCode);
		
		//remove worktimeset
		this.workTimeDisplayModeRepository.remove(companyId, workTimeCode);
		
		//remove fixed
		if(this.fixedWorkSettingRepository.findByKey(companyId, workTimeCode).isPresent()){
			this.fixedWorkSettingRepository.remove(companyId, workTimeCode);
		}
		
		//remove flow
		if(this.flowWorkSettingRepository.find(companyId, workTimeCode).isPresent()){
			this.flowWorkSettingRepository.remove(companyId, workTimeCode);
		}
		
		//remove difftime
		if(this.diffTimeWorkSettingRepository.find(companyId, workTimeCode).isPresent()){
			this.diffTimeWorkSettingRepository.remove(companyId, workTimeCode);
		}
		
		//remove flex
		if(this.flexWorkSettingRepository.find(companyId, workTimeCode).isPresent()){
			this.flexWorkSettingRepository.remove(companyId, workTimeCode);
		}
	}

}
