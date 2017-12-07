package nts.uk.ctx.bs.employee.app.command.employee.deletemanagement;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.RemoveReason;

@Stateless
@Transactional
public class EmployeeDeleteCommandHandler extends CommandHandler<EmployeeDeleteCommand> {

	/** The repository. */
	@Inject
	private EmployeeDataMngInfoRepository EmpDataMngRepo;

	@Override
	protected void handle(CommandHandlerContext<EmployeeDeleteCommand> context) {

		if (context != null) {
			// get command
			EmployeeDeleteCommand command = context.getCommand();
			
			// get EmployeeDataMngInfo
			List<EmployeeDataMngInfo> listEmpData = EmpDataMngRepo.findByEmployeeId(command.getSId());
			if (!listEmpData.isEmpty()) {
				EmployeeDataMngInfo empInfo =  EmpDataMngRepo.findByEmployeeId(command.getSId()).get(0);
				empInfo.setRemoveReason(new RemoveReason(command.getReason()));
				EmpDataMngRepo.updateRemoveReason(empInfo);
			} 
		}

	}

}
