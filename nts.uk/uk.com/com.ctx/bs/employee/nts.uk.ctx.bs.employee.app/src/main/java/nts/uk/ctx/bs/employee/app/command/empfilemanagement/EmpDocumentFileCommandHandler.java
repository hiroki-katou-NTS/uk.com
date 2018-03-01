package nts.uk.ctx.bs.employee.app.command.empfilemanagement;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.app.find.empfilemanagement.EmployeeFileManagementFinder;
import nts.uk.ctx.bs.employee.app.find.empfilemanagement.dto.EmployeeFileManagementDto;
import nts.uk.ctx.bs.employee.dom.empfilemanagement.EmpFileManagementRepository;
import nts.uk.ctx.bs.employee.dom.empfilemanagement.PersonFileManagement;

@Stateless
@Transactional
public class EmpDocumentFileCommandHandler extends CommandHandler<AddEmpDocumentFileCommand> {

	@Inject
	private EmpFileManagementRepository empFileManagementRepo;

	@Inject
	EmployeeFileManagementFinder employeeFileManagementFinder;

	@Override
	protected void handle(CommandHandlerContext<AddEmpDocumentFileCommand> context) {

		AddEmpDocumentFileCommand commad = context.getCommand();

		Optional<PersonFileManagement> empFileMana = empFileManagementRepo.getEmpMana(commad.getFileid());
		if (empFileMana.isPresent()) {
			// update
			PersonFileManagement domain = empFileMana.get();
			domain.setFileID(commad.getFileid());

		} else {
			// insert

			List<EmployeeFileManagementDto> listFIle = this.employeeFileManagementFinder
					.getListDocumentFile(commad.getPid());

			PersonFileManagement domain = PersonFileManagement.createFromJavaType(commad.getPid(), commad.getFileid(),
					2, null);

			if (listFIle.isEmpty()) {
				domain.setUploadOrder(1);
			} else {
				domain.setUploadOrder((listFIle.get(listFIle.size() - 1).getUploadOrder()) + 1);
			}

			empFileManagementRepo.insert(domain);
		}
	}
}
