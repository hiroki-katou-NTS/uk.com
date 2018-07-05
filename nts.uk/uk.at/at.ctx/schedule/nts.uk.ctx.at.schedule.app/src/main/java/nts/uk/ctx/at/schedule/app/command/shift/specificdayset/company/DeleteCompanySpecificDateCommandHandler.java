package nts.uk.ctx.at.schedule.app.command.shift.specificdayset.company;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DeleteCompanySpecificDateCommandHandler extends CommandHandler<DeleteCompanySpecificDateCommand> {

	@Inject
	private CompanySpecificDateRepository repo;
	
	private static final String DATE_FORMAT = "yyyy/MM/dd";

	@Override
	protected void handle(CommandHandlerContext<DeleteCompanySpecificDateCommand> context) {
		String companyId = AppContexts.user().companyId();
		GeneralDate startDate = GeneralDate.fromString(context.getCommand().getStartDate(), DATE_FORMAT);
		GeneralDate endDate = GeneralDate.fromString(context.getCommand().getEndDate(), DATE_FORMAT);
		repo.DeleteComSpecDate(companyId, startDate, endDate);
	}

}
