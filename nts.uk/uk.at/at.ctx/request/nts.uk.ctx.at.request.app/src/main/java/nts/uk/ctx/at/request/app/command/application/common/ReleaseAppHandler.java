package nts.uk.ctx.at.request.app.command.application.common;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterRelease;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforeProcessReleasing;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class ReleaseAppHandler extends CommandHandlerWithResult<AppDispInfoStartupDto, ProcessResult> {
	
	@Inject
	private BeforeProcessReleasing beforeProcessReleasingRepo;
	
	@Inject
	private DetailAfterRelease detailAfterRelease;

	@Override
	protected ProcessResult handle(CommandHandlerContext<AppDispInfoStartupDto> context) {	
		String companyID = AppContexts.user().companyId();
		Application application = context.getCommand().getAppDetailScreenInfo().getApplication().toDomain();
		
		// アルゴリズム「排他チェック」を実行する( thực hiện thuật toán :kiểm tra [排他チェック])
		beforeProcessReleasingRepo.detailScreenProcessBeforeReleasing(companyID, application.getAppID(), application.getVersion());
		
		// 共通アルゴリズム「詳細画面解除後の処理」を実行する(bắt đầu xử lý 「詳細画面解除後の処理」)
		return detailAfterRelease.detailAfterRelease(companyID, application.getAppID(), application);
	}

}
