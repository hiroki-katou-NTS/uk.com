package nts.uk.ctx.at.request.app.command.application.common;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.AfterApprovalProcess;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateApplicationApproveHandler extends CommandHandlerWithResult<ApplicationDto,ListMailApproval> {

	@Inject
	private ApplicationRepository appRepo;

	// 4-1.詳細画面登録前の処理
	@Inject
	private DetailBeforeUpdate beforeRegisterRepo;
	// 8-2.詳細画面承認後の処理
	@Inject
	private AfterApprovalProcess afterApprovalProcessRepo;


	@Override
	protected ListMailApproval handle(CommandHandlerContext<ApplicationDto> context) {
		String companyID = AppContexts.user().companyId();
		ApplicationDto command = context.getCommand();
		
		Application application =  ApplicationDto.toEntity(command);
		// 共通アルゴリズム「詳細画面登録前の処理」を実行する(thực hiện xử lý 「詳細画面登録前の処理」)
		// TODO: cac ham trong 4-1.詳細画面登録前の処理 lan nay deu bi hoan lai
		beforeRegisterRepo.processBeforeDetailScreenRegistration(companyID, application.getApplicantSID(),
				application.getApplicationDate(), 1,command.getApplicationID(), application.getPrePostAtr());

		// 申請個別のエラーチェック(check error theo từng loại đơn)
		afterApprovalProcessRepo.invidialApplicationErrorCheck(command.getApplicationID());
		
		// 2.申請個別の更新
		//afterApprovalProcessRepo.invidialApplicationUpdate(application);
		//共通アルゴリズム「詳細画面承認後の処理」を実行する(thực hiện xử lý 「詳細画面承認後の処理」)
		//8-2.詳細画面承認後の処理
		ListMailApproval listMailApproval = new ListMailApproval(afterApprovalProcessRepo.detailScreenAfterApprovalProcess(application)); 
		return listMailApproval;

	}

}
