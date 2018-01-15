package nts.uk.ctx.at.schedule.pub.budget.premium;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Data
@AllArgsConstructor
public class PremiumItemDto {

	private String companyID;
	
	private Integer displayNumber;
	
	private String name;

	private Integer useAtr;
}
