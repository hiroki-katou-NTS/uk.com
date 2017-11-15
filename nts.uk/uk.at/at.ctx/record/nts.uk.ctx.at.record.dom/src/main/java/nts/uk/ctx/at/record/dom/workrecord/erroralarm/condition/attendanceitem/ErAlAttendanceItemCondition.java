/**
 * 4:47:15 PM Nov 8, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionAtr;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedAmountValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimeDuration;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimesValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * @author hungnm
 *
 */
// 勤怠項目のエラーアラーム条件
public class ErAlAttendanceItemCondition extends AggregateRoot {

	// 会社ID
	@Getter
	private String companyId;

	// エラーアラームコード
	@Getter
	private ErrorAlarmWorkRecordCode errorAlarmCode;

	// NO
	@Getter
	private int targetNO;

	// 条件式の属性
	@Getter
	private ConditionAtr conditionAtr;

	// 使用する
	@Getter
	private Boolean useAtr;

	// チェック対象
	private CheckedTarget checkedTarget;

	// チェック条件
	private CheckedCondition checkedCondition;

	private ErAlAttendanceItemCondition(String companyId, ErrorAlarmWorkRecordCode errorAlarmCode, int targetNO,
			ConditionAtr conditionAtr, Boolean useAtr) {
		super();
		this.companyId = companyId;
		this.errorAlarmCode = errorAlarmCode;
		this.targetNO = targetNO;
		this.conditionAtr = conditionAtr;
		this.useAtr = useAtr;
	}
	
	/**
	 * 
	 * @param companyId
	 * @param errorAlarmCode
	 * @param targetNO
	 * @param conditionAtr
	 * @param useAtr
	 * @return an instance of ErAlAttendanceItemCondition.
	 */
	public static ErAlAttendanceItemCondition init(String companyId, ErrorAlarmWorkRecordCode errorAlarmCode,
			int targetNO, ConditionAtr conditionAtr, Boolean useAtr) {
		return new ErAlAttendanceItemCondition(companyId, errorAlarmCode, targetNO, conditionAtr, useAtr);
	}


	/**
	 * 
	 * @return UncountableTarget or CountableTarget.
	 */
	public CheckedTarget getTarget() {
		return this.checkedTarget;
	}
	
	/**
	 * 
	 * @param lists
	 */
	@SuppressWarnings("unchecked")
	public ErAlAttendanceItemCondition setTarget(List<Integer>... lists) {
		if (this.conditionAtr == ConditionAtr.TIME_WITH_DAY) {
			this.checkedTarget = new UncountableTarget(lists[0]);
		} else {
			if (lists.length > 2) {
				this.checkedTarget = new CountableTarget(new AddSubAttendanceItems(lists[0], lists[1]));
			} else {
				this.checkedTarget = new CountableTarget(new AddSubAttendanceItems(new ArrayList<>(), new ArrayList<>()));
				System.out.println("Invalid parameter to create object CountableTarget.");
			}
		}
		return this;
	}
	
	/**
	 * 
	 * @return CompareSingleValue or CompareRange
	 */
	public CheckedCondition getCondtion(){
		return this.checkedCondition;
	}

	/**
	 * 
	 * @param compareOpertor
	 * @param conditionType
	 * @param fixedValue
	 * @param atdItemValues
	 * @return itself
	 */
	public ErAlAttendanceItemCondition setCompareSingleValue(int compareOpertor, int conditionType, Object fixedValue,
			@SuppressWarnings("unchecked") List<Integer>... atdItemValues) {
		if (conditionType == ConditionType.ATTENDANCE_ITEM.value) {
			if (atdItemValues.length > 2) {
				this.checkedCondition = new CompareSingleValue<AddSubAttendanceItems>(compareOpertor, conditionType)
						.setValue(new AddSubAttendanceItems(atdItemValues[0], atdItemValues[1]));
			}
		} else {
			switch (this.conditionAtr) {
			case AMOUNT_VALUE:
				this.checkedCondition = new CompareSingleValue<CheckedAmountValue>(compareOpertor, conditionType)
						.setValue(new CheckedAmountValue((Integer) fixedValue));
				break;
			case TIME_DURATION:
				this.checkedCondition = new CompareSingleValue<CheckedTimeDuration>(compareOpertor, conditionType)
						.setValue(new CheckedTimeDuration((Integer) fixedValue));
				break;
			case TIME_WITH_DAY:
				this.checkedCondition = new CompareSingleValue<TimeWithDayAttr>(compareOpertor, conditionType)
						.setValue(new TimeWithDayAttr((Integer) fixedValue));
				break;
			case TIMES:
				this.checkedCondition = new CompareSingleValue<CheckedTimesValue>(compareOpertor, conditionType)
						.setValue(new CheckedTimesValue((Integer) fixedValue));
				break;
			}
		}
		return this;
	}

	/**
	 * 
	 * @param compareOperator
	 * @param startValue
	 * @param endValue
	 * @return itself
	 */
	public ErAlAttendanceItemCondition setCompareRange(int compareOperator, Object startValue, Object endValue) {
		switch (this.conditionAtr) {
		case AMOUNT_VALUE:
			this.checkedCondition = new CompareRange<CheckedTimeDuration>(compareOperator)
					.setStartValue(new CheckedTimeDuration((Integer) startValue))
					.setEndValue(new CheckedTimeDuration((Integer) endValue));
			break;
		case TIME_DURATION:
			this.checkedCondition = new CompareRange<CheckedTimeDuration>(compareOperator)
					.setStartValue(new CheckedTimeDuration((Integer) startValue))
					.setEndValue(new CheckedTimeDuration((Integer) endValue));
			break;
		case TIME_WITH_DAY:
			this.checkedCondition = new CompareRange<TimeWithDayAttr>(compareOperator)
					.setStartValue(new TimeWithDayAttr((Integer) startValue))
					.setEndValue(new TimeWithDayAttr((Integer) endValue));
			break;
		case TIMES:
			this.checkedCondition = new CompareRange<CheckedTimesValue>(compareOperator)
					.setStartValue(new CheckedTimesValue((Integer) startValue))
					.setEndValue(new CheckedTimesValue((Integer) endValue));
			break;
		}
		return this;
	}

}
