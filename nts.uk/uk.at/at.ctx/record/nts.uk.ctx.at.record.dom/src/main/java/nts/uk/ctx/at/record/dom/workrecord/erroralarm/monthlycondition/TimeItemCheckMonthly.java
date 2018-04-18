/**
 * 9:20:11 AM Mar 28, 2018
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycondition;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.AttendanceItemCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.ErAlAttendanceItemCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.ErAlConditionsAttendanceItem;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;

/**
 * @author hungnm 月別実績の勤怠項目チェック
 */
@Getter
public class TimeItemCheckMonthly extends AggregateRoot {

	// Check ID
	private String errorAlarmCheckID;

	// 勤怠項目の条件
	private AttendanceItemCondition atdItemCondition;

	/* 表示メッセージ */
	private Optional<DisplayMessage> displayMessage;

	private TimeItemCheckMonthly() {
		super();
		this.displayMessage = Optional.ofNullable(null);
	}

	private TimeItemCheckMonthly(String errorAlarmCheckID, AttendanceItemCondition atdItemCondition,
			Optional<DisplayMessage> displayMessage) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.atdItemCondition = atdItemCondition;
		this.displayMessage = displayMessage;
	}

	/**
	 * Init
	 * 
	 * @return instance of TimeItemCheckMonthly
	 */
	public static TimeItemCheckMonthly init() {
		return new TimeItemCheckMonthly();
	}

	public TimeItemCheckMonthly setDisplayMessage(String message) {
		this.displayMessage = Optional.of(new DisplayMessage(message));
		return this;
	}

	public void setCheckId(String errorAlarmCheckID) {
		this.errorAlarmCheckID = errorAlarmCheckID;
	}

	/**
	 * Create AttendanceItemCondition
	 * 
	 * @param operatorBetweenGroups
	 * @return itself
	 */
	public TimeItemCheckMonthly createAttendanceItemCondition(int operatorBetweenGroups, boolean group2UseAtr) {
		this.atdItemCondition = AttendanceItemCondition.init(operatorBetweenGroups, group2UseAtr);
		return this;
	}

	/**
	 * Set group 1
	 * 
	 * @param conditionOperator
	 * @param conditions
	 * @return
	 */
	public TimeItemCheckMonthly setAttendanceItemConditionGroup1(int conditionOperator,
			List<ErAlAttendanceItemCondition<?>> conditions) {
		ErAlConditionsAttendanceItem group = ErAlConditionsAttendanceItem.init(conditionOperator);
		group.addAtdItemConditions(conditions);
		this.atdItemCondition.setGroup1(group);
		return this;
	}

	/**
	 * Set group 2
	 * 
	 * @param conditionOperator
	 * @param conditions
	 * @return
	 */
	public TimeItemCheckMonthly setAttendanceItemConditionGroup2(int conditionOperator,
			List<ErAlAttendanceItemCondition<?>> conditions) {
		ErAlConditionsAttendanceItem group = ErAlConditionsAttendanceItem.init(conditionOperator);
		group.addAtdItemConditions(conditions);
		this.atdItemCondition.setGroup2(group);
		return this;
	}

	public void setGroupId1(String groupId) {
		this.atdItemCondition.setGroupId1(groupId);
	}

	public void setGroupId2(String groupId) {
		this.atdItemCondition.setGroupId2(groupId);
	}

}
