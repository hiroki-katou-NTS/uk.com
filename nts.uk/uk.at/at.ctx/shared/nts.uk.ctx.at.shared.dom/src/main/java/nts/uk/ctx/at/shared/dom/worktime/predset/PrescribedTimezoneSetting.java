/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset;

import java.util.List;

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

}
