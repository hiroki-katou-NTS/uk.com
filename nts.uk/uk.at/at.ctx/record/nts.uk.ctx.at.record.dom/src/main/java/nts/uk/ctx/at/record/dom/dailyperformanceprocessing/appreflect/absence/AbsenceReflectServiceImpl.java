package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.absence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonCalculateOfAppReflectParam;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonProcessCheckService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonReflectParameter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.PreOvertimeReflectService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.workchange.WorkChangeCommonReflectPara;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.WorkUpdateService;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.ApplicationType;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.worktype.algorithm.JudgmentWorkTypeService;
import nts.uk.ctx.at.shared.dom.worktype.service.WorkTypeIsClosedService;
@Stateless
public class AbsenceReflectServiceImpl implements AbsenceReflectService{
	
	@Inject
	private WorkUpdateService workTimeUpdate;
	@Inject
	private CommonProcessCheckService commonService;
	@Inject
	private BasicScheduleService basicScheService;
	@Inject
	private JudgmentWorkTypeService judgmentService;
	@Inject
	private PreOvertimeReflectService preOTService;
	@Inject 
	private RecordDomRequireService requireService;

	@Override
	public void absenceReflect(WorkChangeCommonReflectPara param, boolean isPre) {
		CommonReflectParameter absencePara = param.getCommon();
			GeneralDate loopDate = absencePara.getAppDate();
			IntegrationOfDaily dailyInfor = preOTService.calculateForAppReflect(absencePara.getEmployeeId(), loopDate);
			WorkInfoOfDailyPerformance workInfor = new WorkInfoOfDailyPerformance(param.getCommon().getEmployeeId(), param.getCommon().getAppDate(), dailyInfor.getWorkInformation());
			//1日休日の判断
			if(workInfor.getWorkInformation().getRecordInfo().getWorkTypeCode() != null
					&& WorkTypeIsClosedService.checkHoliday(requireService.createRequire(),
								workInfor.getWorkInformation().getRecordInfo().getWorkTypeCode().v())) {
				return;
			}
			boolean isRecordWorkType = false;
			//予定勤種の反映
			if(workInfor.getWorkInformation().getScheduleInfo() == null 
					|| workInfor.getWorkInformation().getScheduleInfo().getWorkTimeCode() == null
					|| commonService.checkReflectScheWorkTimeType(absencePara, isPre, workInfor.getWorkInformation().getScheduleInfo().getWorkTimeCode().v())) {
				isRecordWorkType = true;
				workTimeUpdate.updateRecordWorkType(absencePara.getEmployeeId(), loopDate, absencePara.getWorkTypeCode(), true, dailyInfor);
			}				
			//予定開始終了時刻の反映
			this.reflectScheStartEndTime(absencePara.getEmployeeId(), loopDate, 
					absencePara.getWorkTypeCode(), isRecordWorkType, dailyInfor);			
			//勤種の反映
			workTimeUpdate.updateRecordWorkType(absencePara.getEmployeeId(), loopDate, absencePara.getWorkTypeCode(), false, dailyInfor);
			//就業時間帯
			if(param.getExcludeHolidayAtr() != 0) {
				if(isRecordWorkType) {
					workTimeUpdate.updateRecordWorkTime(absencePara.getEmployeeId(), loopDate, absencePara.getWorkTimeCode(), true, dailyInfor);	
				}					
				workTimeUpdate.updateRecordWorkTime(absencePara.getEmployeeId(), loopDate, absencePara.getWorkTimeCode(), false, dailyInfor);
			}
			//workRepository.updateByKeyFlush(workInfor);
			//開始終了時刻の反映
			this.reflectRecordStartEndTime(absencePara.getEmployeeId(), loopDate, absencePara.getWorkTypeCode(),
					dailyInfor);
			CommonCalculateOfAppReflectParam calcParam = new CommonCalculateOfAppReflectParam(dailyInfor,
					absencePara.getEmployeeId(), loopDate,
					ApplicationType.ABSENCE_APPLICATION,
					absencePara.getWorkTypeCode(),
					Optional.ofNullable(absencePara.getWorkTimeCode()),
					Optional.ofNullable(absencePara.getStartTime()),
					Optional.ofNullable(absencePara.getEndTime()),
					isPre,
					absencePara.getIPUSOpt(),
					absencePara.getApprovalSet());
			commonService.calculateOfAppReflect(calcParam);
	}

	@Override
	public void reflectScheStartEndTime(String employeeId, GeneralDate baseDate, String workTypeCode, boolean isReflect, IntegrationOfDaily dailyInfor) {
		//INPUT．予定勤務種類変更フラグをチェックする
		if(!isReflect) {
			return;
		}
		//予定開始終了時刻をクリアするかチェックする
		//1日半日出勤・1日休日系の判定
		if(basicScheService.checkWorkDay(workTypeCode) == WorkStyle.ONE_DAY_REST) {
			//予定開始時刻の反映
			//予定終了時刻の反映
			workTimeUpdate.cleanScheTime(employeeId, baseDate, dailyInfor);
			
		}
	}

	@Override
	public void reflectRecordStartEndTime(String employeeId, GeneralDate baseDate, String workTypeCode,
			IntegrationOfDaily dailyInfor) {
		TimeLeavingOfDailyPerformance dailyPerformance = new TimeLeavingOfDailyPerformance(employeeId, baseDate, dailyInfor.getAttendanceLeave().get());
		boolean isCheckClean =  this.checkTimeClean(employeeId, baseDate, workTypeCode, Optional.ofNullable(dailyPerformance));
		//開始終了時刻をクリアするかチェックする 値：０になる。	
		if(!isCheckClean) return;
		workTimeUpdate.cleanRecordTimeData(employeeId, baseDate, dailyInfor);
	}

	@Override
	public boolean checkTimeClean(String employeeId, GeneralDate baseDate, String workTypeCode,
			Optional<TimeLeavingOfDailyPerformance> optTimeLeavingOfDaily) {
		//1日半日出勤・1日休日系の判定
		if(basicScheService.checkWorkDay(workTypeCode) == WorkStyle.ONE_DAY_REST) {
			//勤務種類が１日年休特休かの判断
			boolean temp = judgmentService.checkWorkTypeIsHD(workTypeCode);
			//打刻元情報をチェックする
			if(temp) {
				if(optTimeLeavingOfDaily.isPresent()) {
					TimeLeavingOfDailyPerformance timeLeavingOfDaily = optTimeLeavingOfDaily.get();
					if(checkReflectNenkyuTokkyu(timeLeavingOfDaily, 1)) {
						return checkReflectNenkyuTokkyu(timeLeavingOfDaily, 2);
					} else {
						return false;
					}
				}
			}
			
			return true;
		}
		return false;
	}
	/**
	 * 打刻元情報をチェックする
	 * @param timeLeavingOfDaily
	 * @param workNo
	 * @return
	 */
	private boolean checkReflectNenkyuTokkyu(TimeLeavingOfDailyPerformance timeLeavingOfDaily, int workNo) {
		List<TimeLeavingWork> timeLeavingWorks = timeLeavingOfDaily.getAttendance().getTimeLeavingWorks().stream()
				.filter(x -> x.getWorkNo().v() == workNo).collect(Collectors.toList());
		if(!timeLeavingWorks.isEmpty()) {
			TimeLeavingWork timeLeaving1 = timeLeavingWorks.get(0);
			Optional<TimeActualStamp> optAttendanceStamp = timeLeaving1.getAttendanceStamp();
			if(optAttendanceStamp.isPresent()) {
				TimeActualStamp attendanceStamp = optAttendanceStamp.get();
				Optional<WorkStamp> optWorkStamp = attendanceStamp.getStamp();
				if(optWorkStamp.isPresent()) {
					WorkStamp workStamp = optWorkStamp.get();
					if(workStamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans() == TimeChangeMeans.SPR_COOPERATION) {
						return false;
					}
				}
			}
			Optional<TimeActualStamp> optLeaveStamp = timeLeaving1.getLeaveStamp();
			if(optLeaveStamp.isPresent()) {
				TimeActualStamp leaveStamp = optLeaveStamp.get();
				Optional<WorkStamp> optStamp = leaveStamp.getStamp();
				if(optStamp.isPresent()) {
					WorkStamp stamp = optStamp.get();
					if(stamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans() == TimeChangeMeans.SPR_COOPERATION) {
						return false;
					}
				}
			}
			
		}
		return true;
	}
	

	
}
