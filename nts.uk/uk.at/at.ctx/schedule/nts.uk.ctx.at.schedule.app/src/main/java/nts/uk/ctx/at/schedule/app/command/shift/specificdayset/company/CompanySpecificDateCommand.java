package nts.uk.ctx.at.schedule.app.command.shift.specificdayset.company;

import java.math.BigDecimal;

import lombok.Value;

@Value
public class CompanySpecificDateCommand {

	private BigDecimal specificDate;

	private BigDecimal specificDateNo;
}
