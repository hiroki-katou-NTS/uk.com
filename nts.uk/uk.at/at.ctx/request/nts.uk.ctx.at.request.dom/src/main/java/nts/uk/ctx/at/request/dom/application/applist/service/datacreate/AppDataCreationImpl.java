package nts.uk.ctx.at.request.dom.application.applist.service.datacreate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.i18n.I18NText;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.applist.extractcondition.AppListExtractCondition;
import nts.uk.ctx.at.request.dom.application.applist.extractcondition.ApplicationListAtr;
import nts.uk.ctx.at.request.dom.application.applist.service.ListOfAppTypes;
import nts.uk.ctx.at.request.dom.application.applist.service.content.AppContentService;
import nts.uk.ctx.at.request.dom.application.applist.service.content.ArrivedLateLeaveEarlyItemContent;
import nts.uk.ctx.at.request.dom.application.applist.service.param.AppListInfo;
import nts.uk.ctx.at.request.dom.application.applist.service.param.AppLstApprovalLstDispSet;
import nts.uk.ctx.at.request.dom.application.applist.service.param.ListOfApplication;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.ArrivedLateLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.ArrivedLateLeaveEarlyRepository;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateCancelation;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrEarlyAtr;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.TimeReport;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.approvallistsetting.ApprovalListDispSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.approvallistsetting.ApprovalListDisplaySetting;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting.DisplayAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class AppDataCreationImpl implements AppDataCreation {
	
	@Inject
	private ArrivedLateLeaveEarlyRepository arrivedLateLeaveEarlyRepository;
	
	@Inject
	private AppContentService appContentService;
	
	@Inject
	private ApprovalListDispSetRepository approvalListDispSetRepository;

	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;
	
	private static final int PC = 0;
	private static final int MOBILE = 1;
	
	@Override
	public void createAppStampData(Application application, DisplayAtr appReasonDisAtr, String screenID,
			String companyID, ListOfAppTypes listOfAppTypes) {
		// TODO Auto-generated method stub
		if(application.getOpStampRequestMode().get()==StampRequestMode.STAMP_ONLINE_RECORD) {
			
		}
	}

	@Override
	public String createArrivedLateLeaveEarlyData(Application application, DisplayAtr appReasonDisAtr, String screenID,
			String companyID) {
		// ドメインモデル「遅刻早退取消申請」
		ArrivedLateLeaveEarly arrivedLateLeaveEarly = arrivedLateLeaveEarlyRepository.getLateEarlyApp(companyID, application.getAppID(), application);
		List<ArrivedLateLeaveEarlyItemContent> itemContentLst = new ArrayList<>();
		// 「遅刻早退取消申請.時刻報告」
		for(TimeReport timeReport : arrivedLateLeaveEarly.getLateOrLeaveEarlies()) {
			String itemName = Strings.EMPTY;
			if(timeReport.getLateOrEarlyClassification() == LateOrEarlyAtr.LATE) {
				itemName = I18NText.getText("CMM045_236");
			} else if(timeReport.getLateOrEarlyClassification() == LateOrEarlyAtr.EARLY) {
				itemName = I18NText.getText("CMM045_238");
			}
			itemContentLst.add(new ArrivedLateLeaveEarlyItemContent(
					itemName, 
					timeReport.getWorkNo(), 
					timeReport.getLateOrEarlyClassification(), 
					Optional.of(timeReport.getTimeWithDayAttr()), 
					false));
		}
		// 「遅刻早退取消申請.取消」
		for(LateCancelation lateCancelation : arrivedLateLeaveEarly.getLateCancelation()) {
			String itemName = Strings.EMPTY;
			if(lateCancelation.getLateOrEarlyClassification() == LateOrEarlyAtr.LATE) {
				itemName = I18NText.getText("CMM045_236");
			} else if(lateCancelation.getLateOrEarlyClassification() == LateOrEarlyAtr.EARLY) {
				itemName = I18NText.getText("CMM045_238");
			}
			itemContentLst.add(new ArrivedLateLeaveEarlyItemContent(
					itemName, 
					lateCancelation.getWorkNo(), 
					lateCancelation.getLateOrEarlyClassification(), 
					Optional.empty(), 
					true));
		}
		// <List>を勤務NO+区分（遅刻、早退の順）で並べる
		itemContentLst.sort(Comparator.comparing((ArrivedLateLeaveEarlyItemContent x) -> {
			return String.valueOf(x.getWorkNo()) + String.valueOf(x.getLateOrEarlyAtr().value);
		}));
		// アルゴリズム「申請内容（遅刻早退取消）」を実行する
		return appContentService.getArrivedLateLeaveEarlyContent(
				application.getOpAppReason().orElse(null), 
				appReasonDisAtr, 
				screenID, 
				itemContentLst, 
				application.getAppType(), 
				application.getOpAppStandardReasonCD().orElse(null));
	}

	@Override
	public ListOfApplication createAppLstData(String companyID, List<Application> appLst, DatePeriod period,
			boolean mode, Map<String, List<ApprovalPhaseStateImport_New>> mapApproval, int device,
			AppListExtractCondition appListExtractCondition) {
		AppListInfo appListInfo = new AppListInfo();
		AppLstApprovalLstDispSet appLstApprovalLstDispSet = new AppLstApprovalLstDispSet();
		// ドメインモデル「承認一覧表示設定」を取得する
		Optional<ApprovalListDisplaySetting> opApprovalListDisplaySetting = approvalListDispSetRepository.findByCID(companyID);
		if(opApprovalListDisplaySetting.isPresent()) {
			appLstApprovalLstDispSet.setWorkplaceNameDisp(opApprovalListDisplaySetting.get().getDisplayWorkPlaceName().value);
		}
		if(device==PC) {
			// ドメインモデル「就業時間帯」を取得
			List<WorkType> workTypeLst = workTypeRepository.findByCompanyId(companyID);
			// ドメインモデル「勤務種類」を取得
			List<WorkTimeSetting> workTimeSettingLst = workTimeSettingRepository.findByCId(companyID);
			// 勤怠名称を取得 ( Lấy tên working time)
		}
		
		for(Application app : appLst) {
			// 申請一覧リスト取得マスタ情報 ( Thông tin master lấy applicationLisst)
			
			// 各申請データを作成 ( Tạo data tên application)
			ListOfApplication listOfApp = null;
			// 
			if(listOfApp.getAppContent()=="-1") {
				
			} else {
				// appListInfo.setAppLst(listOfApp);
			}
		}
		
		
		
		
		return null;
	}

	@Override
	public AppListInfo changeOrderOfAppLst(AppListInfo appListInfo, AppListExtractCondition appListExtractCondition, int device) {
		// 申請一覧抽出条件.申請表示順
		switch (appListExtractCondition.getAppDisplayOrder()) {
		// 申請日順で並び替える
		case APP_DATE_ORDER:
			appListInfo.getAppLst().sort(Comparator.comparing((ListOfApplication x) -> {
				return x.getAppDate().toString() + 
						x.getAppTye().value + 
						x.getOpAppTypeDisplay().map(y -> y.value).orElse(null) + 
						x.getApplicantCD() + 
						x.getPrePostAtr() + 
						x.getInputDate().toString();
			}));
			break;
		// 入力日付順で並び替える
		case INPUT_DATE_ORDER:
			appListInfo.getAppLst().sort(Comparator.comparing(x -> {
				return x.getInputDate().toString() +
						x.getApplicantCD() + 
						x.getAppTye().value + 
						x.getOpAppTypeDisplay() + 
						x.getAppDate().toString() + 
						x.getPrePostAtr();
			}));
			break;
		// 社員コード順で並び替える
		default:
			appListInfo.getAppLst().sort(Comparator.comparing(x -> {
				return x.getApplicantCD() + 
						x.getAppDate().toString() + 
						x.getAppTye().value + 
						x.getOpAppTypeDisplay() + 
						x.getPrePostAtr() + 
						x.getInputDate().toString();
			}));
			break;
		}
		// デバイス
		if(device == PC) {
			// 申請が501件以上存在するかを確認する
			if(appListInfo.getAppLst().size() > 500) {
				// 申請一覧情報.表示行数超にTrueをセットし、申請一覧を先頭から５００行を残して削除する
				appListInfo.setMoreThanDispLineNO(true);
				appListInfo.setAppLst(appListInfo.getAppLst().subList(0, 500));
			} else {
				// 申請一覧情報.表示行数超にFalseをセット
				appListInfo.setMoreThanDispLineNO(false);
			}
		} else {
			// 申請一覧抽出条件.申請一覧区分＝承認
			if(appListExtractCondition.getAppListAtr() == ApplicationListAtr.APPROVER) {
				// 同じ申請者ID２０２件以上となる「申請一覧」は削除
				List<ListOfApplication> groupLst = new ArrayList<>();
				appListInfo.getAppLst().stream().collect(Collectors.groupingBy(ListOfApplication::getApplicantCD)).entrySet().stream()
				.forEach(x -> {
					List<ListOfApplication> appLstByEmp = x.getValue();
					if(appLstByEmp.size() > 200) {
						groupLst.addAll(appLstByEmp.subList(0, 201));
					} else {
						groupLst.addAll(appLstByEmp);
					}
				});
				appListInfo.setAppLst(groupLst);
			}
			// 全件５０２件以上となる「申請一覧」は削除
			appListInfo.setAppLst(appListInfo.getAppLst().subList(0, 501));
		}
		return appListInfo;
	}

}
