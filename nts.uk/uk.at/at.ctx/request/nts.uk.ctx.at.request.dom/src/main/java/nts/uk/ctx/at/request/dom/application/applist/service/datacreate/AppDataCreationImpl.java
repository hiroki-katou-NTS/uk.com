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
import nts.uk.ctx.at.request.dom.application.applist.service.AppListInitialRepository;
import nts.uk.ctx.at.request.dom.application.applist.service.ApplicationStatus;
import nts.uk.ctx.at.request.dom.application.applist.service.ListOfAppTypes;
import nts.uk.ctx.at.request.dom.application.applist.service.content.AppContentService;
import nts.uk.ctx.at.request.dom.application.applist.service.content.ArrivedLateLeaveEarlyItemContent;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.ScreenAtr;
import nts.uk.ctx.at.request.dom.application.applist.service.param.AppListInfo;
import nts.uk.ctx.at.request.dom.application.applist.service.param.ListOfApplication;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.ArrivedLateLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.ArrivedLateLeaveEarlyRepository;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateCancelation;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrEarlyAtr;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.TimeReport;
import nts.uk.ctx.at.request.dom.application.stamp.AppRecordImage;
import nts.uk.ctx.at.request.dom.application.stamp.AppRecordImageRepository;
import nts.uk.ctx.at.request.dom.application.stamp.AppStamp;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampRepository;
import nts.uk.ctx.at.request.dom.application.stamp.DestinationTimeApp;
import nts.uk.ctx.at.request.dom.application.stamp.DestinationTimeZoneApp;
import nts.uk.ctx.at.request.dom.application.stamp.StampFrameNo;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.application.stamp.TimeStampAppEnum;
import nts.uk.ctx.at.request.dom.application.stamp.TimeStampAppOther;
import nts.uk.ctx.at.request.dom.application.stamp.TimeZoneStampClassification;
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
	
	@Inject
	private AppListInitialRepository appListInitialRepository;
	
	@Inject
	private AppStampRepository appStampRepository;
	
	@Inject
	private AppRecordImageRepository appRecordImageRepository;
	
	private static final int PC = 0;
	private static final int MOBILE = 1;
	
	@Override
	public String createAppStampData(Application application, DisplayAtr appReasonDisAtr, ScreenAtr screenAtr,
			String companyID, ListOfAppTypes listOfAppTypes) {
		// 申請.打刻申請モード
		if(application.getOpStampRequestMode().get()==StampRequestMode.STAMP_ONLINE_RECORD) {
			// ドメインモデル「レコーダイメージ申請」を取得する
			AppRecordImage appRecordImage = appRecordImageRepository.findByAppID(companyID, application.getAppID()).get();
			// 申請内容＝#CMM045_293＋'　'＋レコーダイメージ申請.打刻区分
			String result = I18NText.getText("CMM045_239") + " " + appRecordImage.getAppStampCombinationAtr().name;
			// レコーダイメージ申請.外出理由がemptyでない場合
			if(appRecordImage.getAppStampGoOutAtr().isPresent()) {
				// 申請内容＋＝#CMM045_230：{0}＝外出理由（Enum）
				result += I18NText.getText("CMM045_230", appRecordImage.getAppStampGoOutAtr().get().name);
			}
			// 申請内容＋＝’　’＋レコーダイメージ申請.申請時刻
			result += " " + appRecordImage.getAttendanceTime().v();
			// アルゴリズム「申請内容の申請理由」を実行する
			String appReasonContent = appContentService.getAppReasonContent(
					appReasonDisAtr, 
					application.getOpAppReason().orElse(null), 
					screenAtr, 
					application.getOpAppStandardReasonCD().orElse(null), 
					application.getAppType(), 
					Optional.empty());
			result += appReasonContent;
			return result;
		}
		// ドメインモデル「打刻申請」を取得する
		AppStamp appStamp = appStampRepository.findByAppID(companyID, application.getAppID()).get();
		List<StampAppOutputTmp> listTmp = new ArrayList<>();
		// 「打刻申請.時刻の取消」よりリストを収集する
//		for(TimeStampApp timeStampApp : appStamp.getListTimeStampApp()) {
//			listTmp.add(new StampAppOutputTmp(
//					0, 
//					false, 
//					timeStampApp.getDestinationTimeApp().getTimeStampAppEnum().value, 
//				 	new StampFrameNo(timeStampApp.getDestinationTimeApp().getEngraveFrameNo()), 
//					Optional.of(timeStampApp.getTimeOfDay()), 
//					timeStampApp.getAppStampGoOutAtr(), 
//					Optional.empty(), 
//					Optional.of(timeStampApp.getTimeOfDay())));
//		} 
		// 「打刻申請.時刻の取消」よりリストを収集する
		for(DestinationTimeApp destinationTimeApp : appStamp.getListDestinationTimeApp()) {
			listTmp.add(new StampAppOutputTmp(
					0, 
					true, 
					destinationTimeApp.getTimeStampAppEnum().value, 
				 	new StampFrameNo(destinationTimeApp.getEngraveFrameNo()), 
					Optional.empty(), 
					Optional.empty(), 
					Optional.empty(), 
					Optional.empty()));
		}
		// 「打刻申請.時間帯」よりリストを収集する
		for(TimeStampAppOther timeStampAppOther : appStamp.getListTimeStampAppOther()) {
			listTmp.add(new StampAppOutputTmp(
					1, 
					false, 
					timeStampAppOther.getDestinationTimeZoneApp().getTimeZoneStampClassification().value, 
				 	new StampFrameNo(timeStampAppOther.getDestinationTimeZoneApp().getEngraveFrameNo()), 
				 	Optional.of(timeStampAppOther.getTimeZone().getStartTime()), 
					Optional.empty(), 
					Optional.empty(), 
					Optional.of(timeStampAppOther.getTimeZone().getEndTime())));
		}
		// 「打刻申請.時間帯の取消」よりリストを収集する
		for(DestinationTimeZoneApp destinationTimeZoneApp : appStamp.getListDestinationTimeZoneApp()) {
			listTmp.add(new StampAppOutputTmp(
					1, 
					true, 
					destinationTimeZoneApp.getTimeZoneStampClassification().value, 
				 	new StampFrameNo(destinationTimeZoneApp.getEngraveFrameNo()), 
				 	Optional.empty(), 
					Optional.empty(), 
					Optional.empty(), 
					Optional.empty()));
		}
		// 「打刻申請出力用Tmp」を並べて項目名をセットする
		listTmp.sort(Comparator.comparing((StampAppOutputTmp x) -> {
			return String.valueOf(x.getTimeItem()) + String.valueOf(x.getStampAtr()) + String.valueOf(x.getStampFrameNo().v());
		}));
		for(StampAppOutputTmp itemTmp : listTmp) {
			if(itemTmp.getTimeItem() == 0 && itemTmp.getStampAtr() == TimeStampAppEnum.ATTEENDENCE_OR_RETIREMENT.value) {
				// 項目名＝#KAF002_65（勤務時間）：枠NO
				itemTmp.setOpItemName(Optional.of(I18NText.getText("KAF002_65")));
			}
			if(itemTmp.getTimeItem() == 0 && itemTmp.getStampAtr() == TimeStampAppEnum.EXTRAORDINARY.value) {
				// 項目名＝#KAF002_66（臨時時間）：枠NO
				itemTmp.setOpItemName(Optional.of(I18NText.getText("KAF002_66")));
			}
			if(itemTmp.getTimeItem() == 0 && itemTmp.getStampAtr() == TimeStampAppEnum.GOOUT_RETURNING.value) {
				// 項目名＝#KAF002_67（外出時間）：枠NO
				// 項目名＋＝#CMM045_230（）：{0}=打刻申請出力用Tmp.外出理由
				itemTmp.setOpItemName(Optional.of(I18NText.getText("KAF002_67") + I18NText.getText("CMM045_230", itemTmp.getOpGoOutReasonAtr().get().name)));
			}
			if(itemTmp.getTimeItem() == 1 && itemTmp.getStampAtr() == TimeZoneStampClassification.BREAK.value) {
				// 項目名＝#KAF002_75（休憩時間）：枠NO
				itemTmp.setOpItemName(Optional.of(I18NText.getText("KAF002_75")));
			}
			if(itemTmp.getTimeItem() == 1 && itemTmp.getStampAtr() == TimeZoneStampClassification.PARENT.value) {
				// 項目名＝#KAF002_68（育児時間）：枠NO
				itemTmp.setOpItemName(Optional.of(I18NText.getText("KAF002_68")));
			}
			if(itemTmp.getTimeItem() == 1 && itemTmp.getStampAtr() == TimeZoneStampClassification.NURSE.value) {
				// 項目名＝#KAF002_69（介護時間）：枠NO
				itemTmp.setOpItemName(Optional.of(I18NText.getText("KAF002_69")));
			}
		}
		// アルゴリズム「申請内容（打刻申請）」を実行する
		return appContentService.getAppStampContent(
				appReasonDisAtr, 
				application.getOpAppReason().orElse(null), 
				screenAtr, 
				listTmp, 
				application.getAppType(), 
				application.getOpAppStandardReasonCD().orElse(null));
	}

	@Override
	public String createArrivedLateLeaveEarlyData(Application application, DisplayAtr appReasonDisAtr, ScreenAtr screenAtr,
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
				screenAtr, 
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
