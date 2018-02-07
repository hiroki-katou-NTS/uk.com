package nts.uk.ctx.at.request.app.command.setting.company.displayname;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.company.displayname.AppDispName;
import nts.uk.ctx.at.request.dom.setting.company.displayname.AppDispNameRepository;
/**
 * update app display name
 * @author yennth
 *
 */
@Stateless
@Transactional
public class UpdateAppDispNameCommandHandler extends CommandHandler<AppDispNameCommand>{
	@Inject
	private AppDispNameRepository dispRep;
	@Override
	protected void handle(CommandHandlerContext<AppDispNameCommand> context) {
		AppDispNameCommand data = context.getCommand();
		Optional<AppDispName> appDisp = dispRep.getDisplay(data.getAppType());
		AppDispName app = AppDispName.createFromJavaType(data.getCompanyId(), data.getAppType(), data.getDispName());
		if(appDisp.isPresent()){
			dispRep.update(app);
			return;
		}
		dispRep.insert(app);
	}

}
