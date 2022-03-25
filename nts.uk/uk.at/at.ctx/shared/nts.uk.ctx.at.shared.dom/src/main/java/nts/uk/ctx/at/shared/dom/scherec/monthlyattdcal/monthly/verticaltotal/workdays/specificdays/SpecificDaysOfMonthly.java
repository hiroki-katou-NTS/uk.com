package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workdays.specificdays;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.SpecificDateAttr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.SpecificDateAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.SpecificDateItemNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.AggregateMethodOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.WorkTypeDaysCountTable;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.context.AppContexts;

/**
 * 月別実績の特定日数
 * @author shuichi_ishida
 */
@Getter
public class SpecificDaysOfMonthly implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 特定日数 */
	private Map<SpecificDateItemNo, AggregateSpecificDays> specificDays;
	
	/**
	 * コンストラクタ
	 */
	public SpecificDaysOfMonthly(){
		
		this.specificDays = new HashMap<>();
	}
	
	/**
	 * ファクトリー
	 * @param specificDaysList 特定日数リスト
	 * @return 月別実績の特定日数
	 */
	public static SpecificDaysOfMonthly of(List<AggregateSpecificDays> specificDaysList){
		
		val domain = new SpecificDaysOfMonthly();
		for (val specificDays : specificDaysList){
			val specificDayItemNo = specificDays.getSpecificDayItemNo();
			domain.specificDays.putIfAbsent(specificDayItemNo, specificDays);
		}
		return domain;
	}
	
	/**
	 * 集計
	 * @param workingSystem 労働制
	 * @param workType 勤務種類
	 * @param specificDateAttrOfDaily 日別実績の特定日区分
	 * @param isAttendanceDay 出勤しているかどうか
	 */
	public void aggregate(RequireM1 require, WorkingSystem workingSystem, WorkType workType,
			SpecificDateAttrOfDailyAttd specificDateAttrOfDaily, WorkTypeDaysCountTable workTypeDaysCountTable,
			boolean isAttendanceDay){

		if (workType == null) return;
		if (specificDateAttrOfDaily == null) return;
		
		val verticalTotalMethod = require.aggregateMethodOfMonthly(AppContexts.user().companyId());

		/** 勤務種類の判断 */
		boolean isCount = verticalTotalMethod
				.map(vtm -> vtm.isCalcThisWorkTypeAsSpecDays(workingSystem, workType, workTypeDaysCountTable))
				.orElse(false);
		
		if (!isCount) return;

		/** 条件を満たしているか判断 */
		if (!verticalTotalMethod.map(c -> c.getSpecTotalCountMonthly()
				.isFitCondition(workType, workingSystem, isAttendanceDay, workTypeDaysCountTable)).orElse(false))
			return;

		specificDateAttrOfDaily.getSpecificDateAttrSheets().stream().forEach(spe -> {
			val dailySpecNo = specificDays.values().stream()
					.filter(dspe -> dspe.getSpecificDayItemNo().equals(spe.getSpecificDateItemNo())).findFirst();
			
			val specDays = spe.getSpecificDateAttr() == SpecificDateAttr.USE ? 1d : 0;

			AggregateSpecificDays aggrSpecDays;
			if (dailySpecNo.isPresent()) {
				aggrSpecDays = dailySpecNo.get(); 

			} else {
				aggrSpecDays = new AggregateSpecificDays(spe.getSpecificDateItemNo());
				specificDays.put(spe.getSpecificDateItemNo(), aggrSpecDays);
			}
			
			/** ○休出かどうかの判断 */
			if (workType.isHolidayWork()) {
				/** ○休出特定日数に特定日日数を加算 */
				aggrSpecDays.addDaysToHolidayWorkSpecificDays(specDays);

			} else {
				/** ○特定日数に特定日日数を加算 */
				aggrSpecDays.addDaysToSpecificDays(specDays);
			}
		});
	}

	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(SpecificDaysOfMonthly target){

		for (val specificDay : this.specificDays.values()){
			val itemNo = specificDay.getSpecificDayItemNo();
			if (target.specificDays.containsKey(itemNo)){
				specificDay.addDaysToSpecificDays(target.specificDays.get(itemNo).getSpecificDays().v());
				specificDay.addDaysToHolidayWorkSpecificDays(target.specificDays.get(itemNo).getHolidayWorkSpecificDays().v());
			}
		}
		for (val targetSpecificDay : target.specificDays.values()){
			val itemNo = targetSpecificDay.getSpecificDayItemNo();
			this.specificDays.putIfAbsent(itemNo, targetSpecificDay);
		}
	}
	
	public static interface RequireM1 {

		Optional<AggregateMethodOfMonthly> aggregateMethodOfMonthly(String cid);
	}
}
