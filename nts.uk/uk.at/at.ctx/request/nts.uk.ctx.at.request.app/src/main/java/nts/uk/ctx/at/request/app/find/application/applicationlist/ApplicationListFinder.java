package nts.uk.ctx.at.request.app.find.application.applicationlist;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.i18n.I18NText;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.command.application.applicationlist.AppListExtractConditionCmd;
import nts.uk.ctx.at.request.app.command.application.applicationlist.ApplicationListCmdMobile;
import nts.uk.ctx.at.request.app.command.application.applicationlist.ListOfAppTypesCmd;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto_New;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.applist.extractcondition.AppListExtractCondition;
import nts.uk.ctx.at.request.dom.application.applist.extractcondition.ApplicationListAtr;
import nts.uk.ctx.at.request.dom.application.applist.service.AppListInitialRepository;
import nts.uk.ctx.at.request.dom.application.applist.service.AppMasterInfo;
import nts.uk.ctx.at.request.dom.application.applist.service.CheckColorTime;
import nts.uk.ctx.at.request.dom.application.applist.service.ListOfAppTypes;
import nts.uk.ctx.at.request.dom.application.applist.service.PhaseStatus;
import nts.uk.ctx.at.request.dom.application.applist.service.param.AppListInfo;
import nts.uk.ctx.at.request.dom.application.applist.service.param.AppListInitOutput;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.approvallistsetting.ApprovalListDispSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.approvallistsetting.ApprovalListDisplaySetting;
import nts.uk.ctx.at.request.dom.setting.company.displayname.AppDispNameRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class ApplicationListFinder {

	@Inject
	private AppListInitialRepository repoAppListInit;
	@Inject
	private AppDispNameRepository repoAppDispName;

	@Inject
	private ApprovalListDispSetRepository approvalListDispSetRepository;

	// @Inject
	// private ApprovalListService approvalListService;

	private static final int MOBILE = 1;
	public static final Integer NORMAL_MODE = 0;
	public static final Integer APPROVAL_MODE = 1;
	private static final String DATE_FORMAT = "yyyy/MM/dd";

	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面.アルゴリズム.申請一覧初期処理.申請一覧初期処理
	 * @param param
	 * @return
	 */
	public AppListInitDto getAppList(AppListParamFilter param){
		String companyID = AppContexts.user().companyId();
		AppListInfo result = new AppListInfo();
		// ドメインモデル「承認一覧表示設定」を取得する
		Optional<ApprovalListDisplaySetting> opApprovalListDisplaySetting = approvalListDispSetRepository.findByCID(companyID);
		if(opApprovalListDisplaySetting.isPresent()) {
			result.getDisplaySet().setWorkplaceNameDisp(opApprovalListDisplaySetting.get().getDisplayWorkPlaceName().value);
			result.getDisplaySet().setAppDateWarningDisp(opApprovalListDisplaySetting.get().getWarningDateDisAtr().v());
		}
		GeneralDate startDate = null;
		GeneralDate endDate = null;
		// 「SPR連携用のパラメータ」をチェックする
		if(param.getSprParam() != null) {
			// 開始日付＝パラメタの期間（開始日）、終了日付＝パラメタの期間（終了日）とする
			startDate = GeneralDate.fromString(param.getStartDate(), DATE_FORMAT);
			endDate = GeneralDate.fromString(param.getEndDate(), DATE_FORMAT);
		} else {
			// メニューより起動か、申請画面からの戻りかチェックする(Kiểm tra xem bắt đầu từ menu hay là  trở về từ màn hình application)
			if(Strings.isNotBlank(param.getAppListExtractCondition().getPeriodStartDate()) &&
					Strings.isNotBlank(param.getAppListExtractCondition().getPeriodEndDate())) {
				// ドメインモデル「申請一覧抽出条件」を取得する
				AppListExtractCondition appListExtractCondition = param.getAppListExtractCondition().toDomain();
				// có selectAppTypeLst là KAF000, nếu không có là các màn hình khác, mặc định select all
				List<ListOfAppTypes> selectAppTypeLst = appListExtractCondition.getOpListOfAppTypes().map(x -> x.stream().filter(y -> y.isChoice()).collect(Collectors.toList()))
						.orElse(Collections.emptyList());
				if(CollectionUtil.isEmpty(selectAppTypeLst)) {
					appListExtractCondition.getOpListOfAppTypes().ifPresent(x -> {
						x.stream().map(y -> {
							y.setChoice(true);
							return y;
						}).collect(Collectors.toList());
					});
				}
				// アルゴリズム「申請一覧リスト取得」を実行する
				AppListInitOutput appListInitOutput = repoAppListInit.getApplicationList(appListExtractCondition, param.getDevice(), result);
				return AppListInitDto.fromDomain(appListInitOutput);
			}
			// 期間（開始日、終了日）が存する場合
			if(Strings.isNotBlank(param.getStartDate()) && Strings.isNotBlank(param.getEndDate())) {
				// 開始日付＝期間（開始日）、終了日付＝期間（終了日）とする
				startDate = GeneralDate.fromString(param.getStartDate(), DATE_FORMAT);
				endDate = GeneralDate.fromString(param.getEndDate(), DATE_FORMAT);
				result.getDisplaySet().setStartDateDisp(startDate);
				result.getDisplaySet().setEndDateDisp(endDate);
			} else {
				// URLパラメータをチェック/Check URL Parameters
				if(param.getMode() == ApplicationListAtr.APPROVER.value){
					// 申請一覧初期日付期間(Period date intial application list)
					DatePeriod period = repoAppListInit.getInitialPeriod(companyID);
					startDate = period.start();
					endDate = period.end();
					result.getDisplaySet().setStartDateDisp(startDate);
					result.getDisplaySet().setEndDateDisp(endDate);
				}else{
					// 申請一覧初期日付期間_申請 (Period date intial application list _Application)
					DatePeriod period = repoAppListInit.getInitPeriodApp(companyID);
					startDate = period.start();
					endDate = period.end();
					result.getDisplaySet().setStartDateDisp(startDate);
					result.getDisplaySet().setEndDateDisp(endDate);
				}
			}
		}
		// ユーザー固有情報「申請一覧抽出条件」を初期し、初期情報で更新する
		AppListExtractCondition appListExtractCondition = new AppListExtractCondition();
		if(param.getAppListExtractCondition() != null) {
			appListExtractCondition = param.getAppListExtractCondition().toDomain();
		}
		appListExtractCondition.setPeriodStartDate(startDate);
		appListExtractCondition.setPeriodEndDate(endDate);
		appListExtractCondition.setAppListAtr(EnumAdaptor.valueOf(param.getMode(), ApplicationListAtr.class));
		//・申請一覧抽出条件.申請種類リスト　＝Input.申請種類リスト情報
		List<ListOfAppTypes> listOfAppTypesLst = new ArrayList<>();
		for(ListOfAppTypes listOfAppTypes : param.getListOfAppTypes().stream().map(x -> x.toDomain()).collect(Collectors.toList())) {
			listOfAppTypesLst.add(new ListOfAppTypes(
					listOfAppTypes.getAppType(),
					listOfAppTypes.getAppName(),
					false,
					listOfAppTypes.getOpProgramID(),
					listOfAppTypes.getOpApplicationTypeDisplay(),
					Optional.of("A")));
		}
		appListExtractCondition.setOpListOfAppTypes(Optional.of(listOfAppTypesLst));
		// INPUT「対象申請種類List」が存在する
		if(!CollectionUtil.isEmpty(param.getLstAppType())) {
			for(Integer appType : param.getLstAppType()) {
				appListExtractCondition.getOpListOfAppTypes().ifPresent(x -> {
					x.stream().filter(y -> y.getAppType().value == appType).findAny().ifPresent(y -> {
						y.setChoice(true);
					});
				});
			}
		} else {
			// パラメタ「抽出対象」をチェックする
			if(param.getSprParam() != null && param.getSprParam().getExtractionTarget() == 1) {
				// 申請種類＝残業申請の「申請一覧抽出条件.申請種類リスト.選択にTrueをセットする
				appListExtractCondition.getOpListOfAppTypes().ifPresent(x -> {
					x.stream().filter(y -> y.getAppType() == ApplicationType.OVER_TIME_APPLICATION).findAny().ifPresent(y -> {
						y.setChoice(true);
					});
				});
			} else {
				if(!CollectionUtil.isEmpty(param.getLstAppType())) {
					for(Integer appType : param.getLstAppType()) {
						appListExtractCondition.getOpListOfAppTypes().ifPresent(x -> {
							x.stream().filter(y -> y.getAppType().value == appType).findAny().ifPresent(y -> {
								y.setChoice(true);
							});
						});
					}
				} else {
					appListExtractCondition.getOpListOfAppTypes().ifPresent(x -> {
						x.stream().map(y -> {
							y.setChoice(true);
							return y;
						}).collect(Collectors.toList());
					});
				}
			}
		}
		// パラメータ社員ID ( Parameter employeeID)
		if(Strings.isNotBlank(param.getEmployeeID())) {
			appListExtractCondition.setOpListEmployeeID(Optional.of(Arrays.asList(param.getEmployeeID())));
		}
		// アルゴリズム「申請一覧リスト取得」を実行する
		AppListInitOutput appListInitOutput = repoAppListInit.getApplicationList(appListExtractCondition, param.getDevice(), result);
		return AppListInitDto.fromDomain(appListInitOutput);
//		AppListExtractConditionDto condition = param.getCondition();
//		int device = param.getDevice();
//		String companyId = AppContexts.user().companyId();
//		Integer appAllNumber = null;
//		Integer appPerNumber = null;
//		if(device == MOBILE){
//			//・設定の名前：SMART_PHONE
//			//・機能の名前：APP_ALL_NUMBER、APP_PER_NUMBER
//			String[] subName = {"APP_ALL_NUMBER","APP_PER_NUMBER"};
//			Map<String, Integer> mapParam = repoApplication.getParamCMMS45(companyId, "SMART_PHONE", Arrays.asList(subName));
//			if(mapParam.isEmpty()){
//				mapParam = repoApplication.getParamCMMS45(AppContexts.user().contractCode()+ "-0000", "SMART_PHONE", Arrays.asList(subName));
//			}
//			if(!mapParam.isEmpty()){
//				appAllNumber = mapParam.get("APP_ALL_NUMBER");
//				appPerNumber = mapParam.get("APP_PER_NUMBER");
//			}
//		}
//
//		//対象申請種類List
//		List<Integer> lstType = param.getLstAppType();
//		//ドメインモデル「承認一覧表示設定」を取得する-(Lấy domain Approval List display Setting)
//		Optional<RequestSetting> requestSet = repoRequestSet.findByCompany(companyId);
//		ApprovalListDisplaySetting appDisplaySet = null;
//		Integer isDisPreP = null;
//		if(requestSet.isPresent()){
//			appDisplaySet = requestSet.get().getApprovalListDisplaySetting();
//			isDisPreP = requestSet.get().getApplicationSetting().getAppDisplaySetting().getPrePostAtrDisp().value;
//		}
//		//URパラメータが存在する-(Check param)
//		if(StringUtil.isNullOrEmpty(condition.getStartDate(), false) || StringUtil.isNullOrEmpty(condition.getEndDate(), false)){
//			//アルゴリズム「申請一覧初期日付期間」を実行する-(Thực hiện thuật toán lấy ngày　－12)
//			DatePeriod date = null;
//			if(condition.getAppListAtr().equals(ApplicationListAtr.APPROVER.value)){
//				date = repoAppListInit.getInitialPeriod(companyId);
//			}else{
//				date = repoAppListInit.getInitPeriodApp(companyId);
//			}
//			condition.setStartDate(date.start().toString());
//			condition.setEndDate(date.end().toString());
//		}
//		//ドメインモデル「申請一覧共通設定フォーマット.表の列幅」を取得-(Lấy 表の列幅)//xu ly o ui
//		//アルゴリズム「申請一覧リスト取得」を実行する-(Thực hiện thuật toán Application List get): 1-申請一覧リスト取得
//		AppListExtractCondition appListExCon = condition.convertDtotoDomain(condition);
//		// AppListOutPut lstAppData = repoAppListInit.getApplicationList(appListExCon, appDisplaySet, device, lstType);
//		AppListOutPut lstAppData = null;
//		List<ApplicationDto_New> lstAppDto = new ArrayList<>();
//		for (Application_New app : lstAppData.getLstApp()) {
//            lstAppDto.add(ApplicationDto_New.fromDomainCMM045(app));
//		}
//		List<AppStatusApproval> lstStatusApproval = new ArrayList<>();
//		List<ApproveAgent> lstAgent = new ArrayList<>();
//		if(condition.getAppListAtr() == 1){//mode approval
//			List<ApplicationFullOutput> lstFil = lstAppData.getLstAppFull().stream().filter(c -> c.getStatus() != null).collect(Collectors.toList());
//			for (ApplicationFullOutput app : lstFil) {
//				lstAgent.add(new ApproveAgent(app.getApplication().getAppID(), app.getAgentId()));
//				lstStatusApproval.add(new AppStatusApproval(app.getApplication().getAppID(), app.getStatus()));
//			}
//			for (ApplicationDto_New appDto : lstAppDto) {
//				appDto.setReflectPerState(this.findStatusAppv(lstStatusApproval, appDto.getApplicationID()));
//			}
//			for (AppMasterInfo master : lstAppData.getDataMaster().getLstAppMasterInfo()) {
//				master.setStatusFrameAtr(this.findStatusFrame(lstAppData.getLstFramStatus(), master.getAppID()));
//				master.setPhaseStatus(this.findStatusPhase(lstAppData.getLstPhaseStatus(), master.getAppID()));
//				master.setCheckTimecolor(this.findColorAtr(lstAppData.getLstTimeColor(), master.getAppID()));
//			}
//		}
//		List<AppInfor> lstAppType = this.findListApp(lstAppData.getDataMaster().getLstAppMasterInfo(), param.isSpr(), param.getExtractCondition(), device);
//		List<ApplicationDto_New> lstAppSort = appListExCon.equals(ApplicationListAtr.APPROVER) ?
//				this.sortByIdModeApproval(lstAppDto, lstAppData.getDataMaster().getLstAppMasterInfo()) :
//				this.sortByIdModeApp(lstAppDto, lstAppData.getDataMaster().getMapAppBySCD(), lstAppData.getDataMaster().getLstSCD());
//        List<ApplicationDto_New> lstAppSortConvert = lstAppSort.stream().map(c -> c.convertInputDate(c)).collect(Collectors.toList());
//
//		List<ApplicationDataOutput> lstAppCommon= new ArrayList<>();
//		for(ApplicationDto_New app : lstAppSortConvert){
//			lstAppCommon.add(ApplicationDataOutput.convert(app, appListExCon.getAppListAtr().equals(ApplicationListAtr.APPROVER) ?
//					this.convertStatusAppv(app.getReflectPerState(), device) : this.convertStatus(app.getReflectPerState(), device)));
//		}
//		List<AppAbsRecSyncData> lstSyncData = new ArrayList<>();
//		for(AppCompltLeaveSync sync: lstAppData.getLstAppCompltLeaveSync()){
//			if(sync.isSync()){
//				lstSyncData.add(new AppAbsRecSyncData(sync.getTypeApp(), sync.getAppMain().getAppID(), sync.getAppSub().getAppID(), sync.getAppDateSub()));
//			}
//		}
//		Collections.sort(lstAppData.getDataMaster().getLstSCD());
//		return new ApplicationListDto(isDisPreP, condition.getStartDate(), condition.getEndDate(),
//				lstAppData.getDataMaster().getLstAppMasterInfo(), lstAppCommon, lstAppData.getAppStatusCount(), lstAgent, lstAppType,
//				lstAppData.getLstContentApp(), lstSyncData, lstAppData.getDataMaster().getLstSCD(), appAllNumber, appPerNumber);
	}

	/**
	 * find status approval
	 * @param lstStatusApproval
	 * @param appID
	 * @return
	 */
	private Integer findStatusAppv(List<AppStatusApproval> lstStatusApproval, String appID){
		for (AppStatusApproval appStatus : lstStatusApproval) {
			if(appStatus.getAppId().equals(appID)){
				return appStatus.getStatusApproval();
			}
		}
		return null;
	}
	/**
	 * find status frame
	 * @param lstFramStatus
	 * @param appID
	 * @return
	 */
	private boolean findStatusFrame(List<String> lstFramStatus, String appID){
		for (String id : lstFramStatus) {
			if(id.equals(appID)){
				return true;
			}
		}
		return false;
	}
	/**
	 * find status phase by appId
	 * @param phaseState
	 * @param appId
	 * @return
	 */
	private String findStatusPhase(List<PhaseStatus> phaseState, String appId){
		for (PhaseStatus phaseStatus : phaseState) {
			if(phaseStatus.getAppID().equals(appId)){
				return phaseStatus.getPhaseStatus();
			}
		}
		return null;
	}
	/**
	 * find color by appId
	 * @param lstTimeColor
	 * @param appId
	 * @return
	 */
	private int findColorAtr(List<CheckColorTime> lstTimeColor, String appId){
		for (CheckColorTime checkColorTime : lstTimeColor) {
			if(checkColorTime.getAppID().equals(appId)){
				return checkColorTime.getColorAtr();
			}
		}
		return 0;
	}
	/**
	 * find list appType
	 * @param lstApp
	 * @return
	 */
	private List<AppInfor> findListApp(List<AppMasterInfo> lstApp, boolean isSpr, int extractCondition, int device){
		List<AppInfor> lstAppType = new ArrayList<>();
		lstAppType.add(new AppInfor(-1, device == MOBILE ? I18NText.getText("CMMS45_13") : I18NText.getText("CMM045_200")));
		for (AppMasterInfo app : lstApp) {
			if(!lstAppType.contains(new AppInfor(app.getAppType(), app.getDispName()))){
				lstAppType.add(new AppInfor(app.getAppType(), app.getDispName()));
			}
		}
		if(isSpr && extractCondition == 1){
			if(!this.findAppTypeOt(lstAppType)){
				String name = repoAppDispName.getDisplay(ApplicationType.OVER_TIME_APPLICATION.value).get().getDispName().v();
				lstAppType.add(new AppInfor(ApplicationType.OVER_TIME_APPLICATION.value, name));
			}
		}
		return lstAppType.stream().sorted((x, y) -> x.getAppType()-y.getAppType()).collect(Collectors.toList());
	}
	private boolean findAppTypeOt(List<AppInfor> lstAppType){
		for (AppInfor appInfor : lstAppType) {
			if(appInfor.getAppType() == 0){
				return true;
			}
		}
		return false;
	}
	/**
     * 申請日付 + 申請種類 + 事前事後区分 + 入力日付（時分秒）
	 * @param lstApp
	 * @return
	 */
	private List<ApplicationDto_New> sortByDateTypePrePost(List<ApplicationDto_New> lstApp){
		return lstApp.stream().sorted((a, b) -> {
			Integer rs = a.getApplicationDate().compareTo(b.getApplicationDate());
			if (rs == 0) {
				Integer rs2 = a.getApplicationType().compareTo(b.getApplicationType());
				if (rs2 == 0) {
                    Integer rs3 = a.getPrePostAtr().compareTo(b.getPrePostAtr());
                    if(rs3 == 0){
                        return a.getInputDate().compareTo(b.getInputDate());
                    }else{
                        return rs3;
                    }
				} else {
					return rs2;
				}
			} else {
				return rs;
			}
		}).collect(Collectors.toList());
	}
	/**
	 * 並び順: 社員コード＋申請日付＋申請種類 + 事前事後区分順でソートする
	 * 2019/06/11　EA3305
	 * @param lstApp
	 * @return
	 */
	private List<ApplicationDto_New> sortByIdModeApp(List<ApplicationDto_New> lstApp, Map<String, List<String>> mapAppBySCD,
			List<String> lstSCD){
		List<ApplicationDto_New> lstResult = new ArrayList<>();
		java.util.Collections.sort(lstSCD);
		for (String sCD : lstSCD) {
            lstResult.addAll(this.sortByDateTypePrePost(this.findBylstID(lstApp, mapAppBySCD.get(sCD))));

		}
		return lstResult;
	}
	private List<ApplicationDto_New> findBylstID(List<ApplicationDto_New> lstApp, List<String> lstAppID){
		List<ApplicationDto_New> lstAppFind = new ArrayList<>();
		for (ApplicationDto_New app : lstApp) {
			if(lstAppID.contains(app.getApplicationID())){
				lstAppFind.add(app);
			}
		}
		return lstAppFind;
	}
	/**
	 * 並び順: 社員コード＋申請日付＋申請種類 + 事前事後区分順でソートする
	 * 2019/06/11　EA3306
	 * @param lstApp
	 * @param lstAppMasterInfo
	 * @return
	 */
	private List<ApplicationDto_New> sortByIdModeApproval(List<ApplicationDto_New> lstApp, List<AppMasterInfo> lstAppMasterInfo){
		List<String> lstSCD = new ArrayList<>();
		for(AppMasterInfo master : lstAppMasterInfo){
			if(!lstSCD.contains(master.getEmpSD())){
				lstSCD.add(master.getEmpSD());
			}
		}
		Collections.sort(lstSCD);
		List<ApplicationDto_New> lstResult = new ArrayList<>();
		for (String sCD : lstSCD) {
			List<String> lstAppID = lstAppMasterInfo.stream().filter(c -> c.getEmpSD().equals(sCD))
					.map(d -> d.getAppID()).collect(Collectors.toList());
            lstResult.addAll(this.sortByDateTypePrePost(this.findBylstID(lstApp, lstAppID)));
		}
		return lstResult;
	}
    /**
     * convert status from number to string
     */
//  ※申請一覧　　申請モード
    private String convertStatus(int status, int device){
        switch (status) {
            case 0:
            	//下書き保存/未反映　=　PC：未（#CMM045_62)、スマホ：未処理（#CMMS45_7）
                return device == MOBILE ? I18NText.getText("CMMS45_7") : I18NText.getText("CMM045_62");//下書き保存/未反映　=　未
            case 1:
            //  反映待ち　＝　PC：承認済み（#CMM045_63)、スマホ：承認済（#CMMS45_8）
                return device == MOBILE ? I18NText.getText("CMMS45_8") : I18NText.getText("CMM045_63");//反映待ち　＝　承認済み
            case 2:
            //  反映済　＝　PC：反映済み（#CMM045_64)、スマホ：反映済（#CMMS45_9）
                return device == MOBILE ? I18NText.getText("CMMS45_9") : I18NText.getText("CMM045_64");//反映済　＝　反映済み
            case 5:
            //  差し戻し　＝　PC：差戻（#CMM045_66)、スマホ：差戻（#CMMS45_36）
                return device == MOBILE ? I18NText.getText("CMMS45_36") : I18NText.getText("CMM045_66");//差し戻し　＝　差戻
            case 6:
            //  否認　=　PC：否（#CMM045_65)、スマホ：否認（#CMMS45_11）
                return device == MOBILE ? I18NText.getText("CMMS45_11") : I18NText.getText("CMM045_65");//否認　=　否
            default:
            //  取消待ち/取消済　＝　PC：取消（#CMM045_67)、スマホ：取消（#CMMS45_10）
                return device == MOBILE ? I18NText.getText("CMMS45_10") : I18NText.getText("CMM045_67");//取消待ち/取消済　＝　取消
        }
    }
    //UNAPPROVED:5
    //APPROVED: 4
    //CANCELED: 3
    //REMAND: 2
    //DENIAL: 1
    //-: 0
    private String convertStatusAppv(int status, int device){
        switch (status) {
            case 1:  //DENIAL: 1
            	//※　PC：#CMM045_65　＝　否 || スマホ：#CMMS45_11　＝　否認
                return device == MOBILE ? I18NText.getText("CMMS45_11") : I18NText.getText("CMM045_65");
            case 2: //REMAND: 2
            	//※　PC：#CMM045_66　＝　差戻 || スマホ：#CMMS45_36　＝　差戻
                return device == MOBILE ? I18NText.getText("CMMS45_36") : I18NText.getText("CMM045_66");
            case 3: //CANCELED: 3
            	//※　PC：#CMM045_67　＝　取消 || スマホ：#CMMS45_10　＝　取消
                return device == MOBILE ? I18NText.getText("CMMS45_10") : I18NText.getText("CMM045_67");
            case 4: //APPROVED: 4
            	//※　PC：#CMM045_63　＝　承認済み || スマホ：#CMMS45_8　＝　承認済
                return device == MOBILE ? I18NText.getText("CMMS45_8") : I18NText.getText("CMM045_63");
            case 5: //UNAPPROVED:5
            	//※　PC：#CMM045_62　＝　未  || スマホ：#CMMS45_7　＝　未処理
                return device == MOBILE ? I18NText.getText("CMMS45_7") : I18NText.getText("CMM045_62");
            default: //-: 0
                return "-";
        }
    }



    public AppListInfo startCMMS45(List<ListOfAppTypesDto> listAppType) {
    	return null;
    }

    @Inject
    private ApplicationRepository repoApplication;

    @Inject
    private RequestSettingRepository repoRequestSet;

//	UKDesign.UniversalK.就業.KAF_申請.CMMS45_申請一覧・承認一覧（スマホ）.A：申請一覧.アルゴリズム.起動時処理
//  アルゴリズム「起動時処理」を実行する
    public ApplicationListDtoMobile getList(List<Integer> listAppType, List<ListOfAppTypesCmd> listOfAppTypes, AppListExtractConditionCmd appListExtractConditionDto){
    	ApplicationListDtoMobile applicationListDto = new ApplicationListDtoMobile();
//    	裏パラメータ取得
    	int device = MOBILE;
		String companyId = AppContexts.user().companyId();
		Integer appAllNumber = null;
		Integer appPerNumber = null;
		if(device == MOBILE){
			//・設定の名前：SMART_PHONE
			//・機能の名前：APP_ALL_NUMBER、APP_PER_NUMBER
			String[] subName = {"APP_ALL_NUMBER","APP_PER_NUMBER"};
			Map<String, Integer> mapParam = repoApplication.getParamCMMS45(companyId, "SMART_PHONE", Arrays.asList(subName));
			if(mapParam.isEmpty()){
				mapParam = repoApplication.getParamCMMS45(AppContexts.user().contractCode()+ "-0000", "SMART_PHONE", Arrays.asList(subName));
			}
			if(!mapParam.isEmpty()){
				appAllNumber = mapParam.get("APP_ALL_NUMBER");
				appPerNumber = mapParam.get("APP_PER_NUMBER");
			}
		}
		// set param
		AppListParamFilter param = new AppListParamFilter();
		param.setDevice(MOBILE);
    	param.setMode(appListExtractConditionDto.getAppListAtr());
    	param.setListOfAppTypes(listOfAppTypes);
    	param.setLstAppType(listAppType);
    	if (appListExtractConditionDto.getPeriodStartDate() != null && appListExtractConditionDto.getPeriodEndDate() != null) {
	    	param.setStartDate(appListExtractConditionDto.getPeriodStartDate());
	    	param.setEndDate(appListExtractConditionDto.getPeriodEndDate());

    	}
    	param.setAppListExtractCondition(appListExtractConditionDto);

//    	申請一覧初期処理
    	AppListInitDto appListInitDto =this.getAppList(param);
    	applicationListDto.setAppListInfoDto(appListInitDto.getAppListInfo());
    	applicationListDto.setAppListExtractConditionDto(appListInitDto.getAppListExtractCondition());
    	applicationListDto.setAppAllNumber(appAllNumber);
    	applicationListDto.setAppPerNumber(appPerNumber);
//    	AppListInfo appListInfo = new AppListInfo();
//    			AppListInfoDto.fromDomain(this.getAppList(param));

//    	・ドメインモデル「申請表示設定」．事前事後区分表示
//    	Optional<RequestSetting> requestSet = repoRequestSet.findByCompany(companyId);
//		Integer isDisPreP = null;
//		if (requestSet.isPresent()) {
//			isDisPreP = requestSet.get().getApplicationSetting().getAppDisplaySetting().getPrePostAtrDisp().value;
//		}

//		applicationListDto.setIsDisPreP(isDisPreP);
//		applicationListDto.setStartDate(appListInfo.getDisplaySet().getStartDateDisp().toString(DATE_FORMAT));
//		applicationListDto.setEndDate(appListInfo.getDisplaySet().getEndDateDisp().toString(DATE_FORMAT));
//		if (!CollectionUtil.isEmpty(appListInfo.getAppLst())) {
//			List<ApplicationDataOutput> lstApp = appListInfo.getAppLst().stream().map(
//					item -> new ApplicationDataOutput(
//							new Long(0),
//							item.getAppID(),
//							item.getPrePostAtr(),
//							item.getInputDate().toString(DATE_FORMAT),
//							item.getOpEntererName().isPresent() ? item.getOpEntererName().get() : null,
//									item.getAppDate().toString(DATE_FORMAT),
//									item.getAppTye().value,
//									item.getApplicantCD(),
//									item.getReflectionStatus(),
//									item.getOpAppStartDate().isPresent() ? item.getOpAppStartDate().get().toString(DATE_FORMAT) : null ,
//											item.getOpAppEndDate().isPresent() ? item.getOpAppEndDate().get().toString(DATE_FORMAT) : null,
//													null)).collect(Collectors.toList());
//			applicationListDto.setLstApp(lstApp);
//
//		}



//		・ドメインモデル「申請表示名」．申請表示名称（List）
//		List<AppDispName> appDispName = repoAppDispName.getAll();
//		if (!CollectionUtil.isEmpty(appDispName)) {
//			List<AppInfor> lstAppInfor = appDispName.stream().map(item -> new AppInfor(item.getAppType().value, item.getDispName().v())).collect(Collectors.toList());
//			applicationListDto.setLstAppInfor(lstAppInfor);
//		}
//		applicationListDto.setLstApp(appListInfo.get);

		return applicationListDto;
    }
    @Inject
    private AppListInitialRepository appListInitialRepository;
//    UKDesign.UniversalK.就業.KAF_申請.CMMS45_申請一覧・承認一覧（スマホ）.A：申請一覧.ユースケース
    public ApplicationListDtoMobile getListFilter(ApplicationListCmdMobile applicationListCmdMobile) {
    	AppListExtractCondition appListExtractCondition = applicationListCmdMobile.getAppListExtractCondition().toDomain();
    	int device = MOBILE;
    	// AppListInfo appListInfo = applicationListCmdMobile.getAppListInfo().toDomain();
    	// change value of appListExtractCondition and appListInfo
    	AppListInfo appListInfo = new AppListInfo();
    	AppListInitOutput appListInitOutput = appListInitialRepository.getApplicationList(appListExtractCondition, device, appListInfo);
    	// set value
    	ApplicationListDtoMobile applicationListDtoMobile = new ApplicationListDtoMobile();
    	applicationListDtoMobile.setAppAllNumber(applicationListCmdMobile.getAppAllNumber());
    	applicationListDtoMobile.setAppPerNumber(applicationListCmdMobile.getAppPerNumber());
    	applicationListDtoMobile.setAppListInfoDto(AppListInfoDto.fromDomain(appListInitOutput.getAppListInfo()));
    	applicationListDtoMobile.setAppListExtractConditionDto(AppListExtractConditionDto.fromDomain(appListInitOutput.getAppListExtractCondition()));
    	return applicationListDtoMobile;
    }



    /**
     * refactor 4
     * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面.アルゴリズム.申請一覧期間検索.申請一覧期間検索
     * @param param
     * @return
     */
    public AppListInitDto findByPeriod(AppListExtractConditionCmd param) {
    	// ドメインモデル「申請一覧抽出条件」を保存する
    	// xử lý trên UI
    	// AppListExtractCondition appListExtractCondition = param.convertDtotoDomain();
    	// アルゴリズム「申請一覧検索前チェック」を実行する - 21
    	// approvalListService.checkBeforeSearch(appListExtractCondition);
    	// アルゴリズム「申請一覧リスト取得申請」を実行する - 2
    	AppListInfo appListInfo = new AppListInfo();
    	String companyID = AppContexts.user().companyId();
    	// ドメインモデル「承認一覧表示設定」を取得する
		Optional<ApprovalListDisplaySetting> opApprovalListDisplaySetting = approvalListDispSetRepository.findByCID(companyID);
		if(opApprovalListDisplaySetting.isPresent()) {
			appListInfo.getDisplaySet().setWorkplaceNameDisp(opApprovalListDisplaySetting.get().getDisplayWorkPlaceName().value);
			appListInfo.getDisplaySet().setAppDateWarningDisp(opApprovalListDisplaySetting.get().getWarningDateDisAtr().v());
		}
    	return AppListInitDto.fromDomain(repoAppListInit.getApplicationList(param.toDomain(), 0, appListInfo));
	}

    /**
     * refactor 4
     * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面.アルゴリズム.申請一覧申請条件指定.申請一覧申請条件指定
     * @param appListExtractCondition
     * @return
     */
    public AppListInitDto findByEmpIDLst(AppListExtractConditionCmd param) {
    	// ドメインモデル「申請一覧抽出条件」を保存する
    	// xử lý trên UI
    	// AppListExtractCondition appListExtractCondition = param.convertDtotoDomain();
    	// アルゴリズム「申請一覧検索前チェック」を実行する - 21
    	// approvalListService.checkBeforeSearch(appListExtractCondition);
    	// アルゴリズム「申請一覧リスト取得申請」を実行する - 2
    	AppListInfo appListInfo = new AppListInfo();
    	String companyID = AppContexts.user().companyId();
    	// ドメインモデル「承認一覧表示設定」を取得する
		Optional<ApprovalListDisplaySetting> opApprovalListDisplaySetting = approvalListDispSetRepository.findByCID(companyID);
		if(opApprovalListDisplaySetting.isPresent()) {
			appListInfo.getDisplaySet().setWorkplaceNameDisp(opApprovalListDisplaySetting.get().getDisplayWorkPlaceName().value);
			appListInfo.getDisplaySet().setAppDateWarningDisp(opApprovalListDisplaySetting.get().getWarningDateDisAtr().v());
		}
    	return AppListInitDto.fromDomain(repoAppListInit.getApplicationList(param.toDomain(), 0, appListInfo));
	}
}

