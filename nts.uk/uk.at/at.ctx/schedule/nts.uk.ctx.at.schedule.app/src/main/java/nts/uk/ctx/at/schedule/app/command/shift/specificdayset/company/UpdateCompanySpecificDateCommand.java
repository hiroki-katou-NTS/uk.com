package nts.uk.ctx.at.schedule.app.command.shift.specificdayset.company;

import java.math.BigDecimal;
import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.schedule.app.command.shift.specificdayset.workplace.WorkplaceSpecificDateCommand;

@Value
public class UpdateCompanySpecificDateCommand {

	private BigDecimal specificDate;

	private List<BigDecimal> specificDateItemNo;
	
	private boolean isUpdate;
}
