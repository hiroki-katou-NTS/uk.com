/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.predset;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.Timezone;
import nts.uk.ctx.at.shared.dom.worktime.predset.UseSetting;
import nts.uk.ctx.at.shared.infra.entity.predset.KwtstWorkTimeSetNew;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaPredetemineTimeGetMemento.
 */
public class JpaPredetemineTimeGetMemento implements PredetemineTimeGetMemento {

	/** The kwtst work time set. */
	private KwtstWorkTimeSetNew kwtstWorkTimeSet;

	/** The Constant TRUE_VAL. */
	private static final Integer TRUE_VAL = 1;

	/**
	 * Instantiates a new jpa predetemine time get memento.
	 *
	 * @param kwtstWorkTimeSet the kwtst work time set
	 */
	public JpaPredetemineTimeGetMemento(KwtstWorkTimeSetNew kwtstWorkTimeSet) {
		this.kwtstWorkTimeSet = kwtstWorkTimeSet;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeGetMemento#getCompanyID()
	 */
	@Override
	public String getCompanyID() {
		return this.kwtstWorkTimeSet.getKwtspWorkTimeSetPK().getCompanyId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeGetMemento#getRangeTimeDay()
	 */
	@Override
	public int getRangeTimeDay() {
		return this.kwtstWorkTimeSet.getRangeTimeDay();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeGetMemento#getSiftCD()
	 */
	@Override
	public String getSiftCD() {
		return this.kwtstWorkTimeSet.getKwtspWorkTimeSetPK().getSiftCD();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeGetMemento#getAdditionSetID()
	 */
	@Override
	public String getAdditionSetID() {
		return this.kwtstWorkTimeSet.getAdditionSetID();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeGetMemento#isNightShift()
	 */
	@Override
	public boolean isNightShift() {
		return this.kwtstWorkTimeSet.getNightShiftAtr() == TRUE_VAL;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeGetMemento#getPrescribedTimezoneSetting()
	 */
	@Override
	public PrescribedTimezoneSetting getPrescribedTimezoneSetting() {
		List<Timezone> timezones = this.kwtstWorkTimeSet.getKwtdtWorkTimeDay().stream().map(item -> {
			return new Timezone(UseSetting.valueOf(item.getUseAtr()), item.getKwtdpWorkTimeDayPK().getTimeNumberCnt(),
					new TimeWithDayAttr(item.getStart()), new TimeWithDayAttr(item.getEnd()));
		}).collect(Collectors.toList());
		return new PrescribedTimezoneSetting(new TimeWithDayAttr(this.kwtstWorkTimeSet.getMorningEndTime()),
				new TimeWithDayAttr(this.kwtstWorkTimeSet.getAfternoonStartTime()), timezones);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeGetMemento#getStartDateClock()
	 */
	@Override
	public int getStartDateClock() {
		return this.kwtstWorkTimeSet.getStartDateClock();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeGetMemento#isPredetermine()
	 */
	@Override
	public boolean isPredetermine() {
		return this.kwtstWorkTimeSet.getPredetermineAtr() == TRUE_VAL;
	}

}
