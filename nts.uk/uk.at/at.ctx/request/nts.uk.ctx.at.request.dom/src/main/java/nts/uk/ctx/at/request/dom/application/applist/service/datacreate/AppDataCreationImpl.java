package nts.uk.ctx.at.request.dom.application.applist.service.datacreate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.applist.extractcondition.AppListExtractCondition;
import nts.uk.ctx.at.request.dom.application.applist.extractcondition.ApplicationListAtr;
import nts.uk.ctx.at.request.dom.application.applist.service.AppInfoMasterOutput;
import nts.uk.ctx.at.request.dom.application.applist.service.AppListInitialRepository;
import nts.uk.ctx.at.request.dom.application.applist.service.ApplicationStatus;
import nts.uk.ctx.at.request.dom.application.applist.service.content.AppContentService;
import nts.uk.ctx.at.request.dom.application.applist.service.param.AppListInfo;
import nts.uk.ctx.at.request.dom.application.applist.service.param.ListOfApplication;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SyEmployeeImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.approvallistsetting.ApprovalListDispSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.approvallistsetting.ApprovalListDisplaySetting;
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
	private ApprovalListDispSetRepository approvalListDispSetRepository;

	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;
	
	@Inject
	private AppListInitialRepository appListInitialRepository;
	
	@Inject
	private AppContentService appContentService;
	
	private static final int PC = 0;
	private static final int MOBILE = 1;

	@Override
	public AppListInfo createAppLstData(String companyID, List<Application> appLst, DatePeriod period,
			ApplicationListAtr mode, Map<String, List<ApprovalPhaseStateImport_New>> mapApproval, int device,
			AppListExtractCondition appListExtractCondition, AppListInfo appListInfo) {
		// ドメインモデル「承認一覧表示設定」を取得する
		Optional<ApprovalListDisplaySetting> opApprovalListDisplaySetting = approvalListDispSetRepository.findByCID(companyID);
		if(opApprovalListDisplaySetting.isPresent()) {
			appListInfo.getDisplaySet().setWorkplaceNameDisp(opApprovalListDisplaySetting.get().getDisplayWorkPlaceName().value);
		}
		if(device==PC) {
			// ドメインモデル「就業時間帯」を取得
			List<WorkType> workTypeLst = workTypeRepository.findByCompanyId(companyID);
			// ドメインモデル「勤務種類」を取得
			List<WorkTimeSetting> workTimeSettingLst = workTimeSettingRepository.findByCId(companyID);
			// 勤怠名称を取得 ( Lấy tên working time)
		}
		
		Map<String, SyEmployeeImport> mapEmpInfo = new HashMap<>();
		for(Application app : appLst) {
			// 申請一覧リスト取得マスタ情報 ( Thông tin master lấy applicationLisst)
			AppInfoMasterOutput appInfoMasterOutput = appListInitialRepository.getListAppMasterInfo(
					app, 
					period, 
					opApprovalListDisplaySetting.get().getDisplayWorkPlaceName(), 
					mapEmpInfo, 
					device);
			mapEmpInfo.putAll(appInfoMasterOutput.getMapEmpInfo());
			// 各申請データを作成 ( Tạo data tên application)
			ListOfApplication listOfApp = appContentService.createEachAppData(
					app, 
					companyID, 
					Collections.emptyList(), 
					Collections.emptyList(), 
					Collections.emptyList(), 
					mode, 
					opApprovalListDisplaySetting.get(), 
					appInfoMasterOutput.getListOfApplication(), 
					mapApproval, 
					device, 
					appListExtractCondition);
			// 申請内容＝-1(Nội dung đơn xin＝-1 )
			if(listOfApp.getAppContent()=="-1") {
				// パラメータ：申請一覧情報.申請一覧から削除する(xóa từ list đơn xin)
				appListInfo.getAppLst().remove(listOfApp);
			} else {
				// 取得した申請一覧を申請一覧情報．申請リストにセット(Set AppList đã lấy thành AppListInformation.AppList)
				appListInfo.getAppLst().add(listOfApp);
			}
		}
		// アルゴリズム「申請一覧の並び順を変更する」を実行する
		appListInfo = this.changeOrderOfAppLst(appListInfo, appListExtractCondition, device);
		if(mode == ApplicationListAtr.APPROVER && device == PC) {
			// アルゴリズム「申請一覧リスト取得承認件数」を実行する(Thực hiện thuật toán [so luong approve lấy list danh sách đơn xin])
			ApplicationStatus applicationStatus = appListInitialRepository.countAppListApproval(appListInfo.getAppLst(), appListInfo.getNumberOfApp());
			appListInfo.setNumberOfApp(applicationStatus);
		}
		return appListInfo;
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
