package nts.uk.ctx.at.shared.infra.repository.dailypattern;

import nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternValSetMemento;
import nts.uk.ctx.at.shared.infra.entity.dailypattern.KdpstDailyPatternVal;
import nts.uk.ctx.at.shared.infra.entity.dailypattern.KdpstDailyPatternValPK;

public class JpaDailyPatternValSetMemento implements DailyPatternValSetMemento {

	private KdpstDailyPatternVal patternCalendar;

	/**
	 * @param patternCalendar
	 */
	public JpaDailyPatternValSetMemento(KdpstDailyPatternVal patternCalendar) {
		if (patternCalendar.getKdpstDailyPatternValPK() == null) {
			patternCalendar.setKdpstDailyPatternValPK(new KdpstDailyPatternValPK());
		}
		this.patternCalendar = patternCalendar;
	}

	@Override
	public void setCompanyId(String setCompanyId) {
		this.patternCalendar.getKdpstDailyPatternValPK().setCid(setCompanyId);
	}

	@Override
	public void setPatternCode(String setPatternCode) {
		this.patternCalendar.getKdpstDailyPatternValPK().setPatternCd(setPatternCode);
	}

	@Override
	public void setDispOrder(Integer setDispOrder) {
		this.patternCalendar.getKdpstDailyPatternValPK().setDispOrder(setDispOrder);
	}

	@Override
	public void setWorkTypeCodes(String setWorkTypeCodes) {
		this.patternCalendar.setWorkTypeSetCd(setWorkTypeCodes);
	}

	@Override
	public void setWorkHouseCodes(String setWorkHouseCodes) {
		this.patternCalendar.setWorkingHoursCd(setWorkHouseCodes);
	}

	@Override
	public void setDays(Integer setDays) {
		this.patternCalendar.setDays(setDays);
	}

}
