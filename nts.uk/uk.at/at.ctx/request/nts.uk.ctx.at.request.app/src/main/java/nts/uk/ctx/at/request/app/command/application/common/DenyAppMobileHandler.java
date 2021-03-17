package nts.uk.ctx.at.request.app.command.application.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterDeny;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.AppHdsubRec;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.AppHdsubRecRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DenyAppMobileHandler extends CommandHandlerWithResult<AppDetailBehaviorCmd, ProcessResult> {
	
	@Inject
	private DetailAfterDeny detailAfterDeny;
	
	@Inject
	private DetailBeforeUpdate beforeRegisterRepo;
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	@Inject
	private AppHdsubRecRepository appHdsubRecRepository;
	
	@Override
	protected ProcessResult handle(CommandHandlerContext<AppDetailBehaviorCmd> context) {
		String companyID = AppContexts.user().companyId();
		ProcessResult processResult = new ProcessResult();
		AppDetailBehaviorCmd cmd = context.getCommand();
		String memo = cmd.getMemo();
		AppDispInfoStartupOutput appDispInfoStartupOutput = cmd.getAppDispInfoStartupOutput().toDomain();
		Application application = appDispInfoStartupOutput.getAppDetailScreenInfo().get().getApplication();
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
			
			// 9-1.詳細画面否認前の処理 Xử lý trước khi disapprove màn hình chi tiết 
			beforeRegisterRepo.exclusiveCheck(companyID, appID, application.getVersion());
			// 9-2.詳細画面否認後の処理Xử lý sau khi disapprove màn hình chi tiết 
			ProcessResult processResultLoop = detailAfterDeny.doDeny(companyID, appID, opApplication.get(), appDispInfoStartupOutput, memo);
			if(processResultLoop.isProcessDone()) {
				processResult.setProcessDone(true);
			}
		}
		return processResult;
	}

}
