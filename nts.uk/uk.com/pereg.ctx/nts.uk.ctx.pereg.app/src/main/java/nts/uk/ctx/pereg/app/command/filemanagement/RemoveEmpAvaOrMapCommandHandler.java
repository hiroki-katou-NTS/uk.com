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
public class RemoveEmpAvaOrMapCommandHandler extends CommandHandler<EmpAvaOrMapCommand> {

	@Inject
	private EmpFileManagementRepository empFileManagementRepository;

	@Inject
	private EmployeeDataMngInfoRepository emplRepo;

	@Override
	protected void handle(CommandHandlerContext<EmpAvaOrMapCommand> context) {
		EmpAvaOrMapCommand command = context.getCommand();
		Optional<EmployeeDataMngInfo> empOpt = emplRepo.findByEmpId(command.getEmployeeId());
		if (empOpt.isPresent()) {

			EmployeeDataMngInfo emp = empOpt.get();

			if (command.isAvatar()) {
				this.empFileManagementRepository
						.remove(empFileManagementRepository.getDataByParams(emp.getPersonId(), 0).get(0));

				this.empFileManagementRepository
						.remove(empFileManagementRepository.getDataByParams(emp.getPersonId(), 3).get(0));
			} else {

				this.empFileManagementRepository
						.remove(empFileManagementRepository.getDataByParams(emp.getPersonId(), 1).get(0));
			}
		}

	}

}
