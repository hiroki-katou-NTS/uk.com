package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime;

import java.util.*;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainObject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.WorkInfoAndTimeZone;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.worktime.TimeLeaveChangeEvent;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.JustCorrectionAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 日別勤怠の出退勤
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.出退勤時刻.日別勤怠の出退勤
 * @author tutk
 *
 */
@Setter
@NoArgsConstructor
public class TimeLeavingOfDailyAttd implements DomainObject{
	
	// 1 ~ 2
	/** 出退勤 */
	@Getter
	private List<TimeLeavingWork> timeLeavingWorks = new ArrayList<>();
	
	/** 【削除予定】勤務回数 */
	private WorkTimes workTimes = new WorkTimes(0);
	
	public TimeLeavingOfDailyAttd(List<TimeLeavingWork> timeLeavingWorks, WorkTimes workTimes) {
		super();
		this.timeLeavingWorks = timeLeavingWorks;
		this.workTimes = workTimes;
	}
	
	@Override
	public void validate() {
		// inv-1
		this.timeLeavingWorks.stream().forEach(c -> {
			if (c.getWorkNo().v() > 2){
				throw new RuntimeException("TimeLeavingOfDailyAttd:validate WorkNo overflow.");
			}
		});
	}
	
	/**
	 * 出退勤の数から勤務回数を取得する
	 * 削除予定の属性で正常な値が入らない可能性がある為、自身の出退勤をカウントして返す
	 * @return 勤務回数
	 */
	public WorkTimes getWorkTimes() {
		return new WorkTimes(timeLeavingWorks.size());
	}
	
	/**
	 * [C-1] 所定時間帯で作る
	 * @param require
	 * @param companyId 会社ID
	 * @param workInformation 勤務情報
	 * @return
	 */
	public static TimeLeavingOfDailyAttd createByPredetermineZone(Require require, String companyId,
			WorkInformation workInformation) {
		
		Optional<WorkInfoAndTimeZone> workInfoAndTimeZone = workInformation.getWorkInfoAndTimeZone(require, companyId);
		if (! workInfoAndTimeZone.isPresent() ) {
			throw new RuntimeException("Invalid value!");
		}
		
		List<TimeZone> predetermineZoneList = workInfoAndTimeZone.get().getTimeZones();
		List<TimeLeavingWork> timeLeavingWorks = new ArrayList<>(); 
		for ( int index = 0; index < predetermineZoneList.size(); index++) {
			
			TimeLeavingWork newTimeLeavingWork = TimeLeavingWork.createFromTimeSpan( 
					new WorkNo(index + 1) ,
					new TimeSpanForCalc(
							predetermineZoneList.get(index).getStart(), 
							predetermineZoneList.get(index).getEnd()));
			
			timeLeavingWorks.add(newTimeLeavingWork);
		}

		return new TimeLeavingOfDailyAttd(
				timeLeavingWorks, 
				new WorkTimes(predetermineZoneList.size()));
	}
	
	public Optional<TimeLeavingWork> getAttendanceLeavingWork(int workNo) {
		return this.getTimeLeavingWorks().stream().filter(ts -> ts.getWorkNo().v() == workNo).findFirst();
	}

	/**
	 * 出退勤時刻と渡された範囲時間の重複していない部分の取得
	 * @param timeSpan 範囲時間
	 * @return 重複していない時間
	 */
	public List<TimeSpanForCalc> getNotDuplicateSpan(TimeSpanForCalc timeSpan) {
		if(timeSpan == null) return Collections.emptyList();
		List<TimeSpanForCalc> returnList = new ArrayList<>();
		List<TimeSpanForCalc> checkingList = new ArrayList<>(Arrays.asList(timeSpan));
		for (TimeLeavingWork tlw : this.timeLeavingWorks){
			returnList = new ArrayList<>();
			for (TimeSpanForCalc checking : checkingList){
				returnList.addAll(checking.getNotDuplicationWith(tlw.getTimespan()));
			}
			checkingList = new ArrayList<>(returnList);
		}
		return returnList;
	}
	
	/**
	 * 出退勤時刻と渡された範囲時間の重複していない部分の取得
	 * @param timeSpan 範囲時間
	 * @return 重複していない時間
	 */
	public List<TimeSpanForDailyCalc> getNotDuplicateSpan(TimeSpanForDailyCalc timeSpan) {
		return this.getNotDuplicateSpan(timeSpan.getTimeSpan()).stream()
				.map(forCalc -> new TimeSpanForDailyCalc(forCalc))
				.collect(Collectors.toList());
	}
	
	/**
	 * 指定した勤怠Noのデータを取得する
	 * @param workNo 勤怠No
	 * @return 出退勤クラス
	 */
	public Optional<TimeLeavingWork> getAttendanceLeavingWork(WorkNo workNo) {
		return this.timeLeavingWorks.stream().filter(ts -> ts.getWorkNo().v().intValue() == workNo.v().intValue()).findFirst();
	}
	
	/**
	 * 打刻漏れであるか判定する
	 * @return 打刻漏れである
	 */
	public boolean isLeakageStamp(){
		for(TimeLeavingWork timeLeavingWork:this.timeLeavingWorks) {
			//打刻漏れを起こしている(計算できる状態でない)
			if(!timeLeavingWork.checkLeakageStamp())
				return true;
		}
		return false;
	}
	
	/**
	 * 最後の退勤時刻を取得する
	 * @return 退勤時刻
	 */
	public Optional<TimeWithDayAttr> getLastLeaveTime() {
		Optional<TimeLeavingWork> last = this.timeLeavingWorks.stream()
				.filter(c->c.getLeaveTime().isPresent())
				.sorted((f, s) -> s.getWorkNo().compareTo(f.getWorkNo()))
				.findFirst();
		return last.flatMap(l -> l.getLeaveTime());
	}
	
	/**
	 * ジャスト遅刻、ジャスト早退の計算区分を見て時刻調整
	 * @param isJustTimeLateAttendance ジャスト遅刻を計算するか 
	 * @param isJustEarlyLeave ジャスト早退を計算するか
	 * @return 調整後の日別実績の出退勤クラス
	 */
	public TimeLeavingOfDailyAttd calcJustTime(boolean isJustTimeLateAttendance, boolean isJustEarlyLeave,
			JustCorrectionAtr justCorrectionAtr) {
		if (justCorrectionAtr.isNotUse())
			return this;
		List<TimeLeavingWork> newAttendanceLeave = new ArrayList<>();
		for (TimeLeavingWork attendanceLeave : this.timeLeavingWorks) {
			newAttendanceLeave.add(attendanceLeave.correctJustTimeCalcStamp(isJustTimeLateAttendance, isJustEarlyLeave));
		}

		return new TimeLeavingOfDailyAttd(newAttendanceLeave, this.workTimes);
	}
	
	/** <<Event>> 実績の出退勤が変更されたイベントを発行する */
	public void timeLeavesChanged(String employeeId,GeneralDate ymd) {
		TimeLeaveChangeEvent.builder().employeeId(employeeId).targetDate(ymd).timeLeave(this.timeLeavingWorks).build().toBePublished();
	}
	
	// 出退勤回数の計算
	public void setCountWorkTime() {
		this.workTimes = new WorkTimes((int) this.timeLeavingWorks.stream().filter(x -> {
			if (x.getAttendanceStamp().isPresent() && x.getAttendanceStamp().get().getStamp().isPresent()
					&& x.getAttendanceStamp().get().getStamp().get().getTimeDay() != null
					&& x.getLeaveStamp().isPresent() && x.getLeaveStamp().get().getStamp().isPresent()
					&& x.getLeaveStamp().get().getStamp().get().getTimeDay() != null) {
				return true;
			}
			return false;
		}).count());
	}
	
	/**
	 * 出退勤の時間帯を返す
	 * @return
	 */
	public List<TimeSpanForCalc> getTimeOfTimeLeavingAtt(){
		
		return this.timeLeavingWorks.stream()
				.sorted(Comparator.comparing(TimeLeavingWork::getWorkNo))
				.map(TimeLeavingWork::getTimespan)
				.collect(Collectors.toList());
	}

	/**
	 * 出退勤の最大時間帯を返す
	 * @return
	 */
	public TimeSpanForCalc getMaxSpan(){
				Map<Integer, TimeLeavingWork> tlwMap = this.timeLeavingWorks.stream()
				.collect(Collectors.toMap(lw -> lw.getWorkNo().v(), lw -> lw));
		// 1回目の開始時刻　～　最終回目の終了時刻
		return new TimeSpanForCalc(tlwMap.get(1).getTimespan().getStart(),tlwMap.get(tlwMap.size()).getTimespan().getEnd());
	}
	
	/**
	 * 勤務開始の休暇時間帯を取得する
	 * 
	 * @param workNo 勤務NO
	 *            
	 * @return
	 */
	public Optional<TimeSpanForCalc> getStartTimeVacations(WorkNo workNo) {
		return this.timeLeavingWorks.stream().filter(c -> c.getWorkNo().equals(workNo)).findFirst().map(c -> {
			return createTimeSpanForCalc(c.getAttendanceStamp());
		});
	}
	
	/**
	 * 勤務終了の休暇時間帯を取得する
	 * 
	 * @param workNo
	 *            勤務NO
	 * @return
	 */
	public Optional<TimeSpanForCalc> getEndTimeVacations(WorkNo workNo) {
		return this.timeLeavingWorks.stream().filter(c -> c.getWorkNo().equals(workNo)).findFirst().map(c -> {
			return createTimeSpanForCalc(c.getLeaveStamp());
		});
	}
	
	/**
	 * 勤怠打刻(実打刻付き)から、時間休暇時間帯をチェックして、時間休暇時間帯がある場合、計算時間帯を返す。
	 * @param timeActualStamp 勤怠打刻
	 * @return
	 */
	private TimeSpanForCalc createTimeSpanForCalc(Optional<TimeActualStamp> stamp) {
		if (stamp.isPresent() && stamp.get().getTimeVacation().isPresent()) {
			return new TimeSpanForCalc(stamp.get().getTimeVacation().get().getStart()
					, stamp.get().getTimeVacation().get().getEnd());
		}
		
		return null;
	}
	
	
	/**
	 * 勤務時間帯に完全包含するか
	 * @param target
	 * @return
	 */
	public boolean isIncludeInWorkTimeSpan(TimeSpanForCalc target) {
		List<TimeSpanForCalc> timeOfTimeLeavingList = this.getTimeOfTimeLeavingAtt();
		return timeOfTimeLeavingList.stream().anyMatch( timeLeaving -> timeLeaving.contains(target) );
	} 
	
	/**
	 * 打刻漏れ状態チェック
	 * @param workType 勤務種類
	 * @param predetemineTimeSet 所定時間設定
	 * @return 打刻漏れ状態
	 */
	public List<StampLeakStateEachWork> checkStampLeakState(
			Optional<WorkType> workType,
			Optional<PredetemineTimeSetting> predetemineTimeSet){
		
		List<StampLeakStateEachWork> result = new ArrayList<>();

		if (!workType.isPresent()) return result;
		WorkStyle workStyle = workType.get().checkWorkDay();	// 出勤休日区分
		if (workStyle == WorkStyle.ONE_DAY_REST) return result;

		int predWorkCount = 0;		// 所定勤務回数
		if (predetemineTimeSet.isPresent()) {
			predWorkCount = predetemineTimeSet.get().getTimezoneByAmPmAtrForCalc(
					workStyle.toAmPmAtr().orElse(AmPmAtr.ONE_DAY)).size();
		}
		
		for (int workNo = 1; workNo <= predWorkCount; workNo++) {
			Optional<TimeLeavingWork> timeLeavingWork = this.getAttendanceLeavingWork(new WorkNo(workNo));
			if (timeLeavingWork.isPresent()) {
				result.add(new StampLeakStateEachWork(new WorkNo(workNo), timeLeavingWork.get().checkStampLeakState()));
			}
		}
		if (predWorkCount > 0 && result.isEmpty()) {
			result.add(new StampLeakStateEachWork(new WorkNo(1), TLWStampLeakState.NOT_EXIST));
		}
		return result;
	}
	
	public static interface Require extends WorkInformation.Require{
		
	}
	
}
