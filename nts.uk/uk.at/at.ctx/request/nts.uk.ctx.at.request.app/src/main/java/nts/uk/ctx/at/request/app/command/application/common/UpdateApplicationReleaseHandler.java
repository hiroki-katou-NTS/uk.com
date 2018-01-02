package nts.uk.ctx.at.request.app.command.application.common;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto_New;
import nts.uk.ctx.at.request.app.find.application.common.CheckApprover;
import nts.uk.ctx.at.request.app.find.application.common.dto.InputCommonData;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterRelease;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforeProcessReleasing;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateApplicationReleaseHandler extends CommandHandler<InputCommonData> {

	@Inject
	private ApplicationRepository appRepo;
	
	@Inject
	private BeforeProcessReleasing beforeProcessReleasingRepo;
	
	@Inject
	private DetailAfterRelease detailAfterRelease;
	
	@Inject
	private CheckApprover checkApprover;
	
	@Inject
	private DetailBeforeUpdate beforeRegisterRepo;

	@Override
	protected void handle(CommandHandlerContext<InputCommonData> context) {
		String companyID = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		String memo = context.getCommand().getMemo();
		ApplicationDto_New command = context.getCommand().getApplicationDto();
		//TODO: wait
		//checkApprover.checkApprover(command,memo);
		Application_New application =  ApplicationDto_New.toEntity(command);
		//10.1
		beforeProcessReleasingRepo.detailScreenProcessBeforeReleasing(command.getCompanyID(), command.getApplicationID(), command.getVersion());
		//10.2
		
		detailAfterRelease.detailAfterRelease(companyID, command.getApplicationID(), employeeId);
	}

}
