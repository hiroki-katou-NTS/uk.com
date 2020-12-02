package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

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
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWorkRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.AppHdWorkDispInfoOutput;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;

/**
 * Refactor5
 * @author huylq
 *
 */
@Stateless
public class HolidayWorkRegisterServiceImpl implements HolidayWorkRegisterService {
	
	@Inject
	private ApplicationApprovalService applicationApprovalService;
	
	@Inject
	private AppHolidayWorkRepository appHolidayWorkRepository;

	@Inject
	private RegisterAtApproveReflectionInfoService registerAtApproveReflectionInfoService;
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	@Inject 
	private NewAfterRegister newAfterRegister;
	
	@Inject
	private DetailAfterUpdate detailAfterUpdate;
	
	@Override
	public ProcessResult register(String companyId, AppHolidayWork appHolidayWork, AppTypeSetting appTypeSetting, 
			AppHdWorkDispInfoOutput appHdWorkDispInfoOutput, List<ApprovalPhaseStateImport_New> lstApproval) {
		Application application = appHolidayWork.getApplication();
		
		//	2-2.新規画面登録時承認反映情報の整理
		applicationApprovalService.insertApp(application, lstApproval);
		registerAtApproveReflectionInfoService.newScreenRegisterAtApproveInfoReflect(appHolidayWork.getApplication().getEmployeeID(), application);
		appHolidayWorkRepository.add(appHolidayWork);
		
		//	暫定データの登録 (pending)
		
		//	2-3.新規画面登録後の処理
		return newAfterRegister.processAfterRegister(
				application.getAppID(), 
				appTypeSetting,
				appHdWorkDispInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().isMailServerSet());
	}
	
	@Override
	public ProcessResult update(String companyId, AppHolidayWork appHolidayWork) {
		Application application = (Application) appHolidayWork;
		//	ドメインモデル「申請」を更新する
		applicationRepository.update(application);
		//	ドメインモデル「休日出勤申請」を更新する
		appHolidayWorkRepository.update(appHolidayWork);
		
		//	暫定データの登録
		
		//	4-2.詳細画面登録後の処理
		return detailAfterUpdate.processAfterDetailScreenRegistration(
				companyId,
				application.getAppID(),
				null);
	}
}
