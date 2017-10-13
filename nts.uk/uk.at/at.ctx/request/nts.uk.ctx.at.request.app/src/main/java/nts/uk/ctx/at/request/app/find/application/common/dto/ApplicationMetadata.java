package nts.uk.ctx.at.request.app.find.application.common.dto;

import lombok.Value;

@Value
public class ApplicationMetadata {
	private String appID;
	
	private int appType;
}
