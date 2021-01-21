package nts.uk.ctx.at.request.app.command.application.lateorleaveearly;

import lombok.Value;

/**
 * 
 * @author hieult
 *
 */
@Value
public class DeleteLateOrLeaveEarlyCommand {
	private int version;
	private String companyID;
	private String appID;
}
