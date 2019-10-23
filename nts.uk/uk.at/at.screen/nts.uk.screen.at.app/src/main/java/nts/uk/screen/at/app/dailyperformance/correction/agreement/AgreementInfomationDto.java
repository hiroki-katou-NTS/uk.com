package nts.uk.screen.at.app.dailyperformance.correction.agreement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AgreementInfomationDto {
 
	private String agreementTime36;
	
	private String maxTime;
	
	private String cssAgree;
	
	private String excessFrequency;
	
	private String maxNumber;
	
	private String cssFrequency;
	
	private boolean showAgreement;
}
