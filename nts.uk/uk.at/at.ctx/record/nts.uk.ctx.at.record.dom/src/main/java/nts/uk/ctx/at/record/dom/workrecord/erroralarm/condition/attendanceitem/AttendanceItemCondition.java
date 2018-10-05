/**
 * 11:39:17 AM Nov 2, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem;

import java.util.List;
import java.util.function.Function;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.WorkCheckResult;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.LogicalOperator;

/**
 * @author hungnm
 *
 */
// 勤怠項目に対する条件
public class AttendanceItemCondition extends DomainObject {

	// グループ間の演算子
	@Getter
	private LogicalOperator operatorBetweenGroups;

	// グループ1
	@Getter
	private ErAlConditionsAttendanceItem group1;

	// グループ2
	@Getter
	private ErAlConditionsAttendanceItem group2;

	// グループ2を利用する
	private boolean group2UseAtr;

	private AttendanceItemCondition(LogicalOperator operatorBetweenGroups, Boolean group2UseAtr) {
		super();
		this.operatorBetweenGroups = operatorBetweenGroups;
		this.group2UseAtr = group2UseAtr;
	}

	/** Init from Java type. */
	public static AttendanceItemCondition init(int operatorBetweenGroups, boolean group2UseAtr) {
		return new AttendanceItemCondition(EnumAdaptor.valueOf(operatorBetweenGroups, LogicalOperator.class),
				group2UseAtr);
	}

	/** Init from Java type. */
	public static AttendanceItemCondition init(LogicalOperator operatorBetweenGroups, boolean group2UseAtr) {
		return new AttendanceItemCondition(operatorBetweenGroups, group2UseAtr);
	}

	public void setGroup1(ErAlConditionsAttendanceItem group) {
		this.group1 = group;
	}

	public void setGroup2(ErAlConditionsAttendanceItem group) {
		this.group2 = group;
	}

	public void setGroupId1(String groupId) {
		this.group1.setGroupId(groupId);
	}

	public void setGroupId2(String groupId) {
		this.group2.setGroupId(groupId);
	}

	/** 勤怠項目をチェックする */
	public WorkCheckResult check(Function<List<Integer>, List<Integer>> getValueFromItemIds) {
		WorkCheckResult checkGroup1 = group1.check(getValueFromItemIds);
		if (!this.isUseGroup2()) {
			return checkGroup1;
		}
		WorkCheckResult checkGroup2 = group2.check(getValueFromItemIds);
		
		return compareGroups(checkGroup1, checkGroup2, this.operatorBetweenGroups == LogicalOperator.AND);
	}

	public boolean isUseGroup2() {
		return this.group2UseAtr;
	}
	
	private WorkCheckResult compareGroups(WorkCheckResult group1, WorkCheckResult group2, boolean same){
		if(group1 == WorkCheckResult.NOT_CHECK && group2 == WorkCheckResult.NOT_CHECK){
			return WorkCheckResult.NOT_CHECK;
		}
		
		if(group1 == WorkCheckResult.NOT_CHECK) {
			if(WorkCheckResult.ERROR == group2){
				return WorkCheckResult.ERROR;
			}
			return WorkCheckResult.NOT_ERROR;
		}
		if(group2 == WorkCheckResult.NOT_CHECK) {
			if(WorkCheckResult.ERROR == group1){
				return WorkCheckResult.ERROR;
			}
			return WorkCheckResult.NOT_ERROR;
		}
		if(same){
			if(WorkCheckResult.ERROR == group2 && WorkCheckResult.ERROR == group1){
				return WorkCheckResult.ERROR;
			}
			return WorkCheckResult.NOT_ERROR;
		}

		if(WorkCheckResult.ERROR == group2 || WorkCheckResult.ERROR == group1){
			return WorkCheckResult.ERROR;
		}
		return WorkCheckResult.NOT_ERROR;
	}
}
