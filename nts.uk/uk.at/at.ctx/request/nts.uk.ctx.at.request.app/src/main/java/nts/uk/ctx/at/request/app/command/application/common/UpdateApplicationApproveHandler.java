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
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.AfterApprovalProcess;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterApproval_New;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateApplicationApproveHandler extends CommandHandlerWithResult<InputCommonData,String> {

	// 4-1.詳細画面登録前の処理
	@Inject
	private DetailBeforeUpdate beforeRegisterRepo;
	// 8-2.詳細画面承認後の処理
	@Inject
	private AfterApprovalProcess afterApprovalProcessRepo;
	
	@Inject
	private CheckApprover checkApprover;
	
	@Inject
	private DetailAfterApproval_New detailAfterApproval_New;

	@Override
	protected String handle(CommandHandlerContext<InputCommonData> context) {
		String companyID = AppContexts.user().companyId();
		String memo = context.getCommand().getMemo();
		String employeeID = AppContexts.user().employeeId();
		ApplicationDto_New command = context.getCommand().getApplicationDto();
		//TODO: wait
		//checkApprover.checkApprover(command,memo);
		// Application_New application =  ApplicationDto_New.toEntity(command);		
		// 共通アルゴリズム「詳細画面登録前の処理」を実行する(thực hiện xử lý 「詳細画面登録前の処理」)
		// TODO: cac ham trong 4-1.詳細画面登録前の処理 lan nay deu bi hoan lai
		beforeRegisterRepo.processBeforeDetailScreenRegistration(companyID, command.getApplicationID(),
				GeneralDate.today(), 1,command.getApplicationID(), EnumAdaptor.valueOf(command.getPrePostAtr(), PrePostAtr.class), command.getVersion());

		// 申請個別のエラーチェック(check error theo từng loại đơn)
		afterApprovalProcessRepo.invidialApplicationErrorCheck(command.getApplicationID());
		
		// 2.申請個別の更新
		//afterApprovalProcessRepo.invidialApplicationUpdate(application);
		//共通アルゴリズム「詳細画面承認後の処理」を実行する(thực hiện xử lý 「詳細画面承認後の処理」)
		//8-2.詳細画面承認後の処理
		//TODO: wait
		String listMailApproval = detailAfterApproval_New.doApproval(companyID, command.getApplicationID(), employeeID, memo);
		return listMailApproval;

	}

}
