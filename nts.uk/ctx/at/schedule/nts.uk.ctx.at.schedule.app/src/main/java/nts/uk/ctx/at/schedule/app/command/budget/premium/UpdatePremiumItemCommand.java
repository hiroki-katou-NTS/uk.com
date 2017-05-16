package nts.uk.ctx.at.schedule.app.command.budget.premium;

import java.math.BigDecimal;

import lombok.Value;

@Value
public class UpdatePremiumItemCommand {
	private String companyID;
	
	private Integer iD;
	
	private Integer attendanceID;
	
	private String name;

	private Integer displayNumber;

	private int useAtr;
}
