package nts.uk.ctx.at.function.ac.monthlyremain;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.StatusOfHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.checkremainnumber.CheckRemainNumberMonFunImport;
import nts.uk.ctx.at.function.dom.adapter.monthlyremain.AnnualLeaveUsageImport;
import nts.uk.ctx.at.function.dom.adapter.monthlyremain.CheckResultRemainMonthlyAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthlyremain.DayoffCurrentMonthOfEmployeeImport;
import nts.uk.ctx.at.function.dom.adapter.monthlyremain.ReserveLeaveUsageImport;
import nts.uk.ctx.at.function.dom.adapter.periodofspecialleave.SpecialHolidayImported;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.RangeCompareType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.SingleValueCompareType;

@Stateless
public class CheckResultRemainMonthly implements CheckResultRemainMonthlyAdapter{

	@Override
	public boolean checkAnnualLeaveUsage(CheckRemainNumberMonFunImport checkRemainNumberMonFunImport,
			AnnualLeaveUsageImport annualLeaveUsageImport) {
		int typeOperator = checkRemainNumberMonFunImport.getCheckOperatorType();
		double actualValue = annualLeaveUsageImport.getRemainingDays().v();
		return check(typeOperator, actualValue, checkRemainNumberMonFunImport);
	}

	@Override
	public boolean checkDayoffCurrentMonth(CheckRemainNumberMonFunImport checkRemainNumberMonFunImport,
			DayoffCurrentMonthOfEmployeeImport dayoffCurrentMonthOfEmployeeImport) {
		int typeOperator = checkRemainNumberMonFunImport.getCheckOperatorType();
		double actualValue = dayoffCurrentMonthOfEmployeeImport.getRemainingDays();
		return check(typeOperator, actualValue, checkRemainNumberMonFunImport);
	}

	@Override
	public boolean checkStatusOfHoliday(CheckRemainNumberMonFunImport checkRemainNumberMonFunImport,
			StatusOfHolidayImported statusOfHolidayImported) {
		int typeOperator = checkRemainNumberMonFunImport.getCheckOperatorType();
		double actualValue = statusOfHolidayImported.getRemainingDays();
		return check(typeOperator, actualValue, checkRemainNumberMonFunImport);
	}

	@Override
	public boolean checkReserveLeaveUsage(CheckRemainNumberMonFunImport checkRemainNumberMonFunImport,
			ReserveLeaveUsageImport reserveLeaveUsageImport) {
		int typeOperator = checkRemainNumberMonFunImport.getCheckOperatorType();
		double actualValue = reserveLeaveUsageImport.getRemainingDays().v() ;
		return check(typeOperator, actualValue, checkRemainNumberMonFunImport);
	}

	@Override
	public boolean checkSpecialHoliday(CheckRemainNumberMonFunImport checkRemainNumberMonFunImport,
			SpecialHolidayImported specialHolidayImported) {
		int typeOperator = checkRemainNumberMonFunImport.getCheckOperatorType();
		double actualValue = specialHolidayImported.getRemainDays();
		return check(typeOperator, actualValue, checkRemainNumberMonFunImport);
	}
	
	private boolean check(Integer typeOperator,Double actualValue, CheckRemainNumberMonFunImport checkRemainNumberMonFunImport){
		boolean check = false;
		//check single
		if(typeOperator == 0){
			int compare =  checkRemainNumberMonFunImport.getCompareSingleValueEx().getCompareOperator();
			double targetValue = checkRemainNumberMonFunImport.getCompareSingleValueEx().getValue().getDaysValue().doubleValue();
			check = CompareSingle(
					targetValue,
					EnumAdaptor.valueOf(compare,SingleValueCompareType.class),
					actualValue
					);
		}
		//check range
		else {
			int compare =  checkRemainNumberMonFunImport.getCompareRangeEx().getCompareOperator();
			double targetValueEnd = checkRemainNumberMonFunImport.getCompareRangeEx().getEndValue().getDaysValue().doubleValue();
			double targetValueStart = checkRemainNumberMonFunImport.getCompareRangeEx().getStartValue().getDaysValue().doubleValue();
			check = CompareDouble(actualValue, targetValueStart, targetValueEnd, EnumAdaptor.valueOf(compare,RangeCompareType.class));
		}
		return check;
	}
	
	private boolean CompareSingle(Double target,SingleValueCompareType compareType, Double compare) {
		if(target == null) {
			return false;
		}
		switch (compareType) {
		case EQUAL:
			return target.compareTo(compare) == 0;
		case GREATER_OR_EQUAL:
			return target.compareTo(compare) >= 0;
		case GREATER_THAN:
			return target.compareTo(compare) > 0;
		case LESS_OR_EQUAL:
			return target.compareTo(compare) <= 0;
		case LESS_THAN:
			return target.compareTo(compare) < 0;
		case NOT_EQUAL:
			return target.compareTo(compare) != 0;
		default:
			throw new RuntimeException("invalid compareOpertor: " + compareType);
		}
	}
	private boolean CompareDouble(Double valueActual, Double valueAgreementStart, Double valueAgreementEnd,
			RangeCompareType compare) {
		boolean check = false;
		
		switch (compare) {
		/* 範囲の間（境界値を含まない）（＜＞） */
		case BETWEEN_RANGE_OPEN:
			if (valueActual.compareTo(valueAgreementStart) > 0 && valueActual.compareTo(valueAgreementEnd) < 0) {
				check = true;
			}
			break;
		/* 範囲の間（境界値を含む）（≦≧） */
		case BETWEEN_RANGE_CLOSED:
			if (valueActual.compareTo(valueAgreementStart) >= 0 && valueActual.compareTo(valueAgreementEnd) <= 0) {
				check = true;
			}
			break;
		/* 範囲の外（境界値を含まない）（＞＜） */
		case OUTSIDE_RANGE_OPEN:
			if (valueActual.compareTo(valueAgreementStart) < 0 || valueActual.compareTo(valueAgreementEnd) > 0) {
				check = true;
			}
			break;
		/* 範囲の外（境界値を含む）（≧≦） */
		case OUTSIDE_RANGE_CLOSED:
			if (valueActual.compareTo(valueAgreementStart) <= 0 || valueActual.compareTo(valueAgreementEnd) >= 0) {
				check = true;
			}
			break;
		default:
			throw new RuntimeException("invalid compareOpertor: " + compare);
		}
		return check;
	}

}
