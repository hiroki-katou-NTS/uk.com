package nts.uk.ctx.at.request.app.command.application.common;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterRelease;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforeProcessReleasing;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateApplicationReleaseHandler extends CommandHandler<ApplicationDto> {

	@Inject
	private ApplicationRepository appRepo;
	
	@Inject
	private BeforeProcessReleasing beforeProcessReleasingRepo;
	
	@Inject
	private DetailAfterRelease detailAfterRelease;
	


	@Override
	protected void handle(CommandHandlerContext<ApplicationDto> context) {
		//String loginID = AppContexts.user().userId();
		String employeeId = AppContexts.user().employeeId();
		Application application =  ApplicationDto.toEntity(context.getCommand());
		
		//10.1
		beforeProcessReleasingRepo.detailScreenProcessBeforeReleasing();
		//10.2
		detailAfterRelease.detailAfterRelease(application, employeeId);
	}

}
