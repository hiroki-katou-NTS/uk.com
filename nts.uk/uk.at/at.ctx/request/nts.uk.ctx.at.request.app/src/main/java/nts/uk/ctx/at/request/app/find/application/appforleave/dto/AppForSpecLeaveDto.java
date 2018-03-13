package nts.uk.ctx.at.request.app.find.application.appforleave.dto;

import lombok.Data;

@Data
public class AppForSpecLeaveDto {
	/**
	 * 申請ID
	 */
	private String appID;
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
