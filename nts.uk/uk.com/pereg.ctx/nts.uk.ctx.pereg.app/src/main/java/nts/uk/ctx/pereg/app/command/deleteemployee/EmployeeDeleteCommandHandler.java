package nts.uk.ctx.pereg.app.command.deleteemployee;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDeletionAttr;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.RemoveReason;
import nts.uk.shr.com.security.audittrail.correction.DataCorrectionContext;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.PersonInfoProcessAttr;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionProcessorId;

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
				
				// begin process write log
				DataCorrectionContext.transactionBegun(CorrectionProcessorId.PEREG_REGISTER);
				
				
				EmployeeDataMngInfo empInfo =  EmpDataMngRepo.findByEmployeeId(command.getSId()).get(0);
				GeneralDateTime currentDatetime = GeneralDateTime.legacyDateTime(new Date());
				empInfo.setDeleteDateTemporary(currentDatetime);
				empInfo.setRemoveReason(new RemoveReason(command.getReason()));
				empInfo.setDeletedStatus(EmployeeDeletionAttr.TEMPDELETED);
				EmpDataMngRepo.updateRemoveReason(empInfo);
				
				stampCardRepo.deleteBySid(command.getSId());
				
				
				
				
				// set param
				//val correctionLogParameter = new PeregCorrectionLogParameter(setCorrectionTarget(command));
				//DataCorrectionContext.setParameter(correctionLogParameter);
				DataCorrectionContext.transactionFinishing();
			} 
		}
	}
	
//	private List<PeregCorrectionTarget> setCorrectionTarget(EmployeeDeleteCommand command){
//		
//		// get User info from RequestList 486
//		
//		PeregCorrectionTarget target = new PeregCorrectionTarget(
//				"userId",
//				"employeeId",
//				"userName",
//				GeneralDate.today(),
//				PersonInfoProcessAttr.LOGICAL_DELETE,
//				command.getReason());
//		return Arrays.asList(target);
//	}
	
	
}
