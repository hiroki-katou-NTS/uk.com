package nts.uk.ctx.at.request.app.command.setting.company.request.apptypesetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.transaction.Transactional;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.DisplayReason;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.DisplayReasonRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
@Transactional
public class UpdateDisplayReasonCmdHandler extends CommandHandler<UpdateDisplayReasonCmd>{
	@Inject
	private DisplayReasonRepository displayRep;

	@Override
	protected void handle(CommandHandlerContext<UpdateDisplayReasonCmd> context) {
		UpdateDisplayReasonCmd data = context.getCommand();
		String companyId = AppContexts.user().companyId();
		for(DisplayReasonCmd item : data.getListCmd()){
			Optional<DisplayReason> getDB = displayRep.findDpReasonHd(companyId, item.getAppType());
			if(getDB.isPresent()){
				displayRep.update(DisplayReason.toDomain(companyId, item.getAppType(), item.getDisplayFixedReason(), item.getDisplayAppReason()));
			}else{
				displayRep.insert(DisplayReason.toDomain(companyId, item.getAppType(), item.getDisplayFixedReason(), item.getDisplayAppReason()));
			}
		}
	}
}
