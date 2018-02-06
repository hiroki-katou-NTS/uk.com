package nts.uk.ctx.at.request.app.command.setting.company.applicationcommonsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting.AppCommonSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting.AppCommonSetRepository;

@Stateless
public class UpdateAppCommonSetCommandHandler extends CommandHandler<AppCommonSetCommand>{
	@Inject
	private AppCommonSetRepository appRep;

	@Override
	protected void handle(CommandHandlerContext<AppCommonSetCommand> context) {
		AppCommonSetCommand data = context.getCommand();
		Optional<AppCommonSet> appCom = appRep.find();
		AppCommonSet app = AppCommonSet.createFromJavaType(data.getCompanyId(), data.getShowWkpNameBelong());
		if(appCom.isPresent()){
			appRep.update(app);
			return;
		}
		appRep.insert(app);
	}
	
}
