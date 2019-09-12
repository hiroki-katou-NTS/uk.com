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
	/**超過時間*/
	private String agreementTime36;
	/**超過上限時間*/
	private String maxTime;
	
	private String cssAgree;	
	/**超過回数*/
	private String excessFrequency;
	/**超過上限回数*/
	private String maxNumber;
	
	private String cssFrequency;
	/**36協定情報を表示する*/
	private boolean showAgreement;
}
