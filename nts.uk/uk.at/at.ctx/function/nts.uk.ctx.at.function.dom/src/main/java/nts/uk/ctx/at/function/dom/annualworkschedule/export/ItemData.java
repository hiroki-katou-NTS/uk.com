package nts.uk.ctx.at.function.dom.annualworkschedule.export;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeStatusOfMonthly;

@Value
@AllArgsConstructor
public class ItemData {
	private BigDecimal value;
	private AgreementTimeStatusOfMonthly status;
}
