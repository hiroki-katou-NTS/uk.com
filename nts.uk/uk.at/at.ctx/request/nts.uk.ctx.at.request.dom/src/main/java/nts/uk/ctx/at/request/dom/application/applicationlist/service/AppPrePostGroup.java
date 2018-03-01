package nts.uk.ctx.at.request.dom.application.applicationlist.service;

import lombok.Value;

@Value
public class AppPrePostGroup {
	//事前
	private String preAppID;
	//事後
	private String postAppID;
	//実績
	private String time;
}
