package nts.uk.ctx.pereg.app.command.filemanagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.pereg.dom.filemanagement.EmpFileManagementRepository;
import nts.uk.ctx.pereg.dom.filemanagement.PersonFileManagement;
import nts.uk.ctx.sys.auth.app.find.user.GetUserByEmpFinder;
import nts.uk.ctx.sys.auth.app.find.user.UserAuthDto;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCategoryCorrectionLogParameter;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCategoryCorrectionLogParameter.PersonCorrectionItemInfo;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCorrectionLogParameter;
import nts.uk.shr.com.security.audittrail.correction.DataCorrectionContext;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.TargetDataKey;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.TargetDataKey.CalendarKeyType;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.InfoOperateAttr;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.PersonInfoProcessAttr;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionProcessorId;

@Stateless
public class UpdateEmpAvaOrMapCommandHandler extends CommandHandler<EmpAvaOrMapCommand>{

	@Inject
	private EmpFileManagementRepository empFileManagementRepository;
	@Inject
	private EmployeeDataMngInfoRepository emplRepo;
	@Inject
	private GetUserByEmpFinder userFinder;

	@Override
	protected void handle(CommandHandlerContext<EmpAvaOrMapCommand> context) {
		EmpAvaOrMapCommand command = context.getCommand();
		Optional<EmployeeDataMngInfo> employee = emplRepo.findByEmpId(command.getEmployeeId());
		if (employee.isPresent()) {
			EmployeeDataMngInfo emp = employee.get();
			if (command.isAvatar()) {
				// start ghi log
				DataCorrectionContext.transactionBegun(CorrectionProcessorId.PEREG_REGISTER);

				this.empFileManagementRepository.insert(PersonFileManagement.createFromJavaType(emp.getPersonId(), command.getFileId(), 0, null));
				this.empFileManagementRepository.insert(PersonFileManagement.createFromJavaType(emp.getPersonId(), command.getFileIdnew(), 3, null));
				
				setParamPersonLog(command);
				setDataLogCategory(command).forEach(cat -> {
					DataCorrectionContext.setParameter(cat.getHashID(), cat);
				});
				DataCorrectionContext.transactionFinishing();
				
			}else {
				// start ghi log
				DataCorrectionContext.transactionBegun(CorrectionProcessorId.PEREG_REGISTER);
				this.empFileManagementRepository.insert(PersonFileManagement.createFromJavaType(emp.getPersonId(), command.getFileId(), 1, null));
				setParamPersonLog(command);
				setDataLogCategory(command).forEach(cat -> {
					DataCorrectionContext.setParameter(cat.getHashID(), cat);
				});
				DataCorrectionContext.transactionFinishing();
			}
		}
	}
	
	private void setParamPersonLog(EmpAvaOrMapCommand command){
		//get User From RequestList486 Doctor Hieu
		List<UserAuthDto> userAuth = this.userFinder.getByListEmp(Arrays.asList(command.getEmployeeId()));
		UserAuthDto user = new UserAuthDto("", "", "", command.getEmployeeId() , "", "");
		if(userAuth.size() > 0) {
			 user = userAuth.get(0);
		}
		// set PeregCorrectionLogParameter
		PersonCorrectionLogParameter target = new PersonCorrectionLogParameter(
				user != null ? user.getUserID() : "",
				user != null ? user.getEmpID() : "", 
				user != null ?user.getUserName(): "",
				PersonInfoProcessAttr.UPDATE,
				null);
		// set correction log
		DataCorrectionContext.setParameter(target.getHashID(), target);
	}
	
	private List<PersonCategoryCorrectionLogParameter> setDataLogCategory(EmpAvaOrMapCommand command) {
		List<PersonCategoryCorrectionLogParameter> ctgTargets = new ArrayList<>();
		List<PersonCorrectionItemInfo> lstItemInfo = new ArrayList<>();
		lstItemInfo.add(new PersonCorrectionItemInfo(
				command.getFileId(),
				command.getItemName().toString(),
				command.getFileIdOld(),
				command.getFileNameOld(),
				command.getFileId(),
				command.getFileName(),
				1));// 1 : String
		PersonCategoryCorrectionLogParameter ctgTargetCS00001 = new PersonCategoryCorrectionLogParameter(
				null,
				command.getCategoryName().toString(), 
				InfoOperateAttr.UPDATE, 
				lstItemInfo.isEmpty() ? null : lstItemInfo,
				new TargetDataKey(CalendarKeyType.NONE, null, null), Optional.empty());		
		ctgTargets.add(ctgTargetCS00001);
		return ctgTargets;
	}
}
