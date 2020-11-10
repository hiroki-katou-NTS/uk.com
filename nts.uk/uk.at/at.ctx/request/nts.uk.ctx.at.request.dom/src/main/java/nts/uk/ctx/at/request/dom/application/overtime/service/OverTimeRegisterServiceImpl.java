package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTimeRepository;

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
	
	@Override
	public void register(
			String companyId,
			AppOverTime appOverTime,
			List<ApprovalPhaseStateImport_New> lstApproval,
			Boolean mailServerSet) {
		Application application = (Application)appOverTime;
		// 登録処理を実行
		appRepository.insertApp(application, lstApproval);
		registerService.newScreenRegisterAtApproveInfoReflect(application.getEmployeeID(), application);
		appOverTimeRepository.add(appOverTime);
		
		// 暫定データの登録(pendding)
		
		
		// 2-3.新規画面登録後の処理を実行 #112628
		return;
	}

	@Override
	public void update(String companyId, AppOverTime appOverTime) {
		Application application = (Application) appOverTime;
		// ドメインモデル「残業申請」を更新する
		appUpdateRepository.update(application);
		appOverTimeRepository.update(appOverTime);
		
		// 暫定データの登録
		
		// アルゴリズム「4-2.詳細画面登録後の処理」を実行する
		detailAfterUpdate.processAfterDetailScreenRegistration(
				companyId,
				application.getAppID(),
				null); //#112628
	}
	

}
