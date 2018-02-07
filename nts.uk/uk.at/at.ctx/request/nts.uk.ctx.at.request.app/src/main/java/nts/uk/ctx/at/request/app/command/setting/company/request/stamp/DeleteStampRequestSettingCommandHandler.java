package nts.uk.ctx.at.request.app.command.setting.company.request.stamp;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.company.request.stamp.StampRequestSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * TanLV
 *
 */
@Stateless
public class DeleteStampRequestSettingCommandHandler extends CommandHandler<RemoveStampRequestSettingCommand> {
	@Inject
	private StampRequestSettingRepository repository;
	
	@Override
	protected void handle(CommandHandlerContext<RemoveStampRequestSettingCommand> context) {
		String companyId = AppContexts.user().companyId();
		
		//Delete data
		repository.deleteStamp(companyId);
	}
}
