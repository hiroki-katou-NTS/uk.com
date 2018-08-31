package nts.uk.ctx.at.request.dom.application.common.service.newscreen.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSettingRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.RecordDate;
import nts.uk.ctx.at.request.dom.setting.request.application.common.BaseDateFlg;
import nts.uk.ctx.at.request.dom.setting.workplace.ApprovalFunctionSetting;
import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachCompanyRepository;
import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachWorkplaceRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class StartupErrorCheckImpl implements StartupErrorCheckService {
	
	@Inject
	private EmployeeRequestAdapter employeeAdaptor;
	
	@Inject
	private RequestOfEachWorkplaceRepository requestOfEachWorkplaceRepository;
	
	@Inject
	private RequestOfEachCompanyRepository requestOfEachCompanyRepository;
	
	@Inject
	private RequestSettingRepository requestSettingRepository;
	
	@Override
	public void startupErrorCheck(GeneralDate baseDate, int appType, ApprovalRootContentImport_New approvalRootContentImport) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		ApprovalFunctionSetting requestSet = null;
		List<String> workPlaceIDs = employeeAdaptor.findWpkIdsBySid(companyID, employeeID, baseDate);
		List<ApprovalFunctionSetting> loopResult = new ArrayList<>();
		for(String workPlaceID : workPlaceIDs) {
			// ドメインモデル「職場別申請承認設定」を取得する ( Acquire domain model "Application approval setting by workplace" )
			Optional<ApprovalFunctionSetting> settingOfEarchWorkplaceOp = requestOfEachWorkplaceRepository.getFunctionSetting(companyID, workPlaceID, appType);
			if(settingOfEarchWorkplaceOp.isPresent()) {
				loopResult.add(settingOfEarchWorkplaceOp.get());
				break;
			}
		}
		// ドメインモデル「職場別申請承認設定」を取得できたかチェックする ( Check whether domain model "application approval setting by workplace" could be acquired )
		if(loopResult.size() == 0) {
			//ドメインモデル「会社別申請承認設定」を取得する ( Acquire the domain model "application approval setting by company" )
			Optional<ApprovalFunctionSetting> rqOptional = requestOfEachCompanyRepository.getFunctionSetting(companyID, appType);
			if(rqOptional.isPresent()){
				requestSet = rqOptional.get();
			}
		} else {
				// ドメインモデル「会社別申請承認設定」を取得する ( Acquire the domain model "application approval setting by workplace" )
				requestSet = loopResult.get(0);
		}
		if (requestSet.getAppUseSetting().getUserAtr().equals(UseAtr.NOTUSE)) {
			// 利用区分が利用しない
			throw new BusinessException("Msg_323");
		}
		// ドメインモデル「申請設定」．承認ルートの基準日をチェックする
		RecordDate baseDateFlg = RecordDate.SYSTEM_DATE;
		RequestSetting requestSetting = requestSettingRepository.findByCompany(companyID).get();
		baseDateFlg = requestSetting.getApplicationSetting().getRecordDate();
		if(baseDateFlg.equals(RecordDate.APP_DATE)){
			return;
		}
		if(approvalRootContentImport==null){
			return;
		}
		switch (approvalRootContentImport.getErrorFlag()) {
			case NO_APPROVER:
				throw new BusinessException("Msg_324");
			case APPROVER_UP_10:
				throw new BusinessException("Msg_237");
			case NO_CONFIRM_PERSON:
				throw new BusinessException("Msg_238");
			default:
				break;
		}
	}

}
