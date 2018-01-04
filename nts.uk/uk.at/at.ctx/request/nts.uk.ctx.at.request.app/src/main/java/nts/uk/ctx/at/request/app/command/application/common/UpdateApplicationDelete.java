package nts.uk.ctx.at.request.app.command.application.common;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto_New;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.AfterProcessDelete;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.shr.com.context.AppContexts;
@Stateless
@Transactional
public class UpdateApplicationDelete extends CommandHandlerWithResult<UpdateApplicationCommonCmd,String> {
	
	@Inject
	private AfterProcessDelete afterProcessDelete;
	
	@Inject
	private DetailBeforeUpdate beforeRegisterRepo;

	@Override
	protected String handle(CommandHandlerContext<UpdateApplicationCommonCmd> context) {
		String companyID = AppContexts.user().companyId();
		UpdateApplicationCommonCmd command = context.getCommand();
		beforeRegisterRepo.exclusiveCheck(companyID, command.getAppId(), command.getVersion());
		//5.2(hieult)
		return afterProcessDelete.screenAfterDelete(companyID, context.getCommand().getAppId(), context.getCommand().getVersion());
	}
}
