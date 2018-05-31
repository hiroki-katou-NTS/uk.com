package nts.uk.ctx.at.function.app.command.dailyfix;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.dailyfix.AppliCalDaiCorrec;
import nts.uk.ctx.at.function.dom.dailyfix.IAppliCalDaiCorrecRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateAppliCalDaiCorrecCommandHandler extends CommandHandler<AppliCalDaiCorrecCommand>{
	@Inject
	private IAppliCalDaiCorrecRepository appCalRep;
	@Override
	protected void handle(CommandHandlerContext<AppliCalDaiCorrecCommand> context) {
		AppliCalDaiCorrecCommand cm = context.getCommand();
		String companyId = AppContexts.user().companyId();
		appCalRep.delete(companyId);
		for(Integer item : cm.getAppTypes()){
			appCalRep.insert(AppliCalDaiCorrec.createFromJavaType(companyId, item));
		}
	}

}
