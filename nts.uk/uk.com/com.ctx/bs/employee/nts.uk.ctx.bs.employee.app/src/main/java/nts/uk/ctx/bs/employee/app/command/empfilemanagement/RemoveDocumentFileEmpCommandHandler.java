package nts.uk.ctx.bs.employee.app.command.empfilemanagement;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.empfilemanagement.EmpFileManagementRepository;


@Stateless
@Transactional
public class RemoveDocumentFileEmpCommandHandler extends CommandHandler<String>{
	
	@Inject
	private EmpFileManagementRepository empFileManagementRepo;

	@Override
	protected void handle(CommandHandlerContext<String> context) {
		
		String fileId = context.getCommand();
		
		empFileManagementRepo.removebyFileId(fileId);
		
	}

}
