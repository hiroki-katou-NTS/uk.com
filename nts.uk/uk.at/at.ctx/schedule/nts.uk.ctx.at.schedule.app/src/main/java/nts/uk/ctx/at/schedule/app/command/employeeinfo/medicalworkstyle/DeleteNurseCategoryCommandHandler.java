package nts.uk.ctx.at.schedule.app.command.employeeinfo.medicalworkstyle;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.NurseClassificationRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DeleteNurseCategoryCommandHandler extends CommandHandler<DeleteNurseCategoryCommand> {

	@Inject
	private NurseClassificationRepository nurseClassificationRepository;

	@Override
	protected void handle(CommandHandlerContext<DeleteNurseCategoryCommand> context) {
		
		String companyId = AppContexts.user().companyId();
		
		DeleteNurseCategoryCommand command = context.getCommand();
		
		nurseClassificationRepository.delete(companyId, command.getNurseClassificationCode());

	}

}
