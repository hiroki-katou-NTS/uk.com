package nts.uk.ctx.at.record.app.command.divergence.time.history;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeHistory;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeHistoryRepository;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeRepository;

@Stateless
public class ComDivergenceRefTimeHistSaveCommandHandler extends CommandHandler<ComDivergenceRefTimeHistSaveCommand>{
	
	@Inject
	private CompanyDivergenceReferenceTimeHistoryRepository historyRepo;
	
	@Inject
	private CompanyDivergenceReferenceTimeRepository itemRepo;

	@Override
	protected void handle(CommandHandlerContext<ComDivergenceRefTimeHistSaveCommand> context) {
		
		
//		String companyId = AppContexts.user().companyId();
		
		ComDivergenceRefTimeHistSaveCommand command = context.getCommand();
		
		CompanyDivergenceReferenceTimeHistory domain = new CompanyDivergenceReferenceTimeHistory(command);
		
		CompanyDivergenceReferenceTimeHistory find = this.historyRepo.findByHistId(domain.getHistoryItems().get(0).identifier());
		
		if(find.getHistoryItems().isEmpty()){
			//create history
			this.historyRepo.add(domain);
			//make default data for Company DivergenceReference Time
			this.itemRepo.addDefaultDataWhenCreateHistory(domain.getHistoryItems().get(0).identifier());
		} else {
			//update history
			this.historyRepo.update(domain);
		}
		
	}

}
