package nts.uk.ctx.at.request.dom.application.common.service.other.output;

import lombok.Value;

@Value
public class AppCompltLeaveSyncOutput {
	//don xin nghi
	private String absId;
	//don lam bu
	private String recId;
	//dong bo??
	private boolean sync;
	//0 - xin nghi
	//1 - lam bu
	private int type;
}
