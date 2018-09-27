/**
 * 9:19:22 AM Mar 29, 2018
 */
package nts.uk.ctx.at.record.app.command.workrecord.erroralarm.monthlycondition;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.condition.ErAlAtdItemConditionDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.ErAlAttendanceItemCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionAtr;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ErrorAlarmConditionType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycondition.MonthlyCorrectExtractCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycondition.TimeItemCheckMonthly;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.AttendanceItemId;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedAmountValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimeDuration;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimesValue;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * @author hungnm
 *
 */
@Getter
@Setter
public class UpdateMonthlyCorrectConCmd {

	/* 会社ID */
	private String companyId;
	/* コード */
	private String code;
	/* 名称 */
	private String name;
	/* 使用する */
	private int useAtr;
	private int operatorBetweenGroups;
	private int operatorGroup1;
	private int operatorGroup2;
	private boolean group2UseAtr;
	private List<ErAlAtdItemConditionDto> erAlAtdItemConditionGroup1;
	private List<ErAlAtdItemConditionDto> erAlAtdItemConditionGroup2;
	private int newMode;

	public UpdateMonthlyCorrectConCmd(String companyId, String code, String name, int useAtr, int operatorBetweenGroups,
			int operatorGroup1, int operatorGroup2, boolean group2UseAtr,
			List<ErAlAtdItemConditionDto> erAlAtdItemConditionGroup1,
			List<ErAlAtdItemConditionDto> erAlAtdItemConditionGroup2, int newMode) {
		super();
		this.companyId = companyId;
		this.code = code;
		this.name = name;
		this.useAtr = useAtr;
		this.operatorBetweenGroups = operatorBetweenGroups;
		this.operatorGroup1 = operatorGroup1;
		this.operatorGroup2 = operatorGroup2;
		this.group2UseAtr = group2UseAtr;
		this.erAlAtdItemConditionGroup1 = erAlAtdItemConditionGroup1;
		this.erAlAtdItemConditionGroup2 = erAlAtdItemConditionGroup2;
		this.newMode = newMode;
	}

	public UpdateMonthlyCorrectConCmd() {
		super();
	}

	@SuppressWarnings("unchecked")
	private <V> ErAlAttendanceItemCondition<V> convertAtdIemConToDomain(ErAlAtdItemConditionDto atdItemCon) {
		ErAlAttendanceItemCondition<V> atdItemConDomain = new ErAlAttendanceItemCondition<V>(companyId, code,
				atdItemCon.getTargetNO(), atdItemCon.getConditionAtr(), atdItemCon.isUseAtr(),
				atdItemCon.getConditionType());
		// Set Target
		if (atdItemCon.getConditionAtr() == ConditionAtr.TIME_WITH_DAY.value || atdItemCon.getConditionAtr() == ErrorAlarmConditionType.INPUT_CHECK.value) {
			atdItemConDomain.setUncountableTarget(atdItemCon.getUncountableAtdItem());
		} else {
			atdItemConDomain.setCountableTarget(atdItemCon.getCountableAddAtdItems(),
					atdItemCon.getCountableSubAtdItems());
		}
		// Set Compare
		if (atdItemCon.getConditionType() < 2) {
			if (atdItemCon.getCompareOperator() > 5) {
				if (atdItemCon.getConditionAtr() == ConditionAtr.AMOUNT_VALUE.value) {
					atdItemConDomain.setCompareRange(atdItemCon.getCompareOperator(),
							(V) new CheckedAmountValue(atdItemCon.getCompareStartValue().intValue()),
							(V) new CheckedAmountValue(atdItemCon.getCompareEndValue().intValue()));
				} else if (atdItemCon.getConditionAtr() == ConditionAtr.TIME_DURATION.value) {
					atdItemConDomain.setCompareRange(atdItemCon.getCompareOperator(),
							(V) new CheckedTimeDuration(atdItemCon.getCompareStartValue().intValue()),
							(V) new CheckedTimeDuration(atdItemCon.getCompareEndValue().intValue()));
				} else if (atdItemCon.getConditionAtr() == ConditionAtr.TIME_WITH_DAY.value) {
					atdItemConDomain.setCompareRange(atdItemCon.getCompareOperator(),
							(V) new TimeWithDayAttr(atdItemCon.getCompareStartValue().intValue()),
							(V) new TimeWithDayAttr(atdItemCon.getCompareEndValue().intValue()));
				} else if (atdItemCon.getConditionAtr() == ConditionAtr.TIMES.value) {
					atdItemConDomain.setCompareRange(atdItemCon.getCompareOperator(),
							(V) new CheckedTimesValue(atdItemCon.getCompareStartValue().intValue()),
							(V) new CheckedTimesValue(atdItemCon.getCompareEndValue().intValue()));
				}
			} else {
				if (atdItemCon.getConditionType() == ConditionType.FIXED_VALUE.value) {
					if (atdItemCon.getConditionAtr() == ConditionAtr.AMOUNT_VALUE.value) {
						atdItemConDomain.setCompareSingleValue(atdItemCon.getCompareOperator(),
								atdItemCon.getConditionType(),
								(V) new CheckedAmountValue(atdItemCon.getCompareStartValue().intValue()));
					} else if (atdItemCon.getConditionAtr() == ConditionAtr.TIME_DURATION.value) {
						atdItemConDomain.setCompareSingleValue(atdItemCon.getCompareOperator(),
								atdItemCon.getConditionType(),
								(V) new CheckedTimeDuration(atdItemCon.getCompareStartValue().intValue()));
					} else if (atdItemCon.getConditionAtr() == ConditionAtr.TIME_WITH_DAY.value) {
						atdItemConDomain.setCompareSingleValue(atdItemCon.getCompareOperator(),
								atdItemCon.getConditionType(),
								(V) new TimeWithDayAttr(atdItemCon.getCompareStartValue().intValue()));
					} else if (atdItemCon.getConditionAtr() == ConditionAtr.TIMES.value) {
						atdItemConDomain.setCompareSingleValue(atdItemCon.getCompareOperator(),
								atdItemCon.getConditionType(),
								(V) new CheckedTimesValue(atdItemCon.getCompareStartValue() == null ? 0 : atdItemCon.getCompareStartValue().intValue()));
					}
				} else {
					atdItemConDomain.setCompareSingleValue(atdItemCon.getCompareOperator(), atdItemCon.getConditionType(),
							(V) new AttendanceItemId(atdItemCon.getSingleAtdItem()));
				}
			}
		} else {
			atdItemConDomain.setInputCheck(atdItemCon.getInputCheckCondition().intValue());
		}
		return atdItemConDomain;
	}

	public MonthlyCorrectExtractCondition toMonthlyCorrectExtractCondition() {
		MonthlyCorrectExtractCondition domain = MonthlyCorrectExtractCondition.init(companyId, code, name, useAtr == 1);
		return domain;
	}

	public TimeItemCheckMonthly toTimeItemCheckMonthly(String checkId) {
		TimeItemCheckMonthly domain = TimeItemCheckMonthly.init();
		domain.setCheckId(checkId);
		// Set AttendanceItemCondition
		List<ErAlAttendanceItemCondition<?>> conditionsGroup1 = erAlAtdItemConditionGroup1.stream()
				.map(atdItemCon -> convertAtdIemConToDomain(atdItemCon)).collect(Collectors.toList());

		List<ErAlAttendanceItemCondition<?>> conditionsGroup2 = erAlAtdItemConditionGroup2.stream()
				.map(atdItemCon -> convertAtdIemConToDomain(atdItemCon)).collect(Collectors.toList());
		domain.createAttendanceItemCondition(operatorBetweenGroups, group2UseAtr)
				.setAttendanceItemConditionGroup1(operatorGroup1, conditionsGroup1)
				.setAttendanceItemConditionGroup2(operatorGroup2, conditionsGroup2);
		return domain;
	}
}
