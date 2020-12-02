package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTimeRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;

@Stateless
public class OverTimeRegisterServiceImpl implements OverTimeRegisterService {
	
	@Inject
	private DetailAfterUpdate detailAfterUpdate;
	
	@Inject
	private ApplicationRepository appUpdateRepository;
	
	@Inject
	ApplicationApprovalService appRepository;
	
	@Inject
	AppOverTimeRepository appOverTimeRepository;
	
	@Inject
	private RegisterAtApproveReflectionInfoService registerService;
	
	@Inject 
	NewAfterRegister newAfterRegister;
	
	@Override
	public ProcessResult register(
			String companyId,
			AppOverTime appOverTime,
			// change listApproval -> common setting
			AppDispInfoStartupOutput appDispInfoStartupOutput,
			Boolean mailServerSet,
			AppTypeSetting appTypeSetting) {
		Application application = appOverTime.getApplication();
		// 登録処理を実行
		appRepository.insertApp(application,
				appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpListApprovalPhaseState().orElse(Collections.emptyList()));
		registerService.newScreenRegisterAtApproveInfoReflect(application.getEmployeeID(), application);
		appOverTimeRepository.add(appOverTime);
		
		// 暫定データの登録(pendding)
		
		
		// 2-3.新規画面登録後の処理を実行 #112628
		return newAfterRegister.processAfterRegister(
				application.getAppID(), 
				appTypeSetting,
				mailServerSet);
	}

	@Override
	public ProcessResult update(
			String companyId,
			AppOverTime appOverTime,
			AppDispInfoStartupOutput appDispInfoStartupOutput
			) {
		Application application = (Application) appOverTime;
		// ドメインモデル「残業申請」を更新する
		appUpdateRepository.update(application);
		appOverTimeRepository.update(appOverTime);
		
		// 暫定データの登録
		
		// アルゴリズム「4-2.詳細画面登録後の処理」を実行する
		return detailAfterUpdate.processAfterDetailScreenRegistration(
				companyId,
				application.getAppID(),
				appDispInfoStartupOutput); //#112628
	}
	

}
