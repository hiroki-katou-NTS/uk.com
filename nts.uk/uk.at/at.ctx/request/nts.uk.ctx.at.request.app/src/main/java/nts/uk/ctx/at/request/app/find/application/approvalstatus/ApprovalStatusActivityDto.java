package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * @author dat.lh
 */
@AllArgsConstructor
@Value
public class ApprovalStatusActivityDto {
	private String wkpId;
	private int monthConfirm;
	private int monthUnconfirm;
	private int personConfirm;
	private int personUnconfirm;
	private int bossConfirm;
	private int bossUnconfirm;
	private int checkMail;
}
