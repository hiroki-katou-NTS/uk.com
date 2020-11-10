package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTimeRepository;

@Stateless
public class OverTimeRegisterServiceImpl implements OverTimeRegisterService {
	
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
	

}
