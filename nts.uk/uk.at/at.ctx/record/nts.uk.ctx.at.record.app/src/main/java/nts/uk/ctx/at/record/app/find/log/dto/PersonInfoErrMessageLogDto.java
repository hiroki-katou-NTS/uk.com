package nts.uk.ctx.at.record.app.find.log.dto;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class PersonInfoErrMessageLogDto {
	
	private String personCode;

	private String personName;
	
	private GeneralDate disposalDay;
	
	private String messageError;
}
