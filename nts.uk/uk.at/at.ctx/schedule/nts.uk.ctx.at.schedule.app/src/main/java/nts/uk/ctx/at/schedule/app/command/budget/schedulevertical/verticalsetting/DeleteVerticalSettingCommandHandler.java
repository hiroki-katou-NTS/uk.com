package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.verticalsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * TanLV
 *
 */
@Stateless
public class DeleteVerticalSettingCommandHandler  extends CommandHandler<VerticalSettingCommand>{
	@Inject
	private VerticalSettingRepository repository;
	
	@Override
	protected void handle(CommandHandlerContext<VerticalSettingCommand> context) {
		VerticalSettingCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		//Delete Vertical Cal Set
		repository.deleteVerticalCalSet(companyId, command.getVerticalCalCd());
	}
}
