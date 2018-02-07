package nts.uk.ctx.at.request.dom.application.applicationlist.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.Application_New;

@Getter
@AllArgsConstructor
public class AppMasterInfo {

	private Application_New app;
	private String dispName;
	private String empName;
	private String workplaceName;
}
