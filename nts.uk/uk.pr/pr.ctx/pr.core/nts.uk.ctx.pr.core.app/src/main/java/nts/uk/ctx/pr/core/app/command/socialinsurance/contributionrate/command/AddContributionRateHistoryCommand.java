package nts.uk.ctx.pr.core.app.command.socialinsurance.contributionrate.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.pr.core.app.command.socialinsurance.welfarepensioninsurance.command.YearMonthHistoryItemCommand;

@Data
@AllArgsConstructor
public class AddContributionRateHistoryCommand {
	private String officeCode;
	private YearMonthHistoryItemCommand yearMonthHistoryItem;
	private ContributionRateCommand contributionRate;
}