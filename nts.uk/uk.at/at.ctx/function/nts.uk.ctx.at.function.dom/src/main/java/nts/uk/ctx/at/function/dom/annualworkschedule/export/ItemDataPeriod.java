package nts.uk.ctx.at.function.dom.annualworkschedule.export;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreMaxTimeStatusOfMonthly;
@Value
@AllArgsConstructor
public class ItemDataPeriod {
	private BigDecimal value;
	private AgreMaxTimeStatusOfMonthly status;
}
