package nts.uk.ctx.at.request.app.command.setting.company.displayname;

import java.util.List;
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
public class UpdateAppDispNameCommandHandler extends CommandHandler<List<AppDispNameCommand>>{
	@Inject
	private AppDispNameRepository dispRep;
	@Override
	protected void handle(CommandHandlerContext<List<AppDispNameCommand>> context) {
		List<AppDispNameCommand> data = context.getCommand();
		for(AppDispNameCommand item : data){
			Optional<AppDispName> appDisp = dispRep.getDisplay(item.getAppType());
			AppDispName app = AppDispName.createFromJavaType(item.getCompanyId(), item.getAppType(), item.getDispName());
			if(appDisp.isPresent()){
				dispRep.update(app);
			}else{
				dispRep.insert(app);
			}
		}
	}

}
