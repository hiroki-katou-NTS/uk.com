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
import nts.uk.ctx.pereg.dom.filemanagement.EmpFileManagementRepository;
import nts.uk.ctx.sys.auth.app.find.user.GetUserByEmpFinder;
import nts.uk.ctx.sys.auth.app.find.user.UserAuthDto;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCategoryCorrectionLogParameter;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCorrectionLogParameter;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCategoryCorrectionLogParameter.PersonCorrectionItemInfo;
import nts.uk.shr.com.security.audittrail.correction.DataCorrectionContext;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.TargetDataKey;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.TargetDataKey.CalendarKeyType;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.InfoOperateAttr;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.PersonInfoProcessAttr;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionProcessorId;


@Stateless
@Transactional
public class RemoveDocumentFileEmpCommandHandler extends CommandHandler<RemoveDocumentFileCommand>{
	
	@Inject
	private EmpFileManagementRepository empFileManagementRepo;
	@Inject
	private GetUserByEmpFinder userFinder;
	
	@Override
	protected void handle(CommandHandlerContext<RemoveDocumentFileCommand> context) {
		
		// begin process write log
		DataCorrectionContext.transactional(CorrectionProcessorId.PEREG_REGISTER, () -> {
			RemoveDocumentFileCommand commmand = context.getCommand();
			empFileManagementRepo.removebyFileId(commmand.getFileid());
			setParamPersonLog(commmand);
			setDataLogCategory(commmand).forEach(cat -> {
				DataCorrectionContext.setParameter(cat.getHashID(), cat);
			});
		});
		
	}

	private void setParamPersonLog(RemoveDocumentFileCommand commmand) {
		List<UserAuthDto> userAuth = this.userFinder.getByListEmp(Arrays.asList(commmand.getSid()));
		UserAuthDto user = new UserAuthDto("", "", "", commmand.getSid() , "", "");
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
	
	private List<PersonCategoryCorrectionLogParameter> setDataLogCategory(RemoveDocumentFileCommand command) {
		List<PersonCategoryCorrectionLogParameter> ctgTargets = new ArrayList<>();
		List<PersonCorrectionItemInfo> lstItemInfo = new ArrayList<>();
		lstItemInfo.add(new PersonCorrectionItemInfo(
				command.getFileid(),
				command.getItemName().toString(),
				command.getFileid(),
				command.getFileName(),
				null,
				null,
				1));// 1 : String
		PersonCategoryCorrectionLogParameter ctgTargetCS00001 = new PersonCategoryCorrectionLogParameter(
				null,
				command.getCategoryName().toString(), 
				InfoOperateAttr.DELETE, 
				lstItemInfo.isEmpty() ? null : lstItemInfo,
				new TargetDataKey(CalendarKeyType.NONE, null, null),
				Optional.empty());		
		ctgTargets.add(ctgTargetCS00001);
		return ctgTargets;
	}

}
