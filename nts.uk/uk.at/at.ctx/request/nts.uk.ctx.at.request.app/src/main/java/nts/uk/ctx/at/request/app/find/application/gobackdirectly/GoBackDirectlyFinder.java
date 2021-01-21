package nts.uk.ctx.at.request.app.find.application.gobackdirectly;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.EmployeeOvertimeDto;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.AtEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.DetailAppCommonSetService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoNoDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository_Old;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.service.GoBackDirectAppSetService;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.service.GoBackDirectService;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.service.GoBackDirectlyRegisterService;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.HolidayService;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChangeService;
import nts.uk.ctx.at.request.dom.application.workchange.output.WorkTypeWorkTimeSelect;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class GoBackDirectlyFinder {
	@Inject
	private GoBackDirectlyRepository_Old goBackDirectRepo;
//	@Inject
//	private GoBackDirectCommonService goBackCommon;
	@Inject
	private GoBackDirectAppSetService goBackAppSet;
	@Inject
	private AtEmployeeAdapter atEmployeeAdapter;

	@Inject
	private CommonAlgorithm commonAlgorithm;
	@Inject
	private HolidayService holidayServiceDomain;
	@Inject
	private AppWorkChangeService appWorkChangeService;

	@Inject
	private GoBackDirectService goBackDirectService;

	@Inject
	private GoBackDirectlyRegisterService goBackDirectlyRegisterService;
	
	@Inject
	private DetailAppCommonSetService appCommonSetService;
	/**
	 * Get GoBackDirectlyDto
	 * 
	 * @param appID
	 * @return
	 */
	public GoBackDirectlyDto_Old getGoBackDirectlyByAppID(String appID) {
		String companyID = AppContexts.user().companyId();
		return goBackDirectRepo.findByApplicationID(companyID, appID).map(c -> GoBackDirectlyDto_Old.convertToDto(c))
				.orElse(null);
	}
	
	/**
	 * 直行直帰申請起動時初期データを取得する
	 * @param companyId 会社ID
	 * @param employeeId 申請者
	 * @param appDate 申請対象日<Optional>
	 * @param baseDate 基準日
	 * @param appEmployment 雇用別申請承認設定
	 * @param lstWts 就業時間帯の設定
	 * @return 	・勤務種類リスト
				・勤務種類コード
				・就業時間帯コード
	 */
	public InforGoBackDirectOutput getInforGoBackDirect(String companyId, String employeeId, GeneralDate appDate, GeneralDate baseDate,
			AppEmploymentSetting appEmployment, List<WorkTimeSetting> lstWts) {
		InforGoBackDirectOutput output = new InforGoBackDirectOutput();
		// 起動時勤務種類リストを取得する
		List<WorkType> lstWorkType = holidayServiceDomain.getWorkTypeLstStart(companyId, appEmployment);

		// 09_勤務種類就業時間帯の初期選択をセットする
		WorkTypeWorkTimeSelect workTypeAndWorktimeSelect = appWorkChangeService.initWorkTypeWorkTime(companyId,
				employeeId, baseDate, lstWorkType, lstWts);

		// set output
		output.setLstWorkType(lstWorkType);
		WorkType wType = workTypeAndWorktimeSelect.getWorkType();
		output.setWorkType(new InforWorkType(wType.getWorkTypeCode().v(), wType.getName().v()));
//		WorkTimeSetting wTime = workTypeAndWorktimeSelect.getWorkTime();
//		output.setWorkTime(
//				new InforWorkTime(wTime.getWorktimeCode().v(), wTime.getWorkTimeDisplayName().getWorkTimeName().v()));
		return output;
	}

	//

	/**
	 * convert to GoBackDirectSettingDto
	 * 
	 * @param SID
	 * @return
	 */

//	public GoBackDirectSettingDto getGoBackDirectCommonSetting(List<String> employeeIDs, String paramDate) {
//		String companyID = AppContexts.user().companyId();
//		GeneralDate appDate = GeneralDate.fromString(paramDate, "yyyy/MM/dd");
//		List<GeneralDate> listDate = new ArrayList<GeneralDate>();
//		listDate.add(appDate);
//		GoBackDirectSettingDto result = new GoBackDirectSettingDto();
//
//		String sID = AppContexts.user().employeeId();
//		List<EmployeeOvertimeDto> employeeOTs = new ArrayList<>();
//		if (!CollectionUtil.isEmpty(employeeIDs)) {
//			sID = employeeIDs.get(0);
//			List<EmployeeInfoImport> employees = this.atEmployeeAdapter.getByListSID(employeeIDs);
//			if (!CollectionUtil.isEmpty(employees)) {
//				for (EmployeeInfoImport emp : employees) {
//					EmployeeOvertimeDto employeeOT = new EmployeeOvertimeDto(emp.getSid(), emp.getBussinessName());
//					employeeOTs.add(employeeOT);
//				}
//
//			}
//		}
//		result = GoBackDirectSettingDto.convertToDto(goBackCommon.getSettingData(companyID, sID, appDate));
//		result.setEmployees(employeeOTs);
//		return result;
//	}
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
	 * 直行直帰画面初期（新規）
	 * @param companyID 会社ID
	 * @param sIDs 申請者リスト<Optional>
	 * @param appDates 申請者リスト<Optional>
	 * @param appDispInfoStartupOutput 申請表示情報
	 * @return 直行直帰申請起動時の表示情報
	 */
	public InforGoBackCommonDirectDto getCommonSetting(String companyID, List<String> sIDs, List<String> appDates,
			AppDispInfoStartupOutput appDispInfoStartupOutput) {
//		InforGoBackCommonDirectOutput output = new InforGoBackCommonDirectOutput();
//		List<GeneralDate> lstDate = new ArrayList<>();
//		if (!CollectionUtil.isEmpty(appDates)) {
//			lstDate.addAll(appDates.stream().map(item -> GeneralDate.fromString(item, "yyyy/MM/dd"))
//					.collect(Collectors.toList()));
//		}
//		String sId = CollectionUtil.isEmpty(sIDs) ? "" : sIDs.get(0);
//		GeneralDate appDate = CollectionUtil.isEmpty(lstDate) ? null : lstDate.get(0);
//		GeneralDate baseDate = appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getBaseDate();
//		AppDispInfoStartupDto appDispInfoStartupDto = AppDispInfoStartupDto.fromDomain(appDispInfoStartupOutput);
//		AppEmploymentSetting aes = appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getEmploymentSet();
//		List<WorkTimeSetting> lstWts = appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getWorkTimeLst();
//		// 直行直帰申請起動時初期データを取得する
//		InforGoBackDirectOutput inforGoBackDirectOutput = this.getInforGoBackDirect(companyID, sId, appDate, baseDate, aes,
//				lstWts);
//		output.setLstWorkType(inforGoBackDirectOutput.getLstWorkType());
//		output.setWorkType(inforGoBackDirectOutput.getWorkType());
//		output.setWorkTime(inforGoBackDirectOutput.getWorkTime());
//		// エラー情報をチェックする(Check ErrorInfo)
//		if (appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getErrorFlag() != ErrorFlagImport.NO_ERROR) {
//			// handle error message
//		} else {
//			// ドメインモデル「直行直帰申請共通設定」より取得する
//			Optional<GoBackDirectlyCommonSetting> gbdcs = goBackDirectCommonSetRepo.findByCompanyID(companyID);
//			if (gbdcs.isPresent()) {
//				output.setGobackDirectCommon(gbdcs.get());
//			}
//			output.setAppDispInfoStartup(appDispInfoStartupOutput);
//		}
//		// check output mode Screen
//		// waiting handle
//		Optional<GoBackDirectly> gbdOptional = Optional.ofNullable(null);
//		output.setGoBackDirectly(gbdOptional);
//		if (appDispInfoStartupDto.appDetailScreenInfo != null) {
//			if (appDispInfoStartupDto.appDetailScreenInfo.outputMode == 1) {
//				// 直行直帰申請
//			}
//		}
//		return InforGoBackCommonDirectDto.convertDto(output);
		return null;
	}
	/**
	 * 申請日を変更する
	 * @param companyId 会社ID
	 * @param appDate 申請対象日リスト
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

	/**
	 * get Detail Data to
	 * 
	 * @param appID
	 * @return
	 */
	public GoBackDirectDetailDto getGoBackDirectDetailByAppId(String appID) {
		return GoBackDirectDetailDto.convertToDto(goBackAppSet.getGoBackDirectAppSet(appID));
	}
	public InforGoBackCommonDirectDto getOutputApplication(String sId, String appdate) {
		String companyID = AppContexts.user().companyId();
		List<String>sIds = new ArrayList<String>();
		sIds.add(AppContexts.user().employeeId());
		List<String> appDates = new ArrayList<String>();
		GeneralDate today = GeneralDate.today();
		appDates.add(today.toString("yyyy/MM/dd"));
		Boolean newMode = true;
		//起動時の申請表示情報を取得する
		AppDispInfoStartupOutput appDispInfoStartupOutput = this.getApplicationDisplay(
				companyID, 
				ApplicationType.GO_RETURN_DIRECTLY_APPLICATION, 
				sIds, 
				appDates, 
				newMode
				);
//		InforGoBackCommonDirectOutput output = goBackApplicationDomainService.getInfoOutput(companyID, sIds, appDates, appDispInfoStartupOutput);
		
//		return InforGoBackCommonDirectDto.convertDto(output);
		return null;
	}
	
	//Refactor4
	
	public InforGoBackCommonDirectDto getStartKAF009(ParamStart paramStart) {
		String companyId = AppContexts.user().companyId();
		Optional<String> employeeId = Optional.ofNullable(null);
		if (paramStart.getApplicantEmployeeID() != null) {
			employeeId = Optional.of(paramStart.getApplicantEmployeeID());
		}
		
		AppDispInfoStartupOutput appDispInfoStartupOutput = paramStart.getAppDispInfoStartupOutput().toDomain();
		
		return InforGoBackCommonDirectDto.fromDomain(goBackDirectService.getDataAlgorithm(companyId, Optional.ofNullable(null), employeeId, appDispInfoStartupOutput));
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
						paramStart.getEmployeeIds(),
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
