package nts.uk.ctx.at.schedule.infra.repository.dailypattern;

import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkTypeCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkingCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.DailyPatternValSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.Days;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.DispOrder;
import nts.uk.ctx.at.schedule.infra.entity.dailypattern.KdpstDailyPatternVal;
import nts.uk.ctx.at.schedule.infra.entity.dailypattern.KdpstDailyPatternValPK;

/**
 * The Class JpaDailyPatternValSetMemento.
 */
public class JpaDailyPatternValSetMemento implements DailyPatternValSetMemento {

	/** The pattern calendar. */
	private KdpstDailyPatternVal patternCalendar;

	/**
	 * Instantiates a new jpa daily pattern val set memento.
	 *
	 * @param patternCalendar
	 *            the pattern calendar
	 */
	public JpaDailyPatternValSetMemento(KdpstDailyPatternVal patternCalendar) {

		if (patternCalendar.getKdpstDailyPatternValPK() == null) {
			patternCalendar.setKdpstDailyPatternValPK(new KdpstDailyPatternValPK());
		}

		this.patternCalendar = patternCalendar;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternValSetMemento#
	 * setDispOrder(nts.uk.ctx.at.shared.dom.dailypattern.DispOrder)
	 */
	@Override
	public void setDispOrder(DispOrder setDispOrder) {
		this.patternCalendar.getKdpstDailyPatternValPK().setDispOrder(setDispOrder.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternValSetMemento#
	 * setWorkTypeCodes(nts.uk.ctx.at.shared.dom.dailypattern.WorkTypeCode)
	 */
	@Override
	public void setWorkTypeCodes(WorkTypeCode setWorkTypeCodes) {
		this.patternCalendar.setWorkTypeSetCd(setWorkTypeCodes.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternValSetMemento#
	 * setWorkHouseCodes(nts.uk.ctx.at.shared.dom.dailypattern.WorkingCode)
	 */
	@Override
	public void setWorkHouseCodes(WorkingCode setWorkHouseCodes) {
		this.patternCalendar.setWorkingHoursCd(setWorkHouseCodes.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternValSetMemento#setDays(
	 * nts.uk.ctx.at.shared.dom.dailypattern.Days)
	 */
	@Override
	public void setDays(Days setDays) {
		this.patternCalendar.setDays(setDays.v());
	}
}
