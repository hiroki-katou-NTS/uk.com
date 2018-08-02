package nts.uk.ctx.pereg.app.command.deleteemployee;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDeletionAttr;
import nts.uk.ctx.sys.log.app.command.pereg.PeregCorrectionLogParameter;
import nts.uk.ctx.sys.log.app.command.pereg.PeregCorrectionLogParameter.PeregCorrectionTarget;
import nts.uk.shr.com.security.audittrail.correction.DataCorrectionContext;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.PersonInfoProcessAttr;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionProcessorId;

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
			
			// begin process write log
			DataCorrectionContext.transactionBegun(CorrectionProcessorId.PEREG_REGISTER);
			
			// set param
			val correctionLogParameter = new PeregCorrectionLogParameter(setCorrectionTarget(sid));
			DataCorrectionContext.setParameter(correctionLogParameter);
			DataCorrectionContext.transactionFinishing();
		}
	}
	
	private List<PeregCorrectionTarget> setCorrectionTarget(String sid){
		PeregCorrectionTarget target = new PeregCorrectionTarget(
				"userId",
				"employeeId",
				"userName",
				GeneralDate.today(),
				PersonInfoProcessAttr.COMPLETE_DELETE,
				null,
				null);
		return Arrays.asList(target);
	}

}
