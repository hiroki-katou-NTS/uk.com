package nts.uk.ctx.pereg.app.command.deleteemployee;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDeletionAttr;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.RemoveReason;

@Stateless
@Transactional
public class EmployeeDeleteCommandHandler extends CommandHandler<EmployeeDeleteCommand> {

	/** The repository. */
	@Inject
	private EmployeeDataMngInfoRepository EmpDataMngRepo;
	
	@Inject
	private StampCardRepository stampCardRepo;

	@Override
	protected void handle(CommandHandlerContext<EmployeeDeleteCommand> context) {

		if (context != null) {
			// get command
			EmployeeDeleteCommand command = context.getCommand();
			
			// get EmployeeDataMngInfo
			List<EmployeeDataMngInfo> listEmpData = EmpDataMngRepo.findByEmployeeId(command.getSId());
			if (!listEmpData.isEmpty()) {
				EmployeeDataMngInfo empInfo =  EmpDataMngRepo.findByEmployeeId(command.getSId()).get(0);
				GeneralDateTime currentDatetime = GeneralDateTime.legacyDateTime(new Date());
				empInfo.setDeleteDateTemporary(currentDatetime);
				empInfo.setRemoveReason(new RemoveReason(command.getReason()));
				empInfo.setDeletedStatus(EmployeeDeletionAttr.TEMPDELETED);
				EmpDataMngRepo.updateRemoveReason(empInfo);
				
				stampCardRepo.deleteBySid(command.getSId());
				
			} 
		}
	}
}
