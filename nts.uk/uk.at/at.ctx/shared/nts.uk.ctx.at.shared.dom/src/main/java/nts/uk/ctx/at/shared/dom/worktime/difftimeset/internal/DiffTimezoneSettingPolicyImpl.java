/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset.internal;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.policy.DiffTimezoneSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class DiffTimezoneSettingPolicyImpl.
 */
@Stateless
public class DiffTimezoneSettingPolicyImpl implements DiffTimezoneSettingPolicy {

	/** The em tz policy. */
	@Inject
	private EmTimeZoneSetPolicy emTzPolicy;

	/** The ot set policy. */
	@Inject
	private OverTimeOfTimeZoneSetPolicy otSetPolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimezoneSettingPolicy#
	 * validate(nts.arc.error.BundledBusinessException,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimezoneSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode,
	 * nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr, boolean)
	 */
	@Override
	public void validate(BundledBusinessException be, PredetemineTimeSetting predTime, DiffTimezoneSetting diffTzSet,
			DisplayMode displayMode, AmPmAtr dayAtr, boolean useHalfDayShift) {

		// Validate list working timezone
		diffTzSet.getEmploymentTimezones().forEach(workingTimezone -> {
			this.emTzPolicy.validateFixedAndDiff(be, predTime, workingTimezone, displayMode, dayAtr, useHalfDayShift);
		});

		// Validate list OT timezone
		diffTzSet.getOTTimezones().forEach(workingTimezone -> {
			this.otSetPolicy.validate(be, predTime, workingTimezone, displayMode, dayAtr, useHalfDayShift);
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.policy.
	 * DiffTimezoneSettingPolicy#filterTimezone(nts.uk.ctx.at.shared.dom.
	 * worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimezoneSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode,
	 * nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr, boolean)
	 */
	public void filterTimezone(PredetemineTimeSetting predTime, DiffTimezoneSetting origin, DisplayMode displayMode,
			AmPmAtr dayAtr, boolean useHalfDayShift) {

		TimeWithDayAttr morningEndTime = predTime.getPrescribedTimezoneSetting().getMorningEndTime();
		TimeWithDayAttr afternoonStartTime = predTime.getPrescribedTimezoneSetting().getAfternoonStartTime();

		// Filter AM timezone
		if ((AmPmAtr.AM.equals(dayAtr) && DisplayMode.DETAIL.equals(displayMode) && !useHalfDayShift)
				|| (AmPmAtr.AM.equals(dayAtr) && DisplayMode.SIMPLE.equals(displayMode))) {

			// Filter work timezone
			List<EmTimeZoneSet> employmentTimezones = origin.getEmploymentTimezones().stream()
					.filter(timezone -> timezone.getTimezone().getStart().lessThanOrEqualTo(morningEndTime))
					.map(item -> {
						if (item.getTimezone().getStart().equals(morningEndTime)) {
							return null;
						}
						if (item.getTimezone().getStart().lessThan(morningEndTime)
								&& item.getTimezone().getEnd().greaterThan(morningEndTime)) {
							item.getTimezone().setEnd(morningEndTime);
							return item;
						}
						return item;
					}).filter(Objects::nonNull).collect(Collectors.toList());
			origin.setEmploymentTimezones(this.updateTimeFrameNo(employmentTimezones));

			// Filter OT timezone
//			List<DiffTimeOTTimezoneSet> oTTimezones = origin.getOTTimezones().stream()
//					.filter(timezone -> timezone.getTimezone().getStart().lessThanOrEqualTo(morningEndTime))
//					.map(item -> {
//						if (item.getTimezone().getStart().equals(morningEndTime)) {
//							return null;
//						}
//						if (item.getTimezone().getStart().lessThan(morningEndTime)
//								&& item.getTimezone().getEnd().greaterThan(morningEndTime)) {
//							item.getTimezone().setEnd(morningEndTime);
//							return item;
//						}
//						return item;
//					}).filter(Objects::nonNull).collect(Collectors.toList());
//			origin.setOTTimezones(this.updateTimeZoneNo(oTTimezones));
			return;
		}

		// Filter PM timezone
		if ((AmPmAtr.PM.equals(dayAtr) && DisplayMode.DETAIL.equals(displayMode) && !useHalfDayShift)
				|| (AmPmAtr.PM.equals(dayAtr) && DisplayMode.SIMPLE.equals(displayMode))) {

			// Filter work timezone
			List<EmTimeZoneSet> employmentTimezones = origin.getEmploymentTimezones().stream()
					.filter(timezone -> timezone.getTimezone().getEnd().greaterThanOrEqualTo(afternoonStartTime))
					.map(item -> {
						if (item.getTimezone().getEnd().equals(afternoonStartTime)) {
							return null;
						}
						if (item.getTimezone().getStart().lessThan(afternoonStartTime)
								&& item.getTimezone().getEnd().greaterThan(afternoonStartTime)) {
							item.getTimezone().setStart(afternoonStartTime);
							return item;
						}
						return item;
					}).filter(Objects::nonNull).collect(Collectors.toList());
			origin.setEmploymentTimezones(this.updateTimeFrameNo(employmentTimezones));

			// Filter OT timezone
//			List<DiffTimeOTTimezoneSet> oTTimezones = origin.getOTTimezones().stream()
//					.filter(timezone -> timezone.getTimezone().getEnd().greaterThanOrEqualTo(afternoonStartTime))
//					.map(item -> {
//						if (item.getTimezone().getEnd().equals(afternoonStartTime)) {
//							return null;
//						}
//						if (item.getTimezone().getStart().lessThan(afternoonStartTime)
//								&& item.getTimezone().getEnd().greaterThan(afternoonStartTime)) {
//							item.getTimezone().setStart(afternoonStartTime);
//							return item;
//						}
//						return item;
//					}).filter(Objects::nonNull).collect(Collectors.toList());
//			origin.setOTTimezones(this.updateTimeZoneNo(oTTimezones));
			return;
		}
	}

	/**
	 * Update time frame no.
	 *
	 * @param listItem
	 *            the list item
	 * @return the list
	 */
	private List<EmTimeZoneSet> updateTimeFrameNo(List<EmTimeZoneSet> listItem) {
		int timeFrameNo = 0;
		for (EmTimeZoneSet item : listItem) {
			timeFrameNo++;
			item.setEmploymentTimeFrameNo(new EmTimeFrameNo(timeFrameNo));
		}
		return listItem;
	}

	/**
	 * Update time zone no.
	 *
	 * @param listItem
	 *            the list item
	 * @return the list
	 */
//	private List<DiffTimeOTTimezoneSet> updateTimeZoneNo(List<DiffTimeOTTimezoneSet> listItem) {
//		int timeZoneNo = 0;
//		for (DiffTimeOTTimezoneSet item : listItem) {
//			timeZoneNo++;
//			item.setWorkTimezoneNo(new EmTimezoneNo(timeZoneNo));
//		}
//		return listItem;
//	}
}
