package nts.uk.screen.at.app.ksus01.a;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitInformationDto {
	
	private boolean publicOpAtr;
	
	private boolean workDesiredOpAtr;
	
	private String endDatePublicationPeriod;
	
//	private DatePeriod period;
	private String start;
	
	private String end;
}
