package nts.uk.ctx.at.request.pubimp.application.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsence;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsenceRepository;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule.ScBasicScheduleAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule.ScBasicScheduleImport;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.request.dom.application.workchange.IAppWorkChangeRepository;
import nts.uk.ctx.at.request.dom.setting.company.displayname.AppDispName;
import nts.uk.ctx.at.request.dom.setting.company.displayname.AppDispNameRepository;
import nts.uk.ctx.at.request.dom.setting.company.displayname.HdAppDispName;
import nts.uk.ctx.at.request.dom.setting.company.displayname.HdAppDispNameRepository;
import nts.uk.ctx.at.request.pub.application.common.AppDispNameExport;
import nts.uk.ctx.at.request.pub.application.common.ApplicationInfoPub;
import nts.uk.ctx.at.request.pub.application.common.ApplicationPeriodInfo;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class ApplicationInfoPubImpl implements ApplicationInfoPub {
	
	@Inject 
	private ApplicationRepository_New applicationRepository;
	
	@Inject
	private HdAppDispNameRepository hdAppDispNameRepository;
	
	@Inject
	private AppAbsenceRepository appAbsenceRepository;
	
	@Inject
	private AppDispNameRepository appDispNameRepository;
	
	@Inject
	private ScBasicScheduleAdapter scBasicScheduleAdapter;
	
	@Inject
	private BasicScheduleService basicScheduleService;
	
	@Inject
	private IAppWorkChangeRepository appWorkChangeRepository;

	@Override
	public List<ApplicationPeriodInfo> getAppInfoByPeriod(List<String> employeeIDLst, DatePeriod period) {
		List<ApplicationPeriodInfo> appPeriodInfoLst = new ArrayList<>();
		AppDispNameExport appDispNameExport = new AppDispNameExport();
		String companyID = AppContexts.user().companyId();
		// ドメインモデル「申請」を取得する
		List<Application_New> appLst = applicationRepository.getApplicationBySIDs(employeeIDLst, period.start(), period.end());
		if(CollectionUtil.isEmpty(appLst)){
			return Collections.emptyList();
		}
		for(Application_New app : appLst){
			// 申請種類をチェックする
			if(app.getAppType() == ApplicationType.ABSENCE_APPLICATION){
				AppAbsence appAbsence = appAbsenceRepository.getAbsenceById(companyID, app.getAppID()).get();
				// ドメインモデル「休暇申請種類表示名」を取得する
				HdAppDispName hdAppDispName = hdAppDispNameRepository.getHdApp(appAbsence.getHolidayAppType().value).get();
				appDispNameExport = new AppDispNameExport(hdAppDispName.getCompanyId(), hdAppDispName.getHdAppType().value, hdAppDispName.getDispName().v());
			} else {
				// ドメインモデル「申請表示名」を取得する
				AppDispName appDispName = appDispNameRepository.getDisplay(app.getAppType().value).get();
				appDispNameExport = new AppDispNameExport(appDispName.getCompanyId(), appDispName.getAppType().value, appDispName.getDispName().v());
			}
			// 申請開始日、申請終了日をチェックする
			if(!app.getStartDate().isPresent() || !app.getEndDate().isPresent()){
				appPeriodInfoLst.add(new ApplicationPeriodInfo(
						app.getEmployeeID(), 
						app.getAppDate(), 
						app.getAppType().value, 
						appDispNameExport, 
						app.getReflectionInformation().getStateReflectionReal().value));
				continue;
			}
			if(app.getStartDate().get().equals(app.getEndDate().get())){
				appPeriodInfoLst.add(new ApplicationPeriodInfo(
						app.getEmployeeID(), 
						app.getAppDate(), 
						app.getAppType().value, 
						appDispNameExport, 
						app.getReflectionInformation().getStateReflectionReal().value));
				continue;
			}
			// 申請開始日から終了日までをループする
			for(GeneralDate loopDate = app.getStartDate().get(); loopDate.beforeOrEquals(app.getEndDate().get()); loopDate = loopDate.addDays(1)){
				if(app.getAppType() != ApplicationType.WORK_CHANGE_APPLICATION){
					appPeriodInfoLst.add(new ApplicationPeriodInfo(
							app.getEmployeeID(), 
							app.getAppDate(), 
							app.getAppType().value, 
							appDispNameExport, 
							app.getReflectionInformation().getStateReflectionReal().value));
					continue;
				} else {
					AppWorkChange appWorkChange = appWorkChangeRepository.getAppworkChangeById(companyID, app.getAppID()).get();
					if(appWorkChange.getExcludeHolidayAtr()==0){
						appPeriodInfoLst.add(new ApplicationPeriodInfo(
								app.getEmployeeID(), 
								loopDate, 
								app.getAppType().value, 
								appDispNameExport, 
								app.getReflectionInformation().getStateReflectionReal().value));
						continue;
					} else {
						// Imported「勤務予定基本情報」を取得する
						Optional<ScBasicScheduleImport> opScBasicScheduleImport = scBasicScheduleAdapter.findByID(app.getEmployeeID(), loopDate);
						if(!opScBasicScheduleImport.isPresent()){
							appPeriodInfoLst.add(new ApplicationPeriodInfo(
									app.getEmployeeID(), 
									loopDate, 
									app.getAppType().value, 
									appDispNameExport, 
									app.getReflectionInformation().getStateReflectionReal().value));
							continue;
						}
						// 1日半日出勤・1日休日系の判定
						WorkStyle workStyle = basicScheduleService.checkWorkDay(opScBasicScheduleImport.get().getWorkTypeCode());
						if(workStyle.value!=0){
							appPeriodInfoLst.add(new ApplicationPeriodInfo(
									app.getEmployeeID(), 
									loopDate, 
									app.getAppType().value, 
									appDispNameExport, 
									app.getReflectionInformation().getStateReflectionReal().value));
						}
					}
				}
			}
		}
		return appPeriodInfoLst;
	}

}
