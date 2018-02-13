package nts.uk.ctx.at.request.app.command.setting.company.displayname;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.company.displayname.HdAppDispName;
import nts.uk.ctx.at.request.dom.setting.company.displayname.HdAppDispNameRepository;

/** 
 * Update Hd App Disp Name Command Handler
 * @author yennth
 */
@Stateless
@Transactional
public class UpdateHdAppDispNameCommandHandler extends CommandHandler<List<HdAppDispNameCommand>>{
	@Inject
	private HdAppDispNameRepository hdAppRep;

	@Override
	protected void handle(CommandHandlerContext<List<HdAppDispNameCommand>> context) {
		List<HdAppDispNameCommand> data = context.getCommand();
		for(HdAppDispNameCommand item : data){
			Optional<HdAppDispName> hdApp = hdAppRep.getHdApp(item.getHdAppType());
			HdAppDispName hdAppDisp = HdAppDispName.createFromJavaType(item.getCompanyId(), 
					item.getHdAppType(), item.getDispName());
			if(hdApp.isPresent()){
				hdAppRep.update(hdAppDisp);
			}else{
				hdAppRep.insert(hdAppDisp);
			}
		}
	}
}
