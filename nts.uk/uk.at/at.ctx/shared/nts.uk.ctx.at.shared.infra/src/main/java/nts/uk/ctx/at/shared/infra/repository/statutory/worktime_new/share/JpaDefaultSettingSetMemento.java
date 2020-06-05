package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.share;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.common.Month;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share.KshstDeforLarSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share.KshstFlexSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share.KshstNormalSet;

public class JpaDefaultSettingSetMemento {
	
	private Map<Integer, Integer> toMonthlyEstimateTimeMap (List<MonthlyUnit> statutorySetting) {
		return statutorySetting.stream().collect(Collectors.toMap(unit -> unit.getMonth().v(), unit -> unit.getMonthlyTime().v()));
	}
	
	protected void setStatutorySettingToDeforSet(KshstDeforLarSet kshstDeforLarSet, List<MonthlyUnit> statutorySetting) {
		Map<Integer, Integer> map = toMonthlyEstimateTimeMap(statutorySetting);
		kshstDeforLarSet.setJanTime(map.get(Month.JANUARY));
		kshstDeforLarSet.setFebTime(map.get(Month.FEBRUARY));
		kshstDeforLarSet.setMarTime(map.get(Month.MARCH));
		kshstDeforLarSet.setAprTime(map.get(Month.APRIL));
		kshstDeforLarSet.setMayTime(map.get(Month.MAY));
		kshstDeforLarSet.setJunTime(map.get(Month.JUNE));
		kshstDeforLarSet.setJulTime(map.get(Month.JULY));
		kshstDeforLarSet.setAugTime(map.get(Month.AUGUST));
		kshstDeforLarSet.setSepTime(map.get(Month.SEPTEMBER));
		kshstDeforLarSet.setOctTime(map.get(Month.OCTOBER));
		kshstDeforLarSet.setNovTime(map.get(Month.NOVEMBER));
		kshstDeforLarSet.setDecTime(map.get(Month.DECEMBER));
	}
	
	protected void setStatutorySettingToNormalSet(KshstNormalSet kshstNormalSet, List<MonthlyUnit> statutorySetting) {
		Map<Integer, Integer> map = toMonthlyEstimateTimeMap(statutorySetting);
		kshstNormalSet.setJanTime(map.get(Month.JANUARY));
		kshstNormalSet.setFebTime(map.get(Month.FEBRUARY));
		kshstNormalSet.setMarTime(map.get(Month.MARCH));
		kshstNormalSet.setAprTime(map.get(Month.APRIL));
		kshstNormalSet.setMayTime(map.get(Month.MAY));
		kshstNormalSet.setJunTime(map.get(Month.JUNE));
		kshstNormalSet.setJulTime(map.get(Month.JULY));
		kshstNormalSet.setAugTime(map.get(Month.AUGUST));
		kshstNormalSet.setSepTime(map.get(Month.SEPTEMBER));
		kshstNormalSet.setOctTime(map.get(Month.OCTOBER));
		kshstNormalSet.setNovTime(map.get(Month.NOVEMBER));
		kshstNormalSet.setDecTime(map.get(Month.DECEMBER));
	}
	
	protected void setStatutorySettingToFlexSet(KshstFlexSet kshstFlexLarSet, List<MonthlyUnit> statutorySetting) {
		Map<Integer, Integer> map = toMonthlyEstimateTimeMap(statutorySetting);
		kshstFlexLarSet.setStatJanTime(map.get(Month.JANUARY));
		kshstFlexLarSet.setStatFebTime(map.get(Month.FEBRUARY));
		kshstFlexLarSet.setStatMarTime(map.get(Month.MARCH));
		kshstFlexLarSet.setStatAprTime(map.get(Month.APRIL));
		kshstFlexLarSet.setStatMayTime(map.get(Month.MAY));
		kshstFlexLarSet.setStatJunTime(map.get(Month.JUNE));
		kshstFlexLarSet.setStatJulTime(map.get(Month.JULY));
		kshstFlexLarSet.setStatAugTime(map.get(Month.AUGUST));
		kshstFlexLarSet.setStatSepTime(map.get(Month.SEPTEMBER));
		kshstFlexLarSet.setStatOctTime(map.get(Month.OCTOBER));
		kshstFlexLarSet.setStatNovTime(map.get(Month.NOVEMBER));
		kshstFlexLarSet.setStatDecTime(map.get(Month.DECEMBER));
	}
	
	protected void setSpecSettingToFlexSet(KshstFlexSet kshstFlexLarSet, List<MonthlyUnit> specSetting) {
		Map<Integer, Integer> map = toMonthlyEstimateTimeMap(specSetting);
		kshstFlexLarSet.setSpecJanTime(map.get(Month.JANUARY));
		kshstFlexLarSet.setSpecFebTime(map.get(Month.FEBRUARY));
		kshstFlexLarSet.setSpecMarTime(map.get(Month.MARCH));
		kshstFlexLarSet.setSpecAprTime(map.get(Month.APRIL));
		kshstFlexLarSet.setSpecMayTime(map.get(Month.MAY));
		kshstFlexLarSet.setSpecJunTime(map.get(Month.JUNE));
		kshstFlexLarSet.setSpecJulTime(map.get(Month.JULY));
		kshstFlexLarSet.setSpecAugTime(map.get(Month.AUGUST));
		kshstFlexLarSet.setSpecSepTime(map.get(Month.SEPTEMBER));
		kshstFlexLarSet.setSpecOctTime(map.get(Month.OCTOBER));
		kshstFlexLarSet.setSpecNovTime(map.get(Month.NOVEMBER));
		kshstFlexLarSet.setSpecDecTime(map.get(Month.DECEMBER));
	}
	
	protected void setWeekSettingToFlexSet(KshstFlexSet kshstFlexLarSet, List<MonthlyUnit> weekAveSetting) {
		Map<Integer, Integer> map = toMonthlyEstimateTimeMap(weekAveSetting);
		kshstFlexLarSet.setWeekJanTime(map.get(Month.JANUARY));
		kshstFlexLarSet.setWeekFebTime(map.get(Month.FEBRUARY));
		kshstFlexLarSet.setWeekMarTime(map.get(Month.MARCH));
		kshstFlexLarSet.setWeekAprTime(map.get(Month.APRIL));
		kshstFlexLarSet.setWeekMayTime(map.get(Month.MAY));
		kshstFlexLarSet.setWeekJunTime(map.get(Month.JUNE));
		kshstFlexLarSet.setWeekJulTime(map.get(Month.JULY));
		kshstFlexLarSet.setWeekAugTime(map.get(Month.AUGUST));
		kshstFlexLarSet.setWeekSepTime(map.get(Month.SEPTEMBER));
		kshstFlexLarSet.setWeekOctTime(map.get(Month.OCTOBER));
		kshstFlexLarSet.setWeekNovTime(map.get(Month.NOVEMBER));
		kshstFlexLarSet.setWeekDecTime(map.get(Month.DECEMBER));
	}
}
