/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRoundingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class EmTimeZoneSetPolicyImpl.
 */
@Stateless
public class EmTimeZoneSetPolicyImpl implements EmTimeZoneSetPolicy {

	/** The tzr policy. */
	@Inject
	private TimeZoneRoundingPolicy tzrPolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSetPolicy#validate(nts
	 * .uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet)
	 */
	@Override
	public void validate(BundledBusinessException be, PredetemineTimeSetting predTime, EmTimeZoneSet etzSet,
			DisplayMode displayMode, AmPmAtr dayAtr, boolean useHalfDayShift) {
		TimeZoneRounding emTimezone = etzSet.getTimezone();
		PrescribedTimezoneSetting presTz = predTime.getPrescribedTimezoneSetting();

		// Msg_516
		if (this.tzrPolicy.validateRange(predTime, emTimezone)) {
			be.addMessage("Msg_516", "KMK003_86");
		}

		// Msg_773
		if (this.isTimezoneNotValid(presTz, emTimezone)) {
			be.addMessage("Msg_773", "KMK003_86");
		}

		// Msg_774 + Msg_1054 + Msg_1055
		if (presTz.getTimezoneShiftTwo().isUsed()) {
			// Msg_774
			if (AmPmAtr.ONE_DAY.equals(dayAtr)) {
				if (!this.isBetweenOneDayTimezone(presTz, emTimezone)) {
					be.addMessage("Msg_774", "KMK003_86");
				}
			} else if (AmPmAtr.AM.equals(dayAtr)) {
				if (!isBetweenMorningTimezone(presTz, emTimezone)) {
					be.addMessage("Msg_774", "KMK003_295");
				}
			} else if (AmPmAtr.PM.equals(dayAtr)) {
				if (!isBetweenAfternoonTimezone(presTz, emTimezone)) {
					be.addMessage("Msg_774", "KMK003_296");
				}
			}
		} else {
			if (AmPmAtr.AM.equals(dayAtr) && DisplayMode.DETAIL.equals(displayMode) && useHalfDayShift) {
				// Msg_1054
				if (!isBetweenMorningTimezone(presTz, emTimezone)) {
					be.addMessage("Msg_1054", "KMK003_295");
				}
			} else if (AmPmAtr.PM.equals(dayAtr) && DisplayMode.DETAIL.equals(displayMode) && useHalfDayShift) {
				// Msg_1055
				if (!isBetweenAfternoonTimezone(presTz, emTimezone)) {
					be.addMessage("Msg_1055", "KMK003_296");
				}
			}
		}

	}

	/**
	 * Checks if is timezone not valid.
	 *
	 * @param presTz
	 *            the pres tz
	 * @param emTimezone
	 *            the em timezone
	 * @return true, if is timezone not valid
	 */
	private boolean isTimezoneNotValid(PrescribedTimezoneSetting presTz, TimeZoneRounding emTimezone) {
		return (!presTz.getTimezoneShiftTwo().isUsed() && !emTimezone.isBetweenOrEqual(presTz.getTimezoneShiftOne()))
				? true : false;
	}

	/**
	 * Checks if is between morning timezone.
	 *
	 * @param presTz
	 *            the pres tz
	 * @param emTimezone
	 *            the em timezone
	 * @return true, if is between morning timezone
	 */
	private boolean isBetweenMorningTimezone(PrescribedTimezoneSetting presTz, TimeZoneRounding emTimezone) {
		if (presTz.getTimezoneShiftTwo().isUsed()) {
			if (presTz.getMorningEndTime().greaterThanOrEqualTo(presTz.getTimezoneShiftOne().getStart())
					&& presTz.getMorningEndTime().lessThanOrEqualTo(presTz.getTimezoneShiftOne().getEnd())) {
				// Break time in shift one
				TimeWithDayAttr startTime = presTz.getTimezoneShiftOne().getStart();
				TimeWithDayAttr endTime = presTz.getMorningEndTime();
				return emTimezone.getStart().greaterThanOrEqualTo(startTime)
						&& emTimezone.getEnd().lessThanOrEqualTo(endTime);
			} else if (presTz.getMorningEndTime().greaterThanOrEqualTo(presTz.getTimezoneShiftTwo().getStart())
					&& presTz.getMorningEndTime().lessThanOrEqualTo(presTz.getTimezoneShiftTwo().getEnd())) {
				// Break time in shift two
				TimeWithDayAttr startTime = presTz.getTimezoneShiftTwo().getStart();
				TimeWithDayAttr endTime = presTz.getMorningEndTime();
				return emTimezone.getStart().greaterThanOrEqualTo(startTime)
						&& emTimezone.getEnd().lessThanOrEqualTo(endTime);
			}
			return false;
		} else {
			TimeWithDayAttr startTime = presTz.getTimezoneShiftOne().getStart();
			TimeWithDayAttr endTime = presTz.getMorningEndTime();
			return emTimezone.getStart().greaterThanOrEqualTo(startTime)
					&& emTimezone.getEnd().lessThanOrEqualTo(endTime);
		}
	}

	/**
	 * Checks if is between afternoon timezone.
	 *
	 * @param presTz
	 *            the pres tz
	 * @param emTimezone
	 *            the em timezone
	 * @return true, if is between afternoon timezone
	 */
	private boolean isBetweenAfternoonTimezone(PrescribedTimezoneSetting presTz, TimeZoneRounding emTimezone) {
		if (presTz.getTimezoneShiftTwo().isUsed()) {
			if (presTz.getMorningEndTime().greaterThanOrEqualTo(presTz.getTimezoneShiftOne().getStart())
					&& presTz.getMorningEndTime().lessThanOrEqualTo(presTz.getTimezoneShiftOne().getEnd())) {
				// Break time in shift one
				TimeWithDayAttr startTime = presTz.getAfternoonStartTime();
				TimeWithDayAttr endTime = presTz.getTimezoneShiftOne().getEnd();
				return emTimezone.getStart().greaterThanOrEqualTo(startTime)
						&& emTimezone.getEnd().lessThanOrEqualTo(endTime);
			} else if (presTz.getMorningEndTime().greaterThanOrEqualTo(presTz.getTimezoneShiftTwo().getStart())
					&& presTz.getMorningEndTime().lessThanOrEqualTo(presTz.getTimezoneShiftTwo().getEnd())) {
				// Break time in shift two
				TimeWithDayAttr startTime = presTz.getAfternoonStartTime();
				TimeWithDayAttr endTime = presTz.getTimezoneShiftTwo().getEnd();
				return emTimezone.getStart().greaterThanOrEqualTo(startTime)
						&& emTimezone.getEnd().lessThanOrEqualTo(endTime);
			}
			return false;
		} else {
			TimeWithDayAttr startTime = presTz.getAfternoonStartTime();
			TimeWithDayAttr endTime = presTz.getTimezoneShiftOne().getEnd();
			return emTimezone.getStart().greaterThanOrEqualTo(startTime)
					&& emTimezone.getEnd().lessThanOrEqualTo(endTime);
		}
	}

	/**
	 * Checks if is between one day timezone.
	 *
	 * @param presTz
	 *            the pres tz
	 * @param emTimezone
	 *            the em timezone
	 * @return true, if is between one day timezone
	 */
	private boolean isBetweenOneDayTimezone(PrescribedTimezoneSetting presTz, TimeZoneRounding emTimezone) {
		return (emTimezone.isBetweenOrEqual(presTz.getTimezoneShiftOne())
				|| emTimezone.isBetweenOrEqual(presTz.getTimezoneShiftTwo()));
	}

}
