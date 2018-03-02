package nts.uk.ctx.at.request.app.command.setting.company.applicationcommonsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting.AppCommonSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting.AppCommonSetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateAppCommonSetCommandHandler extends CommandHandler<AppCommonSetCommand>{
	@Inject
	private AppCommonSetRepository appRep;

	@Override
	protected void handle(CommandHandlerContext<AppCommonSetCommand> context) {
		String companyId = AppContexts.user().companyId();
		AppCommonSetCommand data = context.getCommand();
		Optional<AppCommonSet> appCom = appRep.find();
		AppCommonSet app = AppCommonSet.createFromJavaType(companyId, data.getShowWkpNameBelong());
		if(appCom.isPresent()){
			appRep.update(app);
			return;
		}
		appRep.insert(app);
	}
	
}
