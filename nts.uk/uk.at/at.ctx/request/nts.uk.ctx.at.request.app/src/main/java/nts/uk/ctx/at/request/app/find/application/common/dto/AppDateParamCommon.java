package nts.uk.ctx.at.request.app.find.application.common.dto;

import lombok.Data;

@Data
public class AppDateParamCommon {
	private Integer appTypeValue; 
	private String appDate;
	private Boolean isStartup;
	private String appID;
	private String employeeID;
	private String overtimeAtrParam;
}
