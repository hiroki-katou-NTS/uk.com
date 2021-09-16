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
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.ApprovalProcessParam;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterApproval;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.AppDetailScreenInfo;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ApproveProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.AppHdsubRec;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.AppHdsubRecRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ApproveAppMobileHandler extends CommandHandlerWithResult<AppDetailBehaviorCmd, ApproveProcessResult> {
	
	@Inject
	private DetailBeforeUpdate beforeRegisterRepo;
	
	@Inject
	private DetailAfterApproval detailAfterApproval;
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	@Inject
	private AppHdsubRecRepository appHdsubRecRepository;
	
	@Override
	protected ApproveProcessResult handle(CommandHandlerContext<AppDetailBehaviorCmd> context) {
		String companyID = AppContexts.user().companyId();
		AppDetailBehaviorCmd cmd = context.getCommand(); 
		String memo = cmd.getMemo();
		AppDispInfoStartupOutput appDispInfoStartupOutput = cmd.getAppDispInfoStartupOutput().toDomain();
		AppDetailScreenInfo appDetailScreenInfo = appDispInfoStartupOutput.getAppDetailScreenInfo().get();
		Application application = appDetailScreenInfo.getApplication();
		ApprovalProcessParam approvalProcessParam = new ApprovalProcessParam(
				appDispInfoStartupOutput.getAppDispInfoNoDateOutput().isMailServerSet(),
				appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getApplicationSetting().getAppTypeSettings()
				.stream().filter(x -> x.getAppType()==application.getAppType()).findAny().orElse(null));
		ApproveProcessResult approveProcessResult = new ApproveProcessResult();
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
			
			//アルゴリズム「排他チェック」を実行する (thực hiện xử lý 「check version」)
	        beforeRegisterRepo.exclusiveCheck(companyID, appID, application.getVersion());
			
			//8-2.詳細画面承認後の処理
	        detailAfterApproval.doApproval(companyID, appID, opApplication.get(), approvalProcessParam, memo);
		}
        approveProcessResult.setProcessDone(true);
 		return approveProcessResult;
	}

}
