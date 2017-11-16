/**
 * 11:15:00 AM Nov 8, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.worktime;

import java.util.List;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.FilterByCompare;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.LogicalOperator;

/**
 * @author hungnm
 *
 */
// 就業時間帯（予実）
@Getter
public class PlanActualWorkTime extends WorkTimeCondition {

	// 予定と実績の間の演算子
	private LogicalOperator operatorBetweenPlanActual;

	// 予定
	private TargetWorkTime workTimePlan;

	// 実績
	private TargetWorkTime workTimeActual;

	/**
	 * Constructor from Superclass 
	 * @param useAtr
	 * @param comparePlanAndActual
	 */
	private PlanActualWorkTime(Boolean useAtr, FilterByCompare comparePlanAndActual) {
		super(useAtr, comparePlanAndActual);
	}
	
	/* Initial from java type */
	public static PlanActualWorkTime init(boolean useAtr, int comparePlanAndActual) {
		return new PlanActualWorkTime(useAtr, EnumAdaptor.valueOf(comparePlanAndActual, FilterByCompare.class));
	}
	
	/** 
	 * Set logical operator
	 * @param: operator 
	 * 0: AND
	 * 1: OR
	 * @return itself
	 * */
	public PlanActualWorkTime chooseOperator(int operator) {
		this.operatorBetweenPlanActual = EnumAdaptor.valueOf(operator, LogicalOperator.class);
		return this;
	}

	/** 
	 * Set WorkTime plan
	 * @param: filterAtr
	 * @param lstWorkTime
	 * @return itself
	 * */
	public PlanActualWorkTime setWorkTimePlan(boolean filterAtr, List<String> lstWorkType) {
		this.workTimePlan = TargetWorkTime.createFromJavaType(filterAtr, lstWorkType);
		return this;
	}

	/** 
	 * Set WorkTime actual
	 * @param filterAtr
	 * @param lstWorkTime
	 * @return itself
	 * */
	public PlanActualWorkTime setworkTimeActual(boolean filterAtr, List<String> lstWorkType) {
		this.workTimeActual = TargetWorkTime.createFromJavaType(filterAtr, lstWorkType);
		return this;
	}
	
}
