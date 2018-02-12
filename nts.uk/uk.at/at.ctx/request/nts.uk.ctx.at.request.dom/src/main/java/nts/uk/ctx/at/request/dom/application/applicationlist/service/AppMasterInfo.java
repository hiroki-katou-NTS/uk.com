package nts.uk.ctx.at.request.dom.application.applicationlist.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AppMasterInfo {

	// 申請ID
	private String appID;
	private int appType;
	private String dispName;
	private String empName;
	private String workplaceName;
}
