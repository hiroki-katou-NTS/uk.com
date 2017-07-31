package nts.uk.ctx.at.schedule.infra.repository.dailypattern;

import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.DailyPatternValGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.Days;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.DispOrder;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.PatternCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.WorkTypeCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.WorkingCode;
import nts.uk.ctx.at.schedule.infra.entity.dailypattern.KdpstDailyPatternVal;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaDailyPatternValGetMemento.
 */
public class JpaDailyPatternValGetMemento implements DailyPatternValGetMemento{

	/** The pattern calendar. */
	private KdpstDailyPatternVal patternCalendar;
	
	/**
	 * Instantiates a new jpa daily pattern val get memento.
	 *
	 * @param patternCalendar the pattern calendar
	 */
	public JpaDailyPatternValGetMemento(KdpstDailyPatternVal patternCalendar) {
		this.patternCalendar = patternCalendar;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternValGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(patternCalendar.getKdpstDailyPatternValPK().getCid());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternValGetMemento#getPatternCode()
	 */
	@Override
	public PatternCode getPatternCode() {
		return new PatternCode(patternCalendar.getKdpstDailyPatternValPK().getPatternCd());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternValGetMemento#getDispOrder()
	 */
	@Override
	public DispOrder getDispOrder() {
		return new DispOrder(patternCalendar.getKdpstDailyPatternValPK().getDispOrder());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternValGetMemento#getWorkTypeSetCd()
	 */
	@Override
	public WorkTypeCode getWorkTypeSetCd() {
		return new WorkTypeCode(patternCalendar.getWorkTypeSetCd());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternValGetMemento#getWorkingHoursCd()
	 */
	@Override
	public WorkingCode getWorkingHoursCd() {
		return new WorkingCode(patternCalendar.getWorkingHoursCd());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternValGetMemento#getDays()
	 */
	@Override
	public Days getDays() {
		return new Days(patternCalendar.getDays());
	}

}
