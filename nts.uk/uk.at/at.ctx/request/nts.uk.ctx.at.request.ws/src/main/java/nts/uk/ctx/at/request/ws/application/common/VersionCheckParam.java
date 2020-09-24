package nts.uk.ctx.at.request.ws.application.common;

import lombok.Data;

@Data
public class VersionCheckParam {
	String appID;
	int version;
}
