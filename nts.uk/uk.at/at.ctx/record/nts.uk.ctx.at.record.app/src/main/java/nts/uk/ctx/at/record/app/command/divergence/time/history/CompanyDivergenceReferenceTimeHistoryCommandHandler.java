package nts.uk.ctx.at.record.app.command.divergence.time.history;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeHistory;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeHistoryRepository;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CompanyDivergenceReferenceTimeHistoryCommandHandler extends CommandHandler<CompanyDivergenceReferenceTimeHistoryCommand>{
	
	@Inject
	private CompanyDivergenceReferenceTimeHistoryRepository historyRepo;
	
	@Inject
	private CompanyDivergenceReferenceTimeRepository itemRepo;

	@Override
	protected void handle(CommandHandlerContext<CompanyDivergenceReferenceTimeHistoryCommand> context) {
		
		
		String companyId = AppContexts.user().companyId();
		
		CompanyDivergenceReferenceTimeHistoryCommand command = context.getCommand();
		
		CompanyDivergenceReferenceTimeHistory domain = new CompanyDivergenceReferenceTimeHistory(command);
		
		CompanyDivergenceReferenceTimeHistory find = this.historyRepo.findByHistId(companyId, domain.getHistoryItems().get(0).identifier());
		
		if(find.getHistoryItems().isEmpty()){
			//create history
			this.historyRepo.add(domain);
			//make default data for Company DivergenceReference Time
			this.itemRepo.addDefaultDataWhenCreateHistory(domain.getHistoryItems().get(0).identifier());
		} else {
			
		}
		
	}

}
