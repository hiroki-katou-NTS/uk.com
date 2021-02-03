/**
 * 4:01:07 PM Mar 28, 2018
 */
package nts.uk.ctx.at.record.app.find.workrecord.erroralarm.monthlycondition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.condition.ErAlAtdItemConditionDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.ErAlAttendanceItemCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionAtr;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycondition.MonthlyCorrectExtractCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycondition.TimeItemCheckMonthly;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.AttendanceItemId;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedAmountValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimeDuration;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimesValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimesValueDay;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * @author hungnm
 *
 */
@Setter
@Getter
public class MonthlyCorrectConditionDto {

	/* 会社ID */
	private String companyId;
	/* コード */
	private String code;
	/* 名称 */
	private String name;
	/* 使用する */
	private int useAtr;
	/* ID */
	private String errorAlarmCheckID;
	private int operatorBetweenGroups;
	private int operatorGroup1;
	private int operatorGroup2;
	private boolean group2UseAtr;
	private List<ErAlAtdItemConditionDto> erAlAtdItemConditionGroup1;
	private List<ErAlAtdItemConditionDto> erAlAtdItemConditionGroup2;

	private static ErAlAtdItemConditionDto convertItemDomainToDto(ErAlAttendanceItemCondition<?> itemDomain) {
		ErAlAtdItemConditionDto erAlAtdItemConditionDto = new ErAlAtdItemConditionDto();
		erAlAtdItemConditionDto.setTargetNO(itemDomain.getTargetNO());
		erAlAtdItemConditionDto.setConditionAtr(itemDomain.getConditionAtr().value);
		erAlAtdItemConditionDto.setUseAtr(itemDomain.isUse());
		// Check Target
		// チェック対象
		if (itemDomain.getConditionAtr() == ConditionAtr.TIME_WITH_DAY) {
			erAlAtdItemConditionDto.setUncountableAtdItem(itemDomain.getUncountableTarget().getAttendanceItem());
		} else {
			erAlAtdItemConditionDto.setCountableAddAtdItems(
					itemDomain.getCountableTarget().getAddSubAttendanceItems().getAdditionAttendanceItems());
			erAlAtdItemConditionDto.setCountableSubAtdItems(
					itemDomain.getCountableTarget().getAddSubAttendanceItems().getSubstractionAttendanceItems());
		}
		// Check Condition
		// チェック条件
		if (itemDomain.getCompareRange() != null) {
			switch (itemDomain.getConditionAtr()) {
				case AMOUNT_VALUE:
					erAlAtdItemConditionDto.setCompareStartValue(
							new BigDecimal(((CheckedAmountValue) itemDomain.getCompareRange().getStartValue()).v()));
					erAlAtdItemConditionDto.setCompareEndValue(
							new BigDecimal(((CheckedAmountValue) itemDomain.getCompareRange().getEndValue()).v()));
					break;
				case TIME_DURATION:
					erAlAtdItemConditionDto.setCompareStartValue(
							new BigDecimal(((CheckedTimeDuration) itemDomain.getCompareRange().getStartValue()).v()));
					erAlAtdItemConditionDto.setCompareEndValue(
							new BigDecimal(((CheckedTimeDuration) itemDomain.getCompareRange().getEndValue()).v()));
					break;
				case TIME_WITH_DAY:
					erAlAtdItemConditionDto.setCompareStartValue(
							new BigDecimal(((TimeWithDayAttr) itemDomain.getCompareRange().getStartValue()).v()));
					erAlAtdItemConditionDto.setCompareEndValue(
							new BigDecimal(((TimeWithDayAttr) itemDomain.getCompareRange().getEndValue()).v()));
					break;
				case TIMES:
					erAlAtdItemConditionDto.setCompareStartValue(
							new BigDecimal(((CheckedTimesValue) itemDomain.getCompareRange().getStartValue()).v()));
					erAlAtdItemConditionDto.setCompareEndValue(
							new BigDecimal(((CheckedTimesValue) itemDomain.getCompareRange().getEndValue()).v()));
					break;
				case DAYS:
					erAlAtdItemConditionDto.setCompareStartValue(
							new BigDecimal(((CheckedTimesValueDay) itemDomain.getCompareRange().getStartValue()).v()));
					erAlAtdItemConditionDto.setCompareEndValue(
							new BigDecimal(((CheckedTimesValueDay) itemDomain.getCompareRange().getEndValue()).v()));
					break;
			}
			erAlAtdItemConditionDto.setCompareOperator(itemDomain.getCompareRange().getCompareOperator().value);
		} else if (itemDomain.getCompareSingleValue() != null) {
			if (itemDomain.getCompareSingleValue().getConditionType() == ConditionType.FIXED_VALUE) {
				switch (itemDomain.getConditionAtr()) {
					case AMOUNT_VALUE:
						erAlAtdItemConditionDto.setCompareStartValue(
								new BigDecimal(((CheckedAmountValue) itemDomain.getCompareSingleValue().getValue()).v()));
						break;
					case TIME_DURATION:
						erAlAtdItemConditionDto.setCompareStartValue(
								new BigDecimal(((CheckedTimeDuration) itemDomain.getCompareSingleValue().getValue()).v()));
						break;
					case TIME_WITH_DAY:
						erAlAtdItemConditionDto.setCompareStartValue(
								new BigDecimal(((TimeWithDayAttr) itemDomain.getCompareSingleValue().getValue()).v()));
						break;
					case TIMES:
						erAlAtdItemConditionDto.setCompareStartValue(
								new BigDecimal(((CheckedTimesValue) itemDomain.getCompareSingleValue().getValue()).v()));
						break;
					case DAYS:
						erAlAtdItemConditionDto.setCompareStartValue(
								new BigDecimal(((CheckedTimesValueDay) itemDomain.getCompareSingleValue().getValue()).v()));
						break;
				}
			} else {
				erAlAtdItemConditionDto
						.setSingleAtdItem(((AttendanceItemId) itemDomain.getCompareSingleValue().getValue()).v());
			}
			erAlAtdItemConditionDto.setConditionType(itemDomain.getCompareSingleValue().getConditionType().value);
			erAlAtdItemConditionDto.setCompareOperator(itemDomain.getCompareSingleValue().getCompareOpertor().value);
		}
		return erAlAtdItemConditionDto;
	}

	public MonthlyCorrectConditionDto() {
		super();
	}

	public static MonthlyCorrectConditionDto fromDomain(MonthlyCorrectExtractCondition monthlyCorrectExtractCondition,
			TimeItemCheckMonthly timeItemCheckMonthly) {
		MonthlyCorrectConditionDto dto = new MonthlyCorrectConditionDto();
		if (monthlyCorrectExtractCondition != null) {
			dto.companyId = monthlyCorrectExtractCondition.getCompanyId();
			dto.code = monthlyCorrectExtractCondition.getCode().v().substring(1);
			dto.name = monthlyCorrectExtractCondition.getName().v();
			dto.useAtr = monthlyCorrectExtractCondition.getUseAtr() ? 1 : 0;
			dto.errorAlarmCheckID = monthlyCorrectExtractCondition.getErrorAlarmCheckID();
		}
		if (timeItemCheckMonthly != null) {
			dto.errorAlarmCheckID = timeItemCheckMonthly.getErrorAlarmCheckID();
			dto.operatorBetweenGroups = timeItemCheckMonthly.getAtdItemCondition().getOperatorBetweenGroups().value;
			dto.operatorGroup1 = timeItemCheckMonthly.getAtdItemCondition().getGroup1().getConditionOperator().value;
			dto.operatorGroup2 = timeItemCheckMonthly.getAtdItemCondition().getGroup2().getConditionOperator().value;
			dto.group2UseAtr = timeItemCheckMonthly.getAtdItemCondition().isUseGroup2();
			// Set ErAlAtdItemConditionDto
			List<ErAlAtdItemConditionDto> erAlAtdItemConditionGroup1 = new ArrayList<>();
			List<ErAlAtdItemConditionDto> erAlAtdItemConditionGroup2 = new ArrayList<>();
			List<ErAlAttendanceItemCondition<?>> atdItemConditionDomain1 = timeItemCheckMonthly.getAtdItemCondition()
					.getGroup1().getLstErAlAtdItemCon();
			for (ErAlAttendanceItemCondition<?> itemDomain : atdItemConditionDomain1) {
				ErAlAtdItemConditionDto erAlAtdItemConditionDto = convertItemDomainToDto(itemDomain);
				erAlAtdItemConditionGroup1.add(erAlAtdItemConditionDto);
			}
			dto.setErAlAtdItemConditionGroup1(erAlAtdItemConditionGroup1);
			List<ErAlAttendanceItemCondition<?>> atdItemConditionDomain2 = timeItemCheckMonthly.getAtdItemCondition()
					.getGroup2().getLstErAlAtdItemCon();
			for (ErAlAttendanceItemCondition<?> itemDomain : atdItemConditionDomain2) {
				ErAlAtdItemConditionDto erAlAtdItemConditionDto = convertItemDomainToDto(itemDomain);
				erAlAtdItemConditionGroup2.add(erAlAtdItemConditionDto);
			}
			dto.setErAlAtdItemConditionGroup2(erAlAtdItemConditionGroup2);
		}
		return dto;
	}
}
