package nts.uk.ctx.bs.employee.app.command.empfilemanagement;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.empfilemanagement.EmpFileManagementRepository;
import nts.uk.ctx.bs.employee.dom.empfilemanagement.PersonFileManagement;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;

@Stateless
public class RemoveEmpAvaOrMapCommandHandler extends CommandHandler<EmpAvaOrMapCommand>{

	@Inject
	private EmpFileManagementRepository empFileManagementRepository;
	
	@Inject
	private EmployeeDataMngInfoRepository emplRepo;
	
	@Override
	protected void handle(CommandHandlerContext<EmpAvaOrMapCommand> context) {
		EmpAvaOrMapCommand command = context.getCommand();
		Optional<EmployeeDataMngInfo>empOpt = emplRepo.findByEmpId(command.getEmployeeId());
		if (empOpt.isPresent()) {
			this.empFileManagementRepository.remove(PersonFileManagement.createFromJavaType(empOpt.get().getPersonId(),
					command.getFileId(), command.getFileType(), null));
		}
		
	}

}
