package nts.uk.ctx.at.request.dom.application.applist.service;

import lombok.Value;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppCompltLeaveFull;

@Value
public class AppCompltLeaveSync {
	//0 - abs
	//1 - rec
	private int typeApp;
	private boolean sync;
	private AppCompltLeaveFull appMain;
	private AppCompltLeaveFull appSub;
	private String appDateSub;
	private String appInputSub;
}
