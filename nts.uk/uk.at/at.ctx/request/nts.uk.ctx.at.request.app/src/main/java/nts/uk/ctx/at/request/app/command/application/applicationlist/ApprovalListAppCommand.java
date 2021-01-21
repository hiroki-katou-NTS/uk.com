package nts.uk.ctx.at.request.app.command.application.applicationlist;

import lombok.Value;

@Value
public class ApprovalListAppCommand {

	private String appId;
	private int version;
}
