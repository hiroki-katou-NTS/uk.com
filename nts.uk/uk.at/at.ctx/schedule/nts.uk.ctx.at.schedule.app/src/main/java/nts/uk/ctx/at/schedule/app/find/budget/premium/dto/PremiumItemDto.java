package nts.uk.ctx.at.schedule.app.find.budget.premium.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.budget.premium.language.PremiumItemLanguage;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
@Setter
public class PremiumItemDto {
	private String companyID;

	private Integer displayNumber;

	private String name;

	private int useAtr;

	public static PremiumItemDto fromDomainPremiumItemLanguage(PremiumItemLanguage premiumItem) {
		return new PremiumItemDto(premiumItem.getCompanyID(), premiumItem.getDisplayNumber(),
				!premiumItem.getName().isPresent()?null:premiumItem.getName().get().v());
	}

	public PremiumItemDto(String companyID, Integer displayNumber, String name) {
		super();
		this.companyID = companyID;
		this.displayNumber = displayNumber;
		this.name = name;
	}
}
