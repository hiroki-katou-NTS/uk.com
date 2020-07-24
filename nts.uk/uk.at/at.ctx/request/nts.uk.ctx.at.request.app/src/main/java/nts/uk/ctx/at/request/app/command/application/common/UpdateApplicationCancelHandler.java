package nts.uk.ctx.at.request.app.command.application.common;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.ProcessCancel;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.shr.com.context.AppContexts;
@Stateless
@Transactional
public class UpdateApplicationCancelHandler extends CommandHandler<UpdateApplicationCommonCmd> {

	
	@Inject
	private ProcessCancel processCancelRepo;
	@Inject
	private DetailBeforeUpdate detailBeforeUpdate;

	@Override
	protected void handle(CommandHandlerContext<UpdateApplicationCommonCmd> context) {
		String companyID = AppContexts.user().companyId();
		UpdateApplicationCommonCmd updateCommand = context.getCommand();
		// refactor 4 error
		/*//1 : 排他チェック,
		detailBeforeUpdate.exclusiveCheck(companyID, updateCommand.getAppId(), updateCommand.getVersion());
		//12 
		processCancelRepo.detailScreenCancelProcess(companyID,context.getCommand().getAppId(), context.getCommand().getVersion());*/
		
	}

}
