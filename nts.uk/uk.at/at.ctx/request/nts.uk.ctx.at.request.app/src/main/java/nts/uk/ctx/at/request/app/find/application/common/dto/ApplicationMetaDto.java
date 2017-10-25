package nts.uk.ctx.at.request.app.find.application.common.dto;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class ApplicationMetaDto {
	private String appID;
	private int appType;
	private GeneralDate appDate;
}
