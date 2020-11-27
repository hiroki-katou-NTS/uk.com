package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWorkRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.AppHdWorkDispInfoOutput;

/**
 * Refactor5
 * @author huylq
 *
 */
@Stateless
public class HolidayWorkRegisterServiceImpl implements HolidayWorkRegisterService {
	
	@Inject
	private ApplicationApprovalService applicationRepository;
	
	@Inject
	private AppHolidayWorkRepository appHolidayWorkRepository;

	@Inject
	private RegisterAtApproveReflectionInfoService registerAtApproveReflectionInfoService;
	
	@Inject 
	private NewAfterRegister newAfterRegister;
	
	@Override
	public ProcessResult register(String companyId, AppHolidayWork appHolidayWork,
			AppHdWorkDispInfoOutput appHdWorkDispInfoOutput, List<ApprovalPhaseStateImport_New> lstApproval) {
		Application application = appHolidayWork.getApplication();
		
		//	2-2.新規画面登録時承認反映情報の整理
		applicationRepository.insertApp(application, lstApproval);
		registerAtApproveReflectionInfoService.newScreenRegisterAtApproveInfoReflect(appHolidayWork.getApplication().getEmployeeID(), application);
		appHolidayWorkRepository.add(appHolidayWork);
		
		//	暫定データの登録 (pending)
		
		//	2-3.新規画面登録後の処理
		return newAfterRegister.processAfterRegister(
				application.getAppID(), 
				null,	//huytodo
				appHdWorkDispInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().isMailServerSet());
	}

}
