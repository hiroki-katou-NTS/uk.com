/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.jobtitle.history;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BundledBusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.jobtitle.JobTitle;
import nts.uk.ctx.bs.employee.dom.jobtitle.JobTitleRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.history.JobTitleHistory;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfo;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfoRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveJobTitleHistoryCommandHandler.
 */
@Stateless
@Transactional
public class SaveJobTitleHistoryCommandHandler extends CommandHandler<SaveJobTitleHistoryCommand> {

    /** The job title repository. */
    @Inject
    private JobTitleRepository jobTitleRepository;

    /** The job title info repository. */
    @Inject
    private JobTitleInfoRepository jobTitleInfoRepository;
    
    /** The Constant DATE_FORMAT. */
    private static final String DATE_FORMAT = "yyyy/MM/dd";
    
    /** The Constant MAX_DATE. */
    private static final String MAX_DATE = "9999/12/31";
    
    /** The Constant LIST_HISTORY_MIN_SIZE. */
    private static final Integer LIST_HISTORY_MIN_SIZE = 1;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveJobTitleHistoryCommand> context) {
		SaveJobTitleHistoryCommand command = context.getCommand();
        String companyId = AppContexts.user().companyId();
        
        Optional<JobTitle> opJobTitle = this.jobTitleRepository.findByJobTitleId(companyId, command.getJobTitleId());
        if (!opJobTitle.isPresent()) {
            throw new RuntimeException(String.format("Job title %s not found!", command.getJobTitleId()));
        }
        JobTitle jobTitle = opJobTitle.get();

        if (command.getIsAddMode()) {
            this.addJobTitleHistory(companyId, command, jobTitle);
        } else {
            this.updateJobTitleHistory(companyId, command, jobTitle);
        }
	}
	
	/**
	 * Adds the job title history.
	 *
	 * @param companyId the company id
	 * @param command the command
	 * @param jobTitle the job title
	 */
	private void addJobTitleHistory (String companyId, SaveJobTitleHistoryCommand command, JobTitle jobTitle) {
				
		// Get new history
		JobTitle newEntity = command.toDomain(companyId);
		newEntity.getLastestHistory().updateEndDate(GeneralDate.fromString(MAX_DATE, DATE_FORMAT));
		JobTitleHistory currentHistory = jobTitle.getLastestHistory();
		
		// Validate
		this.validHistory(Boolean.TRUE,
				currentHistory,
				newEntity.getLastestHistory(),
				Boolean.TRUE);
		
		// Add new history
		this.jobTitleRepository.add(newEntity);
		
		// Update previous history	
		if (CollectionUtil.isEmpty(jobTitle.getJobTitleHistory())) {           
			return;
        } 		
		int previousDay = -1;		
		GeneralDate updatedEndDate = newEntity.getLastestHistory().getPeriod().start().addDays(previousDay);
		this.updateHistory(companyId, 
				currentHistory.getHistoryId().v(), 
				updatedEndDate);
		
		// Add new JobTitleInfo for new history id
		this.addJobTitleInfo(companyId, 
				jobTitle.getJobTitleId().v(), 
				currentHistory.getHistoryId().v(), 
				newEntity.getLastestHistory().getHistoryId().v());
	}
	
	/**
	 * Update job title history.
	 *
	 * @param companyId the company id
	 * @param command the command
	 * @param jobTitle the job title
	 */
	private void updateJobTitleHistory (String companyId, SaveJobTitleHistoryCommand command, JobTitle jobTitle) {
			
		// Get new history
		JobTitle updateEntity = command.toDomain(companyId);
		updateEntity.getLastestHistory().updateEndDate(GeneralDate.fromString(MAX_DATE, DATE_FORMAT));
		int indexPreviousHistory = 1;
		JobTitleHistory previousHistory = jobTitle.getJobTitleHistory().get(indexPreviousHistory);
			
		// Validate
		this.validHistory(Boolean.FALSE, 
				previousHistory,
				updateEntity.getLastestHistory(),
				updateEntity.getLastestHistory().getHistoryId().equals(jobTitle.getLastestHistory().getHistoryId()));
		
		// Add new history
		this.jobTitleRepository.update(updateEntity);
		
		// Update previous history	
		if (jobTitle.getJobTitleHistory().size() == LIST_HISTORY_MIN_SIZE) {           
			return;
        } 		
		int previousDay = -1;		
		GeneralDate updatedEndDate = updateEntity.getLastestHistory().getPeriod().start().addDays(previousDay);
		this.updateHistory(companyId, 
				previousHistory.getHistoryId().v(), 
				updatedEndDate);
	}
	
	/**
	 * Valid start date.
	 *
	 * @param isAddMode the is add mode
	 * @param currentStartDate the current start date
	 * @param newStartDate the new start date
	 */
	private void validHistory(boolean isAddMode, JobTitleHistory currentHistory, JobTitleHistory newHistory, boolean isLastestHistory) {
		boolean isError = false;
        BundledBusinessException exceptions = BundledBusinessException.newInstance();
			
        // Valid only new history can be edited
        if (!isAddMode) {
        	if (!isLastestHistory) {
        		isError = true;
        		exceptions.addMessage("Msg_154");
        	}
        }
        
        // Valid start date
        if (currentHistory.getPeriod().start().afterOrEquals(newHistory.getPeriod().start())) {            
            isError = true;
            if (isAddMode) {
            	// Add mode
            	exceptions.addMessage("Msg_102");
            } else {
            	// Edit mode
            	exceptions.addMessage("Msg_127");
            }
        }        
     
        // Has error, throws message
        if (isError) {
            exceptions.throwExceptions();
        }
	}
	
	/**
	 * Update history.
	 *
	 * @param companyId the company id
	 * @param historyId the history id
	 * @param endĐate the end đate
	 */
	private void updateHistory(String companyId, String historyId, GeneralDate endĐate) {
		Optional<JobTitle> opJobTitle = this.jobTitleRepository.findByHistoryId(companyId, historyId);		
        if (!opJobTitle.isPresent()) {
            throw new RuntimeException(String.format("History id %s didn't existed.", historyId));
        }
        
        // Set end date of previous history
        JobTitle jobTitle = opJobTitle.get();       
        jobTitle.getLastestHistory().updateEndDate(endĐate);
        this.jobTitleRepository.update(jobTitle);
	}
	
	/**
	 * Adds the job title info.
	 *
	 * @param companyId the company id
	 * @param jobTitleId the job title id
	 * @param originHistoryId the origin history id
	 * @param cloneHistoryId the clone history id
	 */
	private void addJobTitleInfo(String companyId, String jobTitleId, String originHistoryId, String cloneHistoryId) {			
		Optional<JobTitleInfo> opJobTitleInfo = this.jobTitleInfoRepository.find(companyId, jobTitleId, originHistoryId);
        if (opJobTitleInfo.isPresent()) {
        	JobTitleInfo jobTitleInfo = opJobTitleInfo.get().clone(cloneHistoryId);
            this.jobTitleInfoRepository.add(jobTitleInfo);
        }
	}
}
