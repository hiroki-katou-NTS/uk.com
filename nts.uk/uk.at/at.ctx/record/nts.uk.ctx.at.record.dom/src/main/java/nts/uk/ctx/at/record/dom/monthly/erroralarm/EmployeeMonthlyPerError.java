package nts.uk.ctx.at.record.dom.monthly.erroralarm;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AnnualLeaveError;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.ReserveLeaveError;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * @author dungdt
 * 社員の月別実績エラー一覧
 */
@Getter
@NoArgsConstructor
public class EmployeeMonthlyPerError extends AggregateRoot{
	
	private int no;
	
	/**
	 * エラー種類: 月別実績のエラー種類
	 */
	private ErrorType errorType;
	
	/**
	 * 年月
	 */
	private YearMonth yearMonth;
	
	/** エラー発生社員: 社員ID*/
	private String employeeID;
	
	/**
	 * 締めID
	 */
	private ClosureId closureId;
	
	/**
	 * 締め日: 日付
	 */
	private ClosureDate closureDate;
	
	/**
	 * フレックス: フレックスエラー
	 */
	private Optional<Flex> flex;
	
	/**
	 * 年休: 年休エラー
	 */
	private Optional<AnnualLeaveError> annualHoliday;
	
	/**
	 * 積立年休: 積立年休エラー
	 */
	private Optional<ReserveLeaveError> yearlyReserved;

	public EmployeeMonthlyPerError(ErrorType errorType, YearMonth yearMonth, String employeeID, ClosureId closureId,
			ClosureDate closureDate, Flex flex, AnnualLeaveError annualHoliday,
			ReserveLeaveError yearlyReserved) {
		super();
		this.errorType = errorType;
		this.yearMonth = yearMonth;
		this.employeeID = employeeID;
		this.closureId = closureId;
		this.closureDate = closureDate;
		this.flex = Optional.ofNullable(flex);
		this.annualHoliday = Optional.ofNullable(annualHoliday);
		this.yearlyReserved = Optional.ofNullable(yearlyReserved);
	}
	
	public EmployeeMonthlyPerError(int no, ErrorType errorType, YearMonth yearMonth, String employeeID, ClosureId closureId,
			ClosureDate closureDate, Flex flex, AnnualLeaveError annualHoliday,
			ReserveLeaveError yearlyReserved) {
		super();
		this.errorType = errorType;
		this.yearMonth = yearMonth;
		this.employeeID = employeeID;
		this.closureId = closureId;
		this.closureDate = closureDate;
		this.flex = Optional.ofNullable(flex);
		this.annualHoliday = Optional.ofNullable(annualHoliday);
		this.yearlyReserved = Optional.ofNullable(yearlyReserved);
		this.no = no;
	}
}
