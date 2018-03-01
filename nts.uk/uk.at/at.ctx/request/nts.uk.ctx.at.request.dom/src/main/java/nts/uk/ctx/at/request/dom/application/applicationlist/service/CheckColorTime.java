package nts.uk.ctx.at.request.dom.application.applicationlist.service;

import lombok.Value;

@Value
public class CheckColorTime {

	private String appID;
	//0: null
	//1: xin truoc < xin sau; k co xin truoc; xin truoc bi denail
	//2: thuc te < xin sau
	private int colorAtr;
}
