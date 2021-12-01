/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.ot.frame;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class OvertimeWorkFrameSaveCommandHandler.
 */
@Stateless

public class OvertimeWorkFrameSaveCommandHandler extends CommandHandler<OvertimeWorkFrameSaveCommand> {
	
	/** The repository. */
	@Inject
	private OvertimeWorkFrameRepository repository;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<OvertimeWorkFrameSaveCommand> context) {
		// Get Company Id
		String companyId = AppContexts.user().companyId();
		
		// Get Command
		OvertimeWorkFrameSaveCommand command = context.getCommand();
		
		for (OvertimeWorkFrameCommandDto item : command.getListData()) {
			Optional<OvertimeWorkFrame> optPlanYearHdFr = 
							this.repository.findOvertimeWorkFrame( new CompanyId(companyId), item.getOvertimeWorkFrNo());
			OvertimeWorkFrame overtimeWorkFrame = new OvertimeWorkFrame(item);;
			
			if(optPlanYearHdFr.isPresent()){

				// Only update value UseClassification
				overtimeWorkFrame = optPlanYearHdFr.get();
				overtimeWorkFrame.setUseClassification(NotUseAtr.valueOf(item.getUseAtr()));
				overtimeWorkFrame.setOvertimeWorkFrName(item.getOvertimeWorkFrameName());
				overtimeWorkFrame.setTransferFrName(item.getTransferFrameName());
				
				this.repository.update(overtimeWorkFrame);
			}
		}
	}

}
