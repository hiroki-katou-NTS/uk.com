package nts.uk.ctx.at.schedule.app.command.employeeinfo.medicalworkstyle;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.NurseClassificationRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author ThanhNX
 *
 */
@Stateless
public class DeleteNurseCategoryCommandHandler extends CommandHandler<DeleteNurseCategoryCommand> {

	@Inject
	private NurseClassificationRepository nurseClassificationRepository;

	@Override
	protected void handle(CommandHandlerContext<DeleteNurseCategoryCommand> context) {
		
		String companyId = AppContexts.user().companyId();
		
		DeleteNurseCategoryCommand command = context.getCommand();
		
		//delete ログイン会社ID, コード
		nurseClassificationRepository.delete(companyId, command.getNurseClassificationCode());

	}

}
