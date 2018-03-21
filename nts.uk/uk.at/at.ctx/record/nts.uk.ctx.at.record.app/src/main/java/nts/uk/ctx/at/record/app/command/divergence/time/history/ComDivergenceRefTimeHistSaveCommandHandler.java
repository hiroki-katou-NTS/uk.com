package nts.uk.ctx.at.record.app.command.divergence.time.history;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeHistory;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeHistoryRepository;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class ComDivergenceRefTimeHistSaveCommandHandler.
 */
@Stateless
public class ComDivergenceRefTimeHistSaveCommandHandler extends CommandHandler<ComDivergenceRefTimeHistSaveCommand>{
	
	/** The history repo. */
	@Inject
	private CompanyDivergenceReferenceTimeHistoryRepository historyRepo;
	
	/** The item repo. */
	@Inject
	private CompanyDivergenceReferenceTimeRepository itemRepo;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<ComDivergenceRefTimeHistSaveCommand> context) {
		//get company Id
		String companyId = AppContexts.user().companyId();
		
		//get command
		ComDivergenceRefTimeHistSaveCommand command = context.getCommand();
		
		// validate start date , end date
		if (GeneralDate.fromString(command.getStartDate(), "yyyy/MM/dd").compareTo(GeneralDate.fromString(command.getEndDate(), "yyyy/MM/dd")) > 0) {
			throw new BusinessException("Msg_917");
		}
		
		//validate duplicate history
		DatePeriod period = new DatePeriod(GeneralDate.fromString(command.getStartDate(), "yyyy/MM/dd"), GeneralDate.fromString(command.getEndDate(), "yyyy/MM/dd"));
		Integer count = this.historyRepo.countByDatePeriod(companyId, period);
		if (count.intValue() > 0){
			throw new BusinessException("106");
		}
		
		//convert to domain
		CompanyDivergenceReferenceTimeHistory domain = new CompanyDivergenceReferenceTimeHistory(command);
		
		//find
		CompanyDivergenceReferenceTimeHistory find = this.historyRepo.findByHistId(domain.getHistoryItems().get(0).identifier());
		
		// check and save
		if(find.getHistoryItems().isEmpty()){
			//create history
			this.historyRepo.add(domain);
			if(!command.isCopyData()){
				//make default data for Company DivergenceReference Time
				this.itemRepo.addDefaultDataWhenCreateHistory(domain.getHistoryItems().get(0).identifier());
			}else {
				CompanyDivergenceReferenceTimeHistory latestHist = this.historyRepo.findLatestHist(companyId);
				this.itemRepo.copyDataFromLatestHistory(domain.getHistoryItems().get(0).identifier(), latestHist.getHistoryItems().get(0).identifier());
			}
		} else {
			//update history
			this.historyRepo.update(domain);
		}
		
	}

}
