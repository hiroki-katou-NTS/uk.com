package nts.uk.ctx.at.request.dom.application.common.service.other.output;

import lombok.Value;

@Value
public class AppCompltLeaveSyncOutput {

	private String absId;
	private String recId;
	private boolean sync;
	private int type;
}
