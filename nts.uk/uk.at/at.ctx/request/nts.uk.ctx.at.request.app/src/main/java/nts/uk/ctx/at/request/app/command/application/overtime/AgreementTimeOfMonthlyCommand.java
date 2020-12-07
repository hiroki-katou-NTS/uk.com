package nts.uk.ctx.at.request.app.command.application.overtime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfMonthly;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgreementTimeOfMonthlyCommand {

	public Integer agreementTime;
	
	public OneMonthTimeCommand threshold;
	
	public AgreementTimeOfMonthly toDomain() {
		
		return AgreementTimeOfMonthly.of(
				new AttendanceTimeMonth(agreementTime),
				threshold.toDomain());
	}
}
