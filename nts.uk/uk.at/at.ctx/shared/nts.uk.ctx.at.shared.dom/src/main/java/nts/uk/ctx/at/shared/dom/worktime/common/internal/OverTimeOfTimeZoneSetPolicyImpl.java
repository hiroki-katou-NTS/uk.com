/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRoundingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.predset.UseSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class OverTimeOfTimeZoneSetPolicyImpl.
 */
@Stateless
public class OverTimeOfTimeZoneSetPolicyImpl implements OverTimeOfTimeZoneSetPolicy {

	/** The tzr policy. */
	@Inject
	private TimeZoneRoundingPolicy tzrPolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSetPolicy#
	 * validate(nts.arc.error.BundledBusinessException,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSet,
	 * nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode,
	 * nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr, boolean)
	 */
	@Override
	public void validate(BundledBusinessException be, PredetemineTimeSetting predTime, OverTimeOfTimeZoneSet otSet,
			DisplayMode displayMode, AmPmAtr dayAtr, boolean useHalfDayShift) {
		TimeZoneRounding otTimezone = otSet.getTimezone();
		TimezoneUse shift1Timezone = predTime.getPrescribedTimezoneSetting().getTimezoneShiftOne();
		TimezoneUse shift2Timezone = predTime.getPrescribedTimezoneSetting().getTimezoneShiftTwo();
		PrescribedTimezoneSetting presTz = predTime.getPrescribedTimezoneSetting();

		// Msg_516
		if (this.tzrPolicy.validateRange(predTime, otTimezone)) {
			be.addMessage("Msg_516", "KMK003_89");
		}

		// Msg_519
		if (!predTime.isPredetermine() && AmPmAtr.ONE_DAY.equals(dayAtr)) {
			if (predTime.getPrescribedTimezoneSetting().getLstTimezone().stream()
					.filter(shift -> UseSetting.USE.equals(shift.getUseAtr()))
					.anyMatch(shift -> otTimezone.isOverlap(shift))) {
				be.addMessage("Msg_519", "KMK003_89");
			}
		}

		// Msg_779
		if (!predTime.isPredetermine() && AmPmAtr.ONE_DAY.equals(dayAtr) && !otSet.isEarlyOTUse()) {
			if (((otTimezone.getStart().lessThan(shift1Timezone.getEnd())
					|| otTimezone.getEnd().lessThan(shift1Timezone.getEnd())))) {
				be.addMessage("Msg_779");
			}
		}
		if (!predTime.isPredetermine() && AmPmAtr.AM.equals(dayAtr) && !otSet.isEarlyOTUse() && !presTz.isUseShiftTwo()
				&& DisplayMode.DETAIL.equals(displayMode) && useHalfDayShift) {
			if (((otTimezone.getStart().lessThan(presTz.getMorningEndTime())
					|| otTimezone.getEnd().lessThan(presTz.getMorningEndTime())))) {
				be.addMessage("Msg_779");
			}
		}
		if (!predTime.isPredetermine() && AmPmAtr.AM.equals(dayAtr) && !otSet.isEarlyOTUse() && presTz.isUseShiftTwo()
				&& DisplayMode.DETAIL.equals(displayMode) && useHalfDayShift) {
			TimeWithDayAttr startTime1 = shift1Timezone.getStart();
			TimeWithDayAttr endTime1 = shift1Timezone.getEnd();
			TimeWithDayAttr startTime2 = shift2Timezone.getStart();
			TimeWithDayAttr endTime2 = shift2Timezone.getEnd();
			if (startTime1.lessThanOrEqualTo(presTz.getMorningEndTime())
					&& endTime1.greaterThanOrEqualTo(presTz.getMorningEndTime())) {
				if (((otTimezone.getStart().lessThan(presTz.getMorningEndTime())
						|| otTimezone.getEnd().lessThan(presTz.getMorningEndTime())))) {
					be.addMessage("Msg_779");
				}
			}
			if (startTime2.lessThanOrEqualTo(presTz.getMorningEndTime())
					&& endTime2.greaterThanOrEqualTo(presTz.getMorningEndTime())) {
				if (((otTimezone.getStart().lessThan(endTime1) || otTimezone.getEnd().lessThan(endTime1)))) {
					be.addMessage("Msg_779");
				}
			}
		}
		if (!predTime.isPredetermine() && AmPmAtr.PM.equals(dayAtr) && !otSet.isEarlyOTUse() && !presTz.isUseShiftTwo()
				&& DisplayMode.DETAIL.equals(displayMode) && useHalfDayShift) {
			if (((otTimezone.getStart().lessThan(shift1Timezone.getEnd())
					|| otTimezone.getEnd().lessThan(shift1Timezone.getEnd())))) {
				be.addMessage("Msg_779");
			}
		}
		if (!predTime.isPredetermine() && AmPmAtr.PM.equals(dayAtr) && !otSet.isEarlyOTUse() && presTz.isUseShiftTwo()
				&& DisplayMode.DETAIL.equals(displayMode) && useHalfDayShift) {
			TimeWithDayAttr startTime1 = shift1Timezone.getStart();
			TimeWithDayAttr endTime1 = shift1Timezone.getEnd();
			TimeWithDayAttr startTime2 = shift2Timezone.getStart();
			TimeWithDayAttr endTime2 = shift2Timezone.getEnd();
			if (startTime1.lessThanOrEqualTo(presTz.getAfternoonStartTime())
					&& endTime1.greaterThanOrEqualTo(presTz.getAfternoonStartTime())) {
				if (((otTimezone.getStart().lessThan(endTime1) || otTimezone.getEnd().lessThan(endTime1)))) {
					be.addMessage("Msg_779");
				}
			}
			if (startTime2.lessThanOrEqualTo(presTz.getAfternoonStartTime())
					&& endTime2.greaterThanOrEqualTo(presTz.getAfternoonStartTime())) {
				if (((otTimezone.getStart().lessThan(endTime2) || otTimezone.getEnd().lessThan(endTime2)))) {
					be.addMessage("Msg_779");
				}
			}
		}

		// Msg_780
		if (!predTime.isPredetermine() && AmPmAtr.ONE_DAY.equals(dayAtr) && otSet.isEarlyOTUse()) {
			if (((otTimezone.getStart().greaterThan(shift1Timezone.getStart())
					|| otTimezone.getEnd().greaterThan(shift1Timezone.getStart())))) {
				be.addMessage("Msg_780");
			}
		}
		if (!predTime.isPredetermine() && (AmPmAtr.AM.equals(dayAtr) || AmPmAtr.PM.equals(dayAtr))
				&& otSet.isEarlyOTUse() && DisplayMode.DETAIL.equals(displayMode) && useHalfDayShift) {
			if (((otTimezone.getStart().greaterThan(shift1Timezone.getStart())
					|| otTimezone.getEnd().greaterThan(shift1Timezone.getStart())))) {
				be.addMessage("Msg_780");
			}
		}

		// Msg_1035
		if (!predTime.isPredetermine() && AmPmAtr.AM.equals(dayAtr) && DisplayMode.DETAIL.equals(displayMode)
				&& useHalfDayShift && !presTz.isUseShiftTwo()) {
			if (isOverlap(otTimezone, shift1Timezone.getStart(), presTz.getMorningEndTime())) {
				be.addMessage("Msg_1035");
			}
		}

		if (!predTime.isPredetermine() && AmPmAtr.AM.equals(dayAtr) && DisplayMode.DETAIL.equals(displayMode)
				&& useHalfDayShift && presTz.isUseShiftTwo()) {
			TimeWithDayAttr startTime1 = shift1Timezone.getStart();
			TimeWithDayAttr endTime1 = shift1Timezone.getEnd();
			TimeWithDayAttr startTime2 = shift2Timezone.getStart();
			TimeWithDayAttr endTime2 = shift2Timezone.getEnd();

			if (startTime1.lessThanOrEqualTo(presTz.getMorningEndTime())
					&& endTime1.greaterThanOrEqualTo(presTz.getMorningEndTime())) {
				if (isOverlap(otTimezone, startTime1, presTz.getMorningEndTime())) {
					be.addMessage("Msg_1035");
				}
			}

			if (startTime2.lessThanOrEqualTo(presTz.getMorningEndTime())
					&& endTime2.greaterThanOrEqualTo(presTz.getMorningEndTime())) {
				if (isOverlap(otTimezone, startTime1, endTime1)
						|| isOverlap(otTimezone, startTime2, presTz.getMorningEndTime())) {
					be.addMessage("Msg_1035");
				}
			}
		}

		// Msg_1036
		if (!predTime.isPredetermine() && AmPmAtr.PM.equals(dayAtr) && DisplayMode.DETAIL.equals(displayMode)
				&& useHalfDayShift && !presTz.isUseShiftTwo()) {
			if (isOverlap(otTimezone, presTz.getAfternoonStartTime(), shift1Timezone.getEnd())) {
				be.addMessage("Msg_1036");
			}
		}

		if (!predTime.isPredetermine() && AmPmAtr.PM.equals(dayAtr) && DisplayMode.DETAIL.equals(displayMode)
				&& useHalfDayShift && presTz.isUseShiftTwo()) {
			TimeWithDayAttr startTime1 = shift1Timezone.getStart();
			TimeWithDayAttr endTime1 = shift1Timezone.getEnd();
			TimeWithDayAttr startTime2 = shift2Timezone.getStart();
			TimeWithDayAttr endTime2 = shift2Timezone.getEnd();

			if (startTime1.lessThanOrEqualTo(presTz.getMorningEndTime())
					&& endTime1.greaterThanOrEqualTo(presTz.getMorningEndTime())) {
				if (isOverlap(otTimezone, presTz.getAfternoonStartTime(), endTime1)
						|| isOverlap(otTimezone, startTime2, endTime2)) {
					be.addMessage("Msg_1036");
				}
			}

			if (startTime2.lessThanOrEqualTo(presTz.getMorningEndTime())
					&& endTime2.greaterThanOrEqualTo(presTz.getMorningEndTime())) {
				if (isOverlap(otTimezone, presTz.getAfternoonStartTime(), endTime2)) {
					be.addMessage("Msg_1036");
				}
			}
		}
	}

	/**
	 * Checks if is overlap.
	 *
	 * @param otTimezone
	 *            the ot timezone
	 * @param startTime
	 *            the start time
	 * @param endTime
	 *            the end time
	 * @return true, if is overlap
	 */
	private boolean isOverlap(TimeZoneRounding otTimezone, TimeWithDayAttr startTime, TimeWithDayAttr endTime) {
		return (otTimezone.getStart().greaterThanOrEqualTo(startTime)
				|| otTimezone.getEnd().lessThanOrEqualTo(endTime));
	}
}
