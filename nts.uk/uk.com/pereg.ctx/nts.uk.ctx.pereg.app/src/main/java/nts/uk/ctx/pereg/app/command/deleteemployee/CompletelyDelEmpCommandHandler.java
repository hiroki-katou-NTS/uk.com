package nts.uk.ctx.pereg.app.command.deleteemployee;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDeletionAttr;
import nts.uk.ctx.sys.auth.app.find.user.GetUserByEmpFinder;
import nts.uk.ctx.sys.auth.app.find.user.UserAuthDto;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCorrectionLogParameter;
import nts.uk.shr.com.security.audittrail.correction.DataCorrectionContext;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.PersonInfoProcessAttr;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionProcessorId;

@Stateless
@Transactional
public class CompletelyDelEmpCommandHandler extends CommandHandler<String>{
	
	@Inject
	private EmployeeDataMngInfoRepository empDataMngRepo;
	@Inject
	private GetUserByEmpFinder userFinder;

	@Override
	protected void handle(CommandHandlerContext<String> context) {
		
		String sid = context.getCommand();
		List<EmployeeDataMngInfo> listEmpData = empDataMngRepo.findByEmployeeId(sid);
		if (!listEmpData.isEmpty()) {
			// begin process write log
			DataCorrectionContext.transactional(CorrectionProcessorId.PEREG_REGISTER, () -> {
				EmployeeDataMngInfo empInfo = listEmpData.get(0);
				empInfo.setDeletedStatus(EmployeeDeletionAttr.PURGEDELETED);
				empDataMngRepo.updateRemoveReason(empInfo);
				
				//get User From RequestList486 Doctor Hieu
				List<UserAuthDto> userAuth = this.userFinder.getByListEmp(Arrays.asList(sid));
				UserAuthDto user = new UserAuthDto("", "", "", sid , "", "");
				if(userAuth.size() > 0) {
					 user = userAuth.get(0);
				}
				// set PeregCorrectionLogParameter
				PersonCorrectionLogParameter target = new PersonCorrectionLogParameter(
						user != null ? user.getUserID() : "",
						user != null ? user.getEmpID() : "", 
						user != null ?user.getEmpName(): "",
					    PersonInfoProcessAttr.COMPLETE_DELETE, null);
				
				DataCorrectionContext.setParameter(target.getHashID(), target);
			});
		}
	}
}
