package nts.uk.ctx.pereg.app.command.filemanagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.app.find.empfilemanagement.dto.EmployeeFileManagementDto;
import nts.uk.ctx.pereg.app.find.filemanagement.EmployeeFileManagementFinder;
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
@Transactional
public class EmpDocumentFileCommandHandler extends CommandHandler<AddEmpDocumentFileCommand> {

	@Inject
	private EmpFileManagementRepository empFileManagementRepo;
	@Inject
	EmployeeFileManagementFinder employeeFileManagementFinder;
	@Inject
	private GetUserByEmpFinder userFinder;

	@Override
	protected void handle(CommandHandlerContext<AddEmpDocumentFileCommand> context) {
		AddEmpDocumentFileCommand commad = context.getCommand();
		// begin process write log
		DataCorrectionContext.transactionBegun(CorrectionProcessorId.PEREG_REGISTER);
		Optional<PersonFileManagement> empFileMana = empFileManagementRepo.getEmpMana(commad.getFileid());
		if (empFileMana.isPresent()) {
			// update
			PersonFileManagement domain = empFileMana.get();
			domain.setFileID(commad.getFileid());
		} else {
			// insert
			List<EmployeeFileManagementDto> listFIle = this.employeeFileManagementFinder.getListDocumentFile(commad.getPid());
			PersonFileManagement domain = PersonFileManagement.createFromJavaType(commad.getPid(), commad.getFileid(),2, null);

			if (listFIle.isEmpty()) {
				domain.setUploadOrder(1);
			} else {
				domain.setUploadOrder((listFIle.get(listFIle.size() - 1).getUploadOrder()) + 1);
			}
			empFileManagementRepo.insert(domain);
		}
		setParamPersonLog(commad);
		setDataLogCategory(commad).forEach(cat -> {
			DataCorrectionContext.setParameter(cat.getHashID(), cat);
		});
		DataCorrectionContext.transactionFinishing();
	}
	
	private void setParamPersonLog(AddEmpDocumentFileCommand command){
		List<UserAuthDto> userAuth = this.userFinder.getByListEmp(Arrays.asList(command.getSid()));
		UserAuthDto user = new UserAuthDto("", "", "", command.getSid() , "", "");
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
		DataCorrectionContext.setParameter(target.getHashID(), target);
	}
	
	private List<PersonCategoryCorrectionLogParameter> setDataLogCategory(AddEmpDocumentFileCommand command) {
		List<PersonCategoryCorrectionLogParameter> ctgTargets = new ArrayList<>();
		List<PersonCorrectionItemInfo> lstItemInfo = new ArrayList<>();
		lstItemInfo.add(new PersonCorrectionItemInfo(
				command.getFileid(),
				command.getItemName().toString(),
				null,
				null,
				command.getFileid(),
				command.getFileName(),
				1));// 1 : String
		PersonCategoryCorrectionLogParameter ctgTargetCS00001 = new PersonCategoryCorrectionLogParameter(
				null,
				command.getCategoryName().toString(), 
				InfoOperateAttr.ADD, 
				lstItemInfo.isEmpty() ? null : lstItemInfo,
				new TargetDataKey(CalendarKeyType.NONE, null, null),
				Optional.empty());		
		ctgTargets.add(ctgTargetCS00001);
		return ctgTargets;
	}
}
