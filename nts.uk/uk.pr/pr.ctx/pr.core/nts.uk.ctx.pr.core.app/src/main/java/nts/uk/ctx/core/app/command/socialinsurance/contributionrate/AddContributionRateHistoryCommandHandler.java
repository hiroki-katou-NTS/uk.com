package nts.uk.ctx.core.app.command.socialinsurance.contributionrate;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.app.command.socialinsurance.contributionrate.command.AddContributionRateHistoryCommand;
import nts.uk.ctx.core.dom.socialinsurance.contribution.ContributionRate;
import nts.uk.ctx.core.dom.socialinsurance.contribution.service.ContributionService;
import nts.uk.shr.com.history.YearMonthHistoryItem;

@Stateless
public class AddContributionRateHistoryCommandHandler extends CommandHandler<AddContributionRateHistoryCommand> {

	@Inject
	private ContributionService contributionService;

	@Override
	protected void handle(CommandHandlerContext<AddContributionRateHistoryCommand> context) {
		String officeCode = context.getCommand().getOfficeCode();
		YearMonthHistoryItem yearMonthItem = context.getCommand().getYearMonthHistoryItem().fromCommandToDomain();
		ContributionRate contributionRate = context.getCommand().getContributionRate().fromCommandToDomain();
		// 新規登録処理
		contributionService.registerContributionRate(officeCode, contributionRate, yearMonthItem);
	}
}
