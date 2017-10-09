package nts.uk.ctx.at.request.app.command.application.common;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.AfterApprovalProcess;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateApplicationApproveHandler extends CommandHandler<UpdateApplicationCommonCmd> {

	@Inject
	private ApplicationRepository appRepo;

	// 4-1.詳細画面登録前の処理
	@Inject
	private DetailBeforeUpdate beforeRegisterRepo;
	// 8-2.詳細画面承認後の処理
	@Inject
	private AfterApprovalProcess afterApprovalProcessRepo;


	@Override
	protected void handle(CommandHandlerContext<UpdateApplicationCommonCmd> context) {
		String companyID = AppContexts.user().companyId();
		UpdateApplicationCommonCmd command = context.getCommand();
		
		Application application = appRepo.getAppById(companyID, command.getAppId()).get();

		// if approve
		// 4-1.   nothing
		beforeRegisterRepo.processBeforeDetailScreenRegistration(companyID, application.getApplicantSID(),
				application.getApplicationDate(), 1, command.getAppId(), application.getPrePostAtr());

		// 8.2.1. check
		afterApprovalProcessRepo.invidialApplicationErrorCheck(command.getAppId());
		// 8.2.2. update application
		afterApprovalProcessRepo.invidialApplicationUpdate(application);
		// 8-2.  
		afterApprovalProcessRepo.detailScreenAfterApprovalProcess(companyID, command.getAppId(), application);

	}

}
