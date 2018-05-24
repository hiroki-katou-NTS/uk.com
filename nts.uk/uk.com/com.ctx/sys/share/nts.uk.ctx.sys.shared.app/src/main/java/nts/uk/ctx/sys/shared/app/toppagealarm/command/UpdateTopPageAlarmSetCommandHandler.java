package nts.uk.ctx.sys.shared.app.toppagealarm.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.shared.dom.toppagealarmset.TopPageAlarmSet;
import nts.uk.ctx.sys.shared.dom.toppagealarmset.TopPageAlarmSetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateTopPageAlarmSetCommandHandler extends CommandHandler<UpdateTopPageAlarmSetCommand>{
	@Inject
	private TopPageAlarmSetRepository toppageAlarmSetRep;

	@Override
	protected void handle(CommandHandlerContext<UpdateTopPageAlarmSetCommand> context) {
		String companyId = AppContexts.user().companyId();
		UpdateTopPageAlarmSetCommand command = context.getCommand();
		TopPageAlarmSet toppageSet = TopPageAlarmSet.createFromJavaType(companyId, command.getAlarmCategory(), command.getUseAtr());
		toppageAlarmSetRep.update(toppageSet);
	}
	
	
}
