/**
 * 11:39:17 AM Nov 2, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.LogicalOperator;

/**
 * @author hungnm
 *
 */
// 勤怠項目に対する条件
@Getter
public class AttendanceItemCondition extends DomainObject {

	// グループ間の演算子
	private LogicalOperator operatorBetweenGroups;

	// グループ1
	private ErAlConditionsAttendanceItem group1;

	// グループ2
	private ErAlConditionsAttendanceItem group2;

	private AttendanceItemCondition(LogicalOperator operatorBetweenGroups) {
		super();
		this.operatorBetweenGroups = operatorBetweenGroups;
	}

	/** Init from Java type. */
	public static AttendanceItemCondition init(int operatorBetweenGroups){
		return new AttendanceItemCondition(EnumAdaptor.valueOf(operatorBetweenGroups, LogicalOperator.class));
	}
	
	public void setGroup1(ErAlConditionsAttendanceItem group){
		this.group1 = group;
	}
	
	public void setGroup2(ErAlConditionsAttendanceItem group){
		this.group2 = group;
	}

}
