package nts.uk.ctx.pereg.app.command.filemanagement;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.pereg.dom.filemanagement.EmpFileManagementRepository;
import nts.uk.ctx.pereg.dom.filemanagement.PersonFileManagement;

@Stateless
public class AddEmpAvaOrMapCommandHandler extends CommandHandler<EmpAvaOrMapCommand> {

	@Inject
	private EmpFileManagementRepository empFileManagementRepository;

	@Inject
	private EmployeeDataMngInfoRepository emplRepo;

	@Override
	protected void handle(CommandHandlerContext<EmpAvaOrMapCommand> context) {
		EmpAvaOrMapCommand command = context.getCommand();
		Optional<EmployeeDataMngInfo> employee = emplRepo.findByEmpId(command.getEmployeeId());
		if (employee.isPresent()) {

			EmployeeDataMngInfo emp = employee.get();
			if (command.isAvatar()) {

				this.empFileManagementRepository.insert(
						PersonFileManagement.createFromJavaType(emp.getPersonId(), command.getFileId(), 0, null));

				this.empFileManagementRepository.insert(
						PersonFileManagement.createFromJavaType(emp.getPersonId(), command.getFileIdnew(), 3, null));
			}else {
				
				this.empFileManagementRepository.insert(
						PersonFileManagement.createFromJavaType(emp.getPersonId(), command.getFileId(), 1, null));
			}
		}

	}

}
