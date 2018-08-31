package nts.uk.ctx.at.request.app.command.applicationreason;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteApplicationReasonCommand {
	/**
	 * 申請種類
	 */
	private int appType;
	
	/** 理由ID */
	private String reasonID;
}
