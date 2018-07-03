package nts.uk.ctx.pereg.app.command.deleteemployee;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDeletionAttr;

@Stateless
@Transactional
public class CompletelyDelEmpCommandHandler extends CommandHandler<String>{
	
	@Inject
	private EmployeeDataMngInfoRepository empDataMngRepo;

	@Override
	protected void handle(CommandHandlerContext<String> context) {
		
		String sid = context.getCommand();
		List<EmployeeDataMngInfo> listEmpData = empDataMngRepo.findByEmployeeId(sid);
		if (!listEmpData.isEmpty()) {
			EmployeeDataMngInfo empInfo = listEmpData.get(0);
			empInfo.setDeletedStatus(EmployeeDeletionAttr.PURGEDELETED);
			empDataMngRepo.updateRemoveReason(empInfo);
		}
	}

}
