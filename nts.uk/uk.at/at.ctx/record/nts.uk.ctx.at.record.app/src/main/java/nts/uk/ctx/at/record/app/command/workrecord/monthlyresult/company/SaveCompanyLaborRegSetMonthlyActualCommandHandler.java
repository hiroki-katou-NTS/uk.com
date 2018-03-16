package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.company;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

@Stateless
public class SaveCompanyLaborRegSetMonthlyActualCommandHandler extends CommandHandler<SaveCompanyLaborRegSetMonthlyActualCommand>{

	
	//@Inject
	//private CompanyLaborRegSetMonthlyActualRepository comLaborRegSetMonthlyActualRepo;
	
	@Override
	protected void handle(CommandHandlerContext<SaveCompanyLaborRegSetMonthlyActualCommand> context) {
		
		
	}

}
