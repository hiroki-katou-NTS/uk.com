/**
 * 6:48:51 PM Nov 6, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.worktype;

import java.util.List;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.WorkCheckResult;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.FilterByCompare;

/**
 * @author hungnm
 *
 */
// 勤務種類（単一）
@Getter
public class SingleWorkType extends WorkTypeCondition {

	// 勤務種類
	private TargetWorkType targetWorkType;

	/* Constructor from Superclass */
	private SingleWorkType(boolean useAtr, FilterByCompare comparePlanAndActual) {
		super(useAtr, comparePlanAndActual);
	}

	/* Initial from java type */
	public static SingleWorkType init(boolean useAtr, int comparePlanAndActual) {
		return new SingleWorkType(useAtr, EnumAdaptor.valueOf(comparePlanAndActual, FilterByCompare.class));
	}

	/**
	 * Set WorkType target
	 * 
	 * @param filterAtr
	 * @param lstWorkType
	 * @return itself
	 */
	public SingleWorkType setTargetWorkType(boolean filterAtr, List<String> lstWorkType) {
		this.targetWorkType = TargetWorkType.createFromJavaType(filterAtr, lstWorkType);
		return this;
	}

	@Override
	public WorkCheckResult checkWorkType(WorkInfoOfDailyPerformance workInfo) {
		if (this.isUse() && this.targetWorkType != null) {
			if(this.targetWorkType.contains(workInfo.getRecordInfo().getWorkTypeCode())){
				return WorkCheckResult.ERROR;
			}
		}
		return WorkCheckResult.NOT_ERROR;
	}
}
