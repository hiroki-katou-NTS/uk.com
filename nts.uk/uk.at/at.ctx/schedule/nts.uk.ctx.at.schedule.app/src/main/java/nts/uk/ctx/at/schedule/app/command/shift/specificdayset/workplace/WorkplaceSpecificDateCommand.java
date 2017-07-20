package nts.uk.ctx.at.schedule.app.command.shift.specificdayset.workplace;

import java.math.BigDecimal;

import lombok.Value;

@Value
public class WorkplaceSpecificDateCommand {

	private BigDecimal specificDate;

	private BigDecimal specificDateNo;
}
