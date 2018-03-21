package nts.uk.ctx.at.record.app.command.divergence.time.history;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.divergence.time.history.WorkTypeDivergenceReferenceTimeHistory;
import nts.uk.ctx.at.record.dom.divergence.time.history.WorkTypeDivergenceReferenceTimeHistoryRepository;
import nts.uk.ctx.at.record.dom.divergence.time.history.WorkTypeDivergenceReferenceTimeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class WorkTypeDivergenceRefTimeHistSaveCommandHandler.
 */
@Stateless
public class WorkTypeDivergenceRefTimeHistSaveCommandHandler extends CommandHandler<WorkTypeDivergenceRefTimeHistSaveCommand>{
	
	/** The history repo. */
	@Inject
	private WorkTypeDivergenceReferenceTimeHistoryRepository historyRepo;
	
	/** The item repo. */
	@Inject
	private WorkTypeDivergenceReferenceTimeRepository itemRepo;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<WorkTypeDivergenceRefTimeHistSaveCommand> context) {
		//get company Id
		String companyId = AppContexts.user().companyId();
		
		//get command
		WorkTypeDivergenceRefTimeHistSaveCommand command = context.getCommand();
		
		// validate start date , end date
		if (command.getStartDate().compareTo(command.getEndDate()) > 0) {
			throw new BusinessException("Msg_917");
		}
		
		//validate duplicate history
		DatePeriod period = new DatePeriod(command.getStartDate(), command.getEndDate());
		Integer count = this.historyRepo.countByDatePeriod(companyId, new WorkTypeCode(command.getWorkTypeCodes()), period);
		if (count.intValue() > 0){
			throw new BusinessException("106");
		}
		
		//convert to domain
		WorkTypeDivergenceReferenceTimeHistory domain = new WorkTypeDivergenceReferenceTimeHistory(command);
		
		//find
		WorkTypeDivergenceReferenceTimeHistory find = this.historyRepo.findByKey(domain.getHistoryItems().get(0).identifier());
		
		// check and save
		if(find.getHistoryItems().isEmpty()){
			//create history
			this.historyRepo.add(domain);
			if(!command.isCopyData()){
				//make default data for Company DivergenceReference Time
				this.itemRepo.addDefaultDataWhenCreateHistory(domain.getHistoryItems().get(0).identifier());
			}else {
				WorkTypeDivergenceReferenceTimeHistory latestHist = this.historyRepo.findLatestHist(companyId, new WorkTypeCode(command.getWorkTypeCodes()));
				this.itemRepo.copyDataFromLatestHistory(domain.getHistoryItems().get(0).identifier(), latestHist.getHistoryItems().get(0).identifier());
			}
		} else {
			//update history
			this.historyRepo.update(domain);
		}
	}

}
