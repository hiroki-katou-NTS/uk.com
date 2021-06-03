/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 固定勤務時間帯設定
 * The Class FixedWorkTimezoneSet.
 * <p>
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.就業時間帯.固定勤務設定.固定勤務の勤務時間帯.固定勤務時間帯設定
 */
@Getter
@Setter
@NoArgsConstructor
public class FixedWorkTimezoneSet extends WorkTimeDomainObject implements Cloneable{

	/**
	 * 就業時間帯
	 * The list of working timezone.
	 */
	private List<EmTimeZoneSet> lstWorkingTimezone;

	/**
	 * 残業時間帯
	 * The list of Overtime timezone.
	 */
	private List<OverTimeOfTimeZoneSet> lstOTTimezone;


	/** The Constant EMPLOYMENT_TIME_FRAME_NO_ONE. */
	public static final int EMPLOYMENT_TIME_FRAME_NO_ONE = 1;

	/** The Constant WORK_TIME_ZONE_NO_ONE. */
	public static final int WORK_TIME_ZONE_NO_ONE = 1;


	/**
	 * Instantiates a new fixed work timezone set.
	 *
	 * @param memento the memento
	 */
	public FixedWorkTimezoneSet(FixedWorkTimezoneSetGetMemento memento) {
		this.lstWorkingTimezone = memento.getLstWorkingTimezone();
		this.lstOTTimezone = memento.getLstOTTimezone();
		
		if (this.lstWorkingTimezone.size() == 0) {
		    throw new BusinessException("Msg_2182");
		}
	}

	/**
	 * [C-1] 新規作成する
	 *
	 * @param memento Memento
	 * @param useShiftTwo use double work?
	 */
	public FixedWorkTimezoneSet(List<EmTimeZoneSet> lstWorkingTimezone, List<OverTimeOfTimeZoneSet> lstOTTimezone, boolean useShiftTwo){
		this.lstWorkingTimezone = lstWorkingTimezone;
		this.lstOTTimezone = lstOTTimezone;
//		if (this.lstWorkingTimezone.size() == 0) {
//            throw new BusinessException("Msg_2182");
//        }
		if (!checkWorkingTimezoneContinue(useShiftTwo))
			this.bundledBusinessExceptions.addMessage("Msg_1919");
		if (!checkOTTimeZoneContinue(useShiftTwo))
			this.bundledBusinessExceptions.addMessage("Msg_1920");
		
	}

	/**
	 * 就業時間帯NOを指定して『残業時間の時間帯設定』を取得する
	 * Gets the over time of time zone set.
	 *
	 * @param workTimezoneNo the work timezone no
	 * @return the over time of time zone set
	 */
	public OverTimeOfTimeZoneSet getOverTimeOfTimeZoneSet(int workTimezoneNo) {
		return this.lstOTTimezone.stream()
				.filter(overtime -> overtime.getWorkTimezoneNo().v() == workTimezoneNo)
				.findFirst().get();
	}

	/**
	 * 就業時間枠NOを指定して『就業時間の時間帯設定』を取得する
	 * Gets the em time zone set.
	 *
	 * @param employmentTimeFrameNo the employment time frame no
	 * @return the em time zone set
	 */
	public EmTimeZoneSet getEmTimeZoneSet(int employmentTimeFrameNo) {
		return this.lstWorkingTimezone.stream()
				.filter(timezone -> timezone.getEmploymentTimeFrameNo().v() == employmentTimeFrameNo)
				.findFirst().get();
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.dom.DomainObject#validate()
	 */
	@Override
	public void validate() {
		this.checkOverlap();

		super.validate();
	}

	/**
	 * Checks if is in over timezone.
	 *
	 * @param timezone the timezone
	 * @return true, if is in over timezone
	 */
	public boolean isInOverTimezone(TimeZone timezone) {
		return this.lstOTTimezone.stream().anyMatch(ot -> timezone.isBetweenOrEqual(ot.getTimezone()));
	}

	/**
	 * Checks if is in em timezone.
	 *
	 * @param timezone the timezone
	 * @return true, if is in em timezone
	 */
	public boolean isInEmTimezone(TimeZone timezone) {
		return this.lstWorkingTimezone.stream().anyMatch(ot -> timezone.isBetweenOrEqual(ot.getTimezone()));
	}

	/**
	 * Check over time and em time overlap.
	 */
	public boolean isOverTimeAndEmTimeOverlap() {
		if (CollectionUtil.isEmpty(this.lstWorkingTimezone) || CollectionUtil.isEmpty(this.lstOTTimezone)) {
			return false;
		}

		return this.lstOTTimezone.stream()
				.anyMatch(ot -> this.lstWorkingTimezone.stream().anyMatch(em -> ot.getTimezone().isOverlap(em.getTimezone())));
	}

	/**
	 * 就業時間帯の連続性を確認
	 * Check the continuity of working timezone
	 *
	 * @param useShiftTwo do you use double work?
	 * @return status
	 */
	private boolean checkWorkingTimezoneContinue(boolean useShiftTwo){
		long discontinueTimes = this.lstWorkingTimezone
				.stream()
				.sorted(Comparator.comparing(EmTimeZoneSet::getEmploymentTimeFrameNo))
				.filter(wt -> {
					int nextIndex = this.lstWorkingTimezone.indexOf(wt) + 1;
					if (nextIndex < this.lstWorkingTimezone.size()){
						val nextWt = this.lstWorkingTimezone.get(nextIndex);
						return !wt.getTimezone().getEnd().equals(nextWt.getTimezone().getStart());
					}
					else return false;
				})
				.count();
		if (!useShiftTwo && discontinueTimes >= 1)
			return false;
		if (useShiftTwo && discontinueTimes > 1)
			return false;
		return true;
	}

	/**
	 * 残業時間帯の連続性を確認
	 * Check the continuity of overtime hours
	 *
	 * @param useShiftTwo do you use double work?
	 * @return status
	 */
	private boolean checkOTTimeZoneContinue(boolean useShiftTwo){
		long discontinueTimes = lstOTTimezone.stream()
				.sorted(Comparator.comparing(OverTimeOfTimeZoneSet::getWorkTimezoneNo))
				.filter(ot -> {
					int nextIndex = this.lstOTTimezone.indexOf(ot) + 1;
					if (nextIndex < this.lstOTTimezone.size()){
						val nextOt = this.lstOTTimezone.get(nextIndex);
						return !ot.getTimezone().getEnd().equals(nextOt.getTimezone().getStart()) && !ot.isEarlyOTUse();
					}
					else return false;
				})
				.count();
		if (!useShiftTwo && discontinueTimes >= 1)
			return false;
		if (useShiftTwo && discontinueTimes > 1)
			return false;
		return true;
	}

	/**
	 * Check overlap.
	 */
	private void checkOverlap() {
		if (!CollectionUtil.isEmpty(this.lstWorkingTimezone)) {
			val size = this.lstWorkingTimezone.size();
			for (int i = 0; i < size; i++) {
				for (int j = i + 1; j < size; j++) {
					if (this.lstWorkingTimezone.get(i).getTimezone()
							.isOverlap(this.lstWorkingTimezone.get(j).getTimezone())) {
						this.bundledBusinessExceptions.addMessage("Msg_515", "KMK003_86");
					}
				}
			}
		}

		if (!CollectionUtil.isEmpty(this.lstOTTimezone)) {
			val size = this.lstOTTimezone.size();
			for (int i = 0; i < size; i++) {
				for (int j = i + 1; j < size; j++) {
					if (this.lstOTTimezone.get(i).getTimezone()
							.isOverlap(this.lstOTTimezone.get(j).getTimezone())) {
						this.bundledBusinessExceptions.addMessage("Msg_515", "KMK003_89");
					}
				}
			}
		}

	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FixedWorkTimezoneSetSetMemento memento){
		memento.setLstWorkingTimezone(this.lstWorkingTimezone);
		memento.setLstOTTimezone(this.lstOTTimezone);
	}

	/**
	 * Restore data.
	 *
	 * @param other the other
	 */
	public void restoreData(FixedWorkTimezoneSet other) {
		// restore 就業時間帯
		Map<EmTimeFrameNo, EmTimeZoneSet> mapEmTimezone = other.getLstWorkingTimezone().stream().collect(
				Collectors.toMap(item -> ((EmTimeZoneSet) item).getEmploymentTimeFrameNo(), Function.identity()));
		if (mapEmTimezone.isEmpty()) {
			this.lstWorkingTimezone = new ArrayList<>();
		} else {
			this.lstWorkingTimezone.forEach(emTimezoneOther -> {
				emTimezoneOther.restoreData(mapEmTimezone.get(emTimezoneOther.getEmploymentTimeFrameNo()));
			});
		}

		// restore OTTimezone
		Map<EmTimezoneNo, OverTimeOfTimeZoneSet> mapOverTimezone = other.getLstOTTimezone().stream().collect(
				Collectors.toMap(item -> ((OverTimeOfTimeZoneSet) item).getWorkTimezoneNo(), Function.identity()));
		if (mapOverTimezone.isEmpty()) {
			this.lstOTTimezone = new ArrayList<>();
		} else {
			this.lstOTTimezone.forEach(overTimezoneOther -> {
				overTimezoneOther.restoreData(mapOverTimezone.get(overTimezoneOther.getWorkTimezoneNo()));
			});
		}
	}

	/**
	 * Restore default data.
	 */
	public void restoreDefaultData() {
//		this.lstWorkingTimezone = new ArrayList<>();
		this.lstOTTimezone = new ArrayList<>();
	}

	/**
	 * Correct default data.
	 */
	public void correctDefaultData() {
		this.lstOTTimezone.forEach(item -> item.correctDefaultData());
	}

	@Override
	public FixedWorkTimezoneSet clone() {
		FixedWorkTimezoneSet cloned = new FixedWorkTimezoneSet();
		try {
			cloned.lstWorkingTimezone = this.lstWorkingTimezone.stream().map(c -> c.clone()).collect(Collectors.toList());
			cloned.lstOTTimezone = this.lstOTTimezone.stream().map(c -> c.clone()).collect(Collectors.toList());
		}
		catch (Exception e){
			throw new RuntimeException("FixedWorkTimezoneSet clone error.");
		}
		return cloned;
	}


	/**
	 * 計算時間帯として就業時間帯リストを取得する
	 * @return 就業時間帯リスト(計算時間帯)
	 */
	public List<TimeSpanForCalc> getWorkingTimezonesForCalc() {
		return this.lstWorkingTimezone.stream()
				.sorted(Comparator.comparing( e -> e.getEmploymentTimeFrameNo().v() ))
				.map( e -> e.getTimezone().timeSpan() )
				.collect(Collectors.toList());
	}

	/**
	 * 就業時間の時間帯
	 * @return 初回の開始時刻～最終の終了時刻
	 */
	public TimeSpanForCalc getFirstAndLastTimeOfWorkingTimezone() {
		return TimeSpanForCalc.join( this.getWorkingTimezonesForCalc() ).get();
	}


	/**
	 * 計算時間帯として残業時間帯リストを取得する
	 * @return 残業時間帯リスト(計算時間帯)
	 */
	public List<TimeSpanForCalc> getOvertimeWorkingTimezonesForCalc() {
		return this.lstOTTimezone.stream()
				.sorted(Comparator.comparing( e -> e.getWorkTimezoneNo().v() ))
				.map( e -> e.getTimezone().timeSpan() )
				.collect(Collectors.toList());
	}

	/**
	 * 残業の時間帯
	 * @return 初回の開始時刻～最終の終了時刻
	 */
	public Optional<TimeSpanForCalc> getFirstAndLastTimeOfOvertimeWorkingTimezone() {
		return TimeSpanForCalc.join( this.getOvertimeWorkingTimezonesForCalc() );
	}
	
	/**
	 * 所定時間内に含まれる就業時間帯を取得する
	 * @param predTimeZone 所定時間設定
	 * @return 就業時間の時間帯設定List
	 */
	public List<EmTimeZoneSet> getWorkTimeSpanWithinPred(TimezoneUse predTimeZone){
		List<EmTimeZoneSet> result = new ArrayList<>();
		// 使用するしない判断
		if (predTimeZone.isUsed()){
			// 取得した所定時間内の就業時間帯を取得
			for (EmTimeZoneSet workTimeZone : this.lstWorkingTimezone){
				Optional<TimeSpanForCalc> dupSpanOpt =
						workTimeZone.getTimezone().getDuplicatedWith(predTimeZone.timeSpan());
				if (!dupSpanOpt.isPresent()) continue;
				TimeSpanForCalc dupSpan = dupSpanOpt.get();
				result.add(new EmTimeZoneSet(
						workTimeZone.getEmploymentTimeFrameNo(),
						new TimeZoneRounding(
								dupSpan.getStart(),
								dupSpan.getEnd(),
								workTimeZone.getTimezone().getRounding())));
			}
		}
		return result;
	}
}
