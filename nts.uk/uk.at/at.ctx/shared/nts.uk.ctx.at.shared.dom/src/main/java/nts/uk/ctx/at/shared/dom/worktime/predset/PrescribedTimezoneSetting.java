/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.error.BundledBusinessException;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class PrescribedTimezoneSetting.
 */
// 所定時間帯設定
@Getter
public class PrescribedTimezoneSetting extends DomainObject {

	/** The morning end time. */
	//午前終了時刻
	private TimeWithDayAttr morningEndTime;
	
	/** The afternoon start time. */
	//午後開始時刻
	private TimeWithDayAttr afternoonStartTime;
	
	/** The timezone. */
	//時間帯
	private List<TimezoneUse> lstTimezone;

	/** The size one. */
	public static Integer SIZE_ONE = 1;
	
	/**
	 * Instantiates a new prescribed timezone setting.
	 *
	 * @param memento the memento
	 */
	public PrescribedTimezoneSetting(PrescribedTimezoneSettingGetMemento memento) {
		this.morningEndTime = memento.getMorningEndTime();
		this.afternoonStartTime = memento.getAfternoonStartTime();
		this.lstTimezone = memento.getLstTimezone();
	}

	public boolean isUseShiftTwo() {
		return this.getTimezoneShiftTwo().isUsed();
	}

	/**
	 * Sets the morning work.
	 */
	public void setMorningWork() {
		if (this.isUseShiftTwo()) {
			// work time set update morning
			if (this.isMorningEndTimeLessThanOrEqualToShift2StartTime()) {

				// update end time shift 1 and remove shift 2
				this.setMorningEndTimeShiftOne();
				this.disableShiftTwo();
			} else {

				// update end time shift 2
				this.setMorningEndTimeShiftTwo();
			}
		} else {
			// update time shift 1 to end time morning
			this.setMorningEndTimeShiftOne();
			this.disableShiftTwo();
		}
	}

	/**
	 * Sets the afternoon work.
	 */
	public void setAfternoonWork() {
		if (this.isUseShiftTwo()) {
			if (this.isAfternoonStartTimeLessThanOrEqualToShift1EndTime()) {

				// update start time shift 1
				this.setAfternoonStartTimeShiftOne();
			} else {

				// update start time shift 2 and remove shift 1
				this.setAfternoonStartTimeShiftTwo();
				this.disableShiftOne();
			}
		} else {
			// update time shift 1 and remove shift 2
			this.setAfternoonStartTimeShiftOne();
			this.disableShiftTwo();
		}
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(PrescribedTimezoneSettingSetMemento memento){
		memento.setMorningEndTime(this.morningEndTime);
		memento.setAfternoonStartTime(this.afternoonStartTime);
		memento.setLstTimezone(this.lstTimezone);
	}

	/**
	 * Gets the timezone shift one.
	 *
	 * @return the timezone shift one
	 */
	public TimezoneUse getTimezoneShiftOne() {
		return this.getTimezone(TimezoneUse.SHIFT_ONE);
	}

	/**
	 * Gets the timezone shift two.
	 *
	 * @return the timezone shift two
	 */
	public TimezoneUse getTimezoneShiftTwo() {
		return this.getTimezone(TimezoneUse.SHIFT_TWO);
	}

	/**
	 * Update start time shift.
	 *
	 * @param newTime the new time
	 * @param workNo the work no
	 */
	public void updateStartTimeShift(TimeWithDayAttr newTime, int workNo) {
		TimezoneUse tz = this.getTimezone(workNo);
		tz.updateStartTime(newTime);
	}

	/**
	 * Update end time shift.
	 *
	 * @param newTime the new time
	 * @param workNo the work no
	 */
	public void updateEndTimeShift(TimeWithDayAttr newTime, int workNo) {
		TimezoneUse tz = this.getTimezone(workNo);
		tz.updateEndTime(newTime);
	}
	
	/**
	 * Update timezone shift.
	 *
	 * @param workNo the work no
	 * @param newStrTime the new str time
	 * @param newEndTime the new end time
	 */
	public void updateTimezoneShift(int workNo, TimeWithDayAttr newStrTime, TimeWithDayAttr newEndTime) {
		this.updateStartTimeShift(newStrTime, workNo);
		this.updateEndTimeShift(newEndTime, workNo);
	}
	
	/**
	 * Gets the timezone.
	 *
	 * @param workNo the work no
	 * @return the timezone
	 */
	private TimezoneUse getTimezone(int workNo) {
		return this.lstTimezone.stream()
			.filter(timezone -> timezone.getWorkNo() == workNo)
			.findFirst()
			.get();
	}

	/**
	 * Disable shift two.
	 */
	public void disableShiftTwo() {
		this.getTimezoneShiftTwo().disable();
	}

	/**
	 * Disable shift one.
	 */
	public void disableShiftOne() {
		this.getTimezoneShiftTwo().disable();
	}

	/**
	 * Checks if is morning end time less than or equal to shift 2 start time.
	 *
	 * @return true, if is morning end time less than or equal to shift 2 start time
	 */
	private boolean isMorningEndTimeLessThanOrEqualToShift2StartTime() {
		return this.morningEndTime.lessThanOrEqualTo(this.getTimezoneShiftTwo().getStart());
	}

	/**
	 * Checks if is afternoon start time less than or equal to shift 1 end time.
	 *
	 * @return true, if is afternoon start time less than or equal to shift 1 end time
	 */
	private boolean isAfternoonStartTimeLessThanOrEqualToShift1EndTime() {
		return this.afternoonStartTime.lessThanOrEqualTo(this.getTimezoneShiftOne().getEnd());
	}

	/**
	 * Sets the morning end time shift two.
	 */
	private void setMorningEndTimeShiftTwo() {
		this.getTimezoneShiftTwo().updateEndTime(this.morningEndTime);
	}

	/**
	 * Sets the morning end time shift one.
	 */
	private void setMorningEndTimeShiftOne() {
		this.getTimezoneShiftOne().updateEndTime(this.morningEndTime);
	}

	/**
	 * Sets the afternoon start time shift one.
	 */
	private void setAfternoonStartTimeShiftOne() {
		this.getTimezoneShiftOne().updateStartTime(this.afternoonStartTime);
	}

	/**
	 * Sets the afternoon start time shift two.
	 */
	private void setAfternoonStartTimeShiftTwo() {
		this.getTimezoneShiftTwo().updateStartTime(this.afternoonStartTime);
	}
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.dom.DomainObject#validate()
	 */
	@Override
	public void validate() {
		super.validate();
		
		// valid timezone must increase
		TimezoneUse tzWorkNo1 = this.getTimezoneShiftOne();
		if(this.lstTimezone.size()> SIZE_ONE){
			TimezoneUse tzWorkNo2 = this.getTimezoneShiftTwo();
			//TODO rcheck overlap 
			// valid: 2 時間帯 có 勤務NO=1 và 2 not overlap
//			boolean isWorkNoOverlap = this.getTimezone(SHIFT_ONE).getWorkNo() == this.getTimezone(SHIFT_TWO).getWorkNo();
//			if (isWorkNoOverlap) {
//				throw new BusinessException("Msg_771");
//			}
			
			// 使用する
			if (tzWorkNo2.isUsed()) {
				
				if (tzWorkNo2.getStart().lessThan(tzWorkNo1.getEnd())) {
					BundledBusinessException be = BundledBusinessException.newInstance();
					be.addMessage("Msg_772");
					be.throwExceptions();
				}
				
				//check Msg_774
				if (!tzWorkNo1.consistOf(this.getMorningEndTime()) && !tzWorkNo2.consistOf(this.getMorningEndTime())) {
					throw new BusinessException("Msg_774", "KMK003_39");
				}

				if (!tzWorkNo1.consistOf(this.getAfternoonStartTime())
						&& !tzWorkNo2.consistOf(this.getAfternoonStartTime())) {
					throw new BusinessException("Msg_774", "KMK003_40");
				}
			}
		}
		
		// valid 時間帯.終了 >= 0:01
		if (this.lstTimezone.stream().anyMatch(timezone -> timezone.isUsed()
				&& !timezone.getEnd().greaterThan(TimeWithDayAttr.THE_PRESENT_DAY_0000))) {
			throw new BusinessException("Msg_778");
		}
		
		/**
		 * 勤務NO2 = する, 勤務NO=1の開始～終了の間  or 勤務NO=2の開始～終了の間であること
		 * or 勤務NO2 = しない,  勤務NO=1の開始～終了の間であること
		 */
		this.validTimeDay();
	}
	
	/**
	 * Valid time day.
	 */
	private void validTimeDay() {
		// 使用しない
		if (!this.getTimezoneShiftTwo().isUsed()) {
			TimezoneUse tzWorkNo1 = this.getTimezoneShiftOne();
			if (!tzWorkNo1.consistOf(this.getAfternoonStartTime())) {
				throw new BusinessException("Msg_773", "KMK003_40");
			}

			if (!tzWorkNo1.consistOf(this.getMorningEndTime())) {
				throw new BusinessException("Msg_773", "KMK003_39");
			}
		}
	}

	/**
	 * Restore disabled data from.
	 *
	 * @param domain the domain
	 */
	public void restoreDisabledDataFrom(PrescribedTimezoneSetting domain) {
		int indexOfShift2 = this.lstTimezone.indexOf(this.getTimezoneShiftTwo());
		this.lstTimezone.set(indexOfShift2, domain.getTimezoneShiftTwo());
	}

	/**
	 * Restore default data.
	 */
	public void restoreDefaultData() {
		this.getTimezoneShiftTwo().restoreDefaultData();
	}

	/**
	 * 引数のNoと一致している勤務Noを持つ時間帯(使用区分付き)を取得する
	 * @param workNo
	 * @return 時間帯(使用区分付き)
	 *  @author keisuke_hoshina
	 */
	public TimezoneUse getMatchWorkNoTimeSheet(int workNo) {
		List<TimezoneUse> timeSheetWithUseAtrList = this.lstTimezone.stream().filter(tc -> tc.getWorkNo() == workNo).collect(Collectors.toList());
		if(timeSheetWithUseAtrList.size()>1) {
			throw new RuntimeException("Exist duplicate workNo : " + workNo);
		}
		return timeSheetWithUseAtrList.get(0);
	}
}
