package nts.uk.ctx.at.request.dom.application.applist.service;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class DataMasterOutput {

	private List<AppMasterInfo> lstAppMasterInfo;
	//EA2236
	private List<String> lstSCD;
	private Map<String, List<String>> mapAppBySCD;//key - SCD, value - lstAppID
}
