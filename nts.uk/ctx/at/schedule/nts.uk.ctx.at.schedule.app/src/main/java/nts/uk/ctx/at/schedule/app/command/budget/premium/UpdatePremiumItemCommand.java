package nts.uk.ctx.at.schedule.app.command.budget.premium;

import java.math.BigDecimal;

import lombok.Value;

@Value
public class UpdatePremiumItemCommand {
	private String companyID;
	
	private BigDecimal iD;
	
	private BigDecimal attendanceID;
	
	private String name;

	private BigDecimal displayNumber;

	private int useAtr;
}
