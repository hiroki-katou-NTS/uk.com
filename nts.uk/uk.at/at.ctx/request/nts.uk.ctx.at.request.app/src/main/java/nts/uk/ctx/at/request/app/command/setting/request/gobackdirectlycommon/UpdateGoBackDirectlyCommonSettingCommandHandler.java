package nts.uk.ctx.at.request.app.command.setting.request.gobackdirectlycommon;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
/**
 * update go back directly common setting
 * @author yennth
 *
 */
import nts.uk.shr.com.context.AppContexts;
@Stateless
@Transactional
public class UpdateGoBackDirectlyCommonSettingCommandHandler extends CommandHandler<GoBackDirectlyCommonSettingCommand>{
//	@Inject
//	private GoBackDirectlyCommonSettingRepository goBackCommonRep;

	@Override
	protected void handle(CommandHandlerContext<GoBackDirectlyCommonSettingCommand> context) {
		GoBackDirectlyCommonSettingCommand data = context.getCommand();
		String companyId = AppContexts.user().companyId();
//		Optional<GoBackDirectlyCommonSetting> goBack = goBackCommonRep.findByCompanyID(companyId);
//		GoBackDirectlyCommonSetting goBackCommon = data.toDomain(companyId);
//		if(goBack.isPresent()){
//			goBackCommonRep.update(goBackCommon);
//			return;
//		}
//		goBackCommonRep.insert(goBackCommon);
	}
	
}
