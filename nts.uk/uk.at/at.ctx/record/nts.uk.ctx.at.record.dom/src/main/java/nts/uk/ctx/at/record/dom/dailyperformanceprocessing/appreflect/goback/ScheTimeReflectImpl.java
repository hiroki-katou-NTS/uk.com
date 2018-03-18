package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.goback;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonProcessCheckService;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ScheWorkUpdateService;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.TimeReflectParameter;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.worktime.common.LateEarlyAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.OtherEmTimezoneLateEarlySet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateEarlySetGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.predset.UseSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ScheTimeReflectImpl implements ScheTimeReflect{

	@Inject
	private WorkTimeTypeScheReflect ScheReflect;
	@Inject
	private PredetemineTimeSettingRepository predetemineTimeRepo;
	@Inject
	private ScheWorkUpdateService scheUpdateService;
	@Inject
	private WorkInformationRepository workInfor;
	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeLeavingOfDaily;
	@Inject
	private WorkTimeSettingRepository workTimeRepository;
	//@Inject
	//private WorkTimezoneLateEarlySetGetMemento workMemento;
	@Inject
	private CommonProcessCheckService commonService;
	@Override
	public void reflectScheTime(GobackReflectParameter para, boolean timeTypeScheReflect) {
		//予定勤務種類による勤種・就時を反映できるかチェックする
		if(!ScheReflect.checkReflectWorkTimeType(para)) {
			return;
		}
		//(開始時刻)反映する時刻を求める
		TimeOfDayReflectOutput startTimeReflect = this.getTimeOfDayReflect(para, timeTypeScheReflect, ApplyTimeAtr.START);
		if(startTimeReflect.isReflectFlg()) {
			//予定開始時刻の反映
			TimeReflectParameter timeRef = new TimeReflectParameter(para.getEmployeeId(), para.getDateData(), para.getGobackData().getStartTime1(), 1, true);
			scheUpdateService.updateStartTimeOfReflect(timeRef);
		}
		//(終了時刻)反映する時刻を求める
		TimeOfDayReflectOutput endTimeReflect = this.getTimeOfDayReflect(para, timeTypeScheReflect, ApplyTimeAtr.END);
		if(endTimeReflect.isReflectFlg()) {
			TimeReflectParameter endTime = new TimeReflectParameter(para.getEmployeeId(), para.getDateData(), para.getGobackData().getEndTime1(), 1, false);
			scheUpdateService.updateStartTimeOfReflect(endTime);
		}
	}
	@Override
	public TimeOfDayReflectOutput getTimeOfDayReflect(GobackReflectParameter para, 
			boolean timeTypeScheReflect,
			ApplyTimeAtr applyTimeAtr) {
		//反映するフラグ=false、反映する時刻=0（初期化）
		TimeOfDayReflectOutput reflectOutput = new TimeOfDayReflectOutput(false, 0);
		//INPUT．予定時刻反映区分をチェックする
		if(para.getScheTimeReflectAtr() == ScheTimeReflectAtr.APPTIME) {
			//INPUT．申請する時刻をチェックする
			if(applyTimeAtr == ApplyTimeAtr.START
					&& para.getGobackData().getStartTime1() != null) {
				reflectOutput.setReflectFlg(true);
				reflectOutput.setTimeOfDay(para.getGobackData().getStartTime1());
			} else if (applyTimeAtr == ApplyTimeAtr.END
					&& para.getGobackData().getEndTime1() != null) {
				reflectOutput.setReflectFlg(true);
				reflectOutput.setTimeOfDay(para.getGobackData().getEndTime1());
			}
			return reflectOutput;
		} else {
			//INPUT．勤種・就時の反映できるフラグをチェックする
			if(timeTypeScheReflect) {
				//ドメインモデル「就業時間帯の設定」を取得する
				String companyId = AppContexts.user().companyId();
				Optional<PredetemineTimeSetting> optFindByCode = predetemineTimeRepo.findByWorkTimeCode(companyId, para.getGobackData().getWorkTimeCode());
				if(!optFindByCode.isPresent()) {
					return reflectOutput;
				}
				PredetemineTimeSetting workTimeData = optFindByCode.get();
				TimezoneUse timeZone = workTimeData.getPrescribedTimezoneSetting().getLstTimezone()
						.stream()
						.filter(x -> x.getWorkNo() == 1).collect(Collectors.toList()).get(0);
				if(timeZone.getUseAtr() == UseSetting.USE) {
					if(applyTimeAtr == ApplyTimeAtr.START) {
						reflectOutput.setTimeOfDay(timeZone.getStart().v());						
					} else {
						reflectOutput.setTimeOfDay(timeZone.getEnd().v());
					}
					reflectOutput.setReflectFlg(true);
					return reflectOutput;
				}
			}
		}
		
		return reflectOutput;
	}
	@Override
	public void reflectTime(GobackReflectParameter para, boolean workTypeTimeReflect) {
		String tmpWorkTimeCode;
		//INPUT．勤種・就時の反映できるフラグをチェックする
		if(workTypeTimeReflect) {
			tmpWorkTimeCode = para.getGobackData().getWorkTimeCode();
		} else {
			//ドメインモデル「日別実績の勤務情報」を取得する
			Optional<WorkInfoOfDailyPerformance> optWorkData = workInfor.find(para.getEmployeeId(), para.getDateData());
			if(!optWorkData.isPresent()) {
				return;
			} 
			WorkInfoOfDailyPerformance workData = optWorkData.get();
			tmpWorkTimeCode = workData.getRecordWorkInformation().getWorkTimeCode().v();
		}
		//出勤時刻を反映できるかチェックする
		if(this.checkAttendenceReflect(para, 1, true)) {
			//ジャスト遅刻により時刻を編集する
			Integer timeLate = this.justTimeLateLeave(tmpWorkTimeCode, para.getGobackData().getStartTime1(), 1, true);
			//開始時刻を反映する 
			TimeReflectParameter timeData = new TimeReflectParameter(para.getEmployeeId(), para.getDateData(), timeLate, 1, true);
			scheUpdateService.updateReflectStartEndTime(timeData, commonService.lstWorkItem());			
		}
		//退勤時刻を反映できるか
		if(this.checkAttendenceReflect(para, 1, false)) {
			//ジャスト早退により時刻を編集する
			Integer timeLeave = this.justTimeLateLeave(tmpWorkTimeCode, para.getGobackData().getEndTime1(), 1, false);
			//終了時刻の反映
			TimeReflectParameter timeData = new TimeReflectParameter(para.getEmployeeId(), para.getDateData(), timeLeave, 1, false);
			scheUpdateService.updateReflectStartEndTime(timeData, commonService.lstWorkItem());
		}
		//TODO 出勤時刻２を反映できるか, 退勤時刻２を反映できるか
	}
	@Override
	public boolean checkAttendenceReflect(GobackReflectParameter para, Integer frameNo, boolean isPre) {
		//INPUT．打刻優先区分をチェックする
		if(para.getPriorStampAtr() == PriorStampAtr.GOBACKPRIOR) {
			//INPUT．申請する時刻に値があるかチェックする
			//chi lam voi frameNo == 1
			if(isPre && frameNo == 1 && para.getGobackData().getStartTime1() != null
					|| isPre && frameNo == 2 && para.getGobackData().getStartTime2() != null
					|| !isPre && frameNo == 1 && para.getGobackData().getEndTime1() != null
					|| !isPre && frameNo == 2 && para.getGobackData().getStartTime2() != null) {
				return true;
			} else {
				return false;
			}
		} else {
			
			Optional<TimeLeavingOfDailyPerformance> optTimeLeave = timeLeavingOfDaily.findByKey(para.getEmployeeId(), para.getDateData());
			if(!optTimeLeave.isPresent()) {
				return true;
			}
			TimeLeavingOfDailyPerformance timeLeave = optTimeLeave.get();
			List<TimeLeavingWork> lstTimeLeaveWork = timeLeave.getTimeLeavingWorks().stream().filter(x-> x.getWorkNo().v() == frameNo)
					.collect(Collectors.toList());
			if(lstTimeLeaveWork.isEmpty()) {
				return true;
			}			
			//打刻元情報をチェックする
			TimeLeavingWork timeLeaveWork = lstTimeLeaveWork.get(0);
			Optional<TimeActualStamp> optAttendanceStamp;
			if(isPre) {
				//出勤打刻があるか
				optAttendanceStamp = timeLeaveWork.getAttendanceStamp();				
			} else {
				//退勤打刻があるか
				optAttendanceStamp = timeLeaveWork.getLeaveStamp();
			}

			if(!optAttendanceStamp.isPresent()) {
				return true;
			}
			TimeActualStamp attendanceStamp = optAttendanceStamp.get();
			Optional<WorkStamp> optActualStamp = attendanceStamp.getActualStamp();
			if(!optActualStamp.isPresent()) {
				return true;		
			} 
			WorkStamp actualStamp = optActualStamp.get();
			//ドメインモデル「勤怠打刻」．打刻元情報が「打刻自動セット(個人情報)、直行直帰申請」
			if(actualStamp.getStampSourceInfo() == StampSourceInfo.STAMP_AUTO_SET_PERSONAL_INFO
					|| actualStamp.getStampSourceInfo() == StampSourceInfo.GO_STRAIGHT_APPLICATION) {
				return true;
			}
		}
		
		return false;
		
	}
	@Override
	public Integer justTimeLateLeave(String workTimeCode, Integer timeData, Integer frameNo, boolean isPre) {
		String companyId = AppContexts.user().companyId();
		//ドメインモデル「就業時間帯の設定」を取得する
		Optional<WorkTimeSetting> findByCode = workTimeRepository.findByCode(companyId, workTimeCode);
		if(!findByCode.isPresent()) {
			return timeData;
		}
		
		//時間丁度の打刻は遅刻・早退とするをチェックする
		WorkTimeSetting workTimeData = findByCode.get();
		//TODO can xem lai
		/*List<OtherEmTimezoneLateEarlySet> lstEmTimezon = workMemento.getOtherClassSet();
		OtherEmTimezoneLateEarlySet emTimezon;
		if(isPre) {
			emTimezon = lstEmTimezon.stream()
					.filter(x -> x.getLateEarlyAtr() == LateEarlyAtr.LATE && x.getGraceTimeSet().getGraceTime().v() == frameNo).collect(Collectors.toList()).get(0);//dieu kien khong dung
			
		} else {
			emTimezon = lstEmTimezon.stream()
					.filter(x -> x.getLateEarlyAtr() == LateEarlyAtr.EARLY && x.getGraceTimeSet().getGraceTime().v() == frameNo).collect(Collectors.toList()).get(0);//dieu kien khong dung

		}
		if(emTimezon.isStampExactlyTimeIsLateEarly()) {
			if(isPre) {
				return timeData - 1;				
			} else {
				return timeData + 1;
			}

		}*/				
		return timeData;
	}

}
