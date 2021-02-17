package nts.uk.ctx.at.request.app.command.application.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterRelease;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforeProcessReleasing;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.AppHdsubRec;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.AppHdsubRecRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ReleaseAppMobileHandler extends CommandHandlerWithResult<AppDispInfoStartupDto, ProcessResult> {
	
	@Inject
	private BeforeProcessReleasing beforeProcessReleasingRepo;
	
	@Inject
	private DetailAfterRelease detailAfterRelease;
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	@Inject
	private AppHdsubRecRepository appHdsubRecRepository;
	
	@Override
	protected ProcessResult handle(CommandHandlerContext<AppDispInfoStartupDto> context) {
		String companyID = AppContexts.user().companyId();
		ProcessResult processResult = new ProcessResult();
		Application application = context.getCommand().getAppDetailScreenInfo().getApplication().toDomain();
		List<String> appIDLst = new ArrayList<>();
		Optional<AppHdsubRec> opAppHdsubRec = appHdsubRecRepository.findByAppId(application.getAppID());
		if(opAppHdsubRec.isPresent()) {
			appIDLst.add(opAppHdsubRec.get().getRecAppID());
			appIDLst.add(opAppHdsubRec.get().getAbsenceLeaveAppID());
		} else {
			appIDLst.add(application.getAppID());
		}
		for(String appID : appIDLst) {
			Optional<Application> opApplication = applicationRepository.findByID(appID);
			
			// アルゴリズム「排他チェック」を実行する( thực hiện thuật toán :kiểm tra [排他チェック])
			beforeProcessReleasingRepo.detailScreenProcessBeforeReleasing(companyID, appID, application.getVersion());
			
			// 共通アルゴリズム「詳細画面解除後の処理」を実行する(bắt đầu xử lý 「詳細画面解除後の処理」)
			ProcessResult processResultLoop = detailAfterRelease.detailAfterRelease(companyID, appID, opApplication.get());
			if(processResultLoop.isProcessDone()) {
				processResult.setProcessDone(true);
			}
		}
		return processResult;
	}

}
