/**
 * 10:08:35 AM Nov 2, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.AttendanceItemCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.ErAlAttendanceItemCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.ErAlConditionsAttendanceItem;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.worktime.PlanActualWorkTime;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.worktime.SingleWorkTime;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.worktime.WorkTimeCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.worktype.PlanActualWorkType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.worktype.SingleWorkType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.worktype.WorkTypeCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.FilterByCompare;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;

/**
 * @author hungnm
 *
 */
// 勤務実績のエラーアラームチェック
@Getter
public class ErrorAlarmCondition extends AggregateRoot {

	/* 表示メッセージ */
	private DisplayMessage displayMessage;

	// アラームチェック対象者の条件
	private AlCheckTargetCondition checkTargetCondtion;

	// 勤務種類の条件
	private WorkTypeCondition workTypeCondition;

	// 就業時間帯の条件
	private WorkTimeCondition workTimeCondition;

	// 勤怠項目の条件
	private AttendanceItemCondition atdItemCondition;

	private ErrorAlarmCondition() {
		super();
		this.displayMessage = new DisplayMessage("");
	}

	/**
	 * Init
	 * 
	 * @return instance of ErrorAlarmCondition
	 */
	public static ErrorAlarmCondition init() {
		return new ErrorAlarmCondition();
	}

	/**
	 * Set display message
	 * 
	 * @param message
	 * @return itself
	 */
	public ErrorAlarmCondition setDisplayMessage(String message) {
		this.displayMessage = new DisplayMessage(message);
		return this;
	}

	/**
	 * 
	 * @param filterByBusinessType
	 * @param filterByJobTitle
	 * @param filterByEmployment
	 * @param filterByClassification
	 * @param lstBusinessTypeCode
	 * @param lstJobTitleId
	 * @param lstEmploymentCode
	 * @param lstClassificationCode
	 * @return itself
	 */
	public ErrorAlarmCondition createAlCheckTargetCondition(Boolean filterByBusinessType, Boolean filterByJobTitle,
			Boolean filterByEmployment, Boolean filterByClassification, List<String> lstBusinessTypeCode,
			List<String> lstJobTitleId, List<String> lstEmploymentCode, List<String> lstClassificationCode) {
		this.checkTargetCondtion = new AlCheckTargetCondition(filterByBusinessType, filterByJobTitle,
				filterByEmployment, filterByClassification, lstBusinessTypeCode, lstJobTitleId, lstEmploymentCode,
				lstClassificationCode);
		return this;
	}

	/**
	 * Set WorkTypeCondtion: PlanActualWorkType or SingleWorkType
	 * 
	 * @param useAtr
	 * @param comparePlanAndActual
	 */
	public void createWorkTypeCondition(boolean useAtr, int comparePlanAndActual) {
		if (comparePlanAndActual != FilterByCompare.EXTRACT_SAME.value) {
			this.workTypeCondition = PlanActualWorkType.init(useAtr, comparePlanAndActual);
		} else {
			this.workTypeCondition = SingleWorkType.init(useAtr, comparePlanAndActual);
		}
	}

	/**
	 * Set WorkTypePlan
	 * 
	 * @param filterAtr
	 * @param lstWorkType
	 */
	public void setWorkTypePlan(boolean filterAtr, List<String> lstWorkType) {
		((PlanActualWorkType) this.workTypeCondition).setWorkTypePlan(filterAtr, lstWorkType);
	}

	/**
	 * Set WorkTypeActual
	 * 
	 * @param filterAtr
	 * @param lstWorkType
	 */
	public void setWorkTypeActual(boolean filterAtr, List<String> lstWorkType) {
		((PlanActualWorkType) this.workTypeCondition).setworkTypeActual(filterAtr, lstWorkType);
	}

	/**
	 * 
	 * @param filterAtr
	 * @param lstWorkType
	 */
	public void setWorkTypeSingle(boolean filterAtr, List<String> lstWorkType) {
		((SingleWorkType) this.workTypeCondition).setTargetWorkType(filterAtr, lstWorkType);
	}

	public void chooseWorkTypeOperator(int operator) {
		((PlanActualWorkType) this.workTypeCondition).chooseOperator(operator);
	}

	/**
	 * Set WorkTimeCondtion: PlanActualWorkTime or SingleWorkTime
	 * 
	 * @param useAtr
	 * @param comparePlanAndActual
	 */
	public void createWorkTimeCondition(boolean useAtr, int comparePlanAndActual) {
		if (comparePlanAndActual != FilterByCompare.EXTRACT_SAME.value) {
			this.workTimeCondition = PlanActualWorkTime.init(useAtr, comparePlanAndActual);
		} else {
			this.workTimeCondition = SingleWorkTime.init(useAtr, comparePlanAndActual);
		}
	}

	/**
	 * Set WorkTimePlan
	 * 
	 * @param filterAtr
	 * @param lstWorkTime
	 */
	public void setWorkTimePlan(boolean filterAtr, List<String> lstWorkTime) {
		((PlanActualWorkTime) this.workTimeCondition).setWorkTimePlan(filterAtr, lstWorkTime);
	}

	/**
	 * Set WorkTimeActual
	 * 
	 * @param filterAtr
	 * @param lstWorkTime
	 */
	public void setWorkTimeActual(boolean filterAtr, List<String> lstWorkTime) {
		((PlanActualWorkTime) this.workTimeCondition).setworkTimeActual(filterAtr, lstWorkTime);
	}

	/**
	 * 
	 * @param filterAtr
	 * @param lstWorkType
	 */
	public void setWorkTimeSingle(boolean filterAtr, List<String> lstWorkTime) {
		((SingleWorkTime) this.workTimeCondition).setTargetWorkTime(filterAtr, lstWorkTime);
	}

	public void chooseWorkTimeOperator(int operator) {
		((PlanActualWorkTime) this.workTimeCondition).chooseOperator(operator);
	}

	/**
	 * Create AttendanceItemCondition
	 * 
	 * @param operatorBetweenGroups
	 * @return itself
	 */
	public ErrorAlarmCondition createAttendanceItemCondition(int operatorBetweenGroups, boolean group2UseAtr) {
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
	public ErrorAlarmCondition setAttendanceItemConditionGroup1(int conditionOperator,
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
	public ErrorAlarmCondition setAttendanceItemConditionGroup2(int conditionOperator,
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
