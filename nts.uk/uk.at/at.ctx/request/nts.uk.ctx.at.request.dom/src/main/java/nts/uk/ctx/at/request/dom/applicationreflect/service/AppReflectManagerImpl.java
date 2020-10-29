package nts.uk.ctx.at.request.dom.applicationreflect.service;

/*import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.PriorStampRequestAtr;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.ScheAndRecordSameChangeFlg;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.ScheTimeReflectRequesAtr;*/
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.persistence.exceptions.OptimisticLockException;

import lombok.extern.slf4j.Slf4j;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.error.ThrowableAnalyzer;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsence;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsenceRepository;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveAppRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentAppRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWorkRepository;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime_Old;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeRepository;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChangeRepository;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.AppReflectProcessRecord;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.AppReflectRecordPara;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.CommonReflectPara;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.GobackReflectPara;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.HolidayWorkReflectPara;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.OvertimeReflectPara;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.ScheAndRecordIsReflect;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.WorkRecordReflectService;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.dailymonthlyprocessing.ExecutionLogRequestImport;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.dailymonthlyprocessing.ExecutionTypeExImport;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workschedule.WorkScheduleReflectService;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.require.RemainNumberTempRequireService;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Slf4j
public class AppReflectManagerImpl implements AppReflectManager {
	@Inject
	private OvertimeRepository overTimeRepo;
	@Inject
	private WorkRecordReflectService workRecordReflect;
	@Inject
	private ApplicationRepository appRepo;
	@Inject
	private GoBackDirectlyRepository gobackRepo;
	@Inject
	private AppAbsenceRepository absenceRepo;
	@Inject
	private AppHolidayWorkRepository holidayWorkRepo;
	@Inject
	private AppWorkChangeRepository workChangeRepo;
	@Inject
	private WorkScheduleReflectService scheReflect;
	@Inject
	private AbsenceLeaveAppRepository absenceLeaveRepo;
	@Inject
	private RecruitmentAppRepository recruitmentRepo;
	@Inject
	private InterimRemainDataMngRegisterDateChange interimRegister;
	@Inject
	private AppReflectProcessRecord proRecord;
	@Resource
	private SessionContext scContext;
	private AppReflectManager self;
	@Inject
	private AppReflectProcessRecord checkReflect;
	@Inject
	private OtherCommonAlgorithm otherCommonAlg;
	@Inject
	private RemainNumberTempRequireService requireSerive;
	@Inject
    private ExecutionLogRequestImport executionLogRequestImport;	
	@PostConstruct
	public void postContruct() {
		this.self = scContext.getBusinessObject(AppReflectManager.class);
	}
	
	@Override	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void reflectEmployeeOfApp(Application appInfor, InformationSettingOfEachApp reflectSetting,
			ExecutionTypeExImport execuTionType, String excLogId, int currentRecord) {
		try {
			self.reflectEmployeeOfAppWithTransaction(appInfor, reflectSetting, execuTionType, excLogId);
			
		} catch(Exception ex) {
			boolean isError = new ThrowableAnalyzer(ex).findByClass(OptimisticLockException.class).isPresent();
			if(!isError) {
				log.info(isError + " 反映処理：　申請ID　＝　" + appInfor.getAppID() + " 申請者ID　＝　" + appInfor.getEmployeeID());
				//return khong dung duoc do neu 1 ngay co nhieu don thi no cu tim trang thai don haneimachi lam khong ket thuc duoc vong lap
				throw ex;
			}		
			if(!excLogId.isEmpty()) {
				proRecord.createLogError(appInfor.getEmployeeID(), appInfor.getAppDate().getApplicationDate(), excLogId);	
			}
			int newCountRerun = currentRecord + 1;
			if (newCountRerun == 10) {
				log.info(isError + " 反映処理：　申請ID　＝　" + appInfor.getAppID() + " 申請者ID　＝　" + appInfor.getEmployeeID());
				throw ex;
			}
			try {
				Thread.sleep(newCountRerun * 50);				
			} catch (InterruptedException e){
				throw ex;
			}	
			
			self.reflectEmployeeOfApp(appInfor, reflectSetting, execuTionType, excLogId, newCountRerun);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Transactional
	public void reflectEmployeeOfAppWithTransaction(Application appInfor,
			InformationSettingOfEachApp reflectSetting, ExecutionTypeExImport execuTionType, String excLogId) {
		String companyID = AppContexts.user().companyId();
		GobackReflectPara appGobackTmp = null;
		OvertimeReflectPara overTimeTmp = null;
		WorkChangeCommonReflectPara workchangeData = null;
		HolidayWorkReflectPara holidayworkInfor = null;
		WorkChangeCommonReflectPara absenceData = null;
		CommonReflectPara absenceLeaveAppInfor = null;
		CommonReflectPara recruitmentInfor = null;
		
		// TODO 再実行かどうか判断する (xác nhận xem có thực hiện lại hay k)
		//申請を取得 (lấy đơn)
		switch (appInfor.getAppType()) {
		case OVER_TIME_APPLICATION:
			if(appInfor.getPrePostAtr() != PrePostAtr.PREDICT) {
				return;
			}
			Optional<AppOverTime_Old> getFullAppOvertime = overTimeRepo.getAppOvertimeFrame(companyID, appInfor.getAppID());
			if(!getFullAppOvertime.isPresent()) {
				return;
			}
			AppOverTime_Old appOvertimeInfor = getFullAppOvertime.get();			
			overTimeTmp = this.getOverTimeReflect(appInfor, appOvertimeInfor, reflectSetting, excLogId);
			if(overTimeTmp == null) {
				return;
			}
			break;
		case GO_RETURN_DIRECTLY_APPLICATION:
			Optional<GoBackDirectly> optGobackInfo = gobackRepo.find(companyID, appInfor.getAppID());
			if(!optGobackInfo.isPresent()) {
				return;
			}
			GoBackDirectly gobackInfo = optGobackInfo.get();
			appGobackTmp = this.getGobackReflectPara(appInfor, gobackInfo, reflectSetting, excLogId);
			if(appGobackTmp == null) {
				return;
			}
			break;
		case ABSENCE_APPLICATION:
			Optional<AppAbsence> optAbsence = absenceRepo.getAbsenceById(companyID, appInfor.getAppID());
			if(!optAbsence.isPresent()) {
				return;
			}
			AppAbsence absenceAppData = optAbsence.get();
			absenceData = this.getAbsence(appInfor, absenceAppData, reflectSetting, excLogId);
			if(absenceData == null) {
				return;
			}
			break;
		case HOLIDAY_WORK_APPLICATION:
			Optional<AppHolidayWork> getFullAppHolidayWork = holidayWorkRepo.getAppHolidayWorkFrame(companyID, appInfor.getAppID());
			if(!getFullAppHolidayWork.isPresent()) {
				return;
			}
			AppHolidayWork holidayWorkData = getFullAppHolidayWork.get();
			holidayworkInfor = this.getHolidayWork(appInfor, holidayWorkData, reflectSetting, excLogId);
			if(holidayworkInfor == null) {
				return;
			}
			break;
		case WORK_CHANGE_APPLICATION:
			Optional<AppWorkChange> getAppworkChangeById = workChangeRepo.findbyID(companyID, appInfor.getAppID());
			if(!getAppworkChangeById.isPresent()) {
				return;
			}
			AppWorkChange workChange = getAppworkChangeById.get();
			workchangeData = this.getWorkChange(appInfor, workChange, reflectSetting, excLogId);
			if(workchangeData == null) {
				return;
			}
			break;
		case COMPLEMENT_LEAVE_APPLICATION:
			Optional<AbsenceLeaveApp> optAbsenceLeaveData = absenceLeaveRepo.findByAppId(appInfor.getAppID());
			if(optAbsenceLeaveData.isPresent()) {
				AbsenceLeaveApp absenceLeave = optAbsenceLeaveData.get();
				absenceLeaveAppInfor = this.getAbsenceLeaveAppInfor(appInfor, absenceLeave, reflectSetting, excLogId);
			} 
			
			Optional<RecruitmentApp> optRecruitmentData = recruitmentRepo.findByAppId(appInfor.getAppID());
			if(optRecruitmentData.isPresent()) {
				RecruitmentApp recruitmentData = optRecruitmentData.get();
				recruitmentInfor = this.getRecruitmentInfor(appInfor, recruitmentData, reflectSetting, excLogId);
			}
			break;
		default:
			return;	
		}
		//勤務実績へ反映処理(xử lý phản ảnh thành tích thực chuyên cần)
		
		boolean isSche = true;
		boolean isRecord = true;
		List<GeneralDate> lstDate = new ArrayList<>();
		//社員に対応する締め開始日を取得する
		Optional<GeneralDate> closure = GetClosureStartForEmployee.algorithm(requireSerive.createRequire(), 
				new CacheCarrier(), appInfor.getEmployeeID());
		if(!closure.isPresent()) {
			return;
		}
		GeneralDate closureStartDate = closure.get();
		List<GeneralDate> lstReflectHoliday = new ArrayList<>();
		for(int i = 0; appInfor.getOpAppStartDate().get().getApplicationDate().daysTo(appInfor.getOpAppEndDate().get().getApplicationDate()) - i >= 0; i++){
			GeneralDate loopDate = appInfor.getOpAppStartDate().get().getApplicationDate().addDays(i);
			if(loopDate.before(closureStartDate)) {
				continue;
			}
			//休暇申請の申請日は休日　又は　勤務変更申請の申請日は休日と休日を除外するチェックの場合反映するが残数データを作成してない
			if(appInfor.getAppType() == ApplicationType.ABSENCE_APPLICATION
					|| (appInfor.getAppType() == ApplicationType.WORK_CHANGE_APPLICATION && workchangeData.getExcludeHolidayAtr() == 1)) {
				List<GeneralDate> lstHoliday = otherCommonAlg.lstDateIsHoliday(appInfor.getEmployeeID(), new DatePeriod(loopDate,loopDate), Collections.emptyList());
				lstHoliday.stream().forEach(x -> {
					lstReflectHoliday.add(x);
				});	
			}
			if(workchangeData != null) {
				workchangeData.getCommonPara().setAppDate(loopDate);
			}
			if(absenceData != null) {
				absenceData.getCommonPara().setAppDate(loopDate);
			}
			AppReflectRecordPara appPara = new AppReflectRecordPara(appInfor.getAppType(), 
					appInfor.getPrePostAtr(),
					appGobackTmp,
					overTimeTmp, 
					workchangeData, 
					holidayworkInfor, 
					absenceData,
					absenceLeaveAppInfor,
					recruitmentInfor,
					execuTionType);
            Boolean isCalWhenLock = this.executionLogRequestImport.isCalWhenLock(excLogId, 2);
			//事前チェック処理
            ScheAndRecordIsReflect checkReflectResult = checkReflect.appReflectProcessRecord(appInfor, execuTionType, loopDate,isCalWhenLock);
			//勤務予定へ反映処理	(Xử lý phản ánh đến kế hoạch công việc)
			if(appInfor.getPrePostAtr() == PrePostAtr.PREDICT
					&& checkReflectResult.isScheReflect()) {
				scheReflect.workscheReflect(appPara);
			} else {
				isSche = false;
			}
			//勤務実績に反映
			if(checkReflectResult.isRecordReflect()) {
				workRecordReflect.workRecordreflect(appPara);
				lstDate.add(loopDate);
			} else {
				isRecord = false;
			}
		}
//		if(isSche) {
//			appInfor.getReflectionInformation().setStateReflection(ReflectedState_New.REFLECTED);
//			appInfor.getReflectionInformation().setNotReason(Optional.of(ReasonNotReflect_New.WORK_CONFIRMED));	
//			appInfor.getReflectionInformation().setDateTimeReflection(Optional.of(GeneralDateTime.now()));
//		}
//		if(isRecord) {
//			appInfor.getReflectionInformation().setStateReflectionReal(ReflectedState_New.REFLECTED);
//			appInfor.getReflectionInformation().setNotReasonReal(Optional.of(ReasonNotReflectDaily_New.ACTUAL_CONFIRMED));
//			appInfor.getReflectionInformation().setDateTimeReflectionReal(Optional.of(GeneralDateTime.now()));
//			
//		}		
		appRepo.update(appInfor);		
		//暫定データの登録
		if(!lstDate.isEmpty()) {
			lstReflectHoliday.stream().forEach(x -> {
				lstDate.remove(x);
			});			
			if(!lstDate.isEmpty()) {
				interimRegister.registerDateChange(companyID, appInfor.getEmployeeID(), lstDate);	
			}				
		}
	}	
	private WorkChangeCommonReflectPara getWorkChange(Application appInfor, AppWorkChange workChange,
			InformationSettingOfEachApp reflectSetting, String excLogId) {
//		CommonReflectPara workchangeInfor = null;
//		workchangeInfor = new CommonReflectPara(appInfor.getEmployeeID(), 
//				appInfor.getAppDate().getApplicationDate(), 
//				workChange.getOpWorkTypeCD().map(x -> x.v()).orElse(null), 
//				workChange.getOpWorkTimeCD().map(x -> x.v()).orElse(null), 
//				workChange.getTimeZoneWithWorkNoLst().stream().filter(x -> x.getWorkNo().v()==1).findAny().map(x -> x.getTimeZone().getStartTime().v()).orElse(null),
//				workChange.getTimeZoneWithWorkNoLst().stream().filter(x -> x.getWorkNo().v()==1).findAny().map(x -> x.getTimeZone().getEndTime().v()).orElse(null),
//				excLogId,
//				reflectSetting.getScheAndWorkChange(), 
//				reflectSetting.isJizenScheYusen(),
//				reflectSetting.getIdentityProcessUseSet(),
//				reflectSetting.getApprovalProcessingUseSetting());
//		return new WorkChangeCommonReflectPara(workchangeInfor, workChange.getExcludeHolidayAtr());		
		return null;
	}
	
	private CommonReflectPara getAbsenceLeaveAppInfor(Application appInfor, AbsenceLeaveApp absenceLeaveApp,
			InformationSettingOfEachApp reflectSetting, String excLogId) {
		CommonReflectPara absenceLeave = null;
//		absenceLeave = new CommonReflectPara(appInfor.getEmployeeID(), 
//				appInfor.getAppDate(), 
//				absenceLeaveApp.getWorkTypeCD().v(), 
//				absenceLeaveApp.getWorkTimeCD(), 
//				absenceLeaveApp.getWorkTime1().getStartTime() != null ? absenceLeaveApp.getWorkTime1().getStartTime().v() : null,
//				absenceLeaveApp.getWorkTime1().getEndTime() != null ? absenceLeaveApp.getWorkTime1().getEndTime().v() : null,
//				excLogId, 
//				reflectSetting.getScheAndWorkChange(), 
//				reflectSetting.isJizenScheYusen(),
//				reflectSetting.getIdentityProcessUseSet(),
//				reflectSetting.getApprovalProcessingUseSetting());
		return absenceLeave;
	}
	
	private CommonReflectPara getRecruitmentInfor(Application appInfor, RecruitmentApp recuitmentApp,
			InformationSettingOfEachApp reflectSetting, String excLogId) {
		CommonReflectPara recruitment = null;
//		recruitment = new CommonReflectPara(appInfor.getEmployeeID(),
//				appInfor.getAppDate(),
//				recuitmentApp.getWorkTypeCD().v(), 
//				recuitmentApp.getWorkTimeCD().v(), 
//				recuitmentApp.getWorkTime1().getStartTime() != null ? recuitmentApp.getWorkTime1().getStartTime().v() : null, 
//				recuitmentApp.getWorkTime1().getEndTime() != null ? recuitmentApp.getWorkTime1().getEndTime().v() : null,
//				excLogId,
//				reflectSetting.getScheAndWorkChange(), 
//				reflectSetting.isJizenScheYusen(),
//				reflectSetting.getIdentityProcessUseSet(),
//				reflectSetting.getApprovalProcessingUseSetting());
		return recruitment;
	}
	
	private HolidayWorkReflectPara getHolidayWork(Application appInfor, AppHolidayWork holidayWorkData,
			InformationSettingOfEachApp reflectSetting, String excLogId) {
		HolidayWorkReflectPara holidayPara = null;
//		Map<Integer, Integer> mapOvertimeFrame =  new HashMap<>();
//		Map<Integer, BreakTime> mapBreakTimeFrame =  new HashMap<>();
//		if(!holidayWorkData.getHolidayWorkInputs().isEmpty()) {
//			holidayWorkData.getHolidayWorkInputs().stream().forEach(x -> {
//				if(x.getAttendanceType() == AttendanceType.BREAKTIME) {
//					mapOvertimeFrame.put(x.getFrameNo(), x.getApplicationTime().v());
//				}
//				if(x.getAttendanceType() == AttendanceType.RESTTIME) {
//					BreakTime breakTime = new BreakTime(x.getStartTime().v(), x.getEndTime().v()); 
//					mapBreakTimeFrame.put(x.getFrameNo(), breakTime);
//				}
//			});
//		}
//		HolidayWorktimeAppRequestPara appPara = new HolidayWorktimeAppRequestPara(holidayWorkData.getWorkTypeCode() != null ? holidayWorkData.getWorkTypeCode().v() : null, 
//				holidayWorkData.getWorkTimeCode() != null ? holidayWorkData.getWorkTimeCode().v() : null,
//				mapOvertimeFrame,
//				holidayWorkData.getHolidayShiftNight(),
//				appInfor.getReflectionInformation().getStateReflectionReal(), 
//				!appInfor.getReflectionInformation().getNotReasonReal().isPresent() ? null : appInfor.getReflectionInformation().getNotReasonReal().get(),
//				holidayWorkData.getWorkClock1().getStartTime() == null ? null : holidayWorkData.getWorkClock1().getStartTime().v(),
//				holidayWorkData.getWorkClock1().getEndTime() == null ? null : holidayWorkData.getWorkClock1().getEndTime().v(),
//				mapBreakTimeFrame);
//		holidayPara = new HolidayWorkReflectPara(appInfor.getEmployeeID(),
//				appInfor.getAppDate(),
//				reflectSetting.isHwScheReflectHwTime(),
//				reflectSetting.getScheAndWorkChange(),
//				reflectSetting.isJizenScheYusen(),
//				appPara,
//				reflectSetting.isHwRecordReflectTime(),
//				reflectSetting.isHwRecordReflectBreak(),
//				excLogId,
//				reflectSetting.getIdentityProcessUseSet(),
//				reflectSetting.getApprovalProcessingUseSetting());
		return holidayPara;
		
	}
	
	private WorkChangeCommonReflectPara getAbsence(Application appInfor, AppAbsence absenceAppData,
			InformationSettingOfEachApp reflectSetting, String excLogId) {
		CommonReflectPara absenceInfor = null;
		
//		absenceInfor = new CommonReflectPara(appInfor.getEmployeeID(),
//				appInfor.getAppDate(), 
//				absenceAppData.getWorkTypeCode().v(), 
//				absenceAppData.getWorkTimeCode() == null ? null : absenceAppData.getWorkTimeCode().v(),
//				null,
//				null,
//				excLogId, 
//				reflectSetting.getScheAndWorkChange(), 
//				reflectSetting.isJizenScheYusen(),
//				reflectSetting.getIdentityProcessUseSet(),
//				reflectSetting.getApprovalProcessingUseSetting());
		return new WorkChangeCommonReflectPara(absenceInfor, absenceAppData.isChangeWorkHour() == true ? 1 : 0);
	}
	
	
	private GobackReflectPara getGobackReflectPara(Application appInfor, GoBackDirectly gobackInfo,
			InformationSettingOfEachApp reflectSetting, String excLogId) {
		GobackReflectPara appGobackTmp = null;		
//		GobackAppRequestPara gobackReques = new GobackAppRequestPara(
//				gobackInfo.getWorkChangeAtr().isPresent() ? gobackInfo.getWorkChangeAtr().get() : null, 
//				gobackInfo.getSiftCD().isPresent() ? gobackInfo.getSiftCD().get().v() : null, 
//				gobackInfo.getWorkTypeCD().isPresent() ? gobackInfo.getWorkTypeCD().get().v() : null, 
//				gobackInfo.getWorkTimeStart1().isPresent() ? gobackInfo.getWorkTimeStart1().get().v() : null, 
//				gobackInfo.getWorkTimeEnd1().isPresent() ? gobackInfo.getWorkTimeEnd1().get().v() : null, 
//				gobackInfo.getWorkTimeStart2().isPresent() ? gobackInfo.getWorkTimeStart2().get().v() : null, 
//				gobackInfo.getWorkTimeEnd2().isPresent() ? gobackInfo.getWorkTimeEnd2().get().v() : null,
//				reflectSetting.getIdentityProcessUseSet(),
//				reflectSetting.getApprovalProcessingUseSetting());
//		appGobackTmp = new GobackReflectPara(appInfor.getEmployeeID(),
//				appInfor.getAppDate(),
//				ApplyTimeRequestAtr.START,
//				reflectSetting.isChokochoki(),
//				reflectSetting.getWorkJikokuYusen(),
//				reflectSetting.getScheAndWorkChange(),
//				reflectSetting.getScheJikokuYusen(),
//				reflectSetting.isJizenScheYusen(),
//				excLogId,
//				gobackReques);
		
		return appGobackTmp;
	}
	
	/**
	 * 残業申請
	 * @param appInfor
	 * @return
	 */
	private OvertimeReflectPara getOverTimeReflect(Application appInfor, AppOverTime_Old appOvertimeInfor,
			InformationSettingOfEachApp reflectSetting, String excLogId) {
		OvertimeReflectPara overTimeTmp = null;
//		Map<Integer, Integer> mapOvertimeFrame =  new HashMap<>();
//		if(!appOvertimeInfor.getOverTimeInput().isEmpty()) {
//			appOvertimeInfor.getOverTimeInput().stream().forEach(x -> {
//				if(x.getAttendanceType() == AttendanceType.NORMALOVERTIME && x.getFrameNo() <= 10) {
//					mapOvertimeFrame.put(x.getFrameNo(), x.getApplicationTime().v());
//				}
//			});
//		}
//		
//		OvertimeAppParameter overtimePara = new OvertimeAppParameter(appInfor.getReflectionInformation().getStateReflectionReal(),
//				appInfor.getReflectionInformation().getNotReasonReal().isPresent() ? appInfor.getReflectionInformation().getNotReasonReal().get() : null,
//						appOvertimeInfor.getWorkTypeCode() == null ? null : appOvertimeInfor.getWorkTypeCode().v(),
//						appOvertimeInfor.getSiftCode() == null ? null : appOvertimeInfor.getSiftCode().v(),
//						appOvertimeInfor.getWorkClockFrom1(),
//						appOvertimeInfor.getWorkClockTo1(),
//						appOvertimeInfor.getWorkClockFrom2(),
//						appOvertimeInfor.getWorkClockTo2(),
//						mapOvertimeFrame, 
//						appOvertimeInfor.getOverTimeShiftNight(),
//						appOvertimeInfor.getFlexExessTime(),
//						appOvertimeInfor.getOverTimeAtr(),
//						(appInfor.getAppReason() == null || appInfor.getReflectionInformation().getStateReflectionReal() == ReflectedState_New.REFLECTED) ? "" : appInfor.getAppReason().v()); 
//		overTimeTmp = new OvertimeReflectPara(appInfor.getEmployeeID(), 
//				appInfor.getAppDate(), 
//				reflectSetting.isZangyouRecordReflect(),
//				reflectSetting.isZangyouScheReflect(),
//				reflectSetting.isZangyouWorktime(),
//				true,
//				reflectSetting.getScheAndWorkChange(),
//				true, 
//				overtimePara,
//				excLogId,
//				reflectSetting.getIdentityProcessUseSet(),
//				reflectSetting.getApprovalProcessingUseSetting()); 
		
		
		return overTimeTmp;
		
	}
}
