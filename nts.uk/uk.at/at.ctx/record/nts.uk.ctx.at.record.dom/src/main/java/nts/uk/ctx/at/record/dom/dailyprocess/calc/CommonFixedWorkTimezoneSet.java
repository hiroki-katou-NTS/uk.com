package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTime;

/**
 * フレックスでも固定勤務の処理をするために
 * 異なるクラスで持つ共通メンバーを管理するクラス
 * @author keisuke_hoshina
 *
 */
@Getter
@NoArgsConstructor
public class CommonFixedWorkTimezoneSet {
	 private Map<AmPmAtr, FixedWorkTimezoneSet> fixedWorkTimezoneMap = new HashMap<>();
	 
	 private CommonFixedWorkTimezoneSet(Map<AmPmAtr, FixedWorkTimezoneSet> fixedWorkTimezoneMap) {
	  super();
	  this.fixedWorkTimezoneMap = fixedWorkTimezoneMap;
	 }
	 
	 private  void addMapToTimezone(AmPmAtr apAtr, FixedWorkTimezoneSet timezoneSet) {
	  if(fixedWorkTimezoneMap.containsKey(apAtr)) { 
	   throw new RuntimeException("already regist AmPmAtr:"+apAtr);
	  }
	  else {
	   this.fixedWorkTimezoneMap.put(apAtr, timezoneSet);
	  }
	  
	 }
	 /**
	  * member への追加
	  * @param findFirst
	  */
	 private void putMap(AmPmAtr ampmAtr, Optional<FixedWorkTimezoneSet> findFirst) {
	  if(findFirst.isPresent())
	   addMapToTimezone(ampmAtr, findFirst.get());
	 }
	 
	 /**
	  * 固定勤務からの窓口
	  * @return CommonFixedWorkTimezoneSet
	  */
	 public CommonFixedWorkTimezoneSet forFixed(List<FixHalfDayWorkTimezone> timeList) {
	  putMap(AmPmAtr.AM,timeList.stream().filter(tc -> tc.getDayAtr().equals(AmPmAtr.AM)).map(tc -> tc.getWorkTimezone()).findFirst());
	  putMap(AmPmAtr.PM,timeList.stream().filter(tc -> tc.getDayAtr().equals(AmPmAtr.PM)).map(tc -> tc.getWorkTimezone()).findFirst());
	  putMap(AmPmAtr.ONE_DAY,timeList.stream().filter(tc -> tc.getDayAtr().equals(AmPmAtr.ONE_DAY)).map(tc -> tc.getWorkTimezone()).findFirst());
	  return new CommonFixedWorkTimezoneSet(this.fixedWorkTimezoneMap);
	 }
	
	 /**
	  * フレ勤務からの窓口
	  * @return CommonFixedWorkTimezoneSet
	  */
	 public CommonFixedWorkTimezoneSet forFlex(List<FlexHalfDayWorkTime> timeList) {
	  
	  putMap(AmPmAtr.AM,timeList.stream().filter(tc -> tc.getAmpmAtr().equals(AmPmAtr.AM)).map(tc -> tc.getWorkTimezone()).findFirst());
	  putMap(AmPmAtr.PM,timeList.stream().filter(tc -> tc.getAmpmAtr().equals(AmPmAtr.PM)).map(tc -> tc.getWorkTimezone()).findFirst());
	  putMap(AmPmAtr.ONE_DAY,timeList.stream().filter(tc -> tc.getAmpmAtr().equals(AmPmAtr.ONE_DAY)).map(tc -> tc.getWorkTimezone()).findFirst());
	  return new CommonFixedWorkTimezoneSet(this.fixedWorkTimezoneMap);
	 }
}