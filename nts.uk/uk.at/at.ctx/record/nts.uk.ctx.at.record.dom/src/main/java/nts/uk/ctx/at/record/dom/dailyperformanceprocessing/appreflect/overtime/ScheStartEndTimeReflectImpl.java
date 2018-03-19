package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ScheWorkUpdateService;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.TimeReflectParameter;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.predset.UseSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ScheStartEndTimeReflectImpl implements ScheStartEndTimeReflect {
	@Inject
	private PredetemineTimeSettingRepository predetemineTimeRepo;
	@Inject
	private BasicScheduleService basicSche;
	@Inject
	private ScheWorkUpdateService scheWork;
	@Override
	public ScheStartEndTimeReflectOutput reflectScheStartEndTime(PreOvertimeParameter para,
			WorkTimeTypeOutput timeTypeData) {
		//反映する開始終了時刻を求める
		ScheStartEndTimeReflectOutput findStartEndTime = this.findStartEndTime(para, timeTypeData);
		//申請する開始終了時刻に値があるかチェックする
		if(para.getOvertimePara().getStartTime1() != null
				|| para.getOvertimePara().getEndTime1() != null
				|| para.getOvertimePara().getStartTime2() != null
				|| para.getOvertimePara().getEndTime2() != null) {
			//１回勤務反映区分(output)をチェックする
			if(findStartEndTime.isCountReflect1Atr()) {
				//予定開始時刻の反映
				TimeReflectParameter startTime = new TimeReflectParameter(para.getEmployeeId(), 
						para.getDateInfo(), 
						findStartEndTime.getStartTime1(), 
						1, 
						true);
				scheWork.updateStartTimeOfReflect(startTime);
				//予定終了時刻の反映
				TimeReflectParameter endTime = new TimeReflectParameter(para.getEmployeeId(), 
						para.getDateInfo(), 
						findStartEndTime.getEndTime1(), 
						1, 
						false);
				scheWork.updateStartTimeOfReflect(endTime);
			}
			//２回勤務反映区分(output)をチェックする
			if(findStartEndTime.isCountReflect2Atr()) {
				//予定開始時刻２の反映
				TimeReflectParameter startTime = new TimeReflectParameter(para.getEmployeeId(), 
						para.getDateInfo(), 
						findStartEndTime.getStartTime2(),
						2,
						true);
				scheWork.updateStartTimeOfReflect(startTime);
				//予定終了時刻２の反映
				TimeReflectParameter endTime = new TimeReflectParameter(para.getEmployeeId(), 
						para.getDateInfo(), 
						findStartEndTime.getEndTime2(),
						2,
						false);
				scheWork.updateStartTimeOfReflect(endTime);
			}
		} else {
			//１回勤務反映区分(output)をチェックする
			if(findStartEndTime.isCountReflect1Atr()) {
				//予定開始時刻を反映できるかチェックする
				//TODO thu 2
			} else {
				
			}
		}
		
		return null;
	}

	@Override
	public ScheStartEndTimeReflectOutput findStartEndTime(PreOvertimeParameter para, WorkTimeTypeOutput timeTypeData) {
		ScheStartEndTimeReflectOutput findDataOut = new ScheStartEndTimeReflectOutput(null, null, true, null, null, true);
		//ドメインモデル「就業時間帯の設定」を取得する
		String companyId = AppContexts.user().companyId();
		Optional<PredetemineTimeSetting> optWorkTimeData = predetemineTimeRepo.findByWorkTimeCode(companyId, timeTypeData.getWorktimeCode());
		if(!optWorkTimeData.isPresent()) {
			findDataOut.setCountReflect2Atr(false);
		} 
		PredetemineTimeSetting workTimeData = optWorkTimeData.get();		
		List<TimezoneUse> lstTimeZone2 = workTimeData.getPrescribedTimezoneSetting().getLstTimezone()
				.stream()
				.filter(x -> x.getWorkNo() == 2).collect(Collectors.toList());
		List<TimezoneUse> lstTimeZone1 = workTimeData.getPrescribedTimezoneSetting().getLstTimezone()
				.stream()
				.filter(x -> x.getWorkNo() == 1).collect(Collectors.toList());
		TimezoneUse timeZone2 = null;
		if(lstTimeZone2.isEmpty()) {
			findDataOut.setCountReflect2Atr(false);
		} else {
			timeZone2 = lstTimeZone2.get(0);	
		}

		TimezoneUse timeZone1 = lstTimeZone1.get(0);
		if(timeZone2 != null && timeZone2.getUseAtr() == UseSetting.NOT_USE) {
			findDataOut.setCountReflect2Atr(false);
		} else {
			findDataOut.setCountReflect2Atr(true);	
		}
		//1日半日出勤・1日休日系の判定
		WorkStyle workStyle =  basicSche.checkWorkDay(timeTypeData.getWorkTypeCode());
		PrescribedTimezoneSetting prescribSeting = workTimeData.getPrescribedTimezoneSetting();
		//申請の開始、終了時刻に値があるかチェックする
		if(para.getOvertimePara().getStartTime1() != null
				|| para.getOvertimePara().getEndTime1() != null
				|| para.getOvertimePara().getStartTime2() != null
				|| para.getOvertimePara().getEndTime2() != null) {
			//取得した２回勤務使用区分をチェックする
			if(!findDataOut.isCountReflect2Atr()) {
				findDataOut.setStartTime1(para.getOvertimePara().getStartTime1());
				findDataOut.setEndTime1(para.getOvertimePara().getEndTime1());
				return findDataOut;
			} else {
				//昼休憩は勤務時間帯か、勤務時間帯2にあるかチェックする
				
				//所定時間帯設定．午後開始時刻＜（２枠目）時間帯(使用区分付き)．開始
				if(timeZone2 != null 
						&& prescribSeting.getAfternoonStartTime().v() < timeZone2.getStart().v()) {//true：昼休憩は勤務時間帯にある
					//開始時刻=申請の開始時刻、終了時刻=申請の開始時刻
					findDataOut.setStartTime1(para.getOvertimePara().getStartTime1());
					findDataOut.setEndTime1(para.getOvertimePara().getEndTime1());
					//OUTPUT．出勤休日区分をチェックする
					if(workStyle == WorkStyle.MORNING_WORK) {
						findDataOut.setCountReflect2Atr(false);
					} else {
						//開始時刻2=申請の開始時刻2、終了時刻2=申請の開始時刻2
						findDataOut.setStartTime2(para.getOvertimePara().getStartTime2());
						findDataOut.setEndTime2(para.getOvertimePara().getEndTime2());
					}
					return findDataOut;
				} else { //false：昼休憩は勤務時間帯２にある
					//開始時刻2=申請の開始時刻2、終了時刻2=申請の開始時刻2
					findDataOut.setStartTime2(para.getOvertimePara().getStartTime2());
					findDataOut.setEndTime2(para.getOvertimePara().getEndTime2());
					//OUTPUT．出勤休日区分をチェックする
					if(workStyle == WorkStyle.AFTERNOON_WORK) {
						findDataOut.setCountReflect1Atr(false);
					}else {
						//開始時刻=申請の開始時刻、終了時刻=申請の開始時刻
						findDataOut.setStartTime1(para.getOvertimePara().getStartTime1());
						findDataOut.setEndTime1(para.getOvertimePara().getEndTime1());
					}
					return findDataOut;
				}
			}
		} else {
			//取得した２回勤務使用区分をチェックする
			if(!findDataOut.isCountReflect2Atr()) {
				//OUTPUT．出勤休日区分をチェックする
				if(workStyle == WorkStyle.AFTERNOON_WORK) {
					//開始時刻=所定時間帯設定．午後開始時刻
					findDataOut.setStartTime1(prescribSeting.getAfternoonStartTime().v());
					//終了時刻=（１枠目）時間帯(使用区分付き)．終了
					findDataOut.setEndTime1(timeZone1.getEnd().v());
				} else if (workStyle == WorkStyle.MORNING_WORK) {
					//開始時刻=（１枠目）時間帯(使用区分付き)．開始
					findDataOut.setStartTime1(timeZone1.getStart().v());
					//終了時刻=所定時間帯設定．午前終了時刻
					findDataOut.setEndTime1(prescribSeting.getMorningEndTime().v());
				} else if (workStyle == WorkStyle.ONE_DAY_WORK) {
					//開始時刻=（１枠目）時間帯(使用区分付き)．開始
					findDataOut.setStartTime1(timeZone1.getStart().v());
					//終了時刻=（１枠目）時間帯(使用区分付き)．終了
					findDataOut.setEndTime1(timeZone1.getEnd().v());
				}
				return findDataOut;
			} else {
				//昼休憩は勤務時間帯か、勤務時間帯2にあるかチェックする
				if(timeZone2 != null 
						&& prescribSeting.getAfternoonStartTime().v() < timeZone2.getStart().v()) {
					//OUTPUT．出勤休日区分をチェックする
					if (workStyle == WorkStyle.AFTERNOON_WORK) {
						//開始時刻=所定時間帯設定．午後開始時刻
						findDataOut.setStartTime1(prescribSeting.getAfternoonStartTime().v());
						//終了時刻=（１枠目）時間帯(使用区分付き)．終了
						findDataOut.setEndTime1(timeZone1.getEnd().v());
						//開始時刻2=（２枠目）時間帯(使用区分付き)．開始
						findDataOut.setStartTime2(timeZone2.getStart().v());
						//終了時刻2=（２枠目）時間帯(使用区分付き)．終了
						findDataOut.setEndTime2(timeZone2.getEnd().v());
					} else if (workStyle == WorkStyle.MORNING_WORK) {
						//開始時刻=（１枠目）時間帯(使用区分付き)．開始
						findDataOut.setStartTime1(timeZone1.getStart().v());
						//終了時刻=所定時間帯設定．午前終了時刻
						findDataOut.setEndTime1(prescribSeting.getMorningEndTime().v());
						//２回勤務反映区分=false
						findDataOut.setCountReflect2Atr(false);
					} else if (workStyle == WorkStyle.ONE_DAY_WORK) {
						//開始時刻=（１枠目）時間帯(使用区分付き)．開始
						findDataOut.setStartTime1(timeZone1.getStart().v());
						//終了時刻=（１枠目）時間帯(使用区分付き)．終了
						findDataOut.setEndTime1(timeZone1.getEnd().v());
						//開始時刻2=（２枠目）時間帯(使用区分付き)．開始
						findDataOut.setStartTime2(timeZone2.getStart().v());
						//終了時刻2=（２枠目）時間帯(使用区分付き)．終了
						findDataOut.setEndTime2(timeZone2.getEnd().v());
					}
					return findDataOut;
				} else {
					//OUTPUT．出勤休日区分をチェックする
					if (workStyle == WorkStyle.AFTERNOON_WORK) {
						//開始時刻2=所定時間帯設定．午後開始時刻
						findDataOut.setStartTime2(prescribSeting.getAfternoonStartTime().v());
						//終了時刻2=（２枠目）時間帯(使用区分付き)．終了
						findDataOut.setEndTime2(timeZone2.getEnd().v());
						//１回勤務反映区分=false
						findDataOut.setCountReflect2Atr(false);
					} else if (workStyle == WorkStyle.MORNING_WORK) {
						//開始時刻=（１枠目）時間帯(使用区分付き)．開始
						findDataOut.setStartTime1(timeZone1.getStart().v());
						//終了時刻=（１枠目）時間帯(使用区分付き)．終了
						findDataOut.setEndTime1(timeZone1.getEnd().v());
						//開始時刻2=（２枠目）時間帯(使用区分付き)．開始
						findDataOut.setStartTime2(timeZone2.getStart().v());
						//終了時刻2=所定時間帯設定．午前終了時刻
						findDataOut.setEndTime2(prescribSeting.getMorningEndTime().v());						
					} else if (workStyle == WorkStyle.ONE_DAY_WORK) {
						//開始時刻=（１枠目）時間帯(使用区分付き)．開始
						findDataOut.setStartTime1(timeZone1.getStart().v());
						//終了時刻=（１枠目）時間帯(使用区分付き)．終了
						findDataOut.setEndTime1(timeZone1.getEnd().v());
						//開始時刻2=（２枠目）時間帯(使用区分付き)．開始
						findDataOut.setStartTime2(timeZone2.getStart().v());
						//終了時刻2=（２枠目）時間帯(使用区分付き)．終了
						findDataOut.setEndTime2(timeZone2.getEnd().v());
					}
					return findDataOut;
				}
			}
		}		
	}

}
