package nts.uk.ctx.at.record.infra.repository.calculationattribute;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.calculationattribute.enums.DivergenceTimeAttr;
import nts.uk.ctx.at.record.dom.calculationattribute.repo.NCalAttrOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.infra.entity.daily.calculationattribute.KrcdtDayCalSet;
import nts.uk.ctx.at.record.infra.entity.daily.calculationattribute.KrcdtDayCalSetPK;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalRestTimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalcOfLeaveEarlySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.deviationtime.AutoCalcSetOfDivergenceTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;
@Stateless
public class JpaCalAttrOfDaily extends JpaRepository implements NCalAttrOfDailyPerformanceRepository{

	private static final String REMOVE_BY_KEY;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtDayCalSet a ");
		builderString.append("WHERE a.krcdtDayCalSetPK.sid = :employeeId ");
		builderString.append("AND a.krcdtDayCalSetPK.ymd = :ymd ");
		REMOVE_BY_KEY = builderString.toString();
	}
	
	@Override
	public CalAttrOfDailyPerformance find(String employeeId, GeneralDate baseDate) {
		KrcdtDayCalSet krcdtDayCalSet = this.queryProxy().find(new KrcdtDayCalSetPK(employeeId, baseDate), KrcdtDayCalSet.class).orElse(null);
		if(krcdtDayCalSet!=null){
			return this.toDomain(krcdtDayCalSet);
		}
		return null;
	}


	@Override
	public void update(CalAttrOfDailyPerformance domain) {
		KrcdtDayCalSet krcdtDayCalSet = this.queryProxy().find(new KrcdtDayCalSetPK(domain.getEmployeeId(), domain.getYmd()), KrcdtDayCalSet.class).orElse(null);		
		if(krcdtDayCalSet==null){
			this.add(domain);
		}else{
			if (domain.getCalcategory().getRasingSalarySetting() != null) {
				krcdtDayCalSet.bonusPayNormalCalSet = domain.getCalcategory().getRasingSalarySetting().isRaisingSalaryCalcAtr() ? 1 : 0;
				krcdtDayCalSet.bonusPaySpeCalSet = domain.getCalcategory().getRasingSalarySetting().isSpecificRaisingSalaryCalcAtr() ? 1 : 0;
			}
			if (domain.getCalcategory().getDivergenceTime() != null) {
				krcdtDayCalSet.divergenceTime = domain.getCalcategory().getDivergenceTime().getDivergenceTime().value;
			}
			if (domain.getCalcategory().getLeaveEarlySetting() != null) {
				krcdtDayCalSet.leaveEarlySet = domain.getCalcategory().getLeaveEarlySetting().isLate() ? 1 : 0;
				krcdtDayCalSet.leaveLateSet = domain.getCalcategory().getLeaveEarlySetting().isLeaveEarly() ? 1 : 0;
			}
			AutoCalSetting autoCalSetting = domain.getCalcategory().getFlexExcessTime().getFlexOtTime();
			if (autoCalSetting != null) {
				krcdtDayCalSet.flexExcessLimitSet = autoCalSetting.getUpLimitORtSet() == null ? 0 : autoCalSetting.getUpLimitORtSet().value;
				krcdtDayCalSet.flexExcessTimeCalAtr = autoCalSetting.getCalAtr() == null ? 0 : autoCalSetting.getCalAtr().value;
			}
			AutoCalRestTimeSetting holidayTimeSetting = domain.getCalcategory().getHolidayTimeSetting();
			if (holidayTimeSetting != null) {
				krcdtDayCalSet.holWorkTimeCalAtr = holidayTimeSetting.getRestTime() == null ? 0 : holidayTimeSetting.getRestTime().getCalAtr().value;
				krcdtDayCalSet.holWorkTimeLimitSet = holidayTimeSetting.getRestTime() == null ? 0
						: holidayTimeSetting.getRestTime().getUpLimitORtSet().value;
				krcdtDayCalSet.lateNightTimeCalAtr = holidayTimeSetting.getLateNightTime() == null ? 0
						: holidayTimeSetting.getLateNightTime().getCalAtr().value;
				krcdtDayCalSet.lateNightTimeLimitSet = holidayTimeSetting.getLateNightTime() == null ? 0
						: holidayTimeSetting.getLateNightTime().getUpLimitORtSet().value;
			}
			AutoCalOvertimeSetting autoCalOvertimeSetting = domain.getCalcategory().getOvertimeSetting();
			if(autoCalOvertimeSetting!=null){
				krcdtDayCalSet.earlyMidOtCalAtr = autoCalOvertimeSetting.getEarlyMidOtTime() == null ? 0
						: autoCalOvertimeSetting.getEarlyMidOtTime().getCalAtr().value;
				krcdtDayCalSet.earlyMidOtLimitSet = autoCalOvertimeSetting.getEarlyMidOtTime() == null ? 0
						: autoCalOvertimeSetting.getEarlyMidOtTime().getUpLimitORtSet().value;
				krcdtDayCalSet.earlyOverTimeCalAtr = autoCalOvertimeSetting.getEarlyOtTime() == null ? 0
						: autoCalOvertimeSetting.getEarlyOtTime().getCalAtr().value;
				krcdtDayCalSet.earlyOverTimeLimitSet = autoCalOvertimeSetting.getEarlyOtTime() == null ? 0
						: autoCalOvertimeSetting.getEarlyOtTime().getUpLimitORtSet().value;
				krcdtDayCalSet.legalMidOtCalAtr = autoCalOvertimeSetting.getLegalMidOtTime() == null ? 0
						: autoCalOvertimeSetting.getLegalMidOtTime().getCalAtr().value;
				krcdtDayCalSet.legalMidOtLimitSet = autoCalOvertimeSetting.getLegalMidOtTime() == null ? 0
						: autoCalOvertimeSetting.getLegalMidOtTime().getUpLimitORtSet().value;
				krcdtDayCalSet.legalOverTimeCalAtr = autoCalOvertimeSetting.getLegalOtTime() == null ? 0
						: autoCalOvertimeSetting.getLegalOtTime().getCalAtr().value;
				krcdtDayCalSet.legalOverTimeLimitSet = autoCalOvertimeSetting.getLegalOtTime() == null ? 0
						: autoCalOvertimeSetting.getLegalOtTime().getUpLimitORtSet().value;
				krcdtDayCalSet.normalMidOtCalAtr = autoCalOvertimeSetting.getNormalMidOtTime() == null ? 0
						: autoCalOvertimeSetting.getNormalMidOtTime().getCalAtr().value;
				krcdtDayCalSet.normalMidOtLimitSet = autoCalOvertimeSetting.getNormalMidOtTime() == null ? 0
						: autoCalOvertimeSetting.getNormalMidOtTime().getUpLimitORtSet().value;
				krcdtDayCalSet.normalOverTimeCalAtr = autoCalOvertimeSetting.getNormalOtTime() == null ? 0
						: autoCalOvertimeSetting.getNormalOtTime().getCalAtr().value;
				krcdtDayCalSet.normalOverTimeLimitSet = autoCalOvertimeSetting.getNormalOtTime() == null ? 0
						: autoCalOvertimeSetting.getNormalOtTime().getUpLimitORtSet().value;
			}
			this.commandProxy().insert(krcdtDayCalSet);
			this.getEntityManager().flush();
		}
	}

	@Override
	public void add(CalAttrOfDailyPerformance domain) {
		int bonusPayNormalCalSet=0;
		int bonusPaySpeCalSet=0;
		int divergenceTime=0;
		int leaveEarlySet=0;
		int leaveLateSet=0;
		if (domain.getCalcategory().getRasingSalarySetting() != null) {
		 bonusPayNormalCalSet = domain.getCalcategory().getRasingSalarySetting().isRaisingSalaryCalcAtr() ? 1 : 0;
		 bonusPaySpeCalSet = domain.getCalcategory().getRasingSalarySetting().isSpecificRaisingSalaryCalcAtr() ? 1 : 0;
		}
		if (domain.getCalcategory().getDivergenceTime() != null) {
			divergenceTime = domain.getCalcategory().getDivergenceTime().getDivergenceTime().value;
		}
		if (domain.getCalcategory().getLeaveEarlySetting() != null) {
			leaveEarlySet = domain.getCalcategory().getLeaveEarlySetting().isLate() ? 1 : 0;
			leaveLateSet = domain.getCalcategory().getLeaveEarlySetting().isLeaveEarly() ? 1 : 0;
		}
		AutoCalSetting autoCalSetting = domain.getCalcategory().getFlexExcessTime().getFlexOtTime();
		int flexExcessLimitSet=0;
		int flexExcessTimeCalAtr=0;
		if(autoCalSetting!=null){
			flexExcessLimitSet = autoCalSetting.getUpLimitORtSet() == null ? 0 : autoCalSetting.getUpLimitORtSet().value;
			flexExcessTimeCalAtr = autoCalSetting.getCalAtr() == null ? 0 : autoCalSetting.getCalAtr().value;
		}
		AutoCalRestTimeSetting autoCalRestTimeSetting = domain.getCalcategory().getHolidayTimeSetting();
		int holWorkTimeCalAtr=0;
		int holWorkTimeLimitSet=0;
		int lateNightTimeCalAtr=0;
		int lateNightTimeLimitSet=0;
		if(autoCalRestTimeSetting!=null){
			holWorkTimeCalAtr = autoCalRestTimeSetting.getRestTime() == null ? 0 : autoCalRestTimeSetting.getRestTime().getCalAtr().value;
			holWorkTimeLimitSet = autoCalRestTimeSetting.getRestTime() == null ? 0
					: autoCalRestTimeSetting.getRestTime().getUpLimitORtSet().value;
			lateNightTimeCalAtr = autoCalRestTimeSetting.getLateNightTime() == null ? 0
					: autoCalRestTimeSetting.getLateNightTime().getCalAtr().value;
			lateNightTimeLimitSet = autoCalRestTimeSetting.getLateNightTime() == null ? 0
					: autoCalRestTimeSetting.getLateNightTime().getUpLimitORtSet().value;
		}
		int earlyMidOtCalAtr=0;
		int earlyMidOtLimitSet=0;
		int earlyOverTimeCalAtr=0;
		int earlyOverTimeLimitSet=0;
		int legalMidOtCalAtr=0;
		int legalMidOtLimitSet=0;
		int legalOverTimeCalAtr=0;
		int legalOverTimeLimitSet=0;
		int normalMidOtCalAtr=0;
		int normalMidOtLimitSet=0;
		int normalOverTimeCalAtr=0;
		int normalOverTimeLimitSet=0;
		
		AutoCalOvertimeSetting autoCalOvertimeSetting = domain.getCalcategory().getOvertimeSetting();
		if(autoCalOvertimeSetting!=null){
			earlyMidOtCalAtr = autoCalOvertimeSetting.getEarlyMidOtTime() == null ? 0
					: autoCalOvertimeSetting.getEarlyMidOtTime().getCalAtr().value;
			earlyMidOtLimitSet = autoCalOvertimeSetting.getEarlyMidOtTime() == null ? 0
					: autoCalOvertimeSetting.getEarlyMidOtTime().getUpLimitORtSet().value;
			earlyOverTimeCalAtr = autoCalOvertimeSetting.getEarlyOtTime() == null ? 0
					: autoCalOvertimeSetting.getEarlyOtTime().getCalAtr().value;
			earlyOverTimeLimitSet = autoCalOvertimeSetting.getEarlyOtTime() == null ? 0
					: autoCalOvertimeSetting.getEarlyOtTime().getUpLimitORtSet().value;
			legalMidOtCalAtr = autoCalOvertimeSetting.getLegalMidOtTime() == null ? 0
					: autoCalOvertimeSetting.getLegalMidOtTime().getCalAtr().value;
			legalMidOtLimitSet = autoCalOvertimeSetting.getLegalMidOtTime() == null ? 0
					: autoCalOvertimeSetting.getLegalMidOtTime().getUpLimitORtSet().value;
			legalOverTimeCalAtr = autoCalOvertimeSetting.getLegalOtTime() == null ? 0
					: autoCalOvertimeSetting.getLegalOtTime().getCalAtr().value;
			legalOverTimeLimitSet = autoCalOvertimeSetting.getLegalOtTime() == null ? 0
					: autoCalOvertimeSetting.getLegalOtTime().getUpLimitORtSet().value;
			normalMidOtCalAtr = autoCalOvertimeSetting.getNormalMidOtTime() == null ? 0
					: autoCalOvertimeSetting.getNormalMidOtTime().getCalAtr().value;
			normalMidOtLimitSet = autoCalOvertimeSetting.getNormalMidOtTime() == null ? 0
					: autoCalOvertimeSetting.getNormalMidOtTime().getUpLimitORtSet().value;
			normalOverTimeCalAtr = autoCalOvertimeSetting.getNormalOtTime() == null ? 0
					: autoCalOvertimeSetting.getNormalOtTime().getCalAtr().value;
			normalOverTimeLimitSet = autoCalOvertimeSetting.getNormalOtTime() == null ? 0
					: autoCalOvertimeSetting.getNormalOtTime().getUpLimitORtSet().value;
		}
		
		KrcdtDayCalSet krcdtDayCalSet = new KrcdtDayCalSet(new KrcdtDayCalSetPK(domain.getEmployeeId(), domain.getYmd()), bonusPayNormalCalSet, bonusPaySpeCalSet, leaveLateSet, leaveEarlySet, divergenceTime, flexExcessTimeCalAtr, flexExcessLimitSet, holWorkTimeCalAtr, holWorkTimeLimitSet, lateNightTimeCalAtr, lateNightTimeLimitSet, earlyOverTimeCalAtr, earlyOverTimeLimitSet, earlyMidOtCalAtr, earlyMidOtLimitSet, normalOverTimeCalAtr, normalOverTimeLimitSet, normalMidOtCalAtr, normalMidOtLimitSet, legalOverTimeCalAtr, legalOverTimeLimitSet, legalMidOtCalAtr, legalMidOtLimitSet);
		this.commandProxy().insert(krcdtDayCalSet);
		this.getEntityManager().flush();
	}

	@Override
	public void deleteByKey(String employeeId, GeneralDate baseDate) {
		this.getEntityManager().createQuery(REMOVE_BY_KEY).setParameter("employeeId", employeeId)
		.setParameter("ymd", baseDate).executeUpdate();
		this.getEntityManager().flush();
		
	}
	
	private AutoCalSetting newAutoCalcSetting(int calc, int limit) {
		return new AutoCalSetting(getEnum(limit, TimeLimitUpperLimitSetting.class),
				getEnum(calc, AutoCalAtrOvertime.class));
	}
	private <T> T getEnum(int value, Class<T> className) {
		return EnumAdaptor.valueOf(value, className);
	}
	private CalAttrOfDailyPerformance toDomain(KrcdtDayCalSet daycalset) {
		AutoCalSetting flex = null;
		AutoCalRestTimeSetting holiday = null;
		AutoCalOvertimeSetting overtime = null;
			flex = newAutoCalcSetting(daycalset.flexExcessTimeCalAtr, daycalset.flexExcessLimitSet);
			holiday = new AutoCalRestTimeSetting(
					newAutoCalcSetting(daycalset.holWorkTimeCalAtr, daycalset.holWorkTimeLimitSet),
					newAutoCalcSetting(daycalset.lateNightTimeCalAtr, daycalset.lateNightTimeLimitSet));
			overtime = new AutoCalOvertimeSetting(
					newAutoCalcSetting(daycalset.earlyOverTimeCalAtr, daycalset.earlyOverTimeLimitSet),
					newAutoCalcSetting(daycalset.earlyMidOtCalAtr, daycalset.earlyMidOtLimitSet),
					newAutoCalcSetting(daycalset.normalOverTimeCalAtr, daycalset.normalOverTimeLimitSet),
					newAutoCalcSetting(daycalset.normalMidOtCalAtr, daycalset.normalMidOtLimitSet),
					newAutoCalcSetting(daycalset.legalOverTimeCalAtr, daycalset.legalOverTimeLimitSet),
					newAutoCalcSetting(daycalset.legalMidOtCalAtr, daycalset.legalMidOtLimitSet));

		return new CalAttrOfDailyPerformance(daycalset.krcdtDayCalSetPK.sid, daycalset.krcdtDayCalSetPK.ymd,
				new AutoCalFlexOvertimeSetting(flex),
				new AutoCalRaisingSalarySetting(daycalset.bonusPayNormalCalSet == 1 ? true : false,
						daycalset.bonusPaySpeCalSet == 1 ? true : false),
				holiday, overtime,
				new AutoCalcOfLeaveEarlySetting(daycalset.leaveEarlySet == 1 ? true : false,
						daycalset.leaveLateSet  == 1 ? true : false),
				new AutoCalcSetOfDivergenceTime(getEnum(daycalset.divergenceTime, DivergenceTimeAttr.class)));
	}
	

}
