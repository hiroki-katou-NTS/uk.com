/**
 * 11:14:44 AM Nov 8, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.worktime;

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
// 就業時間帯（単一）
@Getter
public class SingleWorkTime extends WorkTimeCondition {

	// 就業時間帯
	private TargetWorkTime targetWorkTime;

	/**
	 * Constructor from Superclass
	 * 
	 * @param useAtr
	 * @param comparePlanAndActual
	 */
	private SingleWorkTime(boolean useAtr, FilterByCompare comparePlanAndActual) {
		super(useAtr, comparePlanAndActual);
	}

	/* Initial from java type */
	public static SingleWorkTime init(boolean useAtr, int comparePlanAndActual) {
		return new SingleWorkTime(useAtr, EnumAdaptor.valueOf(comparePlanAndActual, FilterByCompare.class));
	}

	/**
	 * Set WorkTime target
	 * 
	 * @param filterAtr
	 * @param lstWorkTime
	 * @return itself
	 */
	public SingleWorkTime setTargetWorkTime(boolean filterAtr, List<String> lstWorkType) {
		this.targetWorkTime = TargetWorkTime.createFromJavaType(filterAtr, lstWorkType);
		return this;
	}

	@Override
	public WorkCheckResult checkWorkTime(WorkInfoOfDailyPerformance workInfo) {
//		if(workInfo.getScheduleInfo().getWorkTimeCode() != null && 
//				!workInfo.getScheduleInfo().getWorkTimeCode().equals(workInfo.getRecordInfo().getWorkTimeCode())){
//			return true;
//		}
		if (this.targetWorkTime != null && isUse()) {
			if(this.targetWorkTime.contains(workInfo.getRecordInfo().getWorkTimeCode())){
				return WorkCheckResult.ERROR;
			}
		}
		return WorkCheckResult.NOT_ERROR;

	}

}
