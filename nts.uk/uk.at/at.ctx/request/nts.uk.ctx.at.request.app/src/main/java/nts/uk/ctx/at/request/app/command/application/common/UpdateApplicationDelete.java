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
public class UpdateApplicationDelete extends CommandHandlerWithResult<UpdateApplicationCommonCmd,String> {

	
	@Inject
	private AfterProcessDelete afterProcessDelete;

	@Override
	protected String handle(CommandHandlerContext<UpdateApplicationCommonCmd> context) {
		String companyID = AppContexts.user().companyId();
		//5.2(hieult)
		return afterProcessDelete.screenAfterDelete(companyID, context.getCommand().getAppId(), context.getCommand().getVersion());
	}
}
