/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.workdayoff.frame;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WorkdayoffFrameSaveCommandHandler.
 */
@Stateless

public class WorkdayoffFrameSaveCommandHandler extends CommandHandler<WorkdayoffFrameSaveCommand> {
	
	/** The repository. */
	@Inject
	private WorkdayoffFrameRepository repository;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<WorkdayoffFrameSaveCommand> context) {
		// Get Company Id
		String companyId = AppContexts.user().companyId();
		
		// Get Command
		WorkdayoffFrameSaveCommand command = context.getCommand();
		
		for (WorkdayoffFrameCommandDto item : command.getListData()) {
			Optional<WorkdayoffFrame> optWorkdayoffFr = this.repository.findWorkdayoffFrame(new CompanyId(companyId), 
																								item.getWorkdayoffFrameNo().v().intValue());
//			if (item.getUseAtr() == 0){
//				item.setTransferFrName(optWorkdayoffFr.get().getTransferFrName().v());
//				item.setWorkdayoffFrName(optWorkdayoffFr.get().getWorkdayoffFrName().v());
//			}
			WorkdayoffFrame workdayoffFrame = new WorkdayoffFrame(item);
			if(optWorkdayoffFr.isPresent()) {
				workdayoffFrame = optWorkdayoffFr.get();
				workdayoffFrame.setUseClassification(item.getUseClassification());
				this.repository.update(workdayoffFrame);
			}

		}
	}

}
