package nts.uk.ctx.at.request.app.command.application.appabsence;

import lombok.Data;

@Data
public class AppForSpecLeaveCmd {
	
	/**
	 * 喪主フラグ
	 */
	private boolean mournerFlag;
	/**
	 * 続柄コード
	 */
	private String relationshipCD;
	/**
	 * 続柄理由
	 */
	private String relationshipReason;
}
