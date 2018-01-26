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

	// グループ2を利用する
	private Boolean group2UseAtr;

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
}
