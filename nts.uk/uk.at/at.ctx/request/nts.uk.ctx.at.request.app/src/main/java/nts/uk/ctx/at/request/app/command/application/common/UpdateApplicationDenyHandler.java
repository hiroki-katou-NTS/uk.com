package nts.uk.ctx.at.request.app.command.application.common;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto_New;
import nts.uk.ctx.at.request.app.find.application.common.CheckApprover;
import nts.uk.ctx.at.request.app.find.application.common.dto.InputCommonData;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.AfterDenialProcess;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterDeny;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.shr.com.context.AppContexts;
@Stateless
@Transactional
public class UpdateApplicationDenyHandler extends CommandHandlerWithResult<InputCommonData, String> {

	@Inject 
	private AfterDenialProcess afterDenialProcessRepo;
	
	@Inject
	private CheckApprover checkApprover;
	
	@Inject
	private DetailAfterDeny detailAfterDeny;
	
	@Inject
	private DetailBeforeUpdate beforeRegisterRepo;

	@Override
	protected String handle(CommandHandlerContext<InputCommonData> context) {
		String memo = context.getCommand().getMemo();
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		ApplicationDto_New command = context.getCommand().getApplicationDto();
		//TODO: wait
		//checkApprover.checkApprover(command,memo);
		// Application_New application =  ApplicationDto_New.toEntity(command);
		//共通アルゴリズム「詳細画面否認前の処理」を実行する(thực hiện xử lý 「詳細画面否認前の処理)
		beforeRegisterRepo.processBeforeDetailScreenRegistration(companyID, command.getApplicationID(),
				GeneralDate.today(), 1,command.getApplicationID(), EnumAdaptor.valueOf(command.getPrePostAtr(), PrePostAtr.class), command.getVersion());
		//アルゴリズム「排他チェック」を実行する(thực hiện xủa lý 「排他チェック」)
		//Thuc hien tu dong khi thay doi version cua bang
		//9.2 
		//TODO: wait
		return detailAfterDeny.doDeny(companyID, command.getApplicationID(), employeeID);
	}

}
