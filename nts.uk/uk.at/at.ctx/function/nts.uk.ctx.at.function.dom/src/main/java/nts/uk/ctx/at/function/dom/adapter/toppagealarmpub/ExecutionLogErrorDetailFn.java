package nts.uk.ctx.at.function.dom.adapter.toppagealarmpub;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@NoArgsConstructor
public class ExecutionLogErrorDetailFn {
	/** エラーメッセージ */
	private String errorMessage;
	
	/** 対象社員ID */
	private String targerEmployee;

	public ExecutionLogErrorDetailFn(String errorMessage, String targerEmployee) {
		super();
		this.errorMessage = errorMessage;
		this.targerEmployee = targerEmployee;
	}
	
	
}
