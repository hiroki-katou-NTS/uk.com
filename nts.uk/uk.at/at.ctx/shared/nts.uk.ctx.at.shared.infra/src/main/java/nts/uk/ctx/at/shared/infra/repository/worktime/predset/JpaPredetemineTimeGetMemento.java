/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.predset;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.BreakDownTimeDay;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetermineTime;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.Timezone;
import nts.uk.ctx.at.shared.dom.worktime.predset.UseSetting;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtPredTimeSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtWorkTimeSheetSet;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaPredetemineTimeGetMemento.
 */
public class JpaPredetemineTimeGetMemento implements PredetemineTimeGetMemento {

	/** The kwtst work time set. */
	private KshmtPredTimeSet kshmtPredTimeSet;

	private List<KshmtWorkTimeSheetSet> lstKshmtWorkTimeSheetSet;
	/** The Constant TRUE_VAL. */
	private static final Integer TRUE_VAL = 1;

	/**
	 * Instantiates a new jpa predetemine time get memento.
	 *
	 * @param kwtstWorkTimeSet
	 *            the kwtst work time set
	 */
	public JpaPredetemineTimeGetMemento(KshmtPredTimeSet kshmtPredTimeSet,List<KshmtWorkTimeSheetSet> kshmtWorkTimeSheetSet) {
		this.kshmtPredTimeSet = kshmtPredTimeSet;
		this.lstKshmtWorkTimeSheetSet = kshmtWorkTimeSheetSet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.predset.PredetemineTimeGetMemento#getCompanyID()
	 */
	@Override
	public String getCompanyID() {
		return this.kshmtPredTimeSet.getKshmtPredTimeSetPK().getCid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeGetMemento#
	 * getRangeTimeDay()
	 */
	@Override
	public AttendanceTime getRangeTimeDay() {
		return new AttendanceTime(this.kshmtPredTimeSet.getRangeTimeDay());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.predset.PredetemineTimeGetMemento#getSiftCD()
	 */
	@Override
	public WorkTimeCode getWorkTimeCode() {
		return new WorkTimeCode(this.kshmtPredTimeSet.getKshmtPredTimeSetPK().getWorktimeCd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeGetMemento#
	 * getAdditionSetID()
	 */
	@Override
	public PredetermineTime getPredTime() {
		BreakDownTimeDay addTime = BreakDownTimeDay.builder()
				.oneDay(new AttendanceTime(this.kshmtPredTimeSet.getWorkAddOneDay()))
				.morning(new AttendanceTime(this.kshmtPredTimeSet.getWorkAddMorning()))
				.afternoon(new AttendanceTime(this.kshmtPredTimeSet.getWorkAddAfternoon())).build();
		BreakDownTimeDay predTime = BreakDownTimeDay.builder()
				.oneDay(new AttendanceTime(this.kshmtPredTimeSet.getPredOneDay()))
				.morning(new AttendanceTime(this.kshmtPredTimeSet.getPredMorning()))
				.afternoon(new AttendanceTime(this.kshmtPredTimeSet.getPredAfternoon())).build();
		return PredetermineTime.builder().addTime(addTime).predTime(predTime).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.predset.PredetemineTimeGetMemento#isNightShift()
	 */
	@Override
	public boolean isNightShift() {
		return this.kshmtPredTimeSet.getNightShiftAtr() == TRUE_VAL;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeGetMemento#
	 * getPrescribedTimezoneSetting()
	 */
	@Override
	public PrescribedTimezoneSetting getPrescribedTimezoneSetting() {
		List<Timezone> timezones = this.lstKshmtWorkTimeSheetSet.stream().map(item -> {
			return Timezone.builder().workNo(item.getKshmtWorkTimeSheetSetPK().getTimeNumberCnt())
					.useAtr(UseSetting.valueOf(item.getUseAtr())).start(new TimeWithDayAttr(item.getStartTime()))
					.end(new TimeWithDayAttr(item.getEndTime())).build();
		}).collect(Collectors.toList());
		return PrescribedTimezoneSetting.builder()
				.morningEndTime(new TimeWithDayAttr(this.kshmtPredTimeSet.getMorningEndTime()))
				.afternoonStartTime(new TimeWithDayAttr(this.kshmtPredTimeSet.getAfternoonStartTime()))
				.lstTimezone(timezones).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeGetMemento#
	 * getStartDateClock()
	 */
	@Override
	public TimeWithDayAttr getStartDateClock() {
		return new TimeWithDayAttr(this.kshmtPredTimeSet.getStartDateClock());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.predset.PredetemineTimeGetMemento#isPredetermine
	 * ()
	 */
	@Override
	public boolean isPredetermine() {
		return this.kshmtPredTimeSet.getPredetermineAtr() == TRUE_VAL;
	}

}
