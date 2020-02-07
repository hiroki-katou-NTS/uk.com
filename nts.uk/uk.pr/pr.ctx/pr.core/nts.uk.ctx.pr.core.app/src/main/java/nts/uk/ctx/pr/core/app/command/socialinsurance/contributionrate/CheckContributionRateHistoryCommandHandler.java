package nts.uk.ctx.pr.core.app.command.socialinsurance.contributionrate;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.pr.core.app.command.socialinsurance.contributionrate.command.CheckContributionRateHistoryCommand;
import nts.uk.ctx.pr.core.dom.socialinsurance.contribution.ContributionRate;
import nts.uk.ctx.pr.core.dom.socialinsurance.contribution.service.ContributionService;
import nts.uk.shr.com.history.YearMonthHistoryItem;

@Stateless
public class CheckContributionRateHistoryCommandHandler extends CommandHandlerWithResult<CheckContributionRateHistoryCommand, Boolean> {

	@Inject
	private ContributionService contributionService;

	@Override
	protected Boolean handle(CommandHandlerContext<CheckContributionRateHistoryCommand> context) {
		String officeCode = context.getCommand().getOfficeCode();
		YearMonthHistoryItem yearMonthItem = context.getCommand().getYearMonthHistoryItem().fromCommandToDomain();
		ContributionRate contributionRate = context.getCommand().getContributionRate().fromCommandToDomain(officeCode);
		return contributionService.checkContributionRate(contributionRate, yearMonthItem);
	}
}
