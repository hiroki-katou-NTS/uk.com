package nts.uk.ctx.at.schedule.dom.budget.premium;

import lombok.EqualsAndHashCode;
import lombok.Value;
/**
 * 割増時間項目
 * @author Doan Duy Hung
 *
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class PremiumItem {
	
	private String companyID;
	
	private Integer displayNumber;
	
	private PremiumName name;

	private UseAttribute useAtr;

	public PremiumItem(String companyID, Integer displayNumber, PremiumName name, UseAttribute useAtr) {
		super();
		this.companyID = companyID;
		this.displayNumber = displayNumber;
		this.name = name;
		this.useAtr = useAtr;
	}
}
