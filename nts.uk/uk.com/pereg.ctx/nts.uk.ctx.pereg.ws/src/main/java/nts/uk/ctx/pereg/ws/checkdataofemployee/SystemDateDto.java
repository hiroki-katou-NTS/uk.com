package nts.uk.ctx.pereg.ws.checkdataofemployee;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;


@AllArgsConstructor
@Value
public class SystemDateDto {
	
	private GeneralDate referenceDate;

}
