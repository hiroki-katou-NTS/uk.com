package nts.uk.ctx.at.request.dom.application.common.service.newscreen.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.setting.request.application.common.BaseDateFlg;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestAppDetailSetting;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestOfEachCompany;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestOfEachCompanyRepository;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestOfEachWorkplace;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestOfEachWorkplaceRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class StartupErrorCheckImpl implements StartupErrorCheckService {
	
	@Inject
	private RequestOfEachCompanyRepository requestRepo;
	
	@Inject
	private EmployeeRequestAdapter employeeAdaptor;
	
	@Inject
	private RequestOfEachWorkplaceRepository requestOfEachWorkplaceRepository;
	
	@Inject
	private RequestOfEachCompanyRepository requestOfEachCompanyRepository;
	
	@Override
	public void startupErrorCheck(GeneralDate baseDate, int appType, ApprovalRootContentImport_New approvalRootContentImport) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		RequestAppDetailSetting requestSet = null;
		List<String> workPlaceIDs = employeeAdaptor.findWpkIdsBySid(companyID, employeeID, baseDate);
		List<RequestOfEachWorkplace> loopResult = new ArrayList<>();
		for(String workPlaceID : workPlaceIDs) {
			// ドメインモデル「職場別申請承認設定」を取得する ( Acquire domain model "Application approval setting by workplace" )
			Optional<RequestOfEachWorkplace> requestOfEarchWorkplaceOp = requestOfEachWorkplaceRepository.getRequest(companyID, workPlaceID);
			if(requestOfEarchWorkplaceOp.isPresent()) {
				List<RequestAppDetailSetting> requestAppDetailSettings = requestOfEarchWorkplaceOp.get().getRequestAppDetailSettings().stream().filter(x -> x.getAppType().value == appType).collect(Collectors.toList());
				if(!CollectionUtil.isEmpty(requestAppDetailSettings)) {
					loopResult.add(requestOfEarchWorkplaceOp.get());
					break;
				}
			}
		}
		// ドメインモデル「職場別申請承認設定」を取得できたかチェックする ( Check whether domain model "application approval setting by workplace" could be acquired )
		if(loopResult.size() == 0) {
			//ドメインモデル「会社別申請承認設定」を取得する ( Acquire the domain model "application approval setting by company" )
			Optional<RequestOfEachCompany> rqOptional = requestOfEachCompanyRepository.getRequestByCompany(companyID);
			if(rqOptional.isPresent())
				requestSet = rqOptional.get().getRequestAppDetailSettings().get(0);
		} else {
				// ドメインモデル「会社別申請承認設定」を取得する ( Acquire the domain model "application approval setting by company" )
			requestSet = loopResult.get(0).getRequestAppDetailSettings().get(0);
		}
		if (requestSet.getUserAtr().equals(UseAtr.NOTUSE)) {
			// 利用区分が利用しない
			throw new BusinessException("Msg_323");
		}
		// ドメインモデル「申請設定」．承認ルートの基準日をチェックする
		BaseDateFlg baseDateFlg = BaseDateFlg.SYSTEM_DATE;
		if(baseDateFlg.equals(BaseDateFlg.APP_DATE)){
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
