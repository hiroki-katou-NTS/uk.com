package nts.uk.ctx.sys.shared.pub.toppagealarmpub;

import lombok.Data;

@Data
public class ExecutionLogErrorDetail {
	/** エラーメッセージ */
	private String errorMessage;
	
	/** 対象社員ID */
	private String targerEmployee;
}
