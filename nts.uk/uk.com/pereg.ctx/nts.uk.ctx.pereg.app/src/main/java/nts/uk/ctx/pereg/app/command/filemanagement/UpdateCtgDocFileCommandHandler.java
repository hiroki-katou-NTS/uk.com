package nts.uk.ctx.pereg.app.command.filemanagement;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.dom.filemanagement.EmpFileManagementRepository;
import nts.uk.ctx.pereg.dom.filemanagement.PersonFileManagement;

@Stateless
@Transactional
public class UpdateCtgDocFileCommandHandler extends CommandHandler<UpdateCtgDocFileDocumentFileCommand>{
	
	@Inject
	private EmpFileManagementRepository empFileManagementRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateCtgDocFileDocumentFileCommand> context) {
		
		UpdateCtgDocFileDocumentFileCommand command = context.getCommand();
		
		Optional<PersonFileManagement> domain = empFileManagementRepository.getEmpMana(command.getFileId());
		if (domain.isPresent()) {
			PersonFileManagement emp = domain.get();
			empFileManagementRepository.update(emp);
		}
		
		
	}

}
