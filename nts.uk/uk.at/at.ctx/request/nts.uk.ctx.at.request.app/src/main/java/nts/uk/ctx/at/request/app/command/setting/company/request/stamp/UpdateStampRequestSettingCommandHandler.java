package nts.uk.ctx.at.request.app.command.setting.company.request.stamp;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.company.request.stamp.StampRequestSetting_Old;
import nts.uk.ctx.at.request.dom.setting.company.request.stamp.StampRequestSettingRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * update stamp request setting
 * @author yennth
 *
 */
@Stateless
@Transactional
public class UpdateStampRequestSettingCommandHandler extends CommandHandler<StampRequestSettingCommand>{
	@Inject
	private StampRequestSettingRepository stampRep;

	@Override
	protected void handle(CommandHandlerContext<StampRequestSettingCommand> context) {
		StampRequestSettingCommand data = context.getCommand();
		String companyId = AppContexts.user().companyId();
		Optional<StampRequestSetting_Old> stamp = stampRep.findByCompanyID(companyId);
		StampRequestSetting_Old stampRequest = data.toDomain(companyId);
		if(stamp.isPresent()){
			stampRep.updateStamp(stampRequest);
			return;
		}
		stampRep.insertStamp(stampRequest);
	}
	
}
