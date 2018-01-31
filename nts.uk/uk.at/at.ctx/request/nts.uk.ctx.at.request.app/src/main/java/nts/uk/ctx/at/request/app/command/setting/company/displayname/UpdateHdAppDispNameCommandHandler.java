package nts.uk.ctx.at.request.app.command.setting.company.displayname;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.company.displayname.HdAppDispName;
import nts.uk.ctx.at.request.dom.setting.company.displayname.HdAppDispNameRepository;

/** 
 * Update Hd App Disp Name Command Handler
 * @author yennth
 */
@Stateless
public class UpdateHdAppDispNameCommandHandler extends CommandHandler<HdAppDispNameCommand>{
	@Inject
	private HdAppDispNameRepository hdAppRep;

	@Override
	protected void handle(CommandHandlerContext<HdAppDispNameCommand> context) {
		HdAppDispNameCommand data = context.getCommand();
		Optional<HdAppDispName> hdApp = hdAppRep.getHdApp(data.getHdAppType());
		HdAppDispName hdAppDisp = HdAppDispName.createFromJavaType(data.getCompanyId(), 
													data.getHdAppType(), data.getDispName());
		if(hdApp.isPresent()){
			hdAppRep.update(hdAppDisp);
		}
		hdAppRep.insert(hdAppDisp);
	} 
	
}
