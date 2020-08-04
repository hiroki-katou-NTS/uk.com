package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.share;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.shared.dom.common.Month;
import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share.KshstDeforLarSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share.KshstFlexSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share.KshstNormalSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share.KshstRegLaborTime;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share.KshstTransLabTime;

public class JpaDefaultSettingGetMemento {
	
	private KshstDeforLarSet kshstDeforLarSet;
	private KshstNormalSet kshstNormalSet;
	private KshstFlexSet kshstFlexSet;
	 KshstRegLaborTime kshstRegLaborTime; 
	 KshstTransLabTime kshstTransLabTime;
	
	public JpaDefaultSettingGetMemento() {
	}

	public JpaDefaultSettingGetMemento(KshstDeforLarSet kshstDeforLarSet) {
		this.kshstDeforLarSet = kshstDeforLarSet;
	}

	public JpaDefaultSettingGetMemento(KshstRegLaborTime kshstRegLaborTime) {
		this.kshstRegLaborTime = kshstRegLaborTime;
	} 
	
	public JpaDefaultSettingGetMemento(KshstTransLabTime kshstTransLabTime) {
		this.kshstTransLabTime = kshstTransLabTime;
	}

	public JpaDefaultSettingGetMemento(KshstNormalSet kshstNormalSet) {
		this.kshstNormalSet = kshstNormalSet;
	}

	public JpaDefaultSettingGetMemento(KshstFlexSet kshstFlexSet) {
		this.kshstFlexSet = kshstFlexSet;
	}

	protected List<MonthlyUnit> toMonthlyUnitsFromDeforSet() {
		List<MonthlyUnit> monthlyUnits = new ArrayList<>();
		monthlyUnits.add(new MonthlyUnit( new Month(Month.JANUARY), new MonthlyEstimateTime(this.kshstDeforLarSet.getJanTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.FEBRUARY), new MonthlyEstimateTime(this.kshstDeforLarSet.getFebTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.MARCH), new MonthlyEstimateTime(this.kshstDeforLarSet.getMarTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.APRIL), new MonthlyEstimateTime(this.kshstDeforLarSet.getAprTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.MAY), new MonthlyEstimateTime(this.kshstDeforLarSet.getMayTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.JUNE), new MonthlyEstimateTime(this.kshstDeforLarSet.getJunTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.JULY), new MonthlyEstimateTime(this.kshstDeforLarSet.getJulTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.AUGUST), new MonthlyEstimateTime(this.kshstDeforLarSet.getAugTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.SEPTEMBER), new MonthlyEstimateTime(this.kshstDeforLarSet.getSepTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.OCTOBER), new MonthlyEstimateTime(this.kshstDeforLarSet.getOctTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.NOVEMBER), new MonthlyEstimateTime(this.kshstDeforLarSet.getNovTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.DECEMBER), new MonthlyEstimateTime(this.kshstDeforLarSet.getDecTime())));
		return monthlyUnits;
	}
	protected List<MonthlyUnit> toMonthlyUnitsFromNormalSet() {
		List<MonthlyUnit> monthlyUnits = new ArrayList<>();
		monthlyUnits.add(new MonthlyUnit( new Month(Month.JANUARY), new MonthlyEstimateTime(this.kshstNormalSet.getJanTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.FEBRUARY), new MonthlyEstimateTime(this.kshstNormalSet.getFebTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.MARCH), new MonthlyEstimateTime(this.kshstNormalSet.getMarTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.APRIL), new MonthlyEstimateTime(this.kshstNormalSet.getAprTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.MAY), new MonthlyEstimateTime(this.kshstNormalSet.getMayTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.JUNE), new MonthlyEstimateTime(this.kshstNormalSet.getJunTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.JULY), new MonthlyEstimateTime(this.kshstNormalSet.getJulTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.AUGUST), new MonthlyEstimateTime(this.kshstNormalSet.getAugTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.SEPTEMBER), new MonthlyEstimateTime(this.kshstNormalSet.getSepTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.OCTOBER), new MonthlyEstimateTime(this.kshstNormalSet.getOctTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.NOVEMBER), new MonthlyEstimateTime(this.kshstNormalSet.getNovTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.DECEMBER), new MonthlyEstimateTime(this.kshstNormalSet.getDecTime())));
		return monthlyUnits;
	}
	
	protected List<MonthlyUnit> toStatutorySettingFromFlexSet() {
		List<MonthlyUnit> monthlyUnits = new ArrayList<>();
		monthlyUnits.add(new MonthlyUnit( new Month(Month.JANUARY), new MonthlyEstimateTime(this.kshstFlexSet.getStatJanTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.FEBRUARY), new MonthlyEstimateTime(this.kshstFlexSet.getStatFebTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.MARCH), new MonthlyEstimateTime(this.kshstFlexSet.getStatMarTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.APRIL), new MonthlyEstimateTime(this.kshstFlexSet.getStatAprTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.MAY), new MonthlyEstimateTime(this.kshstFlexSet.getStatMayTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.JUNE), new MonthlyEstimateTime(this.kshstFlexSet.getStatJunTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.JULY), new MonthlyEstimateTime(this.kshstFlexSet.getStatJulTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.AUGUST), new MonthlyEstimateTime(this.kshstFlexSet.getStatAugTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.SEPTEMBER), new MonthlyEstimateTime(this.kshstFlexSet.getStatSepTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.OCTOBER), new MonthlyEstimateTime(this.kshstFlexSet.getStatOctTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.NOVEMBER), new MonthlyEstimateTime(this.kshstFlexSet.getStatNovTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.DECEMBER), new MonthlyEstimateTime(this.kshstFlexSet.getStatDecTime())));
		return monthlyUnits;
	}
	
	protected List<MonthlyUnit> toSpecSettingFromFlexSet() {
		List<MonthlyUnit> monthlyUnits = new ArrayList<>();
		monthlyUnits.add(new MonthlyUnit( new Month(Month.JANUARY), new MonthlyEstimateTime(this.kshstFlexSet.getSpecJanTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.FEBRUARY), new MonthlyEstimateTime(this.kshstFlexSet.getSpecFebTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.MARCH), new MonthlyEstimateTime(this.kshstFlexSet.getSpecMarTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.APRIL), new MonthlyEstimateTime(this.kshstFlexSet.getSpecAprTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.MAY), new MonthlyEstimateTime(this.kshstFlexSet.getSpecMayTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.JUNE), new MonthlyEstimateTime(this.kshstFlexSet.getSpecJunTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.JULY), new MonthlyEstimateTime(this.kshstFlexSet.getSpecJulTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.AUGUST), new MonthlyEstimateTime(this.kshstFlexSet.getSpecAugTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.SEPTEMBER), new MonthlyEstimateTime(this.kshstFlexSet.getSpecSepTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.OCTOBER), new MonthlyEstimateTime(this.kshstFlexSet.getSpecOctTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.NOVEMBER), new MonthlyEstimateTime(this.kshstFlexSet.getSpecNovTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.DECEMBER), new MonthlyEstimateTime(this.kshstFlexSet.getSpecDecTime())));
		return monthlyUnits;
	}
	
	protected List<MonthlyUnit> toWeekSettingFromFlexSet() {
		List<MonthlyUnit> monthlyUnits = new ArrayList<>();
		monthlyUnits.add(new MonthlyUnit( new Month(Month.JANUARY), new MonthlyEstimateTime(this.kshstFlexSet.getWeekJanTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.FEBRUARY), new MonthlyEstimateTime(this.kshstFlexSet.getWeekFebTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.MARCH), new MonthlyEstimateTime(this.kshstFlexSet.getWeekMarTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.APRIL), new MonthlyEstimateTime(this.kshstFlexSet.getWeekAprTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.MAY), new MonthlyEstimateTime(this.kshstFlexSet.getWeekMayTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.JUNE), new MonthlyEstimateTime(this.kshstFlexSet.getWeekJunTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.JULY), new MonthlyEstimateTime(this.kshstFlexSet.getWeekJulTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.AUGUST), new MonthlyEstimateTime(this.kshstFlexSet.getWeekAugTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.SEPTEMBER), new MonthlyEstimateTime(this.kshstFlexSet.getWeekSepTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.OCTOBER), new MonthlyEstimateTime(this.kshstFlexSet.getWeekOctTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.NOVEMBER), new MonthlyEstimateTime(this.kshstFlexSet.getWeekNovTime())));
		monthlyUnits.add(new MonthlyUnit( new Month(Month.DECEMBER), new MonthlyEstimateTime(this.kshstFlexSet.getWeekDecTime())));
		return monthlyUnits;
	}
}
