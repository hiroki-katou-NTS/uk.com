package nts.uk.ctx.at.request.app.find.application.gobackdirectly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.DetailAppCommonSetService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoNoDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.service.GoBackDirectService;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.service.GoBackDirectlyRegisterService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class GoBackDirectlyFinder {

	@Inject
	private CommonAlgorithm commonAlgorithm;

	@Inject
	private GoBackDirectService goBackDirectService;

	@Inject
	private GoBackDirectlyRegisterService goBackDirectlyRegisterService;
	
	@Inject
	private DetailAppCommonSetService appCommonSetService;
		/**
	 * 起動時の申請表示情報を取得する
	 * @param companyId 会社ID
	 * @param at 申請種類 = 直行直帰申請
	 * @param sIDs 申請者リスト
	 * @param appDates 申請対象日リスト
	 * @param mode 新規詳細モード = 新規モード
	 * @return 申請表示情報
	 */
	public AppDispInfoStartupOutput getApplicationDisplay(String companyId, ApplicationType at, List<String> sIDs,
			List<String> appDates, Boolean mode) {
		List<GeneralDate> lstDate = new ArrayList<>();
		if (!CollectionUtil.isEmpty(appDates)) {
			lstDate.addAll(appDates.stream().map(item -> GeneralDate.fromString(item, "yyyy/MM/dd"))
					.collect(Collectors.toList()));
		}
		AppDispInfoStartupOutput appDispInfoStartupOutputTemp = commonAlgorithm.getAppDispInfoStart(companyId, at, sIDs,
				lstDate, mode, Optional.empty(), Optional.empty());
		return appDispInfoStartupOutputTemp;
	}

	/**
	 * 申請日を変更する
	 * @param companyId 会社ID
	 * @param appDates 申請対象日リスト
	 * @param sIds 申請者リスト
	 * @param at 申請種類　＝　直行直帰
	 * @param appDispInfoNoDateOutput 申請表示情報(基準日関係なし)
	 * @param appDispInfoWithDateOutput 申請表示情報(基準日関係あり)
	 * @return 申請表示情報(基準日関係あり）
	 */
	public AppDispInfoWithDateOutput getDataByDate(String companyId, List<String> appDates, List<String> sIds, ApplicationType at, AppDispInfoNoDateOutput appDispInfoNoDateOutput, AppDispInfoWithDateOutput appDispInfoWithDateOutput) {
		List<GeneralDate> lstDate = new ArrayList<>();
		if (!CollectionUtil.isEmpty(appDates)) {
			lstDate.addAll(appDates.stream().map(item -> GeneralDate.fromString(item, "yyyy/MM/dd"))
					.collect(Collectors.toList()));
		}
		return commonAlgorithm.changeAppDateProcess(companyId, lstDate, at, appDispInfoNoDateOutput, appDispInfoWithDateOutput, Optional.empty());
	}

	
	//Refactor4
	
	public InforGoBackCommonDirectDto getStartKAF009(ParamStart paramStart) {
		String companyId = AppContexts.user().companyId();
		
		List<GeneralDate> dates = Collections.emptyList();
		if (!CollectionUtil.isEmpty(paramStart.getDates())) {
			dates = paramStart.getDates().stream().map(item -> GeneralDate.fromString(item, "yyyy/MM/dd")).collect(Collectors.toList());
		}
		AppDispInfoStartupOutput appDispInfoStartupOutput = paramStart.getAppDispInfoStartupOutput().toDomain();
		
		return InforGoBackCommonDirectDto.fromDomain(
				goBackDirectService.getDataAlgorithm(companyId, dates, paramStart.getSids(), appDispInfoStartupOutput));
	}
	/**
	 * Refactor 4 info when changing date by kaf009
	 * @param paramStart
	 * @return
	 */
	public InforGoBackCommonDirectDto getChangeDateKAF009(ParamChangeDate paramStart) {
		String companyId = AppContexts.user().companyId();
		
		return InforGoBackCommonDirectDto.fromDomain(
				goBackDirectService.getDateChangeAlgorithm(
						companyId,
						paramStart.getAppDates().stream().map(item -> GeneralDate.fromString(item, "yyyy/MM/dd")).collect(Collectors.toList()),
						CollectionUtil.isEmpty(paramStart.getEmployeeIds()) ? Arrays.asList(AppContexts.user().employeeId()) : paramStart.getEmployeeIds(),
						paramStart.getInforGoBackCommonDirectDto().toDomain())
				);
	}
	
	public List<ConfirmMsgOutput> checkBeforeRegister(ParamBeforeRegister param) {
		Application application = param.getApplicationDto().toDomain();
		// register
		if (param.getInforGoBackCommonDirectDto().getAppDispInfoStartup().getAppDetailScreenInfo() == null) {
			application = Application.createFromNew(
					application.getPrePostAtr(),
					application.getEmployeeID(),
					application.getAppType(),
					application.getAppDate(),
					application.getEnteredPersonID(),
					application.getOpStampRequestMode(),
					application.getOpReversionReason(),
					application.getOpAppStartDate(),
					application.getOpAppEndDate(),
					application.getOpAppReason(),
					application.getOpAppStandardReasonCD());
			
		} else {
			application.setAppID(param.getInforGoBackCommonDirectDto().getAppDispInfoStartup().getAppDetailScreenInfo().getApplication().getAppID());
		}
		GoBackDirectly goBackDirectly = param.getGoBackDirectlyDto().toDomain();
		goBackDirectly.setAppID(application.getAppID());
		return goBackDirectlyRegisterService.checkBeforRegisterNew(
				param.getCompanyId(),
				param.isAgentAtr(),	
				application,
				goBackDirectly,
				param.getInforGoBackCommonDirectDto().toDomain(),
				param.isMode());
	}
	
	public InforGoBackCommonDirectDto getDetailKAF009(ParamUpdate param) {
//		14-1.詳細画面起動前申請共通設定を取得する
		 AppDispInfoStartupOutput appDispInfoStartupOutput = 
				 appCommonSetService.getCommonSetBeforeDetail(param.getCompanyId(), param.getApplicationId());
//		アルゴリズム「直行直帰画面初期（更新）」を実行する
		return InforGoBackCommonDirectDto.fromDomain(goBackDirectService.getDataDetailAlgorithm(param.getCompanyId(), param.getApplicationId(), appDispInfoStartupOutput));		
	}
	
	public InforGoBackCommonDirectDto getDetailKAS009(ParamUpdate param) {
//		14-1.詳細画面起動前申請共通設定を取得する
		 AppDispInfoStartupOutput appDispInfoStartupOutput = 
				 appCommonSetService.getCommonSetBeforeDetail(param.getCompanyId(), param.getApplicationId());
//		アルゴリズム「直行直帰画面初期（更新）」を実行する
		return InforGoBackCommonDirectDto.fromDomain(goBackDirectService.getDataDetailAlgorithmMobile(param.getCompanyId(), param.getApplicationId(), appDispInfoStartupOutput));		
	}
	
	// Refactor5  mobile
	public InforGoBackCommonDirectDto getStartKAFS09(ParamStartMobile paramStart) {
		String companyId = AppContexts.user().companyId();
		Optional<String> employeeId = Optional.ofNullable(null);
		if (paramStart.getEmployeeId() != null) {
			employeeId = Optional.of(paramStart.getEmployeeId());
		}
		
		AppDispInfoStartupOutput appDispInfoStartupOutput = paramStart.getAppDispInfoStartupDto().toDomain();
		
		if (paramStart.getMode()) {
//			new
			return InforGoBackCommonDirectDto.fromDomain(goBackDirectService.getDataAlgorithmMobile(companyId, Optional.ofNullable(null), employeeId, appDispInfoStartupOutput));
		}else {
			
			String appId = "";
			if (appDispInfoStartupOutput.getAppDetailScreenInfo().isPresent()) {
				appId = appDispInfoStartupOutput.getAppDetailScreenInfo().get().getApplication().getAppID();
			}
//			edit
			return InforGoBackCommonDirectDto.fromDomain(goBackDirectService.getDataDetailAlgorithmMobile(paramStart.getCompanyId(), appId, appDispInfoStartupOutput));
		}
	}
	/**
	 * Refactor 5 info when changing date by kaf009
	 * @param paramStart
	 * @return
	 */
	public InforGoBackCommonDirectDto getChangeDateKAFS09(ParamChangeDate paramStart) {
		String companyId = AppContexts.user().companyId();
		
		return InforGoBackCommonDirectDto.fromDomain(
				goBackDirectService.getDateChangeMobileAlgorithm(
						companyId,
						paramStart.getAppDates().stream().map(item -> GeneralDate.fromString(item, "yyyy/MM/dd")).collect(Collectors.toList()),
						paramStart.getEmployeeIds(),
						paramStart.getInforGoBackCommonDirectDto().toDomain())
				);
	}
	

}
