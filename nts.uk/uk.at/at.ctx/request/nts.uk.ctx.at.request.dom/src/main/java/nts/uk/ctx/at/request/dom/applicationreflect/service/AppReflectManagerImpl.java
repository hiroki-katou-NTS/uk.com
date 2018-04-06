package nts.uk.ctx.at.request.dom.applicationreflect.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsence;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsenceRepository;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWorkRepository;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeRepository;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.request.dom.application.workchange.IAppWorkChangeRepository;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.CommonReflectPara;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.AppDegreeReflectionAtr;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.AppExecutionType;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.AppReflectRecordPara;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.GobackAppRequestPara;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.GobackReflectPara;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.HolidayWorkReflectPara;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.HolidayWorktimeAppRequestPara;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.OvertimeAppParameter;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.OvertimeReflectPara;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.PriorStampRequestAtr;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.ReflectRecordInfor;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.ScheAndRecordSameChangeFlg;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.ScheTimeReflectRequesAtr;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.WorkReflectedStatesInfo;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workschedule.ScheReflectedStatesInfo;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workschedule.WorkScheduleReflectService;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.WorkRecordReflectService;

@Stateless
public class AppReflectManagerImpl implements AppReflectManager {
	@Inject
	private OvertimeRepository overTimeRepo;
	@Inject
	private WorkRecordReflectService workRecordReflect;
	@Inject
	private ApplicationRepository_New appRepo;
	@Inject
	private GoBackDirectlyRepository gobackRepo;
	@Inject
	private AppAbsenceRepository absenceRepo;
	@Inject
	private AppHolidayWorkRepository holidayWorkRepo;
	@Inject
	private IAppWorkChangeRepository workChangeRepo;
	@Inject
	private WorkScheduleReflectService scheReflect;
	@Override
	public void reflectEmployeeOfApp(Application_New appInfor) {
		GobackReflectPara appGobackTmp = null;
		OvertimeReflectPara overTimeTmp = null;
		AppOverTime appOvertimeData = null;
		CommonReflectPara commonReflect = null;
		HolidayWorkReflectPara holidayworkInfor = null;
		// TODO 再実行かどうか判断する (xác nhận xem có thực hiện lại hay k)
		//申請を取得 (lấy đơn)
		if(appInfor.getAppType() == ApplicationType.OVER_TIME_APPLICATION) {
			overTimeTmp = this.getOverTimeReflect(appInfor);
			if(overTimeTmp == null) {
				return;
			}
		} else if (appInfor.getAppType() == ApplicationType.GO_RETURN_DIRECTLY_APPLICATION) {
			appGobackTmp = this.getGobackReflectPara(appInfor);
			if(appGobackTmp == null) {
				return;
			}
		} else if (appInfor.getAppType() == ApplicationType.ABSENCE_APPLICATION) {
			//TODO lam trong lan giao hang tiep theo
			/*absenceInfor = this.getAbsence(appInfor);
			if(absenceInfor == null) {
				return;
			}*/
		} else if (appInfor.getAppType() == ApplicationType.BREAK_TIME_APPLICATION) {
			holidayworkInfor = this.getHolidayWork(appInfor);
			if(holidayworkInfor == null) {
				return;
			}
		} else if (appInfor.getAppType() == ApplicationType.WORK_CHANGE_APPLICATION) {
			commonReflect = this.getWorkChange(appInfor);
			if(commonReflect == null) {
				return;
			}
		}
		//TODO 反映するかどうか判断 (Xác định để phản ánh)
		//TODO 勤務予定へ反映処理	(Xử lý phản ánh đến kế hoạch công việc)
		//勤務実績へ反映処理(xử lý phản ảnh thành tích thực chuyên cần)
		ReflectRecordInfor reflectRecordInfor = new ReflectRecordInfor(AppDegreeReflectionAtr.RECORD, AppExecutionType.EXCECUTION, appInfor);		
		AppReflectRecordPara appPara = new AppReflectRecordPara(reflectRecordInfor, appGobackTmp, overTimeTmp, commonReflect, holidayworkInfor);
		//勤務予定反映
		ScheReflectedStatesInfo scheRelectStates = scheReflect.workscheReflect(appInfor);
		appInfor.getReflectionInformation().setStateReflection(scheRelectStates.getReflectedSate());
		if(scheRelectStates.getNotReflectReson() != null) {
			appInfor.getReflectionInformation().setNotReason(Optional.of(scheRelectStates.getNotReflectReson()));
		}
		
		//勤務実績反映
		WorkReflectedStatesInfo workRecordreflect = workRecordReflect.workRecordreflect(appPara);
		appInfor.getReflectionInformation().setStateReflectionReal(workRecordreflect.getReflectedSate());
		if(workRecordreflect.getNotReflectReson() != null) {
			appInfor.getReflectionInformation().setNotReasonReal(Optional.of(workRecordreflect.getNotReflectReson()));
		}
		
		
		appRepo.updateWithVersion(appInfor);
	}
	
	private CommonReflectPara getWorkChange(Application_New appInfor) {
		CommonReflectPara workchangeInfor = null;
		Optional<AppWorkChange> getAppworkChangeById = workChangeRepo.getAppworkChangeById(appInfor.getCompanyID(), appInfor.getAppID());
		if(!getAppworkChangeById.isPresent()) {
			return workchangeInfor;
		}
		AppWorkChange workChange = getAppworkChangeById.get();
		workchangeInfor = new CommonReflectPara(appInfor.getEmployeeID(), 
				appInfor.getAppDate(),
				ScheAndRecordSameChangeFlg.ALWAY, 
				true, 
				workChange.getWorkTypeCd(), 
				workChange.getWorkTimeCd(), appInfor.getReflectionInformation().getStateReflectionReal(), 
				appInfor.getReflectionInformation().getNotReasonReal().isPresent() ? appInfor.getReflectionInformation().getNotReasonReal().get() : null,
				appInfor.getStartDate().get(),
				appInfor.getEndDate().get());
		
		 
		return workchangeInfor;		
	}
	
	private HolidayWorkReflectPara getHolidayWork(Application_New appInfor) {
		HolidayWorkReflectPara holidayPara = null;
		Optional<AppHolidayWork> getFullAppHolidayWork = holidayWorkRepo.getFullAppHolidayWork(appInfor.getCompanyID(), appInfor.getAppID());
		if(!getFullAppHolidayWork.isPresent()) {
			return holidayPara;
		}
		AppHolidayWork holidayWorkData = getFullAppHolidayWork.get();
		Map<Integer, Integer> mapOvertimeFrame =  new HashMap<>();
		if(!holidayWorkData.getHolidayWorkInputs().isEmpty()) {
			holidayWorkData.getHolidayWorkInputs().stream().forEach(x -> {
				if(x.getAttendanceType() == AttendanceType.BREAKTIME) {
					mapOvertimeFrame.put(x.getFrameNo(), x.getApplicationTime().v());
				}
			});
		}
		HolidayWorktimeAppRequestPara appPara = new HolidayWorktimeAppRequestPara(holidayWorkData.getWorkTypeCode().v(), 
				holidayWorkData.getWorkTimeCode().v(),
				mapOvertimeFrame,
				holidayWorkData.getHolidayShiftNight(),
				appInfor.getReflectionInformation().getStateReflectionReal(), 
				!appInfor.getReflectionInformation().getNotReasonReal().isPresent() ? null : appInfor.getReflectionInformation().getNotReasonReal().get()); 
		holidayPara = new HolidayWorkReflectPara(appInfor.getEmployeeID(), appInfor.getAppDate(), true, ScheAndRecordSameChangeFlg.ALWAY, true, appPara);
		return holidayPara;
		
	}
	
	private CommonReflectPara getAbsence(Application_New appInfor) {
		CommonReflectPara absenceInfor = null;
		Optional<AppAbsence> optAbsence = absenceRepo.getAbsenceByAppId(appInfor.getCompanyID(), appInfor.getAppID());
		if(!optAbsence.isPresent()) {
			return absenceInfor;
		}
		AppAbsence absenceAppData = optAbsence.get();
		absenceInfor = new CommonReflectPara(appInfor.getEmployeeID(),
				appInfor.getAppDate(), 
				ScheAndRecordSameChangeFlg.ALWAY, 
				true, 
				absenceAppData.getWorkTypeCode().v(), 
				"",
				appInfor.getReflectionInformation().getStateReflectionReal(), 
				appInfor.getReflectionInformation().getNotReasonReal().isPresent() ? appInfor.getReflectionInformation().getNotReasonReal().get() : null,
				null,
				null);
		return absenceInfor;
	}
	
	
	private GobackReflectPara getGobackReflectPara(Application_New appInfor) {
		GobackReflectPara appGobackTmp = null;
		Optional<GoBackDirectly> optGobackInfo = gobackRepo.findByApplicationID(appInfor.getCompanyID(), appInfor.getAppID());
		if(!optGobackInfo.isPresent()) {
			return appGobackTmp;
		}
		GoBackDirectly gobackInfo = optGobackInfo.get();
		GobackAppRequestPara gobackReques = new GobackAppRequestPara(gobackInfo.getWorkChangeAtr(), 
				gobackInfo.getSiftCD().v(), 
				gobackInfo.getWorkTypeCD().v(), 
				gobackInfo.getWorkTimeStart1().v(), 
				gobackInfo.getWorkTimeEnd1().v(), 
				gobackInfo.getWorkTimeStart2().v(), 
				gobackInfo.getWorkTimeEnd2().v(), 
				appInfor.getReflectionInformation().getStateReflectionReal(),
				appInfor.getReflectionInformation().getNotReasonReal().isPresent() ? appInfor.getReflectionInformation().getNotReasonReal().get() : null);
		appGobackTmp = new GobackReflectPara(appInfor.getEmployeeID(), appInfor.getAppDate(), true, PriorStampRequestAtr.GOBACKPRIOR,
				ScheAndRecordSameChangeFlg.ALWAY,
				ScheTimeReflectRequesAtr.APPTIME,
				true,
				gobackReques);
		
		return appGobackTmp;
	}
	
	/**
	 * 残業申請
	 * @param appInfor
	 * @return
	 */
	private OvertimeReflectPara getOverTimeReflect(Application_New appInfor) {
		OvertimeReflectPara overTimeTmp = null;
		Optional<AppOverTime> getFullAppOvertime = overTimeRepo.getFullAppOvertime(appInfor.getCompanyID(), appInfor.getAppID());
		if(!getFullAppOvertime.isPresent()) {
			return overTimeTmp;
		}
		AppOverTime appOvertimeData = getFullAppOvertime.get();
		Map<Integer, Integer> mapOvertimeFrame =  new HashMap<>();
		if(!appOvertimeData.getOverTimeInput().isEmpty()) {
			appOvertimeData.getOverTimeInput().stream().forEach(x -> {
				if(x.getAttendanceType() == AttendanceType.NORMALOVERTIME && x.getFrameNo() <= 10) {
					mapOvertimeFrame.put(x.getFrameNo(), x.getApplicationTime().v());
				}
			});
		}
		
		OvertimeAppParameter overtimePara = new OvertimeAppParameter(appInfor.getReflectionInformation().getStateReflectionReal(),
				appInfor.getReflectionInformation().getNotReasonReal().isPresent() ? appInfor.getReflectionInformation().getNotReasonReal().get() : null,
				appOvertimeData.getWorkTypeCode().v(),
				appOvertimeData.getSiftCode().v(),
				appOvertimeData.getWorkClockFrom1(),
				appOvertimeData.getWorkClockTo1(),
				appOvertimeData.getWorkClockFrom2(),
				appOvertimeData.getWorkClockTo2(),
				mapOvertimeFrame, 
				appOvertimeData.getOverTimeShiftNight(),
				appOvertimeData.getFlexExessTime()); 
		overTimeTmp = new OvertimeReflectPara(appInfor.getEmployeeID(), 
				appInfor.getAppDate(), 
				true,
				true,
				true,
				true,
				ScheAndRecordSameChangeFlg.ALWAY,
				true, 
				overtimePara); 
		
		
		return overTimeTmp;
		
	}

}
