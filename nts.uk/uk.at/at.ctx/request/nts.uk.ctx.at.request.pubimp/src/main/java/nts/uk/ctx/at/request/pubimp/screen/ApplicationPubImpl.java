package nts.uk.ctx.at.request.pubimp.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsence;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsenceRepository;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule.ScBasicScheduleAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule.ScBasicScheduleImport_Old;
import nts.uk.ctx.at.request.dom.application.common.service.smartphone.output.DeadlineLimitCurrentMonth;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeAtr;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode_Old;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange_Old;
import nts.uk.ctx.at.request.dom.application.workchange.IAppWorkChangeRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.service.AppDeadlineSettingGet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HdAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HdAppSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.displayname.AppDispName;
import nts.uk.ctx.at.request.dom.setting.company.displayname.AppDispNameRepository;
import nts.uk.ctx.at.request.dom.setting.company.displayname.DispName;
import nts.uk.ctx.at.request.pub.screen.AppGroupExport;
import nts.uk.ctx.at.request.pub.screen.AppWithDetailExport;
import nts.uk.ctx.at.request.pub.screen.ApplicationDeadlineExport;
import nts.uk.ctx.at.request.pub.screen.ApplicationExport;
import nts.uk.ctx.at.request.pub.screen.ApplicationPub;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.service.JudgmentOneDayHoliday;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class ApplicationPubImpl implements ApplicationPub {
	@Inject
	private ApplicationRepository applicationRepository_New;
	@Inject
	private AppDispNameRepository appDispNameRepository;
	@Inject
	private AppAbsenceRepository appAbsenceRepository;
	@Inject
	private HdAppSetRepository hdAppSetRepository;
	@Inject
	private IAppWorkChangeRepository appWorkChangeRepository;
	@Inject
	private ScBasicScheduleAdapter scBasicScheduleAdapter;
	
	@Inject
	public WorkTypeRepository workTypeRepo;
	
	@Inject
	private JudgmentOneDayHoliday judgmentOneDayHoliday;
	
	@Inject
	private AppDeadlineSettingGet appDeadlineSettingGet;
	
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<ApplicationExport> getApplicationBySID(List<String> employeeID, GeneralDate startDate,
			GeneralDate endDate) {
		String companyID = AppContexts.user().companyId();
		List<ApplicationExport> applicationExports = new ArrayList<>();
		List<Application> application = this.applicationRepository_New.getApplicationBySIDs(employeeID, startDate, endDate);
		if(CollectionUtil.isEmpty(application)){
			return applicationExports;
		}
		List<Application> applicationExcessHoliday = application.stream()
				.filter(x -> x.getAppType().value != ApplicationType.ABSENCE_APPLICATION.value &&
							x.getAppType().value != ApplicationType.WORK_CHANGE_APPLICATION.value)
				.collect(Collectors.toList());
		List<AppDispName> allApps = appDispNameRepository.getAll(application.stream().map(c -> c.getAppType().value).distinct().collect(Collectors.toList()));
		if(!applicationExcessHoliday.isEmpty()){
			List<ScBasicScheduleImport_Old> basicSchedules = new ArrayList<>();
			GeneralDate minD = applicationExcessHoliday.stream().map(c -> c.getOpAppStartDate().orElse(null)).filter(c -> c.getApplicationDate() != null)
					.min((c1, c2) -> c1.getApplicationDate().compareTo(c2.getApplicationDate())).get().getApplicationDate();
			GeneralDate maxD = applicationExcessHoliday.stream().map(c -> c.getOpAppEndDate().orElse(null)).filter(c -> c.getApplicationDate() != null)
					.max((c1, c2) -> c1.getApplicationDate().compareTo(c2.getApplicationDate())).get().getApplicationDate();
			if(minD != null && maxD != null){
				basicSchedules.addAll(scBasicScheduleAdapter.findByID(applicationExcessHoliday.stream().map(c -> c.getEmployeeID()).distinct().collect(Collectors.toList()), new DatePeriod(minD, maxD)));
			}
			for(Application app : applicationExcessHoliday){
				if((!(app.getOpAppStartDate().isPresent()&&app.getOpAppEndDate().isPresent())) || 
						app.getOpAppStartDate().get().getApplicationDate().equals(app.getOpAppEndDate().get().getApplicationDate())){
					ApplicationExport applicationExport = new ApplicationExport();
					applicationExport.setAppDate(app.getAppDate().getApplicationDate());
					applicationExport.setAppType(app.getAppType().value);
					applicationExport.setEmployeeID(app.getEmployeeID());
					applicationExport.setReflectState(app.getAppReflectedState().value);
					applicationExport.setAppTypeName(getAppName(companyID, allApps, app.getAppType()));
					applicationExports.add(applicationExport);
				} else {
					for(GeneralDate loopDate = app.getOpAppStartDate().get().getApplicationDate(); loopDate.beforeOrEquals(app.getOpAppEndDate().get().getApplicationDate()); loopDate = loopDate.addDays(1)){
						// Imported「勤務予定基本情報」を取得する
						Optional<ScBasicScheduleImport_Old> opScBasicScheduleImport = findBasicSchedule(basicSchedules, app.getEmployeeID(), loopDate);
						if(!opScBasicScheduleImport.isPresent()){
							ApplicationExport applicationExport = new ApplicationExport();
							applicationExport.setAppDate(loopDate);
							applicationExport.setAppType(app.getAppType().value);
							applicationExport.setEmployeeID(app.getEmployeeID());
							applicationExport.setReflectState(app.getAppReflectedState().value);
							applicationExport.setAppTypeName(getAppName(companyID, allApps, app.getAppType()));
							applicationExports.add(applicationExport);
							continue;
						}
						// 1日休日の判定
						boolean judgment = judgmentOneDayHoliday.judgmentOneDayHoliday(companyID, opScBasicScheduleImport.get().getWorkTypeCode());
						if(!judgment){
							ApplicationExport applicationExport = new ApplicationExport();
							applicationExport.setAppDate(loopDate);
							applicationExport.setAppType(app.getAppType().value);
							applicationExport.setEmployeeID(app.getEmployeeID());
							applicationExport.setReflectState(app.getAppReflectedState().value);
							applicationExport.setAppTypeName(getAppName(companyID, allApps, app.getAppType()));
							applicationExports.add(applicationExport);
						}
					}
				}
			}
		}
		List<Application> applicationHoliday = application.stream().filter(x -> x.getAppType().value == ApplicationType.ABSENCE_APPLICATION.value).collect(Collectors.toList());
		if(!applicationHoliday.isEmpty()){
			Optional<HdAppSet> hdAppSet = this.hdAppSetRepository.getAll();
			List<AppAbsence> apps = appAbsenceRepository.getAbsenceByIds(companyID, applicationHoliday.stream().map(c -> c.getAppID()).distinct().collect(Collectors.toList()));
			List<ScBasicScheduleImport_Old> basicSchedules = new ArrayList<>();
			GeneralDate minD = applicationHoliday.stream().map(c -> c.getOpAppStartDate().orElse(null)).filter(c -> c.getApplicationDate() != null)
					.min((c1, c2) -> c1.getApplicationDate().compareTo(c2.getApplicationDate())).get().getApplicationDate();
			GeneralDate maxD = applicationHoliday.stream().map(c -> c.getOpAppEndDate().orElse(null)).filter(c -> c.getApplicationDate() != null)
					.max((c1, c2) -> c1.getApplicationDate().compareTo(c2.getApplicationDate())).get().getApplicationDate();
			if(minD != null && maxD != null){
				basicSchedules.addAll(scBasicScheduleAdapter.findByID(applicationHoliday.stream().map(c -> c.getEmployeeID()).distinct().collect(Collectors.toList()), new DatePeriod(minD, maxD)));
			}
			for(Application app : applicationHoliday){
				if((!(app.getOpAppStartDate().isPresent()&&app.getOpAppEndDate().isPresent())) || 
						app.getOpAppStartDate().get().getApplicationDate().equals(app.getOpAppEndDate().get().getApplicationDate())){
					Optional<AppAbsence> optAppAbsence = apps.stream().filter(c -> c.getAppID().equals(app.getAppID())).findFirst();
					ApplicationExport applicationExport = new ApplicationExport();
					applicationExport.setAppDate(app.getAppDate().getApplicationDate());
					applicationExport.setAppType(app.getAppType().value);
					applicationExport.setEmployeeID(app.getEmployeeID());
					applicationExport.setReflectState(app.getAppReflectedState().value);
					// ドメインモデル「休暇申請種類表示名」を取得する
					applicationExport.setAppTypeName(this.getAppAbsenceName(optAppAbsence.get().getHolidayAppType().value, hdAppSet));
					applicationExports.add(applicationExport);
				} else {
					for(GeneralDate loopDate = app.getOpAppStartDate().get().getApplicationDate(); loopDate.beforeOrEquals(app.getOpAppEndDate().get().getApplicationDate()); loopDate = loopDate.addDays(1)){
						// Imported「勤務予定基本情報」を取得する
						Optional<ScBasicScheduleImport_Old> opScBasicScheduleImport = findBasicSchedule(basicSchedules, app.getEmployeeID(), loopDate);
						if(!opScBasicScheduleImport.isPresent()){
							ApplicationExport applicationExport = new ApplicationExport();
							applicationExport.setAppDate(loopDate);
							applicationExport.setAppType(app.getAppType().value);
							applicationExport.setEmployeeID(app.getEmployeeID());
							applicationExport.setReflectState(app.getAppReflectedState().value);
							applicationExport.setAppTypeName(getAppName(companyID, allApps, app.getAppType()));
							applicationExports.add(applicationExport);
							continue;
						}
						// 1日休日の判定
						boolean judgment = judgmentOneDayHoliday.judgmentOneDayHoliday(companyID, opScBasicScheduleImport.get().getWorkTypeCode());
						if(!judgment){
							Optional<AppAbsence> optAppAbsence = apps.stream().filter(c -> c.getAppID().equals(app.getAppID())).findFirst();
							ApplicationExport applicationExport = new ApplicationExport();
							applicationExport.setAppDate(loopDate);
							applicationExport.setAppType(app.getAppType().value);
							applicationExport.setEmployeeID(app.getEmployeeID());
							applicationExport.setReflectState(app.getAppReflectedState().value);
							// ドメインモデル「休暇申請種類表示名」を取得する
							applicationExport.setAppTypeName(this.getAppAbsenceName(optAppAbsence.get().getHolidayAppType().value, hdAppSet));
							applicationExports.add(applicationExport);
						}
					}
				}
			}
		}
		
		List<Application> appWorkChangeLst = application.stream().filter(x -> x.getAppType().value == ApplicationType.WORK_CHANGE_APPLICATION.value).collect(Collectors.toList());
		if(!appWorkChangeLst.isEmpty()){
			List<AppWorkChange_Old> appWorkChanges = new ArrayList<>();
			List<ScBasicScheduleImport_Old> basicSchedules = new ArrayList<>();
			List<WorkType> workTypes = new ArrayList<>();
			GeneralDate minD = appWorkChangeLst.stream().map(c -> c.getOpAppStartDate().orElse(null)).filter(c -> c.getApplicationDate() != null)
					.min((c1, c2) -> c1.getApplicationDate().compareTo(c2.getApplicationDate())).get().getApplicationDate();
			GeneralDate maxD = appWorkChangeLst.stream().map(c -> c.getOpAppEndDate().orElse(null)).filter(c -> c.getApplicationDate() != null)
					.max((c1, c2) -> c1.getApplicationDate().compareTo(c2.getApplicationDate())).get().getApplicationDate();
			if(minD != null && maxD != null){
				appWorkChanges.addAll(appWorkChangeRepository.getListAppWorkChangeByID(companyID, appWorkChangeLst.stream().map(c -> c.getAppID()).distinct().collect(Collectors.toList())));
				basicSchedules.addAll(scBasicScheduleAdapter.findByID(appWorkChangeLst.stream().map(c -> c.getEmployeeID()).distinct().collect(Collectors.toList()), new DatePeriod(minD, maxD)));
				workTypes.addAll(workTypeRepo.getPossibleWorkTypeV2(companyID, basicSchedules.stream().map(c -> c.getWorkTypeCode()).distinct().collect(Collectors.toList())));
			}
			for(Application app : appWorkChangeLst){
				if((!(app.getOpAppStartDate().isPresent()&&app.getOpAppEndDate().isPresent())) || 
						app.getOpAppStartDate().get().getApplicationDate().equals(app.getOpAppEndDate().get().getApplicationDate())){
					ApplicationExport applicationExport = new ApplicationExport();
					applicationExport.setAppDate(app.getAppDate().getApplicationDate());
					applicationExport.setAppType(app.getAppType().value);
					applicationExport.setEmployeeID(app.getEmployeeID());
					applicationExport.setReflectState(app.getAppReflectedState().value);
					applicationExport.setAppTypeName(getAppName(companyID, allApps, app.getAppType()));
					applicationExports.add(applicationExport);
				} else {
					// 申請種類＝勤務変更申請　＆　休日を除外するの場合
					AppWorkChange_Old appWorkChange = appWorkChanges.stream().filter(c -> c.getAppId().equals(app.getAppID())).findFirst().orElse(null);
					for(GeneralDate loopDate = app.getOpAppStartDate().get().getApplicationDate(); loopDate.beforeOrEquals(app.getOpAppEndDate().get().getApplicationDate()); loopDate = loopDate.addDays(1)){
						if(appWorkChange != null){
							if (appWorkChange.getExcludeHolidayAtr()==0) {
								ApplicationExport applicationExport = new ApplicationExport();
								applicationExport.setAppDate(loopDate);
								applicationExport.setAppType(app.getAppType().value);
								applicationExport.setEmployeeID(app.getEmployeeID());
								applicationExport.setReflectState(app.getAppReflectedState().value);
								applicationExport.setAppTypeName(getAppName(companyID, allApps, app.getAppType()));
								applicationExports.add(applicationExport);								
							}
						} else {
							// Imported「勤務予定基本情報」を取得する
							Optional<ScBasicScheduleImport_Old> opScBasicScheduleImport = findBasicSchedule(basicSchedules, app.getEmployeeID(), loopDate);
							if(!opScBasicScheduleImport.isPresent()){
								ApplicationExport applicationExport = new ApplicationExport();
								applicationExport.setAppDate(loopDate);
								applicationExport.setAppType(app.getAppType().value);
								applicationExport.setEmployeeID(app.getEmployeeID());
								applicationExport.setReflectState(app.getAppReflectedState().value);
								applicationExport.setAppTypeName(getAppName(companyID, allApps, app.getAppType()));
								applicationExports.add(applicationExport);
								continue;
							}
							// 1日休日の判定
							boolean judgment = judgmentOneDayHoliday.judgmentOneDayHoliday(companyID, opScBasicScheduleImport.get().getWorkTypeCode());
							if(!judgment){
								ApplicationExport applicationExport = new ApplicationExport();
								applicationExport.setAppDate(loopDate);
								applicationExport.setAppType(app.getAppType().value);
								applicationExport.setEmployeeID(app.getEmployeeID());
								applicationExport.setReflectState(app.getAppReflectedState().value);
								applicationExport.setAppTypeName(getAppName(companyID, allApps, app.getAppType()));
								applicationExports.add(applicationExport);
							}
						}
					}
				}
			}
		}
		
		return applicationExports;
	}

	private Optional<ScBasicScheduleImport_Old> findBasicSchedule(List<ScBasicScheduleImport_Old> basicSchedules, String empId, GeneralDate loopDate) {
		return basicSchedules.stream().filter(c -> c.getEmployeeId().equals(empId) && c.getDate().equals(loopDate)).findFirst();
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
	private String getAppAbsenceName(int holidayCode, Optional<HdAppSet> hdAppSet){
		String holidayAppTypeName ="";
		if(!hdAppSet.isPresent()){
			return holidayAppTypeName;
		}
		switch (holidayCode) {
		case 0:
			holidayAppTypeName = hdAppSet.get().getYearHdName() == null ? "" : hdAppSet.get().getYearHdName().toString();
			break;
		case 1:
			holidayAppTypeName = hdAppSet.get().getObstacleName() == null ? "" : hdAppSet.get().getObstacleName().toString();
			break;
		case 2:
			holidayAppTypeName = hdAppSet.get().getAbsenteeism()== null ? "" : hdAppSet.get().getAbsenteeism().toString();
			break;
		case 3:
			holidayAppTypeName = hdAppSet.get().getSpecialVaca() == null ? "" : hdAppSet.get().getSpecialVaca().toString();
			break;
		case 4:
			holidayAppTypeName = hdAppSet.get().getYearResig() == null ? "" : hdAppSet.get().getYearResig().toString();
			break;
		case 5:
			holidayAppTypeName = hdAppSet.get().getHdName() == null ? "" : hdAppSet.get().getHdName().toString();
			break;
		case 6:
			holidayAppTypeName = hdAppSet.get().getTimeDigest() == null ? "" : hdAppSet.get().getTimeDigest().toString();
			break;
		case 7:
			holidayAppTypeName = hdAppSet.get().getFurikyuName() == null ? "" :  hdAppSet.get().getFurikyuName().toString();
			break;
		default:
			break;
		}
		return holidayAppTypeName;
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

}
