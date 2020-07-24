package nts.uk.ctx.at.request.app.command.application.common;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterDeny;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.shr.com.context.AppContexts;
@Stateless
@Transactional
public class DenyAppHandler extends CommandHandlerWithResult<AppDetailBehaviorCmd, ProcessResult> {
	
	@Inject
	private DetailAfterDeny detailAfterDeny;
	
	@Inject
	private DetailBeforeUpdate beforeRegisterRepo;

	@Override
	protected ProcessResult handle(CommandHandlerContext<AppDetailBehaviorCmd> context) {
		String companyID = AppContexts.user().companyId();
		AppDispInfoStartupOutput appDispInfoStartupOutput = context.getCommand().getAppDispInfoStartupOutput().toDomain();
		Application application = appDispInfoStartupOutput.getAppDetailScreenInfo().get().getApplication();
		// 9-1.詳細画面否認前の処理 Xử lý trước khi disapprove màn hình chi tiết 
		beforeRegisterRepo.exclusiveCheck(companyID, application.getAppID(), application.getVersion());
		// 9-2.詳細画面否認後の処理Xử lý sau khi disapprove màn hình chi tiết 
		return detailAfterDeny.doDeny(companyID, application.getAppID(), application, appDispInfoStartupOutput);
	}

}
