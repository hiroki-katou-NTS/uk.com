package nts.uk.ctx.at.shared.infra.repository.dailypattern;

import nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternValGetMemento;
import nts.uk.ctx.at.shared.infra.entity.dailypattern.KdpstDailyPatternVal;

public class JpaDailyPatternValGetMemento implements DailyPatternValGetMemento{

	private KdpstDailyPatternVal patternCalendar;
	
	/**
	 * @param patternCalendar
	 */
	public JpaDailyPatternValGetMemento(KdpstDailyPatternVal patternCalendar) {
		this.patternCalendar = patternCalendar;
	}

	@Override
	public String getCompanyId() {
		return patternCalendar.getKdpstDailyPatternValPK().getCid();
	}

	@Override
	public String getPatternCode() {
		return patternCalendar.getKdpstDailyPatternValPK().getPatternCd();
	}

	@Override
	public Integer getDispOrder() {
		return patternCalendar.getKdpstDailyPatternValPK().getDispOrder();
	}

	@Override
	public String getWorkTypeSetCd() {
		return patternCalendar.getWorkTypeSetCd();
	}

	@Override
	public String getWorkingHoursCd() {
		return patternCalendar.getWorkingHoursCd();
	}

	@Override
	public Integer getDays() {
		return patternCalendar.getDays();
	}

}
