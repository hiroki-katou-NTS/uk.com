package nts.uk.ctx.at.schedule.app.command.budget.premium.language;

import lombok.NoArgsConstructor;

import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@NoArgsConstructor
public class InsertPremiumItemLanguageCommand {
	private String companyID;

	private Integer displayNumber;

	private String langID;

	private String name;

	public InsertPremiumItemLanguageCommand(String companyID, Integer displayNumber, String langID, String name) {
		super();
		this.companyID = companyID;
		this.displayNumber = displayNumber;
		this.langID = langID;
		this.name = name;
	}

}
