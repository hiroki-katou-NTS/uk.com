/**
 * 6:40:38 PM Nov 6, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.worktype;

import java.util.List;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.WorkCheckResult;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.FilterByCompare;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.LogicalOperator;

/**
 * @author hungnm
 *
 */
// 勤務種類（予実）
@Getter
public class PlanActualWorkType extends WorkTypeCondition {

	// 予定と実績の間の演算子
	private LogicalOperator operatorBetweenPlanActual;

	// 予定
	private TargetWorkType workTypePlan;

	// 実績
	private TargetWorkType workTypeActual;

	/* Constructor from Superclass */
	private PlanActualWorkType(boolean useAtr, FilterByCompare comparePlanAndActual) {
		super(useAtr, comparePlanAndActual);
	}

	/* Initial from java type */
	public static PlanActualWorkType init(boolean useAtr, int comparePlanAndActual) {
		return new PlanActualWorkType(useAtr, EnumAdaptor.valueOf(comparePlanAndActual, FilterByCompare.class));
	}

	/**
	 * Set logical operator
	 * 
	 * @param: operator
	 *             0: AND 1: OR
	 * @return itself
	 */
	public PlanActualWorkType chooseOperator(int operator) {
		this.operatorBetweenPlanActual = EnumAdaptor.valueOf(operator, LogicalOperator.class);
		return this;
	}

	/**
	 * Set WorkType plan
	 * 
	 * @param: filterAtr
	 * @param lstWorkType
	 * @return itself
	 */
	public PlanActualWorkType setWorkTypePlan(boolean filterAtr, List<String> lstWorkType) {
		this.workTypePlan = TargetWorkType.createFromJavaType(filterAtr, lstWorkType);
		return this;
	}

	/**
	 * Set WorkType actual
	 * 
	 * @param filterAtr
	 * @param lstWorkType
	 * @return itself
	 */
	public PlanActualWorkType setworkTypeActual(boolean filterAtr, List<String> lstWorkType) {
		this.workTypeActual = TargetWorkType.createFromJavaType(filterAtr, lstWorkType);
		return this;
	}

	@Override
	public WorkCheckResult checkWorkType(WorkInfoOfDailyPerformance workInfo) {
		if(this.getComparePlanAndActual() == FilterByCompare.EXTRACT_DIFFERENT){
			if(workInfo.getRecordInfo().getWorkTypeCode().equals(workInfo.getScheduleInfo().getWorkTypeCode())){
				return WorkCheckResult.ERROR;
			}
		}
		
		WorkCheckResult planCheck = WorkCheckResult.NOT_CHECK;
		if (this.workTypePlan != null) {
			if(this.workTypePlan.isUse() && !this.workTypePlan.getLstWorkType().isEmpty()){
				planCheck = this.workTypePlan.contains(workInfo.getScheduleInfo().getWorkTypeCode()) 
						? WorkCheckResult.ERROR : WorkCheckResult.NOT_ERROR;
			}
		}
		WorkCheckResult actualCheck = WorkCheckResult.NOT_CHECK;
		if (this.workTypeActual != null) {
			if(this.workTypeActual.isUse() && !this.workTypeActual.getLstWorkType().isEmpty()){
				actualCheck = this.workTypeActual.contains(workInfo.getRecordInfo().getWorkTypeCode()) 
						? WorkCheckResult.ERROR : WorkCheckResult.NOT_ERROR;
			}
		}
		
		return comparePlanAndActual(planCheck, actualCheck, this.operatorBetweenPlanActual == LogicalOperator.AND);
	}
	
	private WorkCheckResult comparePlanAndActual(WorkCheckResult plan, WorkCheckResult actual, boolean same){
		if(plan == WorkCheckResult.NOT_CHECK && actual == WorkCheckResult.NOT_CHECK){
			return WorkCheckResult.NOT_CHECK;
		}
		
		if(plan == WorkCheckResult.NOT_CHECK) {
			if(WorkCheckResult.ERROR == actual){
				return WorkCheckResult.ERROR;
			}
			return WorkCheckResult.NOT_ERROR;
		}
		if(actual == WorkCheckResult.NOT_CHECK) {
			if(WorkCheckResult.ERROR == plan){
				return WorkCheckResult.ERROR;
			}
			return WorkCheckResult.NOT_ERROR;
		}
		if(same){
			if(WorkCheckResult.ERROR == actual && WorkCheckResult.ERROR == plan){
				return WorkCheckResult.ERROR;
			}
			return WorkCheckResult.NOT_ERROR;
		}

		if(WorkCheckResult.ERROR == actual || WorkCheckResult.ERROR == plan){
			return WorkCheckResult.ERROR;
		}
		return WorkCheckResult.NOT_ERROR;
	}
}
