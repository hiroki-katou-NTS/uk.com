package nts.uk.ctx.pereg.app.command.deleteemployee;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDeletionAttr;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeCode;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.BusinessName;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.PersonNameGroup;
import nts.uk.ctx.sys.log.app.command.pereg.PeregCorrectionLogParameter;
import nts.uk.ctx.sys.log.app.command.pereg.PeregCorrectionLogParameter.PeregCorrectionTarget;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.security.audittrail.correction.DataCorrectionContext;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.PersonInfoProcessAttr;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionProcessorId;

@Stateless
@Transactional
public class RestoreDataEmpCommandHandler extends CommandHandler<EmployeeDeleteToRestoreCommand> {

	@Inject
	PersonRepository personRepo;

	@Inject
	private EmployeeDataMngInfoRepository empDataMngRepo;

	@Override
	protected void handle(CommandHandlerContext<EmployeeDeleteToRestoreCommand> context) {

		EmployeeDeleteToRestoreCommand command = context.getCommand();

		if (command != null) {
			
			//check Exit by SCD
			Optional<EmployeeDataMngInfo> checkEmpByScd = empDataMngRepo.findByEmployeCD(command.getCode(), AppContexts.user().companyId());
			
			if(checkEmpByScd.isPresent()) {
				
				throw new BusinessException("Msg_345");
			}
			
			
			List<EmployeeDataMngInfo> listEmpData = empDataMngRepo.findByEmployeeId(command.getId());

			if (!listEmpData.isEmpty()) {
				EmployeeDataMngInfo empInfo = listEmpData.get(0);
				empInfo.setEmployeeCode(new EmployeeCode(command.getCode().toString()));
				empInfo.setDeletedStatus(EmployeeDeletionAttr.NOTDELETED);
				empInfo.setDeleteDateTemporary(null);
				empInfo.setRemoveReason(null);

				empDataMngRepo.updateRemoveReason(empInfo);

				// get Person
				Person person = personRepo.getByPersonId(empInfo.getPersonId()).get();
				PersonNameGroup nameGroup = person.getPersonNameGroup();
				nameGroup.setBusinessName(new BusinessName(command.getName()));
				person.setPersonNameGroup(nameGroup);
				personRepo.update(person);
				
				// begin process write log
				DataCorrectionContext.transactionBegun(CorrectionProcessorId.PEREG_REGISTER);
				
				
				// set param
				val correctionLogParameter = new PeregCorrectionLogParameter(setCorrectionTarget(command));
				DataCorrectionContext.setParameter(correctionLogParameter);
				DataCorrectionContext.transactionFinishing();
				
			}
		}
	}

	private List<PeregCorrectionTarget> setCorrectionTarget(EmployeeDeleteToRestoreCommand command) {
		
		
		PeregCorrectionTarget target = new PeregCorrectionTarget(
				"userId",
				"employeeId",
				"userName",
				GeneralDate.today(),
				PersonInfoProcessAttr.RESTORE_LOGICAL_DELETE,
				null,
				null);
		return Arrays.asList(target);
		
		
	}

}
