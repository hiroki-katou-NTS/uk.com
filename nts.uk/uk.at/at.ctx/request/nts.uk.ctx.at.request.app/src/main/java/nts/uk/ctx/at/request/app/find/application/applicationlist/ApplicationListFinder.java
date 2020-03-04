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

import nts.arc.i18n.I18NText;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto_New;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.applist.extractcondition.AppListExtractCondition;
import nts.uk.ctx.at.request.dom.application.applist.extractcondition.ApplicationListAtr;
import nts.uk.ctx.at.request.dom.application.applist.service.AppCompltLeaveSync;
import nts.uk.ctx.at.request.dom.application.applist.service.AppListInitialRepository;
import nts.uk.ctx.at.request.dom.application.applist.service.AppListOutPut;
import nts.uk.ctx.at.request.dom.application.applist.service.AppMasterInfo;
import nts.uk.ctx.at.request.dom.application.applist.service.ApplicationFullOutput;
import nts.uk.ctx.at.request.dom.application.applist.service.CheckColorTime;
import nts.uk.ctx.at.request.dom.application.applist.service.PhaseStatus;
import nts.uk.ctx.at.request.dom.setting.company.displayname.AppDispNameRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSettingRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.approvallistsetting.ApprovalListDisplaySetting;
import nts.uk.shr.com.context.AppContexts;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 11 - 申請一覧初期処理
 * @author hoatt
 *
 */
@Stateless
public class ApplicationListFinder {

	@Inject
	private AppListInitialRepository repoAppListInit;
	@Inject
	private RequestSettingRepository repoRequestSet;
	@Inject
	private AppDispNameRepository repoAppDispName;
	@Inject
	private ApplicationRepository_New repoApplication;
	private static final int MOBILE = 1; 
	
	public ApplicationListDto getAppList(AppListParamFilter param){
		AppListExtractConditionDto condition = param.getCondition();
		int device = param.getDevice();
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

		//対象申請種類List
		List<Integer> lstType = param.getLstAppType();
		//ドメインモデル「承認一覧表示設定」を取得する-(Lấy domain Approval List display Setting)
		Optional<RequestSetting> requestSet = repoRequestSet.findByCompany(companyId);
		ApprovalListDisplaySetting appDisplaySet = null;
		Integer isDisPreP = null;
		if(requestSet.isPresent()){
			appDisplaySet = requestSet.get().getApprovalListDisplaySetting();
			isDisPreP = requestSet.get().getApplicationSetting().getAppDisplaySetting().getPrePostAtrDisp().value;
		}
		//URパラメータが存在する-(Check param)
		if(StringUtil.isNullOrEmpty(condition.getStartDate(), false) || StringUtil.isNullOrEmpty(condition.getEndDate(), false)){
			//アルゴリズム「申請一覧初期日付期間」を実行する-(Thực hiện thuật toán lấy ngày　－12)
			DatePeriod date = null;
			if(condition.getAppListAtr().equals(ApplicationListAtr.APPROVER.value)){
				date = repoAppListInit.getInitialPeriod(companyId);
			}else{
				date = repoAppListInit.getInitPeriodApp(companyId);
			}
			condition.setStartDate(date.start().toString());
			condition.setEndDate(date.end().toString());
		}
		//ドメインモデル「申請一覧共通設定フォーマット.表の列幅」を取得-(Lấy 表の列幅)//xu ly o ui
		//アルゴリズム「申請一覧リスト取得」を実行する-(Thực hiện thuật toán Application List get): 1-申請一覧リスト取得
		AppListExtractCondition appListExCon = condition.convertDtotoDomain(condition);
		AppListOutPut lstAppData = repoAppListInit.getApplicationList(appListExCon, appDisplaySet, device, lstType);
		
		List<ApplicationDto_New> lstAppDto = new ArrayList<>();
		for (Application_New app : lstAppData.getLstApp()) {
            lstAppDto.add(ApplicationDto_New.fromDomainCMM045(app));
		}
		List<AppStatusApproval> lstStatusApproval = new ArrayList<>();
		List<ApproveAgent> lstAgent = new ArrayList<>();
		if(condition.getAppListAtr() == 1){//mode approval
			List<ApplicationFullOutput> lstFil = lstAppData.getLstAppFull().stream().filter(c -> c.getStatus() != null).collect(Collectors.toList());
			for (ApplicationFullOutput app : lstFil) {
				lstAgent.add(new ApproveAgent(app.getApplication().getAppID(), app.getAgentId()));
				lstStatusApproval.add(new AppStatusApproval(app.getApplication().getAppID(), app.getStatus()));
			}
			for (ApplicationDto_New appDto : lstAppDto) {
				appDto.setReflectPerState(this.findStatusAppv(lstStatusApproval, appDto.getApplicationID()));
			}
			for (AppMasterInfo master : lstAppData.getDataMaster().getLstAppMasterInfo()) {
				master.setStatusFrameAtr(this.findStatusFrame(lstAppData.getLstFramStatus(), master.getAppID()));
				master.setPhaseStatus(this.findStatusPhase(lstAppData.getLstPhaseStatus(), master.getAppID()));
				master.setCheckTimecolor(this.findColorAtr(lstAppData.getLstTimeColor(), master.getAppID()));
			}
		}
		List<AppInfor> lstAppType = this.findListApp(lstAppData.getDataMaster().getLstAppMasterInfo(), param.isSpr(), param.getExtractCondition(), device);
		List<ApplicationDto_New> lstAppSort = appListExCon.equals(ApplicationListAtr.APPROVER) ?
				this.sortByIdModeApproval(lstAppDto, lstAppData.getDataMaster().getLstAppMasterInfo()) : 
				this.sortByIdModeApp(lstAppDto, lstAppData.getDataMaster().getMapAppBySCD(), lstAppData.getDataMaster().getLstSCD());
        List<ApplicationDto_New> lstAppSortConvert = lstAppSort.stream().map(c -> c.convertInputDate(c)).collect(Collectors.toList());

		List<ApplicationDataOutput> lstAppCommon= new ArrayList<>();
		for(ApplicationDto_New app : lstAppSortConvert){
			lstAppCommon.add(ApplicationDataOutput.convert(app, appListExCon.getAppListAtr().equals(ApplicationListAtr.APPROVER) ? 
					this.convertStatusAppv(app.getReflectPerState(), device) : this.convertStatus(app.getReflectPerState(), device)));
		}
		List<AppAbsRecSyncData> lstSyncData = new ArrayList<>();
		for(AppCompltLeaveSync sync: lstAppData.getLstAppCompltLeaveSync()){
			if(sync.isSync()){
				lstSyncData.add(new AppAbsRecSyncData(sync.getTypeApp(), sync.getAppMain().getAppID(), sync.getAppSub().getAppID(), sync.getAppDateSub()));
			}
		}
		Collections.sort(lstAppData.getDataMaster().getLstSCD());
		return new ApplicationListDto(isDisPreP, condition.getStartDate(), condition.getEndDate(),
				lstAppData.getDataMaster().getLstAppMasterInfo(), lstAppCommon, lstAppData.getAppStatusCount(), lstAgent, lstAppType,
				lstAppData.getLstContentApp(), lstSyncData, lstAppData.getDataMaster().getLstSCD(), appAllNumber, appPerNumber);
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
}
