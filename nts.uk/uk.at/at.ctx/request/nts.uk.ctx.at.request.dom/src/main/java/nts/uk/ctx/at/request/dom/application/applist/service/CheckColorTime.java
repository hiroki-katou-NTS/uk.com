package nts.uk.ctx.at.request.dom.application.applist.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class CheckColorTime {

	private String appID;
	//0: null
	//1: xin truoc < xin sau; k co xin truoc; xin truoc bi denail
	//2: thuc te < xin sau
	@Setter
	private int colorAtr;
}
