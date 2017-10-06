package nts.uk.ctx.at.request.app.command.application.common;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.AfterProcessDelete;
import nts.uk.shr.com.context.AppContexts;
@Stateless
@Transactional
public class UpdateApplicationDelete extends CommandHandlerWithResult<UpdateApplicationCommonCmd,ListMailApproval> {

	
	@Inject
	private AfterProcessDelete afterProcessDelete;

	@Override
	protected ListMailApproval handle(CommandHandlerContext<UpdateApplicationCommonCmd> context) {
		String companyID = AppContexts.user().companyId();
		//5.2(hieult)
		
		return new ListMailApproval(afterProcessDelete.screenAfterDelete(companyID, context.getCommand().getAppId()));
	}
}
