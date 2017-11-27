/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.plannedyearholiday.frame;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrame;
import nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.PlanYearHolidayFrameRepository;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class PlanYearHdFrameSaveCommandHandler.
 */
@Stateless
public class PlanYearHdFrameSaveCommandHandler extends CommandHandler<PlanYearHdFrameSaveCommand> {
	
	/** The repository. */
	@Inject
	private PlanYearHolidayFrameRepository repository;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<PlanYearHdFrameSaveCommand> context) {
		// Get Company Id
		String companyId = AppContexts.user().companyId();
		
		// Get Command
		PlanYearHdFrameSaveCommand command = context.getCommand();
		
		for (PlanYearHdFrameCommandDto item : command.getListData()) {
			if (item.getUseAtr() == 1){
				Optional<PlanYearHolidayFrame> optPlanYearHdFr = this.repository.findPlanYearHolidayFrame(new CompanyId(companyId), item.getPlanYearHdFrameNo());
				PlanYearHolidayFrame planYearHolidayFrame = new PlanYearHolidayFrame(item);
				if(optPlanYearHdFr.isPresent()){
					this.repository.update(planYearHolidayFrame);
				}else {
					this.repository.add(planYearHolidayFrame);
				}
			}
		}
	}

}
