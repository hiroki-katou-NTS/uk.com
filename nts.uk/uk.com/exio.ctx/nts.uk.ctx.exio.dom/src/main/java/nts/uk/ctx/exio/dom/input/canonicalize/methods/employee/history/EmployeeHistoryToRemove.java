package nts.uk.ctx.exio.dom.input.canonicalize.methods.employee.history;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.ExecutionContext;

/**
 * 既存の社員の履歴の削除
 */
@Value
public class EmployeeHistoryToRemove {

	/** 実行コンテキスト */
	ExecutionContext context;
	
	/** 補正対象の社員ID */
	String employeeId;
	
	/** 補正対象の履歴項目のID */
	String historyId;
	
	public static EmployeeHistoryToRemove of(ExecutionContext context, String employeeId, String historyId) {
		return new EmployeeHistoryToRemove(context, employeeId, historyId);
	}
}
