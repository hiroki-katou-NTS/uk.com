package nts.uk.screen.at.app.mobi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.screen.at.app.ktgwidget.find.dto.AgreementTimeOfMonthlyDto;

@AllArgsConstructor
@Getter
public class AgreementTimeToppage {
	public String yearMonth;
	
	public AgreementTimeOfMonthlyDto agreementTimeOfMonthlyDto;
}
