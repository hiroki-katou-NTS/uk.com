/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
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

	/** The shift one. */
	public static Integer SHIFT_ONE = 1;
	
	/** The shift two. */
	public static Integer SHIFT_TWO = 2;
	
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
		return this.getTimezone(SHIFT_ONE);
	}

	/**
	 * Gets the timezone shift two.
	 *
	 * @return the timezone shift two
	 */
	public TimezoneUse getTimezoneShiftTwo() {
		return this.getTimezone(SHIFT_TWO);
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
		TimezoneUse tzWorkNo1 = this.getTimezone(SHIFT_ONE);
		TimezoneUse tzWorkNo2 = this.getTimezone(SHIFT_TWO);
		if (tzWorkNo2.getStart().lessThanOrEqualTo(tzWorkNo1.getEnd())) {
			throw new BusinessException("Msg_772");
		}
		
		// valid 時間帯.終了 >= 0:01
		boolean isValidEnd = this.lstTimezone.stream()
				.filter(timezone -> timezone.getEnd().lessThan(TimeWithDayAttr.THE_PRESENT_DAY_0000))
				.collect(Collectors.toList())
				.isEmpty();
		if (!isValidEnd) {
			throw new BusinessException("Msg_778");
		}
		
		// TODO: valid message Msg_516
		
		// valid: 2 時間帯 có 勤務NO=1 và 2 not overlap
		boolean isWorkNoOverlap = this.getTimezone(SHIFT_ONE).getWorkNo() == this.getTimezone(SHIFT_TWO).getWorkNo();
		if (isWorkNoOverlap) {
			throw new BusinessException("Msg_771");
		}
		
		/**
		 * 勤務NO2 = する, 勤務NO=1の開始～終了の間  or 勤務NO=2の開始～終了の間であること
		 * or 勤務NO2 = しない,  勤務NO=1の開始～終了の間であること
		 */
		validTimeDay();
	}
	
	/**
	 * Valid time day.
	 */
	private void validTimeDay() {
		// 使用しない
		if (this.getTimezone(SHIFT_TWO).getUseAtr() == UseSetting.NOT_USE) {
			
			//get timezone workno#1
			TimezoneUse tzWorkNo1 = this.getTimezone(SHIFT_ONE);
			
			boolean isInValidTimeEndMorning = this.getMorningEndTime().lessThan(tzWorkNo1.getStart())
					|| this.getMorningEndTime().greaterThan(tzWorkNo1.getEnd());
			
			boolean isInValidTimeStrAfternoon = this.getAfternoonStartTime().lessThan(tzWorkNo1.getStart())
					|| this.getAfternoonStartTime().greaterThan(tzWorkNo1.getEnd());
			
			if (isInValidTimeEndMorning || isInValidTimeStrAfternoon) {
				throw new BusinessException("Msg_773");
			}
		}
		// 使用する
		else {
			boolean isInValidTimeDay = this.lstTimezone.stream().filter(timezone -> {
				boolean isValidTimeEndMorning = this.getMorningEndTime().greaterThanOrEqualTo(timezone.getStart())
						&& this.getMorningEndTime().lessThanOrEqualTo(timezone.getEnd());
				boolean isValidTimeStrAfternoon = this.getAfternoonStartTime().greaterThanOrEqualTo(timezone.getStart())
						&& this.getAfternoonStartTime().lessThanOrEqualTo(timezone.getEnd());
				return isValidTimeEndMorning || isValidTimeStrAfternoon;
			}).collect(Collectors.toList()).isEmpty();
			if (isInValidTimeDay) {
				throw new BusinessException("Msg_774");
			}
		}
	}
	
}
