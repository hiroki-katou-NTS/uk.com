package nts.uk.ctx.at.request.app.command.application.common;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.AfterDenialProcess;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforeProcessDenial;

import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateApplicationDenyHandler extends CommandHandler<UpdateApplicationCommonCmd> {

	@Inject
	private BeforeProcessDenial beforeProcessDenialRepo;
	@Inject 
	private AfterDenialProcess afterDenialProcessRepo;
	

	@Override
	protected void handle(CommandHandlerContext<UpdateApplicationCommonCmd> context) {
		String companyID = AppContexts.user().companyId();
		//9-1 .詳細画面否認前の処理
		beforeProcessDenialRepo.detailedScreenProcessBeforeDenial();
		//9.2 
		afterDenialProcessRepo.detailedScreenAfterDenialProcess(companyID, context.getCommand().getAppId());
	}

}
