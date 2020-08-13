package nts.uk.ctx.at.request.app.command.application.common;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.ProcessCancel;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.shr.com.context.AppContexts;
@Stateless
@Transactional
public class CancelAppHandler extends CommandHandler<AppDispInfoStartupDto> {

	
	@Inject
	private ProcessCancel processCancelRepo;
	@Inject
	private DetailBeforeUpdate detailBeforeUpdate;

	@Override
	protected void handle(CommandHandlerContext<AppDispInfoStartupDto> context) {
		String companyID = AppContexts.user().companyId();
		AppDispInfoStartupOutput appDispInfoStartupOutput = context.getCommand().toDomain();
		Application application = appDispInfoStartupOutput.getAppDetailScreenInfo().get().getApplication();
		//1 : 排他チェック,
		detailBeforeUpdate.exclusiveCheck(companyID, application.getAppID(), application.getVersion());
		// 共通アルゴリズム「詳細画面取消の処理」を実行する(thực hiện xử lý 「詳細画面取消の処理」)
		processCancelRepo.detailScreenCancelProcess(companyID, application.getAppID(), application);
	}

}
