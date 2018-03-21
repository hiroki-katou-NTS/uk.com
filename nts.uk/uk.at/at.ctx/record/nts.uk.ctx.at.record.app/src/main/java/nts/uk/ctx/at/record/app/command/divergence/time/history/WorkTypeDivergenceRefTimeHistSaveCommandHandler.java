package nts.uk.ctx.at.record.app.command.divergence.time.history;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.record.dom.divergence.time.history.WorkTypeDivergenceReferenceTimeHistory;
import nts.uk.ctx.at.record.dom.divergence.time.history.WorkTypeDivergenceReferenceTimeHistoryRepository;
import nts.uk.ctx.at.record.dom.divergence.time.history.WorkTypeDivergenceReferenceTimeRepository;
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
		
		BundledBusinessException exceptions = BundledBusinessException.newInstance();
		
		//get command
		WorkTypeDivergenceRefTimeHistSaveCommand command = context.getCommand();
		
		// validate start date , end date
		if (GeneralDate.fromString(command.getStartDate(), "yyyy/MM/dd").compareTo(GeneralDate.fromString(command.getEndDate(), "yyyy/MM/dd")) > 0) {
			exceptions.addMessage("Msg_917");
			exceptions.throwExceptions();
		}
		
		//validate duplicate history
		DatePeriod period = new DatePeriod(GeneralDate.fromString(command.getStartDate(), "yyyy/MM/dd"), GeneralDate.fromString(command.getEndDate(), "yyyy/MM/dd"));
		Integer count = this.historyRepo.countByDatePeriod(companyId, new BusinessTypeCode(command.getWorkTypeCodes()) ,period);
		if (count.intValue() > 0){
			exceptions.addMessage("Msg_106");
			exceptions.throwExceptions();
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
				WorkTypeDivergenceReferenceTimeHistory latestHist = this.historyRepo.findLatestHist(companyId, new BusinessTypeCode(command.getWorkTypeCodes()));
				this.itemRepo.copyDataFromLatestHistory(domain.getHistoryItems().get(0).identifier(), latestHist.getHistoryItems().get(0).identifier());
			}
		} else {
			//update history
			this.historyRepo.update(domain);
		}
	}

}
