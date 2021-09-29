package nts.uk.ctx.at.request.dom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.RequiredArgsConstructor;

/*import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.CompltLeaveSimMng;*/

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.request.dom.application.appabsence.ApplyForLeave;
import nts.uk.ctx.at.request.dom.application.appabsence.ApplyForLeaveRepository;
import nts.uk.ctx.at.request.dom.application.appabsence.apptimedigest.TimeDigestApplication;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripRepository;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveAppRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentAppRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWorkRepository;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTimeRepository;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType_Update;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplicationDetail;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplicationRepository;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChangeRepository;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.AppRemainCreateInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.ApplicationType;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.PrePostAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.TimeDigestionUsageInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.VacationTimeInforNew;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.RemainCreateInforByApplicationData;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHdFrameNo;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class RemainCreateInforByApplicationDataImpl implements RemainCreateInforByApplicationData {
	@Inject
	private ApplicationRepository appRepository;
	@Inject
	private AppWorkChangeRepository workChangeService;
	@Inject
	private GoBackDirectlyRepository goBackRepo;
	@Inject
	private ApplyForLeaveRepository applyForLeaveRepo;
	@Inject
	private RecruitmentAppRepository recAppRepo;

	@Inject
	private AbsenceLeaveAppRepository absAppRepo;
	@Inject
	private AppOverTimeRepository overtimeRepo;
	@Inject
	private AppHolidayWorkRepository holidayWorkRepo;
	@Inject
	private AppWorkChangeRepository workChangeRepos;
	@Inject
	private TimeLeaveApplicationRepository timeLeaveRepo;
	@Inject
	private BusinessTripRepository businessTripRepo;

	@Override
	public List<AppRemainCreateInfor> lstRemainDataFromApp(CacheCarrier cacheCarrier, String cid, String sid, DatePeriod dateData) {
		List<Integer> lstReflect = new ArrayList<>();
		lstReflect.add(ReflectedState.NOTREFLECTED.value);
		lstReflect.add(ReflectedState.WAITREFLECTION.value);
		lstReflect.add(ReflectedState.REMAND.value);
		List<Integer> lstAppType = this.lstAppType();
		List<Application> lstAppData = new ArrayList<>();
		if(!lstAppType.isEmpty()) {
			lstAppData = appRepository.getByPeriodReflectType(sid, dateData, lstReflect, lstAppType);
		}
		return this.lstResult(cid, sid, lstAppData);
	}
	@Override
	public List<AppRemainCreateInfor> lstRemainDataFromApp(CacheCarrier cacheCarrier, String cid, String sid, List<GeneralDate> dates) {
		List<Integer> lstReflect = new ArrayList<>();
		lstReflect.add(ReflectedState.NOTREFLECTED.value);
		lstReflect.add(ReflectedState.WAITREFLECTION.value);
		List<Integer> lstAppType = this.lstAppType();
		List<Application> lstAppData = new ArrayList<>();
		if(!lstAppType.isEmpty()) {
			lstAppData = appRepository.getByListDateReflectType(sid, dates, lstReflect, lstAppType);
		}
		return this.lstResult(cid, sid, lstAppData);
	}

	@Override
	public List<AppRemainCreateInfor> lstRemainDataFromApp(CacheCarrier cacheCarrier, String cid, String sid, GeneralDate baseDate) {
		List<Integer> lstReflect = new ArrayList<>();
		lstReflect.add(ReflectedState.NOTREFLECTED.value);
		lstReflect.add(ReflectedState.WAITREFLECTION.value);
		List<Integer> lstAppType = this.lstAppType();
		List<Application> lstAppData = new ArrayList<>();
		if(!lstAppType.isEmpty()) {
			lstAppData = appRepository.getByPeriodReflectType(sid, baseDate, lstReflect, lstAppType);
		}
		return this.lstResult(cid, sid, lstAppData);
	}
	
	private List<Integer> lstAppType(){
		List<Integer> lstAppType = new ArrayList<>();
		//反映する時、エラーが発生してるので、とりあえずコメントする（暫定データ処理は申請の新ドメインをまだ対応しない）
		lstAppType.add(ApplicationType.WORK_CHANGE_APPLICATION.value);
		lstAppType.add(ApplicationType.GO_RETURN_DIRECTLY_APPLICATION.value);
		lstAppType.add(ApplicationType.ABSENCE_APPLICATION.value);
		lstAppType.add(ApplicationType.COMPLEMENT_LEAVE_APPLICATION.value);
		lstAppType.add(ApplicationType.OVER_TIME_APPLICATION.value);
		lstAppType.add(ApplicationType.BREAK_TIME_APPLICATION.value);
		lstAppType.add(ApplicationType.BUSINESS_TRIP_APPLICATION.value);
		lstAppType.add(ApplicationType.ANNUAL_HOLIDAY_APPLICATION.value);
		return lstAppType;
	}
	private List<AppRemainCreateInfor> lstResult(String cid, String sid, List<Application> lstAppData){
		List<AppRemainCreateInfor> lstOutputData = new ArrayList<>();
		lstAppData.stream().forEach(appData -> {
			AppRemainCreateInfor outData = new AppRemainCreateInfor();
			outData.setSid(sid);
			outData.setAppDate(appData.getAppDate().getApplicationDate());
			outData.setAppId(appData.getAppID());
			outData.setAppType(EnumAdaptor.valueOf(appData.getAppType().value, ApplicationType.class));
			outData.setPrePosAtr(EnumAdaptor.valueOf(appData.getPrePostAtr().value, PrePostAtr.class));
			outData.setInputDate(appData.getInputDate());
			outData.setWorkTimeCode(Optional.empty());
			outData.setWorkTypeCode(Optional.empty());
			outData.setStartDate(appData.getOpAppStartDate().isPresent() ? Optional.of(appData.getOpAppStartDate().get().getApplicationDate()) : Optional.empty());
			outData.setEndDate(appData.getOpAppEndDate().isPresent() ? Optional.of(appData.getOpAppEndDate().get().getApplicationDate()) : Optional.empty());
			switch(outData.getAppType()) {
			case WORK_CHANGE_APPLICATION:
				Optional<AppWorkChange> workChange = workChangeService.findbyID(cid, appData.getAppID());
				workChange.ifPresent(x -> {
					outData.setWorkTimeCode(x.getOpWorkTimeCD().map(time -> time.v()));
					outData.setWorkTypeCode(x.getOpWorkTypeCD().map(type -> type.v()));
				});
				break;
			case GO_RETURN_DIRECTLY_APPLICATION:
				Optional<GoBackDirectly> goBack = goBackRepo.find(cid, appData.getAppID());
				goBack.ifPresent(x -> {
					outData.setWorkTimeCode(x.getDataWork().map(dw -> dw.getWorkTimeCode().v()));
					outData.setWorkTypeCode(x.getDataWork().map(dw-> dw.getWorkTypeCode().v()));
				});
				break;
			case ABSENCE_APPLICATION:
				Optional<ApplyForLeave> absence = applyForLeaveRepo.findApplyForLeave(cid, appData.getAppID());
				absence.ifPresent(x -> {
					outData.setWorkTypeCode(x.getReflectFreeTimeApp().getWorkInfo().getWorkTypeCode() == null ? Optional.empty() : Optional.of(x.getReflectFreeTimeApp().getWorkInfo().getWorkTypeCode().v()));
					if(x.getReflectFreeTimeApp().getWorkChangeUse().equals(NotUseAtr.USE)) {
						outData.setWorkTimeCode(x.getReflectFreeTimeApp().getWorkInfo().getWorkTimeCode() == null ? Optional.empty() : Optional.of(x.getReflectFreeTimeApp().getWorkInfo().getWorkTimeCode().v()));
					}
					outData.setTimeDigestionUsageInfor(x.getReflectFreeTimeApp().getTimeDegestion().map(time-> fromTimeDegest(time)).orElse(Optional.empty()));
				});
				break;
			case COMPLEMENT_LEAVE_APPLICATION:
				Optional<AbsenceLeaveApp> optAbsApp = absAppRepo.findByAppId(appData.getAppID());
				optAbsApp.ifPresent(x -> {
					outData.setWorkTypeCode(Optional.of(x.getWorkInformation().getWorkTypeCode().v()));
					if(x.getWorkChangeUse().equals(NotUseAtr.USE)) {
						outData.setWorkTimeCode(x.getWorkInformation().getWorkTimeCodeNotNull().isPresent() ? Optional.empty() : Optional.of(x.getWorkInformation().getWorkTimeCode().v()));
					}

				});

				Optional<RecruitmentApp> recApp = recAppRepo.findByID(appData.getAppID());
				recApp.ifPresent(y -> {
					outData.setWorkTimeCode(Optional.of(y.getWorkInformation().getWorkTimeCode().v()));
					outData.setWorkTypeCode(Optional.of(y.getWorkInformation().getWorkTypeCode().v()));

				});

				break;
			case OVER_TIME_APPLICATION:
				Optional<AppOverTime> overTimeData = overtimeRepo.find(cid, appData.getAppID());
				Integer appBreakTimeTotal = 0;
				Integer appOvertimeTimeTotal = 0;
				if(overTimeData.isPresent()){
					AppOverTime x = overTimeData.get();
					outData.setWorkTimeCode(x.getWorkInfoOp().map(wrk -> wrk.getWorkTimeCode().v()));
					outData.setWorkTypeCode(x.getWorkInfoOp().map(wrk -> wrk.getWorkTypeCode().v()));
					//申請休出時間合計を設定する
					appBreakTimeTotal = x.getApplicationTime()
								.getApplicationTime()
								.stream()
								.filter(time-> time.getAttendanceType()
										.equals(AttendanceType_Update.BREAKTIME))
								.mapToInt(time -> time.getApplicationTime().v())
								.sum();

					//申請残業時間合計を設定する

					appOvertimeTimeTotal = x.getApplicationTime()
							.getApplicationTime()
							.stream()
							.filter(time -> time.getAttendanceType()
									.equals(AttendanceType_Update.NORMALOVERTIME))
							.mapToInt(time -> time.getApplicationTime().v())
							.sum();
				}
				outData.setAppBreakTimeTotal(Optional.of(appBreakTimeTotal));
				outData.setAppOvertimeTimeTotal(Optional.of(appOvertimeTimeTotal));
				break;
			case BREAK_TIME_APPLICATION:
				Optional<AppHolidayWork> holidayWork = holidayWorkRepo.find(cid, appData.getAppID());
				Integer breakTimeTotal = 0;
				Integer overtimeTimeTotal = 0;
				if(holidayWork.isPresent()) {
					AppHolidayWork x = holidayWork.get();
					outData.setWorkTimeCode(Optional.ofNullable(x.getWorkInformation().getWorkTimeCode().v()));
					outData.setWorkTypeCode(Optional.ofNullable(x.getWorkInformation().getWorkTypeCode().v()));
					//申請休出時間合計を設定する
					breakTimeTotal = x.getApplicationTime()
							.getApplicationTime()
							.stream()
							.filter(time-> time.getAttendanceType()
									.equals(AttendanceType_Update.BREAKTIME))
							.mapToInt(time -> time.getApplicationTime().v())
							.sum();

					overtimeTimeTotal = x.getApplicationTime()
							.getApplicationTime()
							.stream()
							.filter(time -> time.getAttendanceType()
									.equals(AttendanceType_Update.NORMALOVERTIME))
							.mapToInt(time -> time.getApplicationTime().v())
							.sum();
				}
				outData.setAppBreakTimeTotal(Optional.of(breakTimeTotal));
				outData.setAppOvertimeTimeTotal(Optional.of(overtimeTimeTotal));
				break;

			case ANNUAL_HOLIDAY_APPLICATION:
				//時間休暇申請
				this.timeLeaveRepo.findById(cid, appData.getAppID()).ifPresent(x -> {
					List<VacationTimeInforNew> vacationTimes = x.getLeaveApplicationDetails().stream()
							.map(time -> mapFromTimeLeave(time)).collect(Collectors.toList());
					outData.setVacationTimes(vacationTimes);
				});

				break;
			case BUSINESS_TRIP_APPLICATION:
				//出張申請
				this.businessTripRepo.findByAppId(cid, appData.getAppID()).ifPresent(x -> {
					if (!x.getInfos().isEmpty()) {
						WorkInformation wrkInfo =  x.getInfos().get(0).getWorkInformation();
						outData.setWorkTimeCode(
								wrkInfo.getWorkTimeCode() == null ? Optional.empty() : Optional.of(wrkInfo.getWorkTimeCode().v()));
						outData.setWorkTypeCode(
								wrkInfo.getWorkTypeCode() == null ? Optional.empty() : Optional.of(wrkInfo.getWorkTypeCode().v()));
					}
				});
				break;
			default:
				break;
			}
			lstOutputData.add(outData);
		});
		return lstOutputData;
	}

	private Optional<TimeDigestionUsageInfor> fromTimeDegest(TimeDigestApplication time) {
		return Optional.ofNullable(new TimeDigestionUsageInfor(
				time.getTimeAnnualLeave() == null ? null : time.getTimeAnnualLeave().v(),
				time.getTimeOff()         == null ? null : time.getTimeOff().v(),
				time.getOvertime60H()     == null ? null : time.getOvertime60H().v(),
				time.getChildTime()       == null ? null : time.getChildTime().v(),
				time.getNursingTime()     == null ? null : time.getNursingTime().v()));
	}

	private VacationTimeInforNew mapFromTimeLeave(TimeLeaveApplicationDetail time){

		TimeDigestApplication timed = time.getTimeDigestApplication();

		return VacationTimeInforNew.builder()
				.timeType(time.getAppTimeType())
				.nenkyuTime(timed.getTimeAnnualLeave())
				.kyukaTime(timed.getTimeOff())
				.hChoukyuTime(timed.getOvertime60H())
				.specialHolidayUseTime(timed.getTimeSpecialVacation())
				.timeChildCareHolidayUseTime(timed.getChildTime())
				.timeCareHolidayUseTime(timed.getNursingTime())
				.spcVacationFrameNo(Optional
						.ofNullable(timed.getSpecialVacationFrameNO().map(x -> new SpecialHdFrameNo(x)).orElse(null)))
				.build();
	}

//	@Override
//	public Integer excludeHolidayAtr(CacheCarrier cacheCarrier, String cid, String appID) {
//		val require =  new RequireImpl(cacheCarrier);
//		Optional<AppWorkChange> data = require.getAppworkChangeById(cid, appID);
//		if(data.isPresent()) {
//			//return data.get().getExcludeHolidayAtr();
//		}
//		return null;
//	}


	public static interface Require {
//		workChangeRepos.getAppworkChangeById(cid, appID);
		Optional<AppWorkChange> getAppworkChangeById(String cid, String appId);
	}

	@RequiredArgsConstructor
	class RequireImpl implements RemainCreateInforByApplicationDataImpl.Require{

		private final CacheCarrier cacheCarrier;

		@Override
		public Optional<AppWorkChange> getAppworkChangeById(String cid, String appId) {
			return workChangeRepos.findbyID(cid, appId);
		}
	}

}
