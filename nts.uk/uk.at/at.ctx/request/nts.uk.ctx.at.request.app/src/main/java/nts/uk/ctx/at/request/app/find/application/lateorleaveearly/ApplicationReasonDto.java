package nts.uk.ctx.at.request.app.find.application.lateorleaveearly;

import lombok.Value;

@Value
public class ApplicationReasonDto {
	
	private String reasonID;
	
	private String reasonTemp;
	
	private int defaultFlg;
	
}
