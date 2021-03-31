package nts.uk.ctx.at.request.pubimp.screen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule.ScBasicScheduleAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule.ScBasicScheduleImport;
import nts.uk.ctx.at.request.dom.application.common.service.smartphone.output.DeadlineLimitCurrentMonth;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeAtr;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode_Old;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.service.AppDeadlineSettingGet;
import nts.uk.ctx.at.request.dom.setting.company.displayname.AppDispName;
import nts.uk.ctx.at.request.dom.setting.company.displayname.AppDispNameRepository;
import nts.uk.ctx.at.request.dom.setting.company.displayname.DispName;
import nts.uk.ctx.at.request.pub.screen.AppGroupExport;
import nts.uk.ctx.at.request.pub.screen.AppWithDetailExport;
import nts.uk.ctx.at.request.pub.screen.ApplicationDeadlineExport;
import nts.uk.ctx.at.request.pub.screen.ApplicationExport;
import nts.uk.ctx.at.request.pub.screen.ApplicationPub;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.algorithm.SpecHdFrameForWkTypeSetService;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class ApplicationPubImpl implements ApplicationPub {
	
	@Inject
	private ApplicationRepository applicationRepository_New;
	
	@Inject
	private AppDispNameRepository appDispNameRepository;
	
	@Inject
	private ScBasicScheduleAdapter scBasicScheduleAdapter;
	
	@Inject
	public WorkTypeRepository workTypeRepo;
	
	@Inject
	private AppDeadlineSettingGet appDeadlineSettingGet;
	
	@Inject
	private SpecHdFrameForWkTypeSetService specHdFrameForWkTypeSetService;
	
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<ApplicationExport> getApplicationBySID(
			List<String> employeeID,
			GeneralDate startDate,
			GeneralDate endDate) {
    	
		List<ApplicationExport> applicationExports = new ArrayList<>();
		
		// ドメインモデル「申請」を取得する
		List<Application> applications = applicationRepository_New.getApplicationBySIDs(employeeID, startDate, endDate);
		
		if (CollectionUtil.isEmpty(applications)) { // 終了状態：申請取得失敗
			
			return Collections.emptyList();
		}
		
		
		for (Application application : applications) { // 申請の件数分ループ
			// 対象日リストを取得する
			List<GeneralDate> targetList = this.getDateTargets(application);
			
			for (GeneralDate date : targetList) { // 対象日リストのループ
				ApplicationExport applicationExport = ApplicationExport.builder()
						.appDate(date)
						.employeeID(application.getEmployeeID())
						.appType(application.getAppType().value)
						.appTypeName(application.getAppType().name)
						.reflectState(application.getReflectionStatus().getListReflectionStatusOfDay().get(0).getActualReflectStatus().value)
						.prePostAtr(application.getPrePostAtr().value)
						.build();
				
				applicationExports.add(applicationExport);
			}
			
		}
		
		
		
		return applicationExports;
	}

	
	
	private String getAppName(String companyID, List<AppDispName> allApps, ApplicationType appType) {
		return allApps.stream().filter(c -> c.getAppType() == appType).findFirst()
														.orElseGet(() -> new AppDispName(companyID, appType, new DispName("")))
														.getDispName().toString();
	}
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ApplicationDeadlineExport getApplicationDeadline(String companyID, Integer closureID) {
		String employeeID = AppContexts.user().employeeId();
		DeadlineLimitCurrentMonth deadlineLimitCurrentMonth = appDeadlineSettingGet.getApplicationDeadline(companyID, employeeID, closureID);
		ApplicationDeadlineExport applicationDeadlineExport = new ApplicationDeadlineExport();
		applicationDeadlineExport.setUseApplicationDeadline(deadlineLimitCurrentMonth.isUseAtr());
		applicationDeadlineExport.setDateDeadline(deadlineLimitCurrentMonth.getOpAppDeadline().orElse(null));
		return applicationDeadlineExport;
	}
	
	@Override
	public List<AppGroupExport> getApplicationGroupBySID(List<String> employeeID, GeneralDate startDate,
			GeneralDate endDate) {
		List<ApplicationExport> appExportLst = this.getApplicationBySID(employeeID, startDate, endDate);
		List<AppGroupExport> result = new ArrayList<>();
		Map<Object, List<AppGroupExport>> mapDate =  appExportLst.stream()
				.map(x -> new AppGroupExport(x.getAppDate(),x.getAppType(),x.getEmployeeID(),x.getAppTypeName()))
				.collect(Collectors.groupingBy(x -> x.getAppDate()));
		mapDate.entrySet().stream().forEach(x -> {
			Map<Object, List<AppGroupExport>> mapDateType = x.getValue().stream().collect(Collectors.groupingBy(y -> y.getAppType()));
			mapDateType.entrySet().stream().forEach(y -> {
				if(Integer.valueOf(y.getKey().toString())==ApplicationType.ABSENCE_APPLICATION.value){
					Map<Object, List<AppGroupExport>> mapDateTypeAbsence = y.getValue().stream().collect(Collectors.groupingBy(z -> z.getAppTypeName()));
					mapDateTypeAbsence.entrySet().stream().forEach(z -> {
						result.add(z.getValue().get(0));
					});
				} else {
					result.add(y.getValue().get(0));
				}
			});
		});
		return result;
	}
	
	@Override
	public List<AppWithDetailExport> getAppWithOvertimeInfo(String companyID) {
		List<AppWithDetailExport> result = new ArrayList<>();
		// ドメインモデル「申請表示名」を取得する
		List<AppDispName> appDispNameLst = appDispNameRepository.getAll();
		for(AppDispName appDispName : appDispNameLst){
			if(appDispName.getDispName() == null){
				continue;
			}
			if(appDispName.getAppType()==ApplicationType.OVER_TIME_APPLICATION){
				// outputパラメータに残業申請のモード別の値をセットする
				result.add(new AppWithDetailExport(
						ApplicationType.OVER_TIME_APPLICATION.value, 
						appDispName.getDispName().v() + "申請" + " (" + OverTimeAtr.PREOVERTIME.name + ")", 
						OverTimeAtr.PREOVERTIME.value + 1,
						null));
				result.add(new AppWithDetailExport(
						ApplicationType.OVER_TIME_APPLICATION.value, 
						appDispName.getDispName().v() + "申請" + " (" + OverTimeAtr.REGULAROVERTIME.name + ")", 
						OverTimeAtr.REGULAROVERTIME.value + 1,
						null));
				result.add(new AppWithDetailExport(
						ApplicationType.OVER_TIME_APPLICATION.value, 
						appDispName.getDispName().v() + "申請" + " (" + OverTimeAtr.ALL.name + ")", 
						OverTimeAtr.ALL.value + 1,
						null));
			} else if(appDispName.getAppType()==ApplicationType.STAMP_APPLICATION){
				// outputパラメータに打刻申請のモード別の値をセットする
				result.add(new AppWithDetailExport(
						ApplicationType.STAMP_APPLICATION.value, 
						appDispName.getDispName().v() + "申請" + " (" + StampRequestMode_Old.STAMP_GO_OUT_PERMIT.name + ")", 
						null,
						StampRequestMode_Old.STAMP_GO_OUT_PERMIT.value));
				result.add(new AppWithDetailExport(
						ApplicationType.STAMP_APPLICATION.value, 
						appDispName.getDispName().v() + "申請" + " (" + StampRequestMode_Old.STAMP_WORK.name + ")", 
						null,
						StampRequestMode_Old.STAMP_WORK.value));
				result.add(new AppWithDetailExport(
						ApplicationType.STAMP_APPLICATION.value, 
						appDispName.getDispName().v() + "申請" + " (" + StampRequestMode_Old.STAMP_CANCEL.name + ")", 
						null,
						StampRequestMode_Old.STAMP_CANCEL.value));
				result.add(new AppWithDetailExport(
						ApplicationType.STAMP_APPLICATION.value, 
						appDispName.getDispName().v() + "申請" + " (" + StampRequestMode_Old.STAMP_ONLINE_RECORD.name + ")", 
						null,
						StampRequestMode_Old.STAMP_ONLINE_RECORD.value));
				result.add(new AppWithDetailExport(
						ApplicationType.STAMP_APPLICATION.value, 
						appDispName.getDispName().v() + "申請" + " (" + StampRequestMode_Old.OTHER.name + ")", 
						null,
						StampRequestMode_Old.OTHER.value));
			} else {
				// outputパラメータに値をセットする
				result.add(new AppWithDetailExport(appDispName.getAppType().value, appDispName.getDispName().v() + "申請", null, null));
			}
		}
		return result;
	}

	@Override
	public List<ApplicationExport> getAppById(String cid, List<String> lstAppId) {
		List<Application> lstApp = applicationRepository_New.findByListID(cid, lstAppId);
		if (lstApp.isEmpty()) return new ArrayList<>();
		List<AppDispName> allApps = appDispNameRepository.getAll(lstApp.stream().map(c -> c.getAppType().value).distinct().collect(Collectors.toList()));
		return lstApp.stream().map(x -> toAppExportDto(cid, allApps, x, x.getAppDate().getApplicationDate()))
				.collect(Collectors.toList());
	}
	private ApplicationExport toAppExportDto(String companyID, List<AppDispName> allApps, Application app, GeneralDate appDate) {
		
		ApplicationExport appExport = ApplicationExport.builder()
				.appID(app.getAppID())
				.appDate(appDate)
				.appType(app.getAppType().value)
				.employeeID(app.getEmployeeID())
				.reflectState(app.getAppReflectedState().value)
				.appTypeName(getAppName(companyID, allApps, app.getAppType()))
				.prePostAtr(app.getPrePostAtr().value)
				.build();
		
		return appExport;
	}
	/** No.2846
	 * 対象日リストを取得する
	 * @param application
	 * @return
	 */
	public List<GeneralDate> getDateTargets(Application application) {
		List<GeneralDate> output = new ArrayList<>();
		String companyId = AppContexts.user().companyId();
		
		// 申請開始日、申請終了日をチェックする
		if (application.getOpAppStartDate().isPresent() && application.getOpAppEndDate().isPresent()) {
			
			GeneralDate startDate = application.getOpAppStartDate().get().getApplicationDate();
			GeneralDate endDate = application.getOpAppEndDate().get().getApplicationDate();
			
			
			if (startDate.equals(endDate)) { // 申請開始日＝申請終了日の場合
				output.add(application.getAppDate().getApplicationDate());
				
				return output;
			} else { // 申請開始日＜＞申請終了日の場合
				Boolean condition = true;
				
				if (condition) { // (申請種類≠勤務変更申請) OR (申請種類＝勤務変更申請　＆　休日を除外する)の場合
					// 申請開始日から申請終了日までのリストをoutputに追加する
					for(GeneralDate loopDate = startDate;
							loopDate.beforeOrEquals(endDate);
							loopDate = loopDate.addDays(1)) {
						// Imported「勤務予定基本情報」を取得する
						ScBasicScheduleImport scBasicScheduleImport = scBasicScheduleAdapter.findByIDRefactor(application.getEmployeeID(), loopDate);
						// 1日休日の判定
						Boolean isJudgmentHolidayDay = specHdFrameForWkTypeSetService.jubgeHdOneDay(companyId, scBasicScheduleImport.getWorkTypeCode());
						if (!isJudgmentHolidayDay) { // それ以外
							output.add(loopDate);
						}
					}
					
				} else { // 申請種類＝勤務変更申請　＆休日を除外しない
					for(GeneralDate loopDate = startDate;
							loopDate.beforeOrEquals(endDate);
							loopDate = loopDate.addDays(1)) {
						output.add(loopDate);
					}
				}
				
			}
			
						
		}
			
		return output;
		
	}
}
