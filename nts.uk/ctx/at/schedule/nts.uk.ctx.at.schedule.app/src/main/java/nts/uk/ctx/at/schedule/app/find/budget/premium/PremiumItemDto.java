package nts.uk.ctx.at.schedule.app.find.budget.premium;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.schedule.dom.budget.premium.PremiumName;
import nts.uk.ctx.at.schedule.dom.budget.premium.UseAttribute;

@AllArgsConstructor
@Value
public class PremiumItemDto {
	private String companyID;
	
	private Integer iD;
	
	private Integer attendanceID;
	
	private String name;

	private Integer displayNumber;

	private int useAtr;
}
