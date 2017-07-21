package nts.uk.ctx.at.schedule.app.command.shift.specificdayset.workplace;

import java.math.BigDecimal;
import java.util.List;

import lombok.Value;

@Value
public class WorkplaceSpecificDateCommand {
	
	private String workPlaceId;

	private BigDecimal specificDate;

	private List<BigDecimal> specificDateItemNo;
	
	private boolean isUpdate;
}
