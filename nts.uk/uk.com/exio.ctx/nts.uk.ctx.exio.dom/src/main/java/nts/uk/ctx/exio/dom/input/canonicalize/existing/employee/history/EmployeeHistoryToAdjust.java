package nts.uk.ctx.exio.dom.input.canonicalize.existing.employee.history;

import lombok.Value;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * 既存の社員の履歴の補正
 */
@Value
public class EmployeeHistoryToAdjust {

	/** 実行コンテキスト */
	ExecutionContext context;
	
	/** 補正対象の社員ID */
	String employeeId;
	
	/** 補正対象の履歴項目のID */
	String historyId;
	
	/** 補正後の期間 */
	DatePeriod adjustedPeriod;
	
	public static EmployeeHistoryToAdjust of(ExecutionContext context, String employeeId, DateHistoryItem item) {
		return new EmployeeHistoryToAdjust(context, employeeId, item.identifier(), item.span());
	}
}
