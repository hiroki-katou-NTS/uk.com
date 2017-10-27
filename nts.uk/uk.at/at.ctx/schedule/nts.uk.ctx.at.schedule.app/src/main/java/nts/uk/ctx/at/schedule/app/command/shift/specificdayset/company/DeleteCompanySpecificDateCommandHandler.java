package nts.uk.ctx.at.schedule.app.command.shift.specificdayset.company;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DeleteCompanySpecificDateCommandHandler extends CommandHandler<DeleteCompanySpecificDateCommand> {

	@Inject
	private CompanySpecificDateRepository repo;

	@Override
	protected void handle(CommandHandlerContext<DeleteCompanySpecificDateCommand> context) {
		String companyId = AppContexts.user().companyId();
		repo.DeleteComSpecDate(companyId, context.getCommand().getYearMonth());
	}

}
