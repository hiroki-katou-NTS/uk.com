package nts.uk.ctx.at.schedule.app.command.shift.specificdayset.company;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class InsertCompanySpecificDateCommandHandler extends CommandHandler<List<CompanySpecificDateCommand>> {

	@Inject
	private CompanySpecificDateRepository repo;

	@Override
	protected void handle(CommandHandlerContext<List<CompanySpecificDateCommand>> context) {
		
		String companyId = AppContexts.user().companyId();
		
		List<CompanySpecificDateItem> listInsert = context.getCommand()
				.stream()
				.map(c -> CompanySpecificDateItem.createFromJavaType(
				companyId,
				c.getSpecificDate(),
				c.getSpecificDateNo(),
				"")).collect(Collectors.toList());
		repo.InsertComSpecDate(listInsert);
	}

}
