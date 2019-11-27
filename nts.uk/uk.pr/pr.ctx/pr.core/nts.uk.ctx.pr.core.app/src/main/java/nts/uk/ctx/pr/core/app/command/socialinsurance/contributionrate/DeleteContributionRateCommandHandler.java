package nts.uk.ctx.pr.core.app.command.socialinsurance.contributionrate;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.app.command.socialinsurance.contributionrate.command.AddContributionRateHistoryCommand;
import nts.uk.ctx.pr.core.dom.socialinsurance.contribution.service.ContributionService;
import nts.uk.shr.com.history.YearMonthHistoryItem;

@Stateless
@Transactional
public class DeleteContributionRateCommandHandler extends CommandHandler<AddContributionRateHistoryCommand> {

	@Inject
	private ContributionService contributionService;

	@Override
	protected void handle(CommandHandlerContext<AddContributionRateHistoryCommand> context) {
		String officeCode = context.getCommand().getOfficeCode();
		YearMonthHistoryItem yearMonthItem = context.getCommand().getYearMonthHistoryItem().fromCommandToDomain();
		contributionService.deleteHistory(officeCode, yearMonthItem);
	}

}