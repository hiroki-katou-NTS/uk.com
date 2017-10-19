package nts.uk.ctx.bs.employee.app.command.empfilemanagement;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.employee.dom.empfilemanagement.EmpFileManagementRepository;
import nts.uk.ctx.bs.employee.dom.empfilemanagement.EmployeeFileManagement;

@Stateless
@Transactional
public class EmpDocumentFileCommandHandler extends CommandHandler<AddEmpDocumentFileCommand> {

	@Inject
	private EmpFileManagementRepository empFileManagementRepo;

	@Override
	protected void handle(CommandHandlerContext<AddEmpDocumentFileCommand> context) {


		AddEmpDocumentFileCommand commad = context.getCommand();

		List<EmployeeFileManagement> listEmpfile = empFileManagementRepo.getDataByParams(commad.getSid(), 2);

		Optional<EmployeeFileManagement> empFileMana = empFileManagementRepo.getEmpMana(commad.getFileid());
		if (empFileMana.isPresent()) {
			// update
			EmployeeFileManagement domain = empFileMana.get();
			domain.setFileID(commad.getFileid());
			domain.setPersonInfoCategoryId("");
			
		}else {
			// insert
			EmployeeFileManagement domain = new EmployeeFileManagement(commad.getSid(), commad.getFileid(),2,0,"");
			
		}
	}

}
