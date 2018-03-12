package nts.uk.ctx.at.request.app.find.application.common.dto;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.ApplicationMetaOutput;

@Value
public class ApplicationMetaDto {
	private String appID;
	private int appType;
	private GeneralDate appDate;
	
	public static ApplicationMetaDto fromDomain(ApplicationMetaOutput applicationMetaOutput){
		return new ApplicationMetaDto(
				applicationMetaOutput.getAppID(), 
				applicationMetaOutput.getAppType().value, 
				applicationMetaOutput.getAppDate());
	}
}
