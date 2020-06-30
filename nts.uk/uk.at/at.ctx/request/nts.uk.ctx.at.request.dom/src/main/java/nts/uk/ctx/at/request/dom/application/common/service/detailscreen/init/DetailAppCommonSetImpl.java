package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType_Old;
import nts.uk.ctx.at.request.dom.application.Application_New;
//import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.NewAppCommonSetService;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.DetailScreenBefore;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.InitMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforePreBootMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailScreenAppData;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailScreenInitModeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailedScreenPreBootModeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput_Old;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class DetailAppCommonSetImpl implements DetailAppCommonSetService {

	@Inject
	private ApplicationRepository_New applicationRepository;
	
	@Inject
	private DetailScreenBefore detailScreenBefore;
	
	@Inject
	private CommonAlgorithm commonAlgorithm;
	
	@Inject
	private BeforePreBootMode beforePreBootMode;
	
	@Inject
	private InitMode initMode;
	
	@Override
	public ApplicationMetaOutput getDetailAppCommonSet(String companyID, String applicationID) {
		Optional<Application_New> opApplication = applicationRepository.findByID(companyID, applicationID);
		if(!opApplication.isPresent()){
			throw new BusinessException("Msg_198");
		}
		return new ApplicationMetaOutput(
				opApplication.get().getAppID(),
				opApplication.get().getAppType(), 
				opApplication.get().getAppDate());
	}

	@Override
	public List<ApplicationMetaOutput> getListDetailAppCommonSet(String companyID, List<String> listAppID) {
		return applicationRepository.findByListID(companyID, listAppID)
				.stream().map(x -> new ApplicationMetaOutput(
						x.getAppID(),
						x.getAppType(),
						x.getAppDate()
				)).collect(Collectors.toList());
				
	}

	@Override
	public AppDispInfoStartupOutput_Old getCommonSetBeforeDetail(String companyID, String appID) {
		// 詳細画面の申請データを取得する
		DetailScreenAppData detailScreenAppData = detailScreenBefore.getDetailScreenAppData(appID);
		// 起動時の申請表示情報を取得する
		ApplicationType_Old appType = detailScreenAppData.getApplication().getAppType();
		List<String> applicantLst = Arrays.asList(detailScreenAppData.getApplication().getEmployeeID());
		GeneralDate startDate = detailScreenAppData.getApplication().getStartDate().orElse(detailScreenAppData.getApplication().getAppDate());
		GeneralDate endDate = detailScreenAppData.getApplication().getEndDate().orElse(detailScreenAppData.getApplication().getAppDate());
		List<GeneralDate> dateLst = new ArrayList<>();
		for(GeneralDate loopDate = startDate; loopDate.beforeOrEquals(endDate); loopDate = loopDate.addDays(1)) {
			dateLst.add(loopDate);
		}
		AppDispInfoStartupOutput_Old appDispInfoStartupOutput = commonAlgorithm.getAppDispInfoStart(companyID, appType, applicantLst, dateLst, false);
		// 詳細画面の利用者とステータスを取得する
		DetailedScreenPreBootModeOutput detailedScreenPreBootModeOutput = beforePreBootMode.judgmentDetailScreenMode(
				companyID, 
				AppContexts.user().employeeId(), 
				appID, 
				appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getBaseDate());
		// 詳細画面の画面モードを判断する
		DetailScreenInitModeOutput detailScreenInitModeOutput = initMode.getDetailScreenInitMode(
				detailedScreenPreBootModeOutput.getUser(), 
				detailedScreenPreBootModeOutput.getReflectPlanState().value);
		// 取得した「申請表示情報」を更新する
		AppDetailScreenInfo appDetailScreenInfo = new AppDetailScreenInfo();
		appDetailScreenInfo.setApplication(detailScreenAppData.getApplication());
		appDetailScreenInfo.setApprovalLst(detailScreenAppData.getDetailScreenApprovalData().getApprovalLst());
		appDetailScreenInfo.setAuthorComment(detailScreenAppData.getDetailScreenApprovalData().getAuthorComment());
		appDetailScreenInfo.setUser(detailedScreenPreBootModeOutput.getUser());
		appDetailScreenInfo.setReflectPlanState(detailedScreenPreBootModeOutput.getReflectPlanState());
		appDetailScreenInfo.setOutputMode(detailScreenInitModeOutput.getOutputMode());
		appDetailScreenInfo.setAuthorizableFlags(Optional.of(detailedScreenPreBootModeOutput.isAuthorizableFlags()));
		appDetailScreenInfo.setApprovalATR(Optional.of(detailedScreenPreBootModeOutput.getApprovalATR()));
		appDetailScreenInfo.setAlternateExpiration(Optional.of(detailedScreenPreBootModeOutput.isAlternateExpiration()));
		appDispInfoStartupOutput.setAppDetailScreenInfo(Optional.of(appDetailScreenInfo));
		// 更新した「申請表示情報」を返す
		return appDispInfoStartupOutput;
	}

}
